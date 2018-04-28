var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchMenu() {
	$('#StoreGrid').datagrid('load', {
		"menuName" : $("#menuName").val(),
		"ifParent" : $("#ifParent").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/menu/queryMenuAllJson',

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
				title : "主键id",
				field : "menuId",
				checkbox : true,
				width : 100,
			}, {
				title : "父级菜单",
				field : "parentName",
				width : 100
			}, {
				title : "菜单名称",
				field : "menuName",
				width : 100,
			}, {
				title : "菜单类型",
				field : "menuType",
				width : 100,
				formatter : function(value, row) {
					switch (parseInt(value)) {
					case 1:
						return '菜单';
					case 2:
						return '按钮';
					}
				}
			}, {
				title : "菜单链接地址",
				field : "menuUrl",
				width : 100,
			}, {
				title : "菜单展示顺序",
				field : "menuOrd",
				width : 100,
			}, {
				title : "菜单状态",
				field : "status",
				width : 100,
				formatter : function(value, row) {
					switch (parseInt(value)) {
					case 0:
						return '无效';
					case 1:
						return '有效';
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
function toEditMenu() {
	var centerTabs = parent.centerTabs;
	var menu_id = boxDataGrid.datagrid('getSelected').menuId;
	if (menu_id == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer + "/admin/menu/toUpdateMenu?menuId=" + menu_id;
	if (centerTabs.tabs('exists', '修改菜单信息')) {
		centerTabs.tabs('select', '修改菜单信息');
		var tab = centerTabs.tabs('getTab', '修改菜单信息');
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
							title : '修改菜单信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增菜单
function toAddMenu() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/menu/toAddMenu";
	if (centerTabs.tabs('exists', '添加菜单')) {
		centerTabs.tabs('select', '添加菜单');
		var tab = centerTabs.tabs('getTab', '添加菜单');
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
							title : '添加菜单',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
function toDelMenu() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var menuIdArray = "";
	$.each(objectArray, function(i, obj) {
		menuIdArray += obj.menuId + ",";
	});
	if (menuIdArray == '') {
		$.messager.alert('提示', "请先选择菜单", 'error');
		return false;
	} else {
		if (menuIdArray.lastIndexOf(",") != -1) {
			menuIdArray = menuIdArray
					.substring(0, menuIdArray.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			$.ajax({
				url : mainServer + "/admin/menu/delMenu",
				data : {
					"menuIdArray" : menuIdArray
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					if (data.result == 'succ') {
						searchMenu();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}
