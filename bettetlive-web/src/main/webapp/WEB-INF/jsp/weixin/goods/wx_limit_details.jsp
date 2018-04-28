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
	<meta name="keywords" content="挥货,每周新品,挥货每周上新" /> 
	<meta name="description" content="挥货商城每周新品首发为您推荐优质农产品，提升生活品质。" /> 
	<title>
		${productInfo.product_name}${names}-挥货
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=2018004131758" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/goodsdetails.css?t=201804240931" />
   	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/common.js?t=2018004131758"></script>
	<style type="text/css">
		.exampleBox {
			font-size: 0.25rem;
		}
		.exampleBox img {
			width: auto;
			max-width: 100%;
		}
		
	</style>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		var backUrl = "${backUrl}";
        var customerId = "${customerId}";
		var mobile='${mobile}';
		var title = "挥货 - ${productInfo.product_name}";
		var shareExplain = '${productInfo.share_explain}';
		var imgUrl = "${productInfo.product_logo}";
		var product_id = '${productInfo.product_id}';
		var specialId = '${specialVo.specialId}';
		var orderSource = '${orderSource}';
		var specStatus = "${specialVo.status}";
		var specStart = "${specialVo.longStart}";
		var specEnd = "${specialVo.longEnd}";
		var currentTime = "${currentTime}";
		
		var desc = "吧唧零嘴、懒懒速食、锅里乱炖、清爽水果都在挥货星球~";
		var shareExplain = '${productVo.share_explain}';
		if(shareExplain!=null && shareExplain!=""){
			desc = shareExplain;
		}
		
		var link = mainServer+'/weixin/product/toLimitGoodsdetails?productId=${productInfo.product_id}&specialId=${specialVo.specialId}&source='+orderSource;
		var back = backUrl;
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
	<%--下载App--%>
	<div class="dowbox-wrap">
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
	<div class="container">
		<div class="mainBox" style="top:0rem;">
			<div class="swiper-container">
				<div class="swiper-wrapper">
					<c:forEach items="${productInfo.pictures }" var="banner">
						<div class="swiper-slide">
							<a href="javascript:;"><img src="${banner.original_img }" alt=""/></a>
						</div>
					</c:forEach>
				</div>
				<div class="swiper-pagination"></div>
			</div>
			<div class="goodsContent">
				<div class="qianggoutsbg">
					<span>
						<c:choose>
							<c:when test="${specialVo.status != 1 || specialVo.longEnd <= currentTime}">已结束</c:when>
							<c:when test="${specialVo.longStart > currentTime}">预售期</c:when>
							<c:when test="${productInfo.total_stock_copy <= 0}">已抢完</c:when>
							<c:otherwise>抢购中</c:otherwise>
						</c:choose>
					</span>
					<p>仅剩${hasStock}件</p>
				</div>
				<div class="goodsInfor protitlebox">
					 <div class="protitle">
					 	<h3>${productInfo.product_name}</h3>
					 	<p>
					 		<c:if test="${productInfo.detailExplain != null && productInfo.detailExplain != ''}">
					 			${productInfo.detailExplain}
					 		</c:if>
					 	</p>
					 	<c:if test="${productInfo.labelName != null && productInfo.labelName != ''}">
					 		<em>${productInfo.labelName}</em>
					 	</c:if>
					 </div>
					 <div class="projiage">
					 	<p>
				 			<c:choose>
								<c:when test="${productInfo.listSpecVo[0] != null && productInfo.listSpecVo[0].activity_price != null 
												&& productInfo.listSpecVo[0].activity_price != '' && productInfo.listSpecVo[0].activity_price != '-1'}">
									￥${productInfo.listSpecVo[0].activity_price}<strong>￥${productInfo.listSpecVo[0].spec_price}</strong>
								</c:when>
								<c:when test="${productInfo.listSpecVo[0] != null && productInfo.listSpecVo[0].discount_price != null 
												&& productInfo.listSpecVo[0].discount_price >= 0}">
									￥${productInfo.listSpecVo[0].discount_price}<strong>￥${productInfo.listSpecVo[0].spec_price}</strong>
								</c:when>
								<c:otherwise>
									￥${productInfo.listSpecVo[0].spec_price}
								</c:otherwise>
							</c:choose>
					 	</p>   
					 	<span>
					 		销量${unrealSales}份
					 	</span>
					 </div>
					
				</div>
				<c:choose>  
							<c:when test="${postageId != null && postageId != ''}">
								  <div class="probiaoqian">
								   			<span>${postageMsg}</span>
								   			<c:if test="${productInfo.is_quality == 1}">
											  <span>权威质检</span>
										    </c:if> 
										    <c:if test="${productInfo.is_testing == 1}">
											  <span>专业测评</span> 
											  </c:if>
								  </div>  
							</c:when>  
							<c:otherwise>
									   <c:if test="${productInfo.is_freight == 1 || productInfo.is_quality == 1 || productInfo.is_testing == 1}">
										 <div class="probiaoqian"> 
										 		<c:if test="${productInfo.is_freight == 1}">
												  <span>免运费</span>
											    </c:if>
											    <c:if test="${productInfo.is_quality == 1}">
												  <span>权威质检</span>
											    </c:if> 
											    <c:if test="${productInfo.is_testing == 1}">
												  <span>专业测评</span> 
												</c:if>
										 </div>
									 </c:if>
							</c:otherwise>
				</c:choose>
				<div class="parameterBox newprocsbox">
					<ul class="guige">
						<li class="etalon">
							<label for="">规格数量<span style="margin-left:15px;"></span> <i></i></label>
							<em></em>
						</li> 
	 					<li id="cpcsLineId">
	 						产品参数
						</li>
 						<li id="hongbao1"  onclick="showRedCoupon()"> 
 							 领券 :
 							<i></i> 
 						</li>
						<li id="promotion">
	 						<div class="lileft">活动促销</div>
	 						<div class="liright">
	 							<div class="promotion">
	 							</div>
	 							<div class="hold">
	 							</div>
	 						</div>
						</li>
			<%-- 		<c:if test="${specialVo != null && specialVo.specialId > 0}">
							<li <c:if test="${specialVo.specialType == 1}"> onclick="location.href='${specialVo.specialPage}'"</c:if>>
								相关专题：<span class="dispan">${specialVo.specialName}</span>
							</li>
						</c:if>
			--%>		
					</ul>
					<div class="tishiwx">
						<c:if test="${productInfo.prompt != null && productInfo.prompt != ''}">
							温馨提示：${productInfo.prompt}
						</c:if>
					</div>
				</div>
				
				<c:if test="${comments.size() > 0}">
					<div class="evaluateBox plbox">
						 <div class="pltitle" onclick="location.href='${mainserver}/weixin/productcomment/findList?type=2&productId=${productInfo.product_id}&specialId=${specialVo.specialId}'">
						 	<span>用户评论<em>(${comments.size()})</em></span> 
						 	<p>查看全部评论</p>
						 </div>
						 <c:forEach items="${comments}" var="commentVo" begin="0" end="1">
						 <div class="plcenter">
						 	<div class="plyi">
						 		<em onclick='toOtherHome("${commentVo.customerVo.customer_id}");'><img src="${commentVo.customerVo.head_url}" alt="" /></em>
						 		<span onclick='toOtherHome("${commentVo.customerVo.customer_id}");'>${commentVo.customerVo.nickname}</span>
						 		<p>${commentVo.create_time}</p>
						 	</div>
						 	<div  class="pler">
						 		${commentVo.content}
						 	</div> 
						 	<div  class="plssan">
						 		${commentVo.specName}
						 	</div>
						 </div>
						 </c:forEach>
					</div>
				</c:if>
				<div class="evaluateBox plbox cpcentbox">
					 <div class="pltitle">
					 	<span>详情介绍</span> 
					 </div>
				</div>
				<div class="cpcentimg">
					${productInfo.introduce}
				</div>
				
				<div class="evaluateBox tuijianboxs">
					 <div class="pltitle">
					 	<span>为您推荐</span> 
					 </div> 
					 <div class="tuijianbox">
					 </div>		
				</div>				
				 
			</div>
			
		</div>
		<form id="buyForm" action="${mainserver}/weixin/order/addBuyOrder" method="post">				
			<c:if test="${isAgent == 0}">	
			<div class="botShop tuanbot">
				<span id="cartsId">
					<c:if test="${shoppingCarts > 0 && shoppingCarts <= 99}">
					<em>${shoppingCarts}</em>
					</c:if>
					<c:if test="${shoppingCarts > 99}">
					<em>99+</em>
					</c:if>
				</span>
				<c:if test="${collectionId > 0}">
					<span class="shou shoucan" onclick="addOrCancelCollect(1, ${collectionId})"></span>
				</c:if>
				<c:if test="${collectionId == 0}">
					<span class="shou" onclick="addOrCancelCollect(0, 0)"></span>
				</c:if>
				
				<a href="javascript:void(0)" class="addShopCar" onclick="toAddShotCar(2)">加入购物车</a>
				<c:choose>
					<c:when test="${specialVo.status == 1 && specialVo.longStart <= currentTime && specialVo.longEnd > currentTime}">
						<a href="javascript:void(0)" class="newBuy" onclick="toAddShotCar(1)">立即购买</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" class="newBuy huibg" onclick="toAddShotCar(1)">立即购买</a>
					</c:otherwise>
				</c:choose>
				<input type="hidden"  id="productId"  name="productId"  value="${productInfo.product_id}"  readonly="readonly"/>
				<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount"  readonly="readonly"/>
				<input type="hidden"  id="returnType"  name="returnType" value="5" readonly="readonly"/>
				<input type="hidden"  id="tzhuantiId"  name="tzhuantiId" value="${specialVo.specialId}" readonly="readonly"/>
				<input type="hidden" id="orderSource" name="orderSource" value="${orderSource}">
			</div>
			</c:if>
			<c:if test="${isAgent == 1}">
			<div class="onshop">
				<c:if test="${collectionId > 0}">
					<span class="shou shoucan" onclick="addOrCancelCollect(1, ${collectionId})"></span>
				</c:if>
				<c:if test="${collectionId == 0}">
					<span class="shou" onclick="addOrCancelCollect(0, 0)"></span>
				</c:if>
				<c:choose>
					<c:when test="${specialVo.status == 1 && specialVo.longStart <= currentTime && specialVo.longEnd > currentTime}">
						<a href="javascript:void(0)" class="newBuy" onclick="toAddShotCar(1)">立即购买</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" class="newBuy huibg" onclick="toAddShotCar(1)">立即购买</a>
					</c:otherwise>
				</c:choose>
				<input type="hidden"  id="productId"  name="productId"  value="${productInfo.product_id}"  readonly="readonly"/>
				<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount"  readonly="readonly"/>
				<input type="hidden"  id="returnType"  name="returnType" value="5" readonly="readonly"/>
				<input type="hidden"  id="tzhuantiId"  name="tzhuantiId" value="${specialVo.specialId}" readonly="readonly"/>
				<input type="hidden" id="orderSource" name="orderSource" value="${orderSource}">
			</div>
			</c:if>
		</form>
	</div>
		
	<div class="gohome">
		<a href="${mainserver}/weixin/index">
			<img src="${resourcepath}/weixin/img/gohome.png" alt="">
		</a>
	</div>
	<div class="backTop"></div>
	
	<div class="mask"></div>
	<div class="dia-mask"></div>
	
	<div class="vaguealert">
		<p></p>
	</div>
		
	<div id="specDivLine" class="standardBox">
		<div class="standTop">
			<div class="proPic">
				 <img src="${productInfo.listSpecVo[0].spec_img}" alt="" />	
			</div>
			
			<div class="norms">
				<p class="normsPrice">
					<label for="">价格：</label>
					<span></span>
					<strong></strong>
				</p>
				<p class="inventory" id="stockCopy">
					
				</p>
				<p class="normsChoose">请选择规格</p>
				<p class="inventory" id="packageDesc">
				</p>
			</div>
		</div>
		<div id="currentActivity" style="display:none">
			<span>当前活动:</span>
		</div>
		<div class="" id="activity">
			
		</div>
		<div class="installBox">
			<label for="">规格</label>
			<ul>
				<c:forEach items="${productInfo.listSpecVo}"  var="s">
					<c:choose>
						<c:when test="${s.activity_price !=null && s.activity_price != '' && s.activity_price != '-1'}">
							<li onclick='getPromotion(${s.spec_id});' value="¥${s.activity_price}" data-orgPrice="¥${s.spec_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
							data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}"
							data-carNums="${s.carNums }" data-carCanAdd="${s.carCanAdd }" data-package="${s.package_desc }"
							data-promoitionName="${s.promoitionName }" data-fullMoney="${s.fullMoney }" data-cutMoney="${s.cutMoney }" data-single="${s.singleCouponCount}" data-coupon="${s.couponCount}"
							>${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
								
							</li>
						</c:when>
						<c:otherwise>
							<li onclick='getPromotion(${s.spec_id});' value="¥${s.spec_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
							data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}"
							data-carNums="${s.carNums }" data-carCanAdd="${s.carCanAdd }" data-package="${s.package_desc }"
							data-promoitionName="${s.promoitionName }" data-fullMoney="${s.fullMoney }" data-cutMoney="${s.cutMoney }" data-single="${s.singleCouponCount}" data-coupon="${s.couponCount}"
							>${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
								<!-- <input type="hidden" id="copy${s.spec_id}" value="${s.stock_copy }"/> -->
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="comNumber">
			<label for="">数量</label>
			<div class="standBot">
				<div class="calculate" style="float: left;">
					<i class="calCut">-</i>
					<input type="text" value="1" class="calGoodNums" onblur="checkGoodNum(this.value)"/>
					<i class="calAdd">+</i>
				</div>
 				<div class="actTotal"> 
 					<span></span><em></em> 
 				</div> 
			</div>
		</div>
		<div class="outBox">
			<img src="${resourcepath}/weixin/img/outbox.png" alt="" />
		</div>
		
	</div>
	
	<div id="cpccId" class="standardBox cpcanshu">
		<h3>产品参数</h3>
		<c:forEach items="${productInfo.paramAndValue}"  var="pv"  >
				<c:set var="params" value="${fn:split(pv,'&#&$')[0] }"></c:set>
	    		<c:set var="values" value="${fn:split(pv,'&#&$')[1] }"></c:set>
				
				<span><strong>${params}：</strong><p>${values}</p></span>
		</c:forEach>
		<div class="outBox" style="margin-top:14px;">
			<img src="${resourcepath}/weixin/img/outbox.png" alt="" />
		</div>   
	</div>
	 <div class="couponPop">
	    	<div class="couponCon">
	    		<h2>优惠券/红包</h2>
	    			<ul class="couponList">
	    			
	    			</ul>
	    			
	    	</div>
	    </div>	
	
	<script src="${resourcepath}/weixin/js/goods/wx_limit_details.js?t=2018004131758"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
	
</body>
</html>