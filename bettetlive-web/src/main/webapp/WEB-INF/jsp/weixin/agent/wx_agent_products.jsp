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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/goodLists.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
   	 	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/common.js"></script>
		
		<title>挥货-商品列表</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var _hmt = _hmt || [];
    		(function() {
    		  var hm = document.createElement("script");
    		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
    		  var s = document.getElementsByTagName("script")[0]; 
    		  s.parentNode.insertBefore(hm, s);
    		})();
    		
    		var source = getQueryString("source");
    		var title = "挥货 - 你的美食分享平台";  
    		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
    		var link = '${mainserver}/weixin/agentproduct/productList?source='+source;
    		var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
    		
    	</script>
	</head>
	<body>
		<div class="initloading"></div>
		 <div id="showContent0" class="myordercent" style="margin-top:0rem;">
		 		<div class="evaluateBox tuijianboxs">
		 			<div id="banner0" class="lstbanner">
		 				<img src="${resourcepath}/weixin/img/goods/dfpro_banner.png" alt="" />
		 			</div>
					<div id="showProduct" class="tuijianbox">
					</div>		
					<div class="loadingmore">   
						<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
					</div>
				</div>	
		 </div>
		 <div class="vaguealert">
			<p></p>
		</div>
	 <input type="hidden" name="pageCount" id="pageCount" value="">
	 <input type="hidden" name="pageNow" id="pageNow" value="">
	 <input type="hidden" name="pageNext" id="pageNext" value="">
	</body>
	<script src="${resourcepath}/weixin/js/shareToWechart.js"></script>
	<script src="${resourcepath}/weixin/js/agent/wx_agent_products.js"></script> 
</html>
