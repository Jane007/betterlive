<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/personal.css" />
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<title>挥货-我的粉丝</title>
    	<script type="text/javascript">
			var mainServer = '${mainserver}';
			
			var title = "挥货 - 我的粉丝";  
			var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
			var link = '${mainserver}/weixin/socialityhome/toMyFans';
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
			var backUrl = "${backUrl}";
			if(backUrl==''){
				backUrl="${mainserver}/weixin/socialityhome/toSocialityHome";
			}
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
	
	<body style="background:#fff; "> 
		<div class="initloading"></div>
		<div class="zanwubg zwguanzhu" style="display: none;">发布优质帖子能吸引大量粉丝哦<br>快去试试吧~</div>   
		 <div id="dataList" class="gotuanlist">
		 </div> 
	 		<!--上拉加载-->
			<div class="weui-loadmore">
			<i class="weui-loading"></i>
				<span class="weui-loadmore__tips">正在加载</span>
			</div>
		<div class="vaguealert">
			<p></p>
		</div>
		<div class="bkbg" style="display: none;"></div> 
		<div class="shepassdboxs" style="display: none;">
				<span>确定要取消关注吗</span>  
				<div class="qushan">
					<a class="left" href="javascript:closeConcernAlert();">放弃</a>
					<a class="right" id="cancelId" href="javascript:void(0);">确定</a> 
				</div>
		</div>
		
	</body> 
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<!--  <script src="${resourcepath}/weixin/js/flexible.js"></script>-->
	<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	<script src="${resourcepath}/weixin/js/sociality/myFans.js"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
	
	 
</html>