<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="r" uri="http://www.kingleadsw.com/taglib/replace" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection">
	<meta name="keywords" content="挥货,每周新品,挥货每周上新" /> 
	<meta name="description" content="挥货商城每周新品首发为您推荐优质农产品，提升生活品质。" /> 
	<title>
		${productInfo.product_name}${names}-挥货
	</title>
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/goodsdetails.css" />
   	<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<style type="text/css">
		.exampleBox {
			font-size: 0.25rem;
		}
		.exampleBox img {
			width: auto;
			max-width: 100%;
		}
	</style>
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
	 
		<div class="mainBox" style="top:0; ">
			<div class="swiper-container">
				<div class="swiper-wrapper">
					<c:forEach items="${productInfo.pictures }" var="banner">
						<div class="swiper-slide">
							<a href="javascript:;"><img src="${banner.original_img }" alt=""/></a>
						</div>
					</c:forEach>
				</div>
				<div class="swiper-pagination"></div>
			</div>
			<div class="goodsContent">
				<div class="goodsInfor">
					<div class="variety">
						<div class="productName">
							${productInfo.product_name}${names}
						</div>
						<div class="productPrice">
							<c:choose>
								<c:when test="${productInfo.listSpecVo[0] != null && productInfo.listSpecVo[0].activity_price != null 
												&& productInfo.listSpecVo[0].activity_price != '' && productInfo.listSpecVo[0].activity_price != '-1'}">
									￥${productInfo.listSpecVo[0].activity_price}<del>￥${productInfo.listSpecVo[0].spec_price}</del>
								</c:when>
								<c:when test="${productInfo.listSpecVo[0] != null && productInfo.listSpecVo[0].discount_price != null 
												&& productInfo.listSpecVo[0].discount_price >= 0}">
									￥${productInfo.listSpecVo[0].discount_price}<del>￥${productInfo.listSpecVo[0].spec_price}</del>
								</c:when>
								<c:otherwise>
									￥${productInfo.listSpecVo[0].spec_price}
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<ul>
						<li class="etalon">
							<label for="">请选择规格数量<span></span> <i></i></label>
							<em></em>
						</li>
						<c:if test="${limitNum>0}">
							<li class="purchase">
								<i></i><span>每人限购${limitNum}件</span>
							</li>
						</c:if>
						
						
					</ul>
					<div class="reminder">
						<span class="tips">温馨提示：</span>${productInfo.prompt}
					</div>
				</div>
				<div class="parameterBox">
					<div class="parTitle">
						产品参数&nbsp;&nbsp;
					</div>
					<ul>
						<c:forEach items="${productInfo.paramAndValue}"  var="pv"  >
							<c:set var="params" value="${fn:split(pv,'&#&$')[0] }"></c:set>
				    		<c:set var="values" value="${fn:split(pv,'&#&$')[1] }"></c:set>
							
							<li>
								<label for="">${params}：</label>
								<span>${values}</span>
							</li>
						</c:forEach>
					</ul>
				</div>
				
				<div class="evaluateBox">
					<div class="evaTitle">
						评价<em>（${fn:length(comments)}）</em>	
					</div>
					
					<c:if test="${fn:length(comments)>0 }">
					<div class="messageBox">
						<div class="mesList">
							<div class="userPic">
								 <img src="${comments[0].customerVo.head_url }" alt="" />
							</div>
							<div class="mesInfor">
								<div class="userName">
									<r:replace str="${comments[0].customerVo.nickname}" mark="*"/>
								</div>
								<div class="mesTime">
									${comments[0].create_time}
								</div>
								<div class="mesCon">
									${comments[0].content }
								</div>
							</div>
						</div>
					</div>
					</c:if>
				</div>
								
				<div class="exampleBox">
					${productInfo.introduce}						
				</div>
				<div class="evaluateBox tuijianboxs">
					 <div class="pltitle">
					 	<span>为您推荐</span> 
					 </div> 
					 <div class="tuijianbox">
					 </div>		
				</div>				
			</div>
			
		</div>
		<form id="buyForm" action="${mainserver}/weixin/order/addXXBuyOrder" method="post">				
			<div class="botShop" style="display:block;">
				<a href="javascript:void(0)" class="newBuy" onclick="toAddShotCar(1)" style="float:right; background:#e62d29;width: 100%; ">立即购买</a>
				<input type="hidden"  id="productId"  name="productId"  value="${productInfo.product_id}"  readonly="readonly"/>
				<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
				<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly"/>
				<input type="hidden"  id="buyAmount"  name="buyAmount"  readonly="readonly"/>
				<input type="hidden" id="orderSource" name="orderSource" value="${orderSource}">
			</div>
		</form>
	</div>
	<div class="gohome">
		<a href="${mainserver}/weixin/index">
			<img src="${resourcepath}/weixin/img/gohome.png" alt="">
		</a>
	</div>
	<div class="backTop"></div>
	
	<div class="mask"></div>
	<div class="dia-mask"></div>
	
	<div class="vaguealert">
		<p>绑定成功</p>
	</div>
		
	<div class="standardBox">
		<div class="standTop">
			<div class="proPic">
				 <img src="${productInfo.listSpecVo[0].spec_img}" alt="" />	
			</div>
			
			<div class="norms">
				<p class="normsPrice">
					<label for="">价格：</label>
					<span></span>
				</p>
				<p class="inventory" id="stockCopy">
					
				</p>
				<p class="normsChoose">请选择规格</p>
			</div>
		</div>
		<div class="installBox">
			<label for="">规格</label>
			<ul>
				<c:forEach items="${productInfo.listSpecVo}"  var="s">
					<c:choose>
						<c:when test="${s.activity_price !=null && s.activity_price != '' && s.activity_price != '-1'}">
							<li value="¥${s.activity_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
								data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}">
								${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
							</li>
						</c:when>
						<c:when test="${s.discount_price !=null && s.discount_price >= 0}">
							<li value="¥${s.discount_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
							data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}"
							data-carNums="${s.carNums }" data-carCanAdd="${s.carCanAdd }" data-package="${s.package_desc }"
							data-promoitionName="${s.promoitionName }" data-fullMoney="${s.fullMoney }" data-cutMoney="${s.cutMoney }"
							>${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
								
							</li>
						</c:when>
						<c:otherwise>
							<li value="¥${s.spec_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
								data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}">
								${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
								<!-- <input type="hidden" id="copy${s.spec_id}" value="${s.stock_copy }"/> -->
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="comNumber">
			<label for="">数量</label>
			<div class="calculate">
				<i class="calCut">-</i>
				<input type="text" readonly="readonly" value="1" class="calGoodNums"/>
				<i class="calAdd">+</i>
			</div>
		</div>
		<div class="outBox">
			<img src="${resourcepath}/weixin/img/outbox.png" alt="" />
		</div>
		
		
		<div style="display: none;">
			<form id="formSearch" action="${mainserver}/weixin/search" method="post">
				<input  id="searchName" name="productName" readonly="readonly">
			</form>
		</div>
	</div>
	
	<div class="boundPhone">
		<div class="dia-title">绑定手机号码</div>
			<form id="bindingForm" action="" method="post" style="padding: 0 0.24rem;margin-top: 0.24rem;">
				<ul>
					<li class="phoneBox">
						<label for=""></label>
						<input type="tel" name="mobile"  id="mobile"  maxlength="11" placeholder="请输入手机号码"/>
						<span class="verificationcode">获取验证码</span>
					</li>
					<li class="verifyCode">
						<label for=""></label>
						<input type="text"  name="verifycode"  id="verifycode"  maxlength="5"  placeholder="请输入验证码" />
					</li> 
				</ul>
				<div class="now-bound">绑定</div>
			</form>
		<div class="boundout"><img src="${resourcepath}/weixin/img/outbox.png" alt="" /></div>
    </div>
	
	
	    	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript">
	 	
		//初始化swiper
		var mySwiper=new Swiper(".swiper-container",{
			loop:true,
			pagination : '.swiper-pagination',
			autoplayDisableOnInteraction : false,
			autoplay:'3000'
		});
		
		var mobile ='${mobile}';
		var carNums= 0;
		var carCanAdd = 0;	//购物车还可以添加多少份
		var restCopy = 0;	//剩余购买数量
		var stockCopy = 0; 	//库存
		var limitNum = 0;	//限购份数
		var hasBuy = 0;     //已经购买的份数
		var buyMS = 0;		//马上购买剩余的购买数量
		var specLen = $('.installBox ul li').length;  //商品规格数量
		var is_select = 0;  //是否选择了规格
		
		$(function(){
			queryLikeProducts();
			
			//返回上一层
			$('.backPage').click(function(){
				window.location.href='${mainserver}/weixin/index';
			});
			
			//点击搜索框
			$('.searchBox').click(function(){
				$('.search-frame').addClass('active');
				$('.dia-mask').show();
				$('.header').css('z-index','200');
			});
			//点击取消
			$('.search-frame em').click(function(){
				$('.search-frame').removeClass('active');
				$('.dia-mask').hide();
				$('.header').css('z-index','');
			});
			
			$('.search-frame span i').click(function(){//查询商品
				var productName = $('#productName').val();
				if(productName==null || productName==''){
					$('#productName').attr('placeholder','请输入商品名称进行搜索');
					$(this).focus();
				}else{
					$("#searchName").val(productName);
					$("#formSearch").submit();
				}
			});
			
			
			//底部tab切换
			$('.footer li').click(function(){
				$(this).addClass('active').siblings('li').removeClass('active');
			});
			//滚动一定的距离出现返回顶部
		 	$(window).bind("scroll", function(){ 
		        var top = $(this).scrollTop(); // 当前窗口的滚动距离
		        if(top>=300){
		        	$('.backTop').show();	
		        }else{
		        	$('.backTop').hide();
		        }
		  	});
			//点击返回顶部
			$('.backTop').on('click',function(){
				$(document).scrollTop('0');
			});
			//弹出规格窗口
			$('.etalon').click(function(){
				$('.standardBox').addClass('active');
				$('.mask').show();
			});
			//点击隐藏规格窗口
			$('.outBox,.mask').click(function(){
				hideObj();
				isActive();
// 				if (specLen > 1) {
// 					is_select = 0;
// 				}
			});
			
			function hideObj(){
				$('.standardBox').removeClass('active');
				$('.mask').hide();
			}
		
			
			//***********************规格窗口*********************************
			//价格
			var oprice=$('.productPrice span').text();
			$('.normsPrice span').text(oprice);
			
			//选择规格
			$('.installBox li').click(function(){
				is_select = 1;
				$(this).addClass('active').siblings('li').removeClass('active');
				var standnumber=$(this).html();
				var standprice=$(this).attr("value");
				var standspecId=$(this).attr("id");
				
				stockCopy = $(this).attr("data-copy");
				limitNum = $(this).attr("data-limit");
				hasBuy = $(this).attr("data-hasBuy");
				restCopy = $(this).attr("data-restCopy");
				buyMS = restCopy; //能购买分数等于库存
				
				if(limitNum!=null&&limitNum!=''&&parseInt(limitNum)>=1){
					$('.normsChoose').html('已选择：'+standnumber+'(限购'+limitNum+'件)');
				}else{
					$('.normsChoose').html('已选择：'+standnumber);
				}
				$('.proPic img').attr("src",$("#spec_img"+standspecId).val());
				$('.normsPrice span').text(standprice)
				$("#productSpecId").val(standspecId);
				$("#buyAmount").val($('.calGoodNums').val()); 
// 				$('.proPic img').attr("src",$("#spec_img"+standspecId).val());
				if(stockCopy!=null&&stockCopy!=''){
					if(parseInt(stockCopy)>=1){
						$(".botShop").show();
						$("#stockCopy").html("库存"+stockCopy+"件");
						$("#buyForm").show();
					}else{
						$("#stockCopy").html("已秒完");
// 						$("#buyForm").hide();
					}
				}else{
					$(".botShop").hide();
					$("#stockCopy").html("库存不足");
				}
			});
			
			//选择规格之后才可以点击数量
			var outli=$('.installBox li');
			//增加数量
			$('.calAdd').click(function(){
				if(outli.hasClass('active')){
					var $this=$(this);
					var $numval=parseInt($(this).prev('input.calGoodNums').val());
					
			    	var maxBuy = limitNum;
			    	var buyAmount = $("#buyAmount").val();
			    	if(stockCopy!=null&&stockCopy!=''){
			    		if(parseInt(stockCopy)<=0){
			    			showvaguealert("此产品规格暂无库存");
			    			return false;
			    		}
			    	}else{
			    		showvaguealert("此产品规格暂无库存");
		    			return false;
			    	}
			    	if(parseInt(buyAmount)>=parseInt(stockCopy)){
		    			showvaguealert("库存仅剩"+stockCopy+"件");
		    			return false;
		    		}
			    	if(parseInt(limitNum)>=1){
			    		if(parseInt(buyMS)<=0){//剩余购买数量
							showvaguealert("此产品限购"+maxBuy+"份");
			    			return false;
			    		}
			    		
			    		if(parseInt(buyAmount)>parseInt(buyMS)){
			    			showvaguealert("您已经购买了"+hasBuy+"件，可再购买"+buyMS+"件");
			    			return false;
			    		}
			    		if(parseInt(buyAmount)>=1){
			    			if(parseInt(maxBuy)-parseInt(hasBuy)<=parseInt(buyAmount)){
				    			showvaguealert("此产品限购"+maxBuy+"件");
					    		return false;
				    		}
			    		}
			    	}
			    		$(this).siblings('.calGoodNums').val($numval+1);
			    		$("#buyAmount").val($numval+1)
			    	
					
				}else{
					showvaguealert('请先选择规格');
				}
			});
			
			//减少数量
			$('.calCut').click(function(){
				if(outli.hasClass('active')){
					var $numval=parseInt($(this).next('input').val());
					if($numval<=2){
						$numval=2;
					}
					$(this).siblings('.calGoodNums').val($numval-1);
					$("#buyAmount").val($numval-1)
				}else{
					showvaguealert('请先选择规格');
				}
			});
			
			initSepc();
			
			//分享
			var url = window.location.href;
			if(url.indexOf("#")!=-1){
				url = url.substring(0,url.indexOf("#"));
			}
			$.ajax({                                       
				url:'${mainserver}/weixin/shareWeixin',
				data:{
					"url":url,
				},
				type:'post',
				dataType:'json',
				success:function(data){
					wx.config({
					    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					    appId: data.shareInfo.appId, // 必填，公众号的唯一标识
					    timestamp: data.shareInfo.timestamp, // 必填，生成签名的时间戳
					    nonceStr: data.shareInfo.nonceStr, // 必填，生成签名的随机串
					    signature: data.shareInfo.signature,// 必填，签名，见附录1
					    jsApiList: [ // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
							'hideMenuItems'
							
					    ]
					});
				},
				failure:function(data){
					showvaguealert('出错了');
				}
			});
			
		
			
			// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			wx.ready(function(){
				wx.hideMenuItems({
                   	menuList: ['menuItem:share:appMessage',  //发送给朋友
                              'menuItem:share:timeline',    //分享到朋友圈
                              'menuItem:share:qq',  //分享到QQ
                              'menuItem:share:weiboApp',  //分享到Weibo
                              'menuItem:favorite',
                              'menuItem:share:QZone'], // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
          	 		success:function(res){
          	
                   	}
                });
			});
			
			wx.error(function (res) {
				//showvaguealert(res.errMsg);
			});
		});
		
		//判断是否选择有选择规格，如果选择了规格，则更新规格数量的显示
		function isActive(){
			var oli = $('.installBox li');
			if(oli.hasClass('active')){
				is_select=1;
				var oguige = ($('.normsChoose').text()).replace('已选择：','');
				var oval = $('.calGoodNums').val();
				$('.etalon span').text(oguige);
				$('.etalon i').text('x'+oval);
				
				$("#buyAmount").val($('.calGoodNums').val());
				
				stockCopy = parseInt(oli.attr("data-copy"));
				limitNum = parseInt(oli.attr("data-limit"));
				hasBuy = parseInt(oli.attr("data-hasBuy"));
				restCopy = parseInt(oli.attr("data-restCopy"));
			}
		}
		
		//默认选择第一个规格，初始化价格及已选择
		function initSepc() {
// 			if (specLen == 1) {
				$('.installBox li:first').click();
				//更新规格数量显示
				isActive();
// 			}
		}
		
		//提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},20000);
		}
		
		function toAddShotCar(type){
			//是否有一个规格被选中
			var is_spec_active = $('.installBox ul li').hasClass('active');
			if ((is_select == 0) && !is_spec_active) {  //没有选规格
				if ($('.standardBox').hasClass('active')) {  //如果已经弹出选择规格框，则提示请选择规格
					showvaguealert('请选择商品规格！');
				} else {
					$('.etalon').click(); //弹出选择规格
				}
				return;
			} else if ((is_select == 0) && is_spec_active) {
				if (!$('.standardBox').hasClass('active')) {  //如果已经弹出选择规格框，则提示请选择规格
					$('.etalon').click(); //弹出选择规格
					return;
				}
			}
			
			if(1==type &&( null==mobile || ""==mobile) ){
				$(".boundPhone").show();
				$('.dia-mask').show();
				return false;
			}
			
	    	var productId=$("#productId").val();
	    	if(""==productId||null ==productId){
	    		showvaguealert('请重新选择商品！');
	    		return false;
	    	}
	    	
	    	var productSpecId= $("#productSpecId").val();
	    	if(null ==productSpecId || ''==productSpecId ){
	    		showvaguealert('请选择商品规格！');
	    		return false;
	    	}
	    	
	    	var buyAmount=$("#buyAmount").val();
	    	if(""==buyAmount||null ==buyAmount){
	    		showvaguealert('请选择购买数量！');
	    		return false;
	    	}
	
	    	
	    	if(stockCopy!=null&&stockCopy!=''){
	    		if(parseInt(stockCopy)<=0){
	    			showvaguealert("此产品规格暂无库存");
	    			return false;
	    		}
	    	}
	    		
	    	if(type==1){
	    			if(parseInt(buyAmount)>parseInt(stockCopy)){
		    			showvaguealert("库存仅剩"+stockCopy+"件");
		    			return false;
		    		}
	    			
	    			if(parseInt(limitNum)>0){//限购才走这些判断
			    		if(parseInt(buyAmount)>parseInt(buyMS)){
			    			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
			    			return false;
			    		}
			    		if(parseInt(buyAmount)>parseInt(limitNum)){
		    				showvaguealert("此商品限购"+limitNum+"件");
			    			return false;
		    			}
	    			}
		    		is_select = 0;
					$("#buyForm").submit();
		    	}
	    }
		
		$(".evaluateBox .evaTitle").click(function(){
			var productId=$("#productId").val();
	    	if(""==productId||null ==productId){
	    		showvaguealert('参数错误！');
	    		return false;
	    	}
			window.location.href=mainServer+'/weixin/productcomment/findList?productId='+productId;
		});
		
		function queryLikeProducts(){
			$.ajax({                                       
				url:'${mainserver}/weixin/product/queryLikeProducts',
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.code == "1010"){ //获取成功
						if(data.data == null || data.data.length <= 0){
							return;
						}
						var list = data.data;
						for (var continueIndex in list) {
							if(isNaN(continueIndex)){
								continue;
							}
							var productVo = list[continueIndex];
							
							var shareExplain = "";
							if(productVo.share_explain != null){
								shareExplain = productVo.share_explain;
								if(shareExplain.length > 25){
									shareExplain = shareExplain.substring(0, 25)+"...";
								}
							}
							var showLabel="";
							if(productVo.labelName != null && productVo.labelName != ""){
								showLabel = "<p>"+productVo.labelName+"</p>"; 
							}
							var local = "${mainserver}/weixin/product/towxgoodsdetails?productId="+productVo.product_id;
							if(productVo.activityType == 2){ //限量抢购
								local = "${mainserver}/weixin/product/toLimitGoodsdetails?productId="+productVo.product_id+"&specialId="+productVo.activity_id;
							}else if(productVo.activityType == 3){ //团购
								local = "${mainserver}/weixin/product/toGroupGoodsdetails?specialId="+productVo.activity_id+"&productId="+productVo.product_id;
							}
							var showMoneyLine = "";
							if(productVo.activityPrice != null && productVo.activityPrice != "" && parseFloat(productVo.activityPrice) >= 0){
								showMoneyLine = "<span>￥"+checkMoneyByPoint(productVo.activityPrice)
											 + "<strong>￥"+checkMoneyByPoint(productVo.price)+"</strong></span>";
							}else if(productVo.discountPrice != null && productVo.discountPrice != "" && parseFloat(productVo.discountPrice) >= 0){
								showMoneyLine = "<span>￥"+checkMoneyByPoint(productVo.discountPrice)
								 + "<strong>￥"+checkMoneyByPoint(productVo.price)+"</strong></span>";
							}else{
								showMoneyLine = "<span>￥"+checkMoneyByPoint(productVo.price)+"</span>";
							}
							var showHtml='<div class="tuijian" onclick="location.href=\''+local+'\'">'
										+' 		<div class="left">'
										+'		<img src="'+productVo.product_logo+'" alt="" />'
										+'	</div>'
										+'	<div class="right">'
										+'		<div class="tjname">'
										+'		<span>' + productVo.product_name + '</span>'
										+		showLabel
										+'		</div>'
										+'		<div class="tjcent">'
										+			shareExplain
										+'		</div>'
										+'		<div class="tjmoney">'
										+			showMoneyLine
										+'			<p>销量'+productVo.salesVolume+'份</p>'
										+'		</div>'
										+'	</div>'
										+'</div>'
							$(".tuijianbox").append(showHtml);
						}
					}else{
						showvaguealert("出现异常");
					}
				},
				failure:function(data){
					showvaguealert('出错了');
				}
			});
		}
	</script>
	
	<script type="text/javascript">
		$(function(){
	
			//判断手机号码
			var phoneflag=false;
			$("#mobile").change(function(){
				var $phone=/^((1[0-9]{2})+\d{8})$/;
				var $phoneval=$("#mobile").val();
				if($phoneval){
					if(!$phone.test($phoneval)){ 
						showvaguealert('您输入的手机号有误！');
					    phoneflag=false;
					}else{
						phoneflag=true;
					}
				}else{
					showvaguealert('您输入的手机号不能为空!');
				}
				
			});
			
			//点击获取验证码
			var codeflag=false;
			$('.verificationcode').click(function(){
				var $phoneval=$("#mobile").val();
				if(!codeflag){
					if(!codeflag&&phoneflag){
						$(this).addClass('codeactive');
						$('.verificationcode').text('重新获取 ('+ 60+')');
						var codetime=setInterval(countdown,1000);
						var i=60;
						function countdown(){
							i--;
							$('.verificationcode').text('重新获取 (' + i+ ')');
							if(i<=0){
								i=0;
								clearInterval(codetime);
								$('.verificationcode').text('获取验证码').removeClass('codeactive');
								codeflag=false;
							}
						}
						codeflag=true;
						getVerifCode();
					}else{
						if($phoneval){
							showvaguealert('您输入的号码有误！');
						}else{
							showvaguealert('您输入的号码不能为空！');
						}
					}
				}
				
			});
			
			
			//判断验证码
			var getflag=false;
			var $code=/^[0-9]{5}$/;  //验证码正则表达式
			$("#verifycode").change(function(){
				var $entrypass=$("#verifycode").val();
				if(!$code.test($entrypass)){ 
					showvaguealert('验证码为5位数字!');
				    getflag=false;
				}else{
					getflag=true;
				}
			});
			
			
			//点击注册确定
			$('.now-bound').click(function(){
				if(getflag&&phoneflag){
					
					var phone=$("#mobile").val();
					var verifycode=$("#verifycode").val();
					
					$.ajax({
						url:'${mainserver}/weixin/usercenter/toRegeditUser',
						data:{
							"mobile":phone,
							"code":verifycode,
							"source":"browser_offline"
						},
						type:'post',
						dataType:'json',
						success:function(data){
							if(data.result=='succ'){
								mobile=phone;
								
								$(".boundPhone").hide();
								$('.dia-mask').hide();	
								window.location.reload();
							}else{
								showvaguealert(data.msg);
							}
						},
						failure:function(data){
							showvaguealert('出错了');
						}
					});
					
					
				}else{
					var $phoneval=$("#mobile").val();
					var $entrypass=$("#verifycode").val();
					
					if(!$phoneval){
						showvaguealert('您输入的号码不能为空！');
					}else if(!phoneflag){
						showvaguealert('您输入的号码有误！');
					}else if(!$entrypass){
						showvaguealert('验证码不能为空！');
					}
				}
			});	
		});
		
		
		//提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},2000);
		}
		
		
		//绑定手机号码获取验证码
		function getVerifCode(){
	    	var phoneNum =$("#mobile").val();
	    	$.ajax({
	    		url: "${mainserver}/weixin/presentcard/sendMessage",
				type: "POST",
				dataType:'json',
				async:false,
				data:{'phoneNum':phoneNum},
				success: function(data) {
					var obj = data;
					if('fail' != data.result){
						showvaguealert('获取验证码成功！');
					}else{
						showvaguealert('获取验证码失败！');
					}
				}
			});
	    }
		
		
		//点击取消绑定手机框
		$('.boundout').click(function(){
			$('.boundPhone').hide();
			$('.dia-mask').hide();
		});
	</script>
	
</body>
</html>