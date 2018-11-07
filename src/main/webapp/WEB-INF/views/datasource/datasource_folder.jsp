<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-12">

		<div class="box-header">
			<div class="form-group">
				<input type="hidden" id="dsId" value="${dataSource.id }">
				
				<label for="dsName" class="col-sm-2 control-label">目录名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="dsName" value="${dataSource.name }" placeholder="">
				</div>
			</div>
		</div>
		<div class="box-body" style="height: 100%;">
			<div class="form-group">
				<label for="description" class="col-sm-2 control-label">目录简介</label>

				<div class="col-sm-10">
					<textarea class="form-control" id="description" name="reportBrief" rows="3" placeholder="目录简介 ...">${dataSource.description }</textarea>
				</div>
			</div>

		</div>

		<div class="box-footer" style="text-align: right;">
			<button type="button" class="btn btn-info" onclick="saveDsFolder()">保存</button>
			<button type="button" class="btn btn-default">重置</button>
		</div>
	</div>
</div>
<SCRIPT>
	//更新数据源目录
	function saveDsFolder(){
		var zTree = $.fn.zTree.getZTreeObj("datasourceTree");
		var treeNode = zTree.getSelectedNodes()[0];
		
		
		console.log(["treeNode", treeNode]);
		var postData;
		var dsId = $("#dsId").val();
		console.log(["dsId", dsId]);
		if(dsId && dsId!=""){
			postData = {
					id : dsId,
					name : $("#dsName").val() ,
					description : $("#description").val()
			};
		}else{
			postData = {
					id : $("#dsId").val(),
					projectId : $("#sysProject option:selected").val(),
					parentId : 	treeNode.id,
					name : $("#dsName").val() ,
					description : $("#description").val()
			};
		}
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "saveDsFolder",
			data : JSON.stringify(postData),
			dataType : 'JSON',
			timeout : 100000,
	        success: function (res) {
	        	var nodeId = res.data.id;
	        	
	        	var dialog = bootbox.dialog({
        		    title: '提示',
        		    message: '保存成功。'
        		});
        		
        		dialog.init(function(){
        		    setTimeout(function(){
        		    	
        		    	updateZtree();
        		    	setTimeout(function(){
        		    		var updateNode = zTree.getNodeByParam("id", nodeId, null);
             		    	zTree.expandNode(updateNode);
             		    	zTree.selectNode(updateNode);
             		    	
             		    	refreshDataSourceFolder(nodeId);
        		    	 }, 500);
        		    	
        		    	
        		    	dialog.modal('hide');
        		    }, 1500);
        		});
	        },
	        error: function (msg) {
	            console.log(["error" + msg]);
	        }
	    }); 
		
	}
	
	
	function updateZtree(){
		var projectId = $("#sysProject option:selected").val();
		
		refrshDataSourceTree(projectId);
	}
</SCRIPT>