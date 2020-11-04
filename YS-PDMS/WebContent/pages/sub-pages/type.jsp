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
				<button class="btn btn-info" onclick="showModalType()">新增类型</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadType('1')">有效类型</button>&nbsp;
				<button class="btn btn-secondary" onclick="loadType('2')">所有类型</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="typeSearchTypeList">
						<option value="undefined">查询内容</option>
						<option value="parent_type">父类型</option>
						<option value="sub_type">子类型</option>
					</select>
					<input class="form-control" type="text" placeholder="输入查询内容"  id="typeSearchKeyWord" name="typeSearchKeyWord" />
					<button class="btn btn-secondary form-control" onclick="loadType('3')">查询</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
		<table id="contentType" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	<!-- 类型模态框 -->
	<div class="modal fade" id="showUpdateType">
		<div class="modal-dialog">
			<div class="modal-content" style="width:600px;">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改类型资料</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormType">
							<shiro:hasAnyRoles name="super_admin">
								<div id="addOrUseParentTypeDiv">
									<label class="radio-inline mb-3"><input type="radio" name="addOrUseParentType" value="old" checked="checked">使用父类型</label>&nbsp;&nbsp;&nbsp;
									<label class="radio-inline mb-3"><input type="radio" name="addOrUseParentType" value="new">新增初代父类型</label>
								</div>
							</shiro:hasAnyRoles>
							<div class="input-group mb-3" id="modalTypeParentTypeListDiv">
								<div class="input-group-prepend">
									<span class="input-group-text">父类型</span>
								</div>
								<select class="form-control selectpicker  show-menu-arrow" data-live-search="true" data-style="btn-default"  data-width="200px" 
								data-dropup-auto="false" data-size="8" title="请选择父类型" id="modalTypeParentTypeList" >
								</select>
								
							</div>
							<div class="input-group mb-3" id="modalTypeParentTypeInputDiv" style="display:none;">
								<div class="input-group-prepend">
									<span class="input-group-text">父类型</span>
								</div>
								<input type="text" class="form-control" id="modalParentType" name="modalParentType" placeholder="输入父类型"/>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">子类型</span>
								</div>
								<input type="text" class="form-control" id="modalSubType" name="modalSubType" placeholder="输入子类型"/>
							</div>
							<div class="input-group mb-3" id="modalTypeLockedDiv">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="checkbox" id="modalTypeLocked">
									</div>
								</div>
								<input type="text" class="form-control" placeholder="是否锁定？(勾选代表锁定)" disabled="disabled">
							</div>
							<input id="modalTypeId" type="hidden"/>
							<span id="modalTypeMsg" style="color:red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddType" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addType()">新增类型</button>
							<button id="btnUpdateType" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="updateType()">更新资料</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
