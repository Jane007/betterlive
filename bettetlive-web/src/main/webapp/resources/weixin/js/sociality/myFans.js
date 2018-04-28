$(function() {
	listData.init();
});



var listData = {
		//所有的请求参数
		obj:{
			pageIndex:1,/*这两个是假设页码和数据条数*/
			pageSize:10,
			pageCount:'',/*总页数*/
			url:mainServer+'/weixin/socialityhome/queryMyFansList'
			
		},
		//上拉加载分页
		loadMore:function(){
				var oSelf = this;
				var loading = false;//上拉加载状态标记
				$(document.body).infinite();
				$('.weui-loadmore').hide();
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
			},function(data,errror){
				if(data.code == '1010'){
					oSelf.obj.pageCount=data.pageInfo.pageCount;
					
					if(data.data && data.data.length>0){
						oSelf.establishDom(data.data);
					}else{//暂无数据
						$(".zanwubg").show();
					}
				
					
				}else if (data.code == "1011"){
					window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;		
				}else{
					$.toast("出现异常","text");
				}
				
			})
		},
		//创建列表
		establishDom:function(data){
				var list = data;
				for (var continueIndex in list) {
					if(isNaN(continueIndex)){
						continue;
					}
					var vo = list[continueIndex];
					var custSign = "";
					if(vo.signature != null && vo.signature != ""){
						custSign = "<p>"+vo.signature+"</p>";
					}
					
					if(vo.isConcerned > 0){
						concernLine = '<a id="gz'+vo.fansId+'" href="javascript:alertCancelTip('+vo.fansId+','+vo.isConcerned+','+vo.customerId+');">相互关注</a>';
					}else{
						concernLine = '<a id="gz'+vo.fansId+'" href="javascript:addConcern('+vo.fansId+','+vo.customerId+');" class="fsguan">+ 关注</a>';
					}
					
					var showHtml = '<div id="cnl'+vo.fansId+'" class="gotuanbox">'
				 	 			+'		<span class="left" onclick="otherHome('+vo.customerId+')">'
				 	 			+'			<img src="'+vo.headUrl+'" alt="" />'
				 	 			+' 		</span>'
				 				+' 		<span class="midden" onclick="otherHome('+vo.customerId+')">'
				 	 			+			vo.nickname
				 	 			+			custSign
				 	 			+' 		</span>'
				 	 			+		concernLine
				 	 			+' </div>';
					$("#dataList").append(showHtml);
				}
				
				setTimeout(function(){
					$(".initloading").hide();
				},800);
							
			
		},
		init:function(){
			this.loadMore();
			this.getData();
			
		}
}


function closeConcernAlert() {
	$(".bkbg").hide();
	$(".shepassdboxs").hide();
	$("#cancelId").attr("href", "javascript:void(0);");
}

function alertCancelTip(fansId, isConcerned, concernedId) {
	$(".bkbg").show();
	$(".shepassdboxs").show();
	$("#cancelId").attr(
			"href",
			"javascript:cancelConcern(" + fansId + "," + isConcerned + ","
					+ concernedId + ")");
}

function cancelConcern(fansId, isConcerned, concernedId) {
	if (fansId == null || fansId < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	if (concernedId == null || concernedId < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	$("#gz" + fansId).attr("href", "javascript:void(0);");
	$.ajax({
		url : mainServer + "/weixin/concern/cancelConcern",
		data : {
			"fansId" : isConcerned
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				$("#gz" + fansId).html("+ 关注");
				$("#gz" + fansId).addClass("fsguan");
				$("#gz" + fansId).attr("href","javascript:addConcern(" + fansId + "," + concernedId+ ");");
				closeConcernAlert();
				showvaguealert("已取消关注");
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin?backUrl="+ backUrl;
			} else {
				showvaguealert(data.msg);
			}
		},
		failure : function(data) {
			showvaguealert('访问出错');
		}
	});
}

function addConcern(fansId, concernCustId) {
	if (fansId == null || fansId < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	if (concernCustId == null || concernCustId < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	$("#gz" + fansId).attr("href", "javascript:void(0);");
	$.ajax({
		url : mainServer + "/weixin/concern/addConcern",
		data : {
			"concernCustId" : concernCustId
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				$("#gz" + fansId).html("相互关注");
				$("#gz" + fansId).removeClass("fsguan");
				$("#gz" + fansId).attr(
						"href",
						"javascript:alertCancelTip(" + fansId + "," + data.data
								+ "," + concernCustId + ");");
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



function otherHome(otherCustId) {
	
	if (otherCustId > 0 && otherCustId == myCustId) {
		window.location.href = mainServer
				+ "/weixin/socialityhome/toSocialityHome";
	} else {
		window.location.href = mainServer
				+ "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="
				+ otherCustId;
	}
}

$(function() {

	var url = mainServer + "/weixin/socialityhome/toSocialityHome";

	if (backUrl != null && backUrl != "" && backUrl != undefined) {
		url = backUrl;
	}

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
