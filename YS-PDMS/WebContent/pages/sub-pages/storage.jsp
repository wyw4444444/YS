<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
	<div id="toolbar">
		<div class="form-inline">
			<div class="form-group">
				<button class="btn btn-primary" onclick="loadStorage('1')">所有库存明细</button>&nbsp;
				<button class="btn btn-primary" onclick="loadStorageByPartCode()">分料号库存明细</button>&nbsp;
				<button class="btn btn-warning" onclick="loadStorageByPartCodeSafety()">安全库存确认</button>&nbsp;
			</div>
			<div class="form-group">
				<div class="input-group">
					<select class="form-control" id="storageSearchTypeList" edit="true">
						<option value="undefined">--- 请选择 ---</option>
						<option value="part_code">品号</option>
					</select>
					<select class="form-control" id="OtherColumnSearchStorageList" class="selectpicker" data-live-search="true" data-live-search-placeholder="Search">
					</select>
					<button class="btn btn-secondary form-control" id="btn-search-storage" onclick="loadStorage('2')">查询</button>&nbsp;
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-prepend">
						<span class="input-group-text">库存金额：</span>
					</div>
					<input type="text" class="form-control" id="storageAmount" disabled="disabled" style="text-align: right; width: 130px;"/>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-10 col-lg-10">
		<table id="contentStorage" class="table-sm table-striped">
		</table>
	</div>
</body>
</html>
