<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<div class="form-inline" id="toolbar">
		<label><input type='radio' name='importTableName' value="warehouseIn">&nbsp;批量入库</label>&nbsp;&nbsp;&nbsp;&nbsp;
		<label><input type='radio' name='importTableName' value="warehouseOut">&nbsp;批量出库</label>&nbsp;&nbsp;&nbsp;&nbsp;
		<div class="custom-file form-control">
			<label class="form-control custom-file-label" for="importDataFile" id="importDataFileLabel">选择文件</label>
			<input type="file" class="form-control custom-file-input" id="importDataFile" name="filename" accept = '.xls,.xlsx'>
		</div>
		&nbsp;&nbsp;
		<button class="btn btn-secondary" onclick="importDataToExcel()">导入Excel</button>&nbsp;&nbsp;
		<button class="btn btn-warning" onclick="importToSystem()">存入系统</button>
		&nbsp;&nbsp;
		<span id="importDataExcelMsg" style="color: red;"></span>
	</div>
	<br>
	<div id="contentDataImportDiv" class="col-md-8 col-lg-8">
	</div>
	<jsp:include page="/pages/plugins/include_alert.jsp" />
</body>
</html>
