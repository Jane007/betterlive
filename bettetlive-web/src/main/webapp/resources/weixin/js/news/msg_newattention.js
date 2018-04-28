$(function(){
	queryDatas(1, 10);
})
var loadtobottom=true;
 var nextIndex = 1;
$(document).scroll(function(){     
	var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
	if($(document).height() <= totalheight){	
			    
		if(loadtobottom==true){
			var next = $("#pageNext").val();
			var pageCount = $("#pageCount").val();
			var pageNow = $("#pageNow").val(); 
			if(parseInt(next)>1){  
				if(nextIndex != next){
					nextIndex++;
					queryDatas(next,10);      
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

function queryDatas(pageIndex,pageSize){
	 $(".initloading").show();
		$.ajax({                                       
			url:mainServer+'/weixin/socialityhome/queryMyFansList',
			 data: {rows:pageSize,pageIndex:pageIndex},
			type:'post',
			
			dataType:'json',
			success:function(data){
				console.log(data);
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
						   $(".noyhbg").show();
						},800);
						return;
					}
				
					var list = data.data;
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
							concernLine = '<span id="gz'+vo.fansId+'" onclick="javascript:alertCancelTip('+vo.fansId+','+vo.isConcerned+','+vo.customerId+');" class="yet">相互关注</span>';
						}else{
							concernLine = '<span id="gz'+vo.fansId+'" onclick="javascript:addConcern('+vo.fansId+','+vo.customerId+');">关注</span>';
						}
						
						var showHtml = '<div class="friend">'
							+'<div class="friend-left" data-customerId="'+vo.customerId+'">'
							+'<div class="head">'
							+'<img src="'+vo.headUrl+'"/>'
							+'</div>'
							+'<div class="text">'
							+'<h3>'+vo.nickname+'</h3>'
							+custSign
							+'</div>'
							+'</div>'
							+'<div class="attention">'
							+ concernLine
							+'</div>'
							+'</div>';
						$(".friendlist").append(showHtml);
					}
					setTimeout(function(){
						$(".initloading").hide();
					},800);
				}else if (data.code == "1011"){
					window.location.href = mainServer+"/weixin/tologin";						
				}else{
					showvaguealert("出现异常");
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
}

$(document).on('click','.friend-left',function(){
	var cstId= $(this).attr("data-customerId");
	window.location.href=mainServer+'/weixin/socialityhome/toOtherSocialityHome?otherCustomerId='+cstId;
})

function closeConcernAlert(){
	 $(".bkbg").hide();
	 $(".shepassdboxs").hide();
	 $("#cancelId").attr("href", "javascript:void(0);");
}

function alertCancelTip(fansId, isConcerned, concernedId){
	 $(".bkbg").show();
	 $(".shepassdboxs").show();
	 $("#cancelId").attr("href", "javascript:cancelConcern("+fansId+","+isConcerned+","+concernedId+")");
}

function cancelConcern(fansId, isConcerned, concernedId){
		if(fansId == null || fansId < 0){
			showvaguealert('访问数据出错');
			return false;
		}
		if(concernedId == null || concernedId < 0){
			showvaguealert('访问数据出错');
			return false;
		}
		$("#gz"+fansId).attr("onclick", "javascript:void(0);");
		$.ajax({                                       
			url:mainServer+"/weixin/concern/cancelConcern",
			data:{"fansId":isConcerned},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					$("#gz"+fansId).html("关注");
					$("#gz"+fansId).removeClass();
					$("#gz"+fansId).attr("onclick","javascript:addConcern("+fansId+","+concernedId+");");
					closeConcernAlert();
					showvaguealert("已取消关注");
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

	function addConcern(fansId, concernCustId){
		if(fansId == null || fansId < 0){
			showvaguealert('访问数据出错');
			return false;
		}
		if(concernCustId == null || concernCustId < 0){
			showvaguealert('访问数据出错');
			return false;
		}
		$("#gz"+fansId).attr("onclick", "javascript:void(0);");
		$.ajax({                                       
			url: mainServer+"/weixin/concern/addConcern",
			data:{"concernCustId":concernCustId},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					$("#gz"+fansId).html("相互关注");
					$("#gz"+fansId).addClass("yet");
					$("#gz"+fansId).attr("onclick","javascript:alertCancelTip("+fansId+","+data.data+","+concernCustId+");");
					showvaguealert("已关注");
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
	
