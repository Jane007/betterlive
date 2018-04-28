<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=2018004131758" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/shopCar.css?t=2018004131758" />
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css" />
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-购物车</title>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var mobile='${mobile}';
	   		var title = "挥货 - 你的美食分享平台";  
			var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
			var link = '${mainserver}/weixin/shoppingcart/toshoppingcar';
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
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="header">
				<p style="font-size:0.32rem;background:#e62d29;color:#fff;display:inline-block;line-height:0.81rem;width:100%;text-align:center; "> 
					购物车 
				</p>
				   
				<div class="editing" <c:if test="${cartCnt <= 0}">style="display: none;"</c:if>>编辑</div>
			</div>
			<div class="tishiby" style="display: none;"></div> 
			<div class="hasgoods">
				<div class="carBox" <c:if test="${cartCnt <= 0}">style="display: none;"</c:if>>
					<c:forEach items="${listShoppingCart}" var="l">
						<div class="carlist" id="${l.cart_id }">
							<div class="checklist">
								<span class="on"> 
									<input type="checkbox" value="${l.cart_id }" checked="checked"/>
								</span>
							</div>
							<div class="carpic">
								<c:choose>
									<c:when test="${l.status == 2}"><span class="xinpin">下架</span></c:when>
									<c:when test="${l.status == 3}"><span class="xinpin">失效</span></c:when>
									<c:when test="${l.label_name != null && l.label_name != ''}"><span class="xinpin">${l.label_name}</span>
									</c:when>
								</c:choose>
								<img src="${l.spec_img }" alt="" />
							</div>
							<div class="carcontent" onclick="findProduct(${l.product_id}, ${l.activity_type}, ${l.activity_id})">
								<p class="carcakename">${l.product_name}</p>
								<p class="carweight">${l.spec_name}</p>
								<div class="shopprice">
									<%-- 判断是否存在优惠价格，存在就显示优惠价 --%>
									<c:choose>
										<c:when test="${l.activity_price !=null && l.activity_price != '' && l.activity_price != '-1'}">
											￥<span id = "${l.product_id}">${l.activity_price}</span><strong>￥${l.spec_price}</strong>
										</c:when>
										<c:when test="${l.discount_price !=null && l.discount_price != '' && l.discount_price != '-1'}">
											￥<span id = "${l.product_id}">${l.discount_price}</span><strong>￥${l.spec_price}</strong>
										</c:when>
										<c:otherwise>
											￥<span id = "${l.product_id}">${l.spec_price}</span>
										</c:otherwise>
								</c:choose>
								</div>
								<div class="shopnumber">
									x<span>${l.amount}</span>
								</div>
							</div>
							<div class="chNorms">
								<div class="choose" id="${l.product_id }_${l.spec_id}" spec-id="${l.spec_id}" lang="${l.product_id}" extension-type="${l.extension_type}" data-copy="${l.stock_copy }"
									data-limit="${l.limit_max_copy }" data-hasBuy="${l.hasBuy_copy }" data-restCopy="${l.rest_copy}" data-package="${l.package_desc }">
									已选择：<span>${l.spec_name}</span>
								</div>
								<div class="computebox">
									<span class="ocompute" >
										<i class="cut">-</i>    
											<input type="button" value="${l.amount}" readonly="readonly" />
										<i class="add">+</i>
									</span>
								</div>
							</div>
							<div class="activity">
								<c:if test="${l.specialString != null && l.specialString != ''}">
									<div class="promotion">专题：<span>${l.specialString}</span></div>
								</c:if>
								<c:if test="${l.integralString != null && l.integralString != ''}">
								<div class="hold">金币优惠购：<span>${l.integralString}</span></div>
								</c:if>
								<c:if test="${l.salePromotionString != null && l.salePromotionString != ''}">
								<div class="promotion">活动促销：<span>${l.salePromotionString}</span></div>
								</c:if>
								<c:if test="${l.couponNumString != null && l.couponNumString != ''}">
									<div class="promotion">${l.couponNumString}</div>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</div>
				
			</div>
		 
			<div class="noyhbg" style="margin-bottom:1.2rem;<c:if test="${listShoppingCart != null && listShoppingCart.size() > 0}">display: none;</c:if>">  
				<p>您的购物车没有商品哦~</p>
				<a href="${mainserver}/weixin/index">去逛逛</a>
			</div> 
			 <div class="evaluateBox tuijianboxs">
				 <div class="pltitle">
				 	<span>为您推荐</span> 
				 </div>  
				 <div id="tuijianId" class="tuijianbox">
				 
				 </div>		
			</div>	
		</div>
			<div class="placeOrder" style='<c:if test="${empty listShoppingCart}">display:none;</c:if>">'>
				<div class="orderLeft">
					<div class="allCheck">
						<span class=""> <input type="checkbox" checked="checked" />
						</span> <em>已选择（<i>0</i>）
						</em>
						
					</div>
					
					<div class="totalSum">￥0.00</div>
					<div class="hejibox">合计：</div>
				</div>
				<div class="orderRight">去结算</div>
				<div class="delAll">删除所选</div>
	
				<input type="hidden" id="isFirst" name="isFirst" readonly="readonly" value="${isFirst }" />
				<input type="hidden" id="carId" name="carId" readonly="readonly" /> 
				<input type="hidden" id="productId" name="productId" readonly="readonly" />
				<input type="hidden" id="extensionType" name="extensionType" readonly="readonly" /> 
				<input type="hidden" id="productSpecId" name="productSpecId" readonly="readonly" />
				<input type="hidden" id="buyAmount" name="buyAmount" readonly="readonly" />
				<input type="hidden" id="editType" name="editType" readonly="readonly" />
				
				<input type="hidden" id="coalition" name="coalition" readonly="readonly" />
				<input type="hidden" id="coalition_pid" name="coalition_pid" readonly="readonly" />
			</div>
		</div>
		
		<div class="mask"></div>
		<div class="dia-mask"></div>
		
		<div class="vaguealert">
			<p>绑定成功</p>
		</div>
	
		<!-- 选择商品规格与价格 -->
		<div class="standardBox">
			<div class="standTop">
				<div class="proPic">
					<img src="" alt="" />
				</div>
				<div class="norms">
					<p class="normsPrice">
						<label for="">价格：</label> <span></span>&nbsp;<strong></strong>
					</p>
					<p class="inventory" id="stockCopy">
					
					</p>
					<p class="normsChoose">请选择规格</p>
					<p class="inventory" id="packageDesc">
				</p>
				</div>
			</div>
			<div class="installBox">
				<label for="">规格</label>
				<ul>
	
				</ul>
			</div>
			<div class="comNumber">
				<label for="">数量</label>
				<div class="calculate">
					<i class="calCut">-</i> 
						<input type="button" readonly="readonly" value="1" class="calGoodNums" />   
					<i class="calAdd">+</i>
				</div>
				
			</div>
			<div class="sureBox">确定</div>
			<div class="outBox">
				<img src="${resourcepath}/weixin/img/outbox.png" alt="" />
			</div>
		</div>
		<div class="whetherbox">
        	<p></p>
        	<div class="key">
        		<span class="no">取消</span>
        		<span class="yes">确定</span>
        	</div>
        </div>
		
    <div class="footer">
				<ul>
					<li class="homePage"><a href="${mainserver}/weixin/index"><em></em><i>首页</i></a></li>
					<li class="purchase"><a href="${mainserver}/weixin/product/toProductsByType"><em></em><i>分类</i></a></li> 
					<li class="special"><a href="${mainserver}/weixin/discovery/toSelected"><em></em><i>话题</i></a></li>
					<li class="shopping active"><a href="javascript:void(0);"><em></em><i>购物车</i></a></li> 
					<li class="mine"><a href="${mainserver}/weixin/toMyIndex"><em></em><i>我的</i></a></li>
				</ul>
			</div>  
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/shoppingcart/wx_shoppingcart.js?t=2018004131758"></script>
	
	
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
</html>