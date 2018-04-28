var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();

// 打开新增优惠券窗口
function addCouponManagerDlg() {
	clearCouponManagerForm();
	$('#upCouponManagerDlg').dialog('open');
	$('#upCouponManagerDlg').panel({
		title : "新增活动"
	});
	$("#addcoupon").show();
	$("#editcoupon").hide();

	$
			.ajax({
				type : 'POST',
				url : mainServer + '/admin/product/queryListProductAllJson',
				data : {
					'notacticityId' : "yes"
				},
				dataType : 'json',
				success : function(data) {
					if (data.result == 'succ') {
						var obj = data.list;
						$("#productId").empty();

						buffer
								.append('<option value="" selected="selected">请选择</option>');
						$.each(data.list, function(i, ele) {
							buffer.append('<option value="' + ele.product_id
									+ '">' + ele.product_name + '</option>');
						});
						$("#productId").append(buffer.toString());
						buffer.clean();

					} else {
						$.messager.alert('提示', data.msg, 'error');
					}
				},
				error : function() {
					$.messager.alert('提示', '出现异常', 'error');
				}
			});

}

// 选择产品规格
function chooseSpec() {
	$("#product_1").text("");
	$("#product_2").text("");
	$("#product_3").text("");

	var productId = $("#productId").val();
	if (null == productId || '' == productId) {
		$.messager.alert('提示', '请选择产品', 'info');
		return;
	}
	$
			.ajax({
				type : 'POST',
				url : mainServer
						+ '/admin/productspec/queryListProductSpecJson',
				data : {
					"productId" : productId
				},
				dataType : 'json',
				success : function(data) {
					if (data.result == 'succ') {
						var obj = data.list;
						var count = 0;

						$
								.each(
										data.list,
										function(i, ele) {
											count = i + 1;

											var price = ele.spec_price;

											buffer
													.append('<keyValue id="keyAndValue_'
															+ count
															+ '" name="keyAndValue_'
															+ count
															+ '" style="margin-top=50px;">'
															+ '<input  type="hidden"  name="teaId_'
															+ count
															+ '" id="teaId_'
															+ count
															+ '"  value="'
															+ ele.spec_id
															+ '"/>'
															+ '<input  type="text"  name="teaSize_'
															+ count
															+ '" id="teaSize_'
															+ count
															+ '"  value="'
															+ ele.spec_name
															+ '" style="height:27px;margin:0 0;width:180px;" readonly="readonly"/>—'
															+ '<input  type="text"  name="teaSizePrice_'
															+ count
															+ '" id="teaSizePrice_'
															+ count
															+ '"  value="'
															+ price
															+ '" style="height:27px;margin:0 0;width:70px;" maxlength="8" readonly="readonly"/>元—'
															+ '<input  type="text"  name="teaSizeActivityPrice_'
															+ count
															+ '" id="teaSizeActivityPrice_'
															+ count
															+ '"  style="height:27px;margin:0 0;width:70px;" maxlength="8"   />元'
															+ '</keyValue>');

											$("#teaTypeLength").val(count);
											if (count < 4) {
												$("#product_1").append(
														buffer.toString());
											} else if (count > 3 && count < 7) {
												$("#product_2").append(
														buffer.toString());
											} else {
												$("#product_3").append(
														buffer.toString());
											}

											buffer.clean();
										});

					} else {
						$.messager.alert('提示', data.msg, 'error');
					}
				},
				error : function() {
					$.messager.alert('提示', '出现异常', 'error');
				}
			});
}

// 新增活动信息
function addCouponManagerForm() {
	var theme = $("#theme").val();
	if (theme == '') {
		$.messager.alert('提示', '活动主题不能为空', 'error');
		return;
	}

	var productId = $("#productId").val();
	if (productId == '' || productId == null) {
		$.messager.alert('提示', '请选择产品名称', 'error');
		return;
	}

	var starttime = $('#starttime').datetimebox('getValue');
	var startInt = 0;
	if (starttime == '' || starttime == null) {
		$.messager.alert('提示', '开始时间不能为空', 'error');
		return;
	} else {
		startInt = starttime.replace(/-|:|\s/g, "");
	}

	var endtime = $('#endtime').datetimebox('getValue');
	var endInt = 0;
	if (endtime == '' || endtime == null) {
		$.messager.alert('提示', '结束时间不能为空', 'error');
		return;
	} else {
		endInt = endtime.replace(/-|:|\s/g, "");
	}

	if (endInt <= startInt) {
		$.messager.alert('提示', '结束时间不能小于或等于开始时间', 'error');
		return;
	}

	// 规格与价格
	var bool = true;
	$("input[name^='teaId_']").each(function(i, ele) {
		var teaId = $(this).val();
		var teaSizePrice = $("#teaSizePrice_" + (i + 1)).val();
		var teaSizeActivityPrice = $("#teaSizeActivityPrice_" + (i + 1)).val();

		if (null == teaId || "" == teaId) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个规格参数错误', 'error');
			return false;
		}

		/*
		 * if(null == teaSizePrice || "" ==teaSizePrice || teaSizePrice<=0){
		 * bool=false; }
		 * 
		 * if(!bool){ $.messager.alert('提示','第'+(i+1)+'个产品价格参数错误','error');
		 * return false; }
		 */

		if (null == teaSizeActivityPrice || "" == teaSizeActivityPrice) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个活动价格不能为空', 'error');
			return false;
		}

		var reg = /^[0-9]+.?[0-9]*$/;// 用来验证数字，包括小数的正则
		if (!reg.test(teaSizeActivityPrice)) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个活动价格格式错误', 'error');
			return false;
		}

		if (parseFloat(teaSizeActivityPrice) > parseFloat(teaSizePrice)) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个活动价格不能高于产品价格', 'error');
			return false;
		}
	})

	if (!bool) {
		return false;
	}

	/*
	 * var useMinMoney = $("#useMinMoney").val(); if(useMinMoney <= 0){
	 * $.messager.alert('提示','使用门槛金额应大于0元','error'); return ; }
	 */

	$('#couponManagerForm').form('submit', {
		url : mainServer + '/admin/activity/addActivityManager',
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
	clearCouponManagerForm();
	$("#editcoupon").show();
	$("#addcoupon").hide();

	$('#upCouponManagerDlg').dialog('open');
	$('#upCouponManagerDlg').panel({
		title : "更新优惠券管理"
	});

	var activityId = $('#StoreGrid').datagrid('getSelected').activity_id;
	if (activityId == '') {
		$.messager.alert('提示', '请选择要修改的记录', 'error');
		return false;
	}

	$
			.ajax({
				type : 'POST',
				async : false,
				url : mainServer + '/admin/product/queryListProductAllJson',
				data : {
					'notacticityId' : "yes"
				},
				dataType : 'json',
				success : function(data) {
					if (data.result == 'succ') {
						var obj = data.list;
						$("#productId").empty();

						buffer
								.append('<option value="" selected="selected">请选择</option>');
						$.each(data.list, function(i, ele) {
							buffer.append('<option value="' + ele.product_id
									+ '">' + ele.product_name + '</option>');
						});
						$("#productId").append(buffer.toString());
						buffer.clean();
					} else {
						$.messager.alert('提示', data.msg, 'error');
					}
				},
				error : function() {
					$.messager.alert('提示', '出现异常', 'error');
				}
			});

	$
			.ajax({
				type : 'POST',
				url : mainServer + '/admin/activity/findActivityManager',
				data : {
					"activityId" : activityId
				},
				dataType : 'json',
				success : function(data) {
					if (data.result == 'succ') {

						var obj = data.activityInfo;

						$("#activityId").val(obj.activity_id);
						$("#theme").textbox('setValue', obj.activity_theme);
						$("#productId").val(obj.product_id);
						$("#pId").val(obj.product_id);
						$("#productId").attr("disabled", true);
						$("#starttime").datetimebox('setValue', obj.starttime);
						$("#endtime").datetimebox('setValue', obj.endtime);

						var count = 0;

						/* alert(obj.activityProductVo); */

						$
								.each(
										obj.activityProductVo,
										function(i, ele) {
											count = i + 1;

											var price = ele.spec_price;

											buffer
													.append('<keyValue id="keyAndValue_'
															+ count
															+ '" name="keyAndValue_'
															+ count
															+ '" style="margin-top=50px;">'
															+ '<input  type="hidden"  name="teaASpecId_'
															+ count
															+ '" id="teaASpecId_'
															+ count
															+ '"  value="'
															+ ele.activity_spec_id
															+ '"/>'
															+ '<input  type="hidden"  name="teaId_'
															+ count
															+ '" id="teaId_'
															+ count
															+ '"  value="'
															+ ele.spec_id
															+ '"/>'
															+ '<input  type="text"  name="teaSize_'
															+ count
															+ '" id="teaSize_'
															+ count
															+ '"  value="'
															+ ele.spec_name
															+ '" style="height:27px;margin:0 0;width:180px;" readonly="readonly"/>—'
															+ '<input  type="text"  name="teaSizePrice_'
															+ count
															+ '" id="teaSizePrice_'
															+ count
															+ '"  value="'
															+ price
															+ '" style="height:27px;margin:0 0;width:70px;" maxlength="8" readonly="readonly"/>元—'
															+ '<input  type="text"  name="teaSizeActivityPrice_'
															+ count
															+ '" id="teaSizeActivityPrice_'
															+ count
															+ '" value="'
															+ ele.activity_price
															+ '"  style="height:27px;margin:0 0;width:70px;" maxlength="8"   />元'
															+ '</keyValue>');

											$("#teaTypeLength").val(count);
											if (count < 4) {
												$("#product_1").append(
														buffer.toString());
											} else if (count > 3 && count < 7) {
												$("#product_2").append(
														buffer.toString());
											} else {
												$("#product_3").append(
														buffer.toString());
											}

											buffer.clean();
										});

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
	var theme = $("#theme").val();
	if (theme == '') {
		$.messager.alert('提示', '活动主题不能为空', 'error');
		return;
	}

	var starttime = $('#starttime').datetimebox('getValue');
	var startInt = 0;
	if (starttime == '' || starttime == null) {
		$.messager.alert('提示', '开始时间不能为空', 'error');
		return;
	} else {
		startInt = starttime.replace(/-|:|\s/g, "");
	}

	var endtime = $('#endtime').datetimebox('getValue');
	var endInt = 0;
	if (endtime == '' || endtime == null) {
		$.messager.alert('提示', '结束时间不能为空', 'error');
		return;
	} else {
		endInt = endtime.replace(/-|:|\s/g, "");
	}

	if (endInt <= startInt) {
		$.messager.alert('提示', '结束时间不能小于或等于开始时间', 'error');
		return;
	}

	// 规格与价格
	var bool = true;
	$("input[name^='teaId_']").each(function(i, ele) {
		var teaId = $(this).val();
		var teaSizePrice = $("#teaSizePrice_" + (i + 1)).val();
		var teaSizeActivityPrice = $("#teaSizeActivityPrice_" + (i + 1)).val();

		if (null == teaId || "" == teaId) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个规格参数错误', 'error');
			return false;
		}

		/*
		 * if(null == teaSizePrice || "" ==teaSizePrice || teaSizePrice<=0){
		 * bool=false; }
		 * 
		 * if(!bool){ $.messager.alert('提示','第'+(i+1)+'个产品价格参数错误','error');
		 * return false; }
		 */

		if (null == teaSizeActivityPrice || "" == teaSizeActivityPrice) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个活动价格不能为空', 'error');
			return false;
		}

		var reg = /^[0-9]+.?[0-9]*$/;// 用来验证数字，包括小数的正则
		if (!reg.test(teaSizeActivityPrice)) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个活动价格格式错误', 'error');
			return false;
		}

		if (parseFloat(teaSizeActivityPrice) > parseFloat(teaSizePrice)) {
			bool = false;
		}

		if (!bool) {
			$.messager.alert('提示', '第' + (i + 1) + '个活动价格不能高于产品价格', 'error');
			return false;
		}
	})

	if (!bool) {
		return false;
	}

	var activityId = $("#activityId").val();
	var pId = $("#pId").val();
	if (null == activityId | '' == activityId || null == pId | '' == pId) {
		$.messager.alert('提示', '缺少重要参数', 'error');
		return;
	}

	$('#couponManagerForm').form('submit', {
		url : mainServer + '/admin/activity/editActivityManager',
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

var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
	queryCouponManager();
});

function clearCouponManagerForm() {
	// $("#upCouponManagerDlg").form('clear');
	$("#theme").val('');

	$("#productId").empty();
	$("#productId").append('<option value="" selected="selected">请选择</option>');

	$("#starttime").val('');
	$("#endtime").val('');

	$("#teaTypeLength").val(0);

	$("#product_1").text("");
	$("#product_2").text("");
	$("#product_3").text("");
}

function queryCouponManager() {
	$('#StoreGrid').datagrid('load', {
		"theme" : $("#activityNameSearch").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/activity/queryActivityManagerAllJson',
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
				field : "activity_id",
				checkbox : true,
				width : '5%'
			}, {
				title : "活动主题",
				field : "activity_theme",
				width : '15%'
			}, {
				title : "产品名称",
				field : "product_name",
				width : '10%'
			}, {
				title : "开始时间",
				field : "starttime",
				width : '10%'
			}, {
				title : "结束时间",
				field : "endtime",
				width : '10%'
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