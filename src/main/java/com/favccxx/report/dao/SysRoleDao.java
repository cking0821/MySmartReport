package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysRole;

public interface SysRoleDao {
	/**
	 * 列出系统中的所有用户角色
	 * @author chenxu
	 * @date Feb 2, 2018
	 * @return
	 */
	List<SysRole> listRoles();
	
	/**
	 * 根据角色id查找用户角色信息
	 * @author chenxu
	 * @date Feb 2, 2018
	 * @param id
	 * @return
	 */
	SysRole findById(long id);

}
