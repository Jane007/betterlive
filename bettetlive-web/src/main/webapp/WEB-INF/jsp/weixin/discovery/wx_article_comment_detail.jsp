<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/discuss.css?t=201802241711">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/default-skin/default-skin.css?t=201711072204">
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
   	   	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
   	   	<script src="${resourcepath}/weixin/js/rem.js"></script>
   	   	
		<title>评论详情</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var title = "挥货 - 发现";  
			var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
			var link = '${mainserver}/weixin/discovery/toCommentDetail?articleId=${commentVo.specialId}&commentId=${commentVo.commentId}&commentPraiseType=5';
			var theUrl = link;
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
			var customerId = "${customerId}";
			var myCustId = "${customerId}";
			var specialId = "${commentVo.specialId}";
			var commentId = "${commentVo.commentId}";
			var backUrl = "${backUrl}";
    		var _hmt = _hmt || [];
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
		
		<input id="hasPraiseId" type="hidden" value="${commentVo.isPraise}">
		<input id="cmmCustId" type="hidden" value="${commentVo.customerVo.customer_id}">
		<div class="pltopboxs">
			<div class="pltop">
				<span class="pltouxiang" onclick="toSocialityHome(${commentVo.customerVo.customer_id})"><img src="${commentVo.customerVo.head_url}" alt=""></span>
				<span class="plname" onclick="toSocialityHome(${commentVo.customerVo.customer_id})">${commentVo.customerVo.nickname}</span>
				<span class="pltime">${commentVo.createTime} </span>
			</div>
			<div class="plcent" style="padding:0;border:none;">
				${commentVo.content}
			</div>
			<div class="plbotbox">
				<c:if test="${commentVo.isPraise > 0}">
					<span class="dz on" data-priase-id="${commentVo.isPraise }" data-comment-id="${commentVo.commentId }">${commentVo.praiseCount}</span>
				</c:if>
				<c:if test="${commentVo.isPraise <= 0}">
					<span class="dz" data-priase-id="${commentVo.isPraise }" data-comment-id="${commentVo.commentId }">${commentVo.praiseCount}</span>
				</c:if>
			</div>
		</div>
	    <div class="plbotbox plxqbox"  >
	    	<h3>全部评论(<label id="totalCountId">${commentVo.replyCount}</label>)</h3>
	    	
   		    <div class="shafabg" style="display: none;">
   				快来坐沙发吧
   		    </div>
	    	<div id="replysId">
	    	</div>
	    		<!--上拉加载-->
			<div class="weui-loadmore">
			<i class="weui-loading"></i>
				<span class="weui-loadmore__tips">正在加载</span>
			</div>
	    </div>
	    <div class="plbotboxsgai"> 
	    	<input type="text" name="content" id="contentId" placeholder="期待你的神评论~" onchange="addComment(this.value)"/> 
	    </div> 
		  
     <div class="vaguealert">
		<p></p>
	 </div>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	<script src="${resourcepath}/weixin/js/discovery/wx_article_comment_detail.js?t=201802241620"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802241558"></script>
</body> 
</html>
