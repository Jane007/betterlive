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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/setPayPassword.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-设置支付密码</title>
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
			<div class="header">
				<a href="${mainserver}/weixin/index">
					<img src="${resourcepath}/weixin/img/huihuo-logo.png" alt="" />
				</a>
				<span class="backPage"></span>
				<a href="javascript:void(0)" class="shopCar">
				 <span class="totNums<c:if test="${cartCnt >0}"> totNums_sp</c:if>"><c:if test="${cartCnt >0}">${cartCnt }</c:if></span>
				</a>
			</div>
			<div class="mainBox">
				<div class="stepBox">
					<div class="steplist">
						<span class="active s1">1 验证身份</span>
						<span class="s2">2 设置密码</span>
						<span class="s3">3 确认密码</span>
					</div>
					<p><span></span></p>
				</div>
				<div class="contentBox">
					<form action="" class="verification">
						<ul>
							<li class="phoneBox">
								<input type="tel" pattern="/[0-9]*/" placeholder="请输入手机号码" name="phoneNum" id="phoneNum" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" maxlength="11"/>
								<span id="getCode">获取验证码</span>
							</li>
							<li>
								<input type="text" placeholder="请输入验证码" name="msgCode" id="msgCode" maxlength="6"/>
							</li>
						</ul>
						<div class="nextStep" id="msgNext">
							         下一步
						 </div>
					</form>
					<form action="" class="setting">
						<p class="form-title">请输入6位数字支付密码</p>
						<div class="m-passwordInput ">
							<label>
								<input type="tel" pattern="/[0-9]*/" class="inputArea" value="" id="pwd">
								<div class="blockWrapper">
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
								</div>
							</label>
						</div>
						<div class="nextStep" id="newNext">
							         下一步
						 </div>
					</form>
					<form action="" class="affirm">
						<p class="form-title">请再次输入支付密码</p>
						<div class="m-passwordInput " id="affirm">
							<label>
								<input type="tel" pattern="/[0-9]*/" class="inputArea" value="" id="pwdT">
								<div class="blockWrapper">
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
									<span class="block">
										<i></i>
									</span>
								</div>
							</label>
						</div>
						<div class="nextStep" id="sure">
							       确认修改
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="mask"></div>
		<div class="vaguealert">
			<p id="tishi"></p>
		</div>
		<div class="set-success">
			<img src="${resourcepath}/weixin/img/reset.png" alt="" />
			<p>设置成功</p>
			<p>你可以开始绑定礼品卡了</p>
			<a class="backlogin">
				好的
			</a>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_setpaypwd.js"></script>
</html>
