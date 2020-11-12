<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/knowledge.css" />
<script type="text/javascript" src="js/knowledgePartAdd.js"></script>
</head>
<body>
	<div id="toolbar" class="col-md-10 col-lg-10 partAdd">
		<div class="title"><h4>添加分階規格</h4>
		</div>
		<div class="content">
			<div class="leftcontent">
				<div class="number input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">料號</span>
					</div>
					<input type="text" class="form-control" id="number" name="number" placeholder="請輸入料號" />
				</div>
				<div class="version input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">名稱</span>
					</div>
					<input type="text" class="form-control" id="name" name="name" placeholder="請輸入名稱"/>
				</div>
				<div class="version input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">版本</span>
					</div>
					<input type="text" class="form-control" id="version" name="version" placeholder="自動生成最新版本" readonly value="A"/>
				</div>
				<div class="date input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">描述</span>
					</div>
					
					<input type="text" class="form-control" id="desc" name="desc" placeholder="請輸入描述"/>
				</div>
				<div class="date input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">日期</span>
					</div>
					
					<input type="text" class="form-control" id="date" name="date" placeholder="獲取當前日期" readonly/>
				</div>
				<div class="fileTitle">
					<span class="input-group-text">上傳附件</span>
				</div>
				<div class="fileUpload">
					<input type="text" class="form-control" placeholder="請選擇附件" readonly/>
					<label for="file" class="btn btn-info"><span style="color:red">*</span>上傳附件</label>
					<input id="file" type="file" style="display:none" accept=".pdf,.jpg,.png">
				</div>
				<div class="submitContent">
					<button class="submit btn btn-primary">提交</button>
					<div class="alert alert-danger alert-dismissible" role="alert" style="display:none;">
						  <button type="button" class="close"><span aria-hidden="true">&times;</span></button>
						  <span class="text"></span>
					</div>
				</div>

			</div>
			
			<div class="rightcontent">
				<div class="title">
					<h5>附件展示</h5>
				</div>
				<div class="fileContent">
					<img src="" class="fileShow" width="100%" height="100%" style="display:none;">
				</div>
			</div>
			
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
	</div>
	<div class="modal fade" id="showUpdateKnowledge">
		<div class="modal-dialog" style="max-width:70%;">
			<div class="modal-content">
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">選擇同步成品</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div id="toolbar2">
						<div class="form-inline">
							<div class="form-group">
								<div class="input-group">
									<input class="form-control" type="text" placeholder="输入料號" id="docSearchKeyWord" name="docSearchKeyWord"/>
									<button class="btn btn-secondary form-control" onclick="loadKnowledge('2')">查詢</button>
								</div>
							</div>
						</div>
					</div>
					<table id="contentDept" class="table-sm table-striped">
					</table>
					<div class="">
						<button type="button" class="btn btn-secondary" data-dismiss="modal" style="float: right;">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>


<%-- 	<jsp:include page="/pages/plugins/include_alert.jsp" /> --%>

</body>
</html>
