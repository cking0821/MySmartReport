package com.favccxx.report.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jboss.logging.Logger;

import com.favccxx.report.birt.datasource.JdbcDataSourceHandler;
import com.favccxx.report.birt.datasource.JsDataSourceHandler;
import com.favccxx.report.birt.model.BirtDataSet;
import com.favccxx.report.birt.model.BirtDataSource;

public class BirtTemplateUtil {
	
	private static Logger logger = Logger.getLogger(BirtTemplateUtil.class);
	
	@SuppressWarnings("rawtypes")
	public static Map change2JsDataSource(String templatePath) throws DocumentException, IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		File file = new File(templatePath);
		if (!file.exists()) {
			logger.debug("File not exist, and file path is: " + templatePath);
			return null;
		}

		//读取模板文件
		SAXReader reader = new SAXReader();
		Document doc = reader.read(templatePath);
		Element root = doc.getRootElement();
		
		
		//获取JDBC的所有数据源
		List<BirtDataSource> dataSourceList = JdbcDataSourceHandler.buildDataSources(root);
		
		//移除所有的JDBC数据源
		JdbcDataSourceHandler.removeAllDataSources(root);
		
		//添加JDBC 数据源
		JsDataSourceHandler.addDataSources(dataSourceList, root);
		
		//获取所有的JDBC数据集
		List<BirtDataSet> dataSetList = JdbcDataSourceHandler.buildJsDataSets(root);
		
		//移除所有的JDBC数据集
		JdbcDataSourceHandler.removeJDBCDataSets(root);
		
		//添加JS数据集(数据集包含了临时文件名称)
		List<BirtDataSet> dataSets = JsDataSourceHandler.addDataSets(dataSourceList, dataSetList, root);
		dataMap.put("dataSets", dataSets);
		
		String destFilePath = templatePath.substring(0, templatePath.lastIndexOf(File.separator)) + "temp" + UUID.randomUUID().toString() + ".rptdesign";		
		logger.info("Template Javascript template path: " + destFilePath);
		dataMap.put("filePath", destFilePath);
		
		Writer writer = new FileWriter(destFilePath);
		OutputFormat format = OutputFormat.createPrettyPrint();// 格式化
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.setEscapeText(false);
		xmlWriter.write(doc);
		xmlWriter.close();
		
		return dataMap;
		
		
	}

}
