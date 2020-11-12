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
			$('#name').val(data.name);
			$('#desc').val(data.desc);
			$('.fileShow').attr('src',localFormatUrl(data.file))
			if(data.status=="1"){
				$('.submit').show();
				$('.cancel').show();
			}
			if(data.status=="0"){
				$('.update').show();
			}
			if(data.status=="2"){
				$('.levelup').show(); 
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
				part_code : part_code,
				version : version,
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("審核成功")
					window.close();
				}
			}
		})
	})
	$('.cancel').click(function(){
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
	$('.update').click(function(){
		console.log("修改")
		localStorage.setItem("knowledgePartType","update")
		localStorage.setItem("id",id)
		change_page("knowledgePartAdd")
	})
	$('.levelup').click(function(){
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
})
function localFormatUrl(url){
	return "/file"+url.substring(15);
}








