<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/upload.css" />
<script type="text/javascript" src="js/upload.js"></script>
</head>
<body>
	<div id="toolbar" class="col-md-10 col-lg-10">
		<div class="title"><h4>上傳界面</h4>
		</div>
		<div class="content">
			<div class="leftcontent">
				<div class="number input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">料號</span>
					</div>
					<input type="text" class="form-control" id="number" name="number" placeholder="請輸入料號" />
				</div>
				<p class="number-error-tip">料號不存在，請重新輸入</p>
				<div class="version input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">版本</span>
					</div>
					<input type="text" class="form-control" id="version" name="version" placeholder="自動生成最新版本" readonly/>
				</div>
				<div class="date input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">日期</span>
					</div>
					
					<input type="text" class="form-control" id="date" name="date" placeholder="自動生成日期" readonly/>
				</div>
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
					<textarea id="changereason" class="form-control" rows="13" placeholder="客戶需求設變，100字以內"></textarea>
				</div>
			</div>
			<div class="rightcontent">
				<div class="improveImgContent">
					<div class="imgLeft">
						<label><span style="color:red">*</span>請上傳改善前圖片</label>
						<div class="improveImg imgBefore">
							<label for="fileImproveImgBefore"><img class="uploadbefore img-fluid" src="images/upload.png"></label>
							<input id="fileImproveImgBefore" type="file" style="display:none" accept=".jpg,.png" multiple>
						</div>
						<div class="btnContent">
							<button class="btn btn-primary" onclick="showimage('fileImproveImgBefore')">查看所選圖片</button>
							<button class="btn btn-primary" onclick="reuploadimage('fileImproveImgBefore')">重新上傳</button>
						</div>
					</div>
					<div class="imgRight">
						<label><span style="color:red">*</span>請上傳改善後圖片</label>
						<div class="improveImg imgAfter">
							<label for="fileImproveImgAfter"><img class="uploadafter img-responsive" src="images/upload.png"></label>
							<input id="fileImproveImgAfter" type="file" style="display:none" accept=".jpg,.png" multiple> 
						</div>
						<div class="btnContent">
							<button class="btn btn-primary" onclick="showimage('fileImproveImgAfter')">查看所選圖片</button>
							<button class="btn btn-primary" onclick="reuploadimage('fileImproveImgAfter')">重新上傳</button>
						</div>
					</div>
				</div>
				<div class="uploadDoc">
					<div class="docTitle">
						<span class="input-group-text">上傳文檔<span style="color:red;">(上傳文件數不能大於4個)</span></span>
					</div>
					<div class="docContent">
						<input type="text" class="form-control" id="docPDF" name="docPDF" placeholder="請選擇檔案PDF" readonly/>
						<label for="filePDF" class="btn btn-info"><span style="color:red">*</span>上傳PDF文檔</label>
						<input id="filePDF" type="file" style="display:none" accept=".pdf" multiple>
					</div>
					<div class="docContent">
						<input type="text" class="form-control" id="docDWG" name="docDWG" placeholder="請選擇檔案DWG" readonly/>
						<label for="fileDWG" class="btn btn-info">上傳DWG文檔</label>
						<input id="fileDWG" type="file" style="display:none" accept=".dwg" multiple>
					</div>
					<div class="docContent">
						<input type="text" class="form-control" id="docPPT" name="docPPT" placeholder="請選擇檔案PPT" readonly/>
						<label for="filePPT" class="btn btn-info">上傳PPT文檔</label>
						<input id="filePPT" type="file" style="display:none" accept=".ppt,.pptx,.jpg" multiple>
					</div>
					<div class="submitContent">
						<button class="submit btn btn-primary">提交</button>
						<div class="alert alert-danger alert-dismissible" role="alert" style="display:none;">
							  <button type="button" class="close"><span aria-hidden="true">&times;</span></button>
							  <span class="text"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-lg-6">
	</div>
	<div class="modal amodal" tabindex="-1" id="amodal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">提交提醒</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
			        <p>Modal body text goes here.</p>
				</div>
				<div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div id="ShowImage_Form" class="modal hide" style="overflow-y:hidden;">         
         <div class="modal-header">
             <button data-dismiss="modal" class="close" type="button">×</button>
         </div>
         <div class="modal-body" style="overflow-y:scroll;height:100%;">
            <div id="demo" class="carousel slide" data-ride="carousel">
				<!-- 指示符 -->
				  <ul class="carousel-indicators" id="carousel-indicators2">
				  </ul>
				 
				 
				  <!-- 轮播图片 -->
				  <div class="carousel-inner">

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
    </div>

<%-- 	<jsp:include page="/pages/plugins/include_alert.jsp" /> --%>

</body>
</html>
