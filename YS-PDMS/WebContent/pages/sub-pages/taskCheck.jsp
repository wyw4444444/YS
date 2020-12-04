<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<!-- 引入JExcel相關的文件 -->
<script type="text/javascript" src="JExcel/js/jquery.csv.min.js"></script>
<script type="text/javascript" src="JExcel/js/jquery.jexcel.js"></script>
<script type="text/javascript" src="JExcel/js/jquery.jcalendar.js"></script>
<link type="text/css" rel="stylesheet" href="JExcel/css/jquery.jexcel.css">
<link type="text/css" rel="stylesheet" href="JExcel/css/jquery.jcalendar.css">

<script type="text/javascript" src="js/xlsx.full.min.js"></script>
</head>


<style>
	.table .thead-blue th {
		color: #fff;
		background-color: #3195f1;
		border-color: #0d7adf;
	}
</style>

<body>
	<!-- 導入頭部標題欄頁面 -->
	<div id="main" style="padding-top:10px;">
		<div class="container-fluid">
			<div class="page">
				
				<div class="form-inline">
					<div class="input-group" id="importOrderExcelDiv">
						<div class="input-group-prepend">
							<span class="input-group-text">专案编号</span>
						</div>
						<input type="text" class="form-control" style="width:150px;"
						id="modalPDR_id2" name="modalPDR_id2" />
						
						<input type="text" class="form-control"  style="width:300px;"
						id="modalDescription2" name="modalPDR_id2" />
						
						<div class="input-group-prepend">
							<button class="btn btn-warning" onclick="addPDRDetail()">儲存任务记录</button>
						</div>
						<span id="importOrderExcelMsg" style="margin-left:10px;padding-top:8px;color:red;"></span>
					</div>
				</div>
				
				<hr size="3" width="99%" style="border: 1 dashed;">
				<div id='orderData'></div>
				
				<div class="alert alert-success fade show" role="alert" id="alertDiv" style="display: none;">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="alertText"></span>
				</div>
				
				
			</div>
		</div>
	</div>
</body>
</html>
