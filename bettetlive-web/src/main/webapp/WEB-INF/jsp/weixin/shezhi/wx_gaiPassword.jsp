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
		<title>挥货-修改密码</title>
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
		 		<span>绑定成功绑定成功</span>
		 </div>
		 <div class="itbox">
		 	<input type="password" placeholder="请输入旧密码" id="oldPwd" style="font-size: 18px" >
		 </div>
		 <div class="itbox" style="margin-top:0;   ">   
		 	<input type="password" placeholder="请输入新密码" id="newPwd" onblur="newPwdonblurs()" style="font-size: 18px"/>
		 </div>
		 <div class="itbox itboxno">
		 	<input type="password" placeholder="请再次输入新密码" id="affirmPwd" style="font-size: 18px"/>
		 </div>
		 <div class="bdbtnbox" id="sure">
		 	<a>确定</a>
		 </div>
		 
		 <div class="vaguealert">
			<p></p>
		</div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
	<script src="${resourcepath}/weixin/js/shezhi/wx_gaiPassword.js"></script>
</html>

