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
  	<meta name="keywords" content="挥货上新,挥货活动,挥货,限时活动,超值秒杀,挥货商城" /> 
	<meta name="description" content="挥货专题为您网罗上新、限时、超值好货活动" />
  	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
  	<link rel="stylesheet" href="${resourcepath}/weixin/css/zhuanti.css" />
  	<script src="${resourcepath}/weixin/js/rem.js"></script>
  	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<title>挥货-专题列表</title>
	<script type="text/javascript">
		var mainServer = '${mainserver}';
		
		
		var title = "挥货 - 专题";  
		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
		var link = '${mainserver}/weixin/special/findList';
		var imgUrl = "${resourcepath}/weixin/img/huihuologo.png";
		
		var tabType = "${type}";
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
<body>
	<div class="initloading"></div>
	
	<div class="zanwubg" style="display: none;">暂无内容，小编正在上传中</div>
	<div class="mask" style="top:1rem; "> 
		<div class="nvzt_bg">
			<c:forEach var="sat" items="${specialArticleTypes}" varStatus="status">
				<a href="javascript:refreshOne(${sat.typeId});" <c:if test="${status.index == 0}">class="current"</c:if>>${sat.typeName}</a>
			</c:forEach>
		</div>
	</div>   
	<div class="zhuantifx" style="display:none; " onclick="hideShare()">
		<img src="${resourcepath}/weixin/img/fxbg.png" alt="" /> 
	</div>
 	 <div class="myordrnav">
	 	<ul>
	 		<li id="li0"><a href="javascript:void(0);" class="al0 current" >美食推荐</a><span class="daixia"></span></li> 
	 		<li id="li1"><a href="javascript:void(0)" class="al1">美食教程</a></li>  
	 	</ul>  
	 </div> 
	 <div style="padding-bottom:1rem;"> 
		 <div id="special0" class="myordercent"  style="background:none; padding:0;"> 
		 </div>
		 <div id="special1" class="myordercent" style="display:none; padding:0; " > 
	 	 </div>
	 
	 	 <div class="evaluateBox tuijianboxs" style="margin-top:0;">
				 <div class="pltitle">
				 	<span>为您推荐</span> 
				 </div>  
				 <div id="tuijianId" class="tuijianbox">
				 
				 </div>		
			</div>	
		</div>
		<div class="footer">
				<ul>
					<li class="homePage"><a href="${mainserver}/weixin/index"><em></em><i>首页</i></a></li>
					<li class="purchase"><a href="${mainserver}/weixin/product/toProductsByType"><em></em><i>分类</i></a></li> 
					<li class="special active"><a href="${mainserver}/weixin/special/findList"><em></em><i>发现</i></a></li>
					<li class="shopping"><a href="javascript:void(0);"><em></em><i>购物车</i></a></li>
					<li class="mine"><a href="${mainserver}/weixin/tologin"><em></em><i>我的</i></a></li>
				</ul>
			</div>
			
  		<div class="vaguealert">
			<p></p>
		</div>
 
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/special/wx_special.js?t=201802271511"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802271456"></script>
	
</body>
</html>