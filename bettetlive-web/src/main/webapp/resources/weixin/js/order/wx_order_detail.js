$(function() {
	$(".initloading").show();
	setTimeout(function() {
		$(".initloading").hide();
	}, 800);

	if (groupJoinId > 0) {
		var link = mainServer + '/weixin/productgroup/toJoinGroup?userGroupId='
				+ userGroupId + '&productId=' + product_id + '&specialId='
				+ specialId;

		var qrcode = new QRCode(document.getElementById("qrcode"), {
		// width : 100,//设置宽高
		// height : 100
		});

		qrcode.makeCode(link);

		var url = window.location.href;
		if (url.indexOf("#") != -1) {
			url = url.substring(0, url.indexOf("#"));
		}
		$.ajax({
			url : mainServer + '/weixin/shareWeixin',
			data : {
				"url" : url,
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				wx.config({
					debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId : data.shareInfo.appId, // 必填，公众号的唯一标识
					timestamp : data.shareInfo.timestamp, // 必填，生成签名的时间戳
					nonceStr : data.shareInfo.nonceStr, // 必填，生成签名的随机串
					signature : data.shareInfo.signature,// 必填，签名，见附录1
					jsApiList : [ // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					'onMenuShareTimeline', 'onMenuShareAppMessage',
							'onMenuShareQQ' ]
				});
			},
			failure : function(data) {
				showvaguealert('出错了');
			}
		});

		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
		if (shareExplain != null && shareExplain != "") {
			desc = shareExplain;
		}

		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.ready(function() {
			// 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareTimeline({
				title : title,
				desc : desc,
				link : link,
				imgUrl : imgUrl,
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					// alert('用户点击分享到朋友圈');
				},
				success : function(res) {
					shareProduct();
				},
				cancel : function(res) {
					// alert('已取消');
				},
				fail : function(res) {
					alert(JSON.stringify(res));
				}
			});

			// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareAppMessage({
				title : title,
				desc : desc,
				link : link,
				imgUrl : imgUrl,
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					// alert('用户点击分享到朋友圈');
				},
				success : function(res) {
					shareProduct();
				},
				cancel : function(res) {
					// alert('已取消');
				},
				fail : function(res) {
					// alert(JSON.stringify(res));
					showvaguealert(JSON.stringify(res));
				}
			});

			// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareQQ({
				title : title,
				desc : desc,
				link : link,
				imgUrl : imgUrl,
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					// alert('用户点击分享到朋友圈');
				},
				success : function(res) {
					shareProduct();
				},
				cancel : function(res) {
					// alert('已取消');
				},
				fail : function(res) {
					showvaguealert(JSON.stringify(res));
				}
			});
		});

		wx.error(function(res) {
			showvaguealert(res.errMsg);
		});
	}
});
function checkOrder(orderId, orderProId, productId, orderCode) {
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
			if ("succ" == obj.result) { // 确认收货，重新加载本页
				window.location.reload();
			} else {
				showvaguealert(data.msg);
			}
		}
	});
}

// 删除订单
function delOrder(orderId, orderproid) {
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
				setTimeout(function() {
					window.location.href = mainServer
							+ "/weixin/order/findList?status=" + tabType;
				}, 1000);
			} else {
				showvaguealert(data.msg);
			}
		}
	});
}

function invitedJoin() {
	if (groupJoinId <= 0) {
		return;
	}
	showShare();
}

function shareProduct() {
	$.ajax({
		url : mainServer + "/weixin/share/addShare",
		data : {
			"shareType" : 1,
			"objId" : product_id
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			closeShare();
		},
		failure : function(data) {
			showvaguealert('访问出错');
		}
	});
}

function showShare() {
	$(".shad").show();
	$(".shareWx").show();
}

function closeShare() {
	$(".shad").hide();
	$(".shareWx").hide();
}

function showCode() {
	$(".shad").show();
	$(".erbgtu").show();
}

function closeShareOrCode() {
	$(".shad").hide();
	$(".shareWx").hide();
	$(".erbgtu").hide();
}

function scanJoin() {
	showCode();
}

$(function() {

	var url = mainServer + "/weixin/order/findList?status=" + tabType;

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