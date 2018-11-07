package com.favccxx.report.job;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.favccxx.report.birt.export.BirtExportService;
import com.favccxx.report.model.SysScheduleReport;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.model.SysTemplateSchedule;
import com.favccxx.report.service.SysScheduleReportService;
import com.favccxx.report.service.SysTemplateScheduleService;
import com.favccxx.report.service.SysTemplateService;
import com.favccxx.report.util.PropertyUtil;

public class ReportGenerateJob {

	private static Logger logger = Logger.getLogger(ReportGenerateJob.class);

	@Autowired
	SysTemplateScheduleService sysTemplateScheduleService;
	@Autowired
	SysTemplateService sysTemplateService;
	@Autowired
	SysScheduleReportService sysScheduleReportService;

	/**
	 * 定时生成报表
	 * 
	 * @author chenxu
	 * @date Jan 12, 2018
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void generateReport() {
		
		logger.debug("----generateReport---");
		
		System.out.print("------");
		
		List<SysTemplateSchedule> list = sysTemplateScheduleService.listExecuteSchedules();
		if (list == null || list.size() == 0) {
			return;
		}

		for (SysTemplateSchedule templateSchedule : list) {
			long templateId = templateSchedule.getTemplateId();
			String exportType = templateSchedule.getExportType();

			SysTemplate template = sysTemplateService.findbyId(templateId);
			if (template != null) {
				String currentPath =  this.getClass().getClassLoader().getResource("").toString();
				String realPath = currentPath.substring(6, currentPath.length()-8) + File.separator + "reportFiles" + File.separator;
				
				
				String reportTemplate = realPath+ template.getReportName();
				
				
				
				String parameters = template.getParameter();
				HashMap paramMap = new HashMap<>();
				if (parameters != null) {
					String[] params = parameters.split("&");
					for (int i = 0, l = params.length; i < l; i++) {
						String[] keyValue = params[i].split("=");
						if (keyValue.length > 1 && keyValue[1] != null) {
							String value = keyValue[1];
							if(isInt(value)) {
								paramMap.put(keyValue[0], Integer.parseInt(value));
							}else {
								paramMap.put(keyValue[0], value);
							}							
						}
					}

				}

				ByteArrayOutputStream bos = null;
				try {
					bos = (ByteArrayOutputStream) BirtExportService.call(exportType, reportTemplate, paramMap);
					
					String reportName = UUID.randomUUID().toString() + "." + exportType;
					
					
					String fileName = PropertyUtil.getProperty("scheduleReportPath") + reportName;
					
					java.io.File file = new java.io.File(fileName);
					if(!file.exists())
						file.createNewFile();
					OutputStream fis = new FileOutputStream(fileName);
					bos.writeTo(fis);
					
					//更新报表定时任务
					sysTemplateScheduleService.updateScheduleTime(templateSchedule);
					
					//记录报表生成记录
					SysScheduleReport scheduleReport = new SysScheduleReport();
					scheduleReport.setProjectId(template.getProjectId());
					scheduleReport.setTemplateId(templateId);
					scheduleReport.setTemplateName(template.getName());
					
					scheduleReport.setScheduleId(templateSchedule.getId());
					scheduleReport.setScheduleName(templateSchedule.getScheduleTitle());
					scheduleReport.setReportDate(new Date());
					scheduleReport.setReportType(templateSchedule.getExportType());
					scheduleReport.setReportFileName(reportName);
					
					sysScheduleReportService.generateReport(scheduleReport);

				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					if(bos!=null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}

		}

		// sysTemplateScheduleService.listByTemplateId(templateId);
	}
	
	private static boolean isInt(String obj) {
		boolean flag = false;
		try {
			Integer.parseInt(obj);
		}catch(NumberFormatException e) {
			flag = false;
			return flag;
		}
		flag = true;
		return flag;
	}

}
