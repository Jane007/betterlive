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
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>邀请好友</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201801261436"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/invite/invite.css?t=201802231215"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script>
			var resourcepath='${resourcepath}';
			var mainserver='${mainserver}';
			var customerId='${customer.customer_id}';
			var backUrl = '${backUrl}';
			if(backUrl==''){
				backUrl = "${mainserver}/weixin/toMyIndex";
			}
	   		var _hmt = _hmt || [];
	   		(function() {
	   		  var hm = document.createElement("script");
	   		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
	   		  var s = document.getElementsByTagName("script")[0]; 
	   		  s.parentNode.insertBefore(hm, s);
	   		})();
	   	</script>
		<script src="${resourcepath}/weixin/js/invite/wx_invite_friend.js?t=201803261626"></script>
	</head>
	<body>
		<div id="layout">
			<div class="header-wrap">
				<div class="header">
					<div class="goback fl"></div>
					<div class="rule fr"></div>
				</div>
				<div class="banner">
				 	<img src="${resourcepath}/weixin/img/invite/banner.png"/>
				</div>
			</div>
			
			<!--主内容区-->
			<div class="content-wrap">
				<div class="content">
					<div class="box">
						<!--邀请理由-->
						<div class="reason">
							<h3>选择一个理由，成功机率更大哦~</h3>
							<ul id="invitedReason">
								
							</ul>
						</div>
					</div>
					<!--首次分享-->
					<div class="box">
						<h3>每日首次分享最高可获得10元优惠券</h3>
						<div class="discount">
							<div class="prce">
								￥ <span>10</span> 优惠券
							</div>
						</div>
					</div>
					<!--邀请越多-->
					<div class="box">
						<div class="bgbox">
							<h3>邀请越多得奖越多</h3>
							<div class="amount-wrap">
								<div class="hdamount">
									<div class="amount">
										<div class="reasonpeople">
											
										</div>
										<div class="people-bottom">
											<ul>
											
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--邀请达人榜-->
					<div class="box">
						<h3>邀请好友达人榜<span>(前十名)</span></h3>
						<div class="drtitle">
							<span>排行</span>
							<span>用户/奖励</span>
							<span>邀请好友</span>
						</div>
						<ul class="drlist">
							
						
						</ul>
					</div>
				</div>
			</div>
			<!--底部按钮-->
			<div class="footbtn">
				<div class="buttonwrap">
					<span>马上邀请</span>
				</div>
			</div>
			<!--遮罩层-->
			<div class="shade"></div>
			<!--点击规则-->
			<div class="clickrunl">
				<div class="runltext">
					<h3><span class="dd"></span><span class="gz">使用规则</span><span class="dd"></span></h3>
					<ul>
						<li>1.每天首次分享，可随机获得3~10元优惠券。</li>
						<li>2.用户成功邀请了1位、3位、5位、7位、10位新用户（新用户通过分享链接页面注册），即可获得对应优惠券。</li>
						<li>3.优惠券使用方法详见券的具体描述。</li>
						<li>4.分享链接出去后，统计时间为72小时，根据邀请的人数获得对应的券。</li>
						<li>5.同一登录账号，同一手机号，同一终端设备号，同一支付账户，同一收货地址，同一IP或其他合理显示同一的用户情形，均视为同一用户，不能重复享受新用户优惠。</li>
						<li>6、通过不正当手段获得的奖励，挥货有权撤销奖励及相关交易订单。</li>
					</ul>
					<div class="close">
						<img src="${resourcepath}/weixin/img/guizeOut.png"/>
					</div>
				</div>
			</div>
			<!--拆开优惠券-->
			<div class="separate">
				<div class="gain">
					<h3>恭喜您</h3>
					<h4></h4>
					<div class="gaintext">
						<span>(挥货商城全场通用)</span>
					</div>
				</div>
				<div class="sipa">
					<div class="oyes">
						好的
					</div>
					<h6>可到我的-优惠券里查看</h6>
				</div>
			</div>
		
		</div>
		  <div class="vaguealert">
			<p></p>
	     </div>
		<div id="cover"></div>
		<div id="guide"><img src="${resourcepath}/weixin/img/invite/guide1.png"></div>
	</body>
</html>
