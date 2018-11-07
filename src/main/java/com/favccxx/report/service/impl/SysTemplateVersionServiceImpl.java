package com.favccxx.report.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.dao.SysTemplateDataSetDao;
import com.favccxx.report.dao.SysTemplateVersionDao;
import com.favccxx.report.dao.SysTemplateVersionLogDao;
import com.favccxx.report.model.SysTemplateDataSet;
import com.favccxx.report.model.SysTemplateVersion;
import com.favccxx.report.model.SysTemplateVersionLog;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.service.SysTemplateVersionService;
import com.favccxx.report.util.PropertyUtil;

@Service
public class SysTemplateVersionServiceImpl implements SysTemplateVersionService {
	
	@Autowired
	SysTemplateVersionDao sysTemplateVersionDao;
	@Autowired
	SysTemplateDataSetDao sysTemplateDataSetDao;
	@Autowired
	SysTemplateVersionLogDao versionLogDao;

	@Transactional
	@Override
	public void saveTemplateEdition(SysTemplateVersion sysTemplateVersion) {
		sysTemplateVersionDao.saveTemplateEdition(sysTemplateVersion);
	}

	@Transactional
	@Override
	public void createDraftEdition(SysTemplateVersion sysTemplateVersion) {
		//删除草稿版本对应的数据集
		sysTemplateDataSetDao.deleteByTemplateVersionId(sysTemplateVersion.getId());
		sysTemplateVersionDao.deleteEditionByVersion(sysTemplateVersion.getTemplateId(), sysTemplateVersion.getVersion());		
		
		sysTemplateVersionDao.saveTemplateEdition(sysTemplateVersion);
	}

	@Override
	public SysTemplateVersion findById(long id) {
		return sysTemplateVersionDao.findById(id);
	}

	@Override
	public List<SysTemplateVersion> listByTemplateId(long templateId) {
		return sysTemplateVersionDao.listByTemplateId(templateId);
	}

	@Transactional
	@Override
	public void deleteTemplateVersion(SysTemplateVersion sysTemplateVersion) {
		List<SysTemplateDataSet> list = sysTemplateDataSetDao.listDataSetsByVersionId(sysTemplateVersion.getId());
		if(list!=null && list.size()>0) {
			//删除数据集文件
			for(SysTemplateDataSet dataSet : list) {
				if(dataSet.getTempFile()!=null) {
					String jsonDataFilePath = PropertyUtil.getProperty("jsonDataFilePath");
					String filePath = jsonDataFilePath + File.separator + dataSet.getTempFile();
					File file = new File(filePath);
					if(file.exists()) {
						file.delete();
					}
				}
				
			}
			
			//删除数据集记录
			sysTemplateDataSetDao.deleteByTemplateVersionId(sysTemplateVersion.getId());
			
			//删除模板版本文件
			if(sysTemplateVersion.getTemplateFileName()!=null) {
				
				String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();  
				String versionPath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "WEB-INF" + File.separator + "reportFiles" + File.separator + sysTemplateVersion.getTemplateFileName();				
				File file = new File(versionPath);
				if(file.exists()) {
					file.delete();
				}
				
				//删除对应的数据库记录
				sysTemplateVersionDao.deleteTemplateVersion(sysTemplateVersion);
			}
			
			
		}
		
		
		
		
		
	}

	@Override
	public PageInfo<SysTemplateVersion> pageByTemplateId(long templateId, int pageIndex, int pageSize) {
		List<SysTemplateVersion> list = sysTemplateVersionDao.pageListByTemplateId(templateId, pageIndex, pageSize);
		int totalCount = Integer.valueOf(String.valueOf(sysTemplateVersionDao.pageCountByTemplateId(templateId)));
		PageInfo<SysTemplateVersion> pageInfo = new PageInfo<SysTemplateVersion>(pageIndex, pageSize, totalCount, list);
		return pageInfo;
	}

	@Transactional
	@Override
	public void saveVersionLog(SysTemplateVersionLog versionLog) {
		versionLogDao.saveVersionLog(versionLog);
	}

	@Override
	public List<SysTemplateVersionLog> listByTemplateVersionId(long templateVersionId) {
		return versionLogDao.listByTemplateVersionId(templateVersionId);
	}

}
