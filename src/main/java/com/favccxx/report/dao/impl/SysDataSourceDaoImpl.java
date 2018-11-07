package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysDataSourceDao;
import com.favccxx.report.model.SysDataSource;

@Repository
public class SysDataSourceDaoImpl implements SysDataSourceDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	public void addSysDataSource(SysDataSource sysDataSource) {
		hibernateTemplate.save(sysDataSource);
	}

	
	public void updateSysDataSource(SysDataSource sysDataSource) {
		hibernateTemplate.update(sysDataSource);
	}

	
	public void delSysDataSource(SysDataSource sysDataSource) {
		hibernateTemplate.delete(sysDataSource);
	}

	
	public SysDataSource findbyId(long id) {
		return hibernateTemplate.get(SysDataSource.class, id);
	}

	
	
	public List<SysDataSource> listByProjectId(long projectId) {
		String hql = "from SysDataSource where projectId = ?";
		@SuppressWarnings({"unchecked" })
		List<SysDataSource> list = (List<SysDataSource>) hibernateTemplate.find(hql, projectId);
		return list;
	}

	
	public void saveSysDataSource(SysDataSource sysDataSource) {
		hibernateTemplate.saveOrUpdate(sysDataSource);
	}

	
	public List<SysDataSource> listDataSourcesByProjectId(long projectId) {
		String hql = "from SysDataSource where type='DATASOURCE' and projectId = ?";
		@SuppressWarnings({"unchecked" })
		List<SysDataSource> list = (List<SysDataSource>) hibernateTemplate.find(hql, projectId);
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public SysDataSource findRootNodeByProjectId(long projectId) {
		String hql = "from SysDataSource where parentId=0 and projectId = ?";
		List<SysDataSource> list = (List<SysDataSource>) hibernateTemplate.find(hql, projectId);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
