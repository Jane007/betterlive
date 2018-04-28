<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
  	<meta content="email=no" name="format-detection">
  	<meta name="keywords" content="挥货上新,挥货活动,挥货,限时活动,超值秒杀,挥货商城" /> 
	<meta name="description" content="挥货专题为您网罗上新、限时、超值好货活动" />
  	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
  	<link rel="stylesheet" href="${resourcepath}/weixin/css/zhuanti.css" />
  	<script src="${resourcepath}/weixin/js/rem.js"></script>
  	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
   	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<title>挥货-美食推荐</title>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		
   		
		var title = "挥货 - ${specialArticleVo.articleTitle}";  
		var desc = "${specialArticleVo.articleIntroduce}";
		var link = '${mainserver}/weixin/specialarticle/toSpecialArticle?articleId=${specialArticleVo.articleId}';
		var imgUrl = "${specialArticleVo.articleCover}";
		var customerId = "${customerId}";
		var specialId="${specialArticleVo.articleId}",
		
   		var _hmt = _hmt || [];
   		(function() {
   		  var hm = document.createElement("script");
   		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
   		  var s = document.getElementsByTagName("script")[0]; 
   		  s.parentNode.insertBefore(hm, s);
   		})();
   	</script>
</head>
<body style="padding-top:0.7rem; background:#eee;margin:0;"> 
	<div class="initloading"></div>
	
	<div class="zhuantifx" style="display:none; " onclick="hideShare()">
		<img src="${resourcepath}/weixin/img/fxbg.png" alt="" /> 
	</div>
	 
 		<div class="header" style="border-bottom:1px solid #ededed;font-size:0;line-height:0.81rem;text-align:center; ">
			<a style="font-size: 0.31rem;color:#333;  ">
				美食推荐详情  
			</a> 
			<span class="backPage"></span>
			<c:if test="${specialArticleVo.isCollection>0}">
				<span class="scsp scsp2" id="colId" onclick="addOrCancelCollection(1, ${specialArticleVo.articleId}, ${specialArticleVo.isCollection});"></span>
 			</c:if>
 			<c:if test="${specialArticleVo.isCollection<=0}">
	 			<span class="scsp" id="colId" onclick="addOrCancelCollection(0, ${specialArticleVo.articleId}, 0);"></span>
 			</c:if>
			<span class="fxsp" onclick="shareAlert()"></span>
		</div>	  
		  
		 <div style="background:#fff;">
		 	${specialArticleVo.content}  
		 </div> 
		 
		 <div class="plbotbox plxqbox" >
	    	<h3>全部评论(${specialArticleVo.commentCount})</h3>
	    	<div class="shafabg" style="display: none;">
	    		快来坐沙发吧
	    	</div>
	    	<div id="commentsId">
	    	</div>
	    </div>
		 
 		<div class="shipplbt">
 			<a id="addComment" href="javascript:checkLogin();">说点什么呗~</a>
 			
 			<c:if test="${specialArticleVo.isPraise>0}">
 				<span id="pid" class="current" onclick="addOrCancelPraise(1, ${specialArticleVo.articleId}, ${specialArticleVo.isPraise})"></span> 
 	 		</c:if>
 				<c:if test="${specialArticleVo.isPraise<=0}">
 				<span id="pid" onclick="addOrCancelPraise(0, ${specialArticleVo.articleId}, 0)"></span> 
 	 		</c:if>
 		</div>
 		
	     <div class="vaguealert">
			<p></p>
		 </div>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/special/wx_special_article?t=201802271511"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802271456"></script>
	
</body>
</html>