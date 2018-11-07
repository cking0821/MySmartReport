package com.favccxx.report.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favccxx.report.dao.SysTemplateDataSetDao;
import com.favccxx.report.model.SysTemplateDataSet;
import com.favccxx.report.service.SysTemplateDataSetService;

@Service
public class SysTemplateDataSetServiceImpl implements SysTemplateDataSetService {
	
	@Autowired
	SysTemplateDataSetDao sysTemplateDataSetDao;

	@Transactional
	@Override
	public void saveTemplateDataSet(SysTemplateDataSet templateDataSet) {
		sysTemplateDataSetDao.saveTemplateDataSet(templateDataSet);
	}

	@Transactional
	@Override
	public void saveTemplateDataSets(List<SysTemplateDataSet> list) {
		sysTemplateDataSetDao.saveTemplateDataSets(list);
	}

	@Override
	public List<SysTemplateDataSet> getDataSetsByTemplateId(long templateId) {
		return sysTemplateDataSetDao.listDataSetsByVersionId(templateId);
	}

	@Override
	public SysTemplateDataSet getByVersionIdAndDataSetId(long templateVersionId, String dataSetId) {
		return sysTemplateDataSetDao.getByVersionIdAndDataSetId(templateVersionId, dataSetId);
	}

	

}
