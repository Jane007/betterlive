<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
		<title>挥货-发现好友</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/discovery/wx_find_friends.css"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
			var mainServer = "${mainserver}";
			var resourcepath = "${resourcepath}";
			var title = "挥货 - 发现好友";  
			var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
			var link = '${mainserver}/weixin/discovery/toFindFriends';
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
			var backUrl = link;
			var myCustId = "${myCustId}";
			var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
		</script>
	</head>
	<body>
		<div class="initloading"></div>
		<div id="layout">
			<!--导航-->
			<div class="tab-wrap">
				<div class="tab">
					<div class="tab-wx">
						<span>微信好友</span>
					</div>
					<div class="tab-invite">
							<span>邀请有奖</span>
					</div>
				</div>
			</div>
			<div class="content-wrap">
				<div class="content">
					<!--好友列表-->
					<div class="friendlist">
					</div>
					<div class="loadingmore">   
						<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
					</div>
				</div>
			</div>
		</div>
		
		<div class="bkbg"></div> 
		<div class="shepassdboxs">
				<span>确定要取消关注吗</span>  
				<div class="qushan">
					<a class="left" href="javascript:closeConcernAlert();">放弃</a>
					<a class="right" id="cancelId" href="javascript:void(0);">确定</a> 
				</div>
		</div>
		
		<div class="vaguealert">
			<p></p>
		</div>
		
		<input type="hidden" name="pageCount" id="pageCount" value="">
	    <input type="hidden" name="pageNow" id="pageNow" value="">
	    <input type="hidden" name="pageNext" id="pageNext" value="">
		
		<script src="${resourcepath}/weixin/js/common.js"></script> 
		<script src="${resourcepath}/weixin/js/discovery/wx_find_friends.js"></script> 
		<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
		
		
	</body>
</html>
