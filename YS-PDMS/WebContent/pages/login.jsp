<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="Shortcut Icon" href="images/Logo-YS.ico">
<script type="text/javascript" src="jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="jquery/additional-methods.min.js"></script>
<script type="text/javascript" src="jquery/Message_zh_TW.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/login.css" />
<script type="text/javascript" src="js/login.js"></script>
<link rel="stylesheet" type="text/css" href="bootstrap/css/font-awesome.min.css" />
</head>
<body class="d-flex">
	<div class="container">
		<div id="titleDiv" class="row">
			<img id="namePDMS" class="col-lg-8 offset-lg-2 col-md-12 col-sm-12 img-fluid" alt="PDMS Name" src="images/namePDMS.png" />
		</div>
		<div class="row">
			<div id="loginFormDiv" class="col-md-10 offset-md-1 container">
				<div class="row">
					<div class="col-lg-7 d-none d-lg-block bgLogin" id="bgLoginDiv">
						<span><img id="bgLogin" class="img-fluid" alt="bgLogin" src="images/bgLogin.jpg" /></span>
					</div>
					<div class="card col-lg-5 ml-0 mr-0 pr-0 pl-0">
						<div class="card-header">
							<strong><i class="fa fa-user fa-1x"></i><span class="h5">&nbsp;&nbsp;用户登录</span></strong>
						</div>
						<div class="card-body">
							<form id="myform" method="post" role="form" style="margin: 0 auto;">
								<table style="width:100%;margin: 0px;padding: 0px;border-collapse: separate; border-spacing: 0px 12px;">
									<tr>
										<td style="padding:0px;"><label class="control-label" for="mid">用户名：</label></td>
										<td colspan="2"><input class="form-control input-sm"
											type="text" name="mid" id="mid" placeholder="请输入用户名..." value="admin"/>
										</td>
										<td style="padding:0px;">
											<div id="midMsg" style="margin-left: 3px; color: red; font-size: 14px;">*</div>
										</td>
									</tr>
									<tr>
										<td><label class="control-label" for="password">密&nbsp;&nbsp;&nbsp;码：</label>
										</td>
										<td colspan="2"><input class="form-control input-sm"
											type="password" name="password" id="password"
											placeholder="请输入密码..." value="111"/>
										</td>
										<td>
											<div id="passwordMsg" style="margin-left: 3px; color: red; font-size: 14px;">*</div>
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<label class="control-label col-6" for="rememberMe">
												<input type="checkbox" name="rememberMe" id="rememberMe" value="true" />&nbsp;下次免登录
											</label>
										</td>
									</tr>
								</table>
								<div class="col-8 offset-3">
									<input type="submit" value="登录" class="btn btn-primary" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="reset" value="重置" class="btn btn-warning" />
								</div>
							</form>
						</div>
						<div class="card-footer text-right">
							<i class="fa fa-warning fa-1x"></i>&nbsp;&nbsp;忘记密码，请联系管理员
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="footDiv" class="col-md-10 offset-md-1">
				<jsp:include page="/pages/plugins/include_title_foot.jsp" />
			</div>
		</div>
	</div>
	<jsp:include page="/pages/plugins/include_javascript_foot.jsp" />
</body>
</html>