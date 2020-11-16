/********************************料号资讯***********************************/
//操作料號信息前先進行的設置處理函數
function partInfoSetting(){
	$("#importDiv").fadeOut(10);
	$("#partInfoData").fadeOut(10);
	$("#partInfoData").empty();
	
	
	$("#modalPartCode").on('blur',function(e){
		var part_code=$("#modalPartCode").val();
		checkPartCode(part_code,"modalPartInfoMsg");
	});
	
	// 禁止模態框中更新類型的form提交刷新頁面
	$('#modalFormPartInfo').submit(function() {
		return false;
	});
	
	//設置更新模態框的垂直位置
	$('#showUpdatePartInfo').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdatePartInfo').find('.modal-dialog').height();
				// console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) - 400);
			}
		});
	});
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
	
	//提取单位
	$.ajax({
		type : "POST",
		url : "type/listSubTypeByFirstType.action",
		dataType : "json",
		data : {
			parent_type:"单位"
		},
		success : function(data) {
			var select=$('#modalUnitList');
			select.empty();
			var list = data.list;
			for (var i in list) {
				var option = $("<option value=" + list[i].id
						+ ">" + list[i].sub_type + "</option>");
				option.appendTo(select);
			}
		}
	});
	//提取属性1
	$.ajax({
		type : "POST",
		url : "type/listSubTypeByFirstType.action",
		dataType : "json",
		data : {
			parent_type:"属性1"
		},
		success : function(data) {
			var select=$('#modalPropList');
			select.empty();
			var list = data.list;
			for (var i in list) {
				var option = $("<option value=" + list[i].id
						+ ">" + list[i].sub_type + "</option>");
				option.appendTo(select);
			}
		}
	});
	
	
	$("#modalFormPartInfo").on('change','.prop',function(event){
		  var upper_id=event.target.value;
		  if(upper_id=="undefined"){
				$(event.target).parent().nextAll(".mygroup").remove();
			  return;
		  }
		  $.ajax({
				type : "POST",
				url : "type/listSubTypeByUpperID.action",
				dataType : "json",
				data : {
					upper_id:upper_id
				},
				success : function(data) {
					var list = data.list;
					if(list.length>0){
						//重新设定select的值
						//判定是否有下个属性选择框
						 var propId=$(event.target).parent().find("input:first").val();
						 propId++;
						 var select=$(event.target).parent().next().find("select:first");
						 if(select.length==0){
							 var element="<div class='input-group mb-3 mygroup'>" +
					 		"<div class='input-group-prepend'>"+
					 		"<input type='hidden' value="+propId+" />"+
					 		"<span class='input-group-text'>属&nbsp;&nbsp;&nbsp;性&nbsp;&nbsp;"+propId+"</span>" +
					 		"</div>" +
							"<select class='form-control prop'></select>" +
							"<span style='color:red;font-weight:bold;padding: 5px 0;'>&nbsp;*</span>" +
							"</div>";
							 $(event.target).parent().after(element);
						 }
						 
						 var select= $(event.target).parent().next().find("select:first");
						 select.empty();
						 
						 var option= $("<option value='undefined'>--选择属性--</option>");
							option.appendTo(select);
						for (var i in list) {
							var option = $("<option value=" + list[i].id
									+ ">" + list[i].sub_type + "</option>");
							option.appendTo(select);
						}
					}
					else{
						//删除select
						//判定是否有下个属性选择框
						$(event.target).parent().nextAll(".mygroup").remove();
					}
				}
			});
		 
		 
	});
	
}
//顯示模態框
function showModalPartInfo() {
	$("#btnUpdatePartInfo").fadeOut(10);
	$("#btnAddPartInfo").fadeIn(10);
	$("#modalPartCode").val("");
	$("#showUpdatePartInfo .modal-dialog .modal-content .modal-header .modal-title").text("新增料号信息")
	//清空異常提示文字
	$("#modalPartInfoMsg").text("");
	//顯示模態框
	$("#showUpdatePartInfo").modal("show");
}

//新增料號信息
function addPartInfo() {
	var part_code=$("#modalPartCode").val();
	var tradename=$("#modalTradename").val();
	var spec=$("#modalSpec").val();
	var unit=$("#modalUnitList option:selected").text();
	var loss=$("#modalLoss").val();
	var propAry=new Array();
	
	$(".mygroup").each(function(index,element){
		var id=$(element).find("select:first").val();
		if(id=="undefined"){
			$(element).nextAll(".mygroup").remove();
			$(element).remove();
			return false ;
		}
		var prop=$(element).find("select:first option:selected").text();
		propAry.push(prop);
	});
	
	var props = JSON.stringify(propAry);
	
	if (part_code == "" || tradename == "" || spec == "" || unit == ""||loss==""||propAry.length==0) {
		$("#modalPartInfoMsg").text("料号信息未填写完整！");
		return false;
	} else {
		if (part_code.length >20) {
			$("#modalPartInfoMsg").text("料号大于20个字符！");
			return false;
		}
		if (tradename.length > 100) {
			$("#modalPartInfoMsg").text("描述大于100个字符！");
			return false;
		}
		if (spec.length>100) {
			$("#modalPartInfoMsg").text("规格大于100个字符！");
			return false;
		}
		$("#modalPartInfoMsg").text("");
		//console.log(member_id);
		$.ajax({
			type : "POST",
			url : "partinfo/add.action",
			dataType : "json",
			data : {
				part_code : part_code,
				tradename : tradename,
				spec : spec,
				unit : unit,
				loss : loss,
				props:props,
				member_id : member_id
			},
			success : function(data) {
				if (data == true) {
					$("#modalPartCode").val("");
					$("#modalTradename").val("");
					$("#modalSpec").val("");
					$("#modalUnitList").val(0);
					$("#modalLoss").val(0);
					$("#modalPropList").val(0);
					$("#modalPropList").parent().nextAll(".mygroup").remove();
					operateAlertSmall(true, "新增料号发起审核成功！", "");
				} else {
					operateAlertSmall(false, "", "新增料号发起审核失败！");
				}
			}
		});
	}
}

//驗證part_code是否存在
function checkPartCode(part_code,modalMsg){
	if(part_code=="" || part_code==null){
		$("#"+modalMsg).text("料号不允许为空！");
	}else{
		$("#"+modalMsg).text("");
		$.ajax({
			type : "POST",
			url : "partinfo/checkPartCode.action",
			dataType : "json",
			data : {
				part_code : part_code
			},
			success : function(data) {
				if (data == true) {
					$("#"+modalMsg).text("");
					return true;
				} else {
					$("#"+modalMsg).text("此料号已经存在！");
					return false;
				}
			}
		});
	}
}




/********************************待处理项***********************************/
//操作料號信息前先進行的設置處理函數
function awaitSetting(){
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	});
	
	
}

function loadAwait(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	switch(searchType){
		case "1":
			url="checklog/listAwaitCheckSplit.action";  //待审核
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
					title : '料号',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						if(row.type_check=="料号"){
							return row.partInfo.part_code;
						}else if(row.type_check=="BOM"){
							return row.bom.part_code_up;
						}
					}
				}, {
					field : 'type_check',
					title : '类型',
					valign : 'middle',
					align : 'center',
				}, {
					title : '版本',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						if(row.type_check=="料号"){
							return "-";
						}else if(row.type_check=="BOM"){
							return row.bom.ver;
						}
					}
				}, {
					field : 'tips',
					title : '描述',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'member.member_name',
					title : '申请人',
					valign : 'middle',
					align : 'center',
				}, {
					field : 'reg_time_apply',
					title : '申请时间',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
//					field : 'partInfo.status',
					title : '申请状态',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						var value=0;
						if(row.type_check=="料号"){
							value=row.partInfo.status;
						}else if(row.type_check=="BOM"){
							value=row.bom.status;
						}
						var status="";
					switch (value){
					case 1:
						status="待审核";
						break;
					case 2:
						status="取回重办";
						break;
					case 3:
						status="退回重办";
						break;
					case 4:
						status="已取消";
						break;
					case 5:
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

window.operateEventsAwait = {
	// 鎖定或解鎖權限
	"click #showWindowDealingCheck" : function(e, value, row, index) {
		var id_check=row.id_check;
		var page;
		if(row.type_check=="料号"){
			page="pages/pop-up-windows/partinfoCheck.jsp?"
		}
		else if(row.type_check=="BOM"){
			page="pages/pop-up-windows/bomCheck.jsp?"
		}
		window.open(page+"member_id="+member_id+"&member_id="+member_id+"&id_check="+id_check+"&searchType=1","",'height=900, width=1000, top=40, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no'); 	
	},
	"click #showWindowDealingUpdate" : function(e, value, row, index) {
		var id_check=row.id_check;
		var page;
		if(row.type_check=="料号"){
			page="pages/pop-up-windows/partinfoCheck.jsp?"
		}
		else if(row.type_check=="BOM"){
			page="pages/pop-up-windows/bomCheck.jsp?"
		}
		window.open(page+"member_id="+member_id+"&id_check="+id_check+"&searchType=2","",'height=900, width=1000, top=40, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no'); 

	},
	"click #showWindowDealingGetback" : function(e, value, row, index) {
		var id_check=row.id_check;
		var page;
		if(row.type_check=="料号"){
			page="pages/pop-up-windows/partinfoCheck.jsp?"
		}
		else if(row.type_check=="BOM"){
			page="pages/pop-up-windows/bomCheck.jsp?"
		}
		window.open(page+"member_id="+member_id+"&id_check="+id_check+"&searchType=3","",'height=900, width=1000, top=40, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no'); 

	},
}



function rehandleAwait() {
	batchCheckAwait(3);
}

function approveAwait() {
	batchCheckAwait(5);
}

function batchCheckAwait(check_status){
	if(sessionRoles.match("super_admin")||sessionRoles.match("admin")){
		var length=$($("#contentAwait>tbody").find("tr")).length;
		var selected = $("#contentAwait").bootstrapTable('getSelections');
		var arr = new Array();
		var arr1 = new Array();
		if (selected!=null){
			for(var j=0;j<selected.length;j++){
				arr[j] = selected[j].id_check;
				arr1[j] = selected[j].type_check;
			}
		}
		var  id_check_selects= JSON.stringify(arr);
		var  type_check_selects= JSON.stringify(arr1);
		if(selected.length==0){
			operateAlert(false, "","尚未选择任何申请！");
			return false;
		}
		if(check_status==3?window.confirm('确定要将选择的所有申请退回吗？'):window.confirm('确定要将选择的所有申请批准吗？')){
			$.ajax({
				type : "POST",
				url : "checklog/batchCheckAwait.action",
				dataType : "json",
				data : {
					id_check_selects : id_check_selects,
					type_check_selects:type_check_selects,
					member_id:member_id,
					check_status:check_status
				},
				traditional : true,
				success : function(data) {
					if (data == true) {
						check_status==5?operateAlert(true, "批准申請成功！", ""):operateAlert(true, "退回申請成功！", "");
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

function loadPartInfo(searchType) {
	$("#importDiv").fadeOut(10);
	$("#partInfoData").fadeOut(10);
	$("#partInfoData").empty();
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	
	var part_code=$('#KeyWordPart_Code').val().trim();
	var tradename=$('#KeyWordTradeName').val().trim();
	var spec=$('#KeyWordSpec').val().trim();
	var prop=$('#KeyWordProp').val().trim();
	
	var url;
	switch(searchType){
		case "1":
			url="partinfo/listUnlockedSplitByConditions.action";
			break;
		case "2":
			url="partinfo/listSplitByConditions.action";
			break;
	}
	//console.log(url);
	var table = $("#contentPartInfo");
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
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
          return {
          	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				part_code:part_code,
				tradename:tradename,
				spec:spec,
				prop:prop
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
			fileName : 'allPartInfo', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
          //console.log(data);
      },
      idField: 'item_id',//主键
		columns : [{
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
					field : 'tradename',
					title : '品名',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'spec',
					title : '规格',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'unit',
					title : '单位',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'loss',
					title : '损耗',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'prop1',
					title : '属性1',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'prop2',
					title : '属性2',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'prop3',
					title : '属性3',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'prop4',
					title : '属性4',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'prop5',
					title : '属性5',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'prop5',
					title : '属性5',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'status',
					title : '料号状态',
					valign : 'middle',
					align : 'center',
					formatter: function (value, row, index) {
						var status="";
						switch (value){
						case 1:
							status="待审核";
							break;
						case 2:
							status="待审核";
							break;
						case 3:
							status="待审核";
							break;
						case 4:
							status="已取消";
							break;
						case 5:
							status="已发行";
							break;
						default:
							status="错误状态";
							break;
						}
						return status;
					}
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					width:60,
					events : operateEventsPartInfo,
					visible:sessionRoles.match("super_admin")||sessionRoles.match("admin")?true:false,
					formatter:function(value,row,index){
				        if(value==1){
				            return "<a id='lockOrUnlockPartInfo' href='#' title='解除废止'><i class='fa fa-lock' style='color:red'></i></a>";
				        }else if(value==0){
				            return "<a id='lockOrUnlockPartInfo' href='#' title='废止料号'><i class='fa fa-unlock' style='color:green'></i></a>";
				        }
				    }
				}]
	});
}

window.operateEventsPartInfo = {
		// 鎖定或解鎖權限
		"click #lockOrUnlockPartInfo" : function(e, value, row, index) {
			var id = row.id;
			var part_code=row.part_code;
			var locked = row.locked;
			if(locked==0){
				//如果顯示鎖定按鈕，則說明未鎖定，則將locked設置為鎖定1，以便鎖定
				locked="1";
			}else if(locked==1){
				//如果顯示解鎖按鈕，則說明已經鎖定，則將locked設置為未鎖定0，以便解鎖
				locked="0";
			}
			$.ajax({
				type : "POST",
				url : "partinfo/lockOrUnlock.action",
				dataType : "json",
				data : {
					id : id,
					part_code : part_code,
					member_id:member_id,
					locked : locked
				},
				success : function(data) {
					if (data == true) {
						if(locked=="1"){
							operateAlert(true, "料号废止成功！", "");
						}else{
							operateAlert(true, "料号解除废止成功！", "");
						}
						$("#contentPartInfo").bootstrapTable("refresh");
					} else {
						operateAlert(false, "", "操作失败！");
					}
				}
			});
		},
		
}



//顯示批量導入料號信息的相關控件及設置
function preImportPartInfo() {
	$("#toolbar").css({
		'padding-top' : 10
	})
	
	//選擇文件並顯示在控件中的功能初始化
	bsCustomFileInput.init();
	
	var table = $("#contentPartInfo");
	table.bootstrapTable('destroy');
	table.empty();
	$("#importDiv").fadeIn(10);
	$("#partInfoData").fadeIn(10);
	$("#partInfoData").empty();
	var height = $(window).height()-274;
	var width = $(window).width()-180;
	$('#partInfoData').jexcel({
		colHeaders : ["料号","品名","规格","单位","损耗","属性1","属性2","属性3","属性4","属性5"],
		tableOverflow : true,
		allowInsertRow : true,
		allowInsertColumn : false,
		tableHeight : height,
		tableWidth : width,
		onchange : insertRow_partinfo,
		colWidths : [ 150, 280, 280, 100, 100, 120, 120, 120, 120, 120],
		colAlignments : ['center','center','center','center','center','center','center','center','center','center'],
		//editable : false,
	    columns : [
	         { type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'numeric' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' }
	    ]
	});
}
//以下函數是在手工輸入資料的情況下，判斷是否向下新增一行
var insertRow_partinfo = function(){
	var value = new Array();
	value = $("#partInfoData").jexcel("getData", false);
	/*console.log(value);*/
	var length = value.length;
	/*console.log("length : " + length);*/
	//如果只有一行的時候，則數組是一維的
	if (length == 1) {
		/*console.log(value[0] + "," + value[1] + "," + value[2] + "," + value[3]+ "," + value[4]);*/
		if ((value[0] == "undefined" || value[0] == "")) {
			return false;
		} else {
			$("#partInfoData").jexcel("insertRow", 1);
		}
	} else {
		//否則的話數組是二維的
		/*console.log(value[length-1][0] + "," + value[length-1][1] + "," + value[length-1][2] + "," + value[length-1][3]);*/
		if ((value[length-1][0] == "undefined" || value[length-1][0] =="")) {
			return false;
		} else {
			$("#partInfoData").jexcel("insertRow", 1);
		}
	}
}


function importExcel(fileId,tableDiv,msgDiv,colWidths,columnsType,colAlignments){
	//首先清空表格區域
	$('#' + tableDiv).empty();
	$('#' + msgDiv).text("");
	
	//讀取完整的Excel數據進入wb對象
	var wb;
	//rABS標記是否將文件讀取為二進制字符串
  var rABS = false;
  //存儲Json字符串
  var jsonStr;
  //存儲Json對象
	var jsonObj;
  //判斷是否存在文件
  var files = document.getElementById(fileId).files;
  
	//console.log(files.length);
	if (files.length == 0) {
		$('#' + msgDiv).text("沒有选择文件！");
		return;
	}
	//判斷選擇的文件是否為Excel文件，如果不是則停止執行程序
	var fileDir = $("#" + fileId).val();
	var suffix = fileDir.substr(fileDir.lastIndexOf("."));
	if (".xls" != suffix && ".xlsx" != suffix) {
		$("#" + fileId).val("");
		$('#' + msgDiv).text("沒有选择Excel文件！");
		return;
	}    
	// console.log(files[0]);
	var file = files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var data = e.target.result;
		if (rABS) {
			wb = XLSX.read(btoa(fixdata(data)), { //手動轉換
				type : 'base64'
			});
		} else {
			wb = XLSX.read(data, {
				type : 'binary'
			});
		}
		// wb.SheetNames[0]是獲取Sheets中第一個Sheet的名字
		// wb.Sheets[Sheet名]獲取第一個Sheet的數據
		//獲取Json字符串
		jsonStr = JSON.stringify(XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]));
		//轉化為Json對象
		jsonObj = JSON.parse(jsonStr);
		//console.log(jsonObj);
		var jsonKey = new Array();
		var keyItem = 0;
		for(var key in jsonObj[0]){  
          jsonKey[keyItem] = key;
          keyItem = keyItem + 1;
      }
      
		// 拼接JS數組
		var arr = new Array();
		var z;
		for (var i = 0; i < jsonObj.length; i++) {
			arr[i] = new Array();
			for (var j = 0; j < jsonKey.length; j++) {
				arr[i][j] = jsonObj[i][jsonKey[j]];
			}
		}
		//console.log("arr : " + arr);
		
		//下面是生成表格
		var height = $(window).height()-274;
		var width = $(window).width()-180;
		$('#' + tableDiv).jexcel({
		    data : arr,
			colHeaders : jsonKey,
			tableOverflow : true,
			allowInsertRow : true,
			allowInsertColumn : false,
			tableHeight : height,
			tableWidth : width,
			colWidths : colWidths,
		    columns: columnsType,
		    colAlignments : colAlignments
		});
	};
	if (rABS) {
		reader.readAsArrayBuffer(file);
	} else {
		reader.readAsBinaryString(file);
	}
}


function importPartInfoExcel(){
	var colWidths = [ 100, 150, 150, 60, 60, 60, 60, 60, 60, 60];
	var columnsType = [
			{ type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'numeric' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' },
	        { type: 'text' }
	    ]
	var colAlignments = ['center','center','center','center','center'];
	importExcel('importPartInfoFile','partInfoData','importPartInfoExcelMsg',colWidths,columnsType,colAlignments);
}

//判斷列表中除了描述字段可以為空外，其它字段都必須不為空
function validateTableNull_partInfo(arr) {
	var length = arr.length;
	var z=true;
	if (length == 1) {
		if(arr[0][0] == "undefined" || arr[0][0] == "" || arr[0][1] == "undefined" || arr[0][1] == "" || 
			arr[0][2] == "undefined" || arr[0][2] == "" || arr[0][3] == "undefined" || arr[0][3] == ""||
			arr[0][4] == "undefined" || arr[0][4] == "" || arr[0][5] == "undefined" || arr[0][5] == ""){
			$("#partInfoData").jexcel("setStyle", [ { A1:"background-color:red" }, { B1:"background-color:red" },{ C1:"background-color:red" },{ D1:"background-color:red" },{ E1:"background-color:red" },{ F1:"background-color:red" } ]);
			z=false;
		}else{
			$("#partInfoData").jexcel("setStyle", [ { A1:"background-color:white" }, { B1:"background-color:white" },{ C1:"background-color:white" },{ D1:"background-color:white" },{ E1:"background-color:white" },{ F1:"background-color:white" }  ]);
		}
	} else {
		for(var i=0;i<length;i++){
			if(arr[i][0] == "undefined" || arr[i][0] == "" || arr[i][1] == "undefined" || arr[i][1] == "" || 
			arr[i][2] == "undefined" || arr[i][2] == "" || arr[i][3] == "undefined" || arr[i][3] == ""||
			arr[i][4] == "undefined" || arr[i][4] == "" || arr[i][5] == "undefined" || arr[i][5] == ""){
				$("#partInfoData").jexcel("setStyle", "A" + (i + 1), "background-color", "red");
				$("#partInfoData").jexcel("setStyle", "B" + (i + 1), "background-color", "red");
				$("#partInfoData").jexcel("setStyle", "C" + (i + 1), "background-color", "red");
				$("#partInfoData").jexcel("setStyle", "D" + (i + 1), "background-color", "red");
				$("#partInfoData").jexcel("setStyle", "E" + (i + 1), "background-color", "red");
				$("#partInfoData").jexcel("setStyle", "F" + (i + 1), "background-color", "red");
				z=false;
			}else{
				$("#partInfoData").jexcel("setStyle", "A" + (i + 1), "background-color", "white");
				$("#partInfoData").jexcel("setStyle", "B" + (i + 1), "background-color", "white");
				$("#partInfoData").jexcel("setStyle", "C" + (i + 1), "background-color", "white");
				$("#partInfoData").jexcel("setStyle", "D" + (i + 1), "background-color", "white");
				$("#partInfoData").jexcel("setStyle", "E" + (i + 1), "background-color", "white");
				$("#partInfoData").jexcel("setStyle", "F" + (i + 1), "background-color", "white");
			}
		}
	}
	return z;
}

function addPartInfoStocks() {
	var value = new Array();
	value = $('#partInfoData').jexcel('getData', false);
	$("#importPartInfoExcelMsg").text("");
	
	
	// 先從表格下方往上循環，遇到空的行就刪除，一旦遇到非空行則退出；
	var length = value.length;
	if (length > 1) {
		for (var i = value.length - 1; i >= 0; i--) {
			if ((value[i][0] == "undefined" || value[i][0] == "")) {
				$("#partInfoData").jexcel("deleteRow", i);
			}else{
				break;
			}
		}
	}	
	
	var value1 = $('#partInfoData').jexcel('getData', false);
	for (var i =0; i < value1.length; i++) {
		$("#partInfoData").jexcel("setStyle", "A" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "B" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "C" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "D" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "E" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "F" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "H" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "I" + (i + 1), "background-color", "white");
		$("#partInfoData").jexcel("setStyle", "J" + (i + 1), "background-color", "white");
	}

	if (validateTableNull_partInfo(value1)) {
		
		for (var i = 0; i < value1.length; i++) {
			//value1[i][3] = new Date(value[i][3]).format('yyyy/MM/dd');
			//"\s"是转移符号用以匹配任何空白字符，包括空格、制表符、换页符等等，"g"表示全局匹配将替换所有匹配的子串
			value1[i][0] = value1[i][0].replace(/\s+/g,"");
			value1[i][1] = value1[i][1].replace(/\s+/g,"");
			value1[i][2] = value1[i][2].replace(/\s+/g,"");
			value1[i][3] = value1[i][3].replace(/\s+/g,"");
			value1[i][4] = value1[i][4].replace(/\s+/g,"");
			value1[i][5] = value1[i][5].replace(/\s+/g,"");
			value1[i][6] = value1[i][6].replace(/\s+/g,"");
			value1[i][7] = value1[i][7].replace(/\s+/g,"");
			value1[i][8] = value1[i][8].replace(/\s+/g,"");
			value1[i][9] = value1[i][9].replace(/\s+/g,"");
			
			
			//百分號或小數可輸入
			var rate=value1[i][4];
			if(rate.match("%")){
				rate=rate.replace("%","")
				rate=parseFloat(rate)/100;
				rate=rate.toString();
			}
			var rate1=parseFloat(rate);
			if(rate1>1||rate1<0){
				$("#partInfoData").jexcel("setStyle", "E" + (i+1), "background-color", "ORANGE");
				$("#importPartInfoExcelMsg").text("橙色底的<损耗>不在0~1之间！");
				return false;
			}
			value1[i][4]=rate;

		}
		
		var dataString = JSON.stringify(value1);
		//console.log("dataString : " + dataString);
		$.ajax({
			type : "POST",
			url : "partinfo/addStocks.action",
			dataType : "json",
			data : {
				data : dataString,
				member_id : member_id
			},
			success : function(data) {
				if (data.flag == true || data.flag=="true") {
					$("#partInfoData").empty();
					preImportPartInfo();
					operateAlert(true, "料号资料录入成功！", "");
				} else {
					var errorRows = data.errorRows;
					//console.log("errorRows : " + errorRows);
					//console.log("errorRows.length : " + errorRows.length);
					
					for(var i=0;i<errorRows.length;i++){
						$("#partInfoData").jexcel("setStyle", "A" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "B" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "C" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "D" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "E" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "F" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "G" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "H" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "I" + (errorRows[i] + 1), "background-color", "red");
						$("#partInfoData").jexcel("setStyle", "J" + (errorRows[i] + 1), "background-color", "red");
					}
					
					//刪除導入成功的資料
					var length = value.length;
					for(var i=length-1;i>=0;i--){
						var z = 0;
						for(var j=0;j<errorRows.length;j++){
							if(i==errorRows[j]){
								z=1;
								break;
							}
						}
						if(z==0){
							$("#partInfoData").jexcel("deleteRow", i);
						}
					}
					
					operateAlert(false, "", "料号资料录入失败！剩余资料是处理失败的资料！请确认料号是否已存在、单位是否正确、属性是否正确");
				}
			}
		});
	} else {
		$("#importPartInfoExcelMsg").text("");
		$("#importPartInfoExcelMsg").text("数据没有填写完整！除了属性只需至少有一个,其他都必须填写！");
	}
}







/********************************审核记录***********************************/

function checkLogSetting(){

	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
	
	var select=$('#type_check_select');
	var option = $("<option value='料号'>料号</option>");
	option.appendTo(select);
	option = $("<option value='BOM'>BOM</option>");
	option.appendTo(select);
	//此处继续添加审核文件类型
}



function loadCheckLog() {
	var type_check=$('#type_check_select').val();
	var part_code=$('#KeyWordPart_Code').val().trim();
	if(type_check=="undefined"){
		operateAlert(false, "", "查询失败,必须选择查询类型！");
		return false;
	}
	
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");

	var url="checklog/listSplitByTypeAndPartCode.action";
	var table = $("#contentCheckLog");
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
		sidePagination : "server",//指定服务器端分页
		//queryParamsType:'',//查询参数组织方式
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
          return {
            	currentPage : parseInt(params.offset/params.limit)+1,
				lineSize : params.limit,
				type_check:type_check,
				part_code:part_code,
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
			fileName : 'allCheckLog', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
          //console.log(data);
      },
      idField: 'no',//主键
      columns : [{
			title : '序号',
			align: "center",
			width: 40,
			formatter: function (value, row, index) {
				return index+1;
			}
		}, {
			field : 'id_check',
			valign : 'middle',
			align : 'center',
			visible:false
		}, {
			title : '料号',
			valign : 'middle',
			align : 'center',
			formatter: function (value, row, index) {
				if(row.type_check=="料号"){
					return row.partInfo.part_code;
				}else if(row.type_check=="BOM"){
					return row.bom.part_code_up;
				}
			}
		}, {
			field : 'type_check',
			title : '类型',
			valign : 'middle',
			align : 'center',
		}, {
			title : '版本',
			valign : 'middle',
			align : 'center',
			formatter: function (value, row, index) {
				if(row.type_check=="料号"){
					return "-";
				}else if(row.type_check=="BOM"){
					return row.bom.ver;
				}
			}
		}, {
			field : 'reg_time_apply',
			title : '处理时间',
			valign : 'middle',
			align : 'center',
			formatter: function (value, row, index) {
				return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
			}
		}, {
			field : 'member.member_name',
			title : '处理人员',
			valign : 'middle',
			halign : 'center',
			align : 'center'
		}, {
			field : 'tips',
			title : '处理意见',
			valign : 'middle',
			halign : 'center',
			align : 'center'
		}, {
			field : 'check_status',
			title : '申请状态',
			valign : 'middle',
			halign : 'center',
			align : 'center',
			formatter: function (value, row, index) {
				var check_status="";
				switch (value){
				case 1:
					check_status="待审核";
					break;
				case 2:
					check_status="取回重办";
					break;
				case 3:
					check_status="退回重办";
					break;
				case 4:
					check_status="已取消";
					break;
				case 5:
					check_status="已发行";
					break;
				case 6:
					check_status="已废止";
					break;
				}
				return check_status;
			}
		}]
	});
}

/********************************BOM资讯***********************************/

function checkPartCode1(part_code,modalMsg){
	if(part_code=="" || part_code==null){
		$("#"+modalMsg).text("料号不允许为空！");
	}else{
		$("#"+modalMsg).text("");
		$.ajax({
			type : "POST",
			url : "partinfo/checkPartCode.action",
			dataType : "json",
			data : {
				part_code : part_code
			},
			success : function(data) {
				if (data == true) {
					$("#"+modalMsg).text("此料号不存在!");
					return false;
				} else {
					$("#"+modalMsg).text("");
					return true;
				}
			}
		});
	}
}



function bomSetting(){
	$("#modalPartCodeUp").on('blur',function(e){
		var part_code=$("#modalPartCodeUp").val();
		checkPartCode1(part_code,"modalBomMsg");
	});
	
	$("#modalFormBom").on('blur','.code',function(event){
		 var part_code=event.target.value.trim();
		checkPartCode1(part_code,"modalBomMsg");
	});
	
	// 禁止模態框中更新類型的form提交刷新頁面
	$('#modalFormBom').submit(function() {
		return false;
	});
	
	//設置更新模態框的垂直位置
	$('#showUpdateBom').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateBom').find('.modal-dialog').height();
				// console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) - 400);
			}
		});
	});
	
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
	
	//提取单位
	$.ajax({
		type : "POST",
		url : "type/listSubTypeByFirstType.action",
		dataType : "json",
		data : {
			parent_type:"单位"
		},
		success : function(data) {
			var select=$('#modalUnitList');
			select.empty();
			
			var list = data.list;
			for (var i in list) {
				var option = $("<option value=" + list[i].id
						+ ">" + list[i].sub_type + "</option>");
				option.appendTo(select);
			}
		}
	});
	

	$("#modalFormBom").on('change','.code',function(event){
		  var part_code_down=event.target.value.trim();
		  if(part_code_down==""){
				$(event.target).parent().nextAll(".mygroup").remove();
			  return;
		  }				
		  var propId=$(event.target).parent().find("input:first").val();
			 propId++;
			 var select=$(event.target).parent().next().find("select:first");
			 if(select.length==0){
				 var element="<div class='input-group mb-3 mygroup'>" +
		 		"<div class='input-group-prepend'>"+
		 		"<span class='input-group-text'>下阶料号</span>" +
		 		"</div>" +
		 		"<input type='text' class='form-control code' style='width:15%;'/>" +
		 		"<div class='input-group-prepend'>"+
		 		"<span class='input-group-text'>用量</span>" +
		 		"</div>" +
		 		"<input type='number' class='form-control' step='0.0001' min='0'/>" +
		 		"<div class='input-group-prepend'>"+
		 		"<span class='input-group-text'>底数</span>" +
		 		"</div>" +
		 		"<input type='number' class='form-control'  value='1' step='1' min='0'/>" +
		 		"<div class='input-group-prepend'>"+
		 		"<span class='input-group-text'>单位</span>" +
		 		"</div>" +
				"<select class='form-control'></select>" +
				"<span style='color:red;font-weight:bold;padding: 5px 0;'>&nbsp;*</span>" +
				"</div>";
				 $(event.target).parent().after(element);
			 }
			 var select= $(event.target).parent().next().find("select:first");
			 select.empty();
			 $.ajax({
					type : "POST",
					url : "type/listSubTypeByFirstType.action",
					dataType : "json",
					data : {
						parent_type:"单位"
					},
					success : function(data) {
						var list = data.list;
						for (var i in list) {
							var option = $("<option value=" + list[i].id
									+ ">" + list[i].sub_type + "</option>");
							option.appendTo(select);
						}
					}
				});
		 });

}

function preImportBom(){
	
	$("#main").css({
		'display' : 'inline'
	})
	var table=$("#contentBom");
	table.bootstrapTable('destroy');
	table.empty();
	
	$("#toolbar").css({
		'padding-top' : 10
	})
	
	$('#bomData').empty();
	
	//選擇文件並顯示在控件中的功能初始化
	bsCustomFileInput.init();
	//因為導入Excel需要是沒有加密的文件，所以需要人工複製貼入excel的值，先生成一個帶表頭的table
	//下面是生成表格
	var height = $(window).height()-274;
	/*console.log("height : " + height);*/
	var width = $(window).width()-180;
	/*console.log("width : " + width);*/
	$('#bomData').jexcel({
		colHeaders : ["主件料号","下阶料号","组成用量","底数","单位"],
		tableOverflow : true,
		allowInsertRow : true,
		allowInsertColumn : false,
		tableHeight : height,
		tableWidth : width,
		onchange : insertRow_bom,
		colWidths : [ 200, 200, 150,150,150],
	    columns: [
	        { type: 'text' },
	        { type: 'text' },
	       /* { type: 'calendar', options: { format:'YYYY/MM/DD' } },*/
	        { type: 'numeric' },
	        { type: 'numeric' },
	        { type: 'text' }
	    ]
	});
}


//以下函數是在手工輸入資料的情況下，判斷是否向下新增一行
var insertRow_bom = function(){
	var value = new Array();
	value = $("#bomData").jexcel("getData", false);
	/*console.log(value);*/
	var length = value.length;
	/*console.log("length : " + length);*/
	//如果只有一行的時候，則數組是一維的
	if (length == 1) {
		/*console.log(value[0] + "," + value[1] + "," + value[2] + "," + value[3]+ "," + value[4]);*/
		if ((value[0] == "undefined" || value[0] == "") && 
			(value[1] == "undefined" || value[1] == "")) {
			return false;
		} else {
			$("#bomData").jexcel("insertRow", 1);
		}
	} else {
		//否則的話數組是二維的
		/*console.log(value[length-1][0] + "," + value[length-1][1] + "," + value[length-1][2] + "," + value[length-1][3]);*/
		if ((value[length-1][0] == "undefined" || value[length-1][0] =="") && 
			(value[length-1][1] == "undefined" || value[length-1][1] =="")) {
			return false;
		} else {
			$("#bomData").jexcel("insertRow", 1);
		}
	}
}


function importBomExcel(){
	var colWidths = [ 200, 200, 150, 150, 150];
	var columnsType = [
		  { type: 'text' },
	        { type: 'text' },
	        { type: 'numeric' },
	        { type: 'numeric' },
	        { type: 'text' }
	    ]
	var colAlignments = ['center','center','center','center','center'];
	importExcel('importBomFile','BomData','importBomExcelMsg',colWidths,columnsType,colAlignments);
}

//判斷列表中除了描述字段可以為空外，其它字段都必須不為空
function validateTableNull_bom(arr) {
	var length = arr.length;
	var z=true;
	if (length == 1) {
		if(arr[0][0] == "undefined" || arr[0][0] == "" || arr[0][1] == "undefined" || arr[0][1] == "" || 
			arr[0][2] == "undefined" || arr[0][2] == "" || arr[0][3] == "undefined" || arr[0][3] == ""||
			arr[0][4] == "undefined" || arr[0][4] == "" ){
			$("#bomData").jexcel("setStyle", [ { A1:"background-color:red" }, { B1:"background-color:red" },{ C1:"background-color:red" },{ D1:"background-color:red" },{ E1:"background-color:red" }]);
			z=false;
		}else{
			$("#bomData").jexcel("setStyle", [ { A1:"background-color:white" }, { B1:"background-color:white" },{ C1:"background-color:white" },{ D1:"background-color:white" },{ E1:"background-color:white" }]);
		}
	} else {
		for(var i=0;i<length;i++){
			if(arr[i][0] == "undefined" || arr[i][0] == "" || arr[i][1] == "undefined" || arr[i][1] == "" || 
			arr[i][2] == "undefined" || arr[i][2] == "" || arr[i][3] == "undefined" || arr[i][3] == ""||
			arr[i][4] == "undefined" || arr[i][4] == "" ){
				$("#bomData").jexcel("setStyle", "A" + (i + 1), "background-color", "red");
				$("#bomData").jexcel("setStyle", "B" + (i + 1), "background-color", "red");
				$("#bomData").jexcel("setStyle", "C" + (i + 1), "background-color", "red");
				$("#bomData").jexcel("setStyle", "D" + (i + 1), "background-color", "red");
				$("#bomData").jexcel("setStyle", "E" + (i + 1), "background-color", "red");
				z=false;
			}else{
				$("#bomData").jexcel("setStyle", "A" + (i + 1), "background-color", "white");
				$("#bomData").jexcel("setStyle", "B" + (i + 1), "background-color", "white");
				$("#bomData").jexcel("setStyle", "C" + (i + 1), "background-color", "white");
				$("#bomData").jexcel("setStyle", "D" + (i + 1), "background-color", "white");
				$("#bomData").jexcel("setStyle", "E" + (i + 1), "background-color", "white");
			}
		}
	}
	return z;
}



function addBomStocks() {
	var value = new Array();
	value = $('#bomData').jexcel('getData', false);
	$("#importBomExcelMsg").text("");
	
	
	// 先從表格下方往上循環，遇到空的行就刪除，一旦遇到非空行則退出；
	var length = value.length;
	if (length > 1) {
		for (var i = value.length - 1; i >= 0; i--) {
			if ((value[i][0] == "undefined" || value[i][0] == "")&&
					(value[i][1] == "undefined" || value[i][1] == "")) {
				$("#bomData").jexcel("deleteRow", i);
			}else{
				break;
			}
		}
	}	
	
	var value1 = $('#bomData').jexcel('getData', false);
	for (var i =0; i < value1.length; i++) {
		$("#bomData").jexcel("setStyle", "A" + (i + 1), "background-color", "white");
		$("#bomData").jexcel("setStyle", "B" + (i + 1), "background-color", "white");
		$("#bomData").jexcel("setStyle", "C" + (i + 1), "background-color", "white");
		$("#bomData").jexcel("setStyle", "D" + (i + 1), "background-color", "white");
		$("#bomData").jexcel("setStyle", "E" + (i + 1), "background-color", "white");
	}

	if (validateTableNull_bom(value1)) {
		
		for (var i = 0; i < value1.length; i++) {
			//value1[i][3] = new Date(value[i][3]).format('yyyy/MM/dd');
			//"\s"是转移符号用以匹配任何空白字符，包括空格、制表符、换页符等等，"g"表示全局匹配将替换所有匹配的子串
			value1[i][0] = value1[i][0].replace(/\s+/g,"");
			value1[i][1] = value1[i][1].replace(/\s+/g,"");
			value1[i][2] = value1[i][2].replace(/\s+/g,"");
			value1[i][3] = value1[i][3].replace(/\s+/g,"");
			value1[i][4] = value1[i][4].replace(/\s+/g,"");
		}
		
		var dataString = JSON.stringify(value1);
		//console.log("dataString : " + dataString);
		$.ajax({
			type : "POST",
			url : "bom/addStocks.action",
			dataType : "json",
			data : {
				data : dataString,
				member_id : member_id
			},
			success : function(data) {
				
				console.log("result_confirm:"+data.result_confirm);
				console.log("result_add:"+data.result_add);
				if (data.result_confirm == true && data.result_add == true) {
					$("#bomData").empty();
					preImportBom();
					operateAlert(true, "资料录入成功！", "");
				} else {
					var length1 = data.list_part_code_upInPartInfo.length
					if(length1>0){
						for (var k = 0; k < length1; k++) {
							var step=data.list_part_code_upInPartInfo[k];
							$("#bomData").jexcel("setStyle", "A" + (step+1), "background-color", "ORANGE");
							$("#importBomExcelMsg").text("在数据库partinfo中，橙色底的<料号>不存在或未發行！");
						}
						return false;
					}
					
					var length2 = data.list_part_code_upchecked.length
					if(length2>0){
						for (var k = 0; k < length2; k++) {
							var step=data.list_part_code_upchecked[k];
							$("#bomData").jexcel("setStyle", "A" + (step+1), "background-color", "ORANGE");
							$("#importBomExcelMsg").text("在数据库partinfo中，橙色底的<料号>Bom已在审核中！");
						}
						return false;
					}
					
					var length3 = data.list_part_code_upLocked.length
					if(length3>0){
						for (var k = 0; k < length3; k++) {
							var step=data.list_part_code_upLocked[k];
							$("#bomData").jexcel("setStyle", "A" + (step+1), "background-color", "ORANGE");
							$("#importBomExcelMsg").text("在数据库partinfo中，橙色底的<料号>BOM已被废止！");
						}
						return false;
					}
					
					var length4 = data.list_part_code_upOther.length
					if(length4>0){
						for (var k = 0; k < length4; k++) {
							var step=data.list_part_code_upOther[k];
							$("#bomData").jexcel("setStyle", "A" + (step+1), "background-color", "ORANGE");
							$("#importBomExcelMsg").text("在数据库partinfo中，橙色底的<料号>BOM有未知错误！");
						}
						return false;
					}
					
					var length5 = data.list_part_code_downInPartInfo.length
					if(length5>0){
						for (var k = 0; k < length5; k++) {
							var step=data.list_part_code_downInPartInfo[k];
							$("#bomData").jexcel("setStyle", "B" + (step+1), "background-color", "ORANGE");
							$("#importBomExcelMsg").text("在数据库partinfo中，橙色底的<料号>不存在！");
						}
						return false;
					}
					
					var length6 = data.list_unit.length
					if(length6>0) {
						for (var k = 0; k < length6; k++) {
							var step = data.list_unit[k]
							$("#bomData").jexcel("setStyle", "E" + (step+1), "background-color", "ORANGE");
							$("#importBomExcelMsg").text("在数据库type-单位中，橙色底的<单位>不存在！");
						}
						return false;
					}
					operateAlert(false, "", "料号资料录入失败！");
					
				}
			}
		});
	} else {
		$("#importBomExcelMsg").text("");
		$("#importBomExcelMsg").text("数据没有填写完整！所有项目都必须填写！");
	}
}






//顯示模態框
function showModalBom() {

	$("#btnUpdateBom").fadeOut(10);
	$("#btnAddBom").fadeIn(10);
	$("#modalPartCodeUp").val("");
	$("#modalPartCodeDown").val("");
	$("#showUpdateBom .modal-dialog .modal-content .modal-header .modal-title").text("新增BOM信息")
	//清空異常提示文字
	$("#modalBomMsg").text("");
	//顯示模態框
	$("#showUpdateBom").modal("show");
}

//新增料號信息
function addBom() {
	$("#modalBomMsg").text("");
	var part_code_up=$("#modalPartCodeUp").val();
	var bOK=true;
	var part_code_downAry=new Array();
	var dosageAry=new Array();
	var baseAry=new Array();
	var unitAry=new Array();
	$(".mygroup").each(function(index,element){
		var part_code_down=$(element).find("input:eq(0)").val();
		if(part_code_down==""){
			$(element).nextAll(".mygroup").remove();
			$(element).remove();
			return false ;
		}
		part_code_downAry.push(part_code_down);
		var dosage=$(element).find("input:eq(1)").val();
		dosageAry.push(dosage);
		var base=$(element).find("input:eq(2)").val();
		baseAry.push(base);
		var unit=$(element).find("select:first option:selected").text();
		unitAry.push(unit);
		
		if(dosage==""||base==""){
			$("#modalBomMsg").text("所有项目都必须填写！");
			bOK=false;
		}
	});
	
	//检查重复
	var cpyAry=part_code_downAry.slice(0);
	var nary=cpyAry.sort();
	for(var i=0;i<nary.length;i++){
	       if (nary[i]==nary[i+1]){
	             $("#modalBomMsg").text("BOM下阶料号重复："+nary[i]);
	             return false;
	       }
	}
	if(bOK==false){
		return ;
	}
	else{
		
		var part_code_downs = JSON.stringify(part_code_downAry);
		var dosages = JSON.stringify(dosageAry);
		var bases = JSON.stringify(baseAry);
		var units = JSON.stringify(unitAry);

		
		
		$("#modalBomMsg").text("");
		//console.log(member_id);
		$.ajax({
			type : "POST",
			url : "bom/add.action",
			dataType : "json",
			data : {
				part_code_up : part_code_up,
				part_code_downs : part_code_downs,
				dosages : dosages,
				bases : bases,
				units : units,
				member_id : member_id
			},
			success : function(data) {
				
				console.log("result_confirm:"+data.result_confirm);
				console.log("result_add:"+data.result_add);
				if (data.result_confirm == true && data.result_add == true) {
					$("#modalPartCodeUp").val("");
					$("#modalPartCodeDown").val("");
					$("#modalDosage").val("");
					$("#modalPartCodeDown").parent().nextAll(".mygroup").remove();
					operateAlertSmall(true, "新增BOM发起审核成功！", "");
					
				} else {
					var length1 = data.list_part_code_upInPartInfo.length
					if(length1>0){
						operateAlertSmall(false, "", "主件料号不存在或未发行!");
						return false;
					}
					var length2 = data.list_part_code_upchecked.length
					if(length2>0){
						operateAlertSmall(false, "", "主件料号Bom已在审核中!");
						return false;
					}
					
					var length3 = data.list_part_code_upLocked.length
					if(length3>0){
						operateAlertSmall(false, "", "主件料号BOM已被废止!");
						return false;
					}
					
					var length4 = data.list_part_code_upOther.length
					if(length4>0){
						operateAlertSmall(false, "", "主件料号BOM有未知错误!");
						return false;
					}
					
					var length5 = data.list_part_code_downInPartInfo.length
					if(length5>0){
						var msg="第";
						for (var k = 0; k < length5; k++) {
							var step=data.list_part_code_downInPartInfo[k]+1;
							msg+=step+"、";
						}
						operateAlertSmall(false, "",msg+"行下阶BOM料号不存在或未发行!");
						
						return false;
					}
					
					operateAlertSmall(false, "", "新增BOM发起审核失败！");
					
				}
			}
		});
		
	}

}



function loadBom() {
	$("#main").css({
		'display' : 'none'
	})
	
	$("#importBomExcelMsg").text("");
	$("#bomData").empty();
	
	var part_code=$("#partKeyWord").val();
	var searchType=$("#type_select").val();
	
	if(searchType=="undefined"){
		operateAlert(false, "", "必须选择查询类型！");
		return false;
	}
	
	var url;
	switch(searchType){
	case "0":
		url="bom/listSplit0.action";
		break;
	case "1":
		url="bom/listSplit1.action";
		break;
	case "2":
		url="bom/listSplit2.action";
		break;
	case "3":
		url="bom/listSplit3.action";
		break;
	case "4":
		url="bom/listSplit4.action";
		break;
	case "5":
		url="bom/listSplit5.action";
		break;
		}
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	
	var table = $("#contentBom");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		traditional : true,
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
		pageList: [5,15,30,50,100,500,1000],//分页步进值
		sidePagination : "client",//指定客户端分页   
		//queryParamsType:'',//查询参数组织方式   
		queryParams: function (params) {
			//console.log(parseInt(params.offset/5)+1);
			//console.log(params.limit);
            return {
//            	currentPage : parseInt(params.offset/params.limit)+1,
//				lineSize : params.limit,
				part_code: part_code,
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
		exportDataType : "all",        				//--- 改前台分页limit
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allBom', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		
        idField: 'no',//主键
		columns : [{
					/*field : 'checked',
					checkbox:true,
					valign : 'middle',
					align : 'center'
				}, {*/
					title : '序号',
					align: "center",
					width: 40,
					formatter: function (value, row, index) {
					    // 获取每页显示的数量
//						var pageSize = table.bootstrapTable('getOptions').pageSize;
//						// 获取当前是第几页
//						var pageNumber = table.bootstrapTable('getOptions').pageNumber;
//						// 返回序号，注意index是从0开始的，所以要加上1
//						return pageSize * (pageNumber - 1) + index + 1;
						//改为前台分页
						return index + 1;
					}
				}, {
					field : 'no',
					title : 'no',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'level',
					title : '阶次',
					valign : 'middle',
					align : 'center',
					formatter:function(value,row,index){
						if(value==10000){
							return '.';
						}else{
							return value;
						}
				    }
				}, {
					field : 'part_code_up',
					title : '主件料号',
					valign : 'middle',
					align : 'center',
					formatter:function(value,row,index){
						
						if(searchType>=4){
							value=row.part_code_down;
						}
						
						return row.level==0||row.level==10000?value:"";
						
						
				    }
				}, {
					field : 'part_code_down',
					title : searchType<4?'下阶料号':(searchType==4?'上阶料号':'尾阶料号'),
					valign : 'middle',
					align : 'center',
					formatter:function(value,row,index){
						
						if(searchType>=4){
							value=row.part_code_up;
						}
						
						return row.level!=0?value:"";
				    }
				}, {
					field : 'partInfo.tradename',
					title : '品名',
					valign : 'middle',
					align : 'center',
				}, {
					field : 'partInfo.spec',
					title : '规格',
					valign : 'middle',
					align : 'center',
				}, {
					field : 'dosage',
					title : '组成用量',
					valign : 'middle',
					align : 'center',
					visible : searchType==5?false:true,
					formatter:function(value,row,index){
						if(searchType>=4){
							return row.level==0?value:"";
						}
						else{
							return row.level!=0?value:"";
						}
				    }
				}, {
					field : 'base',
					title : '底数',
					valign : 'middle',
					align : 'center',
					visible : searchType==5?false:true,
					formatter:function(value,row,index){
						if(searchType>=4){
							return row.level==0?value:"";
						}
						else{
							return row.level!=0?value:"";
						}
				    }
				}, {
					field : 'unit',
					title : '单位',
					valign : 'middle',
					align : 'center',
					visible : searchType==5?false:true,
				}, {
					field : 'ver',
					title : '版本',
					valign : 'middle',
					align : 'center',
					visible : searchType==5?false:true,
					formatter:function(value,row,index){
						if(searchType>=4){
							return row.level!=0?value:"";
						}
						else{
							return row.level==0||row.level==10000?value:"";
						}
				    }
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					events : operateEventsBom,
					visible:(sessionRoles.match("super_admin")||sessionRoles.match("admin"))&&searchType==0?true:false,
					formatter:function(value,row,index){
						 if(value==1){
					        return "<a id='lockOrUnlockBom' href='#' title='解除废止'><i class='fa fa-lock' style='color:red'></i></a>";
					        
					     }else if(value==0){
					    	return "<a id='lockOrUnlockBom' href='#' title='废止BOM'><i class='fa fa-unlock' style='color:green'></i></a>";
					     }
				    }
				}],
				
				onLoadSuccess : function(data) {
		            //console.log(data);  
		        } //onLoadSuccess 反括號
		
	});
	
	
}


window.operateEventsBom = {
		// 鎖定或解鎖權限
		"click #lockOrUnlockBom" : function(e, value, row, index) {
			var id = row.id;
			var part_code_up=row.part_code_up;
			var locked = row.locked;
			if(locked==0){
				//如果顯示鎖定按鈕，則說明未鎖定，則將locked設置為鎖定1，以便鎖定
				locked="1";
			}else if(locked==1){
				//如果顯示解鎖按鈕，則說明已經鎖定，則將locked設置為未鎖定0，以便解鎖
				locked="0";
			}
			$.ajax({
				type : "POST",
				url : "bom/lockOrUnlock.action",
				dataType : "json",
				data : {
					id : id,
					part_code_up : part_code_up,
					member_id:member_id,
					locked : locked
				},
				success : function(data) {
					if (data == true) {
						if(locked=="1"){
							operateAlert(true, "BOM废止成功！", "");
						}else{
							operateAlert(true, "BOM解除废止成功！", "");
						}
						$("#contentBom").bootstrapTable("refresh");
					} else {
						operateAlert(false, "", "操作失败！");
					}
				}
			});
		},
		
}


