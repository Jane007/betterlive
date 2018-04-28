var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchVideoType() {
	$('#StoreGrid').datagrid('load', {
		"typeName" : $("#typeName").val(),
		"status" : $("#status").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/specialvideotype/queryVideoTypeAllJson',
							rownumbers : true,
							singleSelect : false,
							pagination : true,
							autoRowHeight : true,
							fit : true,
							fitColumns : true, // 自动使列适应表格宽度以防止出现水平滚动。
							striped : true,
							checkOnSelect : true, // 点击某一行时，则会选中/取消选中复选框
							selectOnCheck : true, // 点击复选框将会选中该行
							collapsible : true,
							showFooter : true,
							columns : [ [
									{
										title : "序号",
										field : "typeId",
										checkbox : true,
										width : 100
									},
									{
										title : "视频分类名称",
										field : "typeName",
										width : 100
									},
									{
										title : "排序",
										field : "sort",
										width : 100
									},
//									{
//										title : "分类图",
//										field : "typeCover",
//										width : 100,
//										formatter : function(value, rec) {// 使用formatter格式化刷子
//											return '<img src=' + value
//													+ ' style="height:80px;"/>';
//										}
//									},
									{
										title : "状态",
										field : "status",
										width : 100,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var showValue = "已发布";
											if (value == 0) {
												showValue = "已失效";
											}
											return showValue;
										}
									} ] ],
							toolbar : '#storeToolbar',
							onBeforeLoad : function(param) {
								$('#StoreGrid').datagrid('clearSelections');
							},
							onLoadSuccess : function(data) {
								initCUIDBtn();
							},
							onSelect : function(rowIndex, rowData) {
								initCUIDBtn();
							},
							onUnselect : function(rowIndex, rowData) {
								initCUIDBtn();
							}
						});
	}
};

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn1');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn2');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn1');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn2');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn1');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn2');
	}
}
function toEditVideoType() {
	var centerTabs = parent.centerTabs;
	var typeId = boxDataGrid.datagrid('getSelected').typeId;
	if (typeId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer
			+ "/admin/specialvideotype/toEditVideoType?typeId=" + typeId;
	if (centerTabs.tabs('exists', '修改视频分类')) {
		centerTabs.tabs('select', '修改视频分类');
		var tab = centerTabs.tabs('getTab', '修改视频分类');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '修改视频分类',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增商品
function toAddVideoType() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/specialvideotype/toAddVideoType";
	if (centerTabs.tabs('exists', '添加视频分类')) {
		centerTabs.tabs('select', '添加视频分类');
		var tab = centerTabs.tabs('getTab', '添加视频分类');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '添加视频分类',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
// 逻辑删除专题
function toDelVideoType(value) {
	$.messager.confirm("提示", "你确认此操作吗?", function(r) {
		if (r) {
			var typeId = boxDataGrid.datagrid("getSelected").typeId;
			$.ajax({
				url : mainServer + "/admin/specialvideotype/editTypeStatus",
				data : {
					"typeId" : typeId,
					"status" : value
				},
				datatype : "json",
				type : "post",
				success : function(data) {

					if (data.code == '1010') {
						searchVideoType();
					} else {
						$.messager.alert('提示', data.msg, 'error');
					}

				}

			});
		}
	});
}