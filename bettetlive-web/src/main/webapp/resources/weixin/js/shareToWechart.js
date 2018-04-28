$(function(){
	var url = window.location.href;
	if(typeof(newUrl)!='undefined'&&newUrl!=null){//分享授权的页面，可以传参数
		url = newUrl; 
	}
	
	if(url.indexOf("#")!=-1){
		url = url.substring(0,url.indexOf("#"));
	}
	
	$.ajax({                                       
		url:mainServer+'/weixin/shareWeixin',
		data:{
			"url":url,
		},
		type:'post',
		dataType:'json',
		success:function(data){
			wx.config({
			    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			    appId: data.shareInfo.appId, // 必填，公众号的唯一标识
			    timestamp: data.shareInfo.timestamp, // 必填，生成签名的时间戳
			    nonceStr: data.shareInfo.nonceStr, // 必填，生成签名的随机串
			    signature: data.shareInfo.signature,// 必填，签名，见附录1
			    jsApiList: [ // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					'onMenuShareTimeline',
					'onMenuShareAppMessage',
					'onMenuShareQQ'
			    ]
			});
			
			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			wx.ready(function(){
				// 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
				wx.onMenuShareTimeline({
				  	title: title,
				  	desc: desc,
				  	link: link,
				  	imgUrl: imgUrl,
				  	trigger: function (res) {
				    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				    	//alert('用户点击分享到朋友圈');
				 	},
				  	success: function (res) {
				  		//分享专题文章之类的
				  		if(typeof(shareSpecial)=="function"){
				  			shareSpecial();
				  		}
				  		//分享产品成功
				  		if(typeof(shareProduct)=="function"){
				  			shareProduct();
				  		}
				  		
				  	},
				  	cancel: function (res) {
				    	//alert('已取消');
				  	},
				  	fail: function (res) {
				    	alert(JSON.stringify(res));
				  	}
				});
				  
				// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
				wx.onMenuShareAppMessage({
					title: title,
				  	desc: desc,
				  	link: link,
				  	imgUrl: imgUrl,
				  	trigger: function (res) {
				    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				    	//alert('用户点击分享到朋友圈');
				 	},
				  	success: function (res) {
				  	//分享专题文章之类的
				  		if(typeof(shareSpecial)=="function"){
				  			shareSpecial();
				  		}
				  		//分享产品成功
				  		if(typeof(shareProduct)=="function"){
				  			shareProduct();
				  		}
				  	},
				  	cancel: function (res) {
				    	//alert('已取消');
				  	},
				  	fail: function (res) {
				   // 	alert(JSON.stringify(res));
				    	showvaguealert(JSON.stringify(res));
				  	}
				});
				
				// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
				wx.onMenuShareQQ({
					title: title,
				  	desc: desc,
				  	link: link, 
				  	imgUrl: imgUrl,
				  	trigger: function (res) {
				    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				    	//alert('用户点击分享到朋友圈');
				 	},
				  	success: function (res) {
				  		//分享专题文章之类的
				  		if(typeof(shareSpecial)=="function"){
				  			shareSpecial();
				  		}
				  		//分享产品成功
				  		if(typeof(shareProduct)=="function"){
				  			shareProduct();
				  		}
				  	},
				  	cancel: function (res) {
				    	//alert('已取消');
				  	},
				  	fail: function (res) {
				  		showvaguealert(JSON.stringify(res));
				  	}
				});
			});
			
			wx.error(function (res) {
				showvaguealert(res.errMsg);
			});
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
	
	
});
