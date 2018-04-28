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

function searchProduct() {
	var showStart = $("input[name=showStart]").val();
	$('#StoreGrid').datagrid('load', {
		"labelTitle" : $("#labelTitle").val(),
		"product_name" : $("#product_name").val(),
		"product_code" : $("#product_code").val(),
		"labelType" : $("#labelType").val(),
		"showTime" : showStart
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/productlabel/queryProductLabelAllJson',

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
				title : "序号",
				field : "productLabelId",
				checkbox : true,
				width : 80
			}, {
				title : "标签标题",
				field : "labelTitle",
				width : 100
			}, {
				title : "商品名称",
				field : "product_name",
				width : 100
			}, {
				title : "商品状态",
				field : "product_status",
				width : 50,
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '上架';
					case 2:
						return '下架';
					case 3:
						return '未上架';
					}
				}

			}, {
				title : "商品编号",
				field : "product_code",
				width : 80,

			}, {
				title : "标签类型",
				field : "labelType",
				width : 100,
				formatter : function(value, row) {
					switch (value) {
					case 6:
						return '自定义';
					case 1:
						return '新品';
					case 2:
						return '拼团';
					case 3:
						return '抢购';
					case 4:
						return '减满';
					case 5:
						return '爆款';
					}
				}
			}, {
				title : "开始时间",
				field : "showStart",
				width : 80

			}, {
				title : "结束时间",
				field : "showEnd",
				width : 120
			}, {
				title : "状态",
				field : "status",
				width : 120,
				formatter : function(value, row) {
					switch (value) {
					case 0:
						return '正常';
					case 1:
						return '失效';
					case 2:
						return '删除';
					}
				}
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

function toEditProduct() {
	var centerTabs = parent.centerTabs;
	var product_id = boxDataGrid.datagrid('getSelected').productLabelId;
	if (product_id == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer
			+ "/admin/productlabel/toUpdateProductlabel?productLabelId="
			+ product_id;
	if (centerTabs.tabs('exists', '修改标签信息')) {
		centerTabs.tabs('select', '修改标签信息');
		var tab = centerTabs.tabs('getTab', '修改标签信息');
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
							title : '修改标签信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增商品
function toAddProduct() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/productlabel/toAddProductlabel";
	if (centerTabs.tabs('exists', '添加商品标签')) {
		centerTabs.tabs('select', '添加商品标签');
		var tab = centerTabs.tabs('getTab', '添加商品标签');
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
							title : '添加商品标签',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
function toDelProduct() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var productIdArray = "";
	$.each(objectArray, function(i, obj) {
		productIdArray += obj.productLabelId + ",";
	});
	if (productIdArray == '') {
		$.messager.alert('提示', "请先选择商品", 'error');
		return false;
	} else {
		if (productIdArray.lastIndexOf(",") != -1) {
			productIdArray = productIdArray.substring(0, productIdArray
					.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			$.ajax({
				url : mainServer + "/admin/productlabel/delProductlabel",
				data : {
					"productIdArray" : productIdArray
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					if (data.result == 'succ') {
						searchProduct();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}
