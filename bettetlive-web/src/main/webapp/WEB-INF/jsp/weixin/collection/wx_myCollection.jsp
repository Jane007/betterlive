<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>挥货-我的收藏</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/collection/wx_myCollection.css?t=201803011828"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<script src="${resourcepath}/weixin/js/sharelist.js"></script>	
		<script src="${resourcepath}/weixin/js/collection/wx_myCollection.js"></script>
	</head>
	<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var customerId = "${customerId}";
    		var integralSwitch = "${integralSwitch}";
    		var _hmt = _hmt || [];
    		(function() {
    		  var hm = document.createElement("script");
    		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
    		  var s = document.getElementsByTagName("script")[0]; 
    		  s.parentNode.insertBefore(hm, s);
    		})();
    	</script>
	<body>
		<div id="layout">
			<!--头部-->
			<div class="header-wrap">
				<div class="header">
					<div class="goback">
						<a></a>
					</div>
					<h3>我的收藏</h3>
					<div class="compile">
						编辑
					</div>
				</div>
			</div>
			<div class="swiper-container">
				<div class="my-pagination">
					<ul class="my-pagination-ul"></ul>
				</div>
				<div class="swiper-wrapper">
					<!--商品-->
					<div class="swiper-slide" data-delete="0">
						<div class="nodata"><span>暂无收藏</span></div>
						<ul class="prolist">
						<%-- <li>
									<dl>
										<dt>
											<img src="${resourcepath}/weixin/img/usercenter/pro-dufult.png"/>
										</dt>
										<dd>
											<div class="pro-title">
												<h3 class="ellipse">山东大红枣1.5kg</h3>
												<span>新品</span>
											</div>
											<div class="pro-explain ellipse">
												水晶般晶莹剔透夹带清香
											</div>
											<div class="pro-price">
												<div class="price">
													<span>￥89</span>
													<span>￥199</span>
												</div>
												<div class="data">
													月销售89份
												</div>
											</div>
										</dd>
								</dl>
							</li> --%>
						</ul>
						<!--上拉加载-->
						<div class="weui-loadmore">
						  	<i class="weui-loading"></i>
						  	<span class="weui-loadmore__tips">正在加载...</span>
						</div>
					</div>
					<!--精选-->
					<div class="swiper-slide" data-delete="1">
						<div class="nodata"><span>暂无收藏</span></div>
						<ul class="selection">
							<%-- <li>
								<dl>
									<dt>
										<div class="select"></div>
									</dt>
									<dd>
										<div class="selec-wrap">
											<div class="selec-top">
												<img src="${resourcepath}/weixin/img/discovery/selection-defult.png"/>
											</div>
											<div class="selec-bot">
												<p class="text">汇集清爽果汁，好喝好喝汇集清爽果汁，好喝好喝汇集清爽果汁，好喝好喝汇集清爽果汁，好喝好喝</p>
												<div class="operate">
													<div class="pl fl">
														<span>10</span>
													</div>
													<div class="dz fl">
														<span>20</span>
													</div>
												</div>
											</div>
										</div>
									</dd>
								</dl>
							</li> --%>
						</ul>
						<!--上拉加载-->
						<div class="weui-loadmore">
						  	<i class="weui-loading"></i>
						  	<span class="weui-loadmore__tips">正在加载...</span>
						</div>
					</div>
					<!--视屏-->
					<div class="swiper-slide" data-delete="2">
						<div class="nodata"><span>暂无收藏</span></div>
						<!--视频列表-->
						<ul class="videolist">
							<!-- <li>
								<div class="licontent">
									<div class="video">
										视频
										<video controls="controls" width="100%" height="100%" src=""></video>
										<div class="play-wrap">
											<div class="play"></div>
											<div class="videotime">
												03:36
											</div>
										</div>
									</div>
									<div class="bot">
										<h4>解忧的美食慰藉,冬日寻找温暖味道。解忧的美食慰藉,冬日寻找温暖味道。</h4>
										<div class="botfunc">
											<div class="sc on fl"><span>56</span></div>
											<div class="msg fl"><span>56</span></div>
											<div class="dz fl"><span>56</span></div>
											<div class="icon fl"><span>56</span></div>
										</div>
									</div>
								</div>
							</li> -->
						</ul>
						<!--上拉加载-->
						<div class="weui-loadmore">
						  	<i class="weui-loading"></i>
						  	<span class="weui-loadmore__tips">正在加载...</span>
						</div>
					</div>
				</div>
			</div>
			<!--取消收藏-->
			<div class="cancel">
				<span>取消收藏</span>
			</div>
		</div>
		<!--分享提示效果-->
		<div id="cover">
			<div id="guide"><img src="${resourcepath}/weixin/img/invite/guide1.png"></div>
		</div>
		<!--分享按钮弹窗-->
		<div class="share" onclick="hideShare();">
			<div class="rule">
				<div class="ruletext">
					<h3>分享有奖</h3>
					<p>分享可获得大量金币哦，点击右上角分享至微信即可获得金币，快来体验一下吧~</p>
					<div class="check" onclick="toHelp();">查看规则</div>
				</div>
			</div>
		</div>
		<!--分享成功弹窗提示-->
		<div class="shate-succeed">
			<div class="hold-bg">
				<div class="hold">
					<h3>恭喜你</h3>
					<p>获得了<span>3</span>个金币</p>
				</div>
			</div>
		</div>
	</body>
</html>
