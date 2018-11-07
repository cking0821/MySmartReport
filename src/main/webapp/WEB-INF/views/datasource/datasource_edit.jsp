<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-md-12">

		<div class="box-header">
			<div class="form-group">
				<input type="hidden" id="dsId" value="${dataSource.id }">
			
				<label for="dsName" class="col-sm-2 control-label">数据源名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="dsName" value="${dataSource.name }" placeholder="">
				</div>
			</div>
		</div>
		<div class="box-header">	
			<div class="form-group">
				<label for="description" class="col-sm-2 control-label">数据源简介</label>

				<div class="col-sm-10">
					<textarea class="form-control" id="description" name="reportBrief" rows="3" placeholder="目录简介 ...">${dataSource.description }</textarea>
				</div>
			</div>
			
		</div>
		<div class="box-body" style="height: 100%;">
			
			
			<div class="form-group">
				<label for="dbType" class="col-sm-2 control-label">数据库</label>

				<div class="col-sm-10">
					<select class="form-control" id="dbType" name="dbType">
						<option value="MySQL" <c:if test = "${dataSource.dbType=='MySQL' }">selected</c:if> > MySQL</option>
						<option value="MySQL8" <c:if test = "${dataSource.dbType=='MySQL8' }">selected</c:if> > MySQL8</option>
						<option value="Oralce"  <c:if test = "${dataSource.dbType=='Oralce' }">selected</c:if> >Oracle</option>	                    	                    
	                </select>
				</div>
			</div>
		</div>
		<div class="box-body">
			
			<div class="form-group">
				<label for="username" class="col-sm-2 control-label">用户名</label>

				<div class="col-sm-10">
					<input type="text" class="form-control" id="username" value="${dataSource.username }" placeholder="">
				</div>
			</div>
		</div>
		<div class="box-body">	
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">密码</label>

				<div class="col-sm-10">
					<input type="password" class="form-control" id="password" value="${dataSource.password }" placeholder="">
				</div>
			</div>
		</div>
		<div class="box-body">	
			<div class="form-group">
				<label for="urlAddress" class="col-sm-2 control-label">地址</label>

				<div class="col-sm-10">
					<input type="text" class="form-control" id="urlAddress" value="${dataSource.urlAddress }" placeholder="jdbc:mysql://ipaddress:port/dbname.">
				</div>
			</div>

		</div>

		<div class="box-footer" style="text-align: right;">
			<button type="button" class="btn btn-info" onclick="saveDataSource()">保存</button>
			<button type="button" class="btn btn-default">重置</button>
		</div>
	</div>
</div>
<SCRIPT>
	function saveDataSource(){
		var zTree = $.fn.zTree.getZTreeObj("datasourceTree");
		var treeNode = zTree.getSelectedNodes()[0];
		
		console.log(["treeNode", treeNode]);
		var postData;
		var dsId = $("#dsId").val();
		if(dsId && dsId!=""){
			postData = {
					id : $("#dsId").val(),
					name : $("#dsName").val() ,
					description : $("#description").val(),
					dbType : $("#dbType").val(),
					username : $("#username").val(),
					password : $("#password").val(),
					urlAddress : $("#urlAddress").val()
			};
		}else{
			postData = {
					id : $("#dsId").val(),
					projectId : $("#sysProject option:selected").val(),
					parentId : 	treeNode.id,
					name : $("#dsName").val() ,
					description : $("#description").val(),
					dbType : $("#dbType").val(),
					username : $("#username").val(),
					password : $("#password").val(),
					urlAddress : $("#urlAddress").val()
			};
		}
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "saveDataSource",
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
             		    	
             		    	refreshDataSourceDefine(nodeId);
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
</SCRIPT>


<style type="text/css">
	
</style>