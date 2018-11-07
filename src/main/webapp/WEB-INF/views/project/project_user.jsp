<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <link -->
<!-- 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" -->
<!-- 	rel="stylesheet"> -->
<!-- <link rel="stylesheet" -->
<!-- 	href="http://localhost:8080/ReportServer/assets/tagsinput/bootstrap-tagsinput.css"> -->




<!-- 新建/编辑项目 -->
<form id="saveProjectForm" name="saveProjectForm"
	class="form-horizontal" method="post" action="saveProject">
	<input type="hidden" id="projectId" name="id" value="${sysProject.id }">
	<div id="modal-content" class="box-body">
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-2 control-label">项目名称</label>

			<div class="col-sm-10">
				<input type="text" class="form-control" id="projectName"
					name="projectName" placeholder="请输入项目名称"
					value="${sysProject.projectName }">
			</div>
		</div>
		<div class="form-group">
			<label for="projectStatus" class="col-sm-2 control-label">用户列表</label>

			<div class="col-sm-10">
				<button type="button" onclick="searchUser()" class="btn btn-danger">搜索用户</button>
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-2 control-label"></label>



			<div class="col-sm-10">
				<input type="text" id="userTags" style="width:100%;" />
			</div>
		</div>


	</div>

</form>


<!-- <script src="https://code.jquery.com/jquery-2.1.4.js"></script> -->
<!-- <script -->
<!-- 	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->
<script
	src="http://localhost:8080/SmartReport/assets/tagsinput/bootstrap-tagsinput.js"></script>




<SCRIPT>
	$(function() {
		$('#userTags').tagsinput({
			itemValue : 'userId',
			itemText : 'userName',
			trimValue: true
		});

		$('#userTags').tagsinput('add', {
			userId : 1,
			userName : 'some tag'
		});

		$('#userTags').on('itemAdded', function(event) {
			// Hide the suggestions menu
			$('.typeahead.dropdown-menu').css('display', 'none')
			// Clear the typed text after a tag is added
			$('.bootstrap-tagsinput > input').val("");
		});

	})

	function searchUser() {
		$.get("initSearchUser", function(data) {
			var editContext = data;

			bootbox.dialog({
				title : '选择用户',
				message : editContext,
				buttons : {
					ok : {
						label : "保存",
						className : 'btn-info',
						callback : function() {
							selectUsers();
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

	function updateSelectedUsers(users) {
		if(users.length>0){
			for(var i=0, l=users.length; i<l; i++){
				$('#userTags').tagsinput('add', {
					userId : users[i].userId,
					userName : users[i].userName
				});
			}
		}
	}
	
	//保存用户
	function saveProjectUsers(){
		var userTags = $("#userTags").val();
		
		var postData = {
				projectId : $("#projectId").val(),
				userIds : userTags
		}
		
		
		$.post({
	    	url : 'saveProjectUser',
	    	 data: postData,
  			success : function(res) {
		 		if(res=="SUCCESS"){
			 	 	bootbox.alert({
						title : "提示",
				    	message : "保存成功!",
				    	size : 'small'
					});
			 	 	
			 	 	showSchedule();
// 	 		 		freshMenu();
	 	 		}
	  		},
			error : function(msg){
				alert(msg);
			}
	    });
		
		console.log(userTags);
	}
</SCRIPT>



