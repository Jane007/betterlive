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

function gradeChange() {
	var options = $("#labelType option:selected"); // 获取选中的项
	var labelTitle = options.text();
	$("#labelTitle").val("");
	if (labelTitle == '自定义') {
		$('#labelTitle').removeAttr("disabled");

	} else {
		$("#labelTitle").val(labelTitle);

	}
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
	$("#productNames").textbox("setValue", chooseProductNames);

	$("span[class='chooseTea']").each(function(i, ele) {
		$(this).attr("class", "waiteTea");
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
// //easyUi时间控间
// $('#startTime').datetimebox({
// value : '3/4/2010 00:00:00',
// required : true,
// showSeconds : true
// });
// $('#endTime').datetimebox({
// value : '3/4/2010 23:59:59',
// required : true,
// showSeconds : true
// });

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
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/productlabel/addproductlabel',
				onSubmit : function() {

					// 标签名称
					var specialName = $("#labelTitle").val();
					if (null == specialName || "" == specialName) {
						$("#Name_msg").html("不能为空");
						$("#labelTitle").focus();
						return false;
					} else {
						$("#Name_msg").html("");
					}

					var specialTitle = $("#labelType").val(); // 标签类型
					if (specialTitle == "" || specialTitle == null) {
						$("#Title_msg").html("不能为空");
						$("#labelType").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}

					var startTime = $("input[name=showStart]").val(); // 开始时间
					if ("" == startTime || startTime == null) {
						$("#Start_msg").html("不能为空");
						$("#showStart").focus();
						return false;
					} else {
						$("#Start_msg").html("");
					}
					var endTime = $("input[name=showEnd]").val(); // 结束时间
					if ("" == endTime || endTime == null) {
						$("#End_msg").html("不能为空");
						$("#showEnd").focus();
						return false;
					} else {
						$("#End_msg").html("");
					}
					if (startTime > endTime) {
						$("#Start_msg").html("开始时间不能大于结束时间");
						return false;
					}

					var dispatching = $("#productId").val();// 选择产品可以为空
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
	window.location.href = mainServer + "/admin/productlabel/findList";
}