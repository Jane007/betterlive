var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});
$('#start').datetimebox({
	required : false,
	showSeconds : true
});
$('#end').datetimebox({
	value : '3/4/2010 00:00:00',
	required : true,
	showSeconds : true
});

function searchOrder() {
	$('#StoreGrid').datagrid('load', {
		"mobile" : $("#mobile").val(),
		"orderCode" : $("#orderCode").val(),
		"status" : $("#orderStatusSearch").val(),
		"start" : $("input[name=start]").val(),
		"end" : $("input[name=end]").val()
	});
}

function exportExcel() {
	var orderCode = $("#orderCode").val();
	var status = $("#orderStatusSearch").val();
	var mobile = $("#mobile").val();
	var start = $("input[name=start]").val();
	var end = $("input[name=end]").val();
	location.href = mainServer + "/admin/order/exportExcel?orderCode="
			+ orderCode + "&status=" + status + "&mobile=" + mobile
			+ "&customerId=" + customerId + "&start=" + start + "&end=" + end;
}
var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/customer/queryOrderAllJson?customerId='+customerId,
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
										title : "序号",
										field : "order_id",
										checkbox : true,
										width : '5%'
									},

									{
										title : "订单编号",
										field : "order_code",
										width : '15%',
									},
									{
										title : "订单详情(名称 规格X数量X价格)",
										field : "aaa",
										width : '18%',
										formatter : function(value, rec) {
											var str = "";
											var list = rec.listOrderProductVo;
											if (list != null && list != '') {

												$
														.each(
																list,
																function(i,
																		product) {
																	if (product != null) {
																		var price = 0;
																		if (product.price != null
																				&& product.price != '') {
																			price = Number(
																					product.price)
																					.toFixed(
																							2);
																		}
																		str += product.product_name
																				+ "&nbsp;"
																				+ product.spec_name
																				+ " X "
																				+ product.quantity
																				+ " X "
																				+ price
																				+ "<br/>";
																	}

																});
											}
											return str;
										}
									},

									{
										title : "支付方式",
										field : "pay_type",
										width : '8%',
										formatter : function(value, rec) {
											if (value == 1) {
												return '微信';
											} else if(value == 2) {
												return '支付宝';
											} else if(value == 3) {
												return '招行一网通';
											}
										}
									},
									{
										title : "运费",
										field : "freight",
										width : '8%',
										formatter : function(value) {
											if (value != null && value != '') {
												return Number(value).toFixed(2);
											}

										}
									},
									{
										title : "总价",
										field : "total_price",
										width : '10%',
										formatter : function(value) {
											if (value != null && value != '') {
												return Number(value).toFixed(2);
											}
										}
									},
									{
										title : "实际支付",
										field : "pay_money",
										width : '10%',
										formatter : function(value) {
											if (value != null && value != '') {
												return Number(value).toFixed(2);
											}

										}
									}, {
										title : "收货人",
										field : "receiver",
										width : '8%'
									}, {
										title : "收货人手机号",
										field : "mobile",
										width : '10%'
									}, {
										title : "收货人地址",
										field : "address",
										width : '30%'
									}, {
										title : "创建时间",
										field : "order_time",
										width : '12%'
									} ] ],
							toolbar : '#storeToolbar',
							onBeforeLoad : function(param) {
								$('#StoreGrid').datagrid('clearSelections');
							},
							onLoadSuccess : function(data) {

							},
							onSelect : function(rowIndex, rowData) {

							},
							onUnselect : function(rowIndex, rowData) {

							}
						});
	}
};
