var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
function clearForm() {
	$("#storeToolbar").form('clear');
}
// 判断发放对象
function changeObject() {
	var toObject = $("#toObject").val();
	$("#mobile").val('');
	if (toObject == 1) {
		$("#mobile").hide();
	} else if (toObject == 2) {
		$("#mobile").show();
	}
}

function addCouponManagerDlg() {
	$("#couponManagerForm").form('clear');
	$('#upCouponManagerDlg').dialog('open');

	$.ajax({
		type : 'POST',
		url : mainServer + '/admin/coupon/queryCompensateCouponAllJson',
		dataType : 'json',
		success : function(data) {
			var obj = data;
			if (obj.code == '1010') {
				$("#cmId").empty();

				$.each(obj.data.list, function(i, ele) {
					buffer.append("<option value='" + ele.cm_id + "' >"
							+ ele.coupon_name + "</option>");
				});

				$("#cmId").append(buffer.toString());
				buffer.clean();
			}
		},
		error : function() {
			$.messager.alert('提示', '出现异常', 'error');
			return;
		}
	});
}

function submitCouponManagerForm() {
	var toObject = $("#toObject").val();
	if (null == toObject || toObject == '') {
		$.messager.alert('提示', '请选择发放对象', 'error');
		return;
	} else {
		if ("2" == toObject) {
			var mobile = $("#mobile").val();
			if (null == mobile || mobile == '') {
				$.messager.alert('提示', '请输入用户手机号码', 'error');
				return;
			} else {
				if (!(/^1(3|4|5|7|8)\d{9}$/.test(mobile))) {
					$.messager.alert('提示', '手机号码格式错误', 'error');
					return false;
				}
			}
		}
	}

	var cmId = $("#cmId").val();
	if (null == toObject || toObject == '') {
		$.messager.alert('提示', '请选择优惠券', 'error');
		return;
	}

	$('#couponManagerForm').form('submit', {
		url : mainServer + '/admin/coupon/addUserCouponInfo',
		ajax : true,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			data = typeof data == 'object' ? data : $.parseJSON(data);
			if (data.result == 'succ') {
				$("#couponManagerForm").form('clear');
				$('#upCouponManagerDlg').dialog('close');
				queryCouponManager();
			} else {
				$.messager.alert('提示消息', data.msg, 'error');
			}
		}
	});
}

var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
	queryCouponManager();
});

function queryCouponManager() {
	$('#StoreGrid').datagrid('load', {
		"couponName" : $("#couponNameSearch").val(),
		"couponType" : $("#couponTypeSearch").val(),
		"mobile" : $("#mobileSearch").val(),
		"customerName" : $("#nicknameSearch").val(),
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/coupon/queryUserCouponAllJson',
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
				field : "coupon_id",
				checkbox : true,
				width : '5%'
			}, {
				title : "优惠券名称",
				field : "coupon_name",
				width : '10%'
			}, {
				title : "优惠券类型",
				field : "coupon_from",
				width : '10%',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '分享券';
					case 2:
						return '补偿券';
					}
				}
			}, {
				title : "优惠券金额",
				field : "coupon_money",
				width : '10%'
			}, {
				title : "用户名称",
				field : "nickname",
				width : '10%'
			}, {
				title : "手机",
				field : "mobile",
				width : '10%'
			}, {
				title : "使用门槛",
				field : "start_money",
				width : '10%'

			}, {
				title : "状态",
				field : "status",
				width : '5%',
				formatter : function(value, row) {
					switch (value) {
					case 0:
						return '未使用';
					case 1:
						return '已使用';
					case 2:
						return '已过期';
					default:
						return '';
					}
				}
			}, {
				title : "使用时间",
				field : "used_time",
				width : '12%'
			}, {
				title : "结束使用时间",
				field : "endtime",
				width : '13%'
			}, {
				title : "领取时间",
				field : "create_time",
				width : '13%'
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
