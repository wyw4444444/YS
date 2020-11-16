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
			$('.pdfContent embed').attr('src',localFormatUrl(data.fileUrl))
			$('#printIframe').attr('src',localFormatUrl(data.fileUrl))
//			$('.fileShow').attr('src',localFormatUrl(data.file))
			var status = data.status
			if(data.status=="1"){
				$('.submit').show();
				$('.cancel').show();
				$('.fileContent').show();
			}
			if(data.status=="0"){
				$('.update').show();
				$('.fileContent').show();
			}
			if(data.status=="2"){
				$('.levelup').show(); 
				$('.print').show(); 
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
						if(status!="2"){
							var html = '<tr><td>'+data[i].part_code+'</td><td>'+data[i].version+'</td>'
							+'<td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
							+'<td class="fileUrl" style="display:none;">'+data[i].file+'</td>'
							+'</tr>';							
						}else{
							var html = '<tr><td>'+data[i].part_code+'</td><td>'+data[i].version+'</td>'
							+'<td></td>'
							+'<td class="fileUrl" style="display:none;">'+data[i].file+'</td>'
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
		console.log("升級")
		localStorage.setItem("knowledgeDetailType","levelup")
		change_page('knowledgeUpdate')
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
					window.close();
				}
			}
		})
	})
	$('body').on('click','.print',function(){
		console.log("打印")
		doPrint()
	})
	$('body .partList').on("click",".choose_knowledgePartDetail",function(){
		var fileUrl = $(this).parents("tr").find('.fileUrl').text();
		$('.fileShow').attr('src',localFormatUrl(fileUrl))
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