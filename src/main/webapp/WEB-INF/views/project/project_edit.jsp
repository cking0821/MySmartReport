<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 新建/编辑项目 -->
<form id="saveProjectForm" name="saveProjectForm" class="form-horizontal" method="post" action="saveProject">
	<input type="hidden" id="projectId" name="id" value="${sysProject.id }">
	<div id="modal-content" class="box-body">
		<div class="form-group">
			<label for="projectCode" class="col-sm-2 control-label">项目代码</label>

			<div class="col-sm-10">
				<input type="text" class="form-control" id="projectCode" name="projectCode" placeholder="请输入项目代码" value="${sysProject.projectCode }">
			</div>
		</div>
		<div class="form-group">
			<label for="projectName" class="col-sm-2 control-label">项目名称</label>

			<div class="col-sm-10">
				<input type="text" class="form-control" id="projectName" name="projectName" placeholder="请输入项目名称" value="${sysProject.projectName }">
			</div>
		</div>
		<div class="form-group">
			<label for="projectStatus" class="col-sm-2 control-label">项目状态</label>

			<div class="col-sm-10">
				<select class="form-control" id="projectStatus" name="projectStatus">
					
					
					<option value="INUSE"
						<c:if test="${sysProject.projectStatus=='INUSE' }"> selected</c:if>
					>启用</option>
					<option value="LOCK" 
					<c:if test="${sysProject.projectStatus=='LOCK' }"> selected</c:if>
					>禁用</option>
				</select>

			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-2 control-label">用户组</label>

			<div class="col-sm-10">				
				<div class="input-group">
					<input type="text" id="userTags" name="projectUser" value="${sysProject.projectUser }" class="form-control" placeholder=""> 
					<span class="input-group-btn">
						<button type="button" name="search" id="search-btn"  onclick="searchUserGroup()" class="btn btn-flat">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-2 control-label">项目描述</label>

			<div class="col-sm-10">				
				<textarea class="form-control" id="description" name="description"
					rows="3" placeholder="项目简介 ...">${sysProject.description}</textarea>
			</div>
		</div>


	</div>

</form>

<script src="assets/tagsinput/bootstrap-tagsinput.js"></script>
<SCRIPT>

$(function() {
	$('#userTags').tagsinput({
		itemValue : 'groupId',
		itemText : 'groupName',
		trimValue: true
	});

	
	var userTags = $("#userTags").val();
	var userGroups = userTags.split(";");
	for(var i=0;i<userGroups.length;i++){
		if(userGroups[i]!==''){
			var groupArr = userGroups[i].split("/"); 
			$('#userTags').tagsinput('add', {
				groupId : groupArr[0],
				groupName : groupArr[1]
			});
		}
		
	}
	
	

	$('#userTags').on('itemAdded', function(event) {
		// Hide the suggestions menu
		$('.typeahead.dropdown-menu').css('display', 'none')
		// Clear the typed text after a tag is added
		$('.bootstrap-tagsinput > input').val("");
	});

})

function searchUserGroup() {
	$.get("initSearchUserGroup", function(data) {
		var editContext = data;

		bootbox.dialog({
			title : '选择用户组',
			message : editContext,
			buttons : {
				ok : {
					label : "保存",
					className : 'btn-info',
					callback : function() {
						selectUserGroup();
					}
				},
				cancel : {
					label : "关闭",
					className : 'btn-default',
					callback : function() {

					}
				}

			}
		})
	});
}

function updateSelectedUserGroups(groups) {
	console.log(["groups", groups]);
	if(groups.length>0){
		for(var i=0, l=groups.length; i<l; i++){
			$('#userTags').tagsinput('add', {
				groupId : groups[i].groupId,
				groupName : groups[i].groupName
			});
		}
	}
}

function updateProject(){
	var projectName = $("#projectName").val();
	if(projectName==undefined || projectName==""){
		bootbox.alert({
			title : "提示",
		    message : "项目名称不允许为空!",
		    size : 'small'
		});
		return false;
	}else if(projectName.length<3 || projectName.length>20){
		bootbox.alert({
			title : "提示",
		    message : "项目名称长度应该在 3~20 之间.",
		    size : 'small'
		});
		return false;
	}
	
	
 $.post({
        url : 'saveProject',
        data : $('form[name=saveProjectForm]').serialize(),
        success : function(res) {
       	 if(res=="SUCCESS"){
       	 	bootbox.alert({
				title : "提示",
			    message : "保存成功!",
			    size : 'small'
			});
       		 freshMenu();
       	 }
        
        },
        error : function(msg){
       	 alert(msg);
        }
     })
	
}
</SCRIPT>



