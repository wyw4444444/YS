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
				<div class="input-group" style="width:400px;">
					<div class="input-group-prepend">
						<span class="input-group-text">PDR编号</span>
					</div>
					<input class="form-control" type="text" placeholder="輸入PDR编号" 
						id="PDR_id" name="PDR_id" style="width:100px;"/>
					<input class="form-control" type="text" 
						id="Description" name="Description" style="display: none; width:100px;"/>
					<button class="btn btn-secondary form-control" onclick="loadPDRCost_Check()">查詢</button>
				</div>
				
				<div class="input-group" style="width:300px; margin-left:20px;">
					<div class="input-group-prepend">
						<span class="input-group-text">预计费用合计：</span>
					</div>
					<input class="form-control" type="text" 
							id="total_estimate" name="total_estimate" style="width:100px;"/>
				</div>	
				
				<div class="input-group" style="width:300px; margin-left:20px;">	
					<div class="input-group-prepend">
						<span class="input-group-text">实际费用合计：</span>
					</div>
					<input class="form-control" type="text" 
							id="total_actual" name="total_actual" style="width:100px;"/>
				</div>	
			</div>
		</div>
	</div>
	<div class="col-md-12 col-lg-12">
		<table id="contentPDRCost" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
</body>
</html>
