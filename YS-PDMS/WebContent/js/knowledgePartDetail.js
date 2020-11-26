var part_code = localStorage.getItem("part_code")
var version = localStorage.getItem("archives_version")
var id = localStorage.getItem("knowledge_part_id")
$('document').ready(function(){
	console.log(part_code,version,id)
	
	//需要後台查詢該料號和版本的數據
	$.ajax({
		type : "POST",
		url : "knowledge/findPartById.action",
		dataType : "json",
		data : {
			id:id
		},
		traditional : true,
		success : function(data) {
			console.log(data)
			$('#number').val(data.part_code);
			$('#version').val(data.version);
			$('#name').val(data.part_name);
			$('#desc').val(data.descr);
			$('.fileShow').attr('src',localFormatUrl(data.fileUrl))
			if(data.status=="1"){
				$('.submit').show();
				$('.cancel').show();
				$('.getback').show();
				$('.reject').show();
			}
			if(data.status=="3"||data.status=="2"||data.status=="4"){
				$('.update').show();
			}
			if(data.status=="5"){
				$('.levelup').show(); 
				$('.print').show(); 
				$('.delete').show(); 
			}
		}
	})
	
	
	$('.submit').click(function(){
		console.log("審核")
		$.ajax({
			type : "POST",
			url : "knowledge/updatePartPass.action",
			dataType : "json",
			data : {
				id:id,
				part_code : part_code,
				version : version,
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("審核成功，請選擇同步升版的成品")
					$('#showUpdateKnowledge').modal()
					showKnowledgeList(part_code)
				}
			}
		})
	})
	$('.reject').click(function(){
		console.log("駁回")
		$.ajax({
			type : "POST",
			url : "knowledge/updatePartReject.action",
			dataType : "json",
			data : {
				part_code : part_code,
				version : version,
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("駁回成功")
					window.close();
				}
			}
		})
	})
	$('.cancel').click(function(){
		console.log("取消")
		$.ajax({
			type : "POST",
			url : "knowledge/updatePartStatus.action",
			dataType : "json",
			data : {
				id : id,
				status : "4",
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("取消成功")
					window.close();
				}
			}
		})
	})
	$('.getback').click(function(){
		console.log("取回")
		$.ajax({
			type : "POST",
			url : "knowledge/updatePartStatus.action",
			dataType : "json",
			data : {
				id : id,
				status : "2",
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("取回成功")
					window.close();
				}
			}
		})
	})
	$('.delete').click(function(){
		console.log("廢止")
		$.ajax({
			type : "POST",
			url : "knowledge/updatePartStatus.action",
			dataType : "json",
			data : {
				id : id,
				status : "6",
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("廢止成功")
					window.close();
				}
			}
		})
	})
	$('.update').click(function(){
		console.log("修改")
		localStorage.setItem("knowledgePartType","update")
		localStorage.setItem("id",id)
		change_page("knowledgePartAdd")
	})
	$('body').on('click','.levelup',function(){
		console.log("升版")
//		要判斷是否可以升版
//		找到當前料號的最新版本
		$.ajax({
			type : "POST",
			url : "knowledge/findNewPartByCode.action",
			dataType : "json",
			data : {
				part_code : part_code
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(version==data){
					localStorage.setItem("knowledgePartType","levelup")
					localStorage.setItem("id",id)
					change_page("knowledgePartAdd")
				}
				else{
					alert("當前版本不是最新版本")
				}
			},
			error:function(data){
				console.log(data.responseText)
				if(version==data.responseText){
					localStorage.setItem("knowledgePartType","levelup")
					localStorage.setItem("id",id)
					change_page("knowledgePartAdd")
				}
				else{
					alert("當前版本不是最新版本")
				}
			}
		})
	})
	$('body').on('click','.print',function(){
		console.log("打印")
		doPrint()
	})
	$('#showUpdateKnowledge').on('click','#knowledgeUpdate',function(){
		console.log("同步");
		var that = $(this)
		var id = $(this).parents("tr").find("input.id").val();
		var kid = $(this).parents("tr").find("input.kid").val();
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgeByPart.action",
			dataType : "json",
			data : {
				id:id,
				knowledge_id:kid,
				knowledge_part_id:localStorage.getItem("knowledge_part_id")
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					that.removeClass("btn-primary")
					that.text("已同步")
					that.attr("disabled","disabled")
				}
			}
		})
	})
	$('#showUpdateKnowledge').on('hidden.bs.modal', function (e) {
	  	window.close()
	})
})
function localFormatUrl(url){
	return "/file"+url.substring(15);
}


function doPrint() {
    bdhtml=window.document.body.innerHTML;
    sprnstr="<!--startprint-->"; //开始打印标识字符串有17个字符
    eprnstr="<!--endprint-->"; //结束打印标识字符串
    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); //从开始打印标识之后的内容
    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
    window.document.body.innerHTML=prnhtml; //把需要打印的指定内容赋给body.innerHTML
    window.print(); //调用浏览器的打印功能打印指定区域
    window.document.body.innerHTML=bdhtml; // 最后还原页面
    location.reload()
}






