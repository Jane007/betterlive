var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var boxDataGrid;
$(function() {
	boxDataGrid = dataGrid.createGrid('dataGrid');
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

function queryList() {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();
	if (startTime > endTime) {
		alert("开始时间不能大于结束时间");
		return false;
	}
	$('#dataGrid').datagrid('load', {
		"orderSource" : $("#orderSource").val(),
		"startTime" : startTime,
		"endTime" : endTime
	});
}

function exportExcel() {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();
	var orderSource = $("#orderSource").val();
	location.href = mainServer
			+ "/admin/orderanaly/exportExcelByOrderSource?startTime="
			+ startTime + "&endTime=" + endTime + "&orderSource=" + orderSource;
}

var dataGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/orderanaly/hhQueryTotalByOrderSource',
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
							columns : [ [
									{
										title : "订单来源",
										field : "orderSource",
										width : 100
									},
									{
										title : "订单金额",
										field : "totalPrice",
										width : 100
									},
									{
										title : "实付金额",
										field : "totalPay",
										width : 100
									},
									{
										title : "订单份数",
										field : "orderCount",
										width : 100
									},
									{
										title : "商品份数",
										field : "productCount",
										width : 100
									},
									{
										title : "详细列表",
										field : "xiangXi",
										width : 100,
										formatter : function(value, row) {
											return '<a href="javascript:clickDetailedEntity(\''
													+ row.orderSource
													+ '\');" style="color:blue;">查看</a>';
										}
									} ] ],
							toolbar : '#dataToolbar',
							onBeforeLoad : function(param) {
								$('#dataGrid').datagrid('clearSelections');
							},
							onLoadSuccess : function(data) {
								// initCUIDBtn();
							},
							onSelect : function(rowIndex, rowData) {
								// initCUIDBtn();
							},
							onUnselect : function(rowIndex, rowData) {
								// initCUIDBtn();
							},
							onCheckAll : function(rowIndex, rowData) {
								// initCUIDBtn();
							}

						});
	}
};

function clickDetailedEntity(value) {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();

	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/order/findList?orderSource=" + value
			+ "&startTime=" + startTime + "&endTime=" + endTime;
	if (centerTabs.tabs('exists', '订单列表')) {
		centerTabs.tabs('select', '订单列表');
		var tab = centerTabs.tabs('getTab', '订单列表');
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
							title : '订单列表',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}