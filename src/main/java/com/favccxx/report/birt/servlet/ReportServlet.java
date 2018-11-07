package com.favccxx.report.birt.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;

import com.favccxx.report.birt.reportengine.BirtEngine;

public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	private IReportEngine birtReportEngine = null;
	protected static Logger logger = Logger.getLogger("org.eclipse.birt");

	public ReportServlet() {
		super();
	}

	/**
	 * Destruction of the servlet.
	 */
	public void destroy() {
		super.destroy();
		BirtEngine.destroyBirtEngine();
	}

	/**
	 * The doGet method of the servlet.
	 * 
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		try {
			String reportName = req.getParameter("ReportName");
			String baseUrl = req.getContextPath() + "/webReport?ReportName=" + reportName;
			String imgBaseUrl = req.getContextPath() + "/WEB-INF/report-engine/images";
			String outFormat = "html";
			// 处理页面传过来的参数
			HashMap<String, String> paramMap = new HashMap<String, String>();
			Enumeration en = req.getParameterNames();
			while (en.hasMoreElements()) {
				String pName = (String) en.nextElement();
				String pValue = req.getParameter(pName);
				if (pName.equals("O_FORMAT")) {
					outFormat = pValue;
				} else {
					paramMap.put(pName, pValue);
				}
			}
			ServletContext sc = req.getSession().getServletContext();
			this.birtReportEngine = BirtEngine.getBirtEngine(sc);
			IReportRunnable design = birtReportEngine.openReportDesign(sc.getRealPath("/WEB-INF/reportFiles") + "/" + reportName);
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			// 以打开的报表设计文件为参数，创建一个获取参数的对象
			IGetParameterDefinitionTask paramTask = birtReportEngine.createGetParameterDefinitionTask(design);
			// 获取报表设计文件中的参数定义
			Collection paramDefns = paramTask.getParameterDefns(false);
			// 为获取的参数定义赋值
			Iterator iter = paramDefns.iterator();
			while (iter.hasNext()) {
				IParameterDefnBase pBase = (IParameterDefnBase) iter.next();
				if (pBase instanceof IScalarParameterDefn) {
					IScalarParameterDefn paramDefn = (IScalarParameterDefn) pBase;
					String pName = paramDefn.getName();// 报表参数名称
					// int pType = paramDefn.getDataType( );
					String pValue = paramMap.get(pName);// 页面传过来的值
					if (pValue == null) {
						pValue = "";
					} else {
						baseUrl += "&" + pName + "=" + pValue;
					}
					// Object pObject = stringToObject( pType, pValue );
					parameters.put(pName, pValue);
				}
			}
			if (outFormat.equals("html")) {// 页面展示
				resp.setContentType("text/html");
				HTMLRenderOption htmlOptions = new HTMLRenderOption();
				htmlOptions.setOutputFormat(RenderOption.OUTPUT_FORMAT_HTML);
				// setup image directory
				// HTMLRenderContext renderContext = new HTMLRenderContext();
				// renderContext.setBaseImageURL(req.getContextPath() +
				// "/images");
				// renderContext.setImageDirectory(sc.getRealPath("/images"));
				// logger.log(Level.FINE, "image directory " +
				// sc.getRealPath("/images"));
				// HashMap contextMap = new HashMap();
				// contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
				// renderContext);
				// htmlOptions.setImageDirectory("output/image");
				// htmlOptions.setMasterPageContent(true);
				// htmlOptions.setPageFooterFloatFlag(true);
				ByteArrayOutputStream ostream = new ByteArrayOutputStream();
				htmlOptions.setOutputStream(ostream);
				htmlOptions.setHtmlPagination(true);// 加入分页条
				htmlOptions.setEmbeddable(true);// 去除html、head、body标记
				IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
				if (parameters != null && !parameters.isEmpty()) {
					task.setParameterValues(parameters);
					task.validateParameters();
				}
				String maxRow = req.getParameter("MaxRowsPerQuery");
				if (maxRow != null && maxRow.length() > 0) {
					task.setMaxRowsPerQuery(Integer.parseInt(maxRow));// 设置每次查询最大行数
				}
				task.setRenderOption(htmlOptions);
				task.run();
				task.close();
				
				PrintWriter out = resp.getWriter();
//				ServletOutputStream out = resp.getOutputStream();
//				
//				PrintStream ps = new PrintStream(out);  
//				ps.println();
				
				//输出到html页面
				out.println("<!DOCTYPE html>");
				out.println("<html lang=\"zh\">");
				out.println("<head>");
				out.println("   <meta charset=\"utf-8\">");
				out.println("   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
				out.println("   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");				
				out.println("   <title>机灵报表</title>");
				out.println("   <link href=\"assets/adminlte/bower_components/bootstrap/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");				
				out.println("   <script src=\"assets/adminlte/bower_components/jquery/dist/jquery.js\" type=\"text/javascript\"></script>");
				
				
				out.println("<style>");
				out.println("#top a{");
				out.println("   color:white;");
				out.println("}");
				out.println("</style>");				
				out.println(" </head>");
				
				
				out.println("<body>");
				out.println("   <header id=\"top\" class=\"bs-docs-nav navbar navbar-static-top\" style=\"background-color:#3C8DBC;\">");
				out.println("      <div class=\"container\">");
				out.println("         <div class=\"nav-header\">");
				out.println("            <a href=\"#\" class=\"navbar-brand\">机灵报表</a>");
				out.println("         </div>");
				out.println("         <nav id=\"bs-navbar\" class=\"collapse navbar-collapse\">");
				out.println("            <ul class=\"nav navbar-nav\">");
				out.println("               <li class=\"active\">");
				out.println("                  <a href=\"#\">导出</a>");
				out.println("               </li>");
				out.println("               <li>");
				out.println("                  <a href=\"#\" onclick=\"printData()\">打印</a>");
				out.println("               </li>");
				out.println("            </ul>");
				out.println("         </nav>");
				out.println("      </div>");
				out.println("   </header>");
				
				out.println("<!--startprint-->");
				String ostr = ostream.toString("utf-8");
				out.println("<div style= 'width:100%;overflow:auto '>");
				out.println(ostr);
				out.println("</div>");
				out.println("<!--endprint-->");
				
				out.println("");
				out.println("");
				out.println("");
				
				out.println("<script type=\"text/javascript\">");
				//表格双击可编辑
//				out.println("   jQuery(\"table td\").dblclick(function(el) {");
//				out.println("      var editTxt = el.target.innerText;");
//				out.println("         if(editTxt!=\"\"){");
//				out.println("            var editCell = jQuery(el.target);");
//				out.println("            var input = jQuery(\"<input type='text' value='\" + editTxt +   \"'/>\");");
//				out.println("            editCell.html(input); ");
//				out.println("            input.trigger(\"focus\"); ");
//				out.println("            input.blur(function() { ");
//				out.println("               var newTxt = jQuery(this).val();");
//				out.println("               editCell.html(newTxt);");
//				out.println("            });");
//				out.println("       }");
//				out.println("   });");
				
				//JS打印表格
				out.println("   function printData(){");
				out.println("      var bdhtml = window.document.body.innerHTML;");
				out.println("      var startStr = \"<!--startprint-->\";   ");
				out.println("      var endStr = \"<!--endprint-->\";        ");
				out.println("      var printHtml = bdhtml.substr(bdhtml.indexOf(startStr) + 17);  ");
				out.println("      printHtml = printHtml.substring(0, printHtml.indexOf(endStr)); ");
				out.println("      window.document.body.innerHTML = printHtml;        ");
				out.println("      window.print();");
				out.println("      window.document.body.innerHTML = bdhtml");
				out.println("   }");
				
				
				out.println("</script>");
				out.println("</body>");
				out.println("</html>");
				
				
				
				
				
				
				
				
				
			
				out.flush();
				out.close();
				ostream.close();
			} else {// 导出文件
				RenderOption options;
				if (outFormat.equals("pdf")) {
					resp.setContentType("application/pdf");
					resp.setHeader("Content-Disposition", "attachment; filename=export.pdf");
					options = new PDFRenderOption();
					options.setOutputFormat(RenderOption.OUTPUT_FORMAT_PDF);
				} else {
					if (outFormat.equals("xls")) {
						resp.setContentType("application/msexcel");
						resp.setHeader("Content-disposition", "attachment; filename=export.xls");
					} else if (outFormat.equals("doc")) {
						resp.setContentType("application/msword");
						resp.setHeader("Content-disposition", "attachment; filename=export.doc");
					} else {
						resp.setContentType("text/html");
					}
					options = new HTMLRenderOption();
					options.setOutputFormat(RenderOption.OUTPUT_FORMAT_HTML);
				}
				options.setOutputStream(resp.getOutputStream());
				IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);// 不生成document文件
				if (parameters != null && !parameters.isEmpty()) {
					task.setParameterValues(parameters);// 设置报表参数
					task.validateParameters();
				}
				task.setRenderOption(options);
				task.run();
				task.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	/**
	 * The doPost method of the servlet.
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet.
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		BirtEngine.initBirtConfig();
	}

}
