<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<html>
<head>
	<link rel="Shortcut Icon" href="images/LogoATG.ico">
	<script type="text/javascript" src="jquery/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/artegen.js"></script>
	<script type="text/javascript" src="js/register.js"></script>
</head>
<body>
	<br>
	<div id="systemRegister"
		class="card col-md-6 col-lg-6 ml-0 mr-0 pr-0 pl-0">
		<div class="card-header">系统激活信息</div>
		<div class="card-body">
			<form id="formSystemRegister">
				<div class="input-group mb-3" id="validate_date_div">
					<div class="input-group-prepend">
						<span class="input-group-text">有&nbsp;&nbsp;效&nbsp;&nbsp;期</span>
					</div>
					<input type="text" class="form-control" id="validity_date" name="validity_date" disabled="disabled"/>
				</div>
				<div class="input-group mb-3" id="register_publicKey_div">
					<div class="input-group-prepend">
						<span class="input-group-text">公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;钥</span>
					</div>
					<textarea class="form-control" rows="6" id="register_publicKey" name="register_publicKey" placeholder="Please input the public key"></textarea>
				</div>
				<div class="input-group mb-3" id="register_code_div">
					<div class="input-group-prepend">
						<span class="input-group-text">注&nbsp;&nbsp;册&nbsp;&nbsp;码</span>
					</div>
					<textarea class="form-control" rows="6" id="register_code" name="register_code" placeholder="Please input the register code"></textarea>
				</div>
				<span id="registerMsg" style="color:red;"></span>
				
				<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
				
				<div style="float:right" id="btn_register_div">
					<button class="btn btn-info" id="btn_isRegister" onclick="checkRegister()">提取激活信息</button>&nbsp;&nbsp;
					<button class="btn btn-info" id="btn_register" onclick="registerSystem()">系统激活</button>
				</div>
			</form>
		</div>
	</div>
</body>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
</html>