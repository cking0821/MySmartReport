<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/commonInclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>机灵报表</title>
	
	<link rel="shortcut icon" href="<%=basePath%>assets/images/favicon.ico" />
	<!-- 浏览器根据屏幕自适应宽度 -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<%=basePath%>assets/adminlte/bower_components/bootstrap/dist/css/bootstrap.min.css">
	<!-- 字体样式表 -->
	<link rel="stylesheet" href="<%=basePath%>assets/adminlte/bower_components/font-awesome/css/font-awesome.min.css">
	<!-- 图标 -->
	<link rel="stylesheet" href="<%=basePath%>assets/adminlte/bower_components/Ionicons/css/ionicons.min.css">
	<!-- 主题样式 -->
	<link rel="stylesheet" href="<%=basePath%>assets/adminlte/dist/css/AdminLTE.min.css">
	
	 <link rel="stylesheet" href="<%=basePath%>assets/adminlte/plugins/iCheck/square/blue.css">
	
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
 
 	<script src="<%=basePath%>assets/adminlte/bower_components/jquery/dist/jquery.min.js"></script>
 	<script type="text/javascript" src="<%=basePath%>assets/js/crypto-js-3.1.9/crypto-js.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/crypto-js-3.1.9/tripledes.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/crypto-js-3.1.9/mode-ecb.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/login.js"></script>
 
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a href="../../index2.html"><b>机灵</b>报表</a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg">登 录</p>

			<form id="loginForm" action="<%=basePath%>login" method="post">
				<input id="basePath" type="hidden" value="<%=basePath%>" />
				<div class="form-group has-feedback">
					<input type="text" id="userName" name='userName' class="form-control" placeholder="用户名/邮箱/手机号" value="" onkeypress="pwdPress()" >
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input id="encryptedPwd" type='hidden' class="getinfo" required maxlength='40' name='password' />
					<input id="password" type="password" class="form-control" placeholder="密码" value=""  onkeypress="enterPress()">
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<div class="checkbox icheck">
							<label> <input type="checkbox"> 记住我
							</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<button type="button"  onclick="login()" class="btn btn-primary btn-block btn-flat">
							登录
						</button>
					</div>
					<!-- /.col -->
				</div>
			</form>

			
		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->

	<!-- jQuery 3 -->
	<script src="assets/adminlte/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="assets/adminlte/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- iCheck -->
	<script src="assets/adminlte/plugins/iCheck/icheck.min.js"></script>
	<script>
		$(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' // optional
			});
			
			$("#userName").focus().select();;
			
		});
		
		function pwdPress(e){
			var e = e || window.event;
			if(e.keyCode == 13){   
				$("#password").focus();
			}  
		}
		
		function enterPress(e){
			var e = e || window.event;
			if(e.keyCode == 13){   
				login(); 
			}  
		}
	</script>
</body>
</html>