$(function() {
	listData.init();
});


var listData = {
		//所有的请求参数
		obj:{
			pageIndex:1,/*这两个是假设页码和数据条数*/
			pageSize:10,
			pageCount:'',/*总页数*/
			url:mainServer+'/weixin/socialityhome/queryOtherConcernsList',
			customerId:otherCustId
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
			 $(".initloading").show();
			$.post(oSelf.obj.url,{
				rows:oSelf.obj.pageSize,
				pageIndex:oSelf.obj.pageIndex,
				customerId:oSelf.obj.customerId,
			},function(data,errror){
				if(data.code == '1010'){
					oSelf.obj.pageCount=data.pageInfo.pageCount;
					
					if(data.data && data.data.length>0){
						oSelf.establishDom(data.data);
					}else{//暂无数据
						$(".zanwubg").show();
					}
				
					
				}else if (data.code == "1011"){
					window.location.href = mainServer+"/weixin/tologin?backUrl="+link;		
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
				
				var concernLine = "";
				if(myCustId > 0 && myCustId == vo.concernedId){
					concernLine = "<a id='gz"+vo.fansId+"' href='javascript:void(0);'>已关注</a>";
				}else if(vo.isConcerned > 0 && vo.isConcernedMe > 0){
					concernLine = "<a id='gz"+vo.fansId+"' href='javascript:alertCancelTip("+vo.fansId+","+vo.isConcerned+","+vo.concernedId+","+vo.isConcernedMe+");'>相互关注</a>";
				}else if(vo.isConcerned > 0){	//我关注了TA TA没关注我
					concernLine = "<a id='gz"+vo.fansId+"' href='javascript:alertCancelTip("+vo.fansId+","+vo.isConcerned+","+vo.concernedId+","+vo.isConcernedMe+");'>已关注</a>";
				}else{ //TA关注了我，我没关注TA
					concernLine = "<a id='gz"+vo.fansId+"' href='javascript:addConcern("+vo.fansId+","+vo.concernedId+","+vo.isConcernedMe+");' class='fsguan'>+ 关注</a>";
				}
				
				var showHtml = '<div id="cnl'+vo.fansId+'" class="gotuanbox">'
			 	 			+'		<span class="left" onclick="otherHome('+vo.concernedId+')">'
			 	 			+'			<img src="'+vo.headUrl+'" alt="" />'
			 	 			+' 		</span>'
			 				+' 		<span class="midden" onclick="otherHome('+vo.concernedId+')">'
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

function alertCancelTip(idx, isConcerned, concernedId, isConcernedMe) {
	$(".bkbg").show();
	$(".shepassdboxs").show();
	$("#cancelId").attr(
			"href",
			"javascript:cancelConcern(" + idx + "," + isConcerned + ","
					+ concernedId + "," + isConcernedMe + ")");
}

function cancelConcern(idx, isConcerned, concernedId, isConcernedMe) {
	if (myCustId <= 0) {
		window.location.href = mainServer + "/weixin/tologin?backUrl=" + link;
		return;
	}
	if (isConcerned == null || isConcerned <= 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	if (concernedId == null || concernedId <= 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	$("#gz" + idx).attr("href", "javascript:void(0);");
	$.ajax({
		url : mainServer + "/weixin/concern/cancelConcern",
		data : {
			"fansId" : isConcerned
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				$("#gz" + idx).html("+ 关注");
				$("#gz" + idx).addClass("fsguan");
				$("#gz" + idx).attr(
						"href",
						"javascript:addConcern(" + idx + "," + concernedId
								+ "," + isConcernedMe + ");");
				closeConcernAlert();
				showvaguealert("已取消关注");
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin?backUrl="
						+ link;
			} else {
				showvaguealert(data.msg);
			}
		},
		failure : function(data) {
			showvaguealert('访问出错');
		}
	});
}

function addConcern(idx, concernCustId, isConcernedMe) {
	if (myCustId <= 0) {
		window.location.href = mainServer + "/weixin/tologin?backUrl=" + link;
		return;
	}
	if (concernCustId == null || concernCustId < 0) {
		showvaguealert('访问数据出错');
		return false;
	}
	$("#gz" + idx).attr("href", "javascript:void(0);");
	$.ajax({
		url : mainServer + "/weixin/concern/addConcern",
		data : {
			"concernCustId" : concernCustId
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				if (isConcernedMe > 0) {
					$("#gz" + idx).html("相互关注");
				} else {
					$("#gz" + idx).html("已关注");
				}
				$("#gz" + idx).removeClass("fsguan");
				$("#gz" + idx).attr(
						"href",
						"javascript:alertCancelTip(" + idx + "," + data.data
								+ "," + concernCustId + "," + isConcernedMe
								+ ");");
				showvaguealert("已关注");
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin?backUrl="
						+ link;
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

// 修改微信自带的返回键
$(function() {

	var url = backUrl;

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
