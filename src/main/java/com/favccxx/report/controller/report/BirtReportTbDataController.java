package com.favccxx.report.controller.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.favccxx.report.birt.datasource.BirtDataSourceHelper;
import com.favccxx.report.birt.model.BirtDataSet;
import com.favccxx.report.birt.model.BirtDataSource;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.ReportConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.model.SysTemplateDataSet;
import com.favccxx.report.model.SysTemplateVersion;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysTemplateDataSetService;
import com.favccxx.report.service.SysTemplateService;
import com.favccxx.report.service.SysTemplateVersionService;
import com.favccxx.report.util.DateUtil;
import com.favccxx.report.util.LogUtil;
import com.favccxx.report.util.RestStaticDataUtil;

@Controller
public class BirtReportTbDataController {
	
	private static Logger logger = Logger.getLogger(BirtReportTbDataController.class);
	
	@Autowired
	SysTemplateService sysTemplateService;
	@Autowired
	SysTemplateDataSetService sysTemplateDataSetService;
	@Autowired
	SysTemplateVersionService sysTemplateVersionService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/initReportDataTblEdit")
	public ModelAndView initReportDataTblEdit(HttpServletRequest request, HttpSession session, String templateId) {
		ModelAndView mav = new ModelAndView();
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		
		if (StringUtils.isBlank(templateId) || "null".equals(templateId)) {
			mav.setViewName("/report/report_data_edit_forbidden");
			mav.addObject("message", "该报表不允许编辑。");
			return mav;			
		}
		
		SysTemplate template = sysTemplateService.findbyId(Long.parseLong(templateId));		
		//校验模板是否允许编辑
		if (!template.isEditable()) {
			mav.setViewName("/report/report_data_edit_forbidden");
			mav.addObject("message", "该报表不允许编辑，原因：报表格式不正确。");
			return mav;
		}
		
		mav.addObject("template", template);
		
		//模板参数
		Map<String, String> paramMap = new HashMap<String, String>();
		String parameter = template.getParameter();
		if(parameter!=null) {
			String parameters[] = parameter.split("&");				
			for(int i=0, l=parameters.length; i<l; i++) {
				String[] keyValue = parameters[i].split("=");
				if(keyValue!=null && keyValue.length>1) {
					paramMap.put(keyValue[0], keyValue[1]);
				}
				
			}
		}
		
		//获取报表模板文件的全路径
		String templateFolder = request.getServletContext().getInitParameter("BIRT_VIEWER_WORKING_FOLDER"); 
		String templatePath = request.getSession().getServletContext().getRealPath("") + File.separator + templateFolder + File.separator + template.getReportName();
		
		
		//根据报表模板的数据源类型分别生成新的可编辑数据集
		String templateName = "";
		List<BirtDataSet> dataSetList = null;
		Map dataMap = new HashMap();
		
		
		try {
			if(ReportConstants.REPORT_DATA_SOURCE_JDBC.equals(template.getDataSourceType())) {
				dataMap = buildDraftTemplateEdition(templatePath, paramMap);
			}else if(ReportConstants.REPORT_DATA_SOURCE_JAVASCRIPT.equals(template.getDataSourceType())) {
				dataMap = generateJsTemplateFromJs(templatePath);
			}
			
			if(dataMap.get("birtDataSets")!=null) {
				dataSetList = (List<BirtDataSet>) dataMap.get("birtDataSets");
			}
			
			if(dataMap.get("templateName")!=null) {
				templateName = (String)dataMap.get("templateName");
				SysTemplateVersion templateVersion = generateTemplateEdition(template.getId(), sessionUser.getUserId(), templateName);
				
				List<SysTemplateDataSet> templateDataSetList = generateTemplateDataSets(templateVersion.getId(), dataSetList);
				
				mav.addObject("templateVersionId", templateVersion.getId());
				mav.addObject("dataSets", templateDataSetList);
				mav.setViewName("/report/report_data_table_edit");
				return mav;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	

		LogUtil.response(LogConstants.MODULE_PROJECT, "BirtReportController.initReportEdit", LogConstants.OPERATE_QUERY,
				LogConstants.SUCCESS, "查询项目列表成功");

		return mav;
	}
	
	
	
	@RequestMapping(value="/getReportVersionTblData",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getReportVersionTblData(HttpServletRequest request, String templateId, String dataSetId, String templateVersionId) {		
		SysTemplateDataSet dataSet = sysTemplateDataSetService.getByVersionIdAndDataSetId(Long.parseLong(templateVersionId), dataSetId);
		if(dataSet!=null) {
			String dataSetFileName = dataSet.getTempFile();
			String reportData = RestStaticDataUtil.restDataFromPath(dataSetFileName);						    
			return reportData;
		}
		
		
		return "";
	}
	
	//更改报表数据集
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/changeTemplateReportData")
	@ResponseBody
	public String changeTemplateReportData(HttpSession session, @RequestParam("templateVersionId") String templateVersionId, 
			@RequestParam("dataSetId")String dataSetId, @RequestParam("changeDatas[]")String[] changeDatas) {			
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		
		SysTemplateDataSet dataSet = sysTemplateDataSetService.getByVersionIdAndDataSetId(Long.parseLong(templateVersionId), dataSetId);
		StringBuffer versionDetail = new StringBuffer(); 
		if(dataSet!=null) {
			String dataSetFileName = dataSet.getTempFile();
			String reportData = RestStaticDataUtil.restDataFromPath(dataSetFileName);	
			
			JSONArray list = JSON.parseArray(reportData);
			
			JSONArray updateDatas = new JSONArray();
			for(int i=0; i<list.size(); i++) {
				Map<String, Object> dataMap = (Map) list.get(i);
				
				for(int j=0; j<changeDatas.length; j++) {
					String datas = changeDatas[j];
					String[] dataItems = datas.split("&");
					//只有合规的数据才进行处理
					if(dataItems.length!=4) {
						continue;
					}
					
					int editRowIndex = Integer.parseInt(dataItems[1]);
					if(editRowIndex == i) {
						String dataKey = dataItems[0];
						
						for (Map.Entry<String, Object> entry : dataMap.entrySet()) {  
							  if(entry.getKey().equals(dataKey)) {
								  entry.setValue(dataItems[3]);
							  }
						}  
						
					}
				}
				updateDatas.add(dataMap);
			}
			
		
			String jsonData = JSON.toJSONString(updateDatas, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			
			
			String dataSetName = dataSet.getTempFile();		
			FileWriter fileWriter;
			
			try {
				
				fileWriter = new FileWriter(dataSetName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(jsonData);
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			//记录版本更新记录
			SysTemplateVersion templateVersion = sysTemplateVersionService.findById(Long.parseLong(templateVersionId));
			if(templateVersion!=null) {
				
				for(int j=0; j<changeDatas.length; j++) {
					String datas = changeDatas[j];
					String[] dataItems = datas.split("&");
					//只有合规的数据才进行处理
					if(dataItems.length!=4) {
						continue;
					}
					
					versionDetail.append(sessionUser.getUserId())
						.append(" ")
						.append(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"))
						.append(dataItems[0]).append("[").append(dataItems[1])
						.append("] [")
						.append(dataItems[2])
						.append("]->[")
						.append(dataItems[3])
						.append("] &#13;");
				}
				
				
				if(StringUtils.isBlank(templateVersion.getDetail())) {
					templateVersion.setDetail(versionDetail.toString());
				}else {
					templateVersion.setDetail(templateVersion.getDetail() + versionDetail.toString());
				}
				sysTemplateVersionService.saveTemplateEdition(templateVersion);
			}
			
			return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
		}
		
		return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map buildDraftTemplateEdition(String templatePath, Map<String, String> paramMap) throws DocumentException, ClassNotFoundException, SQLException, IOException {
		Map map = new HashMap();
		
		File file = new File(templatePath);
		if (!file.exists()) {
			logger.debug("File not exist, and file path is: " + templatePath);
			return map;
		}

		//读取模板文件
		SAXReader reader = new SAXReader();
		Document doc = reader.read(templatePath);
		Element root = doc.getRootElement();
		
		//获取Birt ODA 数据源列表
		List<BirtDataSource> dataSourceList = BirtDataSourceHelper.getOdaDataSourceList(root);
		//获取BIRT ODA数据集列表
		List<BirtDataSet> dataSetList = BirtDataSourceHelper.getOdaDataSets(root, paramMap);
			
		//移除ODA数据源  西智
		BirtDataSourceHelper.removeOdaDataSources(root);
		//创建JS数据源
		BirtDataSourceHelper.createJsDataSourceElements(dataSourceList, root);
		//移除ODA数据集
		BirtDataSourceHelper.removeOdaDataSets(root);
		//创建JS数据集
		BirtDataSourceHelper.createJsDataSetElements(root, dataSourceList, dataSetList);
		
		
		String templateName = UUID.randomUUID().toString() + ".rptdesign";
		String newTemplateFile = templatePath.substring(0, templatePath.lastIndexOf(File.separator)) + File.separator + templateName;
		
		Writer writer = new FileWriter(newTemplateFile);
		OutputFormat format = OutputFormat.createPrettyPrint();// 格式化
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.setEscapeText(false);
		xmlWriter.write(doc);
		xmlWriter.close();
		
		map.put("birtDataSets", dataSetList);
		map.put("templateName", templateName);
		
		return map;
		//生成新的报表模板
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map generateJsTemplateFromJs(String templatePath) throws DocumentException, ClientProtocolException, IOException {
		Map map = new HashMap();
		
		File file = new File(templatePath);
		if (!file.exists()) {
			logger.debug("File not exist, and file path is: " + templatePath);
			return map;
		}
		
		//读取模板文件
		SAXReader reader = new SAXReader();
		Document doc = reader.read(templatePath);
		Element root = doc.getRootElement();
		
		List<BirtDataSet>  dataSetList = BirtDataSourceHelper.changeJsDataSetElements(root);
		
		String templateName = UUID.randomUUID().toString() + ".rptdesign";
		String newTemplateFile = templatePath.substring(0, templatePath.lastIndexOf(File.separator)) + File.separator + templateName;
		
		Writer writer = new FileWriter(newTemplateFile);
		OutputFormat format = OutputFormat.createPrettyPrint();// 格式化
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.setEscapeText(false);
		xmlWriter.write(doc);
		xmlWriter.close();
		
		map.put("birtDataSets", dataSetList);
		map.put("templateName", templateName);
		
		return map;
		
	}
	
	private SysTemplateVersion generateTemplateEdition(long templateId, long userId, String templateFileName) {
		SysTemplateVersion version = new SysTemplateVersion();
		version.setName("草稿");
		//草稿状态
		version.setStatus(ReportConstants.TEMPLATE_STATUS_DRAFT);
		//新模板文件的名称
		version.setTemplateFileName(templateFileName);
		version.setTemplateId(templateId);
		version.setVersion("DRAFT");
		version.setUpdateUserId(userId);
		version.setUpdateTime(new Date());
		sysTemplateVersionService.createDraftEdition(version);
		return version;
	}

	private List<SysTemplateDataSet> generateTemplateDataSets(long templateVersionId, List<BirtDataSet> dataSetList){
		if(dataSetList==null) {
			return null;
		}
		
		
		List<SysTemplateDataSet> templateDataList = new ArrayList<SysTemplateDataSet>();
		for(BirtDataSet birtDataSet : dataSetList) {
			SysTemplateDataSet templateDataSet = new SysTemplateDataSet();
			templateDataSet.setDataSetId(birtDataSet.getId());
			templateDataSet.setName(birtDataSet.getDatasetName());
			templateDataSet.setTempFile(birtDataSet.getDataSetFile());
			templateDataSet.setTemplateVersionId(templateVersionId);
			sysTemplateDataSetService.saveTemplateDataSet(templateDataSet);
			templateDataList.add(templateDataSet);
		}
		return templateDataList;
	}
}
