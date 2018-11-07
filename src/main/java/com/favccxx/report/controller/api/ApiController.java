package com.favccxx.report.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApiController {
	
	@RequestMapping("/initApiView")
	public ModelAndView initApiView(HttpSession session) {		
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("/api/api");
		
		return mav;
	}

}
