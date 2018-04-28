$(function(){
	$(".gotuanlist").html("");
	joinGroupList(1, 10);
});
		
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
						joinGroupList(next, 10);
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
	 
function joinGroupList(pageIndex,pageSize){
	 $(".initloading").show();
	 $.ajax({                                       
		 url: mainServer+"/weixin/productgroup/joinGroupList",
		 data:{
			"productId":productId,
			"specialId":specialId,
			rows:pageSize,
			pageIndex:pageIndex
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
					if(data.code != "1010"){
						showvaguealert("活动已下架");
						setTimeout(function(){
							$(".initloading").hide();
						},800);
						return;
					}
						
					if(data.data == null || data.data.length <= 0){
						showvaguealert("还没有开团信息");
						setTimeout(function(){
							$(".initloading").hide();
						},800);
						return;
					}
					var userGroups = data.data;
					for (var continueIndex in userGroups) {
						if(isNaN(continueIndex)){
							break;
						}
						var userGroupVo = userGroups[continueIndex];
						var reNum = parseFloat($("#limitCopy").val()) - parseFloat(userGroupVo.custNum);
						var tuanDesc = "";
						if(tuanFlag == 1){ //活动已结束
							if(reNum <= 0){
								tuanDesc = "时间已结束,已满"+$("#limitCopy").val()+"人，下次再来吧";
							}else{
								tuanDesc = "时间已结束，遗憾缺"+reNum+"人，下次再来吧";
							}
						}else if (userGroupVo.status == 1){
							if(reNum == 1){
								tuanDesc = "就等你1个了，一起享受拼价吧";
							}else if (reNum > 1){
								tuanDesc = "剩余"+reNum+"个名额，一起享受拼价吧";
							}
						}else{
							tuanDesc = "已满"+$("#limitCopy").val()+"人，快邀请好友再次开团吧";
						}
						var btnLine = "";
						var btnDesc = "去参团";
						if(userGroupVo.status != 1){
							btnLine = "class='disable'";
							btnDesc = "已满团";
						}
						var showHtml = '<div class="gotuanbox">'
									 	+'			<span class="left" onclick="toOtherHome('+userGroupVo.originator+')">'
									 	+' 			<img src="'+userGroupVo.custImg+'" alt="" />'
									 	+' 		</span>'
									 	+' 		<span class="midden">'
									 	+			userGroupVo.nickName
									 	+' 			<p>'+tuanDesc+'</p>'
									 	+' 		</span>'
									 	+' 		<a '+btnLine+' href="javascript:toJoinGroup('+userGroupVo.userGroupId+','+ userGroupVo.productId+','+userGroupVo.specialId+');">'
									 	+ 			btnDesc
									 	+' 		</a>'
									 	+' 	</div>';
									 	
						$(".gotuanlist").append(showHtml);
						}
						setTimeout(function(){
							$(".initloading").hide();
						},800);
					}
				},
				failure:function(data){
					showvaguealert('访问出错');
				}
	});
}
	 	
function toJoinGroup(userGroupId, productId, specialId){
	window.location.href = mainServer+"/weixin/productgroup/toJoinGroup?userGroupId="+userGroupId+"&productId="+productId+"&specialId="+specialId;
}
	 	
function shareProduct(){
	 $.ajax({                                       
		url: mainServer+"/weixin/share/addShare",
		data:{
			"shareType":1,
			"objId":productId
			},
		type:'post',
		dataType:'json',
		success:function(data){
			showvaguealert('分享成功');
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
		});
}
	 	
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},1000);
}

function toOtherHome(otherCustId){
	 var back = window.location.href;
	 if(customerId != null && customerId > 0 && customerId == otherCustId){
		window.location.href = mainServer + "/weixin/socialityhome/toSocialityHome?backUrl="+back.replace(/\&/g,"%26");
	 }else{
	 	window.location.href = mainServer + "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+otherCustId+"&backUrl="+back.replace(/\&/g,"%26");
	 }
}