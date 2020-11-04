function loadAllItem() {
	$.messager.confirm('确认', '确认导出所有类型资料为Excel表格？', function(r) {
				if (r) {
					$.messager.progress({
								title : '处理中',
								msg : '请稍候'
							});
					$.messager.progress('close');
					location.href = "item/listExcel.action";
				}
			});
}

function loadAllType() {
	var table = $("#contentData");
	var height = 500;
	$.ajax({
				type : "POST",
				url : "type/list.action",
				async : true,
				dataType : "json",
				traditional : true,
				success : function(data) {
					var list = data;
					var dataArray = new Array();
					for (var i in list) {
						var map = {};
						map['item'] = i * 1 + 1;
						map['parent_type'] = list[i].parent_type;
						map['sub_type'] = list[i].sub_type;
						map['locked'] = list[i].locked;

						dataArray[i] = map;
					}
					var dataString = JSON.stringify(dataArray);
					var tableData = eval("(" + dataString + ")");
					table.bootstrapTable('destroy');
					table.bootstrapTable({
								columns : [{
											field : 'item',
											title : '序号',
											valign : 'middle',
											align : 'center'

										}, {
											field : 'parent_type',
											title : '父类型',
											valign : 'middle',
											align : 'center'
										}, {
											field : 'sub_type',
											title : '子类型',
											valign : 'middle',
											align : 'center'
										}, {
											field : 'locked',
											title : '是否锁定',
											valign : 'middle',
											align : 'center'
										}],
								showExport : true, // 是否显示导出按钮
								buttonsAlign : "left", // 按钮位置
								exportTypes : ['excel'], // 导出文件类型
								exportDataType : "all",
								exportOptions : {
									// ignoreColumn: [0,0], //忽略某一列的索引
									fileName : 'allType', // 文件名称设置
									worksheetName : 'Sheet1', // 表格工作区名称
									tableName : 'DataTable'
								},
								pagination : true,
								paginationHAlign : "left",
								sidePagination : true,
								pageSize : 8,
								searchAlign : "left",
								data : tableData,
								height : height,
								theadClasses : "thead-dark" // 表頭顏色

							});// table.bootstrapTable
				}// success反括號
			}); // ajax反括號
}
