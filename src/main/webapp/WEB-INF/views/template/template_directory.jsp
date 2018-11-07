<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-12">

		<div class="box-header">
			<div class="form-group">
				<input type="hidden" id="dsId" value="${directory.id }">
				
				<label for="directoryName" class="col-sm-2 control-label">目录名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="directoryName" value="${directory.name }" placeholder="">
				</div>
			</div>
		</div>
		<div class="box-body" style="height: 100%;">
			<div class="form-group">
				<label for="description" class="col-sm-2 control-label">目录简介</label>

				<div class="col-sm-10">
					<textarea class="form-control" id="description" name="description" rows="3" placeholder="目录简介 ...">${directory.description }</textarea>
				</div>
			</div>

		</div>

		<div class="box-footer" style="text-align: right;">
			<button type="button" class="btn btn-info" onclick="saveDirectoryFile()">保存</button>
			<button type="button" class="btn btn-default">重置</button>
		</div>
	</div>
</div>
<SCRIPT>
	function saveDirectoryFile(){
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];
		var postData;
		
		var directoryId = $("#dsId").val();
		if(directoryId && directoryId!=""){
			postData = {
					id : directoryId,
					name : $("#directoryName").val() ,
					description : $("#description").val()
			};
		}else{
			postData = {
					id : directoryId,
					projectId : $("#sysProject option:selected").val(),
					parentId : 	treeNode.id,
					name : $("#directoryName").val() ,
					description : $("#description").val()
			};
		}
		
		console.log(["treeNode", treeNode]);
		
		
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "saveDirectoryFile",
			data : JSON.stringify(postData),
			dataType : 'JSON',
			timeout : 100000,
            success: function (res) {
            	if(postData.id && postData.id!=""){
            		treeNode.name = res.data.name;
            		zTree.refresh();
            	}else{
            		$("#dsId").attr("value", res.data.id);
            		zTree.addNodes(treeNode, {id: res.data.id, pId:res.data.parentId, name:res.data.name, iconSkin:'iconFolder', projectId:res.data.projectId  });
            	}
    			
            },
            error: function (msg) {
                console.log(["error" + msg]);
            }
        }); 
	}
</SCRIPT>
