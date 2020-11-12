<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/knowledge.css" />
<script type="text/javascript" src="js/knowledgeAdd.js"></script>
</head>
<body>
	<div class="col-md-10 col-lg-10 knowledgeAdd">
		<div class="title"><h4>合併分階</h4>
		</div>
		<div class="content">
			<div class="leftcontent">
			
				<div class="type input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">类型</span>
					</div>
					<select class="form-control" id="part_type" title="请选择父类型">
						<option>作業指導書</option>
						<option>產品說明書</option>
					</select>
					
				</div>
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
					<span class="input-group-text">請在右側選擇分階</span>
				</div>
				<div class="partList">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr style="text-align:center;">
								<td>料號</td>
								<td colspan=2>操作</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
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
					<h5>分階搜索</h5>
				</div>
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
				<table id="contentDept" class="table-sm table-striped">
				</table>
			</div>
			
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
	</div>



<%-- 	<jsp:include page="/pages/plugins/include_alert.jsp" /> --%>

</body>
</html>
