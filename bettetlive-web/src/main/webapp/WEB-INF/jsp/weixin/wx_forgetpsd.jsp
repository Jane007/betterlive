<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801261741" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/forgetPass.css?t=201801261741" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-忘记密码</title>
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
			<div class="mainBox">
				<div class="LOGO"><img src="${resourcepath}/weixin/img/xxlogo.png?t=20180209" alt="" /></div> 
				<form action="" class="forget-form">
					<ul>
						<li class="phoneBox">
							<input type="text" placeholder="请输入手机号码" name="mobile"  id="mobile"  maxlength="11" style="color:#fff;"/>
							<span class="verificationcode">获取验证码</span>
						</li>
						<li>
							<input type="text" placeholder="请输入验证码" name="verifycode"  id="verifycode"  maxlength="5"style="color:#fff;"/>
						</li>
						<li>
							<input type="password" placeholder="设置新登录密码" id="password" name="password"style="color:#fff;"/>	
						</li>
					</ul>
					<div class="now-complete" onclick="updatePasswordCenter()">完成</div>
				</form>
				<div class="contact"><a href="tel:4008281878">手机号码不可用？联系客服</a></div>
			</div>
		</div>
		<div class="vaguealert">
			<p></p>
		 </div>
		<div class="mask"></div>
		<div class="replacement">
			<img src="${resourcepath}/weixin/img/reset.png" alt="" />
			<p>密码重置成功</p>
			<p>请重新登录</p>
			<a href="${mainserver}/weixin/tologin" class="backlogin">
				返回登录
			</a>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
	<script>
	
	
	var codeflag=false;
	//提示弹框
	function showvaguealert(con){
		$('.vaguealert').show();
		$('.vaguealert').find('p').html(con);
		setTimeout(function(){
			$('.vaguealert').hide();
		},1000);
	}
	var phoneflag=false;
	$(function(){
		//判断手机号码
		$("#mobile").change(function(){
			var $phone=/^((1[0-9]{2})+\d{8})$/;
			var $phoneval=$("#mobile").val();
			if($phoneval){
				if(!$phone.test($phoneval)){ 
					showvaguealert('您输入的手机号有误！');
				    phoneflag=false;
				    return false;
				}else{
					phoneflag=true;
				}
			}else{
				showvaguealert('您输入的手机号不能为空!');
				 return false;
			}
		});
		
		//输入框获取焦点联系客服文案隐藏
		$('input').focus(function(){
			$('.contact').hide();
		}).blur(function(){
			$('.contact').show(1500);
		});
		/* //点击完成
		$('.now-complete').click(function(){
			$('.replacement').show();
			$('.mask').show();
		});
		 */
		
		//点击获取验证码
		
		$('.verificationcode').click(function(){
			if(!codeflag){
				getVerifCode();
				if(!codeflag){
					return;
				}
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
					
			}else{
					if($phoneval){
						showvaguealert('您输入的号码有误！');
					}else{
						showvaguealert('您输入的号码不能为空！');
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
		
		
	});
	
	
	//点击注册确定
	function updatePasswordCenter(){
		var $phone=/^((1[0-9]{2})+\d{8})$/;
		var $phoneval=$("#mobile").val();
		var mobile=$("#mobile").val();
		var code=$("#verifycode").val();
		var passwords = $("#password").val();
		
		 if(mobile.lenght==0){
			showvaguealert('请输入您手机号！');
		    return false;
		}
		if(!$phone.test($phoneval)){ 
			showvaguealert('请输入正确的手机号');
		    return false;
		}
		if(code.length==0){
			showvaguealert('验证码不能为空');
			return false;
		}else if(code.length!=5){ 
			showvaguealert('验证码为5位数字!');
		   	return false;
		}
		
		if(passwords.length==0){
			showvaguealert('请输入密码');
			return false;
		}
		if(!(passwords.length>=6&&passwords.length<=18)){
			showvaguealert('密码长度在6到18之间');
			return false;
		}
		
		var password =$.md5(passwords);
		var ua = navigator.userAgent.toLowerCase();
		var weixinFlog=0;
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {
	    	weixinFlog=1;
	    }
		$.ajax({
			url:'${mainserver}/weixin/updatePasswordCenter',
			data:{
				"mobile":mobile,
				"code":code,
				"password":password,
				"weixinFlog":weixinFlog
			},
			async: false,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.result=='success'){
					showvaguealert("密码重置成功");
					setTimeout(function(){
						toBack();
					},1000);
				}else{
					showvaguealert(data.msg);
				}
			}/* ,
			failure:function(data){
				showvaguealert(data.msg);
			} */
		});
		
		
	}


	
	
	//绑定手机号码获取验证码
	function getVerifCode(){
    	var phoneNum =$("#mobile").val();
    	var $phone=/^((1[0-9]{2})+\d{8})$/;
		var $phoneval=$("#mobile").val();
		
		 if(mobile.lenght==0){
			showvaguealert('请输入您手机号！');
		    return false;
		}
		if(!$phone.test($phoneval)){ 
			showvaguealert('请输入正确的手机号');
		    return false;
		}
    	$.ajax({
    		url: "${mainserver}/weixin/presentcard/findMobile",
			type: "POST",
			dataType:'json',
			async:false,
			data:{'phoneNum':phoneNum},
			success: function(data) {
				if('success' == data.result){
					$.ajax({
			    		url: "${mainserver}/weixin/presentcard/sendMessage",
						type: "POST",
						dataType:'json',
						async:false,
						data:{'phoneNum':phoneNum},
						success: function(data) {
							var obj = data;
							if('fail' != data.result){
								codeflag=true;
								showvaguealert(obj.msg);
							}else{
								showvaguealert(obj.msg);
								codeflag=false;
							}
						}
					});
				}else{
					showvaguealert('你输入的手机号码不存在!');
					return;
				}
			}
		});
    } 
	
	function toBack(){
    	window.location.href="${mainserver}/weixin/tologin";
    }

	</script>
</html>
