<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
	    <link rel="stylesheet" href="${resourcepath}/weixin/css/myCoupon.css" />
	    
		<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-使用红包</title>
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
 	 
		 <!-- 没有红包显示的DIV -->
		  <c:if test="${userSingleCoupons == null || userSingleCoupons.size() <= 0}">
			 <div class="noyhbg">
			 <span>您还没有红包哦~</span>
			 </div>
		 </c:if>
		  
		 <div style="margin-top:0.26rem; ">
		 	<div class="yhbox hbdai">
		 		<c:forEach var="couponVo" items="${userSingleCoupons}">
		 		<div class="yhnrcent">
		 			<div class="left">
		 				￥<fmt:formatNumber value="${couponVo.couponMoney}" pattern="##0.##" type="number" />
		 				<strong>满<fmt:formatNumber value="${couponVo.fullMoney}" pattern="##0.##" type="number" />可用</strong>
		 			</div>
		 			<div class="center">
		 				<span>
		 					  <c:if test="${fn:length(couponVo.couponName) > 8}">
		 					  	${fn:substring(couponVo.couponName, 0, 8)}...
		 					  </c:if>
	 					      <c:if test="${fn:length(couponVo.couponName) <= 8}">
		 					  	${couponVo.couponName}
		 					  </c:if>
 					    </span>
		 				<p>限${couponVo.product_name}使用</p>
		 				<strong>有效期至${fn:substring(couponVo.endTime, 0, 10)}</strong>
		 			</div>
		 			<div class="right">
		 				<c:if test="${returnType == 1 || returnType == 5 || returnType == 6}">
		 					<a href="${mainserver}/weixin/order/addBuyOrder?userSingleCouponId=${couponVo.userSingleId}">立即使用</a>
		 				</c:if>
		 				<c:if test="${returnType == 2}">
		 					<a href="${mainserver}/weixin/order/addBuyOrders?userSingleCouponId=${couponVo.userSingleId}">立即使用</a>
		 				</c:if>
		 			</div>
		 		</div>
		 		</c:forEach>
		 	</div> 
		 </div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
 
</html>	