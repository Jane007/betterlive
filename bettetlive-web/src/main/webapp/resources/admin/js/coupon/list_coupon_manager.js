var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();

function clearForm() {
	$("#storeToolbar").form('clear');
}
// 打开新增优惠券窗口
function addCouponManagerDlg() {
	clearCouponManagerForm();

	$('#upCouponManagerDlg').dialog('open');
	$('#upCouponManagerDlg').panel({
		title : "新增优惠券管理"
	});

	$("#addcoupon").show();
	$("#editcoupon").hide();
}

// 新增优惠券信息
function addCouponManagerForm() {
	var couponName = $("#couponName").val();
	if (couponName == '') {
		$.messager.alert('提示', '优惠卷名称不能为空', 'error');
		return;
	}

	var couponType = $("#couponType").val();
	if (couponType == '' || couponType == null) {
		$.messager.alert('提示', '请选择优惠卷类型', 'error');
		return;
	}

	var endDate = $("#endDate").val();
	if (endDate <= 0) {
		$.messager.alert('提示', '优惠卷有效期应大于0天', 'error');
		return;
	}

	var couponMoney = $("#couponMoney").val();
	if (couponMoney < 0) {
		$.messager.alert('提示', '优惠券金额应大于0元', 'error');
		return;
	}

	var useMinMoney = $("#useMinMoney").val();
	if (useMinMoney < 0) {
		$.messager.alert('提示', '使用门槛金额不能为负数', 'error');
		return;
	}

	$('#couponManagerForm').form('submit', {
		url : mainServer + '/admin/coupon/addCouponManager',
		ajax : true,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			data = typeof data == 'object' ? data : $.parseJSON(data);
			if (data.result == 'succ') {
				$('#upCouponManagerDlg').dialog('close');
				clearCouponManagerForm();
				queryCouponManager();
			} else {
				$.messager.alert('提示消息', data.msg, 'error');
			}
		}
	});
}

// 打开更新弹框
function updateCouponManagerDlg() {
	var cmId = $('#StoreGrid').datagrid('getSelected').cm_id;
	if (parseFloat(cmId) == null || parseFloat(cmId) == '') {
		$.messager.alert('提示', '请选择要修改的记录', 'error');
		return false;
	}

	$.ajax({
		type : 'POST',
		url : mainServer + '/admin/coupon/findCouponManager',
		data : {
			"cmId" : cmId
		},
		dataType : 'json',
		success : function(data) {
			if (data.result == 'succ') {
				clearCouponManagerForm();
				$("#editcoupon").show();
				$("#addcoupon").hide();

				$('#upCouponManagerDlg').dialog('open');
				$('#upCouponManagerDlg').panel({
					title : "更新优惠券管理"
				});

				var obj = data.couponManagerInfo;

				$("#cmId").val(obj.cm_id);
				$("#couponName").val(obj.coupon_name);
				$("#couponType").val(obj.coupon_type);
				$("#endDate").val(obj.usemax_date);
				$("#couponMoney").val(obj.coupon_money);
				$("#useMinMoney").val(obj.usemin_money);
				$("#coupon_content").val(obj.coupon_content);
				$("#homeFlag").val(obj.home_flag);

			} else {
				$.messager.alert('提示', data.msg, 'error');
			}
		},
		error : function() {
			$.messager.alert('提示', '出现异常', 'error');
		}
	});
}

// 更新优惠券信息
function editCouponManagerForm() {
	var couponName = $("#couponName").val();
	if (couponName == '') {
		$.messager.alert('提示', '优惠卷名称不能为空', 'error');
		return;
	}

	var couponType = $("#couponType").val();
	if (couponType == '') {
		$.messager.alert('提示', '请选择优惠卷类型', 'error');
		return;
	}

	var endDate = $("#endDate").val();
	if (endDate <= 0) {
		$.messager.alert('提示', '优惠卷有效期应大于0天', 'error');
		return;
	}

	var couponMoney = $("#couponMoney").val();
	if (couponMoney <= 0) {
		$.messager.alert('提示', '优惠券金额应大于0元', 'error');
		return;
	}

	var useMinMoney = $("#useMinMoney").val();
	if (useMinMoney < 0) {
		$.messager.alert('提示', '使用门槛金额不能为负数', 'error');
		return;
	}

	var cmId = $("#cmId").val();
	if (null == cmId || '' == cmId || cmId <= 0) {
		$.messager.alert('提示', '缺少重要参数', 'error');
		return;
	}

	$('#couponManagerForm').form('submit', {
		url : mainServer + '/admin/coupon/editCouponManager',
		ajax : true,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			data = typeof data == 'object' ? data : $.parseJSON(data);
			if (data.result == 'succ') {
				$('#upCouponManagerDlg').dialog('close');
				clearCouponManagerForm();
				queryCouponManager();
			} else {
				$.messager.alert('提示消息', data.msg, 'error');
			}
		}
	});
}

function deleteCouponManagerDlg() {
	var cmId = $('#StoreGrid').datagrid('getSelected').cm_id;
	if (cmId == '') {
		$.messager.alert('提示', '请选择要删除的记录', 'error');
		return;
	}
	$.ajax({
		type : 'POST',
		url : "deleteCouponManagerById",
		data : {
			cmId : cmId
		},
		dataType : 'json',
		success : function(data) {
			if (data.result == 'success') {
				$.messager.alert('提示', data.msg, 'info');
				queryCouponManager();
			} else {
				$.messager.alert('提示', data.msg, 'error');
			}
		},
		error : function() {
			$.messager.alert('提示', '异常', 'error');
		}
	});
}

var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
	queryCouponManager();
});

function clearCouponManagerForm() {
	$("#upCouponManagerDlg").form('clear');
}
function queryCouponManager() {
	$('#StoreGrid').datagrid('load', {
		"couponName" : $("#couponNameSearch").val(),
		"couponType" : $("#couponTypeSearch").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/coupon/queryCouponManagerAllJson',
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
				field : "cm_id",
				checkbox : true,
				width : '5%'
			}, {
				title : "优惠券名称",
				field : "coupon_name",
				width : '12%'
			}, {
				title : "优惠券类型",
				field : "coupon_type",
				width : '10%',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '分享券';
					case 2:
						return '补偿券';
					case 3:
						return '新手券';
					}
				}
			}, {
				title : "优惠券金额",
				field : "coupon_money",
				width : '10%'
			}, {
				title : "使用门槛",
				field : "usemin_money",
				width : '10%'
			}, {
				title : "优惠券来源",
				field : "get_source",
				width : '11%',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '用户分享';
					case 2:
						return '系统补偿';
					}
				}
			}, {
				title : "推荐首页",
				field : "home_flag",
				width : '8%',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '推荐';
					case 0:
						return '否';
					}
				}
			}, {
				title : "有效期",
				field : "usemax_date",
				width : '11%'
			}, {
				title : "创建时间",
				field : "create_time",
				width : '11%'
			}, {
				title : "描述",
				field : "coupon_content",
				width : '11%'
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