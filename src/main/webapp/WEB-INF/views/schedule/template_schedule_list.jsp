<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="box">
	<!-- /.box-header -->
	<div class="margin">
		<div class="btn-group">
			<button type="button" onclick="addSchedule()" class="btn btn-success">添加</button>
			<button type="button" onclick="editSchedule()" class="btn btn-info">编辑</button>
			<button type="button" onclick="deleteSchedule()" class="btn btn-danger">删除</button>
		</div>
	</div>
	
	<input type="hidden" id="templateId" value="${templateId }">
	
	<table id="scheduleTable" class="table table-bordered table-striped table-hover">
		<thead>
			<tr>
				<th>序号</th>			
				<th>Id</th>						
				<th>标题</th>
				<th>执行频率</th>
				<th>执行时间</th>
				<th>任务开始时间</th>
				<th>任务结束时间</th>
				<th>任务状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="scheduleItem" items="${list }" varStatus="status">
				<tr>	
					<td>${status.index + 1}</td>	
					<td>${scheduleItem.id }</td>				
					<td>${scheduleItem.scheduleTitle }</td>						
					<td>
						<c:if test="${scheduleItem.scheduleFrequency=='DAILY' }">
							每天
						</c:if>
						<c:if test="${scheduleItem.scheduleFrequency=='WEEK' }">
							
							<c:if test="${scheduleItem.scheduleFrequencyDetail=='1' }">
							每周一
							</c:if>
							<c:if test="${scheduleItem.scheduleFrequencyDetail=='2' }">
							每周二
							</c:if>
							<c:if test="${scheduleItem.scheduleFrequencyDetail=='3' }">
							每周三
							</c:if>
							<c:if test="${scheduleItem.scheduleFrequencyDetail=='4' }">
							每周四
							</c:if>
							<c:if test="${scheduleItem.scheduleFrequencyDetail=='5' }">
							每周五
							</c:if>
							<c:if test="${scheduleItem.scheduleFrequencyDetail=='6' }">
							每周六
							</c:if>
							<c:if test="${scheduleItem.scheduleFrequencyDetail=='7' }">
							每周日
							</c:if>
						</c:if>
						<c:if test="${scheduleItem.scheduleFrequency=='MONTH' }">
							每月${scheduleItem.scheduleFrequencyDetail}号
						</c:if>
					
					</td>
					<td>${scheduleItem.schduleTime }</td>
					<td>${scheduleItem.startTime }</td>
					<td>${scheduleItem.endTime }</td>
					<td>
						<c:if test="${scheduleItem.scheduleStatus=='OPEN' }">
							正常
						</c:if>
						<c:if test="${scheduleItem.scheduleStatus=='CLOSED' }">
							暂停
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
		

<SCRIPT>
	var selectedRow = null;
	
	$(function() {
		var table = $('#scheduleTable').DataTable();
		
	    $('#scheduleTable tbody').on('click', 'tr', function () {	
	    	
	    	$(this).addClass('selectedTb').siblings().removeClass('selectedTb').end();
	    	
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

	$('#scheduleTable').DataTable({
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
	
	
	//添加定时任务
	function addSchedule(){
		$.get("initTemplateScheduleEdit?id=0&templateId=" + $("#templateId").val(), function(data){
			var editContext = data;
			
			bootbox.dialog({
				title: '添加定时任务',
				message: editContext,
				buttons: {
					ok: {
				        label: "保存",
				        className: 'btn-primary',
				        callback: function(){
				        	saveSchedule();
				        }
				    },
				    cancel: {
				        label: "关闭",
				        className: 'btn-default',
				        callback: function(){
				            
				        }
				    }
				    
				}
			})
		});
	}
	
	//编辑
	function editSchedule(){
		if(selectedRow==null){
			bootbox.alert({
			    message: "请选择需要编辑的任务!",
			    backdrop: true
			});
			
		}
		
		var id = selectedRow[1];
		
		$.get("initTemplateScheduleEdit?templateId=" + $("#templateId").val() + "&id=" + id, function(data){
			var editContext = data;
			
			bootbox.dialog({
				title: '编辑定时任务',
				message: editContext,
				buttons: {
					ok: {
				        label: "保存",
				        className: 'btn-primary',
				        callback: function(){
				        	saveSchedule();
				        }
				    },
				    cancel: {
				        label: "关闭",
				        className: 'btn-default',
				        callback: function(){
				            
				        }
				    }
				    
				}
			})
		});
	}
	
	
	//删除
	function deleteSchedule(){
		if(selectedRow!=null){
			bootbox.confirm({
			    title: "提示",
			    message : "确定要删除定时任务 [" + selectedRow[2] + "] 吗？",
			    buttons: {
			        confirm: {
			            label: '确定',
			            className: 'btn-danger'
			        },
			        cancel: {
			            label: '取消',
			            className: 'btn-default'
			        }
			    },
			    callback: function (result) {
			    	if(result!=null && result==true){
			    		$.ajax({
							type : "POST",
							contentType : "application/json;charset=utf-8",
							url : "delTemplateSchedule",
							data : selectedRow[1],
							dataType : 'JSON',
							timeout : 100000,
				            success: function (res) {
				            	console.dir(res);
				            	var table = $('#scheduleTable').DataTable();
				            	table.row('.selected').remove().draw( false );
				            	bootbox.alert("删除成功。");
				            },
							error : function(message) {

							}
					    });
			    	}
			    }
			});
		}
	}
	
</SCRIPT>



