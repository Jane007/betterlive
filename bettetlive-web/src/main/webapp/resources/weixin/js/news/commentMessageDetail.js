$(function() {
	$("#replysId").html("");
	queryComments(1, 10);
});

function replyComment(id) {

	if (myCustId == null || myCustId <= 0) {
		window.location.href = mainServer + "/weixin/tologin";
		return;
	}

	// if(uid==myCustId){
	window.location.href = mainServer+ "/weixin/productcomment/toAddCommentByMsg?commentId=" + commnetId+ "&myCommentId=" + myCommentId + "&replyId=" + id;
	// }
};

// 往上滑的
var loadtobottom = true;
var nextIndex = 1;
$(document).scroll(
		function() {
			totalheight = parseFloat($(window).height())
					+ parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) {
				if (loadtobottom == true) {
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();

					if (parseInt(next) > 1) {
						if (nextIndex != next) {
							nextIndex++;
							queryComments(next, 10);
							$(".loadingmore").show();
							setTimeout(function() {
								$(".loadingmore").hide();

							}, 1500);
						}
					}
					if (parseInt(next) >= parseInt(pageCount)) {
						loadtobottom = false;
					}

				}
			}
});

function queryComments(pageIndex,pageSize){
	$(".initloading").show();
	
	$.ajax({                                       
		url:mainServer+'/weixin/productcomment/commentReplyDetails',
		data:{
			rows:pageSize,pageIndex:pageIndex,
			"commentId":commentId,
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
					 },800);
					return;
				}

				var list = data.data;
				for (var continueIndex in list) {
					if(isNaN(continueIndex)){
						continue;
					}
					var commentVo = list[continueIndex];
					var clickPraise = "lineAddOrCancelPraise(0, 'dz"+commentVo.comment_id+"', "+commentVo.comment_id+", 0)";
					var praiseCls = "dianzan";
					if(commentVo.is_praise > 0){
						clickPraise = "lineAddOrCancelPraise(1, 'dz"+commentVo.comment_id+"', "+commentVo.comment_id+","+commentVo.is_praise+")";
						praiseCls = "dianzan2";
					}
				
					var replyerName = "";
					var rootCommentCust = uid;
					if(commentVo.replyerName != null && commentVo.replyerName != "" && commentVo.replyerId != rootCommentCust){
							replyerName = "回复<span style='color:#9f9b5c'>"+commentVo.replyerName+"：</span>";
					}
					var showHtml = '<div class="plcent" >'
					    	+'	<div class="plcttop">'
					    	+'			<span class="one">'
					    	+'				<img src="'+commentVo.customerVo.head_url+'" alt="">'
					    	+'			</span>'
					    	+'			<span class="two">'
					    	+				commentVo.customerVo.nickname
					    	+'			</span>'
					    	+'			<span class="three">'
					    	+				commentVo.create_time
					    	+'			</span>'
					    	+'		</div>'
					    	+'		<div class="dchfcent" onclick="replyComment('+commentVo.comment_id+')">'
					    	+			replyerName + commentVo.content
					    	+'		</div>'
					    	+'		<div class="plbotbox">'
					    	+'			<span class="'+praiseCls+'" id="dz'+commentVo.comment_id+'" onclick="'+clickPraise+'">'+commentVo.praise_count+'</span>'
//						    	+'			<span class="pinglun" id="pl'+commentVo.comment_id+'" onclick="replyComment('+commentVo.comment_id+')">'+commentVo.reply_count+'</span>'
					    	+'		</div>'
					    	+'	</div>';
						
						$("#replysId").append(showHtml);
				}
				 setTimeout(function(){
					$(".initloading").hide();
				 },800);
			}else{
				showvaguealert("访问出错");
			}
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}


function addOrCancelPraise(flag) {
	if ((flag != 0 && flag != 1) || isNaN(flag)) {
		showvaguealert("出现异常");
		return;
	}
	var url = mainServer + "/weixin/praise/addPraise";
	var data = {
		"praiseType" : 1,
		"objId" : commentId
	};
	if (flag == 1) {
		url = mainServer + "/weixin/praise/cancelPraise";
		var praiseId = $("#hasPraiseId").val();
		if (praiseId == null || praiseId == "" || isNaN(praiseId)
				|| parseFloat(praiseId) <= 0) {
			showvaguealert('出现异常啦');
			return;
		}
		data = {
			"praiseId" : praiseId
		};
	}
	$("#praiseId").attr("href", "javascript:void(0);");
	$.ajax({
		url : url,
		data : data,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				if (flag == 1) {
					$("#praiseId").attr("href",
							"javascript:addOrCancelPraise(0);");
					$("#praiseId").removeClass("current");
					$("#praiseId").html("点赞");
					showvaguealert("已取消点赞");
				} else {
					$("#praiseId").addClass("current");
					$("#praiseId").attr("href",
							"javascript:addOrCancelPraise(1);");
					$("#hasPraiseId").val(data.data);
					$("#praiseId").html("已赞");
					showvaguealert("点赞成功");
				}
			} else {
				showvaguealert(data.msg);
			}
		},
		failure : function(data) {
			showvaguealert('访问出错');
		}
	});
}

function lineAddOrCancelPraise(flag, praiseLineId, commentId, praiseId) {
	if ((flag != 0 && flag != 1) || isNaN(flag)) {
		showvaguealert("出现异常");
		return;
	}
	if (praiseLineId == null || praiseLineId == "") {
		showvaguealert("出现异常");
		return;
	}
	if (commentId == null || commentId == "" || isNaN(commentId)
			|| parseFloat(commentId) <= 0) {
		showvaguealert('出现异常啦');
		return;
	}
	var url = mainServer + "/weixin/praise/addPraise";
	var data = {
		"praiseType" : 1,
		"objId" : commentId
	};
	if (flag == 1) {
		url = mainServer + "/weixin/praise/cancelPraise";
		if (praiseId == null || praiseId == "" || isNaN(praiseId)
				|| parseFloat(praiseId) <= 0) {
			showvaguealert('出现异常啦');
			return;
		}
		data = {
			"praiseId" : praiseId
		};
	}
	$("#" + praiseLineId).attr("onclick", "void(0);");
	$.ajax({
		url : url,
		data : data,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				if (flag == 1) {
					$("#" + praiseLineId).attr("onclick","lineAddOrCancelPraise(0,'" + praiseLineId + "',"+ commentId + ",0)");
					$("#" + praiseLineId).attr('class', 'dianzan');
					var praiseCount = $("#" + praiseLineId).html();
					$("#" + praiseLineId).html(parseFloat(praiseCount) - 1);
					showvaguealert("已取消点赞");
				} else {
					$("#" + praiseLineId).attr("onclick","lineAddOrCancelPraise(1,'" + praiseLineId + "',"+ commentId + "," + data.data + ")");
					$("#" + praiseLineId).attr('class', 'dianzan2');
					var praiseCount = $("#" + praiseLineId).html();
					$("#" + praiseLineId).html(parseFloat(praiseCount) + 1);
					showvaguealert("点赞成功");
				}
			} else {
				showvaguealert(data.msg);
			}
		},
		failure : function(data) {
			showvaguealert('访问出错');
		}
	});
}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 1000);
}