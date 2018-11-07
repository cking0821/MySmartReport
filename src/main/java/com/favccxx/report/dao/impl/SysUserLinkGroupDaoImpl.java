package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysUserLinkGroupDao;
import com.favccxx.report.model.SysUserLinkGroup;

@Repository
public class SysUserLinkGroupDaoImpl implements SysUserLinkGroupDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void updateUserLinkGroups(long groupId, List<Long> linkUserIds, List<Long> removeUserIds) {
		if(groupId==0) {
			return;
		}

		if(linkUserIds!=null && linkUserIds.size()>0) {
			for(long userId : linkUserIds) {
				saveUserLinkGroup(groupId, userId);
			}
		}
		
		
		if(removeUserIds!=null && removeUserIds.size()>0) {
			for(long userId : removeUserIds) {
				deleteUserLinkGroup(groupId, userId);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteUserLinkGroup(long groupId, long userId) {
		String hql = "from SysUserLinkGroup where userId=? and groupId=?";
		List<SysUserLinkGroup> list = (List<SysUserLinkGroup>) hibernateTemplate.find(hql, userId, groupId);
		if(list!=null && list.size()>0) {
			for(SysUserLinkGroup linkGroup : list) {
				hibernateTemplate.delete(linkGroup);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveUserLinkGroup(long groupId, long userId) {
		String hql = "from SysUserLinkGroup where userId=? and groupId=?";
		List<SysUserLinkGroup> list = (List<SysUserLinkGroup>) hibernateTemplate.find(hql, userId, groupId);
		if(list==null || list.size()==0) {
			SysUserLinkGroup userLinkGroup = new SysUserLinkGroup();
			userLinkGroup.setGroupId(groupId);
			userLinkGroup.setUserId(userId);
			hibernateTemplate.saveOrUpdate(userLinkGroup);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteByGroupId(long groupId) {
		String hql = "from SysUserLinkGroup where groupId=?";
		List<SysUserLinkGroup> list = (List<SysUserLinkGroup>) hibernateTemplate.find(hql, groupId);
		if(list!=null && list.size()>0) {
			for(SysUserLinkGroup linkGroup : list) {
				hibernateTemplate.delete(linkGroup);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> ListGroupIdsByUserId(long userId) {
		String hql = "select distinct groupId from SysUserLinkGroup where userId=?";
		List<Long> list = (List<Long>) hibernateTemplate.find(hql, userId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUserInGroup(long userId, long groupId) {
		boolean flag = false;
		String hql = "from SysUserLinkGroup where userId=? and groupId=?";
		List<SysUserLinkGroup> list = (List<SysUserLinkGroup>) hibernateTemplate.find(hql, userId, groupId);
		if(list!=null && list.size()>0) {
			flag = true;
		}
		return flag;
	}

}
