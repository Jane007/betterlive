<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="r" uri="http://www.kingleadsw.com/taglib/replace" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection">
	<meta name="keywords" content="挥货,我的消息，活动消息" /> 
	<meta name="description" content="挥货商城每周新品首发为您推荐优质农产品，提升生活品质。" /> 
	<title>
		精选活动
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
	<link rel="stylesheet" href="${resourcepath}/weixin/css/news/news.css" />
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		
   		var title = "挥货 - 你的美食分享平台";  
		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
		var link = '${mainserver}/weixin/message/toMessageList?msgType=1';
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
<body style="background: #eee;">
	<div class="initloading"></div>
	
	<div class="noyhbg" style="display:none;">
	 	<span>暂无消息~</span>
	 </div>
	<div class="hdbox">
	 </div>
	 <div class="vaguealert">
		<p></p>
	 </div>
	 <div class="loadingmore">   
			<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
	</div>
	 <input type="hidden" name="pageCount" id="pageCount" value="">
	 <input type="hidden" name="pageNow" id="pageNow" value="">
	 <input type="hidden" name="pageNext" id="pageNext" value="">
	 <script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	 <script src="${resourcepath}/weixin/js/common.js"></script> 
	 <script type="text/javascript" src="${resourcepath}/weixin/js/news/hhactivity.js?t=201802271511"></script>
	 <script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802271456"></script>
	 
</body>
</html>