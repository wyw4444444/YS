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
				<button class="btn btn-primary" onclick="showModalWarehouseOut()">出库作业</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="warehouseOutSearchTypeList" edit="true">
						<option value="undefined">--- 请选择 ---</option>
						<option value="out_date">出库时间范围</option>
						<option value="part_code">出库料号</option>
						<option value="out_reason">出库原因</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询内容" id="warehouseOutSearchKeyWord" name="warehouseOutSearchKeyWord" />
					<select class="form-control" id="OtherColumnSearchWarehouseOutList" style="display: none;">
					</select>
					<select class="form-control" id="OtherColumnSearchWarehouseOutList2" style="display: none;" class="selectpicker" data-live-search="true" data-live-search-placeholder="Search">
					</select>
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
					<button class="btn btn-secondary form-control" id="btn-search-warehouseOut" onclick="loadWarehouseOut()">查询</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12 col-lg-12">
		<table id="contentWarehouseOut" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 出库模态框 -->
	<div class="modal fade" id="showUpdateWarehouseOut">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改出库资料</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormWarehouseOut">
							<div class="row">
								<div class="col-md-7">
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">出&nbsp;库&nbsp;日&nbsp;期</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseOutOutDate" name="modalWarehouseOutOutDate"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">料&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</span>
										</div>
										<!-- <select class="form-control" id="modalWarehouseOutPartCode" class="selectpicker" data-live-search="true" data-live-search-placeholder="Search">
										</select> -->
										<input type="text" class="form-control" placeholder="输入料号" id="modalWarehouseOutPartCode" name="modalWarehouseOutPartCode"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseOutTradeName" name="modalWarehouseOutTradeName" disabled="disabled"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseOutSpec" name="modalWarehouseOutSpec" disabled="disabled"/>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</span>
										</div>
										<input type="text" class="form-control" id="modalWarehouseOutPartUnit" name="modalWarehouseOutPartUnit" disabled="disabled"/>
									</div>
									
								</div>
							
								<div class="col-md-5">
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量</span>
										</div>
										<input type="text" class="form-control" placeholder="输入数量" id="modalWarehouseOutTotalQuantity" name="modalWarehouseOutTotalQuantity" />
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">出&nbsp;库&nbsp;原&nbsp;因</span>
										</div>
										<select class="form-control" id="modalWarehouseOutOutReason">
										</select>
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">&nbsp;P&nbsp;D&nbsp;R&nbsp;&nbsp;&nbsp;号&nbsp;</span>
										</div>
										<input type="text" class="form-control" placeholder="输入PDR号" id="modalWarehouseOutPdrNo" name="modalWarehouseOutPdrNo" />
									</div>
									<div class="input-group mb-2">
										<div class="input-group-prepend">
											<span class="input-group-text">备&nbsp;注&nbsp;信&nbsp;息</span>
										</div>
										<textarea class="form-control" rows="3" id="modalWarehouseOutNote" name="modalWarehouseOutNote" placeholder="输入备注"></textarea>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<input id="modalWarehouseOutId" type="hidden"/>
									<span id="modalWarehouseOutMsg" style="color:red;"></span>
									
									<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
									
									<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
									<button id="btnAddWarehouseOut" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addWarehouseOut()">新增出库记录</button>
									<button id="btnUpdateWarehouseOut" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="updateWarehouseOut()">更新资料</button>
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
