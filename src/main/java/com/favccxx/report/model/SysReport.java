package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_REPORT")
public class SysReport {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/*** 报表名称  */
	@Column(name = "REPORT_NAME")
	private String reportName;
	
	/*** 报表标签 */
	@Column(name = "REPORT_LABEL")
	private String reportLabel;
	
	@Column(name = "DESCRIPTION")
	private String reportDescription;
	
	/*** 报表数据源id  */
	@Column(name = "DATA_SOURCE_ID")
	private long dataSourceId;
	
	/*** 报表关联目录id  */
	@Column(name = "DIRECTORY_ID")
	private long directoryId;

	/*** 报表文件路径  */
	@Column(name = "REPORTFILEPATH")
	private String reportFilePath;
	
	/*** 报表文件名称  */
	@Column(name = "REPORT_FILE_NAME")
	private String reportFileName;

	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "CREATE_USER_ID")
	private long createUserId;
	
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	
	@Column(name = "UPDATE_USER_ID")
	private long updateUserId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportLabel() {
		return reportLabel;
	}

	public void setReportLabel(String reportLabel) {
		this.reportLabel = reportLabel;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	public long getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public long getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(long directoryId) {
		this.directoryId = directoryId;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}
	

	
	
	
	
}
