var sessionRoles;
$(document).ready(function() {
	
	getActiveUserRolesActions();
	
	var page_name = get_url_params();
	//console.log("page_name : " + page_name);
	if (page_name != null && page_name != "") {
		change_page2(page_name);
	}

	// 给所有ajax定义全局方法
	$.ajaxSetup({
		complete : function(XMLHttpRequest, textStatus) {
			var status = XMLHttpRequest.status;
			if((status == 500 || status == "500") && XMLHttpRequest.responseText.indexOf("用户登录") >= 0){
				alert("登录超时！或此账号在其它地方登录！请重新登录！");
				//window.location.href = 'login';
				window.location = "index.action";
				return false;
			}
			/*
			if (textStatus == "parsererror") {
				alert("登錄超時！請重新登錄！");
				//window.location.href = 'login';
				window.location = "index.action";
			}
			
			else if (textStatus == "error") {
				alert("請求超時！請稍後再試！");
			}
			*/
		}
	})
	
	$('.inactive').click(function() {
		openMenuBut(this);
	});
	
	// 禁止修改密码的form提交并刷新页面
	$("#modalFormUpdatePassword").submit(function() {
		return false;
	});
	
	// 导航栏按钮绑定点击事件
	$("#btn_member").on("click", function() {
		change_page('member');
	});
	$("#btn_role").on("click", function() {
		change_page('role');
	});
	$("#btn_action").on("click", function() {
		change_page('action');
	});
	$("#btn_type").on("click", function() {
		change_page('type');
	});
	$("#btn_dept").on("click", function() {
		change_page('dept');
	});
	$("#btn_partInfo").on("click", function() {
		change_page('partinfo');
	});
	
	$("#btn_await").on("click", function() {
		change_page('await');
	});
	$("#btn_checkLog").on("click", function() {
		change_page('checkLog');
	});
	$("#btn_bom").on("click", function() {
		change_page('bom');
	});
});

// 獲得登錄的member_id對應的Roles，以便後面的js函數中調用
function getActiveUserRolesActions(){
	$.ajax({
		url : "member/getActiveUserRolesActions.action",
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

function get_url_params() { // 获取url里面的id参数
	var w_t = window.location.href.split('#');
	if (w_t.length > 1) {
		var len = w_t.length - 1;
		var w_id = w_t[len];
		return w_id;
	} else {
		return "";
	}
}

// 改變左側工具欄中點選按鈕的背景色
function change_bg(obj) {
	var a = document.getElementsByClassName("navbar-side")[0].getElementsByTagName("a");
	for (var i = 0; i < a.length; i++) {
		// 只需要將current的類名清空，然後把當前標籤類名改為current即可實現current類名轉移
		if (a[i].className == "current") {
			a[i].className = "";
		}
	}
	obj.className = "current";
	obj.addClass("current");
}

// 根據對象打開其下階清單
function openMenuBut(obj) {
	// 遍歷本點選的元素a之所有同胞ul，判斷其顯示狀態是否為none
	if ($(obj).siblings('ul').css('display') == 'none') {
		// 如果是的話，就意味著本階的下階菜單處於隱藏狀態
		// 控制自身變成-號
		$(obj).addClass('inactives');
		// 控制自身菜單下子菜單顯示
		$(obj).siblings('ul').slideDown(100);
	} else {
		// 控制自身变成+号
		$(obj).removeClass('inactives');
		// 控制自身菜单下子菜单隐藏
		$(obj).siblings('ul').slideUp(100);
	}
}

// 根據對象打開其下階清單
function openMenuBut2(obj) {
	// 控制自身變成-號
	$(obj).addClass('inactives');
	// 控制自身菜單下子菜單顯示
	$(obj).siblings('ul').slideDown(100);
}

// 根據名稱載入對應頁面的設置
function loadPageSetting(name) {
	switch (name) {
		case "action" :
			actionSetting();
			loadAction('1');
			break;
		case "role" :
			roleSetting();
			loadRole('1');
			break;
		case "member" :
			memberSetting();
			loadMember('1');
			break;
		case "type" :
			typeSetting();
			loadType('1');
			break;
		case "dept" :
			deptSetting();
			loadDept('1');
			break;
		case "partinfo" :
			partInfoSetting();
			loadPartInfo('1');
			break;
		case "await" :
			awaitSetting();
			loadAwait('2');
			break;
		case "checkLog" :
			checkLogSetting();
			break;
		case "bom" :
			bomSetting();
			break;
	}
}

function change_page2(name) {
	change_bg($("#btn_" + name));
	
	var menuButObj = $($("#btn_" + name)).parent().parent().parent().children("a");
	openMenuBut2(menuButObj);
	
	$(".container-fluid .page").empty();
	$(".container-fluid .page").load("pages/sub-pages/" + name + ".jsp",function(){
		loadPageSetting(name)
	});
}

// 左側工具欄按鈕點選後載入頁面
function change_page(name) {
	change_bg($("#btn_" + name));
	//location.replace("index.action?page_name=" + name);
	window.location.href = "#" + name;
	
	var menuButObj = $($("#btn_" + name)).parent().parent().parent().children("a");
	//console.log(menuButObj.text())
	openMenuBut2(menuButObj);
	//console.log($($("#btn_" + name)).parent().parent().parent().children("a").text());
	
	$(".container-fluid .page").empty();
	$(".container-fluid .page").load("pages/sub-pages/" + name + ".jsp",function(){
		loadPageSetting(name)
	});
}

// 顯示隱藏左側菜單控制函數
function navbar_hide_visible() {
	$("#navbar-bg").toggle(100);
	if ($("#main-content").css("margin-left") == "167px") {
		// $("#main-content").css("margin-left","1px");
		$("#main-content").animate({
			"margin-left" : "1px"
		}, 100);
	} else {
		// $("#main-content").css("margin-left","167px");
		$("#main-content").animate({
			"margin-left" : "167px"
		}, 100);
	}
}

/****************************** 系統激活 *********************************/

// 系統激活頁面設置
function registerSetting(){
	$("#formSystemRegister").submit(function() {
		return false;
	});
}

// 檢查系統是否已經激活
function checkRegister(){
	$.ajax({
		url : "SystemRegisterAction/checkRegister.action",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.flag == true) {
				$("#validity_date").val(data.validity_date);
				$("#register_publicKey_div").fadeOut(100);
				$("#register_code_div").fadeOut(100);
				operateAlertSmall(true, data.msg, "");
			} else {
				$("#validity_date").val(data.validity_date);
				operateAlertSmall(false, "", data.msg);
			}
		}
	})
}

// 系統激活
function registerSystem() {
	var registerCode = $("#register_code").val();
	var publicKey = $("#register_publicKey").val();
	if (registerCode == null || publicKey == null || registerCode == "" || publicKey == "") {
		$("#registerMsg").text("公钥或注册码未填写完整！");
		return false;
	}
	$.ajax({
		url : "SystemRegisterAction/registerSystem.action",
		type : "post",
		dataType : "json",
		data : {
			registerCode : registerCode,
			publicKey : publicKey
		},
		success : function(data) {
			if (data.flag == true) {
				$("#validity_date").val(data.validity_date);
				$("#register_publicKey_div").fadeOut(100);
				$("#register_code_div").fadeOut(100);
				operateAlertSmall(true, data.msg, "");
			} else {
				operateAlertSmall(false, "", data.msg);
			}
		}
	})
}

/****************************** 權限操作 *********************************/

//驗證flag對應的權限是否存在
function checkActionFlag(flag,actionMsg){
	if(flag=="" || flag==null){
		$("#"+actionMsg).text("权限代号不允许为空！");
	}else{
		$("#"+actionMsg).text("");
		$.ajax({
			type : "POST",
			url : "action/checkActionFlag.action",
			dataType : "json",
			data : {
				flag : flag
			},
			success : function(data) {
				if (data == true) {
					//如果沒有找到flag對應的權限，會返回true，反之返回false
					$("#"+actionMsg).text("");
					return true;
				} else {
					$("#"+actionMsg).text("此权限代号已经存在！");
					return false;
				}
			}
		});
	}
}

//驗證title對應的權限是否存在
function checkActionTitle(title,actionMsg){
	if(title=="" || title==null){
		$("#"+actionMsg).text("权限名称不允许为空！");
	}else{
		$("#"+actionMsg).text("");
		$.ajax({
			type : "POST",
			url : "action/checkActionTitle.action",
			dataType : "json",
			data : {
				title : title
			},
			success : function(data) {
				if (data == true) {
					//如果沒有找到title對應的權限，會返回true，反之返回false
					$("#"+actionMsg).text("");
					return true;
				} else {
					$("#"+actionMsg).text("此权限名称已经存在！");
					return false;
				}
			}
		});
	}
}

//新增權限
function addAction() {
	var flag = $("#modalActionFlag").val();
	var title = $("#modalActionTitle").val();
	if (flag == "" || title == "") {
		$("#modalActionMsg").text("权限资料未填写完整！");
		return false;
	} else {
		if (flag.length > 20) {
			$("#modalActionMsg").text("权限代号大于20个字符！");
			return false;
		}
		if (title.length > 20) {
			$("#modalActionMsg").text("权限名称大于20个字符！");
			return false;
		}
		$("#modalActionMsg").text("");
		$.ajax({
			type : "POST",
			url : "action/add.action",
			dataType : "json",
			data : {
				flag : flag,
				title : title
			},
			success : function(data) {
				if (data == true) {
					$("#modalActionFlag").val("");
					$("#modalActionTitle").val("");
					operateAlertSmall(true, "新增权限成功！", "");
				} else {
					operateAlertSmall(false, "", "新增权限失败！");
				}
			}
		});
	}
}

// 更新模態框中顯示的權限，因為各組件ID不同，且alert塊不同，故使用不同 的方法處理，比較簡單
function updateAction() {
	var action_id = $("#modalActionId").val();
	var flag = $("#modalActionFlag").val();
	var title = $("#modalActionTitle").val();
	var locked;
	if ($("#modalActionLocked").is(":checked")) {
		locked = "1";
	} else {
		locked = "0";
	}
	if (title == null || title == "") {
		$("#modalActionMsg").text("未输入权限名称！");
		return false;
	} else {
		if (title.length > 20) {
			$("#modalActionMsg").text("权限名称大于20个字符！");
			return false;
		}
		$("#modalActionMsg").text("");
		$.ajax({
			type : "POST",
			url : "action/update.action",
			dataType : "json",
			data : {
				action_id : action_id,
				flag : flag,
				title : title,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					operateAlert(true, "修改权限资料成功！", "");
				} else {
					operateAlert(false, "", "修改权限资料失败！");
				}
				$("#showUpdateAction").modal("hide");
				$("#contentAction").bootstrapTable("refresh");
			}
		})
	}
}

function loadAction(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	var column;
	var keyword;
	switch(searchType){
		case "1":
			url= path + "/action/listUnlockedSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "2":
			url= path + "/action/listSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "3":
			url= path + "/action/listSplit.action";
			column=$("#actionSearchTypeList").val();
			keyword=$("#actionSearchKeyWord").val();
			break;
	}
	//console.log(url);
	var table = $("#contentAction");
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
		exportTypes : ['excel'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allAction', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		/*
		responseHandler : function(data) {
			//console.log(data);
			
			return {
				"rows" : data.list,
				"total" : data.count
			};
			
		},
		*/
		onLoadSuccess : function(data) {
            //console.log(data);
        },
        idField: 'action_id',//主键
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
					field : 'action_id',
					title : 'ID',
					valign : 'middle',
					align : 'center',
					visible : false

				}, {
					field : 'flag',
					title : '权限代号',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'title',
					title : '权限名称',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					events : operateEvents,
					formatter:function(value,row,index){
				        if(value==1){
				            return [
				            	"<a id='lockOrUnlockAction' href='#' title='解锁'><i class='fa fa-lock' style='color:red'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editAction' href='#' title='编辑权限'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }else if(value==0){
				            return [
				            	"<a id='lockOrUnlockAction' href='#' title='锁定'><i class='fa fa-unlock' style='color:green'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editAction' href='#' title='编辑权限'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }
				    }
				}]
	});
}

//顯示新增權限信息的模態框
function showModalAction() {
	//給flag輸入框增加事件，判斷flag是否存在
	document.getElementById("modalActionFlag").addEventListener("blur",function(){
		checkActionFlag($("#modalActionFlag").val(),"modalActionMsg");
	})
	
	//給title輸入框增加事件，判斷title是否存在
	document.getElementById("modalActionTitle").addEventListener("blur",function(){
		checkActionTitle($("#modalActionTitle").val(),"modalActionMsg");
	})
	
	$("#showUpdateAction .modal-dialog .modal-content .modal-header .modal-title").text("新增权限")
	//$("#modalActionFlag").attr("disabled", false);
	$("#modalActionFlag").val("");
	$("#modalActionTitle").val("");
	$("#modalActionLockedDiv").fadeOut(10);
	$("#btnUpdateAction").fadeOut(10);
	$("#btnAddAction").fadeIn(10);

	//顯示模態框
	$("#showUpdateAction").modal("show");
}

//操作權限前先進行的設置處理函數
function actionSetting() {
	// 禁止新增權限的form提交刷新頁面
	$("#modalFormAction").submit(function() {
		return false;
	});

	// 設置更新權限模態框的垂直位置
	$('#showUpdateAction').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateAction').find('.modal-dialog').height();
				return ($(window).height() / 2 - (modalHeight / 2) - 250);
			}
		});
	});
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}

/****************************** 角色操作 *********************************/

//驗證flag對應的角色是否存在
function checkRoleFlag(flag,roleMsg){
	if(flag=="" || flag==null){
		$("#"+roleMsg).text("角色代号不允许为空！");
	}else{
		$("#"+roleMsg).text("");
		$.ajax({
			type : "POST",
			url : "role/checkRoleFlag.action",
			dataType : "json",
			data : {
				flag : flag
			},
			success : function(data) {
				if (data == true) {
					//如果沒有找到flag對應的權限，會返回true，反之返回false
					$("#"+roleMsg).text("");
					return true;
				} else {
					$("#"+roleMsg).text("此角色代号已经存在！");
					return false;
				}
			}
		});
	}
}

//驗證title對應的角色是否存在
function checkRoleTitle(title,roleMsg){
	if(title=="" || title==null){
		$("#"+roleMsg).text("角色名称不允许为空！");
	}else{
		$("#"+roleMsg).text("");
		$.ajax({
			type : "POST",
			url : "role/checkRoleTitle.action",
			dataType : "json",
			data : {
				title : title
			},
			success : function(data) {
				if (data == true) {
					//如果沒有找到title對應的權限，會返回true，反之返回false
					$("#"+roleMsg).text("");
					return true;
				} else {
					$("#"+roleMsg).text("此角色名称已经存在！");
					return false;
				}
			}
		});
	}
}

//新增角色
function addRole() {
	var flag = $("#modalRoleFlag").val();
	var title = $("#modalRoleTitle").val();
	if (flag == "" || title == "") {
		$("#modalRoleMsg").text("角色资料未填写完整！");
		return false;
	} else {
		if (flag.length > 20) {
			$("#modalRoleMsg").text("角色代号大于20个字符！");
			return false;
		}
		if (title.length > 20) {
			$("#modalRoleMsg").text("角色名称大于20个字符！");
			return false;
		}
		$("#modalRoleMsg").text("");
		var actions = $("#modalActions input[name='modalActionsSelected']");
		var actionsSet = new Array();
		var i=0;
		actions.each(function() {
					if ($(this).is(':checked')) {
						actionsSet[i] = $(this).val();
						i++;
					}
				});
		//console.log(actionsSet);
		$.ajax({
			type : "POST",
			url : "role/add.action",
			dataType : "json",
			data : {
				flag : flag,
				title : title,
				actionsSet : actionsSet
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					$("#modalRoleFlag").val("");
					$("#modalRoleTitle").val("");
					operateAlertSmall(true, "新增角色成功！", "");
					actions.each(function() {
						if ($(this).is(':checked')) {
							$(this).prop("checked", "");
						}
					});
				} else {
					operateAlertSmall(false, "", "新增角色失败！");
				}
			}
		});
	}
}

// 更新模態框中顯示的角色，因為各組件ID不同，且alert塊不同，故使用不同 的方法處理，比較簡單
function updateRole() {
	var role_id = $("#modalRoleId").val();
	var flag = $("#modalRoleFlag").val();
	var title = $("#modalRoleTitle").val();
	var locked;
	if ($("#modalRoleLocked").is(":checked")) {
		locked = "1";
	} else {
		locked = "0";
	}
	if (title == null || title == "") {
		$("#modalRoleMsg").text("未输入角色名称！");
		return false;
	} else {
		if (title.length > 20) {
			$("#modalRoleMsg").text("角色名称大于20个字符！");
			return false;
		}
		$("#modalRoleMsg").text("");
		var actions = $("#modalActions input[name='modalActionsSelected']");
		var actionsSet = new Array();
		var i=0;
		actions.each(function() {
					if ($(this).is(':checked')) {
						actionsSet[i] = $(this).val();
						i++;
					}
				});
		//console.log(actionsSet);
		$.ajax({
			type : "POST",
			url : "role/update.action",
			dataType : "json",
			data : {
				role_id : role_id,
				flag : flag,
				title : title,
				locked : locked,
				actionsSet : actionsSet
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					operateAlert(true, "修改角色资料成功！", "");
				} else {
					operateAlert(false, "", "修改角色资料失败！");
				}
				$("#showUpdateRole").modal("hide");
				$("#contentRole").bootstrapTable("refresh");
			}
		})
	}
}

function loadRole(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	var column;
	var keyword;
	switch(searchType){
		case "1":
			url="role/listUnlockedSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "2":
			url="role/listSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "3":
			url="role/listSplit.action";
			if ($('#roleSearchTypeList').val() == "action_flag" || $('#roleSearchTypeList').val() == "action_title") {
				column = "action_id" ;
				keyword = $("#actionSearchRoleList").val();
			} else {
				column = $("#roleSearchTypeList").val();
				keyword = $("#roleSearchKeyWord").val();
			}
			break;
	}
	//console.log(url);
	var table = $("#contentRole");
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
		onLoadSuccess : function(data) {
            //console.log(data);
        },
        idField: 'role_id',//主键
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
					field : 'role_id',
					title : 'ID',
					valign : 'middle',
					align : 'center',
					visible : false

				}, {
					field : 'flag',
					title : '角色代号',
					valign : 'middle',
					align : 'center',
					width: 100
				}, {
					field : 'title',
					title : '角色名称',
					valign : 'middle',
					align : 'center',
					width: 100
				}, {
					title : '对应权限',
					valign : 'middle',
					halign : 'center',
					align : 'left',
					formatter: function (value, row, index) {
						//console.log(row.roleAction.length);
						var actionList="";
						if(row.roleAction.length>0){
							for (var j in row.roleAction) {
								if (j == 0) {
									actionList = row.roleAction[j].title
								} else {
									actionList = actionList + " ; " + row.roleAction[j].title
								}
							}
						}
						return actionList;
					}
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					width: 100,
					events : operateEvents,
					formatter:function(value,row,index){
				        if(value==1){
				            return [
				            	"<a id='lockOrUnlockRole' href='#' title='解锁'><i class='fa fa-lock' style='color:red'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editRole' href='#' title='编辑角色'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }else if(value==0){
				            return [
				            	"<a id='lockOrUnlockRole' href='#' title='锁定'><i class='fa fa-unlock' style='color:green'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editRole' href='#' title='编辑角色'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }
				    }
				}]
	});
}


//顯示修改角色信息的模態框
function showModalRole() {
	
	// 給flag輸入框增加事件，判斷flag是否存在
	document.getElementById("modalRoleFlag").addEventListener("blur",function(){
		checkRoleFlag($("#modalRoleFlag").val(),"modalRoleMsg");
	})
	
	// 給title輸入框增加事件，判斷title是否存在
	document.getElementById("modalRoleTitle").addEventListener("blur",function(){
		checkRoleTitle($("#modalRoleTitle").val(),"modalRoleMsg");
	})
	
	$("#showUpdateRole .modal-dialog .modal-content .modal-header .modal-title").text("新增角色")
	// 載入所有action供role選擇
	getAllUnlockedList2("action", "modalActions", "action_id", "title", "modalActionsSelected");
	//$("#modalRoleFlag").attr("disabled", false);
	$("#modalRoleFlag").val("");
	$("#modalRoleTitle").val("");
	$("#modalRoleLockedDiv").fadeOut(10);
	$("#btnUpdateRole").fadeOut(10);
	$("#btnAddRole").fadeIn(10);
	
	//顯示模態框
	$("#showUpdateRole").modal("show");
	
}

//新增角色前先進行的設置處理函數
function roleSetting(){
	// 禁止新增權限的form提交刷新頁面
	$("#modalFormRole").submit(function() {
		return false;
	});
	
	// 設置搜索選擇框控件的綁定事件
	$('#roleSearchTypeList').on('change', function(e) {
		if ($('#roleSearchTypeList').val() == "action_flag" || $('#roleSearchTypeList').val() == "action_title") {
			$("#roleSearchKeyWord").fadeOut(10);
			$("#actionSearchRoleList").fadeIn(10);
			if($('#roleSearchTypeList').val() == "action_flag"){
				getAllUnlockedList("action", "actionSearchRoleList", "action_id", "flag", 0);
			}else if($('#roleSearchTypeList').val() == "action_title"){
				getAllUnlockedList("action", "actionSearchRoleList", "action_id", "title", 0);
			}
		} else {
			$("#roleSearchKeyWord").fadeIn(10);
			$("#actionSearchRoleList").fadeOut(10);
		}
	});
	
	//設置更新角色模態框的垂直位置
	$('#showUpdateRole').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateRole').find('.modal-dialog').height();
				//console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) -130);
			}
		});
	});
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}

/****************************** 人員操作 *********************************/

//驗證member_id對應的人員是否存在
function checkMemberId(member_id,memberMsg){
	if(member_id=="" || member_id==null){
		$("#"+memberMsg).text("人员工号不允许为空！");
	}else{
		$("#"+memberMsg).text("");
		$.ajax({
			type : "POST",
			url : "member/checkMemberId.action",
			dataType : "json",
			data : {
				member_id : member_id
			},
			success : function(data) {
				if (data == true) {
					//如果沒有找到flag對應的權限，會返回true，反之返回false
					$("#"+memberMsg).text("");
					return true;
				} else {
					$("#"+memberMsg).text("此人员工号已经存在！");
					return false;
				}
			}
		});
	}
}

//新增人員
function addMember() {
	var member_id = $("#modalMemberId").val();
	var member_name = $("#modalMemberName").val();
	if (member_id == "" || member_name == "") {
		$("#modalMemberMsg").text("人员资料未填写完整！");
		return false;
	} else {
		if (member_id.length > 6) {
			$("#modalMemberMsg").text("人员工号大于6个字符！");
			return false;
		}
		if (member_name.length > 10) {
			$("#modalMemberMsg").text("人员姓名大于10个字符！");
			return false;
		}
		$("#modalMemberMsg").text("");
		var depts = $("#modalDepts input[name='modalDeptsSelected']");
		var roles = $("#modalRoles input[name='modalRolesSelected']");
		var deptsSet = new Array();
		var rolesSet = new Array();
		var i=0;
		depts.each(function() {
			if ($(this).is(':checked')) {
				deptsSet[i] = $(this).val();
				i++;
			}
		});
		//console.log(deptsSet.length==0);
		i=0;
		roles.each(function() {
			if ($(this).is(':checked')) {
				rolesSet[i] = $(this).val();
				i++;
			}
		});
		//console.log(rolesSet.length==0);
		$.ajax({
			type : "POST",
			url : "member/add.action",
			dataType : "json",
			data : {
				member_id : member_id,
				member_name : member_name,
				deptsSet : deptsSet,
				rolesSet : rolesSet
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					$("#modalMemberId").val("");
					$("#modalMemberName").val("");
					operateAlertSmall(true, "新增人员成功！", "");
					depts.each(function() {
						if ($(this).is(':checked')) {
							$(this).prop("checked", "");
						}
					});
					roles.each(function() {
						if ($(this).is(':checked')) {
							$(this).prop("checked", "");
						}
					});
				} else {
					operateAlertSmall(false, "", "新增人员失败！");
				}
			}
		});
	}
}

// 更新模態框中顯示的人員，因為各組件ID不同，且alert塊不同，故使用不同 的方法處理，比較簡單
function updateMember() {
	var member_id = $("#modalMemberId").val();
	var member_name = $("#modalMemberName").val();
	var locked;
	if ($("#modalMemberLocked").is(":checked")) {
		locked = "1";
	} else {
		locked = "0";
	}
	if (member_name == null || member_name == "") {
		$("#modalMemberMsg").text("未输入人员姓名！");
		return false;
	} else {
		if (member_name.length > 10) {
			$("#modalMemberMsg").text("人员姓名大于10个字符！");
			return false;
		}
		$("#modalMemberMsg").text("");
		var depts = $("#modalDepts input[name='modalDeptsSelected']");
		var groups = $("#modalGroups input[name='modalGroupsSelected']");
		var roles = $("#modalRoles input[name='modalRolesSelected']");
		var deptsSet = new Array();
		var groupsSet = new Array();
		var rolesSet = new Array();
		var i=0;
		depts.each(function() {
			if ($(this).is(':checked')) {
				deptsSet[i] = $(this).val();
				i++;
			}
		});
		//console.log(deptsSet.length==0);
		i=0;
		roles.each(function() {
			if ($(this).is(':checked')) {
				rolesSet[i] = $(this).val();
				i++;
			}
		});
		//console.log(rolesSet.length==0);
		$.ajax({
			type : "POST",
			url : "member/update.action",
			dataType : "json",
			data : {
				member_id : member_id,
				member_name : member_name,
				locked : locked,
				deptsSet : deptsSet,
				rolesSet : rolesSet
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					operateAlert(true, "修改人员资料成功！", "");
				} else {
					operateAlert(false, "", "修改人员资料失败！");
				}
				$("#showUpdateMember").modal("hide");
				$("#contentMember").bootstrapTable("refresh");
			}
		})
	}
}

// 管理員修改人員密碼
function updateModalMemberPassword() {
	var member_id = $("#modalMemberPasswordId").val();
	var new_password = $("#modalMemberPasswordNew").val();
	var setType = 1;
	$("#memberModalPasswordMsg").text("");
	$.ajax({
		type : "POST",
		url : "member/updatePassword.action",
		dataType : "json",
		data : {
			member_id : member_id,
			new_password : new_password,
			setType : setType
		},
		traditional : true,
		success : function(data) {
			if (data == true) {
				operateAlert(true, "修改密码成功！", "");
			} else {
				operateAlert(false, "", "修改密码失败！");
			}
			$("#showUpdateMemberPassword").modal("hide");
			$("#contentMember").bootstrapTable("refresh");
		}
	})
}

// 人員自己修改密碼
function updatePassword() {
	var member_id = $("#modalPassMemberId").val();
	var old_password = $("#modalOldPassword").val();
	var new_password = $("#modalNewPassword").val();
	var confirm_password = $("#modalConfirmNewPassword").val();
	var setType = 2;
	if(confirm_password==new_password){
		$.ajax({
			type : "POST",
			url : "member/updatePassword.action",
			dataType : "json",
			data : {
				member_id : member_id,
				old_password : old_password,
				new_password : new_password,
				setType : setType
			},
			traditional : true,
			success : function(data) {
				if (data == true) {
					operateAlertSmall6(true, "修改密码成功！", "");
				} else {
					operateAlertSmall6(false, "", "修改密码失败！");
				}
			}
		})
	}else{
		$("#memberPasswordModalMsg").text("两次输入新密码不同！");
		return false;
	}
}

function loadMember(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var path = (window.location.href).substring(0,(window.location.href).lastIndexOf("\/"));
	//console.log(path);
	var url;
	var column;
	var keyword;
	switch(searchType){
		case "1":
			url="member/listUnlockedSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "2":
			url="member/listSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "3":
			url="member/listSplit.action";
			if ($('#memberSearchTypeList').val() == "dept_name") {
				column = "dept_id";
				keyword = $("#DeptGroupRoleSearchMemberList").val();
			} else if ($('#memberSearchTypeList').val() == "group_name") {
				column = "group_id";
				keyword = $("#DeptGroupRoleSearchMemberList").val();
			} else if ($('#memberSearchTypeList').val() == "role_title") {
				column = "role_id";
				keyword = $("#DeptGroupRoleSearchMemberList").val();
			} else {
				column = $("#memberSearchTypeList").val();
				keyword = $("#memberSearchKeyWord").val();
			}
			break;
	}
	//console.log(url);
	var table = $("#contentMember");
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
			fileName : 'allMember', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
            //console.log(data);
        },
        idField: 'role_id',//主键
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
					field : 'member_id',
					title : '人员工号',
					valign : 'middle',
					align : 'center',
					width: 100

				}, {
					field : 'member_name',
					title : '姓名',
					valign : 'middle',
					align : 'center',
					width: 100
				}, {
					field : 'reg_date',
					title : '建立时间',
					valign : 'middle',
					align : 'center',
					width: 200,
					formatter: function (value, row, index) {
						return new Date(value).format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					title : '对应部门',
					valign : 'middle',
					halign : 'center',
					align : 'left',
					formatter: function (value, row, index) {
						var deptList="";
						if(row.depts.length>0){
							for (var j in row.depts) {
								if (j == 0) {
									deptList = row.depts[j].dept_name
								} else {
									deptList = deptList + " ; " + row.depts[j].dept_name
								}
							}
						}
						return deptList;
					}
				}, {
					title : '对应角色',
					valign : 'middle',
					halign : 'center',
					align : 'left',
					formatter: function (value, row, index) {
						//console.log(row.roles.length);
						var roleList="";
						if(row.roles.length>0){
							for (var j in row.roles) {
								if (j == 0) {
									roleList = row.roles[j].title
								} else {
									roleList = roleList + " ; " + row.roles[j].title
								}
							}
						}
						return roleList;
					}
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					width: 100,
					events : operateEvents,
					formatter:function(value,row,index){
				        if(value==1){
				            return [
				            	"<a id='lockOrUnlockMember' href='#' title='解锁'><i class='fa fa-lock' style='color:red'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editMember' href='#' title='编辑人员资料'><i class='fa fa-pencil'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='updateMemberPassword' href='#' title='修改密码'><i class='fa fa-product-hunt'></i></a>"
				            ].join("");
				        }else if(value==0){
				            return [
				            	"<a id='lockOrUnlockMember' href='#' title='锁定'><i class='fa fa-unlock' style='color:green'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editMember' href='#' title='编辑人员资料'><i class='fa fa-pencil'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='updateMemberPassword' href='#' title='修改密码'><i class='fa fa-product-hunt'></i></a>"
				            ].join("");
				        }
				    }
				}]
	});
}

//顯示修改人員信息的模態框
function showModalMember() {
	$("#showUpdateMember .modal-dialog .modal-content .modal-header .modal-title").text("新增人员")
	$("#modalMemberId").attr("disabled","");
	$("#modalMemberId").prop("disabled",false);
	//抓取member_id
	$("#modalMemberId").val("");
	//抓取member_name
	$("#modalMemberName").val("");
	$("#modalMemberLockedDiv").fadeOut(10);
	$("#btnUpdateMember").fadeOut(10);
	$("#btnAddMember").fadeIn(10);
	//清空異常提示文字
	$("#memberModalMsg").text("");
	
	// 給member_id輸入框增加事件，判斷member_id是否存在
	document.getElementById("modalMemberId").addEventListener("blur",function(){
		checkMemberId($("#modalMemberId").val(),"modalMemberMsg");
	})
	
	// 載入所有部門供人員選擇
	getAllUnlockedList2("dept", "modalDepts", "dept_id", "dept_name", "modalDeptsSelected");
	// 載入所有角色供人員選擇
	getAllUnlockedList2("role", "modalRoles", "role_id", "title", "modalRolesSelected");
	
	//顯示模態框
	$("#showUpdateMember").modal("show");
}

//顯示管理員修改人員密碼的模態框
function showUpdateMemberPassword(obj) {
	//抓取member_id
	$("#modalMemberPasswordId").val($(obj).parent().attr("id"));
	var member_id=$(obj).parent().attr("id");
	//抓取member_name
	$("#modalMemberPasswordName").val($("#" + member_id + "-member_name").text());
	//新密碼
	$("#modalMemberPasswordNew").val("");
	//清空異常提示文字
	$("#memberModalMsg").text("");
	//顯示模態框
	$("#showUpdateMemberPassword").modal("show");
}

//顯示人員自己修改密碼的模態框
function showModalPassword() {
	//抓取member_id
	$("#modalPassMemberId").val(member_id);
	
	$("#modalOldPassword").val("");
	$("#modalNewPassword").val("");
	$("#modalConfirmNewPassword").val("");
	
	//清空異常提示文字
	$("#memberPasswordModalMsg").text("");
	//顯示模態框
	$("#showUpdatePassword").modal("show");
}

// 鎖定或解鎖人員
function lockOrUnlockMember(obj) {
	//抓取member_id
	var member_id=$(obj).parent().attr("id");
	//抓取member_name
	var member_name=$("#" + member_id + "-member_name").text();
	//抓取locked
	var lockedText = $(obj).text();
	var locked;
	if(lockedText=="锁定"){
		//如果顯示鎖定按鈕，則說明未鎖定，則將locked設置為鎖定1，以便鎖定
		locked="1";
	}else if(lockedText=="解锁"){
		//如果顯示解鎖按鈕，則說明已經鎖定，則將locked設置為未鎖定0，以便解鎖
		locked="0";
	}

	$.ajax({
		type : "POST",
		url : "member/update.action",
		dataType : "json",
		data : {
			member_id : member_id,
			member_name : member_name,
			locked : locked
		},
		success : function(data) {
			if (data == true) {
				if(locked=="1"){
					operateAlert(true, "人员锁定成功！", "");
				}else{
					operateAlert(true, "人员解锁成功！", "");
				}
				
			} else {
				operateAlert(false, "", "操作失败！");
			}
		}
	});
}

//新增人員前先進行的設置處理函數
function memberSetting(){
	// 禁止模態框中更新人員的form提交刷新頁面
	$('#modalFormMember').submit(function() {
		return false;
	});
	
	// 禁止模態框中更新人員的form提交刷新頁面
	$('#modalFormUpdateMemberPassword').submit(function() {
		return false;
	});
	
	//設置更新人員模態框的垂直位置
	$('#showUpdateMember').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateMember').find('.modal-dialog').height();
				//console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) -300);
			}
		});
	});
	
	//設置更新人員密碼模態框的垂直位置
	$('#showUpdateMemberPassword').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateMemberPassword').find('.modal-dialog').height();
				//console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) -200);
			}
		});
	});
	
	// 設置搜索選擇框控件的綁定事件
	$('#memberSearchTypeList').on('change', function(e) {
		if ($('#memberSearchTypeList').val() == "dept_name" || $('#memberSearchTypeList').val() == "group_name" || $('#memberSearchTypeList').val() == "role_title") {
			$("#memberSearchKeyWord").fadeOut(10);
			$("#DeptGroupRoleSearchMemberList").fadeIn(10);
			if($('#memberSearchTypeList').val() == "dept_name"){
				getAllUnlockedList("dept", "DeptGroupRoleSearchMemberList", "dept_id", "dept_name", 0);
			}else if($('#memberSearchTypeList').val() == "group_name"){
				getAllUnlockedList("group", "DeptGroupRoleSearchMemberList", "group_id", "group_name", 0);
			}else if($('#memberSearchTypeList').val() == "role_title"){
				getAllUnlockedList("role", "DeptGroupRoleSearchMemberList", "role_id", "title", 0);
			}
		} else {
			$("#memberSearchKeyWord").fadeIn(10);
			$("#DeptGroupRoleSearchMemberList").fadeOut(10);
		}
	});
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}

/****************************** 類型操作 *********************************/

//验证父属性是否已存在
function checkFirstParent(parent_type, modalTypeMsg){
	if(parent_type=="" || parent_type==null){
		$("#"+modalTypeMsg).text("资料不允许为空！");
	}else{
		$("#"+modalTypeMsg).text("");
		$.ajax({
			type : "POST",
			url : "type/checkFirstParent.action",
			dataType : "json",
			data : {
				parent_type : parent_type,
			},
			success : function(data) {
				if (data == true) {
					// 如果没有找到parent_type,sub_type对应的类型，会返回true，反正返回false
					$("#" + modalTypeMsg).text("");
					return true;
				} else {
					$("#" + modalTypeMsg).text("此初代父类型已经存在！");
					return false;
				}
			}
		});
	}
}

//驗證parent_type,sub_type對應的類型是否存在
function checkParentAndSubAndUpper(parent_type, sub_type,upper_id, modalTypeMsg){
	if(parent_type=="" || parent_type==null || sub_type=="" || sub_type==null||upper_id==null){
		$("#"+modalTypeMsg).text("资料不允许为空！");
	}else{
		$("#"+modalTypeMsg).text("");
		$.ajax({
			type : "POST",
			url : "type/checkParentAndSubAndUpper.action",
			dataType : "json",
			data : {
				parent_type : parent_type,
				sub_type : sub_type,
				upper_id : upper_id
			},
			success : function(data) {
				if (data == true) {
					// 如果没有找到parent_type,sub_type对应的类型，会返回true，反正返回false
					$("#" + modalTypeMsg).text("");
					return true;
				} else {
					$("#" + modalTypeMsg).text("此类型对应关系已存在！");
					return false;
				}
			}
		});
	}
}
function showAllParentType(){
	$.ajax({
		url : "type/listAllParentType.action",
		type : "post",
		dataType : "json",
		scriptCharset : "utf-8",
		success : function(data) {
			var select = $('#modalTypeParentTypeList');
			var list = data.list;
			select.empty();
			for (var i in list) {
				var option = $("<option value=" + list[i].upper_id + ">" + list[i].parent_type + "</option>");
				option.appendTo(select);
			}
			$("#modalTypeParentTypeList").selectpicker('refresh');
		}
	});
	
}

//顯示新增及修改類型的模態框
function showModalType() {
	$("#addOrUseParentTypeDiv").fadeIn(10);
	$("#modalTypeLockedDiv").fadeOut(10);
	$("#btnUpdateType").fadeOut(10);
	$("#btnAddType").fadeIn(10);
	$("#modalParentType").val("");
	$("#modalSubType").val("");
	$("#showUpdateType .modal-dialog .modal-content .modal-header .modal-title").text("新增類型")
	// 清空異常提示文字
	$("#modalTypeMsg").text("");
	showAllParentType();
	// 显示模态框
	$("#showUpdateType").modal("show");
}

//新增類型
function addType() {
	var selectedvalue = $('input[name="addOrUseParentType"]:checked').val();
	var parent_type;
	var upper_id;
	if (selectedvalue == "new") { 
		parent_type=$("#modalParentType").val().trim();
		upper_id=0;
	}else if (selectedvalue == "old") {
		parent_type=$('#modalTypeParentTypeList option:selected').text();
		upper_id=$('#modalTypeParentTypeList').val();
	}
	var sub_type=$("#modalSubType").val().trim();
	
	if (parent_type == "" || sub_type == "") {
		$("#modalTypeMsg").text("类型资料未填写完整！");
		return false;
	} else {
		if (sub_type.length > 20) {
			$("#modalTypeMsg").text("子类型不允许超过20个字符！");
			return false;
		}
		if (selectedvalue == "new"&&parent_type.length > 20) {
			$("#modalTypeMsg").text("父类型不允许超过20个字符！");
			return false;
		}
		$("#modalTypeMsg").text("");

		$.ajax({
			type : "POST",
			url : "type/add.action",
			dataType : "json",
			data : {
				parent_type : parent_type,
				sub_type : sub_type,
				member_id : member_id,
				upper_id:upper_id,
			},
			success : function(data) {
				if (data == true) {
					$("#modalSubType").val("");
					operateAlertSmall(true, "新增类型成功！", "");
				} else {
					operateAlertSmall(false, "", "新增类型失败！");
				}
			}
		});
	}
}

//更新模態框中顯示的類型，因為各組件ID不同，且alert塊不同，故使用不同 的方法處理，比較簡單
function updateType() {
	var type_id = $("#modalTypeId").val();
	var parent_type=$("#modalTypeParentTypeList").val();
	var sub_type=$("#modalChildType").val();
	var locked;
	if ($("#modalTypeLocked").is(":checked")) {
		locked = "1";
	} else {
		locked = "0";
	}
	if (parent_type == "" || sub_type == "") {
		$("#modalTypeMsg").text("類型資料未填寫完整！");
		return false;
	} else {
		if (sub_type.length > 20) {
			$("#modalTypeMsg").text("子類型不允許超過20個字符！");
			return false;
		}
		if (parent_type.length > 20) {
			$("#modalTypeMsg").text("主類型不允許超過20個字符！");
			return false;
		}
		$("#modalTypeMsg").text("");
		$.ajax({
			type : "POST",
			url : "type/update.action",
			dataType : "json",
			data : {
				type_id : type_id,
				parent_type : parent_type,
				sub_type : sub_type,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					operateAlert(true, "修改類型資料成功！", "");
				} else {
					operateAlert(false, "", "修改類型資料失敗！");
				}
				$("#showUpdateType").modal("hide");
				$("#contentType").bootstrapTable("refresh");
			}
		})
	}
}

function loadType(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url;
	var column;
	var keyword;
	switch(searchType){
		case "1":
			url="type/listUnlockedSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "2":
			url="type/listSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "3":
			url="type/listSplit.action";
			column=$("#typeSearchTypeList").val();
			keyword=$("#typeSearchKeyWord").val();
			break;
	}
	//console.log(url);
	var table = $("#contentType");
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
		dataField: "list", // 后端json对应的表格数据key
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
		theadClasses : "thead-dark", // 表头颜色
		showExport : true, // 是否显示导出按钮
		buttonsAlign : "right", // 按钮位置
		exportTypes : ['xlsx'], // 导出文件类型
		exportDataType : "limit",
		exportOptions : {
			// ignoreColumn: [0,0], //忽略某一列的索引
			fileName : 'allTypes', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
          //console.log(data);
      },
      idField: 'type_id',//主键
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
					field : 'id',
					title : 'ID',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'upper_id',
					title : '父关系ID',
					valign : 'middle',
					align : 'center',
					visible : false
				}, {
					field : 'parent_type',
					title : '父類型',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'sub_type',
					title : '子類型',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'member_id',
					title : '建立人员',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'reg_time',
					title : '建立时间',
					valign : 'middle',
					align : 'center',
					formatter:function(value,row,index){
					 return new Date(value.time).format('yyyy-MM-dd hh:mm:ss');
					}
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					events : operateEvents,
					width:60,
					formatter:function(value,row,index){
						if(value==1){
				            return "<a id='lockOrUnlockType' href='#' title='解鎖'><i class='fa fa-lock' style='color:red'></i></a>";
				        }else if(value==0){
				            return "<a id='lockOrUnlockType' href='#' title='鎖定'><i class='fa fa-unlock' style='color:green'></i></a>";
				        }
						/*if(value==1){
				            return [
				            	"<a id='lockOrUnlockType' href='#' title='解鎖'><i class='fa fa-lock' style='color:red'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editType' href='#' title='編輯類型'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }else if(value==0){
				            return [
				            	"<a id='lockOrUnlockType' href='#' title='鎖定'><i class='fa fa-unlock' style='color:green'></i></a>&nbsp;&nbsp;&nbsp;",
				            	"<a id='editType' href='#' title='編輯類型'><i class='fa fa-pencil'></i></a>"
				            ].join("");
				        }*/
				    }
				}]
	});
}

//载入类型页面前先进行的设置处理函数
function typeSetting(){
	// 给选择增加类型还是使用主类型radio控件绑定事件
	$("input[name='addOrUseParentType']").bind("click",function(){
		var selectedvalue = $(this).val();
		if (selectedvalue == "new") { 
			$("#modalTypeParentTypeListDiv").fadeOut(5);
			$("#modalTypeParentTypeInputDiv").fadeIn(5);
			$("#modalParentType").val("");
		}else if (selectedvalue == "old") {
			$("#modalTypeParentTypeListDiv").fadeIn(5);
			$("#modalTypeParentTypeInputDiv").fadeOut(5);
			$("#modalTypeParentTypeList").val(0);
			showAllParentType();
		}
	});
	
	// 给parent_type和sub_type输入框增加事件，判断两者结合的类型是否存在
	document.getElementById("modalParentType").addEventListener("blur",function(){
		var selectedvalue = $('input[name="addOrUseParentType"]:checked').val();
		var parent_type;
		var upper_id;
		
		if (selectedvalue == "new") { 
			parent_type=$("#modalParentType").val();
			upper_id=0;
			checkFirstParent(parent_type,"modalTypeMsg");
		}else if (selectedvalue == "old") {
			parent_type=$('#modalTypeParentTypeList option:selected').text();
			
			upper_id=$("#modalTypeParentTypeList").val();
		}
		var sub_type=$("#modalSubType").val();
		checkParentAndSubAndUpper(parent_type,sub_type,upper_id,"modalTypeMsg");
	});
	
	document.getElementById("modalSubType").addEventListener("blur",function(){
		var selectedvalue = $('input[name="addOrUseParentType"]:checked').val();
		var parent_type;
		var upper_id;
		
		if (selectedvalue == "new") { 
			 parent_type=$("#modalParentType").val();
			 upper_id=0;
		}else if (selectedvalue == "old") {
			parent_type=$('#modalTypeParentTypeList option:selected').text();
			
			upper_id=$("#modalTypeParentTypeList").val();
		}
		var sub_type=$("#modalSubType").val();
		checkParentAndSubAndUpper(parent_type,sub_type,upper_id,"modalTypeMsg");
	});

	
	// 禁止模态框中更新类型的form提交刷新页面
	$('#modalFormType').submit(function() {
		return false;
	});

	// 设置模态框的垂直位置
	$('#showUpdateType').on('show.bs.modal', function(e) {
		$(this).find('.modal-dialog').css({
			'margin-top' : function() {
				var modalHeight = $('#showUpdateType').find('.modal-dialog').height();
				// console.log(modalHeight);
				return ($(window).height() / 2 - (modalHeight / 2) - 300);
			}
		});
	});
	
	$("#toolbar").css({
		'padding-top' : 10,
		'padding-left' : 15
	})
}

/****************************** 部門操作 *********************************/

//驗證dept_code對應的部門是否存在
function checkDeptCode(dept_code,deptMsg){
	if(dept_code=="" || dept_code==null){
		$("#"+deptMsg).text("部门代号不允许为空！");
	}else{
		$("#"+deptMsg).text("");
		$.ajax({
			type : "POST",
			url : "dept/checkDeptCode.action",
			dataType : "json",
			data : {
				dept_code : dept_code
			},
			success : function(data) {
				if (data == true) {
					$("#"+deptMsg).text("");
					return true;
				} else {
					$("#"+deptMsg).text("此部门代号已经存在！");
					return false;
				}
			}
		});
	}
}

//驗證dept_name對應的部門是否存在
function checkDeptName(dept_name,deptMsg){
	if(dept_name=="" || dept_name==null){
		$("#"+deptMsg).text("部门名称不允许为空！");
	}else{
		$("#"+deptMsg).text("");
		$.ajax({
			type : "POST",
			url : "dept/checkDeptName.action",
			dataType : "json",
			data : {
				dept_name : dept_name
			},
			success : function(data) {
				if (data == true) {
					$("#"+deptMsg).text("");
					return true;
				} else {
					$("#"+deptMsg).text("此部门名称已经存在！");
					return false;
				}
			}
		});
	}
}

//新增部門
function addDept() {
	var dept_code = $("#modalDeptCode").val();
	var dept_name = $("#modalDeptName").val();
	if (dept_code == "" || dept_name == "") {
		$("#modalDeptMsg").text("部门资料未填写完整！");
		return false;
	} else {
		if (dept_code.length > 7) {
			$("#modalDeptMsg").text("部门代号大于7个字符！");
			return false;
		}
		if (dept_name.length > 10) {
			$("#modalDeptMsg").text("部门名称大于10个字符！");
			return false;
		}
		$("#modalDeptMsg").text("");
		$.ajax({
			type : "POST",
			url : "dept/add.action",
			dataType : "json",
			data : {
				dept_code : dept_code,
				dept_name : dept_name
			},
			success : function(data) {
				if (data == true) {
					$("#modalDeptCode").val("");
					$("#modalDeptName").val("");
					operateAlertSmall(true, "新增部门成功！", "");
				} else {
					operateAlertSmall(false, "", "新增部门失败！");
				}
			}
		});
	}
}

// 更新模態框中顯示的部門，因為各組件ID不同，且alert塊不同，故使用不同 的方法處理，比較簡單
function updateDept() {
	var dept_id = $("#modalDeptId").val();
	var dept_code = $("#modalDeptCode").val();
	var dept_name = $("#modalDeptName").val();
	var locked;
	if ($("#modalDeptLocked").is(":checked")) {
		locked = "1";
	} else {
		locked = "0";
	}
	if (dept_code == "" || dept_name == "") {
		$("#modalDeptMsg").text("部门资料未填写完整！");
		return false;
	} else {
		if (dept_code.length > 7) {
			$("#modalDeptMsg").text("部门代号大于7个字符！");
			return false;
		}
		if (dept_name.length > 10) {
			$("#modalDeptMsg").text("部门名称大于10个字符！");
			return false;
		}
	}
	$("#modalDeptMsg").text("");
	$.ajax({
		type : "POST",
		url : "dept/update.action",
		dataType : "json",
		data : {
			dept_id : dept_id,
			dept_code : dept_code,
			dept_name : dept_name,
			locked : locked
		},
		success : function(data) {
			if (data == true) {
				operateAlert(true, "修改部门资料成功！", "");
			} else {
				operateAlert(false, "", "修改部门资料失败！");
			}
			$("#showUpdateDept").modal("hide");
			$("#contentDept").bootstrapTable("refresh");
		}
	})
}

function loadDept(searchType) {
	$("#toolbar").css("padding-top","");
	$("#toolbar").css("padding-left","");
	var url;
	var column;
	var keyword;
	switch(searchType){
		case "1":
			url= "dept/listUnlockedSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "2":
			url= "dept/listSplit.action";
			column="undefined";
			keyword="undefined";
			break;
		case "3":
			url= "dept/listSplit.action";
			column=$("#deptSearchTypeList").val();
			keyword=$("#deptSearchKeyWord").val();
			break;
	}
	//console.log(url);
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
			fileName : 'allDept', // 文件名称设置
			worksheetName : 'Sheet1', // 表格工作区名称
			tableName : 'DataTable'
		},
		onLoadSuccess : function(data) {
            //console.log(data);
        },
        idField: 'dept_id',//主键
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
					field : 'dept_id',
					title : 'ID',
					valign : 'middle',
					align : 'center',
					visible : false

				}, {
					field : 'dept_code',
					title : '部门代号',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'dept_name',
					title : '部门名称',
					valign : 'middle',
					align : 'center'
				}, {
					field : 'locked',
					title : '操作',
					valign : 'middle',
					align : 'center',
					events : operateEvents,
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

//顯示新增部門信息的模態框
function showModalDept() {
	//給dept_code輸入框增加事件，判斷dept_code是否存在
	document.getElementById("modalDeptCode").addEventListener("blur",function(){
		checkDeptCode($("#modalDeptCode").val(),"modalDeptMsg");
	})
	
	//給dept_name輸入框增加事件，判斷dept_name是否存在
	document.getElementById("modalDeptName").addEventListener("blur",function(){
		checkDeptName($("#modalDeptName").val(),"modalDeptMsg");
	})
	
	$("#showUpdateDept .modal-dialog .modal-content .modal-header .modal-title").text("新增部门")
	$("#modalDeptCode").val("");
	$("#modalDeptName").val("");
	$("#modalDeptLockedDiv").fadeOut(10);
	$("#btnUpdateDept").fadeOut(10);
	$("#btnAddDept").fadeIn(10);

	//顯示模態框
	$("#showUpdateDept").modal("show");
}

//操作部門前先進行的設置處理函數
function deptSetting() {
	// 禁止新增部門的form提交刷新頁面
	$("#modalFormDept").submit(function() {
		return false;
	});

	// 設置更新部門模態框的垂直位置
	$('#showUpdateDept').on('show.bs.modal', function(e) {
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


/****************************** 公共函數 *********************************/

// 獲得所有資料並加入到對應的select下拉清單表單控件中
// type表示要抓取的類別，比如dept，selectName表示對應的select組件ID
function getAllList(type, selectName, valueName, textName, selectedVal) {
	$.ajax({
		url : type + "/list.action",
		type : "post",
		scriptCharset : "utf-8",
		success : function(data) {
			var select = $('#' + selectName);
			var list = data;
			select.empty();
			var option = $("<option value=0 selceted>--- 请选择 ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i][valueName]
						+ ">" + list[i][textName] + "</option>");
				option.appendTo(select);
			}
			select.val(selectedVal);
		}
	})
}

// 獲得所有未鎖定的資料並加入到對應的select下拉清單表單控件中
// type表示要抓取的類別，比如dept，selectName表示對應的select組件ID
function getAllUnlockedList(type, selectName, valueName, textName, selectedVal) {
	$.ajax({
		url : type + "/listUnlocked.action",
		type : "post",
		scriptCharset : "utf-8",
		success : function(data) {
			var select = $('#' + selectName);
			var list = data;
			select.empty();
			var option = $("<option value=0 selceted>--- 请选择 ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i][valueName]
						+ ">" + list[i][textName] + "</option>");
				option.appendTo(select);
			}
			select.val(selectedVal);
		}
	})
}

// 功能：列出所有選項（部門/角色）供選擇
function getAllUnlockedList2(type, divName, valueName, textName, checkName) {
	$.ajax({
		url : type + "/listUnlocked.action",
		type : "post",
		scriptCharset : "utf-8",
		success : function(data) {
			//console.log(data);
			var list = data;
			$('#' + divName).empty();
			for (var i in list) {
				$('#' + divName).append("<label class='my_protocol'>"
								+ "<input class='input_agreement_protocol' type='checkbox' name='" + checkName + "' value="
								+ list[i][valueName] + "><span></span>"
								+ list[i][textName] + "</label>");
			}
		}
	})
}

// 功能：列出所有選項（部門/角色）供選擇，同時根據傳入的list設定選擇項目
function getAllUnlockedList3(type, divName, valueName, textName, checkName, parentId, parentType, parentMethod, columnName, propName) {
	$.ajax({
		url : type + "/listUnlocked.action",
		type : "post",
		scriptCharset : "utf-8",
		success : function(data) {
			//console.log(data);
			var list = data;
			$('#' + divName).empty();
			for (var i in list) {
				$('#' + divName).append("<label class='my_protocol'>"
								+ "<input class='input_agreement_protocol' type='checkbox' name='" + checkName + "' value="
								+ list[i][valueName] + "><span></span>"
								+ list[i][textName] + "</label>");
			}
			
			$.ajax({
				url : parentType + "/" + parentMethod + ".action",
				type : "post",
				scriptCharset : "utf-8",
				data : {
					param : parentId
				},
				success : function(data1) {
					if(data1[columnName]!=null && data1[columnName]!="null"){
						var list2 = data1[columnName];
						//console.log(data1);
						//console.log(list2);
						var subList = $("#" + divName + " input[name='" + checkName + "']");
						if (list2.length > 0) {
							for (var j in list2) {
								subList.each(function() {
									if ($(this).val() == list2[j][propName]) {
										$(this).prop("checked", "checked");
									}
								});
							}
						}
					}
				}
			})
		}
	})
}

function listUnlockedMemberByRoleFlag(selectName, valueName, textName, flag, selectedVal) {
	$.ajax({
		url : "member/listUnlockedByRoleFlag.action",
		type : "post",
		scriptCharset : "utf-8",
		data : {
			flag : flag
		},
		success : function(data) {
			var select = $('#' + selectName);
			var list = data;
			//console.log(list);
			select.empty();
			var option = $("<option value=0 selceted>--- 请选择 ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i][valueName]
						+ ">" + list[i][textName] + "</option>");
				option.appendTo(select);
			}
			select.val(selectedVal);
		}
	})
}

function listByParentType(selectName, valueName, textName, parent_type, selectedVal) {
	$.ajax({
		url : "type/listByParentType.action",
		type : "post",
		scriptCharset : "utf-8",
		data : {
			parent_type : parent_type
		},
		success : function(data) {
			var select = $('#' + selectName);
			var list = data;
			select.empty();
			var option = $("<option value=0 selceted>--- 请选择 ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i][valueName] + ">" + list[i][textName] + "</option>");
				option.appendTo(select);
			}
			select.val(selectedVal);
		}
	})
}

function listUnlockedTypeByParentType(selectName, valueName, textName, parent_type) {
	$.ajax({
		url : "type/listByParentType.action",
		type : "post",
		scriptCharset : "utf-8",
		data : {
			parent_type : parent_type
		},
		success : function(data) {
			var select = $('#' + selectName);
			var list = data;
			select.empty();
			var option = $("<option value=0 selceted>--- 请选择 ---</option>");
			option.appendTo(select);
			for (var i in list) {
				var option = $("<option value=" + list[i][valueName]
						+ ">" + list[i][textName] + "</option>");
				option.appendTo(select);
			}
			select.selectpicker('refresh');  
			select.selectpicker('render');  
		}
	})
}

function tableHeight(){
    return $(window).height() -190;
}

window.operateEvents = {
	// 鎖定或解鎖權限
	"click #lockOrUnlockAction" : function(e, value, row, index) {
		var action_id = row.action_id;
		var flag = row.flag;
		var title = row.title;
		var locked = row.locked;
		if(locked==0){
			//如果顯示鎖定按鈕，則說明未鎖定，則將locked設置為鎖定1，以便鎖定
			locked="1";
		}else if(locked==1){
			//如果顯示解鎖按鈕，則說明已經鎖定，則將locked設置為未鎖定0，以便解鎖
			locked="0";
		}
		//console.log("action_id : " + action_id);
		//console.log("flag : " + flag);
		//console.log("title : " + title);
		//console.log("locked : " + locked);
		$.ajax({
			type : "POST",
			url : "action/update.action",
			dataType : "json",
			data : {
				action_id : action_id,
				flag : flag,
				title : title,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					if(locked=="1"){
						operateAlert(true, "权限锁定成功！", "");
					}else{
						operateAlert(true, "权限解锁成功！", "");
					}
					$("#contentAction").bootstrapTable("refresh");
				} else {
					operateAlert(false, "", "操作失败！");
				}
			}
		});
	},
	
	// 顯示編輯權限模態框
	"click #editAction" : function(e, value, row, index) {
		//如果flag和title兩個一起查詢可以查到的話，如果action_id不同，則不允許修改權限資料
		$("#showUpdateAction .modal-dialog .modal-content .modal-header .modal-title").text("修改权限")
		//$("#modalActionFlag").attr("disabled", true);
		$("#modalActionFlag").val("");
		$("#modalActionTitle").val("");
		$("#modalActionLockedDiv").fadeIn(10);
		$("#btnUpdateAction").fadeIn(10);
		$("#btnAddAction").fadeOut(10);
		var action_id = row.action_id;
		var flag = row.flag;
		var title = row.title;
		var locked = row.locked;
		//抓取action_id
		$("#modalActionId").val(action_id);
		if(locked=="0"){
			$("#modalActionLocked").prop("checked", false);
		}else if(locked=="1"){
			$("#modalActionLocked").prop("checked", true);
		}
		//抓取flag
		$("#modalActionFlag").val(flag);
		//抓取title
		$("#modalActionTitle").val(title);
		//清空異常提示文字
		$("#modalActionMsg").text("");
		
		//顯示模態框
		$("#showUpdateAction").modal("show");
	},
	
	"click #lockOrUnlockRole" : function(e, value, row, index) {
		var role_id = row.role_id;
		var flag = row.flag;
		var title = row.title;
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
			url : "role/update.action",
			dataType : "json",
			data : {
				role_id : role_id,
				flag : flag,
				title : title,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					if(locked=="1"){
						operateAlert(true, "角色锁定成功！", "");
					}else{
						operateAlert(true, "角色解锁成功！", "");
					}
					$("#contentRole").bootstrapTable("refresh");
				} else {
					operateAlert(false, "", "操作失败！");
				}
			}
		});
	},
	
	// 顯示編輯角色模態框
	"click #editRole" : function(e, value, row, index) {
		$("#showUpdateRole .modal-dialog .modal-content .modal-header .modal-title").text("修改角色")
		//$("#modalRoleFlag").attr("disabled", true);
		$("#modalRoleFlag").val("");
		$("#modalRoleTitle").val("");
		$("#modalRoleLockedDiv").fadeIn(10);
		$("#btnUpdateRole").fadeIn(10);
		$("#btnAddRole").fadeOut(10);
		
		var role_id = row.role_id;
		var flag = row.flag;
		var title = row.title;
		var locked = row.locked;
		//抓取action_id
		$("#modalRoleId").val(role_id);
		if(locked=="0"){
			$("#modalRoleLocked").prop("checked", false);
		}else if(locked=="1"){
			$("#modalRoleLocked").prop("checked", true);
		}
		//抓取flag
		$("#modalRoleFlag").val(flag);
		//抓取title
		$("#modalRoleTitle").val(title);
		// 載入所有action供role選擇
		getAllUnlockedList3("action", "modalActions", "action_id", "title", "modalActionsSelected", role_id, "role", "getByRid", "roleAction", "action_id");
		//清空異常提示文字
		$("#modalRoleMsg").text("");
		
		//顯示模態框
		$("#showUpdateRole").modal("show");
	},
	
	// 鎖定或解鎖人員
	"click #lockOrUnlockMember" : function(e, value, row, index) {
		var member_id = row.member_id;
		var member_name = row.member_name;
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
			url : "member/update.action",
			dataType : "json",
			data : {
				member_id : member_id,
				member_name : member_name,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					if(locked=="1"){
						operateAlert(true, "人员锁定成功！", "");
					}else{
						operateAlert(true, "人员解锁成功！", "");
					}
					$("#contentMember").bootstrapTable("refresh");
				} else {
					operateAlert(false, "", "操作失败！");
				}
			}
		});
	},
	
	// 顯示編輯人員模態框
	"click #editMember" : function(e, value, row, index) {
		
		$("#showUpdateMember .modal-dialog .modal-content .modal-header .modal-title").text("修改人员资料");
		$("#modalMemberId").attr("disabled","disabled");
		$("#modalMemberId").prop("disabled",true);
		$("#modalMemberId").val("");
		$("#modalMemberName").val("");
		$("#modalMemberLockedDiv").fadeIn(10);
		$("#btnUpdateMember").fadeIn(10);
		$("#btnAddMember").fadeOut(10);
		var member_id = row.member_id;
		var member_name = row.member_name;
		var locked = row.locked;
		//抓取member_id
		$("#modalMemberId").val(member_id);
		//console.log($("#modalMemberId").val());
		if(locked=="0"){
			$("#modalMemberLocked").prop("checked", false);
		}else if(locked=="1"){
			$("#modalMemberLocked").prop("checked", true);
		}
		//抓取member_name
		$("#modalMemberName").val(member_name);
		//清空異常提示文字
		$("#modalMemberMsg").text("");
		// 載入所有部門供人員選擇
		getAllUnlockedList3("dept", "modalDepts", "dept_id", "dept_name", "modalDeptsSelected", member_id, "member", "getByMidAll", "depts", "dept_id");
		// 載入所有role供member選擇
		getAllUnlockedList3("role", "modalRoles", "role_id", "title", "modalRolesSelected", member_id, "member", "getByMidAll", "roles", "role_id");
		
		//顯示模態框
		$("#showUpdateMember").modal("show");
	},
	
	// 顯示管理員修改人員密碼模態框
	"click #updateMemberPassword" : function(e, value, row, index) {
		//抓取member_id
		$("#modalMemberPasswordId").val(row.member_id);
		//抓取member_name
		$("#modalMemberPasswordName").val(row.member_name);
		//新密碼
		$("#modalMemberPasswordNew").val("");
		//清空異常提示文字
		$("#memberModalPasswordMsg").text("");
		//顯示模態框
		$("#showUpdateMemberPassword").modal("show");
	},
	"click #lockOrUnlockType" : function(e, value, row, index) {
		var id = row.id;
		var locked = row.locked;
		if(locked==0){
			// 如果显示锁定按钮，则说明未锁定，则将locked设置为锁定1，以便锁定
			locked="1";
		}else if(locked==1){
			// 如果显示解锁按钮，则说明已经锁定，则将locked设置为未锁定，以便解锁
			locked="0";
		}
	
		$.ajax({
			type : "POST",
			url : "type/update.action",
			dataType : "json",
			data : {
				id : id,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					if(locked=="1"){
						operateAlert(true, "类型锁定成功！", "");
					}else{
						operateAlert(true, "类型解锁成功！", "");
					}
					$("#contentType").bootstrapTable("refresh");
				} else {
					operateAlert(false, "", "操作失败！");
				}
			}
		});
	},
	
/*	// 显示修改类型模态框
	"click #editType" : function(e, value, row, index) {
		$("#showUpdateType .modal-dialog .modal-content .modal-header .modal-title").text("修改類型資料");
		$("#modalTypeId").val("");
		$("#modalParentType").val("");
		$("#modalSubType").val("");
		$("#addOrUseParentTypeDiv").fadeOut(5);
		$("#modalTypeParentTypeListDiv").fadeIn(5);
		$("#modalTypeParentTypeInputDiv").fadeOut(5);
		$("#modalTypeLockedDiv").fadeIn(10);
		$("#btnUpdateType").fadeIn(10);
		$("#btnAddType").fadeOut(10);
		
//		$("#modalSubType").attr("disabled",true);
//		$("#modalTypeParentTypeList").attr("disabled",true);
		var id = row.id;
		var parent_type = row.parent_type;
		var sub_type = row.sub_type;
		var locked = row.locked;
		$("#modalTypeId").val(id);
		$("#modalSubType").val(sub_type);
		$("#modalTypeMsg").text("");
		if(locked=="0"){
			$("#modalTypeLocked").prop("checked", false);
		}else if(locked=="1"){
			$("#modalTypeLocked").prop("checked", true);
		}
		var select=$("#modalTypeParentTypeList");
		select.empty();
		var option = $("<option value=" +id+ ">" +parent_type + "</option>");
		option.appendTo(select);
		select.selectpicker('refresh');
		select.selectpicker('val',id);
		
		
		// 显示模态框
		$("#showUpdateType").modal("show");
	},*/
	// 鎖定或解鎖部門
	"click #lockOrUnlockDept" : function(e, value, row, index) {
		var dept_id = row.dept_id;
		var dept_code = row.dept_code;
		var dept_name = row.dept_name;
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
			url : "dept/update.action",
			dataType : "json",
			data : {
				dept_id : dept_id,
				dept_code : dept_code,
				dept_name : dept_name,
				locked : locked
			},
			success : function(data) {
				if (data == true) {
					if(locked=="1"){
						operateAlert(true, "部门锁定成功！", "");
					}else{
						operateAlert(true, "部门解锁成功！", "");
					}
					$("#contentDept").bootstrapTable("refresh");
				} else {
					operateAlert(false, "", "操作失败！");
				}
			}
		});
	},
	
	// 顯示編輯部門模態框
	"click #editDept" : function(e, value, row, index) {
		//如果flag和title兩個一起查詢可以查到的話，如果action_id不同，則不允許修改權限資料
		$("#showUpdateDept .modal-dialog .modal-content .modal-header .modal-title").text("修改部門")
		$("#modalDeptCode").val("");
		$("#modalDeptName").val("");
		$("#modalDeptLockedDiv").fadeIn(10);
		$("#btnUpdateDept").fadeIn(10);
		$("#btnAddDept").fadeOut(10);
		var dept_id = row.dept_id;
		var dept_code = row.dept_code;
		var dept_name = row.dept_name;
		var locked = row.locked;
		$("#modalDeptId").val(dept_id);
		if(locked=="0"){
			$("#modalDeptLocked").prop("checked", false);
		}else if(locked=="1"){
			$("#modalDeptLocked").prop("checked", true);
		}
		$("#modalDeptCode").val(dept_code);
		$("#modalDeptName").val(dept_name);
		//清空異常提示文字
		$("#modalDeptMsg").text("");
		
		//顯示模態框
		$("#showUpdateDept").modal("show");
	}
}