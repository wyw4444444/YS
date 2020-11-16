<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<button class="btn btn-primary" onclick="showModalMember()">新增人员</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadMember('1')">有效人员</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadMember('2')">所有人员</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group" class="form-control">
					<select class="form-control" id="memberSearchTypeList">
						<option value="undefined">查询内容</option>
						<option value="member_id">人员工号</option>
						<option value="member_name">人员姓名</option>
						<option value="role_title">角色名称</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询内容" id="memberSearchKeyWord" name="memberSearchKeyWord"/>
					<select class="form-control" id="DeptGroupRoleSearchMemberList" style="display: none;">
					</select>
					<button class="btn btn-secondary form-control" onclick="loadMember('3')">查询</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-8 col-lg-8">
		<table id="contentMember" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 修改人员资料模态框 -->
	<div class="modal fade" id="showUpdateMember">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改人员基本信息</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormMember">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">人员工号</span>
								</div>
								<input type="text" class="form-control" id="modalMemberId" name="modalMemberId"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">人员姓名</span>
								</div>
								<input type="text" class="form-control" id="modalMemberName" name="modalMemberName" />
							</div>
							<div class="input-group mb-3" id="modalMemberLockedDiv">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="checkbox" id="modalMemberLocked">
									</div>
								</div>
								<input type="text" class="form-control" placeholder="是否锁定？(勾选代表锁定)" disabled="disabled">
							</div>
							<br>
								<div class="panel">
									<div class="panel-heading">
										<h6 class="panel-title">选择部门</h6>
									</div>
									<div class="panel-body" id="modalDepts"></div>
								</div>
							<br>
							<div class="panel">
								<div class="panel-heading">
									<h6 class="panel-title">选择角色</h6>
								</div>
								<div class="panel-body" id="modalRoles"></div>
							</div>
							<br>
							<span id="modalMemberMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddMember" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addMember()">新增人员</button>
							<button id="btnUpdateMember" class="btn btn-info" style="float: right; margin-right: 20px;" onclick="updateMember()">更新资料</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改人员密码模态框 -->
	<div class="modal fade" id="showUpdateMemberPassword">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改人员密码</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormUpdateMemberPassword">
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">人员工号</span>
								</div>
								<input type="text" class="form-control" id="modalMemberPasswordId" name="modalMemberPasswordId" disabled="disabled"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">人员姓名</span>
								</div>
								<input type="text" class="form-control" id="modalMemberPasswordName" name="modalMemberPasswordName" disabled="disabled"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">新&nbsp;&nbsp;密&nbsp;&nbsp;码</span>
								</div>
								<input type="text" class="form-control" id="modalMemberPasswordNew" name="modalMemberPasswordNew"/>
							</div>
							<br>
							<span id="memberModalPasswordMsg" style="color: red;"></span>
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button class="btn btn-info" style="float: right; margin-right: 30px;" onclick="updateModalMemberPassword()">修改密码</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
