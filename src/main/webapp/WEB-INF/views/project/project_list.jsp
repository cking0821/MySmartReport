<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="content-header">
	<h1>
		项目 <small>管理</small>
	</h1>
</section>

<!-- Main content -->
<section id="mainContent" class="content container-fluid">

	<div class="box-body">
		<div class="box">
			<div class="margin">
				<div class="btn-group">					
					<button type="button" onclick="addProject()" class="btn btn-success">添加</button>
					<button type="button" onclick="editProject()" class="btn btn-info">编辑</button>
					<button type="button" onclick="deleteProject()" class="btn btn-danger">删除</button>
				</div>
			</div>
			
			<!-- /.box-header -->
			<table id="projectTable" class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>Id</th>
						<th>项目代码</th>
						<th>项目名称</th>
						<th>项目状态</th>
						<th>备注</th>
						<th>更新时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="projectItem" items="${projects }" varStatus="status">
						<tr>	
							<td>${status.index + 1}</td>	
							<td>${projectItem.id }</td>				
							<td>${projectItem.projectCode }</td>		
							<td>${projectItem.projectName }</td>
							<td>
								<c:if test="${projectItem.projectStatus=='INUSE' }">启用</c:if>
								<c:if test="${projectItem.projectStatus=='LOCK' }">禁用</c:if>
							</td>
							<td>${projectItem.description }</td>
							<td>${projectItem.updateTime }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- /.box-body -->
	</div>
</section>

<SCRIPT>
	var selectedRow = null;
	$(function() {
		var table = $('#projectTable').DataTable();
	    $('#projectTable tbody').on('click', 'tr', function () {	    	
	    	selectedRow = table.row( this ).data();
	    	$(this).addClass('selectedTb').siblings().removeClass('selectedTb').end();
	    } );
	});

	$('#projectTable').DataTable({
		'paging' : true,
		'lengthChange' : false,
		'searching' : false,
		'ordering' : true,
		'info' : true,
		'autoWidth' : true,
		"columnDefs": [
            {
                "targets": [ 1,5 ],
                "visible": false,
                "searchable": false
            }
        ]
	});
	
	//添加项目
	function addProject(){
		$.get("initEditProject?projectId=0", function(data){
			var editContext = data;
			
			bootbox.dialog({
				title: '添加项目',
				message: editContext,
				buttons: {
					ok: {
				        label: "保存",
				        className: 'btn-primary',
				        callback: function(){
				        	updateProject();
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
	
	//编辑项目
	function editProject(){
				
		if(selectedRow!=null){
			var projectId = selectedRow[1];
			
			$.get("initEditProject?projectId=" + projectId, function(data){
				var editContext = data;
				
				bootbox.dialog({
					title: '编辑' + selectedRow[2],
					message: editContext,
					buttons: {
						ok: {
					        label: "保存",
					        className: 'btn-info',
					        callback: function(){
					        	updateProject();
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
			    message : "请选择需要编辑的项目!",
			    size : 'small'
			});
		}
	}
	
	
	function assignUsers(){
		if(selectedRow==null){
			bootbox.alert({
				title : "提示",
			    message : "请选择需要操作的项目!",
			    size : 'small'
			});
			return;
		}
		
		//分配用户
		var projectId = selectedRow[1];
		
		
		$.get("initProjectUser?projectId=" + projectId, function(data){
			var editContext = data;
			
			bootbox.dialog({
				title: '分配用户',
				message: editContext,
				buttons: {
					ok: {
				        label: "保存",
				        className: 'btn-info',
				        callback: function(){
				        	saveProjectUsers();
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
	
	//删除项目
	function deleteProject(){
		if(selectedRow == null){
			bootbox.alert({
				title : "提示",
			    message : "请选择需要删除的项目!",
			    size : 'small'
			});
			return;
		}
		
		bootbox.confirm({
		    title: "提示",
		    message : "确定要删除项目 [" + selectedRow[2] + "] 吗？",
		    buttons: {
		        confirm: {
		            label: '确定',
		            className: 'btn-danger'
		        },
		        cancel: {
		            label: '取消',
		            className: 'btn-default'
		        }
		    },
		    callback: function (result) {
		    	if(result!=null && result==true){
		    		$.ajax({
						type : "POST",
						contentType : "application/json;charset=utf-8",
						url : "delSysProject",
						data : selectedRow[1],
						dataType : 'JSON',
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
	
	
	
</SCRIPT>



