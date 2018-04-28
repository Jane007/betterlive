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
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

function searchProduct() {
	var create_time = $("input[name=create_time]").val();
	var update_time = $("input[name=update_time]").val();

	if (create_time > update_time) {
		showvaguealert("开始时间不能大于结束时间");
		return false;
	}
	$('#StoreGrid').datagrid('load', {
		"create_time" : create_time,
		"update_time" : update_time
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/feedback/queryFeedBackAllJson',

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
				field : "id",
				checkbox : true,
				width : 80
			}, {
				title : "用户昵称",
				field : "nickname",
				width : 100
			}, {
				title : "用户联系方式",
				field : "mobile",
				width : 100
			}, {
				title : "反馈联系方式",
				field : "contact",
				width : 100,

			}, {
				title : "标签类型",
				field : "target",
				width : 100,
				formatter : function(value, row) {
					switch (value) {
					case 0:
						return 'IOS客户端';
					case 1:
						return '安卓客户端';
					case 2:
						return '微信';
					}
				}
			}, {
				title : "反馈内容",
				field : "content",
				width : 80

			}, {
				title : "用户id",
				field : "customer_id",
				hidden : true,
				width : 80
			}, {
				title : "创建时间",
				field : "create_time",
				width : 120,
				formatter : function(value, row) {
					var date = new Date();
					date.setTime(value);
					return myformatter(date);
				}
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

function toDelProduct() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var feedBackIdArray = "";
	$.each(objectArray, function(i, obj) {
		feedBackIdArray += obj.id + ",";
	});
	if (feedBackIdArray == '') {
		$.messager.alert('提示', "请先选择反馈用户", 'error');
		return false;
	} else {
		if (feedBackIdArray.lastIndexOf(",") != -1) {
			feedBackIdArray = feedBackIdArray.substring(0, feedBackIdArray
					.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			$.ajax({
				url : mainServer + "/admin/feedback/delFeedBack",
				data : {
					"feedBackIdArray" : feedBackIdArray
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
