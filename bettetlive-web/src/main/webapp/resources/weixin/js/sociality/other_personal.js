$(function() {
	if(sex==1){//1是男 0 女 2 保密
		$('.bottom').find('h3').removeClass('wman').addClass('man');
	}else if(sex==0){
		$('.bottom').find('h3').removeClass('mman').addClass('wman');
	};
	$("#dataList").html("");
	queryDatas(1, 10);
	if(fansId<=0){
		$('.oattention').css({
			"background-color":"#e62d29",
			"border":"1px solid transparent"
		});
	}
});

$(document).on('click','.dz',function(){
	let num = $(this).html();
	if($(this).hasClass('on')){
		num--;
		$(this).removeClass('on');
		$(this).html(num);
		$.toast("取消点赞","text");
	}else{
		num++;
		$(this).addClass('on');
		$(this).html(num);
		$.toast("点赞成功","text");
	}
})


var loadtobottom=true;
var nextIndex = 1;

$(document).scroll(function(){     
	var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
	if($(document).height() <= totalheight){	
		    
		if(loadtobottom==true){
			var next = $("#pageNext").val();
			var pageCount = $("#pageCount").val();
			var pageNow = $("#pageNow").val(); 
			if(parseInt(next)>1){  
				if(nextIndex != next){
					nextIndex++;
					queryDatas(next,10);      
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




function queryDatas(pageIndex,pageSize){
	 $(".initloading").show();
		$.ajax({                                       
			url:mainServer+'/weixin/socialityhome/queryOtherDynamicList',
			data: {rows:pageSize,pageIndex:pageIndex,otherCustId:$("#otherCustId").val()},
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
					
					if((data.data == null || data.data.length <= 0) && pageIndex == 1){
						setTimeout(function(){
							$(".initloading").hide();
							$(".zanwubg").show();
						},1000);
						return;
					}else{
						$(".zanwubg").hide();
					}
				
					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						var specialVo = list[continueIndex];

						var praiseClz = "dz";
						var clickPraise = "lineAddOrCancelPraise(0, 'msdz"+specialVo.articleId+"', "+specialVo.articleId+", 0,4)";
						if(specialVo.isPraise > 0){
							praiseClz += " on";
							clickPraise = "lineAddOrCancelPraise(1, 'msdz"+specialVo.articleId+"', "+specialVo.articleId+","+specialVo.isPraise+",4);";
						}

						var clickLocal = "location.href='"+mainServer+"/weixin/discovery/toDynamicDetail?articleId="+specialVo.articleId+"&backUrl=" + backUrl+"'";
						
						var showHtml = [
											'<li>',
												'<dl>',
													'<dt>',
														'<a onclick="'+clickLocal+'">',
															'<img src="'+specialVo.articleCover+'"'+ "onerror=nofind(this)" +' />',
														'</a>',
													'</dt>',
													'<dd>',
														'<p onclick="'+clickLocal+'">'+specialVo.articleTitle+'</p>',
														'<div class="dbot">',
															'<span class="time">'+specialVo.fomartTime+'</span>',
															'<span class="'+praiseClz+'" id="msdz'+specialVo.articleId+'" onclick="'+clickPraise+'">'+specialVo.praiseCount+'</span>',
														'</div>',
													'</dd>',
												'</dl>',
											'</li>'	                
						                ].join("");
						$(".prolist").append(showHtml);  
					};
					imgLoad();
					setTimeout(function(){
						$(".initloading").hide();
					},800);
				}else{
					$.toast("出现异常","text");
				}
			},
			failure:function(data){
				$.toast("出错了","text");
			}
		});
}
//图片错误替换默认图片
function nofind(img){ 
	img.src=resourcepath+"/weixin/img/home/rush-defult.png"; 
    img.onerror=null; 
}; 
var imgLoad=function(){
	$('.prolist img').load(function(){
		$('.prolist img').each(function(){
			if($(this).width() > $(this).height()){
				$(this).css({
					'width':'auto',
					'height':'100%'
				})
			}else{
				$(this).css({
					'width':'100%',
					'height':'auto'
				})
			}
		});
	})
}

function lineAddOrCancelPraise(flag,praiseLineId,specialId,praiseId,praiseType){
	var customerId = $("#myCustId").val();
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
		return;
	}
	
 	if((flag != 0 && flag != 1) || isNaN(flag)){
 		$.toast("出现异常","text");
 		return;
 	}
 	if(praiseLineId == null || praiseLineId == ""){
 		$.toast("出现异常","text");
 		return;
 	}
 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
 		$.toast("出现异常","text");
			return;
		}
 	var url = mainServer+"/weixin/praise/addPraise";
 	var data={
 		"praiseType":praiseType,
 		"objId":specialId
 	};
 	if(flag == 1){
 		url = mainServer+"/weixin/praise/cancelPraise";
 		
 		if(praiseId == null || praiseId == "" || isNaN(praiseId) || parseFloat(praiseId) <= 0){
 			$.toast("出现异常","text");
 			return;
 		}
 		data={
	 		"praiseId":praiseId
	 	};
 	}
	$("#"+praiseLineId).attr("onclick", "void(0);");
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(0,'"+praiseLineId+"',"+specialId+",0,"+praiseType+")");
					
				}else{
					$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(1,'"+praiseLineId+"',"+specialId+","+data.data+","+praiseType+")");
					
				}
			}else{
				$.toast(data.msg,"text");
			}
		},
		failure:function(data){
			$.toast("访问出错","text");
		}
	});
}


function concernedOrCancel(fansId){
		var concernId = $("#otherCustId").val();
		if(fansId == null || fansId < 0){
			$.toast("访问数据出错","text");
			return false;
		}
		var url = mainServer+"/weixin/concern/addConcern";
		var data={};
		if(fansId > 0){ //取消关注
			
			$('.bkbg').show();
			$('.shepassdboxs').show();
		}else{
			if(concernId == null || concernId < 0){
				$.toast("访问数据出错","text");
 			return false;
			}
			data={
		 		"concernCustId":concernId
	 	};
			$("#guanzhuId").attr("href", "javascript:void(0);");
			sendAjaxToChangeAttention(url,data)
		}
		
		
	}

function makeSureCancelAttention(){
	var url = mainServer+"/weixin/concern/cancelConcern";
	var data={
 		"fansId":fansId
	};
	$("#guanzhuId").attr("href", "javascript:void(0);");
	$('.bkbg').hide();
	$('.shepassdboxs').hide();
	sendAjaxToChangeAttention(url,data);
}

function sendAjaxToChangeAttention(url,data){
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(fansId > 0){
					$("#guanzhuId").html("+关注");
					$("#guanzhuId").attr("class", "oattentionon");
					$("#guanzhuId").attr("href", "javascript:concernedOrCancel(0);");
					var fansCount = parseInt($("#fansCount").html());
					fansId = 0;
					$("#fansCount").html(fansCount-1);
					$.toast("已取消关注","text");
					$('.oattention').css({
						"background-color":"#e62d29",
						"border":"1px solid transparent"
					});
				}else{
					$("#guanzhuId").html("已关注");
					$("#guanzhuId").attr("class", "hasattention");
					$("#guanzhuId").attr("href", "javascript:concernedOrCancel("+data.data+");");
					fansId = data.data;
					var fansCount = parseInt($("#fansCount").html());
					$("#fansCount").html(fansCount+1);
					$.toast("已关注","text");
					$('.oattention').css({
						"background-color":"transparent",
						"color": "#ebe0de",
						"border":"1px solid #e7ded9"
					});
				}
			}else if (data.code == "1011"){
				window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
			}else{
				$.toast(data.msg,"text");
			}
		},
		failure:function(data){
			$.toast("访问出错","text");
		}
	});
}

function closeConcernAlert(){
	 $(".bkbg").hide();
	 $(".shepassdboxs").hide();
}
//修改微信自带的返回键 -
$(function(){
	var url=mainServer+"/weixin/discovery/toDynamic";
	
	if(backUrl != null && backUrl != "" && backUrl != undefined){
		url = backUrl;
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
  //返回上一个页面
	$('.fan').click(function(){
		window.location.href=url;	
	});
});
