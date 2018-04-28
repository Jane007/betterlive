var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
$(function() {

	$('.backPage').click(function() {
		window.history.go(-1);
	});

	$('.shopCar').click(
			function() {
				window.location.href = mainServer
						+ '/weixin/shoppingcart/toshoppingcar';
			});

	findSome(1, 10);
});

var loadtobottom = true;
var nextIndex = 1;
$(document).scroll(
		function() {
			totalheight = parseFloat($(window).height())
					+ parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) {
				if (loadtobottom == true) {
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();

					if (parseInt(next) > 1) {
						if (nextIndex != next) {
							nextIndex++;
							findSome(next, 10);
						}
					}
					if (parseInt(next) >= parseInt(pageCount)) {
						loadtobottom = false;
					}

				}
			}
		});

function findSome(pageIndex, pageSize) {

	$.ajax({
		type : 'POST',
		url : mainServer + "/weixin/presentcardrecord/queryCardRecordAllJson",
		async : false,
		data : {
			rows : pageSize,
			pageIndex : pageIndex
		},
		dataType : 'json',
		success : function(data) {

			// 封装分页
			var obj = data;
			var pageNow = data.pageInfo.pageNow;
			var pageCount = data.pageInfo.pageCount;
			$("#pageCount").val(pageCount);
			$("#pageNow").val(pageNow);
			var next = parseInt(pageNow) + 1;
			if (next >= pageCount) {
				next = pageCount;
			}
			$("#pageNext").val(next);
			$("#pageNow").val(pageNow);

			// $("#list").empty();
			if (data.list && data.list.length > 0) {
				$.each(data.list, function(i, record) {
					var type = record.recordType;
					var css = "";
					var sign = "-";
					var stype = "订单消费";
					if (type == 1) {
						css = "add-card";
						sign = "+";
						stype = "添加礼品卡";
					}
					buffer.append('<li class="' + css + '">'
							+ '<p class="trad-top">' + '<em>' + stype + '</em>'
							+ '<i>' + sign + ' ¥' + record.money + '</i>'
							+ '</p>' + '<p class="trad-bot">' + '<em>订单编号：'
							+ record.orderNo + '</em>' + '<i>'
							+ record.recordTime + '</i>' + '</p>' + '</li>');
				});
				$("#list").append(buffer.toString());
				buffer.clean();
			} else {
				$('#noTrading').show();
			}
		}
	})

}