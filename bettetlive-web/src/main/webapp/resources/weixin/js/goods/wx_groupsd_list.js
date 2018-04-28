$(function(){
	$("#groupsGoods").html("");
	$("#groupsGoods").show();
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
						queryProducts(next, 10);
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
				url:mainServer+'/weixin/special/groupList',
				type:'post',
				data : {
					rows:pageSize,pageIndex:pageIndex
				},
				dataType:'json',
				success:function(data){
					if(data.code == "1010"){
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
							
							var	local = mainServer+"/weixin/product/toGroupGoodsdetails?specialId="+specialVo.specialId+"&productId="+proVo.product_id+"&backUrl="+mainServer+"/weixin/toGroupGoods";
							var btnCls = "xlqiang";
							var btn="去开团";
							if(specialVo.status != 1 || specialVo.longEnd <= data.currtime){
								btn="已结束";
								btnCls += " huibg";
							}else if(specialVo.longStart > data.currtime){
								btn="预售期";
								btnCls += " huibg";
							}else if(specialVo.total_stock_copy <= 0){
								btn="已抢完";
								btnCls += " huibg";
							}
							var showHtml='<div class="ptlistbox" onclick="location.href=\''+local+'\'">'
										+'	<img src="'+specialVo.specialCover+'" alt="" />'
										+'	<div class="ptlistbot">'
										+'		<span>'
										+'			<p class="pttitle">'+specialVo.specialName+'</p>'
										+'			<p class="ptfont">￥'+checkMoneyByPoint(proVo.activity_price)+'<strong>￥'+checkMoneyByPoint(proVo.spec_price)+'</strong><em>已团'+specialVo.fakeSalesCopy+'份</em></p>'
										+'		</span>'
										+'		<a class="'+btnCls+'" href="'+local+'"> '
										+          btn
										+'		</a>'
										+'	</div>'
										+'</div>';
								$("#groupsGoods").append(showHtml);
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
		
//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},1000);
}

//修改微信自带的返回键
$(function(){
			var bool=false;  
			var backUrl=mainServer+"/weixin/index";
            setTimeout(function(){  
                  bool=true;  
            },1000); 
	            
			pushHistory(); 
			
		    window.addEventListener("popstate", function(e) {
		    	if(bool){
		    		window.location.href=backUrl;
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