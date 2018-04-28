$(function(){
			$(".initloading").show();
			setTimeout(function(){
				$(".initloading").hide();
			},800);
			
		 $("#commentsId").html("");
		 queryComments();
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
						$("#pid").removeClass('current');
						showvaguealert("已取消点赞");
					}else{
						$("#pid").attr("onclick", "addOrCancelPraise(1,"+specialId+","+data.data+")");
						$("#pid").addClass('current');
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
	
	function addOrCancelCollection(flag,specialId,collectionId){
		
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
	 	var url = mainServer+"/weixin/collection/addCollection";
	 	var data={
	 		"collectionType":4,
	 		"objId":specialId
	 	};
	 	if(flag == 1){
	 		url = mainServer+"/weixin/collection/cancelCollection";
	 		if(collectionId == null || collectionId == "" || isNaN(collectionId) || parseFloat(collectionId) <= 0){
	 			showvaguealert('出现异常啦');
	 			return;
	 		}
	 		data={
		 		"collectionId":collectionId
		 	};
	 	}
		$("#colId").attr("onclick", "void(0);");
		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(flag == 1){
						$("#colId").attr("onclick", "addOrCancelCollection(0,"+specialId+",0)");
						$("#colId").removeClass('scsp2');
						showvaguealert("已取消收藏");
					}else{
						$("#colId").attr("onclick", "addOrCancelCollection(1,"+specialId+","+data.data+")");
						$("#colId").addClass('scsp2');
						showvaguealert("收藏成功");
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
	 	if(commentId == null || commentId == "" || isNaN(commentId) || parseFloat(commentId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
	 	var url = mainServer+"/weixin/praise/addPraise";
	 	var data={
	 		"praiseType":5,
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
	
	function queryComments(){
		$.ajax({                                       
			url:mainServer+'/weixin/specialcomment/specialCommentDetail',
			data:{
				"specialId":specialId,
				"commentPraiseType":5
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(data.data == null || data.data.length <= 0){
						$(".shafabg").show();
						return;
					}

					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						var commentVo = list[continueIndex];
						var clickPraise = "lineAddOrCancelPraise(0, 'dz"+continueIndex+"', "+commentVo.commentId+", 0)";
						var praiseCls = "dianzan";
						if(commentVo.isPraise > 0){
							clickPraise = "lineAddOrCancelPraise(1, 'dz"+continueIndex+"', "+commentVo.commentId+","+commentVo.isPraise+")";
							praiseCls = "dianzan2";
						}
					
						var showHtml = '<div class="plcent" >'
						    	+'	<div class="plcttop">'
						    	+'			<span class="one">'
						    	+'				<img src="'+commentVo.customerVo.head_url+'" alt="">'
						    	+'			</span>'
						    	+'			<span class="two">'
						    	+				commentVo.customerVo.nickname
						    	+'			</span>'
						    	+'			<span class="three">'
						    	+				commentVo.createTime
						    	+'			</span>'
						    	+'		</div>'
						    	+'		<div class="dchfcent">'
						    	+			commentVo.content
						    	+'		</div>'
						    	+'		<div class="plbotbox">'
						    	+'			<span class="'+praiseCls+'" id="dz'+continueIndex+'" onclick="'+clickPraise+'">'+commentVo.praiseCount+'</span>'
						    	+'		</div>'
						    	+'	</div>';
							
							$("#commentsId").append(showHtml);
					}
				}else{
					showvaguealert("访问出错");
				}
			},
			failure:function(data){
				showvaguealert('访问出错');
			}
		});
	}
	
	function shareSpecial(){
 		$.ajax({                                       
			url: mainServer+"/weixin/share/addShare",
			data:{
				"shareType":4,
				"objId":specialId
			},
			type:'post',
			dataType:'json',
			success:function(data){
				showvaguealert('分享成功');
				hideShare();
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
		window.location.href = mainServer+"/weixin/special/toAddTorialComment?specialId="+specialId+"&flag=0&specialType=5";
	}
	
	function shareAlert(){
		$(".zhuantifx").show();
	}
	
	function hideShare(){
		$(".zhuantifx").hide();
	}
	
	$('.backPage').click(function(){
		window.location.href=mainServer+"/weixin/special/findList?type=0";
	});