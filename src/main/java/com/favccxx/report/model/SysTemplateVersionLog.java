package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_TEMPLATE_VERSION_LOG")
public class SysTemplateVersionLog {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * 模板Id
	 */
	@Column(name = "TEMPLATE_ID")
	private long templateId;
	
	/**
	 * 模板版本Id
	 */
	@Column(name = "TEMPLATE_VERSION_ID")
	private long templateVersionId;
	
	/**
	 * 模板版本详细信息
	 */
	@Column(name = "VERSION_LOG")
	private String versionLog;

	@Column(name = "UPDATE_USER_ID")
	private long updateUserId;
	
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public long getTemplateVersionId() {
		return templateVersionId;
	}

	public void setTemplateVersionId(long templateVersionId) {
		this.templateVersionId = templateVersionId;
	}

	public String getVersionLog() {
		return versionLog;
	}

	public void setVersionLog(String versionLog) {
		this.versionLog = versionLog;
	}

	public long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
	
	
	
	
}
