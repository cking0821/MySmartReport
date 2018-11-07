package com.favccxx.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 模板版本对应的数据集
 * @author chenxu
 * @date Jan 5, 2018
 * @version 1.0
 */
@Entity
@Table(name = "SYS_TEMPLATE_DATA_SET")
public class SysTemplateDataSet {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * 数据集Id（来源于报表模板）
	 */
	@Column(name = "DATA_SET_ID")
	private String dataSetId;

	/**
	 * 数据集名称（来源于模板）
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * 临时数据集文件名称
	 */
	@Column(name = "TEMP_File")
	private String tempFile;

	/**
	 * 模板版本Id
	 */
	@Column(name = "TEMPLATE_VERSION_ID")
	private long templateVersionId;

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

	public long getTemplateVersionId() {
		return templateVersionId;
	}

	public void setTemplateVersionId(long templateVersionId) {
		this.templateVersionId = templateVersionId;
	}

	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getTempFile() {
		return tempFile;
	}

	public void setTempFile(String tempFile) {
		this.tempFile = tempFile;
	}

}
