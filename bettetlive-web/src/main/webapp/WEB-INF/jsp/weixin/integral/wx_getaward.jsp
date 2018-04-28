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
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>领取奖励</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/integral/wx_getaward.css?t=201804100923"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		
		<script src="${resourcepath}/weixin/js/shareWechartForIntegral.js"></script>
	</head>
	<body>
		<div id="layout">
			<div class="head-wrap">
				<div class="head">
					<div class="goback"></div>
					<h3>领取奖励</h3>
					<div class="help" onclick="location.href='${mainserver}/weixin/integral/toHelp?backUrl=${mainserver}/weixin/integral/togetAward'">帮助</div>
				</div>
			</div>
			<div class="header-wrap">
				<div class="header">
					<div class="user clearfix">
						<div class="picture fl">
							<img src="${headUrl }"/>
						</div>
						<div class="text fl">
							<span class="present">当前金币</span>
							<span class="num" id="myIntegralId"><fmt:formatNumber value="${currentIntegral}" pattern="####.##" type="number" /></span>
						</div>
						<c:if test="${lotterySignStatus == 0}">
						<div id="lotteryId" class="btn <c:if test="${checkLottery > 0}">ogray</c:if> fr">
							<c:if test="${checkLottery > 0 && serialSign > 1}">已连续抽奖${serialSign}天</c:if>
							<c:if test="${checkLottery == 0 || serialSign <= 1}">今日<c:if test="${checkLottery > 0}">已</c:if>抽奖</c:if>
						</div>
						</c:if>
					</div>
					<div class="continuouslogin">
						<div class="connect"></div>
						<div class="grade-wrap">
							<c:forEach items="${treeMap}" var="record">
								<div class="grade">
									<c:if test="${record.value=='before' }">
										<img src="${resourcepath}/weixin/img/mygold/grade_02.png"/>
									</c:if>
									<c:if test="${record.value=='now' }">
										<img src="${resourcepath}/weixin/img/mygold/grade_01.png"/>
									</c:if>
									<c:if test="${record.value=='after' }">
										<img src="${resourcepath}/weixin/img/mygold/grade_03.png"/>
									</c:if>
								
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="timenumber">
						<span>周日</span>
						<span>周一</span>
						<span>周二</span>
						<span>周三</span>
						<span>周四</span>
						<span>周五</span>
						<span>周六</span>
						
						
					</div>
				</div>
			</div>
			<!--领取奖励列表-->
			<div class="content">
				<h3 class="otitle">领取奖励</h3>
				<ul class="list">
					
				</ul>
			</div>
			<!--提示框-->
			<div class="hint">
				<div class="hintext">
					<h3>恭喜您</h3>
					<h4>成功领取<span id="goldCount"></span>个金币</h4>
				</div>
			</div>
			<!--没有数据-->
			<div class="nodata">
				<img src="${resourcepath}/weixin/img/mygold/getaward_02.png"/>
				<span>哎呀，当前还没有可领取的奖励噢</span>
				<div class="gomakemoney" onclick="location.href='${mainserver}/weixin/integral/toWinIntegral'">去赚金币</div> 
			</div>
		</div>
		
		<!--今日抽奖-->
		<div class="lottery-wrap">
			<div class="box">
				<div class="box-top">
					<h3>请随机抽一个哦</h3>
					<div class="close"></div>
				</div>
				<div class="box-content">
					<ul class="lotterylist">
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
						<li><span></span></li>
					</ul>
				</div>
				<!--开奖-->
				<div class="onlottery-wrap">
					<div class="onlottery">
						<h4></h4>
					</div>
				</div>
			</div>
		</div>
		<!--分享按钮弹窗-->
		<div class="share">
			<div class="rule">
				<div class="ruletext">
					<h3>分享有奖</h3>
					<p>分享可获得大量金币哦，点击右上角分享至微信即可获得金币，快来体验一下吧~</p>
					<div class="check">查看规则</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		var mainServer = '${mainserver}';
		var resourcepath = '${resourcepath}';
	</script>
	<script src="${resourcepath}/weixin/js/integral/wx_getaward.js"></script>
	
</html>
