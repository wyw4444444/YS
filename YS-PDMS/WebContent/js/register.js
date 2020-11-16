$(document).ready(function() {
	registerSetting();
	
	// 禁止新增人員的form提交刷新頁面
	$("#formSystemRegister").submit(function() {
		return false;
	});
	
});


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
		url : "SystemRegisterAction/checkRegisterLinux.action",
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

