package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SYS_PROJECT")
public class SysProject {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@Size(max = 20, min = 3, message = "{project.name.invalid}")
	@Column(name = "PROJECT_CODE")
	private String projectCode;
	
	@Column(name = "NAME")
	private String projectName;
	
	@Column(name = "TYPE")
	private String projectType;
	
	@Column(name = "PROJECT_STATUS")
	private String projectStatus;
	
	@Column(name = "PROJECT_USER")
	private String projectUser;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "CREATE_USER_ID")
	private long createUserId;
	
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	
	@Column(name = "UPDATE_USER_ID")
	private long updateUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public String getProjectUser() {
		return projectUser;
	}

	public void setProjectUser(String projectUser) {
		this.projectUser = projectUser;
	}
	@Override
	public String toString() {
		return "id=" + id + "&projectName=" + projectName + "&projectType=" + projectType + "&projectStatus=" + projectStatus + "&description=" + description;
				
	}

	

	
	

}
