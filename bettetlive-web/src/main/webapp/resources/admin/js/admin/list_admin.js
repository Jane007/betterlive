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

function searchAdmin() {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();
	if (startTime > endTime) {
		alert("开始时间不能大于结束时间");
		return false;
	}
	$('#StoreGrid').datagrid('load', {
		"username" : $("#username").val(),
		"startTime" : startTime,
		"endTime" : endTime
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/admin/queryAdminAllJson',

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
				field : "staffId",
				checkbox : true,
				width : 100,
			}, {
				title : "用户名",
				field : "username",
				width : 100,
			}, {
				title : "手机号码",
				field : "mobile",
				width : 100,
			}, {
				title : "创建人",
				field : "createName",
				width : 100,
			}, {
				title : "创建时间",
				field : "createTime",
				width : 100,
				formatter : function(value, row) {
					var time = new Date(row.createTime);
					return time.toLocaleString();
				}

			}, {
				title : "角色名称",
				field : "roleName",
				width : 100,

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

function toEditAdmin() {
	var centerTabs = parent.centerTabs;
	var staff_id = boxDataGrid.datagrid('getSelected').staffId;
	if (staff_id == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer+"/admin/admin/toUpdateAdmin?staffId=" + staff_id;
	if (centerTabs.tabs('exists', '修改管理员信息')) {
		centerTabs.tabs('select', '修改管理员信息');
		var tab = centerTabs.tabs('getTab', '修改管理员信息');
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
							title : '修改管理员信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增管理员
function toAddAdmin() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/admin/toAddAdmin";
	if (centerTabs.tabs('exists', '添加管理员')) {
		centerTabs.tabs('select', '添加管理员');
		var tab = centerTabs.tabs('getTab', '添加管理员');
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
							title : '添加管理员',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
function toDelAdmin() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var staffIdArray = "";
	$.each(objectArray, function(i, obj) {
		staffIdArray += obj.staffId + ",";
	});
	if (staffIdArray == '') {
		$.messager.alert('提示', "请先选择管理员", 'error');
		return false;
	} else {
		if (staffIdArray.lastIndexOf(",") != -1) {
			staffIdArray = staffIdArray.substring(0, staffIdArray
					.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			$.ajax({
				url : mainServer + "/admin/admin/delAdmin",
				data : {
					"staffIdArray" : staffIdArray
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					if (data.result == 'succ') {
						searchAdmin();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}
				}
			});
		}
	});
}