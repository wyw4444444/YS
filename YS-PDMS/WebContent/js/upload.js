$('document').ready(function(){
	$('.leftcontent #number').on('keyup',function(){
		var rs = checkPartCode($(this).val())//檢查料號的正確性
		console.log(rs)
//		if(rs){
			
//		}
	})
//	bsCustomFileInput.init()
//	改善前的圖片顯示
	$('#fileImproveImgBefore').on('change',function(){
		console.log($(this)[0].files[0])
		var obj=$(this)[0].files[0];
		var fr=new FileReader();
		fr.onload=function () {
			$(".uploadbefore").attr('src',this.result);
		};
		fr.readAsDataURL(obj);
	})
	//	改善後的圖片顯示
	$('#fileImproveImgAfter').on('change',function(){
		console.log($(this)[0].files[0])
		var obj=$(this)[0].files[0];
		var fr=new FileReader();
		fr.onload=function () {
			$(".uploadafter").attr('src',this.result);
		};
		fr.readAsDataURL(obj);
	})
	//pdf文檔的顯示
	$('#filePDF').on('change',function(){
		var obj = $(this)[0].files
		if(obj.length>4){
			$('#docPDF').val('')
			$('#filePDF').val('')
			toalert("文件數量不能大於4個")
		}
		if(obj.length>1){
			$('#docPDF').val($(this)[0].files[0].name+"等"+obj.length+"个文件")	
		}else{
			$('#docPDF').val($(this)[0].files[0].name)			
		}
	})
	//dwg文檔的顯示
	$('#fileDWG').on('change',function(){
		var obj = $(this)[0].files
		if(obj.length>4){
			$('#docDWG').val('')
			$('#fileDWG').val('')
			toalert("文件數量不能大於4個")
		}
		if(obj.length>1){
			$('#docDWG').val($(this)[0].files[0].name+"等"+obj.length+"个文件")	
		}else{
			$('#docDWG').val($(this)[0].files[0].name)			
		}
	})
	//ppt文檔的顯示
	$('#filePPT').on('change',function(){
		var obj = $(this)[0].files
		if(obj.length>4){
			$('#docPPT').val('')
			$('#filePPT').val('')
			toalert("文件數量不能大於4個")
		}
		if(obj.length>1){
			$('#docPPT').val($(this)[0].files[0].name+"等"+obj.length+"个文件")	
		}else{
			$('#docPPT').val($(this)[0].files[0].name)			
		}
	})
//	submit
	$('.submit').on('click',function(){
//		檢查必填項
		var checkrs = checkForm()
//		var checkrs=true
		if(checkrs){
//			提交文檔數據
			var part_code = $('#number').val();//料號
			var version = $('#version').val();//料號
			var change_reason = $('#changereason').val();//設變原因
//			改善前圖片
			var changeimg_before = ''
			if($('#fileImproveImgBefore').val()){
				changeimg_before = formatLocalPath("doc",{part_code:part_code,version:version})+"_changeimg_before.jpg";
			}
//			改善後圖片
			var changeimg_after = ''
			if($('#fileImproveImgAfter').val()){
				changeimg_after = formatLocalPath("doc",{part_code:part_code,version:version})+"_changeimg_after.jpg";				
			}
//			pdf文档
			var docPdfFiles = $('#filePDF')[0].files;
			var doc_pdf = '';
			for(var i = 0;i<docPdfFiles.length;i++){
				var fileExtension = docPdfFiles[i].name.split('.').pop().toLowerCase();
				var path = formatLocalPath("doc",{part_code:part_code,version:version})+"_doc_pdf_"+i+"."+fileExtension;
				if(i==0) doc_pdf+=path;
				else doc_pdf+=","+path;
			}
			console.log(doc_pdf)
//			dwg文档
			var docDwgFiles = $('#fileDWG')[0].files;
			var doc_dwg = '';
			for(var i = 0;i<docDwgFiles.length;i++){
				var fileExtension = docDwgFiles[i].name.split('.').pop().toLowerCase();
				var path = formatLocalPath("doc",{part_code:part_code,version:version})+"_doc_dwg_"+i+"."+fileExtension;
				if(i==0) doc_dwg+=path;
				else doc_dwg+=","+path;
			}
//			ppt文档
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
				url : "doc/add.action",
				dataType : "json",
				data : {
					part_code : part_code,
					version : version,
					change_reason : change_reason,
					changeimg_before : changeimg_before,
					changeimg_after : changeimg_after,
					doc_pdf : doc_pdf,
					doc_dwg : doc_dwg,
					doc_ppt : doc_ppt
				},
				traditional : true,
				success : function(data) {
					if(data){
						uploadFile("fileImproveImgBefore",changeimg_before)
						uploadFile("fileImproveImgAfter",changeimg_after)
						uploadFile("filePDF",doc_pdf)
						uploadFile("fileDWG",doc_dwg)
						uploadFile("filePPT",doc_ppt)
						alert("提交成功")
						location.reload()
						tosuccess()
					}
				}
			
			})
//			
//			console.log("提交成功")
//			var form = new FormData(); // FormData 对象
//		    form.append("file", $('#fileImproveImgBefore')[0].files[0]); // 文件对象
//		    form.append("part_code", "123"); // 文件对象
//		    form.append("no", "2"); // 文件对象
//		    
//		    xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
//		    xhr.open("post","doc/addpicture.action", true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
//		    xhr.upload.onloadstart = function(){//上传开始执行方法
//		        ot = new Date().getTime();   //设置上传开始时间
//		        oloaded = 0;//设置上传开始时，以上传的文件大小为0
//		    };
//		    xhr.send(form); //开始上传，发送form数据
		}else{
			
		}
	})
	
	$('.close').on('click', function () {
	  	$(this).parents('.alert').hide()
	})
})
function uploadFile(id,path){
	var files = $('#'+id)[0].files;
	var pathArr = path.split(',')
	for(var i=0;i<files.length;i++){
		console.log(files[i],pathArr[i])
		var form = new FormData(); // FormData 对象
		form.append("file", files[i]); // 文件对象
		form.append("path", pathArr[i]); // 文件对象
		
		xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
		xhr.open("post","doc/addpicture.action", true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
		xhr.upload.onloadstart = function(){//上传开始执行方法
			ot = new Date().getTime();   //设置上传开始时间
			oloaded = 0;//设置上传开始时，以上传的文件大小为0
		};
		xhr.send(form); //开始上传，发送form数据		
	}
}
function checkForm(){
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
	var fileImproveImgBefore = $('#fileImproveImgBefore').val();
	if(!fileImproveImgBefore){
		toalert("請選擇改善前圖片");
		return false;
	}
	var fileImproveImgAfter = $('#fileImproveImgAfter').val();
	if(!fileImproveImgAfter){
		toalert("請選擇改善後圖片");
		return false;
	}
	var filePDF = $('#filePDF').val();
	if(!filePDF){
		toalert("請選擇PDF檔案");
		return false;
	}
	return true;
}
function toalert(value){
	$('.alert-danger').show();
	$('.alert-danger span.text').text(value)
}
function tosuccess(){
	$('.alert-success').show();
}
function reset(){
	$('#number').val('')
	$('#version').val('')
	$('#date').val('')
	$('#name').val('')
	$('#speci').val('')
}

//執行檢查料號是否存在
function checkPartCode(value){
	$.ajax({
		url : "part/checkPartCode.action",
		type : "post",
		dataType : "json",
		data : {
			partcode : value
		},
		success : function(data) {
			console.log(data)
			if(data){
				$('.leftcontent .number').removeClass('has-error')
				$('.leftcontent .number').addClass('has-success')
				$('.number-error-tip').hide()
				//存在就渲染該料號的數據
				
				$('.leftcontent #name').val(data.tradename)
				$('.leftcontent #date').val(data.unit)
				$('.leftcontent #speci').val(data.spec)
				
				getMaxVersion(value)//獲取最大版本號
				
			}else{
				$('.leftcontent .number').removeClass('has-success')
				$('.leftcontent .number').addClass('has-error')
				$('.number-error-tip').show()
				//數據清空
				$('.leftcontent #name').val('')
				$('.leftcontent #date').val('')
				$('.leftcontent #speci').val('')
				$('.leftcontent #version').val('')
			}
		},
		error:function(data){
			if(!data.responseText){
				$('.leftcontent .number').removeClass('has-success')
				$('.leftcontent .number').addClass('has-error')
				$('.number-error-tip').show()
				//數據清空
				$('.leftcontent #name').val('')
				$('.leftcontent #date').val('')
				$('.leftcontent #speci').val('')
				$('.leftcontent #version').val('')
			}
		}
	})
}
//獲取最大版本號
function getMaxVersion(value){
	$.ajax({
		url : "doc/getMaxVersion.action",
		type : "post",
		dataType : "json",
		data : {
			part_code : value
		},
		success : function(data) {
			console.log(data)
			$('#version').val(data)
		}
	})
}
function formatLocalPath(type,param){
	var url = '';
	switch(type){
	case "doc":
		url = "E://part-images/"+param.part_code+"_"+param.version+"/"+param.part_code+"_"+param.version;
		break;
	}
	return url;
}


