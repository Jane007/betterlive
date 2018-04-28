$(function(){	
		var pem = $(".protitle").find("em").html();
		if(pem != null && pem != "" && pem.length > 0){
			$(".protitle").find("h3").css("width", "5.5rem");
		}else{
			$(".protitle").find("h3").css("width", "auto");
		}
			
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
			 
});
		
var limitNum=-1;
var hasBuy=0;        //已经购买的份数
var restCopy=100;	//剩余购买数量
		
function toAddBuy(){	
	if(null==customerId || 0==customerId){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+back;
		return false;
	}
			
	if(null==mobile || ""==mobile){
		window.location.href = mainServer+"/weixin/usercenter/toBoundPhone?backUrl="+back;
		return false;
	}
			
			
	if(myCust != null && myCust > 0 && myCust == orig){
		showvaguealert('不能参与自己开的团！');
		return false;
	}
			
	var productId=$("#productId").val();
	if(""==productId||null ==productId){
	    showvaguealert('请选择商品！');
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
			
	 	$.ajax({                                       
			url: mainServer+"/weixin/productgroup/checkJoinGroup",
			data:{
				"userGroupId":$("#userGroupId").val(),
				"productId":productId,
				"specialId":$("#specialId").val()
			},
			type:'post',
			dataType:'json',
			success:function(data){
					if(data.code == "1010"){ //去参团
						$("#buyLine").attr("href", "javascript:void(0);");
						$(".ctbtnbox").addClass("ctshibai");
						$("#buyForm").submit();
					}else{
						showvaguealert(data.msg);
					}
				},
				failure:function(data){
					showvaguealert('访问出错');
				}
		});
	    	
}
		
function shareProduct(){
	var productId=$("#productId").val();
	 	$.ajax({                                       
			url: mainServer+"/weixin/share/addShare",
			data:{
				"shareType":1,
				"objId":productId
			},
			type:'post',
			dataType:'json',
			success:function(data){
				showvaguealert('分享成功');
			},
				
			failure:function(data){
					showvaguealert('访问出错');
				}
			});
}
		 
//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
			$('.vaguealert').hide();
		},20000);
}



$(function(){
	queryLikeProducts();
});

//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},2000);
}

function queryLikeProducts(){
	$.ajax({                                       
		url:mainServer+'/weixin/product/queryLikeProducts',
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
					var local = mainServer+"/weixin/product/towxgoodsdetails?productId="+productVo.product_id;
					if(productVo.activityType == 2){ //限量抢购
						local = mainServer+"/weixin/product/toLimitGoodsdetails?productId="+productVo.product_id+"&specialId="+productVo.activity_id;
					}else if(productVo.activityType == 3){ //团购
						local = mainServer+"/weixin/product/toGroupGoodsdetails?specialId="+productVo.activity_id+"&productId="+productVo.product_id;
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
								+'		<span id="name'+productVo.product_id+'">' + productVo.product_name + '</span>'
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
								+'</div>';
					$(".tuijianbox").append(showHtml);
					if(showLabel != null && showLabel != "" && showLabel.length > 0){
						$("#name"+productVo.product_id).css("width","2.6rem")    
					}else{  
						$("#name"+productVo.product_id).css("width","4rem")  
					}	
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
		
	
		
		 