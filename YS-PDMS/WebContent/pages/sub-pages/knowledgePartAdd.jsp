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
				<p class="number-error-tip">料號已存在，請進入升版界面操作</p>
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
					<input id="file" type="file" style="display:none" accept=".jpg,.png">
				</div>
				<div class="submitContent">
					<button class="submit btn btn-primary">提交</button>
					<div class="alert alert-danger alert-dismissible" role="alert" style="display:none;margin-top:10px;">
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


<%-- 	<jsp:include page="/pages/plugins/include_alert.jsp" /> --%>

</body>
</html>
