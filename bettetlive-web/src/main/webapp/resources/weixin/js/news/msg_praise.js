$(function(){
	$(".msglist").html("");
	queryUnreadByInteract();
	queryMsgs(1,10);
});

//往上滑的
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
					queryMsgs(next,10);
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

var backUrl = mainServer + "/weixin/message/toPraiseMsg";
 
 /**
  * 分页加载列表
  * @param pageIndex
  * @param pageSize
  */
 function queryMsgs(pageIndex,pageSize){
	 	$(".initloading").show();
	 
	 	$.ajax({
			type: 'POST',
		   url: mainServer + "/weixin/message/queryInteractPraiseMsgList",
		   async: false,
		   data: {rows:pageSize,pageIndex:pageIndex},
		   dataType:'json',  
			success:function(data){
				if(data.code == "1010"){//获取成功
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
						    $(".noyhbg").show();
						 },800);
						return;
					}
					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						var msgVo = list[continueIndex];
						
						var local = mainServer + "/resources/images/default_photo.png";
						if(msgVo.imgLocal != null && msgVo.imgLocal != "" && msgVo.imgLocal != undefined){
							local = msgVo.imgLocal;
						}
						
						//是否显示未读消息数量
						var unreadFlag = "";
						if(msgVo.unreadCount <= 0){
							unreadFlag = "display:none;";
						}
						
						//当前点赞人数量是否大于0
						var cmmCountFlag = "";
						if(msgVo.totalCount > 1){
							cmmCountFlag = "等"+msgVo.totalCount+"人";
						}
						
						var typeName = "商品";
						var commMsgLocal = "checkGoodLocal("+ msgVo.subMsgType +","+msgVo.objId+", " + msgVo.parentId + ")";
						if(msgVo.subMsgType == 16 || msgVo.subMsgType == 17){ //我的动态被点赞、动态评论点赞
							typeName = "动态";
							commMsgLocal = "queryDetail("+ msgVo.subMsgType + "," + msgVo.objId+", " + msgVo.parentId + ")";
						}else if(msgVo.subMsgType == 18){ //文章评论点赞
							typeName = "文章";
							commMsgLocal = "queryDetail("+ msgVo.subMsgType + ","+msgVo.objId+", " + msgVo.parentId + ")";
						}else if(msgVo.subMsgType == 20){ //视频评论点赞
							typeName = "视频";
							commMsgLocal = "queryDetail("+ msgVo.subMsgType + ","+msgVo.objId+", " + msgVo.parentId + ")";
						}

						var showHtml = '<li>'
									  +'	<div class="headphoto" onclick="toOtherHome('+msgVo.customerId+');">'
									  +'			<span style="'+unreadFlag+'">'+ msgVo.unreadCount +'</span>'
									  +'			<img src="'+msgVo.imgLocal+'"/>'
									  +'		</div>'
									  +'		<div class="msgcontent" onclick="'+commMsgLocal+'">'
									  +'			<div class="msgtext">'
									  +'				<h3>'+msgVo.customerName + cmmCountFlag+'给你点了赞</h3>'
									  +'				<div class="time">'
									  +'					<span>'+msgVo.createTimeStr+'</span>'
									  +'				</div>'
									  +'				<h4>'+ msgVo.msgDetail +'</h4>'
									  +'			</div>'
									  +'			<div class="msgphoto">'
									  +'				<span>'+typeName+'</span>'
									  +'				<img src="'+msgVo.msgLocal+'"/>'
									  +'			</div>'
									  +'		</div>'
									  +'	</li>';
							
						$(".msglist").append(showHtml);
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
 
/**
 * 未读消息
 */
function queryUnreadByInteract(){
	$.ajax({
		   type: 'POST',
		   url: mainServer + "/weixin/message/readInteractUnreadCount",
		   data: {},
		   dataType:'json',  
			success:function(data){
			if(data.code == "1010"){//获取成功
				if(data.data !=null){
					if(data.data.commentMsgCount > 0){
						var showData = data.data.commentMsgCount+"";
						var $sp = $("#cmmCountId");
						
						if(parseInt(data.data.commentMsgCount) > 99){
							showData = "99+";
						}
						$sp.show();
						$sp.html(showData);
					}
					if(data.data.praiseMsgCount > 0){
						var showData = data.data.praiseMsgCount+"";
						var $sp = $("#praiseCountId");
						
						if(parseInt(data.data.praiseMsgCount) > 99){
							showData = "99+";
						}
						$sp.show();
						$sp.html(showData);
					}
				}
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 检查商品是否参与限量、团购活动
 * @param subMsgType
 * @param objId
 * @param parentId
 */

function checkGoodLocal(subMsgType, objId, parentId){
	checkRead(subMsgType, parentId);
	$.ajax({
		   type: 'POST',
		   url: mainServer + "/weixin/product/checkActivityByProduct",
		   data: {
			   "productId":objId
		   },
		   dataType:'json',  
			success:function(data){
			if(data.code == "1010"){//获取成功
				var proVo = data.data;
				var jumpLocal = mainServer + "/weixin/product/towxgoodsdetails?productId="+proVo.productId+"&backUrl=" + backUrl;
				if(proVo.specialId > 0 && proVo.specialType > 0){
					if(proVo.specialType == 2){ //限量抢购
						jumpLocal =  mainServer + "/weixin/product/toLimitGoodsdetails?productId="+proVo.productId+"&specialId="+proVo.specialId+"&backUrl=" + backUrl;
					}else if (proVo.specialType == 3){
						jumpLocal =  mainServer + "/weixin/product/toLimitGoodsdetails?productId="+proVo.productId+"&specialId="+proVo.specialId+"&backUrl=" + backUrl;
					}
				}
				window.location.href = jumpLocal;
			}else{
				window.location.href = mainServer + "/common/toFuwubc?ttitle=点赞信息提示&tipsContent="+data.msg;
			}
			
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 使消息已读
 * @param subMsgType	子分类
 * @param parentId		业务ID
 */
function checkRead(subMsgType, parentId){
	$.ajax({
	   type: 'POST',
	   url: mainServer + "/weixin/message/checkPraiseRead",
	   data: {"subMsgType":subMsgType, "objId":parentId},
	   dataType:'json',  
	   success:function(data){
		  //无操作
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 跳转详情
 * @param subMsgType
 * @param objId
 * @param parentId
 */
function queryDetail(subMsgType, objId, parentId){
	checkRead(subMsgType, parentId);
	if(subMsgType == 16 || subMsgType == 17){ //我的动态被点赞、动态评论点赞
		window.location.href = mainServer + "/weixin/discovery/toDynamicDetail?articleId="+objId+"&backUrl="+backUrl;
	}else if(subMsgType == 18){ //文章评论点赞
		window.location.href = mainServer + "/weixin/discovery/toSelectedDetail?articleId="+objId+"&backUrl="+backUrl;
	}else if(subMsgType == 20){ //视频评论点赞
		window.location.href = mainServer + "/weixin/special/toTorialComment?specialId="+objId+"&backUrl="+backUrl;
	}
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
			window.location.href = mainServer+"/weixin/message/showUnread";
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



function toOtherHome(otherCustId){
	 var back = window.location.href;
	 if(customerId != null && customerId > 0 && customerId == otherCustId){
		window.location.href = mainServer + "/weixin/socialityhome/toSocialityHome?backUrl="+back.replace("&","%26");
	 }else{
	 	window.location.href = mainServer + "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+otherCustId+"&backUrl="+back.replace("&","%26");
	 }
}

