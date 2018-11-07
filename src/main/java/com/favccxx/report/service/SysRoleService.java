package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysRole;

public interface SysRoleService {
	
	List<SysRole> listRoles();
	
	List<SysRole> listManageRoles();
	
	/**
	 * 根据角色id查找用户角色信息
	 * @author chenxu
	 * @date Feb 2, 2018
	 * @param id
	 * @return
	 */
	SysRole findById(long id);
	
}
