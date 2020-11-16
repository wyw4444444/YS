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
					<button class="btn btn-primary" onclick="showModalRole()">新增角色</button>&nbsp;
				</shiro:hasAnyRoles>
				<button class="btn btn-secondary" onclick="loadRole('1')">有效角色</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadRole('2')">所有角色</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="roleSearchTypeList">
						<option value="undefined">查询内容</option>
						<option value="title">角色名称</option>
						<option value="flag">角色代号</option>
						<option value="action_flag">权限代号</option>
						<option value="action_title">权限名称</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询内容" id="roleSearchKeyWord" name="roleSearchKeyWord"/>
					<select class="form-control" id="actionSearchRoleList" style="display: none;">
					</select>
					<button class="btn btn-secondary form-control" onclick="loadRole('3')">查询</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-8 col-lg-8">
		<table id="contentRole" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 修改角色模态框 -->
	<div class="modal fade" id="showUpdateRole">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改角色</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormRole">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">角色代号</span>
								</div>
								<input type="text" class="form-control" placeholder="输入角色代号"  id="modalRoleFlag" name="modalRoleFlag" />
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">角色名称</span>
								</div>
								<input type="text" class="form-control" placeholder="输入角色名称" id="modalRoleTitle" name="modalRoleTitle" />
							</div>
							<div class="input-group mb-3" id="modalRoleLockedDiv">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="checkbox" id="modalRoleLocked">
									</div>
								</div>
								<input type="text" class="form-control" placeholder="是否锁定？(勾选代表锁定)" disabled="disabled">
							</div>
							<br>
							<div class="panel">
								<div class="panel-heading">
									<h6 class="panel-title">选择权限</h6> 
								</div> 
									<div class="panel-body" id="modalActions"></div> 
							</div>
							<br>
							<input id="modalRoleId" type="hidden" />
							<span id="modalRoleMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddRole" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addRole()">新增角色</button>
							<button id="btnUpdateRole" class="btn btn-info" style="float: right; margin-right: 20px;" onclick="updateRole()">更新资料</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
