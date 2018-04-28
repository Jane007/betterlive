var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

function myparser(s) {
	if (!s)
		return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}

function searchLabel() {
	var showStart = $("input[name=showStart]").val();
	$('#StoreGrid').datagrid('load', {
		"labelId" : $("#labelId").val(),
		"startTime" : $("#startTime").val(),
		"endTime" : $("#endTime").val(),
		"showTime" : showStart
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid(
				{
					url : mainServer
							+ '/admin/label/querySearchLabelAllJson?labelId='
							+ $("#labelId").val() + "&startTime="
							+ $("#startTime").val() + "&endTime="
							+ $("#endTime").val(),

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
					columns : [ [ {
						title : "客户昵称",
						field : "nickName",
						width : 100,
						formatter : function(value, row) {
							var resl = "游客";
							if (value != null && value != "") {
								resl = value;
							}
							return resl;
						}
					}, {
						title : "创建时间",
						field : "createTime",
						width : 100

					}

					] ],
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
					},
					onCheckAll : function(rowIndex, rowData) {
						initCUIDBtn();
					}
				});
	}
};

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况

		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
	}
}
