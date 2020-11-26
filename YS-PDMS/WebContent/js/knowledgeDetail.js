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
			if(data.fileUrl){
				$('.pdfContent embed').attr('src',localFormatUrl(data.fileUrl))
				$('#printIframe').attr('src',localFormatUrl(data.fileUrl))
			}
//			$('.fileShow').attr('src',localFormatUrl(data.file))
			var status = data.status
			if(data.status=="1"){
				$('.submit').show();
				$('.cancel').show();
				$('.getback').show();
				$('.reject').show();
			}
			if(data.status=="3"||data.status=="2"||data.status=="4"){
				$('.update').show();
				$('.fileContent').show();
			}
			if(data.status=="5"){
				$('.levelup').show(); 
				$('.print').show(); 
				$('.delete').show(); 
				$('.pdfContent').show();
			}
			if(data.status=="6"){
				$('.pdfContent').show();
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
						if(status!="5"&&status!="6"){
							var html = '<tr><td>'+data[i].part_code+'</td><td>'+data[i].version+'</td>'
							+'<td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
							+'<td class="fileUrl" style="display:none;">'+data[i].fileUrl+'</td>'
							+'</tr>';							
						}else{
							var html = '<tr><td>'+data[i].part_code+'</td><td>'+data[i].version+'</td>'
							+'<td></td>'
							+'<td class="fileUrl" style="display:none;">'+data[i].fileUrl+'</td>'
							+'</tr>';
						}
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
				id:id,
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
	$('.update').click(function(){
		console.log("修改")
		localStorage.setItem("knowledgeDetailType","update")
		change_page('knowledgeUpdate')
	})
	$('.levelup').click(function(){
//		升版前先判斷是否是最新版
		console.log("升級")
		$.ajax({
			type : "POST",
			url : "knowledge/findOneNewKnowledge.action",
			dataType : "json",
			data : {
				part_code : part_code,
			},
			traditional : true,
			success : function(data) {
				if(data.version&&data.version==version){
					localStorage.setItem("knowledgeDetailType","levelup")
					change_page('knowledgeUpdate')
				}else{
					alert("當前文檔不是最新版")
				}
			},
			error:function(data){
				if(!data.responseText){
					console.log("沒有數據")
					localStorage.setItem("knowledgeDetailType","levelup")
					change_page('knowledgeUpdate')
				}
			}
		})
	})
	$('.reject').click(function(){
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
					window.close();
				}
			}
		})
	})
	$('.getback').click(function(){
		console.log("取消")
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgeStatus.action",
			dataType : "json",
			data : {
				id:id,
				status:"2"
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
	$('.cancel').click(function(){
		console.log("取消")
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgeStatus.action",
			dataType : "json",
			data : {
				id:id,
				status:"4"
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
	$('.delete').click(function(){
		console.log("廢止")
		$.ajax({
			type : "POST",
			url : "knowledge/updateKnowledgeStatus.action",
			dataType : "json",
			data : {
				id:id,
				status:"6"
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
	$('body .partList').on("click",".choose_knowledgePartDetail",function(){
		var fileUrl = $(this).parents("tr").find('.fileUrl').text();
		$('.fileShow').attr('src',localFormatUrl(fileUrl))
		$('.fileContent').show();
	})
	$('body').on('click','.print',function(){
		console.log("打印")
		doPrint()
	})
	$("#printIframe").load(function(){//等待iframe加载完成后再执行doPrint.每次iframe设置src之后都会重新执行这部分代码。
//        doPrint();
    });
})
function localFormatUrl(url){
	return "/file"+url.substring(15);
}

function doPrint(){

    var src = $("#printIframe").attr("src");
    if(!src){//当src为空，即第一次加载时才赋值，如果是需要动态生成的话，那么条件要稍稍变化一下
                $("#printIframe").attr("src","./attachment/Images.pdf");//暂时静态PDF文件
    }else
        $("#printIframe")[0].contentWindow.print();//不知为什么在IE中一直无法打印文件
 
}