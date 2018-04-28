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

	$
			.ajax({
				url : mainServer + "/admin/special/querySpecByProjectId",
				data : {
					"productIdArray" : chooseProductIds
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					// var obj=data;
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

																	buffer
																			.append('<div style="font-size: 14px;font-weight: bolder;margin-top:10px">'
																					+ i
																							.split("_")[0]
																					+ '(规格名称---价格)&nbsp;&nbsp;'
																					+

																					'</div> &nbsp;&nbsp;');
																	bool = false;
																}

																buffer
																		.append('<div class="prospec_item">');
																buffer
																		.append('<input name="specIds" type="checkbox" value="'
																				+ spec.spec_id
																				+ '"/>');
																buffer
																		.append('<span style="font-size: 12px;font-weight: bolder;margin-top:5px">'
																				+ spec.spec_name
																				+ '---'
																				+ spec.spec_price
																				+ '</span>')

																buffer
																		.append('</div>');

																$("#addSpec")
																		.html(
																				buffer
																						.toString());
															});
											bool = true;
										});

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
var _hmt = _hmt || [];
(function() {
	var hm = document.createElement("script");
	hm.src = "//hm.baidu.com/hm.js?b3a3fc356d0af38b811a0ef8d50716b8";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm, s);
})();

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
	$('#promotionform')
			.form(
					'submit',
					{
						url : mainServer + '/admin/promotion/addPromotion',
						dataType : 'json',
						onSubmit : function() {
							// 专题名称
							var promotionName = $("#promotionName").val();
							if (null == promotionName || "" == promotionName) {
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

							var cutMoney = $("#cutMoney").val(); // 减多少钱
							if ("" == cutMoney || cutMoney == null) {
								$("#money_msg").html("请选择");
								$("#cutMoney").focus();
								return false;
							} else {
								$("#money_msg").html("");
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

							var specIds = '';
							if ($("input[type='checkbox']").is(':checked')) {
								var specIdsChecked = $("input[type='checkbox'][name='specIds']:checked");
								$.each(specIdsChecked, function(i, specId) {
									var $this = $(specId);
									specIds += $this.val() + ",";
								})
							}
							if (null == specIds || "" == specIds) {
								$.messager.alert('提示消息',
										'<div style="position:relative;top:20px;">商品规格必须勾选</div>', 'fail');
							return false;
							}
							if (specIds.lastIndexOf(",") != -1) {
								specIds = specIds.substr(0, specIds.length - 1);
							}
							$("#speclist").val(specIds);

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
	window.location.href = mainServer + "/admin/promotion/findList";
}