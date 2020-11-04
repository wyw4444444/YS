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
					<button class="btn btn-primary" onclick="showModalAction()">新增权限</button>&nbsp;
				</shiro:hasAnyRoles>
				<button class="btn btn-secondary" onclick="loadAction('1')">有效权限</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadAction('2')">所有权限</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="actionSearchTypeList">
						<option value="undefined">查询内容</option>
						<option value="title">权限名称</option>
						<option value="flag">权限代号</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询内容" id="actionSearchKeyWord" name="actionSearchKeyWord"/>
					<button class="btn btn-secondary form-control" onclick="loadAction('3')">查询</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
		<table id="contentAction" class="table-sm table-striped">
		</table>
	</div>

	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 修改权限模态框 -->
	<div class="modal fade" id="showUpdateAction">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改权限</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormAction">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">权限代号</span>
								</div>
								<input type="text" class="form-control" placeholder="输入权限代号，如：'action:add'" id="modalActionFlag" name="modalActionFlag"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">权限名称</span>
								</div>
								<input type="text" class="form-control" placeholder="输入权限名称，如：部门新增" id="modalActionTitle" name="modalActionTitle" />
							</div>
							<div class="input-group mb-3" id="modalActionLockedDiv">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="checkbox" id="modalActionLocked">
									</div>
								</div>
								<input type="text" class="form-control" placeholder="是否锁定？(勾选代表锁定)" disabled="disabled">
							</div>
							<input id="modalActionId" type="hidden" />
							<span id="modalActionMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddAction" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addAction()">新增权限</button>
							<button id="btnUpdateAction" class="btn btn-info" style="float: right; margin-right: 20px;" onclick="updateAction()">更新资料</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
