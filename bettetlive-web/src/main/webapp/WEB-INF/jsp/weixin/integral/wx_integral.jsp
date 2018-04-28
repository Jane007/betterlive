<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,我的金币" /> 
		<meta name="description" content="挥货商城我的金币主页" />
		<title>我的金币</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/integral/wx_integral.css?t=1800009"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	</head>
	<script type="text/javascript">
		var mainServer = '${mainserver}';
		var customerId = '${customer.customer_id}';
		var resourcePath = '${resourcepath}';
		var accumulativeIntegral = '${customer.accumulativeIntegral}';
		var backUrl = "${backUrl}";

	</script>
	<body>
		<div id="layout">
			<div class="head-wrap">
				<div class="head">
					<div class="goback"></div>
					<h3>我的金币</h3>
					<div class="help" onclick="location.href='${mainserver}/weixin/integral/toHelp?backUrl=${mainserver}/weixin/integral/toIntegral'">帮助</div>
				</div>
			</div>
			<div class="header-wrap">
				<div class="header">
					<div class="user">
						<div class="text">
							<div class="gold">金币<fmt:formatNumber value="${customer.currentIntegral}" pattern="####.##" type="number" /></div>
							<div class="name">${systemLevel.levelName}</div>
						</div>
						<div class="medal" style="background: url(${systemLevel.imgUrl}) no-repeat right;background-size:1.66rem 2.27rem ;"
							onclick="location.href='${mainserver}/weixin/integral/helpHonor'"></div>
					</div>
					<div class="growup clearfix">
						<div class="growwidth fl"><span>成长值</span></div>
						<div class="graph-wrap fr">
							<div class="currentvalue">
								<div class="currbg">
									<img src="${resourcepath}/weixin/img/integral/head_04.png"/>
									<span>当前<fmt:formatNumber value="${customer.accumulativeIntegral}" pattern="####.##" type="number" /></span>
								</div>
							</div>
							<div class="grapph">
								<div class="measure"></div>
								<div class = "onspan"></div>
								<span></span>
								<span></span>
								<span></span>
								<span></span>
								<span></span>
								<span></span>
								<span></span>
								<span></span>
								<span></span>
							</div>
							<div class="textnum">
								<span>0</span>
								<span>50</span>
								<span>100</span>
								<span>1000</span>
								<span>5000</span>
								<span>10000</span>
								<span>30000</span>
								<span>60000</span>
								<span>100000</span>
							</div>
						</div>
					</div>
					<div class="link">
						<div class="box">
							<c:if test="${untreatedCount > 0}">
							<span class="show">${untreatedCount}</span>
							</c:if>
							<a href="togetAward"><img src="${resourcepath}/weixin/img/integral/head_06.png"/></a>
						</div>
						<div class="box" onclick="location.href='${mainserver}/weixin/productredeem/toGoldBuy'">
							<img src="${resourcepath}/weixin/img/integral/head_07.png"/>
						</div>

						<div class="box" onclick="location.href='${mainserver}/weixin/integral/toWinIntegral'">
							<img src="${resourcepath}/weixin/img/integral/head_08.png"/>
						</div>
					</div>
				</div>
			</div>
			<!--收支明细-->
			<div class="content">
				<div class="headline-wrap">
					<h3 class="headline">收支明细</h3>
					<ul class="list">
					
					</ul>
				</div>
				<!--没有数据-->
				<div class="nodata">
					<img src="${resourcepath}/weixin/img/integral/nodata.png"/>
					<span>哎呀，当前还没有收支明细哦</span>
				</div>
			</div>
			<!--上拉加载-->
			<div class="weui-loadmore">
  				<i class="weui-loading"></i>
  				<span class="weui-loadmore__tips">正在加载...</span>
			</div>
		</div>
		<script src="${resourcepath}/weixin/js/integral/wx_integral.js?t=201804191749"></script>
	</body>
</html>
