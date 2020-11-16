$(function() {
			$("#myform").validate({
				debug : true, // 運行調試模式，也就是不提交表單
				submitHandler : function(form) {
					// form.submit(); // 通過驗證后執行的函數，提交表单
					login();
				},
				// errorPlacement后跟一個函數，自定義錯誤放在哪裡
				errorPlacement : function(error, element) {
					$("#" + $(element).attr("id").replace(".", "\\.") + "Msg")
							.append(error);
				},
				// highlight給未通過驗證的嚴肅加效果、閃爍等
				highlight : function(element, errorClass) {
					$(element).fadeOut(1, function() {
						$(element).fadeIn(1, function() {
							$("#" + $(element).attr("id").replace(".", "\\.")
									+ "Div").attr("class",
									"form-group has-error");
						});

					})
				},
				unhighlight : function(element, errorClass) {
					$(element).fadeOut(1, function() {
						$(element).fadeIn(1, function() {
							$("#" + $(element).attr("id").replace(".", "\\.")
									+ "Div").attr("class",
									"form-group has-success");
						});
					})
				},
				// errorClass：類型String，默認
				// "error"。指定錯誤提示的css類名，可以自定義錯誤提示的樣式。此處指定的是bootstrap中的css樣式
				errorClass : "text-danger",
				// rules自定義規則，key:value的形式，key是要驗證的元素，value可以是字符串或對象。
				rules : {
					"mid" : {
						required : true
					},
					"password" : {
						required : true
					}
				}
			});
		})

function login() {
	// 抓取mid
	var mid = $("#mid").val();
	// 抓取password
	var password = $("#password").val();
	// 抓取Remember Me
	var rememberMe = false;
	if ($("#rememberMe").is(':checked')) {
		rememberMe=true
	}
	console.log(rememberMe);
	$.ajax({
		type : "POST",
		url : "loginWeb.action",
		//url : "loginUrl.action",
		dataType : "json",
		data : {
			mid : mid,
			password : password,
			rememberMe : rememberMe
		},
		success : function(data) {
			console.log(data.isLogin);
			if (data.isLogin == false) {
				alert("用户名或密码错误！");
			} else {
				window.location.href = "index.action";
			}
		}
	});
	//alert("調用登錄程序！");
}