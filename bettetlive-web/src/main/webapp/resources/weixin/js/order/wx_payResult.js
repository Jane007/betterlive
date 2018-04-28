var sourceType = null;
$(function() {
	$(".initloading").show();
	setTimeout(function() {
		$(".initloading").hide();
	}, 800);

	sourceType = navigator.userAgent;

	toCheck();

	if (groupJoinId > 0) {
		var link = mainServer + '/weixin/productgroup/toJoinGroup?userGroupId='
				+ userGroupId + '&productId=' + productId + '&specialId='
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
					alert(JSON.stringify(res));
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
					alert(JSON.stringify(res));
				}
			});
		});

		wx.error(function(res) {
			alert(res.errMsg);
		});
	}
});

function openTipAlert() {
	$(".shepassdboxs").show();
	$(".bkbg").show();
}

function closeTipAlert() {
	$(".shepassdboxs").hide();
	$(".bkbg").hide();
}
// 判断是什么手机，是不是在微信页
function toCheck() {
	if (sourceType == null || sourceType == "") {
		alert("此网页不存在哦");
		return;
	}
	if (sourceType.indexOf('iPhone') > -1 || sourceType.indexOf('Android') > -1
			|| sourceType.indexOf('Linux') > -1
			|| sourceType.indexOf('Windows Phone') > -1) {// 苹果、安卓手机
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) != 'micromessenger') { // 非微信
			$.ajax({
				url : mainServer + '/weixin/getLoginInfo',
				data : {},
				async : false,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.code == "1011") { // 非微信访问到此页面，如果是没登录，提示是否返回微信
						openTipAlert();
						$("#backWeixin").show();
					}
				}
			});
		}
	}
}

function invitedJoin() {

	if (groupJoinId <= 0) {
		return;
	}
	showShare();
}

function showShare() {
	$(".dia-mask").show();
	$(".zhuantifx").show();
}

function closeShareOrCode() {
	$(".dia-mask").hide();
	$(".zhuantifx").hide();
	$(".erbgtu").hide();
}

function scanJoin() {
	showCode();
}

function showCode() {
	$(".dia-mask").show();
	$(".erbgtu").show();
}

function shareProduct() {
	$.ajax({
		url : mainServer + "/weixin/share/addShare",
		data : {
			"shareType" : 1,
			"objId" : productId
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

function toOrder() {
	$.ajax({
		url : mainServer + '/weixin/getLoginInfo',
		data : {},
		async : false,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			var backUrl = mainServer + "/weixin/order/findList?status=0";
			if (data.code == "1011") { // 非微信访问到此页面，如果是没登录，提示是否返回微信
				window.location.href = mainServer + "/weixin/tologin?backUrl="
						+ backUrl;
			} else {
				window.location.href = backUrl;
			}
		}
	});

}

function toDownApp() {
	if (sourceType == null || sourceType == "") {
		alert("此网页不存在哦");
		return;
	}

	var iosUrl = "https://itunes.apple.com/cn/app/jie-zou-da-shi/id1237548987?mt=8";
	var androidUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.klsw.betterlive";
	if (sourceType.indexOf('iPhone') > -1) {// 苹果手机
		window.location.href = iosUrl;
	} else if (sourceType.indexOf('Android') > -1
			|| sourceType.indexOf('Linux') > -1) {// 安卓手机
		window.location.href = androidUrl;
	} else {
		alert("请在android或ios的移动端打开");
	}
}

$(function() {

	var url = mainServer + "/weixin/order/findList?status=0";
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