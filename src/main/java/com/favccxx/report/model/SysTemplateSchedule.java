package com.favccxx.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_TEMPLATE_SCHEDULE")
public class SysTemplateSchedule {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 报表模板Id
	 */
	@Column(name = "TEMPLATE_ID")
	private long templateId;
	
	/**
	 * 定时任务标题
	 */
	@Column(name = "SCHEDULE_TITLE")
	private String scheduleTitle;
	
	/**
	 * 导出文档类型
	 * PDF/EXCEL/WORD
	 */
	@Column(name="EXPORT_TYPE")
	private String exportType;
	
	/**
	 * 任务执行频率
	 * 每天/每周/每月/每年
	 */
	@Column(name = "SCHEDULE_FREQUENCY")
	private String scheduleFrequency;
	
	/**
	 * 任务执行日期
	 * 周一/周二/周三...周日
	 * 1号/2号/...31号
	 */
	@Column(name="SCHEDULE_FREQUENCY_DETAIL")
	private String scheduleFrequencyDetail;
	
	/**
	 * 任务执行时间
	 */
	@Column(name = "SCHDULE_TIME")
	private String schduleTime;

	/**
	 * 任务生效开始日期
	 */
	@Column(name = "START_TIME")
	private String startTime;
	
	/**
	 * 任务生效截止日期
	 */
	@Column(name = "END_TIME")
	private String endTime;
	
	/**
	 * 任务状态
	 * OPEN 进行中
	 * CLOSED 关闭
	 */
	@Column(name="SCHEDULE_STATUS")
	private String scheduleStatus;
	
	/**
	 * 接收用户，填写邮箱，多个用户用英文逗号分开
	 */
	@Column(name = "RECEIVE_USERS")
	private String receiveUsers;
	
	/**
	 * 下次定时任务触发时间
	 */
	@Column(name="NEXT_SCHEDULE_TIME")
	private Date nextScheduleTime;
	
	/**
	 * 备注
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	
	/**
	 * 最后更新用户
	 */
	@Column(name = "UPDATE_USER_ID")
	private long updateUserId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getScheduleTitle() {
		return scheduleTitle;
	}

	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getScheduleFrequency() {
		return scheduleFrequency;
	}

	public void setScheduleFrequency(String scheduleFrequency) {
		this.scheduleFrequency = scheduleFrequency;
	}

	public String getSchduleTime() {
		return schduleTime;
	}

	public void setSchduleTime(String schduleTime) {
		this.schduleTime = schduleTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public String getReceiveUsers() {
		return receiveUsers;
	}

	public void setReceiveUsers(String receiveUsers) {
		this.receiveUsers = receiveUsers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getScheduleFrequencyDetail() {
		return scheduleFrequencyDetail;
	}

	public void setScheduleFrequencyDetail(String scheduleFrequencyDetail) {
		this.scheduleFrequencyDetail = scheduleFrequencyDetail;
	}

	public Date getNextScheduleTime() {
		return nextScheduleTime;
	}

	public void setNextScheduleTime(Date nextScheduleTime) {
		this.nextScheduleTime = nextScheduleTime;
	}
	
	
	
	
	
}
