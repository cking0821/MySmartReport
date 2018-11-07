package com.favccxx.report.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.exception.AppException;

public abstract class AbstractMavController {

	protected ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

	protected ModelAndView mav = new ModelAndView();

	protected String body;

	protected abstract ModelAndView deal() throws AppException, Exception;
	
	public void getSessionUser() {
		
	}

	@RequestMapping(method = RequestMethod.POST)
	public void handleRequest(@RequestBody String body, HttpServletRequest req, HttpServletResponse response) {
		this.body = body;
        request.set(req);
        try {
			ModelAndView mav = deal();
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}

}
