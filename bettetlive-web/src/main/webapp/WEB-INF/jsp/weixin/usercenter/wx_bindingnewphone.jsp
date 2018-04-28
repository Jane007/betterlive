<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/boundNewPhone.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-账号信息</title>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var sMobile = '${customer.mobile}'
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
			<div class="mainBox" style="top:0rem;">
   
				<div class="contentBox">
					<div class="identityBox">
						<form action="" class="verification" id="phoneForm" style="display: none;">
							<ul>
								<li class="phoneBox">
									<input type="text" class="numberbox" placeholder="请输入手机号码" value="${phone}"  readonly="readonly" id="phoneNum" maxlength="11" />
									<span id="getCode">获取验证码</span>
								</li>
								<li>
									<input type="text" placeholder="请输入验证码" name="msgCode" id="msgCode" maxlength="5"/>
								</li>
							</ul>
							<div class="nextStep" id="msgNext">
							        确定  
						 	</div>
						</form>
					
						<form action="" class="verification" style="display:none" id="newphone">
							<ul>
								<li class="phoneBox">
									<input type="text" placeholder="请输入新手机号码" class="numberbox" name="newphoneNum" id="newphoneNum"  maxlength="11" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" />
									<span id="getNewCode">获取验证码</span>
								</li>
								<li>
									<input type="text" placeholder="请输入验证码" name="msgCode" id="msgNewCode" maxlength="5"/>
								</li>
							</ul>
							<div class="nextStep" id="saveNewPhone" >
							         确定
						 	</div>
						</form>
					</div>
					
				</div>
			</div>
		</div>
		<div class="mask"></div>
		<div class="vaguealert">
			<p></p>
		</div>
		<div class="set-success">
			<img src="${resourcepath}/weixin/img/reset.png" alt="" />
			<p>设置成功</p>
			<a class="backlogin">
				好的
			</a>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_bindingnewphone.js"></script>
</html>
