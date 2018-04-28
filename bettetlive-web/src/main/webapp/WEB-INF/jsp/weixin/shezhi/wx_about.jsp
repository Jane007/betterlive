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
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801261746" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/personalCenter.css?t=2018021651" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-关于我们</title>
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
	<body class="aboutbg">		
		  <div class="aboutlogo">
		  	<img src="${resourcepath}/weixin/img/aboutlogo.png?version=201711061637" alt="" />
		  	<div class="logotext" style="font-size: 14px;color: #868686; text-align: center;margin-top: 0.15rem;">版本号:V1.2.7</div>
		  </div>
		  <div class="aboutcomt">
		  	<a href="${mainserver}/weixin/usercenter/toIntroduce">挥货介绍</a>
		  	<a href="${mainserver}/weixin/usercenter/toContactUs">联系我们</a>
		  </div>
		  <div class="aboutfoot">
		  	汇梦电子商务技术有限公司版权所有
		  </div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
 	<script src="${resourcepath}/weixin/js/shezhi/wx_about.js"></script>
</html>

