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
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201801261633"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/discovery/wx_selected.css?t=2018021643"/>
		
    	<script src="${resourcepath}/weixin/js/flexible.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<title>挥货-精选</title>
    	<script type="text/javascript">
			var mainServer = '${mainserver}';
			var resourcepath = "${resourcepath}";
			var customerId = "${customerId}";
			var title = "挥货 - 精选好文";  
	 		var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
	 		var link = '${mainserver}/weixin/discovery/toSelected';
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
			<div class="header">
				<div class="nav-wrap">
					<div class="goback" style="display: none;">
						<img src="${resourcepath}/weixin/img/geren01.png" />
					</div>
					<ul class="faxian_nav">
						<li><a href="${mainserver}/weixin/discovery/toDynamic">圈子</a></li>
						<li  class="current"><a href="${mainserver}/weixin/discovery/toSelected">精选</a></li>
						<li><a href="${mainserver}/weixin/discovery/toVideo">视频</a></li>
					</ul>
				</div>
			</div>
			<!--主内容-->
			<div class="content-wrap">
				<div class="content">
					<!--轮播-->
					<div class="banner">
						<div class="swiper-container">
							<div class="swiper-wrapper" id="topSelectId">
						    </div>
						</div>
					</div>
					<!--文章列表-->
					<div class="article-wrap">
						<div class="article">
							<ul class="aticlelist">
							</ul>
						</div>
					</div>	
					<!--每日推荐-->
					<div id="dayRecId" class="recommendation-wrap" style="display: none;">
						<div class="recommendation">
							<div class="title">
								<h3>每日推荐</h3>
							</div>
							<div class="bot">
								<div class="botcontent">
									<div class="botbottom">
										<div class="picture">
											<a href="javascript:void(0);">
												<img src="${resourcepath}/weixin/img/discovery/selection-defult.png"/>
											</a>
											<h4></h4>
											<div class="func">
												<div class="comment">
													<span>0</span>
												</div>
												<div class="dz">
													<span>0</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 每周精选 -->
					<div class="recommendation-wrap week">
						<div id="recList" class="recommendation">
							<div class="title">
								<h3>每周精选</h3>
								<div class="previous" onclick="toRetrospect()"></div>
							</div>
						</div>
					</div>
					<!--查看往期-->
					<div class="wrap-previous" onclick="toRetrospect()">
						<div class="previousnode">
							<span>查看往期</span>
						</div>
					</div>
				</div>
			</div>
		</div>
<%-- 		<div class="zanwubg" style="display: none;">暂无内容，小编正在上传中</div>   --%>
		<div class="vaguealert">
			<p></p>
		</div>
		<!--新手指引-->
		<div class="guide-wrap">
			<div class="guide-top"></div>
			<div class="roger"></div>
		</div>
		<div class="footer">
				<ul>
					<li class="homePage"><a href="${mainserver}/weixin/index"><em></em><i>首页</i></a></li>  
					<li class="purchase"><a href="${mainserver}/weixin/product/toProductsByType"><em></em><i>分类</i></a></li> 
					<li class="special active"><a href="${mainserver}/weixin/discovery/toDynamic"><em></em><i>话题</i></a></li>
					<li class="shopping"><a href="${mainserver}/weixin/shoppingcart/toshoppingcar"><em></em><i>购物车</i></a>
						<span class="gwnb">
							${cartCnt }
						</span>
					</li> 
					<li class="mine"><a href="${mainserver}/weixin/toMyIndex"><em></em><i>我的</i></a></li>
				</ul> 
			</div>  
	</body> 
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js?t=201801261633"></script> 
	<script src="${resourcepath}/weixin/js/discovery/wx_selected.js?t=201801291833"></script> 
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>

</html>