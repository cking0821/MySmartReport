<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/commonInclude.jsp"%>

<!-- Main content -->


<div class="row">
	<div class="col-md-12">
		<div >
			<input type="hidden" id="groupId" value="${groupId }">				
			<div class="box-body">
				<div id="groupUserContent" class="box-body">
					<div class="mailbox-controls">
						<button type="button" onclick="saveGroupUsers()" class="btn btn-info">保存</button>

						<div class="pull-right">
							<div class="has-feedback">
								<input id="searchTxt" type="text" class="form-control input-sm"
									placeholder="搜索"> <span
									class="glyphicon glyphicon-search form-control-feedback"></span>
							</div>
						</div>
					</div>

					<table id="userGroupUserTable"
						class="table table-hover table-striped">
						<thead>
							<tr>
								<th><input id="chkAll" type="checkbox"
									onclick="checkAll()" value=""></th>
								<th>分组Id</th>
								<th>用户名</th>
								<th>昵称</th>
								<th>邮箱</th>
								<th>电话</th>
							</tr>
						</thead>
					</table>
				</div>


			</div>
			
		</div>

	</div>



	
</div>


<SCRIPT>

var tbSelectedRow = null;

$(function() {
	
	var userGroupUserTable = $('#userGroupUserTable').DataTable({
		'searching' : false,
			'lengthChange' : false,
		    "aoColumns": [
		    	{"mData": function(e){
		    		if(e.groupId!=0){
		    			return '<input id="' + e.userId + '" name="chkUser" type="checkbox" value="' + e.groupId + '"  checked>';
		    		}else{
		    			return '<input id="' + e.userId + '" name="chkUser" type="checkbox" value="' + e.groupId + '">';
		    		}
		    		
		    		
		    		
		    		}, 
		    		"defaultContent": "",
		    		bSearchable : false,
		    		bSortable : false
		    	},
		    	{"mData": "groupId", "defaultContent": ""},
	        {"mData": "userName", "defaultContent": ""},
	        {"mData": "nickName", "defaultContent": ""},
	        {"mData": "userMail", "defaultContent": ""},
	        {"mData": "userTel", "defaultContent": ""}
	        
	    ],
	    'columnDefs': [
	        {
	            "targets": [ 1 ],
	            "visible": false,
	            "searchable": false
	        }
	    ],
		    "processing": true,
	    "serverSide": true,
	    "ajax": {
            "url": "retriveGroupUserTbData",
            "data": function ( d ) {
                d.groupId = $("#groupId").val();
                d.searchTxt = $("#searchTxt").val();
            }
        }
	});
});




function retrieveData(sSource, aoData, fnCallback) {
	$.ajax({
		"type": "post",
		"url": sSource,
		"dataType": "json",
		"data": aoData,
		"success": function(resp) {
			fnCallback(resp);
		} 
	});
}


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

function ViewGroupUsers(groupId, groupTitle){
	$.get("getUserGroupUsers?groupId=" + groupId, function(data){  
	    $("#groupUserContent").html(data);   
	}); 
}



	
</SCRIPT>




