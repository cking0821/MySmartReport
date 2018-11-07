package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysTemplateDataSet;

public interface SysTemplateDataSetDao {
	
	/**
	 * 删除某个模板的编辑版本的所有数据集
	 * @author chenxu
	 * @date Jan 2, 2018
	 * @param templateId
	 */	
	void deleteByTemplateVersionId(long templateVersionId);
	
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
	 * @param templateVersionId
	 * @return
	 */
	List<SysTemplateDataSet> listDataSetsByVersionId(long templateVersionId);
	
	/**
	 * 根据模板版本Id和数据集Id查找模板版本
	 * @author chenxu
	 * @date Jan 5, 2018
	 * @param templateEditionId
	 * @param dataSetId
	 * @return
	 */
	SysTemplateDataSet getByVersionIdAndDataSetId(long templateVersionId, String dataSetId);
}
