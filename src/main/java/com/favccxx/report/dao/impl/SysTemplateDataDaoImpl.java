package com.favccxx.report.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysTemplateDataDao;
import com.favccxx.report.model.SysTemplateData;

@Repository
public class SysTemplateDataDaoImpl implements SysTemplateDataDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void saveTemplateData(SysTemplateData templateData) {
		hibernateTemplate.save(templateData);
	}
}
