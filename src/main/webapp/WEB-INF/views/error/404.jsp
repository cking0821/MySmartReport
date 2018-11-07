<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>西智报表~404</title>
<link rel="stylesheet" href="<%=basePath%>assets/adminlte/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/adminlte/bower_components/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/adminlte/bower_components/Ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/adminlte/dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/adminlte/dist/css/skins/_all-skins.min.css">

</head>
<body>
	<div class="content-wrapper" style="margin-left:0px;">		

		<!-- Main content -->
		<section class="content">
			<div class="error-page" style="margin-top: 200px;">
				<h2 class="headline text-yellow">404</h2>

				<div class="error-content">
					<h3>
						<i class="fa fa-warning text-yellow"></i> 天呐! 页面找不到了。
					</h3>

					<p>
						你到底做错了什么事导致触犯了天怒。点
						<a href="<%=basePath%>index">这里</a> 祈祷一下吧.或者点下面找找天神喜欢的东西。
					</p>

					<form class="search-form">
						<div class="input-group">
							<input type="text" name="search" class="form-control" placeholder="搜索">

							<div class="input-group-btn">
								<button type="submit" name="submit"
									class="btn btn-warning btn-flat">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
						<!-- /.input-group -->
					</form>
				</div>
				<!-- /.error-content -->
			</div>
			<!-- /.error-page -->
		</section>
		<!-- /.content -->
	</div>
</body>
</html>