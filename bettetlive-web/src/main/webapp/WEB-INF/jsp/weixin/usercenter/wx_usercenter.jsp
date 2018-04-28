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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/personalCenter.css?t=201801261746" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-个人中心</title>
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
	<body>
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="mainBox" style="top:0;">
				<div class="userBox">			
					<a href="${mainserver}/weixin/usercenter/toUserSetting"></a>		
						<img src="${customer.head_url}" alt="" onclick="toSocialityHome()"/> 
						<span onclick="toSocialityHome()">${customer.nickname}</span>
						
				</div>
				<div class="PMbox">
						<div class="all-row" onclick="location.href='${mainserver}/weixin/order/findList?status=0'">
							<p class="PMtitle">我的订单</p>
							<a class="ckquan" href="javascript:void(0);">查看全部订单</a>      
						</div>
						<div class="all-row hei60">
							<ul class="manageList">
								<li>
									<a class="manageName myOrder" href="${mainserver}/weixin/order/findList?status=1">待付款</a>
									<c:if test="${waitPayOrderNum > 0 && waitPayOrderNum <= 99}">
									<em>${waitPayOrderNum}</em>
									</c:if>
									<c:if test="${waitPayOrderNum > 99}">
									<em>99+</em>
									</c:if>
								</li>
								<li>
									<a class="manageName myShop" href="${mainserver}/weixin/order/findList?status=2">待发货</a>
									<c:if test="${waitDeliveryOrderNum > 0 && waitDeliveryOrderNum <= 99}">
									<em>${waitDeliveryOrderNum}</em>
									</c:if>
									<c:if test="${waitDeliveryOrderNum > 99}">
									<em>99+</em>
									</c:if>
								</li>
								<li>
									<a class="manageName myAtten" href="${mainserver}/weixin/order/findList?status=3">待收货</a>
									<c:if test="${waitReceiveOrderNum > 0 && waitReceiveOrderNum <= 99}">
									<em>${waitReceiveOrderNum}</em>
									</c:if>
									<c:if test="${waitReceiveOrderNum > 99}">
									<em>99+</em>
									</c:if>
								</li>
								<li>
									<a class="manageName myStart" href="${mainserver}/weixin/order/findList?status=4">待评价</a>
									<c:if test="${waitCommentOrderNum > 0 && waitCommentOrderNum <= 99}">
									<em>${waitCommentOrderNum}</em>
									</c:if>
									<c:if test="${waitCommentOrderNum > 99}">
									<em>99+</em>
									</c:if>
								</li>
							</ul>
						</div>
				</div>
				
				<div class="personMenu">
					<div class="all-row">
						<a href="${mainserver}/weixin/customercoupon/myCoupon">
						    <p class="personMenuName yhq">优惠券</p> 
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row">
						<a href="${mainserver}/weixin/presentcard/toPresentcard">
						<%-- <a href="${mainserver}/weixin/presentcard/findList"> --%>
						    <p class="personMenuName redPac">礼品卡</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row">
						<a href="${mainserver}/weixin/customercoupon/mySingleCoupon">
						    <p class="personMenuName voucher">红包</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row">
						<a href="${mainserver}/weixin/usercenter/toMyCollection">
						    <p class="personMenuName myShop">我的收藏</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row">
						<a href="${mainserver}/weixin/addressmanager/toReceiverAddress">
						<p class="personMenuName realName">地址管理</p>
						<ins class="moreIco"></ins>
						</a>
					</div>
					
					<div class="all-row">
						<a href="${mainserver}/weixin/usercenter/contactUs">
						<p class="personMenuName myNews">联系客服</p>
						<ins class="moreIco"></ins>
						</a>
					</div>		
			</div>
			</div>	
			<div class="footer">
				<ul>
					<li class="homePage"><a href="${mainserver}/weixin/index"><em></em><i>首页</i></a></li>
					<li class="purchase"><a href="${mainserver}/weixin/product/toProductsByType"><em></em><i>分类</i></a></li> 
					<li class="special"><a href="${mainserver}/weixin/discovery/toSelected"><em></em><i>话题</i></a></li>
					<li class="shopping"><a href="${mainserver}/weixin/shoppingcart/toshoppingcar"><em></em><i>购物车</i></a>
						<span class="gwnb" <c:if test="${cartCnt == null || cartCnt<=0}">style="display:none;"</c:if>>
							<c:if test="${cartCnt>0 && cartCnt<= 99}">${cartCnt }</c:if>
							<c:if test="${cartCnt>99}">99+</c:if>
						</span>
					</li> 
					<li class="mine active"><a href="${mainserver}/weixin/tologin"><em></em><i>我的</i></a></li>
				</ul>
			</div>
		</div>
		<div class="mask" style="z-index: 8"></div>
		<div style="display: none;">
			<form id="form2" action="${mainserver}/weixin/search" method="post">
				<input  id="searchName" name="productName" readonly="readonly">
			</form>
		</div>
		
		<div class="vaguealert">
			<p></p>
		 </div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_usercenter.js"></script>
</html>

