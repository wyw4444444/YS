<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<button class="btn btn-primary" onclick="closingAccount()">库存整理</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadClosingAccount()">所有整理记录</button>
			</div>
		</div>
	</div>
	
	<div class="col-md-4 col-lg-4">
		<table id="contentClosingAccount" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
</body>
</html>
