<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>挥货-登录</title> 
<%@include file="common.jsp"%>
<link href="${resourcepath}/admin/css/login.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>

</head>
<body>
	<div class="login-hd">
		<div class="left-bg"></div>
		<div class="right-bg"></div>
		<div class="hd-inner">
			<span class="logo"></span>
			<!-- <span class="split"></span> -->
			<span class="sys-name" style="color:#fff;"></span>
		</div>
	</div>
	<div class="login-bd">
		<div class="bd-inner">
			<div class="inner-wrap">
				<div class="lg-zone">
					<div class="lg-box">
						<div class="lg-label"><h4>用户登录</h4></div>
						<div class="alert alert-error" id="alertError">
			              <i class="iconfont">&#xe62e;</i>
			              <span id="errorTip">请输入用户名</span>
			            </div>
						<form action="admin/dologin">
							<div class="lg-username input-item clearfix">
								<i class="iconfont">&#xe60d;</i>
								<input type="text" id="loginname" placeholder="账号/邮箱" value=""/>
							</div>
							<div class="lg-password input-item clearfix">
								<i class="iconfont">&#xe634;</i>
								<input type="password" id="password" placeholder="请输入密码" value=""/>
							</div>
							<div class="lg-check clearfix">
								<div class="input-item">
									<i class="iconfont">&#xe633;</i>
									<input type="text" id="code" placeholder="验证码" maxlength="4" value=""/>
								</div>
								<span class="check-code"><img src="${mainserver}/admin/safecode/getsafecode" style="cursor: pointer;" id="safecodeimg" onclick="changeimg()"></span>
							</div> 
							<div class="enter">
								<a href="javascript:;" class="purchaser" id="tologin" style="margin-left: 113px;">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
							</div>
						</form>
					</div>
				</div>
				<div class="lg-poster"></div>
			</div>
		</div>
	</div>
	<div class="login-ft">
		<div class="ft-inner">
			<div class="address">地址：广东省深圳市福田区金田路3038&nbsp;深圳市汇梦电子商务科技有限公司&nbsp;&nbsp;Copyright&nbsp;©&nbsp;2015&nbsp;-&nbsp;2016&nbsp;版权所有</div>
			<div class="other-info">建议使用IE8及以上版本浏览器&nbsp;苏ICP备&nbsp;09003055号&nbsp;E-mail：</div>
		</div>
	</div>
	
	<script type="text/javascript">
		function changeimg() {
	        var myimg = document.getElementById("safecodeimg");
	        now = new Date();
	        myimg.src = "${mainserver}/admin/safecode/getsafecode?code=" + now.getTime();
	    }
	   
	   $(function(){
		   location.href = "http://sys.hlife.shop/";
		   $("#alertError").hide();
		   $("#tologin").click(function(){
			   checklogin();
		   });
	   });
	   
	   $('#code').bind('keyup', function(event) {
			if (event.keyCode == "13") {
				checklogin();
			}
		}); 
	   
	   function checklogin(){
		   var loginname=$("#loginname").val();
		   var password=$("#password").val();
		   var code=$("#code").val();
		   //var passwd =password; 进行md5加密
		   var passwd = $.md5(password);
		   if(loginname == ''){
			   $("#alertError").show();
			   $("#errorTip").html("请输入用户名")
			   return ;
		   }
		   if(password == ''){
			   $("#alertError").show();
			   $("#errorTip").html("请输入密码");
			   return ;
		   }
		   if(code == ''){
			   $("#alertError").show();
			   $("#errorTip").html("请输入验证码");
			   return ;
		   } 
		   $.messager.progress(); 
		    $.ajax({
			   type: 'POST',
			   url: mainServer + "/admin/checklogin",
			   async: false,
			   data: {
			    	loginname:loginname,
			    	password:passwd,
			    	code:code
			    } ,
			    dataType:'json',  
			    success:function(data) { 
			    	$.messager.progress('close');
			        if(data.result =='success' ){  
			        	$("#password").val('');
			        	window.location.href = mainServer + "/admin/main";  	
			        	
			        }else{  
			        	$("#alertError").show();
			        	$("#errorTip").html(data.msg);  
			        	changeimg();
			        }  
			     },  
			     error : function() {  
			          alert("异常！");  
			     } 
		   });
	   }
	</script>
</body>
</html>