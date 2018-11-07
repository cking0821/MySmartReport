<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="assets/ztree/css/zTreeStyle/zTreeStyle.css">
<script src="assets/ztree/js/jquery.ztree.core.js"></script>
<script src="assets/ztree/js/jquery.ztree.excheck.js"></script>
<script src="assets/ztree/js/jquery.ztree.exedit.js"></script>
<section class="content-header">
	<h1>
		角色 <small>管理</small>
	</h1>
</section>

<!-- Main content -->
<section id="mainContent" class="content container-fluid">

	<div class="row">
		<div class="col-md-4">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h5>角色列表</h5>
				</div>
				
				<div class="box-body">
					<table class="table table-bordered">
						<tr>
							<th>序号</th>
							<th>角色代码</th>
							<th>角色名称</th>
							<th>角色权限</th>
						</tr>

						<c:forEach var="roleItem" items="${roles }" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${roleItem.roleCode }</td>
								<td>${roleItem.roleName }</td>
								<td><span class="badge bg-light-blue" style="cursor:pointer;" onclick="ViewRolePermission('${roleItem.id }', '${roleItem.roleName }')">查看</span></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
			</div>

		</div>



		<div class="col-md-8">
			<div class="box box-primary">

				<div class="box-header with-border">
					<h5 id="resouceTitle">角色权限</h5>
				</div>
				
				<div id="roleResouce" class="box-body">
					
				</div>

			</div>
		</div>
	</div>
</section>

<SCRIPT>

	$(function() {
		
		
	})

	function ViewRolePermission(roleId, roleName){
		console.log(["roleName", roleName]);
		
		 $("#resouceTitle").html(roleName + "权限");   
		
		$.get("getRoleResources?roleId=" + roleId, function(data){  
            $("#roleResouce").html(data);   
         });  
		
		
	}
	
</SCRIPT>




