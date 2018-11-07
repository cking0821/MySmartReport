package com.favccxx.report.controller.template;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysDataSource;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.model.SysTemplateDataSet;
import com.favccxx.report.service.SysDataSourceService;
import com.favccxx.report.service.SysTemplateDataSetService;
import com.favccxx.report.service.SysTemplateService;
import com.favccxx.report.util.LogUtil;

@Controller
public class FileUploadController {
	
	@Autowired
	SysTemplateService sysTemplateService;
	@Autowired
	SysDataSourceService sysDataSourceService;
	@Autowired
	SysTemplateDataSetService sysTemplateDataSetService;
	
	@GetMapping("/fileUploadForm")
	public String fileUploadForm(Model model) {

		return "fileUploadForm";
	}
	
	public static void main(String args[]) {
		String relativelyPath=System.getProperty("user.dir"); 
		System.out.println(relativelyPath);
	}

	@PostMapping("/fileUpload")
	public ResponseEntity<String> singleFileUpload(HttpSession session, HttpServletRequest request, @RequestParam("file") MultipartFile file, String reportId, Model model) throws IOException {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "上传报表模板", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_TEMPLATE);
		
		
		String fileName = file.getOriginalFilename();
//		String rptNewName = UUID.randomUUID().toString() + ".rptdesign";
		
		if (!fileName.isEmpty()) {
			
			if(!"".equals(reportId)) {
				SysTemplate sysDirectory = sysTemplateService.findbyId(Long.parseLong(reportId));
				sysDirectory.setReportName(fileName);
				sysDirectory.setUpdateTime(new Date());
				sysTemplateService.saveSysTemplate(sysDirectory);		
			}
			
			String reportFolderPath = request.getServletContext().getInitParameter("BIRT_VIEWER_WORKING_FOLDER"); 
			String reportFilePath = request.getSession().getServletContext().getRealPath("") + File.separator + reportFolderPath + File.separator + fileName;		
			
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(reportFilePath));
			outputStream.write(file.getBytes());
			outputStream.flush();
			outputStream.close();
						
			//校验模板是否允许编辑
			setTemplateEditable(reportId, reportFilePath);
			
			try {
				changeDataSource(reportId, reportFilePath);
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			model.addAttribute("msg", "File uploaded successfully.");
		} else {
			model.addAttribute("msg", "Please select a valid file..");
		}

		return new ResponseEntity<String>("File Uploaded Successfully.",HttpStatus.OK);
	}
	
	
	@SuppressWarnings("unchecked")
	private void setTemplateEditable(String templateId, String templatePath) {
		SysTemplate sysTemplate = sysTemplateService.findbyId(Long.parseLong(templateId));
		if(sysTemplate==null) {
			return;
		}
		
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(templatePath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Element dataSetRoot = root.element("data-sets");
		if(dataSetRoot==null) {
			return;
		}
		
		List<Element> dataSets = dataSetRoot.elements();
		String dataSetType = "";
		List<SysTemplateDataSet> dataSetList = new ArrayList<SysTemplateDataSet>();
		
		//校验报表模板的数据集是否一致
		boolean editalbe = true;
		for(Element element : dataSets) {
			if("".equals(dataSetType)) {
				dataSetType = element.getName();
			}
			//出现两种不同的数据集时，报表模板不允许编辑
			if(!dataSetType.equals(element.getName())) {
				editalbe = false;
				return;
			}			
			
			SysTemplateDataSet templateDataSet = new SysTemplateDataSet();
			templateDataSet.setDataSetId(element.attributeValue("id"));
			templateDataSet.setName(element.attributeValue("name"));
			dataSetList.add(templateDataSet);
		}
		
		
		//设置模板的数据源类型
		
		if("oda-data-set".equals(dataSets.get(0).getName())) {
			sysTemplate.setDataSourceType("JDBC");
		}else if("script-data-set".equals(dataSets.get(0).getName())) {
			sysTemplate.setDataSourceType("SCRIPT");
		}
		
		
		//设置模板为可编辑
		sysTemplate.setEditable(editalbe);
		sysTemplateService.saveSysTemplate(sysTemplate);		
		
		//记录报表模板的数据集
		sysTemplateDataSetService.saveTemplateDataSets(dataSetList);
		
	}
	
	
	/**
	 * 更改报表设计文件的数据源
	 * @param reportId
	 * @param reportFilePath
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void changeDataSource(String reportId, String reportFilePath) throws DocumentException, IOException {
		SysTemplate directory = sysTemplateService.findbyId(Long.parseLong(reportId));
		if (directory != null && directory.getDataSource() != null && !"".equals(directory.getDataSource())) {
			SysDataSource dataSource = sysDataSourceService.findbyId(Long.parseLong(directory.getDataSource()));
			if (dataSource != null) {
				File reportFile = new File(reportFilePath);				
				SAXReader reader = new SAXReader();
				Document doc = reader.read(reportFile); 
				Element root = doc.getRootElement();
				for (Iterator<Element> it = root.elementIterator("data-sources"); it.hasNext();) {
			        Element ds = it.next();
			        
			        for (Iterator<Element> iter = ds.elementIterator("oda-data-source"); iter.hasNext();) {
			            Element ods = iter.next();
			            
			            for (Iterator<Element> odsIter = ods.elementIterator(); odsIter.hasNext();) {
			            	
			            	
			            	
			                Element element = odsIter.next();
			                if("property".equals(element.getName()) || "encrypted-property".equals(element.getName())) {
			                	Attribute  attr = element.attribute(0);
			                	if("odaDriverClass".equals(attr.getValue())) {
			                		if("MySQL".equals(directory.getDbType())) {
			                			element.setText("com.mysql.jdbc.Driver");
			                		}else if("MySQL8".equals(directory.getDbType())) {
			                			element.setText("com.mysql.cj.jdbc.Driver");
			                		}else if("Oralce".equals(directory.getDbType())) {
			                			element.setText("oracle.jdbc.driver.OracleDriver");
			                		}else {
			                			System.out.println("-------Unknown datasource dbType-------" + directory.getDbType());
			                		}
			                		
			                	}
			                	if("odaURL".equals(attr.getValue())) {
			                		element.setText(dataSource.getUrlAddress());
			                	}
			                	if("odaUser".equals(attr.getValue())) {
			                		element.setText(dataSource.getUsername());
			                	}
			                	if("odaPassword".equals(attr.getValue())) {
			                		
			                		String encryptPwd = Base64.encodeBase64String(dataSource.getPassword().getBytes());
			                		element.setText(encryptPwd);
			                	}
			                	
			                	System.out.println(attr.getName() + attr.getValue());
			                }
			            }
			            
			            
			           
			            
			          
			        }
			        
			        // do something
			    }
				
				
				
				
					Writer writer = new FileWriter(reportFile);
		            OutputFormat format= OutputFormat.createPrettyPrint();//格式化
		            XMLWriter xmlWriter = new XMLWriter(writer,format);
		            xmlWriter.write(doc);
		            xmlWriter.close();
				
			}
		}
	}
	
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public void updateDataSource(String reportId, InputStream reportDesignData) throws BirtException {
		SysTemplate directory = sysTemplateService.findbyId(Long.parseLong(reportId));
		if (directory != null && directory.getDataSource() != null && !"".equals(directory.getDataSource())) {
			SysDataSource dataSource = sysDataSourceService.findbyId(Long.parseLong(directory.getDataSource()));
			if (dataSource != null) {
				IReportEngine birtEngine = null;
				EngineConfig config = null;

				config = new EngineConfig();
				
				Platform.startup(config);
				IReportEngineFactory factory = (IReportEngineFactory) Platform
						.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
				birtEngine = factory.createReportEngine(config);
				
				IReportRunnable runnable = birtEngine.openReportDesign(reportDesignData);
				
				
				IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(runnable);
				Map appContext = runAndRenderTask.getAppContext();
								
				 ReportDesignHandle design = 
						    (ReportDesignHandle) runnable.getDesignHandle().getModuleHandle(); 
						 OdaDataSourceHandle ds = (OdaDataSourceHandle)design.findDataSource("Data Source"); 
							
				if (ds != null) {
					ds.setProperty("odaURL", dataSource.getUrlAddress());
					ds.setProperty("odaDriverClass", "com.mysql.jdbc.Driver");
					ds.setProperty("odaUser", dataSource.getUsername());
					ds.setProperty("odaPassword", dataSource.getPassword());
					
					design.getDataSources().add(ds);
				}
				Platform.shutdown();
			}
		}
	}
	
	

}
