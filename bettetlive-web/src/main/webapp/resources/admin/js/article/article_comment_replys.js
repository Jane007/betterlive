//提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}
function toBack() {
	window.location.href = mainServer + "/admin/specialcomment/findaSpecialList";
}

$(function() {
	initReplys(); 
});


function initReplys(){
	$.ajax({
		type : "POST",
		url : mainServer
				+ "/admin/specialcomment/queryCommentByReplys",
		data : {
			'commentId' : commentId
		},
		async : false,
		success : function(data) {
			var obj = eval(data);
			if ("succ" != obj.result) {
				showvaguealert('查询失败');
				return;
			}else if (obj.listComment != null && obj.listComment <= 0){
				showvaguealert('暂时没有评论!');
				return;
			}
			
			$.each(obj.listComment, function(i, ele) {
					var buffer1 = "";
					var customer_name = ele.customerVo.nickname;
					var replyerName = "";
					if(ele.replyerName != null && ele.replyerName != ""){
						replyerName = "<strong>回复:</strong>" + ele.replyerName;
					}
					buffer1 = '<div class="plll">'
							+ '		<div class="pltopbox">'
							+ '			<span>'
							+ 				customer_name + replyerName
							+ ' 		</span>'
							+ '			<p>' + ele.createTime + '</p>'
							+ '		</div>'
							+ '		<div class="nrboxs">' + ele.content + '</div>';
		
					if(ele.customerVo.customer_id !='19761'){
						buffer1 += '	<div class="huifubox">'
							+ '			<a href="javascript:void(0)" onclick="searchComment('+ele.commentId +')">回复</a>'
							+ '		</div>';
					}
					
					buffer1 += '</div>';
					$(".plbox").append(buffer1);
			})
		}
	});
}

function addComment(parentId, specialTypeChild) {
	var replyMsg = $("#replyMsg").val();
	if (replyMsg == null || replyMsg == '' || replyMsg.lenght == 0) {
		showvaguealert('请输入回复的内容');
		return;
	}
	
	var subMsgType = 12;
	if(specialTypeChild == "0"){
		subMsgType = 13;
	}
	
	$.ajax({
		type : "POST",
		url : mainServer + "/admin/specialcomment/addReplyComment",
		data : {
			'content' : replyMsg,
			'parentId' : parentId,
			'subMsgType' : subMsgType
		},
		async : false,
		success : function(data) {
			var obj = eval(data);
			if ("succ" == obj.result) {
				showvaguealert('回复成功!');
				window.location.href = mainServer
						+ "/admin/specialcomment/findIdList?id="
						+ commentId;
			} else {
				showvaguealert(obj.msg);
				return;
			}
		}
	});
};

function searchComment(parentId) {
	$.ajax({
		type : "POST",
		url : mainServer + "/admin/specialcomment/getIsCustom",
		data : {
			'commentId' : parentId
		},
		async : false,
		success : function(data) {
			$('.pllls').remove();
			var obj = eval(data);

			if ("succ" != obj.result) {
				return;
			}
			
			var comm = obj.comm;
			var buffer1 = '<div class="pllls">'
					+ '						<div class="pltopbox">'
					+ '							<span><strong>'
					+ "挥货店小二"
					+ '<strong>评论</strong>'
					+ comm.customerVo.nickname
					+ '</strong></span> '
					+ '						</div>'
					+ '							<div class="nrboxs">'
					+ '								'
					+ comm.content
					+ ''
					+ '							</div>'
					+ '							<div class="huifubox">'
					+ '								<textarea rows="7" cols="30" placeholder="请输入回复内容" id="replyMsg"></textarea>'
					+ '								<a href="javascript:void(0)" onclick="addComment(' + comm.commentId + ','+ comm.specialTypeChild +')">回复</a>'
					+ '							</div>'
					+ '						</div>';
			$(".plboxs").append(buffer1);
			return;
		}
	});
}