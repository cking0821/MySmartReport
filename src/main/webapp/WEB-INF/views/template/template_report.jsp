<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-md-12">
		<div class="box box-info">
            <!-- /.box-header -->
            <!-- form start -->
            <div class="form-horizontal">
              <div class="box-body">
                <div class="form-group">
                  <input type="hidden" id="directoryId" value="${directory.id }">
                
                  <label for="reportName" class="col-sm-2 control-label">模板名称</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="directoryName" placeholder="" value="${directory.name }">
                  </div>
                </div>
                <div class="form-group">
                  <label for="directoryLabel" class="col-sm-2 control-label">模板标签</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="directoryLabel" placeholder="" value="${directory.directoryLabel }">
                  </div>
                </div>
                 <div class="form-group">
                  <label for="parameter" class="col-sm-2 control-label">模板参数</label>

                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="parameter" placeholder="" value="${directory.parameter }">
                    <labe>格式：&amp;paramA=A1&amp;paramB=B&amp;userName=$USERNAME(用户名)&amp;userId=$USERID(用户ID)&amp;orgId=$ORGID(组织ID)&amp;date=$DATE(格式yyyy-MM-dd)&amp;datetime=$DATETIME(格式yyyy-MM-dd HH:mm:ss)</labe>
                  </div>
                </div>
                <div class="form-group">
                  <label for="description" class="col-sm-2 control-label">模板简介</label>

                  <div class="col-sm-10">
                     <textarea class="form-control" id="description" name="description" rows="3" placeholder="报表简介 ...">${directory.description }</textarea>
                  </div>
                </div>
                <div class="form-group">
                  <label for="reportSource" class="col-sm-2 control-label">数据源</label>

                  <div class="col-sm-10">
                    <select class="form-control" id="datasource" name="datasource">
                    	<option value="">请选择</option>
	                    	<c:forEach var = "dataSourceItem" items="${dataSourceList }" > 
	                    		<option value="${dataSourceItem.id }"  
	                    			<c:if test = "${directory.dataSource==dataSourceItem.id }">selected</c:if> >${dataSourceItem.name } 
	                    		</option> 
                    	</c:forEach> 
	                    
	                   
	                  </select>
                  </div>
                </div>
               
                <form id="fileUploadForm" action="fileUpload" method="post" enctype="multipart/form-data" style="">
	            <div id="reportDiv" class="form-group" style="visibility:hidden;">		                
	                  <label for="reportFile" class="col-sm-2 control-label">报表文件</label>
	                  <input type="hidden" id="reportId" name="reportId" value="${directory.id }" >
	
	                  <div id="sss" class="col-sm-1">
	                  	 <input type="file" id="reportFile" name="file" style="width:72px;" accept=".rptdesign" value="${directory.reportName }"  onchange="changeInfo();">		                  	                 	                 
	                  </div>
	                  
	                  
	                  <div class="col-sm-1">
	                  	<button type="button" onclick="fileUpload()" style="height:25px;padding:0 5px;" class="btn btn-info">上传</button>
	                  </div>
	                  <div class="col-sm-8">
	                  	<label id="uploadMsg" style="color:right;"></label>
	                  </div>
	             	
	             </div>
                </form>
                
              </div>
              <!-- /.box-body -->
              <div class="box-footer" style="text-align:right;">
              	<button type="button" class="btn btn-info" onclick="saveDirectoryReport()">保存</button>
                <button type="button" class="btn btn-default">重置</button>
                
              </div>
              <!-- /.box-footer -->
            </div>
           </div>

		
	</div>
</div>
<SCRIPT>

	$(document).ready(function() {
		var directoryId = $("#directoryId").val();
		
		var reportName = $("#reportFile").attr("value");
		
		
		$("#uploadMsg").html(reportName);
		
		 if(directoryId && directoryId!=""){
			$("#reportDiv").css({"visibility" : "visible"});
		}else{
			$("#reportDiv").css({"visibility" : "hidden"});
		} 
	});

	function saveDirectoryReport(){
		$("#uploadMsg").html("");
		
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];
		
		console.log(["treeNode", treeNode]);
		var postData;
		
		var directoryId = $("#directoryId").val();
		
		if(directoryId && directoryId!=""){
			postData = {
					id : directoryId,
					name : $("#directoryName").val() ,
					parameter : $("#parameter").val(),
					description : $("#description").val(),
					dataSource : $("#datasource").val(),
					directoryLabel : $("#directoryLabel").val
			};
		}else{
			postData = {
					id : directoryId,
					projectId : $("#sysProject option:selected").val(),
					parentId : 	treeNode.id,
					name : $("#directoryName").val() ,
					parameter : $("#parameter").val(),
					description : $("#description").val(),
					dataSource : $("#datasource").val(),
					directoryLabel : $("#directoryLabel").val
			};
		}
		
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "saveDirectoryReport",
			data : JSON.stringify(postData),
			dataType : 'JSON',
			timeout : 100000,
            success: function (res) {
            	if(directoryId && directoryId!=""){
            		treeNode.name = res.data.name;
            		zTree.refresh();
            	}else{
            		$("#directoryId").attr("value", res.data.id);
            		zTree.addNodes(treeNode, {id: res.data.id, pId:res.data.parentId, name:res.data.name, iconSkin:'iconReport', type:res.data.type, projectId:res.data.projectId  });
            		$("#fileUploadForm").css({"display" : "inline-block"});
            	}
            },
            error: function (msg) {
                console.log(["error" + msg]);
            }
        }); 
	}
	
	function fileUpload(){
		
		var rptFile = $("#reportFile").val();
		if(rptFile==null || rptFile==""){
			bootbox.alert({
				title : "提示",
			    message : "请选择需要上传的报表文件!",
			    size : 'small'
			});
			return;
		}
		
		$("#uploadMsg").html("");
		var form = document.getElementById("fileUploadForm");
	    var formData = new FormData(form);
	    
	    console.log(["formData", formData]);
	    	
	    // Ajax call for file uploaling
	    var ajaxReq = $.ajax({
	      url : 'fileUpload',
	      type : 'POST',
	      data : formData,
	      cache : false,
	      contentType : false,
	      processData : false,
	      success: function (res) {
	    	  $("#uploadMsg").html("上传成功！");
	    	  console.log(["res", res]);
	      }
	    })
	}
	
	function changeInfo(){
		var filePath = $("#reportFile").val();
		if(filePath.indexOf("\\")>0){
			var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
			$("#uploadMsg").html(fileName);
		}
		
	}
</SCRIPT>


<style type="text/css">
	
</style>