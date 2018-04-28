//点击收起打开 
window.onload = function() {
	for (var i = 0; i < $(".shenhefont").length; i++) {
		var fontheight = 50;// 可显示高度
		var fontleng = $(".shenhefont")[i].offsetHeight;
		$(".xianshi")[i].index = i;
		if (fontleng > fontheight) {
			$(".zhankaibox")[i].style.display = "block";
			$(".shenhefont")[i].style.height = "0.7rem";
			$(".shenhefont")[i].style.overflow = "hidden";
		}
		;
		$(".xianshi")[i].onclick = function() {
			if (this.innerHTML == "全部") {
				$(".shenhefont")[this.index].style.height = "auto";
				$(".shenhefont")[this.index].style.overflow = "visible";
				this.innerHTML = "收起";
			} else if (this.innerHTML == "收起") {
				this.innerHTML = "全部";
				$(".shenhefont")[this.index].style.height = "0.7rem";
				$(".shenhefont")[this.index].style.overflow = "hidden";
			}
		}
	}

}

var nextIndex = 1;
var tabIndex = 0;
// 查看大图的js
$(function() {
	nextIndex = 1;
	tabIndex = 0;
	refresh(0);

	checkBigpic();
	function checkBigpic() {
		var initPhotoSwipeFromDOM = function(gallerySelector) {
			// parse slide data (url, title, size ...) from DOM elements
			// (children of gallerySelector)
			var parseThumbnailElements = function(el) {
				var thumbElements = el.childNodes, numNodes = thumbElements.length, items = [], figureEl, linkEl, size, item;

				for (var i = 0; i < numNodes; i++) {

					figureEl = thumbElements[i]; // <figure> element

					// include only element nodes
					if (figureEl.nodeType !== 1) {
						continue;
					}

					linkEl = figureEl.children[0]; // <a> element

					size = linkEl.getAttribute('data-size').split('x');

					// create slide object
					item = {
						src : linkEl.getAttribute('href'),
						w : parseInt(size[0], 10),
						h : parseInt(size[1], 10)
					};

					if (figureEl.children.length > 1) {
						// <figcaption> content
						item.title = figureEl.children[1].innerHTML;

					}

					if (linkEl.children.length > 0) {
						// <img> thumbnail element, retrieving thumbnail url
						item.msrc = linkEl.children[0].getAttribute('src');
					}

					item.el = figureEl; // save link to element for
										// getThumbBoundsFn
					items.push(item);
				}

				return items;
			};

			// find nearest parent element
			var closest = function closest(el, fn) {
				return el && (fn(el) ? el : closest(el.parentNode, fn));
			};

			// triggers when user clicks on thumbnail
			var onThumbnailsClick = function(e) {
				e = e || window.event;
				e.preventDefault ? e.preventDefault() : e.returnValue = false;

				var eTarget = e.target || e.srcElement;

				// find root element of slide
				var clickedListItem = closest(
						eTarget,
						function(el) {
							return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
						});

				if (!clickedListItem) {
					return;
				}

				// find index of clicked item by looping through all child nodes
				// alternatively, you may define index via data- attribute
				var clickedGallery = clickedListItem.parentNode, childNodes = clickedListItem.parentNode.childNodes, numChildNodes = childNodes.length, nodeIndex = 0, index;

				for (var i = 0; i < numChildNodes; i++) {
					if (childNodes[i].nodeType !== 1) {
						continue;
					}

					if (childNodes[i] === clickedListItem) {
						index = nodeIndex;
						break;
					}
					nodeIndex++;
				}

				if (index >= 0) {
					// open PhotoSwipe if valid index found
					openPhotoSwipe(index, clickedGallery);
				}
				return false;
			};

			// parse picture index and gallery index from URL (#&pid=1&gid=2)
			var photoswipeParseHash = function() {
				var hash = window.location.hash.substring(1), params = {};

				if (hash.length < 5) {
					return params;
				}

				var vars = hash.split('&');
				for (var i = 0; i < vars.length; i++) {
					if (!vars[i]) {
						continue;
					}
					var pair = vars[i].split('=');
					if (pair.length < 2) {
						continue;
					}
					params[pair[0]] = pair[1];
				}

				if (params.gid) {
					params.gid = parseInt(params.gid, 10);
				}

				return params;
			};

			var openPhotoSwipe = function(index, galleryElement,
					disableAnimation, fromURL) {
				var pswpElement = document.querySelectorAll('.pswp')[0], gallery, options, items;

				items = parseThumbnailElements(galleryElement);

				// define options (if needed)
				options = {

					// define gallery index (for URL)
					galleryUID : galleryElement.getAttribute('data-pswp-uid'),

					getThumbBoundsFn : function(index) {
						// See Options -> getThumbBoundsFn section of
						// documentation for more info
						var thumbnail = items[index].el
								.getElementsByTagName('img')[0], // find
																	// thumbnail
						pageYScroll = window.pageYOffset
								|| document.documentElement.scrollTop, rect = thumbnail
								.getBoundingClientRect();

						return {
							x : rect.left,
							y : rect.top + pageYScroll,
							w : rect.width
						};
					}

				};

				// PhotoSwipe opened from URL
				if (fromURL) {
					if (options.galleryPIDs) {
						// parse real index when custom PIDs are used
						// http://photoswipe.com/documentation/faq.html#custom-pid-in-url
						for (var j = 0; j < items.length; j++) {
							if (items[j].pid == index) {
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
				if (isNaN(options.index)) {
					return;
				}

				if (disableAnimation) {
					options.showAnimationDuration = 0;
				}

				// Pass data to PhotoSwipe and initialize it
				gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default,
						items, options);
				gallery.init();
			};

			// loop through all gallery elements and bind events
			var galleryElements = document.querySelectorAll(gallerySelector);

			for (var i = 0, l = galleryElements.length; i < l; i++) {
				galleryElements[i].setAttribute('data-pswp-uid', i + 1);
				galleryElements[i].onclick = onThumbnailsClick;
			}
			// Parse URL and open gallery if it contains #&pid=3&gid=1
			var hashData = photoswipeParseHash();
			if (hashData.pid && hashData.gid) {
				openPhotoSwipe(hashData.pid, galleryElements[hashData.gid - 1],
						true, true);
			}
		};

		// execute above function
		initPhotoSwipeFromDOM('.my-gallery');
	}

	document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.2 + 'px';
	window.addEventListener(
	'resize', function() {
			document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.2 + 'px';
		});
})

function refresh(tab) {
	$("#myContent0").html("");
	$("#myContent1").html("");
	$("#myContent2").html("");
	$("#myContent" + tab).show();

	$("#tab0").removeClass("current");
	$("#tab1").removeClass("current");
	$("#tab2").removeClass("current");
	$("#tab" + tab).addClass("current");

	tabIndex = tab;
	queryListBySpecialArticle(1, 10, tab);
}

function editStatus(articleId, customerId, _status,articleTitle) {
	
	var opinonMsg = "";
	var opinonTitle = "";
	var articleTitle="";
	if (_status == 1) { 
		opinonMsg = "您发表的动态已审核通过";
		opinonTitle = "请输入通过理由";   
	}
	
	if (_status == 4) {
		opinonMsg = "您发表的动态有违规内容请查看规则后在发表";
		opinonTitle = "请输入拒绝理由"; 
	}

	var opinion = prompt(opinonTitle, opinonMsg);
	
	$.ajax({
		url : mainServer + "/audit/editStatus",
		data : {
			"articleId" : articleId,
			"customerId":customerId,
			"status" : _status,
			"articleTitle" :articleTitle,
			"opinion" : opinion
		},
		datatype : "json",
		type : "post",
		success : function(data) {
			if (data.result == "succ") {
				alert(data.msg);
				window.location.href = window.location.href + "?id=" + 10000
						* Math.random();
			} else {
				alert(data.msg);
			}
		}

	});
}

// 上滑
var loadtobottom = true;
$(document).scroll(
		function() {
			totalheight = parseFloat($(window).height())
					+ parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) {
				if (loadtobottom == true) {
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();
					if (parseInt(next) > 1) {
						if (nextIndex != next) {
							nextIndex++;
							if (tabIndex == 0) {
								queryListBySpecialArticle(next, 10, tabIndex);
							} else if (tabIndex == 1) {
								queryListBySpecialArticle(next, 10, tabIndex);
							} else if (tabIndex == 2) {
								queryListBySpecialArticle(next, 10, tabIndex);
							}
							$(".loadingmore").show();
							setTimeout(function() {
								$(".loadingmore").hide();

							}, 1500);
						}
					}
					if (parseInt(next) >= parseInt(pageCount)) {
						loadtobottom = false;
					}
				}
			}
		});

function queryListBySpecialArticle(pageIndex, pageSize, tab) {
	var auditStatus = 3;
	if (tab == 1) {
		auditStatus = 1;
	} else if (tab == 2) {
		auditStatus = 4;
	}
	$
			.ajax({
				url : mainServer + '/audit/queryAuditAllJson',
				type : 'post',
				data : {
					"status" : auditStatus,
					"rows" : pageSize,
					"pageIndex" : pageIndex
				},
				dataType : 'json',
				success : function(data) {
					if (data.code == "1010") { // 获取成功
						var pageNow = data.pageInfo.pageNow;
						var pageCount = data.pageInfo.pageCount;
						$("#pageCount").val(pageCount);
						$("#pageNow").val(pageNow);
						var next = parseInt(pageNow) + 1;
						if (next >= pageCount) {
							next = pageCount;
						}
						$("#pageNext").val(next);
						$("#pageNow").val(pageNow);
						if (data.data == null || data.data.length <= 0) {
							setTimeout(function() {
								$("#myContent" + tab).hide();
								$("#myContent3").show();
							}, 800);
							return;
						} else {

							$("#myContent3").hide();
						}
						var list = data.data;
						for ( var continueIndex in list) {
							if (isNaN(continueIndex)) {
								continue;
							}
							var articleVo = list[continueIndex];
							var pics = articleVo.pictures;

							var picHtml = "";
							for ( var subConIndex in pics) {
								if (isNaN(subConIndex)) {
									continue;
								}
								var picVo = pics[subConIndex];
								picHtml += '<figure class="swiper-slide"> '
										+ '<a href="' + picVo.original_img
										+ '" data-size="1024x1024">'
										+ '<img src="' + picVo.small_img
										+ '" alt=""/></a></figure>';
							}
							var showHtml = '<div class="tiecont"><div class="plyi">'
									+ '<em><img src="'
									+ articleVo.headUrl
									+ '" alt=""></em>'
									+ '<span>'
									+ articleVo.author
									+ '</span>'
									+ '<p>'
									+ articleVo.createTime
									+ '</p></div>'
									+ '<div class="mesPic dtboximlist swiper-container">'
									+ '<div class="my-gallery swiper-wrapper"  data-pswp-uid="1">'
									+ picHtml
									+ '</div></div>'
									+ '<div class="shenhefont">'
									+ articleVo.articleTitle
									+ '<br>'
									+ articleVo.content
									+ '</div>'
									+ '<div class="zhankaibox"><a class="xianshi">全部</a> </div>';
							if (articleVo.status == 3) {
								showHtml += '<div class="tbox">'
										+ '	<a class="tongguo" href="javascript:editStatus('
										+ articleVo.articleId + "," + articleVo.customerId + ',4);">不通过</a>'
										+ '	<a class="notong" href="javascript:editStatus('
										+ articleVo.articleId + "," + articleVo.customerId + ',1);">通过</a>'
										+ '</div>';
							}

							showHtml += '</div></div>';

							$("#myContent" + tab).append(showHtml);
						}

						var swiper = new Swiper('.swiper-container', {
							pagination : '.swiper-pagination',
							slidesPerView : 1.6,
							paginationClickable : true,
							spaceBetween : 10
						});
					} else {
						alert("出现异常");
					}
				},
				failure : function(data) {
					alert('出错了');
				}
			});
}