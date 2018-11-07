package com.favccxx.report.controller.scheduler;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysTemplateSchedule;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysTemplateScheduleService;
import com.favccxx.report.util.LogUtil;

@Controller
public class TemplateScheduleController {
	
	@Autowired
	SysTemplateScheduleService sysTemplateScheduleService;
	
	@RequestMapping("/initTemplateScheduleList")
	public ModelAndView initTemplateScheduleList(HttpSession session, long templateId) {
		ModelAndView mav = new ModelAndView();		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询模板定时任务", LogConstants.OPERATE_QUERY, LogConstants.MODULE_TEMPLATE);
		if(templateId!=0) {
			List<SysTemplateSchedule> list = sysTemplateScheduleService.listByTemplateId(templateId);
			mav.addObject("list", list);
		}
		mav.addObject("templateId", templateId);	
		mav.setViewName("/schedule/template_schedule_list");
		return mav;
	}
	
	
	@RequestMapping("/initTemplateScheduleEdit")
	public ModelAndView initTemplateScheduleEdit(HttpSession session, long id, long templateId) {
		ModelAndView mav = new ModelAndView();
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "编辑模板定时任务", LogConstants.OPERATE_QUERY, LogConstants.MODULE_TEMPLATE);
		SysTemplateSchedule sysTemplateSchedule = new SysTemplateSchedule();
		if(id != 0) {
			sysTemplateSchedule = sysTemplateScheduleService.findById(id);			
		}
		
		mav.addObject("sysTemplateSchedule", sysTemplateSchedule);
		mav.addObject("templateId", templateId);	
		mav.setViewName("/schedule/template_schedule_edit");
		
		return mav;
	}
	
	/**
	 * 保存模板的定时任务
	 * @author chenxu
	 * @date Jan 15, 2018
	 * @param sysTemplateSchedule
	 * @return
	 */
	@RequestMapping("/saveTemplateSchedule")
	@ResponseBody
	public String saveTemplateSchedule(HttpSession session, @ModelAttribute("sysTemplateSchedule")SysTemplateSchedule sysTemplateSchedule) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存模板定时任务", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_TEMPLATE);
		if(sysTemplateSchedule.getSchduleTime()==null) {
			sysTemplateSchedule.setSchduleTime("08:00");
		}
		
		if(sysTemplateSchedule.getId()==0) {
			sysTemplateSchedule.setUpdateUserId(sessionUser.getUserId());
			sysTemplateSchedule.setUpdateTime(new Date());
			sysTemplateScheduleService.saveTemplateSchedule(sysTemplateSchedule);
			return SysConstants.SUCCESS_MSG;
		}
		
		SysTemplateSchedule existSchedule = sysTemplateScheduleService.findById(sysTemplateSchedule.getId());
		existSchedule.setScheduleTitle(sysTemplateSchedule.getScheduleTitle());
		existSchedule.setExportType(sysTemplateSchedule.getExportType());
		existSchedule.setScheduleFrequency(sysTemplateSchedule.getScheduleFrequency());
		existSchedule.setScheduleFrequencyDetail(sysTemplateSchedule.getScheduleFrequencyDetail());
		existSchedule.setSchduleTime(sysTemplateSchedule.getSchduleTime());
		existSchedule.setStartTime(sysTemplateSchedule.getStartTime());
		existSchedule.setEndTime(sysTemplateSchedule.getEndTime());
		existSchedule.setReceiveUsers(sysTemplateSchedule.getReceiveUsers());
		existSchedule.setScheduleStatus(sysTemplateSchedule.getScheduleStatus());
		existSchedule.setUpdateUserId(sessionUser.getUserId());
		existSchedule.setUpdateTime(new Date());
		sysTemplateScheduleService.saveTemplateSchedule(existSchedule);
		
		return SysConstants.SUCCESS_MSG;
	}
	
	//删除模板定时任务
	@RequestMapping(value="/delTemplateSchedule", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String delTemplateSchedule(HttpSession session, @RequestBody String scheduleId) {	
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "删除模板定时任务", LogConstants.OPERATE_DELETE, LogConstants.MODULE_TEMPLATE);
		sysTemplateScheduleService.deleteTemplateSchedule(Long.parseLong(scheduleId));
		return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
	}

}
