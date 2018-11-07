<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="box">
	<!-- /.box-header -->
	<table id="reportTable" class="table table-bordered table-striped table-hover">
		<thead>
			<tr>
				<th>序号</th>			
				<th>Id</th>						
				<th>定时任务名称</th>
				<th>报表类型</th>
				<th>生成时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="reportItem" items="${reportList }" varStatus="status">
				<tr>	
					<td>${status.index + 1}</td>	
					<td>${reportItem.id }</td>				
					<td>${reportItem.scheduleName }</td>						
					<td>${reportItem.reportType }</td>
					<td>${reportItem.reportDate }</td>
					<td>
						<span class="badge bg-yellow" style="cursor:pointer;" onclick="download('${reportItem.reportFileName}')">下载</span>		
					</td>				
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
		

<SCRIPT>
	var selectedRow = null;
	
	$(function() {
		var table = $('#reportTable').DataTable();
		
	    $('#reportTable tbody').on('click', 'tr', function () {	
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

	$('#reportTable').DataTable({
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
	function download(filename){
		var url = "<%=basePath%>downloadReport?filename=" + filename;    	
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
		            	var table = $('#reportTable').DataTable();
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



