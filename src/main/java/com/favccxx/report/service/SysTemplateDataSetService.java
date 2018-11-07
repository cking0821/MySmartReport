package com.favccxx.report.service;

import java.util.List;

import com.favccxx.report.model.SysTemplateDataSet;

public interface SysTemplateDataSetService {
	
	/**
	 * 保存模板数据集
	 * @author chenxu
	 * @date Jan 2, 2018
	 * @param templateDataSet
	 */
	void saveTemplateDataSet(SysTemplateDataSet templateDataSet);
	
	/**
	 * 保存模板数据集
	 * @author chenxu
	 * @date Jan 2, 2018
	 * @param list
	 */
	void saveTemplateDataSets(List<SysTemplateDataSet> list);
	
	/**
	 * 根据模板Id查询数据集列表
	 * @author chenxu
	 * @date Jan 3, 2018
	 * @param templateId
	 * @return
	 */
	List<SysTemplateDataSet> getDataSetsByTemplateId(long templateId);
	
	
	/**
	 * 根据模板版本Id和数据集Id查找模板版本
	 * @author chenxu
	 * @date Jan 5, 2018
	 * @param templateVersionId 模板版本Id
	 * @param dataSetId 报表数据集Id
	 * @return
	 */
	SysTemplateDataSet getByVersionIdAndDataSetId(long templateVersionId, String dataSetId);


}
