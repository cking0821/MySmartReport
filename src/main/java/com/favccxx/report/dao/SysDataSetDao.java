package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysDataSet;

public interface SysDataSetDao {
	/**
	 * 添加数据集
	 * @param dataSet
	 */
	void addSysDataSet(SysDataSet dataSet);
	
	/**
	 * 新增或更新数据集
	 * @param dataSet
	 */
	void saveSysDataSet(SysDataSet dataSet);

	/**
	 * 更新数据集
	 * @param dataSet
	 */
	void updateSysDataSet(SysDataSet dataSet);

	/**
	 * 删除数据集
	 * @param dataSet
	 */
	void delSysDataSet(SysDataSet dataSet);
	
	/**
	 * 查询数据集
	 * @param id
	 * @return
	 */
	SysDataSet findbyId(long id);
	
	/**
	 * 根据项目查询数据集
	 * @param projectId 项目Id
	 * @return
	 */
	List<SysDataSet> listByProjectId(long projectId);

}
