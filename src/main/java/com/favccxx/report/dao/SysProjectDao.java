package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysProject;

public interface SysProjectDao {
	
	/**
	 * 新增项目
	 * @param sysProject
	 */
	void addSysProject(SysProject sysProject);
	
	/**
	 * 新增或保存项目
	 * @param sysProject
	 */
	void saveSysProject(SysProject sysProject);
	
	/**
	 * 删除项目
	 * @param projectId 项目Id
	 */
	void deleteSysProject(long projectId);
	
	void updateSysProject(SysProject sysProject);
	
	
	
	/**
	 * 根据项目的属性查询符合条件的项目列表
	 * @param sysProject
	 * @return
	 */
	List<SysProject> listProjects(SysProject sysProject);
	
	
	SysProject findbyId(long projectId);
	
	
	/**
	 * 管理员根据项目名称查询项目列表
	 * @return
	 */
	List<SysProject> listAllProjects();
	
	/**
	 * 管理员根据项目名称查询项目列表
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
	 * 根据用户组Ids查询可访问的项目列表
	 * @param groupIds
	 * @param allStatus
	 * @return
	 */
	List<SysProject> listByGroupIds(List<Long> groupIds, boolean allStatus);
	
	/**
	 * 根据项目的创建人或所属用户组查询项目列表
	 * @param userId 创建人Id
	 * @param groupIds 用户组Ids
	 * @param allStatus
	 * @return
	 */
	List<SysProject> listByUserIdOrGroupId(long userId,List<Long> groupIds, boolean allStatus);

}
