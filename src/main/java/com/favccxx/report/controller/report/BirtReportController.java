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
import com.favccxx.report.util.LogUtil;
import com.favccxx.report.util.PropertyUtil;
import com.favccxx.report.util.RestStaticDataUtil;

@Controller
public class BirtReportController {
	
	private static Logger logger = Logger.getLogger(BirtReportController.class);
	
	@Autowired
	SysTemplateService sysTemplateService;
	@Autowired
	SysTemplateDataSetService sysTemplateDataSetService;
	@Autowired
	SysTemplateVersionService sysTemplateVersionService;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/initReportEdit")
	public ModelAndView initReportEdit(HttpServletRequest request, HttpSession session, String templateId) {

		

		ModelAndView mav = new ModelAndView();
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "编辑报表信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_REPORT);
		
		
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
				SysTemplateVersion templateVersion = generateTemplateEdition(template.getId(),sessionUser.getUserId(), templateName);
				
				List<SysTemplateDataSet> templateDataSetList = generateTemplateDataSets(templateVersion.getId(), dataSetList);
				
				mav.addObject("templateVersionId", templateVersion.getId());
				mav.addObject("dataSets", templateDataSetList);
				mav.setViewName("/report/report_data_edit");
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
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	
	
	/**
	 * 
	 * @author chenxu
	 * @date Jan 5, 2018
	 * @param templatePath 模板文件路径
	 * @param paramMap 模板参数
	 * @return
	 * @throws DocumentException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	
	
	
	/**
	 * 生成模板编辑DRAFT版本
	 * @author chenxu
	 * @date Jan 4, 2018
	 * @param templateId 模板Id
	 * @param templateFileName 模板文件名称
	 * @return
	 */
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
	
	
	/**
	 * 根据模板版本生成对应的版本数据集
	 * @author chenxu
	 * @date Jan 5, 2018
	 * @param templateVersionId 模板版本Id
	 * @param dataSetList Birt数据集
	 * @return
	 */
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
	
	
	
	//生成模板的编辑版本
	@SuppressWarnings("unused")
	private void generateDraftTemplateEdition(String templateId, String templateFileName, Element root, List<BirtDataSource> dataSourceList, List<BirtDataSet> dataSetList  ) {
		
		
		
		
		
		
		//记录模板编辑版本的数据集
		
	}
	
	
	
	
	
	
	
	@RequestMapping("/getReportVersionData")
	@ResponseBody
	public String getReportVersionData(HttpServletRequest request, String templateId, String dataSetId, String templateVersionId) {
		
		SysTemplateDataSet dataSet = sysTemplateDataSetService.getByVersionIdAndDataSetId(Long.parseLong(templateVersionId), dataSetId);
		if(dataSet!=null) {
			String dataSetFileName = dataSet.getTempFile();
			String reportData = RestStaticDataUtil.restDataFromPath(dataSetFileName);			
			if(reportData.startsWith("\"")) {
				reportData = reportData.substring(1, reportData.length()-1);
			}
			    
			return JSON.toJSONString(reportData);
		}
		
		
		return "";
	}
	
	
	
	
	
	
	@RequestMapping("/getReportData")
	@ResponseBody
	public String getReportData(HttpServletRequest request, String templateId, String dataSetId, String version) {
		LogUtil.request(LogConstants.MODULE_PROJECT, "BirtReportController.getReportData", LogConstants.OPERATE_UPDATE, "", "查询报表源数据");
		
		if(StringUtils.isBlank(templateId) || StringUtils.isBlank(dataSetId) || StringUtils.isBlank(version)) {
			return JSON.toJSONString(RestResult.error(400, "操作失败，缺少必要的参数。"));
		}
		
		SysTemplate template = sysTemplateService.findbyId(Long.parseLong(templateId));
		if(template==null) {
			return JSON.toJSONString(RestResult.error(400, "操作失败，参数错误。"));
		}
		
		
		
		
		
//		//第一次编辑时，
//		if("draft".equals(version)) {
//			String templateFolder = request.getServletContext().getInitParameter("BIRT_VIEWER_WORKING_FOLDER"); 
//			String templatePath = request.getSession().getServletContext().getRealPath("") + File.separator + templateFolder + File.separator + template.getReportName();		
//			
//			//将JDBC数据源更改成Javascript数据源
//			if(template.getDataSourceType()!=null && "JDBC".equals(template.getDataSourceType())) {
//				
//				try {
//					
//					Map map = BirtTemplateUtil.change2JsDataSource(templatePath);
//					
//					if(map.get("dataSets")==null || map.get("filePath")==null) {
//						return JSON.toJSONString(RestResult.error(400, "操作失败，系统内部错误。"));
//					}
//					
//					
//					List<BirtDataSet> list = (List<BirtDataSet>) map.get("dataSets");
//					for(BirtDataSet dataSet : list) {
//						if(dataSetId.equals(dataSet.getId())) {
//							String jsonFile = dataSet.getDataSetFile();
//							String jsonData = RestStaticDataUtil.restDataFromPath(jsonFile);
//							
//							return JSON.toJSONString(RestResult.success(jsonData));
//						}
//					}
//					
//					
////					DataSourceHelper.changeDataSource(templatePath);
//				} catch (DocumentException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
//			}
//			
//			
//		}
		
		
		
//		if(templateId!=null && !"".equals(templateId)) {
//			SysTemplate template = sysTemplateService.findbyId(Long.parseLong(templateId));
//			if(template==null) {
//				return "";
//			}
//			
//			//模板文件
//			String templateName = template.getReportName();
//			
//			String templateFolder = request.getServletContext().getInitParameter("BIRT_VIEWER_WORKING_FOLDER"); 
//			String templatePath = request.getSession().getServletContext().getRealPath("") + File.separator + templateFolder + File.separator + templateName;	
//			
//			logger.debug("template path: " + templatePath);
//		
//			List<JsDataSet> dataSetList = DataSetHelper.getDataSetNames(templatePath);
//			if(dataSetList==null) {
//				logger.error("Did not find the report template data sets.");
//				return "";
//			}
//			
//			
//			
//			
//			
//		}
//		
//		
//		
//		String url = "http://172.17.100.130/dash/api/apps/paginate";
//
//		Map<String, String> header = new HashMap<String, String>();
//		header.put("TOKEN", "ekyoffgFN5hUxD4YPxvirrxQ1tFLJxlZu1UMSrv77d3YTDADdUdbLabHVRQNIqfe");
//
//		try {
//			String res = RestClient.rest(url, header);
//			return res;
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		LogUtil.response(LogConstants.MODULE_PROJECT, "BirtReportController.getReportData", LogConstants.OPERATE_UPDATE, LogConstants.SUCCESS, "查询报表源数据成功");
//		
		return SysConstants.SUCCESS_MSG;
	}
	
	@RequestMapping("/saveTemplateData")
	@ResponseBody
	public String saveTemplateData(@RequestParam("templateVersionId") String templateVersionId, 
			@RequestParam("dataSetId")String dataSetId, @RequestParam("dataSetData")String dataSetData) {
		logger.debug("templateVersionId:" + templateVersionId + ", dataSetId: " + dataSetId);
		logger.debug("dataSetData: " + dataSetData);
		
		
		
		SysTemplateDataSet dataSet = sysTemplateDataSetService.getByVersionIdAndDataSetId(Long.parseLong(templateVersionId), dataSetId);
		if(dataSet!=null) {
			String dataSetFile = dataSet.getTempFile();
			
			String jsonDataFilePath = PropertyUtil.getProperty("jsonDataFilePath");
			File directory = new File(jsonDataFilePath);
			if(!directory.exists()) {
				directory.mkdirs();
			}
			
			String dataSetName = jsonDataFilePath + File.separator + dataSetFile;
			FileWriter fileWriter;
			
			try {
				
				fileWriter = new FileWriter(dataSetName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(dataSetData);
				bufferedWriter.close();
				
				return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
					
		
//		sysTemplateDataSetService.getByEditionIdAndDataSetId(templateEditionId, dataSetId)
		
		return "";
	}
	
	/**
	 * 将模板数据保存到新版本
	 * @author chenxu
	 * @date Jan 8, 2018
	 * @return
	 */
	@RequestMapping("/saveTemplateVersion")
	@ResponseBody
	public String saveTemplateVersion(HttpSession session, String name, String version, String templateVersionId) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存报表版本信息", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_REPORT);
		
		
		if(!StringUtils.isBlank(templateVersionId)) {
			SysTemplateVersion templateVersion = sysTemplateVersionService.findById(Long.parseLong(templateVersionId)); 
			
			templateVersion.setName(name);
			templateVersion.setStatus(ReportConstants.TEMPLATE_STATUS_NORMAL);
			templateVersion.setUpdateTime(new Date());
			templateVersion.setUpdateUserId(0);
			templateVersion.setVersion(version);
			sysTemplateVersionService.saveTemplateEdition(templateVersion);
			
			
			//保存该版本对应的数据集
			
			return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
			
			
		}
		
		return JSON.toJSONString(RestResult.error(4001, "系统参数错误。"));
	}
	
	@RequestMapping("/initSaveTemplateData")
	public ModelAndView initSaveTemplateData(long templateId) {
		ModelAndView mav = new ModelAndView();
//		if(templateId!=0) {
//			LogUtil.request(LogConstants.MODULE_PROJECT, "ProjectController.initEditProject", LogConstants.OPERATE_QUERY, String.valueOf(projectId), "编辑项目");
//			SysProject sysProject = sysProjectService.findbyId(projectId);
//			mav.addObject("sysProject", sysProject);
//		}else {
//			LogUtil.request(LogConstants.MODULE_PROJECT, "ProjectController.initEditProject", LogConstants.OPERATE_QUERY, LogConstants.PARAM_EMPTY, "添加项目");
//			
//			SysProject sysProject = new SysProject();
//			sysProject.setId(Long.valueOf(0));
//			mav.addObject("sysProject", sysProject);
//		}
		
		mav.setViewName("/report/report_data_save");
		
		
		
		return mav;
	}

}
