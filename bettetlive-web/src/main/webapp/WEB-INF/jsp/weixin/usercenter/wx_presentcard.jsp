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
		<style type="text/css">
			.mask {
			    width: 100%;
			    height: 100%;
			    background: rgba(0,0,0,0.5);
			    position: fixed;
			    z-index: 100;
			    top: 0;
			    display: none;
			}
		</style>
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
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="header">
				<a href="${mainserver}/weixin/index">
					<img src="${resourcepath}/weixin/img/huihuo-logo.png" alt="" />
				</a>
				<span class="backPage"></span>	
				<a href="javascript:void(0)" class="searchBox"></a>
				<a href="javascript:void(0)" class="shopCar">
				<span class="totNums<c:if test="${cartCnt >0}"> totNums_sp</c:if>"><c:if test="${cartCnt >0}">${cartCnt }</c:if></span>
				</a>
				<div class="search-frame">
					<span>
						<i></i>
						<input type="search" placeholder="请输入商品名称进行搜索" id="productName"/>
					</span>
					<em>取消</em>
				</div>
			</div>
			<div class="mainBox">
				<div class="GiftCardList">
					<p class="bot-git">礼品卡余额</p>
					<p class="bot-sum">¥${restMoney}</p>
					<a href="${mainserver }/weixin/usercenter/toAccount"><div class="Safety">账号安全</div></a>
					<a href="${mainserver }/weixin/presentcardrecord/findList" class="record">交易记录</a>
				</div>
				<div class="presentCardBox">
					<div class="presentCard-img">
						<img src="${resourcepath}/weixin/img/presentCard.png" alt="" />
					</div>
					<p>什么是礼品卡？</p>
				</div>
			</div>
			<div class="addpresentCard">
				添加礼品卡
			</div>
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
        		<a href="${mainserver }/weixin/presentcard/setPayPassword" class="yes">去设置</a>
        	</div>
		</div>
		<div class="dia-presentCard">
			<div class="example-img"><img src="${resourcepath}/weixin/img/precardshili.png" alt="" /></div>
			<p>请输入礼品卡密码</p>
			<div class="import-letter"><input type="text" id="pswd-input" placeholder="16位密码"/></div>
			<div class="dia-addCard">添加礼品卡</div>
			<div class="dia-cancel">取消</div>
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
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_presentcard.js"></script>
</html>
