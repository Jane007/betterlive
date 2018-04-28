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
		${product.product_name}-挥货
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801261745" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/goodsdetails.css?t=201801261745" />
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
   		var title = "挥货 - ${specialVo.specialName}";
		var shareExplain = '${specialVo.specialIntroduce}';
		var desc = "吧唧零嘴、懒懒速食、锅里乱炖、清爽水果都在挥货星球~";
		if(shareExplain!=null && shareExplain!=""){
			desc = shareExplain;
		}
		
		var link = '${mainserver}/weixin/productgroup/toJoinGroup?userGroupId=${usergroup.userGroupId}&productId=${product.product_id}&specialId=${specialVo.specialId}&source=${orderSource}';
		var back = link;
		var imgUrl = "${specialVo.specialCover}";
		
		var mobile='${mobile}';
		var customerId = "${customerId}";
		
		var orig='${usergroup.originator}';
		var myCust = '${customerId}';
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
		<div class="mainBox" style="top:0px;">		 
			<div class="goodsContent">
				<div class="ptboxs">
					<div class="ptleft"> 	
						<img src="${product.product_logo}" alt="">
						<span>
							<c:if test="${product.labelName != null && product.labelName != ''}">${product.labelName}</c:if>
				 			<c:if test="${product.labelName == null || product.labelName == ''}">${sysGroup.limitCopy}人团</c:if>
						</span> 			
					</div>
					<div class="goodsInfor protitlebox ptright">
						 <div class="protitle">
						 	<h3>${product.product_name}---${product.product_id} </h3>
						 	<p> 
						 		<c:if test="${productSpecVo.package_desc != null && productSpecVo.package_desc != ''}">
						 			${productSpecVo.package_desc}
						 		</c:if>
						 		<c:if test="${productSpecVo.package_desc == null || productSpecVo.package_desc == ''}">
						 			${productSpecVo.spec_name}
						 		</c:if>
						 	</p>  
<%-- 						 	<em class="tuan">${sysGroup.limitCopy}人团</em>      --%>
						 </div> 
						 <div class="projiage"> 
						 	<p>  
						 		￥${productSpecVo.activity_price}<strong>￥${productSpecVo.spec_price}</strong>
						 	</p>
						 	<span>
						 		已团${product.salesVolume}份 
						 	</span>
						 </div>
						
					</div>
				</div>
				<c:choose>  
							<c:when test="${postageId != null && postageId != ''}">
								  <div class="probiaoqian">
								   			<span>${postageMsg}</span>
								   			<c:if test="${product.is_quality == 1}">
											  <span>权威质检</span>
										    </c:if> 
										    <c:if test="${product.is_testing == 1}">
											  <span>专业测评</span> 
											  </c:if>
								  </div>  
							</c:when>  
							<c:otherwise>
									   <c:if test="${product.is_freight == 1 || product.is_quality == 1 || product.is_testing == 1}">
										 <div class="probiaoqian"> 
										 		<c:if test="${product.is_freight == 1}">
												  <span>免运费</span>
											    </c:if>
											    <c:if test="${product.is_quality == 1}">
												  <span>权威质检</span>
											    </c:if> 
											    <c:if test="${product.is_testing == 1}">
												  <span>专业测评</span> 
												</c:if>
										 </div>
									 </c:if>
							</c:otherwise>
				</c:choose> 
				<c:if test="${groupRules.size() > 0}"> 
				<div class="evaluateBox plbox" >
					 <div class="pltitle" style="margin-bottom:0;">
					 	<span>拼团规则</span> 
					 </div>
				</div>
				<div class="ptjieshao">
					<c:forEach items="${groupRules}" var="groupRule"  varStatus="idx">
						<p><em>${idx.index+1}</em>${groupRule}</li>
					</c:forEach>
				</div>
				</c:if>
				<!--下面的div的class的最后一个名字改为ptrenbox1即为拼团成功，改为 ptrenbox3即为拼团失败的印章-->  
				<c:if test="${tuanFlag == 1 || tuanFlag == 4}">
				<div class="evaluateBox plbox ptrenbox1" >
				</c:if>
				<c:if test="${tuanFlag == 3}">
				<div class="evaluateBox plbox ptrenbox" >
				</c:if>
				<c:if test="${tuanFlag == 2}">
				<div class="evaluateBox plbox ptrenbox3" >
				</c:if>
					<div class="ptimg">
						<c:forEach items="${groupJoins}" var="grvo" begin="0" end="1">
							<span><img src="${grvo.custImg}" alt="" /></span>
						</c:forEach>
						<c:if test="${fn:length(groupJoins) < 2}">
						<span><img src="http://www.hlife.shop/huihuo/resources/images/default_photo.png" alt="" /></span>
					</c:if>
					</div>
					<div class="ptjsbox">
						<c:if test="${tuanFlag == 1 || tuanFlag == 4}">
							<span>已成团</span>
						</c:if>
						<p>${tuanDesc}</p>
					</div>
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
	 
	</div>
	 <!-- class="ctbtnbox ctshibai"时为失败状态按钮 -->
	<div class="ctbtnbox">
		<form id="buyForm" action="${mainserver}/weixin/order/addOrderByGroup" method="post">
				<input type="hidden"  id="productId"  name="productId"  value="${product.product_id}"  readonly="readonly"/>
				<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly" value="${productSpecVo.spec_id}"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount" value="1"  readonly="readonly"/>
				<input type="hidden"  id="userGroupId"  name="userGroupId" value="${usergroup.userGroupId}"  readonly="readonly"/>
				<input type="hidden"  id="specialId"  name="specialId" value="${specialVo.specialId}"  readonly="readonly"/>
				<input type="hidden" id="orderSource" name="orderSource" value="${orderSource}">
				<c:if test="${tuanFlag == 3}">
					<a id="buyLine" href="javascript:toAddBuy();">立即参团</a>
				</c:if>
				<c:if test="${tuanFlag == 4}"><a id="buyLine" href="${mainserver}/weixin/product/toGroupGoodsdetails?specialId=${specialVo.specialId}&productId=${product.product_id}">一键开团</a></c:if>
		</form>
	</div>
	
	<div class="backTop"></div>
	
	<div class="mask"></div>
	<div class="dia-mask"></div>
	
	<div class="vaguealert">
		<p>绑定成功</p>
	</div>
	 
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/common.js?t=201801262200"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
	<script src="${resourcepath}/weixin/js/goods/wx_gogroupsd.js?t=201802251554"></script>
	
	
</body>
</html>