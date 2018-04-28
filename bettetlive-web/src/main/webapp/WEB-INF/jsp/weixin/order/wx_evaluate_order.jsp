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
	    <link rel="stylesheet" href="${resourcepath}/weixin/css/submitEvaluate.css" />
		
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-评价商品</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var type= "${type}";
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
			<div class="mainBox" style="top:0rem;">
				<div class="orddetcent">
					<c:forEach items="${orderInfo.listOrderProductVo}" var="l" >
					<div class="center">
						<div class="left">
					 			<img src="${l.spec_img}" alt="" />
<!-- 								<span>广告发</span>      -->
					 		</div>
					 		<div class="midden">
					 			<span>${l.product_name}</span>
					 			<p>${l.spec_name}</p>
					 		</div>
					 		<div class="right">
					 			<c:choose>
									<c:when test="${l.activity_price != null && l.activity_price !='' && l.activity_price != '-1'}">
										<span>¥${l.activity_price}</span>
										<p>¥${l.price}</p>
									</c:when>
									<c:when test="${l.discount_price != null && l.discount_price != '' && l.discount_price != '0.00'}">
										<span>¥${l.discount_price}</span>
										<p>¥${l.price}</p>
									</c:when>
									<c:otherwise>
										${l.price}
									</c:otherwise>
								</c:choose>
					 			<em>x${l.quantity}</em>
						 </div> 
					 </div>
					 </c:forEach>
				 </div>
				<form id="evalform"  action="${mainserver}/weixin/productcomment/saveComment"  method="post" enctype="multipart/form-data">
					<div class="textcon">
						<input type="hidden" name="orderCode"  id="orderCode" value="${orderInfo.order_code}"/>
						<input type="hidden" name="orderId" id="orderId"  value="${orderInfo.order_id}"/>
						<input type="hidden" name="productId"  value="${productId}"/>
						<input type="hidden" name="urlimages" id="urlimages"  value=""/>
						
						<input type="hidden" name="orderpro_id" id="orderpro_id"  value="${orderInfo.listOrderProductVo[0].orderpro_id }"/>
						
						<textarea name="textval" id="textval" name="caseContent" maxlength="100" rows="" cols="" placeholder="分享你对商品的评价(100字以内)" style="font-size:0.24rem;  "></textarea>
						<div class="xianfont"> <label id="lyishu" >0</label>/<label id="lsheng" >100</label></div>
						<div class="updataBox">
							<ul>
							</ul>
							
							<label for="filepic">
								<form id="evalform1" enctype="multipart/form-data" method="post">
									<input type="file" name="filepic" id="filepic" accept="image/*"/>
								</form>
							</label>
							
						</div>
					</div>
				</form>	
			</div>
			<div class="submitBox">
				提交
			</div>
		</div>
		<div class="mask"></div>
		<div class="vaguealert">
			<p></p>
		</div>
		
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js?t=201801062323"></script>
	<script src="${resourcepath}/weixin/js/order/wx_evaluate_order.js"></script>
</html>	