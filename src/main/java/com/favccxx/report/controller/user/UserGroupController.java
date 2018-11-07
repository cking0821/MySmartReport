package com.favccxx.report.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.favccxx.report.constants.LogConstants;
import com.favccxx.report.constants.SysConstants;
import com.favccxx.report.context.SessionUser;
import com.favccxx.report.model.SysRole;
import com.favccxx.report.model.SysUser;
import com.favccxx.report.model.SysUserGroup;
import com.favccxx.report.model.SysUserGroupRole;
import com.favccxx.report.model.UserGroupRoleVo;
import com.favccxx.report.model.UserGroupUserVo;
import com.favccxx.report.result.DataTableServerResult;
import com.favccxx.report.result.PageInfo;
import com.favccxx.report.result.RestResult;
import com.favccxx.report.service.SysRoleService;
import com.favccxx.report.service.SysUserGroupService;
import com.favccxx.report.service.SysUserService;
import com.favccxx.report.util.LogUtil;

@Controller
public class UserGroupController {

	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysUserGroupService sysUserGroupService;
	@Autowired
	SysRoleService sysRoleService;

	// 查询所有的用户组
	@RequestMapping("/initUserGroupList")
	public ModelAndView initUserGroupList(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询用户组信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER_GROUP);
		
		
		List<SysUserGroup> list = sysUserGroupService.findAll();
		mav.addObject("userGroups", list);
		mav.setViewName("/usergroup/usergroup_list");

		return mav;
	}

	// 通用的查询用户组
	@RequestMapping("/initSearchUserGroup")
	public ModelAndView initSearchUserGroup(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询用户组信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER_GROUP);
		
		
		List<SysUserGroup> list = sysUserGroupService.findAll();
		mav.addObject("userGroups", list);
		mav.setViewName("/usergroup/search_usergroup");

		return mav;
	}

	// 初始化用户组的角色列表
	@RequestMapping("/initUserGroupRoleList")
	public ModelAndView initUserGroupRoleList(HttpSession session, int userGroupId) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "根据用户组查询角色信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER_GROUP);
		
		
		List<UserGroupRoleVo> ugrList = new ArrayList<UserGroupRoleVo>();

		List<SysRole> list = sysRoleService.listRoles();
		List<SysUserGroupRole> groupRoleList = sysUserGroupService.listByGroupId(userGroupId);

		for (SysRole role : list) {
			UserGroupRoleVo userGroupRole = new UserGroupRoleVo();
			userGroupRole.setRoleId(role.getId());
			userGroupRole.setRoleCode(role.getRoleCode());
			userGroupRole.setRoleName(role.getRoleName());
			for (SysUserGroupRole ugr : groupRoleList) {
				if (ugr.getRoleId() == role.getId()) {
					userGroupRole.setGroupId(ugr.getGroupId());
				}
			}
			ugrList.add(userGroupRole);
		}

		mav.addObject("groupRoleList", ugrList);
		mav.setViewName("/usergroup/userrole_list");

		return mav;
	}

	// 编辑用户组界面
	@RequestMapping("/initUserGroupEdit")
	public ModelAndView initUserGroupEdit(HttpSession session, long groupId) {
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "编辑用户组信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER_GROUP);
		
		
		SysUserGroup userGroup = new SysUserGroup();
		userGroup.setId(Long.valueOf(0));

		if (groupId != 0) {
			userGroup = sysUserGroupService.findById(groupId);
		}

		mav.addObject("userGroup", userGroup);
		mav.setViewName("/usergroup/usergroup_edit");

		return mav;
	}

	/**
	 * 保存用户组
	 * 
	 * @param userGroup
	 * @return
	 */
	@RequestMapping(value = "/saveUserGroup", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String saveUserGroup(HttpSession session, @ModelAttribute("userGroup") @Valid SysUserGroup userGroup) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存用户组信息", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_USER_GROUP);
		
		if (userGroup.getId() == 0) {
			userGroup.setId(0);
			userGroup.setCreateUserId(sessionUser.getUserId());
			userGroup.setCreateTime(new Date());
			userGroup.setUpdateUserId(sessionUser.getUserId());
			userGroup.setUpdateTime(new Date());
			sysUserGroupService.saveOrUpdateUserGroup(userGroup);
			return SysConstants.SUCCESS_MSG;
		}

		userGroup.setUpdateUserId(sessionUser.getUserId());
		userGroup.setUpdateTime(new Date());
		sysUserGroupService.saveOrUpdateUserGroup(userGroup);

		return SysConstants.SUCCESS_MSG;
	}

	@RequestMapping(value = "/deleteUserGroup", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String deleteUserGroup(HttpSession session, long groupId) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "删除用户组信息", LogConstants.OPERATE_DELETE, LogConstants.MODULE_USER_GROUP);
		
		sysUserGroupService.deleteUserGroup(groupId);
		return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
	}

	// 保存用户组用户
	@RequestMapping(value = "/saveGroupUsers", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String saveGroupUsers(HttpSession session, long groupId,
			@RequestParam(value = "selectedUserIds[]", required = false) String[] selectedUserIds,
			@RequestParam(value = "removeUserIds[]", required = false) String[] removeUserIds) {
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存用户组用户信息", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_USER_GROUP);
		
		
		List<Long> addUserIds = new ArrayList<Long>();
		List<Long> delUserIds = new ArrayList<Long>();

		if (selectedUserIds != null && selectedUserIds.length > 0) {
			for (int i = 0; i < selectedUserIds.length; i++) {
				if (selectedUserIds[i] != "undefined") {
					addUserIds.add(Long.valueOf(selectedUserIds[i]));
				}
			}
		}

		if (removeUserIds != null && removeUserIds.length > 0) {
			for (int i = 0; i < removeUserIds.length; i++) {
				if (removeUserIds[i] != "undefined") {
					delUserIds.add(Long.valueOf(removeUserIds[i]));
				}
			}
		}

		System.out.println(groupId + "," + selectedUserIds);
		sysUserGroupService.saveUserGroupUsers(groupId, addUserIds, delUserIds);
		return JSON.toJSONString(RestResult.success(SysConstants.SUCCESS_MSG));
	}

	// 查询用户组的用户列表
	@RequestMapping("/getUserGroupUsers")
	public ModelAndView getUserGroupUsers(String userName, long groupId, int pageIndex, int pageSize) {
		ModelAndView mav = new ModelAndView();
		// PageInfo<VUserGroupUsers> pageInfo =
		// sysUserGroupService.pageUserGroups(userName, pageIndex, pageSize);
		// mav.addObject("pageInfo", pageInfo);
		mav.addObject("groupId", groupId);
		mav.setViewName("/usergroup/usergroup_user_list");

		return mav;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retriveGroupUserTbData", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String retriveGroupUserTbData(HttpSession session, @RequestParam String searchTxt, @RequestParam long groupId,
			@RequestParam int draw, @RequestParam int start, @RequestParam int length, @RequestParam Map params) {
		
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "查询用户组用户信息", LogConstants.OPERATE_QUERY, LogConstants.MODULE_USER_GROUP);
		
		
		DataTableServerResult<UserGroupUserVo> dataResult = new DataTableServerResult<UserGroupUserVo>();

		PageInfo<SysUser> pageInfo = sysUserService.pageUsers(searchTxt, start, length);
		List<SysUser> userList = pageInfo.getDataList();
		List<UserGroupUserVo> voList = new ArrayList<UserGroupUserVo>();
		for(SysUser sysUser : userList) {
			UserGroupUserVo ug = new UserGroupUserVo();
			
			ug.setUserId(sysUser.getId());
			ug.setNickName(sysUser.getNickName());
			ug.setUserMail(sysUser.getUserMail());
			ug.setUserStatus(sysUser.getUserStatus());
			ug.setUserName(sysUser.getUserName());
			ug.setUserTel(sysUser.getUserTel());
			
			
			boolean flag = sysUserGroupService.isUserInGroup(sysUser.getId(), groupId);
			if(flag) {
				ug.setGroupId(groupId);
			}
			voList.add(ug);
		}

		dataResult.setDraw(draw);
		dataResult.setRecordsTotal(pageInfo.getTotalCount());
		dataResult.setRecordsFiltered(pageInfo.getTotalCount());
		dataResult.setData(voList);

		return JSON.toJSONString(dataResult);
	}

	@RequestMapping(value = "/updateUserGroupRoles", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String updateUserGroupRoles(HttpSession session, long groupId, String roleIds) {
		SessionUser sessionUser = (SessionUser)session.getAttribute(SysConstants.USER_SESSION_KEY);
		LogUtil.logActivity(sessionUser.getUserName() + "保存用户组角色信息", LogConstants.OPERATE_UPDATE, LogConstants.MODULE_USER_GROUP);
		
		if (groupId == 0 || StringUtils.isBlank(roleIds)) {
			return JSON.toJSONString(RestResult.invalidParams());
		}

		String[] roleArr = roleIds.split(",");
		List<Long> roleIdsL = new ArrayList<Long>();
		for (int i = 0; i < roleArr.length; i++) {
			if (!StringUtils.isBlank(roleArr[i])) {
				roleIdsL.add(Long.valueOf(roleArr[i]));
			}
		}

		sysUserGroupService.updateUserGroupRoles(sessionUser.getUserId(), groupId, roleIdsL);
		return JSON.toJSONString(RestResult.success("操作成功"));
	}

}
