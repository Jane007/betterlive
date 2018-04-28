<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String ref = request.getHeader("REFERER");%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css?t=201802051355"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css?t=201802051355"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/sociality/other_personal.css?t=201802240943"/>
		
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<title>挥货-Ta的主页</title>
    	<script type="text/javascript">
			var mainServer = '${mainserver}';
			var resourcepath = '${resourcepath}';
			var sex = '${customerVo.sex}';
			var title = "挥货 - TA的主页";  
			var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
			var link = '${mainserver}/weixin/socialityhome/toOtherSocialityHome?otherCustomerId=${customerVo.customer_id}';
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
			var backUrl = "${backUrl}";
			var fansId= "${concernedVo.fansId}";
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
					</div>
					<div class="bottom">
						<a class="headphoto">
							<img src="${customerVo.head_url}"/>
						</a>
						<h3 class="">${customerVo.nickname}</h3>
						<p class="tc">
							<c:if test="${customerVo.signature != null && customerVo.signature != '' && customerVo.signature.length() > 0}">
								${customerVo.signature}
							</c:if>
							<c:if test="${customerVo.signature == null || customerVo.signature == '' || customerVo.signature.length() == 0}">
								添加个人签名，可以给小伙伴更好的印象哦~
							</c:if>
						</p>
						<div class="operate">
							<div class="attention">
								<a href="${mainserver}/weixin/socialityhome/toOtherConcerns?otherCustId=${customerVo.customer_id}">关注<span>(${fansCountVo.customerCount})</span></a>
							</div>
							<div class="fans">
								<a href="${mainserver}/weixin/socialityhome/toOtherFans?otherCustId=${customerVo.customer_id}">粉丝<span>(<label id="fansCount">${fansCountVo.concernedCount}</label>)</span></a>
							</div>
							<div class="grade">
								<a href="${mainserver}/weixin/integral/helpHonor">等级：${customerVo.levelName}</a>
							</div>
						</div>
					</div>
					<div class="oattention">
						<c:if test="${concernedVo != null && concernedVo.fansId > 0}">
		  			 		<a id="guanzhuId" href="javascript:concernedOrCancel(${concernedVo.fansId});" class='hasattention'>已关注</a> 
		  			 	</c:if>
		  			 	<c:if test="${concernedVo == null || concernedVo.fansId <= 0}">
		  			 		<a id="guanzhuId" href="javascript:concernedOrCancel(0);" class="oattentionon">+关注</a>  
		  			 	</c:if>
					</div>
				</div>
			</div>
			 <div class="zanwubg">这家伙很懒，什么都没留下</div> 
			<!--主内容-->
			<div class="content-wrap">
				<div class="content">
					<ul class="prolist">
					</ul>
					<div class="loadingmore">   
					<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
				</div>
				</div>
			</div>
		</div>
		 
		<div class="bkbg" style="display: none;"></div> 
		<div class="shepassdboxs" style="display: none;">
				<span>确定取消关注吗？</span>  
				<div class="qushan">
					<a class="left dia-cancel" href="javascript:closeConcernAlert();">放弃</a>
					<a class="right dia-addCard" href="javascript:makeSureCancelAttention();">确定</a> 
				</div>
		</div>
		 
		  		
		  <input type="hidden" id="myCustId" value="${myCustId}" readonly="readonly">
		  <input type="hidden" id="otherCustId" value="${customerVo.customer_id}" readonly="readonly">
		  <input type="hidden" name="pageCount" id="pageCount" value="">
	      <input type="hidden" name="pageNow" id="pageNow" value="">
	      <input type="hidden" name="pageNext" id="pageNext" value="">
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	<script src="${resourcepath}/weixin/js/flexible.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/sociality/other_personal.js?t=201802241006"></script>
	<script src="${resourcepath}/weixin/js/jquery.VMiddleImg.js"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>
</html>