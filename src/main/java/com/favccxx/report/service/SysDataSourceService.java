package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysDataSource;

public interface SysDataSourceService {

	void addSysDataSource(SysDataSource sysDataSource);
	
	void saveSysDataSource(SysDataSource sysDataSource);

	void updateSysDataSource(SysDataSource sysDataSource);

	void delSysDataSource(SysDataSource sysDataSource);

	SysDataSource findbyId(long id);

	List<SysDataSource> listByProjectId(long projectId);
	
	List<SysDataSource> listDataSourcesByProjectId(long projectId);

}
