<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="box">
	<!-- /.box-header -->
	<table id="versionTable" class="table table-bordered table-striped table-hover">
		<thead>
			<tr>
				<th>序号</th>			
				<th>Id</th>						
				<th>版本名称</th>
				<th>版本号</th>
				<th>生成时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="versionItem" items="${versionList }" varStatus="status">
				<tr>	
					<td>${status.index + 1}</td>	
					<td>${versionItem.id }</td>				
					<td title="${versionItem.detail }" style="color:blue;cursor:pointer;" >${versionItem.name }</td>						
					<td>${versionItem.version }</td>
					<td>${versionItem.updateTime }</td>
					<td>
						<span class="badge bg-yellow" style="cursor:pointer;" onclick="viewReportVersion('${versionItem.templateFileName}', '${versionItem.id }')">查看</span>
						<span class="badge bg-red"  style="cursor:pointer;" onclick="deleteReportVersion('${versionItem.id }', '${versionItem.name }')">删除</span>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
		

<SCRIPT>
	var selectedRow = null;
	
	$(function() {
		var table = $('#versionTable').DataTable();
		
	    $('#versionTable tbody').on('click', 'tr', function () {	
	    	if($(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else{
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    	
	    	selectedRow = table.row( this ).data();
	    } );
	});

	$('#versionTable').DataTable({
		'paging' : false,
		'lengthChange' : false,
		'searching' : false,
		'ordering' : true,
		'info' : true,
		'autoWidth' : true,
		"columnDefs": [
            {
                "targets": [ 1 ],
                "visible": false,
                "searchable": false
            }
        ]
	});
	
	
	//查看报表版本
	function viewReportVersion(templateName, templateVersionId){
		var url = "<%=basePath%>frameset?__report=" + templateName + "&templateVersionId=" + templateVersionId;    	
    	window.open(url);
	}
	
	
	//删除当前的报表版本
	function deleteReportVersion(versionId, versionName){
		bootbox.confirm({
		    title: "提示",
		    message : "确定要删除 [" + versionName + "] 吗？",
		    callback: function (result) {
		    	var postData = {
		    			versionId : versionId
		    	};
		    	$.ajax({
					type : "POST",
					url : "deleteTemplateVersion",
					data : postData,
					timeout : 100000,
		            success: function (res) {
		            	var table = $('#versionTable').DataTable();
		            	table.row('.selected').remove().draw( false );
		            	console.log(res);
		            	bootbox.alert("删除成功。");
		            },
					error : function(message) {

					}
			    });
		    }
		});
	}
	
</SCRIPT>



