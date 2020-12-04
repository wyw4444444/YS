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
				<!-- 選擇條件查詢 -->
				<div class="input-group" style="width:200px;">
					<select class="form-control" id="TaskType" style="width:100px;">
						<option value="1">延误的任务</option>
						<option value="2">5天内到期</option>
					</select>
					
					<button class="btn btn-secondary form-control" onclick="loadTask()">查詢</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12 col-lg-12">
		<table id="contentTask" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
</body>
</html>
