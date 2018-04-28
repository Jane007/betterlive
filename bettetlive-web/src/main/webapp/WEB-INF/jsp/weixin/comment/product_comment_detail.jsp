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
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-评价详情</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var myCustId = "${customerId}";
    		var customerId = "${customerId}";
    		 var uid = "${commentVo.customerVo.customer_id}";
    		 var commentId = "${commentVo.comment_id}";
    		 var specialId = "${specialId}";
    		 var productId = "${productVo.product_id}"
    		 var type = "${type}";
    		 var backUrl = window.location.href;
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
						${productVo.product_name}
					</span>
					<span class="detail">
					<c:if test="${productVo.share_explain != null && fn:length(productVo.share_explain) > 10}">
			 			${fn:substring(productVo.share_explain, 0, 10)}...
			 		</c:if>
		 			<c:if test="${productVo.share_explain != null && fn:length(productVo.share_explain) <= 10}">
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
	    	
   		    <div class="shafabg" style="display: none;">
   				快来坐沙发吧
   		    </div>
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
	    	<a href="javascript:checkLogin();">评论</a>
	    	<c:if test="${commentVo.is_praise > 0}">
				<a href="javascript:addOrCancelPraise(1);" id="praiseId" class="current">已赞</a>
			</c:if>
			<c:if test="${commentVo.is_praise <= 0}">
				<a href="javascript:addOrCancelPraise(0);" id="praiseId">点赞</a>
			</c:if>
	    </div>
	 <!--查看大图的背景以及按键-->
		<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
		    <div class="pswp__bg"></div>
		    <div class="pswp__scroll-wrap">
		        <div class="pswp__container">
		            <div class="pswp__item"></div>
		            <div class="pswp__item"></div>
		            <div class="pswp__item"></div>
		        </div>
		        <div class="pswp__ui pswp__ui--hidden">
		            <div class="pswp__top-bar">
		                <div class="pswp__counter"></div>
		                <div class="pswp__preloader">
		                    <div class="pswp__preloader__icn">
		                      <div class="pswp__preloader__cut">
		                        <div class="pswp__preloader__donut"></div>
		                      </div>
		                    </div>
		                </div>
		            </div>
		            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
		                <div class="pswp__share-tooltip"></div> 
		            </div>
		            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
		            </button>
		            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
		            </button>
		            <div class="pswp__caption">
		                <div class="pswp__caption__center"></div>
		            </div>
		        </div>
		    </div>
		</div>
		 
     <div class="vaguealert">
		<p></p>
	 </div>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/photoswipe.js"></script>
	<script src="${resourcepath}/weixin/js/photoswipe-ui-default.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/comment/product_comment_detail.js?t=201802281522"></script>
	
</body> 
</html>
