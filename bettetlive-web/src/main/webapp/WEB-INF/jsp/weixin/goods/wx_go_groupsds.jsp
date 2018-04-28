<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection">
	<meta name="keywords" content="挥货,团购" /> 
	<meta name="description" content="挥货，你的美食分享平台" /> 
	<title>
		正在开团
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/goodsdetails.css" />
   	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<style type="text/css">
		.exampleBox {
			font-size: 0.25rem;
		}
		.exampleBox img {
			width: auto;
			max-width: 100%; 
		}
	</style>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		var title = '${specialVo.specialName}-挥货';
   		var productId = "${product.product_id}";
   		var customerId = "${customerId}";
		var specialId = "${specialVo.specialId}";
		var shareExplain = '${specialVo.specialIntroduce}';
		var desc = "吧唧零嘴、懒懒速食、锅里乱炖、清爽水果都在挥货星球~";
		if(shareExplain!=null && shareExplain!=""){
			desc = shareExplain;
		}
		
		var link = '${mainserver}/weixin/productgroup/toJoinGroups?specialId=${specialVo.specialId}&productId=${product.product_id}';
		var imgUrl = "${specialVo.specialCover}";
		
   		var _hmt = _hmt || [];
   		(function() {
   		  var hm = document.createElement("script");
   		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
   		  var s = document.getElementsByTagName("script")[0]; 
   		  s.parentNode.insertBefore(hm, s);
   		})();
   	</script>
</head>
<body style="background:#fff;"> 
	 <div class="initloading"></div>
		
	 <input type="hidden" id="limitCopy" value="${sysGroupVo.limitCopy}" readonly="readonly">
     <input type="hidden" id="tuanFlag" value="${tuanFlag}" readonly="readonly">
	 <div class="gotuanlist">
	 </div>
	 
	 
	 <div class="vaguealert">
		<p></p>
	 </div>
	 
    <div class="loadingmore">   
			<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
	</div>
	 <input type="hidden" name="pageCount" id="pageCount" value="">
	 <input type="hidden" name="pageNow" id="pageNow" value="">
	 <input type="hidden" name="pageNext" id="pageNext" value="">
 	 <script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
 	 <script src="${resourcepath}/weixin/js/common.js"></script> 
	
	 <script src="${resourcepath}/weixin/js/goods/wx_go_groupsds.js?t=201802261828"></script>
	 <script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>
	 
</body>
</html>