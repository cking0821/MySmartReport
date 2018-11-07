package com.favccxx.report.birt.datasource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jboss.logging.Logger;

import com.favccxx.report.birt.model.BirtDataSet;
import com.favccxx.report.birt.model.BirtDataSource;
import com.favccxx.report.util.PropertyUtil;

public class DataSourceHelper {
	
	private static Logger logger = Logger.getLogger(DataSourceHelper.class);
	
	public static void changeDataSource(String filePath) throws DocumentException, ClassNotFoundException, SQLException, IOException {
		
		File file = new File(filePath);
		if (!file.exists()) {
			logger.debug("File not exist, and file path is: " + filePath);
			return;
		}

		SAXReader reader = new SAXReader();
		Document doc = reader.read(filePath);
		Element root = doc.getRootElement();
		
		
		//获取JDBC的所有数据源
		List<BirtDataSource> dataSourceList = JdbcDataSourceHandler.buildDataSources(root);
		
		//移除所有的JDBC数据源
		JdbcDataSourceHandler.removeAllDataSources(root);
		
		//添加JDBC 数据源
		JsDataSourceHandler.addDataSources(dataSourceList, root);
		
		//获取所有的JDBC数据集
		List<BirtDataSet> dataSetList = JdbcDataSourceHandler.buildJsDataSets(root);
		
//		//移除所有的JDBC数据集
//		JdbcDataSourceHandler.re
		
		//添加JS数据集
		JsDataSourceHandler.addDataSets(dataSourceList, dataSetList, root);
		
		
		
		
//		//获取JDBC连接
//		Connection connection = JdbcDataSourceHandler.getJdbcConnection(root);
//		
//		//移除JDBC数据源
//		JdbcDataSourceHandler.removeDataSource(root);
//		
//		//添加JS数据源
//		JsDataSourceHandler.addDataSource(root);
//		
//		JdbcDataSourceHandler.buildJsDataSets(root);
//		
//		//获取JS数据集对象并移除JDBC数据集
//		JsDataSet jsDataSet = JdbcDataSourceHandler.buildJsDataSet(root);
//		
//		//获取原有的JDBC数据集和列定义
//		String columns = JdbcUtil.getTbColumns(connection, jsDataSet.getQueryText());
//		String jdbcDataSets = JdbcUtil.getResultSets(connection, jsDataSet.getQueryText());
//		
//		logger.debug(jdbcDataSets);
//		
//		writeJsonFile(jdbcDataSets, "abc.json");
//		
//		//添加JS数据集
//		JsDataSourceHandler.addDataSet(root, jsDataSet, columns);
		
		Writer writer = new FileWriter(filePath);
		OutputFormat format = OutputFormat.createPrettyPrint();// 格式化
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.setEscapeText(false);
		xmlWriter.write(doc);
		xmlWriter.close();
		
	}
	
	
	
	public static void writeJsonFile(String data, String fileName) {
		String tempJsonFilePath = PropertyUtil.getProperty("tempJsonFilePath");
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
			bufferedWriter.write(data);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try {
			DataSourceHelper.changeDataSource("D://birttest//oda.rptdesign");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
