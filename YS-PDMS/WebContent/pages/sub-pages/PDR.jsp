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
					<button class="btn btn-info" onclick="showModalPDR('PER')">新增PER</button>&nbsp;
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="super_admin,admin">
					<button class="btn btn-info" onclick="showModalPDR('PDR')">新增PDR</button>&nbsp;
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="super_admin,admin,DY999">
					<button class="btn btn-secondary" onclick="loadPDR('1')">执行中的PDR</button>&nbsp;
				</shiro:hasAnyRoles>
			</div>
			<div class="form-group">
				<!-- 選擇條件查詢 -->
				<div class="input-group" style="width:600px;">
					<select id="statusList"   title="请选择工單狀態"  multiple
							class="selectpicker show-tick show-menu-arrow btn-white"
							data-actions-box="true" data-width="fit">
					</select>
					<select class="form-control" id="PDRSearchTypeList" style="width:100px;">
						<option value="undefined">查詢內容</option>
						<option value="PDR_id">PDR编号</option>
						<option value="PM" selected="selected">PM名称</option>
					</select>
					<input class="form-control" type="text" placeholder="輸入查詢內容" 
						id="PDRSearchKeyWord" name="roleSearchKeyWord" style="width:100px;"/>
					<select class="form-control" id="PMList" style="display: none; width:100px;" >
					</select>
					<button class="btn btn-secondary form-control" onclick="loadPDR('2')">查詢</button>
				</div>
				
			</div>
		</div>
	</div>
	<div class="col-md-12 col-lg-12">
		<table id="contentPDR" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	
	
	
	
	<!-- 修改權限模态框 -->
	<div class="modal fade" id="showUpdatePDR">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改PDR</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormRole">
						
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text num">PDR编号</span>
								</div>
								<input type="text" class="form-control" placeholder="輸入PDR编号"  
								id="modalPDR_id" name="modalPDR_id" />
							</div>
							<div class="input-group mb-3 modalPER_id-div">
								<div class="input-group-prepend">
									<span class="input-group-text">PER编号</span>
								</div>
								<input type="text" class="form-control" placeholder="輸入PER编号"  
								id="modalPER_id" name="modalPER_id" />
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">客户名称</span>
								</div>
								<select class="form-control" id="customerList" style="width:100px;"></select>
							</div>
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">专案描述</span>
								</div>
								<input type="text" class="form-control" placeholder="輸入描述" 
								id="modalDescription" name="modalDescription" />
							</div>
							
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">&nbsp;PM名称&nbsp;</span>
								</div>
								<select class="form-control" id="PMList2"></select>
							</div>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">&nbsp;業務員名称</span>
								</div>
								<select class="form-control" id="MemberList"></select>
							</div>
							
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">开始日期</span>
								</div>
								<input type="text" class="form-control" placeholder="--- 选择日期 ---"
								autocomplete="off" id="modalstart_date" name="modalstart_date" />
							</div>
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">结案日期</span>
								</div>
								<input type="text" class="form-control" placeholder="--- 选择日期 ---"
								autocomplete="off" id="modalend_date" name="modalend_date" />
							</div>
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">执行状态</span>
								</div>
								<select class="form-control" id="modaltype_id_status"></select>
							</div>
							
							<div class="input-group mb-3 form-inline">
								<div class="input-group-prepend">
									<span class="input-group-text">選擇照片</span>
								</div>
								<input type="file" class="form-control"
								 id="PDRpicture" accept=".jpg" multiple="multiple"/>
							</div>
							
							<span id="modalPDRMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddPDR" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addPDR()">新增PDR</button>
							<button id="btnUpdatePDR" class="btn btn-info" style="float: right; margin-right: 20px;" onclick="updatePDR()">更新資料</button>
						</form>
					</div>
				</div> <!-- 模态框主体 -->
				
			</div>
		</div>
	</div>
	
	
	
	
	
	<!-- 修改權限模态框 -->
	<div class="modal fade" id="showUpdateLog">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改PDR</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body">
						<form id="modalFormLog">
						
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">PDR编号</span>
								</div>
								<input type="text" class="form-control" placeholder="輸入PDR编号"  
								id="modalPDR_id2" name="modalPDR_id2" />
							</div>
							
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">记录日期</span>
								</div>
								<input type="text" class="form-control"
								 id="modalreg_time" name="modalreg_time" />
							</div>
							
							<div  class="input-group mb-3" class="text-center">
								<span class="input-group-text">记录内容</span>
							</div>
							
							<div class="input-group mb-3">
								<textarea class="form-control" rows="7" autocomplete="off" 
								id="modalLog" name="modalLog"></textarea>
							</div>
							
							
							<div class="input-group mb-3 form-inline">
								<div class="input-group-prepend">
									<span class="input-group-text">選擇照片</span>
								</div>
								<input type="file" class="form-control"
								 id="PDRfile" accept=".jpg" multiple="multiple"/>
							</div>
     
							<span id="modalLogMsg" style="color: red;"></span>
							
							<jsp:include page="/pages/plugins/include_alertSmall.jsp" />
							
							<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
							<button id="btnAddLog" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="addLog()">新增记录</button>
						</form>
					</div>
				</div> <!-- 模态框主体 -->
				
			</div>
		</div>
	</div>
	
	
	
	<!-- 圖片模态框 -->
	<div class="modal fade" id="showPicture">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">記錄圖片</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="card-body" id="msgDiv">
						<ul style="padding:0px;">
      						<li v-for="(image,index) in images">
      							<img :src="image" style="width:100%">
      						</li>
  						</ul>
					</div>
				</div> <!-- 模态框主体 -->
				
			</div>
		</div>
	</div>
	
	
	
	
</body>
</html>
