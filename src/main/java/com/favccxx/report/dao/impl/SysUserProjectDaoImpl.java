package com.favccxx.report.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysUserProjectDao;
import com.favccxx.report.model.SysUserProject;

@Repository
public class SysUserProjectDaoImpl implements SysUserProjectDao {
	

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void saveUserProject(SysUserProject sysUserProject) {
		hibernateTemplate.saveOrUpdate(sysUserProject);
	}

	@Override
	public void deleteByProjectId(long projectId) {
		String hql = "delete SysUserProject  where projectId = ?";
		hibernateTemplate.bulkUpdate(hql, projectId);
	}

}
