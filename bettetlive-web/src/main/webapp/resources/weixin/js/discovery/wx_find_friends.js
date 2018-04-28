$(function() {
	queryDatas(1, 10);
});

// 点击邀请有奖
$('.tab-wrap').click(function() {
	window.location.href = mainServer + "/weixin/customerinvite/gotoInvited";
});

var loadtobottom = true;
var nextIndex = 1;
$(document).scroll(
		function() {
			var totalheight = parseFloat($(window).height())
					+ parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) {
				if (loadtobottom == true) {
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();
					if (parseInt(next) > 1) {
						if (nextIndex != next) {
							nextIndex++;
							queryDatas(next, 10);
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

function queryDatas(pageIndex, pageSize) {

	$(".initloading").show();
	$.ajax({
				url : mainServer + '/weixin/discovery/queryFindFriendList',
				data : {
					rows : pageSize,
					pageIndex : pageIndex
				},
				type : 'post',
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

						if ((data.data == null || data.data.length <= 0)
								&& pageIndex == 1) {
							setTimeout(function() {
								$(".initloading").hide();
							}, 800);
							return;
						}

						var list = data.data;
						for ( var conIndex in list) {
							if (isNaN(conIndex)) {
								continue;
							}
							var vo = list[conIndex];
							var articles = vo.articleVos;
							 //console.log(articles);
							var showHtml='';

							var headUrl = resourcepath
									+ "/weixin/img/head-dufult01.png";
							if (vo.headUrl != null && vo.headUrl != "") {
								headUrl = vo.headUrl;
							}
							var custSign = "";
							if (vo.signature != null && vo.signature != "") {
								custSign = "<p>" + vo.signature + "</p>";
							}
							var concernedLine = "";

							if (vo.isConcerned > 0 && vo.isConcernedMe > 0) { // 相互关注
								concernedLine = '<span id="gz'
										+ vo.customerId
										+ '" class="yet" onclick="alertCancelTip('
										+ vo.customerId + ',' + vo.isConcerned
										+ ',' + vo.isConcernedMe
										+ ')">相互关注</span>';
							} else if (vo.isConcerned > 0) { // 已关注
								concernedLine = '<span id="gz'
										+ vo.customerId
										+ '" class="yet" onclick="alertCancelTip('
										+ vo.customerId + ',' + vo.isConcerned
										+ ',0)">已关注</span>';
							} else {
								concernedLine = '<span id="gz' + vo.customerId
										+ '" onclick="addConcern('
										+ vo.customerId + ','
										+ vo.isConcernedMe + ')">+ 关注</span>';
							}
							var wap = '<div class="fri-wrap">';
							showHtml+=['<div class="friend">',
											'<div class="friend-left" onclick="otherHome('+vo.customerId+')">',
												'<div class="head">',
													'<img src="'+headUrl+'"/>',
												'</div>',
											'<div class="text">',
												'<h3>'+vo.nickname+'</h3>',
												custSign,
											'</div>',
										'</div>',
										'<div class="attention">',
										concernedLine,
										'</div>',
									'</div>'].join('');
							wap +=showHtml;
							var articleSize = articles.length;
							if(articleSize==3){//有3篇文章才显示
								var showArticle = '<ul class="pirlist">';
								var liArticle ='';
								$.each(articles, function(index, article) {// 遍历文章
									 liArticle+=['<li>',
														'<dl onclick="goToArticle('+article.articleId+')">',
															'<dt>',
																'<img src="'+article.articleCover+'"/>',
															'</dt>',
															'<dd>',
																'<p>'+article.articleTitle+'</p>',
															'</dd>', 
														'</dl>', 
													'</li>'].join('');
								});
								
								showArticle+=liArticle;
								showArticle +='</ul>';
								wap +=showArticle;
							}
							wap +='</div>';
							$(".friendlist").append(wap);
						};
						imgLoad();

					}
					setTimeout(function() {
						$(".initloading").hide();
					}, 800);
				},
				failure : function(data) {
					showvaguealert('出错了');
				}
			});
}

function goToArticle(articleId){
	window.location.href=mainServer+'/weixin/discovery/toDynamicDetail?articleId='+articleId;
}
var imgLoad=function(){
	$('.friendlist img').load(function(){
		$('.friendlist img').each(function(){
			if($(this).width() > $(this).height()){
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
		});
	})
}
/**
 * 取消关注提示
 * 
 * @param customerId
 * @param isCo
 * @param isCom
 */
function alertCancelTip(customerId, isCo, isCom) {
	$(".bkbg").show();
	$(".shepassdboxs").show();
	$("#cancelId").attr(
			"href",
			"javascript:cancelConcern(" + customerId + "," + isCo + "," + isCom
					+ ")");
}

/**
 * 
 * @param customerId
 * @param isCo
 * @param isCom
 */
function cancelConcern(customerId, isCo, isCom) {
	if (customerId == null || customerId < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	if (isCo == null || isCo < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	$("#gz" + customerId).attr("onclick", "void(0)");
	$.ajax({
		url : mainServer + "/weixin/concern/cancelConcern",
		data : {
			"fansId" : isCo
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				$("#gz" + customerId).html("+ 关注");
				$("#gz" + customerId).removeClass();
				$("#gz" + customerId).attr("onclick",
						"addConcern(" + customerId + "," + isCom + ");");
				closeConcernAlert();
				showvaguealert("已取消关注");
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin?backUrl="
						+ backUrl;
			} else {
				showvaguealert(data.msg);
			}
		},
		failure : function(data) {
			showvaguealert('访问出错');
		}
	});
}

function addConcern(customerId, isCom) {
	if (customerId == null || customerId < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	if (isCom == null || isCom < 0) {
		showvaguealert('访问数据出错');
		return false;
	}

	$("#gz" + customerId).attr("class", "on");
	$("#gz" + customerId).attr("onclick", "void(0)");

	$.ajax({
		url : mainServer + "/weixin/concern/addConcern",
		data : {
			"concernCustId" : customerId
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				setTimeout(function() {
					$("#gz" + customerId).attr("class", "yet");
					var showName = "已关注";
					if (isCom > 0) {
						showName = "相互关注";
					}
					$("#gz" + customerId).html(showName);
				}, 300);
				$("#gz" + customerId).attr(
						"onclick",
						"alertCancelTip(" + customerId + "," + data.data + ","
								+ isCom + ");");
				showvaguealert("已关注");
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin?backUrl="
						+ backUrl;
			} else {
				showvaguealert(data.msg);
			}
		},
		failure : function(data) {
			showvaguealert('访问出错');
		}
	});
}

function closeConcernAlert() {
	$(".bkbg").hide();
	$(".shepassdboxs").hide();
	$("#cancelId").attr("href", "javascript:void(0);");
}

function otherHome(otherCustId) {
	if (otherCustId > 0 && otherCustId == myCustId) {
		window.location.href = mainServer
				+ "/weixin/socialityhome/toSocialityHome?backUrl=" + backUrl;
	} else {
		window.location.href = mainServer
				+ "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="
				+ otherCustId + "&backUrl=" + backUrl;
	}
}

// 修改微信自带的返回键
$(function() {
	var bool = false;
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			window.location.href = mainServer + "/weixin/discovery/toDynamic";
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