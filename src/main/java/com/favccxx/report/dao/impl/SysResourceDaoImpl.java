package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysResourceDao;
import com.favccxx.report.model.SysResource;

@Repository
public class SysResourceDaoImpl implements SysResourceDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<SysResource> listResources() {
		List<SysResource> list = (List<SysResource>) hibernateTemplate.find("from SysResource");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysResource> listUserResourcesByUserId(long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct sr from SysUserLinkGroup ulg, ")
			.append(" SysUserGroupRole ugr, SysRoleResource rr, SysResource sr ")
			.append(" where ")
			.append(" ulg.groupId = ugr.groupId and ugr.roleId = rr.roleResourceId.roleId and rr.roleResourceId.resourceId = sr.id ")
			.append(" and ulg.userId=")
			.append(userId)
			.append(" order by sr.id asc");
		List<SysResource> list = (List<SysResource>) hibernateTemplate.find(sb.toString());
		return list;
	}

}


