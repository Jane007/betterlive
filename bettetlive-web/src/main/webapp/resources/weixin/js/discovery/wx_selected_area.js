$(function(){
	$("#dataList").html("");
	//queryDatas(1, 10);
	list.init();
});


var list = {
		//所有的请求参数
		obj:{
			pageIndex:1,/*这两个是假设页码和数据条数*/
			pageSize:10,
			pageCount:'',/*总页数*/
			url:mainServer+'/weixin/specialarticle/querySpecialArticleList',
			articleTypeId:typeId
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
							 $('.weui-loadmore__tips').html("暂无更多数据");
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
				articleTypeId:oSelf.obj.articleTypeId
			},function(data,errror){
				if(data.code == '1010'){
					oSelf.obj.pageCount=data.pageInfo.pageCount;
					if(data.data && data.data.length>0){
						oSelf.establishDom(data.data);
					}else{//暂无数据
						$(".zanwubg").show();
					}
					
				}else{
					$.toast("出现异常","text");
				}
				
			})
		},
		//创建列表
		establishDom:function(data){
			var _html = '';
			$(".initloading").show();
			for(var i = 0; i < data.length; i++){
				var specialVo = typeof data[i] == 'object' ? data[i] : $.parseJSON(data[i]);
				if(specialVo!=null){
					var onSc='';
					if(specialVo.isCollection>0){
						onSc = ' on ';
					};
					var onDz='';
					if(specialVo.isPraise>0){
						onDz = ' on ';
					};
					var clickLocal = mainServer+"/weixin/discovery/toSelectedDetail?articleId="+specialVo.articleId+"&backUrl="+theUrl+"#comments";
					if(specialVo.articleFrom == 1){
						clickLocal = mainServer+"/weixin/discovery/toDynamicDetail?articleId="+specialVo.articleId+"&backUrl="+theUrl;
						}
					var showHtml = 	'<div class="videobox" style="padding:0.26rem 0.26rem 0 0.26rem;">'  
						   +'		<div class="spbox" onclick="location.href=\''+ clickLocal +'\'">'
						   +' 			<div class="spfmbg"><img src="'+specialVo.articleCover+'" alt="" /></div>'    
						   +'		</div>' 
						   +'		<div class="vidbttiel" style="padding-left:0;">'   
						   +'			<h4 onclick="location.href=\''+ clickLocal +'\'">'+specialVo.articleTitle+'</h4>'
						   +'			<div class="gongnengbox">'
						   +'				<a class="an02" href="'+clickLocal+'">'+specialVo.commentCount+'</a>'
						   +'	 			<a class="dz '+onDz+'" data-collection-id="'+specialVo.isCollection+'" data-special-id="'+specialVo.articleId+'" data-priase-id="'+specialVo.isPraise+'">'+specialVo.praiseCount+'</a>'
						   +'			</div>'
						   +'		</div>'
						   +'	</div>';
				$("#dataList").append(showHtml);
					
				}
				
				
			}
			setTimeout(function(){
				$(".initloading").hide();
			},800);
		},
		//收藏效果
		operate:function(){
			//收藏
			$(document).on('click','.sc',function(){
				//点击获取当前收藏节点的点赞数量
				let num = $(this).html();
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
							oSelf.html(num);
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
							oSelf.html(num);
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
				let num = $(this).html();
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
							oSelf.html(num);
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



function lineAddOrCancelPraise(flag,praiseLineId,specialId,praiseId,praiseType){
	var customerId = "${customerId}";
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin";
		return;
	}
	
 	if((flag != 0 && flag != 1) || isNaN(flag)){
 		showvaguealert("出现异常");
 		return;
 	}
 	if(praiseLineId == null || praiseLineId == ""){
 		showvaguealert("出现异常");
 		return;
 	}
 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
			showvaguealert('出现异常啦');
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
 			showvaguealert('出现异常啦');
 			return;
 		}
 		data={
	 		"praiseId":praiseId
	 	};
 	}
	$("#"+praiseLineId).attr("href", "javascript:void(0);");
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$("#"+praiseLineId).attr("href", "javascript:lineAddOrCancelPraise(0,'"+praiseLineId+"',"+specialId+",0,"+praiseType+")");
					$("#"+praiseLineId).attr('class', 'an03');
					var praiseCount = $("#"+praiseLineId).html();
					$("#"+praiseLineId).html(parseFloat(praiseCount)-1);
					showvaguealert("已取消点赞");
				}else{
					$("#"+praiseLineId).attr("href", "javascript:lineAddOrCancelPraise(1,'"+praiseLineId+"',"+specialId+","+data.data+","+praiseType+")");
					$("#"+praiseLineId).attr('class', 'an030');
					var praiseCount = $("#"+praiseLineId).html();
					$("#"+praiseLineId).html(parseFloat(praiseCount)+1);
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

function lineAddOrCancelCollection(flag,collectionLineId,specialId,collectionId, type){
	var customerId = "${customerId}";
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin";
		return;
	}
	
 	if((flag != 0 && flag != 1) || isNaN(flag)){
 		showvaguealert("出现异常");
 		return;
 	}
 	if(collectionLineId == null || collectionLineId == ""){
 		showvaguealert("出现异常");
 		return;
 	}
 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
			showvaguealert('出现异常啦');
			return;
		}
 	var url = mainServer+"/weixin/collection/addCollection";
 	var data={
 		"collectionType":type,
 		"objId":specialId
 	};
 	if(flag == 1){
 		url = mainServer+"/weixin/collection/cancelCollection";
 		if(collectionId == null || collectionId == "" || isNaN(collectionId) || parseFloat(collectionId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
 		data={
	 		"collectionId":collectionId
	 	};
 	}
 	
		$("#"+collectionLineId).attr("href", "javascript:void(0);");
		
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$("#"+collectionLineId).attr("href", "javascript:lineAddOrCancelCollection(0,'"+collectionLineId+"',"+specialId+",0,"+type+")");
					$("#"+collectionLineId).attr('class', 'an01');
				
					var coCount = $("#"+collectionLineId).html();
					$("#"+collectionLineId).html(parseFloat(coCount)-1);
					showvaguealert("已取消收藏");
				}else{
					$("#"+collectionLineId).attr("href", "javascript:lineAddOrCancelCollection(1,'"+collectionLineId+"',"+specialId+","+data.data+","+type+")");
					$("#"+collectionLineId).attr('class', 'an010');
					var coCount = $("#"+collectionLineId).html();
					$("#"+collectionLineId).html(parseFloat(coCount)+1);
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



// 修改微信自带的返回键
	$(function(){
		
		var url=mainServer+"/weixin/discovery/toSelected";
		
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
        	window.history.pushState(state, "title","#");  
	    }
	    
	    
	  //返回上一个页面
		$('.backPage').click(function(){
			window.location.href=url;	
		});
	});

