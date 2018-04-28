var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchProduct() {
	$('#StoreGrid').datagrid('load', {
		"preName" : $("#preName").val(),
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/recommend/queryReconmmendAllJson',
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
				field : "extensionId",
				checkbox : true,
				width : 100
			}, {
				title : "推荐商品名称",
				field : "productName",
				width : 100
			}, {
				title : "是否首页展示",
				field : "isHomepage",
				width : 100,
				formatter : function(value, rec) {// 使用formatter格式化刷子
					if (value == 1) {
						return '是';
					} else {
						return '否';
					}
				}
			}, ] ],
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

// 查看详细、添加、修改、删除按钮状态同步刷新
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
	if (recommadSize >= 5) {
		boxDataGrid.datagrid("disableToolbarBtn", 'addConfBtn');
	}
}

function toEditRecommend() {
	$('#dlg').dialog({
		title : '修改人气推荐商品'
	});
	var extensionId = boxDataGrid.datagrid('getSelected').extensionId;
	if (extensionId == '' || null == extensionId) {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}

	$.ajax({
		type : "POST",
		url : mainServer + "/admin/recommend/queryRecommendJson",
		data : {
			'extensionId' : extensionId
		},
		async : false,
		success : function(data) {
			var extension = data.data;
			if (1010 == data.code) {
				$('#extensionId').val(extension.extensionId);
				var content = "";
				var select = "";
				$("#productId").attr("disabled", "disabled");
				for (var i = 0; i < data.products.length; i++) {
					if (extension.projectId == data.products[i].product_id) {
						select = "selected='selected'"
					}
					content += "<option value='" + data.products[i].product_id
							+ "' " + select + ">"
							+ data.products[i].product_name + "</option>";
				}
				var isHomepage = extension.isHomepage;
				$("#isHomepage").val(isHomepage);
				$("#productId").html(content);
				$("#productId").val(extension.productId);
				$("#add_Ptype").hide();
				$("#edit_Ptype").show();
				$('#dlg').dialog('open');
			} else {
				$.messager.alert('提示', data.msg, 'info');
			}

		}
	});
}

function editRecommend() {
	$('#dlg').dialog({
		title : '修改人气推荐商品'
	});
	var extensionId = boxDataGrid.datagrid('getSelected').extensionId;
	if (extensionId == '' || null == extensionId) {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	$("#extensionId").val(extensionId);
	var productId = $("#productId").val();
	$("#productId_p").val(productId);
	$('#form1').form('submit', {
		url : mainServer + '/admin/recommend/addRecommend',
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close');
			}
			return isValid;
		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			if (obj.result == 'succ') {
				closeProductType();
				toBack();
			} else {
				$("#uploadInf").append("<p>" + obj.msg + "</p>");
			}
		}
	});
}

function addRecommend() {
	$("#productId").removeAttr("disabled");
	var productId = $("#productId").val();
	if (productId == '') {
		$.messager.alert('提示', "推荐商品名称不能为空", 'info');
		return;
	}
	var isHomepage = $("#isHomepage").val();
	if (isHomepage == '') {
		$.messager.alert('提示', "是否首页展示不能为空", 'info');
		return;
	}
	$("#productId_p").val(productId);
	$('#form1').form('submit', {
		url : mainServer + '/admin/recommend/addRecommend',
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close');
			}
			return isValid;
		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			if (obj.result == 'succ') {
				closeProductType();
				toBack();
			} else {
				$("#uploadInf").append("<p>" + obj.msg + "</p>");
			}

		}
	});
}

function closeProductType() {
	$('#dlg').dialog('close');
}

function delRecommend() {
	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			var extensionId = boxDataGrid.datagrid("getSelected").extensionId;
			$.ajax({
				url : "delRecommend",
				data : {
					extensionId : extensionId
				},
				datatype : "json",
				type : "post",
				success : function(data) {

					var obj = eval('(' + data + ')');
					if (1010 == obj.code) {
						closeProductType();
						toBack();
					} else {
						$.messager.alert('提示', obj.msg, 'info');
					}
				}

			});
		}
	});
}

function addOpenDlg() {
	$('#dlg').form('clear');
	$("#add_Ptype").show();
	$("#edit_Ptype").hide();
	$('#dlg').dialog('open');

}
function toBack() {
	window.location.href = mainServer + "/admin/recommend/findList";
}