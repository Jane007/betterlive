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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/editAccount.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-账号信息</title>
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
				<ul>
					<%-- <li>
						<c:if test="${null eq phone || '' eq phone}">
							<a href="${mainserver}/weixin/usercenter/toBoundPhone">
								<label >绑定手机号码</label>
								<span></span>
							</a>	
						</c:if>
						
						<c:if test="${null ne phone and '' ne phone}">
							<a href="${mainserver}/weixin/usercenter/toUpdateNewPhone">
								<label >更换手机号码</label>
								<span>${phone}</span>
							</a>
						</c:if>	
					</li> --%>
					<li>
						<a href="${mainserver}/weixin/usercenter/toUpdatePayPwd">
							<label >修改支付密码</label>
							<span></span>
						</a>
					</li>
					<li>
						<a href="${mainserver}/weixin/usercenter/fogetPayPwd">
							<label >忘记支付密码</label>
							<span></span>
						</a>
					</li>
				</ul>
				<!-- <div class="exitLogin">
					退出登录
				</div> -->
			</div>
		</div>
		<div class="mask"></div>
		<div class="dia-not-set">
			<p class="pay-success">您还没有设置支付密码</p>
			<p class="getbox">请先设置</p>
			<div class="key">
        		<a class="no">取消</a>
        		<a href="${mainserver}/weixin/presentcard/setPayPassword" class="yes">去设置</a>
        	</div>
		</div>
		<div class="dia-exit">
			<p class="whether">退出登录?</p>
			<div class="key">
        		<a class="no">取消</a>
        		<a href="${mainserver }/weixin/loginOut" class="yes">确定</a>
        	</div>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_account.js"></script>
</html>
