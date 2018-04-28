<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection"> 
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/contact_us.css?t=201802091655" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-联系客服</title>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var resourcePath = '${resourcepath}';
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
			<div class="header" style="border-bottom:1px solid #ededed; ">
			<a class="lxtitle"> 
				联系客服  
			</a>
			<a href="tel:4001869797" class="rengong">人工服务</a>  
			<span class="backPage" onclick="location.href='${mainserver}/weixin/toMyIndex'"></span>
			
			</div> 
			<div class="mainBox">
				<ul class="msglsists">
					<li class="service trouble">
						<div class="portrait"><img src="${resourcepath}/weixin/img/diahui.png" alt="" /></div>
						<div class="infos">
							<p>你好，请问有什么可以帮到您？ </p><p>请选择对应的问题咨询</p> 
							<div class="issueList">
								<div class="issue-order">
									<label for="" data="issue-order">订单问题？</label>
									<div class="child">
									</div>
								</div>
								<div class="issue-logistics">
									<label for="" data="issue-logistics">物流问题？</label>
									<div class="child">
									</div>
								</div>
								<div class="issue-sale">
									<label for="" data="issue-sale">售后问题？</label>
									<div class="child">
									</div>
								</div>
								<div class="issue-lianxi">
									<label for="" data="issue-lianxi">联系方式？</label> 
									<div class="child">
									</div>
								</div>
							</div> 
						</div>
						<span></span>
					</li>
				</ul>
			</div>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_contactus.js"></script>
</html>
