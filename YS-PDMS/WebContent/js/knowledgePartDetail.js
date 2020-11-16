var part_code = localStorage.getItem("part_code")
var version = localStorage.getItem("archives_version")
var id = localStorage.getItem("knowledge_part_id")
$('document').ready(function(){
	console.log(part_code,version,id)
	
	//需要後台查詢該料號和版本的數據
	$.ajax({
		type : "POST",
		url : "knowledge/findPartById.action",
		dataType : "json",
		data : {
			id:id
		},
		traditional : true,
		success : function(data) {
			console.log(data)
			$('#number').val(data.part_code);
			$('#version').val(data.version);
			$('#name').val(data.name);
			$('#desc').val(data.desc);
			$('.fileShow').attr('src',localFormatUrl(data.file))
			if(data.status=="1"){
				$('.submit').show();
				$('.cancel').show();
			}
			if(data.status=="0"){
				$('.update').show();
			}
			if(data.status=="2"){
				$('.levelup').show(); 
				$('.print').show(); 
			}
		}
	})
	
	
	$('.submit').click(function(){
		console.log("審核")
		$.ajax({
			type : "POST",
			url : "knowledge/updatePartPass.action",
			dataType : "json",
			data : {
				part_code : part_code,
				version : version,
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("審核成功，請選擇同步升版的成品")
					$('#showUpdateKnowledge').modal()
					showKnowledgeList(part_code)
				}
			}
		})
	})
	$('.cancel').click(function(){
		console.log("駁回")
		$.ajax({
			type : "POST",
			url : "knowledge/updatePartReject.action",
			dataType : "json",
			data : {
				part_code : part_code,
				version : version,
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("駁回成功")
					window.close();
				}
			}
		})
	})
	$('.update').click(function(){
		console.log("修改")
		localStorage.setItem("knowledgePartType","update")
		localStorage.setItem("id",id)
		change_page("knowledgePartAdd")
	})
	$('body').on('click','.levelup',function(){
		console.log("升版")
//		要判斷是否可以升版
//		找到當前料號的最新版本
		$.ajax({
			type : "POST",
			url : "knowledge/findNewPartByCode.action",
			dataType : "json",
			data : {
				part_code : part_code
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(version==data){
					localStorage.setItem("knowledgePartType","levelup")
					localStorage.setItem("id",id)
					change_page("knowledgePartAdd")
				}
				else{
					alert("當前版本不是最新版本")
				}
			},
			error:function(data){
				console.log(data.responseText)
				if(version==data.responseText){
					localStorage.setItem("knowledgePartType","levelup")
					localStorage.setItem("id",id)
					change_page("knowledgePartAdd")
				}
				else{
					alert("當前版本不是最新版本")
				}
			}
		})
	})
	$('body').on('click','.print',function(){
		console.log("打印")
		doPrint()
	})
	$('#showUpdateKnowledge').on('click','#knowledgeUpdate',function(){
		console.log("同步");
		var that = $(this)
		var id = $(this).parents("tr").find("input.id").val();
		var kid = $(this).parents("tr").find("input.kid").val();
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgeByPart.action",
			dataType : "json",
			data : {
				id:id,
				knowledge_id:kid,
				knowledge_part_id:localStorage.getItem("knowledge_part_id")
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					that.removeClass("btn-primary")
					that.text("已同步")
					that.attr("disabled","disabled")
				}
			}
		})
	})
	$('#showUpdateKnowledge').on('hidden.bs.modal', function (e) {
	  	window.close()
	})
})
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

function doPrint() {
    bdhtml=window.document.body.innerHTML;
    sprnstr="<!--startprint-->"; //开始打印标识字符串有17个字符
    eprnstr="<!--endprint-->"; //结束打印标识字符串
    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); //从开始打印标识之后的内容
    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
    window.document.body.innerHTML=prnhtml; //把需要打印的指定内容赋给body.innerHTML
    window.print(); //调用浏览器的打印功能打印指定区域
    window.document.body.innerHTML=bdhtml; // 最后还原页面
    location.reload()
}






