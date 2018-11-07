package com.favccxx.report.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jboss.logging.Logger;

public class RestStaticDataUtil {

	private static Logger logger = Logger.getLogger(RestStaticDataUtil.class);

	/**
	 * 将JSON文件读取为JAVA格式的JSON数据
	 * @author chenxu
	 * @date Jan 11, 2018
	 * @param jsonFile
	 * @return
	 */
	public static String restJavaJsonFromFile(String jsonFile) {
		StringBuilder stringBuilder = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(jsonFile));
			String data = null;
			while ((data = reader.readLine()) != null) {
				stringBuilder.append(data);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.debug("jsonData:" + stringBuilder.toString());
		String fileData = stringBuilder.toString();

		return StringEscapeUtils.unescapeJson(fileData);
	}

	@SuppressWarnings("unused")
	public static String restDataFromPath(String path) {
		StringBuilder stringBuilder = new StringBuilder();

		try {
			String jsonDataFilePath = PropertyUtil.getProperty("jsonDataFilePath");
			BufferedReader reader = new BufferedReader(
					new FileReader(path));
			String data = null;
			while ((data = reader.readLine()) != null) {
				stringBuilder.append(data);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.debug("jsonData:" + stringBuilder.toString());

		String fileData = stringBuilder.toString();

		return StringEscapeUtils.unescapeJson(fileData);
	}

}
