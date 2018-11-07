<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/commonInclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="common/headInclude.jsp"%>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- 头部Logo和消息 -->
		<%@ include file="common/commonheader.jsp"%>
		
		<!-- 左侧导航菜单 -->
		<%@ include file="common/nav.jsp"%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
		
			<div id="widgetContent">
				
			</div>
		
			
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<footer class="main-footer">
			<!-- To the right -->
			<div class="pull-right hidden-xs">机灵报表V7.02</div>
			
			<strong>版权 &copy; 2015 - 2018 <a href="#">机灵时代（中国）有限公司</a>.
			</strong> 
		</footer>

	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->



	<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
<script type="text/javascript">

var currentTargt;

$(function(){  
   
      
    $('#sieul li').click(function(){//点击li加载界面  
    	$(this).addClass("active").siblings().removeClass("active");
        var current = $(this),  
        
   
        
        target = current.find('a').attr('target'); // 找到链接a中的targer的值  
        
        currentTargt = target;
        
        $.get(target,function(data){  
            $("#widgetContent").html(data);   
         });  
    });  
});


	function freshMenu(){
		$.get(currentTargt,function(data){  
            $("#widgetContent").html(data);   
         });  
	}

	function switchWidget(widget){
		$("#widgetContent").attr("src", widget);
	}
	
	function showProfile(){
		$.get("userProfile",function(data){  
            $("#widgetContent").html(data);   
         });
	}

</script>
</body>
</html>