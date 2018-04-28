<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<meta name="format-detection" content="telephone=no" />
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
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<title>挥货-订单详情</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var groupJoinId = "${orderInfo.groupJoinId}";
    		var userGroupId= '${userGroupId}';
    		var product_id= '${orderInfo.listOrderProductVo[0].product_id}';
    		var specialId= '${specialVo.specialId}';
    		var title = '${specialVo.specialName}-挥货';
    		var shareExplain = '${specialVo.specialIntroduce}';
    		var imgUrl = "${specialVo.specialCover}";
    		var tabType= "${tabType}";
    		var _hmt = _hmt || [];
    		(function() {
    		  var hm = document.createElement("script");
    		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
    		  var s = document.getElementsByTagName("script")[0]; 
    		  s.parentNode.insertBefore(hm, s);
    		})();
    	</script>
	</head>

	<body style="background:#fff;">
	<div class="initloading"></div>
	<div class="dia-mask" style="display:none;" onclick="closeShareOrCode();"></div>  
	<div id="qrcode" class="erbgtu" style="display: none;">
	</div>  
	<div class="zhuantifx" style="display:none;" onclick="closeShareOrCode();">
		<img src="${resourcepath}/weixin/img/fxbg.png" alt="" /> 
	</div>
		<!-- 已支付的团购商品 -->
		<c:if test="${orderInfo.groupJoinId > 0 && invitedFlag != 1 && orderInfo.listOrderProductVo[0].status != 6}">
			<div class="kaituanbox">
				<div class="tuanbox">
					<c:forEach items="${groupJoins}" var="grvo" begin="0" end="1">
						<span><img src="${grvo.custImg}" alt="" /></span>
					</c:forEach>
					<c:if test="${fn:length(groupJoins) < 2}">
						<span><img src="http://www.hlife.shop/huihuo/resources/images/default_photo.png" alt="" /></span>
					</c:if>
				</div>
				<div class="tuanfont">
					<span>
						${tuanDesc}
	<!-- 					<p>剩余20:38:50结束</p> -->
					</span>
				</div>
				<div class="tuan_btn">
					<a class="a1" href="javascript:invitedJoin();">邀请好友来参团</a>
					<a class="a2" href="javascript:scanJoin();">面对面扫码参团</a>
				</div>
			</div>
			<div class="orddetcent" style="padding-bottom: 0.2rem;border-top:0.2rem solid #eee;">
			 	 <div class="orddetfbox">
					 <span>商品名称：</span>
					 <p>${orderInfo.listOrderProductVo[0].product_name}</p>
				 </div>
				 <div class="orddetfbox">
					 <span>订单编号：</span>
					 <p>${orderInfo.listOrderProductVo[0].sub_order_code}</p>
				 </div>
				 <div class="orddetfbox">
					 <span>支付时间：</span>
					 <p>${orderInfo.order_time}</p>
				 </div>
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
				 </div>
				 <c:if test="${orderInfo.gitf_card_money!=null && orderInfo.gitf_card_money !='' && orderInfo.gitf_card_money != '0'}">
					<div class="orddetfbox">
						<span>礼品卡支付：</span>
						<p>-¥<fmt:formatNumber type="number" value="${orderInfo.gitf_card_money}" pattern="0.00" maxFractionDigits="2"/></p>
					</div>
				 </c:if>	
			<div class="orddetfbox">
			 	<span style="display:block;float:left;width:20%;padding-top:0.1rem; ">买家留言：</span>
					<c:if test="${orderInfo.message_info != null && orderInfo.message_info != ''}">
						<p style="display:block; float:right;width:75%;margin-top:0.1rem; text-align:left;">${orderInfo.message_info}</p>
					</c:if>
				 	<c:if test="${orderInfo.message_info == null || orderInfo.message_info == ''}">
				 		<p style="display:block; float:right;width:75%;margin-top:0.1rem; text-align:right;">
				 			暂无留言
				 		</p>
			 		</c:if>
				 <em></em>  
			 </div> 
				 <div class="orddetfbox">
					 <span>实付金额：</span>
					 <p>￥${orderInfo.pay_money}</p>
				 </div>
			</div>
			<c:if test="${groupRules != null && groupRules.size() > 0}"> 
			<div class="pintuanxu">
			<h4>拼团须知：</h4>
			<ul>
				<c:forEach items="${groupRules}" var="groupRule"  varStatus="idx">
					<li><span>${idx.index+1}</span><p>${groupRule}</p></li>
				</c:forEach>
			</ul>
			</div>
			</c:if>
		</c:if>
		
		<!-- 普通商品详情（包括交易取消的团购商品） -->
		<c:if test="${orderInfo.groupJoinId == 0 || invitedFlag == 1 || orderInfo.listOrderProductVo[0].status == 6}">
			<c:if test="${orderInfo.listOrderProductVo[0].status == 2}">
				<div class="orddettop">
					<span class="ddxqbg02">
						<c:if test="${orderInfo.groupJoinId == 0}">等待商家发货</c:if>
						<c:if test="${orderInfo.groupJoinId > 0}">等待拼团成功后发货</c:if>
					</span>
				</div>
			</c:if>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 3}">
				<div class="orddettop">
					<span class="ddxqbg01">等待买家收货<c:if test="${orderInfo.groupJoinId > 0}">(团购订单)</c:if></span>
				</div>
			</c:if>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 4}">
				<div class="orddettop">
					<span class="ddxqbg05">等待买家评价<c:if test="${orderInfo.groupJoinId > 0}">(团购订单)</c:if></span>
				</div>
			</c:if>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 5}">
				<div class="orddettop">
					<span class="ddxqbg04">交易完成<c:if test="${orderInfo.groupJoinId > 0}">(团购订单)</c:if></span>
				</div>
			</c:if>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 6}">
			<div class="orddettop orddettops" >  
				<span class="ddxqbg06">交易已取消<%-- <strong>（拼团缺1人，金额将原路退回）</strong>--%><c:if test="${orderInfo.groupJoinId > 0}">(团购订单)</c:if></span>
			</div> 
			</c:if>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 7}">
			<div class="orddettop" >  
				<span class="ddxqbg06">已退款<c:if test="${orderInfo.groupJoinId > 0}">(团购订单)</c:if></span>
			</div> 
			</c:if>
			
			<div class="drsbg">
				<span>${orderInfo.receiver}   <strong>${orderInfo.mobile}</strong></span>
				<p>${orderInfo.address}</p>
			</div> 
			<div class="orddetcent" style="border-top:0.2rem solid #eee;"> 
				<c:forEach items="${orderInfo.listOrderProductVo}" var="l" >
				<div class="center" >
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
					 <p>${orderInfo.listOrderProductVo[0].sub_order_code}</p>
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
				 <c:if test="${orderInfo.freePrice!=null && orderInfo.freePrice !='' && orderInfo.freePrice != '0' && orderInfo.freePrice != '0.00'}">
					 <div class="orddetfbox">
						<span>活动优惠：</span>
						<p>-¥<fmt:formatNumber type="number" value="${orderInfo.freePrice}" pattern="0.00" maxFractionDigits="2"/></p>
					</div>
				 </c:if>
				<c:if test="${orderInfo.gitf_card_money!=null && orderInfo.gitf_card_money !='' && orderInfo.gitf_card_money != '0'}">
					<div class="orddetfbox">
						<span>礼品卡支付：</span>
						<p>-¥<fmt:formatNumber type="number" value="${orderInfo.gitf_card_money}" pattern="0.00" maxFractionDigits="2"/></p>
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
			 		<p style="display:block; float:right;width:75%;margin-top:0.1rem; text-align:left;"> ${orderInfo.message_info}
		 		</c:if>
				<c:if test="${orderInfo.message_info == null || orderInfo.message_info == ''}">
					<p style="display:block; float:right;width:75%;margin-top:0.1rem; text-align:right;">
				  	 	暂无留言
			  	 	 </p>
		  	 	 </c:if>
				 <em></em>  
			 </div> 
				 <div class="orddetfu">
				 	实付款：<strong>¥<fmt:formatNumber type="number" value="${orderInfo.pay_money}" pattern="0.00" maxFractionDigits="2"/></strong> 
				 </div>
			</div>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 3}">
				<div class="ordetbot" style="border-top:1px solid #bdbdbd;"><span>
				<a id="confirmId" class="red" href="javascript:checkOrder(${orderInfo.order_id},${orderInfo.listOrderProductVo[0].orderpro_id},${orderInfo.listOrderProductVo[0].product_id},'${orderInfo.order_code}');">确认收货</a>
				</span></div>
			</c:if>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 4}">
				<div class="ordetbot"><span>
				<a class="red" href="${mainserver}/weixin/order/toCommentProduct?type=${tabType}&productId=${orderInfo.listOrderProductVo[0].product_id}&orderId=${orderInfo.order_id}&orderCode=${orderInfo.order_code}">去评价</a>
				</span></div>
			</c:if>
			<c:if test="${orderInfo.listOrderProductVo[0].status == 3 || orderInfo.listOrderProductVo[0].status == 4 || orderInfo.listOrderProductVo[0].status == 5}">
				<div class="ordetbot" style="display: none;"><span>
				<a href="${mainserver}/weixin/order/queryLogiticsInfo?type=${tabType}&orderpro_id=${orderInfo.listOrderProductVo[0].orderpro_id}">查看物流</a>
				</span></div>
			</c:if>
			
			<c:if test="${orderInfo.listOrderProductVo[0].status == 6}">
				<div class="ordetbot"><span>
				<a href="javascript:delOrder(${orderInfo.order_id},${orderInfo.listOrderProductVo[0].orderpro_id});">删除订单</a>
				</span></div>
			</c:if>
		</c:if>
		
		 <div class="vaguealert">
			<p></p>
		 </div>
	</body>
 
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script> 
	<script src="${resourcepath}/weixin/js/qrcode.js"></script>
	<script src="${resourcepath}/weixin/js/order/wx_order_detail.js?t=201803071600"></script>
</html>	