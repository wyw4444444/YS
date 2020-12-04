$(document).ready(function() {
	var strUrl=window.location.href;
	var arrUrl=strUrl.split("?");
	
	var name=arrUrl[arrUrl.length-2];
	var PDR_id=arrUrl[arrUrl.length-1];
	
	if (name=="taskCheck"){
		$(".container-fluid .page").empty();
		$(".container-fluid .page").load("pages/sub-pages/" + name + ".jsp",function(){
			
			$("#modalPDR_id2").val(PDR_id);
			$("#modalPDR_id2").attr("disabled", true);
			
			loadPageSetting(name)
			
		});
	}
	
	if (name=="PDRCost"){
		$(".container-fluid .page").empty();
		$(".container-fluid .page").load("pages/sub-pages/" + name + ".jsp",function(){
			
			$("#modalPDR_id2").val(PDR_id);
			$("#modalPDR_id2").attr("disabled", true);
			
			loadPageSetting(name)
			
		});
	}
	
	
	if (name=="PDRDisplay"){
		$(".container-fluid .page").empty();
		$(".container-fluid .page").load("pages/sub-pages/" + name + ".jsp",function(){
			
			$("#modalPDR_id2").val(PDR_id);
			$("#modalPDR_id2").attr("disabled", true);
			
			loadPageSetting(name)
			
		});
		
		
		//全屏高度
		 window.onresize = function(){
             $("#orderData").css({
                 //'width': window.innerWidth,
                  'height': window.innerHeight-200,
             });
        }

	}
	
	
	//导航栏
	$("#btn_PDR").on("click", function() {
		change_page('PDR');
	});
	
	$("#btn_PDRDetail_Check").on("click", function() {
		change_page('PDRDetail_Check');
	});
	
	$("#btn_PDRLog").on("click", function() {
		change_page('PDRLog');
	});
	
	$("#btn_PDRCost_Check").on("click", function() {
		change_page('PDRCost_Check');
	});
	
	$("#btn_recordmember").on("click", function() {
		change_page('recordmember');
	});
});

/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/
/*------------------------------------------客戶-------------------------------------*/

//操作線體前先進行的設置處理函數
function customerSetting(){
	// 禁止模態框中更新類型的form提交刷新頁面
	$('#modalFormCustomer').submit(function() {
		return false;
	});

	//設置更新部門模態框的垂直位置
	$('#showUpdateCustomer').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateLine').find('.modal-dialog').height();
				// console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) - 300);
			}
		});
	});
	
	document.title = '客戶管理';
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
	
	
	document.getElementById("modal_short_name").addEventListener("blur",function(){
		var short_name=$("#modal_short_name").val();
		checkShort_name(short_name,"modalCustomerMsg");
	});
}


//驗證short_name是否存在
function checkShort_name(short_name,modalCustomerMsg){
	if(short_name=="" || short_name==null){
		$("#"+modalCustomerMsg).text("料號不允許為空！");
	}else{
		$("#"+modalCustomerMsg).text("");
		$.ajax({
			type : "POST",
			url : "customer/findByShort_name.action",
			dataType : "json",
			data : {
				short_name : short_name
			},
			success : function(data) {
				if (data.result != true) {
					$("#"+modalCustomerMsg).text("");
					return true;
				} else {
					$("#"+modalCustomerMsg).text("此客戶名稱已經存在！");
					return false;
				}
			}
		});
	}
}

function loadCustomer(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	var column;
	var keyword;
	
	switch(searchType){
		case "1":
			url="customer/listSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "2":
			url="customer/listSplit.action";
			column=$("#itemSearchTypeList").val();
			keyword=$("#itemSearchKeyWord").val();
			break;
	}
	/*console.log(url);*/
	var table = $("#contentCustomer");
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
		dataField: "list1", //后端 json 对应的表格数据 key
		pageNumber: 1,
		pagination: true,
		pageSize: 15, //单页记录数
		pageList: [5,15,30,50,100],//分页步进值
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
		exportTypes : ['excel'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allLine', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		
        idField: 'no',//主键
		columns : [{
					title : '序號',
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
					field : 'no',
					title : 'no',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'short_name',
					title : '客戶簡稱',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'full_name',
					title : '客戶全稱',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'reg_time',
					title : '維護時間',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					field : 'member_id',
					title : '維護人員',
					valign : 'middle',
					halign : 'center',
					align : 'center'
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					events : operateEvents_customer,
					formatter:function(value,row,index){
				        if(value==1){
				            return [
				            	"<a id='lockOrUnlockCustomer' href='#' title='解鎖'><i class='fa fa-lock' style='color:red'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editCustomer' href='#' title='編輯客戶信息'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }else if(value==0){
				            return [
				            	"<a id='lockOrUnlockCustomer' href='#' title='鎖定'><i class='fa fa-unlock' style='color:green'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editCustomer' href='#' title='編輯客戶信息'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }
				    }
				}],
				
				onLoadSuccess : function(data) {
		            //console.log(data);  
		        } //onLoadSuccess 反括號
		
	});
	
	
}


window.operateEvents_customer = {
		// 顯示修改線體模態框
		"click #editCustomer" : function(e, value, row, index) {
			$("#showUpdateCustomer .modal-dialog .modal-content .modal-header .modal-title").text("修改客戶資料");
			$("#modalCustomerLockedDiv").fadeIn(10);
			$("#btnAddCustomer").fadeOut(10);
			
			var no = row.no;
			var short_name = row.short_name;
			var full_name = row.full_name;
			var locked = row.locked;
			$("#modal_customer_no").fadeIn(10);
			$("#modal_customer_no").prop("disabled", true);
			$("#modal_customer_no").val(no);
			$("#modal_short_name").val(short_name);
			$("#modal_full_name").val(full_name);
			if(locked=="0"){
				$("#modalCustomerLocked").prop("checked", false);
			}else if(locked=="1"){
				$("#modalCustomerLocked").prop("checked", true);
			}
			
			$("#btnUpdateCustomer").fadeIn(10);
			
			$("#modalCustomerMsg").text("");
			//顯示模態框
			$("#showUpdateCustomer").modal("show");
		},
		    
		    
		//鎖定或解鎖線體
		"click #lockOrUnlockCustomer" : function(e, value, row, index) {
			var no=row.no;
			var short_name = row.short_name;
			var full_name = row.full_name;
			var locked=row.locked;
			var member_id = row.member_id;
			if(locked==0){
				locked="1";
			}else if(locked==1){
				locked="0";
			}
		
			$.ajax({
				type : "POST",
				url : "customer/update.action",
				dataType : "json",
				data : {
					no: no,
					short_name : short_name,
					full_name : full_name,
					member_id : member_id,
					locked : locked
				},
				success : function(data) {
					if (data == true) {
						if(locked=="1"){
							operateAlert(true, "客戶鎖定成功！", "");
						}else{
							operateAlert(true, "客戶解鎖成功！", "");
						}
						$("#contentCustomer").bootstrapTable("refresh");
					} else {
						operateAlert(false, "", "操作失敗！");
					}
				}
			})
		},
		
		
	}


function showModalCustomer() {
	$("#modalCustomerLockedDiv").fadeOut(10);
	$("#btnUpdateCustomer").fadeOut(10);
	$("#btnAddCustomer").fadeIn(10);
	$("#modalcustomer_no").fadeOut(10);
	
	$("#modal_line_code").prop("disabled", false);
	$("#showUpdateCustomer .modal-dialog .modal-content .modal-header .modal-title").text("新增線體")
	//清空異常提示文字
	$("#modalItemMsg").text("");
	
	//清空
	$("#modal_line_code").val("");
	$("#modal_workshop_name").val("");
	
	//顯示模態框
	$("#showUpdateCustomer").modal("show");
	
}

//新增線體
function addCustomer() {
	var short_name=$("#modal_short_name").val();
	var full_name=$("#modal_full_name").val();
	
	var flag=$("#modalCustomerMsg").text();
	if (flag=="此客戶名稱已經存在！"){
		return false;
	}
	
	$("#modalCustomerMsg").text("");
	if (short_name == "" || full_name == "") {
		$("#modalCustomerMsg").text("客戶資料未填寫完整！");
		return false;
	} else {
		if (short_name.length > 7) {
			$("#modalCustomerMsg").text("客戶簡稱不能大於7個字符！");
			return false;
		}
		$("#modalCustomerMsg").text("");
		//console.log(member_id);
		$.ajax({
			type : "POST",
			url : "customer/add.action",
			dataType : "json",
			data : {
				short_name : short_name,
				full_name : full_name,
				member_id: member_id
			},
			success : function(data) {
				if (data == true) {
					$("#modal_short_name").val("");
					$("#modal_full_name").val("");
					operateAlertSmall(true, "新增客戶成功！", "");
				} else {
					operateAlertSmall(false, "", "新增客戶失敗！");
				}
			}
		});
	}
}


function updateCustomer() {
	var no = $("#modal_customer_no").val();
	var short_name = $("#modal_short_name").val();
	var full_name=$("#modal_full_name").val();
	var locked=0
	
	if($("#modalCustomerLocked").is(':checked')){
		var locked=1;
	}
	/*console.log("line_id:"+line_id);*/
	
	if (short_name == "" || full_name == "") {
		$("#modalItemMsg").text("客戶資料未填寫完整！");
		return false;
	} else {
		if (short_name.length > 7) {
			$("#modalItemMsg").text("客戶簡稱不能大於7個字符！");
			return false;
		}
		
		$("#modalCustomerMsg").text("");
		$.ajax({
			type : "POST",
			url : "customer/update.action",
			dataType : "json",
			dataType : "json",
			data : {
				no:no,
				short_name : short_name,
				full_name : full_name,
				member_id : member_id,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					operateAlert(true, "操作成功！", "");
					$("#showUpdateCustomer").modal("hide");
					$("#contentCustomer").bootstrapTable("refresh");
				} else {
					operateAlert(false, "", "操作失敗！");
				}
			}
		})
	}
}


//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------
//---------------------------------------pdr--------------------------



function PDRSetting(){
	// 禁止新增權限的form提交刷新頁面
	$("#modalFormRole").submit(function() {
		return false;
	});
	
	$("#modalFormRole").submit(function() {
		return false;
	});
	
	$("#modalFormLog").submit(function() {
		return false;
	});
	
	document.title = 'PDR主介面';
	$('#PDRSearchTypeList').val() == "PM"
		
		
//		TypeList("type", "statusList", "id", "sub_type", 1);	
	$.ajax({
		url : "type/listByParentType.action",
		type : "post",
		scriptCharset : "utf-8",
		data : {
			parent_type : "PDR状态"
		},
		success : function(data) {
			var select = $('#statusList');
			var list = data;
			select.empty();
			for (var i in list) {
				var option = $("<option value=" + list[i].id
						+ ">" + list[i].sub_type + "</option>");
				option.appendTo(select);
			}
				
			select.find("option").each(function(){
				if($(this).text() == "執行中"||$(this).text() == "暫停")	{
					 $(this).attr("selected",true);
				}
			});
				
			select.selectpicker({
			     'selectedText': 'cat',
			     'deselectAllText':'不选',
			     'selectAllText': '全选',
			     'dropupAuto':true,
			     'width' : 'calc(100% - 95px)'
			});
				
			$('.selectpicker').selectpicker('refresh');
				
			$("#PDRSearchKeyWord").fadeOut(10);
			$("#PMList").fadeIn(10);
			
			$.ajax({
				url : "member/PMList.action",
				type : "post",
				scriptCharset : "utf-8",
				success : function(data) {
					var select = $('#PMList');
					var list = data;
					console.log(list)
					select.empty();
					if(sessionRoles.search("super_admin") != -1 || sessionRoles.search("DY999") != -1){
						for (var i in list) {
							var option = $("<option value=" + list[i].member_id
									+ ">" + list[i].member_name + "</option>");
							option.appendTo(select);
						}
						
						var option = $("<option value=" + "0"
							+ ">" + "所有PM" + "</option>");
							option.appendTo(select);
					}else{
						for (var i in list) {
							if( list[i].member_id == member_id){
								var option = $("<option value=" + list[i].member_id
										+ ">" + list[i].member_name + "</option>");
								option.appendTo(select);
							}
						}
					}
					
					if( sessionRoles.search("DY999") != -1){
						$("#PMList").val(0);
					}else{
						$("#PMList").val(member_id);
					}
					
					if(sessionRoles.search("super_admin") != -1 || sessionRoles.search("DY999") != -1){
						loadPDR('1');
					}else{
						loadPDR('2');
					}
				
				}
			})
			
		}
	})	
		
		
	// 設置搜索選擇框控件的綁定事件
	$('#PDRSearchTypeList').on('change', function(e) {
		if ($('#PDRSearchTypeList').val() == "PM") {
			$("#PDRSearchKeyWord").fadeOut(10);
			$("#PMList").fadeIn(10);
			
			PMList("member", "PMList", "member_id","member_name", 0);
		} else {
			$("#PDRSearchKeyWord").fadeIn(10);
			$("#PMList").fadeOut(10);
		}
	});
	
	
	
	
	//設置更新角色模態框的垂直位置
	$('#showUpdatePDR').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdatePDR').find('.modal-dialog').height();
				//console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) -300);
			}
		});
	});
	
	//TypeList("type", "statusList", "type_id", "sub_type", 1);
	
	$("#toolbar").css({
		'padding-top' : 5,
		'padding-left' : 15
	})
		
	
	vm = new Vue({
		el: '#msgDiv',		// 设置要进行渲染的根元素
		data() {
			return {
				images : [],
			};
		},
	}) ;	// 实例化MVVM的管理对象
	
	// 設置搜索選擇框控件的綁定事件
	$('#showUpdatePDR').on('keyup','#modalPER_id', function(e) {
		var value = $(this).val()
		$.ajax({
			url : "PDR/checkPER_id.action",
			type : "post",
			data : {
				PER_id : value
			},
			success : function(data) {
				console.log(data)
				if(data.result!="0"){
					var obj = data.result[0]
					$("#customerList").val(obj.customer_no);
					$("#modalDescription").val(obj.description);
					$("#PMList2").val(obj.PM);
					$("#modalstart_date").val(new Date(obj.start_date.time).format("yyyy-MM-dd"));
					$("#modalend_date").val(new Date(obj.end_date.time).format("yyyy-MM-dd"));
					$("#modaltype_id_status").val(obj.type_id_status); 
				}
			}
		})
	});
	
	
}

var vm;

function PMList(type, selectName, valueName, textName, selectedVal) {
	$.ajax({
		url : type + "/PMList.action",
		type : "post",
		scriptCharset : "utf-8",
		success : function(data) {
			var select = $('#' + selectName);
			var list = data;
			select.empty();
			var option = $("<option value=0 selceted>-- 請選擇PM --</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i][valueName]
						+ ">" + list[i][textName] + "</option>");
				option.appendTo(select);
			}
			if(selectedVal==0){
				var option = $("<option value=" + "0"
				+ ">" + "所有PM" + "</option>");
				option.appendTo(select);
			}
			
			select.val(selectedVal);
		}
	})
}

function TypeList(type, selectName, valueName, textName, selectedVal) {
	$.ajax({
		url : type + "/listByParentType.action",
		type : "post",
		scriptCharset : "utf-8",
		data : {
			parent_type : "PDR状态"
		},
		success : function(data) {
			var select = $('#' + selectName);
			var list = data;
			select.empty();
			/*var option = $("<option value=0 selceted>- 請選擇执行状态 -</option>");
			option.appendTo(select);*/
			for (var i in list) {
				var option = $("<option value=" + list[i][valueName]
						+ ">" + list[i][textName] + "</option>");
				option.appendTo(select);
			}
			
			select.find("option").each(function(){
				if($(this).text() == "執行中"||$(this).text() == "暫停")	{
				 		$(this).attr("selected",true);
				 }
			});
			
			select.selectpicker({
		        'selectedText': 'cat',
		        'deselectAllText':'不选',
		         'selectAllText': '全选',
		         'dropupAuto':true,
		         'width' : 'calc(100% - 95px)'
		    });
			
			$('.selectpicker').selectpicker('refresh');
			
			/*var option = $("<option value=" + "0"
						+ ">" + "所有状态"+ "</option>");
						option.appendTo(select);*/
						
			//select.val(selectedVal);
		}
	})
}
	
function loadPDR(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	var column;
	var keyword;
	var status=0;
	switch(searchType){
		case "1":
			url="PDR/listSplit.action";
			column="undefined";
			keyword="undefined";
			
			var data=[];
			data.push(24);
			var dataString = JSON.stringify(data);
			break;
		case "2":
			url="PDR/listSplit.action";
			var status = $('#statusList').selectpicker('val');
			var data=[];
			for (var i =0 ; i<status.length ; i++ ) {
				data.push(status[i]);
			}
			var dataString = JSON.stringify(data);
			
			if ($('#PDRSearchTypeList').val() == "PDR_id") {
				column = $("#PDRSearchTypeList").val();
				keyword = $("#PDRSearchKeyWord").val();
				status=0;
			}
			if ($('#PDRSearchTypeList').val() == "PM") {
				column = $("#PDRSearchTypeList").val();
				keyword = $("#PMList").val();
			}
			if ($('#PDRSearchTypeList').val() == "undefined") {
				column = "undefined";
				keyword = "undefined";
			}
			break;
	}
	//console.log(url);
	var table = $("#contentPDR");
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
		pageSize: 20, //单页记录数
		pageList: [20,50,100,200,500],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
            return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				status : dataString,
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
		exportTypes : ['excel'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allRole', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		
        idField: 'no',//主键
		columns : [{
					field : 'checked',
					checkbox:true,
					valign : 'middle',
					align : 'center'
				}, {
					title : '序號',
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
					field : 'no',
					title : 'no',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'PDR_id',
					title : 'PDR編號',
					valign : 'middle',
					align : 'center',
					visible : true
				}, {
					title : '客戶名稱',
					valign : 'middle',
					halign : 'center',
					align : 'center',
					formatter: function (value, row, index) {
						return row.customer.short_name
					}
				}, {
					title : '業務員',
					valign : 'middle',
					halign : 'center',
					align : 'center',
					formatter: function (value, row, index) {
						return row.manDetail.member_name
					}
				}, {
					field : 'description',
					title : '描述',
					valign : 'middle',
					width: 220,
					align : 'center'
				}, {
					title : 'PM名稱',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return row.member.member_name
					}
				}, {
					title : '開始日期',
					field : 'start_date',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return new Date(value.time).format('MM-dd');
						//return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					title : '結案日期',
					field : 'end_date',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						if(value !=""){
							return new Date(value.time).format('MM-dd');
						}
					}
				}, {
					title : '執行狀態',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return  row.type.sub_type
					}
				}, {
					title : '任務狀況',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						//console.log(row.pdrdetail);
						var z=0;
						for(var i=0;i<row.pdrdetail.length;i++){
							if(row.pdrdetail[i].actual_end_date =="" ){
								var end_date=new Date(row.pdrdetail[i].end_date.time).format('yyyy-MM-dd');
								var today=new Date().format('yyyy-MM-dd');
								if(end_date != "NaN-aN-aN" && end_date < today){
									z=1;
								}
							}
						}
						
						if(row.pdrdetail.length==0){
							z=2;
						}
						switch (z) {
					    case 0:
					    	return "正常"
					        break;
					    case 1:
					    	return "任務延誤"
					        break;
					    case 2:
					    	return "沒有任務"
					        break;
						} 
						
					}
				}, {
					title : '记录日期',
					valign : 'middle',
					align : 'center',
					width: 100,
					formatter: function (value, row, index) {
						if(row.pdrlog.length != 0){
							//return new Date(row.pdrlog[0].reg_time.time).format('yyyy-MM-dd hh:mm:ss');
							return new Date(row.pdrlog[0].reg_time.time).format('yyyy-MM-dd');
						}
					}
				}, {
					title : '记录人',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						if(row.pdrlog.length != 0){
							return  row.pdrlog[0].member.member_name
						}
					}
				}, {
					title : '最新记录',
					valign : 'middle',
					align : 'center',
					width: 260,
					formatter: function (value, row, index) {
						if(row.pdrlog.length != 0){
							return row.pdrlog[0].log
						}
					}
				}, {
					title : '任务',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return "<select id='" + row.PDR_id + "' class='partdata-process form-control'style='width:100%;font-size:8px'>" +
						"<option value='选择' selected>选择" +
						"</option><option value='sub1'>sub1</option><option value='所有'>所有</option>"; 
					}
				}, {
					title : '预计费用',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						if(row.pdrcost.length != 0){
							var total_estimate=0;
					          for(var i=0 ; i<row.pdrcost.length ; i++){
					        	  total_estimate = parseInt(total_estimate) + parseInt(row.pdrcost[i].estimate_cost);
					          }
					        return total_estimate.toLocaleString();
						}
					}
				}, {
					title : '实际费用',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						if(row.pdrcost.length != 0){
					       var total_actual=0;
					       for(var i=0 ; i<row.pdrcost.length ; i++){
					    	   total_actual = parseInt(total_actual) + parseInt(row.pdrcost[i].actual_cost);
					       }
					       return total_actual.toLocaleString();
						}
					}
				}, {
					title : '操作',
					valign : 'middle',
					align : 'center',
					width: 180,
					events : operateEvents_PDR,
					formatter:function(value,row,index){
						if( sessionRoles.search("DY999") != -1){
							return [
						           "<a id='display' href='#' title='展示'><i class='fa fa-television'></i></a>"
						        ].join("");
						}else{
							 return [
						           "<a id='editPDR' href='#' title='编辑PDR'><i class='fa fa-pencil'></i></a>"+
						           "&nbsp;&nbsp;",
						           "<a id='addLog' href='#' title='新增记录'><i class='fa fa-plus'></i></a>"+
						           "&nbsp;&nbsp;",
						           "<a id='forward_to_task' href='#' title='跳转至任务'><i class='fa fa-calendar'></i></a>"+
						           "&nbsp;&nbsp;",
						           "<a id='forward_to_cost' href='#' title='费用'><i class='fa fa-usd' style='color:red'></i></a>"+
						           "&nbsp;&nbsp;",
						           "<a id='display' href='#' title='展示'><i class='fa fa-television'></i></a>"
						        ].join("");
						}
				    }
				}],
			onLoadSuccess : function(data) {
		           //console.log(data);
		           confirmPDR();
		    },
		    
		    detailView: true,// 父子表
	        onExpandRow: function (index, row, table) {
	        	var table2=table.html("<table id='sub" + row.PDR_id +"' class='col-md-11 table-sm'></table>").find("table")
				detail=row.pdrdetail;
				//console.log(detail)
				var dataArray2 = new Array();
				
				//key
				var item1=0;
				var item2=0;
				var item3=0
				
				
				var total_key=0;
				var total_keyandsub1=0;
				if (detail.length!=0){
					for(var i in detail){
						var map = {};
						map['task'] = detail[i].task;
						
						map['type'] = detail[i].type;
						
						map['person'] = detail[i].person;
						
						map['dept'] = detail[i].dept;
						
						map['id'] = parseInt(i)+1;
						
						if(detail[i].type=="key" ){
							total_key = parseInt(i)+1;
						}
						
						if(i!=0){
							if(detail[i-1].type=="sub1"){
								total_keyandsub1 =  parseInt(i);
								//console.log(total_keyandsub1);
							}
						}
						
						
						//序号
						switch (detail[i].type) {
						    case "key":
						    	item1++;
								item2=0;
								map['item'] = item1;
								map['pid']=0;
						        break;
						    case "sub1":
						    	item2++;
								item3=0;
								map['item'] = "&nbsp;" + item1 + "-" + item2;
								map['pid']= total_key;
						        break;
						    case "sub2":
						    	item3++;
								map['item'] = "&nbsp;&nbsp;" + item1 + "-" + item2 + "-" + item3;
								map['pid']= total_keyandsub1;
								break;
						} 
						
						if (detail[i].start_date.time == null){
							map['start_date'] ="";
						}else{
							map['start_date'] =new Date(detail[i].start_date.time).format('yyyy-MM-dd');
						}
						
						if (detail[i].end_date.time == null){
							map['end_date'] ="";
						}else{
							map['end_date'] =new Date(detail[i].end_date.time).format('yyyy-MM-dd');
						}
						
						if (detail[i].actual_end_date.time == null){
							map['actual_end_date'] ="";
						}else{
							map['actual_end_date'] =new Date(detail[i].actual_end_date.time).format('yyyy-MM-dd');
						}
					
						map['remark'] = detail[i].remark;
						dataArray2[i] = map;
					}
				}
				//console.log(dataArray2);
				var dataString2 = JSON.stringify(dataArray2);
				var tableData2 = eval("(" + dataString2 + ")");
				
				table2.bootstrapTable('destroy');
				table2.bootstrapTable({
					columns : [{
						field : 'checked',
						checkbox:true,
						valign : 'middle',
						align : 'center'
					}, {
						field : 'id',
						title : 'id',
						valign : 'middle',
						align : 'center',
						visible : false
					}, {
						field : 'pid',
						title : 'pid',
						valign : 'middle',
						align : 'center',
						visible : false
					}, {
						field : 'item',
						title : '序號',
						valign : 'middle',
						align : 'center',
						width : 140
					}, {
						field : 'task',
						title : '任务名称',
						align : 'center',
						width : 200
					}, {
						field : 'type',
						title : '类型',
						align : 'center'
					}, {
						field : 'start_date',
						title : '预计开始日期',
						valign : 'middle',
						align : 'center'
					}, {
						field : 'end_date',
						title : '预计结束日期',
						valign : 'middle',
						align : 'center'
					}, {
						field : 'actual_end_date',
						title : '实际结束日期',
						valign : 'middle',
						align : 'center'
					}, {
						field : 'dept',
						title : '负责部门',
						valign : 'middle',
						align : 'center',
						width : 150
					}, {
						field : 'person',
						title : '负责人',
						valign : 'middle',
						align : 'center'
					}, {
						field : 'remark',
						title : '备注',
						valign : 'middle',
						align : 'center',
						width : 400
					
					}],
					data : tableData2,
				    theadClasses: "thead-blue",//这里设置表头样式
				    idField :'id',
				    
				    treeShowField: 'task',
				    parentIdField: 'pid',  //指定父id列
				    
				    
				    //toolbar: "#toolbar1",
				    //buttonsAlign : "left", // 按钮位置
			        //showColumns: true,
			        
				    onResetView: function() {
				    	table2.treegrid({
				    		 initialState: 'collapsed',// 所有节点都折叠
				    		 //initialState: 'expanded',// 所有节点都展开，默认展开
				    		 treeColumn: 1,
				    		 onChange: function() {
				    			 table2.bootstrapTable('resetWidth');
				    		 }
				    	 });
				    	  //只展开树形的第一级节点
				    	//var row_num=detail.length-1;
				    	var PDR_id2 = $('#' + row.PDR_id).val();
				    	if(PDR_id2 == "所有"){
				    		table2.treegrid('getAllNodes').treegrid('expand')
				    	}
				    	
				    	if(PDR_id2 == "sub1"){
				    		table2.treegrid('getRootNodes').treegrid('expand')
				    	}
				    	
				    	for(var i=0;i < detail.length;i++){
				    		if(detail[i].type!='key' && detail[i].actual_end_date==""){
				    			var needtr=$($("#sub" + detail[i].PDR_id + ">tbody").find("tr").get(i));
				    			needtr.treegrid('getParentNode').treegrid('expand');
				    			var t=needtr.treegrid('getParentNode').treegrid('getParentNodeId');
				    			var t1=needtr.treegrid('getParentNode').treegrid('getParentNode');
				    			//console.log(t);
				    			//console.log(t1);
				    			//console.log("---------------------------");
				    			if(t!=null){
				    				needtr.treegrid('getParentNode').treegrid('getParentNode').treegrid('expand');
				    			}
				    		}
				    	}
				    	console.log(detail);
				    	confirmDetail(detail[0].PDR_id);
				    },
		
				});// 子表table2.bootstrapTable
				
				
				
	        }   // onExpandRow
		    
		   
	});
}

function confirmDetail(PDR_id) {
	var length=$($("#sub" + PDR_id + ">tbody").find("tr")).length;
	//console.log(length)
	for(var i=0;i<length;i++){
		//key sub1 sub2
		var itemcell=$($($("#sub" + PDR_id + ">tbody").find("tr").get(i)).find("td").get(1));
		var taskcell=$($($("#sub" + PDR_id + ">tbody").find("tr").get(i)).find("td").get(2));
		var itemrow=$($($("#sub" + PDR_id + ">tbody").find("tr").get(i)).find("td:nth-child(n+1):nth-child(-n+10)"));;
		var typecell=$($($("#sub" + PDR_id + ">tbody").find("tr").get(i)).find("td").get(3));
		var logcell=$($($("#sub" + PDR_id + ">tbody").find("tr").get(i)).find("td").get(9));
		switch (typecell.text()) {
		    case "key":
		    	itemcell.css({
		    		'text-align' : 'left',
				});
		    	taskcell.css({
		    		'text-align' : 'left',
				});
		    	logcell.css({
		    		'text-align' : 'left',
		    		'word-break' : 'break-all',
				});
		    	itemrow.css({
					'background' : '#5F9EA0',
					'color' : '#FFFFFF'
				});
		        break;
		    case "sub1":
		    	itemcell.css({
		    		'text-align' : 'left',
				});
		    	taskcell.css({
		    		'text-align' : 'left',
				});
		    	logcell.css({
		    		'text-align' : 'left',
		    		'word-break' : 'break-all',
				});
		    	itemrow.css({
					'background' : '#FFD700'
				});
		        break;
		    case "sub2":
		    	itemcell.css({
		    		'text-align' : 'left',
				});
		    	taskcell.css({
		    		'text-align' : 'left',
				});
		    	logcell.css({
		    		'text-align' : 'left',
		    		'word-break' : 'break-all',
				});
		    	itemrow.css({
					'background' : '#FDF5E6'
				});
				break;
		} 
		
		
		//延误
		var end_date_cell=$($($("#sub" + PDR_id + ">tbody").find("tr").get(i)).find("td").get(5));
		var actual_end_date_cell=$($($("#sub" + detail[i].PDR_id + ">tbody").find("tr").get(i)).find("td").get(6));
		var today=new Date().format('yyyy-MM-dd');
		var end_date=new Date(end_date_cell.text()).format('yyyy-MM-dd');
		var actual_end_date=new Date(actual_end_date_cell.text()).format('yyyy-MM-dd');
		
		if(end_date < today && actual_end_date=="NaN-aN-aN" ){
			end_date_cell.css({'color' : 'red'});
		}
		
		if(end_date < actual_end_date && actual_end_date!="NaN-aN-aN"){
			actual_end_date_cell.css({'color' : 'red'});
		}
		
	}
}

function confirmPDR() {
	//表头颜色
	var recordcell=$($($("#contentPDR>thead").find("tr").get(0)).find("th:nth-child(n+12):nth-child(-n+14)"));
	recordcell.css({
		'background' : 'orange',
	});
	
	
	var path1 = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	var length=$($("#contentPDR>tbody").find("tr")).length;
	for(var i=0;i<length;i++){
		//描述
		var descriptioncell=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(5));
		descriptioncell.css({
			'text-align' : 'left',
			'word-break' : 'break-all'
		});
		
		var part_id = $($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(3)).text();
		var path4= "/PDR-images/" + part_id;
		console.log(path4)
		$.ajax({
			url : "link/images3.action",
			type : "post",
			scriptCharset : "utf-8",
			dataType: 'json',
			data : {
				path : path4,
				no : i 
			},
			success: function(res) {
				var n=0;
				for (var k = 0; k < res.list.length; k++) {
					let index = res.list[k].lastIndexOf("\\");
					let path4 = res.list[k].substring(parseInt(index) + 1);
					n++
				}
				console.log(res)
				if(n>0){
					console.log(res.no);
					let description =$($($("#contentPDR>tbody").find("tr").get(res.no)).find("td").get(5)).text();
					$($($("#contentPDR>tbody").find("tr").get(res.no)).find("td").get(5)).text("");
					$($($("#contentPDR>tbody").find("tr").get(res.no)).find("td").get(5)).append("<i class='fa fa-picture-o' style='color:blue'></i>" + "  " + description);
				}
            },	
		});
		
		//picture
		descriptioncell.hover(function(){
			this.style.cursor="pointer";
		});
		descriptioncell.on("click", function() {
			var part_id=$($($(this).parent()).find("td").get(3)).text();
			let path3= "/PDR-images/" + part_id;
			let logdate = $(this).text();
			//console.log(path3);
			$.ajax({
				url : "link/images.action",
				type : "post",
				scriptCharset : "utf-8",
				dataType: 'json',
				data : {
					path : path3
				},
				success: function(res) {
					$("#showPicture").modal("show");
					var n=0;
					vm.images.splice(0);
					for (var i = 0; i < res.length; i++) {
						let index = res[i].lastIndexOf("\\");
						let path4 = res[i].substring(parseInt(index) + 1);
						console.log(path4.substring(0,10));
						n++
						let path5= path1 + "/PDR-images/" + part_id + "/" +path4;
						vm.images.splice(n);
						vm.$set(vm.images,n,path5);
						
					}
					console.log(vm.images);
                },	
			}).then(function (res) {
			});
		})
		
		//status
		var status_cell=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(9));
		switch (status_cell.text()) {
	    	case "作廢":
	    		status_cell.css({'color' : 'red'});
	    		break;
	    	case "暫停":
	    		status_cell.css({'color' : '#FF69B4'});
	    		break;
	    	case "已結案":
	    		status_cell.css({'color' : 'green'});
	    		break;
		} 
		
		
		//task
		var task_cell=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(10));
		switch (task_cell.text()) {
		    case "任務延誤":
		    	task_cell.css({'color' : 'red'});
		        break;
		    case "沒有任務":
		    	task_cell.css({'color' : 'orange'});
		        break;
		} 
		
		//pdrlog reg_time
		var logdate_cell=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(11));
		var today=new Date().format('yyyy-MM-dd');
		var logdate=new Date(logdate_cell.text()).format('yyyy-MM-dd');
		if(today!=logdate){
			logdate_cell.css({'color' : 'red'});
		}
		
		//pdrlog
		var logcell=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(13));
		logcell.css({
			'text-align' : 'left',
			'word-break' : 'break-all'
		});
		
		var estimatecell=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(15));
		var actualcell=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(16));
		if(estimatecell.text()< actualcell.text()){
			actualcell.css({'color' : 'red'});
		}
		estimatecell.css({
			'text-align' : 'right',
		});
		actualcell.css({
			'text-align' : 'right',
		});
		
		
		var part_id=$($($("#contentPDR>tbody").find("tr").get(i)).find("td").get(3)).text();
		var path3= "/project-images/" + part_id;
		$.ajax({
			url : "link/images2.action",
			type : "post",
			scriptCharset : "utf-8",
			dataType: 'json',
			data : {
				path : path3,
				logdate : logdate,
				no : i 
			},
			success: function(res) {
				var n=0;
				for (var k = 0; k < res.list.length; k++) {
					let index = res.list[k].lastIndexOf("\\");
					let path4 = res.list[k].substring(parseInt(index) + 1);
					if(path4.substring(0,10) == res.logdate ){
						n++
					}
				}
				
				if(n>0){
					console.log(n)
					let log =$($($("#contentPDR>tbody").find("tr").get(res.no)).find("td").get(13)).text();
					$($($("#contentPDR>tbody").find("tr").get(res.no)).find("td").get(13)).text("");
					$($($("#contentPDR>tbody").find("tr").get(res.no)).find("td").get(13)).append("<i class='fa fa-picture-o' style='color:red'></i>" + "  " + log);
				}
            },	
		});
		
		//picture
		logdate_cell.hover(function(){
			this.style.cursor="pointer";
		});
		logdate_cell.on("click", function() {
			var part_id=$($($(this).parent()).find("td").get(3)).text();
			let path3= "/project-images/" + part_id;
			let logdate = $(this).text();
			//console.log(path3);
			$.ajax({
				url : "link/images.action",
				type : "post",
				scriptCharset : "utf-8",
				dataType: 'json',
				data : {
					path : path3
				},
				success: function(res) {
					$("#showPicture").modal("show");
					var n=0;
					vm.images.splice(0);
					for (var i = 0; i < res.length; i++) {
						let index = res[i].lastIndexOf("\\");
						let path4 = res[i].substring(parseInt(index) + 1);
						console.log(path4.substring(0,10));
						if(path4.substring(0,10) == logdate ){
							n++
							let path5= path1 + "/project-images/" + part_id + "/" +path4;
							vm.images.splice(n);
							vm.$set(vm.images,n,path5);
						}
					}
					console.log(vm.images);
                },	
			}).then(function (res) {
			});
		})
		
		
		
		
	
		/*logdate_cell.on("click", function() {
			var part_id=$($($(this).parent()).find("td").get(3)).text();
			
			var path1 = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
			var logdate= new Date(this.innerText).format('yyyy-MM-dd');
			var path2= path1 + "/project-images/" + part_id + "/" + logdate +".jpg"
			//console.log(path2);
			var img = document.createElement('img');
	        img.setAttribute('src',path2);
	        img.width=700;
	        $("#PDRpicture").empty();
	        document.getElementById('PDRpicture').appendChild(img);
			$("#showPicture").modal("show");
		});*/
		
		//需求日期
		/*var demand_datecell=$($($("#contentSchedule>tbody").find("tr").get(i)).find("td").get(7));
		var demand_date= new Date(demand_datecell.text());
		var actual_end_datecell=$($($("#contentSchedule>tbody").find("tr").get(i)).find("td").get(8));
		var actual_end_date= new Date(actual_end_datecell.text());
		var today_date = new Date() ;
		if(demand_date.format("yyyy-MM-dd") < today_date.format("yyyy-MM-dd")){
			if(status=="生產中"  || status == "暫停中" || status == "待生產"){
				demand_datecell.css({'background' : 'red'});
			}
		}*/
		
		
	}
}

window.operateEvents_PDR = {
		// 编辑PDR
		"click #editPDR" : function(e, value, row, index) {
			var PDR_id = row.PDR_id;
			var PER_id = row.PER_id;
			var description = row.description;
			var start_date = new Date(row.start_date.time).format('yyyy-MM-dd');
			var end_date1 = row.end_date;
			var pm = row.PM;
			var man = row.man;
			var customer_no = row.customer_no;
			var type_id_status = row.type_id_status;
			var description = row.description;
			
			$("#modalPDRMsg").text("");
			$("#modalPDR_id").val("");
			$("#customerList").val("");
			$("#modalDescription").val("");
			$("#PMList2").val("");
			$("#modalstart_date").val("");
			$("#modalend_date").val("");
			$("#modaltype_id_status").val(""); 

			$("#modalPDR_id").val(PDR_id);
			if(PER_id){
				$(".modalPER_id-div").show()
				$("#modalPER_id").val(PER_id);				
			}else{
				$(".modalPER_id-div").hide()
			}
			$("#modalDescription").val(description);
			
			$("#modalPDRMsg").text("");
			$("#showUpdatePDR .modal-dialog .modal-content .modal-header .modal-title").text("编辑PDR")
			$("#btnUpdatePDR").fadeIn(10);
			$("#btnAddPDR").fadeOut(10);
			
			$("#modalstart_date").each(function(index, el) {
				$(this).removeAttr("lay-key")   //解决重复关闭模态框后再次点击闪退问题
				laydate.render({
				     elem: this,         //解决多次打开模态框闪退问题
				     format: 'yyyy-MM-dd'
				 });
			});
			
			$("#modalstart_date").val(start_date);
			
			
			$("#modalend_date").each(function(index, el) {
				$(this).removeAttr("lay-key")   //解决重复关闭模态框后再次点击闪退问题
				laydate.render({
				     elem: this,         //解决多次打开模态框闪退问题
				     format: 'yyyy-MM-dd'
				 });
			});
			
			console.log(end_date1)
			var end_date = new Date(row.end_date.time).format('yyyy-MM-dd');
			if(end_date1!=""){
				$("#modalend_date").val(end_date);
			}
			
			
			//客户清单
			$.ajax({
				url : "customer/listSplit.action",
				type : "post",
				scriptCharset : "utf-8",
				data : {
					currentPage : 0,
					lineSize : 100,
				},
				success : function(data) {
					var select = $('#customerList');
					var list = data.list1;
					select.empty();
					var option = $("<option value=0 selceted>--- 選擇客户 ---</option>");
					option.appendTo(select);
					for (var i in list) {
						var option = $("<option value=" + list[i].no
								+ ">" + list[i].short_name + "</option>");
						option.appendTo(select);
					}
					select.val(customer_no);
				}
			});
			
			//PM清单
			$.ajax({
				url : "member/PMList.action",
				type : "post",
				scriptCharset : "utf-8",
				success : function(data) {
					var select = $('#PMList2');
					var list = data;
					select.empty();
					var option = $("<option value=0 selceted>--- 請選擇PM ---</option>");
					option.appendTo(select);
					for (var i in list) {
						var option = $("<option value=" + list[i].member_id
								+ ">" + list[i].member_name + "</option>");
						option.appendTo(select);
					}
					select.val(pm);

					var select2 = $('#MemberList');
					var list2 = data;
					select2.empty();
					var option = $("<option value=0 selceted>--- 請選擇業務員 ---</option>");
					option.appendTo(select2);
					for (var i in list2) {
						var option = $("<option value=" + list2[i].member_id
								+ ">" + list2[i].member_name + "</option>");
						option.appendTo(select2);
					}
					select2.val(man);
				}
			
			})
			
			//执行状态
			$.ajax({
				url : "type/listByParentType.action",
				type : "post",
				scriptCharset : "utf-8",
				data : {
					parent_type : "PDR状态"
				},
				success : function(data) {
					var select = $('#modaltype_id_status');
					var list = data;
					select.empty();
					var option = $("<option value=0 selceted>--- 請選擇执行状态 ---</option>");
					option.appendTo(select);
					for (var i in list) {
						var option = $("<option value=" + list[i].id
								+ ">" + list[i].sub_type + "</option>");
						option.appendTo(select);
					}
					select.val(type_id_status)
				}
			})
			
			$("#modalPDR_id").attr("disabled", true);
			//顯示模態框
			$("#showUpdatePDR").modal("show");
		},
		
		
		// 新增Log
		"click #addLog" : function(e, value, row, index) {
			var PDR_id=row.PDR_id;
			var reg_time = new Date().format('yyyy-MM-dd hh:mm:ss');
			$("#modalLogMsg").text("");
			
			$("#modalPDR_id2").val("");
			$("#modalLog").val("");
			$("#modalreg_time").val("");
			$("#showUpdateLog .modal-dialog .modal-content .modal-header .modal-title").text("新增记录")
			$("#modalPDR_id2").val(PDR_id);
			$("#modalreg_time").val(reg_time);
			$("#modalreg_time").each(function(index, el) {
				$(this).removeAttr("lay-key")   //解决重复关闭模态框后再次点击闪退问题
				laydate.render({
					    elem: this,         // 解决多次打开模态框闪退问题
					    type: 'datetime',
					 });
			});
			
			$("#modalPDR_id2").attr("disabled", true);
			$("#modalreg_time").attr("disabled", true);
			//顯示模態框
			$("#showUpdateLog").modal("show");
		},
		
		// 新增任务
		"click #forward_to_task" : function(e, value, row, index) {
			change_page3('taskCheck',row.PDR_id);
		},
		
		"click #forward_to_cost" : function(e, value, row, index) {
			change_page3('PDRCost',row.PDR_id);
		},
		
		"click #display" : function(e, value, row, index) {
			change_page3('PDRDisplay',row.PDR_id);
		},
}

function change_page3(name,PDR_id) {
	window.open("/YS-PDMS/index.action?" + name +"?" + PDR_id );
}


//修改PDR
function updatePDR() {
	var PDR_id = $("#modalPDR_id").val();
	var customer_no = $("#customerList").val();
	var description = $("#modalDescription").val();
	var pm = $("#PMList2").val();
	var man = $("#MemberList").val();
	var start_date = $("#modalstart_date").val();
	var end_date = $("#modalend_date").val();
	var type_id_status =  $("#modaltype_id_status").val(); 
	
	var Msg=$("#modalPDRMsg");
	if (Msg.text() == "PDR資料未填寫完整！" || Msg.text() == "描述不要大于30個字符！") {
		Msg.text("");
	}
	
	if (Msg.text() != "") {
		return false;
	}
	
	if (PDR_id == "" || customer_no ==0 || description == "" || pm==0 || start_date==""
		|| type_id_status==0) {
		Msg.text("PDR資料未填寫完整！");
		return false;
	} else {
		if (description.length > 30) {
			Msg.text("描述不要大于30個字符！");
			return false;
		}
		Msg.text("");
	
		$.ajax({
			type : "POST",
			url : "PDR/update.action",
			dataType : "json",
			data : {
				PDR_id : PDR_id,
				customer_no : customer_no,
				description : description,
				pm : pm,
				man : man,
				start_date : start_date,
				end_date : end_date,
				type_id_status : type_id_status
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					$("#modalPDR_id").val("");
					$("#customerList").val(0);
					$("#modalDescription").val("");
					$("#PMList2").val(0);
					$("#modalstart_date").val("");
					$("#modalend_date").val("");
					$("#modaltype_id_status").val(0); 
					
					//var fileObj = document.getElementById("PDRfile").files[0]; // js 获取文件对象
					var fileObj = document.getElementById("PDRpicture").files; // js 获取文件对象
					console.log(fileObj)
					var url = "PDR/addpicture.action"; // 接收上传文件的后台地址
				   
					for(var i=0; i< fileObj.length; i++){
						var form = new FormData(); // FormData 对象
						console.log(fileObj[i].name)
					    form.append("file", fileObj[i]); // 文件对象
					    form.append("PDR_id", PDR_id); // 文件对象
					    form.append("no", i); // 文件对象
					    
					    xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
					    xhr.open("post",url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
					    xhr.upload.onloadstart = function(){//上传开始执行方法
					        ot = new Date().getTime();   //设置上传开始时间
					        oloaded = 0;//设置上传开始时，以上传的文件大小为0
					    };
					    xhr.send(form); //开始上传，发送form数据
					}  
					
				    $("#PDRpicture").val("");
					
					
					
					
					operateAlertSmall(true, "修改PDR成功！", "");
					$("#showUpdatePDR").modal("hide");
					$("#contentPDR").bootstrapTable("refresh");
				} else {
					operateAlertSmall(false, "", "修改PDR失敗！");
				}
			}
		});
	}
}

function showModalPDR(type) {
	if(type=="PER"){
		$('#showUpdatePDR .modal-title').text("新增PER");
		$('#showUpdatePDR .num').text("PER编号");
		$('#showUpdatePDR #btnAddPDR').text("新增PER");
		$('.modalPER_id-div').hide();
	}else{
		$("#showUpdatePDR .modal-dialog .modal-content .modal-header .modal-title").text("新增PDR")
		$('#showUpdatePDR .num').text("PDR编号");
		$('#showUpdatePDR #btnAddPDR').text("新增PDR");
		$('.modalPER_id-div').show();
	}
	$("#modalPDRMsg").text("");
	$("#modalPDR_id").val("");
	$("#customerList").val("");
	$("#modalDescription").val("");
	$("#PMList2").val("");
	$("#modalstart_date").val("");
	$("#modalend_date").val("");
	$("#modaltype_id_status").val(""); 
	
	
	
	$("#btnUpdatePDR").fadeOut(10);
	$("#btnAddPDR").fadeIn(10);
	
	$("#modalstart_date").each(function(index, el) {
		$(this).removeAttr("lay-key")   //解决重复关闭模态框后再次点击闪退问题
		laydate.render({
		     elem: this,         //解决多次打开模态框闪退问题
		     format: 'yyyy-MM-dd'
		 });
	});
	$("#modalend_date").each(function(index, el) {
		$(this).removeAttr("lay-key")   //解决重复关闭模态框后再次点击闪退问题
		laydate.render({
		     elem: this,         //解决多次打开模态框闪退问题
		     format: 'yyyy-MM-dd'
		 });
	});
	
	//客户清单
	$.ajax({
		url : "customer/listSplit.action",
		type : "post",
		scriptCharset : "utf-8",
		data : {
			currentPage : 0,
			lineSize : 100,
		},
		success : function(data) {
			var select = $('#customerList');
			var list = data.list1;
			select.empty();
			var option = $("<option value=0 selceted>--- 選擇客户 ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i].no
						+ ">" + list[i].short_name + "</option>");
				option.appendTo(select);
			}
		}
	});
	
	//PM清单
	$.ajax({
		url : "member/PMList.action",
		type : "post",
		scriptCharset : "utf-8",
		success : function(data) {
			var select = $('#PMList2');
			var list = data;
			select.empty();
			var option = $("<option value=0 selceted>--- 請選擇PM ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i].member_id
						+ ">" + list[i].member_name + "</option>");
				option.appendTo(select);
			}


			var select2 = $('#MemberList');
			var list2 = data;
			select2.empty();
			var option = $("<option value=0 selceted>--- 請選擇業務員 ---</option>");
			option.appendTo(select2);
			for (var i in list2) {
				var option = $("<option value=" + list2[i].member_id
						+ ">" + list2[i].member_name + "</option>");
				option.appendTo(select2);
			}
			select2.val("admin")
		}
	
	})
	//执行状态
	$.ajax({
		url : "type/listByParentType.action",
		type : "post",
		scriptCharset : "utf-8",
		data : {
			parent_type : "PDR状态"
		},
		success : function(data) {
			var select = $('#modaltype_id_status');
			var list = data;
			select.empty();
			var option = $("<option value=0 selceted>--- 請選擇执行状态 ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i].id
						+ ">" + list[i].sub_type + "</option>");
				option.appendTo(select);
			}
		}
	})
	
	
	//PDR_id
	$.ajax({
		type : "POST",
		url : "PDR/findLast.action",
		dataType : "json",
		success : function(data) {
			//最后一个PDR
			var PDR_id= data.list[0].PDR_id
			var year_month=PDR_id.substring(3,9)
			var num=PDR_id.substring(10)
			/*console.log("year_month:" + year_month)*/
			//今天
			var today=new Date();
			var year=today.getFullYear(); 
			var month=today.getMonth()+1;
			month<10 ? (month="0" + month) : (month=month); 
			var today_year_month = (year.toString()+month.toString());
			/*console.log("today_year_month:" + today_year_month);*/
			
			if(year_month == today_year_month){
				num= parseInt(num) +1;
				var length = num.toString().length
				switch (length){
					case 1:
						num = "00" + num;
						break;
					case 2:
						num = "0" + num;
						break;
				}
				
				$("#modalPDR_id").val(type + today_year_month.toString() + num.toString());
			}else{
				$("#modalPDR_id").val(type + today_year_month.toString() + "001");
			}
			
		}
	});
	
	$("#modalPDR_id").attr("disabled", false);
	
	document.getElementById("modalPDR_id").addEventListener("blur",function(){
		checkPDR_id($("#modalPDR_id").val(),"modalPDRMsg");
	})
	
	
	//顯示模態框
	$("#showUpdatePDR").modal("show");
	
}

function checkPDR_id(PDR_id,Msg){
	if(PDR_id=="" || PDR_id==null){
		$("#"+Msg).text("PDR编 号不允許為空！");
	}else{
		$("#"+Msg).text("");
		$.ajax({
			type : "POST",
			url : "PDR/checkPDR_id.action",
			dataType : "json",
			data : {
				PDR_id : PDR_id
			},
			success : function(data) {
				if (data == true) {
					$("#"+Msg).text("");
					return true;
				} else {
					$("#"+Msg).text("此PDR编号已經存在！");
					return false;
				}
			}
		});
	}
}


//新增角色
function addPDR() {
	var PDR_id = $("#modalPDR_id").val();
	var PER_id = $("#modalPER_id").val();
	var customer_no = $("#customerList").val();
	var description = $("#modalDescription").val();
	var pm = $("#PMList2").val();
	var man = $("#MemberList").val();
	var start_date = $("#modalstart_date").val();
	var end_date = $("#modalend_date").val();
	var type_id_status =  $("#modaltype_id_status").val(); 
	
	var Msg=$("#modalPDRMsg");
	if (Msg.text() == "PDR資料未填寫完整！" || Msg.text() == "描述不要大于20個字符！") {
		Msg.text("");
	}
	
	if (Msg.text() != "") {
		return false;
	}
	
	console.log(PDR_id)
	if (PDR_id == "" || customer_no ==0 || description == "" || pm==0 || start_date==""
		|| type_id_status==0) {
		Msg.text("PDR資料未填寫完整！");
		return false;
	} else {
		if (description.length > 30) {
			Msg.text("描述不要大于30個字符！");
			return false;
		}
		Msg.text("");
	
		$.ajax({
			type : "POST",
			url : "PDR/add.action",
			dataType : "json",
			data : {
				PDR_id : PDR_id,
				PER_id : PER_id,
				customer_no : customer_no,
				description : description,
				pm : pm,
				man : man,
				start_date : start_date,
				end_date : end_date,
				type_id_status : type_id_status
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					$("#modalPDR_id").val("");
					$("#customerList").val(0);
					$("#modalDescription").val("");
					$("#PMList2").val(0);
					$("#modalstart_date").val("");
					$("#modalend_date").val("");
					$("#modaltype_id_status").val(0); 
				
						//var fileObj = document.getElementById("PDRfile").files[0]; // js 获取文件对象
						var fileObj = document.getElementById("PDRpicture").files; // js 获取文件对象
						console.log(fileObj)
						var url = "PDR/addpicture.action"; // 接收上传文件的后台地址
					   
						for(var i=0; i< fileObj.length; i++){
							var form = new FormData(); // FormData 对象
							console.log(fileObj[i].name)
						    form.append("file", fileObj[i]); // 文件对象
						    form.append("PDR_id", PDR_id); // 文件对象
						    form.append("no", i); // 文件对象
						    
						    xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
						    xhr.open("post",url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
						    xhr.upload.onloadstart = function(){//上传开始执行方法
						        ot = new Date().getTime();   //设置上传开始时间
						        oloaded = 0;//设置上传开始时，以上传的文件大小为0
						    };
						    xhr.send(form); //开始上传，发送form数据
						}  
						
					    $("#PDRpicture").val("");
					
					
					operateAlertSmall(true, "新增PDR成功！", "");
					$("#contentPDR").bootstrapTable("refresh");
				} else {
					operateAlertSmall(false, "", "新增PDR失敗！");
				}
			}
		});
	}
}

//隱藏行沒有用
function hiderow() {
	var length=$($("#contentPDR>tbody").find("tr")).length;
	var selected = $("#contentPDR").bootstrapTable('getSelections');
	var PDR_id=selected[0].PDR_id;
	console.log(PDR_id);
	
	var length2=$($("#sub" + PDR_id + ">tbody").find("tr")).length;
	var selected2 = $("#sub" + PDR_id).bootstrapTable('getSelections');
	
	for(var i=0 ; i<length2 ; i++){
		var task=$($($("#sub" + PDR_id + ">tbody").find("tr").get(i)).find("td").get(2)).text();
		
		for(var j=0;j<selected2.length;j++){
			if(selected2[j].task==task){
				console.log(i)
				$("#sub" + PDR_id).bootstrapTable('hideRow', {index:i});
			}
		}
	}
	
}
	
	


//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG
//-----------------------------------------LOG


//新增记录
function addLog() {
	var PDR_id = $("#modalPDR_id2").val();
	var log = $("#modalLog").val();
	var reg_time = $("#modalreg_time").val();
	
	var Msg=$("#modalLogMsg");
	
	if (PDR_id == "" || reg_time=="" || log=="" ) {
		Msg.text("记录資料未填寫完整！");
		return false;
	} else {
		if (log.length > 100) {
			Msg.text("描述不要大于100個字符！");
			return false;
		}
		Msg.text("");
		$.ajax({
			type : "POST",
			url : "PDRLog/add.action",
			dataType : "json",
			data : {
				PDR_id : PDR_id,
				log : log,
				member_id : member_id,
				reg_time : reg_time
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					//var fileObj = document.getElementById("PDRfile").files[0]; // js 获取文件对象
					var fileObj = document.getElementById("PDRfile").files; // js 获取文件对象
					console.log(fileObj)
					var url = "PDRLog/addpicture.action"; // 接收上传文件的后台地址
				   
					for(var i=0; i< fileObj.length; i++){
						var form = new FormData(); // FormData 对象
						console.log(fileObj[i].name)
					    form.append("file", fileObj[i]); // 文件对象
					    form.append("PDR_id", PDR_id); // 文件对象
					    form.append("reg_time", reg_time); // 文件对象
					    form.append("no", i); // 文件对象
					    
					    xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
					    xhr.open("post",url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
					    xhr.upload.onloadstart = function(){//上传开始执行方法
					        ot = new Date().getTime();   //设置上传开始时间
					        oloaded = 0;//设置上传开始时，以上传的文件大小为0
					    };
					    xhr.send(form); //开始上传，发送form数据
					}  
					
				    $("#PDRfile").val("");
					
					$("#modalPDR_id2").val("");
					$("#modalLog").val("");
					$("#modalreg_time").val("");
					
					operateAlertSmall(true, "新增记录成功！", "");
					$("#showUpdateLog").modal("hide");
					$("#contentPDR").bootstrapTable("refresh");
				} else {
					operateAlertSmall(false, "", "新增记录失敗！");
				}
			}
		});
	}
}






























//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
//-----------------------------------task-----------------------------------------
function taskCheckSetting(){
	
	//選擇文件並顯示在控件中的功能初始化
	bsCustomFileInput.init();
	//因為導入Excel需要是沒有加密的文件，所以需要人工複製貼入excel的值，先生成一個帶表頭的table
	//下面是生成表格
	var height = $(window).height()-274;
	/*console.log("height : " + height);*/
	var width = $(window).width()-180;
	/*console.log("width : " + width);*/
	
	var PDR_id=$("#modalPDR_id2").val();
	
	document.title = PDR_id +'任务';
	
	//部门
	var dept=[];
	$.ajax({
		type : "POST",
		url : "dept/listUnlocked.action",
		dataType : "json",
		
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				dept.push(data[i].dept_name)
			}
		}
	});
	
	
	
	$.ajax({
		type : "POST",
		url : "PDRDetail/findByPDR_id.action",
		dataType : "json",
		data : {
			PDR_id : PDR_id
		},
		success : function(data) {
			
			var list= data.list;
			var PDR=data.PDR;
			console.log(list);
			console.log(PDR);
			$('#modalDescription2').val(PDR[0].description);
			$("#modalDescription2").attr("disabled", true);
			var arr = new Array();
			var z;
			for (var i = 0; i < list.length; i++) {
				arr[i] = new Array();
				
				var start_date= new Date(list[i].start_date.time).format('yyyy/MM/dd');
				var end_date= new Date(list[i].end_date.time).format('yyyy/MM/dd');
				var actual_end_date= new Date(list[i].actual_end_date.time).format('yyyy/MM/dd');
				arr[i][0] = list[i].task;
				arr[i][1] = list[i].type;
				arr[i][2] = start_date;
				arr[i][3] = end_date;
				arr[i][4] = actual_end_date;
				arr[i][5] = list[i].dept;
				arr[i][6] = list[i].person;
				arr[i][7] = list[i].remark;
			}
			
			if(list.length ==0){
				arr=[["項目評估","key"],["專案申請","key"],["模具開發","key"],["問題改善","key"],["送樣","key"]
					,["樣品承認","key"],["試產","key"],["量產","key"]]
			}
			
			
			//console.log(arr);
			var type=['key','sub1','sub2']
			$('#orderData').jexcel({
				colHeaders : ["任务名称","类型","预计开始日期","预计结束日期","实际结束日期","负责部门","负责人","备注"],
				data : arr,
				tableOverflow : true,
				allowInsertRow : true,
				allowInsertColumn : false,
				tableHeight : height,
				tableWidth : width,
				onbeforepaste:paste,	//粘貼前對字符串進行處理
				onchange : insertRow,
				colWidths : [200, 100 ,160, 160, 160,160, 160, 300],
			    columns: [
			        { type: 'text' },
			        {type:'dropdown', source:type},
			        { type: 'calendar', options: { format:'YYYY/MM/DD' } },
			        { type: 'calendar', options: { format:'YYYY/MM/DD' } },
			        { type: 'calendar', options: { format:'YYYY/MM/DD' } },
			        {type:'dropdown', source:dept},
			        { type: 'text' },
			        { type: 'text' }
			    ]
			});
			
			
			var value = new Array();
			value = $("#orderData").jexcel("getData", false);
			var length = value.length;
			for (var i =0 ; i< value.length ; i++) {
				//type
				var type=value[i][1];
				switch (type) {
				    case "key":
				    	$("#orderData").jexcel("setStyle", "A" + (i + 1), "background-color", "#778899");
				    	$("#orderData").jexcel("setStyle", "B" + (i + 1), "background-color", "#778899");
				    	$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "#778899");
				    	$("#orderData").jexcel("setStyle", "D" + (i + 1), "background-color", "#778899");
				    	$("#orderData").jexcel("setStyle", "E" + (i + 1), "background-color", "#778899");
				    	$("#orderData").jexcel("setStyle", "F" + (i + 1), "background-color", "#778899");
				    	$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "#778899");
				    	$("#orderData").jexcel("setStyle", "H" + (i + 1), "background-color", "#778899");
				        break;
				    case "sub1":
				    	$("#orderData").jexcel("setStyle", "A" + (i + 1), "background-color", "#FFD700");
				    	$("#orderData").jexcel("setStyle", "B" + (i + 1), "background-color", "#FFD700");
				    	$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "#FFD700");
				    	$("#orderData").jexcel("setStyle", "D" + (i + 1), "background-color", "#FFD700");
				    	$("#orderData").jexcel("setStyle", "E" + (i + 1), "background-color", "#FFD700");
				    	$("#orderData").jexcel("setStyle", "F" + (i + 1), "background-color", "#FFD700");
				    	$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "#FFD700");
				    	$("#orderData").jexcel("setStyle", "H" + (i + 1), "background-color", "#FFD700");
				        break;
				    case "sub2":
				    	$("#orderData").jexcel("setStyle", "A" + (i + 1), "background-color", "#FDF5E6");
				    	$("#orderData").jexcel("setStyle", "B" + (i + 1), "background-color", "#FDF5E6");
				    	$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "#FDF5E6");
				    	$("#orderData").jexcel("setStyle", "D" + (i + 1), "background-color", "#FDF5E6");
				    	$("#orderData").jexcel("setStyle", "E" + (i + 1), "background-color", "#FDF5E6");
				    	$("#orderData").jexcel("setStyle", "F" + (i + 1), "background-color", "#FDF5E6");
				    	$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "#FDF5E6");
				    	$("#orderData").jexcel("setStyle", "H" + (i + 1), "background-color", "#FDF5E6");
						break;
				} 
				
				//延误
				var start_date=new Date(value[i][2]).format('yyyy-MM-dd');
				var end_date=new Date(value[i][3]).format('yyyy-MM-dd');
				var actual_end_date = new Date(value[i][4]).format('yyyy-MM-dd');
				var today=new Date().format('yyyy-MM-dd');
				
				
				//開始時間不能大於結束時間
				if(end_date < start_date && end_date!="NaN-aN-aN"  && start_date!="NaN-aN-aN"){
					$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "yellow");
					$("#importOrderExcelMsg").text("請注意黃色底單元格，開始時間不能小於結束時間");
				}
				
				//console.log(end_date)
				//console.log(actual_end_date)
				//console.log("-------------------------")
				if( end_date < today && actual_end_date=="NaN-aN-aN"){
					$("#orderData").jexcel("setStyle", "D" + (i + 1), "background-color", "RED");
				}
				
				//console.log(actual_end_date)
				if(end_date < actual_end_date && actual_end_date!="NaN-aN-aN"){
					$("#orderData").jexcel("setStyle", "E" + (i + 1), "background-color", "RED");
				}
				
			}
			
			
		}
	});
	
	
}

//粘入的日期進行處理
function paste(str) {
	var ret = '';
	var rows = str.split('\r\n');
	var trows = [];
	rows.forEach(function(e){
		var cols = e.split('\t');
		var tcols = [];
		cols.forEach(function(item){
			if(item.includes('/')){
				var date = new Date(item);
				if(!date.getTime()){
					item = '20'+item;
					date = new Date(item);
				}
				var year = date.getFullYear();
				var month = date.getMonth();
				month = parseInt(month)+1;
				month = parseInt(month)>=10?month:'0'+month;
				var day = date.getDate();
				day = parseInt(day)>=10?day:'0'+day;
				item = year+'/'+month+'/'+day
			}
			tcols.push(item);
		});
		trows.push(tcols.join('\t'));
	});
	ret = trows.join('\r\n');
	
	return ret;
}


//以下函數是在手工輸入資料的情況下，判斷是否向下新增一行
var insertRow = function(){
	var value = new Array();
	value = $("#orderData").jexcel("getData", false);
	/*console.log(value);*/
	var length = value.length;
	/*console.log("length : " + length);*/
	//如果只有一行的時候，則數組是一維的
	if (length == 1) {
		/*console.log(value[0] + "," + value[1] + "," + value[2] + "," + value[3]+ "," + value[4]);*/
		if ((value[0] == "undefined" || value[0] == "") && 
			(value[2] == "undefined" || value[2] == "") && 
			(value[3] == "undefined" || value[3] == "")) {
			return false;
		} else {
			$("#orderData").jexcel("insertRow", 1);
		}
	} else {
		//否則的話數組是二維的
		/*console.log(value[length-1][0] + "," + value[length-1][1] + "," + value[length-1][2] + "," + value[length-1][3]);*/
		if ((value[length-1][0] == "undefined" || value[length-1][0] =="") && 
			(value[length-1][2] == "undefined" || value[length-1][2] =="") && 
			(value[length-1][3] == "undefined" || value[length-1][3] == "")) {
			return false;
		} else {
			$("#orderData").jexcel("insertRow", 1);
		}
	}
	
	
	for (var i =0 ; i< value.length ; i++) {
		//延误
		var start_date=new Date(value[i][2]).format('yyyy-MM-dd');
		var end_date=new Date(value[i][3]).format('yyyy-MM-dd');
		var actual_end_date = new Date(value[i][4]).format('yyyy-MM-dd');
		var today=new Date().format('yyyy-MM-dd');
		//開始時間不能大於結束時間
		if(end_date < start_date && end_date!="NaN-aN-aN"  && start_date!="NaN-aN-aN"){
			$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("請注意黃色底單元格，開始時間不能小於結束時間");
		}
		
		if(end_date < today && actual_end_date == "NaN-aN-aN"){
			$("#orderData").jexcel("setStyle", "D" + (i + 1), "background-color", "RED");
		}
		if(end_date < actual_end_date && actual_end_date!="NaN-aN-aN"){
			$("#orderData").jexcel("setStyle", "E" + (i + 1), "background-color", "RED");
		}
	}
}


function addPDRDetail() {
	var value = new Array();
	value=null;
	value = $('#orderData').jexcel('getData', false);
	$("#importOrderExcelMsg").text("");
	// console.log(value.length);
	// console.log(value[0][0]);
	/*console.log(value);*/
	// 先從表格下方往上循環，遇到空的行就刪除，一旦遇到非空行則退出；
	var length = value.length;
	if (length > 1) {
		for (var i = value.length - 1; i >= 0; i--) {
			if ((value[i][0] == "undefined" || value[i][0] == "")
					&& (value[i][1] == "undefined" || value[i][1] == "")
					&& (value[i][2] == "undefined" || value[i][2] == "")
					&& (value[i][3] == "undefined" || value[i][3] == "")) {
				$("#orderData").jexcel("deleteRow", i);
			}else{
				break;
			}
		}
	}	
	
	
	
	value1 = $('#orderData').jexcel('getData', false);
	for (var i =0; i < value1.length; i++) {
		
		$("#orderData").jexcel("setStyle", "A" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "B" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "D" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "E" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "F" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "H" + (i + 1), "background-color", "white");
		
		
		
		
		if (value[i][0]=="" ){
			$("#orderData").jexcel("setStyle", "A" + (i+1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("<任务名称>不能為空！");
			return false;
		}
		
		if (value[i][1]==""){
			$("#orderData").jexcel("setStyle", "B" + (i+1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("<类型>不能為空！");
			return false;
		}
		
		if (value[i][1]!="key" && value[i][1]!="sub1" && value[i][1]!="sub2"){
			$("#orderData").jexcel("setStyle", "B" + (i+1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("<类型>只能为key;sub1;sub2这三个值！");
			return false;
		}
		
		
		var start_date=new Date(value[i][2]).format('yyyy-MM-dd');
		var end_date=new Date(value[i][3]).format('yyyy-MM-dd');
		//開始時間不能大於結束時間
		if(end_date < start_date && end_date!="NaN-aN-aN"  && start_date!="NaN-aN-aN"){
			$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("請注意黃色底單元格，開始時間不能小於結束時間");
			return false;
		}
		
		if(value[i][5]==undefined) {
			$("#orderData").jexcel("setStyle", "F" + (i+1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("<負責部門>不能為空！");
			return false;
		}
		
		console.log(value[i][7].length)
		if(value[i][7].length>100) {
			$("#orderData").jexcel("setStyle", "H" + (i+1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("<備注>不能大於100個字！");
			return false;
		}
	}
	
	
	var PDR_id=$("#modalPDR_id2").val();
	
	if (true) {
		for (var i = 0; i < value1.length; i++) {
			value1[i][2] = new Date(value1[i][2]).format('yyyy/MM/dd');
			value1[i][3] = new Date(value[i][3]).format('yyyy/MM/dd');
			value1[i][4] = new Date(value[i][4]).format('yyyy/MM/dd');
			//清除空格
			value1[i][0] = value1[i][0].replace(/\s+/g,"");
		}
		
		//確認
		var dataString = JSON.stringify(value1);
		$.ajax({
			type : "POST",
			url : "PDRDetail/add.action",
			dataType : "json",
			data : {
				PDR_id : PDR_id,
				data : dataString
			},
			success : function(data) {
				if (data == true ) {
					$("#orderData").empty();
					taskCheckSetting();
					operateAlert(true, "任务编辑成功！", "");
				} else {
					operateAlert(false, "", "任务编辑失敗！");
				}
			}
		});
		
		
		
	} else {
		$("#importOrderExcelMsg").text("");
		$("#importOrderExcelMsg").text("數據沒有填寫完整，請刪除空行，且將所有數據填寫完整！");
	}
}

























//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
//---------------------------------------taskFind--------------------------
function PDRDetail_CheckSetting(){
	document.title = '任务查询';
	
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}


function loadTask() {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	var TaskType=$('#TaskType').val();
	console.log(TaskType)
	switch(TaskType){
		case "1":
			url="PDRDetail/findByLate.action";
			break;
		case "2":
			url="PDRDetail/findBy5Day.action";
			break;
	}
	console.log(url)
	var table = $("#contentTask");
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
		pagination: false,
		pageSize: 10, //单页记录数
		pageList: [5,10,20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
            return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit
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
		exportTypes : ['excel'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allRole', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		
        idField: 'no',//主键
		columns : [{
					title : '序號',
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
					field : 'no',
					title : 'no',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'PDR_id',
					title : 'PDR编号',
					valign : 'middle',
					align : 'center',
					visible : true
				}, {
					title : 'PM',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return row.pdr.member.member_name
					}
				}, {
					field : 'task',
					title : '任务名称',
					valign : 'middle',
					halign : 'center',
					align : 'center',
				}, {
					field : 'type',
					title : '类型',
					valign : 'middle',
					align : 'center'
				}, {
					title : '預計开始日期',
					field : 'start_date',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return new Date(value.time).format('yyyy-MM-dd');
						//return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					title : '預計結束日期',
					field : 'end_date',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						if(value !=""){
							return new Date(value.time).format('yyyy-MM-dd');
						}
					}
				}, {
					field : 'dept',
					title : '负责部门',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'person',
					title : '负责人',
					valign : 'middle',
					align : 'center'
				
				
				}, {
					field : 'remark',
					title : '备注',
					valign : 'middle',
					align : 'center',
					width: 400,
				}],
			onLoadSuccess : function(data) {
		           console.log(data);
		    },
		   
	});
}































//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------
//------------------------记录-----------------------------

function PDRLogSetting(){
	// 禁止新增權限的form提交刷新頁面
	$("#modalFormRole").submit(function() {
		return false;
	});
	
	$("#modalFormLog").submit(function() {
		return false;
	});
	
	//設置更新角色模態框的垂直位置
	$('#showUpdatePDR').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdatePDR').find('.modal-dialog').height();
				//console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) -300);
			}
		});
	});
	
	
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
	
	vm1 = new Vue({
		el: '#msgDiv',		// 设置要进行渲染的根元素
		data() {
		   return {
			   images : [],
		    };
		},
	}) ;	// 实例化MVVM的管理对象
	
}

var vm1;

function loadPDRLog() {
	var url="PDRLog/findByPDR_id.action";
	var PDR_id = $('#PDR_id').val();
	if(!PDR_id){
		return;
	}
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var table = $("#contentPDRLog");
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
		pagination: false,
		pageSize: 10, //单页记录数
		pageList: [5,10,20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
            return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				PDR_id : PDR_id
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
		exportTypes : ['excel'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allLog', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		
        idField: 'no',//主键
		columns : [{
					title : '序號',
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
					field : 'no',
					title : 'no',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'PDR_id',
					title : 'PDR编号',
					valign : 'middle',
					align : 'center',
					visible : true
				}, {
					title : '记录人',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return row.member.member_name;
					}
				}, {
					field : 'log',
					title : '记录内容',
					valign : 'middle',
					halign : 'center',
					align : 'center',
				}, {
					title : '记录时间',
					field : 'reg_time',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
						//return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					title : '操作',
					valign : 'middle',
					align : 'center',
					width: 100,
					events : operateEvents_PDRLog,
					formatter:function(value,row,index){
				        return [
				           "<a id='deleteLog' href='#' title='清除记录'><i class='fa fa-trash' style='color:red'></i></a>"+
				           "&nbsp;&nbsp;&nbsp;&nbsp;",
				           "<a id='deletepicture' href='#' title='清除圖片'><i class='fa fa-ban' style='color:red'></i></a>"+
				           "&nbsp;&nbsp;&nbsp;&nbsp;",
				           "<a id='editLog' href='#' title='编辑记录'><i class='fa fa-pencil'></i></a>"
				        ].join("");
				    }
				}],
			onLoadSuccess : function(data) {
		        //console.log(data);
		        var length=$($("#contentPDRLog>tbody").find("tr")).length;
		        
		        //vm1.destroyed();
		        var path1 = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
		    	
		    	
		       	for(var i=0;i<length;i++){
		       		//描述
			        var descriptioncell=$($($("#contentPDRLog>tbody").find("tr").get(i)).find("td").get(3));
			   		descriptioncell.css({
			   			'text-align' : 'left',
			   			'word-break' : 'break-all'
			   		});
		       
			   		//確認是否有圖片
			   		var logdatecell=$($($("#contentPDRLog>tbody").find("tr").get(i)).find("td").get(4));
			   		var logdate= new Date(logdatecell.text()).format('yyyy-MM-dd');
			   		var part_id=$($($("#contentPDRLog>tbody").find("tr").get(i)).find("td").get(1)).text();
					var path3= "/project-images/" + part_id;
					$.ajax({
						url : "link/images2.action",
						type : "post",
						scriptCharset : "utf-8",
						dataType: 'json',
						data : {
							path : path3,
							logdate : logdate,
							no : i 
						},
						success: function(res) {
							var n=0;
							//console.log(res);
							for (var k = 0; k < res.list.length; k++) {
								let index = res.list[k].lastIndexOf("\\");
								let path4 = res.list[k].substring(parseInt(index) + 1);
								if(path4.substring(0,10) == res.logdate ){
									n++
								}
							}
							
							if(n>0){
								//console.log(n)
								let log =$($($("#contentPDRLog>tbody").find("tr").get(res.no)).find("td").get(3)).text();
								$($($("#contentPDRLog>tbody").find("tr").get(res.no)).find("td").get(3)).text("");
								$($($("#contentPDRLog>tbody").find("tr").get(res.no)).find("td").get(3)).append("<i class='fa fa-picture-o' style='color:red'></i>" + "  " + log);
							}
			            },	
					});
					
			       	//picture
			       	var logdate_cell=$($($("#contentPDRLog>tbody").find("tr").get(i)).find("td").get(4));
			       	logdate_cell.hover(function(){
						this.style.cursor="pointer";
					});
			       	
			    	logdate_cell.on("click", function() {
				       	var logdate=new Date($(this).text()).format('yyyy-MM-dd');
						let part_id = $($($(this).parent()).find("td").get(1)).text();
						let path3= "/project-images/" + part_id;
						//console.log(path3)
						$.ajax({
							url : "link/images.action",
							type : "post",
							scriptCharset : "utf-8",
							dataType: 'json',
							data : {
								path : path3
							},
							success: function(res) {
								var n=0;
								vm1.$forceUpdate();
								vm1.images.splice(0);
								for (var k = 0; k < res.length; k++) {
									let index = res[k].lastIndexOf("\\");
									let path4 = res[k].substring(parseInt(index) + 1);
									console.log(path4.substring(0,10));
									if(path4.substring(0,10) == logdate ){
										n++
										let path5= path1 + "/project-images/" + part_id + "/" +path4;
										vm1.images.splice(n);
										vm1.$set(vm1.images,n,path5);
									}
								}
								console.log(vm1.images);
								$("#showPicture").modal("show");
			                },	
						});
			    	});
			    	
		       	
		    	} //for
		       	
		    },
		   
	});
}


window.operateEvents_PDRLog = {
		// 编辑PDR
		"click #editLog" : function(e, value, row, index) {
			var no=row.no;
			var log = row.log;
			var reg_time = new Date().format('yyyy-MM-dd hh:mm:ss');
			reg_time= new Date(row.reg_time.time).format('yyyy-MM-dd hh:mm:ss');
			$("#modalno").val("");
			$("#modalLog").val("");
			$("#modalreg_time").val("");
			$("#modalLogMsg").text("");
			
			
			$("#modalno").val(no);
			$("#modalreg_time").val(reg_time);
			$("#modalLog").val(log);
			
			$("#showUpdateLog .modal-dialog .modal-content .modal-header .modal-title").text("修改记录")
			
			$("#modalreg_time").each(function(index, el) {
				$(this).removeAttr("lay-key")   //解决重复关闭模态框后再次点击闪退问题
				laydate.render({
					    elem: this,         // 解决多次打开模态框闪退问题
					    type: 'datetime',
					  	/*done: function(value, date, endDate){
					  		$("#date1").val(value);
					  		$("#date1").trigger('change');
					  	}*/
					 });
			});
			
			$("#modalno").attr("disabled", true);
			//$("#modalreg_time").attr("disabled", true);
			//顯示模態框
			$("#PDRfile").val("");
			$("#showUpdateLog").modal("show");
		},
		
		
		// 删除Log
		"click #deleteLog" : function(e, value, row, index) {
			var no=row.no;
			$.ajax({
				url : "PDRLog/removeByno.action",
				type : "post",
				scriptCharset : "utf-8",
				data : {
					no : no
				},
				success : function(data) {
					if (data == true) {
						operateAlert(true, "记录删除成功！", "");
						$("#contentPDRLog").bootstrapTable("refresh");
					} else {
						operateAlert(false, "", "记录删除失敗！");
					}
				}
			})
			
			
		},
		
		// 删除Log
		"click #deletepicture" : function(e, value, row, index) {
			var PDR_id =row.PDR_id;
			var reg_time =new Date(row.reg_time.time).format("yyyy-MM-dd");
			$.ajax({
				url : "PDRLog/deletePicture.action",
				type : "post",
				scriptCharset : "utf-8",
				data : {
					PDR_id : PDR_id,
					reg_time : reg_time,
				},
				success : function(data) {
					if (data == true) {
						operateAlert(true, "圖片删除成功！", "");
						$("#contentPDRLog").bootstrapTable("refresh");
					} else {
						operateAlert(false, "", "圖片删除失敗！");
					}
				}
			})
		},
		
}


//新增记录
function editLog() {
	var no = $("#modalno").val();
	var log = $("#modalLog").val();
	var reg_time = $("#modalreg_time").val();
	var reg_time2 = new Date( reg_time).format('yyyy-MM-dd');
	var PDR_id = $("#PDR_id").val();
	
	var Msg=$("#modalLogMsg");
	
	if (no == "" || reg_time=="" || log=="" ) {
		Msg.text("记录資料未填寫完整！");
		return false;
	} else {
		if (log.length > 100) {
			Msg.text("描述不要大于100個字符！");
			return false;
		}
		Msg.text("");
		$.ajax({
			type : "POST",
			url : "PDRLog/update.action",
			dataType : "json",
			data : {
				no : no,
				log : log,
				member_id : member_id,
				reg_time : reg_time
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					var fileObj = document.getElementById("PDRfile").files; // js 获取文件对象
				    var url = "PDRLog/addpicture.action"; // 接收上传文件的后台地址
				    for(var i=0; i< fileObj.length; i++){
						var form = new FormData(); // FormData 对象
					    form.append("file", fileObj[i]); // 文件对象
					    form.append("PDR_id", PDR_id); // 文件对象
					    form.append("reg_time", reg_time2); // 文件对象
					    form.append("no", i); // 文件对象
					    
					    xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
					    xhr.open("post",url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
					    xhr.upload.onloadstart = function(){//上传开始执行方法
					        ot = new Date().getTime();   //设置上传开始时间
					        oloaded = 0;//设置上传开始时，以上传的文件大小为0
					    };
					    xhr.send(form); //开始上传，发送form数据
					}  
				    
				    $("#PDRfile").val("");
					
					$("#modalno").val("");
					$("#modalLog").val("");
					$("#modalreg_time").val("");
					
					operateAlertSmall(true, "修改记录成功！", "");
					$("#showUpdateLog").modal("hide");
					$("#contentPDRLog").bootstrapTable("refresh");
				} else {
					operateAlertSmall(false, "", "修改记录失敗！");
				}
			}
		});
	}
}




//-----------------------------------cost-----------------------------------------
//-----------------------------------cost-----------------------------------------
//-----------------------------------cost-----------------------------------------
//-----------------------------------cost-----------------------------------------
//-----------------------------------cost-----------------------------------------
//-----------------------------------cost-----------------------------------------
//-----------------------------------cost-----------------------------------------
//-----------------------------------cost-----------------------------------------
function PDRCostSetting(){
	document.title = 'PDR费用';
	//選擇文件並顯示在控件中的功能初始化
	bsCustomFileInput.init();
	//因為導入Excel需要是沒有加密的文件，所以需要人工複製貼入excel的值，先生成一個帶表頭的table
	//下面是生成表格
	var height = $(window).height()-274;
	/*console.log("height : " + height);*/
	var width = $(window).width()-180;
	/*console.log("width : " + width);*/
	
	var PDR_id=$("#modalPDR_id2").val();
	
	//部门
	var dept=[];
	$.ajax({
		type : "POST",
		url : "dept/listUnlocked.action",
		dataType : "json",
		
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				dept.push(data[i].dept_name)
			}
		}
	});
	
	
	
	$.ajax({
		type : "POST",
		url : "PDRCost/findByPDR_id.action",
		dataType : "json",
		data : {
			PDR_id : PDR_id
		},
		success : function(data) {
			
			var list= data.list;
			var PDR=data.PDR;
			//console.log(list);
			//console.log(PDR);
			$('#modalDescription2').val(PDR[0].description);
			$("#modalDescription2").attr("disabled", true);
			var arr = new Array();
			var z;
			for (var i = 0; i < list.length; i++) {
				arr[i] = new Array();
				
				//var actual_end_date= new Date(list[i].actual_end_date.time).format('yyyy/MM/dd');
				arr[i][0] = list[i].name;
				arr[i][1] = list[i].PAR;
				arr[i][2] = list[i].type;
				arr[i][3] = list[i].description;
				arr[i][4] = list[i].estimate_cost;
				arr[i][5] = list[i].actual_cost;
				arr[i][6] = list[i].remark;
			}
			
			//console.log(arr);
			var type=['key','sub1','sub2']
			$('#orderData').jexcel({
				colHeaders : ["名称","專案號碼","費用类型","描述","预计费用","实际费用","備注"],
				data : arr,
				tableOverflow : true,
				allowInsertRow : true,
				allowInsertColumn : false,
				tableHeight : height,
				tableWidth : width,
				onbeforepaste:paste,	//粘貼前對字符串進行處理
				onchange : insertRow2,
				colWidths : [200, 160 ,160, 400, 160,160,300],
			    columns: [
			        { type: 'text' },
			        { type: 'text' },
			        { type: 'text' },
			        { type: 'text' },
			        { type: 'numeric' },
			        { type: 'numeric' },
			        { type: 'text' }
			    ]
			});
			
			for (var i =0 ; i< list.length ; i++) {
				//備注
				var remark = list[i].remark;
				console.log(remark)
				var result1 = remark.indexOf("超支");
				var result2 = remark.indexOf("新增");
				if(result1>=0){
					$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "RED");
				}
				if(result2>=0){
					$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "orange");
				}
			}
		}
	});
	
	
}

var insertRow2 = function(){
	var value = new Array();
	value = $("#orderData").jexcel("getData", false);
	/*console.log(value);*/
	var length = value.length;
	/*console.log("length : " + length);*/
	//如果只有一行的時候，則數組是一維的
	if (length == 1) {
		/*console.log(value[0] + "," + value[1] + "," + value[2] + "," + value[3]+ "," + value[4]);*/
		if ((value[0] == "undefined" || value[0] == "") && 
			(value[2] == "undefined" || value[2] == "") && 
			(value[3] == "undefined" || value[3] == "")) {
			return false;
		} else {
			$("#orderData").jexcel("insertRow", 1);
		}
	} else {
		//否則的話數組是二維的
		/*console.log(value[length-1][0] + "," + value[length-1][1] + "," + value[length-1][2] + "," + value[length-1][3]);*/
		if ((value[length-1][0] == "undefined" || value[length-1][0] =="") && 
			(value[length-1][2] == "undefined" || value[length-1][2] =="") && 
			(value[length-1][3] == "undefined" || value[length-1][3] == "")) {
			return false;
		} else {
			$("#orderData").jexcel("insertRow", 1);
		}
	}
	
	
	for (var i =0 ; i< value.length ; i++) {
		//備注
		var remark = value[i][6];
		var result1 = remark.indexOf("超支");
		var result2 = remark.indexOf("新增");
		if(result1>=0){
			$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "RED");
		}
		if(result2>=0){
			$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "orange");
		}
		
		if(result1<0 && result2<0){
			$("#orderData").jexcel("setStyle", "G" + (i + 1), "background-color", "white");
		}
		
	}
}


function addPDRCost() {
	var value = new Array();
	value=null;
	value = $('#orderData').jexcel('getData', false);
	$("#importOrderExcelMsg").text("");
	// console.log(value.length);
	// console.log(value[0][0]);
	/*console.log(value);*/
	// 先從表格下方往上循環，遇到空的行就刪除，一旦遇到非空行則退出；
	var length = value.length;
	if (length > 1) {
		for (var i = value.length - 1; i >= 0; i--) {
			if ((value[i][0] == "undefined" || value[i][0] == "")
					&& (value[i][1] == "undefined" || value[i][1] == "")
					&& (value[i][2] == "undefined" || value[i][2] == "")
					&& (value[i][3] == "undefined" || value[i][3] == "")) {
				$("#orderData").jexcel("deleteRow", i);
			}else{
				break;
			}
		}
	}	
	
	value1 = $('#orderData').jexcel('getData', false);
	for (var i =0; i < value1.length; i++) {
		
		$("#orderData").jexcel("setStyle", "A" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "B" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "C" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "D" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "E" + (i + 1), "background-color", "white");
		$("#orderData").jexcel("setStyle", "F" + (i + 1), "background-color", "white");
		
		if (value[i][0]=="" ){
			$("#orderData").jexcel("setStyle", "A" + (i+1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("<名称>不能為空！");
			return false;
		}
		
		if (value[i][4]==""){
			$("#orderData").jexcel("setStyle", "E" + (i+1), "background-color", "yellow");
			$("#importOrderExcelMsg").text("<预计费用>不能為空！");
			return false;
		}
		
	}
	
	
	var PDR_id=$("#modalPDR_id2").val();
	
	if (true) {
		for (var i = 0; i < value1.length; i++) {
			//value1[i][2] = new Date(value1[i][2]).format('yyyy/MM/dd');
			
			//清除空格
			value1[i][0] = value1[i][0].replace(/\s+/g,"");
		}
		
		//確認
		var dataString = JSON.stringify(value1);
		$.ajax({
			type : "POST",
			url : "PDRCost/add.action",
			dataType : "json",
			data : {
				PDR_id : PDR_id,
				data : dataString
			},
			success : function(data) {
				if (data == true ) {
					$("#orderData").empty();
					PDRCostSetting();
					operateAlert(true, "费用编辑成功！", "");
				} else {
					operateAlert(false, "", "费用编辑失敗！");
				}
			}
		});
		
		
		
	} else {
		$("#importOrderExcelMsg").text("");
		$("#importOrderExcelMsg").text("數據沒有填寫完整，請刪除空行，且將所有數據填寫完整！");
	}
}








//------------------------CostCheck-----------------------------

function PDRCost_CheckSetting(){
	$("#modalFormLog").submit(function() {
		return false;
	});
	
	//設置更新角色模態框的垂直位置
	$('#showUpdatePDR').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdatePDR').find('.modal-dialog').height();
				//console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) -300);
			}
		});
	});
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}

function loadPDRCost_Check() {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url="PDRCost/findByPDR_id.action";
	var PDR_id = $('#PDR_id').val();
	
	var table = $("#contentPDRCost");
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
		pagination: false,
		pageSize: 10, //单页记录数
		pageList: [5,10,20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
            return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				PDR_id : PDR_id
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
		exportTypes : ['excel'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allLog', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		
        idField: 'no',//主键
		columns : [{
					title : '序號',
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
					field : 'no',
					title : 'no',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'PDR_id',
					title : 'PDR编号',
					valign : 'middle',
					align : 'center',
					visible : true
				}, {
					field : 'name',
					title : '名称',
					valign : 'middle',
					align : 'center',
				}, {
					field : 'PAR',
					title : 'PAR编号',
					valign : 'middle',
					halign : 'center',
					align : 'center',
				}, {
					title : '类型',
					field : 'type',
					valign : 'middle',
					align : 'center'
				}, {
					title : '描述',
					field : 'description',
					valign : 'middle',
					align : 'center'
				}, {
					title : '预计费用',
					field : 'estimate_cost',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return value.toLocaleString()
					}
						
				}, {
					title : '实际费用',
					field : 'actual_cost',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return value.toLocaleString()
					}
				}, {
					title : '備注',
					field : 'remark',
					valign : 'middle',
					align : 'left',
					width:400
				}],
			onLoadSuccess : function(data) {
		           console.log(data);
		           var list=data.list;
		           var total_estimate=0;
		           var total_actual=0;
		           for(var i=0 ; i<list.length ; i++){
		        	   total_estimate = parseInt(total_estimate) + parseInt(list[i].estimate_cost);
		        	   total_actual = parseInt(total_actual) + parseInt(list[i].actual_cost);
		           }
		           $("#total_estimate").val(total_estimate.toLocaleString());
		           $("#total_actual").val(total_actual.toLocaleString());
		           confirmPDRCost();
		    },
		   
	});
}


function confirmPDRCost() {
	var length=$($("#contentPDRCost>tbody").find("tr")).length;
	for(var i=0;i<length;i++){
		//描述
		var estimatecell=$($($("#contentPDRCost>tbody").find("tr").get(i)).find("td").get(6));
		var actualcell=$($($("#contentPDRCost>tbody").find("tr").get(i)).find("td").get(7));
		estimatecell.css({
			'text-align' : 'right',
		});
		actualcell.css({
			'text-align' : 'right',
		});
		
		var remark =$($($("#contentPDRCost>tbody").find("tr").get(i)).find("td").get(8));;
		var result1 = remark.text().indexOf("超支");
		var result2 = remark.text().indexOf("新增");
		if(result1>=0){
			remark.css({
				'background' : 'red',
			});
		}
		if(result2>=0){
			remark.css({
				'background' : 'orange',
			});
		}
	}
}



//------------------------display-----------------------------

function PDRDisplaySetting(){
	$("#modalFormLog").submit(function() {
		return false;
	});
	
	//設置更新角色模態框的垂直位置
	$('#showUpdatePDR').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdatePDR').find('.modal-dialog').height();
				//console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) -300);
			}
		});
	});
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
	
	
	var PDR_id=$("#modalPDR_id2").val();
	
	
	document.title = PDR_id + '甘特圖';
	

	$.ajax({
		type : "POST",
		url : "PDRDetail/findByPDR_id.action",
		dataType : "json",
		data : {
			PDR_id : PDR_id
		},
		success : function(data) {
			var list= data.list;
			var PDR=data.PDR;
			console.log(PDR)
			$("#modalDescription2").val(PDR[0].description);
			$("#modalDescription2").attr("disabled", true);
			
			var dataArray1 = new Array();
			var key1=1;
			var sub2=0;
			for(var i=0 ; i<list.length ; i++){
				var map = {};
				map['id'] = parseInt(i)+1;
				map['text'] = list[i].task;
				map['start_date'] = new Date(list[i].start_date.time).format('dd-MM-yyyy');
				var start_date=list[i].start_date.time
				var end_date=list[i].end_date.time
				var duration=((end_date-start_date)/(24*60*60*1000))+1;
				map['duration'] = duration;
				
				//console.log(new Date(list[i].actual_end_date.time).format('dd-MM-yyyy'));
				
				if(new Date(list[i].actual_end_date.time).format('dd-MM-yyyy') == "aN-aN-NaN"){
					map['progress'] = 0.5;
					map['open'] = true;
				}else{
					map['progress'] = 1;
					map['open'] = false;
				}
				
				if(list[i].type=="key"){
					key=parseInt(i)+1;
					map['priority'] = "1";
				}
				if(list[i].type=="sub1"){
					sub2=parseInt(i)+1;
					map['parent'] = key;
					map['priority'] = "2";
				}
				if(list[i].type=="sub2"){
					map['parent'] = sub2;
					map['priority'] = "3";
				}
				
				map['person'] =  list[i].person;
				
				/*if(list[i].task == "打樣" || list[i].task == "打样"){
					map['type'] = "milestone";
				}*/
				
				dataArray1[i] = map;
			}
			console.log(dataArray1);
			var dataString1 = JSON.stringify(dataArray1);
			var tableData1 = eval("(" + dataString1 + ")");
			
			
			
			
			
			var dataArray2 = new Array();
			var key=1;
			var sub1=0;
			var m=0;
			for(var i=1 ; i<list.length ; i++){
				var map2 = {};
				if(list[i].type=="key"){
					key=parseInt(i)+1;
					//map2['source'] = 1;
					//map2['target'] = parseInt(i)+1;
					//map2['type'] = 0;
				}
				if(list[i].type=="sub1"){
					map2['id'] = parseInt(i);
					sub1=parseInt(i)+1;
					map2['source'] = key;
					map2['target'] = parseInt(i)+1;
					map2['type'] = 1;
					dataArray2[m] = map2;
					m=parseInt(m)+1;
				}
				if(list[i].type=="sub2"){
					map2['id'] = parseInt(i);
					map2['source'] = sub1;
					map2['target'] = parseInt(i)+1;
					map2['type'] = 1;
					dataArray2[m] = map2;
					m=parseInt(m)+1;
				}
				
				//type 0代表為頭對頭   1代表為尾對頭  2代表為尾對尾
				
			}
			console.log(dataArray2);
			var dataString2 = JSON.stringify(dataArray2);
			var tableData2 = eval("(" + dataString2 + ")");
			//{ id: 1, text: "Project #2", start_date: "01-04-2018", duration: 18, progress: 0.4, open: true },
			//{id: 1, source: 1, target: 2, type: "1"},		
			
			//顏色
			gantt.templates.grid_row_class = function(start, end, item){
				return item.$level==0?"gantt_project":"";
			};
			gantt.templates.task_row_class = function(start, end, item){
				return item.$level==0?"gantt_project":"";
			};
			gantt.templates.task_class = function(start, end, item){
				return item.$level==0?"gantt_project":"";
			};
			
			/*gantt.templates.task_class = function (start, end, task) {
				switch (task.priority) {
					case "1":
						return "high";
						break;
					case "2":
						return "medium";
						break;
					case "3":
						return "low";
						break;
				}
			};*/
			
			//milestone
			gantt.templates.rightside_text = function (start, end, task) {
				if (task.type == gantt.config.types.milestone) {
					return task.text;
				}
				return "";
			};
			
			
			//周末
			gantt.templates.timeline_cell_class = function(item,date){
				    if(date.getDay()== 0 || date.getDay()== 6){
				         return "weekend";
				   }
			};
			
			//列
			gantt.config.columns = [
				{name: "text", label: "Task name", tree: true, width: 220},
				{name: "start_date", label: "Start date", align: "center",tree: false, width: 120},
				{name: "duration", label: "Dates", align: "center",tree: false, width: 50},
				{name: "owner", label: "Owner", width: '*', align: "center", template: function (item) {
					return item.person}}
			];
			
			//表頭
			gantt.config.scale_unit = "month";
			gantt.config.date_scale = "%Y,%F";
			
			gantt.config.subscales = [
			    {unit:"day",  step:1, date:"%d" }
			];
			//gantt.config.scale_unit = "week";
			//gantt.config.step = 1;
			//gantt.config.date_scale = "%F, %Y";
			//列寬
			gantt.config.min_column_width=18;
			//表頭
			gantt.config.scale_height = 35;
			//行高
			gantt.config.row_height = 28;
			//左側表寬
			gantt.config.grid_width =450;
		    //隔欄
			gantt.config.show_task_cells = true;
			
			//鼠標hover顯示
			gantt.plugins({
				tooltip: true
			});
			gantt.attachEvent("onGanttReady", function(){
				var tooltips = gantt.ext.tooltips;
				tooltips.tooltip.setViewport(gantt.$task_data);
			});
			
			
			//今天 
			gantt.plugins({
				marker: true
			});
			var dateToStr = gantt.date.date_to_str(gantt.config.task_date);
			var today = new Date();
			gantt.addMarker({
				start_date: today,
				css: "today",
				text: "Today",
				title: "Today: " + dateToStr(today)
			});
			
			
			//message
			if(PDR[0].pdrlog.length>0){
				var recorddate = new Date(PDR[0].pdrlog[0].reg_time.time).format('yyyy-MM-dd')
				var text="最新記錄：" + "<br>" + "記錄時間：" + recorddate + "<br>" + "記錄內容：" + PDR[0].pdrlog[0].log
				gantt.message({text: text, expire: -1});
			}
			
			gantt.config.autosize = true;
			
			gantt.init("orderData");
			gantt.parse({
				data: tableData1,
				links: tableData2,
			});
			
		}
	});
}






//記錄登錄人員
function recordmemberSetting(){

	//設置更新部門模態框的垂直位置
	$('#showUpdateCustomer').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateLine').find('.modal-dialog').height();
				// console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) - 300);
			}
		});
	});
	
	document.title = '登錄人員清單';
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
	
	
	$("#reg_time").each(function(index, el) {
		$(this).removeAttr("lay-key")   //解决重复关闭模态框后再次点击闪退问题
		laydate.render({
		    elem: this,         //解决多次打开模态框闪退问题
		  	format: 'yyyy-MM-dd',
		  	value:new Date()
		});
	});
}


function loadrecordmember() {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url="record/listSplit.action";
	var reg_time= $('#reg_time').val();
	
	var table = $("#contentRecordMember");
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
		pageSize: 20, //单页记录数
		pageList: [20,50,100],//分页步进值
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
            return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				reg_time : reg_time
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
		exportTypes : ['excel'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allLog', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		
        idField: 'no',//主键
		columns : [{
					title : '序號',
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
					field : 'no',
					title : 'no',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'member_id',
					title : '工號',
					valign : 'middle',
					align : 'center',
					visible : true
				}, {
					title : '名稱',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						//console.log(row);
						return row.member.member_name;
					}
				}, {
					title : '登錄時間',
					valign : 'middle',
					align : 'center',
					field : 'reg_time',
					formatter: function (value, row, index) {
						return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				}],
			onLoadSuccess : function(data) {
		           console.log(data);
		    },
		   
	});
}