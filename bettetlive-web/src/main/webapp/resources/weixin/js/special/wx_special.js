$(document).ready(function(){
		 
		var ordernav=$(".nvzt_bg a"); 
		for(var i=0;i<ordernav.length;i++){  
			ordernav[i].index=i;  
			ordernav[i].onclick=function(){
				
				for(var i=0;i<ordernav.length;i++){
					ordernav[i].className=""; 
				 	guan();
				}   
				ordernav[this.index].className="current";
			}
		}  	
		
		  var xian=true; 
		  function kai(){
			  $(".mask").show();
			    $(".daixia").addClass("gaijian") 
		  }
		  function guan(){ 
			  $(".mask").hide();
				$(".daixia").removeClass("gaijian")
		  }
		  $(".mask").click(function(){
			  guan(); 
		  }) 
		  $(".daixia").click(function(){ 
			if(xian==true){
				kai();
		    xian=false; 
			}else{   
				guan(); 
			 xian=true;  
			} 
		  });
		});
	$(function(){
		
		
		$(".myordrnav .current").each(function(i,items){
			$(this).removeClass('current');
		});
		$(".zanwubg").hide();
		if(tabType == null || tabType == "" || tabType == "0"){
			tabType = 0;
			refreshOne(-1);
		}else{
			refreshSecond();	//美食推荐
		}
		$(".al"+tabType).addClass('current');  
		queryLikeProducts(); 
		
		var ordernav=$(".myordrnav li a"); 
		var ordercent=$(".myordercent"); 
		for(var i=0;i<ordernav.length;i++){  
			ordernav[i].index=i;  
			ordernav[i].onclick=function(){  
 
 				 $(".mask").hide();
				 $(".daixia").removeClass("gaijian");
				for(var i=0;i<ordernav.length;i++){
					ordernav[i].className=""; 
					ordercent[i].style.display="none";  
				}      
				ordernav[this.index].className="current";  
				ordercent[this.index].style.display="block";
				$("#special"+this.index).html("");
				$("#special"+this.index).show();
				$(".zanwubg").hide();
				
				$(".nvzt_bg a").each(function(i,items){
					$(this).removeClass('current');
				});
				$(".nvzt_bg").find("a").first().addClass("current");
				if(this.index == 0){
					refreshOne(-1);		//美食教程
				}else{
					refreshSecond();	//美食推荐
				}
			} 
		}   
	});
	
	function checkMoneyByPoint(useMoney){
		var result = "0";
		if(useMoney != null && useMoney != ""){
			result = useMoney+"";
			if(result.indexOf(".") > 0 && result.substring(result.indexOf(".")+1, result.length) == "00"){
				result = result.substring(0, result.indexOf("."));
			}
		}
		return result;
	}
	
	function refreshOne(typeId){
		$(".zanwubg").hide();
		$("#special0").html("");
		$("#special0").show();
		$("#special1").html("");
		$("#special1").hide();
		querySpecialsByOne(typeId);
	}
	
	function refreshSecond(){
		$(".zanwubg").hide();
		$("#special0").html("");
		$("#special0").hide();
		$("#special1").html("");
		$("#special1").show();
		querySpecialsBySecond();
	}
	
	function querySpecialsByOne(typeId){
		$(".initloading").show();
		$.ajax({                                       
			url:mainServer+'/weixin/specialarticle/querySpecialArticleList',
			data:{
				"articleTypeId":typeId
			},
			type:'post',
			
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(data.data == null || data.data.length <= 0){
						setTimeout(function(){
							$(".initloading").hide();
							$(".zanwubg").show();
						},800);
						return;
					}
					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						var specialVo = list[continueIndex];
						var showHtml = "";

						
						var stitle = specialVo.articleTitle;
						if(stitle.length > 25){
							stitle = stitle.substring(0, 25)+"...";
						}
						var showDesc = "";
						if(specialVo.articleIntroduce != null){
							showDesc = specialVo.articleIntroduce;
							if(showDesc.length > 20){
								showDesc = showDesc.substring(0, 20)+"...";
							}
						}

						
						var clickLocal = mainServer+"/weixin/specialarticle/toSpecialArticle?articleId="+specialVo.articleId;
						var commentLine = '<a class="an02" href="'+clickLocal+'">'+specialVo.commentCount+'</a>';
						if(specialVo.specialPage != null && specialVo.specialPage != ""){
							clickLocal = specialVo.specialPage;
							commentLine = "";
						}
						
						var praiseClz = "an03";
						var clickPraise = "lineAddOrCancelPraise(0, 'msdz"+specialVo.articleId+"', "+specialVo.articleId+", 0,4)";
						if(specialVo.isPraise > 0){
							praiseClz += " an030";
							clickPraise = "lineAddOrCancelPraise(1, 'msdz"+specialVo.articleId+"', "+specialVo.articleId+","+specialVo.isPraise+",4);";
						}
						
						showHtml =  '<div class="tjmsbox">'
				 	 			+	'	<div class="ztgqbox" onclick="location.href=\''+ clickLocal +'\'">'
				 	 			+	'		<img src="'+specialVo.articleCover+'" alt="" />'
// 				 	 			+			hasExpireLine
				 	 			+	'	</div>'
				 	 			+	'	<div class="tjtop" onclick="location.href=\''+ clickLocal +'\'">'
				 	 			+	'		<span>'+stitle+'</span>'
// 				 	 			+			showMoney
							 	+	' 	</div>'
								 +'	 	<div class="xbz_box xbz_box2">'
								 +'	 		<span class="left" onclick="location.href=\''+ clickLocal +'\'">'+showDesc+'</span>'
								 +'	 		<span class="right">'
								 +'	 			<a id="msdz'+continueIndex+'" class="'+praiseClz+'" href="javascript:'+clickPraise+'">'+specialVo.praiseCount+'</a>'
// 								 +'	 			<a class="'+ collectClz +'" href="javascript:'+clickCollection+'" id="mssc'+continueIndex+'">'+specialVo.collectionCount+'</a>'
								 +				commentLine
								 +'	 		</span>'
								 +'	 	</div>'
								 
							 	+	'</div>';
						$("#special0").append(showHtml);
					}
					setTimeout(function(){
						$(".initloading").hide();
					},800);
				}else{
					showvaguealert("出现异常");
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
	
	function querySpecialsBySecond(){
		$(".initloading").show();
		
		$.ajax({                                       
			url:mainServer+'/weixin/special/tutorialList',
			type:'post',
			dataType:'json', 
			success:function(data){	        
				if(data.code == "1010"){ //获取成功					    
					if(data.data == null || data.data.length <= 0){	 
						setTimeout(function(){
							$(".initloading").hide();
							$(".zanwubg").show();
						},800);
						return;
					}
					
					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						        
						var specialVo = list[continueIndex]; 		
						var collectClz = "an01";
						var clickCollection = "lineAddOrCancelCollection(0, 'cl"+continueIndex+"', "+specialVo.specialId+", 0,3)";
						if(specialVo.isCollection > 0){
							collectClz += " an010"; 
							clickCollection = "lineAddOrCancelCollection(1, 'cl"+continueIndex+"', "+specialVo.specialId+","+specialVo.isCollection+",3);";
						}
						
						var clickComment = mainServer+"/weixin/discovery/toTorialComment?specialId="+specialVo.specialId;
						
						var praiseClz = "an03";
						var clickPraise = "lineAddOrCancelPraise(0, 'dz"+continueIndex+"', "+specialVo.specialId+", 0, 2)";
						if(specialVo.isPraise > 0){
							praiseClz += " an030";
							clickPraise = "lineAddOrCancelPraise(1, 'dz"+continueIndex+"', "+specialVo.specialId+","+specialVo.isPraise+", 2);";
						}
						     
						var showHtml = '<div class="spjcbox">'
									 +'	 	<h3>'+specialVo.specialIntroduce+'</h3>'
									 +'	 	<div class="spbox">'   
									 +' 		<div class="spfmbg"><img src="'+specialVo.specialCover+'" alt="" /><a class="guanfm"></a></div>'    
									 +'			 <video height=100% width=100% src="'+specialVo.specialPage+'" controls="controls" name="video" ></video >'     
									 +'	 	</div>' 
									 +'	 	<div class="xbz_box">'
// 									 +'	 		<span class="left">'+specialVo.endTime+'</span>'
									 +'	 		<span class="right">'
									 +'	 			<a id="cl'+continueIndex+'" class="'+ collectClz +'" href="javascript:'+clickCollection+'">'+specialVo.collectionCount+'</a>'
									 +'	 			<a class="an02" href="'+clickComment+'">'+specialVo.commentCount+'</a>'
									 +'	 			<a id="dz'+continueIndex+'" class="'+praiseClz+'" href="javascript:'+clickPraise+'">'+specialVo.praiseCount+'</a>'
// 									 +'	 			<a class="an04" id="share'+ continueIndex +'" href="javascript:shareSpecial(3,'+specialVo.specialId+','+continueIndex+');">'+specialVo.shareCount+'</a>'
									 +'	 		</span>'
									 +'	 	</div>'
									 +'	 </div>';							
						$("#special1").append(showHtml);   
					}   
					setTimeout(function(){
						$(".initloading").hide();
					},800);
				}else{
					showvaguealert("出现异常");
				}   
				
				 
				for(var a=0;a<data.data.length;a++){
					
					var video = document.getElementsByName("video");//获取那个iframe，也可以用$('#iframe')[0]替代  
					$(".guanfm")[a].index=a;     
					$(".guanfm")[a].onclick=function(){  
						$(".spfmbg")[this.index].style.display="none";
						video[this.index].play();  
					};   
				}   
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
	
 
	function queryLikeProducts(){
		$.ajax({                                       
			url:mainServer+'/weixin/product/queryLikeProducts',
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(data.data == null || data.data.length <= 0){			
						return;
						
					}
					var list = data.data;
					for (var continueIndex in list) {
						if(isNaN(continueIndex)){
							continue;
						}
						var productVo = list[continueIndex];
						
						var shareExplain = "";
						if(productVo.share_explain != null){
							shareExplain = productVo.share_explain;
							if(shareExplain.length > 25){
								shareExplain = shareExplain.substring(0, 25)+"...";
							}
						}
						var showLabel="";
						if(productVo.labelName != null && productVo.labelName != ""){
							showLabel = "<p>"+productVo.labelName+"</p>"; 
						}
						var local = mainServer+"/weixin/product/towxgoodsdetails?productId="+productVo.product_id;
						if(productVo.activityType == 2){ //限量抢购
							local = mainServer+"/weixin/product/toLimitGoodsdetails?productId="+productVo.product_id+"&specialId="+productVo.activity_id;
						}else if(productVo.activityType == 3){ //团购
							local = mainServer+"/weixin/product/toGroupGoodsdetails?specialId="+productVo.activity_id+"&productId="+productVo.product_id;
						}
						var showMoneyLine = "";
						if(productVo.activityPrice != null && productVo.activityPrice != "" && parseFloat(productVo.activityPrice) >= 0){
							showMoneyLine = "<span>￥"+checkMoneyByPoint(productVo.activityPrice)
										 + "<strong>￥"+checkMoneyByPoint(productVo.price)+"</strong></span>";
						}else if(productVo.discountPrice != null && productVo.discountPrice != "" && parseFloat(productVo.discountPrice) >= 0){
							showMoneyLine = "<span>￥"+checkMoneyByPoint(productVo.discountPrice)
							 + "<strong>￥"+checkMoneyByPoint(productVo.price)+"</strong></span>";
						}else{
							showMoneyLine = "<span>￥"+checkMoneyByPoint(productVo.price)+"</span>";
						}
						var showHtml='<div class="tuijian" onclick="location.href=\''+local+'\'">'
									+' 		<div class="left">'
									+'		<img src="'+productVo.product_logo+'" alt="" />'
									+'	</div>'
									+'	<div class="right">'
									+'		<div class="tjname">'
									+'		<span>' + productVo.product_name + '</span>'
									+		showLabel
									+'		</div>'
									+'		<div class="tjcent">'
									+			shareExplain
									+'		</div>'
									+'		<div class="tjmoney">'
									+			showMoneyLine
									+'			<p>销量'+productVo.salesVolume+'份</p>'
									+'		</div>'
									+'	</div>'
									+'</div>'
						
						$("#tuijianId").append(showHtml);
					}
				}else{
					showvaguealert("出现异常");
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
	
	var backUrl = window.location.href;
	function lineAddOrCancelPraise(flag,praiseLineId,specialId,praiseId,praiseType){
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
		$("#"+praiseLineId).attr("href", "javascript:void(0);");
		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(flag == 1){
						$("#"+praiseLineId).attr("href", "javascript:lineAddOrCancelPraise(0,'"+praiseLineId+"',"+specialId+",0,"+praiseType+")");
						$("#"+praiseLineId).attr('class', 'an03');
						var praiseCount = $("#"+praiseLineId).html();
						$("#"+praiseLineId).html(parseFloat(praiseCount)-1);
						showvaguealert("已取消点赞");
					}else{
						$("#"+praiseLineId).attr("href", "javascript:lineAddOrCancelPraise(1,'"+praiseLineId+"',"+specialId+","+data.data+","+praiseType+")");
						$("#"+praiseLineId).attr('class', 'an030');
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
	
	function lineAddOrCancelCollection(flag,collectionLineId,specialId,collectionId, type){
		
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
			return;
		}
		
	 	if((flag != 0 && flag != 1) || isNaN(flag)){
	 		showvaguealert("出现异常");
	 		return;
	 	}
	 	if(collectionLineId == null || collectionLineId == ""){
	 		showvaguealert("出现异常");
	 		return;
	 	}
	 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
	 	var url = mainServer+"/weixin/collection/addCollection";
	 	var data={
	 		"collectionType":type,
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
	 	
 		$("#"+collectionLineId).attr("href", "javascript:void(0);");
 		
		$.ajax({                                       
			url: url,
			data:data,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){ //获取成功
					if(flag == 1){
						$("#"+collectionLineId).attr("href", "javascript:lineAddOrCancelCollection(0,'"+collectionLineId+"',"+specialId+",0,"+type+")");
						$("#"+collectionLineId).attr('class', 'an01');
					
						var coCount = $("#"+collectionLineId).html();
						$("#"+collectionLineId).html(parseFloat(coCount)-1);
						showvaguealert("已取消收藏");
					}else{
						$("#"+collectionLineId).attr("href", "javascript:lineAddOrCancelCollection(1,'"+collectionLineId+"',"+specialId+","+data.data+","+type+")");
						$("#"+collectionLineId).attr('class', 'an010');
						var coCount = $("#"+collectionLineId).html();
						$("#"+collectionLineId).html(parseFloat(coCount)+1);
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
	
	
	function hideShare(){ 
		$(".zhuantifx").hide();
	}
	
	function huitop(){
		scrollTo(0,0);  
	}
 	
	 