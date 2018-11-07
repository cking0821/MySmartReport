package com.favccxx.report.birt.model;

/**
 * 数据集显示列定义
 * @author chenxu
 * @date Dec 27, 2017
 * @version 1.0
 */
public class ColumnHints {
	
	/**
	 * 列名称
	 */
	private String columnName;
	
	/**
	 * 分析方法
	 * （oda数据集）
	 */
	private String analysis;
	
	/**
	 * 别名
	 */
	private String alias;
	
	/**
	 * 显示名称
	 */
	private String displayName;
	
	/**
	 * 头显示标题
	 * （oda数据集）
	 */
	private String heading;
	
	/**
	 * 显示名称的key
	 */
	private String displayNameKey;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayNameKey() {
		return displayNameKey;
	}

	public void setDisplayNameKey(String displayNameKey) {
		this.displayNameKey = displayNameKey;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	
	

}
