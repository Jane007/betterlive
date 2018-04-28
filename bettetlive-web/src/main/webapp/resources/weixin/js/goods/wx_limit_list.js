$(function(){
	$("#showProducts").html("");
	$("#showProducts").show();
	queryTab();
	queryProducts(1,10);
});
	
	
function queryTab(){
		$.ajax({                                       
			url:mainServer+'/classifybanner/queryClassifyBanner',
			data:{
				"bannerType":5
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //领取成功
					if(data.data == null || data.data.classifyBannerId <= 0){
						return;
					}
					var showHtml = '<img src="'+data.data.bannerImg+'" alt="" />';
					$(".xlbnbox").html(showHtml);
					var vo = data.data;
					if(vo.productId != null && vo.productId != "" && !isNaN(vo.productId) && parseInt(vo.productId) > 0){
						$(".xlbnbox").click(function(){
									var local = mainServer+"/weixin/product/towxgoodsdetails?productId="+vo.productId+"&backUrl="+link;
									if(vo.activityType == 2){ //限量抢购
										local = mainServer+"/weixin/product/toLimitGoodsdetails?productId="+vo.productId+"&specialId="+vo.specialId+"&backUrl="+link;
									}else if(vo.activityType == 3){ //团购
										local = mainServer+"/weixin/product/toGroupGoodsdetails?specialId="+vo.specialId+"&productId="+vo.productId+"&backUrl="+link;
									}
									location.href=local;
						});
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
	//往上滑
var loadtobottom=true;
var nextIndex = 1;
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
						queryProducts(next,10);
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
	

function queryProducts(pageIndex,pageSize){
		$(".initloading").show();
		$.ajax({                                      
			type:'post',
			url:mainServer+'/weixin/special/limitList',
			async: false,
			data: {rows:pageSize,pageIndex:pageIndex},
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
							setTimeout(function(){
								$(".initloading").hide();
							},800);
							return;
						}
						var list = data.data;
						for (var continueIndex in list) {
							if(isNaN(continueIndex)){
								continue;
							}
							var specialVo = list[continueIndex];
							var proVo = specialVo.productSpecVo;
							
							var shareExplain = "";
							if(specialVo.specialIntroduce != null){
								shareExplain = specialVo.specialIntroduce;
							}else if(proVo.share_explain != null){
								shareExplain = proVo.share_explain;
							}
							if(shareExplain.length > 25){
								shareExplain = shareExplain.substring(0, 25)+"...";
							}
							var local = mainServer+"/weixin/product/toLimitGoodsdetails?productId="+proVo.product_id+"&specialId="+specialVo.specialId+"&backUrl="+link;
							var btnCls = "xlqiang";
							var btn="马上抢";
							if(specialVo.status != 1 || specialVo.longEnd <= data.currtime){
								btn="已结束";
								btnCls += " huibg";
							}else if(specialVo.longStart > data.currtime){
								btn="即将开始";
								btnCls += " huibg";
							}else if(proVo.total_stock_copy <= 0){
								btn="已抢完";
								btnCls += " huibg";
							}
							var logo = specialVo.specialCover;
							if(logo=='' || logo==null){
								logo = mainServer+"/resources/weixin/img/goods/pro-default.png"
							}
							var stockCopy = proVo.total_stock_copy;
							if(stockCopy=='' || stockCopy==null){
								stockCopy = 0;
							}
							var showHtml='<div class="xlprobox" onclick="location.href=\''+local+'\'">'
										+' 	<div class="xlpro">'
										+' 		<div class="left">'
										+' 			<img src="'+logo+'" alt="" />'
										+' 		</div>'
										+' 		<div class="right">'
										+' 			<h3>'+specialVo.specialName+'</h3>'
										+' 			<span class="xljieshao">'+shareExplain+'</span>'
										+			'<span class="xljia">抢购价<strong>￥'+checkMoneyByPoint(proVo.activity_price)+'</strong>'
										+  			'<em>原价￥'+checkMoneyByPoint(proVo.spec_price)+'</em></span>'
										+' 			<span class="xlbtn">'
										+' 				<p class="shengyu"><strong>仅剩'+stockCopy+'份</strong><em></em></p>'
										+' 				<a href="'+local+'" class="'+btnCls+'">'+btn+'</a>'
										+' 			</span>'
										+' 		</div>'
										+' 	</div>'
										+'</div>';
								$("#showProducts").append(showHtml);
						}
						
						setTimeout(function(){
							$(".initloading").hide();
						},800);
				 }
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
	
// 修改微信自带的返回键
$(function() {
	var bool = false;
	
	var checkUrl = window.location.href;
	var backUrl = mainServer + "/weixin/index";
	if(checkUrl.indexOf("backUrl")!=-1){
		backUrl = getQueryString("backUrl");
	}
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			window.location.href = backUrl;
		}
	}, false);

	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}

});
