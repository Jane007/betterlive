$(function() {

	var video = document.getElementsByName("video")[0];// 点击关封面图片
	$(".play").click(function() {
		$(".play-wrap").hide();
		video.play();
	});

	//$(".list").html("");
	//queryComments(1, 10);
	listData.init();
});


// 修复回复文本框首次弹出键盘遮挡问题
$('#contentId').focus(function() {
	var oSelf = this;
	setTimeout(function() {
		oSelf.scrollIntoView(true);
		$(window).scrollTop($(document).height())

	}, 100)
});

var back = window.location.href;


var listData = {
		//所有的请求参数
		obj:{
			pageIndex:1,/*这两个是假设页码和数据条数*/
			pageSize:10,
			pageCount:'',/*总页数*/
			url:mainServer+'/weixin/specialcomment/specialCommentDetail',
			specialId:objId,
			commentPraiseType:3,
			specialType:4
		},
		//上拉加载分页
		loadMore:function(){
				var oSelf = this;
				var loading = false;//上拉加载状态标记
				$(document.body).infinite();
				$('.weui-loadmore').hide();
				$(document.body).infinite().on("infinite", function() {
					if(loading) return;
					loading = true;
				if(oSelf.obj.pageCount!=null&&oSelf.obj.pageIndex<=oSelf.obj.pageCount){
						if(oSelf.obj.pageIndex<=oSelf.obj.pageCount){
							oSelf.obj.pageIndex++;
							$('.weui-loadmore').show();
							setTimeout(function() {
							   oSelf.getData();
							   loading = false;
							   $('.weui-loadmore').hide();
							 }, 1500);   //模拟延迟
						}else{
							loading = true;
							 $('.weui-loadmore').hide();
							//销毁上拉加载的组件 ,在需要的地方销毁
							$(document.body).destroyInfinite()
						};
						
					}
				});
		},
		//请求数据
		getData:function(){
			var oSelf = this;
			$.post(oSelf.obj.url,{
				rows:oSelf.obj.pageSize,
				pageIndex:oSelf.obj.pageIndex,
				specialId:oSelf.obj.specialId,
				commentPraiseType:oSelf.obj.commentPraiseType,
				specialType:oSelf.obj.specialType
			},function(data,errror){
				if(data.code == '1010'){
					oSelf.obj.pageCount=data.pageInfo.pageCount;
					if(data.data && data.data.length>0){
						oSelf.establishDom(data.data);
					}else{//暂无数据
						$('.shafabg').show();
					}
					
				}else{
					$.toast("出现异常","text");
				}
				
			})
		},
		//创建列表
		establishDom:function(data){
			var list = data;
			for (var continueIndex in list) {
				if(isNaN(continueIndex)){
					continue;
				}
				var commentVo = list[continueIndex];
			
				var onClz = commentVo.isPraise>0?"on":'';
				
				var replyLine = "";
				if(commentVo.replyerId != null && commentVo.replyerId > 0 && commentVo.replyerId != commentVo.customerVo.customer_id){
					replyLine = "回复<span>"+commentVo.replyerName+"</span>：";
				}
				
				var commentDetailLocal = "location.href='"+mainServer+"/weixin/special/toVideoCommentDetail?specialId="+commentVo.specialId+"&commentId="+commentVo.commentId+"&backUrl="+backUrl+"'";
				var showHtml = [
				            	'<li>',
								    '<dl>',
										'<dt>',
											'<div class="head fl" onclick="toSocialityHome('+commentVo.customerVo.customer_id+')">',
												'<img src="'+commentVo.customerVo.head_url+'"/>',
											'</div>',
											'<div class="dtbot fl">',
												'<div class="name ellipse">'+commentVo.customerVo.nickname+'</div>',
												'<div class="nametime">'+commentVo.createTime+'</div>',
											'</div>',
										'</dt>',
										'<dd>',
											'<div class="comdet">',
												'<p>'+commentVo.content+'</p>',
												
												'<div class="dz">',
													'<div class="ondz opl" id="pl'+commentVo.commentId+'" onclick="'+commentDetailLocal+'">'+commentVo.replyCount+'</div>',
													'<div class="ondz odz '+onClz+'"  id="dz'+commentVo.commentId+'" data-comment-id="'+commentVo.commentId+'" data-priase-id="'+commentVo.isPraise+'">'+commentVo.praiseCount+'</div>',
												'</div>',
											'</div>',
									'</dd>',
								'</dl>',
							'</li>'
				                ].join('');
					
					$(".list").append(showHtml);
					
				}
				
		},
		//点赞效果
		operate:function(){
			//收藏
			$(document).on('click','.sc',function(){
				//点击获取当前收藏节点的点赞数量
				let num = $(this).children().html();
				var oSelf = $(this);
				//没登陆
				if(customerId == null || customerId <= 0){
					window.location.href =  mainServer + "/weixin/tologin?backUrl="+link;
					return;
				};
				//判断是否已经收藏
				if($(this).hasClass('on')){
					var collectionId = $(this).attr("data-collection-id");
					var url = mainServer + "/weixin/collection/cancelCollection";
					$.post(url,{
						"collectionId":collectionId
					},function(data,error){
						if(data.code == '1010'){
							num--;
							oSelf.removeClass('on');
							oSelf.children().html(num);
							oSelf.attr("data-collection-id",0);
							$.toast("取消收藏","text");
						}else{
							$.toast(data.msg,"text");
						}
					})
					
				}else{
					var specialId = $(this).attr("data-special-id");
					var url = mainServer + "/weixin/collection/addCollection";
					$.post(url,{
						"collectionType":3,
						"objId":specialId
					},function(data,error){
						if(data.code == '1010'){
							num++;
							oSelf.addClass('on');
							oSelf.children().html(num);
							oSelf.attr("data-collection-id",data.data);
							$.toast("收藏成功","text");
						}else{
							$.toast(data.msg,"text");
						}
					})
					
				}
			});

			//点赞
			$(document).on('click','.botfunc .dz',function(){
				//点击获取当前点赞数量
				let num = $(this).children().html();
				var oSelf = $(this);
				//没登陆
				if(customerId == null || customerId <= 0){
					window.location.href =  mainServer + "/weixin/tologin?backUrl="+link;
					return;
				};
				if($(this).hasClass('on')){
					var url = mainServer + "/weixin/praise/cancelPraise";
					var praiseId = $(this).attr("data-priase-id");
					$.post(url,{
						"praiseId":praiseId
					},function(data,error){
						if(data.code == '1010'){
							num--;
							oSelf.removeClass('on');
							oSelf.children().html(num);
							oSelf.attr("data-priase-id",0);
							$.toast("取消点赞","text");
						}else{
							$.toast(data.msg,"text");
						}
					})
					
				}else{
					var url = mainServer + "/weixin/praise/addPraise";
					var specialId = $(this).attr("data-special-id");
					$.post(url,{
						"praiseType":2,
						"objId":specialId
					},function(data,error){
						if(data.code == '1010'){
							num++;
							oSelf.addClass('on');
							oSelf.children().html(num);
							oSelf.attr("data-priase-id",data.data);
							$.toast("点赞成功","text");;
						}else{
							$.toast(data.msg,"text");
						}
					})
					
					
				}
			});
			//评论点赞
			$(document).on('click','.odz',function(){
				//点击获取当前点赞数量
				let num = $(this).html();
				var oSelf = $(this);
				//判断是否已经收藏
				//没登陆
				if(customerId == null || customerId <= 0){
					window.location.href =  mainServer + "/weixin/tologin?backUrl="+link;
					return;
				};
				if($(this).hasClass('on')){
					var url = mainServer + "/weixin/praise/cancelPraise";
					var praiseId = $(this).attr("data-priase-id");
					$.post(url,{
						"praiseId":praiseId
					},function(data,error){
						if(data.code == '1010'){
							num--;
							oSelf.removeClass('on');
							oSelf.html(num);
							oSelf.attr("data-priase-id",0);
							$.toast("取消点赞","text");
						}else{
							$.toast(data.msg,"text");
						}
					})
					
				}else{
					var url = mainServer + "/weixin/praise/addPraise";
					var commentId = $(this).attr("data-comment-id");
					$.post(url,{
						"praiseType":3,
						"objId":commentId
					},function(data,error){
						if(data.code == '1010'){
							num++;
							oSelf.addClass('on');
							oSelf.html(num);
							oSelf.attr("data-priase-id",data.data);
							$.toast("点赞成功","text");;
						}else{
							$.toast(data.msg,"text");
						}
					})
					
					
				}
			});
		},
		init:function(){
			this.loadMore();
			this.getData();
			this.operate()
		}
}


function shareSpecial(){
		$.ajax({                                       
		url: mainServer+"/weixin/share/addShare",
		data:{
			"shareType":3,
			"objId":objId,
			"shareCustomerId":shareCustomerId
		},
		type:'post',
		dataType:'json',
		success:function(data){
			var sindx = $(".an04").html();
			$.toast("分享成功","text");
			sindx = parseInt(sindx);
			$(".an04").html(sindx+1);
			hideShare();
		},
		failure:function(data){
			$.toast("访问出错","text");
		}
	});
}


function addComment(content){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+back;
	}else{
		if (content == null || content == "" || content.length <= 0){
			return false;
		} 
		if (content.length > 100){
			$.toast('亲,评论过长,请输入100字以内！','text');
			return false;
		} 
		
		$.ajax({    
	          url: mainServer+'/weixin/specialcomment/addComment' , 
	          type: 'POST',    
	          data: {
	        		"specialId":objId,
	        		"specialType":4,
	        		"content":content
	          },    
	          dataType:'json',
	          success:function(data) { 
			        if(data.code == 1010){  
						$(".shafabg").hide();
						var tcount = $("#totalCountId").html();
						var cmmCount = parseInt(tcount) + 1;
						$("#totalCountId").html(cmmCount);
						$("#contentId").val("");
						$("#commentsId").html("");
						$(".list").html("");
						var oSelf = listData.obj;
  						oSelf.pageIndex=1;
  						listData.getData();
  						
			        }else{  
			        	$.toast(data.msg,'text');
			        }  
			     },     
			     error : function() {  
			    	 $.toast('异常！','text');
			     }   
	     });  
	}
}

function toSocialityHome(authorId){
	var myCustId = customerId;
	var backUrl = mainServer+"/weixin/discovery/toTorialComment?specialId="+objId;
	if(myCustId > 0 && myCustId == authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toSocialityHome?backUrl="+backUrl;
	}else if (myCustId > 0 && myCustId != authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+authorId+"&backUrl="+backUrl;
	}else{
		window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
	}
}

function checkStatus(){
	var specialStatus = status;
	if(specialStatus == null || specialStatus != 1){
		window.location.href = mainServer+"/common/toFuwubc?ttitle=视频信息提示&tipsContent=您播放的视频已下架~";
		return false;
	}else{
		return true;
	}
	
}

//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},1000);
}

function shareAlert(){
	if(!checkStatus()){
 		return;
 	}else{
		$(".zhuantifx").show();
 	}
}

function hideShare(){
	$(".zhuantifx").hide();
}


//修改微信自带的返回键 
$(function(){
	var url = mainServer+"/weixin/discovery/toVideo";
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
    		window.location.href= url;
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



