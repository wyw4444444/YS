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
				<shiro:hasAnyRoles name="super_admin,admin">
					<button class="btn btn-primary" onclick="preImportPartInfo()">批量导入料号</button>&nbsp;
				</shiro:hasAnyRoles>
				
				<button class="btn btn-primary" onclick="showModalPartInfo()">新增料号</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<input class="form-control" type="text" placeholder="輸入查询料号" id="KeyWordPart_Code" name="KeyWordPart_Code"/>
					<input class="form-control" type="text" placeholder="輸入查询品名" id="KeyWordTradeName" name="KeyWordTradeName"/>
					<input class="form-control" type="text" placeholder="輸入查询规格" id="KeyWordSpec" name="KeyWordSpec"/>
					<input class="form-control" type="text" placeholder="輸入查詢属性" id="KeyWordProp" name="KeyWordProp"/>&nbsp;
					<button class="btn btn-secondary form-control" onclick="loadPartInfo('1')">有效料号信息</button>&nbsp;
					<button class="btn btn-secondary form-control" onclick="loadPartInfo('2')">所有料号信息</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12 col-lg-12">
		<table id="contentPartInfo" class="table-sm table-striped">
		</table>
	</div>
	<HR>
	<div class="form-inline" id="importDiv" style="padding-left:15px;">
		<div class="form-group">
			<div class="input-group">
				<div class="custom-file">
					<label class="form-control custom-file-label" for="importPartInfoFile" id="importPartInfoFileLabel">选择文件</label>
					<input type="file" class="form-control custom-file-input" id="importPartInfoFile" name="filename" accept = '.xls,.xlsx'>
				</div>
				&nbsp;
				<button class="btn btn-primary" onclick="importPartInfoExcel()">导入Excel料号信息</button>&nbsp;
				<button class="btn btn-primary" onclick="addPartInfoStocks()">存入系统</button>&nbsp;
				<span id="importPartInfoExcelMsg" style="color:red;padding-top:6px;padding-left:10px;"></span>
			</div>
		</div>
	</div>
	
	<div id='partInfoData' style="padding-top:10px;padding-left:15px;"></div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 模态框 -->
	<div class="modal fade" id="showUpdatePartInfo">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改料号</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
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
							
							
							
							<input id="modalPartInfoId" type="hidden" />
							<span id="modalPartInfoMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddPartInfo" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addPartInfo()">发起审核</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
