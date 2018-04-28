var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var boxDataGrid;
$(function() {
	$('#importExcel').dialog('close');

	if (startTime != undefined && startTime != null && startTime != "") {
		$('#start').datetimebox('setValue', startTime);
	}

	if (endTime != undefined && endTime != null && endTime != "") {
		$('#end').datetimebox('setValue', endTime);
	}

	if (customerSource != undefined && customerSource != null
			&& customerSource != "") {
		$('#customerSource').textbox("setValue", customerSource);
	}

	if (orderSource != undefined && orderSource != null && orderSource != "") {
		$('#orderSource').textbox("setValue", orderSource);
	}

	boxDataGrid = StoreGrid.createGrid('StoreGrid');

	// queryOrderList();
});

function parsedate(value) {
	var date = new Date(value);
	var year = date.getFullYear();
	var month = date.getMonth() + 1; // 月份+1
	var day = date.getDate();
	var hour = date.getHours();
	var minutes = date.getMinutes();
	var second = date.getSeconds();
	return day + "/" + month + "/" + year + " " + hour + ":" + minutes + ":"
			+ second;
}

function queryOrderList() {
	var startTime = $("input[name=start]").val();
	var endTime = $("input[name=end]").val();
	if (startTime > endTime) {
		alert("开始时间不能大于结束时间");
		return false;
	}
	// var status=$("#orderStatusSearch").val();
	var hasBuy = "";
	// if(status==null || status==''){
	hasBuy = "hasBuy";
	// }
	$('#StoreGrid').datagrid('load', {
		"orderCode" : $("#orderCodeSearch").val(),
		"status" : status,
		"mobile" : $("#mobile").val(),
		"customerName" : $("#customername").val(),
		"start" : $("input[name=start]").val(),
		"end" : $("input[name=end]").val(),
		"orderSource" : $("#orderSource").val(),
		"customerSource" : $("#customerSource").val(),
		"customerMobile" : $("#customerMobile").val(),
		"hasBuy" : hasBuy,
		"productId" : productId

	});
}
function clearForm() {
	$("#storeToolbar").form('clear');
}
function exportExcel() {
	var orderCode = $("#orderCodeSearch").val();
	var hasBuy = "hasBuy";
	var mobile = $("#mobile").val();
	var customerName = $("#customername").val();
	var start = $("input[name=start]").val();
	var end = $("input[name=end]").val();
	var customerMobile = $("#customerMobile").val();
	var msg = "请选择是否导出";
	if (confirm(msg) == true) {
		$('.vaguealert').show();
		$('.vaguealert').find('p').html("正在导出中，请耐心等待。。。");
		setTimeout(function(){
			$('.vaguealert').hide();
		},2000);
		location.href = mainServer
				+ "/admin/order/exportExcel?orderCode="
				+ $("#orderCodeSearch").val() + '&mobile=' + $("#mobile").val()
				+ '&customerName=' + $("#customername").val() + '&start='
				+ $("input[name=start]").val() + '&end='
				+ $("input[name=end]").val() + '&orderSource='
				+ $("#orderSource").val() + '&customerSource='
				+ $("#customerSource").val() + '&customerMobile='
				+ $("#customerMobile").val() + '&hasBuy=hasBuy&productId='
				+ productId;
	}
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid(
				{
					url : mainServer
							+ '/admin/order/queryOrderAllJson?orderCode='
							+ $("#orderCodeSearch").val() + '&mobile='
							+ $("#mobile").val() + '&customerName='
							+ $("#customername").val() + '&start='
							+ $("input[name=start]").val() + '&end='
							+ $("input[name=end]").val() + '&orderSource='
							+ $("#orderSource").val() + '&customerSource='
							+ $("#customerSource").val() + '&customerMobile='
							+ $("#customerMobile").val()
							+ '&hasBuy=hasBuy&productId=' + productId,
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
						field : "order_id",
						checkbox : true,
						width : '5%'
					}, {
						title : "订单编号",
						field : "order_code",
						width : '13%'
					}, {
						title : "订单金额",
						field : "total_price",
						width : '11%'
					}, {
						title : "支付金额",
						field : "pay_money",
						width : '11%'

					}, {
						title : "优惠券金额",
						field : "conpon_money",
						width : '11%'
					}, {
						title : "礼品卡金额",
						field : "gitf_card_money",
						width : '11%'

					}, {
						title : "用户",
						field : "customer_name",
						width : '11%'

					}, {
						title : "支付方式",
						field : "pay_type",
						width : '12%',
						formatter : function(value, row) {
							switch (value) {
							case 1:
								return '微信';
							case 2:
								return '支付宝';
							case 3:
								return '招行一网通';
							}
						}
					}/*
						 * ,{ title:"订单状态", field:"status", width:'11%',
						 * formatter: function(value, row) { switch(value){ case
						 * 0:return'已删除'; case 1:return'待付款'; case
						 * 2:return'待发货'; case 3:return'待签收'; case
						 * 4:return'待评价'; case 5:return'已完成'; case
						 * 6:return'已取消'; case 7:return'已退款'; } }
						 *  }
						 */, {
						title : "下单时间",
						field : "order_time",
						width : '16%'

					}, {
						title : "用户来源",
						field : "customerSource",
						width : '16%'

					}, {
						title : "订单来源",
						field : "orderSource",
						width : '16%'

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
	var checkedRows = boxDataGrid.datagrid('getChecked');
	var orderStatus = $("#orderStatusSearch").val();
	if (rows.length == 1) {
		var status = boxDataGrid.datagrid('getSelected').status;
		$('#updConfBtn').linkbutton('enable');

		if (status == 1) {
			$('#conOrder').linkbutton('enable');
			$('#conSend').linkbutton('disable');
			$('#conremove').linkbutton('enable');

		} else if (status == 2) {
			// $('#conSend').linkbutton('enable');
			$('#conOrder').linkbutton('enable');
			$('#conFinish').linkbutton('disable');
			// $('#conremove').linkbutton('enable');

		} else if (status == 3) {
			$('#conFinish').linkbutton('enable');
			$('#conSend').linkbutton('disable');
			$('#conOrder').linkbutton('disable');
			$('#conremove').linkbutton('enable');
		}
	} else if (rows.length == 0) {
		$('#updConfBtn').linkbutton('disable');
		$('#conOrder').linkbutton('disable');
		/* $('#conSend').linkbutton('disable'); */
		$('#conFinish').linkbutton('disable');
		/* $('#conremove').linkbutton('disable'); */
	} else if (rows.length > 1 || checkedRows > 1) {
		if (orderStatus == 2) {
			boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
			boxDataGrid.datagrid("enableToolbarBtn", 'conOrder');
		}
		$('#conOrder').linkbutton('disable');
		$('#updConfBtn').linkbutton('disable');

	}

}

function findOrderInfo() {
	var orderId = boxDataGrid.datagrid('getSelected').order_id;
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/order/queryOrderDetails?orderId=" + orderId
			+ "&type=1";
	if (centerTabs.tabs('exists', '单个订单详情')) {
		centerTabs.tabs('select', '单个订单详情');
		var tab = centerTabs.tabs('getTab', '单个订单详情');
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
							title : '单个订单详情',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

function confirmFaHuo() {
	var orderId = boxDataGrid.datagrid('getSelected').order_id;
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/order/queryOrderDetails?orderId=" + orderId
			+ "&type=2";
	if (centerTabs.tabs('exists', '商品发货')) {
		centerTabs.tabs('select', '商品发货');
		var tab = centerTabs.tabs('getTab', '商品发货');
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
							title : '商品发货',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

function upStatus(status) {
	var orderId = boxDataGrid.datagrid('getSelected').order_id;
	var orderCode = boxDataGrid.datagrid('getSelected').order_code;
	$.messager.confirm('确认框', '你确认此操作吗?', function(r) {
		if (r) {
			$
					.ajax({
						url : mainServer + "/admin/order/updateOrderStatus",
						type : "POST",
						async : false,
						data : {
							"orderId" : orderId,
							"status" : status,
							"orderCode" : orderCode
						},
						success : function(data) {
							$.messager.alert('提示消息', '操作成功', 'success');
							window.location.href = mainServer
									+ "/admin/order/findList";
						},
						error : function() {
							alert("异常！");
						}
					});
		}
	})
}
function upStatusSend(status) {
	var objectArray = boxDataGrid.datagrid('getSelections');
	// var orderCode= boxDataGrid.datagrid('getSelected').order_code;
	// console.log(orderId);
	var orderIdArray = "";
	$.each(objectArray, function(i, obj) {
		orderIdArray += obj.order_id + ",";
	});
	console.log(orderIdArray);
	$.messager.confirm('确认框', '你确认此操作吗?', function(r) {
		if (r) {
			$.ajax({
					url : mainServer + "/admin/order/updateOrderStatus",
					type : "POST",
					async : false,
					traditional : true,
					data : {
						"orderIdArray" : orderIdArray,
						"status" : status,
					// "orderCode":orderCode
					},
					success : function(data) {
						$.messager.alert('提示消息', '操作成功', 'success');
						window.location.href = mainServer
								+ "/admin/order/findList";
					},
					error : function() {
						alert("异常！");
					}
				});
		}
	})
}

function uploadExcel() {
	$("#uploadExcel").form('submit');
}

