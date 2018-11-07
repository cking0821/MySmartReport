<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<style>

.main-sidebar a{
	cursor:pointer;
    color: #FFFFFF;
}

</style>

<aside class="main-sidebar">

	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">		

		<!-- Sidebar Menu -->
		<ul id="sieul" class="sidebar-menu" data-widget="tree">
			<!-- Optionally, you can add icons to the links -->
			
			<c:forEach var="menuItem" items="${userResources }">
				<c:if test="${menuItem.resourceType=='MENU' }">
					<li>
						<a target="${menuItem.resourceLink }"><i class="${menuItem.resourceCss }"></i> <span>${menuItem.resourceName }</span></a>
					</li>
				</c:if>
			
			</c:forEach>
			
<!-- 			<li> -->
<!-- 				<a target="initProject"><i class="fa fa-dashboard"></i> <span>项目管理</span></a> -->
<!-- 			</li> -->
			
<!-- 			<li> -->
<!-- 				<a target="initDataSource"><i class="fa fa-database"></i> <span>数据源管理</span></a> -->
<!-- 			</li> -->
			
<!-- 			<li> -->
<!-- 				<a target="initTemplate"><i class="fa fa-book"></i> <span>模板管理</span></a> -->
<!-- 			</li> -->
			
<!-- 			<li> -->
<!-- 				<a target="initReport"><i class="fa fa-bar-chart"></i> <span>报表管理</span></a> -->
<!-- 			</li> -->
			
<!-- 			<li> -->
<!-- 				<a  target="initUserList"><i class="fa fa-user"></i> <span>用户管理</span></a> -->
<!-- 			</li> -->
			
<!-- 			<li> -->
<!-- 				<a  target="initRoleList"><i class="fa fa-user-secret"></i> <span>角色管理</span></a> -->
<!-- 			</li> -->
			
			
			
		</ul>
		<!-- /.sidebar-menu -->
	</section>
	<!-- /.sidebar -->
</aside>