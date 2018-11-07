package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysRoleDao;
import com.favccxx.report.model.SysRole;

@Repository
public class SysRoleDaoImpl implements SysRoleDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> listRoles() {
		List<SysRole> list = (List<SysRole>) hibernateTemplate.find("from SysRole");
		return list;
	}

	@Override
	public SysRole findById(long id) {
		return hibernateTemplate.get(SysRole.class, id);
	}

}
	