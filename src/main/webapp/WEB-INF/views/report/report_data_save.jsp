<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>





<!-- 新建/编辑项目 -->
<form id="saveProjectForm" name="saveProjectForm" class="form-horizontal" method="post" action="saveProject">
	<input type="hidden" id="projectId" name="id" value="${templateData.id }">
	<div id="modal-content" class="box-body">
		<div class="form-group">
			<label for="versionName" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="versionName" name="name" placeholder="请输入名称" value="${templateData.name }">
			</div>
		</div>
		<div class="form-group">
			<label for="version" class="col-sm-2 control-label">版本号</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="version" name="version" placeholder="请输入版本号" value="${templateData.version }">
			</div>
		</div>
		


	</div>

</form>


<SCRIPT>
	function saveVersion(){
		var versionName = $("#versionName").val();
		if(versionName==undefined || versionName==""){
			bootbox.alert({
				title : "提示",
			    message : "名称不允许为空!",
			    size : 'small'
			});
			return false;
		}
		
		var version = $("#version").val();
		
		
		saveDataToNewVersion(versionName, version);
		
		
// 		var postData = {
// 			name : 	versionName,
// 			version : version,
			
// 		};
		
		
		
// 		$.ajax({
// 			type : "POST",
// 			url : "saveTemplateData",
// 			data : postData,
// 			timeout : 100000,
//             success: function (res) {
//             	console.log([res]);
//             	var result = JSON.parse(res);
//             	if(result.statusCode==0){
//             		bootbox.alert({
//     					title : "成功",
//     				    message : "保存成功!",
//     				    size : 'small'
//     				});
//             	}
    			
//             },
//             error: function (msg) {
//                 console.log(["error" + msg]);
//             }
//         }); 
		
		
// 	 $.post({
//          url : 'saveProject',
//          data : $('form[name=saveProjectForm]').serialize(),
//          success : function(res) {
//         	 if(res=="SUCCESS"){
//         	 	bootbox.alert({
// 					title : "提示",
// 				    message : "保存成功!",
// 				    size : 'small'
// 				});
//         		 freshMenu();
//         	 }
         
//          },
//          error : function(msg){
//         	 alert(msg);
//          }
//       })
		
	}
</SCRIPT>



