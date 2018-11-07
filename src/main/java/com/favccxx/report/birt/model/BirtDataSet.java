package com.favccxx.report.birt.model;

import java.util.List;

/**
 * JS数据集
 * @author chenxu
 * @date Dec 27, 2017
 * @version 1.0
 */
public class BirtDataSet {
	
	/**
	 * 数据集Id
	 */
	private String id;
	
	/**
	 * 数据集名称
	 */
	private String datasetName;
	
	/**
	 * 数据源名称
	 */
	private String datasourceName;
	
	/**
	 * 需要查询显示的列
	 */
	private String columnNames;
	
	/**
	 * SQL查询语句
	 * JDBC数据集
	 */
	private String queryText;
	
	/**
	 * JS数据集的Open方法
	 */
	private String openMethod;
	
	/**
	 * JS数据集的Fetch方法
	 */
	private String fetchMethod;
	
	/**
	 * 临时数据集的文件名称
	 */
	private String dataSetFile;
	
	/**
	 * 数据集定义
	 */
	private List<ResultSetHints> resultSetHints;
	
	/**
	 * 显示列定义
	 */
	private List<ColumnHints> columnHints;
	
	/**
	 * 缓存的数据集元数据定义
	 */
	private List<CachedMetaData> cachedMetaDatas;
	
	
	private List<Parameter> parameters;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public List<ResultSetHints> getResultSetHints() {
		return resultSetHints;
	}

	public void setResultSetHints(List<ResultSetHints> resultSetHints) {
		this.resultSetHints = resultSetHints;
	}

	public List<ColumnHints> getColumnHints() {
		return columnHints;
	}

	public void setColumnHints(List<ColumnHints> columnHints) {
		this.columnHints = columnHints;
	}

	public List<CachedMetaData> getCachedMetaDatas() {
		return cachedMetaDatas;
	}

	public void setCachedMetaDatas(List<CachedMetaData> cachedMetaDatas) {
		this.cachedMetaDatas = cachedMetaDatas;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public String getDataSetFile() {
		return dataSetFile;
	}

	public void setDataSetFile(String dataSetFile) {
		this.dataSetFile = dataSetFile;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getOpenMethod() {
		return openMethod;
	}

	public void setOpenMethod(String openMethod) {
		this.openMethod = openMethod;
	}

	public String getFetchMethod() {
		return fetchMethod;
	}

	public void setFetchMethod(String fetchMethod) {
		this.fetchMethod = fetchMethod;
	}
	
	
	
	
	

}
