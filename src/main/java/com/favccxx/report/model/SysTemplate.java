package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_TEMPLATE")
public class SysTemplate {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private String value;
	
	/**
	 * 目录或报表
	 */
	@Column(name = "TYPE")
	private String type;
	
	/**
	 * 数据源类型
	 * JDBC/FOLDER
	 */
	@Column(name = "DATA_SOURCE_TYPE")
	private String dataSourceType;
	
	/**
	 * 数据库类型
	 * MYSQL/MYSQL8/ORACLE/
	 */
	@Column(name = "DB_TYPE")
	private String dbType;
	
	/**
	 * 模板参数
	 */
	@Column(name="PARAMETER")
	private String parameter;
	
	@Column(name = "PARENT_ID")
	private long parentId;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PROJECT_ID")
	private long projectId;
	
	@Column(name = "OPEN")
	private String open;
	
	@Column(name = "ICON_SKIN")
	private String iconSkin;
	
	@Column(name = "DIRECTORY_LABEL")
	private String directoryLabel;
	
	@Column(name = "DATA_SOURCE")
	private String dataSource;
	
	@Column(name = "REPORT_NAME")
	private String reportName;
	
	/**
	 * 模板数据集是否允许编辑
	 */
	@Column(name = "EDITABLE")
	private boolean editable;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getDirectoryLabel() {
		return directoryLabel;
	}

	public void setDirectoryLabel(String directoryLabel) {
		this.directoryLabel = directoryLabel;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	

}
