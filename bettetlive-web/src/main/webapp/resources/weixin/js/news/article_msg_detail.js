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
		url: mainServer + '/weixin/specialcomment/specialCommentDetail',
		data:{
			rows:pageSize,pageIndex:pageIndex,
			"specialId":articleId,
			"rootId":rootId,
			"commentPraiseType":5,
			"specialType":5
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
				var clickPraise = "lineAddOrCancelPraise(0, 'dz"+commentVo.commentId+"', "+commentVo.commentId+", 0)";
				
				if(commentVo.isPraise > 0){
					isPraiseClz = "dz02.png";
					clickPraise = "lineAddOrCancelPraise(1, 'dz"+commentVo.commentId+"', "+commentVo.commentId+","+commentVo.is_praise+")";
				}
				var showHtml = '<li>'
							  +'	<div class="litop">'
							  +'		<div class="topimg">'
							  +'			<img src="'+commentVo.customerVo.head_url+'"/>'
							  +'		</div>'
							  +'		<div class="lileft">'
							  +'			<span>'+commentVo.customerVo.nickname+'</span><span>'+commentVo.createTime+'</span>'
							  +'		</div>'
							  +'	</div>'
							  +'	<div class="libottom">'
							  +'		<p  onclick="toReply('+ commentVo.commentId +',\''+ commentVo.customerVo.nickname +'\')">'+ replyerName + commentVo.content +'</p>'
							  +'		<div class="uplike">'
							  +'			<div id="dz'+commentVo.commentId+'" class="likemsg" onclick="'+clickPraise+'">'
							  +'				<img src="' + resourcepath + '/weixin/img/news/'+ isPraiseClz +'"/>'
							  +'				<span>' + commentVo.praiseCount + '</span>'
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
 		"praiseType":5,
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
 		"praiseType":5,
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
	loadtobottom=true;
	$("#contentId").attr("onclick", "addReplyComment(this.value, 0)");
	$("#contentId").attr("placeholder", "回复"+rootCustName+"：");
}

function addReplyComment(content, flag){
	var parentId = $("#parentId").val();
	if(parentId == null || parentId == ""){
		parentId = rootId;
	}
	
	if(content == null || content == "" || content.length <= 0){
		return;
	}
	
	$.ajax({                                       
		url: mainServer + "/weixin/specialcomment/addComment",
		data:{
			"parentId":parentId,
			"specialId":articleId,
			"rootId":rootId,
			"specialType":5,
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
