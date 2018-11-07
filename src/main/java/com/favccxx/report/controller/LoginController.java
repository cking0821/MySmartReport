package com.favccxx.report.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.favccxx.report.constants.ReportConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.model.SysUserGroup;
import com.favccxx.report.service.SysRoleService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.service.SysUserService;
import com.favccxx.report.util.EncryptDesUtil;
import com.favccxx.report.util.EncryptUtil;
import com.favccxx.report.util.SaltUtil;

@Controller
public class LoginController {
	
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	@Autowired
	SysRoleService sysRoleService;
	
	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, String userName, String password) throws Exception{
		ModelAndView mav = new ModelAndView();
		if(userName==null && password==null) {
			mav.setViewName("/login");
			return mav;
		}
		
//		SessionContext.setSessionUser(null);
		
		SysUser sysUser = sysUserService.findByUsername(userName);
		if(sysUser==null){
			mav.addObject("errorMsg", "用户名或密码错误！");
			mav.setViewName("/login");
			return mav;
		}
		
		String salt = (String) session.getAttribute(SysConstants.USER_SALT_KEY);		
		String originPass = EncryptDesUtil.decryption(password, salt);
		
		String loginPwd = EncryptUtil.encrypt(EncryptUtil.HASH_METHOD_SHA_256,(EncryptUtil.encrypt(EncryptUtil.HASH_METHOD_SHA_256, originPass) + sysUser.getSalt()));
		if(sysUser.getUserPwd().equals(loginPwd)){
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
			session.setAttribute(SysConstants.SESSION_USER_ID, sysUser.getUserName());
//			SessionContext.setSessionUser(sessionUser);
			
			
			//用户权限
//			List<VUserRoleResources> list = sysUserService.listUserResources(sysUser.getId());
			mav.addObject(SysConstants.USER_RESOURCE_KEY, resourceList);
			session.setAttribute(SysConstants.USER_RESOURCE_KEY, resourceList);
			
			mav.setViewName("redirect:/index");
		
			return mav;
		}else{
			mav.addObject("errorMsg", "用户名或密码错误！");
			mav.setViewName("/login");
			return mav;
		}
		
		
	}
	
	/**
	 * 登出操作
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception{
		session.invalidate();
		return "redirect:login";
	}
	
	@RequestMapping("/randomSalt")
	@ResponseBody
	public String randomSalt(HttpSession session){
		String randomSalt = SaltUtil.getSalt();
		return randomSalt;
	}
	
	
	

}
