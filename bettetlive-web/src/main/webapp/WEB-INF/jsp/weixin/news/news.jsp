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
	<meta name="keywords" content="挥货,系统消息" /> 
	<meta name="description" content="挥货商城每周新品首发为您推荐优质农产品，提升生活品质。" /> 
	<title>
		消息中心
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
	<link rel="stylesheet" href="${resourcepath}/weixin/css/news/news.css?t=201801291550" />
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
 	<script type="text/javascript">
 		var mainServer = "${mainserver}";
 		
 		var title = "挥货 - 你的美食分享平台";  
		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
		var link = '${mainserver}/weixin/message/showUnread';
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
<body style="padding-left:0.28rem;"> 
	<div class="initloading" style="top: 25%;"></div>
	
	<div class="newbox">
		<a href="${mainserver}/weixin/message/toMessageList?msgType=1">
		<span>
			<c:if test="${result.activityCount > 0 && result.activityCount <= 99}">
			<em>${result.activityCount}</em>
			</c:if>
			<c:if test="${result.activityCount > 99}">
			<em>99+</em>
			</c:if>
		</span>
		<p class="title">
			精选活动
			<strong>
					${result.activityContent}
			</strong>
		</p>
		<p class="timenews">
			<c:if test="${result.activityTime != null}">
				${result.activityTime}
			</c:if>
		</p>
		</a>
	</div>
	<div class="newbox">
		<a href="${mainserver}/weixin/message/toMessageList?msgType=2">
		<span>
			<c:if test="${result.assetCount > 0 && result.assetCount <= 99}">
				<em>${result.assetCount}</em>
			</c:if>
			<c:if test="${result.assetCount > 99}">
				<em>99+</em>
			</c:if>
		</span>
		<p class="title">
			我的资产
			<strong>
				${result.assetContent}
			</strong>
		</p>
		<p class="timenews">
			<c:if test="${result.assetTime != null}">
				${result.assetTime} 
			</c:if>
		</p>
		</a>
	</div>
	<div class="newbox">
		<a href="${mainserver}/weixin/message/toMessageList?msgType=4">
		<span>
			<c:if test="${result.interactCount > 0 && result.interactCount <= 99}">
				<em>${result.interactCount}</em>
			</c:if>
			<c:if test="${result.interactCount > 99}">
				<em>99+</em>
			</c:if>
		</span>
		<p class="title">
			我的互动
			<strong>
				${result.interactContent}
			</strong>
		</p>
		<p class="timenews">
			<c:if test="${result.interactTime != null}">
				${result.interactTime}
			</c:if>
		</p>
		</a>
	</div>
	<div class="newbox">
		<a href="${mainserver}/weixin/message/toMessageList?msgType=3">
		<span>
			<c:if test="${result.transCount > 0 && result.transCount <= 99}">
				<em>${result.transCount}</em>
			</c:if>
			<c:if test="${result.transCount > 99}">
				<em>99+</em>
			</c:if>
		</span>
		<p class="title">
			交易消息
			<strong>
				${result.transContent}
			</strong>
		</p>
		<p class="timenews">
			<c:if test="${result.transTime != null}">
				${result.transTime}
			</c:if>
		</p>
		</a>
	</div>
	<div class="newbox">
		<a href="${mainserver}/weixin/message/toMessageList?msgType=0">
		<span>
			<c:if test="${result.sysCount > 0 && result.sysCount <= 99}">
				<em>${result.sysCount}</em>
			</c:if>
			<c:if test="${result.sysCount > 99}">
				<em>99+</em>
			</c:if>
		</span>
		<p class="title">
			系统消息
			<strong>
					${result.sysContent}
			</strong>
		</p>
		<p class="timenews">
			<c:if test="${result.sysTime != null}">
				${result.sysTime}
			</c:if>
		</p>
		</a>
	</div>
	<div class="newbox">
		<a href="${mainserver}/weixin/message/toMessageList?msgType=5">
		<span>
			<c:if test="${result.followCount > 0 && result.followCount <= 99}">
				<em>${result.followCount}</em>
			</c:if>
			<c:if test="${result.followCount > 99}">
				<em>99+</em>
			</c:if>
		</span>
		<p class="title">
			新增关注
			<strong>
				${result.followContent}
			</strong>
		</p>
		<p class="timenews">
			<c:if test="${result.followTime != null}">
				${result.followTime}
			</c:if>
		</p>
		</a>
	</div>
<script type="text/javascript" src="${resourcepath}/weixin/js/news/news.js?t=201802271511"></script>
<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802271456"></script>


</body>
</html>