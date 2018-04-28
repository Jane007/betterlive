<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,金币优惠购" /> 
		<meta name="description" content="挥货商城金币优惠购" />
		<title>金币优惠购</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/integral/wx_goldbuy.css"/>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/common.js"></script>
		<script type="text/javascript">
			var mainServer = '${mainserver}';
			var resourcePath = '${resourcepath}';
		</script>
	</head>
	<body>
		<div id="layout">
			<c:if test="${bannerStatus == 1}">
			<div class="banner">
				<a href="${bannerDetailUrl}">
<%-- 					<img src="${resourcepath}/weixin/img/integral/goldbuy_01.png"/> --%>
					<img src="${bannerUrl}"/>
				</a>
			</div>
			</c:if>
			<!--收支明细-->
			<div class="content">
				<div class="headline-wrap">
					<h3 class="headline">${listTile}</h3>
					<ul class="list">
					</ul>
				</div>
				<!--上拉加载-->
				<div class="weui-loadmore">
	  				<i class="weui-loading"></i>
	  				<span class="weui-loadmore__tips">正在加载...</span>
				</div>
				<!--没有数据-->
				<div class="nodata">
					<img src="${resourcepath}/weixin/img/integral/goldbuy_04.png"/>
					<span>哎呀，当前还没有可兑换的物品哦</span>
					<div class="gomakemoney" onclick="location.href='${mainserver}/weixin/integral/toWinIntegral'">去赚金币</div> 
				</div>
			</div>
		</div>
		
		<script src="${resourcepath}/weixin/js/integral/wx_goldbuy.js"></script>
	</body>
</html>
