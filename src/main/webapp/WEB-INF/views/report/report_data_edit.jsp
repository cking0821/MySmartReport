<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="assets/images/favicon.ico" />
<title>机灵报表-编辑数据集</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- 字体样式表 -->
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/bower_components/font-awesome/css/font-awesome.min.css">
<!-- 图标 -->
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/bower_components/Ionicons/css/ionicons.min.css">
<!-- 主题样式 -->
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/dist/css/AdminLTE.min.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/dist/css/skins/skin-blue.min.css">

<%-- <link rel="stylesheet" href="<%=basePath%>assets/adminlte/plugins/iCheck/square/blue.css"> --%>

<link rel="stylesheet"
	href="<%=basePath%>assets/js/jsoneditor-5.12.0/jsoneditor.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/darktheme.css">

<style type="text/css">
.viewerEditor {
	font-size: medium;
	min-height: 80vh !important;
	margin-top: 5px;
	overflow: auto;
}
</style>


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<script
	src="<%=basePath%>assets/adminlte/bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>assets/js/jsonviewer.js"></script>

<script
	src="<%=basePath%>assets/adminlte/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>assets/js/bootbox.min.js"></script>

<script type="text/javascript"
	src="<%=basePath%>assets/js/jsoneditor-5.12.0/jsoneditor.min.js"></script>



</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="background-color: #ECF0F5;">
		<header class="main-header">

			<!-- Logo -->
			<a href="index" class="logo"> <span class="logo-mini"><b>R</b>S</span>
				<span class="logo-lg"><b>Report</b>Server</span>
			</a>

			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="push-menu"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>

			</nav>
		</header>


		<div>
			<section class="content-header">
				<h1>
					模板： ${template.name }, <small> 编辑数据集 </small> <small> <select
						class="form-control" id="dataSet">
							<c:forEach var="dataSetItem" items="${dataSets }">
								<option value="${dataSetItem.dataSetId }">${dataSetItem.name }</option>
							</c:forEach>
					</select>
					</small>
				</h1>




			</section>

			<!-- Main content -->
			<section class="content">
				<input type="hidden" id="templateId" value="${template.id }">
				<input type="hidden" id="templateVersionId" value="${templateVersionId }">
				<div class="row">

					<div class="col-md-5">

						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">数据编辑区</h3>
							</div>


							<div class="box-body">
								<div id="jsoneditor" class="viewerEditor" style="height:400px;"></div>
							</div>
						</div>
					</div>


					<div class="col-md-2">

						<div class="box box-primary">



							<div class="box-body">								
								<button type="button" class="btn btn-default btn-block btn-success" onclick="getReportData()">刷新</button>
             					<button type="button" class="btn btn-default btn-block btn-info" onclick="saveTemplateData()">保存数据</button>
              					<button type="button" class="btn btn-default btn-block btn-sm" onclick="saveNewVersion()">保存成报表</button>
							</div>
						</div>
					</div>



					<div class="col-md-5">

						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">数据展示区</h3>
							</div>


							<div class="box-body">
								<div id="jsonViewer" class="viewerEditor" style="height: 400px;"></div>
							</div>
						</div>
					</div>

				</div>
			</section>
		</div>

	</div>
	<script type="text/javascript">
		var jsonEditor, jsonViewer;
		//初始化编辑器
		function initJsonEditor() {
			var editContainer = document.getElementById("jsoneditor");
			var options = {
				mode : 'code',
				ace : ace,
				navigationBar : false
			};
			jsonEditor = new JSONEditor(editContainer, options);
		}

		//初始化视图
		function initJsonViewer() {
			var viewContainer = document.getElementById("jsonViewer");
			var options = {
				mode : 'view'
			};
			jsonViewer = new JSONEditor(viewContainer, options);
		}

		function getReportData() {
			//校验报表的数据集
			var dataSetId = $("#dataSet").val();
			console.log([ "dataSetId, dataSetId" ]);
			if (dataSetId == null || dataSetId == undefined || dataSetId == "") {
				bootbox.alert({
					title : "提示",
					message : "没有找到报表数据集，无法编辑。",
					size : 'small'
				});
				return;
			}

			var url = "getReportVersionData?templateId="
					+ $("#templateId").val() + "&dataSetId=" + dataSetId
					+ "&templateVersionId=" + $("#templateVersionId").val();

			$.getJSON(url, function(jsonResult) {
				console.log(["jsonResult", jsonResult]);
				// 				var jsonData = JSON.parse(jsonResult);

				// 				console.log(jsonData);
				
				var jsonData = JSON.stringify(jsonResult);

				jsonEditor.set(jsonResult);

				jsonViewer.set(jsonResult);

			});

			// 			$.getJSON("getReportData?templateId=" + $("#templateId").val() + "&dataSetId=" + dataSetId + "&version=draft", function(result) {
			// 				var jsonData = JSON.parse(result);	

			// 				console.log(jsonData);

			// 				jsonEditor.set(jsonData);

			// 				jsonViewer.set(jsonData);
			// 			});
		}

		function refresh() {
			getReportData();
		}

		$(document).ready(function() {
			initJsonEditor();
			initJsonViewer();

			getReportData();
		});

		//保存数据
		function saveTemplateData() {
			var dataSetId = $("#dataSet").val();
			var templateVersionId = $("#templateVersionId").val();
			
			//校验JSON数据格式是否合规
			var jsonData = jsonEditor.get();
			
			var postData = {
					templateVersionId : templateVersionId,
					dataSetId : dataSetId,
					dataSetData :  JSON.stringify(jsonData)
			};
			
			
			$.ajax({
				type : "POST",
				url : "saveTemplateData",
				data : postData,
				timeout : 100000,
	            success: function (res) {
	            	console.log([res]);
	            	var result = JSON.parse(res);
	            	if(result.statusCode==0){
	            		bootbox.alert({
	    					title : "成功",
	    				    message : "保存成功!",
	    				    size : 'small'
	    				});
	            	}
	    			
	            },
	            error: function (msg) {
	                console.log(["error" + msg]);
	            }
	        }); 
			
			
		}
		
		
		
		//保存新版本
		function saveNewVersion(){
			$.get("initSaveTemplateData?templateId=" + $("#templateId").val(),
					function(data) {
						var editContext = data;
		
						bootbox.dialog({
							title : '保存报表数据',
							message : editContext,
							buttons : {
								ok : {
									label : "保存",
									className : 'btn-info',
									callback : function() {
										saveVersion();
									}
								},
								cancel : {
									label : "关闭",
									className : 'btn-default',
									callback : function() {
		
									}
								}
		
							}
						})
					});
		}
		
		
		
		function saveDataToNewVersion(versionName, versionNo){
			var postData = {
					name : versionName,
					version : versionNo,
					templateVersionId : $("#templateVersionId").val()
			};
			
			$.ajax({
				type : "POST",
				url : "saveTemplateVersion",
				data : postData,
				timeout : 100000,
	            success: function (res) {
	            	console.log([res]);
	            	var result = JSON.parse(res);
	            	if(result.statusCode==0){
	            		bootbox.alert({
	    					title : "成功",
	    				    message : "保存成功!",
	    				    size : 'small'
	    				});
	            	}
	    			
	            },
	            error: function (msg) {
	                console.log(["error" + msg]);
	            }
	        }); 
			
		}
		


		
</script>

</body>
</html>