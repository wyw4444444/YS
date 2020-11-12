var knowledgeType
var fileUrl
$('document').ready(function(){
	console.log("進入分階")
	knowledgeType = localStorage.getItem("knowledgePartType")
	console.log(knowledgeType)
	if(knowledgeType=="update"||knowledgeType=="levelup"){
		var id = localStorage.getItem("knowledge_part_id")
		//需要後台查詢該料號和版本的數據
		$.ajax({
			type : "POST",
			url : "knowledge/findPartById.action",
			dataType : "json",
			data : {
				id : id
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				$('#number').val(data.part_code);
				$('#version').val(data.version);
				$('#name').val(data.name);
				$('#desc').val(data.desc);
				$('.fileShow').show()
				fileUrl = data.file
				$('.fileShow').attr('src',localFormatUrl(data.file))
//				如果是升版的話，版本號遞增，料號不能修改，附件需要重新上傳
				if(knowledgeType=="levelup"){
					$('#number').attr("readonly",true);
					$('#version').val(String.fromCharCode(data.version.charCodeAt(0) + 1));
					$('.fileUpload input').eq(0).attr("placeholder","請重新上傳附件")
					$('.fileShow').hide()
				}
			}
		})
		
	}
	var curentTime = CurentTime();
	$('#date').val(curentTime);
	
	$('#file').change(function(){
		console.log("選擇圖片後")
		var type;
		
		console.log($(this)[0].files[0])
		var obj=$(this)[0].files[0];
		var fr=new FileReader();
		fr.onload=function () {
			$(".fileShow").attr('src',this.result);
			$(".fileShow").show();
		};
		fr.readAsDataURL(obj);
		$('.fileUpload input[type="text"]').val($('#file')[0].files[0].name);
	})
	$('.submit').click(function(){
		console.log("提交")
		var checkrs = checkForm()
		if(checkrs){
			var part_code = $('#number').val();
			var name = $('#name').val();
			var version = $('#version').val();
			var desc = $('#desc').val();
			var date = $('#date').val();
			if($('#file').val()){
				fileUrl = "E://part-images/knowledge/"+part_code+"_"+version+"/"+part_code+"_"+version+"."+$('#file')[0].files[0].name.split('.').pop().toLowerCase();
			}
			var id = localStorage.getItem("knowledge_part_id");
//			這裡做一個轉換，如果是升版的話，就不上傳id，使後台新增一條數據
			if(knowledgeType=="levelup"){
				id='';
			}
			$.ajax({
				type : "POST",
				url : "knowledge/addPart.action",
				dataType : "json",
				data : {
					id:id,
					part_code : part_code,
					version : version,
					name : name,
					desc : desc,
					date : date,
					file : fileUrl
				},
				traditional : true,
				success : function(data) {
					console.log(data)
					if($('#file').val()){
						uploadFile("file",fileUrl)
					}
					if(knowledgeType=="levelup"){
						alert("提交成功，請選擇同步升版的成品")
						$('#showUpdateKnowledge').modal()
						showKnowledgeList(data.part_code)
						$('body').data("kpid",data)
					}else{
						alert("提交成功")
						location.reload()
						tosuccess()						
					}
				}
			
			})
		}
	})
	//料號填寫時，1、判斷料號是否正確，在接入料號系統的時候需要補上。2、判斷料號是否已經存在，存在的話就提示應該去升版
	$('.leftcontent #number').on('keyup',function(){
		var value = $(this).val();
		$.ajax({
			type : "POST",
			url : "knowledge/findOnePart.action",
			dataType : "json",
			data : {
				part_code : value,
				version:"A"
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				$('.number-error-tip').show()
				$('.number-error-tip').html("料號已存在，請進入升版界面操作")
			},
			error:function(data){
				if(!data.responseText){
					console.log("沒有數據")
					$('.number-error-tip').hide()
				}
			}
		})
	})
	$('#showUpdateKnowledge').on('click','#knowledgeUpdate',function(){
		console.log("同步");
		var id = $(this).parents("tr").find("input.id").val();
		var kid = $(this).parents("tr").find("input.kid").val();
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgeByPart.action",
			dataType : "json",
			data : {
				id:id,
				knowledge_id:kid,
				knowledge_part_id:$('body').data('kpid'),
				oldid:localStorage.getItem("knowledge_part_id")
			},
			traditional : true,
			success : function(data) {
				console.log(data)
			}
		})
	})
	$('.close').on('click', function () {
	  	$(this).parents('.alert').hide()
	})
	$('#showUpdateKnowledge').on('hidden.bs.modal', function (e) {
	  	window.close()
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
		xhr.open("post","knowledge/addpicture.action", true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
		xhr.upload.onloadstart = function(){//上传开始执行方法
			ot = new Date().getTime();   //设置上传开始时间
			oloaded = 0;//设置上传开始时，以上传的文件大小为0
		};
		xhr.send(form); //开始上传，发送form数据		
	}
}

function CurentTime(addtime)   
{   
    var now = new Date();    
    var year = now.getFullYear();       //年   
    var month = now.getMonth() + 1;     //月   
    var day = now.getDate();            //日

    var hh = now.getHours(); //時
    var mm = (now.getMinutes() + addtime) % 60;  //分
    if ((now.getMinutes() + addtime) / 60 > 1) {
        hh += Math.floor((now.getMinutes() + addtime) / 60);
    }
     
    var clock = year + "-";   
     
    if(month < 10)   
        clock += "0";   
     
    clock += month + "-";   
     
    if(day < 10)   
        clock += "0";   
         
    clock += day + " ";   
     
    return(clock);
} 


function checkForm(){
	var partcode = $('#number').val();
	if(!partcode){
		toalert("請填寫料號");
		return false;
	}
	var name = $('#name').val();
	if(!name){
		toalert("請填寫名称");
		return false;
	}
	var file = $('#file').val();
	if(!file&&knowledgeType!="update"){
		toalert("請上传附件");
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
//執行檢查料號是否存在
function checkPartCode(value){
	$.ajax({
		url : "knowledge/findOnePart.action",
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

function localFormatUrl(url){
	return "/file"+url.substring(15);
}
function showKnowledgeList(part_code){
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: "knowledge/findKnowledgeByPart.action",
		dataType: "json",
		height : 500,
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
            	status:2,
            	part_code:part_code,
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
//					column : column,
//					keyword : keyword
            }
        },
		paginationLoop: false,
		paginationHAlign : "left",
		//buttonsPrefix: 'btn btn-sm',
		toolbar: "#toolbar2",
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
			field : 'code',
			title : '成品料號',
			valign : 'middle',
			align : 'center',
		}, {
			field : 'version',
			title : '成品版本',
			valign : 'middle',
			align : 'center',
		},{
			field : 'part_code',
			title : '分階料號',
			valign : 'middle',
			align : 'center'

		}, {
			field : 'part_version',
			title : '分階版本',
			valign : 'middle',
			align : 'center',
		}, {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			events : operateEvents,
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<button id='knowledgeDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}, {
			field : 'operation',
			title : '操作',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<input class='id' type='hidden' value='"+row.id+"'><input class='kid' type='hidden' value='"+row.knowledge_id+"'><button id='knowledgeUpdate' class='btn btn-primary' href='#' title='同步'>同步</button>"
	            ].join("");
		    }
		}]
	});
}










