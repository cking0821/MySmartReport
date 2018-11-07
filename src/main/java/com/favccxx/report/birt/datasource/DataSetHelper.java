package com.favccxx.report.birt.datasource;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jboss.logging.Logger;

import com.favccxx.report.birt.model.BirtDataSet;

public class DataSetHelper {

	private static Logger logger = Logger.getLogger(DataSetHelper.class);

	/**
	 * 获取数据集列表
	 * 
	 * @author chenxu
	 * @date Jan 2, 2018
	 * @param templateName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSet> getDataSetNames(String templateName) {

		File file = new File(templateName);
		if (!file.exists()) {
			logger.debug("File not exist, and file path is: " + templateName);
			return null;
		}

		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(templateName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();

		Element dataSetRoot = root.element("data-sets");
		if (dataSetRoot == null) {
			return null;
		}

		List<Element> dataEles = dataSetRoot.elements();
		for (Element element : dataEles) {
			if ("oda-data-set".equals(element.getName())) {
				BirtDataSet jsDataSet = new BirtDataSet();

				String datasetName = element.attributeValue("name");
				String datasetId = element.attributeValue("id");
				jsDataSet.setDatasetName(datasetName);
				jsDataSet.setId(datasetId);
			}

			List<BirtDataSet> dataSetList = JdbcDataSourceHandler.buildJsDataSets(root);
			return dataSetList;
		}

		
		return null;
	}

}
