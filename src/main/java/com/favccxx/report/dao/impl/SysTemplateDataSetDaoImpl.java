package com.favccxx.report.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.favccxx.report.dao.SysTemplateDataSetDao;
import com.favccxx.report.model.SysTemplateDataSet;


@Repository
public class SysTemplateDataSetDaoImpl implements SysTemplateDataSetDao {
	

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Override
	public void saveTemplateDataSet(SysTemplateDataSet templateDataSet) {
		hibernateTemplate.saveOrUpdate(templateDataSet);
	}

	@Override
	public void saveTemplateDataSets(List<SysTemplateDataSet> list) {
		for(SysTemplateDataSet dataSet : list) {
			hibernateTemplate.save(dataSet);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteByTemplateVersionId(long templateVersionId) {
		
		hibernateTemplate.execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				String sql = "delete from SYS_TEMPLATE_DATA_SET where TEMPLATE_VERSION_ID=" + templateVersionId;
				NativeQuery query = session.createNativeQuery(sql);
				return query.executeUpdate();
			}
		});
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTemplateDataSet> listDataSetsByVersionId(long templateVersionId) {
		String hql = "from SysTemplateDataSet where templateVersionId = ?";
		List<SysTemplateDataSet> list = (List<SysTemplateDataSet>) hibernateTemplate.find(hql, templateVersionId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysTemplateDataSet getByVersionIdAndDataSetId(long templateVersionId, String dataSetId) {
		String hql = "from SysTemplateDataSet where templateVersionId = ? and dataSetId=?";
		List<SysTemplateDataSet> list = (List<SysTemplateDataSet>) hibernateTemplate.find(hql, templateVersionId, dataSetId);
		if(list==null || list.size()==0) {
			return null;
		}
		return list.get(0);
	}

}



