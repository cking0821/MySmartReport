package com.favccxx.report.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysScheduleReportDao;
import com.favccxx.report.model.SysScheduleReport;

@Repository
public class SysScheduleReportDaoImpl implements SysScheduleReportDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void generateReport(SysScheduleReport sysScheduleReport) {
		hibernateTemplate.save(sysScheduleReport);
	}

	@Override
	public void deleteReport(SysScheduleReport sysScheduleReport) {
		hibernateTemplate.delete(sysScheduleReport);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysScheduleReport> listReportByProjectId(long projectId) {
		String hql = "from SysScheduleReport  where projectId = ?";
		List<SysScheduleReport> list = (List<SysScheduleReport>) hibernateTemplate.find(hql, projectId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysScheduleReport> listReportList(long projectId, long templateId) {
		String hql = "from SysScheduleReport  where projectId = ? and templateId=? order by reportDate DESC";
		List<SysScheduleReport> list = (List<SysScheduleReport>) hibernateTemplate.find(hql, projectId, templateId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysScheduleReport> pageListByTemplateId(long templateId, int pageIndex, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysScheduleReport.class);		
		criteria.add(Property.forName("templateId").eq(templateId));
		int offset = (pageIndex-1)*pageSize;
		List<SysScheduleReport> list = (List<SysScheduleReport>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
		return list;
	}

	@Override
	public int pageCountByTemplateId(long templateId) {
		String sql = "select count(*) from SysScheduleReport where templateId = ?";
		long count = (long) hibernateTemplate.find(sql, templateId).listIterator().next();
		return Integer.valueOf(String.valueOf(count));
	}

}
