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
  	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201711072204" />
  	<link rel="stylesheet" href="${resourcepath}/weixin/css/zhuanti.css?t=201711072204" />
	<link rel="stylesheet" href="${resourcepath}/weixin/css/faxian.css?t=201804081553" /> 
  	<script src="${resourcepath}/weixin/js/rem.js"></script>
  	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
   	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<title>挥货-文章详情</title>
</head>
<body style="padding-top:0.7rem; background:#eee;margin:0;"> 
	<div class="initloading"></div>
	<div class="bkbg" style="display: none;"></div> 
	<div class="shepassdboxs" style="display: none;">
			<span>确定要取消关注吗</span>  
			<div class="qushan">
				<a class="left" href="javascript:closeConcernAlert();">放弃</a>
				<a class="right" id="cancelId" href="javascript:void(0);">确定</a> 
			</div>
	</div>
	<%-- 
	<div class="zhuantifx" style="display:none; " onclick="hideShare()">
		<img src="${resourcepath}/weixin/img/fxbg.png" alt="" /> 
	</div> --%>
	 
 		<div class="header" style="border-bottom:1px solid #ededed;font-size:0;line-height:0.81rem;text-align:center; ">
			<a style="font-size: 0.31rem;color:#fff;  ">
				文章详情  
			</a> 
			<span class="backPage"></span>
			<c:if test="${specialArticleVo.isCollection>0}">
				<span class="scsp scsp2" id="colId" onclick="addOrCancelCollection(1, ${specialArticleVo.articleId}, ${specialArticleVo.isCollection});"></span>
 			</c:if>
 			<c:if test="${specialArticleVo.isCollection<=0}">
	 			<span class="scsp" id="colId" onclick="addOrCancelCollection(0, ${specialArticleVo.articleId}, 0);"></span>
 			</c:if>
 			<c:if test="${integralSwitch != null && integralSwitch == 0 }">
 			</c:if>
			<c:choose>
				<c:when test="${integralSwitch != null && integralSwitch == 0 }">
					<span class="fxsp" onclick="shareAlert()"></span>
					<div class = "gold">
						<img src = "${resourcepath}/weixin/img/discovery/share_03.png" />
					</div>
					<div class = "shareimg">
						<img src = "${resourcepath}/weixin/img/discovery/share_06.png" />
					</div>
				</c:when>
				<c:otherwise>
					<span class="fxsp"></span>
				</c:otherwise>
			</c:choose>
		</div>	  
		  
		 <div  style="background:#fff;padding:0 0.26rem; ">
		 	<div class="dttitlebox"> 
		 		${specialArticleVo.articleTitle}
		 	</div>
		 	<c:if test="${specialArticleVo != null && specialArticleVo.customerId > 0}">
			 	<div class="titiebtguan">
			 		<span onclick="toSocialityHome(${specialArticleVo.customerId})"><img src="${specialArticleVo.headUrl}" alt="" /> </span>
			 		<p onclick="toSocialityHome(${specialArticleVo.customerId})">${specialArticleVo.author}</p>
			 		<c:choose>
		 				<c:when test="${customerId <= 0}">
			 				<a id="guanzhuId" href="javascript:concernOrCancel(0);">+ 关注</a>
		 				</c:when>
	 					<c:when test="${(customerId != specialArticleVo.customerId) && (concernedVo == null || concernedVo.fansId <= 0)}">
			 				<a id="guanzhuId" href="javascript:concernOrCancel(0);">+ 关注</a>
		 				</c:when>
			 			<c:when test="${customerId != specialArticleVo.customerId && concernedVo != null && concernedVo.fansId > 0}">
			 				<a id="guanzhuId" class="current" href="javascript:concernOrCancel(${concernedVo.fansId});">已关注</a>
	 					</c:when>
			 		</c:choose>
			 	</div>
		 	</c:if>
		 	<div style="font-size:0.26rem;color:#333; margin-bottom:0.3rem;">${specialArticleVo.content}</div>  
		 </div> 
		 
		 <c:if test="${linkPros.size() > 0}">
		     <div class="evaluateBox tuijianboxs">
				 <div class="pltitle">
				 	<span>相关产品</span> 
				 </div>  
				 <div id="productIds" class="tuijianbox">
				 <c:forEach var="proVo" items="${linkPros}" varStatus="status">
				 	<c:choose>
						<c:when test="${proVo.activityType == 2}">
							<div class="tuijian" onclick="location.href='${mainserver}/weixin/product/toLimitGoodsdetails?productId=${proVo.product_id}&specialId=${proVo.activity_id}'"> 
						</c:when>
						<c:when test="${proVo.activityType == 3}">
							<div class="tuijian" onclick="location.href='${mainserver}/weixin/product/toGroupGoodsdetails?specialId=${proVo.activity_id}&productId=${proVo.product_id}'"> 
						</c:when>
						<c:otherwise>
							<div class="tuijian" onclick="location.href='${mainserver}/weixin/product/towxgoodsdetails?productId=${proVo.product_id}'"> 
						</c:otherwise>
					</c:choose>
				 		<div class="left">
							<img src="${proVo.product_logo}" alt="" />
						</div>
						<div class="right">
							<div class="tjname">
								<span>${proVo.product_name}</span>
								<c:if test="${proVo.labelName != null && proVo.labelName != ''}">
									<p>${proVo.labelName}</p>
								</c:if>
							</div>
							<div class="tjcent">
								<c:if test="${proVo.labelName != null && proVo.labelName != ''}">
									${proVo.share_explain}
								</c:if>
							</div>
							<div class="tjmoney">
									<c:choose>
										<c:when test="${proVo.activityPrice != null && proVo.activityPrice !='' && proVo.activityPrice != '-1'}">
											<span>￥${proVo.activityPrice}
										 	<strong>￥${proVo.price}</strong></span>
										</c:when>
										<c:when test="${proVo.discountPrice != null && proVo.discountPrice != '' && proVo.discountPrice != '-1'}">
											<span>￥${proVo.discountPrice}
										 	<strong>￥${proVo.price}</strong></span>
										</c:when>
										<c:otherwise>
											<span>￥${proVo.price}</span>
										</c:otherwise>
									</c:choose>
									<p>销量${proVo.salesVolume}份</p>
								</div>
							</div>
						</div>
					</c:forEach>
				 </div>		
			</div>	
	     </c:if>
		 
		 <div class="plbotbox plxqbox" id="comments">
	    	<h3>全部评论(${specialArticleVo.commentCount})</h3>
	    	<div class="shafabg" style="display: none;">
	    		快来坐沙发吧
	    	</div>
	    	<div id="commentsId">
	    	</div>
	    	
	    	 <input type="hidden" name="pageCount" id="pageCount" value="">
			 <input type="hidden" name="pageNow" id="pageNow" value="">
			 <input type="hidden" name="pageNext" id="pageNext" value="">
			 <div class="loadingmore" style="bottom:0.92rem;">   
				<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
			 </div>
	    </div>
		 
 		<div class="shipplbt dtbotpldz">
 			<a id="addComment" href="javascript:checkLogin();">评论(${specialArticleVo.commentCount})</a>
 			
 			<c:if test="${specialArticleVo.isPraise>0}">  
 				<span id="pid" class="currents" onclick="addOrCancelPraise(1, ${specialArticleVo.articleId}, ${specialArticleVo.isPraise})">点赞(<label id="praiseCountId">${specialArticleVo.praiseCount}</label>)</span> 
 	 		</c:if>  
			<c:if test="${specialArticleVo.isPraise<=0}">
 				<span id="pid" onclick="addOrCancelPraise(0, ${specialArticleVo.articleId}, 0)">点赞</span>  
 	 		</c:if>
 		</div>  
 		  
	     <div class="vaguealert">
			<p></p>
		 </div>
		
		<!--分享按钮弹窗-->
		<div class="share" onclick="hideShare();">
			<div class="rule">
				<div class="ruletext">
					<h3>分享有奖</h3>
					<p>分享可获得大量金币哦，点击右上角分享至微信即可获得金币，快来体验一下吧~</p>
					<div class="check" onclick="toHelp();">查看规则</div>
				</div>
			</div>
		</div>
		<!--分享成功弹窗提示-->
		<div class="shate-succeed">
			<div class="hold-bg">
				<div class="hold">
					<h3>恭喜你</h3>
					<p>获得了<span>3</span>个金币</p>
				</div>
			</div>
		</div>
		
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script src="${resourcepath}/weixin/js/common.js"></script>
	
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		var _hmt = _hmt || [];
   		(function() {
   		  var hm = document.createElement("script");
   		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
   		  var s = document.getElementsByTagName("script")[0]; 
   		  s.parentNode.insertBefore(hm, s);
   		})();
   		
   		var source = "${source}";
   		var customerId = "${customerId}";
		var title = "挥货 - ${specialArticleVo.articleTitle}";  
		var desc = "${specialArticleVo.articleIntroduce}";
		var link = '${mainserver}/weixin/discovery/toSelectedDetail?articleId=${specialArticleVo.articleId}&source='+source+'&shareCustomerId=' + customerId;
		var theUrl = link;
		var imgUrl = "${specialArticleVo.articleCover}";
		var objId = "${specialArticleVo.articleId}";
		var concernId = "${specialArticleVo.customerId}";
		var backUrl = "${backUrl}";
		var integralSwitch = "${integralSwitch}";
		var shareCustomerId = 0;
		var checkUrl = window.location.href;
		if(checkUrl.indexOf("shareCustomerId")!=-1){
			shareCustomerId = getQueryString("shareCustomerId");
		}
   	</script>
	
	
	<script type="text/javascript" src="${resourcepath}/weixin/js/discovery/wx_selected_detail.js?time=201804111606"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801391514"></script>
</body>
</html>