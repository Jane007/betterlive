var backUrl = mainServer + "/weixin/productredeem/toGoldBuy";
$(function() {
	productRedeem.inIt()
});
var productRedeem = {
	obj:{
		url: mainServer + "/weixin/productredeem/queryProductRedeems", //请求接口
	pageIndex:1,
	pageCount:''	//总页数
},
//上拉加载
loadMore: function() {
	var oSelf = this;
	var loading = false;
	$(document.body).infinite();
	$(document.body).infinite().on("infinite",function(){
		if(loading) return;
		loading = true;
		$('.weui-loadmore').show();
		setTimeout(function(){
			if(oSelf.obj.pageIndex <= oSelf.obj.pageCount) {
				oSelf.obj.pageIndex++;
				if(oSelf.obj.pageIndex <= oSelf.obj.pageCount) {
					oSelf.queryProductRedeems()
				}
			}else {
				$(document.body).destroyInfinite();
			};
			loading = false;
			$('.weui-loadmore').hide()
		}, 1500)
	})
},
//请求数据
queryProductRedeems:function(){
	var oSelf = this;
	$.post(oSelf.obj.url,{
		pageIndex: oSelf.obj.pageIndex,
		rows: 10
	},function(data, error){
		if (data.code == "1010") {
			oSelf.obj.pageCount = data.pageInfo.pageCount;
			oSelf.establishpage(data.data);
		}else if (data.code == "1011"){
			window.location.href = mainServer + "/weixin/tologin";
		}
	});
},
//渲染页面
establishpage:function(list){
	if (list.length == 0) {
		$('.nodata').show();
	};
	for (var i = 0; i < list.length; i++) {
		var product = list[i];
		var productLogo = resourcePath + "/weixin/img/integral/goldbuy_03.png";
		if(product.productLogo != null && product.productLogo != ""){
			productLogo = product.productLogo;
		}
		
		var showCurrentMoney = "";
		var orgStatus = "block";
		if(product.activityId != null && product.activityType != null && product.activityPrice != null 
				&& product.activityPrice != "" && parseFloat(product.activityPrice) >= 0){
			showCurrentMoney = checkMoneyByPoint(product.activityPrice);
		}else if(product.discountPrice != null && parseFloat(product.discountPrice) >= 0){
			showCurrentMoney = checkMoneyByPoint(product.discountPrice);
		}else{
			showCurrentMoney = checkMoneyByPoint(product.specPrice);
		}
		
		if(parseFloat(product.specPrice) == parseFloat(showCurrentMoney)){
			orgStatus = "none";
		}
		
		var buyStatusLine = product.totalStockCopy == 0 ? '<div class="btn off">已抢完</div>' : '<div class="btn">立即抢</div>';
		
		var showHtml = [
						'<li data-product-id="' + product.productId + '" data-spec-id="' + product.specId 
							+ '" data-activity-id="' + product.activityId + '" data-activity-type="' + product.activityType + '" >',
						'<dl>',
							'<dt>',
								'<img src="' + productLogo + '"/>',
							'</dt>',
							'<dd>',
								'<h3 class="ellipse">' + product.productName + '</h3>',
								'<div class="price">',
									'<span class="currentprice">￥' + showCurrentMoney + "+" + checkMoneyByPoint(product.needIntegral) + '个<img src="' + resourcePath + '/weixin/img/integral/goldbuy_02.png"/></span>', 
									'<span class="originalprice" style="display:' + orgStatus + ';">￥' + product.specPrice + '</span>',
								'</div>',
								'<div class="limitof">限量' + product.totalStockCopy + '份</div>',
								buyStatusLine,
							'</dd>',
						'</dl>',
						'</li>'	
		               ].join('');
		$('.list').append(showHtml);
	}
},

listLiClick: function (){
	$('.list').on("click", 'li', function(){
		var productId = $(this).attr("data-product-id");
		var specId = $(this).attr("data-spec-id"); 
		var activityId = $(this).attr("data-activityId-id"); 
		var activityType = $(this).attr("data-activity-type"); 
		var local = mainServer + "/weixin/product/towxgoodsdetails?productId="+productId+"&specId="+specId+"&backUrl="+backUrl;
		if(activityId != null && activityType != null){ 
			var methodName = "toLimitGoodsdetails"; //限量
			if (activityType == 3){ //团购
				methodName = "toGroupGoodsdetails";
			}
			if(activityType == 2 || activityType == 3){
				local = mainServer + "/weixin/product/" + methodName + "?specialId="+activityId+"&productId="+productId+"&specId="+specId+"&backUrl="+backUrl;
			}
		}
		location.href = local;
	});
},
	inIt:function(){
		this.queryProductRedeems();
		this.loadMore();
		this.listLiClick();
	}
};

//微信自带返回
$(function(){
	var url=mainServer+"/weixin/integral/toIntegral";
	
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
    },1000); 
        
	pushHistory(); 
	
    window.addEventListener("popstate", function(e) {
    	if(bool){
    		window.location.href = url;	
    	}
    }, false);
    
    function pushHistory(){ 
        var state = { 
    		title: "title", 
    		url:"#"
    	}; 
    	window.history.pushState(state, "title","");  
    }
    
    
  //返回上一个页面
	$('.fan').click(function(){
		window.location.href=url;	
	});
});

