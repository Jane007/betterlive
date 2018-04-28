<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801271741" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/login.css?t=201801271741" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-登录</title>
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
		<div class="close">
			<img src="${resourcepath}/weixin/img/login-close.png" alt="" />
		</div>
		<script src="${resourcepath}/weixin/js/refresh.js"></script> 
		<div class="container">
		 
			<div class="mainBox">
				<div class="LOGO"><img src="${resourcepath}/weixin/img/xxlogo.png?t=20180209" alt="" /></div>  
				<form action="" class="login-form">
					<ul>
						<li class="">
							<input type="text" placeholder="请输入手机号码" name="mobile"  id="mobile"  maxlength="11" style="color: #fff;font-size: 16px"/>
						</li>
						<li>
							<input type="password" placeholder="请输入登录密码" id="password" style="color: #fff;font-size: 16px" maxlength="18"/>
						</li>
						
					</ul>
					<div class="now-login" onclick="checklogin();">登录</div>
					<div class="other-fun">
						<a href="javascript:toRegister();" class="go-register">我要注册</a>
						<a href="${mainserver}/weixin/forgetPassword" class="forget-pass">忘记密码</a>
					</div>
				</form>
				<%-- 
				<div class="other-login-type">
					<p><span id="check">微信登录</span></p> 
					<div class="weichat"></div>
				</div>
				--%>
			</div>
		</div>
		 <div class="vaguealert">
			<p></p>
		 </div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
	<script type="text/javascript">
		$('.close').click(function(){
			var backUrl = window.location.href;
			if(backUrl.indexOf("backUrl")!=-1){
				backUrl = backUrl.substring(backUrl.indexOf("backUrl=")+8,backUrl.length);
				window.location.href = backUrl;  
			}else{
				window.location.href = mainServer + "/weixin/index";  
			}
		})
		
	   function checklogin(){
		   var password=$("#password").val();
		   var passwd = $.md5(password);
		   var checkPhone=/^((1[0-9]{2})+\d{8})$/;
		   var phoneval=$("#mobile").val();
		   var source = '${source}';
		   var backUrl = window.location.href;
		   if(backUrl.indexOf("backUrl")!=-1){
				backUrl = backUrl.substring(backUrl.indexOf("backUrl=")+8,backUrl.length);
		   }else{
				backUrl = "";
		   }
		
			if(phoneval == null || phoneval == ""){
				  showvaguealert('请输入手机号码');
				  return ;
			}
			
			if(!checkPhone.test(phoneval)){ 
				 showvaguealert('请输入输入正确的手机号码');
				 return ;
			}
			
		    if(password == '' || password.length==0||password==null){
			   showvaguealert('请输入密码');
			   return ;
		    }
		    if(!(password.length>=6&&password.length<=18)){
			   showvaguealert('密码长度只能在6到18之间');
			   return ;
		    }
		  
		    $.ajax({
			   type: 'POST',
			   url: mainServer + "/weixin/checklogin",
			   async: true,
			   data:{
				   'phoneval':phoneval,
			    	'password':passwd
			    },
			    dataType:'json',  
			    success:function(data) { 
			        if(data.result =='success' ){
			        	showvaguealert(data.msg);
			        	//window.location.href = mainServer + "/weixin/tologin?backUrl="+backUrl; 
			        	if(backUrl!=null || backUrl!=''){
			        		window.location.href = backUrl;  
			        	}else{
			        		window.location.href = mainServer + "/weixin/toMyIndex";  
			        	}
			        	
			        }else{  
			        	showvaguealert(data.msg); 
			        	return;
			        }  
			     },  
			     error : function() {  	
			          alert("异常！");  
			     } 
		   });
	   }
	   
	   function toRegister(){
		   var backUrl = window.location.href;
		   if(backUrl.indexOf("backUrl")!=-1){
				backUrl = backUrl.substring(backUrl.indexOf("backUrl=")+8,backUrl.length);
				window.location.href = "${mainserver}/weixin/toRegister?backUrl="+backUrl;
		   }else{
				backUrl = "";
				window.location.href = "${mainserver}/weixin/toRegister";
		   }
	   }
	   
	   $('#check').click(function(){
			window.location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=hihuihuo&redirect_uri=http://192.168.40.225:8080/bettetlive-web/weixin/index&response_type=code&scope=snsapi_userinfo&state=128#wechat_redirect';
		}); 
	   
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},1000);
	    }
		
	</script>
</html>

