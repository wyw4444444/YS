<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="js/knowledgePartDetail.js"></script>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<div class="input-group">
					<input class="form-control" type="text" placeholder="输入料號" id="docSearchKeyWord" name="docSearchKeyWord"/>
					<button class="btn btn-secondary form-control" onclick="loadProcessedPart('2')">查詢</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
		<table id="contentDept" class="table-sm table-striped">
		</table>
	</div>

	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 修改部门模态框 -->
	<div class="modal fade" id="showUpdateDept">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改部门</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormDept">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">部门代号</span>
								</div>
								<input type="text" class="form-control" placeholder="输入部门代号" id="modalDeptCode" name="modalDeptCode"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">部门名称</span>
								</div>
								<input type="text" class="form-control" placeholder="输入部门名称" id="modalDeptName" name="modalDeptName" />
							</div>
							<div class="input-group mb-3" id="modalDeptLockedDiv">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="checkbox" id="modalDeptLocked">
									</div>
								</div>
								<input type="text" class="form-control" placeholder="是否锁定？(勾选代表锁定)" disabled="disabled">
							</div>
							<input id="modalDeptId" type="hidden" />
							<span id="modalDeptMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddDept" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addDept()">新增部门</button>
							<button id="btnUpdateDept" class="btn btn-info" style="float: right; margin-right: 20px;" onclick="updateDept()">更新资料</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
