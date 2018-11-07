package com.favccxx.report.dao;

import com.favccxx.report.model.SysUserProject;

public interface SysUserProjectDao {
	/**
	 * 保存用户项目信息
	 * @author chenxu
	 * @date Feb 2, 2018
	 * @param sysUserProject
	 */
	void saveUserProject(SysUserProject sysUserProject);
	
	/**
	 * 删除某项目的用户信息
	 * @author chenxu
	 * @date Feb 2, 2018
	 * @param projectId
	 */
	void deleteByProjectId(long projectId);

}
