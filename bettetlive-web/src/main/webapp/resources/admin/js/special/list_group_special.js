var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchSpecial() {
	$('#StoreGrid').datagrid('load', {
		"specialType" : $("#specialType").val(),
		"specialName" : $("#specialName").val(),
		"specialType" : 3
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/special/querySpecialAllJson?specialType=3',
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
										title : "序号",
										field : "specialId",
										checkbox : true,
										width : 100
									},
									{
										title : "拼团标题",
										field : "specialName",
										width : 100
									},
									{
										title : "商品名称",
										field : "productSpecVo.product_name",
										width : 80,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											if (rec.productSpecVo != null
													&& rec.productSpecVo.product_name != null) {
												return rec.productSpecVo.product_name;
											}
										}
									},
									{
										title : "商品编号",
										field : "productSpecVo.product_code",
										width : 100,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											if (rec.productSpecVo != null
													&& rec.productSpecVo.product_code != null) {
												return rec.productSpecVo.product_code;
											}
										}
									},
									{
										title : "拼团封面",
										field : "specialCover",
										width : 120,
										height : 80,
										align : "left",
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<img src=' + value
													+ ' style="height:80px;"/>';
										}
									}, {
										title : "创建时间",
										field : "createTime",
										width : 100
									}, {
										title : "开始时间",
										field : "startTime",
										width : 100
									}, {
										title : "结束时间",
										field : "endTime",
										width : 100
									}, {
										title : "状态",
										field : "status",
										width : 80,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var res = "失效";
											if (value == 1) {
												res = "上架";
											} else if (value == 2) {
												res = "下架";
											}
											return res;
										}
									}
									// ,{
									// title:"是否推荐到首页",
									// field:"homeFlag",
									// width:80,
									// formatter:function(value,
									// rec){//使用formatter格式化刷子
									// var showValue = "否";
									// if(value == 1){
									// showValue = "是";
									// }
									// return showValue;
									// }
									// }
									, {
										title : "排序",
										field : "specialSort",
										width : 80
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
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'upConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'downConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'upConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'downConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'upConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'downConfBtn');
	}
}

function addOpenDlg() {
	$('#dlg').dialog('open');
	var orgimg = $("#original_img").val();
	if (orgimg != null && orgimg != "" && orgimg.length > 0) {
		$('.bannerImgShow').html(
				'<img style="width:60px;height:60px;" src="' + orgimg + '">');
		$('.bannerImgShow').show();
	}
}

function closeDlg() {
	$('#dlg').dialog('close');
}

function toEditSpecial() {
	var centerTabs = parent.centerTabs;
	var specialId = boxDataGrid.datagrid('getSelected').specialId;
	if (specialId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer + "/admin/special/toEditGroupSpecial?specialId="
			+ specialId + "&specialType=3&activityType=4";
	if (centerTabs.tabs('exists', '修改活动信息')) {
		centerTabs.tabs('select', '修改活动信息');
		var tab = centerTabs.tabs('getTab', '修改活动信息');
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
							title : '修改活动信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增活动
function toAddSpecial() {
	var centerTabs = parent.centerTabs;
	var url = mainServer
			+ "/admin/special/toAddGroupSpecial?specialType=3&activityType=4";
	if (centerTabs.tabs('exists', '添加活动信息')) {
		centerTabs.tabs('select', '添加活动信息');
		var tab = centerTabs.tabs('getTab', '添加活动信息');
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
							title : '添加活动信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
// 活动状态修改
function toDelSpecial(value) {
	var objectArray = boxDataGrid.datagrid('getSelections');
	var specialIdArray = "";
	$.each(objectArray, function(i, obj) {
		specialIdArray += obj.specialId + ",";
	});
	if (specialIdArray == '') {
		$.messager.alert('提示', "请先选择活动", 'error');
		return false;
	} else {
		if (specialIdArray.lastIndexOf(",") != -1) {
			specialIdArray = specialIdArray.substring(0, specialIdArray
					.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "确认此操作吗?", function(r) {
		if (r) {
			$.ajax({
				url : "delSpecial",
				data : {
					"specialIdArray" : specialIdArray,
					"checkStatus" : value
				},
				datatype : "json",
				type : "post",
				success : function(data) {

					var obj = eval('(' + data + ')');
					if (obj.result == 'succ') {
						searchSpecial();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
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
		var _size = file.size / 1048576;// MB
		if(_size > 1) {
			alert('请选择不要超过1MB的图片');
			return false;
		} else {
			$(e).next().next().next().empty().prepend($img);
		}
	}
}

function editGroupHomePic() {
	$('#form1').form('submit', {
		url : mainServer + '/admin/special/editSpecialBanner',
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
				$("#picture_id").val(obj.data.picture_id);
				$("#original_img").val(obj.data.original_img);
				alert("保存成功");
			} else {
				alert(obj.msg);
			}

		}
	});
}

function delGroupHomePic(status) {
	$.ajax({
		url : mainServer + "/admin/special/delHomePic",
		data : {
			"pictureId" : $("#picture_id").val(),
			"status" : status
		},
		datatype : "json",
		type : "post",
		success : function(data) {

			if (data.code == 1010) {
				alert("操作成功");
				window.location.href = mainServer
						+ "/admin/special/findGroupList"
			} else {
				$.messager.alert('提示', data.msg, 'error');
			}

		}

	});
}