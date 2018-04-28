<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201803071702" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/myOrder.css?t=201803071702" />		
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-我的订单</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var type = "${type}";
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
		 <div class="initloading"></div>
		 <div class="myordrnav">
		 	<ul>
		 		<li class="current" id="li0"><a href="javascript:void(0);">全部</a></li>
		 		<li id="li1"><a href="javascript:void(0);">待付款</a></li>
		 		<li id="li2"><a href="javascript:void(0);">待发货</a></li>
		 		<li id="li3"><a href="javascript:void(0);">待收货</a></li>
		 		<li id="li4"><a href="javascript:void(0);">待评价</a></li> 
		 	</ul>
		 </div>
		 <!--没有订单时的显示，去掉display:none即可见-->
		 <div class="ordernone" style="display:none;" onclick="location.href='${mainserver}/weixin/index'">
		 	<span >您还没有订单，去下一单试试吧</span>
		 	<a href="${mainserver}/weixin/index">去逛逛</a>
		 </div>
		 <div class="myordercent" id="myOrder0">  
		 </div>
		 <div class="myordercent" style="display:none;" id="myOrder1">
		 </div>
		 <div class="myordercent" style="display:none;" id="myOrder2">
		 </div>
		 <div class="myordercent" style="display:none;" id="myOrder3">
		 </div>
		 <div class="myordercent" style="display:none;" id="myOrder4">
		 </div>
		 <div class="loadingmore">   
			<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
		 </div>
		 <div class="vaguealert">
			<p></p>
		 </div>
		 
		 <input type="hidden" name="pageCount" id="pageCount" value="">
	    <input type="hidden" name="pageNow" id="pageNow" value="">
	    <input type="hidden" name="pageNext" id="pageNext" value="">
	</body>
 
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/countDown.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script> 
	<script src="${resourcepath}/weixin/js/order/wx_myorders.js?t=201804191448"></script>
</html>	