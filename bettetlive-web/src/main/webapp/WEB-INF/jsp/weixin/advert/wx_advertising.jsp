<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/advert/wx_advertising.css?t=201811111111"/>
  		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-广告</title>
		<script type="text/javascript">
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
		<div id="layout">
			<div class="outtime">
				<div class="go fr" onclick="location.href='${mainserver}/weixin/index'">
					跳过<span>3</span>S
				</div>
			</div>
			<div class="btn">
				<a href="${mainserver}/weixin/advert/toAdvertGift"><img src="${resourcepath}/weixin/img/advert/gg.png"/></a>
			</div>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script> 
	<script type="text/javascript">
		var url = window.location.href;
		var ua = navigator.userAgent;
 		if(ua.indexOf('HuiHuoApp') > 0){
 			$('.outtime').hide;
 			$('.btn').hide;
 		}
		var codetime=setInterval(countdown,1000);
 		var i=3;
		function countdown(){
			i--;
			$('.go span').text(i);
			if(i<=0){
				i=0;
				clearInterval(codetime);
				if(ua.indexOf('HuiHuoApp') < 0){
					window.location.href="${mainserver}/weixin/index";
				}
			}
		}
	</script>
	
</html>
