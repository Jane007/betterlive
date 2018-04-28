var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
// easyUi时间控间
$('#raiseStart').datetimebox({
	value : '3/4/2010 00:00:00',
	required : true,
	showSeconds : true
});
$('#raiseEnd').datetimebox({
	value : '3/4/2010 23:59:59',
	required : true,
	showSeconds : true
});

$('#deliverTime').datetimebox({
	value : '3/4/2010 23:59:59',
	required : true,
	showSeconds : true
});

$(function() {
	// 初始化插件
	$("#zyupload").zyUpload({
		width : "515px", // 宽度
		height : "310px", // 宽度
		itemWidth : "100px", // 文件项的宽度
		itemHeight : "85px", // 文件项的高度
		url : mainServer + "/common/uploadImg", // 上传文件的路径
		fileType : [ "jpg", "png", "gif", "JPG", "PNG", "GIF" ], // 上传文件的类型
		fileSize : 2560000, // 上传文件的大小
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
			console.info("此文件上传成功：");
			console.info(file.name);
			console.info("此文件上传到服务器地址：");
			console.info(response);
			$.messager.alert('提示', response.msg, 'info');
			$("#productLogo").val(response.imgurl);
			$('#pimg').attr("src", response.imgurl);
		},
		onFailure : function(file, response) { // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file.name);
		},
		onComplete : function(response) { // 上传完成的回调方法
			console.info("文件上传完成");
			console.info(response);
		}
	});
});

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

function isSubmit() {
	$('#productform')
			.form(
					'submit',
					{
						url : mainServer + '/admin/preproduct/addPreProduct',
						onSubmit : function() {

							// 商品名称
							var preName = $("#preName").val();
							if (null == preName || "" == preName) {
								$("#Name_msg").html("不能为空");
								$("#productName").focus();
								return false;
							} else {
								$("#Name_msg").html("");
							}

							var productId = $("#productId").val(); // 产品名称
							if ("" == productId || productId == null) {
								$("#Product_msg").html("请选择");
								$("#productId").focus();
								return false;
							} else {
								$("#Product_msg").html("");
							}

							var raiseTarget = $("#raiseTarget").val(); // 众筹目标
							if (raiseTarget == "" || raiseTarget == null) {
								$("#Target_msg").html("不能为空");
								$("#productStatus").focus();
								return false;
							} else {
								$("#Target_msg").html("");
							}

							var raiseStart = $("input[name=raiseStart]").val(); // 众筹开始时间
							if (null == raiseStart || "" == raiseStart) {
								$("#Start_msg").html("不能为空");
								$("#raiseStart").focus()
								return false;
							} else {
								$("#Start_msg").html("");
							}

							var raiseEnd = $("input[name=raiseEnd]").val(); // 众筹结束时间
							if (null == raiseEnd || "" == raiseEnd) {
								$("#End_msg").html("不能为空");
								$("#raiseEnd").focus()
								return false;
							} else {
								$("#Start_msg").html("");
							}

							if (raiseStart > raiseEnd) {
								$("#Start_msg").html("开始时间必须小于结束时间");
								$("#raiseStart").focus()
								return false;
							} else {
								$("#Start_msg").html("");
							}

							var deliverTime = $("input[name=deliverTime]")
									.val(); // 发货时间
							if (null == deliverTime || "" == deliverTime) {
								$("#Deliver_msg").html("不能为空");
								$("#deliverTime").focus()
								return false;
							} else {
								$("#Deliver_msg").html("");
							}

							if (raiseStart > deliverTime) {
								$("#Start_msg").html("开始时间必须小于发货时间");
								$("#raiseStart").focus()
								return false;
							} else {
								$("#Start_msg").html("");
							}

							// 规格与价格
							var bool = true;
							$("input[name^='teaId_']")
									.each(
											function(i, ele) {
												var teaId = $(this).val();
												var teaSizePrice = $(
														"#teaSizePrice_"
																+ (i + 1))
														.val();
												var teaSizeActivityPrice = $(
														"#teaSizeActivityPrice_"
																+ (i + 1))
														.val();

												if (null == teaId
														|| "" == teaId) {
													bool = false;
												}

												if (!bool) {
													$.messager.alert('提示', '第'
															+ (i + 1)
															+ '个规格参数错误',
															'error');
													return false;
												}
												if (null == teaSizeActivityPrice
														|| "" == teaSizeActivityPrice) {
													bool = false;
												}

												if (!bool) {
													$.messager.alert('提示', '第'
															+ (i + 1)
															+ '个预售价格不能为空',
															'error');
													return false;
												}

												var reg = /^[0-9]+.?[0-9]*$/;// 用来验证数字，包括小数的正则
												if (!reg
														.test(teaSizeActivityPrice)) {
													bool = false;
												}

												if (!bool) {
													$.messager.alert('提示', '第'
															+ (i + 1)
															+ '个预售价格格式错误',
															'error');
													return false;
												}

												if (parseFloat(teaSizeActivityPrice) > parseFloat(teaSizePrice)) {
													bool = false;
												}

												if (!bool) {
													$.messager.alert('提示', '第'
															+ (i + 1)
															+ '个预售价格不能高于产品价格',
															'error');
													return false;
												}
											});

							if (!bool) {
								return false;
							}

							var productLogo = $('#productLogo').val(); // 众筹封面图
							if (null == productLogo || "" == productLogo) {
								$("#productLogo_msg").html("不能为空");
								return false;
							} else {
								$("#productLogo_msg").html("");
							}

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
	window.location.href = mainServer + "/admin/preproduct/findList";
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
						+ '/admin/productspec/queryListProductSpecJsonToADD',
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

											var price = "";
											if (null != ele.discount_price
													&& '' != ele.discount_price
													&& ele.discount_price > 0) {
												price = ele.discount_price;
											} else {
												price = ele.spec_price;
											}

											buffer
													.append('<keyValue id="keyAndValue_'
															+ count
															+ '" name="keyAndValue_'
															+ count
															+ '" style="margin-top:50px;margin-left:200px;line-height:50px">'
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
															+ '" style="height:27px;margin:0 0;width:70px;" maxlength="8" readonly="readonly"/>&nbsp;元—'
															+ '<input  type="text"  name="teaSizeActivityPrice_'
															+ count
															+ '" id="teaSizeActivityPrice_'
															+ count
															+ '" value="'
															+ ele.activity_price
															+ '"  style="height:27px;margin:0 0;width:70px;" maxlength="8"/>&nbsp;元'
															+ '</keyValue><br/>');

										});
						$("#teaTypeLength").val(count);
						if (count < 4) {
							$("#product_1").append(buffer.toString());
						} else if (count > 3 && count < 7) {
							$("#product_2").append(buffer.toString());
						} else {
							$("#product_3").append(buffer.toString());
						}

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
