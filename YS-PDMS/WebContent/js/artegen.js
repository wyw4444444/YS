$(document).ready(function() {
	var $submenu = $('.submenu');
	var $mainmenu = $('.mainmenu');
	$submenu.hide();
	$submenu.first().delay(400).slideDown(700);
	$submenu.on('click', 'li', function() {
				$submenu.siblings().find('li').removeClass('chosen');
				$(this).addClass('chosen');
			});
	$mainmenu.on('click', 'li', function() {
				$(this).next('.submenu').slideToggle().siblings('.submenu')
						.slideUp();
			});
	$mainmenu.children('li:last-child').on('click', function() {
				$mainmenu.fadeOut().delay(500).fadeIn();
			});
});

// 倒計時跳轉網頁
function goTime() {
	// 抓取時間值
	var t = $("#mytime").text();
	//console.log(t);
	// 時間值得減1秒
	t--;
	// 如果時間等於0，則直接跳轉，否則繼續循環
	if (t == 0) {
		window.location = goUrl;
	} else {
		$("#mytime").text(t);
		setTimeout(goTime, 1000);
	}
}

/**
 * 警告框操作信息，ID必須為“alertDiv”
 * 
 * @param flag
 *            操作成功失敗的標記
 * @param suctext
 *            操作成功時的顯示文本內容
 * @param faltext
 *            操作失敗時的顯示文本內容
 */
function operateAlert(flag, suctext, faltext) {
	if (flag) {
		$("#alertDiv").attr("class", "alert alert-success");
		$("#alertText").text(suctext);
	} else {
		$("#alertDiv").attr("class", "alert alert-danger");
		$("#alertText").text(faltext);
	}
	$("#alertDiv").fadeIn(1000, function() {
				$("#alertDiv").fadeOut(3000);
			});
}

function operateAlertSmall(flag, suctext, faltext) {
	if (flag) {
		$("#alertSmallDiv").attr("class", "alert alert-success");
		$("#alertSmallText").text(suctext);
	} else {
		$("#alertSmallDiv").attr("class", "alert alert-danger");
		$("#alertSmallText").text(faltext);
	}
	$("#alertSmallDiv").fadeIn(1000, function() {
				$("#alertSmallDiv").fadeOut(3000);
			});
}

function operateAlertSmall2(flag, suctext, faltext) {
	if (flag) {
		$("#alertSmallDiv2").attr("class", "alert alert-success");
		$("#alertSmallText2").text(suctext);
	} else {
		$("#alertSmallDiv2").attr("class", "alert alert-danger");
		$("#alertSmallText2").text(faltext);
	}
	$("#alertSmallDiv2").fadeIn(1000, function() {
				$("#alertSmallDiv2").fadeOut(3000);
			});
}

function operateAlertSmall3(flag, suctext, faltext) {
	if (flag) {
		$("#alertSmallDiv3").attr("class", "alert alert-success");
		$("#alertSmallText3").text(suctext);
	} else {
		$("#alertSmallDiv3").attr("class", "alert alert-danger");
		$("#alertSmallText3").text(faltext);
	}
	$("#alertSmallDiv3").fadeIn(1000, function() {
				$("#alertSmallDiv3").fadeOut(3000);
			});
}

function operateAlertSmall4(flag, suctext, faltext) {
	if (flag) {
		$("#alertSmallDiv4").attr("class", "alert alert-success");
		$("#alertSmallText4").text(suctext);
	} else {
		$("#alertSmallDiv4").attr("class", "alert alert-danger");
		$("#alertSmallText4").text(faltext);
	}
	$("#alertSmallDiv4").fadeIn(1000, function() {
				$("#alertSmallDiv4").fadeOut(3000);
			});
}

function operateAlertSmall5(flag, suctext, faltext) {
	if (flag) {
		$("#alertSmallDiv5").attr("class", "alert alert-success");
		$("#alertSmallText5").text(suctext);
	} else {
		$("#alertSmallDiv5").attr("class", "alert alert-danger");
		$("#alertSmallText5").text(faltext);
	}
	$("#alertSmallDiv5").fadeIn(1000, function() {
				$("#alertSmallDiv5").fadeOut(3000);
			});
}

function operateAlertSmall6(flag, suctext, faltext) {
	if (flag) {
		$("#alertSmallDiv6").attr("class", "alert alert-success");
		$("#alertSmallText6").text(suctext);
	} else {
		$("#alertSmallDiv6").attr("class", "alert alert-danger");
		$("#alertSmallText6").text(faltext);
	}
	$("#alertSmallDiv6").fadeIn(1000, function() {
				$("#alertSmallDiv6").fadeOut(3000);
			});
}

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

//將Excel轉換來的日期數值轉格式
//拿到的整數值是日期距離1900年1月1日的天數
function formatExcelDate(numb, format) {
	var time = new Date((numb - 1) * 24 * 3600000 + 1);
	time.setYear(time.getFullYear() - 70);
	var year = time.getFullYear() + '';
	var month = time.getMonth() + 1 + '';
	var date = time.getDate() + '';
	if (format && format.length === 1) {
		// return year + format + month + format + date;
		return year + format + (month < 10 ? '0' + month : month) + format + (date < 10 ? '0' + date : date);
	}
	return year + (month < 10 ? '0' + month : month) + (date < 10 ? '0' + date : date);
}

//抓取當月最大天數
function getMonthDays(date){
     var year = date.getFullYear();
     var month = date.getMonth();
     var d = new Date(year, month, 0);
     return d.getDate();
}

function getMonthDays2(date){
     var year = date.getFullYear();
     var month = date.getMonth();
     var d = new Date(year, month+1, 0);
     return d.getDate();
}
