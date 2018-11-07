<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>西智报表~404</title>
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/bower_components/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/bower_components/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/bower_components/Ionicons/css/ionicons.min.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/dist/css/AdminLTE.min.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/adminlte/dist/css/skins/_all-skins.min.css">

</head>
<body>
	<div class="content-wrapper" style="margin-left: 0px;">

		<section class="content">

			<div class="error-page" style="margin-top:200px;">
				<h2 class="headline text-red">500</h2>

				<div class="error-content">
					<h3>
						<i class="fa fa-warning text-red"></i> 天呐! 服务器居然出错了.
					</h3>

					<p>
						服务器运行一直都是好好的，怎么会出现这种情况呢. 点 <a
							href="<%=basePath%>index">这里</a> 回首页看看吧，或者搜索一下。
					</p>

					<form class="search-form">
						<div class="input-group">
							<input type="text" name="search" class="form-control"
								placeholder="Search">

							<div class="input-group-btn">
								<button type="submit" name="submit"
									class="btn btn-danger btn-flat">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>

		</section>
	</div>
</body>
</html>