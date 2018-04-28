function expandNodeUser(e) {
	var zTree = $.fn.zTree.getZTreeObj("mtree"), type = e.data.type;
	if (type == "expandAll") {
		zTree.expandAll(true);
	} else if (type == "collapseAll") {
		zTree.expandAll(false);
	}
}

function ajaxDataFilter(treeId, parentNode, data) {
	return data.rows;
}

var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchRole() {
	$('#StoreGrid').datagrid('load', {
		"roleName" : $("#roleName").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer + '/admin/role/queryRoleAllJson',

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
										title : "主键id",
										field : "roleId",
										checkbox : true,
										width : 100,
									},
									{
										title : "角色名称",
										field : "roleName",
										width : 100,
									},
									{
										title : "操作",
										field : "do",
										width : 100,
										formatter : function(value, row) {
											return '<a href="javascript:clickDetailedEntity('
													+ row.roleId
													+ ',\''
													+ row.roleName
													+ '\');" style="color:blue;">分配菜单</a>';
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

function toEditRole() {
	var centerTabs = parent.centerTabs;
	var role_id = boxDataGrid.datagrid('getSelected').roleId;
	if (role_id == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer + "/admin/role/toUpdateRole?roleId=" + role_id;
	if (centerTabs.tabs('exists', '修改角色信息')) {
		centerTabs.tabs('select', '修改角色信息');
		var tab = centerTabs.tabs('getTab', '修改角色信息');
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
							title : '修改角色信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增角色
function toAddRole() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/role/toAddRole";
	if (centerTabs.tabs('exists', '添加角色')) {
		centerTabs.tabs('select', '添加角色');
		var tab = centerTabs.tabs('getTab', '添加角色');
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
							title : '添加角色',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
function toDelRole() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var roleIdArray = "";
	$.each(objectArray, function(i, obj) {
		roleIdArray += obj.roleId + ",";
	});
	if (roleIdArray == '') {
		$.messager.alert('提示', "请先选择角色", 'error');
		return false;
	} else {
		if (roleIdArray.lastIndexOf(",") != -1) {
			roleIdArray = roleIdArray
					.substring(0, roleIdArray.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			$.ajax({
				url : mainServer + "/admin/role/delRole",
				data : {
					"roleIdArray" : roleIdArray
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					if (data.result == 'succ') {
						searchRole();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}
function clickDetailedEntity(value, value2) {
	$("#mtree").find("li").remove();

	$('#roleIdTree').val(value);
	$("#roleName_msg").html(value2);
	$('#dlg').dialog('open');
	var zSetting = {
		check : {
			chkStyle : "checkbox",
			enable : true
		},
		data : {
			simpleData : {
				enable : true,
			}
		},
		async : {
			enable : true,
			url : mainServer + "/admin/menu/queryMenuByRoleId?roleId="
					+ $("#roleIdTree").val(),
			dataFilter : ajaxDataFilter,
			dataType : 'json'
		}
	};

	$.fn.zTree.init($("#mtree"), zSetting);
	$("#openTree").on("click", {
		type : "expandAll"
	}, expandNodeUser);
	$("#closeTree").on("click", {
		type : "collapseAll"
	}, expandNodeUser);
}
// 获取选中节点
function onCheck() {
	var rid = $("#roleIdTree").val();
	var treeObj = $.fn.zTree.getZTreeObj("mtree");
	nodes = treeObj.getCheckedNodes(true);
	var ids = "";
	for (var i = 0; i < nodes.length; i++) {
		// 获取选中节点的值
		ids += nodes[i].id + ",";
	}
	// window.location.href =
	// "${mainserver}/admin/role/roleMenuChange?rid="+rid+"&ids="+ids;
	ajaxSubmit(rid, ids)
}
function ajaxSubmit(rid, ids) {
	if (ids == '') {
		$.messager.alert('提示', "请先选择菜单", 'error');
		return false;
	} else {
		if (ids.lastIndexOf(",") != -1) {
			ids = ids.substring(0, ids.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要重新分配菜单吗?", function(r) {
		if (r) {
			$.ajax({
				url : mainServer + "/admin/role/roleMenuChange",
				data : {
					"rid" : rid,
					"ids" : ids
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					if (data.result == 'succ') {
						$('#dlg').dialog('close');
					} else {
						$.messager.alert('提示', data.msg, 'error');
					}

				}

			});
		}
	});
}