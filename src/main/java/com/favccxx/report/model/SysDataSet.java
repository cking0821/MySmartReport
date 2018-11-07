package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_DATA_SET")
public class SysDataSet {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	
	@Column(name = "PARENT_ID")
	private long parentId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "ICON_SKIN")
	private String iconSkin;
	
	/**
	 * 数据集抽取出来的SQL
	 */
	@Column(name = "DATA_SQL")
	private String dataSql;
	
	/**
	 * 数据集的列
	 */
	@Column(name = "DATA_COLUMNS")
	private String dataColumns;
	
	@Column(name = "PROJECT_ID")
	private long projectId;
	
	@Column(name = "DATA_SOURCE_ID")
	private long datasourceId;
	
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

	public String getDataSql() {
		return dataSql;
	}

	public void setDataSql(String dataSql) {
		this.dataSql = dataSql;
	}

	public String getDataColumns() {
		return dataColumns;
	}

	public void setDataColumns(String dataColumns) {
		this.dataColumns = dataColumns;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(long datasourceId) {
		this.datasourceId = datasourceId;
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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	
	

}
