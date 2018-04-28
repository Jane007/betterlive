$(function() {

	$("#dataList").html("");
	queryDatas(1, 10);
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
function queryDatas(pageIndex,pageSize){
	 $(".initloading").show();
		$.ajax({                                       
			url:mainServer+'/weixin/socialityhome/queryOtherFansList',
			 data: {rows:pageSize,pageIndex:pageIndex,concernedId:otherCustId},
			type:'post',
			
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					var pageNow = data.pageInfo.pageNow;
					var pageCount = data.pageInfo.pageCount;
					$("#pageCount").val(pageCount);
					$("#pageNow").val(pageNow);
					$("#rowCount").val(pageNow.rowCount);
					var next = parseInt(pageNow)+1;
					if(next>=pageCount){
						next=pageCount;
					}
					$("#pageNext").val(next);
					$("#pageNow").val(pageNow);
					
					if((data.data == null || data.data.length <= 0) && pageIndex == 1){
						setTimeout(function(){
							$(".initloading").hide();
							$(".zanwubg").show();
						},800);
						return;
					}else{
						$(".zanwubg").hide();
					}
				
					var list = data.data;
					var myCustId = $("#myCustId").val();
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
						if(myCustId > 0 && myCustId == vo.customerId){
							concernLine = "<a id='gz"+vo.fansId+"' href='javascript:void(0);'>已关注</a>";
						}else if(vo.isConcerned > 0 && vo.isConcernedMe > 0){
							concernLine = "<a id='gz"+vo.fansId+"' href='javascript:alertCancelTip("+vo.fansId+","+vo.isConcerned+","+vo.customerId+","+vo.isConcernedMe+");'>相互关注</a>";
						}else if(vo.isConcerned > 0){	//我关注了TA TA没关注我
							concernLine = "<a id='gz"+vo.fansId+"' href='javascript:alertCancelTip("+vo.fansId+","+vo.isConcerned+","+vo.customerId+","+vo.isConcernedMe+");'>已关注</a>";
						}else{ //TA关注了我，我没关注TA
							concernLine = "<a id='gz"+vo.fansId+"' href='javascript:addConcern("+vo.fansId+","+vo.customerId+","+vo.isConcernedMe+");' class='fsguan'>+ 关注</a>";
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
				}else if (data.code == "1011"){
					window.location.href = mainServer+"/weixin/tologin?backUrl="+back;						
				}else{
					showvaguealert("出现异常");
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
}
function closeConcernAlert() {
	$(".bkbg").hide();
	$(".shepassdboxs").hide();
	$("#cancelId").attr("href", "javascript:void(0);");
}

function alertCancelTip(idx, isConcerned, concernedId, isConcernedMe) {
	$(".bkbg").show();
	$(".shepassdboxs").show();
	$("#cancelId").attr("href","javascript:cancelConcern(" + idx + "," + isConcerned + ","+ concernedId + "," + isConcernedMe + ")");
}

function cancelConcern(idx, isConcerned, concernedId, isConcernedMe) {
	var myCustId = $("#myCustId").val();
	if (myCustId <= 0) {
		window.location.href = mainServer + "/weixin/tologin?backUrl=" + back;
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
				$("#gz" + idx).attr("href","javascript:addConcern(" + idx + "," + concernedId+ "," + isConcernedMe + ");");
				closeConcernAlert();
				showvaguealert("已取消关注");
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin?backUrl="+ back;
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
	var myCustId = $("#myCustId").val();
	if (myCustId <= 0) {
		window.location.href = mainServer + "/weixin/tologin?backUrl=" + back;
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
				$("#gz" + idx).attr("href","javascript:alertCancelTip(" + idx + "," + data.data+ "," + concernCustId + "," + isConcernedMe+ ");");
				showvaguealert("已关注");
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin?backUrl="+ back;
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
		window.location.href = mainServer+ "/weixin/socialityhome/toSocialityHome";
	} else {
		window.location.href = mainServer+ "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+ otherCustId;
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
