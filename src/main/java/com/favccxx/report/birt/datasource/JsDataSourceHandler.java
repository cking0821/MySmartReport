package com.favccxx.report.birt.datasource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.favccxx.report.birt.model.BirtDataSet;
import com.favccxx.report.birt.model.BirtDataSource;
import com.favccxx.report.birt.model.CachedMetaData;
import com.favccxx.report.birt.model.ColumnHints;
import com.favccxx.report.birt.model.ResultSetHints;
import com.favccxx.report.util.JdbcUtil;
import com.favccxx.report.util.PropertyUtil;

public class JsDataSourceHandler {
	
	/**
	 * 添加JS数据源
	 * @author chenxu
	 * @date Dec 29, 2017
	 * @param list 数据源对象列表
	 * @param root	设计文档根元素
	 */
	public static void addDataSources(List<BirtDataSource> list, Element root) {
		if(root!=null) {
			Element dataSources = root.element("data-sources");
			
			for(BirtDataSource dataSource : list) {
				dataSources.addElement("script-data-source")
					.addAttribute("id", dataSource.getId())
					.addAttribute("name", dataSource.getName());
			}
		}
		
	}
	
	
	/**
	 * 添加JS数据集
	 * @author chenxu
	 * @date Dec 29, 2017
	 * @param list 数据集列表
	 * @param root 设计文档根节点
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSet> addDataSets(List<BirtDataSource> dataSourceList, List<BirtDataSet> dataSetList, Element root) {
		if(root==null || dataSetList==null || dataSetList.size()==0) {
			return null;
		}
		
		List<Element> list = root.element("data-sets").elements();
		
		for(int i=0, l=dataSetList.size(); i<l; i++) {
			BirtDataSet dataSet = dataSetList.get(i);
			//创建JS数据集对象
			Element ele = DocumentHelper.createElement("script-data-set");		
			ele.addAttribute("id", dataSet.getId());
			ele.addAttribute("name", dataSet.getDatasetName());
			list.add(ele);
			
			//添加数据集的属性-resultSetHints
			Element resultSetHintsEle = ele.addElement("list-property"); 
			resultSetHintsEle.addAttribute("name", "resultSetHints");						
			for(ResultSetHints rsh : dataSet.getResultSetHints()) {
				Element structure = DocumentHelper.createElement("structure"); 
				
				Element position =  structure.addElement("property").addAttribute("name", "position");
				if(position!=null) {
					position.setText(rsh.getPosition());
				}
				
				structure.addElement("property").addAttribute("name", "name").setText(rsh.getName());
				structure.addElement("property").addAttribute("name", "dataType").setText(rsh.getDataType());
				
				resultSetHintsEle.add((Element)structure.clone());
			}
			
			
			//构建columnHints属性节点
			Element columnHints = ele.addElement("list-property");	
			columnHints.addAttribute("name", "columnHints");
			for(ColumnHints ch : dataSet.getColumnHints()) {				
				Element structure = DocumentHelper.createElement("structure");				
				structure.addElement("property").addAttribute("name", "columnName").setText(ch.getColumnName());
				
				if(ch.getAlias()!=null && !"".equals(ch.getAlias())) {
					structure.addElement("property").addAttribute("name", "alias").setText(ch.getAlias());
				}
				
				if(ch.getDisplayName()!=null && !"".equals(ch.getDisplayName())) {
					Element displayEle = structure.addElement("text-property");
					displayEle.addAttribute("name", "displayName").setText(ch.getDisplayName());
					if(ch.getDisplayNameKey()!=null && !"".equals(ch.getDisplayNameKey())) {
						displayEle.addAttribute("key", ch.getDisplayNameKey());
					}
				}
				
				columnHints.add((Element)structure.clone());
			}
			
			
			//构建cachedMetaData属性节点
			Element cachedMetaData = ele.addElement("structure");	
			cachedMetaData.addAttribute("name", "cachedMetaData");
			Element cacheEle = cachedMetaData.addElement("list-property").addAttribute("name", "resultSet");
			for(CachedMetaData cm : dataSet.getCachedMetaDatas()) {
				Element structure = DocumentHelper.createElement("structure");
				
				structure.addElement("property").addAttribute("name", "position").setText(cm.getPosition());
				structure.addElement("property").addAttribute("name", "name").setText(cm.getName());
				structure.addElement("property").addAttribute("name", "dataType").setText(cm.getDataType());
				
				cacheEle.add((Element)structure.clone());
			}
			
			
			//添加数据源名称
			ele.addElement("property").addAttribute("name", "dataSource").setText(dataSet.getDatasourceName());		
			
			//查找JS数据集对应的数据源
			for(BirtDataSource dataSource : dataSourceList) {
				if(dataSet.getDatasourceName().equals(dataSource.getName())) {
					//获取JDBC查询的字段名
					Connection connection;
					String columnNames = "";
					String jdbcDataSets = "";
					try {
						connection = JdbcUtil.getConnection(dataSource.getDriverClass(), dataSource.getUrl(), dataSource.getUser(), dataSource.getPassword());
						columnNames = JdbcUtil.getTbColumns(connection, dataSet.getQueryText(),null);				
						
						jdbcDataSets = JdbcUtil.getResultSets(connection, dataSet.getQueryText(),null);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}	
					
					//保存原有的数据集
					String fileName = saveDataSetData(jdbcDataSets, dataSet);
					dataSet.setDataSetFile(fileName);
					
					//添加JS数据集的OPEN和FETCH方法
					String openTxt = getOpenContext(fileName);
					String fetchTxt = getFetchContext(columnNames);
					ele.addElement("method").addAttribute("name", "open").setText(openTxt);		
					ele.addElement("method").addAttribute("name", "fetch").setText(fetchTxt);
					
				}
			}
							
		}
		
		return dataSetList;
		
	}
	
	
	/**
	 * 将JSON数据保存到磁盘
	 * @author chenxu
	 * @date Dec 29, 2017
	 * @param jsonData
	 * @param dataSet
	 * @return
	 */
	private static String saveDataSetData(String jsonData, BirtDataSet dataSet) {
		String tempJsonFilePath = PropertyUtil.getProperty("tempJsonFilePath");
		String fileName = UUID.randomUUID().toString() + ".json";
		File directory = new File(tempJsonFilePath);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		File file = new File(directory, fileName);
		FileWriter fileWriter;
		
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(tempJsonFilePath + File.separator + fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(jsonData);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	
	
	
	private static String getOpenContext(String fileName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<![CDATA[")
			.append("_index = 0;")
			.append("result = Packages.com.favccxx.report.util.RestStaticDataUtil.restDataFromPath('")
			.append(fileName)
			.append("');")
			.append("jsonResult = JSON.parse(result);")
			.append("]]>");
		return sb.toString();		
	} 
	
	private static String getFetchContext(String columnNames) {
		StringBuffer sb = new StringBuffer();
		sb.append("<![CDATA[")
			.append("if( _index < jsonResult.length){");
		
		String[] columns = columnNames.split(",");
		for (int i = 0; i < columns.length; i++) {
			sb.append("row['").append(columns[i]).append("'] = jsonResult[_index].").append(columns[i]).append(";");
		}
		
		
		sb.append(" _index++;")
			.append("return true;")
			.append("}")
			.append("return false;")
			.append("]]>");
		return sb.toString();
	}
	
	
	
	
	

	/**
	 * 添加JS数据源
	 * @author chenxu
	 * @date Dec 28, 2017
	 * @param root
	 */
	@SuppressWarnings("unchecked")
	protected static void addDataSource(Element root) {
		// 移除ODA数据源
		Element dssEl = root.element("data-sources");
		if (dssEl != null) {
			Element odaEl = dssEl.element("oda-data-source");
			if (odaEl != null) {
				dssEl.remove(odaEl);
			}
		}

		// 添加Script数据源
		List<Element> list = root.element("data-sources").elements();
		Element dsEle = DocumentHelper.createElement("script-data-source");
		dsEle.addAttribute("name", "Data Source");
		dsEle.addAttribute("id", "4");
		list.add(dsEle);
	}
	
	
	/**
	 * 添加JS数据集
	 * @author chenxu
	 * @date Dec 27, 2017
	 * @param root 根节点
	 * @param jsDataSet JS数据集对象
	 */
	@SuppressWarnings("unchecked")
	protected static void addDataSet(Element root, BirtDataSet jsDataSet, String columnNames) {
		List<Element> list = root.element("data-sets").elements();
				
		Element ele = DocumentHelper.createElement("script-data-set");		
		ele.addAttribute("id", jsDataSet.getId());
		ele.addAttribute("name", jsDataSet.getDatasetName());
		list.add(ele);
		
		//构建resultSetHints属性节点
		Element resultSetHintsEle = ele.addElement("list-property"); 
		
		System.out.println(resultSetHintsEle.toString());	
		resultSetHintsEle.addAttribute("name", "resultSetHints");
		
//		List<Element> ss = resultSetHintsEle.elements();
		
		System.out.println(resultSetHintsEle.toString());
		
		
		for(ResultSetHints rsh : jsDataSet.getResultSetHints()) {
			Element structure = DocumentHelper.createElement("structure"); 
			
			Element position =  structure.addElement("property").addAttribute("name", "position");
			if(position!=null) {
				position.setText(rsh.getPosition());
			}
			
			
			structure.addElement("property").addAttribute("name", "name").setText(rsh.getName());
			structure.addElement("property").addAttribute("name", "dataType").setText(rsh.getDataType());
			
			resultSetHintsEle.add((Element)structure.clone());
		}
		
		
		//构建columnHints属性节点
		Element columnHints = ele.addElement("list-property");	
		columnHints.addAttribute("name", "columnHints");
		for(ColumnHints ch : jsDataSet.getColumnHints()) {
			
			Element structure = DocumentHelper.createElement("structure");
			
			structure.addElement("property").addAttribute("name", "columnName").setText(ch.getColumnName());
			
			if(ch.getAlias()!=null && !"".equals(ch.getAlias())) {
				structure.addElement("property").addAttribute("name", "alias").setText(ch.getAlias());
			}
			
			if(ch.getDisplayName()!=null && !"".equals(ch.getDisplayName())) {
				Element displayEle = structure.addElement("text-property");
				displayEle.addAttribute("name", "displayName").setText(ch.getDisplayName());
				if(ch.getDisplayNameKey()!=null && !"".equals(ch.getDisplayNameKey())) {
					displayEle.addAttribute("key", ch.getDisplayNameKey());
				}
			}
			
			columnHints.add((Element)structure.clone());
		}
		
		
		//构建cachedMetaData属性节点
		Element cachedMetaData = ele.addElement("structure");	
		cachedMetaData.addAttribute("name", "cachedMetaData");
		Element cacheEle = cachedMetaData.addElement("list-property").addAttribute("name", "resultSet");
		for(CachedMetaData cm : jsDataSet.getCachedMetaDatas()) {
			Element structure = DocumentHelper.createElement("structure");
			
			structure.addElement("property").addAttribute("name", "position").setText(cm.getPosition());
			structure.addElement("property").addAttribute("name", "name").setText(cm.getName());
			structure.addElement("property").addAttribute("name", "dataType").setText(cm.getDataType());
			
			cacheEle.add((Element)structure.clone());
		}
		
		//添加数据源名称
		ele.addElement("property").addAttribute("name", "dataSource").setText(jsDataSet.getDatasourceName());		
		//添加JS数据集的open方法
		
		String openTxt = getOpenMethod();
		String fetchTxt = getFetchMethod(columnNames);
		
		ele.addElement("method").addAttribute("name", "open").setText(openTxt);		
		ele.addElement("method").addAttribute("name", "fetch").setText(fetchTxt);
	}
	
	
	private static String getOpenMethod() {
		StringBuffer sb = new StringBuffer();
		sb.append("<![CDATA[")
			.append("_index = 0;")
			.append("result = Packages.com.favccxx.report.util.RestStaticDataUtil.restDataFromPath('abc.json');")
			.append("jsonResult = JSON.parse(result);")
			.append("]]>");
		return sb.toString();		
	} 
	
	private static String getFetchMethod(String columnNames) {
		StringBuffer sb = new StringBuffer();
		sb.append("<![CDATA[")
			.append("if( _index < jsonResult.length){");
		
		String[] columns = columnNames.split(",");
		for (int i = 0; i < columns.length; i++) {
			sb.append("row['").append(columns[i]).append("'] = jsonResult[_index].").append(columns[i]).append(";");
		}
		
		
		sb.append(" _index++;")
			.append("return true;")
			.append("}")
			.append("return false;")
			.append("]]>");
		return sb.toString();
	}
	
}
