//打開新頁面
function open_page(type,id){
	switch(type){
	case "doc":
		var page = "pages/pop-up-windows/docDetail.jsp?";
		window.open(page+"member_id="+member_id+"&doc_id="+id,"",'height=900, width=1400, top=40, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no'); 	
		break;
	}

}

function loadArchivesAdd(){
	$('document').ready(function(){
		$('.leftcontent #date').val(new Date().format('yyyy-MM-dd'))
		$('.leftcontent #number').on('keyup',function(){
			var rs = checkPartCode($(this).val())//檢查料號的正確性
			console.log(rs)
//			if(rs){
				
//			}
		})
//		bsCustomFileInput.init()
//		改善前的圖片顯示
		$('#fileImproveImgBefore').on('change',function(){
			console.log($(this)[0].files[0])
			var obj=$(this)[0].files[0];
			var fr=new FileReader();
			fr.onload=function () {
				$(".uploadbefore").attr('src',this.result);
			};
			fr.readAsDataURL(obj);
			$('#fileImproveImgBefore').data("imgArr","")
			
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
			$('#fileImproveImgAfter').data("imgArr","")
		})
		//pdf文檔的顯示
		$('#filePDF').on('change',function(){
			var obj = $(this)[0].files
			if(obj.length>4){
				$('#docPDF').val('')
				$('#filePDF').val('')
				toalert("文件數量不能大於4個")
			}else{
				$('.alert-danger').hide();
				if(obj.length>1){
					$('#docPDF').val($(this)[0].files[0].name+"等"+obj.length+"个文件")	
				}else{
					$('#docPDF').val($(this)[0].files[0].name)			
				}
			}
		})
		//dwg文檔的顯示
		$('#fileDWG').on('change',function(){
			var obj = $(this)[0].files
			if(obj.length>4){
				$('#docDWG').val('')
				$('#fileDWG').val('')
				toalert("文件數量不能大於4個")
			}else{
				$('.alert-danger').hide();
				if(obj.length>1){
					$('#docDWG').val($(this)[0].files[0].name+"等"+obj.length+"个文件")	
				}else{
					$('#docDWG').val($(this)[0].files[0].name)			
				}
			}
		})
		//ppt文檔的顯示
		$('#filePPT').on('change',function(){
			var obj = $(this)[0].files
			if(obj.length>4){
				$('#docPPT').val('')
				$('#filePPT').val('')
				toalert("文件數量不能大於4個")
			}else{
				$('.alert-danger').hide();
				if(obj.length>1){
					$('#docPPT').val($(this)[0].files[0].name+"等"+obj.length+"个文件")	
				}else{
					$('#docPPT').val($(this)[0].files[0].name)			
				}
			}
		})
//		submit
		$('.submit').on('click',function(){
//			檢查必填項
			var checkrs = checkForm()
//			var checkrs=true
			if(checkrs){
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
						doc_ppt : doc_ppt,
						member_id:member_id
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
//				console.log("提交成功")
//				var form = new FormData(); // FormData 对象
//			    form.append("file", $('#fileImproveImgBefore')[0].files[0]); // 文件对象
//			    form.append("part_code", "123"); // 文件对象
//			    form.append("no", "2"); // 文件对象
//			    
//			    xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
//			    xhr.open("post","doc/addpicture.action", true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
//			    xhr.upload.onloadstart = function(){//上传开始执行方法
//			        ot = new Date().getTime();   //设置上传开始时间
//			        oloaded = 0;//设置上传开始时，以上传的文件大小为0
//			    };
//			    xhr.send(form); //开始上传，发送form数据
			}else{
				
			}
		})
		
		$('.close').on('click', function () {
		  	$(this).parents('.alert').hide()
		})
		$('.carousel').carousel()
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
		console.log(changereason.length)
		if(changereason.length>100){
			toalert("設變原因請控制在100字以內");
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
			url : "doc/checkPartCode.action",
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
//					$('.leftcontent #date').val(data.unit)
					$('.leftcontent #speci').val(data.spec)
					
					getMaxVersion(value)//獲取最大版本號
					
				}else{
					$('.leftcontent .number').removeClass('has-success')
					$('.leftcontent .number').addClass('has-error')
					$('.number-error-tip').show()
					//數據清空
					$('.leftcontent #name').val('')
//					$('.leftcontent #date').val('')
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
//					$('.leftcontent #date').val('')
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
				console.log(data.success)
				if(data.result=="0"){
					$('#version').val("A")
				}else{
					$('#version').val(String.fromCharCode(data.result.charCodeAt(0) + 1))
				}
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

	

}
//显示大图    
function showimage(t){
	console.log(1,$('#'+t).data("imgArr"))
	if($('#'+t).data("imgArr")){
		$("#demo .carousel-indicators").html('')
		$("#demo .carousel-inner").html('')
		var arr = $('#'+t).data("imgArr").split(",");
		for(var i =0;i<arr.length;i++){
			if(i==0){
				var html = '<li data-target="#demo" data-slide-to="'+i+'" class="active"></li>';
				var html2 = '<div class="carousel-item active"><img class="c-img" src="'+localFormatUrl(arr[i])+'"></div>';
			}else{
				var html = '<li data-target="#demo" data-slide-to="'+i+'"></li>';
				var html2 = '<div class="carousel-item"><img class="c-img" src="'+localFormatUrl(arr[i])+'"></div>';
			}
			$("#demo .carousel-indicators").append(html)
			$("#demo .carousel-inner").append(html2)
			
			$("#ShowImage_Form").modal();
		}
	}else{
		var obj=$('#'+t)[0].files;
		$("#demo .carousel-indicators").html('')
		$("#demo .carousel-inner").html('')
		$.each(obj,function(i,value){
			var source = value
			var fr=new FileReader();
			fr.onload=function () {
				if(i==0){
					var html = '<li data-target="#demo" data-slide-to="'+i+'" class="active"></li>';
					var html2 = '<div class="carousel-item active"><img class="c-img" src="'+this.result+'"></div>';
				}else{
					var html = '<li data-target="#demo" data-slide-to="'+i+'"></li>';
					var html2 = '<div class="carousel-item"><img class="c-img" src="'+this.result+'"></div>';
				}
				$("#demo .carousel-indicators").append(html)
				$("#demo .carousel-inner").append(html2)
			};
			fr.readAsDataURL(source);
		})
		
		
		$("#ShowImage_Form").modal();
	}
}
function reuploadimage(id){
	$('#'+id).click();
}
//文檔查詢頁面
function loadDoc(type){
	var url;
	var part_code = $('#docSearchKeyWord').val();
	console.log(part_code)
	switch(type){
		case "1":
			url="doc/findByPartCode.action";
			searchDoc(url)
			break;
		case "2":
			if(part_code){
				url="doc/findNewByPartCode.action";
				searchDoc(url)
			}else{
				url="doc/findAllNewDoc.action";
				searchDoc(url)
			}
			break;
		case "3":
			if(part_code){
				url="doc/findDocByBom.action";
				searchBomDoc(url)
			}else{
				alert("請輸入料號")
			}
			break;
	}
	
}
function searchDoc(url){
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: url,
		dataType: "json",
		height : tableHeight(),
		striped: true,
		cache: false,
		sortable: true,
		totalField: "count",
		dataField: "list", //后端 json 对应的表格数据 key
		pageNumber: 1,
		pagination: true,
		pageSize: 15, //单页记录数
		pageList: [5,10,15,20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			console.log(params,parseInt(params.offset/5)+1);
			console.log(params.limit);
            return {
            	part_code:$('#docSearchKeyWord').val(),
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
//				column : column,
//				keyword : keyword
            }
        },
		paginationLoop: false,
		paginationHAlign : "left",
		//buttonsPrefix: 'btn btn-sm',
		toolbar: "#toolbar",
        showColumns: true,
        showRefresh: true,
        clickToSelect: true,//是否启用点击选中行
		minimumCountColumns: 2,
		theadClasses : "thead-dark", // 表頭顏色
		showExport : true, // 是否显示导出按钮
		buttonsAlign : "right", // 按钮位置
		exportTypes : ['excel','xlsx'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allDoc', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
            console.log(111,data);
        },
        idField: 'id',//主键
		columns : [{
					field : 'part_code',
					title : '料號',
					valign : 'middle',
					align : 'center'

				}, {
					field : 'version',
					title : '版本',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'reg_time',
					title : '日期',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				},{
					field : 'spec',
					title : '屬性',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'change_reason',
					title : '新增和設變原因',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'operation',
					title : '查看',
					valign : 'middle',
					align : 'center',
					events : operateEventsArchives,
					formatter:function(value,row,index){
			            return [
			            	"<button id='searchDoc' class='btn btn-primary' href='#' title='查看'>查看</button>"
			            ].join("");
				    }
				}]
	});
}
function searchBomDoc(url){
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: url,
		dataType: "json",
		height : tableHeight(),
		striped: true,
		cache: false,
		sortable: true,
		totalField: "count",
		dataField: "list", //后端 json 对应的表格数据 key
		pageNumber: 1,
		pagination: true,
		pageSize: 15, //单页记录数
		pageList: [5,10,15,20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			console.log(params,parseInt(params.offset/5)+1);
			console.log(params.limit);
            return {
            	part_code:$('#docSearchKeyWord').val(),
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
//				column : column,
//				keyword : keyword
            }
        },
		paginationLoop: false,
		paginationHAlign : "left",
		//buttonsPrefix: 'btn btn-sm',
		toolbar: "#toolbar",
        showColumns: true,
        showRefresh: true,
        clickToSelect: true,//是否启用点击选中行
		minimumCountColumns: 2,
		theadClasses : "thead-dark", // 表頭顏色
		showExport : true, // 是否显示导出按钮
		buttonsAlign : "right", // 按钮位置
		exportTypes : ['excel','xlsx'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allDoc', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
            console.log(111,data);
        },
        idField: 'id',//主键
		columns : [{
			field : 'level',
			title : '階次',
			valign : 'middle',
			align : 'center'

		}, {
			title : '料號',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
	            if(row.part_code_down){
	            	return row.part_code_down;
	            }else{
	            	return row.part_code_up
	            }
		    }
				
		}, {
			field : 'ver',
			title : '版本',
			valign : 'middle',
			align : 'center'
		}, {
			field : 'partInfo.spec',
			title : '屬性',
			valign : 'middle',
			align : 'center'
		}, {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			events : operateEventsArchives,
			formatter:function(value,row,index){
	            return [
	            	"<button id='searchDoc' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
}

//操作上傳頁面先進行的設置處理函數
function uploadSetting() {
//		//設置showUpdateDept模態框關閉的更新列表事件
	$('#amodal').on('hide.bs.modal', function(e) {
		$("#contentDept").bootstrapTable("refresh");
	});
	
	// 設置更新部門模態框的垂直位置
	$('#amodal').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateDept').find('.modal-dialog').height();
				return ($(window).height() / 2 - (modalHeight / 2) - 250);
			}
		});
	});
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}
//打開
function archivesDetail() {
//		//設置showUpdateDept模態框關閉的更新列表事件
	$('#amodal').on('hide.bs.modal', function(e) {
		$("#contentDept").bootstrapTable("refresh");
	});
	
	// 設置更新部門模態框的垂直位置
	$('#amodal').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateDept').find('.modal-dialog').height();
				return ($(window).height() / 2 - (modalHeight / 2) - 250);
			}
		});
	});
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}
//操作待處理前先進行的設置處理函數
function processSetting() {
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}
//進入待處理頁面後執行
function loadProcess() {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url = "";
	var column="";
	var keyword="";
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: url,
		dataType: "json",
		height : tableHeight(),
		striped: true,
		cache: false,
		sortable: true,
		totalField: "count",
		dataField: "list", //后端 json 对应的表格数据 key
		pageNumber: 1,
		pagination: true,
		pageSize: 15, //单页记录数
		pageList: [5,10,15,20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
            return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				column : column,
				keyword : keyword
            }
        },
		paginationLoop: false,
		paginationHAlign : "left",
		//buttonsPrefix: 'btn btn-sm',
		toolbar: "#toolbar",
        showColumns: true,
        showRefresh: true,
        clickToSelect: true,//是否启用点击选中行
		minimumCountColumns: 2,
		theadClasses : "thead-dark", // 表頭顏色
		showExport : true, // 是否显示导出按钮
		buttonsAlign : "right", // 按钮位置
		exportTypes : ['excel','xlsx'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allDoc', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
            //console.log(data);
        },
        idField: 'dept_id',//主键
		columns : [{
					field : 'dept_id',
					title : '料號',
					valign : 'middle',
					align : 'center'

				}, {
					field : 'dept_code',
					title : '版本',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'reg_date',
					title : '日期',
					valign : 'middle',
					align : 'center'
				},{
					field : 'dept_name',
					title : '屬性',
					valign : 'middle',
					align : 'center'
				},{
					field : 'dept_name',
					title : '新增和設變原因',
					valign : 'middle',
					align : 'center'
				},{
					field : 'dept_name',
					title : '狀態',
					valign : 'middle',
					align : 'center'
				},{
					field : 'dept_name',
					title : '處理類型',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					events : operateEventsArchives,
					formatter:function(value,row,index){
				        if(value==1){
				            return [
				            	"<a id='lockOrUnlockDept' href='#' title='解锁'><i class='fa fa-lock' style='color:red'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editDept' href='#' title='编辑部门'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }else if(value==0){
				            return [
				            	"<a id='lockOrUnlockDept' href='#' title='锁定'><i class='fa fa-unlock' style='color:green'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editDept' href='#' title='编辑部门'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }
				    }
				}]
	});
}

//進入查詢頁面後執行
function loadArchive() {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url = "doc/findAlldoc.action";
	var column="1";
	var keyword="1";
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: url,
		dataType: "json",
		height : tableHeight(),
		striped: true,
		cache: false,
		sortable: true,
		totalField: "count",
		dataField: "list", //后端 json 对应的表格数据 key
		pageNumber: 1,
		pagination: true,
		pageSize: 15, //单页记录数
		pageList: [5,10,15,20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			console.log(params,parseInt(params.offset/5)+1);
			console.log(params.limit);
            return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				column : column,
				keyword : keyword
            }
        },
		paginationLoop: false,
		paginationHAlign : "left",
		//buttonsPrefix: 'btn btn-sm',
		toolbar: "#toolbar",
        showColumns: true,
        showRefresh: true,
        clickToSelect: true,//是否启用点击选中行
		minimumCountColumns: 2,
		theadClasses : "thead-dark", // 表頭顏色
		showExport : true, // 是否显示导出按钮
		buttonsAlign : "right", // 按钮位置
		exportTypes : ['excel','xlsx'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allDept', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
            console.log(111,data);
        },
        idField: 'id',//主键
		columns : [{
					field : 'part_code',
					title : '料號',
					valign : 'middle',
					align : 'center',
					width:200

				}, {
					field : 'version',
					title : '版本',
					valign : 'middle',
					align : 'center',
					width:100
				}, {
					field : 'reg_time',
					title : '日期',
					valign : 'middle',
					align : 'center',
					width:100,
					formatter: function (value, row, index) {
						return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				},{
					field : 'spec',
					title : '屬性',
					valign : 'middle',
					align : 'center',
					width:100
				}, {
					field : 'change_reason',
					title : '新增和設變原因',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'operation',
					title : '查看',
					valign : 'middle',
					align : 'center',
					width:100,
					events : operateEventsArchives,
					formatter:function(value,row,index){
						console.log("123",value,row,index)
			            return [
			            	"<button id='searchDoc' class='btn btn-primary' href='#' title='查看'>查看</button>"
			            ].join("");
				    }
				}]
	});
}
function loadArchivesProcessed(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	switch(searchType){
		case "1":
			url="doc/listDocProcessed.action";  //待审核
			break;
		case "2":
			url="checklog/listAwaitRehandleSplit.action"; //需重办
			break;
		case "3":
			url="checklog/listAwaitHandledSplit.action";   //已发起(取回重办)
			break;
	}
	//console.log(url);
	var table = $("#contentAwait");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: url,
		dataType: "json",
		height : tableHeight(),
		striped: true,
		cache: false,
		sortable: true,
		totalField: "count",
		dataField: "list", //后端 json 对应的表格数据 key
		pageNumber: 1,
		pagination: true,
		pageSize: 15, //单页记录数
		pageList: [5,15,30,50,100,2000,5000],//分页步进值
		sidePagination : "client",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
          return {
				member_id:member_id
          }
      },
		paginationLoop: false,
		paginationHAlign : "left",
		//buttonsPrefix: 'btn btn-sm',
		toolbar: "#toolbar",
		showColumns: true,
		showRefresh: true,
		clickToSelect: true,//是否启用点击选中行
		minimumCountColumns: 2,
		theadClasses : "thead-dark", // 表頭顏色
		showExport : true, // 是否显示导出按钮
		buttonsAlign : "right", // 按钮位置
		exportTypes : ['xlsx'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allAwait', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
          //console.log(data);
      },
//      idField: '',//主键
		columns : [{
			field : 'checked',
			checkbox:true,
			valign : 'middle',
			align : 'center',
			visible:searchType==1?true:false
		}, {
			field : 'id_check', //料号  类型   描述     申请人   申请时间    申请状态
			title : 'id',
			valign : 'middle',
			align : 'center',
			visible:false
		}, {
			title : '序号',
			align: "center",
			width: 40,
			formatter: function (value, row, index) {
			    // 获取每页显示的数量
				var pageSize = table.bootstrapTable('getOptions').pageSize;
				//console.log("pageSize : " + pageSize);
				// 获取当前是第几页
				var pageNumber = table.bootstrapTable('getOptions').pageNumber;
				//console.log("pageNumber : " + pageNumber);
				// 返回序号，注意index是从0开始的，所以要加上1
				return pageSize * (pageNumber - 1) + index + 1;
			}
		}, {
			field : 'part_code', 
			title : '料号',
			valign : 'middle',
			align : 'center'
		}, {
			field : 'type_check',
			title : '类型',
			valign : 'middle',
			align : 'center',
			formatter: function (value, row, index) {
				return "圖檔";
			}
		}, {
			field : 'version',
			title : '版本',
			valign : 'middle',
			align : 'center'
		}, {
			field : 'spec',
			title : '描述',
			valign : 'middle',
			align : 'center'
		}, {
			field : 'change_reason',
			title : '申请人',
			valign : 'middle',
			align : 'center',
		}, {
			field : 'reg_time',
			title : '申请时间',
			valign : 'middle',
			align : 'center',
			formatter: function (value, row, index) {
				return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
			}
		}, {
			field : 'status',
			title : '申请状态',
			valign : 'middle',
			align : 'center',
			formatter: function (value, row, index) {
				var status = "";
				switch (value){
				case "1":
					status="待审核";
					break;
				case "2":
					status="取回重办";
					break;
				case "3":
					status="退回重办";
					break;
				case "4":
					status="已取消";
					break;
				case "5":
					status="已发行";
					break;
				default:
					status="错误状态";
					break;
				}
				return status;
			}
		}, {
			title : '操作',
			valign : 'middle',
			align : 'center',
			events : operateEventsAwait,
			formatter:function(value,row,index){
				if(searchType==1){
					return "<a id='showWindowDealingCheck' href='#' title='跳转处理事务'><i class='fa fa-pencil'></i></a>";
				}
				else if(searchType==2){
					return "<a id='showWindowDealingUpdate' href='#' title='跳转处理事务'><i class='fa fa-pencil'></i></a>";	
				}
				else if(searchType==3){
					return "<a id='showWindowDealingGetback' href='#' title='跳转处理事务'><i class='fa fa-pencil'></i></a>";
				}
		    }
		}]
	});
}

function updateArchivesStatus(status){
	if(sessionRoles.match("super_admin")||sessionRoles.match("admin")){
		var length=$($("#contentAwait>tbody").find("tr")).length;
		var selected = $("#contentAwait").bootstrapTable('getSelections');
		var arr = new Array();
		if (selected!=null){
			for(var j=0;j<selected.length;j++){
				arr[j] = selected[j].id;
			}
		}
		var  id_check_selects= JSON.stringify(arr);
		if(selected.length==0){
			operateAlert(false, "","尚未选择任何申请！");
			return false;
		}
		console.log(selected)
		if(status==3?window.confirm('确定要将选择的所有申请退回吗？'):window.confirm('确定要将选择的所有申请批准吗？')){
			$.ajax({
				type : "POST",
				url : "doc/updateArchivesStatus.action",
				dataType : "json",
				data : {
					id_check_selects : id_check_selects,
					member_id:member_id,
					check_status:status
				},
				traditional : true,
				success : function(data) {
					if (data == true) {
						status==5?operateAlert(true, "批准申請成功！", ""):operateAlert(true, "退回申請成功！", "");
						$("#contentAwait").bootstrapTable("refresh");
					}
				}
			});
	    }
	}
	else{
		operateAlert(false, "","沒有操作权限！");
	}
}



window.operateEventsArchives = {
	// 顯示查詢文檔模態框
	"click #searchDoc" : function(e, value, row, index) {
		var id = row.id;
		var version = row.version;
		open_page("doc",id)
	}
}


