<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801262309" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/index.css?t=201801262309" />
    	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
    	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    	<title>挥货-你的美食分享平台</title>
    	<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var title = "挥货 - 你的美食分享平台";  
			var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
			var link = '${mainserver}/weixin/index';
			var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
    		var backFlag = "${stype}";
    		var backType = "${stype}";
    		var _hmt = _hmt || [];
			(function() { 
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
    	</script>
	</head>
	
	<body style="background:#fff;">
		
		<div class="initloading"></div>
			
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		 
		
		<div class="sounobg" style="display:none;">
			抱歉，没有找到相应的商品
		</div>  
		<div class="container">
			<div class="header headtop" style="height:auto;top:0.9rem;">
				 
			 
				<div class="search-frame">
					<span>
						<input type="text"  placeholder="搜索商品" id="productName" onchange="queryByPro(this.value)" class="searchtext" /><a href="javascript:" class="clear" onclick="clearsearch()"></a>
					</span>
					<em onclick="toCancel()" style="font-size: 16px">取消</em>
				</div>
	 			
		 
		</div>
 		<c:if test="${productLabels != null && productLabels.size() > 0}">
			<div id="labelsId" class="dia-mask" style="display:block;position:static; background:none !important;">  
				<div class="remsou">热门搜索</div>
				<div class="soulei">
				<c:forEach var="labelVo" items="${productLabels}" >
					<a href="javascript:queryProsByLabel('${labelVo.labelName}', ${labelVo.labelType});">${labelVo.labelName}</a>
				</c:forEach>
				</div>
			</div>
		</c:if>
 		<!-- <p>&nbsp;</p> -->
		<div class="evaluateBox tuijianboxs" style="margin-top: 0.8rem;">
			 <div class="tuijianbox">
			 </div>		
		</div>	
		<div class="loadingmore">   
			<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
		</div>
	 	<div class="vaguealert">
			<p></p>
		</div>
	</body>
	<input type="hidden" name="pageCount" id="pageCount" value="">
	 <input type="hidden" name="pageNow" id="pageNow" value="">
	 <input type="hidden" name="pageNext" id="pageNext" value="">
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js?t=201801262252"></script> 
	<script src="${resourcepath}/weixin/js/wx_search.js?t=201801301106"></script> 
  	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201801300927"></script>
  	
	 
</html>