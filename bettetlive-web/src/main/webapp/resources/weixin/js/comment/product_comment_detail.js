
 
  
		$(function(){
			checkBigpic();	  
			//查看大图的js
			function checkBigpic(){
				var initPhotoSwipeFromDOM = function(gallerySelector) {
					
				    // parse slide data (url, title, size ...) from DOM elements 
				    // (children of gallerySelector)
				    var parseThumbnailElements = function(el) {
				        var thumbElements = el.childNodes,
				            numNodes = thumbElements.length,
				            items = [],
				            figureEl,
				            linkEl,
				            size,
				            item;
				
				        for(var i = 0; i < numNodes; i++) {
				
				            figureEl = thumbElements[i]; // <figure> element
				
				            // include only element nodes 
				            if(figureEl.nodeType !== 1) {
				                continue;
				            }
				
				            linkEl = figureEl.children[0]; // <a> element
				
				            size = linkEl.getAttribute('data-size').split('x');
				
				            // create slide object
				            item = {
				                src: linkEl.getAttribute('href'),
				                w: parseInt(size[0], 10),
				                h: parseInt(size[1], 10)
				            };
				
				
				
				            if(figureEl.children.length > 1) {
					           // <figcaption> content
				                item.title = figureEl.children[1].innerHTML; 
				            }
				
				            if(linkEl.children.length > 0) {
				                // <img> thumbnail element, retrieving thumbnail url
				                item.msrc = linkEl.children[0].getAttribute('src');
				            } 
				
				            item.el = figureEl; // save link to element for getThumbBoundsFn
				            items.push(item);
				        }
				
				        return items;
				    };
				
				    // find nearest parent element
				    var closest = function closest(el, fn) {
				        return el && ( fn(el) ? el : closest(el.parentNode, fn) );
				    };
				
				    // triggers when user clicks on thumbnail
				    var onThumbnailsClick = function(e) {
				        e = e || window.event;
				        e.preventDefault ? e.preventDefault() : e.returnValue = false;
				
				        var eTarget = e.target || e.srcElement;
				
				        // find root element of slide
				        var clickedListItem = closest(eTarget, function(el) {
				            return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
				        });
				
				        if(!clickedListItem) {
				            return;
				        }
				
				        // find index of clicked item by looping through all child nodes
				        // alternatively, you may define index via data- attribute
				        var clickedGallery = clickedListItem.parentNode,
				            childNodes = clickedListItem.parentNode.childNodes,
				            numChildNodes = childNodes.length,
				            nodeIndex = 0,
				            index;
				
				        for (var i = 0; i < numChildNodes; i++) {
				            if(childNodes[i].nodeType !== 1) { 
				                continue; 
				            }
				
				            if(childNodes[i] === clickedListItem) {
				                index = nodeIndex;
				                break;
				            }
				            nodeIndex++;
				        }
				
				        if(index >= 0) {
				            // open PhotoSwipe if valid index found
				            openPhotoSwipe( index, clickedGallery );
				        }
				        return false;
			    	};
			
				    // parse picture index and gallery index from URL (#&pid=1&gid=2)
				    var photoswipeParseHash = function() {
				        var hash = window.location.hash.substring(1),
				        params = {};
				
				        if(hash.length < 5) {
				            return params;
				        }
				
				        var vars = hash.split('&');
				        for (var i = 0; i < vars.length; i++) {
				            if(!vars[i]) {
				                continue;
				            }
				            var pair = vars[i].split('=');  
				            if(pair.length < 2) {
				                continue;
				            }           
				            params[pair[0]] = pair[1];
				        }
				
				        if(params.gid) {
				            params.gid = parseInt(params.gid, 10);
				        }
				
				        return params;
				    };
			
				    var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
				        var pswpElement = document.querySelectorAll('.pswp')[0],
				            gallery,
				            options,
				            items;
				
				        items = parseThumbnailElements(galleryElement);
				
				        // define options (if needed)
				        options = {
				
				            // define gallery index (for URL)
				            galleryUID: galleryElement.getAttribute('data-pswp-uid'),
				
				            getThumbBoundsFn: function(index) {
				                // See Options -> getThumbBoundsFn section of documentation for more info
				                var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
				                    pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
				                    rect = thumbnail.getBoundingClientRect(); 
				
				                return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
				            }
				
				        };
				
				        // PhotoSwipe opened from URL
				        if(fromURL) {
				            if(options.galleryPIDs) {
				                // parse real index when custom PIDs are used 
				                // http://photoswipe.com/documentation/faq.html#custom-pid-in-url
				                for(var j = 0; j < items.length; j++) {
				                    if(items[j].pid == index) {
				                        options.index = j;
				                        break;
				                    }
				                }
				            } else {
				                // in URL indexes start from 1
				                options.index = parseInt(index, 10) - 1;
				            }
				        } else {
				            options.index = parseInt(index, 10);
				        }
				
				        // exit if index not found
				        if( isNaN(options.index) ) {
				            return;
				        }
				
				        if(disableAnimation) {
				            options.showAnimationDuration = 0;
				        }
				
				        // Pass data to PhotoSwipe and initialize it
				        gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
				        gallery.init();
				    };
				
				    // loop through all gallery elements and bind events
				    var galleryElements = document.querySelectorAll( gallerySelector );
				
				    for(var i = 0, l = galleryElements.length; i < l; i++) {
				        galleryElements[i].setAttribute('data-pswp-uid', i+1);
				        galleryElements[i].onclick = onThumbnailsClick;
				    }
				
				    // Parse URL and open gallery if it contains #&pid=3&gid=1
				    var hashData = photoswipeParseHash();
				    if(hashData.pid && hashData.gid) {
				        openPhotoSwipe( hashData.pid ,  galleryElements[ hashData.gid - 1 ], true, true );
				    }
				};
			
			// execute above function
			initPhotoSwipeFromDOM('.my-gallery');
		}
	});
		
	  
			  
		  
	  
	 function replyComment(id){
		 
		 if(myCustId == null || myCustId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
			return;
		 }
		
// 		 if(uid==myCustId){
			 window.location.href = mainServer+"/weixin/productcomment/toAddProductComment?type="+type+"&commentId="+commentId+"&replyId="+id;
// 		 }
	 }; 
	 
	 
	 
	 $(function(){
		 	  
		 $("#replysId").html("");
		 queryComments(1,10);
	 });
	 
	function checkLogin(){
		
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
			return;
		}
		window.location.href = mainServer+"/weixin/productcomment/toAddProductComment?type=${type}&commentId=${commentVo.comment_id}";
	}
	
	//往上滑的
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
						queryComments(next,10);
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
	
	 function queryComments(pageIndex,pageSize){
		$(".initloading").show();
		$.ajax({                                       
			url:mainServer+'/weixin/productcomment/commentReplyDetails',
			data:{
				"rows":pageSize,"pageIndex":pageIndex,
				"commentId":commentId,
			},
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
							$(".shafabg").show();
						},800);
						return;
					}else{
						$(".shafabg").hide();
					}
					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						var commentVo = list[continueIndex];
						var clickPraise = "lineAddOrCancelPraise(0, 'dz"+commentVo.comment_id+"', "+commentVo.comment_id+", 0)";
						var praiseCls = "dianzan";
						if(commentVo.is_praise > 0){
							clickPraise = "lineAddOrCancelPraise(1, 'dz"+commentVo.comment_id+"', "+commentVo.comment_id+","+commentVo.is_praise+")";
							praiseCls = "dianzan2";
						}
						var replyerName = "";
						var rootCommentCust = commentVo.customer_id;
						var replName =commentVo.replyerName;
						var nickname = commentVo.customerVo.nickname;
						if(commentVo.replyerName != null && commentVo.replyerName != "" && commentVo.replyerId != rootCommentCust){
								replyerName = "回复<span style='color:#9f9b5c'>"+replName+"：</span>";
						}
						var showHtml = '<div class="plcent">'
						    	+'	<div class="plcttop">'
						    	+'			<span class="one" >'
						    	+'				<img src="'+commentVo.customerVo.head_url+'" alt="">'
						    	+'			</span>'
						    	+'			<span class="two">'
						    	+				nickname
						    	+'			</span>'
						    	+'			<span class="three">'
						    	+				commentVo.create_time
						    	+'			</span>'
						    	+'		</div>'
						    	+'		<div class="dchfcent" onclick="replyComment('+commentVo.comment_id+')">'
						    	+			replyerName + commentVo.content
						    	+'		</div>'
						    	+'		<div class="plbotbox">'
						    	+'			<span class="'+praiseCls+'" id="dz'+commentVo.comment_id+'" onclick="'+clickPraise+'">'+commentVo.praise_count+'</span>'
						    	+'		</div>'
						    	+'	</div>';
							$("#replysId").append(showHtml);
					}
					setTimeout(function(){
						$(".initloading").hide();
					},800);
				}else{
					showvaguealert("访问出错");
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
			}
		});
	}
	 
	function addOrCancelPraise(flag){
		
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
			return;
		}
		
		if((flag != 0 && flag != 1) || isNaN(flag)){
	 		showvaguealert("出现异常");
	 		return;
	 	}
	 	var url = mainServer+"/weixin/praise/addPraise";
	 	var data={
	 		"praiseType":1,
	 		"objId":commentId
	 	};
	 	if(flag == 1){
	 		url = mainServer+"/weixin/praise/cancelPraise";
	 		var praiseId = $("#hasPraiseId").val();
	 		if(praiseId == null || praiseId == "" || isNaN(praiseId) || parseFloat(praiseId) <= 0){
	 			showvaguealert('出现异常啦');
	 			return;
	 		}
	 		data={
		 		"praiseId":praiseId
		 	};
	 	}
		$("#praiseId").attr("href", "javascript:void(0);");
		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(flag == 1){
						$("#praiseId").attr("href", "javascript:addOrCancelPraise(0);");
						$("#praiseId").removeClass('current');
						$("#praiseId").html("点赞");
						showvaguealert("已取消点赞");
					}else{
						$("#praiseId").addClass("current");
						$("#praiseId").attr("href", "javascript:addOrCancelPraise(1);");
						$("#hasPraiseId").val(data.data);
						$("#praiseId").html("已赞");
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
	 
	function lineAddOrCancelPraise(flag,praiseLineId,commentId,praiseId){
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
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
	 	if(commentId == null || commentId == "" || isNaN(commentId) || parseFloat(commentId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
	 	var url = mainServer+"/weixin/praise/addPraise";
	 	var data={
	 		"praiseType":1,
	 		"objId":commentId
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
		$("#"+praiseLineId).attr("onclick", "void(0);");
		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(flag == 1){
						$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(0,'"+praiseLineId+"',"+commentId+",0)");
						$("#"+praiseLineId).attr('class', 'dianzan');
						var praiseCount = $("#"+praiseLineId).html();
						$("#"+praiseLineId).html(parseFloat(praiseCount)-1);
						showvaguealert("已取消点赞");
					}else{
						$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(1,'"+praiseLineId+"',"+commentId+","+data.data+")");
						$("#"+praiseLineId).attr('class', 'dianzan2');
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
	 
	//提示弹框
	function showvaguealert(con){
		$('.vaguealert').show();
		$('.vaguealert').find('p').html(con);
		setTimeout(function(){
			$('.vaguealert').hide();
		},1000);
	}















//修改微信自带的返回键 
$(function() {
	var url = mainServer + "/weixin/productcomment/findList?type=" + type+ "&productId=" + productId + "&specialId=" + specialId;
	var bool = false;
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			window.location.href = url;
		}
	}, false);

	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}

});
