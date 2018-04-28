<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common2.css?t=20171109034" />
  		<link rel="stylesheet" href="${resourcepath}/weixin/css/new-getRedpacket.css?v=20180424034" />
		<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-领取优惠券</title>
	</head>
	<body class="dpbgbox dpbgboxs">
		<input type="hidden" id="checkMobile" value="${customerVo.mobile}">
		<input type="hidden" id="cmId" value="${cmId}">
		<div class="guizbt"></div>
		<div class="container dancentbox">
		 	<div class="cpbgbox" style="height:6.8rem;position: relative;">
		 		<span style="position: absolute;bottom: 40px;font-size: 0.4rem;color: red;">
		 			<c:if test="${cmId != null && cmId == 75}">新用户专享，满69元减30元</c:if>
		 			<c:if test="${cmId != null && cmId == 55}">新用户专享，10元无门槛券</c:if>
	 			</span>
		 	</div>  
			<div id="mainBoxShow" class="mainBox">
				<div class="getBox" style="display: block;"> 
					<c:if test="${customerVo == null || customerVo.mobile == null || customerVo.mobile == ''}">
					<form action="">
					<ul>
						<li class="phoneBox">
							<input type="tel" id="phoneNum" placeholder="请输入手机号领取" maxlength="11"/>
							<div class="phonealert">
								<span></span>
							</div>
						</li>
						<li class="verifyCode">
							<input type="tel" id="msgCode" placeholder="请输入短信验证码"/>
							<span class="phoneCode">获取验证码</span>
							<div class="verifyalert">
								<span></span>
							</div>
						</li>
					</ul>
					</form>
					</c:if>
					<div class="now-get" onclick="register()">
						立即领取
					</div>
				</div>
			</div>
		</div>
		<div class="mask"></div>
		<div class="useRuleBox">
			<div class="ruleCon">
				<h3>••• 使用规则 •••</h3>
				<p>1.输入手机号，获取验证码即可获得现金抵扣券。</p>
				<p>2.每人仅限领取1个现金券。</p>
				<p>3.现金券查询请打开挥货商城→我的→优惠券</p>
				<p>4.现金券仅限购买挥货官网商品使用，不能兑换成现金。</p>
				<p>5.现金券使用：不找零、不折现，限一次性使用完（不能抵扣快递、物流或服务费）；如使用后发生退款，现金券不作为退款项。</p>
				<p>6.现金券支付部分不开发票（如需发票）。</p> 
				<p>7.订单退还：订单退还时，已经使用的现金券不再退还。</p>
				<p>8.本次活动最终解释权归挥货所有</p>

			</div>
			<div class="outBoxTitle"></div>
		</div>
		<div class="vaguealert">
			<p id="tishi"></p>
		</div>
		
	</body>
	
	<script src="${resourcepath}/weixin/js/common.js"></script>
	
	<script type="text/javascript">
		var mainServer = "${mainserver}";
		var ordSource = "";
		var theUrl = window.location.href;
		if(theUrl.indexOf("source")!=-1){
			ordSource = getQueryString("source");
			ordSource = "&source="+ordSource;
		}
		
		var title = "挥货 - 你的美食分享平台";  
		var desc = "挥货送礼啦，优惠享不停。";
		var link = mainServer + "/weixin/customercoupon/toCollarCoupon?cmId=${cmId}"+ordSource;
		var imgUrl = "http://www.hlife.shop/huihuo/resources/weixin/img/huihuologo.png";
   		var _hmt = _hmt || [];
		(function() { 
			  
		  var hm = document.createElement("script");
		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
    </script>
    	
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/coupon/wx_collar_coupon.js"></script>
</html>
