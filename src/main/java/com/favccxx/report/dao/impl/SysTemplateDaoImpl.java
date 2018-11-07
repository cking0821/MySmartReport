package com.favccxx.report.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysTemplateDao;
import com.favccxx.report.model.SysTemplate;

@Repository
public class SysTemplateDaoImpl implements SysTemplateDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void addSysTemplate(SysTemplate sysTemplate) {
		hibernateTemplate.save(sysTemplate);
	}

	@Override
	public void saveSysTemplate(SysTemplate sysTemplate) {
		hibernateTemplate.saveOrUpdate(sysTemplate);
	}

	@Override
	public void delSysTemplate(SysTemplate sysTemplate) {
		hibernateTemplate.delete(sysTemplate);
	}

	@Override
	public void delSysTemplate(long id) {
		hibernateTemplate.delete(findbyId(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplate> listTemplates() {
		List<SysTemplate> list = (List<SysTemplate>) hibernateTemplate.find("from SysTemplate");
		return list;
	}

	@Override
	public SysTemplate findbyId(long directoryId) {
		return hibernateTemplate.get(SysTemplate.class, directoryId);
	}

	@Override
	public List<SysTemplate> listByProjectId(long projectId) {
		String hql = "from SysTemplate where projectId = ?";
		@SuppressWarnings("unchecked")
		List<SysTemplate> list = (List<SysTemplate>) hibernateTemplate.find(hql, projectId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplate> listByProjectId(long projectId, int offset, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTemplate.class);
		criteria.add(Property.forName("projectId").eq(projectId)).add(Property.forName("type").eq("REPORT"));
		List<SysTemplate> list = (List<SysTemplate>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
		return list;
	}

	@Override
	public long countByProjectId(long projectId) {
		String hql = "select id(*) from SysTemplate where type='REPORT' ";
		return (long) hibernateTemplate.find(hql).listIterator().next();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplate> pageListByProjectId(long projectId, String templateType, int pageIndex, int pageSize,
			String sortField, boolean ascending) {
		// StringBuffer sb = new StringBuffer();
		// sb.append("from SysTemplate where projectId = ?");
		// if(!StringUtils.isBlank(templateType)) {
		// sb.append(" and type=?");
		// }
		//
		// if(!StringUtils.isBlank(sortField)) {
		// sb.append(" order by ").append(sortField).append(" ").append(direction);
		// }
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTemplate.class);
		criteria.add(Property.forName("projectId").eq(projectId));
		if (!StringUtils.isBlank(templateType)) {
			criteria.add(Property.forName("type").eq(templateType));
		}

		if (!StringUtils.isBlank(sortField)) {
			Order order = Order.asc(sortField);
			if (!ascending) {
				order = Order.desc(sortField);
			}
			criteria.addOrder(order);
		}

		int offset = (pageIndex - 1) * pageSize;
		List<SysTemplate> list = (List<SysTemplate>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);

		return list;
	}

	@Override
	public long pageCountByProjectId(long projectId, String templateType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from SysTemplate where projectId = ?");

		if (!StringUtils.isBlank(templateType)) {
			sb.append(" and type=?");
		}

		if (StringUtils.isBlank(templateType)) {
			return (long) hibernateTemplate.find(sb.toString(), projectId).listIterator().next();
		} else {
			return (long) hibernateTemplate.find(sb.toString(), projectId, templateType).listIterator().next();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplate> pageAll(String templateType, int pageIndex, int pageSize, String sortField,
			boolean ascending) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTemplate.class);

		if (!StringUtils.isBlank(templateType)) {
			criteria.add(Property.forName("type").eq(templateType));
		}

		if (!StringUtils.isBlank(sortField)) {
			Order order = Order.asc(sortField);
			if (!ascending) {
				order = Order.desc(sortField);
			}
			criteria.addOrder(order);
		}

		int offset = (pageIndex - 1) * pageSize;
		List<SysTemplate> list = (List<SysTemplate>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
		return list;
	}

	@Override
	public int countAll(String templateType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTemplate.class);

		if (!StringUtils.isBlank(templateType)) {
			criteria.add(Property.forName("type").eq(templateType));
		}

		return hibernateTemplate.findByCriteria(criteria).size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplate> pageByProjectIds(List<Long> projectIds, String templateType, int pageIndex, int pageSize,
			String sortField, boolean ascending) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTemplate.class);

		if (!StringUtils.isBlank(templateType)) {
			criteria.add(Property.forName("type").eq(templateType));
		}
		
		criteria.add(Property.forName("projectId").in(projectIds));

		if (!StringUtils.isBlank(sortField)) {
			Order order = Order.asc(sortField);
			if (!ascending) {
				order = Order.desc(sortField);
			}
			criteria.addOrder(order);
		}

		int offset = (pageIndex - 1) * pageSize;
		List<SysTemplate> list = (List<SysTemplate>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
		return list;
	}

	@Override
	public int countByProjects(List<Long> projectIds, String templateType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysTemplate.class);

		if (!StringUtils.isBlank(templateType)) {
			criteria.add(Property.forName("type").eq(templateType));
		}
		
		criteria.add(Property.forName("projectId").in(projectIds));
		
		criteria.setProjection(Projections.rowCount());
		
		return hibernateTemplate.findByCriteria(criteria).size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysTemplate findRootNodeByProjectId(long projectId) {
		String hql = "from SysTemplate where parentId=0 and projectId = ?";
		List<SysTemplate> list = (List<SysTemplate>) hibernateTemplate.find(hql, projectId);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
