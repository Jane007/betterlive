<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,个人中心,挥货商城"/> 
		<meta name="description" content="挥货商城,轻松赢奖励" />
		<title>轻松赢奖励</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/integral/wx_win_integral.css"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	</head>
	<body>
		<div id="layout">
			<div class="head-wrap">
				<div class="head">
					<div class="goback" onclick="location.href='${mainserver}/weixin/integral/toIntegral'"></div>
					<h3>轻松赢奖励</h3>
					<div class="help" onclick="location.href='${mainserver}/weixin/integral/toHelpeasy'">帮助</div>
				</div>
			</div>
			<div class="content">
				<div class="list-wrap">
				<h3 class="title">
					<c:if test="${remain != '0'}">
						主银，今天还有<span>${remain}</span>个金币未赚取哦~
					</c:if>
					<c:if test="${remain == '0'}">
						主银好棒噢，今天的奖励都赚完啦，明天再接再厉~
					</c:if>
				</h3>
					<ul class="list">
						<li>
							<dl>
								<dt>
									<img src="${resourcepath}/weixin/img/integral/easygold_01.png"/>
								</dt>
								<dd>
									<h3 class="ellipse">美食期刊分享</h3>
										<div class="limitof">		
											<c:if test="${articleRemain == '0'}">今日分享任务已完成啦<span></span></c:if>
											<c:if test="${ articleRemain != '0'}">
												今日最高还可以获得<span>${articleRemain}</span>个金币哦
											</c:if>
										</div> 
										<div class="btn <c:if test="${articleRemain == '0'}">off</c:if>" onclick="location.href='${mainserver}/weixin/discovery/toSelected'">
										<span>
											<c:if test="${articleRemain == '0'}">继续</c:if>
											<c:if test="${ articleRemain != '0'}">去完成</c:if>
										</span>
										</div>
								</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>
									<img src="${resourcepath}/weixin/img/integral/easygold_02.png"/>
								</dt>
								<dd>
									<h3 class="ellipse">发帖任务</h3>
										<div class="limitof">
											<c:if test="${dynamicRemain == '0'}">今日发帖任务已完成啦<span></span></c:if>
											<c:if test="${ dynamicRemain != '0'}">
												今日最高还可以获得<span>${dynamicRemain}</span>个金币哦
											</c:if>
										</div> 
										<div class="btn <c:if test="${dynamicRemain == '0'}">off</c:if>" onclick="location.href='${mainserver}/weixin/discovery/toPublishDynamic?backType=3'">
										<span>
											<c:if test="${dynamicRemain != '0'}">去完成</c:if>											
											<c:if test="${dynamicRemain == '0'}">继续</c:if>
										</span>
										</div>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
				<div class="list-wrap">
					<h3 class="title">赚取其它奖励</h3>
					<ul class="list">
						<li>
							<dl>
								<dt>
									<img src="${resourcepath}/weixin/img/integral/easygold_03.png"/>
								</dt>
								<dd>
									<h3 class="ellipse">邀请好友注册</h3>
									<div class="limitof">邀请越多，获奖越高哦</div> 
									<div class="btn" onclick="location.href='${mainserver}/weixin/customerinvite/gotoInvited?backUrl=${mainserver}/weixin/integral/toWinIntegral'">
										<span>去完成</span>
									</div>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>