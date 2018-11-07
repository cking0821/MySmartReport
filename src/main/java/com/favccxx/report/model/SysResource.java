package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_RESOURCE")
public class SysResource {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 资源代码
	 */
	@Column(name = "RESOURCE_Code")
	private String resourceCode;

	/**资源名称*/
	@Column(name = "RESOURCE_NAME")
	private String resourceName;
	
	/**
	 * 资源访问地址
	 */
	@Column(name = "RESOURCE_LINK")
	private String resourceLink;
	
	/**
	 * 资源的样式表
	 */
	@Column(name = "RESOURCE_CSS")
	private String resourceCss;
	
	/**
	 * 父资源Id
	 */
	@Column(name = "PARENT_ID")
	private long parentId;
	
	/**
	 * 资源类型
	 * MENU 菜单
	 * OPERATION 操作
	 */
	@Column(name = "RESOURCE_TYPE")
	private String resourceType;
	
	/**
	 * 资源状态
	 * 0：正常
	 * 1：禁用
	 */
	@Column(name = "RESOURCE_STATUS")
	private int resourceStatus;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getResourceLink() {
		return resourceLink;
	}

	public void setResourceLink(String resourceLink) {
		this.resourceLink = resourceLink;
	}

	public String getResourceCss() {
		return resourceCss;
	}

	public void setResourceCss(String resourceCss) {
		this.resourceCss = resourceCss;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public int getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(int resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
	

}
