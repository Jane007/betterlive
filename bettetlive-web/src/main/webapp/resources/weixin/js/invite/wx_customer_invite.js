var loginUrl =  mainServer + "/weixin/tologin?backUrl="+link;
$(function(){
	
	$("#ruleBtn").on("click",function(){
		$("#rulePop").fadeIn();
	});
	$(".closePop").on("click",function(){
		$("#rulePop").fadeOut();
	});
	
	$(".initloading").show();
	setTimeout(function(){
		$(".initloading").hide();
	},800);
	
	$(".obtn").on("click",function(){
		$("#jumpPop").fadeOut();
	});
	
	$("#jumpPop .btn").on("click",function(){
		window.location.href = mainServer + "/weixin/index";
	});
	queryRegCoupons();
	//领取100新人大礼包
	$('.getaward').on('click',function(){
		var url = mainServer + "/weixin/customerinvite/receiveHandredCoupon";
		var backUrl = window.location.href;
		$.post(url,{},function(data){
			if(data.code==1010 || data.code=='1010'){//领取成功
				//弹领取成功提示
				$("#jumpPop").fadeIn();
			}else if(data.code==1011 || data.code=='1011'){//没有登录
				window.location.href = mainServer + "/weixin/tologin?backUrl="+backUrl;
			}else if(data.code==1025 || data.code=='1025'){//老用户
				//弹窗出来去商城
				$(".text-wrap p").html("您不是新用户了，<br/>无法享受新人礼包哦<br>进入商城可享受更多优惠");
				$("#jumpPop .btn").html("去商城看看");
				$("#jumpPop").fadeIn();
			}else if(data.code==1405 || data.code=='1405'){
				//已经领取过了
				$(".text-wrap p").html("您已经领取过大礼包了");
				$("#jumpPop").fadeIn();
			}
		});
	});
	$(document).on('click','.drawList li',function(){
		var productId = $(this).attr("data-id");
		var backUrl = window.location.href;
		window.location.href= mainServer+"/weixin/product/towxgoodsdetails?productId="+productId;
	});
	
	
});

//新人红包列表
function queryRegCoupons(){
	$.ajax({                                       
		url: mainServer + '/weixin/customerinvite/queryProdcuts',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data.length <= 0){  //没有数据
				return;
			}
			var list = data.data.records;
			for (var conIndex in list) {
				if(isNaN(conIndex)){
					continue;
				}
				var vo = list[conIndex];
				var showHtml = '<li data-id='+vo.product_id+'>'
							  +'	<div class="drawCon">'
							  +'			<img src="'+vo.product_logo+'">'
				if(vo.status != 1){
				    showHtml +='			<div class="noDraw"></div>';
				}
			    	showHtml +='		</div>'
							  +'		<p class="drawName">'+vo.product_name+'</p>'
							  +'		<p class="drawPrice">挥货价￥'+vo.discountPrice+'<a>¥'+vo.price+'</a></p>'
							  +'</li>';
				
				$(".drawList").append(showHtml);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 获取券
 * @param couponId
 */
function getCoupon(couponId, productId){
	if(customerId == null || customerId <= 0){	//未登录
		window.location.href = loginUrl;
	}else if(orderNum > 0){	//已经下过单的用户视为老用户
		//看看有没有别的优惠
		checkMoreCoupon(productId);
	}else{
		//新用户优惠
		receiveCoupon(couponId,productId);
	}
}

function checkMoreCoupon(productId){
	$.ajax({                                       
		url: mainServer + '/weixin/customerinvite/checkMoreCoupon',
		data:{
			"productId": productId
		}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1011"){	//未登录
				window.location.href = loginUrl;
			}else{
				if(data.code !="1010"){  //没有数据
					showvaguealert(data.msg);
				}else{
					if(data.data != null && data.data > 0){//去领取红包
						window.location.href = mainServer + "/weixin/singlecoupon/getClickRedPacket?couponId="+data.data+"&productId="+productId;
					}else{ //跳转去下载APP
						
						$("#jumpPop").show();
						setTimeout(function(){
							$("#jumpPop").hide();
							toDownloadApp();
						},1500);
					}
				}
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

function receiveCoupon(couponId,productId) {
	$.ajax({                                       
		url: mainServer + '/weixin/customerinvite/receiveCoupon',
		data:{
			"couponId": couponId,
			"recordId": recordId
		}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1011"){	//未登录
				window.location.href = loginUrl;
			}else{
				if(data.code !="1010"){  //没有数据
					showvaguealert(data.msg);
				}else{
					showvaguealert("领取成功");
				}
				$("#btn"+productId).html("立即购买");
				$("#btn"+productId).attr("href", mainServer+"/weixin/product/towxgoodsdetails?productId="+productId);
			}
			
			
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}