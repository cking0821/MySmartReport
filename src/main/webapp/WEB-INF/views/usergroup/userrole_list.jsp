<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-md-12">
		<div class="">
			<div class="box-header with-border">
				<h5>角色列表</h5>
			</div>

			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th>选择</th>
						<th>序号</th>
						<th>角色代码</th>
						<th>角色名称</th>
					</tr>

					<c:forEach var="groupRoleItem" items="${groupRoleList }"
						varStatus="status">
						<tr>
							<td><c:if test="${groupRoleItem.groupId!=0 }">
									<input type="checkbox" name="chkRole" checked value="${groupRoleItem.roleId }">
								</c:if> <c:if test="${groupRoleItem.groupId==0 }">
									<input type="checkbox" name="chkRole" value="${groupRoleItem.roleId }">
								</c:if></td>
							<td>${status.index + 1}</td>
							<td>${groupRoleItem.roleCode }</td>
							<td>${groupRoleItem.roleName }</td>

						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="box-footer">
				<button type="button" onclick="saveGroupRoles()" class="btn btn-info pull-right">保存</button>
			</div>

		</div>

	</div>

</div>

<script>

function saveGroupRoles(){
	var checkedRoles = "";
	$("input[name='chkRole']:checked").each(function() {   
		checkedRoles = checkedRoles + $(this).attr("value") + ",";
	}); 
	
	updateUserGroupRoles(checkedRoles);
}

</script>






