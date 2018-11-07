package com.favccxx.report.birt.model;

/**
 * 构建JS数据源的类
 * @author chenxu
 * @date Dec 29, 2017
 * @version 1.0
 */
public class BirtDataSource {
	
	/**
	 * 数据源Id
	 */
	private String id;
	
	/**
	 * 数据源名称
	 */
	private String name;
	
	/**
	 * JDBC驱动类
	 */
	private String driverClass;
	
	/**
	 * JDBC连接地址
	 */
	private String url;
	
	/**
	 * JDBC用户名
	 */
	private String user;
	
	/**
	 * JDBC密码
	 */
	private String password;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
