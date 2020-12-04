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
				<button class="btn btn-primary" onclick="showModalSafetyStorage()">新增安全库存信息</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadSafetyStorage('1')">最新安全库存信息</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadSafetyStorage('2')">所有安全库存信息</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="safetyStorageSearchTypeList">
						<option value="undefined">查询内容</option>
						<option value="part_code">料号</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询内容" id="safetyStorageSearchKeyWord" name="safetyStorageSearchKeyWord"/>
					<button class="btn btn-secondary form-control" onclick="loadSafetyStorage('3')">查询</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-8 col-lg-8">
		<table id="contentSafetyStorage" class="table-sm table-striped">
		</table>
	</div>

	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 修改安全库存模态框 -->
	<div class="modal fade" id="showUpdateSafetyStorage">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">新增安全库存信息</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormSafetyStorage">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">料&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</span>
								</div>
								<select class="form-control" id="modalSafetyStoragePartCode" class="selectpicker" data-live-search="true" data-live-search-placeholder="Search">
								</select><span style="margin-left: 3px; color: red; margin-top: 8px;">*</span>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
								</div>
								<input type="text" class="form-control" id="modalSafetyStorageTradeName" name="modalSafetyStorageTradeName" disabled="disabled"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</span>
								</div>
								<input type="text" class="form-control" id="modalSafetyStorageSpec" name="modalSafetyStorageSpec" disabled="disabled"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</span>
								</div>
								<input type="text" class="form-control" id="modalSafetyStorageUnit" name="modalSafetyStorageUnit" disabled="disabled"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">安全库存数</span>
								</div>
								<input type="text" class="form-control" placeholder="输入数量" id="modalSafetyStorageSafetyStock" name="modalSafetyStorageSafetyStock" /><span style="margin-left: 3px; color: red; margin-top: 8px;">*</span>
							</div>
							<span id="modalSafetyStorageMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddSafetyStorage" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addSafetyStorage()">新增安全库存资料</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
