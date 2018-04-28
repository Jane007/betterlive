<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection">
	<meta name="keywords" content="挥货,团购" /> 
	<meta name="description" content="挥货，你的美食分享平台" /> 
	<title>
		支付结果
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201711171604" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/goodsdetails.css?t=201711171604" />
	<link rel="stylesheet" href="${resourcepath}/weixin/css/myOrder.css?t=201711171604" />		
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
		
		 .bkbg{background:rgba(0,0,0,0.5);position:fixed;width:100%;height:100%;top:0;z-index: 100;}     
		.shepassdboxs{background:#fff;position:fixed;width:80%;margin-left:10%;top:2rem;height:2.1rem;border-radius:0.3rem; z-index:300; padding-top:0.3rem; }  
		.shepassdboxs a.left{color:#333;}
		.shepassdboxs span{font-size:0.31rem; display:block;width:100%;text-align:center;height:1rem;line-height:1rem; }   
		.shepassdboxs input{background:#eee; padding:0.15rem 0;border-radius:3px;  width:80%;padding-left:3px; height:0.61rem; border:none;margin-left:10%; bordrr-radius:4px; display:block;  font-size:0.26rem; text-indent:0.2rem; }
		.shepassdboxs .qushan{overflow:hidden;zoom;1; height:0.84rem; line-height:0.84rem;border-top:1px solid #d9d9d9;margin-top:0.24rem; }
		.shepassdboxs .qushan a{display:inline-block;width:49%;text-align:center; font-size:0.31rem;float:left; position:relative; height:100%;  }
		.shepassdboxs .qushan a.left{border-right:1px solid #d9d9d9; }
		.shepassdboxs .qushan a.right{color:#e62d29; } 
		.succbtct .ellipsis{overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 4.6rem;text-align: right;}
	</style>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		var groupJoinId = "${orderVo.groupJoinId}";
		var userGroupId = '${userGroupId}';
		var productId = '${productId}';
		var specialId = '${specialVo.specialId}';
		var imgUrl = "${specialVo.specialCover}";
		var title = '${specialVo.specialName}-挥货';
		var shareExplain = '${specialVo.specialIntroduce}';
		
   		var _hmt = _hmt || [];
   		(function() {
   		  var hm = document.createElement("script");
   		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
   		  var s = document.getElementsByTagName("script")[0]; 
   		  s.parentNode.insertBefore(hm, s);
   		})();
   	</script>
</head>
<body style="background:#eee;"> 
<div class="initloading"></div>

<div class="bkbg" style="display: none;"></div> 
<div class="shepassdboxs" style="display: none;">
		<span>您是否返回微信</span>  
		<div class="qushan">
			<a class="left dia-cancel" href="javascript:closeTipAlert();">否</a>
			<a class="right dia-addCard" id="cancelId" onclick="closeTipAlert();" href="weixin://http://weixin.qq.com">是</a> 
		</div>
</div>
	
<div class="dia-mask" style="display:none;" onclick="closeShareOrCode();"></div>  
<div id="qrcode" class="erbgtu" style="display: none;">
</div>  
<div class="zhuantifx" style="display:none;" onclick="closeShareOrCode();">
	<img src="${resourcepath}/weixin/img/fxbg.png" alt="" /> 
</div>
<%--参团或普通商品购买 --%>
<c:if test="${isMine == 0}">
	 <div class="ctsucctop">
	 		<div class="cttoptp">
					<c:if test="${orderVo.status == 2 || orderVo.status == 3 || orderVo.status == 4 || orderVo.status == 5}">
					<img src="${resourcepath}/weixin/img/succbg.png" alt="" />  
					<span>
						<c:if test="${orderVo.groupJoinId != null && orderVo.groupJoinId > 0}">恭喜你，参团成功！</c:if>
						<c:if test="${orderVo.groupJoinId == null || orderVo.groupJoinId == 0}">恭喜你，支付成功！</c:if>
						<p>我们将尽快为您发货，请耐心等待</p>
						<c:if test="${orderVo.orderSource == 'sbs0319'}"><p>加微信客服返现4元：huihuokefu123</p></c:if>
					</span>
					</c:if>
					<c:if test="${orderVo.status != 2 && orderVo.status != 3 && orderVo.status != 4 && orderVo.status != 5}">
					<img src="${resourcepath}/weixin/img/libaoxx.png?t=201711201333" alt="" />  
					<span>
						<p>很抱歉，支付失败！</p>
					</span>
					</c:if>
	
				<c:if test="${orderVo.groupJoinId > 0}">
					<div class="cttopbot"> 
						<c:forEach items="${groupJoins}" var="grvo" begin="0" end="1">
							<span><img src="${grvo.custImg}" alt="" /></span>
						</c:forEach>
						<c:if test="${fn:length(groupJoins) < 2}">
							<span><img src="http://www.hlife.shop/huihuo/resources/images/default_photo.png" alt="" /></span>
						</c:if>
					</div>
				</c:if>
			</div>
	 </div>
 </c:if>
 <%--自己开团 --%>
 <c:if test="${isMine == 1}">
 	<c:if test="${orderVo.status != 2 && orderVo.status != 3 && orderVo.status != 4 && orderVo.status != 5}">
 	 <div class="ctsucctop">
		<div class="cttoptp">
		<img src="${resourcepath}/weixin/img/libaoxx.png?t=201711201333" alt="" />  
		<span>
			<p>很抱歉，支付失败！</p>
		</span>
		</div>
	 </div>
	</c:if>
	<c:if test="${orderVo.status == 2 || orderVo.status == 3 || orderVo.status == 4 || orderVo.status == 5}">
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
	</c:if>
 </c:if>
 	 <div class="succbtbox">
 	 	<c:if test="${orderVo.groupJoinId > 0 || orderVo.quantity_detail == 1}">
	 	 	<div class="succbtct">
	 	 		<span>商品名称：</span>
	 	 		<p class="ellipsis">${productName}</p>
	 	 	</div>
	 	</c:if>
 	 	<div class="succbtct">
 	 		<span>订单编号：</span>
 	 		<p>${orderVo.order_code}</p>
 	 	</div>

 	 	<div class="succbtct">
 	 		<span>支付时间：</span>
 	 		<p>${payLogVo.pay_time}</p>
 	 	</div>
 	 	<div class="succbtct">
 	 		<span>支付方式：</span>
 	 		<p>
 	 			<c:if test="${payLogVo.pay_type == 1}">微信支付</c:if>
 	 			<c:if test="${payLogVo.pay_type == 2}">支付宝</c:if>
 	 			<c:if test="${payLogVo.pay_type == 3}">招行一网通</c:if>
	 			<c:if test="${payLogVo.pay_type == 4}">礼品卡支付</c:if>
 			</p>
 	 	</div>
 	 	<c:if test="${orderVo.gitf_card_money!=null && orderVo.gitf_card_money !='' && orderVo.gitf_card_money != '0'}">
			<div class="succbtct">
	 	 		<span>礼品卡支付：</span>
	 	 		<p>-￥${orderVo.gitf_card_money}</p>
	 	 	</div>
		</c:if>
 	 	<div class="succbtct succbtct2" >
 	 		<span>实付金额：</span>
 	 		<p><strong>¥${payLogVo.pay_money}</strong></p>
 	 	</div>
 	 </div>
 	 <div class="gwptsuccbt">
<%--  	 	<a href="${mainserver}/weixin/index">继续购物</a> --%>
		<a id="backWeixin" style="display: none;" href="weixin://http://weixin.qq.com">返回微信</a>
 	 	<a href="javascript:toOrder();">查看订单</a>
 	 	<a href="javascript:toDownApp();">下载APP</a>
 	 	<a href="${mainserver}/weixin/index" 
 	 		style="color:#999;background:none;font-size:0.2rem; border:none;text-align:center; 
 	 		height:0.3rem;line-height:0.3rem;margin-top:0.15rem;">
			使用挥货APP下单，尽享更多优惠吧！
		</a>
 	 </div>
 	 <script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
 	 <script src="${resourcepath}/weixin/js/common.js"></script> 
 	 <script src="${resourcepath}/weixin/js/qrcode.js"></script>
	 <script src="${resourcepath}/weixin/js/order/wx_payResult.js"></script>
</body>
</html>