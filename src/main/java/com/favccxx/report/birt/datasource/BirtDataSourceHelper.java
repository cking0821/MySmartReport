package com.favccxx.report.birt.datasource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jboss.logging.Logger;

import com.alibaba.fastjson.JSONObject;
import com.favccxx.report.birt.model.BirtDataSet;
import com.favccxx.report.birt.model.BirtDataSource;
import com.favccxx.report.birt.model.CachedMetaData;
import com.favccxx.report.birt.model.ColumnHints;
import com.favccxx.report.birt.model.Parameter;
import com.favccxx.report.birt.model.ResultSetHints;
import com.favccxx.report.util.JdbcUtil;
import com.favccxx.report.util.PropertyUtil;
import com.favccxx.report.util.RestClient;

public class BirtDataSourceHelper {
	
	private static Logger logger = Logger.getLogger(BirtDataSourceHelper.class);
	
	/**
	 * 获取JAVASCRIPT数据源列表
	 * @author chenxu
	 * @date Jan 10, 2018
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSource> getJsDataSourceList(Element root){
		List<BirtDataSource> list = new ArrayList<BirtDataSource>();
		
		Element dataSourceRoot = root.element("data-sources");
		if(dataSourceRoot==null) {
			return null;
		}
		
		List<Element> dataSources = dataSourceRoot.elements();
		for(Element element : dataSources) {
			//只有SCRIPT数据源才进行转换
			if("script-data-source".equals(element.getName())) {
				BirtDataSource dataSource = new BirtDataSource();
				String name = element.attributeValue("name");
				String id = element.attributeValue("id");				
				dataSource.setName(name);
				dataSource.setId(id);
				list.add(dataSource);
			}
		}
		return list;
	}
	
	/**
	 * 获取ODA DataSource列表
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSource> getOdaDataSourceList(Element root) {
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
	 * 移除所有的ODA数据源
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param root
	 */
	@SuppressWarnings("unchecked")
	public static void removeOdaDataSources(Element root) {
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
	 * 移除所有的ODA数据集
	 * @author chenxu
	 * @date Jan 4, 2018
	 * @param root
	 */
	@SuppressWarnings("unchecked")
	public static void removeOdaDataSets(Element root) {
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
	 * 获取所有的ODA数据集
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param root
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSet> getOdaDataSets(Element root, Map<String, String> paramMap) throws ClassNotFoundException, SQLException {
		List<BirtDataSet> list = new ArrayList<BirtDataSet>();
		
		Element dataSetRoot = root.element("data-sets");
		if(dataSetRoot==null) {
			return null;
		}
				
		//BIRT数据源列表
		List<BirtDataSource> dataSourceList = getOdaDataSourceList(root);
		
		
		List<Element> dataEles = dataSetRoot.elements();
		for(Element element : dataEles) {
			//只有ODA数据集才进行转换
			if("oda-data-set".equals(element.getName())) {
				BirtDataSet birtDataSet = new BirtDataSet();
				
				String datasetName = element.attributeValue("name");
				String datasetId = element.attributeValue("id");				
				birtDataSet.setDatasetName(datasetName);
				birtDataSet.setId(datasetId);
				
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
						
						birtDataSet.setColumnHints(columnHints);
						
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
								birtDataSet.setCachedMetaDatas(cachedMetaDatas);
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
								birtDataSet.setResultSetHints(resultSets);
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
								birtDataSet.setParameters(parameters);
							}
						}												
					}else  if("dataSource".equals(eleValue)) {
						birtDataSet.setDatasourceName(ele.getText());
					}else if("queryText".equals(eleValue)) {
						birtDataSet.setQueryText(ele.getText());
						
						logger.debug("queryText:" + ele.getText());
					}
				}
				
				//记录数据集文件
				for(BirtDataSource dataSource : dataSourceList) {
					if(birtDataSet.getDatasourceName().equals(dataSource.getName())) {
						
						String queryText = birtDataSet.getQueryText();
						logger.debug("Query Text: " + queryText);
						
						Map<Integer, Object> parameterMap = new HashMap<Integer, Object>();
						if(birtDataSet.getParameters()!=null && birtDataSet.getParameters().size()>0) {
							//遍历BIRT的所有参数列表
							
							for(int i=0, l=birtDataSet.getParameters().size(); i<l; i++) {
								Parameter parameter = birtDataSet.getParameters().get(i);
								
								String parameterName = parameter.getParamName();
								if(paramMap.get(parameterName)!=null) {
									
									if("string".equals(parameter.getDataType().toLowerCase())) {
										parameterMap.put(Integer.parseInt(parameter.getPosition()), (String)paramMap.get(parameterName));
									}else if("integer".equals(parameter.getDataType().toLowerCase())) {
										parameterMap.put(Integer.parseInt(parameter.getPosition()), Integer.parseInt(paramMap.get(parameterName).toString()));
									}else {
										parameterMap.put(Integer.parseInt(parameter.getPosition()), paramMap.get(parameterName));
									}
									
									
								}
							}
							
							
							
						}
						
						
						//获取JDBC查询的字段名
						Connection connection = JdbcUtil.getConnection(dataSource.getDriverClass(), dataSource.getUrl(), dataSource.getUser(), dataSource.getPassword());
						String	columnNames = JdbcUtil.getTbColumns(connection, queryText, parameterMap);
							
						birtDataSet.setColumnNames(columnNames);
					
							
						
							
						String jdbcDataSets = JdbcUtil.getResultSets(connection, queryText, parameterMap);
						
						
						//保存原有的数据集
						String fileName = saveDataSetData(jdbcDataSets);
						birtDataSet.setDataSetFile(fileName);
						
										
						
					}
				}
				
				list.add(birtDataSet);	
				
			}
		}
		
		
		
		return list;
	}
	
	/**
	 * 将JSON数据保存到磁盘
	 * @author chenxu
	 * @date Dec 29, 2017
	 * @param jsonData
	 * @param dataSet
	 * @return
	 */
	private static String saveDataSetData(String jsonData) {
		//去除转移字符
		String formatData = StringEscapeUtils.unescapeJson(jsonData);
		String tempJsonFilePath = PropertyUtil.getProperty("tempJsonFilePath");
		String fileName = UUID.randomUUID().toString() + ".json";
		String fullPathName = tempJsonFilePath + File.separator + fileName;
		
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
			fileWriter = new FileWriter(fullPathName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(formatData);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fullPathName;
	}
	
	
	/**
	 * 创建JS数据源
	 * @author chenxu
	 * @date Jan 4, 2018
	 * @param list
	 * @param root
	 */
	public static void createJsDataSourceElements(List<BirtDataSource> list, Element root) {
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
	 * 将JS数据集更改成本地JS数据集
	 * @author chenxu
	 * @date Jan 10, 2018
	 * @param root
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static List<BirtDataSet> changeJsDataSetElements(Element root) throws ClientProtocolException, IOException {
		if(root==null) {
			return null;
		}
		
		Element dataSetRoot = root.element("data-sets");
		if(dataSetRoot==null) {
			return null;
		}
				
		//BIRT数据源列表
		List<BirtDataSet> dataSetsList = new ArrayList<BirtDataSet>();
		
		
		List<Element> dataEles = dataSetRoot.elements();
		for(Element element : dataEles) {
			//只有ODA数据集才进行转换
			if("script-data-set".equals(element.getName())) {
				
				BirtDataSet birtDataSet = new BirtDataSet();
				
				String datasetName = element.attributeValue("name");
				String datasetId = element.attributeValue("id");				
				birtDataSet.setDatasetName(datasetName);
				birtDataSet.setId(datasetId);
				
				
				List<Element> dataSetProps = element.elements();
				for(Element ele : dataSetProps) {
					String eleValue = ele.attributeValue("name");
					//数据列定义
					if("open".equals(eleValue)) {
						String openText = ele.getText();
						//通过本地数据文件获取数据集的方式不可编辑
						if(openText.indexOf("url")==-1) {
							return null;
						}
						
						String restUrlStr = openText.substring(openText.indexOf("url"));
						String restUrl = restUrlStr.substring(restUrlStr.indexOf("\"") + 1, restUrlStr.indexOf(";")-1);
						
						String headersStr = openText.substring(openText.indexOf("headers"));
						String headers = headersStr.substring(headersStr.indexOf("=") + 1, headersStr.indexOf(";"));
						JSONObject jb = JSONObject.parseObject(headers);
						Map<String, Object> headerMap = jb.getInnerMap();
						
						String jsonData = RestClient.rest(restUrl, headerMap);
						
						if(jsonData!=null) {
							
							String dataSetFileName = saveDataSetData(jsonData);
							birtDataSet.setDataSetFile(dataSetFileName);
							String newOpenText = getOpenContext(dataSetFileName);
							
							ele.setText(newOpenText);
						}
					}else if ("fetch".equals(eleValue)) {
						String columnNames = birtDataSet.getColumnNames();
						if(columnNames!=null && !"".equals(columnNames)) {
							String fetchText = getFetchContext(columnNames);
							ele.setText(fetchText);							
						}
					}else if ("resultSetHints".equals(eleValue)) {
						List<Element> structures = ele.elements();	
//						List<ResultSetHints> resultSets = new ArrayList<ResultSetHints>();
						
						String columnNames = "";
						StringBuffer sb = new StringBuffer();
						
						if (structures!=null && structures.size()>0) {
							for(Element rs : structures) {
								//遍历每个structure的元素
								List<Element> columns = rs.elements();
								if(columns!=null) {									
									for(Element prop : columns) {
										if("name".equals(prop.attributeValue("name"))) {
											sb.append(prop.getText()).append(",");
										}							
									}
									
									columnNames = sb.toString();
									if(columnNames.length()>0) {
										columnNames = columnNames.substring(0, columnNames.length()-1);
									}
									birtDataSet.setColumnNames(columnNames);
								}
							}
						}
						
						
						
					}
				}
				
				dataSetsList.add(birtDataSet);
			}
		}
		
		return dataSetsList;
	}
	
	
	
	/**
	 * 创建JS数据集
	 * @author chenxu
	 * @date Jan 4, 2018
	 * @param root 报表模板文件的xml根元素
	 * @param dataSourceList 数据源列表
	 * @param dataSetList 数据集列表
	 */
	@SuppressWarnings("unchecked")
	public static void createJsDataSetElements(Element root, List<BirtDataSource> dataSourceList, List<BirtDataSet> dataSetList) {
		if(root==null || dataSetList==null || dataSetList.size()==0) {
			return;
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
			if(dataSet.getColumnHints()!=null) {
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
											
					
					//添加JS数据集的OPEN和FETCH方法
					String openTxt = getOpenContext(dataSet.getDataSetFile());
					String fetchTxt = getFetchContext(dataSet.getColumnNames());
					ele.addElement("method").addAttribute("name", "open").setText(openTxt);		
					ele.addElement("method").addAttribute("name", "fetch").setText(fetchTxt);
					
				}
			}
							
		}				
		
	}
	
	private static String getOpenContext(String fileName) {
		fileName = fileName.replaceAll("\\\\", "\\\\\\\\");
		StringBuffer sb = new StringBuffer();
		sb.append("<![CDATA[")
			.append("_index = 0;")
			.append("result = Packages.com.favccxx.report.util.RestStaticDataUtil.restJavaJsonFromFile('")
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

}
