var nextIndex = 1;
var tabIndex = 0;
var loadtobottom = true;
$(function() {
	$(".current").each(function(i, items) {
		$(this).removeClass('current');
	});

	$("#li" + type).addClass('current');
	$("#myOrder" + type).html("");
	$("#myOrder" + type).show();
	queryMyOrders(type, 1, 10);
	var ordernav = $(".myordrnav li");
	var ordercent = $(".myordercent");
	for (var i = 0; i < ordernav.length; i++) {
		ordernav[i].index = i;
		ordernav[i].onclick = function() {

			for (var i = 0; i < ordernav.length; i++) {
				ordernav[i].className = "";
				ordercent[i].style.display = "none";
			}
			ordernav[this.index].className = "current";
			ordercent[this.index].style.display = "block";
			$("#myOrder" + this.index).html("");
			$("#myOrder" + this.index).show();
			$(".ordernone").hide();
			nextIndex = 1;
			tabIndex = this.index;
			$("#pageNext").val(1);
			loadtobottom = true;
			$(window).scrollTop(0);
			queryMyOrders(this.index, 1, 10);
		}
	}
});

$(document).scroll(
		function() {
			var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) {
				if (loadtobottom == true) {
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();
					if (parseInt(next) > 1) {
						if (nextIndex != next) {
							nextIndex++;
							queryMyOrders(tabIndex, next, 10);
							$(".loadingmore").show();
							setTimeout(function() {
								$(".loadingmore").hide();
							}, 1500);
						}
					}
					if (parseInt(next) >= parseInt(pageCount)) {
						loadtobottom = false;

					}

				}
			}
		});

function queryMyOrders(idx, pageIndex, pageSize) {
	$(".initloading").show();

	var type = "";
	if (idx > 0) {
		type = idx;
	}
	$.ajax({
				type : "POST",
				url : mainServer + "/weixin/order/queryOrderAllJson",
				data : {
					'status' : type,
					rows : pageSize,
					pageIndex : pageIndex
				},
				async : false,
				dataType : "json",
				success : function(data) {
					if (1010 == data.code) {
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

						if ((data.data == null || data.data.length <= 0)
								&& pageIndex == 1) {
							$(".initloading").hide();
							$("#myOrder" + idx).hide();
							$(".ordernone").show();
							     
							return;
						} else {
							$(".ordernone").hide();
						}

						var list = data.data;
						for ( var continueIndex in list) {
							if (isNaN(continueIndex)) {
								continue;
							}
							var orderVo = list[continueIndex];
							var showHtml = "";
							if (orderVo.status == 1) { // 待付款
								var pics = "";
								var listOrderPro = orderVo.listOrderProductVo;
								var isExpire = 0;
								var totalPay = 0;
								for ( var subIndex in listOrderPro) {
									if (isNaN(subIndex)) {
										continue;
									}
									var subOrd = listOrderPro[subIndex];
									if (subOrd.product_status != 1) {
										isExpire++;
									}
									totalPay = totalPay
											+ parseFloat(subOrd.totalPay);
									if (subIndex <= 4) {
										pics += '<div class="left"><img src="'
												+ subOrd.spec_img
												+ '" alt="" /></div>';
									}
								}

								var detailClick = "onclick=\"location.href='"
										+ mainServer
										+ "/weixin/order/toPayOrderDetail?type="
										+ idx + "&orderId=" + orderVo.order_id
										+ "&orderCode=" + orderVo.order_code
										+ "'\"";
								
								var surplusTiem = "超时";
								var nextOrderPay = "void(0);";
								if(orderVo.surplusTiem != null && orderVo.surplusTiem != ""){
									surplusTiem = orderVo.surplusTiem.substring(3,8);
									nextOrderPay = 'nextOrderPay(' + orderVo.order_id + ',\'' + orderVo.order_code + '\', ' + orderVo.pay_type + ')';
								}
								var payLine = '<a class="red" href="javascript:'+nextOrderPay+'">付款<span class="spannode '+ idx +'countDown'+ orderVo.order_id +'">'+ surplusTiem +'</span></a>';
								var expShow = "";
								// 调用定时器
								if( orderVo.surplusTiem && orderVo.surplusTiem != ''){
									timecount(orderVo.surplusTiem,idx+'countDown', orderVo.order_id)
								};
								var showGroupLabel = "";
								if (isExpire > 0) {
									payLine = "";
									if (orderVo.groupJoinId != null
											&& orderVo.groupJoinId > 0) {
										expShow = "(团购订单已失效)";
									} else {
										expShow = "(订单已失效)";
									}
								} else if (orderVo.groupJoinId != null
										&& orderVo.groupJoinId > 0) {
									showGroupLabel = "<strong>（团购订单）</strong>";
								}

								if (listOrderPro.length > 1) { // 单个商品
									showHtml = ' <div class="newordlistbox">'
											+ '	 	<div class="top" '
											+ detailClick
											+ '>'
											+ '		 	<span>订单编号：'
											+ orderVo.order_code
											+ '	</span>'
											+ '	 		<p>'
											+ showGroupLabel
											+ '待付款'
											+ expShow
											+ '</p>'
											+ '	 	</div>'
											+ '	 	<div class="center" '
											+ detailClick
											+ '>'
											+ pics
											+ '</div>'
											+ '	 	<div class="ordheji" '
											+ detailClick
											+ '>'
											+ '	 		共'
											+ orderVo.quantityTotal
											+ '件  实付款：<strong>¥'
											+ orderVo.pay_money
											+ '</strong>'
											+ '	 	</div>'
											+ ' 		<div class="ordbtnbox">'
											+ ' 			<a  href="javascript:cancelOrder('
											+ orderVo.order_id
											+ ');">取消订单</a>  '
											+ payLine
											+ ' 		</div>' + ' </div>';
								} else { // 多个商品
									var productLabel = "";
									if (orderVo.product_label != null
											&& orderVo.product_label != "") {
										productLabel = "<span>"
												+ orderVo.product_label
												+ "</span>";
									}

									var showMoneyLine = "";
									if (orderVo.activity_price != null
											&& orderVo.activity_price != ""
											&& parseFloat(orderVo.activity_price) > 0) {
										showMoneyLine = "<span>￥"
												+ checkMoneyByPoint(orderVo.activity_price)
												+ "</span>"
												+ "<p>￥"
												+ checkMoneyByPoint(orderVo.price)
												+ "</p>";
									} else if (orderVo.discount_price > 0) {
										showMoneyLine = "<span>￥"
												+ checkMoneyByPoint(orderVo.discount_price)
												+ "</span>"
												+ "<p>￥"
												+ checkMoneyByPoint(orderVo.price)
												+ "</p>";
									} else {
										showMoneyLine = "<span>￥"
												+ checkMoneyByPoint(orderVo.price)
												+ "</span>";
									}

									showHtml = '<div class="newordlistbox">'
											+ '		 <div class="top" '
											+ detailClick
											+ '>'
											+ ' 			<span>订单编号：'
											+ orderVo.order_code
											+ '</span>'
											+ ' 			<p>'
											+ showGroupLabel
											+ '待付款'
											+ expShow
											+ '</p>'
											+ ' 		</div>'
											+ ' 		<div class="center" '
											+ detailClick
											+ '>'
											+ ' 			<div class="left">'
											+ ' 				<img src="'
											+ orderVo.spec_img
											+ '" alt="" />'
											+ productLabel
											+ ' 			</div>'
											+ ' 			<div class="midden">'
											+ ' 				<span>'
											+ orderVo.product_name
											+ '</span>'
											+ ' 				<p>'
											+ orderVo.spec_name
											+ '</p>'
											+ ' 			</div>'
											+ ' 			<div class="right">'
											+ showMoneyLine
											+ ' 				<em>x'
											+ orderVo.quantity
											+ '</em>'
											+ ' 			</div>'
											+ ' 		</div>'
											+ '	 	<div class="ordheji" '
											+ detailClick
											+ '>'
											+ '	 		共'
											+ orderVo.quantityTotal
											+ '件  实付款：<strong>¥'
											+ orderVo.pay_money
											+ '</strong>'
											+ '	 	</div>'
											+ ' 		<div class="ordbtnbox">'
											+ ' 			<a  href="javascript:cancelOrder('
											+ orderVo.order_id
											+ ');">取消订单</a>  '
											+ payLine
											+ ' 		</div>' + '</div>';

								};
								
							} else { // 非待付款
								var productLabel = "";
								if (orderVo.product_label != null
										&& orderVo.product_label != "") {
									productLabel = "<span>"
											+ orderVo.product_label + "</span>";
								}

								var showMoneyLine = "";
								if (orderVo.activity_price != null
										&& orderVo.activity_price != ""
										&& parseFloat(orderVo.activity_price) > 0) {
									showMoneyLine = "<span>￥"
											+ checkMoneyByPoint(orderVo.activity_price)
											+ "</span>" + "<p>￥"
											+ checkMoneyByPoint(orderVo.price)
											+ "</p>";
								} else if (orderVo.discount_price > 0) {
									showMoneyLine = "<span>￥"
											+ checkMoneyByPoint(orderVo.discount_price)
											+ "</span>" + "<p>￥"
											+ checkMoneyByPoint(orderVo.price)
											+ "</p>";
								} else {
									showMoneyLine = "<span>￥"
											+ checkMoneyByPoint(orderVo.price)
											+ "</span>";
								}

								var clickLine = "onclick=\"location.href='"
										+ mainServer
										+ "/weixin/order/queryOrderDetails?tabType="
										+ idx + "&orderId=" + orderVo.order_id
										+ "&orderCode=" + orderVo.order_code
										+ "&orderpro_id=" + orderVo.orderpro_id
										+ "'\"";

								if (orderVo.sub_status == 2) { // 待发货
									var showGroupLabel = "";
									if (orderVo.groupJoinId != null
											&& orderVo.groupJoinId > 0) {
										showGroupLabel = "<strong>（拼团成功后发货）</strong>";
									}
									showHtml = '<div class="newordlistbox" '
											+ clickLine
											+ '>'
											+ '		 <div class="top">'
											+ ' 			<span>订单编号：'
											+ orderVo.sub_order_code
											+ '</span>'
											+ ' 			<p>'
											+ showGroupLabel
											+ '待发货</p>'
											+ ' 		</div>'
											+ ' 		<div class="center">'
											+ ' 			<div class="left">'
											+ ' 				<img src="'
											+ orderVo.spec_img
											+ '" alt="" />'
											+ productLabel
											+ ' 			</div>'
											+ ' 			<div class="midden">'
											+ ' 				<span>'
											+ orderVo.product_name
											+ '</span>'
											+ ' 				<p>'
											+ orderVo.spec_name
											+ '</p>'
											+ ' 			</div>'
											+ ' 			<div class="right">'
											+ showMoneyLine
											+ ' 				<em>x'
											+ orderVo.quantity
											+ '</em>'
											+ ' 			</div>'
											+ ' 		</div>'
											+ ' 		<div class="ordheji ordhejinb">'
											+ ' 			共'
											+ orderVo.quantity
											+ '件  实付款：<strong>¥'
											+ checkMoneyByPoint(orderVo.pay_money)
											+ '</strong>' + ' 		</div>'
											+ '</div>';
								} else if (orderVo.sub_status == 3) { // 待收货
									var showGroupLabel = "";
									if (orderVo.groupJoinId != null
											&& orderVo.groupJoinId > 0) {
										showGroupLabel = "<strong>（团购订单）</strong>";
									}
									showHtml = '<div class="newordlistbox">'
											+ '		 <div class="top" '
											+ clickLine
											+ '>'
											+ ' 			<span>订单编号：'
											+ orderVo.sub_order_code
											+ '</span>'
											+ ' 			<p>'
											+ showGroupLabel
											+ '待收货</p>'
											+ ' 		</div>'
											+ ' 		<div class="center" '
											+ clickLine
											+ '>'
											+ ' 			<div class="left">'
											+ ' 				<img src="'
											+ orderVo.spec_img
											+ '" alt="" />'
											+ productLabel
											+ ' 			</div>'
											+ ' 			<div class="midden" '
											+ clickLine
											+ '>'
											+ ' 				<span>'
											+ orderVo.product_name
											+ '</span>'
											+ ' 				<p>'
											+ orderVo.spec_name
											+ '</p>'
											+ ' 			</div>'
											+ ' 			<div class="right" '
											+ clickLine
											+ '>'
											+ showMoneyLine
											+ ' 				<em>x'
											+ orderVo.quantity
											+ '</em>'
											+ ' 			</div>'
											+ ' 		</div>'
											+ ' 		<div class="ordheji ordhejinb" '
											+ clickLine
											+ '>'
											+ ' 		共'
											+ orderVo.quantity
											+ '件  实付款：<strong>¥'
											+ checkMoneyByPoint(orderVo.pay_money)
											+ '</strong>'
											+ ' 		</div>'
											+ '		<div class="ordbtnbox">'
											+ ' 			<a href="'
											+ mainServer
											+ '/weixin/order/queryLogiticsInfo?type='
											+ idx
											+ '&orderpro_id='
											+ orderVo.orderpro_id
											+ '">查看物流</a>'
											+ ' 			<a class="red" href="javascript:checkOrder('
											+ orderVo.order_id
											+ ','
											+ orderVo.orderpro_id
											+ ','
											+ orderVo.product_id
											+ ',\''
											+ orderVo.order_code
											+ '\','
											+ continueIndex
											+ ','
											+ idx
											+ ')" id="comfirmOrder'
											+ continueIndex
											+ '">确认收货</a>'
											+ ' 		</div>' + '</div>';
								} else if (orderVo.sub_status == 4) { // 待评价
									var showGroupLabel = "";
									if (orderVo.groupJoinId != null
											&& orderVo.groupJoinId > 0) {
										showGroupLabel = "<strong>（团购订单）</strong>";
									}
									showHtml = '<div class="newordlistbox">'
											+ '		 <div class="top" '
											+ clickLine
											+ '>'
											+ ' 			<span>订单编号：'
											+ orderVo.sub_order_code
											+ '</span>'
											+ ' 			<p>'
											+ showGroupLabel
											+ '待评价</p>'
											+ ' 		</div>'
											+ ' 		<div class="center" '
											+ clickLine
											+ '>'
											+ ' 			<div class="left">'
											+ ' 				<img src="'
											+ orderVo.spec_img
											+ '" alt="" />'
											+ productLabel
											+ ' 			</div>'
											+ ' 			<div class="midden">'
											+ ' 				<span>'
											+ orderVo.product_name
											+ '</span>'
											+ ' 				<p>'
											+ orderVo.spec_name
											+ '</p>'
											+ ' 			</div>'
											+ ' 			<div class="right">'
											+ showMoneyLine
											+ ' 				<em>x'
											+ orderVo.quantity
											+ '</em>'
											+ ' 			</div>'
											+ ' 		</div>'
											+ ' 		<div class="ordheji" '
											+ clickLine
											+ '>'
											+ ' 			共'
											+ orderVo.quantity
											+ '件  实付款：<strong>¥'
											+ checkMoneyByPoint(orderVo.pay_money)
											+ '</strong>'
											+ ' 		</div>'
											+ '		<div class="ordbtnbox">'
											+ ' 			<a href="'
											+ mainServer
											+ '/weixin/order/queryLogiticsInfo?type='
											+ idx
											+ '&orderpro_id='
											+ orderVo.orderpro_id
											+ '">查看物流</a>'
											+ ' 			<a class="red" href="javascript:toCommentOrder('
											+ orderVo.product_id
											+ ','
											+ orderVo.order_id
											+ ',\''
											+ orderVo.order_code
											+ '\','
											+ idx
											+ ');">去评价</a>'
											+ ' 		</div>'
											+ '</div>';
								} else if (orderVo.sub_status == 5) { // 已完成
									var showGroupLabel = "";
									if (orderVo.groupJoinId != null
											&& orderVo.groupJoinId > 0) {
										showGroupLabel = "<strong>（团购订单）</strong>";
									}
									showHtml = '<div class="newordlistbox">'
											+ '		 <div class="top" '
											+ clickLine
											+ '>'
											+ ' 			<span>订单编号：'
											+ orderVo.sub_order_code
											+ '</span>'
											+ ' 			<p>'
											+ showGroupLabel
											+ '交易已完成</p>'
											+ ' 		</div>'
											+ ' 		<div class="center" '
											+ clickLine
											+ '>'
											+ ' 			<div class="left">'
											+ ' 				<img src="'
											+ orderVo.spec_img
											+ '" alt="" />'
											+ productLabel
											+ ' 			</div>'
											+ ' 			<div class="midden">'
											+ ' 				<span>'
											+ orderVo.product_name
											+ '</span>'
											+ ' 				<p>'
											+ orderVo.spec_name
											+ '</p>'
											+ ' 			</div>'
											+ ' 			<div class="right">'
											+ showMoneyLine
											+ ' 				<em>x'
											+ orderVo.quantity
											+ '</em>'
											+ ' 			</div>'
											+ ' 		</div>'
											+ ' 		<div class="ordheji" '
											+ clickLine
											+ '>'
											+ ' 			共'
											+ orderVo.quantity
											+ '件  实付款：<strong>¥'
											+ checkMoneyByPoint(orderVo.pay_money)
											+ '</strong>'
											+ ' 		</div>'
											+ '		<div class="ordbtnbox">'
											+ ' 			<a href="'
											+ mainServer
											+ '/weixin/order/queryLogiticsInfo?type='
											+ idx
											+ '&orderpro_id='
											+ orderVo.orderpro_id
											+ '">查看物流</a>'
											+ ' 		</div>'
											+ '</div>';
								} else if (orderVo.sub_status == 6) { // 已取消支付
									var showGroupLabel = "";
									if (orderVo.groupJoinId != null
											&& orderVo.groupJoinId > 0) {
										showGroupLabel = "<strong>（团购订单）</strong>";
									}
									showHtml = '<div class="newordlistbox" id="cancelOrder'
											+ continueIndex
											+ '">'
											+ '		 <div class="top" '
											+ clickLine
											+ '>'
											+ ' 			<span>订单编号：'
											+ orderVo.sub_order_code
											+ '</span>'
											+ ' 			<p>'
											+ showGroupLabel
											+ '交易已取消</p>'
											+ ' 		</div>'
											+ ' 		<div class="center" '
											+ clickLine
											+ '>'
											+ ' 			<div class="left">'
											+ ' 				<img src="'
											+ orderVo.spec_img
											+ '" alt="" />'
											+ productLabel
											+ ' 			</div>'
											+ ' 			<div class="midden">'
											+ ' 				<span>'
											+ orderVo.product_name
											+ '</span>'
											+ ' 				<p>'
											+ orderVo.spec_name
											+ '</p>'
											+ ' 			</div>'
											+ ' 			<div class="right">'
											+ showMoneyLine
											+ ' 				<em>x'
											+ orderVo.quantity
											+ '</em>'
											+ ' 			</div>'
											+ ' 		</div>'
											+ ' 		<div class="ordheji" '
											+ clickLine
											+ '>'
											+ ' 			共'
											+ orderVo.quantity
											+ '件  实付款：<strong>¥'
											+ checkMoneyByPoint(orderVo.pay_money)
											+ '</strong>'
											+ ' 		</div>'
											+ '		<div class="ordbtnbox">'
											+ ' 			<a href="javascript:delOrder('
											+ orderVo.order_id
											+ ','
											+ orderVo.orderpro_id
											+ ','
											+ continueIndex
											+ ');">删除订单</a>'
											+ ' 		</div>' + '</div>';
								} else if (orderVo.sub_status == 7) { // 已退款
									var showGroupLabel = "";
									if (orderVo.groupJoinId != null
											&& orderVo.groupJoinId > 0) {
										showGroupLabel = "<strong>（团购订单）</strong>";
									}
									showHtml = '<div class="newordlistbox">'
											+ '		 <div class="top" '
											+ clickLine
											+ '>'
											+ ' 			<span>订单编号：'
											+ orderVo.sub_order_code
											+ '</span>'
											+ ' 			<p>'
											+ showGroupLabel
											+ '订单已退款</p>'
											+ ' 		</div>'
											+ ' 		<div class="center" '
											+ clickLine
											+ '>'
											+ ' 			<div class="left">'
											+ ' 				<img src="'
											+ orderVo.spec_img
											+ '" alt="" />'
											+ productLabel
											+ ' 			</div>'
											+ ' 			<div class="midden">'
											+ ' 				<span>'
											+ orderVo.product_name
											+ '</span>'
											+ ' 				<p>'
											+ orderVo.spec_name
											+ '</p>'
											+ ' 			</div>'
											+ ' 			<div class="right">'
											+ showMoneyLine
											+ ' 				<em>x'
											+ orderVo.quantity
											+ '</em>'
											+ ' 			</div>'
											+ ' 		</div>'
											+ ' 		<div class="ordheji" '
											+ clickLine
											+ '>'
											+ ' 			共'
											+ orderVo.quantity
											+ '件  实付款：<strong>¥'
											+ checkMoneyByPoint(orderVo.pay_money)
											+ '</strong>'
											+ ' 		</div>'
											+ '</div>';
								}
							}
							$("#myOrder" + idx).append(showHtml);
						}
						setTimeout(function() {
							$(".initloading").hide();
						}, 800);
					} else if (data.code == 1011) {
						window.location.href = mainServer + "/weixin/tologin";
					}
				}
			});
}

// 去支付
var orderCode = "";
var payData = "";
function nextOrderPay(orderId, ordercode, payType) {
	if (null == orderId || "" == orderId || null == ordercode
			|| "" == ordercode) {

		showvaguealert("订单ID或者编号为空");
		return false;
	}

	if (payType == 1) {
		if (!isCheckWeiXin("请在微信端支付，或选择支付宝支付")) {
			return;
		}
	}
	var $this = $("#" + orderId + " a");
	$.ajax({
		async : false,
		url : mainServer + "/weixin/payment/nextPrePay",
		data : {
			"orderId" : orderId,
			"orderCode" : ordercode
		},
		type : "POST",
		dataType : 'json',
		success : function(data) {
			if (data.result == 'succ') {
				if (data.payType == '1') {
					var record = data;
					orderCode = record.tradeNo;
					payData = data.payData;
					wexinBridge();
				} else if (data.payType == '2') {

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
				if (data.canBuy == 'NO') {
					$this.addClass('disable').removeAttr('onclick');
					$this.html("限购不能购买");
				}
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

function onBridgeReady() {
	WeixinJSBridge.invoke('getBrandWCPayRequest', payData, function(res) {
		// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 ok，但并不保证它绝对可靠。
		if (res.err_msg == "get_brand_wcpay_request:ok") {
			window.location.href = mainServer
					+ "/weixin/order/payResult?orderCode=" + orderCode; // 从支付完成进入订单详情
		} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
			showvaguealert('您已取消支付');
			setTimeout(function() {
				window.location.href = mainServer
						+ "/weixin/order/findList?status=" + type;
			}, 500);
		} else {
			showvaguealert('出错了');
			setTimeout(function() {
				window.location.href = mainServer
						+ "/weixin/order/findList?status=" + type;
			}, 500);
		}
	});
}

function checkOrder(orderId, orderProId, productId, orderCode, continueIndex,
		idx) {
	$.ajax({
		type : "POST",
		url : mainServer + "/weixin/order/orderStatus",
		data : {
			'tstatus' : 4,
			'orderId' : orderId,
			'orderproid' : orderProId
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			var obj = eval(data);
			if ("succ" == obj.result) { // 确认收货，按钮改为去评价
				$("#comfirmOrder" + continueIndex).attr(
						"href",
						"javascript:toCommentOrder(" + productId + ","
								+ orderId + ",'" + orderCode + "'," + idx
								+ ");");
				$("#comfirmOrder" + continueIndex).html("去评价");
			} else {
				showvaguealert(data.msg);
			}
		}
	});
}

// 删除订单
function delOrder(orderId, orderproid, continueIndex) {
	if (null == orderId || "" == orderId) {
		showvaguealert("请选择需要删除的订单ID");
		return false;
	}

	$.ajax({
		type : "POST",
		url : mainServer + "/weixin/order/deleteOrderById",
		data : {
			'orderId' : orderId,
			"orderproid" : orderproid
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			var obj = eval(data);
			if ("succ" == obj.result) {
				showvaguealert("删除成功");
				$('#cancelOrder' + continueIndex).remove();
			} else {
				showvaguealert(data.msg);
			}
		}
	});
}

function cancelOrder(orderId) {
	if (null == orderId || "" == orderId) {
		showvaguealert("请选择需要取消的订单");
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
			showvaguealert("取消成功");
			if ("succ" == obj.result) {
				setTimeout(function() {
					window.location.href = mainServer
					+ "/weixin/order/findList?status=" + type;
				}, 1000);
			} else {
				showvaguealert(data.msg);
			}
		}
	});
}

function toCommentOrder(productId, orderId, orderCode, idx) {
	window.location.href = mainServer + "/weixin/order/toCommentProduct?type="
			+ idx + "&productId=" + productId + "&orderId=" + orderId
			+ "&orderCode=" + orderCode;
}
// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

/** 倒计时
 * @param starttime 后台返回的时间
 * @param classOrder 倒计时的当前节点前缀
 * @param orderId 订单ID
 */
var timecount = function(starttime,classOrder, orderId){
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
			$('.'+classOrder+orderId).html(_html);
			if(_html == '00:00'){
				clearInterval(countDown);
				$('.'+classOrder+orderId).html('超时');
				$('.'+classOrder+orderId).parent().css('background','#eee');
				$('.'+classOrder+orderId).parent().removeAttr('href');
				cancelOrder(orderId);
				return;
			};
		},1000)
	}else{
		return;
	}
};
$(function() {

	var url = mainServer + "/weixin/tologin";

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

	// 返回上一个页面
	$('.backPage').click(function() {
		window.location.href = url;
	});
});
