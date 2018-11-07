package com.favccxx.report.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysUserGroupRoleDao;
import com.favccxx.report.model.SysUserGroupRole;

@Repository
public class SysUserGroupRoleDaoImpl implements SysUserGroupRoleDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void updateUserGroupRole(long userId, long groupId, List<Long> linkRoleIds, List<Long> removeRoleIds) {
		if(userId==0 || groupId==0) {
			return;
		}
		
		//移除用户组的所有角色关联关系
		deleteByGroupId(groupId);
		
		//建立新的关联关系
		if(linkRoleIds!=null && linkRoleIds.size()>0) {
			for(long roleId : linkRoleIds) {
				saveUserGroupRole(userId, groupId, roleId);
			}
		}
	}

	@Override
	public void saveUserGroupRole(long userId, long groupId, long roleId) {
		SysUserGroupRole userGroupRole = new SysUserGroupRole();
		userGroupRole.setRoleId(roleId);
		userGroupRole.setGroupId(groupId);
		userGroupRole.setUpdateUserId(userId);
		userGroupRole.setUpdateTime(new Date());
		hibernateTemplate.saveOrUpdate(userGroupRole);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteByGroupId(long groupId) {
		String hql = "from SysUserGroupRole where groupId=?";
		List<SysUserGroupRole> list = (List<SysUserGroupRole>) hibernateTemplate.find(hql, groupId);
		if(list!=null && list.size()>0) {
			for(SysUserGroupRole userGroupRole : list) {
				hibernateTemplate.delete(userGroupRole);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroupRole> listByGroupId(long groupId) {
		String hql = "from SysUserGroupRole where groupId=?";
		List<SysUserGroupRole> list = (List<SysUserGroupRole>) hibernateTemplate.find(hql, groupId);
		return list;
	}

}
