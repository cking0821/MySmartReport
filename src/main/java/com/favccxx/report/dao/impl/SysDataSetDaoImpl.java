package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysDataSetDao;
import com.favccxx.report.model.SysDataSet;

@Repository
public class SysDataSetDaoImpl implements SysDataSetDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void addSysDataSet(SysDataSet dataSet) {
		hibernateTemplate.save(dataSet);
	}

	@Override
	public void saveSysDataSet(SysDataSet dataSet) {
		hibernateTemplate.saveOrUpdate(dataSet);
	}

	@Override
	public void updateSysDataSet(SysDataSet dataSet) {
		hibernateTemplate.update(dataSet);
	}

	@Override
	public void delSysDataSet(SysDataSet dataSet) {
		hibernateTemplate.delete(dataSet);
	}

	@Override
	public SysDataSet findbyId(long id) {
		return hibernateTemplate.get(SysDataSet.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDataSet> listByProjectId(long projectId) {
		String hql = "from SysDataSet where projectId = ?";
		List<SysDataSet> list = (List<SysDataSet>) hibernateTemplate.find(hql, projectId);
		return list;
	}

}
