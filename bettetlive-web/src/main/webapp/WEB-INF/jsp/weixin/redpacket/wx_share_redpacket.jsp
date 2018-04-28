<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<meta name="description" content="美味无限，用红包更划算。" /> 
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/shareRedpacket.css" />
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-分享领红包</title>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		
	   		
	   		var title = "小伙伴们，快来领取挥货${coupon.coupon_money}元红包~";  
			var desc = "美味无限，用红包更划算。";
			var link = '${mainserver}/weixin/shareredpacket/getShareRedPacket?orderCode=${orderCode}';
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
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="hongbaoPic"><img src="${resourcepath}/weixin/img/hongbao.png" alt="" /></div>
			<p class="getRed">恭喜你获得${sharecount}个红包</p>
			<p class="shareWith">分享给好友一起领红包吧</p>
			<div class="shareRedpacket">分享领红包</div>
			<div class="red-back">
				<img src="${resourcepath}/weixin/img/zhuback.png" alt="" />
			</div>
		</div>
		<div class="mask"></div>
		<div class="dia-share">
			<p class="share-friend">将该页面分享给好友</p>
			<p class="can-get">好友与你都可以领取红包</p>
			<div class="i-see">我知道了</div>
			<div class="zhiyin"></div>
		</div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript">
		var sharecount='${sharecount}';
		
		$(function(){
			
			$('.red-back').click(function(){
				window.history.go(-1);
			});
			
			//提示弹框
			function showvaguealertback(con){
				$('.mask').show();
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function(){
					$('.vaguealert').hide();
					$('.mask').hide();
					window.location.href="${mainserver}/weixin/order/queryOrderDetails?orderCode=${orderCode}";
					
				},2000);
			}
			
			//提示弹框
			function showvaguealert(con){
				$('.mask').show();
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function(){
					$('.vaguealert').hide();
					$('.mask').hide();
				},2000);
			}
			
			
			if(null ==sharecount || ""==sharecount){
				showvaguealertback("分享红包活动已结束");				
			}
			
			$('.shareRedpacket').click(function(){
				$('.mask').show();
				$('.dia-share').show();
			});
			$('.i-see').click(function(){
				$('.mask').hide();
				$('.dia-share').hide();
			});
			
		});
		
	</script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802271456"></script>
	
</html>	