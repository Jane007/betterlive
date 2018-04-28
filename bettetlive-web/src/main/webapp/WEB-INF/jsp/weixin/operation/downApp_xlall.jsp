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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common2.css?t=20171109034" />
  		<link rel="stylesheet" href="${resourcepath}/weixin/css/new-getRedpacket.css?v=20171109034" />
  		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-领取红包</title>
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
	<body class="dpbgbox dpbgboxs">
		<div id="alertTipId" class="lingbgnew" style="display: none;">   
			<div class="xxdiv"><img src="${resourcepath}/weixin/img/libaoxx.png" alt=""/></div> 
			<span id="showContent"></span>
			<a id="jumpId" href="${mainserver}/weixin/index">去使用</a>
		</div> 
		<div class="guizbt"></div>
		<div class="container dancentbox">
		 	<div class="cpbgbox" style="height:6.8rem;"> </div>  
			<div id="mainBoxShow" class="mainBox">
				<div class="getBox" style="display: block;"> 
					<form action="">
						<ul>
							<li class="phoneBox">
								<input type="tel" id="phoneNum" placeholder="请输入手机号领取"/>
								<div class="phonealert">
									<span></span>
								</div>
							</li>
							<li class="verifyCode">
								<input type="tel" id="msgCode" placeholder="请输入短信验证码"/>
								<span class="phoneCode">获取验证码</span>
								<div class="verifyalert">
									<span></span>
								</div>
							</li>
						</ul>
					</form>
					<div class="now-get" onclick="register()">
						立即领取
					</div>
				</div>
			</div>
		</div>
		<div class="mask"></div>
		<div class="useRuleBox">
			<div class="ruleCon">
				<h3>••• 使用规则 •••</h3>
				<p>1.红包仅限购买挥货官网商品使用，不能兑换成现金。</p>
				<p>2.使用数量：每个订单只能使用1个红包。</p>
				<p>3.使用时间：自红包领取后的1个月内有效。</p>
				<p>4.产品限定：仅限于正价商品可使用，特惠商品不在使用范围内。</p>
				<p>5.使用门槛：有门槛的红包根据不同红包的规则，需达到一定金额方可使用；无门槛红包可以直接抵用订单金额。</p>
				<p>6.红包使用：不找零、不折现，限一次性使用完（不能抵扣快递、物流或服务费）；如使用后发生退款，红包不作为退款项。</p>
				<p>7.红包支付部分不开发票（如需发票）。</p> 
				<p>8.订单退还：订单退还时，已经使用的红包不再退还。</p>

			</div>
			<div class="outBoxTitle"></div>
		</div>
		<div class="vaguealert">
			<p id="tishi"></p>
		</div>
		
	</body>
	<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script>
	
	$(function(){
		
		$('.guizbt').click(function(){
			$('.useRuleBox').show();
			$('.mask').show();
		});
		 
		$('.outBoxTitle').click(function(){
			$('.useRuleBox').hide();
			$('.mask').hide();
		});
		 
		$('.xxdiv').click(function(){
			$('#alertTipId').hide();
		});
		
		$('.phoneCode').click(function(){
			var $this = $(this);
			var sMobile = $('#phoneNum').val();
			  if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(sMobile)) || sMobile.length != 11){ 
				  showvaguealert('请输入正确手机号码！');
			  } else {//手机发短信验证码
				  time_lun();
				  getMessage();
			  }
		});
		
		var url = window.location.href;
		if(url.indexOf("#")!=-1){
			url = url.substring(0,url.indexOf("#"));
		}
		
		$.ajax({
			url : 'http://www.hlife.shop/huihuo/weixin/shareWeixin',
			data:{
				"url":url,
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: data.shareInfo.appId, // 必填，公众号的唯一标识
				    timestamp: data.shareInfo.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.shareInfo.nonceStr, // 必填，生成签名的随机串
				    signature: data.shareInfo.signature,// 必填，签名，见附录1
				    jsApiList: [ // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						'onMenuShareTimeline',
						'onMenuShareAppMessage',
						'onMenuShareQQ'
				    ]
				});
			},
			error : function(data) {
				return false;
			}
		});
		
		var title = "挥货 - 你的美食分享平台";  
		var desc = "挥货大礼包，优惠享不停。";
		var link = url;
		var imgUrl = "http://www.hlife.shop/huihuo/resources/weixin/img/huihuologo.png";

		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.ready(function(){
			// 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareTimeline({
			  	title: desc,
			  	desc: desc,
			  	link: link,
			  	imgUrl: imgUrl,
			  	trigger: function (res) {
			    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			    	//alert('用户点击分享到朋友圈');
			 	},
			  	success: function (res) {
			    	//alert('已分享');
			  	},
			  	cancel: function (res) {
			    	//alert('已取消');
			  	},
			  	fail: function (res) {
			    	alert(JSON.stringify(res));
			  	}
			});
			  
			// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareAppMessage({
				title: title,
			  	desc: desc,
			  	link: link,
			  	imgUrl: imgUrl,
			  	trigger: function (res) {
			    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			    	//alert('用户点击分享到朋友圈');
			 	},
			  	success: function (res) {
			    	//alert('已分享');
			  	},
			  	cancel: function (res) {
			    	//alert('已取消');
			  	},
			  	fail: function (res) {
			   // 	alert(JSON.stringify(res));
			    	alert(JSON.stringify(res));
			  	}
			});
			
			// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareQQ({
				title: title,
			  	desc: desc,
			  	link: link,
			  	imgUrl: imgUrl,
			  	trigger: function (res) {
			    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			    	//alert('用户点击分享到朋友圈');
			 	},
			  	success: function (res) {
			    	//alert('已分享');
			  	},
			  	cancel: function (res) {
			    	//alert('已取消');
			  	},
			  	fail: function (res) {
			  		alert(JSON.stringify(res));
			  	}
			});
		});
	});
	
		
	 var count=59;
	 function countDown(){
			$("#getCode").html(count+"s");
			if(count == 0){
				$("#getCode").attr("onclick","getMessage()");
				$("#getCode").html("再次发送");
				count=59;
			}else{
				setTimeout(countDown, 1000);
				count--;
			}
	}
	 function getMessage(){
			var phoneNum = $('#phoneNum').val();
			$.ajax({
				type: 'POST',
			   url:"${mainserver}/weixin/presentcard/sendMessage",
			   data:{'phoneNum':phoneNum},
			   async: false,
			   dataType:'json',  
			   success:function(data) {
				   if('fail' == data.result){
					   showvaguealert(data.msg);
				   }else{
					   showvaguealert("手机验证码已经发送！");
					   $('#getCode').css("background","#E3DBDA");
					   $('#getCode').unbind("click");
					   setTimeout(countDown, 1000);
				   }
				  
			   }
		  })
	}
	 
	    //提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},2000);
		}
		
		var codeflag = false;
		function time_lun(){
			if(!codeflag){
				var codetime=setInterval(countdown,1000);
				var i=60;
				countdown();
				function countdown(){
					i--;
					$('.phoneCode').text(i+'s').addClass('active');
					if(i<=0){
						i=0;
						clearInterval(codetime);
						$('.phoneCode').text('获取验证码').removeClass('active');
						codeflag=false;
					}
				}
				codeflag=true;
			}
		}
		
		
		function register(){
			var mobilePhone = $("#phoneNum").val();
			if(mobilePhone == null || mobilePhone == ""){
				showvaguealert('请输入手机号码');
				return;
			}
			
			if(!(/^((1[0-9]{2})+\d{8})$/.test(mobilePhone))){ 
				  showvaguealert('手机号码格式错误');
				  return false;
			}
			
			var code = $('#msgCode').val();
			if(code==null || code==''){
				showvaguealert("请输入手机验证码");
				return false;
			}
			 
			$.ajax({                                       
				url:'${mainserver}/weixin/operation/downAppByxlall',
				data:{
					"mobile": mobilePhone,
					"couponIds": "${couponIds}",
					"source":"${regSource}",
					"checkCode":code
				},
				type:'post',
				dataType:'json',
				success:function(data){
						if(data.flag == 1){
							if(data.regFlag == 2){
								$("#alertTipId").addClass("lingbglao");
							}
							$("#showContent").html(data.msg);
							$("#alertTipId").show();
						}else if(data.flag == 3){
							$("#showContent").html(data.msg);
							if(data.ling > 0){
								$("#jumpId").html("去使用");
							}else{
								$("#jumpId").html("去挥货商城");
							}
							$("#alertTipId").show();
						}else{
							showvaguealert(data.msg);
						}
				},
				failure:function(data){
					showvaguealert('出错了');
				}
			});
		}
	</script>
	
	<!-- 修改微信自带的返回键 -->
	<script type="text/javascript">
		$(function(){
			var bool=false;  
            setTimeout(function(){  
                  bool=true;  
            },1000); 
            
        	var backUrl = "${mainserver}/weixin/index";
	            
			pushHistory(); 
			
		    window.addEventListener("popstate", function(e) {
		    	if(bool){
					window.location.href = backUrl;
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
