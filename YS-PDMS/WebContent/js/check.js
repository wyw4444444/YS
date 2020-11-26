var member_id;
var sessionRoles;



//bootstrap table  时间格式转换
Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4
						- RegExp.$1.length));
	}
	for (var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? date[k]
							: ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}

//獲得登錄的member_id對應的Roles，以便後面的js函數中調用
function getActiveUserRolesActions(){
	$.ajax({
		url : "../../member/getActiveUserRolesActions.action",
		type : "post",
		dataType : "json",
		data : {
			member_id : member_id
		},
		success : function(data) {
			sessionRolesArray = data.allRoles;
			// 將數組轉為字符串，以便用search函數查找
			sessionRoles = sessionRolesArray.join(',');
			//console.log(sessionRoles);
			//console.log(sessionRoles.length);
			//console.log(sessionRoles.search("super_admin"));
		}
	})
}


$(document).ready(function() {
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = decodeURIComponent(window.location.search).substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	 member_id = $.getUrlParam('member_id');
	var id_check = $.getUrlParam('id_check');
	var searchType = $.getUrlParam('searchType');
	getActiveUserRolesActions();
	
	//判定开启的页面
	var strUrl=window.location.href;
	var arrUrl=strUrl.split("/");
	var strPage=arrUrl[arrUrl.length-1];
	
	if (strPage.indexOf("partinfoCheck.jsp")!=-1){
		$("#modalFormPartInfo").on('change','.prop',function(event){
			  var upper_id=event.target.value;
			  if(upper_id=="undefined"){
					$(event.target).parent().nextAll(".mygroup").remove();
				  return;
			  }
			  $.ajax({
					type : "POST",
					url : "../../type/listSubTypeByUpperID.action",
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
		
		
		
		//提取单位
		$.ajax({
			type : "POST",
			url : "../../type/listSubTypeByFirstType.action",
			dataType : "json",
			data : {
				parent_type:"单位"
			},
			success : function(data) {
				var select=$('#modalUnitList');
				select.empty();
				var list = data.list;
				for (var i in list) {
					var option = $("<option value=" + list[i].sub_type
							+ ">" + list[i].sub_type + "</option>");
					option.appendTo(select);
				}
				
				
				//提取partinfo
				$.ajax({
					url : "../../partinfo/listPartInfoById.action",
					type : "post",
					dataType : "json",
					data : {
						id_check : id_check
					},
					success : function(data) {
				//		console.log(data);
						var data=data.list;
						$("#modalPartInfoId").val(data.id);
						$("#modalPartCode").val(data.part_code);
						$("#modalTradename").val(data.tradename);
						$("#modalSpec").val(data.spec);
						$("#modalUnitList").val(data.unit);
						$("#modalLoss").val(data.loss);
						var prop1=data.prop1;
						var prop2=data.prop2;
						var prop3=data.prop3;
						var prop4=data.prop4;
						var prop5=data.prop5;
						
						$(".mygroup").each(function(index,element){
							var select=$(element).find("select:first");
							var no=$(element).find("input:first").val();
							if(no==1){
								var option = $("<option value=" + prop1+ ">" + prop1 + "</option>");
								option.appendTo(select);
								select.val(prop1);
							}
							else if(no==2){
								var option = $("<option value=" + prop2+ ">" + prop2 + "</option>");
								option.appendTo(select);
								select.val(prop2);
							}else if(no==3){
								var option = $("<option value=" + prop3+ ">" + prop3+ "</option>");
								option.appendTo(select);
								select.val(prop3);
							}else if(no==4){
								var option = $("<option value=" + prop4+ ">" + prop4 + "</option>");
								option.appendTo(select);
								select.val(prop4);
							}else if(no==5){
								var option = $("<option value=" + prop5+ ">" + prop5 + "</option>");
								option.appendTo(select);
								select.val(prop5);
							}
							
							if(select.val()==""){
								$(element).nextAll(".mygroup").remove();
								$(element).remove();
								return false ;
							}
						});
					}
				});
			}
		});
			
		$("#modalPropList").one('click',function(event){
		
			//提取属性1
			$.ajax({
				type : "POST",
				url : "../../type/listSubTypeByFirstType.action",
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
					
					select.val("");
				}
			});
			
		});
		
		$("#modalPartCode").attr("readOnly",true);
		if(searchType==1){//审核  
			
			$("#modalFormPartInfo").find('.form-control').each(function(index, item){
				$(this).attr("disabled",true);
			});
			
			$("#btnUpdatePartInfo").fadeOut(10);
			$("#btnApprovePartInfo").fadeIn(10);
			$("#btnSendBackPartInfo").fadeIn(10);
			$("#btnGetBackPartInfo").fadeOut(10);
		}
		else if(searchType==2){//修改
			
			$("#modalFormPartInfo").find('.form-control').each(function(index, item){
				$(this).attr("disabled",false);
			});
			
			$("#btnUpdatePartInfo").fadeIn(10);
			$("#btnApprovePartInfo").fadeOut(10);
			$("#btnSendBackPartInfo").fadeOut(10);
			$("#btnGetBackPartInfo").fadeOut(10);
		}
		else if(searchType==3){//取回
			
			$("#modalFormPartInfo").find('.form-control').each(function(index, item){
				$(this).attr("disabled",true);
			});
			
			$("#btnUpdatePartInfo").fadeOut(10);
			$("#btnApprovePartInfo").fadeOut(10);
			$("#btnSendBackPartInfo").fadeOut(10);
			$("#btnGetBackPartInfo").fadeIn(10);
			
		}
			
			
		
		
		var table = $("#contentPartInfoCheck");
		table.bootstrapTable('destroy');
		table.empty();
		table.fadeIn(100);
		table.bootstrapTable({
			method: 'post',
			contentType: "application/x-www-form-urlencoded",
			url: "../../checklog/listById_checkAndType.action",
			dataType: "json",
			striped: true,
			cache: false,
			pagination: true,
			height : 300,
			totalField: "count",
			dataField: "list", //后端 json 对应的表格数据 key
			pageSize: 5, //单页记录数
			pageList: [5,15,30,50,100],//分页步进值
			sidePagination : "client",//指定前端分页
			paginationLoop: false,
			paginationHAlign : "left",
			//queryParamsType:'',//查询参数组织方式
			queryParams: function (params) {
				//console.log(parseInt(params.offset/5)+1);
				//console.log(params.limit);
	            return {
	            	id_check : id_check,
	            	type_check:"料号"
	            }
	        },
	        showColumns: false,
	        showRefresh: false,
	        clickToSelect: true,//是否启用点击选中行
			minimumCountColumns: 2,
			theadClasses : "thead-dark", // 表頭顏色
			showExport : false, // 是否显示导出按钮
			buttonsAlign : "right", // 按钮位置
			exportTypes : ['xlsx'], // 导出文件类型
			exportDataType : "all",
			exportOptions : {
				// ignoreColumn: [0,0], //忽略某一列的索引
				fileName : 'allPartInfoCheck', // 文件名称设置
				worksheetName : 'Sheet1', // 表格工作区名称
				tableName : 'DataTable'
			},
	        idField: 'no',//主键
			columns : [{
						title : '序號',
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
						field : 'type_check',
						title : '类型',
						valign : 'middle',
						align : 'center'
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
	else if (strPage.indexOf("bomCheck.jsp")!=-1){

		$("#modalFormBom").on('blur','.code',function(event){
			 var part_code=event.target.value.trim();
			checkPartCode2(part_code,"modalBomMsg");
		});
		
		
		//提取bom
		$.ajax({
			url : "../../bom/listBomById.action",
			type : "post",
			dataType : "json",
			data : {
				id_check : id_check
			},
			success : function(data) {
				var data=data.list;
		//		console.log(data);
				var len=data.length;
				if(data.length>0){
					$("#modalPartCodeUp").val(data[0].part_code_up);
					$("#modalBomId").val(data[0].id);
					len--;
					while(len){
						var element="<div class='input-group mb-3 mygroup'>" +
				 		"<div class='input-group-prepend'>"+
				 		"<span class='input-group-text'>下阶料号</span>"+
				 		"</div>" +
				 		"<input type='text' class='form-control code' style='width:15%;'/>" +
				 		"<div class='input-group-prepend'>" +
				 		"<span class='input-group-text'>用量</span>" +
				 		"</div>" +
						"<input type='number' class='form-control' step='0.0001' min='0'/>" +
						"<div class='input-group-prepend'>" +
						"<span class='input-group-text'>底数</span>" +
						"</div>" +
						"<input type='number'  min=0 step=1 value=1 class='form-control' />" +
						"<div class='input-group-prepend'>" +
						"<span class='input-group-text'>单位</span>" +
						"</div>" +
						"<select class='form-control'></select>" +
						"<span style='color:red;font-weight:bold;padding: 5px 0;'>&nbsp;*</span>" +
						"</div>";
						$("#modalPartCodeDown").parent().after(element);
						len--;	
					}
					//提取单位
					$.ajax({
						type : "POST",
						url : "../../type/listSubTypeByFirstType.action",
						dataType : "json",
						data : {
							parent_type:"单位"
						},
						success : function(data1) {
							var list = data1.list;
							
							var count=0;
							$(".mygroup").each(function(index,element){
								var select=$(element).find("select:first");
								select.empty();
								for (var i in list) {
									var option = $("<option value=" + list[i].id
											+ ">" + list[i].sub_type + "</option>");
									option.appendTo(select);
									if(list[i].sub_type==data[count].unit){
										select.val(list[i].id);
									}
								}
								var part_code_down=$(element).find("input:first");
								var dosage=$(element).find("input:eq(1)");
								var base=$(element).find("input:eq(2)");
								part_code_down.val(data[count].part_code_down);
								dosage.val(data[count].dosage);
								base.val(data[count].base);
								count++;
								});
							
							}
						});
					
					$("#modalPartCodeUp").attr("readOnly",true);
					if(searchType==1){//审核  
						
						$("#modalFormBom").find('.form-control').each(function(index, item){
							$(this).attr("disabled",true);
						});
						
						$("#btnUpdateBom").fadeOut(10);
						$("#btnApproveBom").fadeIn(10);
						$("#btnSendBackBom").fadeIn(10);
						$("#btnGetBackBom").fadeOut(10);
					}
					else if(searchType==2){//修改
						
						$("#modalFormBom").find('.form-control').each(function(index, item){
							$(this).attr("disabled",false);
						});
						
						$("#btnUpdateBom").fadeIn(10);
						$("#btnApproveBom").fadeOut(10);
						$("#btnSendBackBom").fadeOut(10);
						$("#btnGetBackBom").fadeOut(10);
					}
					else if(searchType==3){//取回
						
						$("#modalFormBom").find('.form-control').each(function(index, item){
							$(this).attr("disabled",true);
						});
						
						$("#btnUpdateBom").fadeOut(10);
						$("#btnApproveBom").fadeOut(10);
						$("#btnSendBackBom").fadeOut(10);
						$("#btnGetBackBom").fadeIn(10);
					}
					
					
					
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
						url : "../../type/listSubTypeByFirstType.action",
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

		
		
		var table = $("#contentBomCheck");
		table.bootstrapTable('destroy');
		table.empty();
		table.fadeIn(100);
		table.bootstrapTable({
			method: 'post',
			contentType: "application/x-www-form-urlencoded",
			url: "../../checklog/listById_checkAndType.action",
			dataType: "json",
			striped: true,
			cache: false,
			pagination: true,
			height : 300,
			totalField: "count",
			dataField: "list", //后端 json 对应的表格数据 key
			pageSize: 5, //单页记录数
			pageList: [5,15,30,50,100],//分页步进值
			sidePagination : "client",//指定前端分页
			paginationLoop: false,
			paginationHAlign : "left",
			//queryParamsType:'',//查询参数组织方式
			queryParams: function (params) {
				//console.log(parseInt(params.offset/5)+1);
				//console.log(params.limit);
	            return {
	            	id_check : id_check,
	            	type_check:"BOM"
	            }
	        },
	        showColumns: false,
	        showRefresh: false,
	        clickToSelect: true,//是否启用点击选中行
			minimumCountColumns: 2,
			theadClasses : "thead-dark", // 表頭顏色
			showExport : false, // 是否显示导出按钮
			buttonsAlign : "right", // 按钮位置
			exportTypes : ['xlsx'], // 导出文件类型
			exportDataType : "all",
			exportOptions : {
				// ignoreColumn: [0,0], //忽略某一列的索引
				fileName : 'allPartInfoCheck', // 文件名称设置
				worksheetName : 'Sheet1', // 表格工作区名称
				tableName : 'DataTable'
			},
	        idField: 'no',//主键
			columns : [{
						title : '序號',
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
						field : 'type_check',
						title : '类型',
						valign : 'middle',
						align : 'center'
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
	else if (strPage.indexOf("docCheck.jsp")!=-1){

		$("#modalFormBom").on('blur','.code',function(event){
			 var part_code=event.target.value.trim();
			checkPartCode2(part_code,"modalBomMsg");
		});
		
		
		//提取bom
		$.ajax({
			url : "../../bom/listBomById.action",
			type : "post",
			dataType : "json",
			data : {
				id_check : id_check
			},
			success : function(data) {
				var data=data.list;
		//		console.log(data);
				var len=data.length;
				if(data.length>0){
					$("#modalPartCodeUp").val(data[0].part_code_up);
					$("#modalBomId").val(data[0].id);
					len--;
					while(len){
						var element="<div class='input-group mb-3 mygroup'>" +
				 		"<div class='input-group-prepend'>"+
				 		"<span class='input-group-text'>下阶料号</span>"+
				 		"</div>" +
				 		"<input type='text' class='form-control code' style='width:15%;'/>" +
				 		"<div class='input-group-prepend'>" +
				 		"<span class='input-group-text'>用量</span>" +
				 		"</div>" +
						"<input type='number' class='form-control' step='0.0001' min='0'/>" +
						"<div class='input-group-prepend'>" +
						"<span class='input-group-text'>底数</span>" +
						"</div>" +
						"<input type='number'  min=0 step=1 value=1 class='form-control' />" +
						"<div class='input-group-prepend'>" +
						"<span class='input-group-text'>单位</span>" +
						"</div>" +
						"<select class='form-control'></select>" +
						"<span style='color:red;font-weight:bold;padding: 5px 0;'>&nbsp;*</span>" +
						"</div>";
						$("#modalPartCodeDown").parent().after(element);
						len--;	
					}
					//提取单位
					$.ajax({
						type : "POST",
						url : "../../type/listSubTypeByFirstType.action",
						dataType : "json",
						data : {
							parent_type:"单位"
						},
						success : function(data1) {
							var list = data1.list;
							
							var count=0;
							$(".mygroup").each(function(index,element){
								var select=$(element).find("select:first");
								select.empty();
								for (var i in list) {
									var option = $("<option value=" + list[i].id
											+ ">" + list[i].sub_type + "</option>");
									option.appendTo(select);
									if(list[i].sub_type==data[count].unit){
										select.val(list[i].id);
									}
								}
								var part_code_down=$(element).find("input:first");
								var dosage=$(element).find("input:eq(1)");
								var base=$(element).find("input:eq(2)");
								part_code_down.val(data[count].part_code_down);
								dosage.val(data[count].dosage);
								base.val(data[count].base);
								count++;
								});
							
							}
						});
					
					$("#modalPartCodeUp").attr("readOnly",true);
					if(searchType==1){//审核  
						
						$("#modalFormBom").find('.form-control').each(function(index, item){
							$(this).attr("disabled",true);
						});
						
						$("#btnUpdateBom").fadeOut(10);
						$("#btnApproveBom").fadeIn(10);
						$("#btnSendBackBom").fadeIn(10);
						$("#btnGetBackBom").fadeOut(10);
					}
					else if(searchType==2){//修改
						
						$("#modalFormBom").find('.form-control').each(function(index, item){
							$(this).attr("disabled",false);
						});
						
						$("#btnUpdateBom").fadeIn(10);
						$("#btnApproveBom").fadeOut(10);
						$("#btnSendBackBom").fadeOut(10);
						$("#btnGetBackBom").fadeOut(10);
					}
					else if(searchType==3){//取回
						
						$("#modalFormBom").find('.form-control').each(function(index, item){
							$(this).attr("disabled",true);
						});
						
						$("#btnUpdateBom").fadeOut(10);
						$("#btnApproveBom").fadeOut(10);
						$("#btnSendBackBom").fadeOut(10);
						$("#btnGetBackBom").fadeIn(10);
					}
					
					
					
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
						url : "../../type/listSubTypeByFirstType.action",
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

		
		
		var table = $("#contentBomCheck");
		table.bootstrapTable('destroy');
		table.empty();
		table.fadeIn(100);
		table.bootstrapTable({
			method: 'post',
			contentType: "application/x-www-form-urlencoded",
			url: "../../checklog/listById_checkAndType.action",
			dataType: "json",
			striped: true,
			cache: false,
			pagination: true,
			height : 300,
			totalField: "count",
			dataField: "list", //后端 json 对应的表格数据 key
			pageSize: 5, //单页记录数
			pageList: [5,15,30,50,100],//分页步进值
			sidePagination : "client",//指定前端分页
			paginationLoop: false,
			paginationHAlign : "left",
			//queryParamsType:'',//查询参数组织方式
			queryParams: function (params) {
				//console.log(parseInt(params.offset/5)+1);
				//console.log(params.limit);
	            return {
	            	id_check : id_check,
	            	type_check:"BOM"
	            }
	        },
	        showColumns: false,
	        showRefresh: false,
	        clickToSelect: true,//是否启用点击选中行
			minimumCountColumns: 2,
			theadClasses : "thead-dark", // 表頭顏色
			showExport : false, // 是否显示导出按钮
			buttonsAlign : "right", // 按钮位置
			exportTypes : ['xlsx'], // 导出文件类型
			exportDataType : "all",
			exportOptions : {
				// ignoreColumn: [0,0], //忽略某一列的索引
				fileName : 'allPartInfoCheck', // 文件名称设置
				worksheetName : 'Sheet1', // 表格工作区名称
				tableName : 'DataTable'
			},
	        idField: 'no',//主键
			columns : [{
						title : '序號',
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
						field : 'type_check',
						title : '类型',
						valign : 'middle',
						align : 'center'
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
});

function checkPartCode2(part_code,modalMsg){
	if(part_code=="" || part_code==null){
		$("#"+modalMsg).text("料号不允许为空！");
	}else{
		$("#"+modalMsg).text("");
		$.ajax({
			type : "POST",
			url : "../../partinfo/checkPartCode.action",
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



function closeWindow(){
	
	window.opener=null;
	window.close();
}

//发起重审
function updatePartInfo(){
	$("#modalPartInfoMsg").text("");
	var id=$("#modalPartInfoId").val();
	var part_code=$("#modalPartCode").val();
	var tradename=$("#modalTradename").val();
	var spec=$("#modalSpec").val();
	var unit=$("#modalUnitList option:selected").text();
	var loss=$("#modalLoss").val();
	var tips=$("#modalPartInfoTips").val();
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
			url : "../../partinfo/update.action",
			dataType : "json",
			data : {
				id:id,
				part_code : part_code,
				tradename : tradename,
				spec : spec,
				unit : unit,
				loss : loss,
				props:props,
				tips:tips,
				member_id : member_id
			},
			success : function(data) {
				if (data == true) {
					opener.loadAwait('2');
					closeWindow();
	
				} else {
					$("#modalPartInfoMsg").val("重新发起发核失败！");
				}
			}
		});
	}
}


//退回
function sendbackPartInfo(){
	$("#modalPartInfoMsg").text("");
	var id=$("#modalPartInfoId").val();
	var part_code=$("#modalPartCode").val();
	var tips=$("#modalPartInfoTips").val();

	$.ajax({
		type : "POST",
		url : "../../partinfo/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code : part_code,
			tips:tips,
			member_id : member_id,
			status:3
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('1');
				closeWindow();
			} else {
				$("#modalPartInfoMsg").val("退回申请失败！");
			}
		}
	});

}

//退回
function getbackPartInfo(){
	$("#modalPartInfoMsg").text("");
	var id=$("#modalPartInfoId").val();
	var part_code=$("#modalPartCode").val();
	var tips=$("#modalPartInfoTips").val();

	$.ajax({
		type : "POST",
		url : "../../partinfo/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code : part_code,
			tips:tips,
			member_id : member_id,
			status:2
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('3');
				closeWindow();
			} else {
				$("#modalPartInfoMsg").val("取回申请失败！");
			}
		}
	});

}

//核准
function approvePartInfo(){
	$("#modalPartInfoMsg").text("");
	var id=$("#modalPartInfoId").val();
	var part_code=$("#modalPartCode").val();
	var tips=$("#modalPartInfoTips").val();

	$.ajax({
		type : "POST",
		url : "../../partinfo/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code : part_code,
			tips:tips,
			member_id : member_id,
			status:5
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('1');
				closeWindow();
			} else {
				$("#modalPartInfoMsg").val("核准申请失败！");
			}
		}
	});
}

//取消
function cancelPartInfo(){
	$("#modalPartInfoMsg").text("");
	var id=$("#modalPartInfoId").val();
	var part_code=$("#modalPartCode").val();
	var tips=$("#modalPartInfoTips").val();

	$.ajax({
		type : "POST",
		url : "../../partinfo/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code : part_code,
			tips:tips,
			member_id : member_id,
			status:4
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('1');
				closeWindow();
			} else {
				$("#modalPartInfoMsg").val("取消申请失败！");
			}
		}
	});
}


//发起重审
function updateBom(){
	$("#modalBomMsg").text("");
	var id=$("#modalBomId").val();
	var part_code_up=$("#modalPartCodeUp").val();
	var tips=$("#modalBomTips").val();
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
			url : "../../bom/update.action",
			dataType : "json",
			data : {
				id:id,
				part_code_up : part_code_up,
				part_code_downs : part_code_downs,
				dosages : dosages,
				bases : bases,
				units : units,
				tips:tips,
				member_id : member_id
			},
			success : function(data) {
				console.log("result_confirm:"+data.result_confirm);
				console.log("result_add:"+data.result_add);
				if (data.result_confirm == true && data.result_add == true) {
					opener.loadAwait('2');
					closeWindow();
					
				} else {
				
					var length5 = data.list_part_code_downInPartInfo.length
					if(length5>0){
						var msg="第";
						for (var k = 0; k < length5; k++) {
							var step=data.list_part_code_downInPartInfo[k]+1;
							msg+=step+"、";
						}
						$("#modalBomMsg").text(msg+"行下阶BOM料号不存在或未发行!");
						return false;
					}
					$("#modalBomMsg").text("重新发起审核失败！");
				}
			}
		});
		
	}
}


//退回
function sendbackBom(){
	$("#modalBomMsg").text("");
	var id=$("#modalBomId").val();
	var part_code_up=$("#modalPartCodeUp").val();
	var tips=$("#modalBomTips").val();

	$.ajax({
		type : "POST",
		url : "../../bom/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code_up : part_code_up,
			tips:tips,
			member_id : member_id,
			status:3
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('1');
				closeWindow();
			} else {
				$("#modalBomMsg").val("退回申请失败！");
			}
		}
	});

}


//取回
function getbackBom(){
	$("#modalBomMsg").text("");
	var id=$("#modalBomId").val();
	var part_code_up=$("#modalPartCodeUp").val();
	var tips=$("#modalBomTips").val();

	$.ajax({
		type : "POST",
		url : "../../bom/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code_up : part_code_up,
			tips:tips,
			member_id : member_id,
			status:2
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('3');
				closeWindow();
			} else {
				$("#modalBomMsg").val("取回申请失败！");
			}
		}
	});

}

//核准
function approveBom(){
	$("#modalBomMsg").text("");
	var id=$("#modalBomId").val();
	var part_code_up=$("#modalPartCodeUp").val();
	var tips=$("#modalBomTips").val();

	$.ajax({
		type : "POST",
		url : "../../bom/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code_up : part_code_up,
			tips:tips,
			member_id : member_id,
			status:5
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('1');
				closeWindow();
			} else {
				$("#modalBomMsg").val("核准申请失败！");
			}
		}
	});
}

//取消
function cancelBom(){
	$("#modalBomMsg").text("");
	var id=$("#modalBomId").val();
	var part_code_up=$("#modalPartCodeUp").val();
	var tips=$("#modalBomTips").val();
	$.ajax({
		type : "POST",
		url : "../../bom/updateCheck.action",
		dataType : "json",
		data : {
			id:id,
			part_code_up : part_code_up,
			tips:tips,
			member_id : member_id,
			status:4
		},
		success : function(data) {
			if (data == true) {
				opener.loadAwait('1');
				closeWindow();
			} else {
				$("#modaBomMsg").val("取消申请失败！");
			}
		}
	});
}


