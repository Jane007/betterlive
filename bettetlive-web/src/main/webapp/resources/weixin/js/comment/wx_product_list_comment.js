		
		$(function(){
			$('.titlelists li').click(function(){
				$(this).addClass('active').siblings('li').removeClass('active');
				
				var type = $(this).index();
				if (isInit == -1) {
					queryComment(type,1,10);
					checkBigpic();
					isInit = 1;
				}
				
				if(type == 0){
					divselect = 0; //选择全部
				   	$("#div1").show();
				  	$("#div2").hide();
				}else{
					isInit = 1;
					divselect = 1;  //选择有图
				   	$("#div2").show();
				   	$("#div1").hide();
				}
			});
			
			queryComment(0,1,10);
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
			//往上滑
			var loadtobottom=true;
			$(document).scroll(function(){
				totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
				if($(document).height() <= totalheight){
					if(loadtobottom==true){ //取模  避免单次滑动执行两次
						var next = $("#pageNext").val();
						var pageCount = $("#pageCount").val();
						var pageNow = $("#pageNow").val();
						
						if(parseInt(next)>1){
							if(nextIndex != next){
								queryComment(next, 10);
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
		  
			
			
		//查询商品评论，type，0：全部，1：有图
		function queryComment(type,pageIndex,rows){
			$(".initloading").show();
			$('.messageBox .has-msgBox').hide();
			
			$.ajax({
				async:false,
	    		url:mainServer+'/weixin/productcomment/queryProductCommentAllJson',
	    		type:'post',
	    		dataType:'json',
	    		data:{
	    			'productId':productId,
	    			'contentImg':type,
	    			'pageIndex':pageIndex,
	    			'rows':rows,
	    		},
	    		success:function(data){
	    			 setTimeout(function(){
							$(".initloading").hide();
						 },800);
	    			if(data.result=='succ'){
	    				if (data.data == null) {
	    					$('.messageBox').eq(type).show();
	    					$('.messageBox').eq(type).css('position', 'fixed');
	    					$('.messageBox').eq(type).find('.no-msgBox').show();
							return;
	    				}
	    				$('.messageBox').eq(type).css('position', 'inherit');
	    				
	    				$.each(data.data,function(i,ele){
	    					var  nickname = ele.customerVo.nickname;
// 	    					var  nickname1 = "";
	    					var  leng = nickname.length;
	    					
				
	    					if(type == 0){
		    					buffer1.append('<div class="mesList">');
		    					buffer1.append('						<div class="userPic" onclick = "toOtherHome('+ele.customerVo.customer_id+')" >');
		    					buffer1.append('							<img src="'+ele.customerVo.head_url+'" alt="">');
		    					buffer1.append('						</div>');
		    					buffer1.append('						<div class="mesInfor">');
		    					buffer1.append('							<div class="userName" onclick = "toOtherHome('+ele.customerVo.customer_id+')" >');
		    					buffer1.append('								'+nickname+'');
		    					buffer1.append('							</div>');
		    					buffer1.append('							<div class="mesTime">');
		    					buffer1.append('								'+ele.create_time+'');
		    					buffer1.append('							</div>');
		    					buffer1.append('						</div>');
		    					buffer1.append('							<div class="mesCon">');
		    					buffer1.append('								'+ele.content+'');
		    					buffer1.append('							</div>');
		    					buffer1.append('							<div class="mesPic">');
		    					buffer1.append('								<div class="my-gallery" itemscope="" data-pswp-uid="1">');
		    					
		    					$.each(ele.commentArrayImgs,function(j,el){
			    					buffer1.append('								    <figure itemprop="associatedMedia" itemscope="">');
			    					buffer1.append('								      <a href="'+el+'" itemprop="contentUrl" data-size="1024x1024">');
			    					buffer1.append('								          <img src="'+el+'" itemprop="thumbnail" alt="Image description">');
			    					buffer1.append('								      </a>');
			    					buffer1.append('								    </figure>');
		    					});	
		    					
		    					buffer1.append('							    </div>');
		    					buffer1.append('							</div>');
		    					buffer1.append('							<div class="plbotbox">');
		    					buffer1.append('								      <span class="cpname">');
		    					buffer1.append('								        	'+ele.specName+'');  
		    					buffer1.append('								      </span>'); 
		    					
		    					var praiseCls = "";
		    					var clickPraise = "lineAddOrCancelPraise(0, 'dz"+ele.comment_id+"', "+ele.comment_id+", 0)";
		    					if(ele.is_praise > 0){
		    						praiseCls = "dianzan2";
		    						clickPraise = "lineAddOrCancelPraise(1, 'dz"+ele.comment_id+"', "+ele.comment_id+", "+ele.is_praise+")";
		    					}
		    					buffer1.append('								      <span id="dz'+ele.comment_id+'" class="dianzan '+praiseCls+'" onclick="'+clickPraise+'">');
		    					buffer1.append('								        	'+ele.praise_count+''); 
		    					buffer1.append('								      </span>');   
		    					buffer1.append('								      <span class="pinglun" onclick="location.href=\''+mainServer+'/weixin/productcomment/toProductCommentDetail?type='+type+'&productId='+productId+'&specialId='+specialId+'&commentId='+ele.comment_id+'\'">');
		    					buffer1.append('								        	'+ele.reply_count+''); 
		    					buffer1.append('								      </span>'); 
		    					buffer1.append('							</div>');
		    					buffer1.append('					');
		    					buffer1.append('				</div>');
	    					}
	    					
	    					if(type == 1){
		    					buffer2.append('<div class="mesList">');
		    					buffer2.append('						<div class="userPic" onclick = "toOtherHome('+ele.customerVo.customer_id+')" >');
		    					buffer2.append('							<img src="'+ele.customerVo.head_url+'" alt="">');
		    					buffer2.append('						</div>');
		    					buffer2.append('						<div class="mesInfor" onclick = "toOtherHome('+ele.customerVo.customer_id+')" >');
		    					buffer2.append('							<div class="userName">');
		    					buffer2.append('								'+nickname+'');
		    					buffer2.append('							</div>');
		    					buffer2.append('							<div class="mesTime">');
		    					buffer2.append('								'+ele.create_time+'');
		    					buffer2.append('							</div>');		    					
		    					buffer2.append('						</div>'); 
		    					buffer2.append('							<div class="mesCon">');
		    					buffer2.append('								'+ele.content+'');
		    					buffer2.append('							</div>');
		    					buffer2.append('							<div class="mesPic">');
		    					buffer2.append('								<div class="my-gallery" itemscope="" data-pswp-uid="1">');
		    					
		    					$.each(ele.commentArrayImgs,function(j,el){
			    					buffer2.append('								    <figure itemprop="associatedMedia" itemscope="">');
			    					buffer2.append('								      <a href="'+el+'" itemprop="contentUrl" data-size="1024x1024">');
			    					buffer2.append('								          <img src="'+el+'" itemprop="thumbnail" alt="Image description">');
			    					buffer2.append('								      </a>');
			    					buffer2.append('								    </figure>');
		    					});	
		    					
		    					buffer2.append('							    </div>');
		    					buffer2.append('							</div>'); 
		    					buffer2.append('							<div class="plbotbox">');
		    					buffer2.append('								      <span class="cpname">');
		    					buffer2.append('								        	'+ele.specName+'');  
		    					buffer2.append('								      </span>'); 
		    					
		    					var praiseCls = "";
		    					var clickPraise = "lineAddOrCancelPraise(0, 'dz1"+ele.comment_id+"', "+ele.comment_id+", 0)";
		    					if(ele.is_praise > 0){
		    						praiseCls = "dianzan2";
		    						clickPraise = "lineAddOrCancelPraise(1, 'dz1"+ele.comment_id+"', "+ele.comment_id+", "+ele.is_praise+")";
		    					}
		    					buffer2.append('								      <span id="dz1'+ele.comment_id+'" class="dianzan '+praiseCls+'" onclick="'+clickPraise+'">');
		    					buffer2.append('								        	'+ele.praise_count+''); 
		    					buffer2.append('								      </span>');   
		    					buffer2.append('								      <span class="pinglun" onclick="location.href=\''+mainServer+'/weixin/productcomment/toProductCommentDetail?type='+type+'&commentId='+ele.comment_id+'\'">');
		    					buffer2.append('								        	'+ele.reply_count+''); 
		    					buffer2.append('								      </span>'); 
		    					buffer2.append('							</div>');
		    					buffer2.append('					');
		    					buffer2.append('				</div>');
	    					}
	    				});
	    				
	    				if(type == 0){
	    				   $("#div1").html(buffer1.toString());
	    				   $("#div1").show();
	    				   $("#div2").hide();
	    				   divselect = 0;
	    				   
	    				   var obj = data;
	                  	   var pageNow = data.pageInfo.pageNow;
	   					   var pageCount = data.pageInfo.pageCount;
	   					   $("#pageCountAll").val(pageCount);
	   					   $("#pageNowAll").val(pageNow);
	   					   var next = parseInt(pageNow)+1;
	   					   if(next>=pageCount){
	   						  next=pageCount;
	   					   }
	   					   $("#pageNextAll").val(next);
	    				} else {
	    				   $("#div2").html(buffer2.toString());
	    				   $("#div2").show();
	    				   $("#div1").hide();
	    				   divselect = 1;
	    				   
	    				   var obj = data;
	                  	   var pageNow = data.pageInfo.pageNow;
	   					   var pageCount = data.pageInfo.pageCount;
	   					   $("#pageCountSome").val(pageCount);
	   					   $("#pageNowSome").val(pageNow);
	   					   var next = parseInt(pageNow)+1;
	   					   if(next>=pageCount){
	   						  next=pageCount;
	   					   }
	   					   $("#pageNextSome").val(next);
	    				}
	    			}else{
	    				showvaguealert('没有查询到相关评论');
	    			} 
	    		}
	    	});
		}
		
		var loadtobottomAll = true;
		var loadtobottomSome = true;
		$(document).scroll(function(){
			totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
			if($(document).height() <= totalheight){
				if(divselect == 0){
					//走全部
				if(loadtobottomAll==true){
						//loadtobottom=false;
						//此处为加载更多数据代码
						//alert('加载全部更多数据');
						
						var next = $("#pageNextAll").val();
						var pageCount = $("#pageCountAll").val();
						var pageNow = $("#pageNowAll").val();
						
						if(parseInt(next)>1){
							if(nextIndex != next){
								nextIndex++;
								queryComment(0,next,10)
								checkBigpic();
							}
						}
						if(parseInt(next)>=parseInt(pageCount)){
							loadtobottomAll=false;
						}
					}
				}else {
					//走有图
					if(loadtobottomSome==true){
						//此处为加载更多数据代码
						//alert('加载有图更多数据');
						
						var next = $("#pageNextSome").val();
						var pageCount = $("#pageCountSome").val();
						var pageNow = $("#pageNowSome").val();
						
						if(parseInt(next)>1){
							if(nextIndex != next){
								nextIndex++;
								nextIndex++;
								queryComment(1,next,10)
								checkBigpic();
							}
						}
						if(parseInt(next)>=parseInt(pageCount)){
							loadtobottomSome=false;
						}
					}
				}
			}
		});
	});
		
	var backUrl = window.location.href;
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
			$("#"+praiseLineId).attr("href", "javascript:void(0);");
			$.ajax({                                       
				url: url,
				data:data,
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.code == "1010"){ //获取成功
						if(flag == 1){
							$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(0,'"+praiseLineId+"',"+commentId+",0)");
							$("#"+praiseLineId).removeClass('dianzan2');
							var praiseCount = $("#"+praiseLineId).html();
							$("#"+praiseLineId).html(parseFloat(praiseCount)-1);
							showvaguealert("已取消点赞");
						}else{
							$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(1,'"+praiseLineId+"',"+commentId+","+data.data+")");
							$("#"+praiseLineId).addClass('dianzan2');
							var praiseCount = $("#"+praiseLineId).html();
							$("#"+praiseLineId).html(parseFloat(praiseCount)+1);
							showvaguealert("点赞成功");
						}
					}else{
						showvaguealert(data.data);
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
	$(function(){
		
		
		var url="";
		if(type == 1){
			url = mainServer+"/weixin/product/towxgoodsdetails?productId="+productId;
		}else if (type == 2){
			url = mainServer+"/weixin/product/toLimitGoodsdetails?productId="+productId+"&specialId="+specialId;
		}else if (type == 3){
			url = mainServer+"/weixin/product/toGroupGoodsdetails?productId="+productId+"&specialId="+specialId;
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
	
	function toOtherHome(otherCustId){
		 if(customerId != null && customerId > 0 && customerId == otherCustId){
			window.location.href = mainServer + "/weixin/socialityhome/toSocialityHome?backUrl="+backUrl.replace("&","%26");
		 }else{
		 	window.location.href = mainServer + "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+otherCustId+"&backUrl="+backUrl.replace("&","%26");
		 }
	}	