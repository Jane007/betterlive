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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/discuss.css">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/photoswipe.css">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/default-skin/default-skin.css">
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-评价详情</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		
    		var title = "挥货 - 你的美食分享平台";  
			var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
			var link = '${mainserver}/weixin/index';
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
			
			 var myCustId = "${customerId}";
			 var uid = "${commentVo.customerVo.customer_id}";
			 var commnetId = "${commentVo.comment_id}";
			 var myCommentId = "${myCommentId}";
			 var commentId = "${commentVo.comment_id}";
			
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
			
		<input id="hasPraiseId" type="hidden" value="${commentVo.is_praise}">
		<div class="pltopboxs">
			<div class="pltop">
				<span class="pltouxiang"><img src="${commentVo.customerVo.head_url}" alt=""></span>
				<span class="plname">${commentVo.customerVo.nickname}</span>
				<span class="pltime">${commentVo.create_time} </span>
			</div>
			<div class="plcent" style="padding:0;border:none;">
				${commentVo.content}
			</div>
			<c:if test="${fn:length(commentVo.commentArrayImgs) > 0}">
			<div class="mesPic"> 
				 <c:forEach items="${commentVo.commentArrayImgs}" var="imgs" varStatus="idx">
	    			<div class="my-gallery"  data-pswp-uid="1">    
		    			 <figure > 
		    				 <a href="${imgs}"    data-size="1024x1024">
		    				<img src="${imgs}"    >
		    				</a>
		    			</figure> 
	    			 </div> 
	   			 </c:forEach>
		    </div>
		    </c:if>
			<div class="cpnames" style="padding-top:0.2rem;">
				${commentProductDesc}
			</div>
			<div class="plproduct">
				<div class="plproleft"> 
					<img src="${productVo.product_logo}" alt="">
				</div>
				<div class="plproright">
					<span class="name">
					<c:if test="${productVo.product_name != null && fn:length(productVo.product_name) > 10}">
						${fn:substring(productVo.product_name, 0, 10)}...
					</c:if>
					<c:if test="${productVo.product_name != null && fn:length(productVo.product_name) <= 10}">
			 			${productVo.product_name}
			 		</c:if>
					</span>
					<span class="detail">
					<c:if test="${productVo.share_explain != null && fn:length(productVo.share_explain) > 15}">
			 			${fn:substring(productVo.share_explain, 0, 15)}...
			 		</c:if>
		 			<c:if test="${productVo.share_explain != null && fn:length(productVo.share_explain) <= 15}">
			 			${productVo.share_explain}
			 		</c:if>
					</span>
					<span class="money">
						<c:choose>
							<c:when test="${productVo.activityPrice != null && productVo.activityPrice != '' && productVo.activityPrice != '-1'}">
								${productVo.activityPrice}<strong>￥${productVo.price}</strong>
							</c:when>
							<c:when test="${productVo.discountPrice != null && productVo.discountPrice != '' && productVo.discountPrice != '-1'}">
								${productVo.discountPrice}<strong>￥${productVo.price}</strong>
							</c:when>
							<c:otherwise>
								${productVo.price}
							</c:otherwise>
						</c:choose>
					</span>
				</div>
			</div>
		</div>
	    <div class="plbotbox plxqbox"  >
	    	<h3>全部评论(${commentVo.reply_count})</h3>
	    	<div id="replysId">
	    	
	    	</div>
    		 <input type="hidden" name="pageCount" id="pageCount" value="">
			 <input type="hidden" name="pageNow" id="pageNow" value="">
			 <input type="hidden" name="pageNext" id="pageNext" value="">
	    	 <div class="loadingmore">   
					<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
			</div>
	    </div>
	    <div class="plbotboxs"> 
	    	<a href="${mainserver}/weixin/productcomment/toAddCommentByMsg?commentId=${commentVo.comment_id}&myCommentId=${myCommentId}">评论</a>
	    	<c:if test="${commentVo.is_praise > 0}">
				<a href="javascript:addOrCancelPraise(1);" id="praiseId" class="current">已赞</a>
			</c:if>
			<c:if test="${commentVo.is_praise <= 0}">
				<a href="javascript:addOrCancelPraise(0);" id="praiseId">点赞</a>
			</c:if>
	    </div>
	    
     <div class="vaguealert">
		<p></p>
	 </div>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/photoswipe.js"></script>
	<script src="${resourcepath}/weixin/js/photoswipe-ui-default.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/news/commentMessageDetail.js?t=201802271446"></script>
	 <script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
</body> 
</html>
