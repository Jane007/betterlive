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
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801261436" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/invite/customerInvite.css?t=201801271436" />
		<title>新人专享</title>
		<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<script>
  			document.documentElement.style.fontSize = document.documentElement.clientWidth / 10.8 + 'px';
  		</script>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var resourcepath = '${resourcepath}';
    		var customerId = '${myCust.customer_id}';
    		var orderNum = '${orderNum}';
    		var recordId = '${recordId}';
    		
    		
    		var title = "挥货 - 送你一份新人专属豪礼，速来领取！";  
			var desc = "${inviteInfo.objValue}";
			var link = '${mainserver}/weixin/customerinvite/shareRegister?customerId=${customerVo.customer_id}&sysInviteId=${inviteInfo.sysInviteId}&recordId=${recordId}';
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
		
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
		<div class="act_header">
			<a class="rule" id="ruleBtn">规则</a>
			<div class="act_info">
				<div class="user_head">
					<img src="${customerVo.head_url}">
				</div>
				<h2>我是${customerVo.nickname}</h2>
				<p>悄悄告诉你，${inviteInfo.objValue}送你一份福利，希望你喜欢~</p>
			</div>
			<div class="getaward"></div>
			<div class="newUserDraw"></div>
		</div>
		<div class="act_body">
			<ul class="drawList">
			</ul>
		</div>
		<div class="act_footer" onclick="toDownloadApp()">
			<a class="others" href="javascript:void(0);"></a>
		</div>
		<!-- 活动规则 -->
		<div class="popMask" id="rulePop">
			<div class="act_rule">
				<div class="rule_head">活动规则</div>
				<div class="rule_con">
					<p>1.新人专享商品仅限挥货新用户下单购买.</p>
					<p>2.新人专享商品仅限领券购买，并且只能享受一次优惠.</p>
					<p>3.同一登录账号，同一手机号，同一终端设备号，同一支付账户，同一收货地址，同一IP或其他合理显示同一的用户情形，均视为同一用户，不能重复享受新用户优惠.</p>
					<p>4.通过不正当手段获得的奖励，挥货有权撤销奖励及相关交易订单.</p>
					<i class="closePop"></i>
				</div>
			</div>
		</div>
		<!-- 活动规则 -->
		<div class="popMask" id="jumpPop">
			<div class="act_jump">
				<div class="jump_head"></div>
				<div class="text-wrap">
					<p>
						恭喜你,<br>
						成功领取100元新人大礼包;<br>
						可到我的—优惠券里查看哦~
					</p>
					<div class="btn">去商城使用</div>
					<div class="obtn"><span>好的</span></div>
				</div>
			</div>
		</div>
		
		<div class="initloading"></div>
		<div class="vaguealert">
			<p></p>
		</div>
	</body>
	<script type="text/javascript" src="${resourcepath}/weixin/js/common.js?t=201801261436"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/invite/wx_customer_invite.js?t=201801261436"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802231334"></script>
	
</html>
					