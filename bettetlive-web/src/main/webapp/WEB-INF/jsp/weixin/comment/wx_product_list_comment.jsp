<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />
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
		<title>挥货-评价</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		
    		var productId = '${productId}';
    		var customerId = "${customerId}";
    		var type = "${type}";
    		var specialId="${specialId}";
    		var StringBuffer = Application.StringBuffer;
    		var buffer1 = new StringBuffer();
    		var buffer2 = new StringBuffer();
    		var divselect = 0;  //选择全部还是有图标识
    		var isInit = -1; //初始化状态为全部评论
    		var nextIndex = 1;
    		
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
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<ul class="titlelists" style="top: 0rem;">
				<li class="active">全部（${totalCount}）<p></p></li>
				<li id="some">有图（${totalImg}）<p></p></li>
			</ul>
			<div id="div1" class="messageBox" style="top: 0.7rem;">
				<div class="has-msgBox">
					
				</div>
				<div class="no-msgBox">
					<img src="${resourcepath}/weixin/img/no-msgBox.png" alt="">
					<p>还没有评论</p>
				</div>
			</div>
			<div id="div2" class="messageBox" style="top: 0.7rem;">
				<div class="has-msgBox">
					
				</div>
				<div class="no-msgBox">
					<img src="${resourcepath}/weixin/img/no-msgBox.png" alt="">
					<p>还没有评论</p>
				</div>
			</div>
			
			<div class="loadingmore">   
			    <img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
	        </div>
			<input type="hidden" name="pageNowAll" id="pageNowAll" value=""/>
		    <input type="hidden" name="pageNextAll" id="pageNextAll" value=""/>
			<input type="hidden" name="pageCountAll" id="pageCountAll" value=""/>
		    <input type="hidden" name="pageCountSome" id="pageCountSome" value=""/>
		    <input type="hidden" name="pageNowSome" id="pageNowSome value=""/>
		    <input type="hidden" name="pageNextSome" id="pageNextSome" value=""/>
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
	<script type="text/javascript" src="${resourcepath}/weixin/js/comment/wx_product_list_comment.js?t=201804161127"></script>
	

</body>
</html>
