<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<link rel="Shortcut Icon" href="images/Logo-YS.ico">
<script type="text/javascript" src="jquery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/artegen.js"></script>
<link rel="stylesheet" type="text/css" href="bootstrap/css/font-awesome.min.css" />
<script type="text/javascript" src="js/logout.js"></script>
</head>
<body>
	<jsp:include page="/pages/plugins/include_title_head.jsp" />
	<div id="main-content2">
		<div class="container-fluid">
			<br>
			<br>
			<div class="panel panel-success">
				<div class="panel-heading">
					<strong><i class="fa fa-hand-peace-o fa-2x"></i><span
						class="h3">&nbsp;操作完毕！</span></strong>
				</div>
				<div class="panel-body">
					<br>
					<div>
						<div class="h5">${msg}</div>
						<div class="h5">
							<span id="mytime">5</span>秒后跳转到<a href="<%=basePath%>${url}">首页</a>！
						</div>
					</div>
					<br>
					<script type="text/javascript">
						var goUrl = "<%=basePath%>${url}";
						goTime();
					</script>
				</div>
				<div class="panel-footer" style="height:80px;">
					<jsp:include page="/pages/plugins/include_alert.jsp" />
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/pages/plugins/include_title_foot.jsp" />
	<jsp:include page="/pages/plugins/include_javascript_foot.jsp" />
</body>
</html>
