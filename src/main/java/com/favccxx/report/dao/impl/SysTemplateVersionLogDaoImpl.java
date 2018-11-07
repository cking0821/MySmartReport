package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysTemplateVersionLogDao;
import com.favccxx.report.model.SysTemplateVersionLog;

@Repository
public class SysTemplateVersionLogDaoImpl implements SysTemplateVersionLogDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void saveVersionLog(SysTemplateVersionLog versionLog) {
		hibernateTemplate.saveOrUpdate(versionLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplateVersionLog> listByTemplateVersionId(long templateVersionId) {
		String hql = "from SysTemplateVersionLog where templateVersionId = ?";
		List<SysTemplateVersionLog> list = (List<SysTemplateVersionLog>) hibernateTemplate.find(hql, templateVersionId);
		return list;
	}

}
