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
				<div class="input-group" style="width:500px;">
					<input class="form-control" type="text" placeholder="输入料号" id="KeyWordPart_Code" name="KeyWordPart_Code" />
					<select class="form-control" id="type_check_select">
						<option value="undefined">选择查询类型</option>
					</select>
					<button class="btn btn-secondary form-control" onclick="loadCheckLog()">查询</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-lg-10">
		<table id="contentCheckLog" class="table-sm table-striped">
		</table>
	</div>

	<jsp:include page="/pages/plugins/include_alert.jsp" />
</body>
</html>
