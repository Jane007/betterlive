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
		<meta name="description" content="挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！" /> 
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/discovery/wx_selected_retrospect.css?201803281035"/>
		
    	<script src="${resourcepath}/weixin/js/flexible.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<title>挥货-精选</title>
    	<script type="text/javascript">
			var mainServer = '${mainserver}';
			var resourcepath = "${resourcepath}";
			var customerId = "${customerId}";
			var title = "挥货 - 精选好文";  
	 		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
	 		var link = '${mainserver}/weixin/discovery/toSelectedRetrispect';
	 		var theUrl =link;
	 		var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
			var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm,s);
			})();
		</script>
	</head>
	
	<body>
		 
		<div class="initloading"></div>
		<div id="layout">
			<!--主内容-->
			<div class="content-wrap">
				<div class="content">
					<!-- 每周精选 -->
					<div class="recommendation-wrap week">
						<div id="recList" class="recommendation">
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<input type="hidden" name="pageCount" id="pageCount" value="">
	    <input type="hidden" name="pageNow" id="pageNow" value="">
	    <input type="hidden" name="pageNext" id="pageNext" value="">
		<div class="loadingmore">   
			<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
		</div>
		<div class="zanwubg" style="display: none;">暂无内容，小编正在上传中</div>  
		<div class="vaguealert">
			<p></p>
		</div>
	</body> 
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script> 
	<script src="${resourcepath}/weixin/js/discovery/wx_selected_retrospect.js"></script> 
 	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802251511"></script>
 	
</html>