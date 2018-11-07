<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/commonInclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../common/headInclude.jsp"%>
<style>
.odd {
	background-color: #f9f9f9;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="background-color: #ECF0F5;">
		<header class="main-header">

			<!-- Logo -->
			<a href="index" class="logo"> <span class="logo-mini"><b>机</b>灵</span>
				<span class="logo-lg"><b>机灵</b>报表</span>
			</a>

			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="push-menu"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>

			</nav>
		</header>


		<div>
			<section class="content-header">
				<h1>
					模板： ${template.name }, <small> 编辑数据集 </small> <small> <select
						class="form-control" id="dataSet">
							<c:forEach var="dataSetItem" items="${dataSets }">
								<option value="${dataSetItem.dataSetId }">${dataSetItem.name }</option>
							</c:forEach>
					</select>
					</small>
				</h1>
				<ol class="breadcrumb">					
					<li class="active">
						<button type="button" class="btn btn-success btn-block btn-sm"
							onclick="saveTemplateData()">保存成报表</button>
					</li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<input type="hidden" id="templateId" value="${template.id }">
				<input type="hidden" id="templateVersionId"
					value="${templateVersionId }">
				<div class="row">

					<div class="col-md-10">

						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">数据编辑区</h3>
							</div>


							<div class="box-body">
								<table id="reportDataTbl"
									class="table table-bordered table-striped">

								</table>
							</div>
						</div>
					</div>


					<div class="col-md-2">

						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">报表编辑记录</h3>
							</div>


							<div id="editLogs" class="box-body">
								<input type="hidden" id="editUserName" value="${editUserName }"> 
							</div>
						</div>
					</div>

				</div>
			</section>
		</div>

	</div>
	<script type="text/javascript">
		var editDatas = new Array(); 
		
		Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "H+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
	
		$(function() {
			var dataSetId = $("#dataSet").val();
			
			if (dataSetId == null || dataSetId == undefined || dataSetId == "") {
				bootbox.alert({
					title : "提示",
					message : "没有找到报表数据集，无法编辑。",
					size : 'small'
				});
				return;
			}

			var url = "getReportVersionTblData?templateId="
					+ $("#templateId").val() + "&dataSetId=" + dataSetId
					+ "&templateVersionId=" + $("#templateVersionId").val();

			$.ajax({
				url : url,
				type : 'GET',
				success : function(data) {
					var item;
					$.each(data, function(i, result) {
						if (i % 2 === 0) {
							item = "<tr>";
						} else {
							item = "<tr class='odd'>";
						}

						if (i == 0) {
							for ( var name in result) {
								item = item + "<th>" + name + "</th>";								
							}
							item = item + "</tr><tr>";
						}
						
						for ( var name in result) {
							item = item
									+ "<td ondblclick=\"editTblContent(this, "
									+ i + ", '" + name + "' , '" + result[name]
									+ "' )\">" + result[name] + "</td>";
						}
						
						
						item = item + "</tr>"
						$('#reportDataTbl').append(item);
					});
				}
			})

		});

		function editTblContent(obj, rowIndex, columnName, txtValue) {
			var td = $(obj);
			var input = $("<input type='text' value='" + txtValue +   "'/>");
			$(obj).html(input);
			//选中td的内容
			$('input').select();

			$('input').click(function() {
				return false;
			});

			$('input').blur(function() {
				var newtxt = $(this).val();
				console.log("xxx", newtxt, txtValue);
				if(txtValue == newtxt){
					td.html(newtxt);
				}else{
					var showTxt = "<span style='color:blue;font-weight:bold;'>"
						+ newtxt + "</span>";
					td.html(showTxt);
	
					updateChangeRecord(rowIndex, columnName, txtValue, newtxt);
					
					editDatas.push(columnName + "&" + rowIndex + "&" + txtValue + "&" + newtxt);
				}
				
			});
		}
		
		
		//添加一条显示记录
		function updateChangeRecord(rowIndex, columnName, orginValue, newValue){
			var editUserName = $("#editUserName").val();
			var editItem = "<div class='direct-chat-messages' style='height:auto;'>"
			 				+ "<div class='direct-chat-info clearfix'>"
			 					+ "<span class='direct-chat-name pull-left'>" + editUserName
			 					+ "</span> <span class='direct-chat-timestamp pull-right'>"
			 					+ new Date().Format("yyyy-MM-dd HH:mm:ss") 
			 					+ "</span>"
			 				+	"</div>"
			 				
			 				+	"<div class='direct-chat-text'>"
			 				+   "第" + rowIndex + "行" + columnName + "列的值由【" + orginValue + "】更改成【" + newValue + "】"
							+	"</div></div>";
		
			$("#editLogs").append(editItem);				
		}
		

		//保存数据
		function saveTemplateData() {
			var dataSetId = $("#dataSet").val();
			var templateVersionId = $("#templateVersionId").val();
			
			if(editDatas.length==0){
				bootbox.alert({
					title : "提示",
					message : "报表数据没有修改，无需保存!",
					size : 'small'
				});
				return;
			}

			var postData = {
				templateVersionId : templateVersionId,
				dataSetId : dataSetId,
				changeDatas : editDatas
			};

			$.ajax({
				type : "POST",
				url : "changeTemplateReportData",
				data : postData,
				timeout : 100000,
				success : function(res) {
// 					console.log([ res ]);
// 					var result = JSON.parse(res);
// 					if (result.statusCode == 0) {
// 						bootbox.alert({
// 							title : "成功",
// 							message : "保存成功!",
// 							size : 'small'
// 						});
// 					}
					
					editDatas = new Array(); 
					
					saveNewVersion();

				},
				error : function(msg) {
					console.log([ "error" + msg ]);
				}
			});

		}

		//保存新版本
		function saveNewVersion() {
			$.get("initSaveTemplateData?templateId=" + $("#templateId").val(),
					function(data) {
						var editContext = data;

						bootbox.dialog({
							title : '保存报表数据',
							message : editContext,
							buttons : {
								ok : {
									label : "保存",
									className : 'btn-info',
									callback : function() {
										saveVersion();
									}
								},
								cancel : {
									label : "关闭",
									className : 'btn-default',
									callback : function() {

									}
								}

							}
						})
					});
		}

		function saveDataToNewVersion(versionName, versionNo) {
			var postData = {
				name : versionName,
				version : versionNo,
				templateVersionId : $("#templateVersionId").val()
			};

			$.ajax({
				type : "POST",
				url : "saveTemplateVersion",
				data : postData,
				timeout : 100000,
				success : function(res) {
					console.log([ res ]);
					var result = JSON.parse(res);
					if (result.statusCode == 0) {
						bootbox.alert({
							title : "成功",
							message : "保存成功!",
							size : 'small'
						});
					}

				},
				error : function(msg) {
					console.log([ "error" + msg ]);
				}
			});

		}
	</script>

</body>
</html>