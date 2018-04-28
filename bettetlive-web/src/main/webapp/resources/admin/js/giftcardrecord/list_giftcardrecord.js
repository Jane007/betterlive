var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchProductType() {
	$('#StoreGrid').datagrid('load', {
		"customerMobile" : $("#customerMobile").val(),
		"cardNo" : $("#cardNo").val(),
		"customerName" : $("#customerName").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/giftcardrecord/queryGiftCardAllJson',
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
				field : "recordId",
				checkbox : true,
				width : '5%'
			}, {
				title : "礼品卡卡号",
				field : "cardNo",
				width : '12%'
			}, {
				title : "用户手机",
				field : "customerMobile",
				width : '10%',
			}, {
				title : "用户昵称",
				field : "customerName",
				width : '10%',
			},

			{
				title : "订单编号",
				field : "orderNo",
				width : '25%'
			}, {
				title : "记录类型",
				field : "recordType",
				width : '10%',
				formatter : function(value, row) {
					// 类型：1：自定义链接，2：单个商品，3：活动
					if (value == 1) {
						return "添加礼品卡";
					} else if (value == 2) {
						return "消费";
					}
				}
			}, {
				title : "金额",
				field : "money",
				width : '8%',
			}, {
				title : "记录时间",
				field : "recordTime",
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