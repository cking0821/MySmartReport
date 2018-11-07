<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-md-12">
		<form>
			<input type="hidden" id="roleId" value="${roleId }">
			<div class="form-group">
				<c:forEach var="menuItem" items="${resources }" >
				<!--遍历菜单 -->
					<c:if test = "${menuItem.resourceType=='MENU' }">
						<div class="checkbox">
							<div>
								<label style="font-weight:bold;"> 
									<input type="checkbox" name="menuBox" value="${menuItem.id }" 
										<c:forEach var="roleResourceItem" items="${roleResources }" >
											<c:if test = "${roleResourceItem.roleResourceId.resourceId == menuItem.id }">
												checked
											</c:if>
										</c:forEach>
										<c:if test="${roleId==1 }">
											disabled
										</c:if>
									> ${menuItem.resourceName }
								</label>
							</div>
							
							
							<!-- 遍历按钮权限 -->
							<div style="margin-top:5px; margin-bottom:5px;">
								<c:forEach var="operationItem" items="${resources }" >
									<c:if test = "${operationItem.resourceType=='OPERATION' && operationItem.parentId==menuItem.id}">
										<label style="margin-left:20px;"> 
											<input type="checkbox" name="operationBox" value="${operationItem.id }"
												<c:forEach var="roleResourceItem" items="${roleResources }" >
													<c:if test = "${roleResourceItem.roleResourceId.resourceId == operationItem.id }">
														checked
													</c:if>
												</c:forEach>
												<c:if test="${roleId==1 }">
													disabled
												</c:if>
											> ${operationItem.resourceName }
										</label>
									</c:if>								
								</c:forEach>
							</div>
						</div>
					</c:if>
				</c:forEach>




			</div>
			<div class="form-group">
				<button type="button" name="search" onclick="updateResource()" class="btn btn-primary">保存
               	</button>               	               			
			</div>
		</form>


	</div>
</div>

<SCRIPT>
	$(function() {

	})

// 	function ViewRolePermission(roleId) {

// 		$.get("getRoleResources?roleId=" + roleId, function(data) {
// 			$("#roleResouce").html(data);
// 		});
// 	}
	
	
	function updateResource(){
		var menuValues = "";
		var operationValues = "";
		$("input[name='menuBox']:checkbox:checked").each(function(){
			menuValues += $(this).val()+",";
		});
		
		$("input[name='operationBox']:checkbox:checked").each(function(){
			operationValues += $(this).val()+",";
		});
		
		console.log(["xxx", menuValues, operationValues ]);
		
		var postData = {
				roleId : $("#roleId").val(),
				menuData : menuValues,
				operationData : operationValues
		}
		
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "updateRoleResource",
			data : JSON.stringify(postData),
			dataType : 'JSON',
			timeout : 100000,
            success: function (res) {
            	bootbox.alert({
    				title : "提示",
    			    message : "保存成功!",
    			    size : 'small'
    			});
        		return;
            },
			error : function(message) {

			}
	    });
		
		
	}
</SCRIPT>




