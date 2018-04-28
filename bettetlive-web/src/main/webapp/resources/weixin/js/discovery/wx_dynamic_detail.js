$(function() {

	checkBigpic();
	$(".initloading").show();
	setTimeout(function() {
		$(".initloading").hide();
	}, 800);

	$("#commentsId").html("");
	queryComments(1, 10);
	anniversary.getStorage()
});
var anniversary = {
	//读取缓存
	getStorage:function(){
		var anniversaryObj = JSON.parse(localStorage.getItem("dynamic"));
		var timer = new Date().getTime();
		if(anniversaryObj && (timer - anniversaryObj.storageTime < 3600000 * 24)){
			$('.shareimg').hide()
		}else{
			$('.shareimg').show()
		}
	}
}
window.onload=function(){
	$("figure img").VMiddleImg({"width":120,"height":120}); //设置图片显示  
	$(".dtaiimg img").VMiddleImg({"width":270,"height":270}); //设置图片显示   		
	var figurech=$(".my-gallery").children("figure");//
	 var figureimg=$(".my-gallery").find("img"); 
	 $(function(){   
			 if(figurech.length==1){ 
				 figurech.width("100%");   
				 figurech.height("auto");
				 figureimg.width("100%"); 
				 figureimg.height("auto"); 
			 }  
	 }); 

};
$.fn.extend({
	 displayPart:function () {
		 var displayLength = 100;
		 displayLength = this.attr("displayLength") || displayLength;
		 var text = this.text();
		 if (!text) return "";
	
		 var result = "";
		 var count = 0;
		 for (var i = 0; i < displayLength; i++) {
			 var _char = text.charAt(i);
			 if (count >= displayLength) break;
			 if (/[^x00-xff]/.test(_char)) count++; //双字节字符，//[u4e00-u9fa5]中文
				 result += _char;
				 count++;
			 }
			 if (result.length < text.length) {
			 	result += "...";
			 }
		 	 this.text(result);
		 }
	 });






function addOrCancelPraise(flag,specialId,praiseId){
	
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+link;
		return;
	}
	
 	if((flag != 0 && flag != 1) || isNaN(flag)){
 		showvaguealert("出现异常");
 		return;
 	}
 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
			showvaguealert('出现异常啦');
			return;
		}
 	var url = mainServer+"/weixin/praise/addPraise";
 	var data={
 		"praiseType":4,
 		"objId":specialId,
 		"shareCustomerId":shareCustomerId
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
	$("#pid").attr("onclick", "void(0);");
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$("#pid").attr("onclick", "addOrCancelPraise(0,"+specialId+",0)");
					$("#pid").removeClass('currents');
					var pct = parseInt($("#praiseCountId").html());
					if(pct > 0){
						$("#praiseCountId").html(pct-1);
					}
					showvaguealert("已取消点赞");
				}else{
					$("#pid").attr("onclick", "addOrCancelPraise(1,"+specialId+","+data.data+")");
					$("#pid").addClass('currents');
					var pct = parseInt($("#praiseCountId").html());
					$("#praiseCountId").html(pct+1);
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
function lineAddOrCancelPraise(flag,praiseLineId,specialId,praiseId,praiseType){
	
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+link;
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
	$("#"+praiseLineId).attr("onclick", "void(0);");
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(0,'"+praiseLineId+"',"+specialId+",0,"+praiseType+")");
					$("#"+praiseLineId).attr('class', 'dianzan');
					var praiseCount = $("#"+praiseLineId).html();
					$("#"+praiseLineId).html(parseFloat(praiseCount)-1);
					showvaguealert("已取消点赞");
				}else{
					$("#"+praiseLineId).attr("onclick", "lineAddOrCancelPraise(1,'"+praiseLineId+"',"+specialId+","+data.data+","+praiseType+")");
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

//往上滑
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
		url:mainServer+'/weixin/specialcomment/specialCommentDetail',
		data:{
			rows:pageSize,pageIndex:pageIndex,
			"specialId":objId,
			"commentPraiseType":5,
			"specialType":5
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
					var clickPraise = "lineAddOrCancelPraise(0, 'dz"+commentVo.commentId+"', "+commentVo.commentId+", 0, 5)";
					var praiseCls = "dianzan";
					if(commentVo.isPraise > 0){
						clickPraise = "lineAddOrCancelPraise(1, 'dz"+commentVo.commentId+"', "+commentVo.commentId+","+commentVo.isPraise+", 5)";
						praiseCls = "dianzan2";
					}
				
					var commentDetailLocal = "location.href='"+mainServer+"/weixin/discovery/toCommentDetail?articleId="+commentVo.specialId+"&commentId="+commentVo.commentId+"&commentPraiseType=5&backUrl="+link+"'";
					
					var showHtml = '<div class="plcent" >'
						    	+'		<div class="plcttop">'
						    	+'			<span class="one" onclick="toSocialityHome('+commentVo.customerVo.customer_id+')">'
						    	+'				<img src="'+commentVo.customerVo.head_url+'" alt="">'
						    	+'			</span>'
						    	+'			<span class="two" onclick="toSocialityHome('+commentVo.customerVo.customer_id+')">'
						    	+				commentVo.customerVo.nickname
						    	+'			</span>'
						    	+'			<span class="three" onclick="'+commentDetailLocal+'">'
						    	+				commentVo.createTime
						    	+'			</span>'
						    	+'		</div>'
						    	+'		<div class="dchfcent" onclick="'+commentDetailLocal+'">'
						    	+			commentVo.content
						    	+'		</div>'
						    	+'		<div class="plbotbox">'
						    	+'			<span class="'+praiseCls+'" id="dz'+commentVo.commentId+'" onclick="'+clickPraise+'">'+commentVo.praiseCount+'</span>'
						    	+'			<span class="dianzan dtplspan" id="pl'+commentVo.commentId+'" onclick="'+commentDetailLocal+'">'+commentVo.replyCount+'</span>'
						    	+'		</div>'
						    	+'	</div>';
						
						$("#commentsId").append(showHtml);
				}
				setTimeout(function(){
					$(".initloading").hide();
				},800);
			}else{
				showvaguealert(data.msg);
				$(".initloading").hide();
			}
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}

function shareSpecial(){
	
	if(articleStatus != 1){
		return;
	}
		$.ajax({                                       
		url: mainServer+"/weixin/share/addShare",
		data:{
			"shareType":4,
			"objId":objId
		},
		type:'post',
		dataType:'json',
		success:function(data){
			if (data.code == 1010) {
				hideShare();
				var obj = {
					flag:true,
					storageTime:new Date().getTime()
				};
				localStorage.setItem('dynamic',JSON.stringify(obj));
				if (integralSwitch != null && integralSwitch == 0 && data.data.hasIntegralFlag == 0) {
					$(".shate-succeed").show();
					setTimeout(function(){
						$(".shate-succeed").hide();
					},800);
				}
			}
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}

function checkLogin(){
	
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+link;
		return;
	}
	window.location.href = mainServer+"/weixin/discovery/toAddComment?articleId="+objId;
}

function concernOrCancel(fansId){
	if(customerId == null || customerId <= 0){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+link;
		return;
	}
	
		
		if(fansId == null || fansId < 0){
			showvaguealert('访问数据出错');
			return false;
		}
		var url = mainServer+"/weixin/concern/addConcern";
		var data={};
		if(fansId > 0){ //取消关注
			url = mainServer+"/weixin/concern/cancelConcern";
			data={
		 		"fansId":fansId
	 	};
			
		}else{
			if(concernId == null || concernId < 0){
				showvaguealert('访问数据出错');
 			return false;
			}
			data={
		 		"concernCustId":concernId
	 	};
		}
		$("#guanzhuId").attr("href", "javascript:void(0);");
		$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(fansId > 0){
					$("#guanzhuId").html("+ 关注");
					$("#guanzhuId").removeClass();
					$("#guanzhuId").attr("href", "javascript:concernOrCancel(0);");
					closeConcernAlert();
					showvaguealert("已取消关注");
				}else{
					$("#guanzhuId").html("已关注");
					$("#guanzhuId").attr("class", "current");
					$("#guanzhuId").attr("href", "javascript:alertCancelTip("+data.data+");");
					showvaguealert("已关注");
				}
			}else if (data.code == "1011"){
				window.location.href = mainServer+"/weixin/tologin";
			}else{
				showvaguealert(data.msg);
			}
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}

function toSocialityHome(authorId){
	var myCustId = customerId;
	var backUrl = mainServer+"/weixin/discovery/toDynamicDetail?articleId="+objId;
	if(myCustId > 0 && myCustId == authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toSocialityHome?backUrl="+link;
	}else if (myCustId > 0 && myCustId != authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+authorId+"&backUrl="+link;
	}else{
		window.location.href = mainServer+"/weixin/tologin?backUrl="+link;
	}
}

 function closeConcernAlert(){
	 $(".bkbg").hide();
	 $(".shepassdboxs").hide();
	 $("#cancelId").attr("href", "javascript:void(0);");
 }
 
 function alertCancelTip(fansId){
	 $(".bkbg").show();
	 $(".shepassdboxs").show();
	 $("#cancelId").attr("href", "javascript:concernOrCancel("+fansId+")");
 }
 
function shareAlert(){
	$(".share").fadeIn(500,function(){
		var obj = {
			flag:true,
			storageTime:new Date().getTime()
		};
		localStorage.setItem('dynamic',JSON.stringify(obj));
		$('.shareimg').hide()
	});
}

function hideShare(){
	$(".share").hide();
}

function toHelp(){
	var backUrl = mainServer+"/weixin/discovery/toDynamicDetail?articleId="+objId;
	window.location.href = mainServer+"/weixin/discovery/toHelp?backUrl="+link;
}

$('.backPage').click(function(){
	
	if(backUrl != null && backUrl != "" && backUrl != undefined){
		window.location.href = backUrl;
	}else{
		window.location.href=mainServer+"/weixin/discovery/toDynamic";  
	}
});

//微信自带返回
$(function(){
	var url=mainServer+"/weixin/discovery/toDynamic";  
	
	if(backUrl != null && backUrl != "" && backUrl != undefined){
		url= backUrl;
	}
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
    	window.history.pushState(state, "title","");  
    }
    
    
  
});



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