function loadKnowledge(status){
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: "knowledge/findKnowledgeByCode.action",
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
            	status:status,
            	part_code:$('#docSearchKeyWord').val(),
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
			field : 'part_name',
			title : '名稱',
			valign : 'middle',
			align : 'center',
		}, {
			field : 'version',
			title : '版本',
			valign : 'middle',
			align : 'center',
		},{
			field : 'descr',
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
	            	"<button id='knowledgeDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
}
function loadKnowledgeByPart(status){
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: "knowledge/findKnowledgeByPart.action",
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
            	status:status,
            	part_code:$('#docSearchKeyWord').val(),
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
		}]
	});
}
