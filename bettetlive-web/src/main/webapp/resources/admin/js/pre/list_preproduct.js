var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchProduct() {
	$('#StoreGrid').datagrid('load', {
		"preName" : $("#preName").val(),
		"status" : $("#status").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid(
				{
					url : mainServer
							+ '/admin/preproduct/queryPreProductAllJson',
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
								field : "preId",
								checkbox : true,
								width : 100
							},
							{
								title : "预售标题",
								field : "preName",
								width : 150
							},
							{
								title : "规格--预售价",
								field : "aa",
								width : 170,
								formatter : function(value, rec, rowIndex) {
									var list = rec.activityProductVo;
									var str = "";
									if (list != null && list != "") {
										$.each(list, function(i, activity) {
											str += activity.spec_name + '--'
													+ activity.activity_price
													+ "<br/>"
										});
									}
									return str;
								}
							}, {
								title : "众筹目标",
								field : "raiseTarget",
								width : 60
							}, {
								title : "预售开始时间",
								field : "raiseStart",
								width : 125
							}, {
								title : "预售结束时间",
								field : "raiseEnd",
								width : 125
							}, {
								title : "已筹金额",
								field : "raiseMoney",
								width : 60
							}, {
								title : "支付人数",
								field : "supportNum",
								width : 60
							}, {
								title : "发货时间",
								field : "deliverTime",
								width : 135
							}, {
								title : "排序",
								field : "rankOrder",
								width : 50
							}, {
								title : "状态",
								field : "status",
								width : 100,
								formatter : function(value, row) {
									if (value == 1) {
										return "已上架";
									} else if (value == 2) {
										return "已下架";
									} else if (value == 2) {
										return "已删除";
									}
								}
							}, {
								title : "限购数量",
								field : "limitBuy",
								width : 50
							}, {
								title : "创建时间",
								field : "createTime",
								width : 140

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

// 查看详细、添加预售、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
	}
}

function toEditProduct() {
	var centerTabs = parent.centerTabs;
	var preId = boxDataGrid.datagrid('getSelected').preId;
	if (preId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer + "/admin/preproduct/toEditPreProduct?preId=" + preId;
	if (centerTabs.tabs('exists', '修改预售商品信息')) {
		centerTabs.tabs('select', '修改预售商品信息');
		var tab = centerTabs.tabs('getTab', '修改预售商品信息');
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
							title : '修改预售商品信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

function delProduct() {

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			var preId = boxDataGrid.datagrid("getSelected").preId;
			$.ajax({
				url : "delPreProduct",
				data : {
					"preId" : preId
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					var obj = data;
					if (1010 == obj.code) {
						toBack();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}
				}

			});
		}
	});
}
// 新增商品
function toAddProduct() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/preproduct/toAddPreProduct";
	if (centerTabs.tabs('exists', '添加预售商品信息')) {
		centerTabs.tabs('select', '添加预售商品信息');
		var tab = centerTabs.tabs('getTab', '添加预售商品信息');
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
							title : '添加预售商品信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

function toBack() {
	window.location.href = mainServer + "/admin/preproduct/findList";
}