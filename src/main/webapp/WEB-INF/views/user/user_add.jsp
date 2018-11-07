<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="assets/js/crypto-js-3.1.9/crypto-js.js"></script>
<script type="text/javascript" src="assets/js/crypto-js-3.1.9/tripledes.js"></script>
<script type="text/javascript" src="assets/js/crypto-js-3.1.9/mode-ecb.js"></script>


<div class="box-body">
	<div class="register-box" style="width:460px;margin:1% auto;">
		<div class="register-box-body">
						
			<form id="sysUserForm" action="../../index.html" method="post">
				<c:if test="${sysUser.id!=null && sysUser.id!=0 }">
					<input type="hidden" name="id" class="form-control" placeholder="用户名" value="${sysUser.id }">
				</c:if>
				<div class="form-group has-feedback">
					<input type="text" name="userName" class="form-control" placeholder="用户名,长度为3~20" value="${sysUser.userName }">
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="text" name="nickName" class="form-control" placeholder="昵称" value="${sysUser.nickName }">
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				
				<c:if test="${sysUser.id==null || sysUser.id==0 }">
					<div class="form-group has-feedback">
						<input type="hidden" id="encryptedPwd" name="userPwd" class="form-control" placeholder="密码"> 
						<input type="password" id="password" class="form-control" placeholder="密码"> 
						<span class="glyphicon glyphicon-lock form-control-feedback"></span>
					</div>
				</c:if>
				
				<div class="form-group has-feedback">
					<input type="email" name="userMail" class="form-control" placeholder="邮箱，xx@xx.com" value="${sysUser.userMail }">
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				
				<div class="form-group has-feedback">
					<input type="text" name="userTel" class="form-control" placeholder="电话" value="${sysUser.userTel }"> 
					<span class="glyphicon glyphicon-phone form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<textarea class="form-control" name="userDescription" rows="3" placeholder="用户简介 ...">${sysUser.userDescription }</textarea>
					<span class="glyphicon glyphicon-paw form-control-feedback"></span>
				</div>
			</form>
		</div>
	</div>


</div>
			

<SCRIPT>
	//DES加密
	function encryptByDES(message, key) {
	    var keyHex = CryptoJS.enc.Utf8.parse(key);
	    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
	        mode: CryptoJS.mode.ECB,
	        padding: CryptoJS.pad.Pkcs7
	    });
	    return encrypted.toString();
	}

	function saveUser(){
		
		var pass = $("#password").val();			
		
		$.ajax({
			type : 'POST',
			url : "randomSalt",
			cache : false,
			async : false,
			success : function(salt) {
				console.log(["salt", salt]);
				var encyptPass = encryptByDES(pass, salt);
				$("#encryptedPwd").val(encyptPass);			
				//提交表单
				$.post({
			         url : 'saveUser',
			         data : $("#sysUserForm").serializeArray(),
			         success : function(res) {
			        	 bootbox.alert({
			 				title : "提示",
			 			    message : "保存成功!",
			 			    size : 'small'
			 			 });
			        		 
			        	 freshMenu();
			         },
			         error : function(msg){
			        	 bootbox.alert({
			 				title : "提示",
			 			    message : "保存失败，参数不正确!",
			 			    size : 'small'
			 			 });
			         }
			    })
			},
			error : function(message) {

			}
		});
	}
	
	
	function goback(){
		$.get("initUserList", function(data) {
			$("#widgetContent").html(data);
		});
	}
	
	
</SCRIPT>



