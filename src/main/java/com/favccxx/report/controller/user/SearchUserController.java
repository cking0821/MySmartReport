package com.favccxx.report.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.model.SysUser;
import com.favccxx.report.service.SysUserService;

@Controller
public class SearchUserController {

	@Autowired
	SysUserService sysUserService;
	
	@RequestMapping("/initSearchUser")
	public ModelAndView initSearchUser() {
		ModelAndView mav = new ModelAndView();

		List<SysUser> list = sysUserService.listUsers();
		mav.addObject("users", list);
		mav.setViewName("/user/search_user");

		return mav;
	}
}
