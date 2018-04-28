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
		<title>挥货-账户信息</title>
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
			<div class="mainBox personMenu" style="top:0rem;">	 
						<c:if test="${null eq phone || '' eq phone}">
							<div class="all-row shezhi">
								<a href="${mainserver}/weixin/usercenter/toBoundPhone">
							    <p class="personMenuName">绑定手机</p> 
							    <ins class="moreIco"></ins>
								</a>
							</div>	
						</c:if>
						<c:if test="${null ne phone and '' ne phone}">
							<div class="all-row shezhi">
								<a href="${mainserver}/weixin/usercenter/toUpdateNewPhone">
							    <p class="personMenuName">修改手机 </p>
							    
							    <ins class="moreIco"></ins>
							    <strong>  ${phone}</strong>   
								</a>
							</div>	
						</c:if>	
					
					
					<div class="all-row shezhi">
						<a href="${mainserver}/weixin/usercenter/toUpdatepwd">
						    <p class="personMenuName">修改密码</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>	
					<div class="all-row shezhi">
						<a href="${mainserver}/weixin/usercenter/toAffirmpwd">
						    <p class="personMenuName">忘记密码</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>
				 
			</div>
			</div>	
			 
		</div>
		 
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
 	<script src="${resourcepath}/weixin/js/shezhi/wx_account.js"></script>
</html>

