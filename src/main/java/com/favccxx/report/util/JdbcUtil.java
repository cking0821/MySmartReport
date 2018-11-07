package com.favccxx.report.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JdbcUtil {

	public static Connection getConnection(String driverClass, String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	public static String getTbColumns(Connection conn, String sql, Map<Integer, Object> paramMap) throws SQLException {
		
		PreparedStatement ps = conn.prepareStatement(sql);
		if(!paramMap.isEmpty()) {
			Iterator<Entry<Integer, Object>> iterator = paramMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, Object> entry = iterator.next();
				
				if(entry.getValue() instanceof Integer) {
					ps.setInt(entry.getKey(), (Integer)entry.getValue());
				}else if (entry.getValue() instanceof String) {
					ps.setString(entry.getKey(), (String)entry.getValue());
				}else if(entry.getValue() instanceof Boolean) {
					ps.setBoolean(entry.getKey(), (Boolean)entry.getValue());
				}
				
				
					
			}
		}
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsme = rs.getMetaData();

		int columnCount = rsme.getColumnCount();
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < columnCount + 1; i++) {
			sb.append(rsme.getColumnName(i)).append(",");
		}
		if (sb.toString().length() > 1) {
			return sb.toString().substring(0, sb.toString().length() - 1);
		}
		return "";
	}

	public static String getResultSets(Connection conn, String sql, Map<Integer, Object> paramMap) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		if(!paramMap.isEmpty()) {
			Iterator<Entry<Integer, Object>> iterator = paramMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, Object> entry = iterator.next();
				
				if(entry.getValue() instanceof Integer) {
					ps.setInt(entry.getKey(), (Integer)entry.getValue());
				}else if (entry.getValue() instanceof String) {
					ps.setString(entry.getKey(), (String)entry.getValue());
				}else if(entry.getValue() instanceof Boolean) {
					ps.setBoolean(entry.getKey(), (Boolean)entry.getValue());
				}
				
				
					
			}
		}
		
		
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for(int i=1; i<columnCount+1; i++) {
				//别名
				String columnName = rsmd.getColumnLabel(i);
				String columnValue = rs.getString(columnName);
				if(columnValue != null) {
					dataMap.put(columnName, columnValue);
				}else {
					dataMap.put(columnName, "");
				}
				
			}
			dataList.add(dataMap);		 
		}
		
		return JSON.toJSONString(dataList, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
	}

	public static void main(String[] args) {
		Connection conn;
		try {
			conn = getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://172.17.100.130/mp", "root", "111111");
			String aa = getTbColumns(conn, "select * from sys_user",null);
			System.out.println(aa);
			
			String rs = getResultSets(conn, "select * from sys_user", null);
			System.out.println(rs);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
