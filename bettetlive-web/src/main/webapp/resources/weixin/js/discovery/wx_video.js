$(function(){
	
	
	//判断是否隐藏底部导航
	 	if(localStorage.getItem('discoveryBackFlag') == "ok"){
	 		$(".goback").show();
	 		$('.footer').hide();
	 		$(".loadingmore").attr("style", "bottom:0rem;");
	 	}else{
	 		$('.footer').show();
	 	};
	 	//点击返回按钮清除缓存
 	$('.goback').click(function(){
 		localStorage.clear('discoveryBackFlag');
 		window.location.href= mainServer + "/weixin/index";
 	});
		$("#dataList").html("");
 	queryDatas(1, 3);
 	
 	video.inIt();
 	
})



var video = {
	isClick:function(){ //点击事件属性
		$('.videolist').on('click','.fl', function(){
			var specialId = $(this).parents("li").attr("data-special-id");
			//判断是否已经点击
			if($(this).hasClass('on')){
				//判断是否为收藏节点和点赞节点
				if($(this).hasClass('sc')){	//收藏
					var collectionId = $(this).attr("data-check-collection");
					lineAddOrCancelCollection($(this),specialId,collectionId,3);
				}else if($(this).hasClass('dz')){	//点赞
					var praiseId = $(this).attr("data-check-praise");
					lineAddOrCancelPraise($(this), specialId, praiseId, 2);
				}
			}else{
				//判断是否为收藏节点和点赞节点
				if($(this).hasClass('sc')){
					lineAddOrCancelCollection($(this),specialId,0,3);
				}else if($(this).hasClass('dz')){
					lineAddOrCancelPraise($(this),specialId,0,2);
				}
				
			}
		});
		//播放按钮点击播放
		$('.videolist').on('click','.play',function(){
			$(this).parent().hide();
			var videoArr = document.getElementsByTagName('video');
			for(var i = 0; i < videoArr.length; i++){
				videoArr[i].pause();
			};
			this.parentNode.parentNode.getElementsByTagName('video')[0].play()
		});
		//视频点击播放
		$('.videolist').on('click','video',function(){
			var videoArr = document.getElementsByTagName('video');
			for(var i = 0; i < videoArr.length; i++){
				videoArr[i].pause();
			};
			this.play();
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
		//分享提示弹窗
		$('#cover').click(function(){
			$(this).fadeOut(500)
		})
		$('.shate-succeed').click(function(){
			$(this).fadeOut(500)
		})
		
	},
	inIt:function(){//初始化
		this.isClick();
	}
}

function toHelp(){
	var backUrl = mainServer+"/weixin/discovery/toSelectedDetail?articleId="+objId;
	window.location.href = mainServer+"/weixin/discovery/toHelp?backUrl="+backUrl;
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
			var shareCount = $("#share"+objId).find("span").html();
			shareCount = parseFloat(shareCount) + 1;
			$("#share"+objId).find("span").html(shareCount);
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

var loadtobottom=true;
var nextIndex = 1;
$(document).scroll(function(){  
	var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
	if($(document).height() <= totalheight){	
		if(loadtobottom==true ){
		var next = $("#pageNext").val();
		var pageCount = $("#pageCount").val();
		var pageNow = $("#pageNow").val(); 
		if(parseInt(next)>1){
			if(nextIndex != next){
				nextIndex++;
				queryDatas(next,3);
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



function queryDatas(pageIndex,pageSize){
	 $(".initloading").show();
		$.ajax({                                       
			url: mainServer + "/weixin/discovery/queryVideoList",
			 data: {rows:pageSize,pageIndex:pageIndex},
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
					
					if((data.data == null || data.data.length <= 0) && pageIndex == 1){
						setTimeout(function(){
							$(".initloading").hide();
							$(".zanwubg").show();
						},800);
						return;
					}else{
						$(".zanwubg").hide();
					}
					var typeList = data.data;
					for (var continueIndex in typeList) {
						if(isNaN(continueIndex)){
							continue;
						}
						var typeVo = typeList[continueIndex];
						if(typeVo.specialList == null || typeVo.specialList.length <= 0){
							continue;
						}
						var clickLocal = mainServer + "/weixin/discovery/toVideoArea?typeId="+typeVo.typeId;
						var showHtml= "<li>"
							 +"	<ul class=\"childlist\" id=\"child"+typeVo.typeId+"\" data-type-id=\"" + typeVo.typeId + "\">";
							 
						var videos = typeVo.specialList;
						for (var subConIndex in videos) {
							if(isNaN(subConIndex)){
								continue;
							}
							var specialVo = videos[subConIndex];
							var typeNameLine="";
							if(subConIndex == 0){
								var moreLine = "";
								if(typeVo.typeCount > 2){
									moreLine = "<span onclick=\"location.href='"+clickLocal+"'\"></span>";
								}
								typeNameLine = "<h3>"+typeVo.typeName+moreLine+"</h3>";
							}
							
							var collectClz = "";
							if(specialVo.isCollection > 0){
								collectClz = " on";
							}
							var dzClz = "";
							if(specialVo.isPraise > 0){
								dzClz = " on";
							}
							
							var integral = "";
							if (integralSwitch != null && integralSwitch == 0) {
								integral = "<div class='hold'></div>";
							}
							
							var clickComment = mainServer + "/weixin/discovery/toTorialComment?specialId="+specialVo.specialId;
							
							showHtml +=" <li data-special-id=" + specialVo.specialId + ">"
									 +"		<div class=\"licontent\">"
									 +			typeNameLine
									 +"			<div class=\"video\">"
									 +"				<video controls='controls' width=\"100%\" height=\"100%\" src=\""+specialVo.specialPage+"\"video/mp4 ></video>"
									 +"				<div style=\"background:url("+ specialVo.specialCover +") no-repeat; background-size:100% 100%; \" class=\"play-wrap\">"
									 +"					<div class=\"play\"></div>"
									 +"					<div class=\"videotime\">"+specialVo.objValue+"</div>"
									 +"				</div>"
									 +"			</div>"
									 +"			<div class=\"bot\">"
									 +"				<h4>"+ specialVo.specialTitle +"</h4>"
									 +"				<div class=\"botfunc\">"
									 +"					<div class=\"sc fl"+collectClz+"\" data-check-collection="+specialVo.isCollection+"><span>"+ specialVo.collectionCount +"</span></div>"
									 +"					<div class=\"msg fl\" onclick=\"location.href='"+clickComment+"'\"><span>"+ specialVo.commentCount +"</span></div>"
									 +"					<div class=\"dz fl" + dzClz + "\" data-check-praise="+specialVo.isPraise+"><span>"+ specialVo.praiseCount +"</span></div>"
									 +"					<div id='share"+specialVo.specialId+"' data-specialIntroduce='"+ specialVo.specialIntroduce +"' imgUrl-id = '"+ specialVo.specialCover +"' share-url='/weixin/discovery/toTorialComment?specialId="+ +specialVo.specialId + "&shareCustomerId=" + customerId + "' class=\"icon fl\"><span>"+ specialVo.shareCount
									 +"					</span>"+integral+"</div>"
									 +"				</div>"
									 +"			</div>"
									 +"		</div>"
									 +"	</li>";
						};
						showHtml    +="	 </ul>"
								     +" </li>";
						$(".videolist").append(showHtml);
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
function lineAddOrCancelPraise($this, specialId, praiseId, praiseType){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer + "/weixin/tologin?backUrl="+backUrl;
		return;
	}
	
	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
		showvaguealert('出现异常啦');
		return;
	}
	var url = mainServer + "/weixin/praise/addPraise";
	var data={
		"praiseType":praiseType,
		"objId":specialId
	};
	if(praiseId > 0){
		url = mainServer + "/weixin/praise/cancelPraise";
		if(praiseId == null || praiseId == "" || isNaN(praiseId) || parseFloat(praiseId) <= 0){
			showvaguealert('出现异常啦');
			return;
		}
		data={
	 		"praiseId":praiseId
	 	};
	}
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(praiseId > 0){
					$this.removeClass('on');
					var dzNum = $this.find('span').html();
					dzNum--;
					$this.find('span').html(dzNum);
					showvaguealert("已取消点赞");
				}else{
					$this.attr("data-check-praise", data.data);
					$this.addClass('on');
					var dzNum = $this.find('span').html();
					dzNum++;
					$this.find('span').html(dzNum);
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

function lineAddOrCancelCollection($this,specialId,collectionId, type){

	if(customerId == null || customerId <= 0){
		window.location.href = mainServer + "/weixin/tologin?backUrl="+backUrl;
		return;
	}
	
	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
		showvaguealert('出现异常啦');
		return;
	}
	var url = mainServer + "/weixin/collection/addCollection";
	var data={
		"collectionType":type,
		"objId":specialId
	};
	if(collectionId > 0){
		url = mainServer + "/weixin/collection/cancelCollection";
		data={
	 		"collectionId":collectionId
	 	};
	}
	
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(collectionId > 0){ //取消收藏
					$this.removeClass('on');
					var scNum =  $this.find('span').html();
					scNum--;
					$this.find('span').html(scNum);
					showvaguealert("已取消收藏");
				}else{	//成功收藏
					$this.attr("data-check-collection", data.data);
					$this.addClass('on');
					var scNum = $this.find('span').html();
					scNum++;
					$this.find('span').html(scNum);
					showvaguealert("收藏成功");
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