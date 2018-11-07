<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 新建/编辑用户组 -->
<form id="userGroupForm" name="userGroupForm" class="form-horizontal" method="post" action="saveUserGroup">
	<input type="hidden" id="groupId" name="id" value="${userGroup.id }">
	
	<div id="modal-content" class="box-body">
		<div class="form-group">
			<label for="groupCode" class="col-sm-2 control-label">代码</label>

			<div class="col-sm-10">
				<input type="text" class="form-control" id="groupCode" name="groupCode" placeholder="请输入用户组代码" value="${userGroup.groupCode }">
			</div>
		</div>
		<div class="form-group">
			<label for="groupName" class="col-sm-2 control-label">名称</label>

			<div class="col-sm-10">
				<input type="text" class="form-control" id="groupName" name="groupName" placeholder="请输入用户组名称" value="${userGroup.groupName }">
			</div>
		</div>
		
		<div class="form-group">
			<label for="description" class="col-sm-2 control-label">备注</label>

			<div class="col-sm-10">				
				<textarea class="form-control" id="description" name="description"
					rows="3" placeholder="用户组简介 ...">${userGroup.description}</textarea>
			</div>
		</div>


	</div>

</form>


<SCRIPT>


function saveUserGroup(){
	var groupCode = $("#groupCode").val();
	if(groupCode==undefined || groupCode==""){
		bootbox.alert({
			title : "提示",
		    message : "用户组代码不允许为空!",
		    size : 'small'
		});
		return false;
	}else if(groupName==undefined || groupName==""){
		bootbox.alert({
			title : "提示",
		    message : "用户组名称不允许为空.",
		    size : 'small'
		});
		return false;
	}
	
	
 $.post({
        url : 'saveUserGroup',
        data : $('form[name=userGroupForm]').serialize(),
        success : function(res) {
        	bootbox.alert({
				title : "提示",
			    message : "保存成功!",
			    size : 'small'
			});
       		 freshMenu();
        
        },
        error : function(msg){
        	console.log(msg);
//        	 alert(msg);
        }
     })
	
}
</SCRIPT>



