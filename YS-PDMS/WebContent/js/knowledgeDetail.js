var part_code = localStorage.getItem("part_code")
var version = localStorage.getItem("archives_version")
var id = localStorage.getItem("knowledge_id")
$('document').ready(function(){
	console.log(part_code,version,id)
	
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
			$('#number').val(data.part_code);
			$('#version').val(data.version);
			$('#name').val(data.part_name);
			$('#desc').val(data.descr);
			$('#date').val(data.date);
//			$('.fileShow').attr('src',localFormatUrl(data.file))
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
//			查詢分階列表
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
						var html = '<tr><td>'+data[i].part_code+'</td>'
						+'<td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
						+'<td class="fileUrl" style="display:none;">'+data[i].file+'</td>'
						+'</tr>';
						$('.partList tbody').append(html)
					}
				}
			})
		}
	})
	
	
	$('.submit').click(function(){
		console.log("審核")
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgePass.action",
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
					location.reload();
				}
			}
		})
	})
	$('.update').click(function(){
		console.log("修改")
		localStorage.setItem("knowledgeDetailType","update")
		window.open("/YS-PDMS/index.action#knowledgeUpdate")
	})
	$('.levelup').click(function(){
		console.log("升級")
		localStorage.setItem("knowledgeDetailType","levelup")
		window.open("/YS-PDMS/index.action#knowledgeUpdate")
	})
	$('.cancel').click(function(){
		console.log("駁回")
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgeReject.action",
			dataType : "json",
			data : {
				id:id
			},
			traditional : true,
			success : function(data) {
				console.log(data)
				if(data){
					alert("駁回成功")
					location.reload();
				}
			}
		})
	})
	$('body .partList').on("click",".choose_knowledgePartDetail",function(){
		var fileUrl = $(this).parents("tr").find('.fileUrl').text();
		$('.fileShow').attr('src',localFormatUrl(fileUrl))
	})
})
function localFormatUrl(url){
	return "/file"+url.substring(15);
}
