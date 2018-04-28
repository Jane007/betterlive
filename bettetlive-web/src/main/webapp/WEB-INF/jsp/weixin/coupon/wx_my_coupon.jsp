<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no" />
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801262200" />
<link rel="stylesheet" href="${resourcepath}/weixin/css/myCoupon.css?t=201801262200" />

<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
<script type="text/javascript"
	src="${resourcepath}/admin/js/application.js"></script>
<title>挥货-我的优惠券</title>
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
	<div class="initloading"></div>
	 
	<div class="myordrnav">
		<ul>
			<li class="current"><a href="javascript:void(0);">待使用</a></li>
			<li><a href="javascript:void(0);">已使用</a></li>
			<li><a href="javascript:void(0);">已过期</a></li>
		</ul>
	</div>

	<!-- 没有优惠券时显示的DIV -->
	<div class="noyhbg" style="display: none;">
		<span>您还没有优惠券哦~</span>
	</div>

	<div class="myordercent" id="myordercent0" style="background: none;">
	</div>
	<div class="myordercent" style="display: none;background: none;" id="myordercent1">
	</div>
	<div class="myordercent" style="display: none;background: none;" id="myordercent2">
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
</body>

<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
<script src="${resourcepath}/weixin/js/common.js?t=201801262200"></script>
<script src="${resourcepath}/weixin/js/coupon/wx_my_coupon.js"></script>
</html>
