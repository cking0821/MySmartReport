package com.favccxx.report.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 获取Session
		HttpSession session = request.getSession();
		SessionUser sessionUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);

		if (sessionUser != null) {
			return true;
		}
		
		
		String url = request.getRequestURI();
		if (url.indexOf("login") >= 0 || url.indexOf("api") >= 0 ||  url.indexOf("iframe") >= 0 ) {
			return true;
		}

		//response.sendRedirect("login");
		
		// 不符合条件的，跳转到登录界面
		request.getRequestDispatcher("/login").forward(request, response);

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
