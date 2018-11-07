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
<%@ page import="org.eclipse.birt.report.presentation.aggregation.IFragment,
				 org.eclipse.birt.report.resource.BirtResources"%>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment" type="org.eclipse.birt.report.presentation.aggregation.IFragment" scope="request" />

<%-----------------------------------------------------------------------------
	Print report dialog fragment
-----------------------------------------------------------------------------%>



<div class="row">
	<div class="col-sm-10 col-sm-offset-1">
		<div class="form-horizontal">
				<div class="form-group" id="printFormatSetting">
					<label for="printAsHTML" class="col-sm-3 control-label"><%=BirtResources.getMessage("birt.viewer.dialog.print.format")%></label>
				    <div class="col-sm-9">
						<div class="radio">
							<label> 
								<input type="radio" name="printFormat" id="printAsHTML" checked>
								<%=BirtResources.getMessage("birt.viewer.dialog.print.format.html")%>
							</label>
						</div>
						<div>
							<label> 
								<input type="radio" name="printFormat" id="printAsPDF">
								<%=BirtResources.getMessage("birt.viewer.dialog.print.format.pdf")%>
							</label>
							<SELECT	ID="printFitSetting" CLASS="form-control" DISABLED="true" style="display:inline-block;width:auto;">
								<option value="0" selected><%=BirtResources.getMessage( "birt.viewer.dialog.export.pdf.fittoauto" )%></option>
								<option value="1"><%=BirtResources.getMessage( "birt.viewer.dialog.export.pdf.fittoactual" )%></option>
								<option value="2"><%=BirtResources.getMessage( "birt.viewer.dialog.export.pdf.fittowhole" )%></option>
							</SELECT>
						</div>
					</div>
				</div>
			
				<div class="form-group" id="printPageSetting">
					<label for="exportPages" class="col-sm-3 control-label"><%=BirtResources.getMessage("birt.viewer.dialog.page")%></label>
				    <div class="col-sm-9">
				    	<label class="radio-inline">
							<input type="radio" name="printPages" id="printPageAll" checked>
							<%=BirtResources.getMessage("birt.viewer.dialog.page.all")%>
						</label>
						<label class="radio-inline">
							<input type="radio" name="printPages" id="printPageCurrent">
							<%=BirtResources.getMessage("birt.viewer.dialog.page.current")%>
						</label>
						
						
						<label class="radio-inline">
							<INPUT TYPE="radio" ID="printPageRange" NAME="printPages">
							<%=BirtResources.getMessage( "birt.viewer.dialog.page.range" )%>							
						</label>
						
						<label class="radio-inline" style="margin-left:0px;padding-left:0px;">
							<INPUT TYPE="text" CLASS="birtviewer_printreport_dialog_input" ID="printPageRange_input" DISABLED="true" style="width:80px;"/>
						</label>
						
					</div>
				</div>				
		</div>
	</div>
</div>