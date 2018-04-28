var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();

function clearForm() {
	$("#storeToolbar").form('clear');
}

// 打开新增礼品卡窗口
function addGiftCardManagerDlg() {
	clearCouponManagerForm();

	$('#upCouponManagerDlg').dialog('open');
	$('#upCouponManagerDlg').panel({
		title : "新增礼品卡管理"
	});

	$("#addcoupon").show();
	$("#editcoupon").hide();
}

// 新增礼品卡信息
function addGiftCardManagerForm() {
	var giftCardNo = $("#giftCardNo").val();
	if (giftCardNo == '') {
		$.messager.alert('提示', '礼品卡卡号不能为空', 'error');
		return;
	}

	var giftCardPw = $("#giftCardPw").val();
	if (giftCardPw == '') {
		$.messager.alert('提示', '礼品卡密码不能为空', 'error');
		return;
	} else if (giftCardPw.length != 16) {
		$.messager.alert('提示', '礼品卡密码长度应该为16位', 'error');
		return;
	}

	var giftCardMoney = $("#giftCardMoney").val();
	if (giftCardMoney <= 0) {
		$.messager.alert('提示', '礼品卡金额应大于0元', 'error');
		return;
	}

	var status = $("#status").val();
	if (status == '' || status == null) {
		$.messager.alert('提示', '请选择礼品卡状态', 'error');
		return;
	}

	$('#couponManagerForm').form('submit', {
		url : mainServer + '/admin/giftcard/addGiftCardManager',
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

// 打开更新礼品卡弹框
function updateGiftCardManagerDlg() {
	var card_id = $('#StoreGrid').datagrid('getSelected').card_id;
	if (card_id == '') {
		$.messager.alert('提示', '请选择要修改的记录', 'error');
		return false;
	}

	$.ajax({
		type : 'POST',
		url : mainServer + '/admin/giftcard/findGiftCard',
		data : {
			"cardId" : card_id
		},
		dataType : 'json',
		success : function(data) {
			if (data.result == 'succ') {
				clearCouponManagerForm();
				$("#editcoupon").show();
				$("#addcoupon").hide();

				$('#upCouponManagerDlg').dialog('open');
				$('#upCouponManagerDlg').panel({
					title : "更新礼品卡管理"
				});

				var obj = data.giftCardInfo;

				$("#cardId").val(obj.card_id);
				$("#giftCardNo").val(obj.card_no);
				$("#giftCardPw").val(obj.card_pwd);
				$("#giftCardMoney").val(obj.card_money);
				$("#status").val(obj.status);
			} else {
				$.messager.alert('提示', data.msg, 'error');
			}
		},
		error : function() {
			$.messager.alert('提示', '出现异常', 'error');
		}
	});
}

// 更新礼品卡信息
function editGiftCardManagerForm() {
	var giftCardNo = $("#giftCardNo").val();
	if (giftCardNo == '') {
		$.messager.alert('提示', '礼品卡卡号不能为空', 'error');
		return;
	}

	var giftCardPw = $("#giftCardPw").val();
	if (giftCardPw == '') {
		$.messager.alert('提示', '礼品卡密码不能为空', 'error');
		return;
	}

	var giftCardMoney = $("#giftCardMoney").val();
	if (giftCardMoney <= 0) {
		$.messager.alert('提示', '礼品卡金额应大于0元', 'error');
		return;
	}

	var status = $("#status").val();
	if (status == '' || status == null) {
		$.messager.alert('提示', '请选择礼品卡状态', 'error');
		return;
	}

	var cardId = $("#cardId").val();
	if (null == cardId || '' == cardId || cardId <= 0) {
		$.messager.alert('提示', '缺少重要参数', 'error');
		return;
	}

	$('#couponManagerForm').form('submit', {
		url : mainServer + '/admin/giftcard/editGiftCardManager',
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
	var cmId = $('#StoreGrid').datagrid('getSelected').cmId;
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
		"giftCardNo" : $("#cardNoSearch").val(),
		"status" : $("#cardStatusSearch").val(),
		"customerName" : $("#customerNameSearch").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/giftcard/queryGiftCardAllJson',
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
				field : "card_id",
				checkbox : true,
				width : '5%'
			}, {
				title : "礼品卡编号",
				field : "card_no",
				width : '12%'
			}, {
				title : "礼品卡密码",
				field : "card_pwd",
				width : '12%'
			}, {
				title : "礼品卡金额",
				field : "card_money",
				width : '10%'
			}, {
				title : "礼品卡状态",
				field : "status",
				width : '11%',
				formatter : function(value, row) {
					switch (value) {
					case 0:
						return '未使用';
					case 1:
						return '已使用';
					case 2:
						return '已过期';
					}
				}
			}, {
				title : "绑定用户",
				field : "customer_name",
				width : '10%'
			}, {
				title : "绑定时间",
				field : "binding_time",
				width : '11%'
			}, {
				title : "已使用金额",
				field : "card_use",
				width : '11%'
			}, {
				title : "创建时间",
				field : "create_time",
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

function loadExcel() {
	var formId = "uploadPresentCard" + new Date().getTime();
	$("body")
			.append(
					'<form id="'
							+ formId
							+ '" action="" style="display: none;" enctype="multipart/form-data"><input id="file" name="file" type="file" onchange="uploadPresentCardExcel(\''
							+ formId + '\')"></form>');
	$("#" + formId + " input:file").click();
}

function uploadPresentCardExcel(formId) {
	var uploadPresentExcelFileName = $("#" + formId + " input:file").val();
	if (uploadPresentExcelFileName != null
			&& uploadPresentExcelFileName.length > 0) {
		$("body").mask("上传中...");
		$("#" + formId).ajaxSubmit({
			type : 'post',
			url : mainServer + "/admin/giftcard/upload",
			dataType : 'json',
			success : function(data) {
				if (data) {
					if (data.success == 'success') {
						$.messager.alert('提示', "上传成功", 'info');
						$('#StoreGrid').datagrid('reload');
					} else {
						$.messager.alert('提示', "上传失败", 'info');
					}
				}
			},
			error : function(data) {
				$("body").unmask();
				alert("系统异常,请刷新再试或者联系相关技术人员!");
			}
		});

	}
}