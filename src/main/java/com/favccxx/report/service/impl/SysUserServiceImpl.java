package com.favccxx.report.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.dao.SysResourceDao;
import com.favccxx.report.dao.SysUserDao;
import com.favccxx.report.dao.VUserRoleResourcesDao;
import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.model.VUserRoleResources;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserDao userDao;
	@Autowired
	SysResourceDao sysResourceDao;
	@Autowired
	VUserRoleResourcesDao vUserRoleResourcesDao;
	

	@Transactional
	public void addUser(SysUser user) {
		userDao.addUser(user);
	}


	public List<SysUser> listUsers() {
		return userDao.listUsers();
	}

	@Override
	public SysUser findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public SysUser findById(long id) {
		return userDao.findById(id);
	}

	@Transactional
	@Override
	public void delUser(long id) {
		SysUser sysUser = userDao.findById(id);
		if (sysUser != null) {
			sysUser.setUserStatus(SysConstants.USER_STATUS_DELETE);
			sysUser.setUpdateTime(new Date());
			userDao.saveUser(sysUser);
		}

	}

	@Transactional
	@Override
	public void saveUser(SysUser user) {
		userDao.saveUser(user);
	}


	@Cacheable(value="userResourceCache", key = "#userId")
	@Override
	public List<VUserRoleResources> listUserResources(long userId) {
		List<VUserRoleResources> list = vUserRoleResourcesDao.listUserResources(userId);
		return list;
	}

	@Override
	public PageInfo<SysUser> pageUsers(String searchTxt, int offset, int pageSize) {
		List<SysUser> list = userDao.pageList(searchTxt, offset, pageSize);
		int totalCount = userDao.pageCount(searchTxt);
		int pageIndex = (offset + pageSize)/pageSize;
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(pageIndex, pageSize, totalCount, list);
		return pageInfo;
	}

	@Override
	public List<SysResource> listUserResourcesByUserId(long userId) {
		return sysResourceDao.listUserResourcesByUserId(userId);
	}

	

}
