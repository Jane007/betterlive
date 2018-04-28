	
	var extensionType = $("#extensionType").val();
	var backUrl = mainServer+"/weixin/product/productList?extensionType="+extensionType;
	
	
	
	$(function(){
		$(".initloading").show();
		setTimeout(function(){
			$(".initloading").hide();
		},800);
		
		$("#showProduct0").html("");
		$("#showProduct0").show();
		queryTab();
		queryProducts(1,10);
		
	});
	
	//提示弹框
	function showvaguealert(con){
		$('.vaguealert').show();
		$('.vaguealert').find('p').html(con);
		setTimeout(function(){
			$('.vaguealert').hide();
		},1000);
	}
	
	//分类图
	function queryTab(){
		var type = 6;
		if(extensionType == 2){
			type = 7;
		}
		$.ajax({                                       
			url: mainServer + '/classifybanner/queryClassifyBanner',
			data:{
				"bannerType":type
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //领取成功
					if(data.data == null || data.data.classifyBannerId == undefined || data.data.classifyBannerId <= 0){
						$("#banner0").hide();
						return;
					}
					$("#banner0").find("img").attr("src", data.data.bannerImg);
					var vo = data.data;
					if(vo.productId != null && vo.productId != "" && !isNaN(vo.productId) && parseInt(vo.productId) > 0){
						$("#banner0").click(function(){
									var local = mainServer + "/weixin/product/towxgoodsdetails?productId="+vo.productId+"&backUrl="+backUrl;
									if(vo.activityType == 2){ //限量抢购
										local = mainServer + "/weixin/product/toLimitGoodsdetails?productId="+vo.productId+"&specialId="+vo.specialId+"&backUrl="+backUrl;
									}else if(vo.activityType == 3){ //团购
										local = mainServer + "/weixin/product/toGroupGoodsdetails?specialId="+vo.specialId+"&productId="+vo.productId+"&backUrl="+backUrl;
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
			url: mainServer + '/weixin/product/productListAllJson',
			data:{
				rows:pageSize,pageIndex:pageIndex,"extensionType":extensionType
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
				
				if(data.list == null || data.list.length <= 0){
					return;
				}
				var list = data.list;
				for (var continueIndex in list) {
					if(isNaN(continueIndex)){
						continue;
					}
					var proVo = list[continueIndex];
					var showMoneyLine = "";
					if(proVo.activityPrice != null && proVo.activityPrice != "" && parseFloat(proVo.activityPrice) >= 0){
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.activityPrice)
									 + "<strong>￥"+checkMoneyByPoint(proVo.price)+"</strong></span>";
					}else if(proVo.discountPrice != null && proVo.discountPrice != "" && parseFloat(proVo.discountPrice) >= 0){
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.discountPrice)
						 + "<strong>￥"+checkMoneyByPoint(proVo.price)+"</strong></span>";
					}else{
						showMoneyLine = "<span>￥"+checkMoneyByPoint(proVo.price)+"</span>";
					}
					
					var shareExplain = "";
					if(proVo.share_explain != null){
						shareExplain = proVo.share_explain;
						if(shareExplain.length > 13){
							shareExplain = shareExplain.substring(0, 13)+"...";
						}
					}
					var showLabel="";
					var nameCls = "";
					if(proVo.labelName != null && proVo.labelName != ""){
						showLabel = "<p>"+proVo.labelName+"</p>"; 
						nameCls = "class='listjietitle'";
					}
					var local = mainServer + "/weixin/product/towxgoodsdetails?productId="+proVo.product_id+"&backUrl="+backUrl;
					if(proVo.activityType == 2){ //限量抢购
						local = mainServer + "/weixin/product/toLimitGoodsdetails?productId="+proVo.product_id+"&specialId="+proVo.activity_id+"&backUrl="+backUrl;
					}else if(proVo.activityType == 3){ //团购
						local = mainServer + "/weixin/product/toGroupGoodsdetails?specialId="+proVo.activity_id+"&productId="+proVo.product_id+"&backUrl="+backUrl;
					}
					var showHtml = '<div class="tuijian" onclick="location.href=\''+local+'\'">'
						+'	 			<div class="left">'
						+'	 				<img src="'+proVo.product_logo+'" alt="" />'
						+'	 			</div>'
						+'	 			<div class="right">'
						+'	 			<div class="tjname">'
						+'	 				<span  id="name'+proVo.product_id+'" '+nameCls+'>'+proVo.product_name+'</span>'
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
						 
						$("#showProduct0").append(showHtml);
						if(showLabel != null && showLabel != "" && showLabel.length > 0){
							$("#name"+proVo.product_id).css("width","2.8rem")  
						}else{  
							$("#name"+proVo.product_id).css("width","4.1rem")  
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
