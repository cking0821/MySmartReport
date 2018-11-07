package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.VUserRoleResources;

public interface VUserRoleResourcesDao {
	
	List<VUserRoleResources> listUserResources(long userId);

}
