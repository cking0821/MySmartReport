package com.favccxx.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.dao.SysDataSourceDao;
import com.favccxx.report.model.SysDataSource;
import com.favccxx.report.service.SysDataSourceService;

@Service
public class SysDataSourceServiceImpl implements SysDataSourceService {

	@Autowired
	SysDataSourceDao sysDataSourceDao;
	
	
	public void addSysDataSource(SysDataSource sysDataSource) {
		sysDataSourceDao.addSysDataSource(sysDataSource);
	}

	@Transactional
	public void updateSysDataSource(SysDataSource sysDataSource) {
		sysDataSourceDao.updateSysDataSource(sysDataSource);
	}

	
	@Transactional
	public void delSysDataSource(SysDataSource sysDataSource) {
		sysDataSourceDao.delSysDataSource(sysDataSource);
	}

	
	public SysDataSource findbyId(long id) {
		return sysDataSourceDao.findbyId(id);
	}

	
	public List<SysDataSource> listByProjectId(long projectId) {
		return sysDataSourceDao.listByProjectId(projectId);
	}

	@Transactional
	public void saveSysDataSource(SysDataSource sysDataSource) {
		sysDataSourceDao.saveSysDataSource(sysDataSource);
	}

	
	public List<SysDataSource> listDataSourcesByProjectId(long projectId) {
		return sysDataSourceDao.listDataSourcesByProjectId(projectId);
	}

}
