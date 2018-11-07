package com.favccxx.report.birt.model;

/**
 * 缓存数据集的元数据定义
 * @author chenxu
 * @date Dec 27, 2017
 * @version 1.0
 */
public class CachedMetaData {
	/**
	 * 展示位置
	 */
	private String position;
	
	/**
	 * 字段名称
	 */
	private String name;
	
	/**
	 * 数据类型
	 */
	private String dataType;
	
	

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	
	
}
