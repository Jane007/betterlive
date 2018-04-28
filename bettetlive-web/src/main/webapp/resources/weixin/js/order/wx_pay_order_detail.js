$(function() {
	$(".initloading").show();
	setTimeout(function() {
		$(".initloading").hide();
	}, 800);

	// 倒计时
	var expireTime = $("#waitPayTime").val();
	var ordId = $("#ordId").val();
	if(expireTime != null && expireTime != "") {
		timecount(expireTime, ordId);
	}else{	//避免初始化加载计时器还没执行到超时
		$("#payId").html("付款&nbsp;超时");
		$("#payId").attr("href", "javascript:void(0);");
		$("#ExpireTip1").html("(");
		$("#timeExpire").text("交易关闭");
		$("#ExpireTip").html(")");
	}
});

var timecount = function(starttime, ordId){
	if( starttime && starttime != ''){
		var _html = '';
		
		// 截取时分秒字符串
		var h = starttime.split(':')[0];
		var m = starttime.split(':')[1];
		var s = starttime.split(':')[2];
		var countDown = setInterval(function(){
			--s;
			if(s < 0){
				m--;
				s = 59;
			};
			if(m < 0){
				h--;
				m = 60;
			};
			if(s < 10){
				s = '0' + s
			};
			if(m < 10 && m >= 1){
				if(m.toString().split('')[0] == '0'){
					m = m
				}else{
					m = '0' + m
				}
			};
			if(m < 1){
				m = '00'
			};
			if(h < 0){
				h = 24
			};
			_html = m + ':' + s;
			$("#ExpireTip1").html("(");
			$('#timeExpire').text(_html);
			$("#ExpireTip").html("后自动关闭)");
			if(_html == '00:00'){
				cancelOrder(ordId);
				clearInterval(countDown);
				$("#payId").attr("href", "javascript:void(0);");
				$('#payId').html("付款&nbsp;超时");
				$('#timeExpire').text("交易关闭");
				$("#ExpireTip").html(")");
				return;
			};
		},1000)
	}else{
		return;
	}
};

function cancelOrder(orderId) {
	if (null == orderId || "" == orderId) {
		showvaguealert("操作失败");
		return false;
	}

	$.ajax({
		type : "POST",
		url : mainServer + "/weixin/order/orderStatus",
		data : {
			'orderId' : orderId,
			'tstatus' : 6
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			var obj = eval(data);
			if ("succ" == obj.result) {
				showvaguealert("订单已失效");
//				setTimeout(function() {
//					window.location.href = mainServer
//							+ "/weixin/order/findList?status=" + type;
//				}, 1000);
			} else {
				showvaguealert(data.msg);
			}
		}
	});
}

// 去支付
var orderCode = "";
var payData = "";
function nextOrderPay(orderId, orderCode) {

	if (null == orderId || "" == orderId || null == orderCode
			|| "" == orderCode) {
		showvaguealert("订单ID或者编号为空");
		return false;
	}

	if (payType == 1) {
		if (!isCheckWeiXin("请在微信端支付，或选择支付宝支付")) {
			return;
		}
	}

	$.ajax({
		async : false,
		url : mainServer + "/weixin/payment/nextPrePay",
		data : {
			'orderId' : orderId,
			"orderCode" : orderCode
		},
		type : "POST",
		dataType : 'json',
		success : function(data) {

			if (data.result == 'succ') {
				if (data.payType == 1) {
					var record = data;
					orderCode = record.tradeNo;
					payData = data.payData;
					wexinBridge();
				} else if (data.payType == 2) {
					window.location.href = mainServer
							+ "/weixin/payment/toAliPaySkip?orderCode="
							+ data.tradeNo;
				} else if (payType == '3') {
					oneCardPay(data.tradeNo);
				} else {
					showvaguealert("未知的支付方式");
				}
			} else {
				showvaguealert(data.msg);
			}
		}
	});
}

function wexinBridge() {
	if (typeof WeixinJSBridge == "undefined") {
		if (document.addEventListener) {
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
					false);
		} else if (document.attachEvent) {
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			onBridgeReady();
		}
	} else {
		onBridgeReady();
	}
}

function onBridgeReady() {
	WeixinJSBridge.invoke('getBrandWCPayRequest', payData, function(res) {
		// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 ok，但并不保证它绝对可靠。
		if (res.err_msg == "get_brand_wcpay_request:ok") {
			window.location.href = mainServer
					+ "/weixin/order/payResult?orderCode=" + orderCode; // 从支付完成进入订单详情
		} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
			showvaguealert('您已经取消支付');
		} else {
			showvaguealert('出错了');
		}
	});
}

function oneCardPay(orderNo) {
	$.ajax({
		url : mainServer + '/weixin/payment/oneCardPay',
		data : {
			"orderCode" : orderNo
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.result == 'succ') {
				sendFormRequest(JSON.stringify(data.oneCardPack), data.payApi);
			}
		},
		failure : function(data) {
			showvaguealert('出错了');
		}
	});
}

// 发送表单数据
function sendFormRequest(xmlRequest, payApi) {
	var form = document.createElement("form");
	document.body.appendChild(form);
	var param = document.createElement("input");
	param.type = "hidden";
	param.value = xmlRequest;
	param.name = "jsonRequestData";
	form.appendChild(param);
	form.action = payApi;
	form.method = "POST";
	form.accept_charset = "UTF-8";
	form.submit();
}

$(function() {

	var url = mainServer + "/weixin/order/findList?status=" + type;

	var bool = false;
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			window.location.href = url;
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
