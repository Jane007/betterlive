<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
		<title>挥货-圈子</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201801221630"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/discovery/wx_dynamic.css?t=2018020916"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
			var mainServer = "${mainserver}";
			var resourcepath = "${resourcepath}";
			var customerId = "${customerId}";
			var title = "挥货 - 圈子";  
			var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
			var link = '${mainserver}/weixin/discovery/toDynamic';
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
	<body>
		<div class="initloading"></div>
		<div id="layout">
			<div class="header">
				<div class="nav-wrap">
					<div class="goback" style="display: none;">
						<img src = "${resourcepath}/weixin/img/geren01.png" />
					</div>
					<ul class="faxian_nav">
						<li onclick="location.href='${mainserver}/weixin/discovery/toDynamic'" class="current"><a href="javascript:void(0);">圈子</a></li>
						<li onclick="location.href='${mainserver}/weixin/discovery/toSelected'"><a href="javascript:void(0);">精选</a></li>
						<li onclick="location.href='${mainserver}/weixin/discovery/toVideo'"><a href="javascript:void(0);">视频</a></li>
					</ul>
					<div class="head-right" onclick="toPublic()"></div>
				</div>
			</div>
			<!--主内容区-->
			<div class="content-wrap">
				<div class="content">
					<div class="banner">
						<ul class="banlist">
							<li>
								<a href="javascript:toPublic();">
									<dl>
										<dt>
											<img src="${resourcepath}/weixin/img/discovery/banlist-01.png"/>
											<div class="hold"></div>
										</dt>
										<dd>
											<span>我要发帖</span>
										</dd>
									</dl>
								</a>
							</li>
							<li>
								<a href="${mainserver}/weixin/discovery/toFindFriends">
									<dl>
										<dt>
											<img src="${resourcepath}/weixin/img/discovery/banlist-02.png"/>
										</dt>
										<dd>
											<span>发现吃货</span>
										</dd>
									</dl>
								</a>
							</li>
							<li>
								<a href="javascript:toMyIndex();">
									<dl>
										<dt>
											<img src="${resourcepath}/weixin/img/discovery/banlist-03.png"/>
										</dt>
										<dd>
											<span>我的主页</span>
										</dd>
									</dl>
								</a>
							</li>
						</ul>
					</div>
					<!--知名美食达人-->
					<div class="intelligent">
							<div class="title">
								<h3>知名美食达人</h3>
								<!-- <div class="close"></div> -->
							</div>
							<div class="intelist-wrap">
								<ul class="intelist">
								</ul>
							</div>
						</div>
					<!--瀑布流-->
					<aside class="fall-box grid">
					</aside>
				</div>
			</div>
			<div class="loadingmore" style="bottom:50px;">   
				<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
			</div>
<%-- 			<div class="zanwubg">暂无内容，小编正在上传中</div>   --%>
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
						<span class="gwnb" <c:if test="${cartCnt == null || cartCnt<=0}">style="display:none;"</c:if>>
							<c:if test="${cartCnt>0 && cartCnt<= 99}">${cartCnt }</c:if>
							<c:if test="${cartCnt>99}">99+</c:if>
						</span>
					</li>
					<li class="mine"><a href="${mainserver}/weixin/toMyIndex"><em></em><i>我的</i></a></li>
				</ul> 
			</div>  
		</div>
		
		<script src="${resourcepath}/weixin/js/imagesloaded.pkgd.min.js"></script>
		<script src="${resourcepath}/weixin/js/masonry.pkgd.min.js"></script>
		<script src="${resourcepath}/weixin/js/common.js"></script> 
		<script src="${resourcepath}/weixin/js/discovery/wx_dynamic.js?t=201801221630"></script> 
	
		<script type="text/javascript">
			var backUrl = window.location.href;
			$(function () {
				queryHotList();
			 	
				var url = window.location.href;
				if(url.indexOf("#")!=-1){
					url = url.substring(0,url.indexOf("#"));
				};
			 	//判断是否需要隐藏底部导航
			 	var flag = url.split('=')[1];
			 	if(flag && flag == "ok"){
			 		localStorage.setItem('discoveryBackFlag',flag);
			 		$(".loadingmore").attr("style", "bottom:0rem;");
			 		$(".goback").show();
			 		$('.footer').hide();
			 	}else if(localStorage.getItem('discoveryBackFlag')){
			 		$(".goback").show();
			 		$('.footer').hide();
			 		$(".loadingmore").attr("style", "bottom:0rem;");
			 	}else{
			 		$('.footer').show();
			 	};
			 	//点击返回按钮清除缓存
			 	$('.goback').click(function(){
			 		localStorage.removeItem('discoveryBackFlag');
			 		window.location.href=mainServer+"/weixin/index";
			 	});
			 	
			 	// 新手指引状态
				anniversary.getStorage();
				
				// 点击遮罩新增数据
				$('.guide-wrap').click(function() {
					saveGuide();
				});
			 });
		</script>
		<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
		
	</body>
</html>
