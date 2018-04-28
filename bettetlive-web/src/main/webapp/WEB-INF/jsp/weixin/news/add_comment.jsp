<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/myCoupon.css" />
	    <link rel="stylesheet" href="${resourcepath}/weixin/css/submitEvaluate.css" />
		
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-评价</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		
    		var title = "挥货 - 你的美食分享平台";  
    		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
    		var link = '${mainserver}/weixin/index';
    		var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
    		var myCommentId = "${myCommentId}";
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
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
 
			<div class="mainBox" style="top:0;">
			 
			 
				<form id="evalform"  action="${mainserver}/weixin/productcomment/replyComment"  method="post" enctype="multipart/form-data">
					<div class="textcon">
						<c:if test="${replyId != null && replyId != ''}">
							<input type="hidden" name="commentId"  id="commentId" value="${replyId}"/>
						</c:if>
						<c:if test="${replyId == null  || replyId == ''}">
							<input type="hidden" name="commentId"  id="commentId" value="${commentVo.comment_id}"/>
						</c:if>
						<input type="hidden" name="rootId" id="rootId"  value="${commentVo.comment_id}"/>
						
						<textarea name="textval" id="textval" name="content" maxlength="100" rows="" cols="" placeholder="说点什么呗~"></textarea>
						<div class="xianfont" style="border:none;"> <label id="lyishu" >0</label>/<label id="lsheng" >100</label></div>  
					</div>
				</form>	
			</div>
			<div class="submitBox">
				提交评论
			</div>    
		</div>
		<div class="mask"></div>
		<div class="vaguealert">
			<p></p>
		</div>
		
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/news/add_comment.js?t=201802271433"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
</html>	