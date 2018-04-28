var backUrl = mainServer + "/weixin/product/toProductsByType?whoClick="+whoClick;

var tabIndex = 0;
var nextIndex = 1;
var loadtobottom=true;
var newUrl = '';
$(function(){
	//判断消息数量小图标是否显示
	var checkMsgNum = $('.msg').find('span').html();
	if(checkMsgNum > 0){
		$('.msg').find('span').css('visibility','visible')
		if(checkMsgNum>99){
			$('.msg').find('span').html("99+").css('font-size','10px');
		}
	}else{
		$('.msg').find('span').css('visibility','hidden')
	};
	
	
	//购物车数据处理
	var cartCnt = $('.footer').find('span').html();
	if(cartCnt==0 || cartCnt == null){
		$('.gwnb').hide();
	}else if(cartCnt>99){
		$('.footer').find('span').html("99+");
	}//否则就不处理，照常显示

	queryHomeLabel();
	//保存所有tab图片的地址
	var imgArr = [];
	$('.tab li img').each(function(){
		imgArr.push($(this).attr('src'))
	});
	
	$('.tab li').click(function(){
		var imgstr = $(this).find('img').attr('src').split('-0')[0];
		$('.tab li').find('dl').removeClass('isclick');
		$('.content-wrap').css("display",'none'); 
		$(this).find('dl').addClass('isclick');
		//循环给tab下面的图片添加路径
		$('.tab li img').each(function(index,val){
			$(this).attr('src',imgArr[index])
		});
		//改变当前点击图片路径
		$(this).find('img').attr('src',imgstr + '-0'+ '2.png');
		$("#showProduct"+$(this).index()).html("");
		$("#showContent"+$(this).index()).css("display",'block');
		tabIndex = $(this).index();
		$("#pageNext").val(1);
		loadtobottom = true;
		nextIndex = 1;
		$(window).scrollTop(0);
		var stateObject = {};
		var title = "Wow Title";
		newUrl = mainServer+"/weixin/product/toProductsByType?whoClick="+$(this).index();
		history.pushState(stateObject,title,newUrl);
		refreshData($(this).index(), 1, 10);
		
	})
	
	$(".tab li:eq("+whoClick+")").trigger("click");
	
	
});



function refreshData(idx,pageIndex,pageSize){
	$(".loadingmore").show();  
	 setTimeout(function(){    	
		$(".loadingmore").hide(); 	
		
	},1000);
	queryProducts(idx,pageIndex,pageSize);
}

function queryHomeLabel() {
	$.ajax({                                       
		url: mainServer + '/common/queryHomeLabel',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010" && data.data != null && data.data.labelId>0){
				$(".searchBox").val(data.data.labelName);  
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/*
function queryTab(idx){
	var btype = 0; //全部
	if(idx == 1){ //鲜香果蔬
		btype = 1;
	}else if(idx == 2){ //零食特产
		btype = 3;
	}else if(idx == 3){ //网红推荐
		btype = 4;
	}else if(idx == 4){ //厨房百味
		btype = 2;
	}
	$.ajax({                                       
		url: mainServer + '/classifybanner/queryClassifyBanner',
		data:{
			"bannerType":btype,
		},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code != "1010"){ //领取成功
				$("#banner"+idx).hide();
				return;
			}
			var vo = data.data;
			if(vo == null || vo.classifyBannerId == undefined
					|| vo.classifyBannerId <= 0){
				$("#banner"+idx).hide();
				return;
			}
			
			$("#banner"+idx).find("img").attr("src", vo.bannerImg);
			if(vo.productId != null && vo.productId != "" && !isNaN(vo.productId) && parseInt(vo.productId) > 0){
				$("#banner"+idx).click(function(){
							var local = mainServer + "/weixin/product/towxgoodsdetails?productId="+vo.productId+"&backUrl="+backUrl;
							if(vo.activityType == 2){ //限量抢购
								local = mainServer + "/weixin/product/toLimitGoodsdetails?productId="+vo.productId+"&specialId="+vo.specialId+"&backUrl="+backUrl;
							}else if(vo.activityType == 3){ //团购
								local = mainServer + "/weixin/product/toGroupGoodsdetails?specialId="+vo.specialId+"&productId="+vo.productId+"&backUrl="+backUrl;
							}
							location.href=local;
				});
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}
*/

$(document).scroll(function(){
	totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
	if($(document).height() <= totalheight){
		if(loadtobottom==true){
			var next = $("#pageNext").val();
			var pageCount = $("#pageCount").val();
			var pageNow = $("#pageNow").val();
			
			if(parseInt(next)>1){
				if(nextIndex != next){
					nextIndex++;
					queryProducts(tabIndex, next, 10);
					$(".loadingmore").show();  
					 setTimeout(function(){    	
						$(".loadingmore").hide(); 	
						
					},1500); 
				}
			}
			if(parseInt(next)>=parseInt(pageCount)){
				loadtobottom=false;
			} 
			
		}
	}
});

function queryProducts(idx,pageIndex,pageSize){
	$(".initloading").show();
	
	var btype = 0; //全部
	if(idx == 1){ //星球推荐
		btype = 4;
	}else if(idx == 2){ //网红零食
		btype = 3;
	}else if(idx == 3){ //必备零食
		btype = 1;
	}else if(idx == 4){ //其他
		btype = 2;
	}
	$.ajax({                                       
		url: mainServer + '/weixin/product/queryProductListByType',
		data:{
			rows:pageSize,pageIndex:pageIndex,"productType":btype
		},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				
				var pageNow = data.pageInfo.pageNow;
				var pageCount = data.pageInfo.pageCount;
				$("#pageCount").val(pageCount);
				$("#pageNow").val(pageNow);
				var next = parseInt(pageNow)+1;
				if(next>=pageCount){
					next=pageCount;
				}
				$("#pageNext").val(next);
				$("#pageNow").val(pageNow);
				
				if(data.data == null || data.data.length <= 0){
					$("#showProduct"+idx).hide();
					return;
				}
				var list = data.data;
				for (var continueIndex in list) {
					if(isNaN(continueIndex)){
						continue;
					}
					var proVo = list[continueIndex];
					var showMoneyLine = "";
					if(proVo.activityPrice != null && proVo.activityPrice != "" && parseFloat(proVo.activityPrice) >= 0){
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.activityPrice)
									 + "</span><span>￥"+checkMoneyByPoint(proVo.price)+"</span>";
					}else if(proVo.discountPrice != null && proVo.discountPrice != "" && parseFloat(proVo.discountPrice) >= 0){
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.discountPrice)
						 + "</span><span>￥"+checkMoneyByPoint(proVo.price)+"</span>";
					}else{
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.price)+"</span>";
					}
					
					var shareExplain = "";
					if(proVo.share_explain != null){
						shareExplain = proVo.share_explain;
					}
					var local = mainServer + "/weixin/product/towxgoodsdetails?productId="+proVo.product_id+"&backUrl="+backUrl;
					if(proVo.activityType == 2){ //限量抢购
						local = mainServer + "/weixin/product/toLimitGoodsdetails?productId="+proVo.product_id+"&specialId="+proVo.activity_id+"&backUrl="+backUrl;
					}else if(proVo.activityType == 3){ //团购
						local = mainServer + "/weixin/product/toGroupGoodsdetails?specialId="+proVo.activity_id+"&productId="+proVo.product_id+"&backUrl="+backUrl;
					}
					
					var showLabel="";
					if(proVo.labelName != null && proVo.labelName != ""){
						showLabel = '<span>'+proVo.labelName+'</span>';
					}
					var showHtml = '<li onclick="location.href=\''+local+'\'">'
						+'<div class="pro-top">'
						+'<img src="'+proVo.product_logo+'"/>'
						+showLabel+'</div>'
						+'<div class="pro-bottom">'
						+'<h3>'+proVo.product_name+'</h3>'
						+'<h4>'+shareExplain+'</h4>'
						+'<div class="price">'
						+showMoneyLine
						+'</div></div></li>';
					
						$("#showProduct"+idx).append(showHtml);
						
				}
				setTimeout(function(){
					$(".initloading").hide();
				},800);
			}else{
				showvaguealert("出现异常");
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}