$('.easyui-tabs1').tabs({
	tabHeight : 36
});
$(window).resize(function() {
	$('.easyui-tabs1').tabs("resize");
}).resize();

function Responsive($a) {
	if ($a == true)
		$("#Device").css("opacity", "100");
	if ($a == false)
		$("#Device").css("opacity", "0");
	$('#iframe-wrap').removeClass().addClass('full-width');
	$('.icon-tablet,.icon-mobile-1,.icon-monitor,.icon-mobile-2,.icon-mobile-3')
			.removeClass('active');
	$(this).addClass('active');
	return false;
};

var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
var productLogo = ''; // 商品封面图
var productBanner = new Array(); // 商品轮播图

$(function() {
	state = UE.getEditor('editor-state', {
		toolbars : [ [ 'source', '|', 'fullscreen', '|', 'undo', 'redo', '|',
				'bold', 'italic', 'underline', 'fontborder', 'removeformat',
				'formatmatch', 'blockquote', 'pasteplain', '|', 'forecolor',
				'backcolor', 'insertorderedlist', 'insertunorderedlist',
				'selectall', 'cleardoc', '|', 'rowspacingtop',
				'rowspacingbottom', 'lineheight', '|', 'fontsize', '|',
				'indent', '|', 'justifyleft', 'justifycenter', 'justifyright',
				'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
				'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright',
				'imagecenter', '|', , 'insertimage' ] ],
		allowDivTransToP : false,
		initialFrameWidth : '100%',
		initialFrameHeight : 500,
		autoHeightEnabled : false,
		enableAutoSave : false
	});

	$("input[name='isOnline']").on(
			"click",
			function() {
				var val = $(this).val();
				console.log(val);
				if (val == '2') {
					$('input[name=weekly][value=0]').attr("checked", true);
					$("#weekly_no").click();
					$('input[name=weekly_homepage][value=0]').attr("checked",
							true);
					$('#weekly_homepage_no').click();
					$('input[name=tuijian][value=0]').attr("checked", true)
					$('#tuijian_no').click();
					$('input[name=tuijian_homepage][value=0]').attr("checked",
							true);
					$('#tuijian_homepage_no').click();
					$('input[name=weekly][value=1]').attr("checked", false);
					$('input[name=weekly][value=1]').attr("disabled",
							"disabled");
					$('input[name=weekly_homepage][value=1]').attr("disabled",
							"disabled");
					$('input[name=tuijian][value=1]').attr("disabled",
							"disabled");
					$('input[name=tuijian_homepage][value=1]').attr("disabled",
							"disabled");
				} else {
					// if(productDisable==1 || productDisable=='1'){
					// $('input[name=weekly_homepage][value=1]').attr("disabled","disabled");
					// $('input[name=tuijian_homepage][value=1]').attr("disabled","disabled");
					// }else{
					$('input[name=weekly_homepage][value=1]').attr("disabled",
							false);
					$('input[name=tuijian_homepage][value=1]').attr("disabled",
							false);
					$('input[name=weekly][value=1]').attr("disabled", false);
					$('input[name=tuijian][value=1]').attr("disabled", false);
					// }
				}
			})
});

function isSubmit() {
	$('#productform')
			.form(
					'submit',
					{
						url : mainServer + '/admin/product/addProduct',
						onSubmit : function() {
							// 商品名称
							var productName = $("#productName").val();
							if (null == productName || "" == productName) {
								$("#Name_msg").html("不能为空");
								$("#productName").focus();
								return false;
							} else {
								$("#Name_msg").html("");
							}

							var productPrompt = $("#productPrompt").val(); // 温馨提示
							if ("" == productPrompt || productPrompt == null) {
								$("#Prompt_msg").html("不能为空");
								$("#productPrompt").focus();
								return false;
							} else if (productPrompt.length > 50) {
								$("#Prompt_msg").html("不能超过50字");
								$("#productPrompt").focus();
								return false;

							} else {
								$("#Prompt_msg").html("");
							}

							var productLove = $("#productLove").val(); // 猜你喜欢
							if (productLove == "" || productLove == null) {
								$("#Love_msg").html("请选择");
								$("#productLove").focus();
								return false;
							} else {
								$("#Love_msg").html("");
							}

							var productStatus = $("#productStatus").val(); // 商品状态
							if (productStatus == "" || productStatus == null) {
								$("#Status_msg").html("请选择");
								$("#productStatus").focus();
								return false;
							} else {
								$("#Status_msg").html("");
							}
							/**
							 * var orderNum = $("#orderNum").val();//商品排序不是必填项
							 * if(orderNum=="" || orderNum==null){
							 * $("#orderNum_msg").html("请输入");
							 * $("#orderNum").focus(); return false; }else{
							 * $("#orderNum_msg").html(""); }
							 */

							var cityId = $("#cityId").val();
							if (null == cityId || "" == cityId) {
								$("#Area_msg").html("请选择仓库所在地址");
								return false;
							} else {
								$("#Area_msg").html("");
							}

							// 产品参数
							var bl = true;
							$("input[name^='sizeOrParam_']")
									.each(
											function(i, ele) {
												var teaSize = $(this).val();
												var teaSizePrice = $(
														"#sizeOrValue_"
																+ (i + 1))
														.val();
												if (null == teaSize
														|| "" == teaSize) {
													bl = false;
												} else {
													$("#Size_msg").html("");
												}
												if (!bl) {
													$("#Size_msg")
															.html(
																	"第"
																			+ (i + 1)
																			+ "个参数名称不能为空");
													$(this).focus();
													return false;
												}

												if (null == teaSizePrice
														|| "" == teaSizePrice
														|| teaSizePrice <= 0) {
													bl = false;
												} else {
													$("#Size_msg").html("");
												}
												if (!bl) {
													$("#Size_msg")
															.html(
																	"第"
																			+ (i + 1)
																			+ "个参数值不能为空");
													$("#sizeOrValue_" + (i + 1))
															.focus();
													return false;
												}
											});

							if (!bl) {
								return false;
							}

							// 规格与价格
							var bool = true;
							$("input[name^='teaSize_']")
									.each(
											function(i, ele) {
												var teaSize = $(this).val();
												var teaSizePrice = $(
														"#teaSizePrice_"
																+ (i + 1))
														.val();
												if (null == teaSize
														|| "" == teaSize) {
													bool = false;
												} else {
													$("#Product_msg").html("");
												}
												if (!bool) {
													$("#Product_msg")
															.html(
																	"第"
																			+ (i + 1)
																			+ "个规格不能为空");
													$(this).focus();
													return false;
												}

												if (null == teaSizePrice
														|| "" == teaSizePrice
														|| teaSizePrice <= 0) {
													bool = false;
												} else {
													$("#Product_msg").html("");
												}
												if (!bool) {
													$("#Product_msg")
															.html(
																	"第"
																			+ (i + 1)
																			+ "个价格不能为空");
													$(
															"#teaSizePrice_"
																	+ (i + 1))
															.focus();
													return false;
												}

												var teaDiscountPrice = $(
														"#teaDiscountPrice_"
																+ (i + 1))
														.val();
												if (null == teaDiscountPrice
														|| "" == teaDiscountPrice
														|| (parseFloat(teaDiscountPrice) != -1 && parseFloat(teaDiscountPrice) < 0)) {
													$(
															"#teaDiscountPrice_"
																	+ (i + 1))
															.textbox(
																	'setValue',
																	-1);
												}

												var teaLimitMaxCopy = $(
														"#teaLimitMaxCopy_"
																+ (i + 1))
														.val();
												if (teaLimitMaxCopy != null
														&& teaLimitMaxCopy != ""
														&& parseFloat(teaLimitMaxCopy) > -1) {
													var teaLimitStartTime = $(
															"#teaLimitStartTime_"
																	+ (i + 1))
															.textbox('getValue');
													var teaLimitEndTime = $(
															"#teaLimitEndTime_"
																	+ (i + 1))
															.textbox('getValue');
													if (teaLimitStartTime == null
															|| teaLimitStartTime == ""
															|| teaLimitEndTime == null
															|| teaLimitEndTime == "") {
														bool = false;
													}
												} else {
													if (teaLimitMaxCopy == null
															|| teaLimitMaxCopy == "") {
														$(
																"#teaLimitMaxCopy_"
																		+ (i + 1))
																.val(-1);
													}
													$("#Product_msg").html("");
												}

												if (!bool) {
													$("#Product_msg")
															.html(
																	"第"
																			+ (i + 1)
																			+ "个限购需填时间");
													$(
															"#teaLimitMaxCopy_"
																	+ (i + 1))
															.focus();
													return false;
												}

												// 判断所有的规格价格是否已经上传图片
												var specImg = $(
														'#specImg' + (i + 1))
														.filebox('getValue');
												if (specImg == '') {
													$("#specimg_msg" + (i + 1))
															.html("请选择规格图片");
													bool = false;
													return false;
												} else {
													$("#specimg_msg" + (i + 1))
															.html("");
												}
											});

							if (!bool) {
								return false;
							}

							/*
							 * var dispatching=$("#dispatching").val(); var
							 * notDispatchs=$("#notDispatchs").val(); if(null
							 * ==dispatching || ""==dispatching || null
							 * ==notDispatchs || ""==notDispatchs){
							 * $("#Dispatch_msg").html("不能为空");
							 * $("#dispatching").foucs(); return false; }else{
							 * $("#Dispatch_msg").html(""); }
							 */

							// 商品封面图
							if (productLogo == '') {
								$(window.frames["proLogoFrm"].document).find(
										"#prologo_msg").text('请上传商品封面图');
								return false;
							} else {
								$('#product_logo').val(productLogo);
								$(window.frames["proLogoFrm"].document).find(
										"#prologo_msg").text("");
							}

							// 商品封面图
							var proBanner = productBanner.join(',');
							if (proBanner == null || proBanner == '') {
								$(window.frames["proBannerFrm"].document).find(
										"#probanner_msg").text('请至少上传一张商品轮播图');
								return false;
							} else {
								$('#pictureArray').val(proBanner);
								$(window.frames["proBannerFrm"].document).find(
										"#probanner_msg").text("");
							}

							// 商品配送地址
							var deliverAddress = $("#deliverAddress").val();

							var deliverType = ""; // 全部标记
							var areaIds = ""; // (镇)区标记

							if (null == deliverAddress || "" == deliverAddress) {
								$("#Deliver_msg").html("请选择配送地址类型");
								alert("请选择配送地址类型");
								return false;
							} else {
								if (3 == deliverAddress) {
									var root = $("#getDeliverTree").tree(
											'getRoot'); // 根节点是否选中
									/*
									 * console.log(root);
									 * console.log(root.checkState);
									 */

									if (root.checkState == 'checked') { // 全部选择
										deliverType = root.id;
										$("#deliverType").val(deliverType);
									} else if (root.checkState == "indeterminate") { // 选择一部分
										// 循环省份
										$
												.each(
														root.children,
														function(i, ele) {

															if (ele.checkState == 'checked') {
																areaIds += ele.id
																		+ ",";

															} else if (ele.checkState == "indeterminate") {
																// 循环城市
																$
																		.each(
																				ele.children,
																				function(
																						j,
																						el) {
																					if (el.checkState == 'checked') {
																						areaIds += el.id
																								+ ",";

																					} else if (el.checkState == "indeterminate") {
																						// cityIds

																						// 循环镇区
																						$
																								.each(
																										el.children,
																										function(
																												k,
																												els) {
																											if (els.checkState == 'checked') {
																												areaIds += els.id
																														+ ",";
																											}
																										})

																					}
																				})
															}
														})

										if ("" != areaIds
												&& areaIds.lastIndexOf(",") != -1) {
											areaIds = areaIds.substring(0,
													areaIds.lastIndexOf(","));
										}

										$("#areaIds").val(areaIds);
									} else { // 未选择
										$("#Deliver_msg").html("请在左侧选择配送地址");
										alert("请选择配送地址类型");
										return false;
									}
								}

								$("#Deliver_msg").html("");
							}

							buffer.clean();

							var productIntroduce = state.getContent(); // 商品介绍
							if (null == productIntroduce
									|| "" == productIntroduce) {
								$("#Introduce_msg").html("不能为空");
								return false;
							} else {
								$("#productIntroduce").val(productIntroduce);
								$("#Introduce_msg").html("");
							}

						},
						success : function(data) {
							var obj = eval('(' + data + ')');
							if ("succ" == obj.result) {
								$.messager.alert('提示消息',
										'<div style="position:relative;top:20px;">'
												+ obj.msg + '</div>', 'info',
										toBack);
							} else {
								$.messager.alert('提示消息',
										'<div style="position:relative;top:20px;">'
												+ obj.msg + '</div>', 'error');
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
		var _size = file.size / 1048576;// MB
		if(_size > 1) {
			alert('请选择不要超过1MB的图片');
			return false;
		} else {
			$(e).next().next().next().empty().prepend($img);
		}
	}
}

function toBack() {
	window.location.href = mainServer + "/admin/product/findList";
}

// 增加产品参数 input框
function addProductType() {
	var teaLength = $("div[name^='paramAndValue_']");
	var teaSize = teaLength.length + 1;

	if (teaSize <= 8) {
		$("#tea_1")
				.append(
						'<div id="paramAndValue_'
								+ teaSize
								+ '" name="paramAndValue_'
								+ teaSize
								+ '">'
								+ '<input class="textbox" type="text"  name="sizeOrParam_'
								+ teaSize
								+ '" id="sizeOrParam_'
								+ teaSize
								+ '" '
								+ 'style="height:33px;margin:0 0;width:125px;margin-top:10px;" maxlength="15" />&nbsp;—&nbsp;'
								+ '<input class="textbox" type="text"  name="sizeOrValue_'
								+ teaSize
								+ '" id="sizeOrValue_'
								+ teaSize
								+ '" '
								+ ' style="height:33px;margin:0 0;width:125px;margin-top:10px;" maxlength="60"/>'
								+ '</div>');

		$("#productTypeLength").val(teaSize);

	} else {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">最多只可以添加8个</div>',
				'error');
		return false;
	}
}

// 减少产品参数 input框
function delProductType() {
	var teaLength = $("div[name^='paramAndValue_']");
	var teaSize = teaLength.length;

	if (teaSize > 1) {
		$("#paramAndValue_" + teaSize).remove();
		/*
		 * $("#sizeOrValue_"+teaSize).remove();
		 */
		$("#productTypeLength").val(teaSize - 1);
	} else {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">最少只能有1个</div>',
				'error');
		return false;
	}
}

// 增加产品规格与价格 input框
function addTeaType() {
	var teaLength = $("input[name^='teaSize_']");
	var teaSize = teaLength.length + 1;
	// if(teaSize<=8){
	$("#product_1")
			.append(
					'<div id="keyAndValue_'
							+ teaSize
							+ '" name="keyAndValue_'
							+ teaSize
							+ '" class="prospec_item">'
							+ '<input class="textbox" type="text" name="teaSize_'
							+ teaSize
							+ '" id="teaSize_'
							+ teaSize
							+ '" '
							+ 'style="height:30px;width:80px;" maxlength="15"/>&nbsp;—&nbsp;'
							+ '<input class="easyui-numberbox" type="text" name="teaSizePrice_'
							+ teaSize
							+ '" id="teaSizePrice_'
							+ teaSize
							+ '" '
							+ 'style="height:33px;width:70px;margin-top:10px;" data-options="min:0,precision:2,max:10000" />&nbsp;&nbsp;元（原价）&nbsp;'
							+ '<input class="easyui-numberbox" type="text" name="teaDiscountPrice_'
							+ teaSize
							+ '" id="teaDiscountPrice_'
							+ teaSize
							+ '" '
							+ 'style="height:33px;width:70px;margin-top:10px;" data-options="min:-1,precision:2,max:10000" value="-1"/>&nbsp;&nbsp;元（优惠价）&nbsp;'
							+ '<input class="easyui-numberbox" type="text" name="teaCopy_'
							+ teaSize
							+ '" id="teaCopy_'
							+ teaSize
							+ '" style="height:33px;width:70px;margin-top:10px;" data-options="min:0,max:10000">件'
							+ '<input class="numberbox" type="text" name="teaLimitMaxCopy_'
							+ teaSize
							+ '" id="teaLimitMaxCopy_'
							+ teaSize
							+ '"'
							+ 'style="height:33px;width:70px;" data-options="min:0" maxlength="6" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,\'\');}).call(this)"/>件'
							+ '<input class="easyui-datetimebox" type="text" name="teaLimitStartTime_'
							+ teaSize
							+ '"  id="teaLimitStartTime_'
							+ teaSize
							+ '"  style="height:35px;margin:0 0;width:170px;"/>'
							+ '<input class="easyui-datetimebox" type="text" name="teaLimitEndTime_'
							+ teaSize
							+ '"  id="teaLimitEndTime_'
							+ teaSize
							+ '"  style="height:35px;margin:0 0;width:170px;"/>'
							+ '<div style="padding-left:20px; width: 170px;display: inline-block;">'
							+ '规格图片<input class="easyui-filebox" type="text" name="specImg'
							+ teaSize
							+ '" id="specImg'
							+ teaSize
							+ '" style="height:35px;width:60px;"'
							+ ' data-options="buttonText:\'选择图片\',accept:\'image/jpeg,image/png,image/gif,image/JPEG,image/PNG,image/GIF\','
							+ 'onChange:function(){previewImg($(this));}"/>'
							+ '<span id="specimg_msg'
							+ teaSize
							+ '" style="color: red;"></span><div class="specImgPrev"></div>'
							+ '</div><div style="display: inline-block;padding-left: 20px;">套餐说明&nbsp;&nbsp;&nbsp;<input type="text" class="textbox" name="teaPackageDesc_'
							+ teaSize
							+ '" style="height:33px;width:250px;margin-top:10px;font-size: 14px;" maxlength="50"/></div>'
							+ '</div>');
	$.parser.parse('#keyAndValue_' + teaSize); // 重新解析改节点
	$("#teaTypeLength").val(teaSize);
	// }else{
	// $.messager.alert('提示消息','<div
	// style="position:relative;top:20px;">最多只可以添加8个</div>','error');
	// return false;
	// }

}

// 减少产品规格与价格 input框
function delTeaType() {
	var teaLength = $("div[name^='keyAndValue_']");
	var teaSize = teaLength.length;
	if (teaSize > 1) {
		$("#keyAndValue_" + teaSize).remove();
		/*
		 * $("#teaSizePrice_"+teaSize).remove();
		 */
		$("#teaTypeLength").val(teaSize - 1);
	} else {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">必须要有一个规格价格</div>',
				'error');
		return false;
	}
}

function selectAddress() {
	var provinceId = $("#provinceId").val();
	if (null != provinceId && "" != provinceId) {
		$("#Area_msg").html("");
		$("#cityId").empty();
		$("#chioceArea").html("");

		$
				.ajax({
					url : mainServer + "/admin/area/findAllAreaJson",
					type : "POST",
					dataType : 'json',
					async : false,
					data : {
						'parentId' : provinceId
					},
					success : function(data) {
						var obj = data;
						console.log(obj);
						if ("succ" == obj.result) {
							buffer.append("<option value=''>请选择</option>");
							$.each(obj.data, function(i, ele) {
								buffer.append("<option value=" + ele.areaId
										+ ">" + ele.areaName + "</option>");
							})

							$("#cityId").html(buffer.toString());
							buffer.clean();
						} else {
							$.messager
									.alert(
											'提示消息',
											'<div style="position:relative;top:20px;">出现异常</div>',
											'error');
							return false;
						}
					}
				});
	} else {
		$("#cityId").empty();
		$("#chioceArea").html("");

		$("#Area_msg").html("请选择仓库地址");
		return false;
	}
}

function selectCity() {
	var cityId = $("#cityId").val();
	if (null == cityId || "" == cityId) {
		$("#Area_msg").html("请选择仓库地址");
		return false;
	} else {
		$("#Area_msg").html("");

		var cityName = $("#cityId").find("option:selected").text();
		$("#chioceArea").html(cityName);
	}
}

/*
 * function chooseAddress(){ var chooseAddressIds=$("#notDispatchs").val();
 * 
 * if(""!=chooseAddressIds && null!=chooseAddressIds){ var array=new Array();
 * array=chooseAddressIds.split(",");
 * 
 * for (var i= 0; i< array.length; i++) {
 * $("#"+array[i]).attr("class","chooseTea"); } }
 * 
 * $("#dlg").dialog("open"); }
 * 
 * function closeAddress(){ $("span[class='chooseTea']").each(function(i,ele){
 * $(this).attr("class","waiteTea"); }); $("#dlg").dialog("close"); }
 * 
 * 
 * function isSureAddress(){ var chooseAddressIds=""; var chooseAddressNames="";
 * $("span[class='chooseTea']").each(function(i,ele){
 * chooseAddressIds+=$(this).attr("id")+",";
 * chooseAddressNames+=$(this).attr("title")+","; });
 * 
 * if(chooseAddressNames.lastIndexOf(",")){
 * chooseAddressNames=chooseAddressNames.substr(0,chooseAddressNames.lastIndexOf(",")); }
 * 
 * if(chooseAddressIds.lastIndexOf(",")){
 * chooseAddressIds=chooseAddressIds.substr(0,chooseAddressIds.lastIndexOf(",")); }
 * 
 * $("#notDispatchs").val(chooseAddressIds);
 * $("#dispatching").textbox("setValue", chooseAddressNames);
 * 
 * $("span[class='chooseTea']").each(function(i,ele){
 * $(this).attr("class","waiteTea"); });
 * 
 * $("#dlg").dialog("close"); }
 * 
 * 
 * 
 * function addCreateAddress(obj){ if($(obj).attr("class")=="waiteTea"){
 * $(obj).attr("class","chooseTea"); }else{ $(obj).attr("class","waiteTea"); } }
 */

$('#getDeliverTree').tree(
		{
			onBeforeExpand : function(node, param) {
				if (!$('#getDeliverTree').tree('isLeaf', node.target)) {
					$('#getDeliverTree').tree('options').url = mainServer
							+ "/admin/deliverarea/queryTreeAreaJson?parentId="
							+ node.id;
				}
			}
		});

function selectDeliverType() {
	var deliverAddress = $("#deliverAddress").val();
	if (deliverAddress != 3) {
		// 先将回显数据全部清除
		var root = $("#getDeliverTree").tree('getRoot');
		$("#getDeliverTree").tree('uncheck', root.target);

		if (null == deliverAddress || "" == deliverAddress) {
			$("#Deliver_msg").html("请选择配送地址类型");
			$("#chioceArea").html("");
			alert("请选择配送地址类型");
		} else if (1 == deliverAddress) {
			var cityId = $("#cityId").val();
			if (null == cityId || "" == cityId) {
				$("#Area_msg").html("请选择仓库地址");
				return false;
			} else {
				$("#Area_msg").html("");

				var cityName = $("#cityId").find("option:selected").text();
				$("#chioceArea").html(cityName);
			}
		} else {
			var provinceId = $("#provinceId").val();
			if (null == provinceId || "" == provinceId) {
				$("#Area_msg").html("请选择仓库地址");
				return false;
			} else {
				$("#Area_msg").html("");

				var provinceName = $("#provinceId").find("option:selected")
						.text();
				$("#chioceArea").html(provinceName);
			}
		}

	} else {
		$("#Deliver_msg").html("");
		$("#chioceArea").html("");
	}

}