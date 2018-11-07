package com.favccxx.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.dao.SysUserGroupDao;
import com.favccxx.report.dao.SysUserGroupRoleDao;
import com.favccxx.report.dao.SysUserLinkGroupDao;
import com.favccxx.report.model.SysUserGroup;
import com.favccxx.report.model.SysUserGroupRole;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.service.SysUserGroupService;

@Service
public class SysUserGroupServiceImpl implements SysUserGroupService {
	
	@Autowired
	SysUserGroupDao sysUserGroupDao;
	@Autowired
	SysUserLinkGroupDao sysUserLinkGroupDao;
	@Autowired
	SysUserGroupRoleDao userGroupRoleDao;

	@Transactional
	@Override
	public void saveOrUpdateUserGroup(SysUserGroup sysUserGroup) {
		sysUserGroupDao.saveOrUpdateUserGroup(sysUserGroup);
	}

	@Transactional
	@Override
	public void deleteUserGroup(long id) {
		//删除用户组
		sysUserGroupDao.deleteUserGroup(id);
		//删除用户组与用户之间的关系
		sysUserLinkGroupDao.deleteByGroupId(id);
		//删除用户组与角色之间的关系
		userGroupRoleDao.deleteByGroupId(id);
	}

	@Override
	public SysUserGroup findById(long id) {
		return sysUserGroupDao.findById(id);
	}

	@Override
	public PageInfo<SysUserGroup> pageUserGroups(int pageIndex, int pageSize) {
		List<SysUserGroup> list = sysUserGroupDao.pageList(pageIndex, pageSize);
		int totalCount = sysUserGroupDao.pageCount();
		PageInfo<SysUserGroup> pageInfo = new PageInfo<SysUserGroup>(pageIndex, pageSize, totalCount, list);
		return pageInfo;
	}

	@Override
	public List<SysUserGroup> findAll() {
		return sysUserGroupDao.findAll();
	}


	@Transactional
	@Override
	public void saveUserGroupUsers(long groupId, List<Long> addUserIds, List<Long> removeUserIds) {
		if(groupId==0) {
			return;
		}
		
		sysUserLinkGroupDao.updateUserLinkGroups(groupId, addUserIds, removeUserIds);
	}

	@Override
	public List<SysUserGroupRole> listByGroupId(long groupId) {
		return userGroupRoleDao.listByGroupId(groupId);
	}

	@Transactional
	@Override
	public void updateUserGroupRoles(long userId, long groupId, List<Long> roleIds) {
		userGroupRoleDao.updateUserGroupRole(userId, groupId, roleIds, null);
	}

	@Override
	public List<Long> ListGroupIdsByUserId(long userId) {
		return sysUserLinkGroupDao.ListGroupIdsByUserId(userId);
	}

	@Override
	public boolean isUserInGroup(long userId, long groupId) {
		return sysUserLinkGroupDao.isUserInGroup(userId, groupId);
	}

}
