<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="assets/js/crypto-js-3.1.9/crypto-js.js"></script>
<script type="text/javascript" src="assets/js/crypto-js-3.1.9/tripledes.js"></script>
<script type="text/javascript" src="assets/js/crypto-js-3.1.9/mode-ecb.js"></script>

<section class="content-header">
	<h1>
		${reportUser.userName }
	</h1>
</section>

<!-- Main content -->
<section id="mainContent" class="content container-fluid">
	<div class="box-body">
		<div class="box">

			<div class="register-box" style="width:460px;margin:1% auto;">
				<div class="register-box-body">
					
					<form id="sysUserForm" action="../../index.html" method="post">						
						<input type="hidden" name="id" value="${reportUser.id }">
						
						<div class="form-group has-feedback">
							<input type="text" name="nickName" class="form-control" placeholder="昵称" value="${reportUser.nickName }">
							<span class="glyphicon glyphicon-user form-control-feedback"></span>
						</div>
						
						<div class="form-group has-feedback">
							<input type="email" name="userMail" class="form-control" placeholder="邮箱" value="${reportUser.userMail }">
							<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
						</div>
						
						<div class="form-group has-feedback">
							<input type="text" name="userTel" class="form-control" placeholder="电话" value="${reportUser.userTel }"> 
							<span class="glyphicon glyphicon-phone form-control-feedback"></span>
						</div>
						<div class="form-group has-feedback">
							<textarea class="form-control" name="userDescription" rows="3" placeholder="用户简介 ...">${reportUser.userDescription }</textarea>
							<span class="glyphicon glyphicon-paw form-control-feedback"></span>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<button type="button" onclick="goback()" class="btn btn-default btn-block btn-flat">返回</button>
							</div>
							<!-- /.col -->
							<div class="col-xs-6">
								<button type="button" onclick="saveUser()" class="btn btn-primary btn-block btn-flat">保存</button>
							</div>
							<!-- /.col -->
						</div>
					</form>
				</div>
			</div>


		</div>
			<!-- /.box-body -->
		</div>
</section>

<SCRIPT>
	//DES加密
	

	function saveUser(){
		
		$.post({
	         url : 'saveUserProfile',
	         data : $("#sysUserForm").serializeArray(),
	         success : function(res) {
	        	 if(res.statusCode==0){
	        		 goback();
	        	 }
	         
	         },
	         error : function(msg){
	        	 alert(msg);
	         }
	    })
	}
	
	
		
	
</SCRIPT>



