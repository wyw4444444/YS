<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include_static_head.jsp"%>
<header class="main-header">
	<nav class="navbar navbar-expand navbar-dark bg-primary fixed-top">
		<a class="navbar-brand" href="index.html"><img id="logo" alt="YS Logo" src="images/Logo-YS(Border).png" />&nbsp;&nbsp;<span class="systemName">产品开发及管理系统</span></a>
		<a class='navbar-btn btn pull-left' onclick="navbar_hide_visible()"><i class="fa fa-bars fa-1x"></i></a>

		<div class="collapse navbar-collapse justify-content-end" id="navbarTogglerMain">
			<ul class="navbar-nav">
				<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown"><shiro:principal property="member_id"/> / <shiro:principal property="member_name"/>&nbsp;</a>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="<%=basePath%>logoutUrl.action">登出</a>
						<a class="dropdown-item" href="#" onclick="showModalPassword()">修改密码</a>
					</div></li>
			</ul>
		</div>
	</nav>
</header>
<div class="navabarBg" id="navbar-bg">
	<div class="navbar-side">
		<ul>
			<shiro:hasAnyRoles name="super_admin,admin,PM,staff">
				<li><a class="inactive active"><i class="fa fa-gears fa-fw fa-lg"></i> 基础资料 </a>
					<ul>
						<li><a id="btn_await"><i class="fa fa-info-circle fa-fw"></i> 待处理项</a></li>
						<shiro:hasAnyRoles name="super_admin,admin">
							<li><a id="btn_member"><i class="fa fa-user-circle fa-fw"></i> 用户管理</a></li>
						</shiro:hasAnyRoles>
						<shiro:hasRole name="super_admin">
							<li><a id="btn_role"><i class="fa fa-users fa-fw"></i> 角色管理</a></li>
							<li><a id="btn_action"><i class="fa fa-key fa-fw"></i> 权限管理</a></li>
							<li><a id="btn_type"><i class="fa fa-dedent fa-fw"></i> 类别管理</a></li>
						</shiro:hasRole>
						<shiro:hasAnyRoles name="super_admin,admin,PM,staff">
							<li><a id="btn_dept"><i class="fa fa-university fa-fw"></i> 部门管理</a></li>
							<li><a id="btn_customer"><i class="fa fa-fighter-jet fa-fw"></i> 客户管理</a></li>
							<li><a id="btn_partInfo"><i class="fa fa-file-text fa-fw"></i> 料号资讯</a></li>
							<li><a id="btn_bom"><i class="fa fa-sort-amount-asc fa-fw"></i> BOM资讯</a></li>
							<li><a id="btn_repalce"><i class="fa fa-random fa-fw"></i> 替代物料</a></li>
							<li><a id="btn_checkLog"><i class="fa fa-list-alt fa-fw"></i> 审核记录</a></li>
						</shiro:hasAnyRoles>
					</ul>
				</li>
			</shiro:hasAnyRoles>
			<li><a class="inactive active"><i class="fa fa-bar-chart fa-fw fa-lg"></i> BOM管理</a>
				<ul>
					<shiro:hasAnyRoles name="super_admin,admin,PM,rd_manager,sales,sales_manager">
						<li><a id="btn_PER"><i class="fa fa-tasks fa-fw"></i> PER</a></li>
					</shiro:hasAnyRoles>
					<li><a id="btn_PDR"><i class="fa fa-tasks fa-fw"></i> PDR</a></li>
					<li><a id="btn_PAR"><i class="fa fa-tasks fa-fw"></i> PAR</a></li>
				</ul>
			</li>
			<li><a class="inactive active"><i class="fa fa-cubes fa-fw fa-lg"></i> 库存管理</a>
				<ul>
					<shiro:hasAnyRoles name="super_admin,admin,PM,rd_manager,sales,sales_manager">
						<li><a id="btn_safetyStorage"><i class="fa fa-warning fa-fw"></i> 安全库存管理</a></li>
						<li><a id="btn_warehouseIn"><i class="fa fa-indent fa-fw"></i> 入库作业</a></li>
					</shiro:hasAnyRoles>
					<li><a id="btn_warehouseOut"><i class="fa fa-outdent fa-fw"></i> 出库作业</a></li>
					<li><a id="btn_inventoryQuery"><i class="fa fa-search fa-fw"></i> 库存查询</a></li>
					
				</ul>
			</li>
			<shiro:hasAnyRoles name="super_admin,admin,sales_manager,problem_confirm,PM">
				<li><a class="inactive active"><i class="fa fa-database fa-fw fa-lg"></i> 数据导出 </a>
					<ul>
						<li><a id="btn_dataload"><i class="fa fa-download fa-fw"></i> 数据导出</a></li>
					</ul>
				</li>
			</shiro:hasAnyRoles>
		</ul>
	</div>
</div>
<!-- 修改人员密码模态框 -->
<div class="modal fade" id="showUpdatePassword">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- 模态框头部 -->
			<div class="modal-header">
				<h4 class="modal-title">修改密码</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<!-- 模态框主体 -->
			<div class="modal-body">
				<div class="card-body">
					<form id="modalFormUpdatePassword">
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">人员工号</span>
							</div>
							<input type="text" class="form-control" id="modalPassMemberId" name="modalPassMemberId" disabled="disabled"/>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">旧&nbsp;&nbsp;密&nbsp;&nbsp;码</span>
							</div>
							<input type="password" class="form-control" id="modalOldPassword" name="modalOldPassword"/>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">新&nbsp;&nbsp;密&nbsp;&nbsp;码</span>
							</div>
							<input type="password" class="form-control" id="modalNewPassword" name="modalNewPassword"/>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">确认密码</span>
							</div>
							<input type="password" class="form-control" id="modalConfirmNewPassword" name="modalConfirmNewPassword"/>
						</div>
						<br>
						<span id="memberPasswordModalMsg" style="color: red;"></span>
						
						<jsp:include page="/pages/plugins/include_alertSmall6.jsp" />
						
						<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
						<button class="btn btn-info" style="float: right; margin-right: 30px;" onclick="updatePassword()">修改密码</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var member_id = "<shiro:principal property='member_id'/>";
</script>