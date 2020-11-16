<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/upload.css" />

<script type="text/javascript" src="js/archivesDetail.js"></script>
<script src="//npmcdn.com/pdfjs-dist/build/pdf.js"></script>
</head>
<body>
	<div id="toolbar" class="col-md-10 col-lg-10">
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
