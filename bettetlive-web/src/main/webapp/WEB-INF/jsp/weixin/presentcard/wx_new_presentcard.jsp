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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/presentCard.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-礼品卡</title>
 		
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var payPwd = '${payPwd}';
	   		var mobile = '${mobile}';
	   		
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
		 <div class="lipintop" style="margin-top:0rem;">
		 	<span>￥${restMoney}</span>
		 	<p>礼品卡余额(元)</p>
		 </div>
		 
		 <div class="personMenu">
					<div class="all-row">
						<a href="${mainserver}/weixin/presentcardrecord/setPresentCardReCord">
						    <p class="personMenuName redPac">交易记录</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row">
						<a href="${mainserver }/weixin/usercenter/toAccount">
						    <p class="personMenuName voucher">账户安全</p>
						   <ins class="moreIco"></ins>
						</a>
					</div>
					<div class="all-row">
						<a href="${mainserver}/weixin/presentcard/setPresentWhat">
						    <p class="personMenuName myShop">什么是礼品卡</p> 
						   <ins class="moreIco"></ins>
						</a>
					</div>
				 	
			</div>
			<div class="addpresentCard">
				添加礼品卡
			</div>
		<div class="mask"></div>
		<div class="vaguealert">
			<p id="tishi"></p>
		</div>
		<div class="dia-psd">
			<p class="pay-success">绑定礼品卡</p>
			<p class="getbox">请先设置支付密码</p>
			<div class="key">
        		<a class="no">取消</a>
        		<a href="${mainserver }/weixin/usercenter/fogetPayPwd" class="yes">去设置</a>
        	</div>
		</div>
		<div class="vaguealert">
			<p id="tishi"></p>
			</div>
		<div class="dia-presentCard">
			
		  	<div class="shepassdbox"  >
				<span>请输入礼品卡卡号</span>
				<input type="text" id="pswd-input" maxlength="19" placeholder="16位卡号" >
				<div class="qushan">
					<a class="left dia-cancel">取消</a>
					<a class="right dia-addCard">添加</a>
				</div>
			</div>   
			  
			 
		</div>
		<div class="dia-card-success">
			<img src="${resourcepath}/weixin/img/reset.png" alt="" />
			<p>礼品卡绑定成功</p>
			<a class="OK">
				好的
			</a>
		</div>
		<div class="mask"></div>
		<div style="display: none;">
			<form id="form2" action="${mainserver}/weixin/search" method="post">
				<input  id="searchName" name="productName" readonly="readonly">
			</form>
		</div>
					<%-- <div class="addkabox"> 
				<span class="addbox">添加礼品卡</span>
			</div>
			<!-- 弹出礼品卡密码 -->
			<div class="bkbg" style="display:none;"></div>
			<div class="shepassdbox" style="display:none;">
				<span>请输入礼品卡密码</span>
				<input type="password"  >
				<div class="qushan">
					<a class="left">取消</a>
					<a class="right ">添加</a>
				</div>
			</div> 
			<div class="shepassdbox" style="display:none;">
				<span class="getbox">请先设置支付密码</span>
				<input type="password"  >
				<div class="qushan">
					<a class="left">取消</a>
					<a href="${mainserver }/weixin/presentcard/setPayPassword" class="right">去设置</a>
				</div>
			</div>  --%>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/presentcard/wx_new_presentcard.js"></script>
</html>
