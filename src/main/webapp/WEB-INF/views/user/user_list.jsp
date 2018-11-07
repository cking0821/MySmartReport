<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<section class="content-header">
	<h1>
		用户 <small>管理</small>
	</h1>
</section>

<!-- Main content -->
<section id="mainContent" class="content container-fluid">

	<div class="box-body">
		<div class="box">
			<div class="margin">
				<div class="btn-group">
					<button type="button" onclick="addUser()"class="btn btn-success" >添加</button>
					<button type="button" onclick="editUser()" class="btn btn-info">编辑</button>
					<button type="button" onclick="delUser()" class="btn btn-danger">删除</button>
				</div>
			</div>
			
			<!-- /.box-header -->
			<table id="userTable"
				class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>id</th>
						<th>用户名</th>
						<th>昵称</th>
						<th>电话</th>
						<th>邮箱</th>
						<th>状态</th>
						<th>更新时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="userItem" items="${users }" varStatus="status">
						<tr>	
							<td>${status.index + 1}</td>	
							<td>${userItem.id }</td>						
							<td>${userItem.userName }</td>
							<td>${userItem.nickName }</td>							
							<td>${userItem.userTel }</td>
							<td>${userItem.userMail }</td>
							<td>
								<c:if test="${userItem.userStatus=='NORMAL' }">
									正常
								</c:if>
								<c:if test="${userItem.userStatus=='LOCK' }">
									冻结
								</c:if>
								<c:if test="${userItem.userStatus=='DELETE' }">
									已删除
								</c:if>
							</td>
							<td>${userItem.updateTime }</td>
						</tr>
					</c:forEach>
				
				
				
				
				</tbody>
			</table>


			<!-- 删除提示 -->
			<div class="modal fade" id="userModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">删除提示</h4>
						</div>
						<div class="modal-body">
							<form name="delSysUserForm" class="form-horizontal" method="post" action="delSysUser">
				              <div class="box-body">
				              	<div id="modalContent"></div>				              	
				              </div>
				              <!-- /.box-body -->
				              <div class="modal-footer">
									<button type="button" onclick="delSysUser()" class="btn btn-danger">确定</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>									
								</div>
				              <!-- /.box-footer -->
				            </form>
						
						</div>
						
					</div>
				</div>
			</div>


		</div>
		<!-- /.box-body -->
	</div>
</section>

<SCRIPT>
	var selectedUserRow = null;

	$('#userTable').DataTable({
		'paging' : true,
		'lengthChange' : false,
		'searching' : true,
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
	
	
	
	$(function() {
		$('#userModal').on('hidden.bs.modal', function () {
			freshMenu();
		})
		
		var table = $('#userTable').DataTable();
	     
	    $('#userTable tbody').on('click', 'tr', function () {
	    	selectedUserRow = table.row( this ).data();	    	
	    	$(this).addClass('selectedTb').siblings().removeClass('selectedTb').end();
	    } );
	   
	});
	
	function addUser(){
		$.get("user_add?userId=", function(data){
			var editContext = data;
			
			bootbox.dialog({
				title: '添加用户',
				message: editContext,
				buttons: {
					ok: {
				        label: "保存",
				        className: 'btn-primary',
				        callback: function(){
				        	saveUser();
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
	
	function editUser(){		
		if(selectedUserRow!=null){
			var userId = selectedUserRow[1];
			
			$.get("user_add?userId=" + userId, function(data){
				var editContext = data;
				
				bootbox.dialog({
					title: '编辑用户',
					message: editContext,
					buttons: {
						ok: {
					        label: "保存",
					        className: 'btn-primary',
					        callback: function(){
					        	saveUser();
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
			$("#modalContent").html("请选择需要编辑的用户。");
			$('#userModal').modal('show');
		}
	}
	
	function delUser(){
		if(selectedUserRow==null){
			$("#modalContent").html("请选择需要删除的用户。");
			$('#userModal').modal('show');
		}else{			
			$("#modalContent").html("确定要删除用户 [" + selectedUserRow[2] + "] 吗？删除后，用户将无法进行任何操作。" );
			$('#userModal').modal('show');
		}
		
	}
	
	function delSysUser(){
		if(selectedUserRow!=null){
			var userId = selectedUserRow[1];
			
			$.ajax({
				type : "POST",
				contentType : "application/json;charset=utf-8",
				url : "delSysUser",
				data : userId,
				dataType : 'JSON',
				timeout : 100000,
	            success: function (res) {
	            	$('#userModal').modal('hide');
	            },
				error : function(message) {

				}
		    });
		}
	}
</SCRIPT>



