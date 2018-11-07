<%-----------------------------------------------------------------------------
	Copyright (c) 2004-2008 Actuate Corporation and others.
	All rights reserved. This program and the accompanying materials 
	are made available under the terms of the Eclipse Public License v1.0
	which accompanies this distribution, and is available at
	http://www.eclipse.org/legal/epl-v10.html
	
	Contributors:
		Actuate Corporation - Initial implementation.
-----------------------------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" buffer="none" %>
<%@ page import="org.eclipse.birt.report.presentation.aggregation.IFragment,
				 org.eclipse.birt.report.context.BaseAttributeBean,
				 org.eclipse.birt.report.resource.ResourceConstants,
				 org.eclipse.birt.report.resource.BirtResources,
				 org.eclipse.birt.report.utility.ParameterAccessor" %>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment" type="org.eclipse.birt.report.presentation.aggregation.IFragment" scope="request" />
<jsp:useBean id="attributeBean" type="org.eclipse.birt.report.context.BaseAttributeBean" scope="request" />

<%
	// base href can be defined in config file for deployment.
	String baseHref = request.getScheme( ) + "://" + request.getServerName( ) + ":" + request.getServerPort( );
	if( !attributeBean.isDesigner( ) )
	{
		String baseURL = ParameterAccessor.getBaseURL( );
		if( baseURL != null )
			baseHref = baseURL;
	}
	baseHref += request.getContextPath( ) + fragment.getJSPRootPath( );
%>

<%-----------------------------------------------------------------------------
	Viewer root fragment
-----------------------------------------------------------------------------%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<HTML lang="<%= ParameterAccessor.htmlEncode( attributeBean.getLanguage() ) %>">
	<HEAD>
		<TITLE><%= ParameterAccessor.htmlEncode( attributeBean.getReportTitle( ) ) %></TITLE>
		<BASE href="<%= baseHref %>" >
		
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; CHARSET=utf-8">
		<LINK REL="stylesheet" HREF="birt/styles/style.css" TYPE="text/css">
		 <link rel="shortcut icon" href="images/favicon.ico" />
		
		<LINK REL="stylesheet" HREF="adminlte/bower_components/bootstrap/dist/css/bootstrap.min.css" TYPE="text/css">
		
		<%
		if( attributeBean.isRtl() )
		{
		%>
		<LINK REL="stylesheet" HREF="birt/styles/dialogbase_rtl.css" MEDIA="screen" TYPE="text/css"/>
		<%
		}
		else
		{
		%>
		<LINK REL="stylesheet" HREF="birt/styles/dialogbase.css" MEDIA="screen" TYPE="text/css"/>	
		<%
		}
		%>
		<script type="text/javascript">			
			<%
			if( request.getAttribute("SoapURL") != null )
			{
			%>
			var soapURL = "<%= (String)request.getAttribute("SoapURL")%>";
			<%
			}
			else
			{
			%>
			var soapURL = document.location.href;
			<%
			}
			%>
			var rtl = <%= attributeBean.isRtl( ) %>;
		</script>
		
		<script src="birt/ajax/utility/Debug.js" type="text/javascript"></script>
		<script src="birt/ajax/lib/prototype.js" type="text/javascript"></script>
		<script src="birt/ajax/lib/head.js" type="text/javascript"></script>
		
		<script type="text/javascript">	
			<%= attributeBean.getClientInitialize( ) %>
		</script>
		
		<!-- Mask -->
		<script src="birt/ajax/core/Mask.js" type="text/javascript"></script>
		<script src="birt/ajax/utility/BrowserUtility.js" type="text/javascript"></script>
		
		<!-- Drag and Drop -->
		<script src="birt/ajax/core/BirtDndManager.js" type="text/javascript"></script>
		
		<script src="birt/ajax/utility/Constants.js" type="text/javascript"></script>
		<script src="birt/ajax/utility/BirtUtility.js" type="text/javascript"></script>
		
		<script src="birt/ajax/core/BirtEventDispatcher.js" type="text/javascript"></script>
		<script src="birt/ajax/core/BirtEvent.js" type="text/javascript"></script>
		
		<script src="birt/ajax/mh/BirtBaseResponseHandler.js" type="text/javascript"></script>
		<script src="birt/ajax/mh/BirtGetUpdatedObjectsResponseHandler.js" type="text/javascript"></script>

		<script src="birt/ajax/ui/app/AbstractUIComponent.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/AbstractBaseToolbar.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtToolbar.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtNavigationBar.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/AbstractBaseToc.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtToc.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/app/BirtProgressBar.js" type="text/javascript"></script>

 		<script src="birt/ajax/ui/report/AbstractReportComponent.js" type="text/javascript"></script>
 		<script src="birt/ajax/ui/report/AbstractBaseReportDocument.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/report/BirtReportDocument.js" type="text/javascript"></script>

		<script src="birt/ajax/ui/dialog/AbstractBaseDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtTabedDialogBase.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/AbstractParameterDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtParameterDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtSimpleExportDataDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtExportReportDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtPrintReportDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtPrintReportServerDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/AbstractExceptionDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtExceptionDialog.js" type="text/javascript"></script>
		<script src="birt/ajax/ui/dialog/BirtConfirmationDialog.js" type="text/javascript"></script>
		
		<script src="birt/ajax/utility/BirtPosition.js" type="text/javascript"></script>
		<script src="birt/ajax/utility/Printer.js" type="text/javascript"></script>

		<script src="birt/ajax/core/BirtCommunicationManager.js" type="text/javascript"></script>
		<script src="birt/ajax/core/BirtSoapRequest.js" type="text/javascript"></script>
		<script src="birt/ajax/core/BirtSoapResponse.js" type="text/javascript"></script>
		
		<script src="adminlte/bower_components/jquery/dist/jquery.min.js" type="text/javascript"></script>
		<script>
			jQuery.noConflict();
	 	</script>
	</HEAD>
	
	<BODY 
		CLASS="BirtViewer_Body"  
		ONLOAD="javascript:init( );" 
		SCROLL="no" 
		LEFTMARGIN='0px' 
		STYLE='overflow:hidden; direction: <%= attributeBean.isRtl()?"rtl":"ltr" %>'
		>
		

		<div id='layout' style="background-color:#fff;">
		
			
			<!-- <div style="width:100%;height:50px;background-color:#333;">
				<div class="nav-header" style="float:left;">
					<a href="#" style="font-size:18px;font-weight:bold;vertical-align:middle;">超神报表</a>
				 </div> 
			
			</div> -->
		
		
		
		
		
			 
			
			<%
				if ( fragment != null )
				{
					fragment.callBack( request, response );
				}
			%>
		</div>
		<input id="templateId" type="hidden" value="${templateId }">
		
		<div id="versionLog" style="position:fixed;background:#6AAAD1;right:0;top:55px;width:500px;overflow: auto;">
		
		</div>
		
	</BODY>

	<%@include file="../common/Locale.jsp" %>	
	<%@include file="../common/Attributes.jsp" %>	

	<script type="text/javascript">
	// <![CDATA[
		var hasSVGSupport = false;
		var useVBMethod = false;
		if ((!!document.createElementNS && !!document.createElementNS(
				'http://www.w3.org/2000/svg', 'svg').createSVGRect)
				|| navigator.mimeTypes != null
				&& navigator.mimeTypes.length > 0
				&& navigator.mimeTypes["image/svg+xml"] != null) {
			hasSVGSupport = true;
		} else {
			useVBMethod = true;
		}

	// ]]>
	</script>
	
	<script type="text/vbscript">
		On Error Resume Next
		If useVBMethod = true Then
		    hasSVGSupport = IsObject(CreateObject("Adobe.SVGCtl"))
		End If
	</script>

	<script type="text/javascript">
		var Mask =  new Mask(false); //create mask using "div"
		var BrowserUtility = new BrowserUtility();
		DragDrop = new BirtDndManager();

		var birtToolbar = new BirtToolbar( 'toolbar' );
		var navigationBar = new BirtNavigationBar( 'navigationBar' );
		var birtToc = new BirtToc( 'display0' );
		var birtProgressBar = new BirtProgressBar( 'progressBar' );
		var birtReportDocument = new BirtReportDocument( "Document", birtToc );

		var birtParameterDialog = new BirtParameterDialog( 'parameterDialog', 'frameset' );
		var birtSimpleExportDataDialog = new BirtSimpleExportDataDialog( 'simpleExportDataDialog' );
		var birtExportReportDialog = new BirtExportReportDialog( 'exportReportDialog' );
		var birtPrintReportDialog = new BirtPrintReportDialog( 'printReportDialog' );
		var birtPrintReportServerDialog = new BirtPrintReportServerDialog( 'printReportServerDialog' );
		var birtExceptionDialog = new BirtExceptionDialog( 'exceptionDialog' );
		var birtConfirmationDialog = new BirtConfirmationDialog( 'confirmationDialog' );

		// register the base elements to the mask, so their input
		// will be disabled when a dialog is popped up.
		Mask.setBaseElements( new Array( birtToolbar.__instance, navigationBar.__instance, birtReportDocument.__instance) );
		
		//读取报表编辑记录
		var templateVersionId = GetQueryString('templateVersionId');
		if(templateVersionId!=null && templateVersionId!=undefined){
			getTemplateVersionLog(templateVersionId);
		}
		
		//查询URL参数
		function GetQueryString(name) {
		   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
		   var r = window.location.search.substr(1).match(reg);
		   if (r!=null) 
			   return unescape(r[2]); 
		   return null;
		}
		
		//读取报表版本编辑记录
		function getTemplateVersionLog(templateVersionId){						
			jQuery.ajax({
				type : "POST",
				contentType : "application/json;charset=utf-8",
				url : "../getTemplateVersionLog",
				data : templateVersionId,
				dataType : 'JSON',
				timeout : 100000,
	            success: function (res) {
	            	if(res.list){
	            		var logList = eval(res.list);
	            		jQuery("#versionLog").append("<h3 style='margin-left:5px;'>编辑历史   <span onclick='closeEditLog()' style='color:gray;float:right;margin-right:10px;cursor:pointer;'>x</span></h3>");
	            		for(var i=0;i<logList.length;i++){
	            			jQuery("#versionLog").append("<h5 style='margin-left:5px;'>" + logList[i].versionLog + "   " + logList[i].updateTime  + "</h5>" );
	            		}
	            	}
	            	console.log(["res", res]);
	            	
	            	
	            	
	            },
	            error: function (xhr, errorType, error) {
	                console.log(["error" + ajaxUrl, errorType, error]);
	            }
	        });
			
		}
		
		
		function closeEditLog(){
			jQuery("#versionLog").css("display", "none");
		}
		
		function init()
		{
			soapURL = birtUtility.initSessionId( soapURL );
			
		<%
		if ( attributeBean.isShowParameterPage( ) )
		{
		%>
			birtParameterDialog.__cb_bind( );
		<%
		}
		else
		{
		%>
			soapURL = birtUtility.initDPI( soapURL );
			navigationBar.__init_page( );
		<%
		}
		%>
		}
		
		// When link to internal bookmark, use javascript to fire an Ajax request
		function catchBookmark( bookmark )
		{	
			birtEventDispatcher.broadcastEvent( birtEvent.__E_GETPAGE, { name : "__bookmark", value : bookmark } );		
		}
		
		
// 		function mainOpen(url){
// 			var ss = document.getElementById("templateId");
// 			console.log("ss", ss);
<%-- 			window.open(url + "<%= (String)request.getAttribute("templateId")%>"); --%>
// 		}
		
	</script>
</HTML>

