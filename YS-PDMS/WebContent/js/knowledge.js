

//合併分階的頁面初始加載
function knowledgeAdd(){
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url = "knowledge/findPartByCode.action";
	var column="1";
	var keyword="1";
	var table = $("#contentDept");
	console.log(table)
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
				status:"5",
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
		},  {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			events : operateEventsKnowledge,
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
			events : operateEventsKnowledge,
			formatter:function(value,row,index){
	            return [
	            	"<input type='hidden' value='"+row.id+"'><button id='knowledgePartChoose' class='btn btn-primary' href='#' title='選擇'>選擇</button>"
	            ].join("");
		    }
		}]
	});
}
//加載分階添加頁面
function loadKnowledgePartAdd(){
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
					$('#name').val(data.part_name);
					$('#desc').val(data.descr);
					$('.fileShow').show()
					fileUrl = data.file
					$('.fileShow').attr('src',localFormatUrl(data.fileUrl))
//					如果是升版的話，版本號遞增，料號不能修改，附件需要重新上傳
					if(knowledgeType=="levelup"){
						$('#number').attr("readonly",true);
						$('#version').val(String.fromCharCode(data.version.charCodeAt(0) + 1));
						$('.fileUpload input').eq(0).attr("placeholder","請重新上傳附件")
						$('.fileShow').hide()
						$('.tips').show()
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
				var tips = $('#tips').val();
				if($('#file').val()){
					fileUrl = "E://part-images/knowledge/"+part_code+"_"+version+"/"+part_code+"_"+version+"."+$('#file')[0].files[0].name.split('.').pop().toLowerCase();
				}
				var id = localStorage.getItem("knowledge_part_id");
//				這裡做一個轉換，如果是升版的話，就不上傳id，使後台新增一條數據
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
						file : fileUrl,
						member_id:member_id,
						tips:tips
					},
					traditional : true,
					success : function(data) {
						console.log(data)
						if($('#file').val()){
							uploadFile("file",fileUrl)
						}
						if(knowledgeType=="levelup"||knowledgeType=="update"){
							alert("提交成功")
							window.close()
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
			if(value){
				$.ajax({
					type : "POST",
					url : "doc/checkPartCode.action",
					dataType : "json",
					data : {
						partcode : value
					},
					traditional : true,
					success : function(data) {
						if(data){
							$('.tips').hide()
							
							$.ajax({
								type : "POST",
								url : "knowledge/findOneNewPart.action",
								dataType : "json",
								data : {
									part_code : value,
									version:"A"
								},
								traditional : true,
								success : function(data) {
									console.log(data)
									if(data.id){
										$('.number-error-tip').show()
			//							因為有數據，所以自動將部分數據帶出來
										$('#name').val(data.name);
										$('#desc').val(data.desc);
										$('#version').val(String.fromCharCode(data.version.charCodeAt(0) + 1))
										$('.number-error-tip').html("料號已存在，版本號自動升一級")
										$('.tips').show()
									}else{
										console.log("沒有數據")
										$('.number-error-tip').hide()
										$('.tips').hide()
									}
								},
								error:function(data){
									if(!data.responseText){
										console.log("沒有數據")
										$('.number-error-tip').hide()
										$('.tips').hide()
									}
								}
							})
						}else{
							$('.tips').hide()
							$('.number-error-tip').html("料號不存在或待審核，請重新輸入")
							$('.number-error-tip').show()	
						}
					},
					error:function(data){
						console.log(data)
						if(!data.responseText){
							console.log("沒有數據")
							$('.tips').hide()
							$('.number-error-tip').html("料號不存在或待審核，請重新輸入")
							$('.number-error-tip').show()
						}
					}
				})
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
//						column : column,
//						keyword : keyword
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
				events : operateEventsKnowledge,
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
}

//加載成品添加頁面
function loadKnowledgeAdd(){
	$('document').ready(function(){
		knowledgeAdd()
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
//			獲取信息
			if(checkForm()){
				var part_type = $('#part_type').val()
				var part_code = $('#number').val();
				var part_name = $('#name').val();
				var version = $('#version').val();
				var desc = $('#desc').val();
				var date = $('#date').val();
				var tips = $('#tips').val();
				
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
						reg_man : member_id,
						desc : desc,
						tips : tips,
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
			var id = row.find('td').eq(4).text();
			console.log(part_code,version,id)
			open_page("knowledgePartDetail",part_code,version,"knowledge_part_id",id)
		})
		$('.partList').on("click","#choose_knowledgePartRemove",function(){
			var row = $(this).parents('tr');
			row.remove()
		})
		//料號填寫時，1、判斷料號是否正確，在接入料號系統的時候需要補上。2、判斷料號是否已經存在，存在的話就提示應該去升版
		$('.leftcontent #number').on('keyup',function(){
			var value = $(this).val();
			$.ajax({
				type : "POST",
				url : "knowledge/findOneNewKnowledge.action",
				dataType : "json",
				data : {
					part_code : value,
					version:"A"
				},
				traditional : true,
				success : function(data) {
					console.log(data)
					if(data.id){
						$('.number-error-tip').show()
						$('#type').val(data.part_type);
						$('#name').val(data.part_name);
						$('#desc').val(data.descr);
						$('#version').val(String.fromCharCode(data.version.charCodeAt(0) + 1))
						$('.number-error-tip').html("料號已存在，版本號自動升一級")
						$('.tips').show()
						
					}else{
						console.log("沒有數據")
						$('.number-error-tip').hide()
					}
				},
				error:function(data){
					if(!data.responseText){
						console.log("沒有數據")
						$('.number-error-tip').hide()
					}
				}
			})
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
//		渲染
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
//						column : column,
//						keyword : keyword
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
				events : operateEventsKnowledge,
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
				events : operateEventsKnowledge,
				formatter:function(value,row,index){
		            return [
		            	"<input type='hidden' value='"+row.id+"'><button id='knowledgePartChoose' class='btn btn-primary' href='#' title='選擇'>選擇</button>"
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
}

//加載成品更新頁面
function loadKnowledgeUpdate(){
	var type = localStorage.getItem('knowledgeDetailType')

	var part_code = localStorage.getItem("part_code")
	var version = localStorage.getItem("archives_version")
	var id = localStorage.getItem("knowledge_id")
	$('document').ready(function(){
		console.log(type)
//		如果是升級時
		if(type=="levelup"){
			console.log(part_code,version);
			//需要後台查詢該料號和版本的數據
			$.ajax({
				type : "POST",
				url : "knowledge/findKnowledgeById.action",
				dataType : "json",
				data : {
					id:id
				},
				traditional : true,
				success : function(data) {
					console.log(data)
					$('#part_type').remove();
					$('.type').append('<input type="text" class="form-control" id="part_type" name="part_type" placeholder="請輸入料號" readonly/>');
					$('#part_type').val(data.part_type);
					$('#number').val(data.part_code);
					$('#version').val(String.fromCharCode(data.version.charCodeAt(0) + 1));
					$('#name').val(data.part_name);
					$('#desc').val(data.descr);
					$('#date').val(data.date);

//					查詢分階列表
					$.ajax({
						type : "POST",
						url : "knowledge/findPartList.action",
						dataType : "json",
						data : {
							knowledge_id : data.id
						},
						traditional : true,
						success : function(data) {
							console.log(data)
							for(var i=0;i<data.length;i++){
								var html = '<tr><td>'+data[i].part_code+'</td><td>'+data[i].version+'</td>'
								+'<td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
								+'<td><button class="choose_knowledgePartRemove btn btn-primary" title="刪除">刪除</button></td><td style="display:none">'+data[i].id
								+'</td></tr>'
								$('.partList tbody').append(html)
							}
						}
					})
				}
			})
		}
//		如果是修改
		if(type=="update"){
			console.log(part_code,version);
			//需要後台查詢該料號和版本的數據
			$.ajax({
				type : "POST",
				url : "knowledge/findKnowledgeById.action",
				dataType : "json",
				data : {
					id:id
				},
				traditional : true,
				success : function(data) {
					console.log(data)
					$('#part_type').val(data.part_type);
					$('#number').val(data.part_code);
					$('#number').attr("readonly",false);
					$('#version').val(data.version);
					$('#name').val(data.part_name);
					$('#desc').val(data.descr);
					$('#date').val(data.date);

//					查詢分階列表
					$.ajax({
						type : "POST",
						url : "knowledge/findPartList.action",
						dataType : "json",
						data : {
							knowledge_id : data.id
						},
						traditional : true,
						success : function(data) {
							console.log(data)
							for(var i=0;i<data.length;i++){
								var html = '<tr><td>'+data[i].part_code+'</td><td>'+data[i].version+'</td>'
								+'<td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
								+'<td><button class="choose_knowledgePartRemove btn btn-primary" title="刪除">刪除</button></td><td style="display:none">'+data[i].id
								+'</td></tr>'
								$('.partList tbody').append(html)
							}
						}
					})
				}
			})
		}
		$('.submit').click(function(){
			console.log("提交");
//			獲取信息
			var part_type = $('#part_type').val()
			var s_part_code = $('#number').val();
			var part_name = $('#name').val();
			var s_version = $('#version').val();
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
			var param = {
				part_type : part_type,
				part_code : s_part_code,
				part_name : part_name,
				version : s_version,
				desc : desc,
				date : date,
				partArr:JSON.stringify(arr)
			}
			if(type=="update"){
				param.id = id
			}
			$.ajax({
				type : "POST",
				url : "knowledge/add.action",
				dataType : "json",
				data : param,
				traditional : true,
				success : function(data) {
					console.log(data)
					if(data){
						alert("提交成功")
						window.close();
					}
				}
			})
		})
		
		
		$('.partList').on("click",".choose_knowledgePartDetail",function(){
			var row = $(this).parents('tr');
			var s_part_code = row.find('td').eq(0).text();
			var s_version = row.find('td').eq(1).text();
			var id = row.find('td').eq(4).text();
			console.log(part_code,version,row)
			open_page("knowledgePartDetail",s_part_code,s_version,"knowledge_part_id",id)
		})
		$('.partList').on("click",".choose_knowledgePartRemove",function(){
			var row = $(this).parents('tr');
			row.remove()
		})
		knowledgeAdd();
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
	})
	function partChoose(row){
		var s_part_code = row.find('td').eq(0).text();
		var s_version = row.find('td').eq(2).text();
		var id = row.find('input').val();
		console.log(part_code,version,id)
//		渲染
		var html = '<tr><td>'+s_part_code+'</td><td>'+s_version
		+'</td><td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
		+'<td><button class="choose_knowledgePartRemove btn btn-primary" title="刪除">刪除</button></td><td style="display:none">'+id
		+'</td></tr>'
		$('.partList tbody').append(html); 
	}
}


//當打開查詢分階頁面時執行
function knowledgePart(){
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url = "knowledge/findAllPart.action";
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
			field : 'status',
			title : '申請狀態',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				var rs = "未知";
				switch(value){
				case "1":
					rs = "待審核";
					break;
				case "2":
					rs = "取回重辦";
					break;
				case "3":
					rs = "退回重辦";
					break;
				case "4":
					rs = "已取消";
					break;
				case "5":
					rs = "已發行";
					break;
				case "6":
					rs = "已廢止";
					break;
				}
	            return rs;
		    }
		}, {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			events : operateEventsKnowledge,
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<button id='knowledgePartDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
}

//待審核成品頁面初始加載
function knowledgePending(){
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url = "knowledge/findKnowledgeByCode.action";
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
            	status:'1',
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
			field : 'part_name',
			title : '名稱',
			valign : 'middle',
			align : 'center',
			width:100
		}, {
			field : 'version',
			title : '版本',
			valign : 'middle',
			align : 'center',
			width:100
		},{
			field : 'descr',
			title : '描述',
			valign : 'middle',
			align : 'center',
			width:100
		}, {
			field : 'status',
			title : '申請狀態',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				var rs = "未知";
				switch(value){
				case "1":
					rs = "待審核";
					break;
				case "2":
					rs = "取回重辦";
					break;
				case "3":
					rs = "退回重辦";
					break;
				case "4":
					rs = "已取消";
					break;
				case "5":
					rs = "已發行";
					break;
				case "6":
					rs = "已廢止";
					break;
				}
	            return rs;
		    }
		}, {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			width:100,
			events : operateEventsKnowledge,
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<button id='knowledgeDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
}

//待審核分階頁面初始加載
function knowledgeProcessed(){
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url = "knowledge/findProcessed.action";
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
			events : operateEventsKnowledge,
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<button id='knowledgePartDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
}

//查詢成品頁面
function knowledgeQuery(){
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url = "knowledge/findAllKnowledge.action";
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
			field : 'status',
			title : '申請狀態',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				var rs = "未知";
				switch(value){
				case "1":
					rs = "待審核";
					break;
				case "2":
					rs = "取回重辦";
					break;
				case "3":
					rs = "退回重辦";
					break;
				case "4":
					rs = "已取消";
					break;
				case "5":
					rs = "已發行";
					break;
				case "6":
					rs = "已廢止";
					break;
				}
	            return rs;
		    }
		}, {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			events : operateEventsKnowledge,
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<button id='knowledgeDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
}

//操作知識庫頁面
function knowledgeSetting() {
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

//打開新頁面
function open_page(name,part_code,version,typename,id){
	var win = window.open("/YS-PDMS/index.action#"+name)
	localStorage.setItem('part_code', part_code);
	localStorage.setItem('archives_version', version);
	localStorage.setItem(typename, id);
}







//上階查詢頁面使用
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
            	status:5,
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
			events : operateEventsKnowledge,
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
			field : 'status',
			title : '申請狀態',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				var rs = "未知";
				switch(value){
				case "1":
					rs = "待審核";
					break;
				case "2":
					rs = "取回重辦";
					break;
				case "3":
					rs = "退回重辦";
					break;
				case "4":
					rs = "已取消";
					break;
				case "5":
					rs = "已發行";
					break;
				case "6":
					rs = "已廢止";
					break;
				}
	            return rs;
		    }
		}, {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			events : operateEventsKnowledge,
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
			events : operateEventsKnowledge,
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<button id='knowledgeDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
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
			field : 'status',
			title : '申請狀態',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				var rs = "未知";
				switch(value){
				case "1":
					rs = "待審核";
					break;
				case "2":
					rs = "取回重辦";
					break;
				case "3":
					rs = "退回重辦";
					break;
				case "4":
					rs = "已取消";
					break;
				case "5":
					rs = "已發行";
					break;
				case "6":
					rs = "已廢止";
					break;
				}
	            return rs;
		    }
		}, {
			field : 'operation',
			title : '查看',
			valign : 'middle',
			align : 'center',
			events : operateEventsKnowledge,
			formatter:function(value,row,index){
				console.log("123",value,row,index)
	            return [
	            	"<button id='knowledgePartDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
	            ].join("");
		    }
		}]
	});
}
function loadAllKnowledge(){
	var status = $('#knowledgeStatus').val();
	console.log(status);
	loadKnowledge(status)
}
function loadKnowledgePart(){
	var status = $('#knowledgePartStatus').val();
	console.log(status);
	loadProcessedPart(status)
}

function loadKnowledgeLog(){
	var table = $("#contentDept");
	table.bootstrapTable('destroy');
	table.empty();
	table.fadeIn(100);
	table.bootstrapTable({
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		url: "knowledge/findKnowledgeLevelupLog.action",
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
            	part_type:$('#knowledgeType').val(),
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
			field : 'part_type',
			title : '類型',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				var rs = "";
				switch(value){
				case "part":
					rs = "分階";
					break;
				case "knowledge":
					rs = "成品";
					break;
				}
				return rs;
		    }
		}, {
			field : 'part_code',
			title : '料號',
			valign : 'middle',
			align : 'center'
		}, {
			field : 'version',
			title : '版本',
			valign : 'middle',
			align : 'center',
		},{
			field : 'member_id',
			title : '申請人',
			valign : 'middle',
			align : 'center',
		}, {
			field : 'reg_time',
			title : '申請時間',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				var date = new Date(value.time);
				var Y = date.getFullYear()+'-';
				var M = (date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-';
				var D = date.getDate();
				return Y+M+D;
		    }
		}, {
			field : 'tips',
			title : '原因',
			valign : 'middle',
			align : 'center'
		}, {
			field : 'approve_time',
			title : '發行時間',
			valign : 'middle',
			align : 'center',
			formatter:function(value,row,index){
				if(value){
					var date = new Date(value.time);
					var Y = date.getFullYear()+'-';
					var M = (date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-';
					var D = date.getDate();					
					return Y+M+D;
				}
		    }
//		}, {
//			field : 'operation',
//			title : '查看',
//			valign : 'middle',
//			align : 'center',
//			events : operateEvents,
//			formatter:function(value,row,index){
//				console.log("123",value,row,index)
//	            return [
//	            	"<button id='knowledgePartDetail' class='btn btn-primary' href='#' title='查看'>查看</button>"
//	            ].join("");
//		    }
		}]
	});
}






window.operateEventsKnowledge = {
	// 打開分階詳情
	"click #knowledgePartDetail" : function(e, value, row, index) {
		console.log(123)
		//如果flag和title兩個一起查詢可以查到的話，如果action_id不同，則不允許修改權限資料
		$("#showUpdateAction .modal-dialog .modal-content .modal-header .modal-title").text("查詢")
		//$("#modalActionFlag").attr("disabled", true);
		$("#modalActionFlag").val("");
		$("#modalActionTitle").val("");
		$("#modalActionLockedDiv").fadeIn(10);
		$("#btnUpdateAction").fadeIn(10);
		$("#btnAddAction").fadeOut(10);
		var id = row.id;
		var part_code = row.part_code;
		var version = row.version;
		open_page("knowledgePartDetail",part_code,version,"knowledge_part_id",id)
	},
	// 打開成品詳情
	"click #knowledgeDetail" : function(e, value, row, index) {
		//如果flag和title兩個一起查詢可以查到的話，如果action_id不同，則不允許修改權限資料
		$("#showUpdateAction .modal-dialog .modal-content .modal-header .modal-title").text("查詢")
		//$("#modalActionFlag").attr("disabled", true);
		$("#modalActionFlag").val("");
		$("#modalActionTitle").val("");
		$("#modalActionLockedDiv").fadeIn(10);
		$("#btnUpdateAction").fadeIn(10);
		$("#btnAddAction").fadeOut(10);
		var id = row.id;
		if(row.knowledge_id) id = row.knowledge_id;
		var part_code = row.part_code;
		var version = row.version;
		open_page("knowledgeDetail",part_code,version,"knowledge_id",id)
	},
}
