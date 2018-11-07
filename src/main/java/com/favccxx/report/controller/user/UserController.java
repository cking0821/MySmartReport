package com.favccxx.report.controller.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysResource;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysUserService;
import com.favccxx.report.util.EncryptDesUtil;
import com.favccxx.report.util.EncryptUtil;
import com.favccxx.report.util.LogUtil;
import com.favccxx.report.util.SaltUtil;

@Controller
public class UserController {

	@Autowired
	SysUserService sysUserService;

	@RequestMapping("/save")
	public String save() {

		SysUser sysUser = new SysUser();

		String salt = SaltUtil.getSalt();
		// String originPass = EncryptDesUtil.decryption("111", salt);
		String encryptPass = "";
		try {
			encryptPass = EncryptUtil.encrypt(EncryptUtil.HASH_METHOD_SHA_256,
					(EncryptUtil.encrypt(EncryptUtil.HASH_METHOD_SHA_256, "1") + salt));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sysUser.setUserName("root");
		sysUser.setNickName("超级管理员");
		sysUser.setUserMail("root@root.com");
		sysUser.setUserTel("18710010000");
		sysUser.setSalt(salt);
		sysUser.setUserPwd(encryptPass);
		sysUser.setUserStatus(SysConstants.USER_STATUS_NOMAL);
		sysUser.setCreateUserId(1);
		sysUser.setCreateTime(new Date());
		sysUser.setUpdateUserId(1);
		sysUser.setUpdateTime(new Date());
		sysUserService.saveUser(sysUser);
		return "";
	}

	@RequestMapping("/initUserList")
	public ModelAndView initUserList(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		SessionUser sessionUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询用户信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER);

		List<SysUser> list = sysUserService.listUsers();
		mav.addObject("users", list);
		mav.setViewName("/user/user_list");

		return mav;
	}

	@RequestMapping("/user_add")
	public ModelAndView addUser(HttpSession session, String userId) {
		ModelAndView mav = new ModelAndView();

		SessionUser sessionUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "编辑用户信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER);

		if (userId != null && !"".equals(userId.trim())) {
			SysUser sysUser = sysUserService.findById(Long.parseLong(userId));
			mav.addObject("sysUser", sysUser);
			mav.addObject("operationName", "编辑");
		} else {
			mav.addObject("operationName", "添加");
		}
		mav.setViewName("/user/user_add");

		return mav;
	}

	@RequestMapping("/userProfile")
	public ModelAndView userProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		SessionUser sessionUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询用户信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER);

		if (sessionUser != null) {
			SysUser sysUser = sysUserService.findById(sessionUser.getUserId());
			mav.addObject("reportUser", sysUser);
		}
		mav.setViewName("/user/user_profile");
		return mav;
	}

	@PostMapping("/saveUser")
	@ResponseBody
	public String saveUser(HttpSession session, SysUser sysUser) throws Exception {
		SessionUser sessionUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存用户信息", LogConstants.OPERATE_UPDATE,
				LogConstants.MODULE_USER);

		if (sysUser.getId() != 0) {
			SysUser existUser = sysUserService.findById(sysUser.getId());
			existUser.setNickName(sysUser.getNickName());
			existUser.setUserMail(sysUser.getUserMail());
			existUser.setUserTel(sysUser.getUserTel());
			existUser.setUserRole(sysUser.getUserRole());
			if ("ADMINISTRATOR".equals(sysUser.getUserRole())) {
				existUser.setRoleId(1);
			} else if ("OPERATOR".equals(sysUser.getUserRole())) {
				existUser.setRoleId(2);
			} else {
				existUser.setRoleId(3);
			}
			existUser.setUserDescription(sysUser.getUserDescription());
			existUser.setUpdateUserId(sessionUser.getUserId());
			existUser.setUpdateTime(new Date());
			sysUserService.saveUser(existUser);
		} else {
			String salt = (String) session.getAttribute(SysConstants.USER_SALT_KEY);
			String originPass = EncryptDesUtil.decryption(sysUser.getUserPwd(), salt);
			String encryptPass = EncryptUtil.encrypt(EncryptUtil.HASH_METHOD_SHA_256,
					(EncryptUtil.encrypt(EncryptUtil.HASH_METHOD_SHA_256, originPass) + salt));

			sysUser.setSalt(salt);
			sysUser.setUserPwd(encryptPass);
			sysUser.setUserStatus(SysConstants.USER_STATUS_NOMAL);

			if ("ADMINISTRATOR".equals(sysUser.getUserRole())) {
				sysUser.setRoleId(1);
			} else if ("OPERATOR".equals(sysUser.getUserRole())) {
				sysUser.setRoleId(2);
			} else {
				sysUser.setRoleId(3);
			}

			sysUser.setCreateUserId(sessionUser.getUserId());
			sysUser.setCreateTime(new Date());
			sysUser.setUpdateUserId(sessionUser.getUserId());
			sysUser.setUpdateTime(new Date());
			sysUserService.saveUser(sysUser);
		}
		return JSON.toJSONString(RestResult.success(sysUser));
	}

	@PostMapping("/saveUserProfile")
	@ResponseBody
	public String saveUserProfile(HttpSession session, SysUser sysUser) throws Exception {
		SessionUser sessionUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存用户信息", LogConstants.OPERATE_UPDATE,
				LogConstants.MODULE_USER);

		if (sysUser.getId() != 0) {
			SysUser existUser = sysUserService.findById(sysUser.getId());
			existUser.setNickName(sysUser.getNickName());
			existUser.setUserMail(sysUser.getUserMail());
			existUser.setUserTel(sysUser.getUserTel());
			existUser.setUserDescription(sysUser.getUserDescription());
			existUser.setUpdateTime(new Date());
			sysUserService.saveUser(existUser);


			sessionUser.setNickName(sysUser.getNickName());
			sessionUser.setUserMail(sysUser.getUserMail());
			sessionUser.setUserName(sysUser.getUserName());
			sessionUser.setUserTel(sysUser.getUserTel());

			
			session.setAttribute(SysConstants.USER_SESSION_KEY, sessionUser);
		}
		return JSON.toJSONString(RestResult.success(sysUser));
	}

	@RequestMapping(value = "/delSysUser", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String delSysUser(HttpSession session, @RequestBody String userId) {
		SessionUser sessionUser = (SessionUser) session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "删除用户信息", LogConstants.OPERATE_DELETE,
				LogConstants.MODULE_USER);

		sysUserService.delUser(Long.parseLong(userId));
		return JSON.toJSONString(RestResult.success("success"));
	}

}
