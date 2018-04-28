<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
		<link rel="stylesheet" href="${resourcepath}/weixin/css/result.css?t=2018004131758" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css?t=201801260603" />
	 	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/home/wx_index.css?t=2018004131758"/>
	 	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/dropload.css?t=201801260603"/> 
	 	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script> 
    	<script src="${resourcepath}/weixin/js/flexible.js"></script>
    	<script src="${resourcepath}/weixin/js/dropload.min.js"></script>
    	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<title>挥货-你的美食分享平台</title>
    	<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var resourcepath = '${resourcepath}';
    		var title = "挥货 - 你的美食分享平台";  
    		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
    		var link = mainServer+'/weixin/index';
    		var imgUrl = resourcepath+"/weixin/img/huihuologo.png";
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
			<%--下载App--%>
			<div class="dowbox-wrap" style="display: none;">
				<div class="dowbox">
					<div class="shut">
						<img src="${resourcepath}/weixin/img/xx.png"/>
					</div>
					<div class="activitytext">
						<h3></h3>
						<p></p>
					</div>
					<div class="dowbtn"><a href="javascript:void(0);"></a></div>
				</div>
			</div>
			<div class="header-wrap">
				<div class="header">
					<div class="seach">
						<img src="${resourcepath}/weixin/img/home/seach.png"/>
						<input type="text" name id="searchBox" placeholder="搜索商品" readonly="readonly" onclick="location.href='${mainserver}/weixin/search?stype=1'"/>
					</div>
					<div class="msg" onclick="location.href='${mainserver}/weixin/message/showUnread'">
						<span>
							${unreadCount}
			  			</span>
					</div>
				</div>
			</div>
			<!--轮播-->
			<div class="content" id="contentId" style="margin-top:1.166rem;">
			<div class="banner" >
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<c:forEach items="${banners}" var="banner">
							<div class="swiper-slide">
								<c:if test="${banner.bannerUrl != null && banner.bannerUrl != ''}">
									<a href="${banner.bannerUrl}"><img src="${banner.bannerImg}" alt="" /></a>
								</c:if>
								<c:if test="${banner.bannerUrl == null || banner.bannerUrl == ''}">
									<a href="javascript:void(0);"><img src="${banner.bannerImg}" alt="" /></a>
								</c:if>
							</div>
						</c:forEach>
					</div>
					<div class="swiper-pagination"></div>
				</div>
				<div class="bgyy"></div>
			</div>
			<!--菜单导航-->
			<div class="menu-wrap">
				<div class="menu">
					<ul>
						<li>
							<a href="${mainserver}/weixin/toLimitGoods">
 								<img src="http://images.hlife.shop/zhounianqing1201804041319.png"/> 
<%--  								<img src="${resourcepath}/weixin/img/home/menu-001.png"/> --%>
<%--								<img alt="" src="http://images.hlife.shop/2018x1.png"> --%>
								<span>限时秒杀</span>
							</a>
						</li>
						<li>
							<a href="${mainserver}/weixin/toGroupGoods">
 								<img src="http://images.hlife.shop/zhounianqing2201804041319.png"/> 
<%--  								<img src="${resourcepath}/weixin/img/home/menu-002.png"/> --%>
<%--								<img alt="" src="http://images.hlife.shop/2018x2.png"> --%>
								<span>周末拼团</span>
							</a>
						</li>
						<li>
							<a href="${mainserver}/weixin/discovery/toDynamic?discoveryBackFlag=ok">
 								<img src="http://images.hlife.shop/zhounianqing3201804041319.png"/> 
<%--  								<img src="${resourcepath}/weixin/img/home/menu-003.png"/>  --%>
<%--								<img alt="" src="http://images.hlife.shop/2018x3.png"> --%>
								<span>吃货圈</span>
							</a>
						</li>
						<li>
							<a href="${mainserver}/weixin/customerinvite/gotoInvited?backUrl=${mainserver}/weixin/index">
 								<img src="http://images.hlife.shop/zhounianqing4201804041319.png"/> 
<%--  								<img src="${resourcepath}/weixin/img/home/menu-004.png"/>  --%>
<%--								<img alt="" src="http://images.hlife.shop/2018x4.png"> --%>
								<span>邀请好友</span>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<!--新品发布-->
			<div class="newproduct-wrap">
				<div class="newproduct">
					<div class="title">
						每周新品发布
						<span class="on" onclick="location.href='${mainserver}/weixin/product/productList?extensionType=1'"></span>
					</div>
					<div class="pro-bottom">
						<div class="pro-left">
							<a href="javascript:void(0);">
								<img src="${resourcepath}/weixin/img/home/prodefult-03.png"/>
							</a>
							<div class="protext">
								<h3></h3>
								<h4></h4>
								<span class="price"></span>
							</div>
						</div>
						<div class="pro-right">
							<div class="proright-top">
								<a href="javascript:void(0);">
									<img src="${resourcepath}/weixin/img/home/prodefult-02.png"/>
								</a>
								<div class="protext">
									<h3></h3>
									<h4></h4>
									<span class="price"></span>
								</div>
							</div>
							<div class="proright-bottom">
								<a href="javascript:void(0);">
									<img src="${resourcepath}/weixin/img/home/prodefult-02.png"/>
								</a>
								<div class="protext">
									<h3></h3>
									<h4></h4>
									<span class="price"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--人气推荐-->
			<div class="recommend-wrap">
				<div class="recommend">
					<div class="title">
						人气推荐
						<span class="on" onclick="location.href='${mainserver}/weixin/product/productList?extensionType=2'"></span>
					</div>
					<ul class="prolist">
						
					</ul>
				</div>
			</div>
			<!--限量抢购-->
			<div class="rush-wrap" style="display: none;">
				<div class="rush">
					<div class="title">
						限量特惠抢购
						<span class="on" style="display: none;" onclick="location.href='${mainserver}/weixin/toLimitGoods'"></span>
					</div>
					<ul class="rushlist">
					</ul>
				</div>
			</div>
			<!--专题精选-->
			<div class="special-wrap">
				<div class="special">
					<div class="title">
						专题精选
						<span class="on" onclick="location.href='${mainserver}/weixin/discovery/toSelected?discoveryBackFlag=ok'"></span>
					</div>
					<div class="swiper-container1">    
						<div class="swiper-wrapper"> 
							
						</div>
					</div>
				</div>
			</div>
				<!--每日视频-->
				<div class="todayvideo-wrap" style="display: none;">
					<div class="todayvideo">
						<div class="title">
							每日视频
						</div>
						<div class="video">
							<video width="100%" height="100%" controls='controls'>
							
							</video>
							<div class="videoshad">
								<div class="paly on"></div>
							</div>
						</div>
						<div class="bottext">
							<h3></h3>
							<div id="vdoCollectId" class="dz">
								<span></span>
							</div>
						</div>
					</div>
				</div>
				<!--备案-->
				<div class="beian">
					<img src="${resourcepath}/weixin/img/home/beian.jpg"/>
					<span>粤ICP备17040250号</span>
					<img src="${resourcepath}/weixin/img/home/beian.jpg"/>
				</div>
			</div>
			
			<!--大礼包-->
			<div class="bggift-wrap">
				<div class="bggift">
					<div class="close">
						<img src="${resourcepath}/weixin/img/home/close.png"/>
					</div>
					<h2>优惠专享豪华大礼包</h2>
					<ul class="giftlist">
						
					</ul>
					<div class="bgbtn">
						一键领取
					</div>
				</div>
			</div>
			<!--红包-->
			<div class="hb"></div>
			<!--领券-->
			<div class="getcoupon-wrap">
				<div class="getcoupon">
					<ul class="couponlist">
					</ul>
				</div>
				<div class="over">
					<span>完成</span>
				</div>
				
			</div>
			<!--遮罩层-->
			<!--领取红包后的结构-->
			<div class="outhb">
				<h3>挥货给您送红包啦</h3>
				<h4>快去使用吧</h4>
				<ul class="hblist">
				</ul>
				<div class="hbclose"></div>
			</div>
			<div class="shad"></div>
			<!--专题活动弹窗-->
			<div class="anniversary-wrap">
				<div class="anniversary">
					<div class="oclose"></div>
				<!-- 	<div class="obtn">立即参与</div> -->
				</div>
			</div>
			<!-- 底部切换 -->
			<div class="footer">
				<ul>
					<li class="homePage active"><a href="${mainserver}/weixin/index"><em></em><i>首页</i></a></li>
					<li class="purchase"><a href="${mainserver}/weixin/product/toProductsByType"><em></em><i>分类</i></a></li> 
					<li class="special"><a href="${mainserver}/weixin/discovery/toSelected"><em></em><i>话题</i></a></li>
					<li class="shopping"><a href="${mainserver}/weixin/shoppingcart/toshoppingcar"><em></em><i>购物车</i></a>
						<span class="gwnb">
							${cartCnt }
						</span>
					</li> 
					<li class="mine"><a href="${mainserver}/weixin/toMyIndex"><em></em><i>我的</i></a></li>
				</ul>
			</div>
		</div>
		<div class="vaguealert">
		<p></p>
		</div>
		<script src="${resourcepath}/weixin/js/common.js?t=2018004131758"></script>
		<script src="${resourcepath}/weixin/js/wx_index.js?t=2018004131758"></script>
		<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>
		
	</body>
</html>
