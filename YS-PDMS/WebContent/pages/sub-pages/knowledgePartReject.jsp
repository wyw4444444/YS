<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="js/knowledge.js"></script>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<div class="input-group">
					<input class="form-control" type="text" placeholder="输入料號" id="docSearchKeyWord" name="docSearchKeyWord"/>
					<button class="btn btn-secondary form-control" onclick="loadProcessedPart('3')">查詢</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
		<table id="contentDept" class="table-sm table-striped">
		</table>
	</div>

	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	
</body>
</html>
