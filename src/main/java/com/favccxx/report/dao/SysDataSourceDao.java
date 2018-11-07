package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysDataSource;

public interface SysDataSourceDao {

	void addSysDataSource(SysDataSource sysDataSource);
	
	void saveSysDataSource(SysDataSource sysDataSource);

	void updateSysDataSource(SysDataSource sysDataSource);

	void delSysDataSource(SysDataSource sysDataSource);
	
	SysDataSource findbyId(long id);
	
	List<SysDataSource> listByProjectId(long projectId);

	List<SysDataSource> listDataSourcesByProjectId(long projectId);
	
	/**
	 * 根据项目Id查找数据源目录的根节点
	 * @param projectId
	 * @return
	 */
	SysDataSource findRootNodeByProjectId(long projectId);
}
