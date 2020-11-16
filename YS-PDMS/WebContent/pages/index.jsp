<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/pages/plugins/include_javascript_head.jsp" />
</head>
<body>
	<jsp:include page="/pages/plugins/include_title_head2.jsp" />
	<div id="main-content">
		<div class="container-fluid">
			<div class="page">
				<shiro:user>
					<br>
					<br>
					<div id="welcomeDiv-1">欢迎进入</div>
					<div id="welcomeDiv-2">产品开发及管理系统</div>
				</shiro:user>
			</div>
			<jsp:include page="/pages/plugins/include_title_foot.jsp" />
		</div>
	</div>
	<jsp:include page="/pages/plugins/include_javascript_foot.jsp" />
	<shiro:user>
		<script type="text/javascript" src="charts/echarts.min.js"></script>
	</shiro:user>
</body>
</html>
