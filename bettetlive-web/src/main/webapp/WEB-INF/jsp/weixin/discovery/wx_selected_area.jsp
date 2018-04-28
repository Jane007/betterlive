<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
  	<meta content="email=no" name="format-detection">
	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
	<meta name="description" content="挥货，你的美食分享平台" /> 
  	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/weui.min.css"/>
    <link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/jquery-weui.min.css"/>
 	<link rel="stylesheet" href="${resourcepath}/weixin/css/discovery/wx_selected_area.css?t=201712181110" />
  	<script src="${resourcepath}/weixin/js/rem.js"></script>
  	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<title>挥货-<c:if test="${typeVo != null && typeVo.typeName != null && typeVo.typeName != ''}">${typeVo.typeName}</c:if><c:if test="${typeVo == null || typeVo.typeName == null || typeVo.typeName == ''}">精选好文</c:if></title>
	<script type="text/javascript">
		var mainServer = '${mainserver}';
		var title = "挥货 - 发现";  
		var desc = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
		var link = '${mainserver}/weixin/discovery/toSelectedArea?typeId=${typeId}';
		var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
		var theUrl = link;
		var typeId = "${typeId}";
		var customerId = "${customerId}";
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>
</head>
<body style="padding-top:0px;">
	<div class="initloading"></div>
	
	<div class="zanwubg" style="display: none;">暂无内容，小编正在上传中</div>
		<div id="dataList" style="margin-top: 0rem;"></div>
			<!--上拉加载-->
			<div class="weui-loadmore">
			<i class="weui-loading"></i>
				<span class="weui-loadmore__tips">正在加载</span>
			</div>
  		<div class="vaguealert">
			<p></p>
		</div>
 
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/jquery-weui.min.js"></script>
	<script src="${resourcepath}/weixin/js/discovery/wx_selected_area.js"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802251120"></script>
	
	
</body>
</html>