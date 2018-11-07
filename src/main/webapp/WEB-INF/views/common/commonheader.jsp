<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Main Header -->
<header class="main-header">

	<!-- Logo -->
	<a href="index" class="logo"> 
		<span class="logo-mini"><b>机</b>灵</span> 
		<span class="logo-lg"><b>机灵</b>报表</span>
	</a>

	<!-- Header Navbar -->
	<nav class="navbar navbar-static-top" role="navigation">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="push-menu"
			role="button"> <span class="sr-only">Toggle navigation</span>
		</a>
		
		<span>
<!-- 			<img src="assets/images/banner.png" style="height:50px;"> -->
		</span>
		
		<!-- Navbar Right Menu -->
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">		
				 <li class="dropdown messages-menu">
		            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
		              <i class="fa fa-envelope-o"></i>
		              <span class="label label-success"></span>
		            </a>
		          </li>				
				
				<li class="dropdown user user-menu">
					<!-- Menu Toggle Button --> 
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
					<!-- The user image in the navbar--> 
					
						<span class="hidden-xs">${reportUser.userName }</span>
				</a>
					<ul class="dropdown-menu">
						
						<!-- Menu Footer-->
						<li class="user-footer">
<!-- 							<div class="pull-left"> -->
<!-- 								<a href="#" class="btn btn-default btn-flat" onclick="showProfile()">用户信息</a> -->
<!-- 							</div> -->
							
							<div class="pull-right">
								<a href="logout" class="btn btn-default btn-flat">退出</a>
							</div>
						</li>
					</ul>
				</li>
				
			</ul>
		</div>
	</nav>
</header>