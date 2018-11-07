package com.favccxx.report.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.favccxx.report.constants.ImageConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.dao.SysDataSetDao;
import com.favccxx.report.dao.SysDataSourceDao;
import com.favccxx.report.dao.SysProjectDao;
import com.favccxx.report.dao.SysProjectLinkUserGroupDao;
import com.favccxx.report.dao.SysTemplateDao;
import com.favccxx.report.dao.SysUserDao;
import com.favccxx.report.dao.SysUserProjectDao;
import com.favccxx.report.model.SysDataSource;
import com.favccxx.report.model.SysProject;
import com.favccxx.report.model.SysProjectLinkUserGroup;
import com.favccxx.report.model.SysTemplate;
import com.favccxx.report.service.SysProjectService;

@Service
public class SysProjectServiceImpl implements SysProjectService {
	
	@Autowired
	SysProjectDao sysProjectDao;
	@Autowired
	SysUserDao sysUserDao;
	@Autowired
	SysTemplateDao sysTemplateDao;
	@Autowired
	SysDataSourceDao sysDataSourceDao;
	@Autowired
	SysDataSetDao sysDataSetDao;
	@Autowired
	SysUserProjectDao sysUserProjectDao;
	@Autowired
	SysProjectLinkUserGroupDao sysProjectLinkUserGroupDao;


	@Transactional
	@Override
	public void addSysProject(SysProject sysProject) {
		sysProjectDao.addSysProject(sysProject);
	}

	@Transactional
	@Override
	public void updateSysProject(SysProject sysProject) {
		sysProjectDao.updateSysProject(sysProject);
	}

	@Transactional
	@Override
	public void deleteSysProject(long projectId) {
		sysProjectDao.deleteSysProject(projectId);
	}

	@Override
	public SysProject findbyId(long projectId) {
		return sysProjectDao.findbyId(projectId);
	}

	@Override
	@Transactional
	public void saveBizProject(SysProject sysProject) {
		if(sysProject.getId()==null || sysProject.getId()==0) {
			sysProjectDao.saveSysProject(sysProject);
			
			//项目的用户组
			sysProjectLinkUserGroupDao.deleteByProjectId(sysProject.getId());
			
			if(sysProject.getProjectUser()!=null && !StringUtils.isBlank(sysProject.getProjectUser())) {								
				String[] groupIds = sysProject.getProjectUser().split(",");
				for(int i=0; i<groupIds.length; i++) {
					SysProjectLinkUserGroup plug = new SysProjectLinkUserGroup();
					plug.setProjectId(sysProject.getId());
					plug.setUserGroupId(Long.valueOf(groupIds[i]));
					plug.setUpdateUserId(sysProject.getUpdateUserId());
					plug.setUpdateTime(new Date());
					sysProjectLinkUserGroupDao.save(plug);					
				}
			}			
			
			SysTemplate sysTemplate = new SysTemplate();
			sysTemplate.setName(sysProject.getProjectName());
			sysTemplate.setProjectId(sysProject.getId());
			sysTemplate.setIconSkin(ImageConstants.ICON_HOME);
			sysTemplate.setType(SysConstants.FILE_TYPE_FOLDER);
			sysTemplate.setParentId(0);
			sysTemplate.setOpen("true");
			sysTemplate.setCreateTime(new Date());
			sysTemplate.setCreateUserId(sysProject.getUpdateUserId());
			sysTemplate.setUpdateTime(new Date());
			sysTemplate.setUpdateUserId(sysProject.getUpdateUserId());
			sysTemplateDao.addSysTemplate(sysTemplate);
			
			SysDataSource dataSource = new SysDataSource();
			dataSource.setName(sysProject.getProjectName());
			dataSource.setParentId(0);
			dataSource.setProjectId(sysProject.getId());
			dataSource.setType(SysConstants.FILE_TYPE_FOLDER);
			dataSource.setIconSkin(ImageConstants.ICON_HOME);
			dataSource.setCreateUserId(sysProject.getUpdateUserId());
			dataSource.setCreateTime(new Date());
			dataSource.setUpdateUserId(sysProject.getUpdateUserId());
			dataSource.setUpdateTime(new Date());
			sysDataSourceDao.addSysDataSource(dataSource);
			
//			SysDataSet dataSet = new SysDataSet();
//			dataSet.setName(sysProject.getProjectName());
//			dataSet.setParentId(0);
//			dataSet.setProjectId(sysProject.getId());
//			dataSet.setType(SysConstants.FILE_TYPE_FOLDER);
//			dataSet.setIconSkin(ImageConstants.ICON_HOME);
//			dataSet.setCreateUserId(sysProject.getUpdateUserId());
//			dataSet.setCreateTime(new Date());
//			dataSet.setUpdateUserId(sysProject.getUpdateUserId());
//			dataSet.setUpdateTime(new Date());
//			sysDataSetDao.saveSysDataSet(dataSet);
			
		}else {
			long projectId = sysProject.getId();
			sysProjectDao.saveSysProject(sysProject);
		
			//项目参与用户
			sysProjectLinkUserGroupDao.deleteByProjectId(sysProject.getId());
			if(sysProject.getProjectUser()!=null && !StringUtils.isBlank(sysProject.getProjectUser())) {
				
				String[] groupIds = sysProject.getProjectUser().split(",");
				for(int i=0; i<groupIds.length; i++) {
					SysProjectLinkUserGroup plug = new SysProjectLinkUserGroup();
					plug.setProjectId(sysProject.getId());
					plug.setUserGroupId(Long.valueOf(groupIds[i]));
					plug.setUpdateUserId(sysProject.getUpdateUserId());
					plug.setUpdateTime(new Date());
					sysProjectLinkUserGroupDao.save(plug);					
				}				
			}
			
			//更新数据源/数据集
			SysTemplate rootTemplate = sysTemplateDao.findRootNodeByProjectId(projectId);
			rootTemplate.setUpdateUserId(sysProject.getUpdateUserId());
			rootTemplate.setUpdateTime(new Date());
			rootTemplate.setName(sysProject.getProjectName());
			sysTemplateDao.saveSysTemplate(rootTemplate);
			
			
			SysDataSource rootSource = sysDataSourceDao.findRootNodeByProjectId(projectId);
			rootSource.setUpdateUserId(sysProject.getUpdateUserId());
			rootSource.setUpdateTime(new Date());
			rootSource.setName(sysProject.getProjectName());
			sysDataSourceDao.saveSysDataSource(rootSource);
		}
		
	}


	@Override
	public List<SysProject> listAllProjects() {
		return sysProjectDao.listAllProjects();
	}

	@Override
	public List<SysProject> listAllValidProjects() {
		return sysProjectDao.listAllValidProjects();
	}

	@Override
	public List<SysProject> listProjectsByUserId(long userId) {
		return sysProjectDao.listProjectsByUserId(userId);
	}

	@Override
	public List<SysProject> listValidProjectsByUserId(long userId) {
		return sysProjectDao.listValidProjectsByUserId(userId);
	}

	@Override
	public List<SysProject> listProjects(long userGroupId, boolean allStatus) {
		return sysProjectDao.listProjects(userGroupId, allStatus);
	}

	@Override
	public List<SysProject> listByGroupIds(List<Long> groupIds, boolean allStatus) {
		return sysProjectDao.listByGroupIds(groupIds, allStatus);
	}

	@Override
	public List<SysProject> listByUserIdOrGroupId(long userId, List<Long> groupIds, boolean allStatus) {
		return sysProjectDao.listByUserIdOrGroupId(userId, groupIds, allStatus);
	}

	

}
