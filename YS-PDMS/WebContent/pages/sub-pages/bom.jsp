<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<shiro:hasAnyRoles name="super_admin,admin">
					<button class="btn btn-primary" onclick="preImportBom()">批量导入BOM</button>&nbsp;
				</shiro:hasAnyRoles>
				<button class="btn btn-primary" onclick="showModalBom()">新增BOM</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group"style="width:500px;">
					<select class="form-control" id="type_select">
						<option value="undefined">选择查询类型</option>
						<option value=1>单阶BOM</option>
						<option value=2>多阶BOM</option>
						<option value=3>单阶BOM历史</option>
						<option value=4>上阶用途</option>
						<option value=5>尾阶用途</option>
						<option value=0>所有单阶BOM</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询料号" id="partKeyWord" name="partKeyWord"  style="width:80px"/>&nbsp;
					<button class="btn btn-secondary form-control" onclick="loadBom()">BOM查询</button>&nbsp;
				</div>
			</div>
		</div>
	</div>
	<div  class="col-md-11 col-lg-11">
		<table id="contentBom" class="table-sm table-striped">
		</table>
	</div>
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 導入頭部標題欄頁面 -->
	<div id="main" style="margin-top:10px; display: none;">
		<hr size="3" width="99%" style="border: 1 dashed;">
		<div class="container-fluid">
			<div class="page">
				
				<div class="form-inline" >
					<div class="input-group" id="importDiv">
						<div class="input-group-prepend">
							<span class="input-group-text">导入BOM：</span>
						</div>
						<div class="custom-file">
							<label class="form-control custom-file-label" for="importBomFile" id="importBomFileLabel">选择文件</label>
							<input type="file" class="form-control custom-file-input" id="importBomFile" name="filename" accept = '.xls,.xlsx'>
						</div>
						<div class="input-group-prepend">
							<button class="btn btn-primary" onclick="importBomExcel()">导入文件excel</button>
						</div>
						<div class="input-group-prepend">
							<button class="btn btn-warning" onclick="addBomStocks()">存储BOM</button>
						</div>
						<span id="importBomExcelMsg" style="margin-left:10px;padding-top:8px;color:red;"></span>
					</div>
				</div>
				
				
				<div id='bomData' style="margin-top:10px;"></div>
				
				<div class="alert alert-success fade show" role="alert" id="alertDiv" style="display: none;">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="alertText"></span>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 模态框 -->
	<div class="modal fade" id="showUpdateBom">
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
						<form id="modalFormBom">
						
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">主件料号</span>
								</div>
								<input type="text" class="form-control" id="modalPartCodeUp" name="modalPartCodeUp" placeholder="输入主件料号"/>
								<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
							</div>
	
							<div class="input-group mb-3 mygroup">
								<div class="input-group-prepend">
									<span class="input-group-text">下阶料号</span>
								</div>
								<input type="text" class="form-control code" id="modalPartCodeDown" name="modalPartCodeDown" style="width:15%;"/>
								<div class="input-group-prepend">
									<span class="input-group-text">用量</span>
								</div>
								<input type="number" class="form-control" id="modalDosage" name="modalDosage" step="0.0001" min="0"/>
								<div class="input-group-prepend">
									<span class="input-group-text">底数</span>
								</div>
								<input type="number"  min=0 step=1 value=1 class="form-control" id="modalBase" name="modalBase" />
								<div class="input-group-prepend">
									<span class="input-group-text">单位</span>
								</div>
								<select class="form-control" id="modalUnitList"></select>
								<span style="color:red;font-weight:bold;padding: 5px 0;">&nbsp;*</span>
							</div>
							
							
							
							<input id="modalBomId" type="hidden" />
							<span id="modalBomMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddBom" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addBom()">发起审核</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
