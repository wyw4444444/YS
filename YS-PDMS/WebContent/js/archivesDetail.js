$('document').ready(function(){
	var part_code = localStorage.getItem("part_code")
	var version = localStorage.getItem("archives_version")
	console.log(part_code,version)
	
	//需要後台查詢該料號和版本的數據
	$.ajax({
		type : "POST",
		url : "doc/findOnedoc.action",
		dataType : "json",
		data : {
			part_code : part_code,
			version : version,
		},
		traditional : true,
		success : function(data) {
			console.log(data)
			$("#number").val(data.part_code);
			$("#version").val(data.version);
			$("#changereason").val(data.change_reason);
			
			//渲染改善前圖片
			var arrCB = data.changeimg_before.split(",")
			for(var i =0;i<arrCB.length;i++){
				if(i==0) var html = '<div class="carousel-item active" style="height:100%;"><img src="'+localFormatUrl(arrCB[i])+'" style="width:100%;height:100%;"></div>';
				else var html = '<div class="carousel-item" style="height:100%;"><img src="'+localFormatUrl(arrCB[i])+'" style="width:100%;height:100%;"></div>';
				$('.imgBefore .carousel-inner').append(html)
			}
			//渲染改善後圖片
			var arrCA = data.changeimg_after.split(",")
			for(var i =0;i<arrCA.length;i++){
				if(i==0) var html = '<div class="carousel-item active" style="height:100%;"><img src="'+localFormatUrl(arrCA[i])+'" style="width:100%;height:100%;"></div>';
				else var html = '<div class="carousel-item" style="height:100%;"><img src="'+localFormatUrl(arrCA[i])+'" style="width:100%;height:100%;"></div>';
				$('.imgAfter .carousel-inner').append(html)
			}
			if(data.doc_pdf){
//				pdf文檔可能出現多個，所以需要轉成數組，拿第一個出來顯示
				var pdfUrl = data.doc_pdf.split(",");
				$(".uploadPDF").attr("src",localFormatUrl(pdfUrl[0]));
				$("#docPDFFile").val(data.doc_pdf);
//				在input下方渲染多個a變遷可下載的文檔
				for(var i=0;i<pdfUrl.length;i++){
					var html = '<a href="'+localFormatUrl(pdfUrl[i])+'" Download>'+localFormatUrl(pdfUrl[i])+'</a>';
					$('.docPDFSingle').append(html)
				}
				$(".docPDF").attr("download","")
				$(".docPDF").attr("href",localFormatUrl(data.doc_pdf));				
			}
			if(data.doc_dwg){
				var dwgUrl = data.doc_dwg.split(",");
//				在input下方渲染多個a變遷可下載的文檔
				for(var i=0;i<dwgUrl.length;i++){
					var html = '<a href="'+localFormatUrl(dwgUrl[i])+'" Download>'+localFormatUrl(dwgUrl[i])+'</a>';
					$('.docDWGSingle').append(html)
				}
				$("#docDWGFile").val(data.doc_dwg);
				$(".docDWG").attr("download","")
				$(".docDWG").attr("href",localFormatUrl(data.doc_dwg));
			}else{
				$("#docDWG").val("暫無DWG文檔")
				$(".docDWG").attr("disabled",true)
			}
			if(data.doc_ppt){
				var pptUrl = data.doc_ppt.split(",");
//				在input下方渲染多個a變遷可下載的文檔
				for(var i=0;i<pptUrl.length;i++){
					var html = '<a href="'+localFormatUrl(pptUrl[i])+'" Download>'+localFormatUrl(pptUrl[i])+'</a>';
					$('.docPPTSingle').append(html)
				}
				$("#docPPTFile").val(data.doc_ppt);
				$(".docPPT").attr("download","")
				$(".docPPT").attr("href",localFormatUrl(data.doc_ppt));
			}else{
				$("#docPPT").val("暫無PPT文檔")
				$(".docPPT").attr("disabled",true)
			}
			
		}
	
	})
	//需要後台查詢該料號的數據
	$.ajax({
		type : "POST",
		url : "part/checkPartCode.action",
		dataType : "json",
		data : {
			partcode : part_code
		},
		traditional : true,
		success : function(data) {
			console.log(data)
			$("#name").val(data.tradename);
			$("#speci").val(data.spec);
		}
	
	})
})
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
//显示大图    
function showimage(t,type){
	if(type=="pdf"){
		var source = $('.'+t).attr('src')
		$("#ShowImage_Form").find("#img_show").html('<embed class="uploadPDF img-responsive" src="'+source+'" style="width:100%;height:100%;">');
		$('.modal-body').css("height","90%")
	}else{
		var source = $('.'+t+' .active img').attr('src');
		$("#ShowImage_Form").find("#img_show").html("<image src='"+source+"' class='carousel-inner img-responsive img-rounded' />");
	}
	$("#ShowImage_Form").modal();
}
function downloadimage(t){
	var source = $('.'+t+' img')
	for(var i=0;i<source.length;i++){
		var url = source.eq(i).attr('src');
		let a = document.createElement('a') // 创建a标签
		let e = document.createEvent('MouseEvents') // 创建鼠标事件对象
		e.initEvent('click', false, false) // 初始化事件对象
		a.href = url // 设置下载地址
		a.download = '' // 设置下载文件名
		a.dispatchEvent(e)
	}	
}