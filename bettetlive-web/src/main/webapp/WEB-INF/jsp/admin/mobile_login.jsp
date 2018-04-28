<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/login.css" />
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
		 
		<script src="${resourcepath}/weixin/js/refresh.js"></script> 
		<div class="container">
		 
			<div class="mainBox">
				<div class="LOGO"><img src="${resourcepath}/weixin/img/xxlogo.png" alt="" /></div>  
				<form action="" class="login-form">
					<ul>
						<li class="">
							<input type="text" placeholder="请输入管理员账号" name="loginname"  id="loginname" style="color: #000;font-size: 16px"/>
						</li>
						<li>
							<input type="password" placeholder="请输入登录密码" id="password" style="color: #000;font-size: 16px"/>
						</li>
					</ul>
					<div class="now-login" onclick="checklogin();">登录</div>
					<%-- 
					<div class="other-fun">
						<a href="${mainserver}/weixin/toRegister" class="go-register">我要注册</a>
						<a href="${mainserver}/weixin/forgetPassword" class="forget-pass">忘记密码</a>
					</div>
					--%>
				</form>
				<%-- 
				<div class="other-login-type">
					<p><span id="check">微信登录</span></p> 
					<div class="weichat"></div>
				</div>
				--%>
			</div>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
	<script type="text/javascript">
	   function checklogin(){
		   var loginname=$("#loginname").val();
		   var password=$("#password").val();
		   var passwd = $.md5(password);
		   if(loginname == ''){
			   alert("请输入用户名");
			   return ;
		   }
		   if(password == ''){
			   alert("请输入密码");
			   return;
		   }
		   $.ajax({
			   type: 'POST',
			   url: mainServer + "/audit/checklogin",
			   async: false,
			   data: {
			    	loginname:loginname,
			    	password:passwd
			    } ,
			    dataType:'json',  
			    success:function(data) { 
			        if(data.result =='success'){
			        	$("#password").val('');
			        		window.location.href = mainServer + "/audit/auditArticle";
			        }else{  
			        	alert(data.msg);
			        }  
			     },  
			     error : function() {  
			          alert("异常！");  
			     } 
		   });
		   
	   }
	   
		
	</script>
</html>

