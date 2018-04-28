$(function(){
	//性别样式
	if(sex==1){
		$('.bottom').find('h3').removeClass('wman').addClass('man');
	}else if(sex==0){
		$('.bottom').find('h3').removeClass('mman').addClass('wman');
	}
	//进入页面加载数据
	$(".prolist").html("");
	queryDatas(1, 10);
});

var isPraise = 0;
$(document).on('click','.dz',function(){
	let num = $(this).html();
	if($(this).hasClass('on')){
		num--;
		$(this).removeClass('on');
		isPraise = 1;
		$(this).html(num);
		$.toast("取消点赞","text");
	}else{
		num++;
		isPraise = 0;
		$(this).addClass('on');
		$(this).html(num);
		
		$.toast("点赞成功","text");
	}
})




var loadtobottom = true;
var nextIndex = 1;
//加载更多
$(document).scroll(
		function() {
			var totalheight = parseFloat($(window).height())
					+ parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) {
				if (loadtobottom == true) {
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();
					if (parseInt(next) > 1) {
						if (nextIndex != next) {
							nextIndex++;
							queryDatas(next, 10);
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

function queryDatas(pageIndex, pageSize) {
	$(".initloading").show();
	$
			.ajax({
				url : mainServer+'/weixin/socialityhome/queryMyDynamicList',
				data : {
					rows : pageSize,
					pageIndex : pageIndex
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.code == "1010") { // 获取成功
						var pageNow = data.pageInfo.pageNow;
						var pageCount = data.pageInfo.pageCount;
						$("#pageCount").val(pageCount);
						$("#pageNow").val(pageNow);
						var next = parseInt(pageNow) + 1;
						if (next >= pageCount) {
							next = pageCount;
						}
						$("#pageNext").val(next);
						$("#pageNow").val(pageNow);

						if ((data.data == null || data.data.length <= 0)
								&& pageIndex == 1) {
							setTimeout(function() {
								$(".initloading").hide();
								$(".zanwubg").show();
							}, 800);
							return;
						} else {
							$(".zanwubg").hide();
						}

						var list = data.data;
						for ( var continueIndex in list) {
							if (isNaN(continueIndex)) {
								continue;
							}
							var specialVo = list[continueIndex];

							var praiseClz = "dz";
							var clickPraise = "lineAddOrCancelPraise(0, 'msdz"
									+ specialVo.articleId + "', "
									+ specialVo.articleId + ", 0,4)";
							if (specialVo.isPraise > 0) {
								praiseClz += " on";
								clickPraise = "lineAddOrCancelPraise(1, 'msdz"
										+ specialVo.articleId + "', "
										+ specialVo.articleId + ","
										+ specialVo.isPraise + ",4);";
							}

							var articleClz = "audit sed";
							if (specialVo.status == 4) { // 审核失败
								articleClz += "audit up";
							} else if (specialVo.status == 3) { // 待审核
								articleClz += "audit pend";
							}
							
							var showHtml=[
							              	
							              	'<li>',
							              		'<dl>',
							              			'<dt>',
							              				'<a href="'+mainServer+'/weixin/discovery/toDynamicDetail?articleId='+specialVo.articleId+'&backUrl='+theUrl+'">',
							              					'<img src="'+specialVo.articleCover+'"/>',
							              				'</a>',
							              			'</dt>',
							              			'<dd>',
							              				'<p>'+specialVo.articleTitle+'</p>',
							              				'<div class="dbot">',
							              					'<span class="time">'+specialVo.fomartTime+'</span>',
							              					'<span class="'+praiseClz+'" onclick="'+clickPraise+'" id="msdz'+specialVo.articleId+'">'+specialVo.praiseCount+'</span>',
							              				'</div>',
							              			'</dd>',
							              		'</dl>',
							              		'<div class="'+articleClz+'"></div>',
							              	'</li>',
							              
							              ].join('');
							
							$(".prolist").append(showHtml);
						};
						imgLoad();
						setTimeout(function() {
							$(".initloading").hide();
						}, 800);
					} else if (data.code == "1011") {
						window.location.href = mainServer + "/weixin/tologin";
					} else {
						$.toast("出现异常","text");
					}
				},
				failure : function(data) {
					$.toast("出错了","text");
				}
			});
};

function imgLoad(){
	$('.prolist img').load(function(){
		$('.prolist img').each(function(){
			if($(this).width() > $(this).height()){
				$(this).css({
					'width':'auto',
					'height':'100%'
				})
			}else{
				$(this).css({
					'width':'100%',
					'height':'auto'
				})
			}
		});
	})
}

//点赞
function lineAddOrCancelPraise(flag,praiseLineId,specialId,praiseId,praiseType){
	var customerId = $("#customerId").val();
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin";
		return;
	}
	
 	if((flag != 0 && flag != 1) || isNaN(flag)){
 		$.toast("出现异常","text");
 		return;
 	}
 	if(praiseLineId == null || praiseLineId == ""){
 		$.toast("出现异常","text");
 		return;
 	}
 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
 		$.toast("出现异常","text");
			return;
		}
 	var url = mainServer+"/weixin/praise/addPraise";
 	var data={
 		"praiseType":praiseType,
 		"objId":specialId
 	};
 	if(flag == 1){
 		url = mainServer+"/weixin/praise/cancelPraise";
 		if(praiseId == null || praiseId == "" || isNaN(praiseId) || parseFloat(praiseId) <= 0){
 			$.toast("出现异常","text");
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
					$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(0,'"+praiseLineId+"',"+specialId+",0,"+praiseType+")");
				}else{
					$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(1,'"+praiseLineId+"',"+specialId+","+data.data+","+praiseType+")");
					
				}
			}else{
				$.toast(data.msg,"text");
				
			}
		},
		failure:function(data){
			$.toast("访问出错","text");
		}
	});
}





//微信自带返回
$(function(){
	var url=mainServer+"/weixin/discovery/toDynamic";
	if(backUrl != null && backUrl != "" && backUrl != undefined){
		url = backUrl;
	}
	
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
    },1000); 
        
	pushHistory(); 
	
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
    	window.history.pushState(state, "title","");  
    }
    
    
  //返回上一个页面
	$('.fan').click(function(){
		window.location.href=url;	
	});
});





