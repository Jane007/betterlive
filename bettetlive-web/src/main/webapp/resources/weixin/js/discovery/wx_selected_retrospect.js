$(function(){
	video.inIt();
	queryDatas(2, 3);
});


var video = {
	isClick:function(){ //点击事件属性
		$('#recList').on('click','.dz', function(){
			var articleId = $(this).attr("data-article-id");
			//判断是否已经点击
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
		$('img').each(function(){
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
	},
	inIt:function(){//初始化
		this.isClick();
		
	}
}

var loadtobottom=true;
var nextIndex = 1;
$(window).scroll(function(){     
	var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
	if($(document).height() <= totalheight){	
		if(loadtobottom==true){
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
		
/**
 * 每周精选
 */
function queryDatas(pageIndex,pageSize){
		var pcount = $("#pageCount").val();
		var pgn = $("#pageNow").val();
		if(pgn > 0 && pcount == pgn){
			return false;
		}
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
					var pageNow = data.pageInfo.pageNow;
					var pageCount = data.pageInfo.pageCount;
					$("#pageCount").val(pageCount);
					$("#pageNow").val(pageNow);
					var next = parseInt(pageNow)+1;
					if(next>=pageCount){
						next=pageCount;
					};
					$("#pageNext").val(next);
					$("#pageNow").val(pageNow);
					if((data.data == null || data.data.length <= 0) && pageIndex == 1){
						$("#dtList").hide();
						$(".zanwubg").show();
					}else{
						$(".zanwubg").hide();
					}
					
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
					};
					
				}
				setTimeout(function(){
					$(".initloading").hide();
					video.imgLoad();
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
 * 创建横向的单行
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

function toDetail(articleId, articleFrom, maodian){
	var clickLocal = mainServer + "/weixin/discovery/toSelectedDetail?articleId="+articleId+"&backUrl="+theUrl;
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

//修改微信自带的返回键
$(function(){
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
    },1000); 
    
	pushHistory(); 
	
    window.addEventListener("popstate", function(e) {
    	if(bool){
			window.location.href = mainServer+"/weixin/discovery/toSelected";
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
