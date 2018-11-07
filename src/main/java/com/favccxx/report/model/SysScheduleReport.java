package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_SCHEDULE_REPORT")
public class SysScheduleReport {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 项目Id
	 */
	@Column(name = "PROJECT_ID")
	private long projectId;
	
	/**
	 * 报表模板Id
	 */
	@Column(name = "TEMPLATE_ID")
	private long templateId;
	
	/**模板名称*/
	@Column(name = "TEMPLATE_NAME")
	private String templateName;
	
	/**定时任务Id*/
	@Column(name = "SCHEDULE_ID")
	private long scheduleId;
	
	/**定时任务名称*/
	@Column(name = "SCHEDULE_NAME")
	private String scheduleName;
	
	/**报表生成时间*/
	@Column(name = "REPORT_DATE")
	private Date reportDate;
	
	/**报表格式*/
	@Column(name = "REPORT_TYPE")
	private String reportType;
	
	/**报表名称*/
	@Column(name = "REPORT_FILE_NAME")
	private String reportFileName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	
	
	

}
