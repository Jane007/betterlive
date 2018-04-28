<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801061746" />
  		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-礼品兑换</title>
		<script type="text/javascript">
			var myCustId = "${customerId}";
			var mainServer = "${mainserver}";
    		var _hmt = _hmt || [];
    		
			(function() { 
				  
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})(); 
    	</script>  
    	<style> 
    		body{background:#f9d7de url(${resourcepath}/weixin/img/cash/cash_bg.jpg) no-repeat center bottom;background-size: 100%;}
    		.zhaobox{width:94%;margin-left:3%; }  
    		.zhaobox input{width:96%; border:none; height:0.6rem;padding:0.15rem 2%;border-radius:0.1rem;margin-bottom:0.2rem;  }   
    		.guize{margin-top:1.8rem;margin-bottom:0.1rem}
			.guize img{width:100%;}
    		.duihuan{background:#e62d29;color:#fff;text-align:center;height:1rem; line-height:1rem; border-radius:0.1rem; font-size:0.29rem; }
    		.tishi{font-size:0.42rem;color:#333; margin-top:0.5rem; margin-bottom:0.3rem; } 
    		.toGoods{font-size:0.28rem;color:#333;width:98%;text-align:center;} 
    		.toGoods a{display:inline-block;color:#333;margin-top: 0.4rem;} 
    		.toGoods span{text-decoration:underline;color: red;} 
    		form{font-size:0;} 
    	</style>
	</head>
	<body class="zhaohd">
		 <div class="zhaobox">
			 <form action=""> 
			 <div class="tishi">请输入6位数的兑换码</div> 
			 <input type="text" id="codeNum" placeholder="请输入兑换码" maxlength="6"/>
			 <div class="duihuan" onclick="cashGift()">立即兑换</div>
			 <div class="toGoods">
		 	 	<a href="javascript:toMyOrder();">点击查看，<span>我的订单</span></a>
		 	 </div>
		 	 <div class="guize">
				<%--<img src="${resourcepath}/weixin/img/zhaohangduihuanjieshao.jpg" />--%>
				<img src="http://images.hlife.shop/zhdhsm20180202.jpg" />
			 </div>
			 </form>
		 </div>

		 <div class="vaguealert">
			<p></p>
		</div>
	</body>
	<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script> 
	
	<script type="text/javascript" src="${resourcepath}/weixin/js/cash/wx_cash_activity?t=201802281532"></script>
</html>
