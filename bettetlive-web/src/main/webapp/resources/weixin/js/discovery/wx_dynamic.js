
$(function(){
	containner.inIt()
})
//初始化瀑布流
var $grid = $('.grid').masonry({
	itemSelector :'.grid-item',
	gutter:10
});

//瀑布流
var containner = {
	obj:{//请求参数
		pageIndex:1,
		pageSize:10,
		loadtobottom:true,
		pageCount:1,//总页数
		dataFall:[]
	},
	scroll:function(){//滚动事件上拉加载
		var oSelf = this;
		 $(window).scroll(function(){
			//导航右边隐藏显示效果
			 if($(this).scrollTop() >= $('.banner').height()){
					$('.head-right').show(500)
				}else{
					$('.head-right').hide(500)
				};
			  $grid.masonry('layout');
		      var scrollTop = $(this).scrollTop();
		      var scrollHeight = $(document).height();
		      var windowHeight = $(this).height();
		      if(scrollTop + windowHeight == scrollHeight){
		    	  if(oSelf.obj.pageIndex < oSelf.obj.pageCount){
		    		  if(oSelf.obj.loadtobottom == true){
				    	  oSelf.obj.pageIndex++;
				    	  if(oSelf.obj.pageIndex <= oSelf.obj.pageCount){
				    		  $(".loadingmore").show();  
				    		  //请求数据
				    		  oSelf.getData();
				    		  setTimeout(function(){
				    			  $(".loadingmore").hide(); 	
								},1500);
				    	  }
		    		  }else{
		    			  oSelf.obj.loadtobottom = false
		    		  }
		    	  }
		      }
		})
	},
	//请求数据
	getData:function(){
		var oSelf = this;
		$(".initloading").show();
		$.ajax({
	        dataType:"json",
			type:'post',
			url:mainServer + '/weixin/discovery/queryDynamicList',
			data:{
				rows:oSelf.obj.pageSize,
				pageIndex:oSelf.obj.pageIndex
			},
			success:function(data){
				if(data.code != "1010"){
					showvaguealert(data.msg);
				}else{
					if(data.data == null || data.data.length <= 0){
						$(".intelligent").hide();
					};
					//保存总页数
					oSelf.obj.pageCount = data.pageInfo.pageCount;

					//保存数据
					oSelf.obj.dataFall = data.data;
					setTimeout(function(){
				       oSelf.appendFall();
				       $(".initloading").hide();
				   },300)
				};
				
			  
			 },
			error:function(e){
				console.log('请求失败')
			}
		            
       })
	},
	//创建dom节点
	appendFall:function(){
		var oSelf = this;
		var dataFall = oSelf.obj.dataFall;
		
		//遍历循环创建节点
		$.each(dataFall, function(index ,value) {
			var dataLength = dataFall.length;
          		$grid.imagesLoaded().done( function() {
		        	$grid.masonry('layout');
		        });
	      var detailUrl;
      	  var $griDiv = $('<div class="grid-item item" data-article-id = '+ value.articleId +'>');
      	  var $img = $('<img class="item-img" >');
      	  $img.attr('src',value.articleCover).appendTo($griDiv);
      	  var $section = $('<section class="section-p">');
      	  $section.appendTo($griDiv);
      	  var $h3 = $('<h3>');
      	  $h3.html(value.articleTitle).appendTo($section);
      	  var $p = $('<p>');
      	  $p.html(value.content).appendTo($section);
      	  var $personage = $("<div class='personage'>");
      	  $personage.appendTo($section);
      	  var $perleft = $("<div class='per-left' data-author-id='"+value.customerId+"'>");
      	  $perleft.appendTo($personage); 
      	 
      	  var $preright = $("<div class='per-right' data-check-praise= "+ value.isPraise +">");
      	  //判断是否已经点赞
      	  value.isPraise > 0?$preright.addClass('on'):$preright.removeClass('on');
      	  $preright.appendTo($personage); 
      	  var $leftimg = $("<img>");
      	  $leftimg.attr('src',value.headUrl).appendTo($perleft);
      	  var $span = $('<span>'+ value.author +'</span>');
      	  $span.appendTo($perleft);
      	  var $rspan = $('<span>'+ value.praiseCount +'</span>');
      	  $rspan.appendTo($preright);
      	  
      	  var $items = $griDiv;
		  $items.imagesLoaded().done(function(){
				$grid.masonry('layout');
	            $grid.append( $items).masonry('appended', $items);
		  	})
           });
	},
	
	//点击事件跳转
	isClick:function(){
		
		$('.grid').on('click','.item-img',function(){
			toDetail($(this).parents('.item').attr('data-article-id'))
		});
		$('.grid').on('click','h3',function(){
			toDetail($(this).parents('.item').attr('data-article-id'))
		});
		$('.grid').on('click','p',function(){
			toDetail($(this).parents('.item').attr('data-article-id'))
		});
		$('.grid').on('click','.per-left',function(){
			toOtherHome($(this).attr("data-author-id"))
		});
		//点赞事件
		$('.grid').on('click','.per-right', function(){
			var articleId = $(this).parents(".grid-item").attr("data-article-id");
			//判断是否已经点击
			if($(this).hasClass('on')){
				//判断是否为点赞节点
					var praiseId = $(this).attr("data-check-praise");
					lineAddOrCancelPraise($(this), articleId, praiseId, 4);
				
			}else{
				//判断是否点赞节点
				lineAddOrCancelPraise($(this),articleId,0,4);
			}
		});
		
	},
	//初始化
	inIt:function(){
		this.getData();
		this.scroll();
		//this.intelLigent();
		this.isClick()
	}
	
}

var anniversary = {
	// 读取缓存
	getStorage : function() {
		var anniversaryObj = JSON.parse(localStorage.getItem("circleGuideObj"));
		if(anniversaryObj && parseInt(anniversaryObj.flag) >= 0) {
			$('.guide-wrap').hide();
			if(anniversaryObj.flag == 1 && customerId != null && customerId != "") {
				saveGuide(anniversaryObj.guideType);
			}
		} else {
			checkCircleGuide();
		}
	}
}

/**
 * 美食达人
 */
function queryHotList(){
		$.ajax({                                       
			url: mainServer + '/weixin/customermaster/queryHotList',
			data:{},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code != "1010"){
					showvaguealert(data.msg);
				}else{ //获取成功
					if(data.data == null || data.data.length <= 0){
						$(".intelligent").hide();
					}
					
					var list = data.data;
					
					//打印期刊
					for (var continueIndex in list) {
 						if(isNaN(continueIndex)){
 							continue;
 						}
 						
 						var customerMasterVo = list[continueIndex];
 						var customer = customerMasterVo.customerVo;
 						var showHtml = '<li>'
									+ '		<a href="javascript:toOtherHome('+customer.customer_id+');">'
									+ '		<dl>'
									+ '			<dt>'
									+ '				<img src="'+customer.head_url+'"/>'
									+ '			</dt>'
									+ '			<dd>'
									+ '				<span>'+customer.nickname+'</span>'
									+ '			</dd>'
									+ '		</dl>'
									+ '		</a>'
									+ '	</li>';
 						
						$(".intelist").append(showHtml);
					};
					//获取每个li的margin-left的值
					var marleftNum = $('.intelist li:last-child').css('margin-left').split('p')[0] * 1;
					//计算知名达人列表的宽度
					var widthNum = $('.intelist li').width();
					$('.intelist').width((marleftNum + widthNum) * $('.intelist li').length - marleftNum);
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
}

function toPublic(){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer + "/weixin/tologin?backUrl="+backUrl;
	}else{
		window.location.href= mainServer + "/weixin/discovery/toPublishDynamic?backType=1";
	}
}

function toMyIndex(){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer + "/weixin/tologin?backUrl="+backUrl;
	}else{
		window.location.href= mainServer + "/weixin/socialityhome/toSocialityHome";
	}
}


function toDetail(articleId){
	window.location.href = mainServer + "/weixin/discovery/toDynamicDetail?articleId="+articleId;
}

function lineAddOrCancelPraise($this, articleId, praiseId, praiseType){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer + "/weixin/tologin?backUrl="+backUrl;
		return;
	};
	
	if(articleId == null || articleId == "" || isNaN(articleId) || parseFloat(articleId) <= 0){
		showvaguealert('出现异常啦');
		return;
	};
	var url = mainServer + "/weixin/praise/addPraise";
	var data={
		"praiseType":praiseType,
		"objId":articleId
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

function toOtherHome(otherCustId){
	 if(customerId != null && customerId > 0 && customerId == otherCustId){
		window.location.href = mainServer + "/weixin/socialityhome/toSocialityHome";
	 }else{
	 	window.location.href = mainServer + "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+otherCustId;
	 }
}
//圈子引导
function checkCircleGuide() {
	$.ajax({                                       
		url: mainServer + '/weixin/customerGuide/checkCircleGuide',
		data:{},
		type:'post',
		dataType:'json',
		success:function(data){
			customerCircle = data.customerCircle;
			if(customerCircle == 'Y') {
				var checkFlag = 0;// 未登陆
				if(customerId != null && customerId != '') {
					checkFlag = 1;
				}
				var guideType = 2;
				//缓存状态标记
				var obj = {
					flag: checkFlag,
					guideType: guideType
				};
				localStorage.setItem('circleGuideObj',JSON.stringify(obj));
				$('.guide-wrap').fadeIn(1000);
				if(checkFlag == 1) {
					saveGuide(guideType);
				}
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 新手指引
 */
$('.guide-wrap').click(function(){
		$('.guide-wrap').fadeOut(1000);
});

//新增数据
function saveGuide(type) {
	if(customerId > 0) {
		$.ajax({                                       
			url: mainServer + '/weixin/customerGuide/saveGuide',
			data:{
				'customerId': customerId,
				'type': type
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == '1010') {
					var anniversaryObj = JSON.parse(localStorage.getItem("circleGuideObj"));
					if(anniversaryObj) {
						var obj = {
							flag: 1,
							guideType: type
						};
						localStorage.setItem('circleGuideObj',JSON.stringify(obj));
					}
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
}