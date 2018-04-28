$(function() {
	$(".hdbox").html("");
	$(".noyhbg").hide();
	queryMsgs(1, 10);
});


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
	
	
function queryMsgs(pageIndex,pageSize){
		$(".initloading").show();

		$.ajax({
			type: 'POST',
		   url: mainServer+"/weixin/message/queryMsgList",
		   async: false,
		   data: {rows:pageSize,pageIndex:pageIndex,"msgType":0},
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
						var showHtml = '<div class="hdtime">'
									  +'	<span>'+msgVo.createTimeStr+'</span>'
									  +' </div> '
									  +' <div class="xxboxs xtxxbox">'
									  +'	<h2>'+msgVo.msgTitle+'</h2>'
									  +'	<p>'+msgVo.msgDetail+'</p>'
									  +'	</a>'
									  +' </div>';
							
							$(".hdbox").append(showHtml);
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
 
//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},1000);
}