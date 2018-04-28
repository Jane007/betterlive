 $(function(){
		$(".initloading").show();
		setTimeout(function(){
			$(".initloading").hide();
		},800);
			
	$("#commentsId").html("");
	queryComments(1,10);
 	anniversary.getStorage()
});
 var anniversary = {
	 //读取缓存
	 getStorage:function(){
		 var anniversaryObj = JSON.parse(localStorage.getItem("selected"));
		 var timer = new Date().getTime();
		 if(anniversaryObj && (timer - anniversaryObj.storageTime < 3600000 * 24)){
			 $('.shareimg').hide()
		 }else{
			 $('.shareimg').show()
		 }
	 }
 }
function addOrCancelPraise(flag,specialId,praiseId){	
	
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+theUrl;
			return;
		}
		
	 	if((flag != 0 && flag != 1) || isNaN(flag)){
	 		showvaguealert("出现异常");
	 		return;
	 	}
	 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
	 	var url = mainServer+"/weixin/praise/addPraise";
	 	var data={
	 		"praiseType":4,
	 		"objId":specialId,
	 		"shareCustomerId":shareCustomerId
	 	};
	 	if(flag == 1){
	 		url = mainServer+"/weixin/praise/cancelPraise";
	 		if(praiseId == null || praiseId == "" || isNaN(praiseId) || parseFloat(praiseId) <= 0){
	 			showvaguealert('出现异常啦');
	 			return;
	 		}
	 		data={
		 		"praiseId":praiseId
		 	};
	 	}
		$("#pid").attr("onclick", "void(0);");
		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(flag == 1){
						$("#pid").attr("onclick", "addOrCancelPraise(0,"+specialId+",0)");
						$("#pid").removeClass('currents');
						var pct = parseInt($("#praiseCountId").html());
						if(pct > 0){
							$("#praiseCountId").html(pct-1);
						}
						showvaguealert("已取消点赞");
					}else{
						$("#pid").attr("onclick", "addOrCancelPraise(1,"+specialId+","+data.data+")");
						$("#pid").addClass('currents');
						var pct = parseInt($("#praiseCountId").html());
						$("#praiseCountId").html(pct+1);
						showvaguealert("点赞成功");
					}
				}else{
					showvaguealert(data.msg);
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
			}
		});
	}
	
function addOrCancelCollection(flag,specialId,collectionId){
		var customerId = "${customerId}";
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+theUrl;
			return;
		}
		
	 	if((flag != 0 && flag != 1) || isNaN(flag)){
	 		showvaguealert("出现异常");
	 		return;
	 	}
	 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
	 	var url = mainServer+"/weixin/collection/addCollection";
	 	var data={
	 		"collectionType":4,
	 		"objId":specialId
	 	};
	 	if(flag == 1){
	 		url = mainServer+"/weixin/collection/cancelCollection";
	 		if(collectionId == null || collectionId == "" || isNaN(collectionId) || parseFloat(collectionId) <= 0){
	 			showvaguealert('出现异常啦');
	 			return;
	 		}
	 		data={
		 		"collectionId":collectionId
		 	};
	 	}
		$("#colId").attr("onclick", "void(0);");
		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(flag == 1){
						$("#colId").attr("onclick", "addOrCancelCollection(0,"+specialId+",0)");
						$("#colId").removeClass('scsp2');
						showvaguealert("已取消收藏");
					}else{
						$("#colId").attr("onclick", "addOrCancelCollection(1,"+specialId+","+data.data+")");
						$("#colId").addClass('scsp2');
						showvaguealert("收藏成功");
					}
				}else{
					showvaguealert(data.msg);
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
			}
		});
	}
	
function lineAddOrCancelPraise(flag,praiseLineId,commentId,praiseId){
		var customerId = "${customerId}";
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
			return;
		}
		
	 	if((flag != 0 && flag != 1) || isNaN(flag)){
	 		showvaguealert("出现异常");
	 		return;
	 	}
	 	if(praiseLineId == null || praiseLineId == ""){
	 		showvaguealert("出现异常");
	 		return;
	 	}
	 	if(commentId == null || commentId == "" || isNaN(commentId) || parseFloat(commentId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
	 	var url = mainServer+"/weixin/praise/addPraise";
	 	var data={
	 		"praiseType":5,
	 		"objId":commentId
	 	};
	 	if(flag == 1){
	 		url = mainServer+"/weixin/praise/cancelPraise";
	 		if(praiseId == null || praiseId == "" || isNaN(praiseId) || parseFloat(praiseId) <= 0){
	 			showvaguealert('出现异常啦');
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
						$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(0,'"+praiseLineId+"',"+commentId+",0)");
						$("#"+praiseLineId).attr('class', 'dianzan');
						var praiseCount = $("#"+praiseLineId).html();
						$("#"+praiseLineId).html(parseFloat(praiseCount)-1);
						showvaguealert("已取消点赞");
					}else{
						$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(1,'"+praiseLineId+"',"+commentId+","+data.data+")");
						$("#"+praiseLineId).attr('class', 'dianzan2');
						var praiseCount = $("#"+praiseLineId).html();
						$("#"+praiseLineId).html(parseFloat(praiseCount)+1);
						showvaguealert("点赞成功");
					}
				}else{
					showvaguealert(data.msg);
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
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
						queryComments(next,10);
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
	
function queryComments(pageIndex,pageSize){
		$(".initloading").show();
		$.ajax({                                       
			url:mainServer+'/weixin/specialcomment/specialCommentDetail',
			data:{
				rows:pageSize,pageIndex:pageIndex,
				"specialId":objId,
				"commentPraiseType":5,
				"specialType":5
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
					
					if((data.data == null || data.data.length <= 0) && pageIndex == 1){
						 setTimeout(function(){
							$(".initloading").hide();
						    $(".shafabg").show();
						 },800);
						return;
					}else{
						$(".shafabg").hide();
					}

					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						var commentVo = list[continueIndex];
						var clickPraise = "lineAddOrCancelPraise(0, 'dz"+commentVo.commentId+"', "+commentVo.commentId+", 0)";
						var praiseCls = "dianzan";
						if(commentVo.isPraise > 0){
							clickPraise = "lineAddOrCancelPraise(1, 'dz"+commentVo.commentId+"', "+commentVo.commentId+","+commentVo.isPraise+")";
							praiseCls = "dianzan2";
						}
					
						var commentDetailLocal = "location.href='"+mainServer+"/weixin/discovery/toCommentDetail?articleId="+commentVo.specialId+"&commentId="+commentVo.commentId+"&commentPraiseType=5&backUrl="+theUrl+"'";
						var showHtml = '<div class="plcent" >'
							    	+'		<div class="plcttop">'
							    	+'			<span class="one" onclick="toSocialityHome('+commentVo.customerVo.customer_id+')">'
							    	+'				<img src="'+commentVo.customerVo.head_url+'" alt="">'
							    	+'			</span>'
							    	+'			<span class="two" onclick="toSocialityHome('+commentVo.customerVo.customer_id+')">'
							    	+				commentVo.customerVo.nickname
							    	+'			</span>'
							    	+'			<span class="three" onclick="'+commentDetailLocal+'">'
							    	+				commentVo.createTime
							    	+'			</span>'
							    	+'		</div>'
							    	+'		<div class="dchfcent" onclick="'+commentDetailLocal+'">'
							    	+			commentVo.content
							    	+'		</div>'
							    	+'		<div class="plbotbox">'			
							     	+'			<span id="dz'+commentVo.commentId+'" class="'+praiseCls+'" onclick="'+clickPraise+'">'+commentVo.praiseCount+'</span>'       
							    	+'			<span class="dianzan dtplspan" id="pl'+commentVo.commentId+'" onclick="'+commentDetailLocal+'">'+commentVo.replyCount+'</span>'
							    	+'		</div>'
							    	+'	</div>';
							
							$("#commentsId").append(showHtml);
					}
					setTimeout(function(){
						$(".initloading").hide();
					},800);
				}else{
					showvaguealert("访问出错");
					$(".initloading").hide();
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
			}
		});
	}
	
function shareSpecial(){
 		$.ajax({                                       
			url: mainServer+"/weixin/share/addShare",
			data:{
				"shareType":4,
				"objId":objId
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if (data.code == 1010) {
					hideShare();
					var obj = {
						flag:true,
						storageTime:new Date().getTime()
					};
					localStorage.setItem('selected',JSON.stringify(obj));
					if (integralSwitch != null && integralSwitch == 0) {
						$(".shate-succeed").show();
						setTimeout(function(){
							$(".shate-succeed").hide();
						},800);
					}
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
			}
		});
	}
	
function checkLogin(){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+theUrl;
		return;
	}
	window.location.href = mainServer+"/weixin/discovery/toAddComment?articleId="+objId;
}

function concernOrCancel(fansId){
	
 		if(fansId == null || fansId < 0){
 			showvaguealert('访问数据出错');
 			return false;
 		}
 		var url = mainServer+"/weixin/concern/addConcern";
 		var data={};
 		if(fansId > 0){ //取消关注
 			url = mainServer+"/weixin/concern/cancelConcern";
 			data={
			 		"fansId":fansId
		 	};
 			
 		}else{
 			if(concernId == null || concernId < 0){
 				showvaguealert('访问数据出错');
	 			return false;
 			}
 			data={
			 		"concernCustId":concernId
		 	};
 		}
 		$("#guanzhuId").attr("href", "javascript:void(0);");
 		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(fansId > 0){
						$("#guanzhuId").html("+ 关注");
						$("#guanzhuId").removeClass();
						$("#guanzhuId").attr("href", "javascript:concernOrCancel(0);");
						closeConcernAlert();
						showvaguealert("已取消关注");
					}else{
						$("#guanzhuId").html("已关注");
						$("#guanzhuId").attr("class", "current");
						$("#guanzhuId").attr("href", "javascript:alertCancelTip("+data.data+");");
						showvaguealert("已关注");
					}
				}else if (data.code == "1011"){
					window.location.href = mainServer+"/weixin/tologin?backUrl="+theUrl;
				}else{
					showvaguealert(data.msg);
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
			}
		});
 	}
	
function closeConcernAlert(){
	$(".bkbg").hide();
	$(".shepassdboxs").hide();
	$("#cancelId").attr("href", "javascript:void(0);");
}
	 
function alertCancelTip(fansId){
	$(".bkbg").show();
	$(".shepassdboxs").show();
	$("#cancelId").attr("href", "javascript:concernOrCancel("+fansId+")");
}
	
function toSocialityHome(authorId){
	
	var myCustId = customerId;
	if(myCustId > 0 && myCustId == authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toSocialityHome?backUrl="+theUrl;
	}else if (myCustId > 0 && myCustId != authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+authorId+"&backUrl="+theUrl;
	}else{
		window.location.href = mainServer+"/weixin/tologin?backUrl="+theUrl;
	}
}
	
function shareAlert(){
	$(".share").fadeIn(500,function(){
		$('.shareimg').hide()
	});
	var obj = {
		flag:true,
		storageTime:new Date().getTime()
	};
	localStorage.setItem('selected',JSON.stringify(obj));
}
	
function hideShare(){
	$(".share").fadeOut(500);
}

function toHelp(){
	var backUrl = mainServer+"/weixin/discovery/toSelectedDetail?articleId="+objId;
	window.location.href = mainServer+"/weixin/discovery/toHelp?backUrl="+theUrl;
}


//修改微信自带的返回键
$(function(){
	var url = mainServer+"/weixin/discovery/toSelected";
			
	if(backUrl != null && backUrl != "" && backUrl != undefined){
		url = backUrl;
	}
			
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
     },1000); 
		
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
		    
		    $('.backPage').click(function(){
				window.location.href=url;
			});
		    
});
	