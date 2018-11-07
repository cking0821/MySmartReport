package com.favccxx.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.dao.SysTemplateDataDao;
import com.favccxx.report.model.SysTemplateData;
import com.favccxx.report.service.SysTemplateDataService;

@Service
public class SysTemplateDataServiceImpl implements SysTemplateDataService {
	
	@Autowired
	SysTemplateDataDao sysTemplateDataDao;

	@Transactional
	@Override
	public void saveTemplateData(SysTemplateData templateData) {
		sysTemplateDataDao.saveTemplateData(templateData);
	}

}
