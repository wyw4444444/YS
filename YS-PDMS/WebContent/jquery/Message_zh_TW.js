$.extend($.validator.messages, {
			required : "不允許為空！",
			remote : "數據輸入錯誤，請重新輸入！",
			email : "請輸入正確的EMAIL地址！",
			url : "請輸入合法的網址！",
			date : "請輸入合法的日期！",
			dateISO : "請輸入合法的日期（例如：yyyy-mm-dd或yyyy/mm/dd）！",
			number : "請輸入合法的數字（整數或小數）！",
			digits : "請輸入整型數據！",
			creditcard : "請輸入合法的信用卡號！",
			equalTo : "兩次輸入的數據內容不相同！",
			accept : "文件後綴不符合要求！",
			extension : "該文件不允許使用！",
			maxlength : $.validator.format("輸入內容長度不能大於{0}"),
			minlength : $.validator.format("輸入內容長度不能小於{0}！"),
			rangelength : $.validator.format("輸入數據的長度應該為{0} ~ {1}！"),
			range : $.validator.format("請輸入一個介於 {0}和和 {1}之間的值"),
			max : $.validator.format("可以輸入的最大值為 {0} ！"),
			min : $.validator.format("可以輸入的最小值為 {0} ！")
		});