<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css?t=201712251337"/>
  		<link rel="stylesheet" href="${resourcepath}/weixin/css/singlecoupon/single_redpacket.css?v=201709081724" />
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<title>挥货-礼品兑换</title>
		<script type="text/javascript">
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
		<div id="laiout">
<!-- 			<div class="header-wrap"> -->
<%-- 				<div class="header" onclick="location.href='${mainserver}/weixin/index'"> --%>
<!-- 					<div class="goback fl"> -->
<%-- 						<img src="${resourcepath}/weixin/img/singlecoupon/backpage.png"/> --%>
<!-- 					</div> -->
<!-- 					<h3>礼品兑换</h3> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="content-wrap">
				<div class="content">
					<div class="product">
						<div class="productbanner">
							<img src="${resourcepath}/weixin/img/singlecoupon/banner.jpg"/>
							<div class="text">
								<h3>作为印尼官方旅游部推荐零食</h3>
								<p>丽芝士采用经过两百多道严格检测的优质原料</p>
								<p>并使用现代化无菌生产技术，吃得安全又放心</p>
								<p>独立小袋包装，方便携带，好吃不脏手</p>
								<p>现在<span>领取25元</span><span>挥货APP优惠券</span></p>
								<h4>仅需0.1元即刻带回家!</h4>
							</div>
						</div>
						<div class="pronanme">
							<h2><span>丽芝士威化饼</span></h2>
							<h3>下单立减<span><fmt:formatNumber value="25" pattern="#,###.##" type="number" /></span>元</h3>
						</div>
						<div class="propicture">
							<img src="http://images.hlife.shop/32315c15b8544041b525bf68cea6482b.jpg"/>
						</div>
					</div>
					<form id="subForm" action="" method="post">
						<div class="lab">
							<input type="tel" id="codeNum" name="codeNum" placeholder="请输入兑换码" maxlength="6"/>
						</div>
						<input type="button" id="btn" value="立即兑换" onclick="cashGift()"/>
					</form>
					<div class="rule">
						<img src="${resourcepath}/weixin/img/singlecoupon/rule_03.png"/>
						<ul class="rulelist">
							<li>
								<h4>1.礼品使用期限：2018.01.01-2018.01.31</h4>
							</li>
							<li>
								<h4>2.礼品领取方式：</h4>
								<p>（1）输入正确的兑换码，点击立即兑换；</p>
								<p>（2）进入到订单页面。自动抵扣对应商品价格；</p>
								<p>（3）限制前300用户，领完即止</p>
							</li>
							<li>
								<h4>3.礼品配送范围：包邮地区只限北京地区。</h4>
							</li>
							<li>
								<h4>4.同一个设备同一个手机号同一个用户和同一个收货地址仅限领取一次。</h4>
							</li>
							<li>
								<h4>5.此活动最终解释权归挥货平台所有。</h4>
							</li>
						</ul>
						<img src="${resourcepath}/weixin/img/singlecoupon/rule_06.png"/>
					</div>
				</div>
			</div>	
		</div>
		
		<div class="vaguealert">
			<p id="tishi"></p>
		</div>
	</body>
	<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script>
	
	function cashGift(){
		var myCustId = "${customerId}";
		if(myCustId == null || myCustId <= 0){
			window.location.href = "${mainserver}/weixin/tologin?backUrl="+window.location.href;
			return;
		}
		var codeNum = $("#codeNum").val();
		if(codeNum != null && codeNum != "" && codeNum.length > 0){
//				if(!/^[0-9]*[1-9][0-9]*$/.test(codeNum)){
//					showvaguealert("只能输入正整数");
//					return;
//				}
			if(codeNum.length > 6){
				showvaguealert("兑换码格式错误");
				return;
			}
		}else{
			showvaguealert("请输入兑换码");
			return;
		}
		
		$.ajax({                                       
			url:'${mainserver}/weixin/customercash/getCashGift',
			data:{
				"codeValue":codeNum,
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){	//操作成功
					showvaguealert("您已兑换成功");		
					setTimeout(function(){
						toBuy(data.data);
					},900);
				}else if (data.code == "1011"){
					window.location.href = "${mainserver}/weixin/tologin?backUrl="+window.location.href;
				}else if(data.code == "1405"){
					showvaguealert(data.msg);
					setTimeout(function(){
						toBuy(data.data);
					},900);
				}else{
					showvaguealert(data.msg);
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
	
	function toBuy(productId){
		if(productId == null || productId == "" || parseInt(productId) <= 0){
			showvaguealert('出现异常，请及时联系客服~');
			return;
		}
		var ordSource = "";
		var theUrl = window.location.href;
		if(theUrl.indexOf("source")!=-1){
			ordSource = getQueryString("source");
			ordSource = "&orderSource="+ordSource;
		}
		window.location.href = "${mainserver}/weixin/product/towxgoodsdetails?productId="+productId+ordSource;
	}
	
	function toMyOrder(){
		var myCustId = "${customerId}";
		var backUrl =  window.location.href;
		if(myCustId == null || myCustId <= 0){
			window.location.href = "${mainserver}/weixin/tologin?backUrl="+backUrl;
			return;
		}else{
			window.location.href = "${mainserver}/weixin/order/findList?status=0";
		}
	}
	</script>
	
	<!-- 修改微信自带的返回键 -->
	<script type="text/javascript">
		$(function(){
			
			var bool=false;  
            setTimeout(function(){  
                  bool=true;  
            },1000); 
	            
			pushHistory(); 
			
		    window.addEventListener("popstate", function(e) {
		    	if(bool){
		    		window.location.href="${mainserver}/weixin/index";	
		    	}
		    }, false);
		    
		    function pushHistory(){ 
		        var state = { 
	        		title: "title", 
	        		url:"#"
	        	}; 
	        	window.history.pushState(state, "title","#");  
		    }
		});
	
	</script>
</html>
