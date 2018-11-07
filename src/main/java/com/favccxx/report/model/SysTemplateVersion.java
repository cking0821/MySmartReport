package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_TEMPLATE_VERSION")
public class SysTemplateVersion {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 编辑版本名称
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * 模板Id
	 */
	@Column(name = "TEMPLATE_ID")
	private long templateId;
	
	/**
	 * 模板文件名称
	 */
	@Column(name = "TEMPLATE_FILE_NAME")
	private String templateFileName;
	
	/**
	 * 模板版本号
	 */
	@Column(name = "VERSION")
	private String version;
	
	/**
	 * 版本状态
	 * DRAFT 草稿
	 * NORMAL 正常
	 */
	@Column(name = "STATUS")
	private String status;
	
	/**
	 * 版本更新内容
	 */
	@Column(name = "DETAIL",length=4000)
	private String detail;
	
	
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
	
}
