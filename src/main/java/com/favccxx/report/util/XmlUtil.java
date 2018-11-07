package com.favccxx.report.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XmlUtil {
	
	/**
	 * 获取xml document文档
	 * @param in inputstream文件流
	 * @return
	 * @throws DocumentException
	 */
	public static Document read(InputStream in) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(in); 
		return doc;
	}
	
	/**
	 * 获取xml document文档
	 * @param file 文件
	 * @return
	 * @throws DocumentException
	 */
	public static Document read(File file) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(file); 
		return doc;
	}
	
	
	
	/**
	 * 获取报表设计文件的oda数据源
	 * @param doc
	 * @return
	 * @throws DocumentException
	 */
	public static List<Node> readXmlNodes(Document doc) throws DocumentException {
		@SuppressWarnings("unchecked")
		List<Node> nodes = doc.selectNodes("//report/data-sources/oda-data-source");
		return nodes;
	}

}
