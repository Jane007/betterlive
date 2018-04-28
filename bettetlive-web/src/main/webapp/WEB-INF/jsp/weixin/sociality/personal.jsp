<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>挥货-我的主页</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201802051355"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css?t=201802051355"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css?t=201802051355"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/sociality/personal.css?t=201802051357"/>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		
		<script type="text/javascript">
			var title = "挥货 - 个人主页";  
			var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
			var mainServer = '${mainserver}';
			var resourcepath = '${resourcepath}';
			var link = mainServer + '/weixin/socialityhome/toSocialityHome';
			var imgUrl = resourcepath + "/weixin/img/huihuologo.png";
			var theUrl = link;
			var backUrl = "${backUrl}";
			var sex = '${customerVo.sex}';
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
		<div class="initloading"></div>
		<div id="layout">
			<div class="header-wrap">
				<div class="header">
					<div class="top">
						<div class="goback"></div>
						<div class="set">
							<div class="pl" onclick="location.href='${mainserver}/weixin/discovery/toPublishDynamic?backType=2'"></div>
							<div class="oset" onclick="location.href='${mainserver}/weixin/customer/toCustomerModify'"></div>
						</div>
					</div>
					<div class="bottom">
						<a class="headphoto" href="${mainserver}/weixin/customer/toCustomerModify">
							<img src="${customerVo.head_url}"/>
						</a>
						<h3 class="" onclick="location.href='${mainserver}/weixin/customer/toCustomerModify'">
							${customerVo.nickname}
						</h3>
						<p class="tc" onclick="location.href='${mainserver}/weixin/customer/toCustomerModify'">
							<c:if test="${customerVo.signature != null && customerVo.signature != '' && customerVo.signature.length() > 0}">
								${customerVo.signature}
							</c:if>
							<c:if test="${customerVo.signature == null || customerVo.signature == '' || customerVo.signature.length() == 0}">
								添加个人签名，可以给小伙伴更好的印象哦~
							</c:if>
						
						</p>
						<div class="operate">
							<div class="attention">
								<a href="${mainserver}/weixin/socialityhome/toMyConcerns">关注<span>(${fansCountVo.customerCount})</span></a>
							</div>
							<div class="fans">
								<a class="fensi" href="${mainserver}/weixin/socialityhome/toMyFans">粉丝<span>(${fansCountVo.concernedCount})</span></a>
							</div>
							<div class="grade">
								<a href="${mainserver}/weixin/integral/helpHonor">等级：${customerVo.levelName}</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--主内容-->
			<div class="zanwubg" style="display: none;">您还未发表心得哦，快去试试吧</div> 
			<div class="content-wrap">
				<div class="content">
					<ul class="prolist">
						
						
					</ul>
					<div class="loadingmore">   
						<img src="${resourcepath}/weixin/img/timg.gif" alt="" />
						<p>正在加载更多的数据...</p>
					</div>
				</div>
			</div>
		</div>
		
		<input type="hidden" id="customerId" value="${customerVo.customer_id}" readonly="readonly">
		<input type="hidden" name="pageCount" id="pageCount" value="">
	    <input type="hidden" name="pageNow" id="pageNow" value="">
	    <input type="hidden" name="pageNext" id="pageNext" value="">
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	<script src="${resourcepath}/weixin/js/flexible.js"></script>
	<script src="${resourcepath}/weixin/js/sociality/personal.js?t=201802051355"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>
</html>


