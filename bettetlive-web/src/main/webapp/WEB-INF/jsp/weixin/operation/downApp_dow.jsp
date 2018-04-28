<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/index.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<title>挥货-你的美食分享平台</title>
    	<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
    	</script>
	</head>
	
	<body class="dowbgbox">
		<div class="dowbgboxs"> 
			<a class="dowapp" href="javascript:todownload();">立即下载APP</a>
		</div>
	 
		<div class="vaguealert">
			<p></p>
		</div>
	</body>
	
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script> 
 	<script>
 		var sourceType = null;
		$(function(){
			sourceType = navigator.userAgent;
			
			var url = window.location.href;
			if(url.indexOf("#")!=-1){
				url = url.substring(0,url.indexOf("#"));
			}
			
			$.ajax({
				url : 'http://www.hlife.shop/huihuo/weixin/shareWeixin',
				data:{
					"url":url,
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
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
				},
				error : function(data) {
					return false;
				}
			});
	});

	var title = "挥货 - 你的美食分享平台";  
	var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
	var link = 'http://www.hlife.shop/huihuo/weixin/operation/basicDownApp';
	var imgUrl = "http://www.hlife.shop/huihuo/resources/weixin/img/huihuologo.png";

	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	wx.ready(function(){
		// 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
		wx.onMenuShareTimeline({
		  	title: desc,
		  	desc: desc,
		  	link: link,
		  	imgUrl: imgUrl,
		  	trigger: function (res) {
		    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
		    	//alert('用户点击分享到朋友圈');
		 	},
		  	success: function (res) {
		    	//alert('已分享');
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
		    	//alert('已分享');
		  	},
		  	cancel: function (res) {
		    	//alert('已取消');
		  	},
		  	fail: function (res) {
		   // 	alert(JSON.stringify(res));
		    	alert(JSON.stringify(res));
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
		    	//alert('已分享');
		  	},
		  	cancel: function (res) {
		    	//alert('已取消');
		  	},
		  	fail: function (res) {
		  		alert(JSON.stringify(res));
		  	}
		});
	});
		
		//点击去下载
		function todownload(){
			if(sourceType == null || sourceType == ""){
				showvaguealert("此网页不存在哦");
				return false;
			}
			if (sourceType.indexOf('iPhone') > -1) {//苹果手机
					window.location.href = "${iosDownloadUrl}";
			} else  if (sourceType.indexOf('Android') > -1 || sourceType.indexOf('Linux') > -1) {//安卓手机
					var lowerUrl = sourceType.toLowerCase();
					if(lowerUrl.match(/MicroMessenger/i) == 'micromessenger'){ //微信端
						var downloadUrl = "${verInfo.downloadUrl}";
						if(downloadUrl != null && downloadUrl != ""){
							window.location.href = downloadUrl;
						}else{
							showvaguealert("请在其它浏览器访问");
						}
					}else{
						var downloadUrl = "${verInfo.downloadUrl}";
						if(downloadUrl != null && downloadUrl != ""){
							window.location.href = downloadUrl;
						}else{
							var bakUrl = "${verInfo.bakUrl}";
							if(bakUrl != null && bakUrl != ""){
								window.location.href = bakUrl;
							}
						}
					}
			}else if (sourceType.indexOf('Windows Phone') > -1) {//winphone手机
					showvaguealert("请在android或ios的移动端打开");
			}else{
				showvaguealert("请在android或ios的移动端打开");
			}
		}
		
		   //提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},2000);
		}
 	</script>
</html>