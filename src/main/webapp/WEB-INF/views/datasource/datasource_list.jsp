<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<link rel="stylesheet" href="assets/ztree/css/zTreeStyle/zTreeStyle.css">
<script src="assets/ztree/js/jquery.ztree.core.js"></script>
<script src="assets/ztree/js/jquery.ztree.excheck.js"></script>
<script src="assets/ztree/js/jquery.ztree.exedit.js"></script>
<section class="content-header">
	<h1>
		数据源 <small>管理</small>
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
					<ul id="datasourceTree" class="ztree"></ul>
				</div>				
			</div>
			
		</div>
		<div class="col-md-9">
			<div id="datasourceContent" class="box box-info">
	            
            </div>
		</div>
	</div>



</section>

<SCRIPT>
	var newCount = 1;
	var zTree, rMenu, zTreeObj, treeNode;
	
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
		zTree = $.fn.zTree.getZTreeObj("datasourceTree");
		rMenu = $("#rMenu");
	});

	function changeProject(){
		var projectId = $("#sysProject option:selected").val();
		if(projectId!=""){
			refrshDataSourceTree(projectId);
		}
	}

	//刷新数据源目录树
	function refrshDataSourceTree(projectId){
		var postData = {
				"projectId" : projectId
		};
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "getDatasources",
			data : projectId,
			dataType : 'JSON',
			timeout : 100000,
            success: function (res) {
            	if(res.statusCode==0){
            		var zNodes = res.data;
            		$.fn.zTree.init($("#datasourceTree"), setting, zNodes);
            	}
            },
            error: function (xhr, errorType, error) {
                console.log(["error" + ajaxUrl, errorType, error]);
            }
        });
		
	}
	
	//左键单击树
	function zTreeOnClick(event, treeId, treeNode) {
		if(treeNode.type=="FOLDER"){
			refreshDataSourceFolder(treeNode.id);
		}else{
			refreshDataSourceDefine(treeNode.id);
		}
		
		console.log(["treeNode", treeNode]);
	}
	
	//刷新数据源文件夹
	function refreshDataSourceFolder(treeId){
		 $.get("initDataSourceFolder?id=" + treeId ,function(data){  
			 $("#datasourceContent").html(data);   
	     }); 
	}
	
	
	function refreshDataSourceDefine(treeId){
		 $.get("initNewDs?id=" + treeId ,function(data){  
			 $("#datasourceContent").html(data);   
	     }); 
	}
	

	//右键事件
	function OnRightClick(event, treeId, treeNode) {
		if(treeNode){
			zTree = $.fn.zTree.getZTreeObj("datasourceTree");
			zTree.selectNode(treeNode);
			this.treeNode = treeNode;
			showRMenu(treeNode, event.clientX, event.clientY);
		}
	}
	
	//右键菜单
	function showRMenu(treeNode, x, y) {
		console.log(["treeNode", treeNode]);
		
		if(treeNode && treeNode.type=="DATASOURCE"){
			$("#m_addFolder").hide();
			$("#m_addDs").hide();			
			$("#m_delFolder").hide();
			$("#d_delDs").show();
		}
		
		if(treeNode && treeNode.type=="FOLDER"){
			$("#m_addFolder").show();
			$("#m_addDs").show();		
			$("#d_delDs").hide();
			console.log(["treeNode.isLastNode", treeNode.isLastNode]);
			if(!treeNode.children && treeNode.iconSkin!="iconHome"){
				$("#m_delFolder").show();
			}else{
				$("#m_delFolder").hide();
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
	
	//添加新节点
	function addFolder() {
		hideRMenu();
		 
		 $.get("initDataSourceFolder",function(data){  
			 $("#datasourceContent").html(data);   
	     }); 
	}
	
	//初始化添加数据源
	function addDs(){
		hideRMenu();
		
		$.get("initNewDs",function(data){  
			 $("#datasourceContent").html(data); 
	    }); 
	}	
	
	//删除节点
	function removeTreeNode(){
		hideRMenu();
		hideReport();
		
		var zTree = $.fn.zTree.getZTreeObj("datasourceTree");
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
					url : "delDataSource",
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
	
	//删除数据源
	function delDs(){
		hideRMenu();
		hideReport();
		
		var zTree = $.fn.zTree.getZTreeObj("datasourceTree");
		var treeNode = zTree.getSelectedNodes()[0];
		
		var msg = "确定要删除数据源 [" + treeNode.name + "] 吗?";
		
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
					url : "delDataSource",
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
	function addReport(){
		$("#reportContent").css({"display" : "inline-block"});
		
	}
	
	function hideReport(){
		$("#reportContent").css({"display" : "none"});
	}
	
	function saveReport(){
		var zTree = $.fn.zTree.getZTreeObj("datasourceTree");
		var treeNode = zTree.getSelectedNodes()[0];
		var reportId = $("#reportId").val();
		
		
		var postData = {	
				reportId : reportId,
				reportName : $("#reportName").val(),
				reportLabel : $("#reportLabel").val(),
				reportBrief : $("#reportBrief").val(),
				reportSource : $("#reportSource").val(),
				parentId : "" + treeNode.id,
				projectId : $("#sysProject option:selected").val()
		}
		
		
		$.ajax({
			type : "POST",
			contentType : "application/json;charset=utf-8",
			url : "saveReportFile",
			data : JSON.stringify(postData),
			dataType : 'JSON',
			timeout : 100000,
            success: function (res) {
            	if(res.statusCode==0){
            		
            		
            		if(reportId=="" || reportId==undefined){
            			
            			reportId = res.data.id;
                		$("#reportId").attr("value", reportId);
                		//新增节点
                		zTree.addNodes(treeNode, {id: res.data.id, pId:res.data.parentId, name:res.data.name, iconSkin:'iconReport' });
            			return false;
            		}else{
            			zTree.refresh();
            		}
            	}
            },
            error: function (xhr, errorType, error) {
                console.log(["error" + ajaxUrl, errorType, error]);
            }
        });			
	}
	
	
	function fileUpload(){
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
	    	  console.log(["res", res]);
	      }
	    })
	}

	
</SCRIPT>
<style type="text/css">
	div#reportContent {display:none;}
	
	
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
	.ztree li span.button.iconReport_ico_docu{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconDataSource_ico_docu{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/datasource.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconDataSource_ico_open{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/datasource.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.iconDataSource_ico_close{margin-right:2px; background: url(assets/ztree/css/zTreeStyle/img/diy/datasource.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>

<div id="rMenu">
	<ul style="margin-bottom:0px;padding-left:0px;">
		<li id="m_addFolder" onclick="addFolder();">添加目录</li>
		<li id="m_addDs" onclick="addDs();">添加数据源</li>
		<li id="m_delFolder" onclick="removeTreeNode();">删除目录</li>
		<li id="d_delDs" onclick="delDs();">删除数据源</li>
	</ul>
</div>




