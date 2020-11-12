var type = localStorage.getItem('knowledgeDetailType')

var part_code = localStorage.getItem("part_code")
var version = localStorage.getItem("archives_version")
var id = localStorage.getItem("knowledge_id")
$('document').ready(function(){
	console.log(type)
//	如果是升級時
	if(type=="levelup"){
		console.log(part_code,version);
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
				$('#part_type').remove();
				$('.type').append('<input type="text" class="form-control" id="part_type" name="part_type" placeholder="請輸入料號" readonly/>');
				$('#part_type').val(data.part_type);
				$('#number').val(data.part_code);
				$('#version').val(String.fromCharCode(data.version.charCodeAt(0) + 1));
				$('#name').val(data.part_name);
				$('#desc').val(data.descr);
				$('#date').val(data.date);

//				查詢分階列表
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
							var html = '<tr><td>'+data[i].part_code+'</td><td style="display:none">'+data[i].version+'</td>'
							+'<td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
							+'<td><button class="choose_knowledgePartRemove btn btn-primary" title="刪除">刪除</button></td><td style="display:none">'+data[i].id
							+'</td></tr>'
							$('.partList tbody').append(html)
						}
					}
				})
			}
		})
	}
//	如果是修改
	if(type=="update"){
		console.log(part_code,version);
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
				$('#part_type').val(data.part_type);
				$('#number').val(data.part_code);
				$('#number').attr("readonly",false);
				$('#version').val(data.version);
				$('#name').val(data.part_name);
				$('#desc').val(data.descr);
				$('#date').val(data.date);

//				查詢分階列表
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
							var html = '<tr><td>'+data[i].part_code+'</td><td style="display:none">'+data[i].version+'</td>'
							+'<td><button class="choose_knowledgePartDetail btn btn-primary" title="查看">查看</button></td>'
							+'<td><button class="choose_knowledgePartRemove btn btn-primary" title="刪除">刪除</button></td><td style="display:none">'+data[i].id
							+'</td></tr>'
							$('.partList tbody').append(html)
						}
					}
				})
			}
		})
	}
	$('.submit').click(function(){
		console.log("提交");
//		獲取信息
		var part_type = $('#part_type').val()
		var part_code = $('#number').val();
		var part_name = $('#name').val();
		var version = $('#version').val();
		var desc = $('#desc').val();
		var date = $('#date').val();

		var list = $('.partList tbody tr');
		var arr = [];
		for(var i=0;i<list.length;i++){
			var obj = {
				knowledge_part_id:list.eq(i).find('td').eq(4).text(),
				part_code:list.eq(i).find('td').eq(0).text(),
				version:list.eq(i).find('td').eq(1).text()
			}
			arr.push(obj);
		}
		console.log(arr)
		var param = {
			part_type : part_type,
			part_code : part_code,
			part_name : part_name,
			version : version,
			desc : desc,
			date : date,
			partArr:JSON.stringify(arr)
		}
		if(type=="update"){
			param.id = id
		}
		$.ajax({
			type : "POST",
			url : "knowledge/add.action",
			dataType : "json",
			data : param,
			traditional : true,
			success : function(data) {
				console.log(data)
			}
		})
	})
	
	
	$('.partList').on("click",".choose_knowledgePartDetail",function(){
		var row = $(this).parents('tr');
		var part_code = row.find('td').eq(0).text();
		var version = row.find('td').eq(1).text();
		var id = row.find('td').eq(4).text();
		console.log(part_code,version,row)
		open_page("knowledgePartDetail",part_code,version,"knowledge_part_id",id)
	})
	$('.partList').on("click",".choose_knowledgePartRemove",function(){
		var row = $(this).parents('tr');
		row.remove()
	})
	knowledgeAdd();
	$('body').on("click","#knowledgePartChoose",function(){
		console.log(1)
		partChoose($(this).parents('tr'))
	})
})
function partChoose(row){
	var part_code = row.find('td').eq(0).text();
	var version = row.find('td').eq(2).text();
	var id = row.find('input').val();
	console.log(part_code,version,id)
//	渲染
	var html = '<tr><td>'+part_code+'</td><td style="display:none">'+version
	+'</td><td><button id="choose_knowledgePartDetail" class="btn btn-primary" title="查看">查看</button></td>'
	+'<td><button id="choose_knowledgePartRemove" class="btn btn-primary" title="刪除">刪除</button></td><td style="display:none">'+id
	+'</td></tr>'
	$('.partList tbody').append(html); 
}