<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/result.css?t=201801262155" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/usercenter/wx_usercenter.css?t=201803290932" />
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
    	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
    	<script src="${resourcepath}/weixin/js/flexible.js"></script>
   		<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
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

<input type="hidden" id="lotterySignStatus" value="${lotterySignStatus }" />
<input type="hidden" id="customerId" value="${customer.customer_id }"/>

<div class="bodywrap">
	<div id="layout">
		<div class="header-wrap">
			<div class="header">
				<div class="set" onclick="location.href='${mainserver}/weixin/usercenter/toUserSetting'">
					<img src="${resourcepath}/weixin/img/usercenter/set.png"/>
				</div>
				<div class="msg" onclick="location.href='${mainserver}/weixin/message/showUnread'">
					<div class = "msnum">
						<img src="${resourcepath}/weixin/img/usercenter/msg.png"/>
						<span>${unreadMessageCount }</span>
					</div>
				</div>
			</div>
		</div>
		<div class="content-wrap">
			<div class="content">
				<div class="conbg"></div>
				<!--用户信息-->
				<div class="user">
					<div class="usertop">
						<div class="headportrait" onclick="location.href='${mainserver}/weixin/socialityhome/toSocialityHome?backUrl=${mainserver}/weixin/toMyIndex'">
							<c:if test="${customer!=null && customer.head_url!=''}">
								<img src="${ customer.head_url }"/>
							</c:if>
							<c:if test="${customer==null}">
								<img src="${resourcepath}/weixin/img/usercenter/hportrait.png"/>
							</c:if>
						</div>
						<div class="login">
								<c:if test="${customer==null}">
									<span onclick="location.href='${mainserver}/weixin/tologin'">
										<a href="javascript:void(0);">登录/注册</a>
									</span>
								</c:if>
								<c:if test="${customer!=null}">
									<span onclick="location.href='${mainserver}/weixin/socialityhome/toSocialityHome?backUrl=${mainserver}/weixin/toMyIndex'">
									${customer.nickname }
									</span>
									<span class="integral">
										<label id="levelNameId">${customer.levelName}</label>
										<%--此处两个金币格式化标签之间不能回车换行，会导致出现空格  --%>
										(<label id="myTotalIntegralId"><fmt:formatNumber value="${customer.accumulativeIntegral}" pattern="####.##" type="number" /></label>/<label id="upgradeIntegralId">${requirementIntegral}</label>)
									</span>	
								</c:if>
						</div>
						<!--抽奖-->
						<c:if test="${lotterySignStatus == 0}">
						<div class="drawlottery <c:if test="${customer != null && checkLottery > 0}">off</c:if>" 
							<c:if test="${customer == null}"> onclick="location.href='${mainserver}/weixin/tologin'" </c:if>>
							<c:if test="${checkLottery == 0 || serialSign <= 1}">今日<c:if test="${checkLottery > 0}">已</c:if>抽奖</c:if>
							<c:if test="${checkLottery > 0 && serialSign > 1}">已连续抽奖${serialSign}天</c:if>
						</div>
						</c:if>
					</div>
					<div class="userbottom">
						<ul>
							<li onclick="location.href='${mainserver}/weixin/socialityhome/toSocialityHome?backUrl=${mainserver}/weixin/toMyIndex'">
								<div class="bg"></div>
								<span class="first">${circleCount}</span>
								<span>动态</span>
							</li>
							<li onclick="location.href='${mainserver}/weixin/socialityhome/toMyConcerns?backUrl=${mainserver}/weixin/toMyIndex'">
								<div class="bg"></div>
								<span class="first">${customerCount }</span>
								<span>关注</span>
							</li>
							<li onclick="location.href='${mainserver}/weixin/socialityhome/toMyFans?backUrl=${mainserver}/weixin/toMyIndex'">
								<div class="bg"></div>
								<span class="first">${concernedCount }</span>
								<span>粉丝</span>
							</li>
							<%-- <li onclick="location.href='${mainserver}/weixin/usercenter/toMyCollection?backUrl=${mainserver}/weixin/toMyIndex'">
								<span class="first">${collectionCount }</span>
								<span>收藏</span>
							</li> --%>
							<li onclick="location.href='${mainserver}/weixin/integral/toIntegral?backUrl=${mainserver}/weixin/toMyIndex'">
								<span class="first" id="myIntegralId">
									<c:if test="${customer == null || customer.currentIntegral == null}">
										0
									</c:if>
									<c:if test="${customer != null && customer.currentIntegral != null}">
										<fmt:formatNumber value="${customer.currentIntegral}" pattern="####.##" type="number" />
									</c:if>
								</span>
								<span>金币</span>
							</li>
						</ul>
					</div>
					
				</div>
				<!--订单-->
					<div class="order-wrap">
						<div class="order">
							<div class="ordertop-wrap">
								<div class="ordertop" onclick="location.href='${mainserver}/weixin/order/findList?status=0'">
									<h4>我的订单</h4>
									<span>查看全部订单</span>
								</div>
							</div>
							<div class="orderbottom">
								<ul>
									<li>
										<dl>
											<dt>
												<em>${waitPayOrderNum}</em><img src="${resourcepath}/weixin/img/usercenter/cent01.png"/></dt>
											<dd><span><a class="manageName myOrder" href="${mainserver}/weixin/order/findList?status=1">待付款</a>
												
												</span>
											</dd>
										</dl>
										
									</li>
									<li>
										<dl>
											<dt><em>${waitDeliveryOrderNum}</em>
													<img src="${resourcepath}/weixin/img/usercenter/cent04.png"/></dt>
											<dd><span><a class="manageName myOrder" href="${mainserver}/weixin/order/findList?status=2">待发货</a>
													
												</span>
											</dd>
											
										</dl>
									</li>
									<li>
										<dl>
											<dt><em>${waitReceiveOrderNum}</em><img src="${resourcepath}/weixin/img/usercenter/cent03.png"/></dt>
											<dd>
												<span>
													<a class="manageName myAtten" href="${mainserver}/weixin/order/findList?status=3">待收货</a>
													
												</span>
											</dd>
											
										</dl>
									</li>
									<li>
										<dl>
											<dt><em>${waitCommentOrderNum}</em><img src="${resourcepath}/weixin/img/usercenter/cent02.png"/></dt>	
											
											<dd>
												<span>
													<a class="manageName myStart" href="${mainserver}/weixin/order/findList?status=4">待评价</a>
													
												</span>
											</dd>
											
										</dl>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!--个人账户信息列表-->
					<div class="umsglist-wrap">
						<div class="umsglist">
							<div class="banner" style="display: none;">
								<a href="${mainserver}/weixin/customerinvite/gotoInvited"><img src="${resourcepath}/weixin/img/usercenter/userbanner.png"/></a>
							</div>
							<ul>
								<li onclick="location.href='${mainserver}/weixin/customercoupon/myCoupon'">
									<img src="${resourcepath}/weixin/img/usercenter/youhuiq_bg.png"/>
									<h4>优惠券<span>有<em style="color: red;">${couponCount}</em>张可用</span></h4>
									
								</li>
								<li onclick="location.href='${mainserver}/weixin/presentcard/toPresentcard'">
									<img src="${resourcepath}/weixin/img/usercenter/cent05.png"/>
									<h4>礼品卡<span><label style="color: red;">￥</label><em style="color: red;">${myGiftMoney}</em></span></h4>
								</li>
								<li onclick="location.href='${mainserver}/weixin/customercoupon/mySingleCoupon'">
									<img src="${resourcepath}/weixin/img/usercenter/cent06.png"/>
									<h4>红包<span>有<em style="color: red;">${uscCount }</em>个可用</span></h4>
								</li>
								<li onclick="location.href='${mainserver}/weixin/usercenter/toMyCollection?backUrl=${mainserver}/weixin/toMyIndex'">
									<img src="${resourcepath}/weixin/img/usercenter/cent07.png"/>
									<h4>我的收藏</h4>
									
								</li>
								<li onclick="location.href='${mainserver}/weixin/addressmanager/toReceiverAddress'">
									<img src="${resourcepath}/weixin/img/usercenter/cent08.png"/>
									<h4>地址管理</h4>
								</li>
								<li onclick="location.href='${mainserver}/weixin/usercenter/contactUs'">
									<img src="${resourcepath}/weixin/img/usercenter/cent09.png"/>
									<h4>帮助与客服</h4>
								</li>
							</ul>
						</div>
					</div>
			</div>
		</div>
	</div>
	<!--今日抽奖-->
	<div class="lottery-wrap">
			<div class="box">
				<div class="box-top">
					<h3>请随机抽一个哦</h3>
					<div class="close"></div>
				</div>
				<div class="box-content">
					<ul class="lotterylist">
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
						
<!-- 						<li class="ondraw"><span>金币2个</span></li> -->
<!-- 						<li class="draw"><span>金币2个</span></li> -->
<!-- 						<li><span></span></li> -->
<!-- 						<li class="participation"><span>谢谢参与</span></li> -->
<!-- 						<li><span></span></li> -->
<!-- 						<li><span></span></li> -->
					</ul>
				</div>
				<!--开奖-->
				<div class="onlottery-wrap">
					<div class="onlottery">
						<h4></h4>
					</div>
				</div>
			</div>
		</div>
		<!--新手指引-->
		<div class="guide-wrap">
			<div class="guide-top"></div>
			<div class="roger"></div>
		</div>
		<!--底部tab-->
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
					<li class="mine active"><a href="${mainserver}/weixin/toMyIndex"><em></em><i>我的</i></a></li>
				</ul>
		</div>
</div>
			
	<script src="${resourcepath}/weixin/js//common.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_user_index.js?t=201804071329"></script>

</body>
</html>