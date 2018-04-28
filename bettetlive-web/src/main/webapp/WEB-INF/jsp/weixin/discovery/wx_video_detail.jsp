<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta name="keywords" content="挥货上新,挥货活动,挥货,限时活动,超值秒杀,挥货商城" />
<meta name="description" content="挥货专题为您网罗上新、限时、超值好货活动" />

<link rel="stylesheet" type="text/css"href="${resourcepath}/weixin/css/result.css" />
<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
<link rel="stylesheet" type="text/css"href="${resourcepath}/weixin/css/discovery/wx_video_detail.css" />
<script src="${resourcepath}/weixin/js/flexible.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<title>挥货-视频</title>
</head>
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
						<div class="play-wrap" style="background:url(${specialVo.specialCover}) no-repeat;background-size: 100% 100%">
							<div class="play"></div>
							<div class="videotime">${specialVo.objValue }</div>
						</div>
					</div>
					<div class="bot">
						<div class="time">${specialVo.createTime }</div>
						<div class="botfunc">
							<div class="sc fl  ${specialVo.isCollection>0?'on':''}" data-collection-id="${specialVo.isCollection}" data-special-id="${specialVo.specialId}">
								<span>
			 	 					${specialVo.collectionCount}
	 	 						</span>
							</div>
							<div class="msg fl">
								<span>${specialVo.commentCount}</span>
							</div>
							<div class="dz fl ${specialVo.isPraise>0?'on':''}" data-special-id="${specialVo.specialId}" data-priase-id="${specialVo.isPraise}">
								<span>${specialVo.praiseCount}</span>
							</div>
							<div class="icon fl" onclick='shareAlert()'>
 	 							<span class="an04">${specialVo.shareCount}</span>
							</div>
						</div>
					</div>
				</div>
				<!--评论-->
				<div class="comment-wrap">
					<div class="comment">
						<div class="commenttitle">全部评论(<label id="totalCountId">${specialVo.commentCount}</label>)</div>
						<div class="shafabg" style="display: none;">
    						快来坐沙发吧
    					</div> 
						<ul class="list">
						
				
						</ul>
						<!--上拉加载-->
					<div class="weui-loadmore">
					<i class="weui-loading"></i>
						<span class="weui-loadmore__tips">正在加载</span>
					</div>
					</div>
					<!--回复-->
					<div class="reply_wrap">
						<div class="reply">
							<input type="text" name="content" id="contentId"" placeholder="期待你的神评论" onchange="addComment(this.value)"/>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	
	<script type="text/javascript">
		var mainServer = '${mainserver}';
		var backUrl = '${backUrl}';
		var resourcepath = '${resourcepath}';
		var objId = '${specialVo.specialId}';
		var customerId = "${customerId}";
		var status = '${specialVo.status}';
		var title = "挥货 - ${specialVo.specialTitle}";
		var desc = "${specialVo.specialIntroduce}";
		var link = mainServer + '/weixin/discovery/toTorialComment?specialId=${specialVo.specialId}&shareCustomerId=' + customerId;
		var imgUrl = "${specialVo.specialCover}";
		var commentCount = "${specialVo.commentCount}";
		if(backUrl==""){
			backUrl = mainServer+"/weixin/discovery/toVideo";
		}
		
		var shareCustomerId = 0;
		var checkUrl = window.location.href;
		if(checkUrl.indexOf("shareCustomerId")!=-1){
			shareCustomerId = getQueryString("shareCustomerId");
		}
		var _hmt = _hmt || [];
		(function() {
			var hm = document.createElement("script");
			hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(hm, s);
		})();
	</script>
	
	<script src="${resourcepath}/weixin/js/discovery/wx_video_detail.js?t=201802161514"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>

</body>
</html>