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
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/personalCenter.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-绑定手机</title>
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
		 <div class="tishibox">
		 		<!-- <span>绑定成功绑定成功</span> -->
		 </div>
		 <div class="itbox">
		 	<input type="text" placeholder="请输入手机号码" name="mobile"  id="mobile"  maxlength="11"/>
		 	<a  class="verificationcode">获取验证码</a>
		 </div>
		 <div class="itbox itboxno">
		 	<input type="text"placeholder="请输入验证码" name="verifycode"  id="verifycode"  maxlength="5"/>
		 </div>
		 <div class="bdbtnbox">
		 	<a class="now-complete now-bound" >确定</a>
		 </div>
		 
		 <div class="vaguealert">
			<p></p>
		</div>
		 
		 <div class="vaguealert">
			<p></p>
		 </div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
 	<script src="${resourcepath}/weixin/js/shezhi/wx_bindMobile.js"></script>
</html>

