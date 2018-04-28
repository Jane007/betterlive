//微信分享
var wxShare = function(appId,timestamp,nonceStr,signature,title,link,imgUrl,shareExplain,objId,shareType){
	wx.config({
	    debug:false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId:appId, // 必填，企业号的唯一标识，此处填写企业号corpid
	    timestamp: timestamp, // 必填，生成签名的时间戳
	    nonceStr: nonceStr, // 必填，生成签名的随机串
	    signature: signature,// 必填，签名，见附录1
	    jsApiList: [// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	    		'onMenuShareTimeline',
	    		'onMenuShareAppMessage',
	    		'onMenuShareQQ',
	    		'onMenuShareWeibo',
	    		'onMenuShareWeibo'
	    	] 
	});
	
	wx.ready(function(){
	   	/*
	   	 *config信息验证后会执行ready方法
	   	 *所有接口调用都必须在config接口获得结果之后，
	   	 *config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
	   	 *则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口
	   	 *则可以直接调用，不需要放在ready函数中
	   	 * */
		//分享到朋友圈
		wx.onMenuShareTimeline({
			title:title, 	 // 分享标题
			link:link, 	 	// 分享链接，该链接域名必须与当前企业的可信域名一致
			imgUrl:imgUrl, // 分享图标
			success: function(){
				// 用户确认分享后执行的回调函数
				if(typeof(shareVideo)=="function" && (shareType=='video')){
					shareVideo(objId);
				}
				//分享专题文章之类的
				if(typeof(shareSpecial)=="function" && (shareType=='article')){
					shareSpecial(objId);
				}
			},
			cancel: function () {
				// 用户取消分享后执行的回调函数
			}
		});
		//分享到朋友
		wx.onMenuShareAppMessage({
			title: title, //分享标题
			desc: shareExplain, //分享描述
			link:link, //分享链接，该链接域名必须与当前企业的可信域名一致
			imgUrl:imgUrl, //分享图标
			type: '', // 分享类型,music、video或link，不填默认为link
			dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			success: function () {
				// 用户确认分享后执行的回调函数
				if(typeof(shareVideo)=="function" && (shareType=='video')){
					shareVideo(objId);
				}
				//分享专题文章之类的
				if(typeof(shareSpecial)=="function" && (shareType=='article')){
					shareSpecial(objId);
				}
			},
			cancel: function () {
				// 用户取消分享后执行的回调函数
			}
		});
		//分享到qq
		wx.onMenuShareQQ({
			title: title, // 分享标题
			desc: shareExplain, // 分享描述
			link: link, // 分享链接
			imgUrl: imgUrl, // 分享图标
			success: function () {
				// 用户确认分享后执行的回调函数
				if(typeof(shareVideo)=="function" && (shareType=='video')){
					shareVideo(objId);
				}
				//分享专题文章之类的
				if(typeof(shareSpecial)=="function" && (shareType=='article')){
					shareSpecial(objId);
				}
			},
			cancel: function () {
				// 用户取消分享后执行的回调函数
			}
		});
		//分享到腾讯微博
		wx.onMenuShareWeibo({
			title: title, // 分享标题
			desc: shareExplain, // 分享描述
			link:link, // 分享链接
			imgUrl: imgUrl, // 分享图标
			success: function () {
				// 用户确认分享后执行的回调函数
				if(typeof(shareVideo)=="function"){
					shareVideo(objId);
				}
			},
			cancel: function () {
				// 用户取消分享后执行的回调函数
			}
		});
		
		wx.onMenuShareWeibo({
			title:title, // 分享标题
			desc: shareExplain, // 分享描述
			link: link, // 分享链接
			imgUrl: imgUrl, // 分享图标
			success: function () {
				// 用户确认分享后执行的回调函数
				if(typeof(shareVideo)=="function" && (shareType=='video')){
					shareVideo(objId);
				}
				//分享专题文章之类的
				if(typeof(shareSpecial)=="function" && (shareType=='article')){
					shareSpecial(objId);
				}
			},
			cancel: function () {
				// 用户取消分享后执行的回调函数
			}
		});
	});
}