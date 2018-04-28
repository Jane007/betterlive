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
		<link rel="stylesheet" href="${resourcepath}/weixin/css/useRedpacket.css" />
		<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-领取红包</title>
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
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="headbg">
				<img src="${resourcepath}/weixin/img/shiyong.png" alt="" />
				<a href="javascript:;">使用规则</a>
			</div>
			<div class="mainBox" >		
				<div class="couponBox">
					<div class="coupon">
						<div class="valid-left">
							<div class="va-top">
								<p class="va-title">挥货优惠红包</p>
								<p class="validity">有效期至 ${endDate }</p>
							</div>
							<div class="va-bot">
								<p>仅限用户${mobile }使用</p>
							</div>
						</div>
						<div class="valid-right">
							<p class="figure"><em>¥</em> ${coupon.coupon_money}</p>
							<p class="usable">满${coupon.usemin_money}可用</p>
						</div>
					</div>
				</div>
				<div class="now-use">
					立即使用
				</div>
				<div class="redpacketHit">
					你已成功领取红包，红包已放入您的账号，<a href="${mainserver}/weixin/customercoupon/toCustomerCoupon?canusecoupon=0">点击查看</a>
				</div>
			</div>
			
		</div>
		
		<div class="boundPhone">
			<div class="dia-title">绑定手机号码</div>
				<form id="form1" action="" method="post">
					<ul>
						<li class="phoneBox">
							<label for=""></label>
							<input type="tel" name="mobile"  maxlength="11"  id="mobile"  placeholder="请输入手机号码"/>
							
						</li>
						<li class="verifyCode">
							<label for=""></label>
							<input type="text"  name="verifycode"  id="verifycode"  maxlength="5"  placeholder="请输入验证码" />
							<span class="verificationcode">获取验证码</span>
						</li> 
					</ul>
					<div class="now-bound" align="center">绑定</div>
				</form>
			<%-- <div class="boundout"><img src="${resourcepath}/weixin/img/outbox.png" alt="" /></div> --%>
	    </div>
		    
		<div class="mask"></div>
		<div class="dia-mask"></div>
		
		<div class="vaguealert">
			<p></p>
		</div>
		
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript">
		var flag='${flag}';
		var mobile='${mobile}';
		var orderCode='${orderCode}';
		$(function(){
			if(null==flag || ""==flag || null==mobile || ""==mobile){
				$('.dia-mask').show();
				$(".boundPhone").show();
			}else{
				if("exist"==flag){
					showvaguealert("请勿重复领取!");
				}
				$(".boundPhone").hide();
				//$(".mainBox").show();
			}
			
			$(".now-use").click(function(){
				window.location.href = "${mainserver}/weixin/index";  //跳转首页
			})
			
			//提示弹框
			function showvaguealert(con){
				$('.mask').show();
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function(){
					$('.vaguealert').hide();
					$('.mask').hide();
				},2000);
			}
			
			
			//提示弹框
			function showvaguealertback(con){
				$('.mask').show();
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function(){
					$('.vaguealert').hide();
					$('.mask').hide();
					window.location.href = "${mainserver}/weixin/index";  //跳转首页
				},2000);
			}
			
			
			//绑定手机号码 提示弹框
			function showvaguealertbound(con){
				$('.mask').show();
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function(){
					$('.vaguealert').hide();
					$('.mask').hide();
				},2000);
			}
			
			//判断手机号码
			var phoneflag=false;
			$("#mobile").change(function(){
				var $phone=/^((1[0-9]{2})+\d{8})$/;
				var $phoneval=$("#mobile").val();
				if($phoneval){
					if(!$phone.test($phoneval)){ 
						showvaguealert('您输入的手机号有误！');
					    phoneflag=false;
					}else{
						phoneflag=true;
					}
				}else{
					showvaguealert('您输入的手机号不能为空!');
				}
				
			});
			
			//点击获取验证码
			var codeflag=false;
			$('.verificationcode').click(function(){
				var $phoneval=$("#mobile").val();
				if(!codeflag){
					if(!codeflag&&phoneflag){
						$(this).addClass('codeactive');
						$('.verificationcode').text('重新获取 ('+ 60+')');
						var codetime=setInterval(countdown,1000);
						var i=60;
						function countdown(){
							i--;
							$('.verificationcode').text('重新获取 (' + i+ ')');
							if(i<=0){
								i=0;
								clearInterval(codetime);
								$('.verificationcode').text('获取验证码').removeClass('codeactive');
								codeflag=false;
							}
						}
						codeflag=true;
						getVerifCode();
					}else{
						if($phoneval){
							showvaguealert('您输入的号码有误！');
						}else{
							showvaguealert('您输入的号码不能为空！');
						}
					}
				}
				
			});
			
			
			//判断验证码
			var getflag=false;
			var $code=/^[0-9]{5}$/;
			$("#verifycode").change(function(){
				var $entrypass=$("#verifycode").val();
				if(!$code.test($entrypass)){ 
					showvaguealert('验证码为5位数字!');
				    getflag=false;
				}else{
					getflag=true;
				}
			});
			
			
			//点击注册确定
			$('.now-bound').click(function(){
				if(getflag&&phoneflag){
					
					var phone=$("#mobile").val();
					var verifycode=$("#verifycode").val();
					
					$.ajax({                                       
						url:'${mainserver}/weixin/shareredpacket/getUserShareRedPacket',
						data:{
							"mobile":phone,
							"code":verifycode,
							"orderCode":orderCode
						},
						type:'post',
						dataType:'json',
						success:function(data){
							if(data.result=='succ'){
								$(".boundPhone").hide();
								$(".dia-mask").hide();
								$(".mainBox").show();
								
								$(".va-bot p").html("仅限用户"+phone+"使用");
							}else if(data.result=='successful'){	
								showvaguealert("优惠券已被领取完"); 	
							}else{
								showvaguealert(data.msg);
							}
						},
						failure:function(data){
							showvaguealert('出错了');
						}
					});
					
				}else{
					var $phoneval=$("#mobile").val();
					var $entrypass=$("#verifycode").val();
					
					if(!$phoneval){
						showvaguealert('您输入的号码不能为空！');
					}else if(!phoneflag){
						showvaguealert('您输入的号码有误！');
					}else if(!$entrypass){
						showvaguealert('验证码不能为空！');
					}
				}
			});
			
		});
		
		//提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},1000);
		}
		
		
		//绑定手机号码获取验证码
		function getVerifCode(){
	    	var phoneNum =$("#mobile").val();
	    	$.ajax({
	    		url: "${mainserver}/weixin/presentcard/sendMessage",
				type: "POST",
				dataType:'json',
				async:false,
				data:{'phoneNum':phoneNum},
				success: function(data) {
					var obj = data;
					if('fail' != data.result){
						showvaguealert('获取验证码成功！');
					}else{
						showvaguealert('获取验证码失败！');
					}
				}
			});
	    }
		
	  /*   //绑定手机号码获取验证码
		function getVerifCode(){
	    	var phoneNum =$("#mobile").val();
	    	$.ajax({
	    		url: "${mainserver}/weixin/usercenter/sendEmail",
				type: "POST",
				dataType:'json',
				async:false,
				data:{'eMail':phoneNum,
					  'selectType':'phone'
					},
				success: function(data) {
					var obj = data;
					if('fail' != data.result){
						showvaguealert('获取验证码成功！');
					}else{
						showvaguealert('获取验证码失败！');
					}
				}
			});
	    } */
	    
	    
		//点击取消绑定手机框
		$('.boundout').click(function(){
			$('.boundPhone').hide();
			$('.dia-mask').hide();
		});
	</script>
</html>	