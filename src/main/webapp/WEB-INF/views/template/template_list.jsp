<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<link rel="stylesheet" href="assets/ztree/css/zTreeStyle/zTreeStyle.css">
<script src="assets/ztree/js/jquery.ztree.core.js"></script>
<script src="assets/ztree/js/jquery.ztree.excheck.js"></script>
<script src="assets/ztree/js/jquery.ztree.exedit.js"></script>
<section class="content-header">
	<h1>
		模板 <small>管理</small>
	</h1>
</section>

<!-- Main content -->
<section id="mainContent" class="content container-fluid">

	<div class="row">
		<div class="col-md-3">
			<div class="box box-primary">
				<div class="box-header">
					 <div class="form-group">
					 	<form name="saveProjectForm" class="form-horizontal" method="post" action="saveProject">
		                    <label for="sysProject" class="col-sm-3 control-label">项目</label>
		
			                  <div class="col-sm-9">
			                  	 <select id="sysProject" class="form-control" onchange="changeProject()">
			                  	 	<option value="">请选择</option>	
			                  	 	<c:forEach var="projectItem" items="${projects }" varStatus="status">
										<option value="${projectItem.id }">${projectItem.projectName }</option>	
									</c:forEach>
				                  </select>
			                  </div>
		                 </form>
		             </div>
				</div>
				<div class="box-body" style="height:100%;">
					<input type="hidden" id="templateId" value="">
					
					<ul id="directoryTree" class="ztree"></ul>
				</div>
				
			</div>


			
		</div>
		<div class="col-md-9">
			<div id="templateInfo" class="nav-tabs-custom">
	            <ul class="nav nav-tabs">
	            	<li class="active"><a href="#baseInfo" data-toggle="tab">模板信息</a></li>	              	
	              	<li id="schedualTaskTab"><a href="#scheduleTask" data-toggle="tab" onclick="showSchedule()">定时任务</a></li>
<!-- 	            	<li id="permissionInfoTab"><a href="#permissionInfo" data-toggle="tab" onclick="showPermission()">权限管理</a></li> -->
	            </ul>
	            <div class="tab-content">
	            	<div class="active tab-pane" id="baseInfo">
	              		<div id="reportContent" class="box box-info">	            
	           			</div>
	              	</div>
	              	<div class="tab-pane" id="scheduleTask">
	              		<div id="scheduleContent" class="box box-info">	            
	           			</div>
	              	</div>
<!-- 	              	<div class="tab-pane" id="permissionInfo"> -->
<!-- 	              		<div id="versionContent" class="box box-info">	             -->
<!-- 	           			</div> -->
<!-- 	              	</div> -->
	              
	            </div>
			</div>
		</div>
		
		
	</div>



</section>

<SCRIPT>
	var newCount = 1;
	var zTree, rMenu;
	
	var zTreeObj;	
	var setting = {
		view: {
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: 0
			}
		},
		callback: {
			onClick: zTreeOnClick,
			onRightClick: OnRightClick
		}
	};
	
	$(document).ready(function() {
		zTree = $.fn.zTree.getZTreeObj("directoryTree");
		rMenu = $("#rMenu");
	});

	function changeProject(){
		var projectId = $("#sysProject option:selected").val();
		if(projectId!=""){
			refrshProjectTree(projectId);
		}
	}


	function refrshProjectTree(projectId){
		var postData = {
				"projectId" : projectId
		};
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "getTemplateTree",
			data : projectId,
			dataType : 'JSON',
			timeout : 100000,
            success: function (res) {
            	var zNodes = res.list;
            	console.log(["zNodes", zNodes]);
            	zTreeObj = $.fn.zTree.init($("#directoryTree"), setting, zNodes);
            },
            error: function (xhr, errorType, error) {
                console.log(["error" + ajaxUrl, errorType, error]);
            }
        });
		
	}
	
	//左键单击树
	function zTreeOnClick(event, treeId, treeNode) {
		console.log(["treeNode", treeNode]);
		var projectId = treeNode.projectId;		
		if(treeNode.type=="REPORT"){
			
			$("#templateId").val(treeNode.id);
			$("#permissionInfoTab").css("display", "inline-block");
			$("#schedualTaskTab").css("display", "inline-block");
			
			showTemplateBaseInfo();
			
			 $.get("initTemplateReport?id=" + treeNode.id + "&projectId=" + projectId ,function(data){  
				 $("#reportContent").html(data);   
		     }); 
			 
		}else{
			showTemplateBaseInfo();
			
			$("#permissionInfoTab").css("display", "none");
			$("#schedualTaskTab").css("display", "none");
			
			 $.get("initTemplateDirectory?id=" + treeNode.id ,function(data){  
				 $("#reportContent").html(data);   
		     }); 
		}
	}
	
	//展示模板基本信息
	function showTemplateBaseInfo(){
		$("#templateInfo ul").find("li").each(function () {
			$(this).removeClass("active");
			
		});
			
		$("#templateInfo ul li").first().addClass('active');
		
		$(".tab-content").find("div").each(function () {
			$(this).removeClass("active");			
		});
		
		$(".tab-content div").first().addClass('active');
	}
	
	//查看版本信息
	function showPermission(){
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];
		//模板Id
		var templateId = treeNode.id;
		
		$.get("initTemplateVersion?templateId=" + treeNode.id, function(data){  
			 $("#versionContent").html(data);   
	     }); 	
		
	}
	
	//查看模板定时任务
	function showSchedule(){
		//模板Id
		var templateId = $("#templateId").val();
		
		$.get("initTemplateScheduleList?templateId=" + templateId, function(data){  
			 $("#scheduleContent").html(data);   
	     }); 
	}
	
	

	//右键事件
	function OnRightClick(event, treeId, treeNode) {
		if(treeNode){
			zTree = $.fn.zTree.getZTreeObj("directoryTree");
			zTree.selectNode(treeNode);
			this.treeNode = treeNode;
			showRMenu(treeNode, event.clientX, event.clientY);
		}
	}
	
	//右键菜单
	function showRMenu(treeNode, x, y) {
		if(treeNode && treeNode.type=="REPORT"){
			$("#d_add").hide();
			$("#r_add").hide();
			$("#d_del").hide();
			$("#r_del").show();
			$("#r_view").show();
// 			$("#q_view").show();
		}		
		
		if(treeNode && treeNode.type=="FOLDER"){
			$("#d_add").show();
			$("#r_add").show();
			$("#r_del").hide();
			$("#r_view").hide();
// 			$("#q_view").hide();
			
			if(!treeNode.children && treeNode.iconSkin!="iconHome"){
				$("#d_del").show();
			}else{
				$("#d_del").hide();
			}
		}
		
		$("#rMenu ul").show();
		
        y += document.body.scrollTop;
        x += document.body.scrollLeft;
        $("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});

		$("body").bind("mousedown", onBodyMouseDown);
		
		
	}
	//隐藏右键菜单
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	
	//执行右键事件时
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	
	var addCount = 1;
	
	//添加新节点
	function addTemplateDirectory() {
		hideRMenu();
		
		$.get("initTemplateDirectory",function(data){  
			 $("#reportContent").html(data);   
	    }); 
	}
	

	
	//删除节点
	function removeTemplateDirectory(){
		hideRMenu();
		
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];
		
		var msg = "确定要删除目录 [" + treeNode.name + "] 吗?";
		
		
		bootbox.confirm({
		    title: "提示",
		    message : msg,
		    callback: function (result) {
		    	var postData = {
						id : treeNode.id
				};
				
				$.ajax({
					type : "POST",
					contentType : "application/json;charset=utf-8",
					url : "delTreeNode",
					data : JSON.stringify(postData),
					dataType : 'JSON',
					timeout : 100000,
		            success: function (res) {
		            	zTree.removeNode(treeNode);
		            },
		            error: function (xhr, errorType, error) {
		                console.log(["error" + ajaxUrl, errorType, error]);
		            }
		        });		
		    }
		})
		
	}
	
	//删除报表
	function deleteTemplate(){
		hideRMenu();
		
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];
		var msg = "确定要删除报表 [" + treeNode.name + "] 吗?";
		
		bootbox.confirm({
		    title: "提示",
		    message : msg,
		    callback: function (result) {
		    	var postData = {
						id : treeNode.id
				};
				
				$.ajax({
					type : "POST",
					contentType : "application/json;charset=utf-8",
					url : "delTreeNode",
					data : JSON.stringify(postData),
					dataType : 'JSON',
					timeout : 100000,
		            success: function (res) {
		            	zTree.removeNode(treeNode);
		            },
		            error: function (xhr, errorType, error) {
		                console.log(["error" + ajaxUrl, errorType, error]);
		            }
		        });		
		    }
		})
	}

	//添加报表	
	function addTemplate(){
		hideRMenu();
		
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];
		var projectId = treeNode.projectId;
		
		$.get("initTemplateReport?projectId=" + projectId, function(data){  
			 $("#reportContent").html(data);   
	    }); 
		
		
	}
	
	
	function viewReport(){
		hideRMenu();
				
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];		
		
		var testURL = "<%=basePath%>securityViewReport?id=" + treeNode.id;
		window.open(testURL);
	}
	
	
	function quickViewReport(){
		hideRMenu();
		
		var zTree = $.fn.zTree.getZTreeObj("directoryTree");
		var treeNode = zTree.getSelectedNodes()[0];
		
		var postData = {
				id : treeNode.id
		};
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "previewReport",
			data : JSON.stringify(postData),
			dataType : 'JSON',
			timeout : 100000,
            success: function (res) {
            	if(res.statusCode==0 && !res.data.reportName){
            		bootbox.alert({
    					title : "提示",
    				    message : "请上传报表文件后，再进行预览操作。",
    				    size : 'small'
    				});
            		return;
            	}
            	var url = "<%=basePath%>webReport?ReportName=" + res.data.reportName;            	
            	window.open(url);
            },
            error: function (xhr, errorType, error) {
                console.log(["error" + ajaxUrl, errorType, error]);
            }
        });	
		
	}
	
	
	
	

	
</SCRIPT>
<style type="text/css">
	
	
	
	div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
	div#rMenu ul li{
		margin: 1px 0;
		padding: 0 5px;
		cursor: pointer;
		list-style: none outside none;
		background-color: #DFDFDF;
	}
	
	.ztree li span.button.iconHome_ico_docu{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconHome_ico_open{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconHome_ico_close{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconFolder_ico_open{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/folder_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconFolder_ico_close{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/folder_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconFolder_ico_docu{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/folder_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconReport_ico_open{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/report.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconReport_ico_close{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/report.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconReport_ico_docu{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/report.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
<div id="rMenu">
	<ul style="margin-bottom:0px;padding-left:0px;">
		<li id="d_add" onclick="addTemplateDirectory();">增加目录</li>
		<li id="r_add" onclick="addTemplate();">添加模板</li>
		<li id="d_del" onclick="removeTemplateDirectory();">删除目录</li>
		<li id="r_del" onclick="deleteTemplate();">删除模板</li>		
		<li id="r_view" onclick="viewReport();">预览报表</li>
		<li id="q_view" onclick="quickViewReport();">快速预览</li>
	</ul>
</div>



