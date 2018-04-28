<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/redPacketFinish.css" />
		<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-领红包</title>
		<script>
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
		<div class="container">
			<div class="headbg">
				<img src="${resourcepath}/weixin/img/lingqu.png" alt="" />
				<a href="javascript:;">使用规则</a>
			</div>
			<div class="mainBox">
				<div class="toLate">
					<label for=""></label>
					<span>
						<em>你来晚了，红包已被抢完</em>
						<em>下次记得快点哟</em>
					</span>
				</div>
				<div class="behavior">
					<p>更多优惠，更多好货</p>
					<p>去挥货逛逛吧</p>
					<a href="${mainserver}/weixin/index" class="stroll">去逛逛</a>
				</div>
			</div>
		</div>
			
	</body>

</html>	