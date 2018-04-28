var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function gradeChange() {
	var options = $("#bannerType option:selected"); // 获取选中的项
	var labelTitle = options.text();
	$("#bannerTitle").val("");
	if (labelTitle == '自定义') {
		$('#bannerTitle').removeAttr("disabled");

	} else {
		$("#bannerTitle").val(labelTitle);

	}
}

function searchProductType() {
	$('#StoreGrid').datagrid('load', {
		"status" : $("#statusSearch").val(),
		"bannerTitle" : $("#bannerTitleStr").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/classifybanner/queryclassifybannerlistpage',
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
							columns : [ [
									{
										title : "序号",
										field : "classifyBannerId",
										checkbox : true,
										width : '18%'
									},
									{
										title : "banner分类标题",
										field : "bannerTitle",
										width : '20%'
									},
									{
										title : "banner分类类型",
										field : "bannerType",
										width : '20%',
										formatter : function(value, row) {
											switch (value) {
											case 0:
												return '分类列表';
											case 1:
												return '应季水果';
											case 2:
												return '水产肉类';
											case 3:
												return '休闲零食';
											case 4:
												return '星球推荐';
											case 5:
												return '限量抢购';
											case 6:
												return '每周新品';
											case 7:
												return '人气推荐';
											}
										}
									},
									{
										title : "banner分类图片",
										field : "bannerImg",
										width : '28%',
										align : "left",
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<img src=' + value
													+ ' style="height:80px;"/>';
										}
									},
									/*
									 * { title:"banner链接地址", field:"bannerUrl",
									 * width:'40%' },
									 */
									{
										title : "状态",
										field : "status",
										width : '12%',
										formatter : function(value, row) {
											// 状态：1：正常，:0：禁用
											if (value == 1) {
												return "上架";
											} else {
												return "下架";
											}
										}
									}, {
										title : "创建时间",
										field : "createTime",
										width : '18%'
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
	$.messager
			.confirm(
					"提示",
					"你确认要删除吗?",
					function(r) {
						if (r) {
							var bannerId = boxDataGrid.datagrid("getSelected").classifyBannerId;
							$
									.ajax({
										url : "delclassfiybanner",
										data : {
											classifyBannerId : bannerId
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
												$.messager.alert('提示', obj.msg,
														'info');
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
	var bannerId = boxDataGrid.datagrid('getSelected').classifyBannerId;
	if (bannerId == '' || null == bannerId) {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	$.ajax({
		type : "POST",
		url : mainServer + "/admin/classifybanner/queryclassfiybannerjson",
		data : {
			'classifyBannerId' : bannerId
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			if (1 == data.flag) {
				var bannerInfo = data.bannerVo;
				console.log(bannerInfo);
				$('#classifyBannerId').val(bannerInfo.classifyBannerId);
				$("#bannerTitle").val(bannerInfo.bannerTitle);
				$('#bannerType').val(bannerInfo.bannerType);
				$('#status').val(bannerInfo.status);
				$('#bannerImg').val(bannerInfo.bannerImg);
				$('#productId').val(bannerInfo.productId);
				$('#pimg').attr("src", bannerInfo.bannerImg);
				$('#pimg').show();
				$("#add_Ptype").hide();
				$("#edit_Ptype").show();
				$('#dlg').dialog('open');
			} else {
				$.messager.alert('提示', "查询出错", 'info');
			}

		}
	});
}

$(function() {
	// 初始化插件
	$("#zyupload").zyUpload({
		width : "515px", // 宽度
		height : "310px", // 宽度
		itemWidth : "100px", // 文件项的宽度
		itemHeight : "85px", // 文件项的高度
		url : mainServer + "/common/uploadImg", // 上传文件的路径
		fileType : [ "jpg", "png", "gif", "JPG", "PNG", "GIF" ], // 上传文件的类型
		fileSize : 1048576, // 上传文件的大小
		multiple : false, // 是否可以多个文件上传
		dragDrop : true, // 是否可以拖动上传文件
		tailor : false, // 是否可以裁剪图片
		del : true, // 是否可以删除文件
		finishDel : true, // 是否在上传文件完成后删除预览
		isadd : false, // 区分添加专题 true 还是添加专题类型 false
		/* 外部获得的回调接口 */
		onSelect : function(selectFiles, allFiles) { // 选择文件的回调方法
														// selectFile:当前选中的文件
														// allFiles:还没上传的全部文件
			console.info("当前选择了以下文件：");
			console.info(selectFiles);
			if (allFiles.length > 1) {
				$.messager.alert('提示', '只能选择一张图片', 'error');
			}
			if (allFiles.length == 1) {
				$.messager.alert('提示', '选择图片后请点击上传再保存', 'info');
			}
		},
		onDelete : function(file, files) { // 删除一个文件的回调方法 file:当前删除的文件
											// files:删除之后的文件
			console.info("当前删除了此文件：");
			console.info(file.name);
		},
		onSuccess : function(file, resp) { // 文件上传成功的回调方法
			var response = eval('(' + resp + ')');
			console.info("此文件上传成功：");
			console.info(file.name);
			console.info("此文件上传到服务器地址：");
			console.info(response);
			$.messager.alert('提示', response.msg, 'info');
			// buffer.append(response.imgurl+',');
			// imgBuffer.append('<img id="pimg" src="');
			// imgBuffer.append(response.imgurl);
			// imgBuffer.append('"
			// width="160px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
			$('#pimg').attr("src", response.imgurl);
			$('#pimg').show();
			$("#bannerImg").val(response.imgurl);
			imgBuffer.clean();
		},
		onFailure : function(file, response) { // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file.name);
		},
		onComplete : function(response) { // 上传完成的回调方法
			console.info("文件上传完成");
			console.info(response);
			imgBuffer.clean();
		}
	});

});

function closeProductType() {
	$('#proTypeName').textbox('setValue', '');
	$('#dlg').dialog('close');
}

function addProductType() {
	var bannerTitle = $("#bannerTitle").val();
	var bannerType = $("#bannerType").val();
	var bannerImg = $("#bannerImg").val();
	var status = $("#status").val();
	if (bannerTitle == '') {
		$.messager.alert('提示', "banner标题不能为空", 'info');
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
	if (bannerImg == '') {
		$.messager.alert('提示', "banner图片不能为空", 'info');
		return;
	}

	// alert($("#objectId_h").val());
	$('#form1').form('submit', {
		url : mainServer + '/admin/classifybanner/addclassifybanner',
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
	$('#form1').form('submit', {
		url : mainServer + '/admin/classifybanner/addclassifybanner',
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

function addOpenDlg() {
	$('#dlg').form('clear');
	$("#add_Ptype").show();
	$("#edit_Ptype").hide();
	$('#dlg').dialog('open');
	$('#pimg').hide();
}
function toBack() {
	window.location.href = mainServer + "/admin/banner/tobannerlist";
}