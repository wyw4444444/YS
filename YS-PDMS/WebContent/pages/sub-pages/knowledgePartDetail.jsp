<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/knowledge.css" />
<script type="text/javascript" src="js/knowledgePartDetail.js"></script>
</head>
<body>
	<div id="toolbar" class="col-md-10 col-lg-10 partDetail">
		<div class="content">
			<div class="leftcontent">
				<div class="title"><h4>分階詳情</h4>
				</div>
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
				<div class="submitContent">
					<button style="display:none;" class="submit btn btn-primary">通過審核</button>
					<button style="display:none;" class="cancel btn btn-primary">駁回</button> 
					<button style="display:none;" class="update btn btn-primary">修改</button> 
					<button style="display:none;" class="levelup btn btn-primary">升版</button>
					<button style="display:none;" class="print btn btn-primary">打印</button> 
				</div>

			</div>
			
			<div class="rightcontent">
				<div class="title">
					<h5>附件展示</h5>
				</div>
				<div class="fileContent">
					<!--startprint-->
					<img src="" class="fileShow">
					<!--endprint-->
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
<!-- 					<div id="toolbar2"> -->
<!-- 						<div class="form-inline"> -->
<!-- 							<div class="form-group"> -->
<!-- 								<div class="input-group"> -->
<!-- 									<input class="form-control" type="text" placeholder="输入料號" id="docSearchKeyWord" name="docSearchKeyWord"/> -->
<!-- 									<button class="btn btn-secondary form-control" onclick="loadKnowledge('2')">查詢</button> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
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
