<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common2.css" />
  		<link rel="stylesheet" href="${resourcepath}/weixin/css/new-getRedpacket2.css" />
  		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-领取红包</title>
	</head>
	<body>
		<div class="container">
			<div class="headbg">
				<a class="useRule" id="useRule" style="position:absolute;top:0;height:1rem;width:100%;"></a>
				<img src="${resourcepath}/weixin/img/new_h5Bg2.png" alt="" />
			</div>
			<div id="mainBoxShow" class="mainBox">
				<div class="getBox">
					<form action="">
						<ul>
							<li class="phoneBox">
								<input type="tel" id="phoneNum" placeholder="请输入手机号领取"/>
								<div class="phonealert">
									<span></span>
								</div>
							</li>
							<li class="verifyCode">
								<input type="tel" id="msgCode" placeholder="请输入短信验证码"/>
								<span class="phoneCode">获取验证码</span>
								<div class="verifyalert">
									<span></span>
								</div>
							</li>
						</ul>
					</form>
					<div class="now-get">
						立即领取
					</div>
				</div>
				<div class="useBox">
					<div class="couponBox">
						<div class="coupon">
							<div class="validBox">
								<div class="validLeft"> 
									<span><em>¥</em><fmt:formatNumber type="number" value="${singleCouponVo.couponMoney }" pattern="0" maxFractionDigits="0"></fmt:formatNumber></span>
									<!-- <strong>满<fmt:formatNumber type="number" value="${singleCouponVo.fullMoney }" pattern="0" maxFractionDigits="0"/>元可用</strong> -->
								</div>
								<div class="validRight">
									<!-- <p class="va-title">挥货优惠红包</p>
									<p class="usable">仅限${productName }</p>  
									<%--<p class="va-local">单品</p>--%>
									<%-- <p class="validity">仅限用户<span id="myphone">${customer.mobile}</span>使用</p> --%>
									<p class="validity">有效期至${singleCouponVo.endTime }</p> -->
									<p class="redPac">专享红包</p>
									<p class="pacInfo">限单品${productName}</p>
									<p class="pacTime">有效期至${singleCouponVo.endTime }</p>
								</div>
							</div>
							<div class="getType active"></div>
						</div>
					</div>
					<div class="now-use">
						立即使用
					</div>
				<%--
					<div class="redpacketHit">
						<span class="rule"><i></i>使用规则</span><em class="put">红包已放入您的账号，<a href="${mainserver}/weixin/customercoupon/toCustomerCoupon?canusecoupon=0">点击查看</a></em>
					</div>
				--%>
				    </div>
					  <div class="oldUser">
						<div class="coupon">
							<div class="validBox">
								<div class="validLeft">
									<span><em>¥</em><fmt:formatNumber type="number" value="${singleCouponVo.couponMoney }" pattern="0" maxFractionDigits="0"></fmt:formatNumber></span>
<%-- 									<strong>满<fmt:formatNumber type="number" value="${singleCouponVo.fullMoney }" pattern="0" maxFractionDigits="0"/>元可用</strong> --%>
								</div>   
								    <div class="validRight">
										<p class="usable">仅限${productName }</p>
										<%--<p class="va-local">单品</p>--%>
										<%-- <p class="validity">仅限用户<span id="myphone">${customer.mobile}</span>使用</p> --%>
										<p class="validity">有效期至 ${singleCouponVo.endTime }</p>
									</div>
								</div>
								<div class="getType"></div>
							</div>
						   <div class="now-old">
							立即领取
						  </div>
					</div>
			</div>
		</div>
		<div class="mask"></div>
		<div class="useRuleBox">
			<div class="ruleCon">
				<h3>••• 使用规则 •••</h3>
				<p>1.红包仅限购买挥货官网商品使用，不能兑换成现金。</p>
				<p>2.使用数量：每个订单只能使用1个红包。</p>
				<p>3.时间限定：请在有效使用时间内使用，过期未使用自动作废；长期有效的红包不受此条限制。</p>
				<p>4.产品限定：部分红包限定某个特定的商品，仅可用于该商品优惠；不限定红包可全场通用，特价商品或者特殊注明商品除外。</p>
				<p>5.使用门槛：有门槛的红包根据不同红包的规则，需达到一定金额方可使用；无门槛红包可以直接抵用订单金额。</p>
				<p>6.红包使用：不找零、不折现，限一次性使用完（ 不能抵扣快递、物流或服务费）；如使用后发生退款，红包不作为退款项。</p>
				<p>7.红包支付部分不开发票（如需发票）。</p>
				<p>8.订单退还：订单退还时，已经使用的红包不再退还。</p>

			</div>
			<div class="outBoxTitle"></div>
		</div>
		<div class="vaguealert">
			<p id="tishi"></p>
		</div>
		
		<form id="buyForm" action="${mainserver}/weixin/order/addBuyOrder" method="post">				
			<div class="botShop">
				<input type="hidden"  id="productId"  name="productId" readonly="readonly"/>
				<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly" value="${specId}"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount"  readonly="readonly" value="1"/>
				<input type="hidden"  id="returnType"  name="returnType" value="1" readonly="readonly"/>
			</div>
		</form>
		
	</body>
	<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script>
	$(function(){
		var phonNum = '${customer.mobile}';//空为未绑定手机的用户，否則为已绑定手机的用户
		var couponId = '${couponId}';
		var specId = '${specId}';
		var productId='${productId}';
		var operateFlag ='${operateFlag}';
		var regSource = '${regSource}';
		
		if(operateFlag == null || operateFlag == 0){
			showvaguealert("优惠券不存在");
			setTimeout(function(){
				window.location.href = "${mainserver}/weixin/404.jsp";
				return false;
			},1000);
		}
		
		if(phonNum == null || phonNum == ''){
			$('.getBox').show();
			$('.useBox').hide();
			
			//绑定之后领取红包
			$('.now-get').click(function(){
				var sMobile = $('#phoneNum').val();
				var code = $('#msgCode').val();
				if(sMobile==null || sMobile==''){
					showvaguealert("请输入手机号码");
					return false;
				}
				if(code==null || code==''){
					showvaguealert("请输入手机验证码");
					return false;
				}
				$.ajax({
					type: 'POST',
				   	url: "${mainserver}/weixin/singlecoupon/checkSmsCode",
				   	data:{"phoneNum":sMobile, "checkCode":code,"couponId":couponId,"specId":specId,"regSource":regSource},
				   	async: false,
				   	dataType:'json',  
					success:function(data) { 
					  	if(data.result == 'success'){
							$("#myphone").html("");
							$("#myphone").html(sMobile);
							$('.now-use').on('click',function(){
// 								location.href='${mainserver}/weixin/product/towxgoodsdetails?productId='+productId;
								$("#productId").val(productId);
								subPay();
							});	
// 					  		$('.headbg img').attr('src','${resourcepath}/weixin/img/lingquchenggong.jpg');
							$('.useBox').show();
							$('.getBox').hide();
							$("#mainBoxShow").removeClass("mainBox");
							$("#mainBoxShow").addClass("mainBox1");
					  	}else{
						  	showvaguealert(data.msg);
					  	}
			   		}
				});
			});
		}else {
			$('.getBox').hide();
			var flagkey = '${flagkey}';
			if(flagkey == 1){
				$('.now-use').on('click',function(){
					$("#productId").val(productId);
					subPay();
				});	
				$('.useBox').hide();
				$('.oldUser').show();
			}else{
				if(flagkey == 2){
					$('.now-use').on('click',function(){
						location.href='${mainserver}/weixin/product/towxgoodsdetails?productId='+productId;
					});	
					showvaguealert("您已经领取过该红包");
					$(".now-use").html("去购买");
				}
// 				$('.headbg img').attr('src','${resourcepath}/weixin/img/lingquchenggong.jpg');
				$('.useBox').show();
				$('.oldUser').hide();
				$("#mainBoxShow").removeClass("mainBox");
				$("#mainBoxShow").addClass("mainBox1");
			}
		}
		$('.phoneCode').click(function(){
			var $this = $(this);
			var sMobile = $('#phoneNum').val();
			  if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(sMobile)) || sMobile.length != 11){ 
				  showvaguealert('请输入正确手机号码！');
			  } else {//手机发短信验证码
				  time_lun();
				  getMessage();
			  }
			
		});
		$('.useRule').click(function(){
			$('.useRuleBox').show();
			$('.mask').show();
		});
		
		$('.outBoxTitle').click(function(){
			$('.useRuleBox').hide();
			$('.mask').hide();
		});
		
		$('.now-old').click(function(){
			$.ajax({
				type: 'POST',
				data:{"phoneNum":phonNum, "couponId":couponId,"specId":specId},
			   	url: "${mainserver}/weixin/singlecoupon/couponReceive",
			   	async: false,
			   	dataType:'json',  
				success:function(data) { 
				  	if(data.result == 0){
// 				  		$("#productId").val(productId);
// 				  		$(".now-use").attr("onclick", "subPay()");
				  		$('.oldUser').hide();
// 						$('.headbg img').attr('src','${resourcepath}/weixin/img/lingquchenggong.jpg');
						$('.useBox').show();
						$("#mainBoxShow").removeClass("mainBox");
						$("#mainBoxShow").addClass("mainBox1");
				  	}else{
					  	showvaguealert(data.msg);
				  	}
		   		}
			});
		});
		
	});
	
	function subPay(){
		var productId = $("#productId").val();
		if(productId == null || productId == "" || isNaN(productId)){
			 showvaguealert("系统出错啦");
			return false;
		}
		$("#buyForm").submit();
	}
	 var count=59;
	 function countDown(){
			$("#getCode").html(count+"s");
			if(count == 0){
				$("#getCode").attr("onclick","getMessage()");
				$("#getCode").html("再次发送");
				count=59;
			}else{
				setTimeout(countDown, 1000);
				count--;
			}
	}
	 function getMessage(){
			var phoneNum = $('#phoneNum').val();
				$.ajax({
					type: 'POST',
				   url:"${mainserver}/weixin/presentcard/sendMessage",
				   data:{'phoneNum':phoneNum},
				   async: false,
				   dataType:'json',  
				   success:function(data) {
					   if('fail' == data.result){
						   showvaguealert(data.msg);
					   }else{
						   showvaguealert("手机验证码已经发送！");
						   $('#getCode').css("background","#E3DBDA");
						   $('#getCode').unbind("click");
						   setTimeout(countDown, 1000);
					   }
					  
				   }
			  })
		}
	   //提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},2000);
		}
		//提示手机号弹框
		function showphonealert(con){
			$('.phonealert').show();
			$('.phonealert').find('span').html(con);
			setTimeout(function(){
				$('.phonealert').hide();
			},2000);
		}
		//提示验证码弹框
		function showverifyalert(con){
			$('.verifyalert').show();
			$('.verifyalert').find('span').html(con);
			setTimeout(function(){
				$('.verifyalert').hide();
			},2000);
		}
		var codeflag = false;
		function time_lun(){
			if(!codeflag){
				var codetime=setInterval(countdown,1000);
				var i=60;
				countdown();
				function countdown(){
					i--;
					$('.phoneCode').text(i+'s').addClass('active');
					if(i<=0){
						i=0;
						clearInterval(codetime);
						$('.phoneCode').text('获取验证码').removeClass('active');
						codeflag=false;
					}
				}
				codeflag=true;
			}
			
		}
	</script>
</html>
