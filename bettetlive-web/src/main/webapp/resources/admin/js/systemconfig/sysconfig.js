var boxDataGrid;
$(function() {
	boxDataGrid = SysconfigGrid.createGrid('sysconfigGrid');
});

var SysconfigGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/sysconfig/sysDictList',
			rownumbers : true,
			singleSelect : false,
			pagination : true,
			fit : true,// 自适应大小
			fitColumns : true, // 自动使列适应表格宽度以防止出现水平滚动。
			striped : true,
			checkOnSelect : true, // 点击某一行时，则会选中/取消选中复选框
			selectOnCheck : true, // 点击复选框将会选中该行
			collapsible : true,
			columns : [ [ {
				title : "",
				field : "sysDictId",
				checkbox : true
			}, {
				title : "参数编码",
				field : "dictCode",
				width : '12%'
			}, {
				title : "参数名称",
				field : "dictName",
				width : '10%'
			}, {
				title : "参数值",
				field : "dictValue",
				width : '6%'
			}, {
				title : "参数类型",
				field : "dictType",
				width : '10%',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '系统内置';
					case 2:
						return '管理员添加';
					case 3:
						return '不限';
					default:
						return '';
					}
				}
			}, {
				title : "参数状态",
				field : "status",
				width : '6%',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '有效';
					case 2:
						return '禁用';
					default:
						return '';
					}
				}

			}, {
				title : "参数描述",
				field : "description",
				width : '16%'
			} ] ],
			toolbar : '#sysconfigToolbar',
			onBeforeLoad : function(param) {
				$('#sysconfigGrid').datagrid('clearSelections');
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

function submitForm() {
	var spId = $('#sysconfigGrid').datagrid('getSelected').sysDictId;
	;
	$('#detail').form('submit', {
		url : mainServer + '/admin/sysconfig/save',
		ajax : true,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			data = typeof data == 'object' ? data : $.parseJSON(data);

			if (data.result == "succ") {
				$.messager.alert('提示消息', data.message, 'info');
				$("#updConfDlg").dialog("close");
				Store();
			} else {
				$.messager.alert('提示消息', data.message, 'error');
				$("#updConfDlg").dialog("close");
			}
		}
	});
}

function Store() {
	$('#sysconfigGrid').datagrid('load', {
		"dict_code" : $("#dict_code2").val(),
		"dict_name" : $("#dict_name2").val()
	});
}

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'deleteConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'deleteConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'deleteConfBtn');
	}
}

function addConfDlg() {
	$('#updConfDlg').dialog({
		title : '新增系统参数'
	});
	$('#updConfDlg').dialog('open');
	clearForm();
}
function updConfDlg() {
	$('#updConfDlg').dialog({
		title : '修改系统参数'
	});
	$('#updConfDlg').dialog('open');
	clearForm();
	var sys_dict_id = boxDataGrid.datagrid('getSelected').sysDictId;
	$.ajax({
		type : 'POST',
		url : mainServer + "/admin/sysconfig/queryDicById",
		data : {
			sys_dict_id : sys_dict_id
		},
		dataType : 'json',
		success : function(data) {
			var sysDictVo = data.sysDictVo;
			$("#sysDictId").val(sysDictVo.sysDictId);
			$("#dictCode").textbox('setValue', sysDictVo.dictCode);
			$("#dictName").textbox('setValue', sysDictVo.dictName);
			$('#dictType').combobox('setValue', sysDictVo.dictType);
			$("#dictValue").textbox('setValue', sysDictVo.dictValue);
			$('#status').combobox('setValue', sysDictVo.status);
			$('#description').val(sysDictVo.description);
		},
		error : function() {
			alert("异常！");
		}
	});
}

function deleteConfDlg() {
	var spId = $('#sysconfigGrid').datagrid('getSelected').sysDictId;
	if (spId <= 0) {
		$.messager.alert('提示消息', '请先选择要删除的数据', 'error');
		return false;
	}
	$.messager.confirm('确认框', '你确认要删除该条记录吗?', function(r) {
		if (r) {
			$.ajax({
				type : 'POST',
				url : mainServer + "/admin/sysconfig/delete",
				data : {
					sys_dict_id : spId
				},
				dataType : 'json',
				success : function(data) {
					if (data.result) {
						$.messager.alert('提示消息', '删除成功', 'info');
						Store();
					} else {
						$.messager.alert('提示消息', '删除失败', 'error');
					}
				},
				error : function() {
					alert("异常！");
				}
			});
		}
	});

}

function clearForm() {
	$('#updConfDlg').form('clear');
}

function clearConForm() {
	$('#sysconfigToolbar').form('clear');
}
