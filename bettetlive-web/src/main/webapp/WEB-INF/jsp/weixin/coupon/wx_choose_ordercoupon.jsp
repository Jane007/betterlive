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
		<title>挥货-使用优惠券</title>
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
 
		 
		 <!-- 没有优惠券时显示的DIV -->
		 <c:if test="${userCoupons == null || userCoupons.size() <= 0}">
			 <div class="noyhbg">
			 <span>您还没有优惠券哦~</span>
			 </div>
		 </c:if>
	 	<c:if test="${userCoupons != null && userCoupons.size() > 0}">
		 <div style="margin-top:0.2rem;" >
		 	<div class="yhbox">
		 		<c:forEach var="couponVo" items="${userCoupons}">
		 		<div class="yhnrcent">
		 			<div class="top">
		 				<strong><em>￥</em><fmt:formatNumber value="${couponVo.coupon_money}" pattern="##0.##" type="number" /></strong>    
		 				<span><em>
		 						 <c:if test="${fn:length(couponVo.coupon_name) > 8}">
			 					  	${fn:substring(couponVo.coupon_name, 0, 8)}...
			 					  </c:if>
		 					      <c:if test="${fn:length(couponVo.coupon_name) <= 8}">
			 					  	${couponVo.coupon_name}
			 					  </c:if>
		 					  </em>(满${couponVo.start_money}可用)<p>${couponVo.coupon_content}</p>
		 				</span>
		 			</div>
		 			<div class="xiam">
		 				<span>有效期至${fn:substring(couponVo.endtime, 0, 10)}</span>
	 					<c:if test="${returnType == 1 || returnType == 5 || returnType == 6}">
	 						<a class="shiyong" href="${mainserver}/weixin/order/addBuyOrder?couponId=${couponVo.coupon_id}">立即使用</a> 
		 				</c:if>
		 				<c:if test="${returnType == 2}">
		 					<a href="${mainserver}/weixin/order/addBuyOrders?couponId=${couponVo.coupon_id}">立即使用</a>
		 				</c:if>
		 			</div>
		 		</div>
		 		</c:forEach>
		 	</div>
		 </div>
	 	</c:if>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
</html>	