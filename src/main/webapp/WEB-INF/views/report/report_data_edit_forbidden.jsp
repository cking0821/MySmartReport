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



<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<script src="<%=basePath%>assets/adminlte/bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>assets/js/jsonviewer.js"></script>
<script src="<%=basePath%>assets/adminlte/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="<%=basePath%>assets/js/bootbox.min.js"></script>

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
					${message }
				</h1>
			</section>
		</div>

	</div>
	<script type="text/javascript">
		
		$(document).ready(function() {
			
		});

		
		
</script>

</body>
</html>