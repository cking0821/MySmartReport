<%-----------------------------------------------------------------------------
	Copyright (c) 2004 Actuate Corporation and others.
	All rights reserved. This program and the accompanying materials 
	are made available under the terms of the Eclipse Public License v1.0
	which accompanies this distribution, and is available at
	http://www.eclipse.org/legal/epl-v10.html
	
	Contributors:
		Actuate Corporation - Initial implementation.
-----------------------------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page session="false" buffer="none"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%
    String ctxPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ctxPath+"/";
	HttpSession session = request.getSession();
	boolean editStatus = false;
	String editReportFlag = (String)session.getAttribute("REPORT_EDIT_FLAG");
	if(editReportFlag!=null && "true".equals(editReportFlag)){
		editStatus = true;
	}
%>
<%@ page
	import="org.eclipse.birt.report.presentation.aggregation.IFragment,
				 org.eclipse.birt.report.context.BaseAttributeBean,
				 org.eclipse.birt.report.resource.BirtResources,
				 org.eclipse.birt.report.utility.ParameterAccessor,
				 org.eclipse.birt.report.servlet.ViewerServlet,
				 com.favccxx.report.context.SessionUser,
				 com.favccxx.report.constants.SysConstants"%>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment"
	type="org.eclipse.birt.report.presentation.aggregation.IFragment"
	scope="request" />
<jsp:useBean id="attributeBean"
	type="org.eclipse.birt.report.context.BaseAttributeBean"
	scope="request" />

<%-----------------------------------------------------------------------------
	Toolbar fragment
-----------------------------------------------------------------------------%>

<div style="background-color:#3C8DBC;">

<div ID="toolbar" class="container" >
	<div class=navbar-header>
		<button aria-controls=bs-navbar aria-expanded=false
			class="collapsed navbar-toggle" data-target=#bs-navbar
			data-toggle=collapse type=button>
			<span class=sr-only>Toggle navigation</span> <span class=icon-bar></span>
			<span class=icon-bar></span> <span class=icon-bar></span>
		</button>
		<a href="" class="navbar-brand" style="color:white;">机灵报表</a>
	</div>
	<nav class="collapse navbar-collapse" id=bs-navbar>
		<ul class="nav navbar-nav">			
			<li>
				<INPUT TYPE="image" NAME='export' SRC="birt/images/export.png" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.export")%>"
					ALT="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.export")%>" CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 15px;">
			</li>
			<li>
				<INPUT TYPE="image" NAME='exportReport' SRC="birt/images/report.png" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.exportreport")%>"
					ALT="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.exportreport")%>" CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 15px;">
			</li>
			<li>
				<INPUT TYPE="image" NAME='print' SRC="birt/images/print.png" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.print")%>"
					ALT="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.print")%>" CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 15px;">
			</li>
			<c:if test="<%=editStatus %>">
				<li>
					<INPUT TYPE="image" NAME='edit' formtarget="_blank" value="<%=basePath %>initReportDataTblEdit?templateId=" SRC="birt/images/edit.png" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.edit")%>"
						ALT="<%=BirtResources.getHtmlMessage("birt.viewer.toolbar.edit")%>" CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 15px;">
				</li>
			</c:if>
			
		</ul>
		<%
		String imagesPath = "birt/images/";
	%>
		<ul id="navigationBar" class="nav navbar-nav navbar-right">
			<li>
				<INPUT TYPE="image" SRC="<%=imagesPath + (attributeBean.isRtl() ? "last" : "first") + "_disabled.jpg"%>"
					NAME='first' ALT="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.first")%>" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.first")%>"
					CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 5px;">
			</li>
			<li>
				 <INPUT TYPE="image" SRC="<%=imagesPath + (attributeBean.isRtl() ? "next" : "previous") + "_disabled.jpg"%>"
					NAME='previous' ALT="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.previous")%>" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.previous")%>"
					CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 5px;">
			</li>
			<li>
				 <INPUT TYPE="image" SRC="<%=imagesPath + (attributeBean.isRtl() ? "previous" : "next") + "_disabled.jpg"%>"
					NAME='next' ALT="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.next")%>" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.next")%>"
					CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 5px;">
					
				<INPUT TYPE="image" SRC="<%=imagesPath + (attributeBean.isRtl() ? "first" : "last") + "_disabled.jpg"%>" NAME='last'
					ALT="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.last")%>" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.last")%>"
					CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 5px;">
			</li>
			<li>
				<INPUT ID='gotoPage' TYPE='text' VALUE='' MAXLENGTH="8" SIZE='5'  style="margin-top:15px;height:20px;padding: 10px 2px 5px 10px;" CLASS="birtviewer_navbar_input"> 
				
				
			</li>
			<li>
				<INPUT TYPE="image" SRC="<%=imagesPath + (attributeBean.isRtl() ? "go.jpg" : "go.jpg")%>" NAME='goto'
					ALT="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.goto")%>" TITLE="<%=BirtResources.getHtmlMessage("birt.viewer.navbar.goto")%>"
					CLASS="birtviewer_clickable" style="line-height:20px;padding: 10px 15px;">
			</li>
			<li>
				<div style="display: inline-block;height:50px; line-height:40px; padding: 10px 15px; color: #FFF;">
					第<span id='pageNumber'><%="" + attributeBean.getReportPage()%></span>页，
					共<span id='totalPage'></span>页 
				</div>
			</li>
			<li>
			
			</li>
			<li>
			
			</li>
			<li>
			
			</li>
		</ul>
	</nav>
</div>

</div>







