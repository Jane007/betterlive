var nextIndex = 1;
var tempName = '';
var loadtobottom=true;
$(function(){
	 queryHomeLabel();
	var soulist=$(".soulei a");
	for(var i=0;i<soulist.length;i++){  
		soulist[i].index=i;
		soulist[i].onclick=function(){ 
			for(var k=0;k<soulist.length;k++){ 
				soulist[k].className="";   
			} 
			soulist[this.index].className="current"; 
		} 
		
	}
}) 






function clearsearch(){
	document.getElementById("productName").value="";
	$("#labelsId").show();
	$(".tuijianbox").html("");
	$(".sounobg").hide();
	$(window).scrollTop(0);
	loadtobottom=true;
} 
 
function queryProsByLabel(productNa,labelType){
	tempName = productNa;
	$(".sounobg").hide();
	$("#labelsId").hide();
	$(".tuijianbox").html("");
	$("#productName").val(tempName);
	nextIndex = 1;
	loadtobottom=true;
	$("#pageNext").val(1);
	$(window).scrollTop(0);
	queryProducts(tempName, labelType, 1, 10);
}


function queryByPro(productNa){
	$(".sounobg").hide();
	if(productNa.trim().length==0){
		$(".soulei").show();
		$(".remsou").show();
	}
	$("#labelsId").hide();
	$(".tuijianbox").html("");
	loadtobottom=true;
	tempName = productNa;
	nextIndex = 1;
	$("#pageNext").val(1);
	$(window).scrollTop(0);
	queryProducts(tempName, 1, 1,10);
}

//往上滑
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
					queryProducts(tempName, 1, next, 10);
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

function queryProducts(productName, labelType , pageIndex, pageSize){
	$(".initloading").show();

	if(productName == null || productName == ""){
		setTimeout(function(){
			$(".initloading").hide();
			$("#labelsId").show();
		},800);
		return;
	}
	$.ajax({                                       
		url: mainServer + '/weixin/searchProductAllJson',
		data:{
			"productName":productName,
			"labelType":labelType,
			rows:pageSize,
			pageIndex:pageIndex,
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
					setTimeout(function(){
						$(".initloading").hide();
						$(".sounobg").show();
						$("#labelsId").hide();
					},800);
					return;
				}else{
// 							$(".soulei").hide();
// 							$(".remsou").hide();
					$(".sounobg").hide();
// 							$("#labelsId").show();
				}
				var list = data.data;
				for (var continueIndex in list) {
					if(isNaN(continueIndex)){
						continue;
					}
					var proVo = list[continueIndex];
					var shareExplain = "";
					if(proVo.share_explain != null){
						shareExplain = proVo.share_explain;
						if(shareExplain.length > 25){
							shareExplain = shareExplain.substring(0, 25)+"...";
						}
					}
					
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
					
					var local = mainServer + "/weixin/product/towxgoodsdetails?productId="+proVo.product_id;
					if(proVo.activityType == 2){ //限量抢购
						local = mainServer + "/weixin/product/toLimitGoodsdetails?productId="+proVo.product_id+"&specialId="+proVo.activity_id;
					}else if(proVo.activityType == 3){ //团购
						local = mainServer + "/weixin/product/toGroupGoodsdetails?specialId="+proVo.activity_id+"&productId="+proVo.product_id;
					}
					
					var showLabel="";
					if(proVo.labelName != null && proVo.labelName != ""){
						showLabel = "<p>"+proVo.labelName+"</p>"; 
					}
					var showHtml = '<div class="tuijian" onclick="location.href=\''+local+'\'">'
						+'				<div class="left">'
						+'	 				<img src="'+proVo.product_logo+'" alt="" />'
						+'				</div>'
						+'				<div class="right">'
						+'					<div class="tjname">'
						+'						<span id="name'+proVo.product_id+'">' + proVo.product_name + '</span>'
						+						showLabel
						+'					</div>'
						+'					<div class="tjcent">'
						+						shareExplain
						+'					</div>'
						+'					<div class="tjmoney">'
						+						showMoneyLine
						+'						<p>销量'+proVo.salesVolume+'份</p>'
						+'					</div>'
						+'				</div>'
						+'			</div>';
						
						$(".tuijianbox").append(showHtml);
						if(showLabel != null && showLabel != "" && showLabel.length > 0){
							$("#name"+proVo.product_id).css("width","3.0rem")  
						}else{  
							$("#name"+proVo.product_id).css("width","4.1rem")  
						}
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

function queryHomeLabel() {
	$.ajax({                                       
		url:  mainServer + '/common/queryHomeLabel',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010" && data.data != null && data.data.labelId>0){
				$("#productName").val(data.data.labelName);  
				queryByPro(data.data.labelName);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

function toCancel(){
	if(backFlag == 1){
		window.location.href =  mainServer + "/weixin/index";
	}else{
		window.location.href =  mainServer + "/weixin/product/toProductsByType";
	}
}

// 修改微信自带的返回键 
$(function(){
	var url="";
	if(backType == 1){
		url = mainServer+"/weixin/index";
	}else{
		url = mainServer+"/weixin/product/toProductsByType";
	}
	
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
    },1000); 
        
	pushHistory(); 
	
    window.addEventListener("popstate", function(e) {
    	if(bool){
    		window.location.href=url;	
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