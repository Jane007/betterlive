function previewImg(e, f) {
	// $(e).parent().next().text('');
	var fileInput = $(e).next().find('input[type=file]');
	var file = fileInput[0].files[0];
	var img = new Image(), url = img.src = URL.createObjectURL(file);
	var $img = $(img);
	img.onload = function() {
		// URL.revokeObjectURL(url)
		$(e).next().next().next().empty().prepend($img);
	}
}

var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
var i = 0;
var j = 0;
// easyUi时间控间
$('#startTime').datetimebox({

	required : true,
	showSeconds : true
});
$('#endTime').datetimebox({

	required : true,
	showSeconds : true
});
var _hmt = _hmt || [];
(function() {
	var hm = document.createElement("script");
	hm.src = "//hm.baidu.com/hm.js?b3a3fc356d0af38b811a0ef8d50716b8";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm, s);
	getProduct();
})();

var boxDataGrid;

$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');

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
		isadd : false, // 区分添加拼团活动 true 还是添加拼团活动类型 false
		/* 外部获得的回调接口 */
		onSelect : function(selectFiles, allFiles) { // 选择文件的回调方法
														// selectFile:当前选中的文件
														// allFiles:还没上传的全部文件
			console.info("当前选择了以下文件：");
			console.info(selectFiles);
			if (allFiles.length > 1) {
				$.messager.alert('提示', '只能选择一张图片', 'error');
			}
			if (allFiles.length == 1 && j < 1) {
				j = j + 1;
				$.messager.alert('提示', '选完图片请点击新增再保存', 'info');
			}
		},
		onDelete : function(file, files) { // 删除一个文件的回调方法 file:当前删除的文件
											// files:删除之后的文件
			console.info("当前删除了此文件：");
			console.info(file.name);
		},
		onSuccess : function(file, resp) { // 文件上传成功的回调方法
			var response = eval('(' + resp + ')');
			// console.info("此文件上传成功：");
			// console.info(file.name);
			// console.info("此文件上传到服务器地址：");
			// console.info(response);
			$.messager.alert('提示', response.msg, 'info');
			buffer.append(response.imgurl + ',');
			imgBuffer.append('<img id="pimg" src="');
			imgBuffer.append(response.imgurl);
			imgBuffer.append('" width="160px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
			$("#specialCover").val(response.imgurl);
			$('#pics').html(imgBuffer.toString());
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

function searchProduct() {
	$('#StoreGrid').datagrid('load', {
		"productStatus" : $("#product_status").val(),
		"productName" : $("#product_name").val(),
		"hasNoPro" : 1,
		"isOnline" : 1,
		"editFlag" : 1,
		"specialId" : $("#specialId").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/product/queryProductAllJson?hasNoPro=1&productStatus=1&editFlag=1&isOnline=1&specialId='
									+ $("#specialId").val(),

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
								field : "product_id",
								checkbox : true,
								width : 80
							}, {
								title : "商品编号",
								field : "product_code",
								width : 100
							}, {
								title : "商品名称",
								field : "product_name",
								width : 100
							}, {
								title : "温馨提示",
								field : "prompt",
								width : 100
							}, {
								title : "创建时间",
								field : "create_time",
								width : 80

							}

							] ],
							toolbar : '#storeToolbar',

							onBeforeLoad : function(param) {
								$('#StoreGrid').datagrid('clearSelections');

							},
							onLoadSuccess : function(data) {
							},
							onSelect : function(rowIndex, rowData) {
								var row = $("#StoreGrid").datagrid(
										"getSelected");
								$("#productId").val(row.product_id);
								$("#dispatching").textbox("setValue",
										row.product_name);
								isSureProduct(row.product_id);

								closeProduct();
							},
							onUnselect : function(rowIndex, rowData) {
							},
							onCheckAll : function(rowIndex, rowData) {
							}
						});
	}
};

function getProduct() {
	var chooseProductIds = $("#notDispatchs").val();

	$
			.ajax({
				url : "querySpecByProjectIdModify",
				data : {
					"productIdArray" : chooseProductIds,
					"specialId" : $("#specialId").val()
				},
				datatype : "json",
				type : "post",
				success : function(data) {

					var obj = eval('(' + data + ')')

					var specIds = "";
					var productIds = "";

					if (obj != null && '' != obj) {
						$("#spec").show();
						$("#spectbl").show();

						$
								.each(
										obj,
										function(i, p) {

											var bool = true;
											$
													.each(
															p,
															function(j, spec) {

																if (bool) {
																	productIds += spec.product_id
																			+ ","

																	var limitProBuy = spec.limit_pro_buy;
																	if (null == limitProBuy
																			|| "" == limitProBuy) {
																		limitProBuy = "";
																	}

																	buffer
																			.append('<div style="font-size: 14px;font-weight: bolder;margin-top:10px">'
																					+ i
																							.split("_")[0]
																					+ '&nbsp;&nbsp;'
																					+
																					// '专题限购数量&nbsp;<input
																					// type="text"
																					// class="numberbox"
																					// maxlength="5"
																					// style="height:20px;width:100px;"
																					// name="activty_limitBuy_'+spec.product_id+'"
																					// id="activty_limitBuy_'+spec.spec_id+'"
																					// value="'+limitProBuy+'"
																					// />'+
																					'</div> &nbsp;&nbsp;');
																	bool = false;
																}

																buffer
																		.append('<div class="prospec_item">');
																specIds += spec.spec_id
																		+ ",";
																buffer
																		.append('<input class="textbox" type="text" style="height:33px;width:100px;margin-top:10px;align:center"  readonly="readonly" value="'
																				+ spec.spec_name
																				+ '"/>—&nbsp;'
																				+ '&nbsp;&nbsp;<input class="textbox" type="text" style="height:33px;width:100px;margin-top:10px;align:center"  readonly="readonly" value="'
																				+ spec.spec_price
																				+ '"/>—&nbsp;')
																if (spec.activity_price == null
																		|| spec.activity_price == '') {
																	buffer
																			.append('&nbsp;&nbsp;<input class="textbox" type="text" style="height:33px;width:100px;margin-top:10px;align:center"  name="activty_'
																					+ spec.spec_id
																					+ '" value="" id="activty_'
																					+ spec.spec_id
																					+ '"/><span id="activty_msg_'
																					+ spec.spec_id
																					+ '" style="color: red;"></span>');
																} else {
																	buffer
																			.append('&nbsp;&nbsp;<input class="textbox" type="text" style="height:33px;width:100px;margin-top:10px;align:center"  name="activty_'
																					+ spec.spec_id
																					+ '" value="'
																					+ spec.activity_price
																					+ '" id="activty_'
																					+ spec.spec_id
																					+ '"/><span id="activty_msg_'
																					+ spec.spec_id
																					+ '" style="color: red;"></span>');
																}
																buffer
																		.append('</div>');

																$("#addSpec")
																		.html(
																				buffer
																						.toString());

															});
											bool = true;
										});

						if (specIds.lastIndexOf(",") != -1) {
							specIds = specIds.substr(0, specIds.length - 1);
						}
						$("#speclist").val(specIds);

						if (productIds.lastIndexOf(",") != -1) {
							productIds = productIds.substr(0,
									productIds.length - 1);
						}
						$("#productlist").val(productIds);

						buffer.clean();
					} else {
						$.messager.alert('提示', '没查到商品规格', 'error');
					}

				}

			});
}

function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/special/editGroupSpecial',
				onSubmit : function() {

					var specialTitle = $("#specialTitle").val(); // 拼团活动标题
					if (specialTitle == "" || specialTitle == null) {
						$("#Title_msg").html("不能为空");
						$("#specialTitle").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}

					var specialType = $("#specialType").val(); // 活动类型
					if ("" == specialType || specialType == null) {
						alert("活动类型异常");
						return false;
					}

					var activityType = $("#activityType").val(); // 活动规格表类型
					if ("" == activityType || activityType == null) {
						alert("活动类型异常");
						return false;
					}

					var groupCopy = $("#groupCopy").val(); // 开团数量
					if ("" == groupCopy || groupCopy == null
							|| isNaN(groupCopy) || parseFloat(groupCopy) <= 0) {
						$("#groupCopy_msg").html("请输入开团数量，且只能是大于0的整数");
						return false;
					} else {
						$("#groupCopy_msg").html("");
					}

					var limitCopy = $("#limitCopy").val(); // 参团最大人数
					if ("" == limitCopy || limitCopy == null
							|| isNaN(limitCopy) || parseFloat(limitCopy) <= 0) {
						$("#limitCopy_msg").html("请输入参团最大人数，且只能是大于0的整数");
						return false;
					} else {
						$("#limitCopy_msg").html("");
					}

					var startTime = $("input[name=startTime]").val(); // 开始时间
					if ("" == startTime || startTime == null) {
						$("#Start_msg").html("不能为空");
						$("#startTime").focus();
						return false;
					} else {
						$("#Start_msg").html("");
					}

					var endTime = $("input[name=endTime]").val(); // 结束时间
					if ("" == endTime || endTime == null) {
						$("#End_msg").html("不能为空");
						$("#endTime").focus();
						return false;
					} else {
						$("#End_msg").html("");
					}

					if (startTime > endTime) {
						$("#Start_msg").html("开始时间不能大于结束时间");
						return false;
					}

					var specialIntroduce = $("#specialIntroduce").val(); // 拼团活动介绍
					if (null == specialIntroduce || "" == specialIntroduce) {
						$("#Introduce_msg").html("不能为空");
						$("#specialIntroduce").focus();
						return false;
					} else if (specialIntroduce.length > 120) {
						$("#Introduce_msg").html("不能超过120字");
						$("#specialIntroduce").focus();
						return false;
					} else {
						$("#Introduce_msg").html("");
					}

					var dispatching = $("#dispatching").val();// 选择产品可以为空
					if (null == dispatching || dispatching == "") {
					} else {
						var speclist = $("#speclist").val();// 可以为空

						var specId = speclist.split(",");
						for (var i = 0; i < specId.length; i++) {

							var price = $('#activty_' + specId[i]).val();

							if (price == null || price == '') {
								$('#activty_msg_' + specId[i]).html("不能为空");
								$('#activty_' + specId[i]).focus();
								return false;
							} else {
								$('#activty_msg_' + specId[i]).html("");
							}

						}
					}

					$("#specialCover").val();
					var specialUrl2 = $("#specialCover").val(); // 拼团活动封面
					if ("" == specialUrl2 || null == specialUrl2) {
						$("#specialCover1").html("图片已失效请重新选择");
						return false;
					} else {
						$("#specialCover1").html("");
					}
					buffer.clean();
				},
				success : function(data) {
					var obj = eval('(' + data + ')');
					$.messager.alert('提示消息',
							'<div style="position:relative;top:20px;">'
									+ obj.msg + '</div>', 'success');
					if ("succ" == obj.result) {
						toBack();
					}
				}
			});
}

function toBack() {
	window.location.href = mainServer + "/admin/special/findGroupList";
}

function isSureProduct(productId) {
	var chooseProductIds = productId + "";
	$
			.ajax({
				url : "querySpecByProjectId",
				data : {
					"productIdArray" : chooseProductIds,
					"specStatus" : 1
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					var obj = eval('(' + data + ')')
					var specIds = "";
					var productIds = "";
					if (data != null && '' != data) {
						$("#spec").show();
						$("#spectbl").show();

						$
								.each(
										obj,
										function(i, p) {

											var bool = true;

											$
													.each(
															p,
															function(j, spec) {

																if (bool) {
																	productIds += spec.product_id
																			+ ","
																	buffer
																			.append('<div style="font-size: 14px;font-weight: bolder;margin-top:10px">'
																					+ i
																							.split("_")[0]
																					+ '&nbsp;&nbsp;'
																					+
																					// '拼团活动限购数量&nbsp;<input
																					// type="text"
																					// class="numberbox"
																					// maxlength="5"
																					// style="height:20px;width:100px;"
																					// name="activty_limitBuy_'+spec.product_id+'"
																					// id="activty_limitBuy_'+spec.spec_id+'"/>'+
																					'</div> &nbsp;&nbsp;');
																	bool = false;
																}

																buffer
																		.append('<div class="prospec_item">');
																// buffer.append('<input
																// type="hidden"
																// name="spec_'+spec.spec_id+'"/>');
																specIds += spec.spec_id
																		+ ",";
																buffer
																		.append('<input class="textbox" type="text" style="height:33px;width:100px;align:center"  readonly="readonly" value="'
																				+ spec.spec_name
																				+ '"/>—&nbsp;'
																				+ '&nbsp;&nbsp;<input class="textbox" type="text" style="height:33px;width:100px;align:center"  readonly="readonly" value="'
																				+ spec.spec_price
																				+ '"/>—&nbsp;'
																				+ '&nbsp;&nbsp;<input class="floatbox" type="text" style="height:33px;width:100px;align:center"  name="activty_'
																				+ spec.spec_id
																				+ '" id="activty_'
																				+ spec.spec_id
																				+ '"/>'
																				+ '<span id="activty_msg_'
																				+ spec.spec_id
																				+ '" style="color: red;"></span>');
																buffer
																		.append('</div>');

																$("#addSpec")
																		.html(
																				buffer
																						.toString());
															});
											bool = true;
										});
						if (specIds.lastIndexOf(",") != -1) {
							specIds = specIds.substr(0, specIds.length - 1);
						}
						$("#speclist").val(specIds);

						buffer.clean();
					} else {
						$.messager.alert('提示', '没查到商品规格', 'error');
					}

				}

			});
	$("#dlg").dialog("close");
}

$(document).on('keyup', '.numberbox', function() {
	$(this).val($(this).val().replace(/[^0-9]/g, ''));
})

$(document).on('keyup', '.floatbox', function() {
	$(this).val($(this).val().replace(/[^0-9.]/g, ''));
})

function chooseProduct() {
	$("#dlg").dialog("open");
}

function closeProduct() {
	$("#dlg").dialog("close");
}