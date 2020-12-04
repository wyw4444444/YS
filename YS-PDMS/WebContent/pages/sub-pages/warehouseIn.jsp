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
				<button class="btn btn-primary" onclick="showModalWarehouseIn()">入库作业</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="warehouseInSearchTypeList" edit="true">
						<option value="undefined">--- 请选择 ---</option>
						<option value="in_date">入库时间范围</option>
						<option value="part_code">入库料号</option>
						<option value="in_reason">入库原因</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询内容" id="warehouseInSearchKeyWord" name="warehouseInSearchKeyWord" />
					<select class="form-control" id="OtherColumnSearchWarehouseInList" style="display: none;">
					</select>
					<div id="OtherColumnSearchWarehouseInList2Div">
						<select class="form-control" id="OtherColumnSearchWarehouseInList2" style="display: none;" class="selectpicker" data-live-search="true" data-live-search-placeholder="Search">
						</select>
					</div>
				</div>
				&nbsp;
				<div class="input-group">
					<select class="form-control" id="searchDateType">
						<option value="1">本年度</option>
						<option value="2">自定时间范围</option>
					</select>
					<input class="form-control" id="headStartDate" type="text" value="开始日期：" disabled="disabled" style="display: none;"/>
					<input class="form-control" id="startDate" name="startDate" placeholder="请选择日期" style="display: none;"/>
					<input class="form-control" id="headEndDate" type="text" value="結束日期：" disabled="disabled" style="display: none;"/>
					<input class="form-control" id="endDate" name="endDate" placeholder="请选择日期" style="display: none;"/>
					<button class="btn btn-secondary form-control" id="btn-search-warehouseIn" onclick="loadWarehouseIn('1')">查询整理后资料</button>
					<button class="btn btn-info form-control" id="btn-search-warehouseIn2" onclick="loadWarehouseIn('2')">查询所有资料</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12 col-lg-12">
		<table id="contentWarehouseIn" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 入库模态框 -->
	<div class="modal fade" id="showUpdateWarehouseIn">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改入库资料</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormWarehouseIn">
							<div class="row">
								<div class="col-md-7">
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">入&nbsp;库&nbsp;日&nbsp;期</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseInInDate" name="modalWarehouseInInDate"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">料&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</span>
										</div>
										<!-- <select class="form-control" id="modalWarehouseInPartCode" class="selectpicker" data-live-search="true" data-live-search-placeholder="Search">
										</select> -->
										<input type="text" class="form-control" placeholder="输入料号" id="modalWarehouseInPartCode" name="modalWarehouseInPartCode"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseInTradeName" name="modalWarehouseInTradeName" disabled="disabled"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseInSpec" name="modalWarehouseInSpec" disabled="disabled"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseInPartUnit" name="modalWarehouseInPartUnit" disabled="disabled"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量</span>
										</div>
										<input type="text" class="form-control" placeholder="输入数量" id="modalWarehouseInQuantity" name="modalWarehouseInQuantity" />
									</div>
								</div>
								<div class="col-md-5">
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">入&nbsp;库&nbsp;原&nbsp;因</span>
										</div>
										<select class="form-control" id="modalWarehouseInInReason">
										</select>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">&nbsp;P&nbsp;D&nbsp;R&nbsp;&nbsp;&nbsp;号&nbsp;</span>
										</div>
										<input type="text" class="form-control" placeholder="输入PDR号" id="modalWarehouseInPdrNo" name="modalWarehouseInPdrNo" />
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">采&nbsp;购&nbsp;单&nbsp;号</span>
										</div>
										<input type="text" class="form-control" placeholder="输入采购单号" id="modalWarehouseInPurSheetId" name="modalWarehouseInPurSheetId" />
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">采&nbsp;购&nbsp;单&nbsp;价</span>
										</div>
										<input type="text" class="form-control" placeholder="输入单价" id="modalWarehouseInPrice" name="modalWarehouseInPrice" />
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">备&nbsp;注&nbsp;信&nbsp;息</span>
										</div>
										<textarea class="form-control" rows="3" id="modalWarehouseInNote" name="modalWarehouseInNote" placeholder="输入备注"></textarea>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<input id="modalWarehouseInId" type="hidden"/>
									<span id="modalWarehouseInMsg" style="color:red;"></span>
									
									<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
									
									<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
									<button id="btnAddWarehouseIn" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addWarehouseIn()">新增入库记录</button>
									<button id="btnUpdateWarehouseIn" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="updateWarehouseIn()">更新资料</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
