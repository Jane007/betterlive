var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();

$(function() {
	getProduct();

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
			$("#couponBanner").val(response.imgurl);
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

	getProduct();
	$("#dlg").dialog("close");
}

function getProduct() {
	var chooseProductIds = $("#notDispatchs").val();
	var specIds = $("#specIds").val();
	var couponId = $("#couponId").val();
	$.ajax({
			url : mainServer + "/admin/singlecoupon/querySpecByProjectIdModify",
			data : {
				"productIdArray" : chooseProductIds,
				"couponId" : couponId
			},
			datatype : "json",
			type : "post",
			success : function(data) {

				var obj = eval('(' + data + ')')
				if (obj == null || '' == obj) {
					$.messager.alert('提示', '没查到商品规格', 'error');
					return;
				}
				$("#spec").show();
				$("#spectbl").show();

				$.each(obj, function(i, p) {
					var projectName = i.split("_")[0];
					var bool = true;
					$.each(p, function(j, spec) {
					if (bool) {
						buffer.append('<div style="font-size: 14px;font-weight: bolder;margin-top:10px">'
										+ projectName
										+ '(规格名称---价格)&nbsp;&nbsp;<br/>(所选红包一键领取：'
										+ mainServer
										+ '/weixin/singlecoupon/getClickRedPacket?couponId='
										+ couponId
										+ '&productId='
										+ i.split("_")[1]
										+ '</div> &nbsp;&nbsp;');
						bool = false;
					}

					buffer.append('<div class="prospec_item">');
					var spec_checked = '';
					var check = '';
					if (specIds != "") {
						spec_checked = specIds.split(",");
						for (var k = 0; k < spec_checked.length; k++) {
							if (spec.spec_id == spec_checked[k]) {
								check = 'checked';
								break;
							}
						}
					}

					buffer.append('<input name="specIds" type="checkbox" value="'
									+ spec.spec_id
									+ '" '
									+ check
									+ '/>');
					buffer.append('<span style="font-size: 12px;font-weight: bolder;margin-top:5px;margin-right:10px;">'
									+ spec.spec_name
									+ '---'
									+ spec.spec_price
									+ '</span>')
					if (spec.linkUrl != null
							&& spec.linkUrl != '') {
						buffer.append('<span class="easyui-linkbutton">链接地址:</span>'
										+ spec.linkUrl);
					}

					buffer.append('</div>');

					$("#addSpec").html(buffer.toString());

				});
				bool = true;
				});

				buffer.clean();
			}

		});
}

$(document).on('keyup', '.numberbox', function() {
	$(this).val($(this).val().replace(/[^0-9]/g, ''));
})

$(document).on('keyup', '.floatbox', function() {
	$(this).val($(this).val().replace(/[^0-9.]/g, ''));
})

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

	required : true,
	showSeconds : true
});
$('#endTime').datetimebox({

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

function isSubmit() {
	$('#couponform').form('submit',
	{
		url : mainServer + '/admin/singlecoupon/addCoupon',
		dataType : 'json',
		onSubmit : function() {
			// 红包名称
			var couponName = $("#couponName").val();
			if (null == couponName || "" == couponName) {
				$("#Name_msg").html("不能为空");
				$("#promotionName").focus();
				return false;
			} else {
				$("#Name_msg").html("");
			}

			var fullMoney = $("#fullMoney").val(); // 满多少钱
			if (fullMoney == "" || fullMoney == null) {
				$("#money_msg").html("不能为空");
				$("#fullMoney").focus();
				return false;
			} else {
				$("#money_msg").html("");
			}

			var couponMoney = $("#couponMoney").val(); // 红包金额
			if ("" == couponMoney || couponMoney == null) {
				$("#couponMoney_msg").html("不能为空");
				$("#couponMoney").focus();
				return false;
			} else {
				$("#couponMoney_msg").html("");
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
			var dispatching = $("#dispatching").val();
			if (null == dispatching || "" == dispatching) {
				$("#Dispatch_msg").html("不能为空");
				$("#dispatching").foucs();
				return false;
			} else {
				$("#Dispatch_msg").html("");
			}

			var dispatching = $("#dispatching").val();
			if (null == dispatching || "" == dispatching) {
				$("#Dispatch_msg").html("不能为空");
				$("#dispatching").foucs();
				return false;
			} else {
				$("#Dispatch_msg").html("");
			}

			var specIds = '';
			if ($("input[type='checkbox']").is(':checked')) {
				var specIdsChecked = $("input[type='checkbox'][name='specIds']:checked");
				$.each(specIdsChecked, function(i, specId) {
					var $this = $(specId);
					specIds += $this.val() + ",";
				})
			}

			if (specIds.length <= 0) {
				alert("请选择红包规格");
				return false;
			}

			if (specIds.lastIndexOf(",") != -1) {
				specIds = specIds.substr(0, specIds.length - 1);
			}
			$("#speclist").val(specIds);
			if (buffer.toString() != null
					&& buffer.toString() != "") {
				$("#couponBanner").val(buffer.toString());
			}
			buffer.clean();
		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			$.messager.alert('提示消息',
					'<div style="position:relative;top:20px;">'
							+ obj.msg + '</div>', 'success');
			if ("success" == obj.result) {
				toBack();
			}
		}
	});

}

function toBack() {
	window.location.href = mainServer + "/admin/singlecoupon/findList";
}
