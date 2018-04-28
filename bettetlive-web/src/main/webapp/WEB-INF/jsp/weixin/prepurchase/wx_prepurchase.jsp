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
	<meta name="keywords" content="新品预售,挥货,挥货商城,电商平台" /> 
	<meta name="description" content="挥货新品预售活动为您网罗最新，最全地道好货" />
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/yugou.css" />
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<title>挥货-新品预售</title>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
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
			<div class="header">
				<a href="${mainserver}/weixin/index">
					<img src="${resourcepath}/weixin/img/huihuo-logo.png" alt="" />
				</a>
				<a href="javascript:void(0)" class="searchBox"></a>
				<a href="javascript:void(0)" class="shopCar">
				<span class="totNums<c:if test="${cartCnt >0}"> totNums_sp</c:if>"><c:if test="${cartCnt >0}">${cartCnt }</c:if></span>
				</a>
				<div class="search-frame">
					<span>
						<i></i>
						<input type="search" placeholder="请输入商品名称进行搜索" id="productName"/>
					</span>
					<em>取消</em>
				</div>
			</div>
			<div class="mainBox">
				
			</div>
			<div class="footer">
				<ul>
					<li class="homePage"><a href="${mainserver}/weixin/index"><em></em><i>首页</i></a></li>
					<li class="purchase active"><a href="${mainserver}/weixin/prepurchase/findList"><em></em><i>预售</i></a></li>
					<li class="special"><a href="${mainserver}/weixin/special/findList"><em></em><i>专题</i></a></li>
					<li class="mine"><a href="${mainserver}/weixin/tologin"><em></em><i>我的</i></a></li>
				</ul>
			</div>
		</div>
		<div class="mask" style="z-index: 8"></div>
		<div style="display: none;">
			<form id="form2" action="${mainserver}/weixin/search" method="post">
				<input  id="searchName" name="productName" readonly="readonly">
			</form>
		</div>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript">
		var StringBuffer = Application.StringBuffer;
		var buffer = new StringBuffer();
		$(function(){
			
			queryPreProducts();
			
			$(".shopCar").click(function(){
				window.location.href='${mainserver}/weixin/shoppingcart/toshoppingcar';
			});
			
		});
		
		function queryPreProducts(){
			$.ajax({
				url:mainServer+'/weixin/prepurchase/queryPreProductAllJson',
				dataType:"json",
				type:"POST",
				async: false,
				success:function(data){
					$(".mainBox").empty();
					if(data.list!=null&&data.list.length>0){
						$.each(data.list,function(i,pre){
							var raiseMoney = (pre.raiseMoney/10000).toFixed(2);
							var raiseTarget = (pre.raiseTarget/10000).toFixed(2);
							var barwidth = ((pre.raiseMoney/pre.raiseTarget)*100).toFixed(2)+"%";
							var preName = pre.preName;
							if (preName != null && preName.length > 27) {
								preName = preName.substr(0, 21) + "......";
							}
							buffer.append('<div class="goodsList">'+
												'<a href="javascript:toWxGoodDetails('+pre.productId+','+pre.preId+')">'+
													'<div class="goodsPic imgsrc" data-src="'+pre.productLogo+'"></div>'+
														'<div class="lineName">'+preName+'</div>'+
														'<div class="linePrice">￥'+pre.discountPrice+'</div>'+
														'<div class="crowdFunding">'+
															'<div class="barBox">'+
																'<span class="bar" style="width:'+barwidth+'"></span>'+
															'</div>'+
															'<div class="crowData">'+
																'<div class="crowSum">'+
																	'<label for="">已筹金额</label>'+
																	'<p>￥'+raiseMoney+'万</p>'+
																'</div>'+
																'<div class="supportNum">'+
																	'<label for="">支持人数</label>'+
																	'<p>'+pre.supportNum+'</p>'+
																'</div>'+
																'<div class="residueTime">'+
																	'<label for="">剩余时间</label>'+
																	'<p>'+pre.raiseTime+'</p>'+
																'</div>'+
																'<div class="objective">'+
																	'<label for="">预售目标</label>'+
																	'<p>￥'+raiseTarget+'万</p>'+
																'</div>'+
															'</div>'+
														'</div>'+
												'</a>'+
										  '</div>');
						})
					}
					 $(".mainBox").append(buffer.toString());
				    buffer.clean();
				}
			})
		}
		
		
		
		function toWxGoodDetails(pId,preId){
			if(pId>0){                                                               
				window.location.href=mainServer+'/weixin/prepurchase/toPreProductdetail?productId='+pId+"&extension_type=3"+"&preId="+preId;
			}else{
				alert("参数错误");
			}
		}

	</script>
</body>
</html>