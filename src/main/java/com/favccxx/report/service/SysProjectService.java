package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysProject;

public interface SysProjectService {
	
	void addSysProject(SysProject sysProject);

	void updateSysProject(SysProject sysProject);
	
	void deleteSysProject(long projectId);
	
	/**
	 * 查询所有的项目
	 * 仅管理员用
	 * @return
	 */
	List<SysProject> listAllProjects();
	
	/**
	 * 查询所有的可用项目
	 * 状态为正常的项目，仅管理员用
	 * @return
	 */
	List<SysProject> listAllValidProjects();
	
	
	/**
	 * 查询某用户可以访问的所有项目
	 * @param userId
	 * @return
	 */
	List<SysProject> listProjectsByUserId(long userId);
	
	/**
	 * 查询某用户可以访问的正常状态的项目
	 * @param userId
	 * @return
	 */
	List<SysProject> listValidProjectsByUserId(long userId);
	
	/**
	 * 根据用户的用户组查询用户的可访问项目
	 * @param userGroupId 用户组Id
	 * @param allStatus true， 查询所有的项目； false，仅查询正常状态的项目
	 * @return
	 */
	List<SysProject> listProjects(long userGroupId, boolean allStatus);
	
	
	/**
	 * 根据用户组Id列表查询可访问的项目列表
	 * @param groupIds 用户组Ids
	 * @param allStatus
	 * @return
	 */
	List<SysProject> listByGroupIds(List<Long> groupIds, boolean allStatus);

	
	
	SysProject findbyId(long projectId);
	
	/**
	 * 添加新项目
	 * 添加新项目时，会同时创建报表文件根目录和数据源根目录
	 * @param sysProject
	 */
	void saveBizProject(SysProject sysProject);
	
	
	
	
	/**
	 * 根据项目的创建人或所属用户组查询项目列表
	 * @param userId 创建人Id
	 * @param groupIds 用户组Ids
	 * @param allStatus
	 * @return
	 */
	List<SysProject> listByUserIdOrGroupId(long userId,List<Long> groupIds, boolean allStatus);
}
