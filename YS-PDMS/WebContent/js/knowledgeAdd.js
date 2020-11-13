$('document').ready(function(){
	var curentTime = CurentTime();
	$('#date').val(curentTime);
	$('body').on("click","#knowledgePartChoose",function(){
		var chooseVal = $(this).parents('tr').find('td').eq(0).text()
		var list = $('.partList tbody tr');
		var rs = true;
		for(var i=0;i<list.length;i++){
			if(chooseVal==list.eq(i).find('td').eq(0).text()){
				rs=false
			}
		}
		if(rs){
			partChoose($(this).parents('tr'))			
		}else{
			alert("已存在該料號")
		}
	})
	$('.submit').click(function(){
		console.log("提交");
//		獲取信息
		if(checkForm()){
			var part_type = $('#part_type').val()
			var part_code = $('#number').val();
			var part_name = $('#name').val();
			var version = $('#version').val();
			var desc = $('#desc').val();
			var date = $('#date').val();
			
			var list = $('.partList tbody tr');
			var arr = [];
			for(var i=0;i<list.length;i++){
				var obj = {
						knowledge_part_id:list.eq(i).find('td').eq(4).text(),
						part_code:list.eq(i).find('td').eq(0).text(),
						version:list.eq(i).find('td').eq(1).text()
				}
				arr.push(obj);
			}
			console.log(arr)
			$.ajax({
				type : "POST",
				url : "knowledge/add.action",
				dataType : "json",
				data : {
					part_type : part_type,
					part_code : part_code,
					part_name : part_name,
					version : version,
					desc : desc,
					date : date,
					partArr:JSON.stringify(arr)
				},
				traditional : true,
				success : function(data) {
					console.log(data)
					if(data){
						alert("提交成功")
						location.reload()
					}
				}
			})
		}
	})
	$('.partList').on("click","#choose_knowledgePartDetail",function(){
		var row = $(this).parents('tr');
		var part_code = row.find('td').eq(0).text();
		var version = row.find('td').eq(1).text();
		open_page("knowledgePartDetail",part_code,version)
	})
	$('.partList').on("click","#choose_knowledgePartRemove",function(){
		var row = $(this).parents('tr');
		row.remove()
	})
	$('.close').on('click', function () {
	  	$(this).parents('.alert').hide()
	})
})
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
	var list = $('.partList tbody tr').length;
	if(!list){
		toalert("請選擇分階");
		return false;
	}

	return true;
}
function toalert(value){
	$('.alert-danger').show();
	$('.alert-danger span.text').text(value)
}

function partChoose(row){
	var part_code = row.find('td').eq(0).text();
	var version = row.find('td').eq(2).text();
	var id = row.find('input').val();
	console.log(part_code,version,id)
//	渲染
	var html = '<tr><td>'+part_code+'</td><td style="display:none">'+version
	+'</td><td><button id="choose_knowledgePartDetail" class="btn btn-primary" title="查看">查看</button></td>'
	+'<td><button id="choose_knowledgePartRemove" class="btn btn-primary" title="刪除">刪除</button></td><td style="display:none">'+id
	+'</td></tr>'
	$('.partList tbody').append(html); 
}


function loadProcessedPart(status){
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: "knowledge/findPartByCode.action",
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
            	status:status,
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
//					column : column,
//					keyword : keyword
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
			field : 'name',
			title : '名稱',
			valign : 'middle',
			align : 'center',
		}, {
			field : 'version',
			title : '版本',
			valign : 'middle',
			align : 'center',
		},{
			field : 'desc',
			title : '描述',
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
	            	"<button id='knowledgePartDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}, {
			field : 'operation',
			title : '選擇',
			valign : 'middle',
			align : 'center',
			events : operateEvents,
			formatter:function(value,row,index){
	            return [
	            	"<button id='knowledgePartChoose' class='btn btn-primary' href='#' title='選擇'>選擇</button>"
	            ].join("");
		    }
		}]
	});
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









