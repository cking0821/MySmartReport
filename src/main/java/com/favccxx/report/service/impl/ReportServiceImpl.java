package com.favccxx.report.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.favccxx.report.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService, ApplicationContextAware {
	
	
	@Autowired
	private ServletContext servletContext;
	
	private IReportEngine birtEngine;
	private ApplicationContext context;
	
	private Map<String, IReportRunnable> reports = new HashMap<String, IReportRunnable>();
	private Map<String, IReportRunnable> thumbnails = new HashMap<String, IReportRunnable>();

	private static final String IMAGE_FOLDER = "/images";
	
	//@PostConstruct
	@SuppressWarnings("unchecked")
	protected void initialize() throws BirtException {
		EngineConfig config = new EngineConfig();
		config.getAppContext().put("spring", this.context);
		Platform.startup(config);
		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		birtEngine = factory.createReportEngine(config);
		loadReports();
	}
	
	public void loadReports() throws EngineException {
		File folder = new File(servletContext.getRealPath("/") + "WEB-INF/reports");
		for (String file : folder.list()) {
			if (!file.endsWith(".rptdesign")) {
				continue;
			}
			if (file.contains("thumb")) {
				thumbnails.put(file.replace("-thumb.rptdesign", ""),
						birtEngine.openReportDesign(folder.getAbsolutePath() + File.separator + file));
			} else {
				reports.put(file.replace(".rptdesign", ""),
						birtEngine.openReportDesign(folder.getAbsolutePath() + File.separator + file));
			}
		}
	}

	
	public void generateReport(String reportName, HttpServletRequest request, HttpServletResponse response) {
		generateReport(reports.get(reportName), request, response);
	}

	
	public void generateReportThumb(String reportName, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
	@SuppressWarnings("unchecked")
	public void generateReport(IReportRunnable report, HttpServletRequest request, HttpServletResponse response) {
		IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
		response.setContentType(birtEngine.getMIMEType("html"));
		IRenderOption options = new RenderOption();
		HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
		htmlOptions.setOutputFormat("html");
		htmlOptions.setImageHandler(new HTMLServerImageHandler());
		htmlOptions.setBaseImageURL(request.getContextPath() + IMAGE_FOLDER);
		htmlOptions.setImageDirectory(servletContext.getRealPath(IMAGE_FOLDER));
		htmlOptions.setHtmlPagination(true);
		
		
		runAndRenderTask.setRenderOption(htmlOptions);
		runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, request);
		IGetParameterDefinitionTask task = birtEngine.createGetParameterDefinitionTask(report);
		Map<String, Object> params = new HashMap<String, Object>();
		for (Object h : task.getParameterDefns(false)) {
			IParameterDefn def = (IParameterDefn) h;
			if (def.getDataType() == IParameterDefn.TYPE_INTEGER) {
				params.put(def.getName(), Integer.parseInt(request.getParameter(def.getName())));
			} else {
				params.put(def.getName(), request.getParameter(def.getName()));
			}
		}
		try {
			htmlOptions.setOutputStream(response.getOutputStream());
			runAndRenderTask.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			runAndRenderTask.close();
		}
	}

}
