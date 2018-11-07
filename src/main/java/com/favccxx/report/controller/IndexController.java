package com.favccxx.report.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysResource;

@Controller	
public class IndexController {
	
	@RequestMapping("/mav")
	public ModelAndView aa() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test");
		return mav;
	}
	
	
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		SessionUser sysUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);
		if(sysUser!=null) {
			mav.addObject("reportUser", sysUser);
			List<SysResource> resourceList = sysUser.getUserResourceList();
			mav.addObject(SysConstants.USER_RESOURCE_KEY, resourceList);
		}
		mav.setViewName("/index");
		return mav;
	}

}
