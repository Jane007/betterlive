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
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201803071600" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/myOrder.css?t=201803071600" />		
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-订单详情</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var payType = "${orderInfo.pay_type}";
    		var type = '${type}';
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
		<input type="hidden" id="ordId" value="${orderInfo.order_id}">
		<input type="hidden" id="waitPayTime" value="${orderInfo.surplusTiem}">
		<!--待支付-->
		<div class="orddettop orddettops">  
			<span class="ddxqbg03">等待买家支付<strong id="ExpireTip1"></strong><strong id="timeExpire"></strong><strong id="ExpireTip"></strong></span>  
		</div>
		<div class="drsbg">
			<span>${orderInfo.receiver}  <strong>${orderInfo.mobile}</strong></span>
			<p>${orderInfo.address}</p>
		</div>
		<div class="orddetcent">
			<c:forEach items="${orderInfo.listOrderProductVo}" var="l" >
			<div class="center">
				<div class="left">
			 			<img src="${l.spec_img}" alt="" />
	
			 		</div>
			 		<div class="midden">
			 			<span>${l.product_name}</span>
			 			<p>${l.spec_name}</p>
			 		</div>
			 		<div class="right">
			 			<c:choose>
							<c:when test="${l.activity_price != null && l.activity_price != ''}">
								<span>￥${l.activity_price}</span><p>￥${l.price}</p>
							</c:when>
							<c:when test="${l.discount_price != null && l.discount_price != '' && l.discount_price != '0' && l.discount_price != '0.00'}">
								<span>￥${l.discount_price}</span><p>￥${l.price}</p>
							</c:when>
							<c:otherwise>
								<span>￥${l.price}</span>
							</c:otherwise>
						</c:choose>
			 			<em>x${l.quantity}</em>
			 	</div> 
			 </div>
			 </c:forEach>
			 
			 <div class="orddetfbox">
				 <span>订单编号：</span>
				 <p>${orderInfo.order_code }</p>
			 </div>
			 <div class="orddetfbox">
				 <span>下单时间：</span>
				 <p>${orderInfo.order_time}</p>
				 <em></em>
			 </div>
			 <div class="orddetfbox">
				 <span>商品总价：</span>
				 <p>￥${orderInfo.total_price}</p>
			 </div>
			 <div class="orddetfbox">
				 <span>运费：</span>
				 <p>
				 	<c:if test="${orderInfo.freight != null && orderInfo.freight != '' 
				 				&& orderInfo.freight != '0.00' && orderInfo.freight != '0'}">
				 		￥${orderInfo.freight}
			 		</c:if>
		 		 	<c:if test="${orderInfo.freight == null || orderInfo.freight == '' 
				 				|| orderInfo.freight == '0.00' || orderInfo.freight == '0'}">
				 		免运费
			 		</c:if>
			 	 </p>
			 </div>
			 <c:if test="${orderInfo.freePrice!=null && orderInfo.freePrice !='' 
			 			   && orderInfo.freePrice != '0' && orderInfo.freePrice != '0.00'}">
				 <div class="orddetfbox">
					<span>活动优惠：</span>
					<p>-¥${fn:replace(orderInfo.freePrice, "-", "") }</p>
				</div>
			 </c:if>
			<c:if test="${orderInfo.gitf_card_money!=null && orderInfo.gitf_card_money !='' && orderInfo.gitf_card_money != '0'}">
				<div class="orddetfbox">
					<span>礼品卡支付：</span>
					<p>-¥${orderInfo.gitf_card_money}</p>
				</div>
			</c:if>	
			 <div class="orddetfbox">
				 <span>支付方式：</span>
				 <p>
					<c:if test="${orderInfo.pay_type==1 }">
						微信支付
					</c:if>
					<c:if test="${orderInfo.pay_type==2 }">
						支付宝
					</c:if>
					<c:if test="${orderInfo.pay_type==3 }">
						招行一网通
					</c:if>
					<c:if test="${orderInfo.pay_type == 4}">
						礼品卡支付
					</c:if>
				</p>
				 <em></em>
			 </div>
			 <div class="orddetfbox">
				<span style="display:block;float:left;width:20%;padding-top:0.1rem; ">买家留言：</span>
			 	<c:if test="${orderInfo.message_info != null && orderInfo.message_info != ''}">
			 		<p style="display:block; float:right;width:75%;margin-top:0.1rem; text-align:left;">${orderInfo.message_info}</p>
			 	</c:if>
			 	<c:if test="${orderInfo.message_info == null || orderInfo.message_info == ''}">
			 		<p style="display:block; float:right;width:75%;margin-top:0.1rem; text-align:right;">暂无留言</p>
		 		</c:if>
				 <em></em>  
			 </div> 
			 <div class="orddetfu">
			 	实付款：<strong>¥${orderInfo.pay_money}</strong> 
			 </div>
		</div>
		<div class="ordetbot">
			<span>
			<a href="javascript:cancelOrder(${orderInfo.order_id});">取消订单</a>
			<a id="payId" class="red" href="javascript:nextOrderPay(${orderInfo.order_id },'${orderInfo.order_code}');">付款</a>
			</span>
		</div>
		
		<div class="vaguealert">
			<p></p>
		</div>
	</body>
 
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script> 
	<script src="${resourcepath}/weixin/js/order/wx_pay_order_detail.js?t=201803291600"></script>
</html>	