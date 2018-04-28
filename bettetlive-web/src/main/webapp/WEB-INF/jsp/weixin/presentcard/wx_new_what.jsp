<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/presentCard.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-什么是礼品卡</title> 
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
	<body style="background: #fff;">
		 <div class="smlipbox">
		 	<span>什么是礼品卡？</span>
		 	<p>礼品卡是指定用户及时间段内消费的代金券或预付费卡，用户可以凭礼品卡在平台内使用，可以兑换平台上的与礼品卡本身金额数相等的所有产品；
		 	</p>
		 	<span>礼品卡如何使用？</span>
<p>1.在[我的]界面，点击[礼品卡]进入礼品卡界面，点击[添加礼品卡] ;</p>
<p>2.弹出设置支付密码界面，设置支付密码，填入手机号，获取并输入验证码，设置6位数字支付密码；</p>
<p>3.自动跳转到添加礼品卡界面，再次点击添加礼品卡；输入礼品卡密码，点击添加，完成礼品卡添加；</p>
<p>4.选择想购买的商品，结算时勾选礼品卡，完成支付。</p>

		 	
		 </div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/presentcard/wx_new_what.js"></script>
</html>
