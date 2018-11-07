package com.favccxx.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysReportDao;
import com.favccxx.report.model.SysReport;

@Repository
public class SysReportDaoImpl implements SysReportDao{
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	
	public void addSysReport(SysReport sysReport) {
		hibernateTemplate.save(sysReport);
	}

	
	public void updateSysReport(SysReport sysReport) {
		hibernateTemplate.update(sysReport);
	}

	
	public List<SysReport> findByDirectoryId(long directoryId) {
		String hql = "from SysReport where directoryId = ?";
		@SuppressWarnings("unchecked")
		List<SysReport> list = (List<SysReport>) hibernateTemplate.find(hql, directoryId);
		return list;
	}

	
	public SysReport findById(long id) {
		return hibernateTemplate.get(SysReport.class, id);
	}

}
