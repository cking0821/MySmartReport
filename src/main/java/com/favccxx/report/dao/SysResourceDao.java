package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysResource;

public interface SysResourceDao {
	
	List<SysResource> listResources();
	
	
	List<SysResource> listUserResourcesByUserId(long userId);

}
