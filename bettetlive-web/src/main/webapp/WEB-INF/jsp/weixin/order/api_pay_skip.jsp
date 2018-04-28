<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" type="text/css" href="${resourcepath}/alipay/css/common.css">
		<link rel="stylesheet" type="text/css" href="${resourcepath}/alipay/css/payment.css">
		<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<title>挥货-支付宝跳转</title>
		<script >
		var orderCode ="${orderCode}";
		var orderStatus = "${orderStatus}";
		var mainServer = "${mainserver}";
		</script>
</head>
<body>
<div class="supernatant">
    <div class="content">
		<div class="orderform">
			<div class="payhint">
				订单支付
			</div>
			<div class="resulthint">
				<div class="y-ico"></div>
				<div class="txt">
					<h4>支付信息加载成功</h4>
					<p>因微信无法处理支付宝请求</p>
					
					<p>请您按照以下步骤继续完成支付</p>
				</div>
			</div>
			<div class="text">
				<p>一丶点击微信客户端右上角（三个小点）</p>
				<p>二丶选择在其他浏览器打开。苹果系统选择“在Safari中打开”,安卓系统选在“在浏览器中打开”即可</p>
				<p>三丶在外部浏览器中打开后继续完成支付，然后支付成功后返回此页面刷新，即可看到订单状态</p>
			</div>
		</div>    	
    </div>
    <div class="fiter" onclick="hideTip()">
    </div>
    <div class="hint"></div>
   </div>
   <script src="${resourcepath}/weixin/js/order/api_pay_skip.js"></script> 
</body>
</html>