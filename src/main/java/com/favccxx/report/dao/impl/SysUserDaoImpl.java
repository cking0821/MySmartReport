package com.favccxx.report.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysUserDao;
import com.favccxx.report.model.SysUser;

@Repository
public class SysUserDaoImpl implements SysUserDao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	public void addUser(SysUser user) {
		hibernateTemplate.save(user);
	}
	
	@Override
	public void saveUser(SysUser user) {
		hibernateTemplate.saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	public List<SysUser> listUsers() {
		
		List<SysUser> list = (List<SysUser>) hibernateTemplate.find("from SysUser");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysUser findByUsername(String username) {
		List<SysUser> list;
		String hql = "from SysUser where lower(userName) = lower(?)";
		
		list = (List<SysUser>) hibernateTemplate.find(hql, username);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		
		hql = "from SysUser where userMail = ?";
		list = (List<SysUser>) hibernateTemplate.find(hql, username);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		
		hql = "from SysUser where userTel = ?";
		list = (List<SysUser>) hibernateTemplate.find(hql, username);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
			
		return null;
	}

	@Override
	public SysUser findById(long id) {
		return hibernateTemplate.get(SysUser.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> pageList(String searchTxt, int offset, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);
		if(!StringUtils.isBlank(searchTxt)) {
			String searchText = "%" + searchTxt + "%";
			
			criteria.add(Restrictions.or(Restrictions.like("userName", searchText,MatchMode.ANYWHERE),   
                    Restrictions.like("nickName", searchText,MatchMode.ANYWHERE)));  
		}
		List<SysUser> list = (List<SysUser>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
		return list;
	}

	@Override
	public int pageCount(String searchTxt) {
		int totalCount = 0;
		String sql = "select count(*) from SysUser";
		if(!StringUtils.isBlank(searchTxt)) {
			String searchText = "'%" + searchTxt + "%'";
			sql = sql + " where userName like " + searchText + " or nickName like " + searchText; 
			long count = (long)hibernateTemplate.find(sql).listIterator().next();
			totalCount = Integer.valueOf(String.valueOf(count));	
			return totalCount;
		}
		
		
		long count = (long)hibernateTemplate.find(sql).listIterator().next();
		totalCount = Integer.valueOf(String.valueOf(count));	
		return totalCount;
	}

	

	

}
