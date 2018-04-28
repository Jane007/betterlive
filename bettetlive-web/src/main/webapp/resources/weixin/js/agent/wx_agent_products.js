	var backUrl = window.location.href;
	$(function(){
		
		if(backUrl.indexOf("#")!=-1){
			backUrl = backUrl.substring(0,backUrl.indexOf("#"));
		}
		$(".initloading").show();
		setTimeout(function(){
			$(".initloading").hide();
		},800);

		queryProducts(1,10);
		
	});
	
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
	
	//商品列表
	function queryProducts(pageIndex,pageSize){
		$(".initloading").show();
		$.ajax({                                       
			url: mainServer + '/weixin/agentproduct/queryProductList',
			data:{
				rows:pageSize,pageIndex:pageIndex
			},
			type:'post',
			dataType:'json',
			success:function(data){
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
									 + "<strong>￥"+checkMoneyByPoint(proVo.specPrice)+"</strong></span>";
					}else if(proVo.discountPrice != null && proVo.discountPrice != "" && parseFloat(proVo.discountPrice) >= 0){
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.discountPrice)
						 + "<strong>￥"+checkMoneyByPoint(proVo.specPrice)+"</strong></span>";
					}else{
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.specPrice)+"</span>";
					}
					
					var shareExplain = proVo.shareExplain == null ? "" : proVo.shareExplain;
					var showLabel="";
					var nameCls = "";
					if(proVo.productLabel != null && proVo.productLabel != ""){
						showLabel = "<p>"+proVo.productLabel+"</p>"; 
						nameCls = "class='listjietitle'";
					}
					var local = mainServer + "/weixin/product/towxgoodsdetails?productId="+proVo.productId+"&backUrl="+backUrl;
					if(proVo.activityType == 2){ //限量抢购
						local = mainServer + "/weixin/product/toLimitGoodsdetails?productId="+proVo.productId+"&specialId="+proVo.activityId+"&backUrl="+backUrl;
					}else if(proVo.activityType == 3){ //团购
						local = mainServer + "/weixin/product/toGroupGoodsdetails?specialId="+proVo.activityId+"&productId="+proVo.productId+"&backUrl="+backUrl;
					}
					var showHtml = '<div class="tuijian" onclick="location.href=\''+local+'\'">'
						+'	 			<div class="left">'
						+'	 				<img src="'+proVo.productLogo+'" alt="" />'
						+'	 			</div>'
						+'	 			<div class="right">'
						+'	 			<div class="tjname">'
						+'	 				<span  id="name'+proVo.productId+'" '+nameCls+'>'+proVo.productName+'</span>'
						+					showLabel 
						+'	 			</div>'
						+'	 			<div class="tjcent">'
						+	 				shareExplain
						+'	 			</div>'
						+'	 			<div class="tjmoney">'
						+				showMoneyLine
						+'	 				<p>销量'+proVo.salesVolume+'份</p>'
						+'	 			</div>'
						+'	 		</div>'
						+'		</div>';
						 
						$("#showProduct").append(showHtml);
						if(showLabel != null && showLabel != "" && showLabel.length > 0){
							$("#name"+proVo.productId).css("width","2.8rem")  
						}else{  
							$("#name"+proVo.productId).css("width","4.1rem")  
						}
				}
				setTimeout(function(){
					$(".initloading").hide();
				},800);
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
	
	//修改微信自带的返回键
	$(function(){
		var bool=false;  
        setTimeout(function(){  
              bool=true;  
        },1000); 
            
		pushHistory(); 
		
	    window.addEventListener("popstate", function(e) {
	    	if(bool){
				window.location.href = mainServer+"/weixin/index";
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
