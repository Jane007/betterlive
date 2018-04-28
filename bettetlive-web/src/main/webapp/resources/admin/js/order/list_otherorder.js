var boxDataGrid;
$(function() {
	$('#importExcel').dialog('close');
	if (startTime != undefined && startTime != null && startTime != "") {
		$('#startTime').datebox('setValue', startTime);
	}

	if (endTime != undefined && endTime != null && endTime != "") {
		$('#endTime').datebox('setValue', endTime);
	}

	if (customerSource != undefined && customerSource != null
			&& customerSource != "") {
		$('#customerSource').textbox("setValue", customerSource);
	}

	if (orderSource != undefined && orderSource != null && orderSource != "") {
		$('#orderSource').textbox("setValue", orderSource);
	}
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
	queryOrderList();
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

function queryOrderList() {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();
	if (startTime > endTime) {
		alert("开始时间不能大于结束时间");
		return false;
	}
	$('#StoreGrid').datagrid('load', {
		"orderCode" : $("#orderCode").val(),
		"payCode" : $("#payCode").val(),
		"orderMobile" : $("#orderMobile").val(),
		"customerName" : $("#customerName").val(),
		"startTime" : startTime,
		"endTime" : endTime,
		"customerSource" : $("#customerSource").val(),
		"orderSource" : $("#orderSource").val()

	});
}
function clearForm() {
	$("#storeToolbar").form('clear');
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid(
				{
					url : mainServer
							+ '/admin/otherorder/queryOrderAllJson?orderCode='
							+ $("#orderCodeSearch").val() + '&status=' + status
							+ '&mobile=' + $("#mobile").val()
							+ '&customerName=' + $("#customername").val()
							+ '&start=' + $("input[name=start]").val()
							+ '&end=' + $("input[name=end]").val()
							+ '&orderSource=' + $("#orderSource").val()
							+ '&customerSource=' + $("#customerSource").val()
							+ '&customerMobile=' + $("#customerMobile").val()
							+ '&hasBuy=hasBuy&productCode=' + productCode,
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
					columns : [ [ {
						title : "序号",
						field : "orderId",
						checkbox : true,
						width : '5%'
					}, {
						title : "支付编号",
						field : "payCode",
						width : '10%'
					}, {
						title : "订单编号",
						field : "orderCode",
						width : '10%'
					}, {
						title : "商品名称",
						field : "productName",
						width : '10%'
					}, {
						title : "商品规格",
						field : "specName",
						width : '10%'
					}, {
						title : "下单数量",
						field : "quantity",
						width : '10%'
					}, {
						title : "单价",
						field : "price",
						width : '10%'
					}, {
						title : "订单金额",
						field : "totalPrice",
						width : '10%'
					}, {
						title : "实付金额",
						field : "payMoney",
						width : '10%'

					}, {
						title : "下单时间",
						field : "createTime",
						width : '10%'

					}, {
						title : "支付时间",
						field : "payTime",
						width : '10%'

					}, {
						title : "收货手机",
						field : "orderMobile",
						width : '10%'

					}, {
						title : "收货人",
						field : "customerName",
						width : '10%'

					}, {
						title : "发货时间",
						field : "sendTime",
						width : '10%'

					}, {
						title : "物流编号",
						field : "logisticsCode",
						width : '10%'

					}, {
						title : "用户来源",
						field : "customerSource",
						width : '10%'

					}, {
						title : "订单来源",
						field : "orderSource",
						width : '10%'

					} ] ],
					toolbar : '#storeToolbar',
					onBeforeLoad : function(param) {
						$('#StoreGrid').datagrid('clearSelections');
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

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	// var rows = boxDataGrid.datagrid('getSelections');
	// var checkedRows = boxDataGrid.datagrid('getChecked');
}

function uploadExcel() {
	$("#uploadExcel").form('submit');
}
