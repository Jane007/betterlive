<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201801070359"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/advert/wx_advert_gift.css?t=2018004131758"/>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
  		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/common.js"></script>
		<title>挥货-星迷幸运礼</title>
		<script type="text/javascript">
		
			var ordSource = "";
			var theUrl = window.location.href;
			if(theUrl.indexOf("source")!=-1){
				ordSource = getQueryString("source");
				ordSource = "?source=" + ordSource;
			}
    		var _hmt = _hmt || [];
    		var title = "挥货- 星迷幸运礼";   
    		var desc = "星迷幸运礼送您大红包";
    		var link = '${mainserver}/weixin/advert/toAdvertGift' + ordSource;
    		var imgUrl = "${mainserver}/weixin/img/advert/jml.png";
    		var mainServer  = '${mainserver}';
			(function() { 
				  
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})(); 
    	</script>  
	</head>
	<body>
		<img src="${resourcepath}/weixin/img/advert/body-bj.png" alt="" />
		
		
		<div id="layout">
			<%--下载App--%>
			<div class="dowbox-wrap dow">
				<div class="dowbox">
					<div class="shut dow">
						<img src="${resourcepath}/weixin/img/xx.png"/>
					</div>
					<div class="activitytext">
						<h3>挥货APP豪礼送不停！</h3>
						<p>多款美食限量抢购，低至1元！</p>
					</div>
					<div class="dowbtn"><a href="${mainserver}/weixin/share/shareDownloadApp">点击下载</a></div>
				</div>
			</div>
			<!--标题图片-->
			<div class = title-tp>
				<div class = tp>
					<img src="${resourcepath}/weixin/img/advert/jml-06.png" alt="" />
				</div>
				<div class = tpti>
					<img src="${resourcepath}/weixin/img/advert/jml-03.png" alt="" />
				</div>
			</div>
			<!--券-->
			<div class="content-wrap">
				<div class="content">
					<ul class="list">
						<li>
							<h3>￥8.00</h3>
							<h4>无门槛券</h4>
						</li>
						<li>
							<h3>￥10.00</h3>
							<h4>满99可用</h4>
						</li>
						<li>
							<h3>￥30.00</h3>
							<h4>满299可用</h4>
						</li>
						<li>
							<h3>￥50.00</h3>
							<h4>满499可用</h4>
						</li>
						<li>
							<h3>￥80.00</h3>
							<h4>满799可用</h4>
						</li>
						<li>
							<h3>￥100.00</h3>
							<h4>满999可用</h4>
						</li>
					</ul>
					<!--点击解码-->
					<div class="decode">
						<a href="/weixin/advert/receiveMultiCoupon">点击解码</a>
					</div>
					<!--活动规则-->
					<div class="rule">
						<img src="${resourcepath}/weixin/img/advert/jml-02.png"/>
					</div>
				</div>
			</div>
			<!--遮罩层-->
			<div class="shad"></div>
			<!--红包-->
			<div class="redpacket" id="successed">
				<h3>成功解码</h3>
				<h4>获<span>278</span>元好礼</h4>
			</div>

			<div class="redpacket" id="hasPacket">
				<h3>你已经来过了~</h3>
				<h4>快去使用吧</h4>
			</div>
		</div>


		<div class="shepassdboxs" style="display: none;">
			<span>你还没有登录账号哦</span>  
			<div class="qushan">
				<a class="left" href="javascript:closeAlert();">取消</a>
				<a class="right" id="cancelId" href="javascript:void(0);">登录/注册</a> 
			</div>
		</div>

		<div class="vaguealert">
			<p></p>
		</div>
	</body>

	<script type="text/javascript" src="${resourcepath}/weixin/js/advert/wx_advert_git.js?t=2018004131758"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
</html>
