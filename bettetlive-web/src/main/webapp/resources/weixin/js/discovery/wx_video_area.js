
$(function(){
	//修改微信自带的返回键
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
    },1000); 
        
	pushHistory(); 
	
    window.addEventListener("popstate", function(e) {
    	if(bool){
    		window.location.href= mainServer+"/weixin/discovery/toVideo";
    	}
    }, false);
    
    function pushHistory(){ 
        var state = { 
    		title:"title", 
    		url:"#"
    	}; 
    	window.history.pushState(state, "title","#");  
    };
    //初始化视频列表对象
	videoList.inIt();
});
//创建视频列表对象
var videoList = {
	//所有的请求参数
	obj:{
		pageIndex:1,/*这两个是假设页码和数据条数*/
		pageSize:10,
		pageCount:'',/*总页数*/
		url:mainServer + '/weixin/special/tutorialList',
		objTypeId:typeId
	},
	//上拉加载分页
	loadMore:function(){
			var oSelf = this;
			var loading = false;//上拉加载状态标记
			$(document.body).infinite();
			$(document.body).infinite().on("infinite", function() {
				if(loading) return;
				loading = true;
			if(oSelf.obj.pageCount!=null&&oSelf.obj.pageIndex<=oSelf.obj.pageCount){
					
					if(oSelf.obj.pageIndex<oSelf.obj.pageCount){
						oSelf.obj.pageIndex++;
						$('.weui-loadmore').show();
						setTimeout(function() {
						   oSelf.getData();
						   loading = false;
						   $('.weui-loadmore').hide();
						 }, 1500);   //模拟延迟
					}else{
						loading = true;
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
			objTypeId:oSelf.obj.objTypeId
		},function(data,errror){
			if(data.code == '1010'){
				oSelf.obj.pageCount=data.pageInfo.pageCount;
				if(data.data && data.data.length>0){
					oSelf.establishDom(data.data);
				}else{//暂无数据
					
				}
				
			}else{
				$.toast("出现异常","text");
			}
			
		})
	},
	//创建视频列表
	establishDom:function(data){
		var _html = '';
		for(var i = 0; i < data.length; i++){
			var specialVo = typeof data[i] == 'object' ? data[i] : $.parseJSON(data[i]);
			if(specialVo!=null){
				var onDz='';
				if(specialVo.isPraise>0){
					onDz = ' on ';
				};
				var onSc = '';
				if(specialVo.isCollection>0){
					onSc = ' on ';
				};
				var integral = "";
				if (integralSwitch != null && integralSwitch == 0) {
					integral = "<div class='hold'></div>";
				}

					_html = [
								'<li>',
								'	<div class="licontent">',
								'		<div class="video">',
								'			<video controls="controls" width="100%" height="100%" src="'+ specialVo.specialPage +'"></video>',
								'			<div class="play-wrap" style="background:url('+ specialVo.specialCover+') no-repeat;background-size:100% 100%">',
								'				<div class="play"></div>',
								'				<div class="videotime">'+ specialVo.objValue +'</div>',
								'			</div>',
								'		</div>',
								'		<div class="bot">',
								'			<h4>'+specialVo.specialTitle+'</h4>',
								'			<div class="botfunc">',
								'				<div class="sc '+onSc+' fl" data-special-id="'+specialVo.specialId+'" data-collection-id="'+specialVo.isCollection+'"><span>'+ specialVo.collectionCount +'</span></div>',
								'				<div class="msg fl" data-special-id="'+specialVo.specialId+'"><span>'+ specialVo.commentCount +'</span></div>',
								'				<div class="dz '+onDz+' fl" data-special-id="'+specialVo.specialId+'" data-priase-id="'+specialVo.isPraise+'"><span>'+ specialVo.praiseCount +'</span></div>',
								'					<div id="share'+specialVo.specialId+'" data-specialIntroduce="'+ specialVo.specialIntroduce +'"imgUrl-id = "'+ specialVo.specialCover +'" share-url="/weixin/discovery/toTorialComment?specialId='+specialVo.specialId +"&shareCustomerId=" + customerId + '" class=\'icon fl\'><span>'+ specialVo.shareCount,
								'					</span>'+integral+'</div>',
								'			</div>',
								'		</div>',
								'	</div>',
								'</li>'
							].join('');
					$('.videolist').append(_html)
				
			}
			
			
		}
	},
	//视频播放
	videoPlay:function(){
		//播放按钮事件
		$(document).on('click','.play',function(){
			//获取所有的视频节点
			var videoArr = document.getElementsByTagName('video');
			//循环停止播放所有的视频
			for(var i = 0; i < videoArr.length; i++){
				videoArr[i].pause();
			};
			//显示所有的视频播放按钮及遮罩
			$('.play-wrap').show();
			//隐藏当前的播放按钮以及遮罩
			$(this).parents('.play-wrap').hide();
			//播放事件只有原生js能够实现
			var videoNode = this.parentNode.parentNode.getElementsByTagName('video')[0];
			//播放当前选中的视频
			videoNode.play();
		})
	},
	//点赞以及收藏效果
	operate:function(){
		//收藏
		$(document).on('click','.sc',function(){
			//点击获取当前收藏节点的点赞数量
			let num = $(this).children().html();
			var oSelf = $(this);
			//没登陆
			if(customerId == null || customerId <= 0){
				window.location.href =  mainServer + "/weixin/tologin?backUrl="+backUrl;
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
		$(document).on('click','.dz',function(){
			//点击获取当前点赞数量
			let num = $(this).children().html();
			var oSelf = $(this);
			//判断是否已经收藏
			//没登陆
			if(customerId == null || customerId <= 0){
				window.location.href =  mainServer + "/weixin/tologin?backUrl="+backUrl;
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
		
		$(document).on('click','.msg',function(){
			var specialId = $(this).attr("data-special-id");
			var clickComment = mainServer + "/weixin/discovery/toTorialComment?specialId="+specialId+"&backUrl=" + backUrl;
			window.location.href=clickComment;
		});
		$('.videolist').on('click','.icon',function(){
			var url = mainServer+'/weixin/shareWeixin';
			var pageUrl = window.location.href;
			var sharUrl = mainServer + $(this).attr('share-url');
			var title = $(this).parents('.bot').find('h4').html();
			var imgUrl = $(this).attr('imgUrl-id');
			var specialintroduce = $(this).attr('data-specialintroduce');
			var videoId = $(this).parents("li").attr("data-special-id");
			var shr;
			if (integralSwitch != null && integralSwitch == 0) {
				$('.share').fadeIn(500,function(){
					$.post(url,{url:pageUrl},function(data){
						shr = eval('(' + data + ')');
						if(shr.shareInfo != null){
							wxShare(shr.shareInfo.appId,shr.shareInfo.timestamp,shr.shareInfo.nonceStr,shr.shareInfo.signature,title,sharUrl,imgUrl,specialintroduce,videoId);
						}
					})
				});
			}else{
				$('#cover').fadeIn(500,function(){
					$.post(url,{url:pageUrl},function(data){
						shr = eval('(' + data + ')');
						if(shr.shareInfo != null){
							wxShare(shr.shareInfo.appId,shr.shareInfo.timestamp,shr.shareInfo.nonceStr,shr.shareInfo.signature,title,sharUrl,imgUrl,specialintroduce,videoId);
						}
					})
				});
			}
		});
	},
	//初始化
	inIt:function(){
		this.videoPlay();
		this.loadMore();
		this.getData();
		this.operate()
	}
}

function shareVideo(objId){
	$.ajax({                                       
		url: mainServer+"/weixin/share/addShare",
		data:{
			"shareType":3,
			"objId":objId
		},
		type:'post',
		dataType:'json',
		success:function(data){
			hideShare();
			if (integralSwitch != null && integralSwitch == 0) {
				$(".shate-succeed").show();
				setTimeout(function(){
					$(".shate-succeed").hide();
				},800);
			}
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}

function hideShare(){
	$("#cover").hide();
	$('.share').hide();
}

function toHelp(){
	var backUrl = mainServer+"/weixin/discovery/toSelectedDetail?articleId="+objId;
	window.location.href = mainServer+"/weixin/discovery/toHelp?backUrl="+backUrl;
}


