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
 
 //这里是分页
 function queryMsgs(pageIndex,pageSize){
	 	$(".initloading").show();
	 
	 	$.ajax({
			type: 'POST',
		   url: mainServer + "/weixin/message/queryInteractCommentMsgList",
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
						
						//当前评论人数量是否大于0
						var cmmCountFlag = "";
						if(msgVo.totalCount > 1){
							cmmCountFlag = "等"+msgVo.totalCount+"人";
						}
						
						var typeName = "商品";
						var commMsgLocal = mainServer + "/weixin/message/toMessageCommentDetail?commentId="+msgVo.rootId+"&myCommentId="+msgVo.parentId;
						if(msgVo.subMsgType == 12 || msgVo.subMsgType == 22){
							typeName = "动态";
							if(msgVo.subMsgType == 12){
								commMsgLocal = mainServer + "/weixin/message/toArticleMessageCommentDetail?commentId="+msgVo.rootId+"&myCommentId="+msgVo.parentId+"&subMsgType=12";
							}else{
								commMsgLocal = mainServer + "/weixin/message/toDynamicMessageCommentDetail?articleId="+msgVo.rootId;
							}
						}else if(msgVo.subMsgType == 13){
							typeName = "文章";
							commMsgLocal = mainServer + "/weixin/message/toArticleMessageCommentDetail?commentId="+msgVo.rootId+"&myCommentId="+msgVo.parentId+"&subMsgType=13";
						}else if(msgVo.subMsgType == 14){
							typeName = "视频";
							commMsgLocal = mainServer + "/weixin/message/toVideoMessageCommentDetail?commentId="+msgVo.rootId+"&myCommentId="+msgVo.parentId+"&subMsgType=14";
						}

						var showHtml = '<li>'
									  +'	<div class="headphoto" onclick="toOtherHome('+msgVo.customerId+');">'
									  +'			<span style="'+unreadFlag+'">'+ msgVo.unreadCount +'</span>'
									  +'			<img src="'+msgVo.imgLocal+'"/>'
									  +'		</div>'
									  +'		<div class="msgcontent" onclick="location.href=\''+commMsgLocal+'\'">'
									  +'			<div class="msgtext">'
									  +'				<h3>'+msgVo.customerName + cmmCountFlag+'评论了你</h3>'
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
		   async: false,
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

