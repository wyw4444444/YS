<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<shiro:hasAnyRoles name="super_admin,admin">
					<button class="btn btn-danger" onclick="rehandleAwait()">退回</button>&nbsp;
					<button class="btn btn-success" onclick="approveAwait()">核准</button>&nbsp;
					<button class="btn btn-secondary" onclick="loadAwait('1')">待审核</button>&nbsp;
				</shiro:hasAnyRoles>
				<button class="btn btn-secondary" onclick="loadAwait('2')">待处理</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadAwait('3')">已发起</button>&nbsp;
			</div>
		</div>
	</div>
	<div class="col-md-10 col-lg-10">
		<table id="contentAwait" class="table-sm table-striped">
		</table>
	</div>
	<!-- <HR> -->
	<jsp:include page="/pages/plugins/include_alert.jsp" />
</body>
</html>
