package com.favccxx.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.dao.SysResourceDao;
import com.favccxx.report.dao.SysRoleResourceDao;
import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysRoleResource;
import com.favccxx.report.service.SysResourceService;

@Service
public class SysResourceServiceImpl implements SysResourceService {
	
	@Autowired
	SysResourceDao sysResourceDao;
	@Autowired
	SysRoleResourceDao sysRoleResourceDao;

	@Override
	public List<SysResource> listResources() {
		return sysResourceDao.listResources();
	}

	@Override
	public List<SysResource> listResourcesByRoleId(long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysRoleResource> listRoleResources(long roleId) {
		return sysRoleResourceDao.listRoleResoucesByRoleId(roleId);
	}

	@Transactional
	@Override
	public void addRoleResource(long roleId, long userId, Long[] resourceIds) {
		sysRoleResourceDao.addRoleResource(roleId, userId, resourceIds);
	}

	@Transactional
	@Override
	public void deleteRoleResource(long roleId) {
		sysRoleResourceDao.deleteRoleResource(roleId);
	}

}
