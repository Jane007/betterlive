

$(function(){
	collect.init()
})
//创建原型对象
var collect = {
	//所有的请求参数
	obj:{
		thenUrl:window.location.href,
		//商品
		pro:{
			rows:10,//请求条数
			pageIndex:1,//请求页码
			pageCount:'',//总页数
		},
		//精选
		selection:{
			rows:10,
			pageIndex:1,
			pageCount:''//总夜数
		},
		//视屏
		video:{
			rows:3,
			pageIndex:1,
			pageCount:''//总夜数
		}
		
	},
	//请求视频数据
	videoData:function(){
		var oSelf = this;
		var videoList = '';
		var _html = '';
		$.post(mainServer+'/weixin/collection/queryListByTutorial',{
			rows:oSelf.obj.video.rows,
			pageIndex:oSelf.obj.video.pageIndex
		},function(data,error){
			if(data.code == '1010'){
				videoList = data.data;
				//保存总页数
				oSelf.obj.video.pageCount = data.pageInfo.pageCount;
				var specialVo = '';
				var integral = "";
				if (integralSwitch != null && integralSwitch == 0) {
					integral = "<div class='hold'></div>";
				}
				for(var i = 0; i < videoList.length; i++){
					specialVo = videoList[i].specialVo;
					_html = [
				        	'<li data-delete="'+ videoList[i].collectionId +'">',
				        	'	<dl>',
				        	'		<dt>',
				        	' 			<div class="select"></div>',
				        	'		</dt>',
				        	'		<dd>',
							'			<div class="licontent" data-status="'+ specialVo.status +'">',
							'				<div class="video">',
							'						<video controls="controls" width="100%" height="100%" src="'+ specialVo.specialPage +'"></video>',
							'						<div class="play-wrap" style="background:url('+ specialVo.specialCover +') no-repeat;background-size:100% 100%">',
							'							<div class="play"></div>',
							'							<div class="videotime '+ (specialVo.objValue?'':'hide') +'">'+ specialVo.objValue +'</div>',
							'						</div>',
							'					</div>',
							'					<div class="bot">',
							'						<h4>'+ specialVo.specialTitle +'</h4>',
							'						<div class="botfunc">',
							'						<div class="msg fl" data-specialTitle="'+ specialVo.specialTitle +'"><span>'+ specialVo.commentCount +'</span></div>',
							'						<div class="dz fl '+ (specialVo.isPraise>0?'on':'') +'"  data-articleId="'+ videoList[i].objId +'" data-isPraise="'+ specialVo.isPraise +'" data-specialId="'+ specialVo.specialId +'"><span>'+ specialVo.praiseCount +'</span></div>',
							'						<div id="share'+specialVo.specialId+'" class="icon fl" data-specialIntroduce= "'+ specialVo.specialIntroduce +'" imgUrl-id = "'+ specialVo.specialCover +'" data-specialId="'+ specialVo.specialId +'"><span>'+ specialVo.shareCount,
							'						</span>'+integral+'</div>',
							'					</div>',
							'				</div>',
							'			</div>',
							'		</dd>',
							'	</dl>',
							'</li>'
				        ].join('');
					$('.videolist').append(_html)
				};
				if($('.videolist li').length > 0){
					$('.videolist').siblings('.nodata').hide()
				}else{
					$('.videolist').siblings('.nodata').show()
				}
			}
		})
	},
	//请求精选数据
	selectionData:function(){
		var oSelf = this;
		var seleclist = '';
		$.post(mainServer+'/weixin/collection/queryListBySpecialArticle',{
			rows:oSelf.obj.selection.rows,
			pageIndex:oSelf.obj.selection.pageIndex
		},function(data,error){
			if(data.code == '1010'){
				var specialArticleVo = '';
				var _html = '';
				seleclist = data.data;
				for(var i = 0; i < seleclist.length; i++){
					specialArticleVo = seleclist[i].specialArticleVo;
					_html = [
								'<li data-delete="'+ specialArticleVo.collectionId +'">',
								'	<dl>',
								'		<dt>',
								' 			<div class="select"></div>',
								'		</dt>',
								'		<dd>',
								'			<div class="selec-wrap" data-articletitle="'+ specialArticleVo.articleTitle +'" data-status= "'+specialArticleVo.status+'" data-articleId = "'+ seleclist[i].specialArticleVo.articleId +'">',
								'				<div class="selec-top">',
								'					<img src="'+ specialArticleVo.articleCover +'"/>',
								'				</div>',
								'				<div class="selec-bot">',
								'					<p class="text">'+ specialArticleVo.articleTitle +'</p>',
								'					<div class="operate">',
								'						<div class="pl fl">',
								'							<span>'+ specialArticleVo.commentCount +'</span>',
								'						</div>',
								'						<div class="dz fl '+ (specialArticleVo.isPraise>0?'on':'') +'" data-isPraise="'+ specialArticleVo.isPraise +'" data-articleId="'+ specialArticleVo.articleId +'" data-id="'+ seleclist[i].collectionId +'">',
								'							<span>'+ specialArticleVo.praiseCount +'</span>',
								'						</div>',
								'					</div>',
								'				</div>',
								'			</div>',
								'		</dd>',
								'	</dl>',
								'</li>',
							].join('');
					$('.selection').append(_html)
				};
				if($('.selection li').length > 0){
					$('.selection').siblings('.nodata').hide()
				}else{
					$('.selection').siblings('.nodata').show()
				}
			}
		})
	},
	//请求商品数据
	productData:function(){
		var oSelf = this;
		var prolist = ''; 
		let _html = '';
		$.post(mainServer+'/weixin/collection/queryListByProduct',{
			rows:oSelf.obj.pro.rows,
			pageIndex:oSelf.obj.pro.pageIndex
		},function(data,errror){
			if(data.code=='1010'){
				prolist = data.data;
				//保存总页数
				oSelf.obj.pro.pageCount = data.pageInfo.pageCount;
				if(prolist && prolist.length > 0){
					for(var i = 0; i < prolist.length; i++){
						_html = [
									'<li class="pro-li" pro-id = "'+ prolist[i].collectionId +'" data-status = "'+  prolist[i].productVo.status +'" data-activeid = "'+ prolist[i].productVo.activity_id +'" data-type = "'+ prolist[i].productVo.activityType +'" data-id="'+ prolist[i].productVo.product_id +'">',
									'<div class="select"></div>',
									'	<dl>',
									'		<dt>',
									'			<img src="'+ prolist[i].productVo.product_logo +'"/>',
									'			<span class="'+ (prolist[i].productVo.status != '1'?'show':'') +'">已下架</span>',
									'		</dt>',
									'		<dd>',
									'			<div class="pro-title">',
									'				<h3 class="ellipse">'+ prolist[i].productVo.product_name +'</h3>',
									'				<span>新品</span>',
									'			</div>',
									'			<div class="pro-explain ellipse">'+ (prolist[i].productVo.share_explain?prolist[i].productVo.share_explain:"") +'</div>',
									'			<div class="pro-price">',
									'				<div class="price">',
									'					<span class="' + (prolist[i].productVo.activityPrice ? "" : "hide" )+ '">￥'+prolist[i].productVo.activityPrice +'</span>',		
									'					<span class="' + (prolist[i].productVo.activityPrice ? "delete" : "" )+ '">￥'+ prolist[i].productVo.price +'</span>',
									'				</div>',
									'				<div class="data">月销售'+ prolist[i].productVo.salesVolume +'份</div>',
									'			</div>',
									'		</dd>',
									'	</dl>',
									'</li>',
								    ].join('');
						$('.prolist').append(_html)
					}
				};
				if($('.pro-li').length > 0){
					$('.prolist').siblings('.nodata').hide();
				}else{
					$('.prolist').siblings('.nodata').show()
				}
			}else if(data.code == "1011"){
				window.location.href = mainServer+"/weixin/tologin?backUrl="+oSelf.obj.thenUrl;
			}else{
				$.toast("出现异常","text",500);
			}
		})
		
	},
	//上拉加载分页
	loadMore:function(){
		var oSelf = this;
		var loading = false;//上拉加载状态标记
		var loadtype = ''; //标记加载商品或者是精选以及视频("0"为商品,"1"为精选,"2"为视频)
		$('.swiper-slide').infinite();
		$('.swiper-slide').infinite().on("infinite", function() {
			if(loading) return;
			loading = true;
			$('.weui-loadmore').hide();
			$(this).find('.weui-loadmore').show();
			//根据type值请求不同接口数据
			loadtype = $('.swiper-pagination-bullet-active').attr('data-id');
			setTimeout(function() {
			   if(loadtype == '0'){//请求商品数据
				 if( oSelf.obj.pro.pageIndex <=  oSelf.obj.pro.pageCount){
					 oSelf.obj.pro.pageIndex++;
					 oSelf.productData()
				 }
				  
			   }else if(loadtype == '1'){//请求精选数据
				 if(oSelf.obj.selection.pageIndex <=  oSelf.obj.selection.pageCount){
					 oSelf.obj.selection.pageIndex++;
					 oSelf.selectionData()
				 }
			   }else if(loadtype == '2'){//请求视频数据
					 if(oSelf.obj.video.pageIndex <=  oSelf.obj.video.pageCount){
						 oSelf.obj.video.pageIndex++;
						 oSelf.videoData()
					 }
				   }
			   loading = false;
			   $('.weui-loadmore').hide();
			 }, 2000);   //模拟延迟
		})
		//销毁上拉加载的组件  $(document.body).destroyInfinite(),在需要的地方销毁
	},
	//模拟请求数据 要初始化的时候调用一次其余在上拉加载分页里面调用
	//视频播放
	videoPlay:function(){
		//播放按钮事件
		$(document).on('click','.play',function(){
			var status = $(this).parents('.licontent').attr('data-status');
			var clickComment = mainServer+"/common/toFuwubc?ttitle=视频信息提示&tipsContent="+$(this).parents('.licontent').find('.msg').attr('data-specialtitle')+"已被删除";
			if(status == 1){//判断是否为下架视频
				if(!$('.compile').hasClass('on')){
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
				}
			}else{
				window.location.href = clickComment
			}
		})
	},
	//点赞以及收藏效果
	operate:function(){
		//精选点赞接口
		var addUrl = mainServer+"/weixin/praise/addPraise";
		//精选取消点赞接口
		var cancelUrl = mainServer+"/weixin/praise/cancelPraise";
		//精选点赞
		$(document).on('click','.selection .dz',function(){
			//点击获取当前收藏节点的点赞数量
			let num = $(this).children().html();
			let self = this;
			//判断是否已经点赞
			if(!$('.compile').hasClass('on')){
				if($(self).hasClass('on')){
					$.post(cancelUrl,{
						"praiseId":$(self).attr('data-ispraise')
					},function(data,error){
						if(data.code == '1010'){
							num--;
							$(self).removeClass('on');
							$(self).children().html(num);
							$.toast('取消点赞',"text",500)
						}else{
							$.toast(data.msg,"text",500)
						}
					});
					
				}else{
					$.post(addUrl,{
						"praiseType":4,
				 		"objId":$(self).attr('data-articleid')
					},function(data,error){
						if(data.code == '1010'){
							num++;
							$(self).children().html(num);
							$(self).addClass('on');
							$(self).children().html(num);
							$(self).attr('data-ispraise',data.data);
							$.toast("点赞成功","text",500);
						}else{
							$.toast(data.msg,"text",500)
						}
					})
					
				}
			}
		});
		//视频点赞
		$(document).on('click','.videolist .dz',function(){
			//点击获取当前收藏节点的点赞数量
			let num = $(this).children().html();
			let self = this;
			//判断是否已经收藏
			if(!$('.compile').hasClass('on')){
				if($(this).hasClass('on')){
					$.post(cancelUrl,{
						"praiseId":$(self).attr('data-ispraise')
					},function(data,error){
						if(data.code == '1010'){
							num--;
							$(self).removeClass('on');
							$(self).children().html(num);
							$.toast("取消点赞","text",500);
						}else{
							$.toast(data.msg,"text",500)
						}
					});
					
				}else{
					$.post(addUrl,{
						"praiseType":2,
				 		"objId":$(self).attr('data-articleid')
					},function(data,error){
						if(data.code == '1010'){
							num++;
							$(self).children().html(num);
							$(self).addClass('on');
							$(self).children().html(num);
							$(self).attr('data-ispraise',data.data);
							$.toast("点赞成功","text",500);
						}else{
							$.toast(data.msg,"text",500)
						}
					})
				}
			}
		})
	},
	//滑动tab效果
	tab:function(){
		var swiper = new Swiper('.swiper-container', {
			pagination: {
				el: '.my-pagination-ul',
				clickable: true,
				renderBullet: function (index, className) {
					 switch(index){
						 case 0:text='商品';break;
						 case 1:text='精选';break;
						 case 2:text='视频';break;
					 };
					return '<li data-id = "'+ index +'" class="' + className + '"><span>' + text + '</span></li>';
				 },
			},
		});
		//点击取消编辑状态
		$('.my-pagination-ul li').click(function(){
			$('.compile').removeClass('on').html('编辑');
			$('.select').hide();
			$('.select').removeClass('on');
			$('.pro-li').removeClass('on');
			$('.selection li').removeClass('on');
			$('.videolist li').removeClass('on');
			$('.cancel').hide();
			$('.swiper-slide').removeClass('swiper-no-swiping');
		})
		
	},
	//计算slide的高度
	slide:function(){
		$('.swiper-slide').height($(window).height() - $('.my-pagination-ul').height() - $('.header-wrap').height() - 10)
	},
	//编辑以及删除订单
	deletcOrder:function(){
		var oSelf = this;
		//商品点击跳转详情页面
		var localurl = '';
		//取消收藏商品
		var abrogate = '';
		//保存商品id
		var dataid,
			activeid,
			datatype,
			datastatus;
		//编辑点击事件
		$('.compile').click(function(){
			//清除选中商品id
			abrogate='';
			if($(this).hasClass('on')){
				$(this).removeClass('on');
				$(this).html('编辑');
				//取消选中状态
				$('.swiper-slide').removeClass('swiper-no-swiping');
				$('.swiper-slide .select').hide();
				$('.swiper-slide-active .select').removeClass('on');
				$('.pro-li').removeClass('on');
				$('.selection li').removeClass('on');
				$('.videolist li').removeClass('on');
				$('.swiper-slide-active .select').hide();
				$('.cancel').hide();
			}else{
				$(this).addClass('on');
				$(this).html('完成');
				$('.swiper-slide').addClass('swiper-no-swiping');
				$('.swiper-slide .select').hide();
				$('.swiper-slide-active .select').show();
				$('.cancel').show();
				//停止所有的视频播放
				var videoArr = document.getElementsByTagName('video');
				//循环停止播放所有的视频
				for(var i = 0; i < videoArr.length; i++){
					videoArr[i].pause();
				};
				$('.play-wrap').show()
			}
			
		});
		//商品订单选择事件
		$('.prolist').on('click','.pro-li',function(){
			//保存各种id值
			dataid = $(this).attr('data-id');
			activeid = $(this).attr('data-activeid');
			datatype = $(this).attr('data-type');
			datastatus = $(this).attr('data-status');
			if($('.compile').hasClass('on')){
				//判断书否为选中状态
				if($(this).hasClass('on')){
					$(this).removeClass('on');
					$(this).find('.select').removeClass('on')
				}else{
					$(this).addClass('on');
					$(this).find('.select').addClass('on')
				};
			}else{
				localurl = mainServer+"/weixin/product/towxgoodsdetails?productId=";
				if(datatype == '2'){//限量抢购
					localurl = mainServer+"/weixin/product/toLimitGoodsdetails?productId=";
					window.location.href = localurl + dataid+"&specialId="+activeid+"&backUrl="+oSelf.obj.thenUrl;
				}else if(datatype == '3'){//团购
					localurl = mainServer+"/weixin/product/toGroupGoodsdetails?productId=";
					window.location.href = localurl + dataid+"&specialId="+activeid+"&backUrl="+oSelf.obj.thenUrl;
				}else if(datastatus != '1'){ //下架商品
					localurl = mainServer+"/common/toFuwubc?ttitle=商品信息提示&tipsContent=";
					window.location.href = localurl + $(this).find('.pro-title h3').html() +"已下架";
				}else{
					window.location.href = localurl + dataid+"&backUrl="+oSelf.obj.thenUrl
				}
			}
		});
		//删除收藏商品
		$('.cancel span').click(function(){
			var deletetype = $('.swiper-slide.swiper-slide-active').attr('data-delete');
			if(deletetype == '0'){
				$('.pro-li.on').each(function(val,index){
					abrogate += $(this).attr('pro-id') + ',';
				});
			};
			if(deletetype == '1'){
				$('.selection li.on').each(function(val,index){
					abrogate += $(this).attr('data-delete') + ',';
				});
			};
			if(deletetype == '2'){
				$('.videolist li.on').each(function(val,index){
					abrogate += $(this).attr('data-delete') + ',';
				});
			};
			if(abrogate && abrogate.length > 0){
				//请求接口
				$.post(mainServer+'/weixin/collection/cancelCollectionAll',{
					"collectionIds":abrogate
				},function(data,error){
					if(data.code = '1010'){
						$('.compile').removeClass('on');
						$('.compile').html('编辑');
						if(deletetype == '0'){
							$('.pro-li.on').each(function(val,index){
								$(this).remove()
							});
							//删除成功后判断当前收藏是否为空
							if($('.prolist li').length > 0){
								$('.prolist').siblings('.nodata').hide()
							}else{
								$('.prolist').siblings('.nodata').show()
							}
							
						}else if(deletetype == '1'){
							$('.selection li.on').each(function(val,index){
								$(this).remove()
							});
							//删除成功后判断当前收藏是否为空
							if($('.selection li').length > 0){
								$('.selection').siblings('.nodata').hide()
							}else{
								$('.selection').siblings('.nodata').show()
							}
						}else if(deletetype == '2'){
							$('.videolist li.on').each(function(val,index){
								$(this).remove()
							});
							//删除成功后判断当前收藏是否为空
							if($('.videolist li').length > 0){
								$('.videolist').siblings('.nodata').hide()
							}else{
								$('.videolist').siblings('.nodata').show()
							}
						};
						$('.select').hide();
						$('.cancel').hide();
					}
				})
			}else{
				$('.compile').removeClass('on');
				$('.compile').html('编辑');
				$('.select').hide();
				$('.cancel').hide();
			};
			$('.swiper-slide').removeClass('swiper-no-swiping');
			
		});
		var clickLocal = mainServer+"/weixin/discovery/toSelectedDetail?articleId=";
		var statusId = '';//判断是否下架id
		var articletitleId = '';
		//精选点击事件
		$('.selection').on('click','.selec-top',function(){//图片点击跳转事件
			if(!$('.compile').hasClass('on')){
				statusId = $(this).parents('.selec-wrap').attr('data-status');
				articletitleId = $(this).parents('.selec-wrap').attr('data-articletitle');
				if(statusId != 1){
					clickLocal = mainServer+"/common/toFuwubc?ttitle=文章信息提示&tipsContent="+articletitleId+"已被删除";
				};
				window.location.href = clickLocal+$(this).parents('.selec-wrap').attr('data-articleid')+"&backUrl="+oSelf.obj.thenUrl;
			}
		});
		$('.selection').on('click','.text',function(){//文本击跳转事件
			if(!$('.compile').hasClass('on')){
				statusId = $(this).parents('.selec-wrap').attr('data-status');
				articletitleId = $(this).parents('.selec-wrap').attr('data-articletitle');
				if(statusId != 1){
					clickLocal = mainServer+"/common/toFuwubc?ttitle=文章信息提示&tipsContent="+articletitleId+"已被删除";
				};
				window.location.href = clickLocal+$(this).parents('.selec-wrap').attr('data-articleid')+"&backUrl="+oSelf.obj.thenUrl;
			}
		});
		$('.selection').on('click','.pl',function(){//评论点击跳转事件
			if(!$('.compile').hasClass('on')){
				statusId = $(this).parents('.selec-wrap').attr('data-status');
				articletitleId = $(this).parents('.selec-wrap').attr('data-articletitle');
				if(statusId != 1){
					clickLocal = mainServer+"/common/toFuwubc?ttitle=文章信息提示&tipsContent="+articletitleId+"已被删除";
				};
				window.location.href = clickLocal+$(this).parents('.selec-wrap').attr('data-articleid')+"&backUrl="+oSelf.obj.thenUrl;
			}
		});
		//精选选中删除
		$('.selection').on('click','dl',function(){
			if($('.compile').hasClass('on')){
				if(!$(this).parent().hasClass('on')){
					$(this).parent().addClass('on');
					$(this).find('.select').addClass('on');
				}else{
					$(this).parent().removeClass('on');
					$(this).find('.select').removeClass('on');
				}
			}
		});
		//视频评论跳转事件
		$('.videolist').on('click','.msg',function(){
			let clickComment = mainServer+"/weixin/discovery/toTorialComment?flag=2&specialId="+$(this).siblings('.dz').attr('data-specialid')+"&backUrl="+oSelf.obj.thenUrl;
			if($(this).parents('.licontent').attr('data-status') != 1){ //已下架
				clickComment = mainServer+"/common/toFuwubc?ttitle=视频信息提示&tipsContent="+$(this).attr('data-specialtitle')+"已被删除";
			};
			if(!$('.compile').hasClass('on')){
				window.location.href = clickComment
			}
		});
		//视频分享
		$(document).on('click','.videolist .icon',function(){
			let clickComment = mainServer+"/weixin/discovery/toTorialComment?flag=2&specialId="+$(this).siblings('.dz').attr('data-specialid')+"&shareCustomerId"+ customerId +"&backUrl="+oSelf.obj.thenUrl;
			if($(this).parents('.licontent').attr('data-status') != 1){ //已下架
				clickComment = mainServer+"/common/toFuwubc?ttitle=视频信息提示&tipsContent="+$(this).siblings('.msg').attr('data-specialtitle')+"已被删除";
				window.location.href = clickComment
			}else{
				if(!$('.compile').hasClass('on')){
					var url = mainServer+'/weixin/shareWeixin';
					var pageUrl = window.location.href;
					var sharUrl = clickComment;
					var title = $(this).parents('.bot').find('h4').html();
					var imgUrl = $(this).attr('imgUrl-id');
					var specialintroduce = $(this).attr('data-specialintroduce');
					var videoId = $(this).attr("data-specialId");
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
					})
					}
				}
			}
		});
		//分享提示弹窗
		$('#cover').click(function(){
			$(this).fadeOut(500)
		});
		$('.share').click(function(){
			$(this).fadeOut(500)
		});
		//选择删除收藏视频
		$('.videolist').on('click','dl',function(){
			if($('.compile').hasClass('on')){
				if(!$(this).parent().hasClass('on')){
					$(this).parent().addClass('on');
					$(this).find('.select').addClass('on');
				}else{
					$(this).parent().removeClass('on');
					$(this).find('.select').removeClass('on');
				}
			}
		});
		//返回按钮事件
		$('.goback').click(function(){
			window.location.href = mainServer + '/weixin/toMyIndex'
		});
		
		
	},
	init:function(){
		//保存当前页面地址
		if(this.obj.thenUrl.indexOf("#")!=-1){
			this.obj.thenUrl = this.obj.thenUrl.substring(0,theUrl.indexOf("#"));
 		};
		this.tab();
		this.slide();
		this.videoData();
		this.loadMore();
		this.videoPlay();
		this.operate();
		this.productData();
		this.selectionData();
		this.deletcOrder()
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
			$.toast("访问出错","text");
			
		}
	});
}

function hideShare(){
	$("#cover").hide();
	$('.share').hide();
}

