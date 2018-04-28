$(function(){
	$("#replysId").html("");
	queryComments(1, 10);
});
//修复回复文本框首次弹出键盘遮挡问题
$('#contentId').focus(function(){
	var oSelf = this;
	setTimeout(function(){
		oSelf.scrollIntoView(true);
		$(window).scrollTop($(document).height())
	
	},100)
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
		url: mainServer + '/weixin/productcomment/productMessageReplyDetails',
		data:{
			rows:pageSize,pageIndex:pageIndex,
			"rootId":rootId,
			"myCommentId":parentId
		},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1011"){ //访问超时
				window.location.href = mainServer + "/weixin/tologin";
				return;
			}else if(data.code != "1010"){ //获取失败
				window.location.href = mainServer + "/common/toFuwubc?ttitle=评论消息提示&tipsContent=您查看的评论消息不存在~";
				return;
			}
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
					$(".shafabg").show();
					$(".initloading").hide();
				 },800);
				return;
			}

			var list = data.data;
			for (var continueIndex in list) {
				if(isNaN(continueIndex)){
					continue;
				}
				var commentVo = list[continueIndex];
			
				var replyerName = "";
				if(commentVo.replyerName != null && commentVo.replyerName != "" && commentVo.replyerId != rootCustId){
						replyerName = "回复<span>"+commentVo.replyerName+"：</span>";
				}
				var isPraiseClz = "dz.png";
				var clickPraise = "lineAddOrCancelPraise(0, 'dz"+commentVo.comment_id+"', "+commentVo.comment_id+", 0)";
				
				if(commentVo.is_praise > 0){
					isPraiseClz = "dz02.png";
					clickPraise = "lineAddOrCancelPraise(1, 'dz"+commentVo.comment_id+"', "+commentVo.comment_id+","+commentVo.is_praise+")";
				}
				var showHtml = '<li>'
							  +'	<div class="litop">'
							  +'		<div class="topimg" onclick="toOtherHome('+commentVo.customerVo.customer_id+');">'
							  +'			<img src="'+commentVo.customerVo.head_url+'"/>'
							  +'		</div>'
							  +'		<div class="lileft">'
							  +'			<span>'+commentVo.customerVo.nickname+'</span><span>'+commentVo.create_time+'</span>'
							  +'		</div>'
							  +'	</div>'
							  +'	<div class="libottom">'
							  +'		<p  onclick="toReply('+ commentVo.comment_id +',\''+ commentVo.customerVo.nickname +'\')">'+ replyerName + commentVo.content +'</p>'
							  +'		<div class="uplike">'
							  +'			<div id="dz'+commentVo.comment_id+'" class="likemsg" onclick="'+clickPraise+'">'
							  +'				<img src="' + resourcepath + '/weixin/img/news/'+ isPraiseClz +'"/>'
							  +'				<span>' + commentVo.praise_count + '</span>'
							  +'			</div>'
							  +'		</div>'
							  +'	</div>'
							  +'</li>';
					
					$("#replysId").append(showHtml);
			}
			setTimeout(function(){
				$(".initloading").hide();
			},800);
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}
 
function addOrCancelPraise(flag){
	if((flag != 0 && flag != 1) || isNaN(flag)){
 		showvaguealert("出现异常");
 		return;
 	}
 	var url = mainServer + "/weixin/praise/addPraise";
 	var data={
 		"praiseType":1,
 		"objId": rootId
 	};
 	if(flag == 1){
 		url = mainServer + "/weixin/praise/cancelPraise";
 		if(myPraiseId == null || myPraiseId == "" || isNaN(myPraiseId) || parseFloat(myPraiseId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
 		data={
	 		"praiseId":myPraiseId
	 	};
 	}
	$("#praiseId").attr("onclick", "void(0);");
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$("#praiseId").attr("onclick", "addOrCancelPraise(0)");
					$("#praisePicId").attr("src", resourcepath + "/weixin/img/news/dz.png");
					var praiseCount = $("#praiseCountId").html();
					praiseCount = parseInt(praiseCount)-1;
					$("#praiseCountId").html(praiseCount);
					showvaguealert("已取消点赞");
				}else{
					$("#praiseId").attr("onclick", "javascript:addOrCancelPraise(1);");
					$("#praisePicId").attr("src", resourcepath + "/weixin/img/news/dz02.png");
					var praiseCount = $("#praiseCountId").html();
					praiseCount = parseInt(praiseCount)+1;
					$("#praiseCountId").html(praiseCount);
					myPraiseId = data.data;
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
 
function lineAddOrCancelPraise(flag,praiseLineId,commentId,praiseId){
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
 	var url = mainServer + "/weixin/praise/addPraise";
 	var data={
 		"praiseType":1,
 		"objId":commentId
 	};
 	if(flag == 1){
 		url = mainServer + "/weixin/praise/cancelPraise";
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
					var praiseCount = $("#"+praiseLineId).find("span").html();
					$("#"+praiseLineId).find("span").html(parseInt(praiseCount)-1);
					$("#"+praiseLineId).find("img").attr("src", resourcepath + "/weixin/img/news/dz.png");
					showvaguealert("已取消点赞");
				}else{
					$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(1,'"+praiseLineId+"',"+commentId+","+data.data+")");
					var praiseCount = $("#"+praiseLineId).find("span").html();
					$("#"+praiseLineId).find("span").html(parseInt(praiseCount)+1);
					$("#"+praiseLineId).find("img").attr("src", resourcepath + "/weixin/img/news/dz02.png");
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

function toReply(commentId, nickName){
	if(commentId == null || commentId == ""){
		showvaguealert('访问出错');
		return;
	}
	$("#parentId").val(commentId);
	$("#contentId").attr("placeholder", "回复"+nickName+"：");
	$("#contentId").attr("onchange", "addReplyComment(this.value, 1)");
	loadtobottom=true;
}

//重新加载回复列表
function refreshReplyCommens(){
	$("#replysId").html("");
	$("#pageNext").val(1);
	nextIndex = 1;
	queryComments(1, 10);
	$(window).scrollTop(0);
	$("#parentId").val("");
	$("#contentId").val("");
	$("#contentId").attr("onclick", "addReplyComment(this.value, 0)");
	$("#contentId").attr("placeholder", "回复"+$("#pnickname").val()+"：");
}

function addReplyComment(content, flag){
	var commId = $("#parentId").val();
	if(flag == 0){
		commId = $("#rootId").val();
	}
	
	if(content == null || content == "" || content.length <= 0){
		return;
	}
	
	$.ajax({                                       
		url: mainServer + "/weixin/productcomment/replyComment",
		data:{
			"commentId":commId,
			"content":content
		},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				$(".shafabg").hide();
				refreshReplyCommens();
				showvaguealert("评论成功");
			}else{
				showvaguealert(data.msg);
			}
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}

function toOtherHome(otherCustId){
	 var back = window.location.href;
	 if(customerId != null && customerId > 0 && customerId == otherCustId){
		window.location.href = mainServer + "/weixin/socialityhome/toSocialityHome?backUrl="+back.replace("&","%26");
	 }else{
	 	window.location.href = mainServer + "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+otherCustId+"&backUrl="+back.replace("&","%26");
	 }
}
