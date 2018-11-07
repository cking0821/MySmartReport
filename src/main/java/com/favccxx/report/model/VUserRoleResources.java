package com.favccxx.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_USER_ROLE_RESOURCES")
public class VUserRoleResources {
	
	@Column(name="USER_ID")
	private long userId;
	
	@Id
	@Column(name="RESOURCE_ID")
	private long resourceId;
	
	@Column(name="RESOURCE_CODE")
	private String resourceCode;
	
	@Column(name="RESOURCE_NAME")
	private String resourceName;
	
	@Column(name="RESOURCE_CSS")
	private String resourceCss;
	
	@Column(name="RESOURCE_LINK")
	private String resourceLink;
	
	@Column(name="RESOURCE_STATUS")
	private String resourceStatus;
	
	@Column(name="RESOURCE_TYPE")
	private String resourceType;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceCss() {
		return resourceCss;
	}

	public void setResourceCss(String resourceCss) {
		this.resourceCss = resourceCss;
	}

	public String getResourceLink() {
		return resourceLink;
	}

	public void setResourceLink(String resourceLink) {
		this.resourceLink = resourceLink;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	

}
