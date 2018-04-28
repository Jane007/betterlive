<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no" />
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta name="keywords" content="挥货上新,挥货活动,挥货,限时活动,超值秒杀,挥货商城" />
<meta name="description" content="挥货专题为您网罗上新、限时、超值好货活动" />

<link rel="stylesheet" type="text/css"
	href="${resourcepath}/weixin/css/result.css" />
<link rel="stylesheet" type="text/css"
	href="${resourcepath}/weixin/css/special/wx_special_pl.css" />
<script src="${resourcepath}/weixin/js/flexible.js"></script>
<script type="text/javascript"
	src="${resourcepath}/admin/js/application.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<title>挥货-视频</title>
<script type="text/javascript">
	var mainServer = '${mainserver}';
	var backUrl = '${backUrl}';
	var resourcepath = '${resourcepath}';
	var objId = '${specialVo.specialId}';
	var status = '${specialVo.status}';
	var title = "挥货 - ${specialVo.specialTitle}";
	var desc = "${specialVo.specialIntroduce}";
	var link = '${mainserver}/weixin/discovery/toTorialComment?specialId=${specialVo.specialId}';
	var customerId = "${customerId}";
	var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";

	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>
<body>
	<div class="zhuantifx" style="display:none; " onclick="hideShare()">
			<img src="${resourcepath}/weixin/img/fxbg.png" alt="" /> 
		</div>
	<div id="layout">
		<div class="content-wrap">
			<div class="content">
				<div class="title-wrap">
					<p class="title">${specialVo.specialTitle}</p>
				</div>
				<div class="video-wrap">
					<div class="video">
						<video controls='controls' src="${specialVo.specialPage}" width="100%" height="100%" name="video"></video>
						<div class="play-wrap" style="background:url(${specialVo.specialCover})">
							<div class="play"></div>
							<div class="videotime">${specialVo.objValue }</div>
						</div>
					</div>
					<div class="bot">
						<div class="time">2018.01.31</div>
						<div class="botfunc">
							<div class="sc on fl">
								<span>56</span>
							</div>
							<div class="msg fl">
								<span>56</span>
							</div>
							<div class="dz fl">
								<span>56</span>
							</div>
							<div class="icon fl">
								<span>56</span>
							</div>
						</div>
					</div>
				</div>
				<!--评论-->
				<div class="comment-wrap">
					<div class="comment">
						<div class="commenttitle">全部评论(3)</div>
						<ul class="list">
							<li>
								<dl>
									<dt>
										<div class="head fl">
											<img src="../img/head-dufult01.png" />
										</div>
										<div class="dtbot fl">
											<div class="name ellipse">张三</div>
											<div class="nametime">2017.10.11</div>
										</div>
									</dt>
									<dd>
										<div class="comdet">
											<p>包装很完美，包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美</p>
											<div class="pl">
												<div class="pllist">
													<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
												</div>
												<div class="pllist">
													回复<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
												</div>
												<div class="remain">查看6条剩余回复</div>
												<div class="nonepllist">
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
												</div>
											</div>
											<div class="dz">
												<div class="ondz opl">11</div>
												<div class="ondz odz">12</div>
											</div>
										</div>
									</dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>
										<div class="head fl">
											<img src="../img/head-dufult01.png" />
										</div>
										<div class="dtbot fl">
											<div class="name ellipse">张三</div>
											<div class="nametime">2017.10.11</div>
										</div>
									</dt>
									<dd>
										<div class="comdet">
											<p>包装很完美，包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美</p>
											<div class="pl">
												<div class="pllist">
													<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
												</div>
												<div class="pllist">
													回复<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
												</div>
												<div class="remain">查看6条剩余回复</div>
												<div class="nonepllist">
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
												</div>
											</div>
											<div class="dz">
												<div class="ondz opl">11</div>
												<div class="ondz odz">12</div>
											</div>
										</div>
									</dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>
										<div class="head fl">
											<img src="../img/head-dufult01.png" />
										</div>
										<div class="dtbot fl">
											<div class="name ellipse">张三</div>
											<div class="nametime">2017.10.11</div>
										</div>
									</dt>
									<dd>
										<div class="comdet">
											<p>包装很完美，包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美包装很完美</p>
											<div class="pl">
												<div class="pllist">
													<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
												</div>
												<div class="pllist">
													回复<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
												</div>
												<div class="remain">查看6条剩余回复</div>
												<div class="nonepllist">
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
													<div class="pllist">
														<span class="pname">立夏:</span>看起来很漂亮，在哪里可以买得到！
													</div>
												</div>
											</div>
											<div class="dz">
												<div class="ondz opl">11</div>
												<div class="ondz odz">12</div>
											</div>
										</div>
									</dd>
								</dl>
							</li>
						</ul>
					</div>
					<!--回复-->
					<div class="reply_wrap">
						<div class="reply">
							<input type="text" name="txt" id="txt" placeholder="回复主席:" />
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/special/wx_special_pl.js?t=201801391514"></script>
	<script
		src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>

</body>
</html>