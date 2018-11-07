package com.favccxx.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favccxx.report.dao.SysRoleDao;
import com.favccxx.report.model.SysRole;
import com.favccxx.report.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	SysRoleDao sysRoleDao;

	@Override
	public List<SysRole> listRoles() {
		return sysRoleDao.listRoles();
	}

	@Override
	public List<SysRole> listManageRoles() {
		List<SysRole> roleList = sysRoleDao.listRoles();
		if(roleList==null || roleList.size()==0) {
			
		}
		return null;
	}

	@Override
	public SysRole findById(long id) {
		return sysRoleDao.findById(id);
	}

}
