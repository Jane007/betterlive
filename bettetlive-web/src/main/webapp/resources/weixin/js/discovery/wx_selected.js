var theUrl = window.location.href;
 $(function(){
	//购物车数据处理
		var cartCnt = $('.footer').find('span').html();
		if(cartCnt==0 || cartCnt == null){
			$('.gwnb').hide();
		}else if(cartCnt>99){
			$('.footer').find('span').html("99+");
		}//否则就不处理，照常显示
		
 	//判断是否隐藏底部导航
	if(theUrl.indexOf("#")!=-1){
		theUrl = theUrl.substring(0,theUrl.indexOf("#"));
	}
 	
 	var flag = theUrl.split('=')[1];
	if(flag && flag == "ok"){
		localStorage.setItem('discoveryBackFlag',flag);
		$(".goback").show();
		$('.footer').hide();
	}else if(localStorage.getItem('discoveryBackFlag')){
		$(".goback").show();
		$('.footer').hide();
	}else{
		$('.footer').show();
	};
	//点击返回按钮清除缓存
	$('.goback').click(function(){
		localStorage.removeItem('discoveryBackFlag');
		window.location.href=mainServer+"/weixin/index";
	});
	
	queryRecomendArticles();
	selecteType();
	queryDay();
 	queryDatas(1, 3);
 	article.inIt();
 	
 	// 新手指引状态
 	anniversary.getStorage();
});
 var anniversary = {
	// 读取缓存
	getStorage : function() {
		var anniversaryObj = JSON.parse(localStorage.getItem("selectedGuideObj"));
		if(anniversaryObj && parseInt(anniversaryObj.flag) >= 0) {
			$('.guide-wrap').hide();
			if(anniversaryObj.flag == 1 && customerId != null && customerId != "") {
				saveGuide(anniversaryObj.guideType);
			}
		} else {
			checkSelectedGuide();
		}
	}
}
var article = {
	isClick:function(){ //点击事件属性
		$('.recommendation').on('click','.dz', function(){
			var articleId = $(this).attr("data-article-id");
			//判断是否已经点赞
			if($(this).hasClass('on')){
				//判断是否为点赞节点
				if($(this).hasClass('dz')){	//点赞
					var praiseId = $(this).attr("data-check-praise");
					lineAddOrCancelPraise($(this), articleId, praiseId, 4);
				}
			}else{
				//判断是否点赞节点
				if($(this).hasClass('dz')){
					lineAddOrCancelPraise($(this),articleId,0,4);
				}
			}
		});
	},
	//图片失真处理
	imgLoad:function(){
		$(window).load(function(){
			$('img').not($('.banner img')).each(function(){
				if($(this).width() / $(this).parent().width() > $(this).height() / $(this).parent().height()){
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
			})
		})
		
	},
	inIt:function(){//初始化
		this.isClick();
		this.imgLoad()
	}
}

/**
 * 顶部精选列表
 */
function queryRecomendArticles() {
	$.ajax({                                       
		url: mainServer + '/weixin/queryRecomendArticles',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data.length <= 0){  //没有数据
				$(".content .banner").hide();
				return;
			}
			
			var list = data.data;
			for (var conIndex in list) {
				if(isNaN(conIndex)){
					continue;
				}
				var vo = list[conIndex];
				var articleCover = resourcepath + "/weixin/img/discovery/slide.png";
				if(vo.articleCover != null && vo.articleCover != ""){
					articleCover = vo.articleCover;
				}

				var showHtml = '<div class="swiper-slide">'
							+'		<a class="goods" href="'+toDetail(vo.articleId, vo.articleFrom, 0)+'">'
							+'			 <div class="img"><img src="'+ articleCover +'"></div>'
							+'		</a>'
							+'</div>';
				
				
				$("#topSelectId").append(showHtml);
				
			};
			var mySwiper = new Swiper('.swiper-container', {
				loop: true,
				autoplay: {
					delay: 3000,//秒
					disableOnInteraction: false,//滑动不会失效
					reverseDirection: true,//如果最后一个 反向播放
				},
				slidesPerView: "auto",
				autoplayDisableOnInteraction:false,
				centeredSlides:true,
				//watchSlidesProgress: !0,
				//resistanceRatio: 1,
				on: {
					progress: function(b) {
						for(i = 0; i < this.slides.length; i++) {
							slide = this.slides.eq(i), slideProgress = this.slides[i].progress, prevIndent = 4 == i ? .3228 : .0898, scale = 1 > Math.abs(slideProgress + prevIndent) ? .15 * (1 - Math.abs(slideProgress + prevIndent)) + 1 : 1, slide.find(".goods").transform("scale3d(" +
								scale + "," + scale + ",1)")
						}
					},
					setTransition: function(b){
						for(var a = 0; a < this.slides.length; a++) this.slides.eq(a).find(".goods").transition(b)
					}
				}
			});
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 分类列表
 */
function selecteType(){
	$.ajax({                                       
		url: mainServer + '/weixin/discovery/selectedMainArea',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data.length <= 0){  //没有数据
				$(".aticlelist").hide();
				return;
			}
			
			var list = data.data;
			for (var conIndex in list) {
				if(isNaN(conIndex)){
					continue;
				}
				var vo = list[conIndex];
				var articleTypeSrc = resourcepath + "/weixin/img/discovery/articlelist.png";
				if(vo.typeCover != null && vo.typeCover != ""){
					articleTypeSrc = vo.typeCover;
				}
				var showHtml='<li>'
							+'	<dl>'
							+'		<dt>'
							+'			<a href="'+mainServer+'/weixin/discovery/toSelectedArea?typeId='+vo.typeId+'">'
							+'				<img src="'+articleTypeSrc+'"/>'
							+'				<p><span>'+vo.typeCount+'篇文章</span></p>'
							+'			</a>'
							+'		</dt>'
							+'		<dd>'
							+'			<span>'+vo.typeName+'</span>'
							+'		</dd>'
							+'	</dl>'
							+'</li>';
				$(".aticlelist").append(showHtml);
			}
			//计算文章列表宽度
			var mar = $('.aticlelist li').css('marginLeft');
			var widthNum = ($('.aticlelist li').width() + 18) * $('.aticlelist li').length;
			$('.aticlelist').width(widthNum);
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 每日推荐
 */
function queryDay(){
		$.ajax({                                       
			url: mainServer + '/weixin/discovery/queryHomeArticle',
			data:{},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code != "1010"){
					showvaguealert(data.msg);
				}else if(data.data != null){
					var articleVo = data.data;
					$("#dayRecId").show();
					$("#dayRecId").find("a").attr("href", toDetail(articleVo.articleId,articleVo.articleFrom,0));
					$("#dayRecId").find("img").attr("src", articleVo.homeCover);
					$("#dayRecId").find("h4").html(articleVo.articleTitle);
					$("#dayRecId .comment").find("span").html(articleVo.commentCount);
					$("#dayRecId .comment").attr("onclick", "location.href='"+toDetail(articleVo.articleId,articleVo.articleFrom,1)+"'");
					$("#dayRecId .dz").find("span").html(articleVo.praiseCount);
					$("#dayRecId .dz").attr("data-article-id", articleVo.articleId);
					$("#dayRecId .dz").attr("data-check-praise", articleVo.isPraise);
					if(articleVo.isPraise > 0){
						$("#dayRecId .dz").addClass("on");
					}

				}
					
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
}

/**
 * 每周精选
 */
function queryDatas(pageIndex,pageSize){
		$(".initloading").show();
		$.ajax({                                       
			url: mainServer + '/weixin/discovery/querySelectedList',
			data:{rows:pageSize,pageIndex:pageIndex},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code != "1010"){
					showvaguealert(data.msg);
				}else{ //获取成功
					if((data.data == null || data.data.length <= 0) && pageIndex == 1){
						$("#dtList").hide();
//						$(".zanwubg").show();
					}
//					else{
//						$(".zanwubg").hide();
//					}
					
					if(data.pageInfo.pageCount > 1){	//最多显示3期，如果不止3期就显示查看往期
						$("#recList .previous").show();
					}
					
					var list = data.data;
					
					//打印期刊
					for (var continueIndex in list) {
 						if(isNaN(continueIndex)){
 							continue;
 						}
 						var periodicalVo = list[continueIndex];
 						var showHtml = '<div class="bot">'
	 								 + '	<div class="bottitle">'
									 + '		<span>' + periodicalVo.periodical + '</span><h3>' + periodicalVo.periodicalTitle + '</h3>'
									 + '	</div>'
									 + '	<div class="botcontent">'
									 + '		<div class="botbottom">';
 						
 						
 						var articles = periodicalVo.specialArticleList;
 						//打印每期文章
						for(var ctIndex in articles) {
							if(isNaN(ctIndex)){
	 							continue;
	 						}
							var articleVo = articles[ctIndex];
							if(articles.length <= 0){	//期刊里没有文章
								continue;
							}
							
							if(ctIndex == 0){	//第一行 展示大图
								showHtml += createFristLine(articleVo);
							}else if(articles.length <= 3){	//文章数量小于等于3的布局
								showHtml += createSingleLine(articleVo);
							}else{	//大于3的布局 
								if(ctIndex > 0 && ctIndex < 4){
									showHtml += createColsLine(articleVo, ctIndex);
								}else{
									showHtml += createSingleLine(articleVo);
								}
							}
						
						}
 						
						showHtml +='  </div>'
								  +' </div>'
								  +'</div>';
						$("#recList").append(showHtml);
					}
					if(data.pageInfo.pageCount > 1){	//最多显示3期，如果不止3期就显示查看往期
						$(".wrap-previous").show();
					}
				}
				setTimeout(function(){
					$(".initloading").hide();
				},800);
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
}

/**
 * 创建期刊第一行
 */
function createFristLine(articleVo){
	var homeCover = resourcepath + "/weixin/img/discovery/selection-defult.png";
	if(articleVo.homeCover != null && articleVo.homeCover != ""){
		homeCover = articleVo.homeCover;
	}
	
	var praiseClz = "";
	if(articleVo.isPraise > 0){
		praiseClz += " on";
	}
	
	var html = '<div class="picture bor">'
			 + '	<a href="'+toDetail(articleVo.articleId, articleVo.articleFrom, 0)+'">'
			 + '		<img src="'+homeCover+'"/>'
			 + '	</a>'
			 + '	<h4 onclick="location.href=\''+toDetail(articleVo.articleId, articleVo.articleFrom, 0)+'\'">'+articleVo.articleTitle+'</h4>'
			 + '	<div class="func">'
			 + '		<div class="comment" onclick="location.href=\''+toDetail(articleVo.articleId, articleVo.articleFrom, 1)+'\'">'
			 + '			<span>'+articleVo.commentCount+'</span>'
			 + '		</div>'
			 + '		<div class="dz'+praiseClz+'" data-article-id="'+articleVo.articleId+'" data-check-praise="'+articleVo.isPraise+'">'
			 + '			<span>'+articleVo.praiseCount+'</span>'
			 + '		</div>'
			 + '	</div>'
			 + '</div>';
	return html;
}

/**
 * 创建横向的单行sssss
 */
function createSingleLine(articleVo){
	var homeCover = resourcepath + "/weixin/img/discovery/selection-defult.png";
	if(articleVo.homeCover != null && articleVo.homeCover != ""){
		homeCover = articleVo.homeCover;
	}
	
	var praiseClz = "";
	if(articleVo.isPraise > 0){
		praiseClz += " on";
	}
	var intro = "";
	if(articleVo.articleIntroduce != null && articleVo.articleIntroduce != ""){
		intro = articleVo.articleIntroduce;
	}
	var html = '<div class="chinode">'
			 + '	<div class="chintext">'
			 + '		<h3 onclick="location.href=\''+toDetail(articleVo.articleId, articleVo.articleFrom, 0)+'\'">'+articleVo.articleTitle+'</h3>'
			 + '		<p onclick="location.href=\''+toDetail(articleVo.articleId, articleVo.articleFrom, 0)+'\'">'+intro+'</p>'
			 + '		<div class="func">'
			 + '			<div class="comment" onclick="location.href=\''+toDetail(articleVo.articleId, articleVo.articleFrom, 1)+'\'"><span>'+articleVo.commentCount+'</span></div>'
			 + '			<div class="dz'+praiseClz+'" data-article-id="'+articleVo.articleId+'" data-check-praise="'+articleVo.isPraise+'"><span>'+articleVo.praiseCount+'</span></div>'
			 + '		</div>'
			 + '	</div>'
			 + '	<div class="chiimg">'
			 + '		<a href="'+toDetail(articleVo.articleId, articleVo.articleFrom, 0)+'">'
			 + '			<img src="'+homeCover+'"/>'
			 + '		</a>'
			 + '	</div>'
			 + '</div>';
	return html;
}

/**
 * 创建横向的3列
 */
function createColsLine(articleVo, ctIndex){
	var homeCover = resourcepath + "/weixin/img/discovery/articlelist.png";
	if(articleVo.homeCover != null && articleVo.homeCover != ""){
		homeCover = articleVo.homeCover;
	}
	
	var praiseClz = "";
	if(articleVo.isPraise > 0){
		praiseClz += " on";
	}
	var html = '';
	if(ctIndex == 1){
		html += '<ul class="botlist">';
	}
	
	if(ctIndex == 1 || ctIndex == 2 || ctIndex == 3){
		html += '<li>'
			+ '		<dl>'
			+ '			<dt>'
			+ '				<a href="'+toDetail(articleVo.articleId, articleVo.articleFrom, 0)+'">'
			+ '					<img src="'+homeCover+'"/>'
			+ '				</a>'
			+ '			</dt>'
			+ '			<dd>'
			+ '				<p onclick="location.href=\''+toDetail(articleVo.articleId, articleVo.articleFrom, 0)+'\'">'+articleVo.articleTitle+'</p>'
			+ '			</dd>'
			+ '		</dl>'
			+ '	</li>';
	}
	
	if(ctIndex == 3){
		html += '</ul>';
	}
	return html;
}

function toRetrospect(){
	 window.location.href = mainServer + "/weixin/discovery/toSelectedRetrispect";
}

function toDetail(articleId, articleFrom, maodian){
	var clickLocal = mainServer + "/weixin/discovery/toSelectedDetail?articleId="+articleId;
	if(maodian != null && maodian == 1){
		clickLocal += "#comments";
	}

	if(articleFrom == 1){
		clickLocal = mainServer + "/weixin/discovery/toDynamicDetail?articleId="+articleId+"&backUrl="+theUrl;
	}
	
	return clickLocal;
}

function lineAddOrCancelPraise($this, articleId, praiseId, praiseType){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer + "/weixin/tologin?backUrl="+theUrl;
		return;
	}
	
	if(articleId == null || articleId == "" || isNaN(articleId) || parseFloat(articleId) <= 0){
		showvaguealert('出现异常啦');
		return;
	}
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
//精选指引弹窗
function checkSelectedGuide() {
	$.ajax({                                       
		url: mainServer + '/weixin/customerGuide/checkSelectedGuide',
		data:{},
		type:'post',
		dataType:'json',
		success:function(data){
			customerSelected = data.customerSelected;
			if(customerSelected == 'Y') {
				var checkFlag = 0;// 未登陆
				if(customerId != null && customerId != '') {
					checkFlag = 1;
				}
				var guideType = 3;
				//缓存状态标记
				var obj = {
					flag: checkFlag,
					guideType: guideType
				};
				localStorage.setItem('selectedGuideObj',JSON.stringify(obj));
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
					var anniversaryObj = JSON.parse(localStorage.getItem("selectedGuideObj"));
					if(anniversaryObj) {
						var obj = {
							flag: 1,
							guideType: type
						};
						localStorage.setItem('selectedGuideObj',JSON.stringify(obj));
					}
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
}