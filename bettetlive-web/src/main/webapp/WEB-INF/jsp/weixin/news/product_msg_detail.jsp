<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection">
	<meta name="keywords" content="挥货,我的消息,评论消息" /> 
	<meta name="description" content="挥货商城每周新品首发为您推荐优质农产品，提升生活品质。" /> 
	<title>
		评论消息
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/result.css?t=201801032142" />
	<link rel="stylesheet" href="${resourcepath}/weixin/css/news/msg_details.css?t=201712291746" />
	<script src="${resourcepath}/weixin/js/flexible.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		var resourcepath = '${resourcepath}';

   		
   		var title = "挥货 - 你的美食分享平台";  
		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
		var link = '${mainserver}/weixin/message/toMessageList?msgType=4';
		var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";

   		var customerId = "${customerId}";

   		var _hmt = _hmt || [];
   		var rootId = "${commentVo.comment_id}";
   		var rootCustId = "${commentVo.customer_id}";
   		var parentId = "${commentVo.parent_id}";
   		var myPraiseId = "${commentVo.is_praise}";
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
	 <input type="hidden" id="pnickname" value="${commentVo.customerVo.nickname}">	
	 <input type="hidden" id="rootId" value="${commentVo.comment_id}">	
	 <div id="layout">
			<div class="content-wrap">
				<div class="content">
					<!--帖子-->
					<div class="invitation-wrap">
						<div class="invitation">
							<div class="invitation_top">
								<div class="topimg">
									<img src="${commentVo.customerVo.head_url}" />
								</div>
								<div class="lileft">
									<span>${commentVo.customerVo.nickname}</span><span>${commentVo.create_time}</span>
								</div>
							</div>
							<div class="invitation_bottom">
								<p>${commentVo.content}</p>
								<div class="uplike">
									<c:if test="${commentVo.is_praise > 0}">
										<div class="likemsg" onclick="addOrCancelPraise(1)" id="praiseId">
											<img id="praisePicId" src="${resourcepath}/weixin/img/news/dz02.png" />
											<span id="praiseCountId">${commentVo.praise_count}</span>
										</div>
									</c:if>
									<c:if test="${commentVo.is_praise <= 0}">
										<div class="likemsg" onclick="addOrCancelPraise(0)" id="praiseId">
											<img id="praisePicId" src="${resourcepath}/weixin/img/news/dz.png" />
											<span id="praiseCountId">${commentVo.praise_count}</span>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<!--评论对象-->
					<div class="details-wrap">
						<div class="msgdetails">
							<h3>${productVo.product_name}</h3>
							<!--图片-->
							<div class="msgimg">
								<img src="${productVo.product_logo}"/>
							</div>
							<!--视频
							<div class="msgvideo disnone">
								<video width="100%" height="100%">
									<source src="myvideo.mp4" type="video/mp4"></source>
								</video>
								<div class="paly"></div>
							</div>
							<div class="publish">
								发表于<span>9小时前</span>
							</div>
							-->
						</div>
					</div>
					<!--评论消息列表-->
					<div class="detailslist-wrap">
						<div class="detailslist">
							<div class="listtop">
								<h3>当前评论</h3>
							</div>
							<!--暂无评论提示-->
							<div class="shafabg">
		   						快来坐沙发吧
		   		   			 </div>
							<ul id="replysId">
							</ul>
							
							 <input type="hidden" name="pageCount" id="pageCount" value="">
							 <input type="hidden" name="pageNow" id="pageNow" value="">
							 <input type="hidden" name="pageNext" id="pageNext" value="">
					    	 <div class="loadingmore" style="bottom: 1.2777rem;">   
									<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
							 </div>
						</div>
					</div>
					
					<!--回复-->
					<div class="reply_wrap">
						<div class="reply">
							<input type="text" name="content" id="contentId" placeholder="回复：${commentVo.customerVo.nickname}" onchange="addReplyComment(this.value, 0)"/>
							<input type="hidden" name="parentId" id="parentId" />
						</div>
					</div>
				</div>
			</div>
	 </div>
	
     <div class="vaguealert">
		<p></p>
	 </div>
	 <script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	 <script src="${resourcepath}/weixin/js/common.js"></script> 
  	 <script src="${resourcepath}/weixin/js/news/product_msg_detail.js?t=201802261758"></script> 
	 <script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802271456"></script>
	
</body>
</html>