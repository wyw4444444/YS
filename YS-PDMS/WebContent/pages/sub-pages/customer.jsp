<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<button class="btn btn-info" onclick="showModalCustomer()">新增客戶</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadCustomer('1')">所有客戶</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="itemSearchTypeList">
						<option value="undefined">查詢內容</option>
						<option value="short_name">客戶簡稱</option>
					</select>
					<input class="form-control" type="text" placeholder="輸入查詢內容" id="itemSearchKeyWord" name="itemSearchKeyWord" />
					<button class="btn btn-secondary form-control" onclick="loadCustomer('2')">查詢</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-8 col-lg-8">
		<table id="contentCustomer" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 類型模态框 -->
	<div class="modal fade" id="showUpdateCustomer">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改客戶資料</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormCustomer">
							<div class="input-group mb-3" id="modalcustomer_no">
								<div class="input-group-prepend">
									<span class="input-group-text">客戶編號</span>
								</div>
								<input type="text" class="form-control" id="modal_customer_no"  name="modal_customer_no"/>
							</div>
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">客戶簡稱</span>
								</div>
								<input type="text" class="form-control" id="modal_short_name" name="modal_short_name" placeholder="輸入客戶簡稱"/>
							</div>
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">客戶全稱</span>
								</div>
								<input type="text" class="form-control" id="modal_full_name" name="modal_full_name" placeholder="輸入客戶全稱"/>
							</div>
							
						
							<div class="input-group mb-3" id="modalCustomerLockedDiv">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="checkbox" id="modalCustomerLocked">
									</div>
								</div>
								<input type="text" class="form-control" placeholder="是否鎖定？(勾選代表鎖定)" disabled="disabled">
							</div>
							<span id="modalCustomerMsg" style="color:red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddCustomer" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addCustomer()">新增客戶</button>
							<button id="btnUpdateCustomer" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="updateCustomer()">更新資料</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
