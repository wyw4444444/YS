<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>产品开发管理系统-料号审核</title>
<link rel="Shortcut Icon" href="../../images/Logo-YS.ico">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="../../jquery/jquery-3.3.1.min.js"></script>
<!-- <script type="text/javascript" src="js/artegen.js"></script> -->
<script type="text/javascript" src="../../jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../jquery/additional-methods.min.js"></script>
<script type="text/javascript" src="../../jquery/Message_zh_TW.js"></script>
<script type="text/javascript" src="../../bootstrap/js/popper.min.js"></script>
<script type="text/javascript" src="../../bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../../bootstrap/js/bootstrap-select.min.js"></script>
<!-- 引入圖標字體文件 -->
<link type="text/css" rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">
<!-- 引入bootstrap上傳控件插件 -->
<script type="text/javascript" src="../../js/bs-custom-file-input.min.js"></script>
<!-- 引入JExcel相關的文件 -->
<script type="text/javascript" src="../../JExcel/js/jquery.csv.min.js"></script>
<script type="text/javascript" src="../../JExcel/js/jquery.jexcel.js"></script>
<script type="text/javascript" src="../../JExcel/js/jquery.jcalendar.js"></script>
<link type="text/css" rel="stylesheet" href="../../JExcel/css/jquery.jexcel.css">
<link type="text/css" rel="stylesheet" href="../../JExcel/css/jquery.jcalendar.css">
<!-- 引入js-xlsx相關的文件 -->
<script type="text/javascript" src="../../js/xlsx.full.min.js"></script>
<!-- 引入laydate控件，日期時間選擇 -->
<script type="text/javascript" src="../../laydate/laydate.js"></script>
<!-- 引入bootstrap table組件 -->
<script type="text/javascript" src="../../bootstrap-table/js/bootstrap-table.min.js"></script>
<link type="text/css" rel="stylesheet" href="../../bootstrap-table/css/bootstrap-table.min.css">
<script type="text/javascript" src="../../bootstrap-table/js/bootstrap-table-export.min.js"></script>
<script type="text/javascript" src="../../bootstrap-table/tableExport/tableExport.js"></script>
<script type="text/javascript" src="../../bootstrap-table/js/bootstrap-table-zh-CN.min.js"></script>

<link rel="stylesheet" type="text/css" href="../../bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="../../bootstrap/css/bootstrap-select.min.css">
<!-- <link type="text/css" rel="stylesheet" href="../../css/artegen.css"> -->
<link type="text/css" rel="stylesheet" href="../../css/check.css">
<!-- 引入主要js控制文件 -->
<!-- <script type="text/javascript" src="../../js/main.js"></script> -->
<script type="text/javascript" src="../../js/spark.js"></script>
<script type="text/javascript" src="../../js/check.js"></script>
</head>

<body>
	<div class="modal-dialog modal-lg" style="margin-top: 15px;margin-bottom: 15px;">
		<div class="modal-content">
			<div class="modal-body" style="padding: 0px;">
				<div class="card-body">
					<form id="modalFormPartInfo">
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">料&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</span>
							</div>
							<input type="text" class="form-control" id="modalPartCode" name="modalPartCode" placeholder="输入料号"/>
							<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
							</div>
							<input type="text" class="form-control" id="modalTradename" name="modalTradename" placeholder="输入品名"/>
							<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</span>
							</div>
							<input type="text" class="form-control" id="modalSpec" name="modalSpec" placeholder="输入规格"/>
							<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</span>
							</div>
							<select class="form-control" id="modalUnitList"></select>
							<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
						</div>
						
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">损&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;耗</span>
							</div>
							<input type="number" max=1 min=0 step=0.0001  value=0 class="form-control" id="modalLoss" name="modalLoss" placeholder="输入损耗"/>
							<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
						</div>
						
						
						<div class="input-group mb-3 mygroup">
							<div class="input-group-prepend">
								<input type="hidden" value=1 />
								<span class="input-group-text">属&nbsp;&nbsp;&nbsp;性&nbsp;&nbsp;1</span>
							</div>
							<select class="form-control prop" id="modalPropList"></select>
							<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
						</div>
						
						<div class="input-group mb-3 mygroup">
								<div class="input-group-prepend">
									<input type="hidden" value=2 />
									<span class="input-group-text">属&nbsp;&nbsp;&nbsp;性&nbsp;&nbsp;2</span>
								</div>
								<select class="form-control prop" ></select>
								<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
							</div>
							
							<div class="input-group mb-3 mygroup">
								<div class="input-group-prepend">
									<input type="hidden" value=3 />
									<span class="input-group-text">属&nbsp;&nbsp;&nbsp;性&nbsp;&nbsp;3</span>
								</div>
								<select class="form-control prop" ></select>
								<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
							</div>
							
							<div class="input-group mb-3 mygroup">
								<div class="input-group-prepend">
									<input type="hidden" value=4 />
									<span class="input-group-text">属&nbsp;&nbsp;&nbsp;性&nbsp;&nbsp;4</span>
								</div>
								<select class="form-control prop" ></select>
								<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
							</div>
							
							<div class="input-group mb-3 mygroup">
								<div class="input-group-prepend">
									<input type="hidden" value=5 />
									<span class="input-group-text">属&nbsp;&nbsp;&nbsp;性&nbsp;&nbsp;5</span>
								</div>
								<select class="form-control prop" ></select>
								<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
							</div>
						
						<input id="modalPartInfoId" type="hidden" />
						<span id="modalPartInfoMsg" style="color: red;"></span>
						
					</form>
				</div>
			</div>
		</div>
	</div> 
	
	<button type="button" class="btn btn-secondary" style="float: right;margin-right: 20px;" onclick="closeWindow()">关闭</button>
	<button id="btnUpdatePartInfo" class="btn btn-info" style="float: right; margin-right: 20px;" onclick="updatePartInfo()">发起重审</button>
	<button id="btnApprovePartInfo" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="approvePartInfo()">核准申请</button>
	<button id="btnSendBackPartInfo" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="sendbackPartInfo()">退回申请</button>
	<button id="btnGetBackPartInfo" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="getbackPartInfo()">取回申请</button>
	<button id="btnCancelPartInfo" class="btn btn-warning" style="float: right;margin-right: 20px;" onclick="cancelPartInfo()">取消申请</button>
	
	<input type="text" class="form-control" id="modalPartInfoTips" name="tips" placeholder="请输入处理意见"  style="float: left;margin-left: 80px; width:480px"/>
	 <br/><br/><HR>  
	 
	 <div class="col-md-12 col-lg-12" style="float:bottom">
		<table id="contentPartInfoCheck" class="table-sm table-striped">
		</table>
	</div>
	
	<!-- 導入公司尾部認證信息 -->
<%-- 	<jsp:include page="/pages/plugins/include_title_foot.jsp" /> --%>

</body>



</html>
