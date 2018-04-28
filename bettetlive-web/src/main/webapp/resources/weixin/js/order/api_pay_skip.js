$(function() {
	if (orderStatus == 2 || orderStatus == 3 || orderStatus == 4
			|| orderStatus == 5) {
		location.href = mainServer + "/weixin/order/payResult?orderCode="
				+ orderCode;
	} else if (isWeiXin()) {
		$('.supernatant').show();
		setInterval("checkResult()", 5000);
	} else {
		// //支付宝请求参数
		location.href = mainServer + "/weixin/payment/alipay?payBusinessId="
				+ orderCode;
	}
});

function checkResult() {
	$.ajax({
		url : mainServer + '/weixin/order/checkPayStatus',
		data : {
			"orderCode" : orderCode
		},
		async : false,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == '1010') { // 操作成功
				if (data.data == 2 || data.data == 3 || data.data == 4
						|| data.data == 5) {
					location.href = mainServer
							+ "/weixin/order/payResult?orderCode=" + orderCode;
				}
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin";
			}
		}
	});
}
/**
 * 判断是否是用微信浏览器
 * 
 * @returns {Boolean}
 */
function isWeiXin() {
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger') {
		return true;
	} else {
		return false;
	}
}

function back() {
	$.ajax({
		url : mainServer + '/weixin/order/checkPayStatus',
		data : {
			"orderCode" : orderCode
		},
		async : false,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == '1010') { // 操作成功
				location.href = mainServer
						+ "/weixin/order/payResult?orderCode=" + orderCode;
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin";
			}
		}
	});
}
$(function() {

	var bool = false;
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			back();
		}
	}, false);

	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}

});

function hideTip() {
	$(".fiter").hide();
	$(".hint").hide();
}