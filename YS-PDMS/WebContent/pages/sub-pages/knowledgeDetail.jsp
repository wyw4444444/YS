<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/knowledge.css" />
<script type="text/javascript" src="js/knowledgeDetail.js"></script>
</head>
<body>
	<div id="toolbar" class="col-md-10 col-lg-10 partDetail">
		<div class="content">
			<div class="leftcontent">
				<div class="number input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">料號</span>
					</div>
					<input type="text" class="form-control" id="number" name="number" placeholder="" readonly/>
				</div>
				<div class="version input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">名稱</span>
					</div>
					<input type="text" class="form-control" id="name" name="name" placeholder="" readonly/>
				</div>
				<div class="version input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">版本</span>
					</div>
					<input type="text" class="form-control" id="version" name="version" placeholder="" readonly/>
				</div>
				<div class="date input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">描述</span>
					</div>
					
					<input type="text" class="form-control" id="desc" name="desc" placeholder="" readonly/>
				</div>
				<div class="date input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">日期</span>
					</div>
					
					<input type="text" class="form-control" id="date" name="date" placeholder="" readonly/>
				</div>
				<div class="fileTitle">
					<span class="input-group-text">分階列表</span>
				</div>
				<div class="partList">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr style="text-align:center;">
								<td>料號</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div class="submitContent">
					<button style="display:none;" class="submit btn btn-primary">通過審核</button>
					<button style="display:none;" class="cancel btn btn-primary">駁回</button> 
					<button style="display:none;" class="update btn btn-primary">修改</button> 
					<button style="display:none;" class="levelup btn btn-primary">升版</button> 
				</div>

			</div>
			
			<div class="rightcontent">
				<div class="title">
					<h5>附件展示</h5>
				</div>
				<div class="fileContent">
					<img src="" class="fileShow">
				</div>
			</div>
			
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
	</div>



<%-- 	<jsp:include page="/pages/plugins/include_alert.jsp" /> --%>

</body>
</html>
