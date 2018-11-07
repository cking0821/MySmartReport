package com.favccxx.report.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysRoleResourceDao;
import com.favccxx.report.model.SysRoleResource;

@Repository
public class SysRoleResourceDaoImpl implements SysRoleResourceDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRoleResource> listRoleResoucesByRoleId(long roleId) {
		String hql = "from SysRoleResource  where roleResourceId.roleId = ?";
		List<SysRoleResource> list = (List<SysRoleResource>) hibernateTemplate.find(hql, roleId);
		return list;
	}

	




	@Override
	public void addRoleResource(long roleId, long userId, Long[] resourceIds) {
		
		for (long resourceId : resourceIds) {
			SysRoleResource roleResource = new SysRoleResource();
			roleResource.getRoleResourceId().setResourceId(resourceId);
			roleResource.getRoleResourceId().setRoleId(roleId);
			roleResource.setUpdateTime(new Date());
			hibernateTemplate.save(roleResource);
		}
		
	}



	@Override
	public void deleteRoleResource(long roleId) {
		String hql = "delete SysRoleResource  where roleResourceId.roleId = ?";
		hibernateTemplate.bulkUpdate(hql, roleId);
		
		
	}

}
