$(function() {
	awardlist.inIt()
});
var awardlist = {
	obj:{
		url:mainServer+"/weixin/integral/getAWardList", //请求接口
		pageIndex:1,
		pageCount:'',	//总页数
		checkLotterySign:0	//是否签到
	},
	//上拉加载
	loadMore: function() {
		var oSelf = this;
		var loading = false;
		$(document.body).infinite();
		$(document.body).infinite().on("infinite",function(){
			if(loading) return;
			loading = true;
			$('.weui-loadmore').show();
			setTimeout(function(){
				if(oSelf.obj.pageIndex <= oSelf.obj.pageCount) {
					oSelf.obj.pageIndex++;
					if(oSelf.obj.pageIndex <= oSelf.obj.pageCount) {
						oSelf.findAwardRecord()
					}
				}else {
					$(document.body).destroyInfinite();
				};
				loading = false;
				$('.weui-loadmore').hide()
			}, 1500)
		})
	},
	goBack:function(){
		$(".goback").click(function(){
			var url = mainServer+"/weixin/integral/toIntegral";
			location.href = url;
		});
	},
	goWinIntegral:function(){
		$(".gomakemoney").click(function(){
			var url = mainServer+"/weixin/integral/toWinIntegral";
			location.href = url;
		});
	},
	//请求数据
	findAwardRecord:function(){
		var oSelf = this;
		$.post(oSelf.obj.url,{
			pageIndex: oSelf.obj.pageIndex,
			rows: 5
		},function(data, error){
			if (data.code == '1010') {
				if(oSelf.obj.pageIndex==1){
					if(data.data.records==null || data.data.records.length==0){
						$('.nodata').show();
						return;
					}
				}
				oSelf.obj.pageCount = data.data.pageCount;
				oSelf.establishpage(data.data.records);
				
			} else {
				$.toast(data.msg, "text");
			}
		});
	},
	//渲染页面
	establishpage:function(list){
		var oSelf = this;
		if (list!=null && list.length != 0) {
			var showHtml = '';
			for (var i = 0; i < list.length; i++) {
				var recordVo = list[i];
				var integraltype = (function(){
					switch (recordVo.integralType) {
					case 1:return"每日签到"
					case 2:return"发动态"
					case 3:return"分享文章"
					case 4:return"分享视频"
					case 5:return"文章点赞"
					case 6:return"视频点赞"
					case 7:return"订单支付"
					}
				})();
				showHtml = oSelf.getListToHTML(recordVo,integraltype);
				$('.list').append(showHtml);
			}
		}

	},
	getListToHTML:function(recordVo,integraltype){
		var oSelf = this;
		var button_status='';
		var typeTwoHtml = '';
		var showText = '';
		var ahref=recordVo.linkUrl;
		if(recordVo.integralType==2){//发布动态
			button_status = (function(){
				switch (recordVo.status) {
					case 0:return'<a class="oshaer" href="'+mainServer+'/weixin/discovery/toPublishDynamic?backUrl='+mainServer+'/weixin/integral/togetAward">继续发布</a><a class="get"  data-recordId="'+recordVo.recordId+'">领取</a>';
					case 1:return'<a class="oshaer" href="'+mainServer+'/weixin/discovery/toPublishDynamic?backUrl='+mainServer+'/weixin/integral/togetAward">继续发布</a><a class="get lose">已领取</a>';
					case 2:return'<a class="oshaer" href="'+mainServer+'/discovery/toPublishDynamic?backUrl='+mainServer+'/weixin/integral/togetAward">继续发布</a><a class="get lose">已失效</a>';
				}
			})();
			
		}else if(recordVo.integralType==5 || recordVo.integralType==6){//5是文章点赞，6是视频点赞
			button_status = (function(){
				switch (recordVo.status) {
					case 0:return'<a class="oshaer" onclick="shareToWechart('+recordVo.objId+',\''+recordVo.title+'\',\''+recordVo.imgUrl+'\',\''+recordVo.shareDesc+'\',\''+recordVo.linkUrl+'\')">再次分享</a><a class="get" data-recordId="'+recordVo.recordId+'">领取</a>';
					case 1:return'<a class="oshaer" onclick="shareToWechart('+recordVo.objId+',\''+recordVo.title+'\',\''+recordVo.imgUrl+'\',\''+recordVo.shareDesc+'\',\''+recordVo.linkUrl+'\')">再次分享</a><a class="get lose">已领取</a>';
					case 2:return'<a class="oshaer" onclick="shareToWechart('+recordVo.objId+',\''+recordVo.title+'\',\''+recordVo.imgUrl+'\',\''+recordVo.shareDesc+'\',\''+recordVo.linkUrl+'\')">再次分享</a><a class="get lose">已失效</a>';
					case 3:return'<a class="oshaer" onclick="shareToWechart('+recordVo.objId+',\''+recordVo.title+'\',\''+recordVo.imgUrl+'\',\''+recordVo.shareDesc+'\',\''+recordVo.linkUrl+'\')">再次分享</a>';
				}
			})();
			
		}else if(recordVo.integralType==7){//支付成功获取的金币
			button_status = (function(){
				switch (recordVo.status) {
					case 0:return'<a class="oshaer" href="'+mainServer+'/weixin/product/towxgoodsdetails?productId='+recordVo.productId+'&backUrl='+mainServer+'/weixin/integral/togetAward">继续购买</a><a class="get" data-recordId="'+recordVo.recordId+'">领取</a>'
					case 1:return'<a class="oshaer" href="'+mainServer+'/weixin/product/towxgoodsdetails?productId='+recordVo.productId+'&backUrl='+mainServer+'/weixin/integral/togetAward">继续购买</a><a class="get lose">已领取</a>'
				}
			})();
		}
		showText = recordVo.appDesc;
		var typeHtml = [
						'<li>',
						'   <dl>',
						'	   <dt data-href="'+ahref+'">',
						' 		 <img src="'+recordVo.imgUrl+'" onload="imgFunc.imgLoad(this)" onerror="imgFunc.imgError(this)"/>',
						'			<div class="imgtitle">'+integraltype+'</div>',
						'		</dt>',
						'		<dd>',
						'			<h3 class="ellipse" data-href="'+ahref+'">'+recordVo.title+'</h3>',
						'			<h4 data-href="'+ahref+'"><span>'+showText+'</h4>',
						'			<div class="timer">'+(recordVo.createTime==null?" ":recordVo.createTime)+'</div>',
						'			<div class="func">'+button_status+'</div>',
						'		</dd>',
						'	</dl>',
						'	</li>'
		               ].join('');
		return typeHtml;
	},
	operate:function(){
		$(".list").on('click','.get',function(){
			var recordId = $(this).attr("data-recordId");
			var url = mainServer+"/weixin/integral/getGoldAWard";
			var $_this = $(this);
			if(recordId!=null && recordId!=''){
				$.post(url,{
					recordId: recordId,
				},function(data, error){
					if (data.code == '1010') {
						$_this.addClass("lose");
						$_this.html("已领取");
						var total = $('.num').html();
						total = parseInt(total)+parseInt(data.data);
						$('.num').html(total)
						$("#goldCount").html(data.data);
						$('.hint').fadeIn(1000,function(){
							setTimeout(function(){
								$('.hint').fadeOut(1000)
							},1000)
						});
					} else {
						$.toast(data.msg, "text");
					}
				});
			}
		});
		$(document).on('click',".list dt,h3,h4",function(){
			var ahref = $(this).attr("data-href");
			window.location.href=ahref+"&backUrl="+mainServer+"/weixin/integral/togetAward";
		});
		/*$(".hint").click(function(){
			$(this).fadeOut(1000);
		});*/
		$('.check').click(function(){
			var backUrl = mainServer+"/weixin/integral/togetAward";
			window.location.href = mainServer+"/weixin/discovery/toHelp?backUrl="+backUrl;
		});
		$('.share').click(function(){
			$(this).fadeOut(1000);
		});
	},
	drawlottery:function(){ 	//今日抽奖
		var checkLottery = $("#lotteryId").attr("class");
		//如果当前用户未抽奖，则获取抽奖需要的信息
		if(checkLottery == undefined || checkLottery.indexOf("ogray") != -1 || awardlist.obj.checkLotterySign == 1){
			return;
		};
		$('#lotteryId').click(function(){
			//如果当前用户未抽奖，则获取抽奖需要的信息
			if(checkLottery == undefined || checkLottery.indexOf("ogray") != -1 || awardlist.obj.checkLotterySign == 1){
				return;
			};
			$('.lottery-wrap').fadeIn(1000);
		});
		//点击开奖
		$('.lotterylist li').click(function(){
			if(awardlist.obj.checkLotterySign == 1){ //今日已签到
				return;
			};
			var clickNode = $(this);
			$.ajax({
				url: mainServer + "/weixin/integral/getLotterySign",
				data:{
					"clickIndex": clickNode.index()
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					if(data.code == "1010"){
						awardlist.obj.checkLotterySign = 1;
						var result = data.data;
						var descs = result.lotteryIntegralDesc;
						var signIntegral = result.signIntegral;
						var serialSign = result.serialSign;
						var currentIntegral = result.currentIntegral;
				        var currDate = new Date(data.currtime);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
				        var checkDay = currDate.getDay();
				        
						$(".lotterylist li").each(function(index){
							 var thisSpan = $(this).find("span");
							 thisSpan.html(descs[index]);
						});
						var $thisTip = $('.onlottery-wrap');
						var $thisH4 = $thisTip.find("h4");
						var resultTip = "谢谢参与";
						var oclassName = '';
						if(signIntegral == 0){
							$thisTip.find("div").addClass("offlottery");
							$thisH4.html(resultTip);
							resultTip = "很遗憾，您未能抽中哦";
							oclassName = 'onparticipation'; //没有中奖
						}else{
							
							$("#myIntegralId").html(currentIntegral);
							$(".grade-wrap div:eq("+ checkDay +")").find("img").attr("src", resourcepath + "/weixin/img/mygold/grade_01.png");
							
							
							resultTip = "恭喜您，成功领取"+signIntegral+"个金币";
							$thisH4.html("金币<span>"+signIntegral+"</span>个");
							oclassName = 'ondraw'; //点击已中奖的券
						}
						$("#lotteryId").addClass("ogray");
						var signDesc = "今日已抽奖";
						if(serialSign > 1){
							signDesc = "已连续抽奖"+serialSign+"天";
						}
						$("#lotteryId").text(signDesc);
						
						$(".lottery-wrap .box-top").find("h3").text(resultTip);
						//短时间切换点击的券在去掉点击状态
						clickNode.addClass('on');
						setTimeout(function(){
							clickNode.removeClass('on').addClass(oclassName);
							clickNode.css({
								'transition': 'all 1s',
								'transform': 'rotateY(0deg)'
							});
						},100);
						setTimeout(function(){
							clickNode.find('span').css('display','block');
						},600);
						$('.onlottery-wrap').show(function(){
							$('.onlottery-wrap').animate({
								height:"100%",
								width:"100%",
								opacity:"1"
							},1000,function(){
								setTimeout(function(){
									$('.onlottery-wrap').fadeOut(1000,function(){
										$('.lotterylist li').css({
											'transition': 'all 1s',
											'transform': 'rotateY(0deg)'
										});
										//循环给li不同的class类名
										$('.lotterylist li').not(clickNode).each(function(){
											 var thisSpan = $(this).find("span");
											 if(thisSpan.text() == "谢谢参与"){
												 $(this).addClass("participation");
											 }else{
												 $(this).addClass("draw")
											 }
										});
										$('.lotterylist li span').css('display','block');
									});
								},2000);
							});
						})
					}else if (data.code == "1011"){
						window.location.href = mainServer + "/weixin/tologin";
					}else{
						if(data.code == "1405"){
							$(".lottery-wrap .box-top").find("h3").text("您今天已经抽奖过啦");
						};
						$.toast(data.msg, "text");
					}
				}
			});
		});
		//关闭
		$('.close').click(function(){
			$(this).parents('.lottery-wrap').fadeOut(1000,function(){
				$('.lotterylist li').css({
					'transition': 'all 1s',
					'transform': 'rotateY(-180deg)'
				})
			})
		});
	},
	inIt:function(){
		this.findAwardRecord();
		this.loadMore();
		this.goBack();
		this.operate();
		this.drawlottery();
		this.goWinIntegral();
	}
};
//图片错误和不规则图片完全显示处理
var imgFunc = {
	//错误图片换成默认图片
	imgError:function(img){
		img.src= resourcepath + "/weixin/img/home/rush-defult.png";
		img.onerror=null;
	},
	//不规则图片完全显示(暂时对html结构有要求)参数select为容器dom节点为jq对象
	imgLoad:function(select){
			if($(select).width() > $(select).height()){
				$(select).css({
					'width':'auto',
					'height':'100%'
				})
			}else{
				$(select).css({
					'width':'100%',
					'height':'auto'
				})
			}

	}
}

function shareToWechart(objId,title,imgUrl,introduce,shareUrl){
	var url = mainServer+'/weixin/shareWeixin';
	var pageUrl = window.location.href;
	var imgUrl = imgUrl;
	$('.share').fadeIn(500,function(){
		$.post(url,{url:pageUrl},function(data){
			var shr = eval('(' + data + ')');
			if(shr.shareInfo != null){
			wxShare(shr.shareInfo.appId,shr.shareInfo.timestamp,shr.shareInfo.nonceStr,shr.shareInfo.signature,title,sharUrl,imgUrl,introduce,objId,shareType);
			};
		});
	});
}


function shareSpecial(objId){
	
		$.ajax({                                       
		url: mainServer+"/weixin/share/addShare",
		data:{
			"shareType":4,
			"objId":objId
		},
		type:'post',
		dataType:'json',
		success:function(data){
			$('.share').fadeOut(1000);
			$.toast('分享成功');
			
		},
		failure:function(data){
			$.toast('访问出错');
		}
	});
}

function shareVideo(objId){
	$.ajax({                                       
		url: mainServer+"/weixin/share/addShare",
		data:{
			"shareType":3,
			"objId":objId
		},
		type:'post',
		dataType:'json',
		success:function(data){
			$('.share').fadeOut(1000);
			$.toast('分享成功',"text");
			
		},
		failure:function(data){
			$.toast("访问出错","text");
			
		}
	});
}




//微信自带返回
$(function(){
	var url=mainServer+"/weixin/integral/toIntegral";
	
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
    
    
  //返回上一个页面
	$('.fan').click(function(){
		window.location.href=url;	
	});
});
