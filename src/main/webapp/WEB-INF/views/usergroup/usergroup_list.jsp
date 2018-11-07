<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.selectedTb{
	background-color:#3C8DBC !important;
}
</style>

<section class="content-header">
	<h1>
		用户组 <small>管理</small>
	</h1>
</section>

<!-- Main content -->
<section id="mainContent" class="content container-fluid">

	<div class="row">
		<div class="col-md-4">
			<div class="box box-primary">				
				
				<div class="box-body">
					<div class="margin">
						<div class="btn-group">
							<button type="button" onclick="addUserGroup()" class="btn btn-success">添加</button>
							<button type="button" onclick="editUserGroup()" class="btn btn-info">编辑</button>
							<button type="button" onclick="deleteUserGroup()" class="btn btn-danger">删除</button>
						</div>
					</div>
					<table id="userGroupTable" class="table table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th>序号</th>
								<th>用户组id</th>
								<th>用户组代码</th>
								<th>用户组名称</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="userGroupItem" items="${userGroups }" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${userGroupItem.id }</td>
									<td>${userGroupItem.groupCode }</td>
									<td>${userGroupItem.groupName }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
			</div>

		</div>



		<div class="col-md-8">
			<div id="userGroupPrivilegeInfo" class="nav-tabs-custom">
	            <ul class="nav nav-tabs">
	            	<li class="active"><a href="#userTab" data-toggle="tab">用户管理</a></li>	              	
	              	<li id="roleContentTab"><a href="#roleTab" data-toggle="tab" onclick="showRoles()">角色管理</a></li>	            	
	            </ul>
	            <div class="tab-content">
	            	<div class="active tab-pane" id="userTab">
	              		<div id="userListContent" class="box box-info">	       
	           			</div>
	              	</div>
	              	<div class="tab-pane" id="roleTab">
	              		<div id="roleListContent" class="box box-info">	            
	           			</div>
	              	</div>
	              	
	              
	            </div>
			</div>
		
		</div>
	</div>
</section>

<SCRIPT>

var selectedRow = null;

$(function() {
	var table = $('#userGroupTable').DataTable();
    $('#userGroupTable tbody').on('click', 'tr', function () {	    	
    	selectedRow = table.row( this ).data();    	
    	$(this).addClass('selectedTb').siblings().removeClass('selectedTb').end();
    	
    	searchUserGroupUsers(selectedRow[1]);
    } );
    
});

$('#userGroupTable').DataTable({
	'paging' : true,
	'lengthChange' : false,
	'searching' : false,
	'ordering' : true,
	'info' : true,
	'autoWidth' : true,
	"columnDefs": [
        {
            "targets": [ 1 ],
            "visible": false,
            "searchable": false
        }
    ]
});

//添加用户组
function addUserGroup(){
	$.get("initUserGroupEdit?groupId=0", function(data){
		var editContext = data;
		
		bootbox.dialog({
			title: '添加用户组',
			message: editContext,
			buttons: {
				ok: {
			        label: "保存",
			        className: 'btn-primary',
			        callback: function(){
			        	saveUserGroup();
			        }
			    },
			    cancel: {
			        label: "关闭",
			        className: 'btn-default',
			        callback: function(){
			            
			        }
			    }
			    
			}
		})
	});
}

//编辑用户组
function editUserGroup(){
			
	if(selectedRow!=null){
		var projectId = selectedRow[1];
		
		$.get("initUserGroupEdit?projectId=" + projectId, function(data){
			var editContext = data;
			
			bootbox.dialog({
				title: '编辑' + selectedRow[2],
				message: editContext,
				buttons: {
					ok: {
				        label: "保存",
				        className: 'btn-info',
				        callback: function(){
				        	saveUserGroup();
				        }
				    },
				    cancel: {
				        label: "关闭",
				        className: 'btn-default',
				        callback: function(){
				            
				        }
				    }
				    
				}
			})
		});
	}else{
		bootbox.alert({
			title : "提示",
		    message : "请选择需要编辑的用户组!",
		    size : 'small'
		});
	}
}

//删除用户组
function deleteUserGroup(){
	if(selectedRow == null){
		bootbox.alert({
			title : "提示",
		    message : "请选择需要删除的用户组!",
		    size : 'small'
		});
		return;
	}
	
	console.log([selectedRow]);
	
	bootbox.confirm({
	    title: "提示",
	    message : "确定要删除用户组 [" + selectedRow[3] + "] 吗？>",
	    buttons: {
	        confirm: {
	            label: '确定',
	            className: 'btn-success'
	        },
	        cancel: {
	            label: '取消',
	            className: 'btn-danger'
	        }
	    },
	    callback: function (result) {
	    	if(result!=null && result==true){
	    		var postData = {
	    				groupId : selectedRow[1]	
	    		};
	    		$.ajax({
					type : "POST",
// 					contentType : "application/json;charset=utf-8",
					url : "deleteUserGroup",
					data : postData,
// 					dataType : 'JSON',
					timeout : 100000,
		            success: function (res) {
		            	freshMenu();
		            	bootbox.alert("删除成功。");
		            },
					error : function(message) {

					}
			    });
	    	}
	    	
	    }
	});
}



function ViewGroupUsers(groupId, groupTitle){
	$.get("getUserGroupUsers?userName=&pageIndex=1&pageSize=10&groupId=" + groupId + "", function(data){  
	    $("#groupUserContent").html(data);   
	}); 
}


function checkAll(){
	var isChecked = $('#chkAll').prop('checked'); 
	if(isChecked){
		$("input[name='chkUser']").attr("checked","true");
	}else{
		$("input[name='chkUser']").removeAttr("checked","true");
	}
}


function saveGroupUsers(){
	if(selectedRow==null){
		bootbox.alert({
			title : "提示",
		    message : "请选择需要保存的用户组!",
		    size : 'small'
		});
		return;
	}
	
	var groupId = selectedRow[1];
	var selectedUserIds = new Array();
	var removeUserIds = new Array();;
	$("input[name='chkUser']:checked").each(function() {   
		var userId = $(this).attr("id");
		if(userId!="undefined"){
			selectedUserIds.push(userId);   
		}
		
	}); 
	
	$("input[name='chkUser']:not(:checked)").each(function() {  
		var userId = $(this).attr("id");
		if(userId!="undefined"){
			removeUserIds.push(userId);   
		}
	}); 
	
	var postData = {
			groupId : groupId,
			selectedUserIds : selectedUserIds,
			"removeUserIds" : removeUserIds
	}
	
	 $.post({
	        url : 'saveGroupUsers',
	        data : postData,
	        success : function(res) {
	        	bootbox.alert({
					title : "提示",
				    message : "保存成功!",
				    size : 'small'
				});
// 	       	 if(res=="SUCCESS"){
// 	       	 	bootbox.alert({
// 					title : "提示",
// 				    message : "保存成功!",
// 				    size : 'small'
// 				});
// 	       		 freshMenu();
// 	       	 }
	        
	        },
	        error : function(msg){
	        	console.log([msg]);
	       	 alert(msg);
	        }
	     })
	
	
	console.log([selectedUserIds, removeUserIds]);
}


function searchUserGroupUsers(groupId){
	$("#userGroupPrivilegeInfo ul").find("li").each(function () {
		$(this).removeClass("active");		
	});
	$("#userGroupPrivilegeInfo ul li").first().addClass('active');
	
	$(".tab-content").find("div").each(function () {
		$(this).removeClass("active");			
	});
	$(".tab-content div").first().addClass('active');
	
	$.get("getUserGroupUsers?userName=&pageIndex=1&pageSize=10&groupId=" + groupId + "", function(data){  
	    $("#userListContent").html(data);   
	});
}




function getGroupUsers(groupId){
	//移除原来选中的
	$("input[name='chkUser']").each(function() { 
		$(this).prop("checked", false);
		$(this).removeAttr("checked");
// 		$(this).prop("checked", "false");
	}); 
	
	$("input[name='chkUser']").each(function() {   
		var currentGroupId = $(this).attr("value");
		if(groupId === currentGroupId){
			$(this).prop("checked", true);
// 			$(this).attr("checked","true");
// 			$(this).prop("checked", "true");
		} 
	}); 
}

//显示角色列表
function showRoles(){
	if(selectedRow!=null){
		var userGroupId = selectedRow[1];
		
		$.get("initUserGroupRoleList?userGroupId=" + userGroupId, function(data){  
			 $("#roleListContent").html(data);   
	     }); 
	}
	
}

//更新用户组与角色之间的关系
function updateUserGroupRoles(roleIds){
	if(selectedRow==null){
		bootbox.alert({
			title : "提示",
		    message : "请选择用户组!",
		    size : 'small'
		});
		return;
	}
	
	var postData = {
			groupId : selectedRow[1],
			roleIds : roleIds
	}
	
	$.post({
        url : 'updateUserGroupRoles',
        data : postData,
        success : function(res) {
        	bootbox.alert({
				title : "提示",
			    message : "保存成功!",
			    size : 'small'
			});
        },
        error : function(msg){
       	 alert(msg);
        }
     })
	
}

	
</SCRIPT>




