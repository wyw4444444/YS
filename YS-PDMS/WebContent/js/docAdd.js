var member_id;
var id;
$('document').ready(function(){
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = decodeURIComponent(window.location.search).substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	member_id = $.getUrlParam('member_id');
	id = $.getUrlParam("doc_id")
	
	//需要後台查詢該料號和版本的數據
	$.ajax({
		type : "POST",
		url : "../../doc/findById.action",
		dataType : "json",
		data : {
			id : id,
		},
		traditional : true,
		success : function(data) {
			console.log(data)
			$("#number").val(data.part_code);

			//查詢該料號的一些基本屬性，並渲染出來
			$.ajax({
				type : "POST",
				url : "../../doc/checkPartCode.action",
				dataType : "json",
				data : {
					partcode : $("#number").val()
				},
				traditional : true,
				success : function(data) {
					console.log(data)
					$("#name").val(data.tradename);
					$("#speci").val(data.spec);
				}
			
			})
			$("#version").val(data.version);
			$("#changereason").val(data.change_reason);
			
			//渲染改善前圖片
			var arrCB = data.changeimg_before.split(",")
			$('.uploadbefore').attr("src",localFormatUrl(arrCB[0]))
			$('#fileImproveImgBefore').data("imgArr",data.changeimg_before)
			//渲染改善後圖片
			var arrCA = data.changeimg_after.split(",")
			$('.uploadafter').attr("src",localFormatUrl(arrCA[0]))
			$('#fileImproveImgAfter').data("imgArr",data.changeimg_after)
			if(data.doc_pdf){
				var pdfUrl = data.doc_pdf.split(",");
				$('#docPDF').val(pdfUrl[0].split('/').pop()+"等"+pdfUrl.length+"个文件")		
			}
			if(data.doc_dwg){
				var dwgUrl = data.doc_dwg.split(",");
				$('#docDWG').val(dwgUrl[0].split('/').pop()+"等"+dwgUrl.length+"个文件")		
			}
			if(data.doc_ppt){
				var pptUrl = data.doc_ppt.split(",");
				$('#docPPT').val(pptUrl[0].split('/').pop()+"等"+pptUrl.length+"个文件")	
			}
			//根據狀態決定顯示哪幾個操作按鈕
			switch(data.status){
			case 1:
				$('#btnApproveDoc').show();
				$('#btnSendBackDoc').show();
				$('#btnGetBackDoc').show();
				$('#btnCancelDoc').show();
				break;
			case 2,3,4:
				$('#btnUpdateDoc').show();
				break;
			case 5:
				$('#btnDeleteDoc').show();
				break;
			}
			$('#btnClose').show();
			loadArchivesAdd()
		}
	
	})
	$('.update').on('click',function(){
//			檢查必填項
		var partcode = $('#number').val();
		if(!partcode){
			toalert("請填寫料號");
			return false;
		}
		var name = $('#name').val();
		if(!name){
			toalert("請填寫正確的料號");
			return false;
		}
		var changereason = $('#changereason').val();
		if(!changereason){
			toalert("請填寫設變原因");
			return false;
		}
		console.log(changereason.length)
		if(changereason.length>100){
			toalert("設變原因請控制在100字以內");
			return false;
		}
		var fileImproveImgBefore = $('#fileImproveImgBefore').val();
		var fileImproveImgBefore2 = $('#fileImproveImgBefore').data("imgArr");
		console.log(fileImproveImgBefore2)
		if(!fileImproveImgBefore&&!fileImproveImgBefore2){
			toalert("請選擇改善前圖片");
			return false;
		}
		var fileImproveImgAfter = $('#fileImproveImgAfter').val();
		var fileImproveImgAfter2 = $('#fileImproveImgAfter').data("imgArr");
		if(!fileImproveImgAfter&&!fileImproveImgAfter2){
			toalert("請選擇改善後圖片");
			return false;
		}
		var filePDF = $('#filePDF').val();
		var docPDF = $('#docPDF').val();
		if(!filePDF&&!docPDF){
			toalert("請選擇PDF檔案");
			return false;
		}
//				提交文檔數據
			var part_code = $('#number').val();//料號
			var version = $('#version').val();//料號
			var change_reason = $('#changereason').val();//設變原因
//				改善前圖片
			var changeimg_before = ''
			var changeimg_beforeFiles = $('#fileImproveImgBefore')[0].files;
			for(var i = 0;i<changeimg_beforeFiles.length;i++){
				var fileExtension = changeimg_beforeFiles[i].name.split('.').pop().toLowerCase();
				var path = formatLocalPath("doc",{part_code:part_code,version:version})+"_changeimg_before_"+i+"."+fileExtension;
				if(i==0) changeimg_before+=path;
				else changeimg_before+=","+path;
			}
//				改善後圖片
			var changeimg_after = ''
			var changeimg_afterFiles = $('#fileImproveImgAfter')[0].files;
			for(var i = 0;i<changeimg_afterFiles.length;i++){
				var fileExtension = changeimg_afterFiles[i].name.split('.').pop().toLowerCase();
				var path = formatLocalPath("doc",{part_code:part_code,version:version})+"_changeimg_after_"+i+"."+fileExtension;
				if(i==0) changeimg_after+=path;
				else changeimg_after+=","+path;
			}
//				pdf文档
			var docPdfFiles = $('#filePDF')[0].files;
			var doc_pdf = '';
			for(var i = 0;i<docPdfFiles.length;i++){
				var fileExtension = docPdfFiles[i].name.split('.').pop().toLowerCase();
				var path = formatLocalPath("doc",{part_code:part_code,version:version})+"_doc_pdf_"+i+"."+fileExtension;
				if(i==0) doc_pdf+=path;
				else doc_pdf+=","+path;
			}
			console.log(doc_pdf)
//				dwg文档
			var docDwgFiles = $('#fileDWG')[0].files;
			var doc_dwg = '';
			for(var i = 0;i<docDwgFiles.length;i++){
				var fileExtension = docDwgFiles[i].name.split('.').pop().toLowerCase();
				var path = formatLocalPath("doc",{part_code:part_code,version:version})+"_doc_dwg_"+i+"."+fileExtension;
				if(i==0) doc_dwg+=path;
				else doc_dwg+=","+path;
			}
//				ppt文档
			var docPptFiles = $('#filePPT')[0].files;
			var doc_ppt = '';
			for(var i = 0;i<docPptFiles.length;i++){
				var fileExtension = docPptFiles[i].name.split('.').pop().toLowerCase();
				var path = formatLocalPath("doc",{part_code:part_code,version:version})+"_doc_ppt_"+i+"."+fileExtension;
				if(i==0) doc_ppt+=path
				else doc_ppt+=","+path
			}
			
			$.ajax({
				type : "POST",
				url : "../../doc/updateDoc.action",
				dataType : "json",
				data : {
					id:id,
					part_code : part_code,
					version : version,
					change_reason : change_reason,
					changeimg_before : changeimg_before,
					changeimg_after : changeimg_after,
					doc_pdf : doc_pdf,
					doc_dwg : doc_dwg,
					doc_ppt : doc_ppt,
					member_id:member_id
				},
				traditional : true,
				success : function(data) {
					if(data){
						console.log(changeimg_before)
						uploadFile("fileImproveImgBefore",changeimg_before)
						uploadFile("fileImproveImgAfter",changeimg_after)
						uploadFile("filePDF",doc_pdf)
						uploadFile("fileDWG",doc_dwg)
						uploadFile("filePPT",doc_ppt)
						alert("提交成功")
						window.close()
						opener.loadAwait('1');
					}
				}
			
			})
	})
})
function formatLocalPath(type,param){
	var url = '';
	switch(type){
	case "doc":
		url = "E://part-images/"+param.part_code+"_"+param.version+"/"+param.part_code+"_"+param.version;
		break;
	}
	return url;
}
function uploadFile(id,path){
	var files = $('#'+id)[0].files;
	var pathArr = path.split(',')
	for(var i=0;i<files.length;i++){
		console.log(files[i],pathArr[i])
		var form = new FormData(); // FormData 对象
		form.append("file", files[i]); // 文件对象
		form.append("path", pathArr[i]); // 文件对象
		
		xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
		xhr.open("post","../../doc/addpicture.action", true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
		xhr.upload.onloadstart = function(){//上传开始执行方法
			ot = new Date().getTime();   //设置上传开始时间
			oloaded = 0;//设置上传开始时，以上传的文件大小为0
		};
		xhr.send(form); //开始上传，发送form数据		
	}
}
function toalert(value){
	$('.alert-danger').show();
	$('.alert-danger span.text').text(value)
}
function tosuccess(){
	$('.alert-success').show();
}
function downloadDoc(t){
	var obj = $(t).parent().find('input[type="hidden"]');
	var urlStr = obj.val();
	var urlArr = urlStr.split(",");
	console.log(urlArr);
	if(urlArr[0]){
		for(var i=0;i<urlArr.length;i++){
			var url = localFormatUrl(urlArr[i]);
			let a = document.createElement('a') // 创建a标签
			let e = document.createEvent('MouseEvents') // 创建鼠标事件对象
			e.initEvent('click', false, false) // 初始化事件对象
			a.href = url // 设置下载地址
			a.download = '' // 设置下载文件名
				a.dispatchEvent(e)
		}		
	}
}
function localFormatUrl(url){
	return "/file"+url.substring(15);
}

function closeWindow(){
	window.close();
}







