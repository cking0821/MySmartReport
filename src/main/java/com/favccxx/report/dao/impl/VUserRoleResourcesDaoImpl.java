package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.VUserRoleResourcesDao;
import com.favccxx.report.model.VUserRoleResources;

@Repository
public class VUserRoleResourcesDaoImpl implements VUserRoleResourcesDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<VUserRoleResources> listUserResources(long userId) {
		List<VUserRoleResources> list = (List<VUserRoleResources>) hibernateTemplate.find("from VUserRoleResources where userId=?", userId);
		return list;
	}

}
