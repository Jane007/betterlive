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
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201712251337"/>
  		<link rel="stylesheet" href="${resourcepath}/weixin/css/singlecoupon/single_redpacket.css?v=201709081724" />
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
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
	<body>
		<div id="laiout">
			<div class="header-wrap">
				<div class="header" onclick="location.href='${mainserver}/weixin/index'">
					<div class="goback fl">
						<img src="${resourcepath}/weixin/img/singlecoupon/backpage.png"/>
					</div>
					<h3>领取红包</h3>
				</div>
			</div>
			<div class="content-wrap">
				<div class="content">
					<div class="product">
						<div class="productbanner">
							<img src="${resourcepath}/weixin/img/singlecoupon/banner.jpg"/>
							<div class="text">
								<h3>作为印尼官方旅游部推荐零食</h3>
								<p>丽芝士采用经过两百多道严格检测的优质原料</p>
								<p>并使用现代化无菌生产技术，吃得安全又放心</p>
								<p>独立小袋包装，方便携带，好吃不脏手</p>
								<p>现在<span>领取25元</span><span>挥货APP优惠券</span></p>
								<h4>仅需0.1元即刻带回家!</h4>
							</div>
						</div>
						<div class="pronanme">
							<h2><span>${singleCouponVo.couponName}</span></h2>
							<h3>下单立减<span><fmt:formatNumber value="${singleCouponVo.couponMoney}" pattern="#,###.##" type="number" /></span>元</h3>
						</div>
						<div class="propicture">
							<img src="${singleCouponVo.couponBanner}"/>
						</div>
					</div>
					<form id="subForm" action="" method="post">
						<div class="lab">
							<input type="tel" id="phoneNum" name="pho" value="" placeholder="请输入手机号码"/>
						</div>
						<div class="lab">
							<input type="tel" id="msgCode" name="txt" placeholder="请输入验证码" />
							<span id="phoneCode">获取验证码</span>
						</div>
						<input type="button" id="btn" value="立即领取" onclick="checkForm()"/>
					</form>
					<div class="rule">
						<img src="${resourcepath}/weixin/img/singlecoupon/rule_03.png"/>
						<ul class="rulelist">
							<li>
								<h4>1.礼品使用期限：2017.12.26-2018.01.31</h4>
							</li>
							<li>
								<h4>2.礼品领取方式：</h4>
								<p>（1）输入手机号，获得验证码；</p>
								<p>（2）输入验证码，领取25元优惠券，支付0.1元点击立即购买，即可成功；</p>
								<p>（3）限制前300用户，领完即止。</p>
							</li>
							<li>
								<h4>3.礼品配送范围：包邮地区只限北京地区。</h4>
							</li>
							<li>
								<h4>4.同一个设备同一个手机号同一个用户和同一个收货地址仅限领取一次。</h4>
							</li>
							<li>
								<h4>5.此活动最终解释权归挥货平台所有。</h4>
							</li>
						</ul>
						<img src="${resourcepath}/weixin/img/singlecoupon/rule_06.png"/>
					</div>
				</div>
			</div>	
		</div>
		
		<form id="buyForm" action="${mainserver}/weixin/order/addBuyOrder" method="post">				
			<input type="hidden"  id="productId"  name="productId" readonly="readonly" value="${productId}"/>
			<input type="hidden"  id="orderSource"  name="orderSource" readonly="readonly"/>
			<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly" value="${specId}"/>	
	  		<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
			<input type="hidden"  id="buyAmount"  name="buyAmount"  readonly="readonly" value="1"/>
			<input type="hidden"  id="returnType"  name="returnType" value="1" readonly="readonly"/>
		</form>
		
		<div class="vaguealert">
			<p id="tishi"></p>
		</div>
	</body>
	<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script>
	
	var phonNum = '${customer.mobile}';//空为未绑定手机的用户，否則为已绑定手机的用户
// 	var couponId = '${couponId}';
// 	var specId = '${specId}';
	var operateFlag ='${operateFlag}';
	
	$(function(){
		if(operateFlag == null || operateFlag == 0){
			showvaguealert("优惠券不存在");
			setTimeout(function(){
				window.location.href = "${mainserver}/common/toFuwubc?ttitle=单品红包信息提示&tipsContent=您来晚了，红包已被抢完了~";
				return false;
			},1000);
		}
		var theUrl = window.location.href;
		if(theUrl.indexOf("source")!=-1){
			var orderSource = getQueryString("source");
			$("#orderSource").val(orderSource);
		}
		
		if(phonNum == null || phonNum == ''){
			$(".lab").show();
		}else {
			$(".lab").hide();
			var flagkey = '${flagkey}';
			if(flagkey == 1){
				$("#btn").attr("onclick", "toCouponReceive()");
			}else{
				if(flagkey == 2){
					$("#btn").attr("onclick", "subPay()");
					$("#btn").attr("value", "立即购买");
					showvaguealert("您已经领取过该红包");
				}
			}
		}
	});
	
	function checkForm(){
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
		
		checkReg(sMobile, code);
	}
	
	function checkReg(sMobile, code){
		var couponId = '${couponId}';
		var specId = '${specId}';
		$.ajax({
			type: 'POST',
		   	url: "${mainserver}/weixin/singlecoupon/checkSmsCode",
		   	data:{"phoneNum":sMobile, "checkCode":code,"couponId":couponId,"specId":specId,"regSource":"${regSource}"},
		   	async: false,
		   	dataType:'json',  
			success:function(data) { 
			  	if(data.result == 'success'){
			  		showvaguealert("领取成功");
			  		$(".lab").hide();
			  		$("#btn").attr("onclick", "subPay()");
					$("#btn").attr("value", "立即购买");
			  	}else{
			  		if(data.msg == "您已经领取过了"){
			  			$(".lab").hide();
			  			$("#btn").attr("onclick", "subPay()");
						$("#btn").attr("value", "立即购买");
						showvaguealert("您已经领取过该红包，不能重复领取");
			  		}else{
				  		showvaguealert(data.msg);
			  		}
			  	}
	   		}
		});
	}
		$('#phoneCode').click(function(){
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
			var couponId = '${couponId}';
			var specId = '${specId}';
			$.ajax({
				type: 'POST',
				data:{"phoneNum":phonNum, "couponId":couponId,"specId":specId},
			   	url: "${mainserver}/weixin/singlecoupon/couponReceive",
			   	async: false,
			   	dataType:'json',  
				success:function(data) { 
				  	if(data.result == 0){
				  		showvaguealert("领取成功");
				  		$(".lab").hide();
				  		$("#btn").attr("onclick", "subPay()");
						$("#btn").attr("value", "立即购买");
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
		var productSpecId=$("#productSpecId").val();
		
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
					$('#phoneCode').text(i+'s').addClass('active');
					if(i<=0){
						i=0;
						clearInterval(codetime);
						$('#phoneCode').text('获取验证码').removeClass('active');
						codeflag=false;
					}
				}
				codeflag=true;
			}
		}
	</script>
</html>
