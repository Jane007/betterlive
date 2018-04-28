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
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/discovery/wx_video_area.css"/>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<title>挥货-${svtVo.typeName}</title>
	</head>
	
	<body>
	
	<div class="initloading"></div>
		<div id="layout">
			<div class="content-wrap">
				<div class="content">
					<!--视频列表-->
					<ul class="videolist">
					</ul>
					<!--上拉加载-->
					<div class="weui-loadmore">
					  	<i class="weui-loading"></i>
					  	<span class="weui-loadmore__tips">正在加载</span>
					</div>
				</div>
			</div>
		</div>
		
		<div class="zanwubg" style="display: none;">暂无内容，小编正在上传中</div>
		<!--分享提示效果-->
		<div id="cover">
			<div id="guide"><img src="${resourcepath}/weixin/img/invite/guide1.png"></div>
		</div>
		<!--分享按钮弹窗-->
		<div class="share" onclick="hideShare();">
			<div class="rule">
				<div class="ruletext">
					<h3>分享有奖</h3>
					<p>分享可获得大量金币哦，点击右上角分享至微信即可获得金币，快来体验一下吧~</p>
					<div class="check" onclick="toHelp();">查看规则</div>
				</div>
			</div>
		</div>
		<!--分享成功弹窗提示-->
		<div class="shate-succeed">
			<div class="hold-bg">
				<div class="hold">
					<h3>恭喜你</h3>
					<p>获得了<span>3</span>个金币</p>
				</div>
			</div>
		</div>
	    
	</body> 
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	<script src="${resourcepath}/weixin/js/flexible.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	
	<script type="text/javascript">
			var mainServer = '${mainserver}';
			var customerId = "${customerId}";
			var typeId = "${svtVo.typeId}";
			var integralSwitch = "${integralSwitch}";
			var link = mainServer + "/weixin/discovery/toVideoArea?typeId=" + typeId + "&shareCustomerId=" + customerId;
			var title = "挥货 - 发现";  
			var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
			var backUrl = link;
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
			var shareCustomerId = 0;
			var checkUrl = window.location.href;
			if(checkUrl.indexOf("shareCustomerId")!=-1){
				shareCustomerId = getQueryString("shareCustomerId");
			}
			
			var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
	</script>
	
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
	<script src="${resourcepath}/weixin/js/discovery/wx_video_area.js?t=201804171048"></script>
 	
	
</html> 