<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
</head>


<body>
		<div class="form-inline" >
			<div class="input-group" id="importOrderExcelDiv" style="margin-top:10px;">
					<div class="input-group-prepend">
						<span class="input-group-text">专案编号</span>
					</div>
					<input type="text" class="form-control" style="width:150px;"
						id="modalPDR_id2" name="modalPDR_id2" />
						
					<input type="text" class="form-control"  style="width:300px;"
					id="modalDescription2" name="modalPDR_id2" />
						
					
				<span id="importOrderExcelMsg" style="margin-left:10px;padding-top:8px;color:red;"></span>
			</div>
			
		</div>
		
		<div id='orderData' style='width:100%; height:730px; margin-top:10px;' ></div>
		<div class="alert alert-success fade show" role="alert" id="alertDiv" style="display: none;">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				span aria-hidden="true">&times;</span>
			</button>
			<span id="alertText"></span>
		</div>
				
			
</body>
</html>
