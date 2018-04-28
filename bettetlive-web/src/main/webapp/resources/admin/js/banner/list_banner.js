var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchProductType() {
	$('#StoreGrid').datagrid('load', {
		"status" : $("#statusSearch").val(),
		"bannerTitle" : $("#bannerTitleStr").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/banner/querybannerlistpage',
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
				field : "bannerId",
				checkbox : true,
				width : '5%'
			}, {
				title : "banner图标题",
				field : "bannerTitle",
				width : '15%'
			}, {
				title : "展示顺序",
				field : "isSort",
				width : '15%'
			}, {
				title : "banner图片",
				field : "bannerImg",
				width : '18%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "banner链接地址",
				field : "bannerUrl",
				width : '40%'
			}, {
				title : "banner类型",
				field : "bannerType",
				width : '10%',
				formatter : function(value, row) {
					// 类型：1：自定义链接，2：单个商品，3：活动
					if (value == 1) {
						return "自定义链接";
					} else if (value == 4) {
						return "单个商品";
					} else if (value == 3) {
						return "活动";
					} else {
						return "";
					}
				}
			}, {
				title : "状态",
				field : "status",
				width : '8%',
				formatter : function(value, row) {
					// 状态：1：正常，:0：禁用
					if (value == 1) {
						return "正常";
					} else {
						return "禁用";
					}
				}
			}, {
				title : "创建时间",
				field : "createTimeStr",
				width : '8%'
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
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
	}
}

function delConfDlg() {
	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			var bannerId = boxDataGrid.datagrid("getSelected").bannerId;
			$.ajax({
				url : "delbanner",
				data : {
					bannerId : bannerId
				},
				datatype : "json",
				type : "post",
				success : function(data) {

					var obj = data;
					if (1010 == obj.code) {
						closeProductType();
						searchProductType();
						// toBack();
					} else {
						$.messager.alert('提示', obj.msg, 'info');
					}
				}

			});
		}
	});
}

var editObjectId = 0;
function updConfDlg() {
	$('#dlg').dialog({
		title : '修改商品类型'
	});
	var bannerId = boxDataGrid.datagrid('getSelected').bannerId;
	if (bannerId == '' || null == bannerId) {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}

	$.ajax({
		type : "POST",
		url : mainServer + "/admin/banner/querybannerjson",
		data : {
			'bannerId' : bannerId
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			var bannerInfo = data.data;
			if (1010 == data.code) {
				$('#bannerId').val(bannerInfo.bannerId);
				var bannerTitle = $("#bannerTitle").textbox("setValue",
						bannerInfo.bannerTitle);
				var bannerUrl = $("#bannerUrl").textbox("setValue",
						bannerInfo.bannerUrl);
				$('#bannerType').val(bannerInfo.bannerType);
				$('#status').val(bannerInfo.status);
				// $('#bannerImg').val(bannerInfo.bannerImg);
				$('.bannerImgShow').html(
						'<img style="width:60px;height:60px;" src="'
								+ bannerInfo.bannerImg + '">');
				$('.bannerImgShow').show();
				$('#isSort').textbox("setValue", bannerInfo.isSort);
				$("#add_Ptype").hide();
				$("#edit_Ptype").show();
				$('#dlg').dialog('open');

				if (bannerInfo.bannerType != 1) {
					$('#bannerUrl').textbox('textbox').attr('readonly', true);
					editObjectId = bannerInfo.objectId;
					$("#objectId_h").val(bannerInfo.objectId);
					var bannerType = bannerInfo.bannerType;
					buffer = new StringBuffer();
					getObjectId(bannerType, buffer);
				} else {
					$("#linkObjId").hide();
					$('#bannerUrl').textbox('textbox').attr('readonly', false);
					if (bannerInfo.bannerUrl != null
							&& bannerInfo.bannerUrl != "") {
						$('#bannerUrl_h').val(bannerInfo.bannerUrl);
					} else {
						$('#bannerUrl_h').val("");
					}
					$("#objectId_h").val("");
				}

			} else {
				$.messager.alert('提示', data.msg, 'info');
			}

		}
	});
}

function previewImg(e, f) {
	// $(e).parent().next().text('');
	var fileInput = $(e).next().find('input[type=file]');
	var file = fileInput[0].files[0];
	var img = new Image(), url = img.src = URL.createObjectURL(file);
	img.style.width = '60px';
	img.style.height = '60px';
	var $img = $(img);
	img.onload = function() {
		$(e).next().next().next().empty().prepend($img);
	}
}

function closeProductType() {
	$('#proTypeName').textbox('setValue', '');
	$('#dlg').dialog('close');
}

function addProductType() {
	var bannerTitle = $("#bannerTitle").val();
	var bannerUrl = $("#bannerUrl").val();
	var bannerType = $("#bannerType").val();
	// var bannerImg=$("#bannerImg").val();
	var status = $("#status").val();
	var isSort = $("#isSort").val();
	if (bannerTitle == '') {
		$.messager.alert('提示', "banner标题不能为空", 'info');
		return;
	}

	if (bannerUrl == '') {
		$.messager.alert('提示', "banner链接地址不能为空", 'info');
		return;
	}

	if (bannerType == '' || bannerType == 'null' || bannerType == null) {
		$.messager.alert('提示', "请先选择banner类型", 'info');
		return;
	}

	if (status == '' || status == 'null' || status == null) {
		$.messager.alert('提示', "请先选择banner状态", 'info');
		return;
	}
	if (isSort == null || isSort == "" || isNaN(isSort)
			|| parseFloat(isSort) < 1) {
		$.messager.alert('提示', "请输入顺序", 'info');
		return;
	}

	if (bannerUrl != null && bannerUrl != "") {
		$("#bannerUrl_h").val(bannerUrl);
	}
	// if(bannerImg==''){
	// $.messager.alert('提示',"banner图片不能为空",'info');
	// return ;
	// }

	// alert($("#objectId_h").val());
	$('#form1').form('submit', {
		url : mainServer + '/admin/banner/addbanner',
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close');
			}

			return isValid;
		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			if (1010 == obj.code) {
				closeProductType();
				searchProductType();
			} else {
				$("#uploadInf").append("<p>" + obj.msg + "</p>");
			}

		}
	});
}

function editProductType() {
	var isSort = $("#isSort").val();
	if (isSort == null || isSort == "" || isNaN(isSort)
			|| parseFloat(isSort) < 1) {
		$.messager.alert('提示', "请输入顺序", 'info');
		return;
	}
	var bannerUrl = $("#bannerUrl").val();
	var bannerType = $("#bannerType").val();
	if (bannerType != null && bannerUrl != null) {
		$("#bannerUrl_h").val(bannerUrl);
	}
	$('#form1').form('submit', {
		url : mainServer + '/admin/banner/addbanner',
		datatype : "json",
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close');
			}
			return isValid;
		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			if (1010 == obj.code) {
				closeProductType();
				searchProductType();
			} else {
				$("#uploadInf").append("<p>" + obj.msg + "</p>");
			}
		}
	});
}
var bannerUrl = "";
$("#bannerType").change(function() {
	$("#objectId option:last").remove();
	$("#objectId_h").val("");
	var bannerType = $(this).val();
	buffer = new StringBuffer();
	if (bannerType != 1) {
		$("#linkObjId").show();
		getObjectId(bannerType, buffer);
		$('#bannerUrl').textbox('textbox').attr('readonly', true);
	} else {
		$("#linkObjId").hide();
		$('#bannerUrl').textbox('textbox').attr('readonly', false);
	}

});

function getObjectId(bannerType, buffer) {

	$.ajax({
		type : "POST",
		url : mainServer + "/admin/banner/bannerTypeChange",
		data : {
			'bannerType' : bannerType
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			var list = data.list;

			$.each(list, function(i, obj) {
				var selected = "";
				if (bannerType == 2) {// 预售id
					if (editObjectId == obj.preId) {
						selected = "selected"
					}
					buffer.append('<option value="' + obj.preId + '" '
							+ selected + '>' + obj.preName + '</option>');
				} else if (bannerType == 3) {// 专题活动id
					if (editObjectId == obj.specialId) {
						selected = "selected"
					}
					var local = "";
					// if(obj.specialType == 1){ //限时活动
					local = obj.specialPage;
					// }else if(obj.specialType == 2){ //限量抢购
					// local =
					// "${mainserver}/weixin/product/toLimitGoodsdetails?productId="+obj.productSpecVo.product_id+"&specialId="+obj.specialId;
					// }else if(obj.specialType == 3){ //团购
					// local =
					// "${mainserver}/weixin/product/toGroupGoodsdetails?productId="+obj.productSpecVo.product_id+"&specialId="+obj.specialId;
					// }else if (obj.specialType == 4){ //美食教程
					// local =
					// "${mainserver}/weixin/special/toTorialComment?specialId"+obj.specialId;
					// }
					buffer.append('<option value="' + local + '_'
							+ obj.specialId + '" ' + selected + '>'
							+ obj.specialName + '</option>');
				} else if (bannerType == 4) {// 4：产品id
					if (editObjectId == obj.product_id) {
						selected = "selected"
					}
					buffer.append('<option value="' + obj.product_id + '" '
							+ selected + '>' + obj.product_name + '</option>');
				}
			});

			$("#objectId").html(buffer.toString());

			var objectId = $("#objectId").val();
			bannerUrl = data.bannerUrl;
			var url = mainServer + "/" + data.bannerUrl + objectId;
			if (bannerType == 3) {// 专题活动有自己的页面
				bannerUrl = objectId.split("_")[0];
				$("#bannerUrl").textbox("setValue", objectId.split("_")[0]);
				$("#bannerUrl_h").val(objectId.split("_")[0]);
				$("#objectId_h").val(objectId.split("_")[1]);
			} else {
				$("#bannerUrl").textbox("setValue", url);
				$("#bannerUrl_h").val(url);
				$("#objectId_h").val(objectId);
			}

		}
	});
}

$("#objectId").change(function() {
	var objectId = $(this).val();
	var url = mainServer + "/" + bannerUrl + objectId
	bannerType = $("#bannerType").val();
	if (bannerType == 3) {
		$("#bannerUrl").textbox("setValue", objectId.split("_")[0]);
		$("#bannerUrl_h").val(objectId.split("_")[0]);
		$("#objectId_h").val(objectId.split("_")[1]);
	} else {
		$("#bannerUrl").textbox("setValue", url);
		$("#bannerUrl_h").val(url);
		$("#objectId_h").val(objectId);
	}
});

function addOpenDlg() {
	$('#dlg').form('clear');
	$("#add_Ptype").show();
	$("#edit_Ptype").hide();
	$('#dlg').dialog('open');
	$('#pimg').hide();
	$("#bannerUrl").textbox("setValue", "");
	$("#bannerUrl_h").val("");
	$('.bannerImgShow').html('');
}
function toBack() {
	window.location.href = mainServer + "/admin/banner/tobannerlist";
}