<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="../../jquery/jquery-3.3.1.min.js"></script>
<!-- <script type="text/javascript" src="js/artegen.js"></script> -->
<script type="text/javascript" src="../../jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../jquery/additional-methods.min.js"></script>
<script type="text/javascript" src="../../jquery/Message_zh_TW.js"></script>
<script type="text/javascript" src="../../bootstrap/js/popper.min.js"></script>
<script type="text/javascript" src="../../bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../../bootstrap/js/bootstrap-select.min.js"></script>
<!-- 引入圖標字體文件 -->
<link type="text/css" rel="stylesheet" href="../../bootstrap/css/font-awesome.min.css">
<!-- 引入bootstrap上傳控件插件 -->
<script type="text/javascript" src="../../js/bs-custom-file-input.min.js"></script>
<!-- 引入JExcel相關的文件 -->
<script type="text/javascript" src="../../JExcel/js/jquery.csv.min.js"></script>
<script type="text/javascript" src="../../JExcel/js/jquery.jexcel.js"></script>
<script type="text/javascript" src="../../JExcel/js/jquery.jcalendar.js"></script>
<link type="text/css" rel="stylesheet" href="../../JExcel/css/jquery.jexcel.css">
<link type="text/css" rel="stylesheet" href="../../JExcel/css/jquery.jcalendar.css">
<!-- 引入js-xlsx相關的文件 -->
<script type="text/javascript" src="../../js/xlsx.full.min.js"></script>
<!-- 引入laydate控件，日期時間選擇 -->
<script type="text/javascript" src="../../laydate/laydate.js"></script>
<!-- 引入bootstrap table組件 -->
<script type="text/javascript" src="../../bootstrap-table/js/bootstrap-table.min.js"></script>
<link type="text/css" rel="stylesheet" href="../../bootstrap-table/css/bootstrap-table.min.css">
<script type="text/javascript" src="../../bootstrap-table/js/bootstrap-table-export.min.js"></script>
<script type="text/javascript" src="../../bootstrap-table/tableExport/tableExport.js"></script>
<script type="text/javascript" src="../../bootstrap-table/js/bootstrap-table-zh-CN.min.js"></script>

<link rel="stylesheet" type="text/css" href="../../bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="../../bootstrap/css/bootstrap-select.min.css">
<link rel="stylesheet" type="text/css" href="../../css/upload.css" />

<script type="text/javascript" src="../../js/archives.js"></script>
<script type="text/javascript" src="../../js/docDetail.js"></script>
<script src="//npmcdn.com/pdfjs-dist/build/pdf.js"></script>
</head>
<body>
	<div id="toolbar" class="docDetail col-md-12 col-lg-12">
		<div class="title"><h4>圖檔詳情</h4>
		</div>
		<div class="content detailContent">
			<input id="archives_partCode" type="hidden">
			<div class="leftcontent">
				<div class="number input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">料號</span>
					</div>
					<input type="text" class="form-control" id="number" name="number" placeholder="請輸入料號" readonly />
				</div>
				<p class="number-error-tip">料號不存在，請重新輸入</p>
				<div class="version input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">版本</span>
					</div>
					<input type="text" class="form-control" id="version" name="version" placeholder="自動生成最新版本" readonly/>
				</div>
<!-- 				<div class="date input-group mb-3"> -->
<!-- 					<div class="input-group-prepend"> -->
<!-- 						<span class="input-group-text">日期</span> -->
<!-- 					</div> -->
<!-- 					<input type="text" class="form-control" id="date" name="date" placeholder="自動生成日期" readonly/> -->
<!-- 				</div> -->
				<div class="name input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">品名</span>
					</div>
					<input type="text" class="form-control" id="name" name="name" placeholder="自動提取" readonly/>
				</div>
				<div class="speci input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">規格</span>
					</div>
					<input type="text" class="form-control" id="speci" name="speci" placeholder="自動提取" readonly/>
				</div>
				<div class="reason">
					<span class="input-group-text">設變原因</span>
				</div>
				<div class="reasondetail">
					<textarea id="changereason" class="form-control" rows="13" placeholder="客戶需求設變" readonly></textarea>
				</div>
			</div>
			<div class="rightcontent">
				<div class="improveImgContent">
					<div class="imgLeft">
						<label>改善前圖片</label>
						<div class="improveImg imgBefore">
							<div id="demo" class="carousel slide" data-ride="carousel" style="width:100%;">
								  <!-- 轮播图片 -->
								  <div class="carousel-inner" style="height:100%; ">
								  </div>
								
								  <!-- 左右切换按钮 -->
								  <a class="carousel-control-prev" href="#demo" data-slide="prev">
									<span class="carousel-control-prev-icon"></span>
								  </a>
								  <a class="carousel-control-next" href="#demo" data-slide="next">
									<span class="carousel-control-next-icon"></span>
								  </a>
				 
							</div>	
						</div>
						<div class="btnContent">
							<button class="btn btn-primary" onclick="showimage('imgBefore')">放大</button> 
							<button class="btn btn-primary" onclick="downloadimage('imgBefore')">下載</button>
						</div>
					</div>
					<div class="imgRight">
						<label>改善後圖片</label>
						<div class="improveImg imgAfter">
							<div id="demo2" class="carousel slide" data-ride="carousel" style="width:100%;">
								  <!-- 轮播图片 -->
								  <div class="carousel-inner" style="height:100%; ">
								  </div>
								
								  <!-- 左右切换按钮 -->
								  <a class="carousel-control-prev" href="#demo2" data-slide="prev">
									<span class="carousel-control-prev-icon"></span>
								  </a>
								  <a class="carousel-control-next" href="#demo2" data-slide="next">
									<span class="carousel-control-next-icon"></span>
								  </a>
				 
							</div>
						</div>
						<div class="btnContent">
							<button class="btn btn-primary" onclick="showimage('imgAfter')">放大</button>
							<button class="btn btn-primary" onclick="downloadimage('imgAfter')">下載</button>
						</div>
					</div>
				</div>
				<div style="border-bottom:1px solid #ccc;"></div>
				<div class="uploadDoc">
					<div class="uploadDocRgiht">
						<div class="docTitle">
							<span class="input-group-text">文檔</span>
						</div>
						<div class="docBox">
							<div class="docContent docPDFContent">
								<input type="text" class="form-control" id="docPDF" name="docPDF" placeholder="PDF文檔" readonly/>
								<input type="hidden" id="docPDFFile"/>
								<label class="docPDF btn btn-info" onclick="downloadDoc(this)">Download</label>
							</div>
							<div class="docSingle docPDFSingle">
							</div>
							
							<div class="docContent docDWGContent">
								<input type="text" class="form-control" id="docDWG" name="docDWG" placeholder="DWG文檔" readonly/>
								<input type="hidden" id="docDWGFile"/>
								<label class="docDWG btn btn-info" onclick="downloadDoc(this)">Download</label>
							</div>
							<div class="docSingle docDWGSingle">
							</div>
							<div class="docContent docPPTContent">
								<input type="text" class="form-control" id="docPPT" name="docPPT" placeholder="PPT文檔" readonly/>
								<input type="hidden" id="docPPTFile"/>
								<label class="docPPT btn btn-info" onclick="downloadDoc(this)">Download</label>
							</div>
							<div class="docSingle docPPTSingle">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="rightContent2">
				<label>PDF縮圖</label>
				<div class="improvePDF">
					<embed class="uploadPDF img-responsive" src="" type="">
				</div>
				<div class="btnContent">
					<button class="btn btn-primary" onclick="showimage('uploadPDF','pdf')">放大</button>
					<button class="btn btn-primary" onclick="downloadimage('uploadPDF')">下載</button>
				</div>
			</div>
		</div>
		<div class="control">
			<button id="btnClose" type="button" class="btn btn-secondary" onclick="closeWindow()">关闭</button>
		<!-- 	<button id="btnUpdateBom" class="btn btn-info" onclick="updateBom()">发起重审</button> -->
			<button id="btnApproveDoc" class="btn btn-info" onclick="approveDoc()">核准申请</button>
			<button id="btnUpdateDoc" class="btn btn-info" onclick="updateDoc()">修改</button>
			<button id="btnSendBackDoc" class="btn btn-danger" onclick="sendbackDoc()">退回申请</button>
			<button id="btnGetBackDoc" class="btn btn-danger" onclick="getbackDoc()">取回申请</button>
			<button id="btnCancelDoc" class="btn btn-warning" onclick="cancelDoc()">取消申请</button>
			<button id="btnDeleteDoc" class="btn btn-warning" onclick="deleteDoc()">廢止</button>
		</div>
	</div>

<%-- 	<jsp:include page="/pages/plugins/include_alert.jsp" /> --%>
	<div id="ShowImage_Form" class="modal hide">         
         <div class="modal-header">
             <button data-dismiss="modal" class="close" type="button">×</button>
         </div>
         <div class="modal-body">
            <div id="img_show">
            </div>
        </div>
    </div>
</body>
</html>
