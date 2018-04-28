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
		<title>挥货-设置</title>
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
		 
		<div class="container">
			<div class="mainBox" style="top: 0rem;">	 
				<div class="personMenu" >
					<div class="all-row shezhi">
						<a href="${mainserver}/weixin/usercenter/toAccountUs">
						    <p class="personMenuName">账户信息</p> 
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row shezhi">
						<a href="${mainserver}/weixin/feedback/toFeedBackUs">
						    <p class="personMenuName">意见反馈</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>	
					<div class="all-row shezhi">
						<a href="${mainserver}/weixin/usercenter/toAboutUs">
						    <p class="personMenuName">关于我们</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row shezhi">
						<a href="${mainserver}/weixin/share/shareDownloadApp">
						    <p class="personMenuName">下载APP</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>	
					<!-- <div class="tuidl">
						<a>退出登录</a>
					</div> -->
			</div>
			</div>	
			 
		</div>
		 
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/shezhi/wx_shezhi.js"></script>
</html>

