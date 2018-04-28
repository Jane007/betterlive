var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

function searchProductType() {
	var startTime = $("input[name=start]").val();
	var endTime = $("input[name=end]").val();
	if (startTime > endTime) {
		showvaguealert("开始时间不能大于结束时间");
		return false;
	}
	$('#StoreGrid').datagrid('load', {
		"mobile" : $("#customerMobile").val(),
		"nickname" : $("#customerName").val(),
		"isBinding" : $("#isBinding").val(),
		"start" : $("input[name='start']").val(),
		"end" : $("input[name='end']").val(),
		"source" : $("#orderSource").val(),
		"existsOrder" : $("#existsOrder").val()
	});
}

function exportExcel() {
	var mobile = $("#customerMobile").val();
	var nickname = $("#customerName").val();
	var isBinding = $("#isBinding").val();
	var source = $("#orderSource").val();
	var existsOrder = $("#existsOrder").val();
	var start = $("input[name=start]").val();
	var end = $("input[name=end]").val();
	location.href = mainServer + "/admin/customer/exportExcel?mobile=" + mobile
			+ "&nickname=" + nickname + "&isBinding=" + isBinding
			+ "&nickname=" + nickname + "&start=" + start + "&end=" + end
			+ "&source=" + source + "&existsOrder=" + existsOrder;
}
var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/customer/queryCustomerAllJson',
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
										field : "recordId",
										checkbox : true,
										width : '5%'
									},
									{
										title : "用户手机",
										field : "mobile",
										width : '10%',
									},
									{
										title : "用户昵称",
										field : "nickname",
										width : '10%',
									},

									{
										title : "用户头像",
										field : "head_url",
										width : '10%',
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<img src="'
													+ value
													+ '" style="width:80px;height:80px"/>'
										}
									},
									{
										title : "用户来源",
										field : "source",
										width : '10%',

									},

									{
										title : "创建时间",
										field : "create_time",
										width : '12%'
									},
									{
										title : "操作",
										field : "aa",
										width : '12%',
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<a href="javascript:buyhistory('
													+ rec.customer_id
													+ ');">历史购买记录</a>'
										}
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

function buyhistory(customerId) {
	var centerTabs = parent.centerTabs;

	console.log(customerId);

	var url = mainServer + "/admin/customer/findBuyList?customerId="
			+ customerId;
	if (centerTabs.tabs('exists', '历史购买记录')) {
		centerTabs.tabs('select', '历史购买记录');
		var tab = centerTabs.tabs('getTab', '历史购买记录');
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
							title : '历史购买记录',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}