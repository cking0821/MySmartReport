<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../common/headInclude.jsp"%>



<div class="box box-info">
	<form id="scheduleForm" method="post" action="saveTemplateSchedule" class="form-horizontal">
		<input type="hidden" id="templateId" name="templateId" value="${templateId }">
		<input type="hidden" id="scheduleId" name="id" value="${sysTemplateSchedule.id }">
	
		<div class="box-body">

			<div class="form-group">
				<label for="scheduleTitle" class="col-sm-2 control-label">任务标题</label>

				<div class="col-sm-10">
					<input type="text" class="form-control" id="scheduleTitle" name="scheduleTitle" placeholder="请输入任务标题" value="${sysTemplateSchedule.scheduleTitle }">
				</div>
			</div>


			<div class="form-group">
				<label for="exportType" class="col-sm-2 control-label">报表格式</label>

				<div class="col-sm-10">
					<div class="radio">
						<label> 
							<input type="radio" name="exportType" value="PDF" <c:if test="${sysTemplateSchedule.exportType=='PDF' }">checked</c:if> checked > PDF
						</label> 
						<label> 
							<input type="radio" name="exportType" value="DOC" <c:if test="${sysTemplateSchedule.exportType=='DOC' }">checked</c:if> > WORD
						</label> 
						<label> 
							<input type="radio" name="exportType" value="XLS" <c:if test="${sysTemplateSchedule.exportType=='XLS' }">checked</c:if> > EXCEL
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="schduleTime" class="col-sm-2 control-label">执行时间</label>

				<div class="col-sm-10">
					<div class="input-group">
						<div class="input-group">
							<input type="time" id="schduleTime" name="schduleTime" class="form-control timepicker" value="${sysTemplateSchedule.schduleTime }">
						</div>
					</div>

				</div>
			</div>
			<div class="form-group">
				<label for="scheduleFrequency" class="col-sm-2 control-label">触发类型</label>

				<div class="col-sm-5">
					<select id="scheduleFrequency" name="scheduleFrequency" class="form-control" onchange="changeFrequency()">
						<option value="DAILY" <c:if test="${sysTemplateSchedule.scheduleFrequency=='DAILY'}">selected </c:if>   >每天</option>
						<option value="WEEK" <c:if test="${sysTemplateSchedule.scheduleFrequency=='WEEK'}">selected</c:if> >每周</option>
						<option value="MONTH" <c:if test="${sysTemplateSchedule.scheduleFrequency=='MONTH'}">selected</c:if> >每月</option>
					</select>
				</div>

				<div class="col-sm-5">
					<input type="hidden" id="scheduleFrequencyDetailIndex" value="${sysTemplateSchedule.scheduleFrequencyDetail }">
					<select id="scheduleFrequencyDetail" name="scheduleFrequencyDetail" class="form-control" style="display: none;">
					</select>
				</div>
			</div>


			<div class="form-group">
				<label for="scheduleType" class="col-sm-2 control-label">生效时间</label>

				<div class="col-sm-10">
					<div class="input-group date">
						<div class="input-group-addon">
							<i class="fa fa-calendar"></i>
						</div>
						<input type="text" class="form-control pull-right" id="startTime" name="startTime" data-date-format="yyyy-mm-dd" value="${sysTemplateSchedule.startTime }">

						<div class="input-group-addon">至</div>

						<div class="input-group-addon">
							<i class="fa fa-calendar"></i>
						</div>
						<input type="text" class="form-control pull-right" id="endTime" name="endTime" data-date-format="yyyy-mm-dd"  value="${sysTemplateSchedule.endTime }">
					</div>

				</div>
			</div>


			<div class="form-group">
				<label for="receiveUsers" class="col-sm-2 control-label">接收用户</label>

				<div class="col-sm-10">
					<textarea class="form-control" id="receiveUsers" name="receiveUsers" rows="3" placeholder="请输入用户邮箱地址，多个用户用英文逗号分开 ...">${sysTemplateSchedule.receiveUsers }</textarea>
				</div>
			</div>

			<div class="form-group">
				<label for="scheduleStatus" class="col-sm-2 control-label">立即生效</label>

				<div class="col-sm-10">
					<div class="radio">
						<label> 
							<input type="radio" name="scheduleStatus" value="OPEN" <c:if test="${sysTemplateSchedule.scheduleStatus=='OPEN' }">checked</c:if> checked > 是
						</label> 
						<label> 
							<input type="radio" name="scheduleStatus" value="CLOSED" <c:if test="${sysTemplateSchedule.scheduleStatus=='CLOSED' }">checked</c:if> > 否
						</label>
					</div>
				</div>
			</div>
		</div>
		
	</form>
</div>






<SCRIPT>
	$(function() {
		$('#startTime').datepicker({
			autoclose : true
		})

		$('#endTime').datepicker({
			autoclose : true
		})
		
		var schduleTime = $("#schduleTime").val();
		if(schduleTime==null || schduleTime==undefined || schduleTime==""){
			$("#schduleTime").val("08:00");
		}
		
		var startTime = $("#startTime").val();
		if(startTime==null || startTime==undefined || startTime==""){		
			var d = new Date();
			var year = d.getFullYear();
			var month = d.getMonth() + 1;
			if(month<10){
				month = "0" + month;
			}
			var day = d.getDate();
			if(day<10){
				day = "0" + day;
			}
			var today = year + "-" + month + "-" + day;
			$("#startTime").val(today);
		} 
		
		
 		changeFrequency();
		
		var scheduleFrequencyDetailIndex = $("#scheduleFrequencyDetailIndex").val();
		if(scheduleFrequencyDetailIndex!=null && scheduleFrequencyDetailIndex>0){
			$("#scheduleFrequencyDetail").find("option[value='" + scheduleFrequencyDetailIndex + "']").attr("selected",true);
		}
// 		alert(schedleFrequencyIndex);
		
	})
	
	

	

	function changeFrequency() {
		var scheduleFrequency = $("#scheduleFrequency").val();
		if (scheduleFrequency === "DAILY") {
			$("#scheduleFrequencyDetail").css("display", "none");
			$("#scheduleFrequencyDetail").css("value", "");
		} else if (scheduleFrequency === "WEEK") {
			$("#scheduleFrequencyDetail").css("display", "inline-block");
			$("#scheduleFrequencyDetail").html("");
			
			

			$("#scheduleFrequencyDetail").append("<option value='1' >周一</option>");
			$("#scheduleFrequencyDetail").append("<option value='2'>周二</option>");
			$("#scheduleFrequencyDetail").append("<option value='3'>周三</option>");
			$("#scheduleFrequencyDetail").append("<option value='4'>周四</option>");
			$("#scheduleFrequencyDetail").append("<option value='5'>周五</option>");
			$("#scheduleFrequencyDetail").append("<option value='6'>周六</option>");
			$("#scheduleFrequencyDetail").append("<option value='7'>周日</option>");
		} else if (scheduleFrequency === "MONTH") {
			$("#scheduleFrequencyDetail").css("display", "inline-block");
			$("#scheduleFrequencyDetail").html("");
			
			for (var i = 1, l = 31; i <= l; i++) {
				$("#scheduleFrequencyDetail").append("<option value='" + i + "'>" + i + "号</option>");
			}

		}

	}
	
	//保存任务
	function saveSchedule(){	
		
		$.post({
	    	url : 'saveTemplateSchedule',
	    	 data: $('#scheduleForm').serialize(),
  			success : function(res) {
		 		if(res=="SUCCESS"){
			 	 	bootbox.alert({
						title : "提示",
				    	message : "保存成功!",
				    	size : 'small'
					});
			 	 	
			 	 	showSchedule();
// 	 		 		freshMenu();
	 	 		}
	  		},
			error : function(msg){
				alert(msg);
			}
	    });
		
		
		
		
		
		
		}
</SCRIPT>