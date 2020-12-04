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
				<!-- 選擇條件查詢 -->
				<div class="input-group" style="width:400px;">
					<div class="input-group-prepend">
						<span class="input-group-text">選擇登錄日期</span>
					</div>
					<input class="form-control" type="text" placeholder="選擇日期" 
						id="reg_time" name="reg_time" style="width:100px;"/>
					<button class="btn btn-secondary form-control" onclick="loadrecordmember()">查詢</button>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-7 col-lg-7">
		<table id="contentRecordMember" class="table-sm table-striped">
		</table>
	</div>
	
	<jsp:include page="/pages/plugins/include_alert.jsp" />
	
	
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
									<span class="input-group-text">系统编号</span>
								</div>
								<input type="text" class="form-control"
								id="modalno" name="modalno" />
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
							<button id="btnEditLog" class="btn btn-info" style="float: right;margin-right: 20px;" onclick="editLog()">修改记录</button>
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
				<div class="modal-body" id="msgDiv">
					<ul style="padding:0px;">
      					<li v-for="(image,index) in images">
      						<img :src="image" style="width:100%;">
      					</li>
  					</ul>
				</div> <!-- 模态框主体 -->
				
			</div>
		</div>
	</div>
	
	
</body>
</html>
