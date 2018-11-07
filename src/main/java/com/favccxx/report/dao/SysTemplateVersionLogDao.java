package com.favccxx.report.dao;

import java.util.List;

import com.favccxx.report.model.SysTemplateVersionLog;

public interface SysTemplateVersionLogDao {
	
	/**
	 * 保存版本编辑记录
	 * @param versionLog
	 */
	void saveVersionLog(SysTemplateVersionLog versionLog);
	
	/**
	 * 根据版本id查询编辑详情
	 * @param templateVersionId
	 * @return
	 */
	List<SysTemplateVersionLog> listByTemplateVersionId(long templateVersionId);

}
