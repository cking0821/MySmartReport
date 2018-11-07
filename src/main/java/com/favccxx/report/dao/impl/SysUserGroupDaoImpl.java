package com.favccxx.report.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysUserGroupDao;
import com.favccxx.report.model.SysUserGroup;

@Repository
public class SysUserGroupDaoImpl implements SysUserGroupDao {
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void saveOrUpdateUserGroup(SysUserGroup sysUserGroup) {
		hibernateTemplate.saveOrUpdate(sysUserGroup);
	}

	@Override
	public void deleteUserGroup(long id) {
		SysUserGroup userGroup = findById(id);
		hibernateTemplate.delete(userGroup);
	}

	@Override
	public SysUserGroup findById(long id) {
		return hibernateTemplate.get(SysUserGroup.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroup> pageList(int pageIndex, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUserGroup.class);
		int offset = (pageIndex-1)*pageSize;
		List<SysUserGroup> list = (List<SysUserGroup>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
		return list;
	}

	@Override
	public int pageCount() {
		String hql = "select id(*) from SysUserGroup";
		long count = (long) hibernateTemplate.find(hql).listIterator().next();
		int totalCount = Integer.valueOf(String.valueOf(count));
		return totalCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroup> findAll() {
		String hql = "from SysUserGroup";
		return (List<SysUserGroup>) hibernateTemplate.find(hql);
	}

}
