package com.favccxx.report.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysTemplateVersionDao;
import com.favccxx.report.model.SysTemplateVersion;

@Repository
public class SysTemplateVersionDaoImpl implements SysTemplateVersionDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void saveTemplateEdition(SysTemplateVersion sysTemplateVersion) {
		hibernateTemplate.saveOrUpdate(sysTemplateVersion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteEditionByVersion(long templateId, String version) {
		String hql = "from SysTemplateVersion where templateId = ? and version=?";
		List<SysTemplateVersion> list = (List<SysTemplateVersion>) hibernateTemplate.find(hql, templateId, version);
		if (list != null && list.size() > 0) {
			hibernateTemplate.deleteAll(list);
		}
	}

	@Override
	public SysTemplateVersion findById(long id) {
		return hibernateTemplate.get(SysTemplateVersion.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplateVersion> listByTemplateId(long templateId) {
		String hql = "from SysTemplateVersion where status='NORMAL' and templateId = ? order by updateTime desc";
		return (List<SysTemplateVersion>) hibernateTemplate.find(hql, templateId);
	}

	@Override
	public void deleteTemplateVersion(SysTemplateVersion sysTemplateVersion) {
		hibernateTemplate.delete(sysTemplateVersion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplateVersion> pageListByTemplateId(long templateId, int pageIndex, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTemplateVersion.class);
		
		criteria.add(Property.forName("templateId").eq(templateId));
		int offset = (pageIndex-1)*pageSize;
		List<SysTemplateVersion> list = (List<SysTemplateVersion>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
		return list;
	}

	@Override
	public long pageCountByTemplateId(long templateId) {
		String sql = "select count(*) from SysTemplateVersion where templateId = ?";
		return (long) hibernateTemplate.find(sql, templateId).listIterator().next();
	}

}
