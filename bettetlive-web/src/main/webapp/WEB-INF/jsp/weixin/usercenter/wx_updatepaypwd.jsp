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
		<title>挥货-修改支付密码</title>
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
			<div class="mainBox" style="top:0rem;"> 
				<div class="contentBox">
						<form action="" class="verification" >
							<div class="m-passwordInput " id="verifinput">
								<label>
									<input type="password" pattern="/[0-9]*/" placeholder="请输入6位数旧支付密码"  class="inputArea" value="" id="oldPwd">
								</label>
							</div>
							<div class="nextStep" id="oldNext">
							         下一步
						    </div>
							  
						</form>
						
					
						<form action="" class="setting">
							<div class="m-passwordInput "> 
								<label>
									<input type="password" pattern="/[0-9]*/" placeholder="请输入6位数字支付密码"   class="inputArea" value="" id="newPwd">
									 
								</label>
							</div>
							<div class="nextStep" id="newNext">
							         下一步
						    </div>
						</form>
						<form action="" class="affirm"> 
							<div class="m-passwordInput " id="affirm">
								<label>
									<input type="password" pattern="/[0-9]*/" placeholder="请输再次输入密码确认" class="inputArea" value="" id="confirmPwd">
									  
								</label>
							</div>
							<div class="nextStep" id="sure">
							          确定修改
						    </div>
						</form>
						
					
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
	<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_updatepaypwd.js"></script>
</html>
