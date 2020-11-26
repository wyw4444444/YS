<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/upload.css" />
<script type="text/javascript" src="js/archives.js"></script>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<div class="input-group">
					<input class="form-control" type="text" placeholder="输入料號" id="docSearchKeyWord" name="docSearchKeyWord"/>
					<button class="btn btn-secondary form-control" onclick="loadDoc('1')">所有版本</button>
					<button class="btn btn-info form-control" onclick="loadDoc('2')">最新版本</button>
					<button class="btn btn-primary form-control" onclick="loadDoc('3')">全階查詢</button>
<!-- 					<button class="btn btn-primary form-control" onclick="loadDoc('3')">Bom</button> -->
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-8 col-lg-8">
		<table id="contentDept" class="table-sm table-striped">
		</table>
	</div>

	<jsp:include page="/pages/plugins/include_alert.jsp" />
</body>
</html>
