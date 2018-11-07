package com.favccxx.report.birt.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Element;
import org.jboss.logging.Logger;

import com.favccxx.report.birt.model.BirtDataSet;
import com.favccxx.report.birt.model.BirtDataSource;
import com.favccxx.report.birt.model.CachedMetaData;
import com.favccxx.report.birt.model.ColumnHints;
import com.favccxx.report.birt.model.Parameter;
import com.favccxx.report.birt.model.ResultSetHints;
import com.favccxx.report.util.JdbcUtil;

public class JdbcDataSourceHandler {

	private static Logger logger = Logger.getLogger(JdbcDataSourceHandler.class);
	
	
	/**
	 * 将JDBC数据集转换为JS数据集列表，并移除原来的JDBC数据集
	 * @author chenxu
	 * @date Dec 29, 2017
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSet> buildJsDataSets(Element root) {
		List<BirtDataSet> list = new ArrayList<BirtDataSet>();
		
		Element dataSetRoot = root.element("data-sets");
		if(dataSetRoot==null) {
			return null;
		}
		
		List<Element> dataEles = dataSetRoot.elements();
		for(Element element : dataEles) {
			//只有ODA数据集才进行转换
			if("oda-data-set".equals(element.getName())) {
				BirtDataSet jsDataSet = new BirtDataSet();
				
				String datasetName = element.attributeValue("name");
				String datasetId = element.attributeValue("id");				
				jsDataSet.setDatasetName(datasetName);
				jsDataSet.setId(datasetId);
				
				List<Element> dataSetProps = element.elements();
				for(Element ele : dataSetProps) {
					String eleValue = ele.attributeValue("name");
					//数据列定义
					if("columnHints".equals(eleValue)) {
						List<ColumnHints> columnHints = new ArrayList<ColumnHints>();
						List<Element> structures = ele.elements();
						
						for(Element structure : structures) {
							ColumnHints ch = new ColumnHints();
							List<Element> props = structure.elements();
							for(Element prop : props) {
								if("columnName".equals(prop.attributeValue("name"))) {
									ch.setColumnName(prop.getText());
								}
								
								if("displayName".equals(prop.attributeValue("name"))) {
									ch.setDisplayName(prop.getText());
									if(prop.attributeValue("key")!=null) {
										ch.setDisplayNameKey(prop.attributeValue("key"));
									}										
								}
								
								if("alias".equals(prop.attributeValue("name"))) {
									ch.setAlias(prop.getText());
								}
							}

							columnHints.add(ch);							
						}
						
						jsDataSet.setColumnHints(columnHints);
						
					}else if("cachedMetaData".equals(eleValue)) {
						List<Element> resultSets = ele.element("list-property").elements();	
						List<CachedMetaData> cachedMetaDatas = new ArrayList<CachedMetaData>();
						for(Element rs : resultSets) {
							//遍历每个数据集的元素
							List<Element> rss = rs.elements();
							if(rss!=null) {
								CachedMetaData cachedMetaData = new CachedMetaData();
								for(Element prop : rss) {
									if("position".equals(prop.attributeValue("name"))) {
										cachedMetaData.setPosition(prop.getText());
									}
									if("name".equals(prop.attributeValue("name"))) {
										cachedMetaData.setName(prop.getText());
									}
									if("dataType".equals(prop.attributeValue("name"))) {
										cachedMetaData.setDataType(prop.getText());
									}
								}
								cachedMetaDatas.add(cachedMetaData);
								jsDataSet.setCachedMetaDatas(cachedMetaDatas);
							}
						}
					}else if("resultSet".equals(eleValue)) {
						List<Element> structures = ele.elements();	
						List<ResultSetHints> resultSets = new ArrayList<ResultSetHints>();
						for(Element rs : structures) {
							//遍历每个structure的元素
							List<Element> columns = rs.elements();
							if(columns!=null) {
								ResultSetHints rsh = new ResultSetHints();
								for(Element prop : columns) {
									if("position".equals(prop.attributeValue("name"))) {
										rsh.setPosition(prop.getText());
									}
									if("name".equals(prop.attributeValue("name"))) {
										rsh.setName(prop.getText());
									}
									if("dataType".equals(prop.attributeValue("name"))) {
										rsh.setDataType(prop.getText());
									}									
								}
								resultSets.add(rsh);
								jsDataSet.setResultSetHints(resultSets);
							}
						}
					}else if("parameters".equals(eleValue)) {
						List<Element> params = ele.elements();	
						List<Parameter> parameters = new ArrayList<Parameter>();
						for(Element param : params) {
							//遍历每个structure的parameter元素
							List<Element> columns = param.elements();
							if(columns!=null) {
								Parameter parameter = new Parameter();
								for(Element prop : columns) {
									if("name".equals(prop.attributeValue("name"))) {
										parameter.setName(prop.getText());
									}
									if("paramName".equals(prop.attributeValue("name"))) {
										parameter.setParamName(prop.getText());
									}
									if("nativeName".equals(prop.attributeValue("name"))) {
										parameter.setNativeName(prop.getText());
									}
									if("dataType".equals(prop.attributeValue("name"))) {
										parameter.setDataType(prop.getText());
									}
									if("nativeDataType".equals(prop.attributeValue("name"))) {
										parameter.setNativeDataType(prop.getText());
									}
									if("position".equals(prop.attributeValue("name"))) {
										parameter.setPosition(prop.getText());
									}
									if("defaultValue".equals(prop.attributeValue("name"))) {
										parameter.setDefaultValue(prop.getText());
									}
									if("isInput".equals(prop.attributeValue("name"))) {
										parameter.setIsInput(prop.getText());
									}
									if("isOutput".equals(prop.attributeValue("name"))) {
										parameter.setIsOutput(prop.getText());
									}
									
								}
								parameters.add(parameter);
								jsDataSet.setParameters(parameters);
							}
						}												
					}else  if("dataSource".equals(eleValue)) {
						jsDataSet.setDatasourceName(ele.getText());
					}else if("queryText".equals(eleValue)) {
						jsDataSet.setQueryText(ele.getText());
					}
				}
				
				
				
			list.add(jsDataSet);	
				
			}
		}
		
		return list;
	}
	
	
	/**
	 * 移除所有的JDBC数据集
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param root
	 */
	@SuppressWarnings("unchecked")
	public static void removeJDBCDataSets(Element root) {
		Element dataSetRoot = root.element("data-sets");
		if(dataSetRoot==null) {
			return;
		}
		
		List<Element> dataEles = dataSetRoot.elements();
		for(Element element : dataEles) {
			if("oda-data-set".equals(element.getName())) {
				dataSetRoot.remove(element);
			}
		}
	}
	
	
	/**
	 * 根据JDBC数据集创建JS数据集
	 * @author chenxu
	 * @date Dec 28, 2017
	 * @param root xml设计文档根元素
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BirtDataSet buildJsDataSet(Element root) {
		BirtDataSet jsDataSet = new BirtDataSet();
		
		Element dssEl = root.element("data-sets");
		if (dssEl != null) {
			Element odaEl = dssEl.element("oda-data-set");
			if (odaEl != null) {
				String datasetName = odaEl.attributeValue("name");
				String datasetId = odaEl.attributeValue("id");				
				jsDataSet.setDatasetName(datasetName);
				jsDataSet.setId(datasetId);
				
				System.out.println(datasetName + "," + datasetId);
				
				//遍历ODA数据集的属性
				List<Element> list = odaEl.elements();
				for(Element ele : list) {
					String eleValue = ele.attributeValue("name");
					//数据列定义
					if("columnHints".equals(eleValue)) {
						List<ColumnHints> columnHints = new ArrayList<ColumnHints>();
						List<Element> structures = ele.elements();
						
						for(Element structure : structures) {
							ColumnHints ch = new ColumnHints();
							List<Element> props = structure.elements();
							for(Element element : props) {
								if("columnName".equals(element.attributeValue("name"))) {
									ch.setColumnName(element.getText());
								}
								
								if("displayName".equals(element.attributeValue("name"))) {
									ch.setDisplayName(element.getText());
									if(element.attributeValue("key")!=null) {
										ch.setDisplayNameKey(element.attributeValue("key"));
									}										
								}
								
								if("alias".equals(element.attributeValue("name"))) {
									ch.setAlias(element.getText());
								}
							}

							columnHints.add(ch);							
						}
						
						jsDataSet.setColumnHints(columnHints);
						
					}else if("cachedMetaData".equals(eleValue)) {
						List<Element> resultSets = ele.element("list-property").elements();	
						List<CachedMetaData> cachedMetaDatas = new ArrayList<CachedMetaData>();
						for(Element rs : resultSets) {
							//遍历每个数据集的元素
							List<Element> rss = rs.elements();
							if(rss!=null) {
								CachedMetaData cachedMetaData = new CachedMetaData();
								for(Element element : rss) {
									if("position".equals(element.attributeValue("name"))) {
										cachedMetaData.setPosition(element.getText());
									}
									if("name".equals(element.attributeValue("name"))) {
										cachedMetaData.setName(element.getText());
									}
									if("dataType".equals(element.attributeValue("name"))) {
										cachedMetaData.setDataType(element.getText());
									}
								}
								cachedMetaDatas.add(cachedMetaData);
								jsDataSet.setCachedMetaDatas(cachedMetaDatas);
							}
						}
					}else if("resultSet".equals(eleValue)) {
						List<Element> structures = ele.elements();	
						List<ResultSetHints> resultSets = new ArrayList<ResultSetHints>();
						for(Element rs : structures) {
							//遍历每个structure的元素
							List<Element> columns = rs.elements();
							if(columns!=null) {
								ResultSetHints rsh = new ResultSetHints();
								for(Element element : columns) {
									if("position".equals(element.attributeValue("name"))) {
										rsh.setPosition(element.getText());
									}
									if("name".equals(element.attributeValue("name"))) {
										rsh.setName(element.getText());
									}
									if("dataType".equals(element.attributeValue("name"))) {
										rsh.setDataType(element.getText());
									}									
								}
								resultSets.add(rsh);
								jsDataSet.setResultSetHints(resultSets);
							}
						}
					}else if("dataSource".equals(eleValue)) {
						jsDataSet.setDatasourceName(ele.getText());
					}else if("queryText".equals(eleValue)) {
						jsDataSet.setQueryText(ele.getText());
					}
				}
				
				dssEl.remove(odaEl);
			}
		}
		
		return jsDataSet;
	}
	
	/**
	 * 获取JDBC的所有数据源
	 * 根据这些数据源可以构建JS数据源
	 * @author chenxu
	 * @date Dec 29, 2017
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSource> buildDataSources(Element root) {
		List<BirtDataSource> list = new ArrayList<BirtDataSource>();
		
		Element dataSourceRoot = root.element("data-sources");
		if(dataSourceRoot==null) {
			return null;
		}
		
		List<Element> dataSources = dataSourceRoot.elements();
		for(Element element : dataSources) {
			//只有ODA数据源才进行转换
			if("oda-data-source".equals(element.getName())) {
				BirtDataSource dataSource = new BirtDataSource();
				
				String name = element.attributeValue("name");
				String id = element.attributeValue("id");				
				dataSource.setName(name);
				dataSource.setId(id);
				
				List<Element> jdbcProps = element.elements();
				for (Element ele : jdbcProps) {
					if ("property".equals(ele.getName()) && "odaDriverClass".equals(ele.attribute(0).getValue())) {
						dataSource.setDriverClass(ele.getText());
					}

					if ("property".equals(ele.getName()) && "odaURL".equals(ele.attribute(0).getValue())) {
						dataSource.setUrl(ele.getText());
					}

					if ("property".equals(ele.getName()) && "odaUser".equals(ele.attribute(0).getValue())) {
						dataSource.setUser(ele.getText());
					}

					if ("encrypted-property".equals(ele.getName())
							&& "odaPassword".equals(ele.attribute(0).getValue())) {
						String encryptPwd = ele.getText();
						String password = new String(Base64.decodeBase64(encryptPwd));
						dataSource.setPassword(password);
					}
				}
				
				
				list.add(dataSource);
			}
		}
		
		return list;
	}
	
	
	/**
	 * 移除所有的JDBC数据源
	 * @author chenxu
	 * @date Dec 29, 2017
	 * @param root
	 */
	@SuppressWarnings("unchecked")
	public static void removeAllDataSources(Element root) {
		Element dataSources = root.element("data-sources");
		if(dataSources!=null) {
			List<Element> list = dataSources.elements();
			for (Element element : list) {
				if("oda-data-source".equals(element.getName())) {
					dataSources.remove(element);
				}
			}
		}
			
	}
	

	/**
	 * 移除JDBC数据源
	 * @author chenxu
	 * @date Dec 28, 2017
	 * @param root xml设计文档的根元素
	 */
	public static void removeDataSource(Element root) {
		Element dssEl = root.element("data-sources");
		if (dssEl != null) {
			Element odaEl = dssEl.element("oda-data-source");
			if (odaEl != null) {
				dssEl.remove(odaEl);
			}
		}
	}

	/**
	 * 获取JDBC数据源的数据连接
	 * 
	 * @author chenxu
	 * @date Dec 28, 2017
	 * @param root
	 *            birt xml文档的根元素
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected static Connection getJdbcConnection(Element root) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		String url = "", driverClass = "", user = "", password = "";

		List<Element> jdbcProps = root.element("data-sources").element("oda-data-source").elements();
		for (Element element : jdbcProps) {
			if ("property".equals(element.getName()) && "odaDriverClass".equals(element.attribute(0).getValue())) {
				driverClass = element.getText();
			}

			if ("property".equals(element.getName()) && "odaURL".equals(element.attribute(0).getValue())) {
				url = element.getText();
			}

			if ("property".equals(element.getName()) && "odaUser".equals(element.attribute(0).getValue())) {
				user = element.getText();
			}

			if ("encrypted-property".equals(element.getName())
					&& "odaPassword".equals(element.attribute(0).getValue())) {
				String encryptPwd = element.getText();
				password = new String(Base64.decodeBase64(encryptPwd));
			}
		}

		logger.debug("Connection Info: driverClass=" + driverClass + ", url=" + url + ", username=" + user
				+ ", password=" + password);

		try {
			conn = JdbcUtil.getConnection(driverClass, url, user, password);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}

		return conn;
	}

}
