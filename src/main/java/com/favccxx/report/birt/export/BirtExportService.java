package com.favccxx.report.birt.export;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

import com.favccxx.report.util.PropertyUtil;

public class BirtExportService {

	/** */
	/** 初始化的状态 */
	protected static boolean initStatus = false;

	private static IReportEngine engine = null;

	private static EngineConfig config = null;

	private static IReportRunnable design = null;

	private static PDFRenderOption ro = null;

	/** */
	/** 初始化资源 */
	@SuppressWarnings("unchecked")
	public static void initilize(String reportType) {
		if (initStatus == true)
			return;

		try {

			config = new EngineConfig();
			
			Platform.startup(config);
			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			engine = factory.createReportEngine(config);

			ro = new PDFRenderOption();
			//BIRT运行时环境，通常是http://localhost:8080/ReportServer
			String baseUrl = PropertyUtil.getProperty("birtServer");
			ro.setBaseURL(baseUrl); 
			//生成文档格式PDF/DOCX/XLSX
			config.getEmitterConfigs().put(reportType, ro); 

			initStatus = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			initStatus = false;
		}
	}

	/** */
	/** 释放资源 */
	@SuppressWarnings("deprecation")
	public static void release() {
		engine.shutdown();
		Platform.shutdown();

		initStatus = false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static OutputStream run(String reportType, String filename, HashMap parameters) throws EngineException {
		design = engine.openReportDesign(filename);

		// Create task to run and render the report,
		IRunAndRenderTask task = engine.createRunAndRenderTask(design);
		HashMap contextMap = new HashMap();
		contextMap.put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, ro);
		task.setAppContext(contextMap);
		task.setParameterValues(parameters);
		task.validateParameters();

		OutputStream os = new ByteArrayOutputStream();
		ro.setOutputStream(os);
		ro.setOutputFormat(reportType);
		task.setRenderOption(ro);

		task.run();
		task.close();

		return os;
	}

	/** */
	/**
	 * 生成PDF格式报表,以OutputStream格式返回
	 * 
	 * @param filename
	 *            报表设计文件名全路径
	 * @param parameters
	 *            报表参数
	 * @return ByteArrayOutputStream
	 * @throws EngineException
	 */
	@SuppressWarnings("rawtypes")
	public static OutputStream call(String reportType, String filename, HashMap parameters) throws EngineException {
		initilize(reportType);
		OutputStream os = run(reportType, filename, parameters);
		release();

		return os;
	}

}
