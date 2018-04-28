<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common2.css?v=201709081724" />
  		<link rel="stylesheet" href="${resourcepath}/weixin/css/new-getRedpacket.css?v=201712191007" />
		<script type="text/javascript" async src="//yun.tuia.cn/h5-tuia/tuiac.js" ></script>
  		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-领取红包</title>
		<script type="text/javascript">
    		var _hmt = _hmt || [];
			(function() { 
				  
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
    	</script>
	</head>
	<body class="dpbgbox">
		<div class="guizbt"></div>
		<div class="dptitlebox">
			<span>
				${singleCouponVo.couponName}
			</span>
			<p>下单立减<fmt:formatNumber value="${singleCouponVo.couponMoney}" pattern="#,###.##" type="number" />元</p>
		</div>
		<div class="container dancentbox">
		 	<div class="cpbgbox"><img src="${singleCouponVo.couponBanner}" alt="" /> </div> 
			<div id="mainBoxShow" class="mainBox">
				<div class="getBox" style="display: block;"> 
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
				 
					<div id="couponShow" class="oldUser tyyiling"  style="display: none;"> 
						
							<div class="coupon">
								<div class="validBox">
									<div class="validLeft">
										 <strong>￥</strong><fmt:formatNumber value="${singleCouponVo.couponMoney}" pattern="#,###.##" type="number" />
									</div>   
									    <div class="validRight">
									    	<span>专享红包</span>
											<p class="usable">限${productName}</p>
											<p class="validity">有效期至${singleCouponVo.endTime}</p>
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
		
		<form id="buyForm" action="${mainserver}/weixin/product/towxgoodsdetails" method="get">				
			<div class="botShop">
				<input type="hidden"  id="productId"  name="productId" readonly="readonly" value="${productId}"/>
				<input type="hidden"  id="orderSource"  name="orderSource" readonly="readonly"/>
	<%-- 		<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly" value="${specId}"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount"  readonly="readonly" value="1"/>
				<input type="hidden"  id="returnType"  name="returnType" value="1" readonly="readonly"/>
	--%>
			</div>
		</form>
		
	</body>
	<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js?t=201801071644"></script>
	<script>
	
	var phonNum = '${customer.mobile}';//空为未绑定手机的用户，否則为已绑定手机的用户
	var couponId = '${couponId}';
	var productId = '${productId}';
	var operateFlag ='${operateFlag}';
	
	$(function(){
		
		$('.guizbt').click(function(){
			$('.useRuleBox').show();
			$('.mask').show();
		});
		
		$('.outBoxTitle').click(function(){
			$('.useRuleBox').hide();
			$('.mask').hide();
		});
		 
		if(operateFlag == null || operateFlag == 0){
			setTimeout(function(){
				window.location.href = "${mainserver}/common/toFuwubc?ttitle=领红包信息提示&tipsContent=您来晚了，红包已被抢完了~";
				return false;
			},1000);
		}
		
		var theUrl = window.location.href;
		if(theUrl.indexOf("source")!=-1){
			var orderSource = getQueryString("source");
			$("#orderSource").val(orderSource);
		}
		
		if(phonNum == null || phonNum == ''){
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
				   	url: "${mainserver}/weixin/singlecoupon/checkCodeNewCoupon",
				   	data:{"phoneNum":sMobile, "checkCode":code,"couponId":couponId,"productId":productId},
				   	async: false,
				   	dataType:'json',  
					success:function(data) { 
					  	if(data.result == '1010' || data.result == 1010){
					  		$(".getBox").hide();
					  		
							$('.now-old').on('click',function(){
								subPay();
							});
							$('#couponShow').show();
							showvaguealert("领取成功");
							$('.now-old').html("立即购买");
							
					  	}else{
					  		if(data.msg == "您已经领取过了"){
					  			$(".getBox").hide();
								$('.now-old').on('click',function(){
									subPay();
								});	
								showvaguealert("您已经领取过该红包，不能重复领取");
					  			$('#couponShow').show();
								$('.now-old').html("立即购买");
					  		}else{
						  		showvaguealert(data.msg);
						  		
					  		}
					  	}
			   		}
				});
			});
		}else {
			$('.getBox').hide();
			var flagkey = '${flagkey}';
			if(flagkey == 1){
				$('.now-old').on('click',function(){
					toCouponReceive();
				});	
				$('#couponShow').addClass("tydailing");
				$('#couponShow').show();
			}else{
				if(flagkey == 2){
					$('#couponShow').show();
					$('.now-old').on('click',function(){
						subPay();
					});	
					showvaguealert("您已经领取过该红包");
					$(".now-old").html("立即购买");
				}
			}
		}
	});
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
		
		function toCouponReceive(){
			$.ajax({
				type: 'POST',
				data:{"phoneNum":phonNum, "couponId":couponId,"productId":productId},
			   	url: "${mainserver}/weixin/singlecoupon/getCouponsByProjectId",
			   	async: false,
			   	dataType:'json',  
				success:function(data) { 
				  	if(data.result == 1010){
				  		showvaguealert("领取成功");
				  		$(".now-old").unbind("click");
				  		$('#couponShow').show();
				  		$('#couponShow').removeClass("tydailing");
						$('.now-old').on('click',function(){
							subPay();
						});	
						$(".now-old").html("立即购买");
				  	}else{
					  	showvaguealert(data.msg);
				  	}
		   		}
			});
		}
		
	
	function subPay(){
		var productId = $("#productId").val();
		if(productId == null || productId == "" || isNaN(productId)){
			 showvaguealert("系统出错啦");
			return false;
		}
		
		if(!!window.TuiaAdverter) { //先判断js是否引入成功       
			TuiaAdverter.init(function() {
				$("#buyForm").submit();
			}, 
			{
				"location": "singles_register"
			}) 	
		}else{
			$("#buyForm").submit();
		} 
		
		
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
