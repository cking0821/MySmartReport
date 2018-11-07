package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysProjectLinkUserGroupDao;
import com.favccxx.report.model.SysProjectLinkUserGroup;

@Repository
public class SysProjectLinkUserGroupDaoImpl implements SysProjectLinkUserGroupDao{
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void save(SysProjectLinkUserGroup projectLinkUserGroup) {
		hibernateTemplate.save(projectLinkUserGroup);
	}

	@Override
	public void delete(SysProjectLinkUserGroup projectLinkUserGroup) {
		hibernateTemplate.delete(projectLinkUserGroup);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProjectLinkUserGroup> listByProjectId(long projectId) {
		String hql = "from SysProjectLinkUserGroup where projectId=?";
		List<SysProjectLinkUserGroup> list = (List<SysProjectLinkUserGroup>) hibernateTemplate.find(hql, projectId);		
		return list;
	}

	@Override
	public void deleteByProjectId(long projectId) {		
		List<SysProjectLinkUserGroup> list = listByProjectId(projectId);
		if(list!=null && list.size()>0) {
			for(SysProjectLinkUserGroup plug : list) {
				hibernateTemplate.delete(plug);
			}
		}
	}


	
	
	

}
