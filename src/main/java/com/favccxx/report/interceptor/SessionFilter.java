package com.favccxx.report.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.favccxx.report.constants.ReportConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.model.SysUserGroup;
import com.favccxx.report.service.impl.SysUserGroupServiceImpl;
import com.favccxx.report.service.impl.SysUserServiceImpl;

public class SessionFilter implements Filter {
	
//	@Autowired
//	SysUserService sysUserService;
//	@Autowired
//	SysUserGroupService sysUserGroupService;

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		// 排除登录和加盐请求
		if (request.getRequestURI().contains("login") || request.getRequestURI().contains("randomSalt")
				|| request.getRequestURI().contains("assets") || request.getRequestURI().contains("api")
				|| request.getRequestURI().contains("iframe")) {
			
			
			
			chain.doFilter(req, res);
			return;
		}
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute(SysConstants.SESSION_USER_ID) != null) {	
			String userName = (String) session.getAttribute(SysConstants.SESSION_USER_ID);
			
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
			SysUserServiceImpl sysUserService = ctx.getBean(SysUserServiceImpl.class);
			SysUserGroupServiceImpl sysUserGroupService = ctx.getBean(SysUserGroupServiceImpl.class);
			
			if(StringUtils.isNoneBlank(userName)) {
				
			}
						
			if (session.getAttribute(SysConstants.USER_SESSION_KEY) == null) {		
				SysUser sysUser = sysUserService.findByUsername(userName);
				
				SessionUser sessionUser = new SessionUser();
				
				sessionUser.setNickName(sysUser.getNickName());
				sessionUser.setOrgId("");
				sessionUser.setUserId(sysUser.getId());
				sessionUser.setUserMail(sysUser.getUserMail());
				sessionUser.setUserName(sysUser.getUserName());
				sessionUser.setUserStatus(sysUser.getUserStatus());
				sessionUser.setUserTel(sysUser.getUserTel());
				
				//查询用户可访问资源
				List<SysResource> resourceList = sysUserService.listUserResourcesByUserId(sysUser.getId());
				sessionUser.setUserResourceList(resourceList);
				
				//查询用户组Ids
				List<Long> groupIdList = sysUserGroupService.ListGroupIdsByUserId(sysUser.getId());
				for(long groupId : groupIdList) {
					SysUserGroup userGroup = sysUserGroupService.findById(groupId);
					if(ReportConstants.REPORT_EDIT_GROUP_CODE.equals(userGroup.getGroupCode())) {
						session.setAttribute(ReportConstants.REPORT_EDIT_GROUP_FLAG, "true");
					}
				}
				sessionUser.setGroupIdList(groupIdList);
				
				session.setAttribute(SysConstants.USER_SESSION_KEY, sessionUser);	
			}
			chain.doFilter(req, res);
			return;
		}
		
		
//		if (session.getAttribute(SysConstants.USER_SESSION_KEY) == null) {			
//			HttpServletResponse response = (HttpServletResponse) res;
//			response.sendRedirect("login");
//			return;
//		}
		chain.doFilter(req, res);
		
//		HttpSession session = request.getSession();
//		if (session.getAttribute(SysConstants.SESSION_USER_ID) != null) {	
//			String userName = (String) session.getAttribute(SysConstants.SESSION_USER_ID);
//			//获取SessionUser
//			if (session.getAttribute(SysConstants.USER_SESSION_KEY) == null) {	
//				HttpServletResponse response = (HttpServletResponse) res;
//				response.sendRedirect("reportSSO");
//				session.setAttribute("redirectU", request.getRequestURI());
//				response.setHeader("redirectU", request.getRequestURI());
//				
//				
////				chain.doFilter(req, res);
//				return;
//			}
//			
//			System.out.println("-------------------" + userName);
//		}
		
		
		HttpServletResponse response = (HttpServletResponse) res;
		response.sendRedirect("login");
		return;

	}

	@Override
	public void destroy() {
		
	}

}
