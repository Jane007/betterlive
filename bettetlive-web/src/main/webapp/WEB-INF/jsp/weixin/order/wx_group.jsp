<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<meta name="format-detection" content="telephone=no" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>团购订单详情</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/order/wx_group.css?t=201804191503"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
	<body>
	<div id="qrcode" class="erbgtu" style="display: none;">
	</div>  
	<div class="shareWx" style="display:none;" onclick="closeShareOrCode();">
		<img src="${resourcepath}/weixin/img/fxbg.png" alt="" /> 
	</div>  
		<div id="layout">
			<div class="content-wrap">
				<div class="content">
					<!--拼团-->
					<div class="group-wrap">
						<div class="group">
							<ul>
								<c:forEach items="${groupJoins}" var="grvo" begin="0" end="1">
									<li>
										<img src="${grvo.custImg}"/>
									</li>
								</c:forEach>
								<c:if test="${fn:length(groupJoins) < 2}">
									<li>
										<img src="http://www.hlife.shop/huihuo/resources/images/default_photo.png"/>
									</li>
								</c:if>
							</ul>
							<div class="text">
								<h3>${tuanDesc}</h3>
							</div>
							<div class="btn">
								<div class="obtn on" onclick="javascript:invitedJoin();">
									邀请好友来参团
								</div>
								<div class="obtn" onclick="javascript:scanJoin();">
									面对面扫码来参团
								</div>
							</div>
						</div> 
					</div>
					<!--订单-->
					<div class="order-wrap">
						<div class="order">
							<div class="serial">
								<div class="sertxt-wrap">
									<div class="sertext">
										<span>商品名称:</span>
										<span>${orderInfo.listOrderProductVo[0].product_name}</span>
									</div>
								</div>
								<div class="sertxt-wrap">
									<div class="sertext">
										<span>订单编号:</span>
										<span>${orderInfo.listOrderProductVo[0].sub_order_code}</span>
									</div>
								</div>
								<div class="sertxt-wrap">
									<div class="sertext">
										<span>支付时间:</span>
										<span>${orderInfo.order_time}</span>
									</div>
								</div>
								<div class="sertxt-wrap">
									<div class="sertext">
										<span>支付方式:</span>
										<span>
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
										</span>
									</div>
								</div>
								 <c:if test="${orderInfo.gitf_card_money!=null && orderInfo.gitf_card_money !='' && orderInfo.gitf_card_money != '0'}">
									<div class="sertxt-wrap">
										<div class="sertext">
											<span>礼品卡支付：</span>
											<span>
												-¥<fmt:formatNumber type="number" value="${orderInfo.gitf_card_money}" pattern="0.00" maxFractionDigits="2"/>
											</span>
										</div>
									</div>
								 </c:if>	
								<div class="sertxt-wrap">
									<div class="sertext">
										<span>买家留言:</span>
										<c:if test="${orderInfo.message_info != null && orderInfo.message_info != ''}">
											<span>${orderInfo.message_info}</span>
										</c:if>
										<c:if test="${orderInfo.message_info == null || orderInfo.message_info == ''}">
											<span>暂无留言</span>
										</c:if>
									</div>
								</div>
							</div> 	
						</div>
						<div class="serfoot">
							<span>实付金额:</span><span>￥${orderInfo.pay_money}</span>
						</div>
						<%--拼团须知--%>
						<c:if test="${groupRules != null && groupRules.size() > 0}">
						<div class="hint-wrap">
							<div class="hint">
								<h3>拼团须知:</h3>
								<c:forEach items="${groupRules}" var="groupRule"  varStatus="idx">
									<div class="hinttext">
										<span class="number">${idx.index+1}</span>
										<span>${groupRule}</span>
									</div>
								</c:forEach>
							</div>
						</div>
						</c:if>
					</div>
				</div>
			</div>
			<!--遮罩-->
			<div class="shad" onclick="closeShareOrCode();"  style="display:none;"></div>
		</div> 
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/qrcode.js"></script>
	<script src="${resourcepath}/weixin/js/order/wx_order_detail.js?time=8888888"></script>
</html>
