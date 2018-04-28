$(function() {

	$(".hdbox").html("");
	$(".noyhbg").hide();
	queryMsgs(1, 10);// 这里要加
});

// 往上滑
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
							queryMsgs(next, 10);
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


//这里是分页的
function queryMsgs(pageIndex,pageSize){
	$(".initloading").show();

	$.ajax({
		type: 'POST',
	   url: mainServer+"/weixin/message/queryMsgList",
	   async: false,
	   data: {rows:pageSize,pageIndex:pageIndex,"msgType":2},
	   dataType:'json',  
	   success:function(data) { 
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
					    $(".noyhbg").show();
					 },800);
					return;
				}else{
					$(".noyhbg").hide();
				}

				var list = data.data;
				for (var continueIndex in list) {
					if(isNaN(continueIndex)){
						continue;
					}
					var msgVo = list[continueIndex];
					var local = "javascript:void(0);";
					if(msgVo.msgLocal != null && msgVo.msgLocal != ""){
						local = msgVo.msgLocal;
					}
					var picShow = resourcepath+"/weixin/img/wx_youhuiquan.png";
					if(msgVo.subMsgType == 2 || msgVo.subMsgType == 4){
						picShow = resourcepath+"/weixin/img/wx_hongbao.png";
					}
					var msgdetail = "";
					if(msgVo.msgDetail != null && msgVo.msgDetail != ""){
						msgdetail = msgVo.msgDetail;
					}
					var showHtml = ' <div class="hdtime">'
							+'	 		<span>'+msgVo.createTimeStr+'</span>'
							+'	 	</div>' 
							+'	 	<div class="xxboxs jiaoyibox">'
							+'	 	<h2>'+msgVo.msgTitle+'</h2>'
							+'	 	<div class="jywai">'
							+'	 		<a href="javascript:void(0);">'
							+'		 	<span><img src="'+picShow+'" alt="" /></span>'
							+'		 	<p>'+msgdetail+'</p>'
							+'		 	</a>'
							+'	 	</div>'
							+'	 </div>';
						
						$(".hdbox").append(showHtml);
				   
				}
				setTimeout(function(){
					$(".initloading").hide();
				},800);
			}else{
				showvaguealert("出现异常");
			}
	   }
	})
	
}
// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 1000);
}