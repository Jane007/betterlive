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
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801261745" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/goodsdetails.css?t=201804240931" />
   	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
				<div class="goodsInfor protitlebox">
					 <div class="protitle">
					 	<h3>${specialVo.specialName}</h3>
					 	<p> 
					 		<c:if test="${specialVo.specialIntroduce != null && specialVo.specialIntroduce != ''}">
					 			${specialVo.specialIntroduce}
					 		</c:if>
					 	</p>  
					 	<em class="tuan">
					 		<c:if test="${productInfo.labelName != null && productInfo.labelName != ''}">${productInfo.labelName}</c:if>
				 			<c:if test="${productInfo.labelName == null || productInfo.labelName == ''}">${sysGroup.limitCopy}人团</c:if>
			 			</em>     
					 </div> 
					 <div class="projiage"> 
					 	<p>  
							￥${productInfo.listSpecVo[0].activity_price}<strong>￥${productInfo.listSpecVo[0].spec_price}</strong>
					 	</p>
					 	<span>
					 		已团${unrealSales}份 
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
							<label for="">规格数量<span style="margin-left:15px;">${productInfo.listSpecVo[0].spec_name}</span> <i>x1</i></label>
							<em></em>
						</li> 
	 					<li id="cpcsLineId">
	 						产品参数
						</li>
						<%--
						<li>
							相关专题：<span>${specialVo.specialName}</span>
						</li>
						--%>
					</ul>
					<div class="tishiwx">
						<c:if test="${productInfo.prompt != null && productInfo.prompt != ''}">
							温馨提示：${productInfo.prompt}
						</c:if>
					</div>
				</div>
				<c:if test="${userGroups.size() > 0 && specialStatus !=1}">
					<div class="evaluateBox plbox">
						 <div class="pltitle">
						 	<span>Ta在开团</span> 
						 	<c:if test="${userGroups.size() > 2}">
					 		<p onclick="location.href='${mainserver}/weixin/productgroup/toJoinGroups?specialId=${specialVo.specialId}&productId=${productInfo.product_id}'">查看更多开团</p>
						 	</c:if>
						 </div>
						 <c:forEach items="${userGroups}" var="userGroupVo" begin="0" end="1">
						  <div class="cantuanbox">
						  	  <span class="one" onclick='toOtherHome("${userGroupVo.originator}");'>
						  	  	<img src="${userGroupVo.custImg}" alt="" />
						  	  </span>
						  	  <span class="two">
						  	  	${userGroupVo.nickName}
<%-- 						  	  	 <p>还差1人，剩余1天23:38:09</p> --%>
								<p>
									<c:if test="${sysGroup.limitCopy - userGroupVo.custNum <= 0}">
										已满${sysGroup.limitCopy}人，快邀请好友再次开团吧
									</c:if>
									<c:if test="${sysGroup.limitCopy - userGroupVo.custNum == 1}">
										就等你1个了，一起享受拼价吧
									</c:if>
									<c:if test="${sysGroup.limitCopy - userGroupVo.custNum > 1}">
										剩余${sysGroup.limitCopy - userGroupVo.custNum}个名额，一起享受拼价吧
									</c:if>
								</p>
						  	  </span>
						  	  
						  	  <a <c:if test="${userGroupVo.status != 1}">class="disable"</c:if> href="javascript:toJoinGroup(${userGroupVo.userGroupId}, ${productInfo.product_id}, ${specialVo.specialId});">
						  	  	<c:if test="${userGroupVo.status == 1}">
						  	  		去参团
						  	  	</c:if>
						  	  	<c:if test="${userGroupVo.status != 1}">
						  	  		已满团
						  	  	</c:if>
						  	  </a>
						  </div>
						  </c:forEach>
					</div>
				</c:if>
				
				<c:if test="${comments.size() > 0}">
					<div class="evaluateBox plbox">
						 <div class="pltitle" onclick="location.href='${mainserver}/weixin/productcomment/findList?type=3&productId=${productInfo.product_id}&specialId=${specialVo.specialId}'">
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
		<form id="buyForm" action=mainServer+"/weixin/order/addOrderByGroup" method="post" >				
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
				
				<a href="javascript:void(0)" class="addShopCar" onclick="toAddBuy()"><p>单独购买</p></a>
				<c:choose>
					<c:when test="${specialVo.status == 1 && specialVo.longStart <= currentTime && specialVo.longEnd > currentTime}">
						<a href="javascript:void(0)" class="newBuy" onclick="toAddGroupBuy()"><p>立即开团</p></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" class="newBuy huibg" onclick="toAddGroupBuy()"><p>立即开团</p></a>
					</c:otherwise>
				</c:choose>
				
				<input type="hidden"  id="productId"  name="productId"  value="${productInfo.product_id}"  readonly="readonly"/>
				<input type="hidden"  id="specialId"  name="specialId" value="${specialVo.specialId}"  readonly="readonly"/>
				<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId" value="${productInfo.listSpecVo[0].spec_id}" readonly="readonly"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount" value="1" readonly="readonly"/>
				<input type="hidden" id="orderSource" name="orderSource" value="${orderSource}">
			</div>
			</c:if>
			<c:if test="${isAgent == 1}">
			<div class="footbtn">
				<c:if test="${collectionId > 0}">
				<span class="shou shoucan" onclick="addOrCancelCollect(1, ${collectionId})"></span>
				</c:if>
				<c:if test="${collectionId == 0}">
				<span class="shou" onclick="addOrCancelCollect(0, 0)"></span>
				</c:if>
				<a href="javascript:void(0)" class="addShopCar" onclick="toAddBuy()"><p>单独购买</p></a>
				<c:choose>
					<c:when test="${specialVo.status == 1 && specialVo.longStart <= currentTime && specialVo.longEnd > currentTime}">
				<a href="javascript:void(0)" class="newBuy" onclick="toAddGroupBuy()"><p>立即开团</p></a>
				</c:when>
					<c:otherwise>
				<a href="javascript:void(0)" class="newBuy huibg" onclick="toAddGroupBuy()"><p>立即开团</p></a>
				</c:otherwise>
				</c:choose>
				<input type="hidden"  id="productId"  name="productId"  value="${productInfo.product_id}"  readonly="readonly"/>
				<input type="hidden"  id="specialId"  name="specialId" value="${specialVo.specialId}"  readonly="readonly"/>
				<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId" value="${productInfo.listSpecVo[0].spec_id}" readonly="readonly"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount" value="1" readonly="readonly"/>
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
					<label for=""></label>
					单买价:<strong style="font-weight: normal;color: #AA2C23;font-size: 0.25rem;text-decoration: none;">¥${productInfo.listSpecVo[0].spec_price}
						 </strong>
					团购价:<span style="font-weight: normal;color: #AA2C23;font-size: 0.25rem;text-decoration: none;">¥${productInfo.listSpecVo[0].activity_price}
						</span>
				</p>
				<p class="inventory" id="stockCopy">
					库存${productInfo.listSpecVo[0].stock_copy}件	
				</p>
				<p class="normsChoose">已选择  : ${productInfo.listSpecVo[0].spec_name}</p>
				<p class="inventory" id="packageDesc">
					<label>规格说明：</label>
					${productInfo.listSpecVo[0].package_desc}
				</p>
			</div>
		</div>
		<div class="" id="activity">
			
		</div>
		<div class="installBox">
			<label for="">规格</label>
			<ul>
				<c:forEach items="${productInfo.listSpecVo}"  var="s" varStatus="tstatus">
					<c:choose>
						<c:when test="${s.activity_price !=null}">
							<li value="¥${s.activity_price}" data-orgPrice="¥${s.spec_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
							data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}"
							data-carNums="${s.carNums }" data-carCanAdd="${s.carCanAdd }" data-package="${s.package_desc }"
							data-promoitionName="${s.promoitionName }" data-fullMoney="${s.fullMoney }" data-cutMoney="${s.cutMoney }"
							<c:if test="${tstatus.index == 0}"> class="active" </c:if>>${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
								
							</li>
						</c:when>
						<c:otherwise>
							<li value="¥${s.spec_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
							data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}"
							data-carNums="${s.carNums }" data-carCanAdd="${s.carCanAdd }" data-package="${s.package_desc }"
							data-promoitionName="${s.promoitionName }" data-fullMoney="${s.fullMoney }" data-cutMoney="${s.cutMoney }"
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
	    	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/common.js?t=201801262200"></script>
	<script type="text/javascript">
	var title = "挥货 - ${productInfo.product_name}";
	var shareExplain = '${productInfo.share_explain}';
	var desc = "吧唧零嘴、懒懒速食、锅里乱炖、清爽水果都在挥货星球~";
	if(shareExplain!=null && shareExplain!=""){
		desc = shareExplain;
	}
	
	var sourceParam = getQueryString("source");
	if(sourceParam != null && sourceParam != "" && sourceParam.length > 0){
		sourceParam = "&source="+sourceParam;
	}else{
		sourceParam = "";
	}
	
	var link = '${mainserver}/weixin/product/toGroupGoodsdetails?productId=${productInfo.product_id}&specialId=${specialVo.specialId}&source=${orderSource}';
	var back = '${backUrl}';
	var imgUrl = "${productInfo.product_logo}";
	var specStatus = "${specialVo.status}";
	var specStart = "${specialVo.longStart}";
	var specEnd = "${specialVo.longEnd}";
	var currentTime = "${currentTime}";
	
	var joinCopy = "${sysGroup.joinCopy}";
	var groupCopy = "${sysGroup.groupCopy}";
	var customerId = "${customerId}";
	var objId = '${productInfo.product_id}';
	var specialId ='${specialVo.specialId}';
	var mobile = '${mobile}';
	var stockCopy = "${productInfo.listSpecVo[0].stock_copy}"; // 库存 默认第一个规格的库存
	</script>
	
	<script src="${resourcepath}/weixin/js/goods/wx_groupsdetails.js?t=201802261813"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>
</body>
</html>