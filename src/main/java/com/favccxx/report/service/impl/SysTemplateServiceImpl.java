package com.favccxx.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.dao.SysTemplateDao;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.service.SysTemplateService;

@Service
public class SysTemplateServiceImpl implements SysTemplateService {
	
	@Autowired
	SysTemplateDao sysTemplateDao;

	@Transactional
	@Override
	public void addSysTemplate(SysTemplate sysTemplate) {
		sysTemplateDao.addSysTemplate(sysTemplate);
	}

	@Transactional
	@Override
	public void saveSysTemplate(SysTemplate sysTemplate) {
		sysTemplateDao.saveSysTemplate(sysTemplate);
	}

	@Transactional
	@Override
	public void delSysTemplate(SysTemplate sysTemplate) {
		sysTemplateDao.delSysTemplate(sysTemplate);
	}

	@Transactional
	@Override
	public void delSysTemplate(long id) {
		sysTemplateDao.delSysTemplate(id);
	}

	@Override
	public List<SysTemplate> listTemplates() {
		return sysTemplateDao.listTemplates();
	}

	@Override
	public SysTemplate findbyId(long directoryId) {
		return sysTemplateDao.findbyId(directoryId);
	}

	@Override
	public List<SysTemplate> listByProjectId(long projectId) {
		return sysTemplateDao.listByProjectId(projectId);
	}

	@Transactional
	@Override
	public void findAndUpdate(SysTemplate sysTemplate) {
		SysTemplate template = findbyId(sysTemplate.getId());
		template.setName(sysTemplate.getName());
		saveSysTemplate(template);
	}

	@Override
	public PageInfo<SysTemplate> pageByProjectId(long projectId, int pageIndex, int pageSize, String sortField, boolean ascending) {
		List<SysTemplate> list = sysTemplateDao.pageListByProjectId(projectId, "REPORT", pageIndex, pageSize, sortField, ascending);
		long count = sysTemplateDao.pageCountByProjectId(projectId, "REPORT");
		int totalCount = Integer.valueOf((String.valueOf(count)));
		
		PageInfo<SysTemplate> pageInfo = new PageInfo<SysTemplate>(pageIndex, pageSize, totalCount, list);
		return pageInfo;
	}

	@Override
	public PageInfo<SysTemplate> pageAll(int pageIndex, int pageSize, String sortField, boolean ascending) {
		List<SysTemplate> list = sysTemplateDao.pageAll("REPORT", pageIndex, pageSize, sortField, ascending);
		int totalCount = sysTemplateDao.countAll("REPORT");
		PageInfo<SysTemplate> pageInfo = new PageInfo<SysTemplate>(pageIndex, pageSize, totalCount, list);
		return pageInfo;
	}

	@Override
	public PageInfo<SysTemplate> pageByProjects(List<Long> projectIds, int pageIndex, int pageSize, String sortField,
			boolean ascending) {
		List<SysTemplate> list = sysTemplateDao.pageByProjectIds(projectIds, "REPORT", pageIndex, pageSize, sortField, ascending);
		int totalCount = sysTemplateDao.countByProjects(projectIds, "REPORT");
		PageInfo<SysTemplate> pageInfo = new PageInfo<SysTemplate>(pageIndex, pageSize, totalCount, list);
		return pageInfo;
	}
	
}
