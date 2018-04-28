var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
function addCreateProduct(obj) {
	if ($(obj).attr("class") == "waiteTea") {
		$(obj).attr("class", "chooseTea");
	} else {
		$(obj).attr("class", "waiteTea");
	}
}
function closeProduct() {
	$("span[class='chooseTea']").each(function(i, ele) {
		$(this).attr("class", "waiteTea");
	});
	$("#dlg").dialog("close");
}

function isSureProduct() {
	var chooseProductIds = "";
	var chooseProductNames = "";
	$("span[class='chooseTea']").each(function(i, ele) {
		chooseProductIds += $(this).attr("id") + ",";
		chooseProductNames += $(this).attr("title") + ",";
	});

	if (chooseProductNames.lastIndexOf(",")) {
		chooseProductNames = chooseProductNames.substr(0, chooseProductNames
				.lastIndexOf(","));
	}

	if (chooseProductIds.lastIndexOf(",")) {
		chooseProductIds = chooseProductIds.substr(0, chooseProductIds
				.lastIndexOf(","));
	}

	$("#notDispatchs").val(chooseProductIds);
	$("#dispatching").textbox("setValue", chooseProductNames);

	$("span[class='chooseTea']").each(function(i, ele) {
		$(this).attr("class", "waiteTea");
	});

	$.ajax({
			url : "querySpecByProjectId",
			data : {
				"productIdArray" : chooseProductIds
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
						$.each(obj,function(i, p) {
							var bool = true;
							$.each(p, function(j, spec) {
								if (bool) {
									productIds += spec.product_id
											+ ","
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
													// id="activty_limitBuy_'+spec.spec_id+'"/>'+
													'</div> &nbsp;&nbsp;');
									bool = false;
								}

								buffer.append('<div class="prospec_item">');
								// buffer.append('<input
								// type="hidden"
								// name="spec_'+spec.spec_id+'"/>');
								specIds += spec.spec_id
										+ ",";
								buffer.append('<input class="textbox" type="text" style="height:33px;width:100px;align:center"  readonly="readonly" value="'
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
								buffer.append('</div>');

								$("#addSpec").html(buffer.toString());
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
	$("#dlg").dialog("close");
}

$(document).on('keyup', '.numberbox', function() {
	$(this).val($(this).val().replace(/[^0-9]/g, ''));
});

$(document).on('keyup', '.floatbox', function() {
	$(this).val($(this).val().replace(/[^0-9.]/g, ''));
});

function chooseProduct() {
	var chooseProductIds = $("#notDispatchs").val();

	if ("" != chooseProductIds && null != chooseProductIds) {
		var array = new Array();
		array = chooseProductIds.split(",");

		for (var i = 0; i < array.length; i++) {
			$("#" + array[i]).attr("class", "chooseTea");
		}
	}

	$("#dlg").dialog("open");
}

function closeProduct() {
	$("span[class='chooseTea']").each(function(i, ele) {
		$(this).attr("class", "waiteTea");
	});
	$("#dlg").dialog("close");
}
// easyUi时间控间
$('#startTime').datetimebox({
	value : '3/4/2010 00:00:00',
	required : true,
	showSeconds : true
});
$('#endTime').datetimebox({
	value : '3/4/2010 23:59:59',
	required : true,
	showSeconds : true
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

function isSubmit() {
	$('#specialform').form('submit',
			{
				url : mainServer + '/admin/special/addTutorialSpecial',
				onSubmit : function() {

					// 专题名称
					var specialName = $("#specialName").val();
					if (null == specialName || "" == specialName) {
						$("#Name_msg").html("不能为空");
						$("#specialName").focus();
						return false;
					} else {
						$("#Name_msg").html("");
					}

					var specialTitle = $("#specialTitle").val(); // 专题标题
					if (specialTitle == "" || specialTitle == null) {
						$("#Title_msg").html("不能为空");
						$("#specialTitle").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}

					// 视频路径
					var specialPage = $("#specialPage").val();
					if (specialPage == '' || specialPage == null) {
						$("#Page_msg").html("请输入视频路径");
						$("#specialPage").focus();
						return false;
					} else {
						$("#Page_msg").html("");
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
					
					var objValue = $("input[name=objValue]").val();
					if(objValue == null || objValue == "" || objValue.length <= 0){
						$("#objValue_msg").html("请输入视频时长");
						return false;
					}

					var specialIntroduce = $("#specialIntroduce").val(); // 专题介绍
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
					$("#specialCover").val(buffer.toString());
					var specialUrl2 = $("#specialCover").val(); // 专题封面
					if ("" == specialUrl2 || null == specialUrl2) {
						$("#specialCover1").html("图片已失效请重新选择");
						return false;
					} else {
						$("#specialCover1").html("");
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
	window.location.href = mainServer + "/admin/special/findTutorialList";
}