<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>商品分类</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201801261943"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/goods/wx_productsByTypes.css?t=201801261944"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var whoClick = "${whoClick}";
    		var resourcepath = '${resourcepath}';
    		//微信分享页面所要的参数
    		var title = "挥货 - 你的美食分享平台";  
    		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
    		var link = mainServer+'/weixin/product/toProductsByType?whoClick='+whoClick;
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
			<div class="header-wrap">
				<div class="header">
					<div class="seach">
						<img class="fdj" src="${resourcepath}/weixin/img/home/seach.png"/>
						<input type="text" id="txt" class="searchBox" readonly="readonly" placeholder="搜索商品"
							   onclick="location.href='${mainserver}/weixin/search?stype=2'"/>
					</div>
					<div class="msg" onclick="location.href='${mainserver}/weixin/message/showUnread'">
						<span>
							${unreadCount}
						</span>
					</div>
				</div>
				<div class="tab">
					<ul>
						<li>
							<dl class="isclick">
								<dt>
									<img src="${resourcepath}/weixin/img/goods/all-01.png?t=201801270209"/>
								</dt>
								<dd>
									<span>全部</span>
								</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>
									<img src="${resourcepath}/weixin/img/goods/fruits-01.png?t=201801270209"/>
								</dt>
								<dd>
									<span>星球推荐</span>
								</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>
									<img src="${resourcepath}/weixin/img/goods/snacks-01.png?t=201801270209"/>
								</dt>
								<dd>
									<span>网红零食</span>
								</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>
									<img src="${resourcepath}/weixin/img/goods/recommend-01.png?t=201801270209"/>
								</dt>
								<dd>
									<span>必备零食</span>
								</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>
									<img src="${resourcepath}/weixin/img/goods/kitchen-01.png?t=201801270209"/>
								</dt>
								<dd>
									<span>其它</span>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
			</div>
		<div class="content-wrap" id="showContent0" style="display:none;" >
			<div class="pad"></div>
				<div class="content">
				
					<!--商品列表-->
					<div class="prolist">
						<ul id="showProduct0">
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="content-wrap" id="showContent1" style="display:none;">
			<div class="pad"></div>
			<div class="content">
				
				<!--商品列表-->
				<div class="prolist">
					<ul id="showProduct1">
					</ul>
				</div>
			</div>
		</div>
		<div class="content-wrap" id="showContent2" style="display:none;">
			<div class="pad"></div>
			<div class="content">
			
				<!--商品列表-->
				<div class="prolist">
					<ul id="showProduct2">
					</ul>
				</div>
			</div>
		</div>
		<div class="content-wrap" id="showContent3" style="display:none;" >
			<div class="pad"></div>
			<div class="content">
				
				<!--商品列表-->
				<div class="prolist">
					<ul id="showProduct3">
					</ul>
				</div>
			</div>
		</div>
		<div class="content-wrap" id="showContent4" style="display:none;" >
			<div class="pad"></div>
			<div class="content">
			
				<!--商品列表-->
				<div class="prolist">
					<ul id="showProduct4">
					</ul>
				</div>
			</div>
		</div>
		
		
		<div class="vaguealert">
			<p></p>
		</div>
		
		<div class="loadingmore" style="bottom: 50px;">   
			<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
		</div>
		<div class="footer">
				<ul>
					<li class="homePage"><a href="${mainserver}/weixin/index"><em></em><i>首页</i></a></li>
					<li class="purchase active"><a href="${mainserver}/weixin/product/toProductsByType"><em></em><i>分类</i></a></li> 
					<li class="special"><a href="${mainserver}/weixin/discovery/toSelected"><em></em><i>话题</i></a></li>
					<li class="shopping"><a href="${mainserver}/weixin/shoppingcart/toshoppingcar"><em></em><i>购物车</i></a>
						<span class="gwnb" <c:if test="${cartCnt == null || cartCnt<=0}">style="display:none;"</c:if>>
							${cartCnt}
						</span>
					</li> 
					<li class="mine"><a href="${mainserver}/weixin/toMyIndex"><em></em><i>我的</i></a></li>
				</ul>
			</div>
	</body>
	<input type="hidden" name="pageCount" id="pageCount" value="">
	 <input type="hidden" name="pageNow" id="pageNow" value="">
	 <input type="hidden" name="pageNext" id="pageNext" value="">
	<script src="${resourcepath}/weixin/js/common.js?t=201801270209"></script> 
	<script src="${resourcepath}/weixin/js/goods/wx_productByTypes.js?t=201801270210"></script> 
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>
	
</html>

