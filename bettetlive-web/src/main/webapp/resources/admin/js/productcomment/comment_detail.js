//提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}
function toBack() {
	window.location.href = mainServer + "/admin/productcomment/findList";
}

$(function() {
	$.ajax({
		type : "POST",
		url : mainServer
				+ "/admin/productcomment/queryCommentByRootIdList",
		data : {
			'rootId' : commentId
		},
		async : false,
		success : function(data) {
			var obj = eval(data);
			//获取成功处理获取到的数据
			if ("succ" == obj.result) {
				$.each(obj.listComment, function(i, comment) {
					//记录parentid的昵称	
					var replyerName = "";
					if(comment.parent_id != ""){
						replyerName = "<strong>回复:</strong>" + comment.replyerName;
					}
					
					var buffer1 = '<div class="plll">'
						+ '						<div class="pltopbox">'
						+ '							<span>'
						+ comment.customerVo.nickname + replyerName
						+ ' </span>'
						+ '						<p>'
						+ comment.create_time
						+ '</p>'
						+ '						</div>'
						+ '							<div class="nrboxs">'
						+ '								'
						+ comment.content
						+ ''
						+ '							</div>';
				
						if(comment.customer_id != 19761){
							buffer1 +='<div class="huifubox">'
								+ '	<a href="javascript:void(0)" onclick="searchComment('
								+ comment.comment_id
								+ ',\''
								+ comment.customerVo.nickname
								+ '\')">回复</a>'
								+ '</div>';
						}
								
						buffer1 +='</div>';
					$(".plbox").append(buffer1);
				});

			} else {
				showvaguealert('暂时没有评论!');
				return;
			}
		}
	});
});

function addComment(comment_id, parent_id) {
	var replyMsg = $("#replyMsg").val();
	if (replyMsg == null || replyMsg == '' || replyMsg.lenght == 0) {
		showvaguealert('请输入回复的内容');
		return;
	}
	$.ajax({
		type : "POST",
		url : mainServer + "/admin/productcomment/addReplyComment",
		data : {
			'commentId' : comment_id,
			'replyMsg' : replyMsg,
			'comment_id' : commentId
		},
		async : false,
		success : function(data) {
			var obj = eval(data);
			if ("succ" == obj.result) {
				showvaguealert('回复成功!');
				window.location.href = mainServer
						+ "/admin/productcomment/findIdList?id="
						+ commentId;
			} else {
				showvaguealert('系统异常!');
			}
		}
	});
};

//两种情况调用的没有任何区别所以无需判断
function searchComment(id, customerName) {
		$.ajax({
			type : "POST",
			url : mainServer + "/admin/productcomment/queryCommentById",
			data : {
				'commentId' : id
			},
			async : false,
			success : function(data) {
				var obj = eval(data);
				if ("succ" == obj.result) {
					var ele = obj.comment;
			
					$('.pllls').remove();
					var buffer1 = '<div class="pllls">'
							+ '						<div class="pltopbox">'
							+ '							<span><strong>'
							+ '挥货店小二'
							+ '</strong>评论<strong>'
							+ customerName
							+ '</strong></span> '
							+ '						</div>'
							+ '							<div class="nrboxs">'
							+ '								'
							+ ele.content
							+ ''
							+ '							</div>'
							+ '							<div class="huifubox">'
							+ '								<textarea rows="7" cols="30" placeholder="请输入回复内容" id="replyMsg"></textarea>'
							+ '								<a href="javascript:void(0)" onclick="addComment('
							+ ele.comment_id
							+ ',\''
							+ ele.parent_id
							+ '\')">回复</a>'
							+ '							</div>'
							+ '						</div>';
					$(".plboxs").append(buffer1);

				} else {
					$('.pllls').remove();
					showvaguealert('未找到要回复的记录!');
				}
			}
		});

}