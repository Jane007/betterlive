var checkLotterySign = 0;
var customerIntegral = "N";
$(function(){
	//头部透明度切换效果
	$(window).scroll(function(){
		if($(window).scrollTop() < 100){
			$('.header-wrap').css('opacity',0.8)
		};
		if($(window).scrollTop() > 100  || $(window).scrollTop() == 0){
			$('.header-wrap').css('opacity',1)
		};
	 });
	//个人动态
	$('.msnum').find('span').each(function(){
		if($(this).html() > 99){
			$(this).show();
			$(this).html('99+');
			$(this).css('font-size','10px')
		}else if($(this).html() <= 0){
			$(this).hide()
		}
	});
	//个人动态、关注列表小图标
	$('.orderbottom').find('em').each(function(){
		if($(this).html() > 99){
			$(this).show();
			$(this).html('99+');
			$(this).css('font-size','10px')
		}else if($(this).html() <= 0){
			$(this).hide()
		}
	});
	//没有红包时不显示数量
	$('.umsglist').find('em').each(function(){
		var count = $(this).html();
		if(count<=0){
			$(this).parent('span').hide();
		}
	});
	getSysInvite();
	//今日抽奖
	drawlottery();
	//新手指引
	anniversary.getStorage();
});
//新手指引
var anniversary = {
	//读取缓存
	getStorage:function(){
		var anniversaryObj = JSON.parse(localStorage.getItem("userGuide"));
		if(anniversaryObj && parseInt(anniversaryObj.flag) >= 0){
			$('.guide-wrap').hide();
			var custId = $("#customerId").val();
			if(anniversaryObj.flag == 0 && custId != null && custId != "") { //flag=0 是在未登录情况下读的指引
				saveGuide(anniversaryObj.guideType);
			}
		}else {
			checkPersonalGuide();
		}
	}
}
function getSysInvite(){
	$.ajax({
		url: mainServer + "/weixin/usercenter/getSysInvite",
		datatype : "json",
		type : "post",
		success : function(data) {
			if(data.code == "1010"){
				var inviteStatus = data.data.inviteStatus;
				if(inviteStatus == 1){
					$(".umsglist .banner").show();
				}
				var banner = data.data.inviteBanner;
				if(banner!=null && banner!=''){
					$(".banner img").attr("src",banner);
				}
			}
		}
	});
}


function toSocialityHome(){
	var backUrl = mainServer + "/weixin/tologin";
	window.location.href = mainServer + "/weixin/socialityhome/toSocialityHome?backUrl="+backUrl;
}
//今日抽奖
var drawlottery = function(){
	var checkLottery = $(".drawlottery").attr("class");
	//如果当前用户未抽奖，则获取抽奖需要的信息
	if(checkLottery == undefined || checkLottery.indexOf("off") != -1 || checkLotterySign == 1){
		return;
	}
	$('.drawlottery').click(function(){
		//如果当前用户未抽奖，则获取抽奖需要的信息
		if(checkLottery == undefined || checkLottery.indexOf("off") != -1 || checkLotterySign == 1){
			return;
		}
		$('.lottery-wrap').fadeIn(1000);
	});
	//点击开奖
	$('.lotterylist li').click(function(){
		if(checkLotterySign == 1){ //今日已签到
			return;
		}
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
					
					checkLotterySign = 1;
					var result = data.data;
					var descs = result.lotteryIntegralDesc;
					var signIntegral = result.signIntegral;
					var serialSign = result.serialSign;
					var custLevelName = result.custLevelName;
					var currentIntegral = result.currentIntegral;
					var accumulativeIntegral = result.accumulativeIntegral;
					var requirementIntegral = result.requirementIntegral;
					
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
						oclassName = 'onparticipation' //没有中奖
					}else{
						$("#levelNameId").html(custLevelName);
						$("#myTotalIntegralId").html(accumulativeIntegral);
						$("#upgradeIntegralId").html(requirementIntegral);
						$("#myIntegralId").html(currentIntegral);
						resultTip = "恭喜您，成功领取"+signIntegral+"个金币";
						$thisH4.html("金币<span>"+signIntegral+"</span>个");
						oclassName = 'ondraw' //点击已中奖的券
					}
					
					$(".drawlottery").addClass("off");
					var signDesc = "今日已抽奖";
					if(serialSign > 1){
						signDesc = "已连续抽奖"+serialSign+"天";
					}
					$(".drawlottery").text(signDesc);
					
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
										 //var $this = $(this);
										 var thisSpan = $(this).find("span");
										 if(thisSpan.text() == "谢谢参与"){
											 $(this).addClass("participation");
										 }else{
											 $(this).addClass("draw")
										 }
										 /*if(clickNode != $this){
											 $this.addClass('draw');
										 }*/
									})
									//clickNode.addClass('ondraw');
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
					}
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
}

/**
 * 新手指引
 */
function checkPersonalGuide() {
	$.ajax({                                       
		url: mainServer + '/weixin/customerGuide/checkPersonalGuide',
		data:{},
		type:'post',
		dataType:'json',
		success:function(data){
			var lotterySignStatus = $('#lotterySignStatus').val();
			var customerLotterySign = data.customerLotterySign;
			customerIntegral = data.customerIntegral;
			if((lotterySignStatus == 0 && customerLotterySign == "Y") || customerIntegral == "Y"){
				var checkFlag = 0;
				var custId = $("#customerId").val();
				if(custId != null && custId != ""){
					checkFlag = 1;
				}
				
				var guideType = 0;
				if(customerLotterySign == "N" && customerIntegral == "Y"){
					guideType = 1;
					$(".guide-wrap>.guide-top").addClass("ghold");
				}
				
				//缓存状态标记
				var obj = {
					flag:checkFlag,
					guideType:guideType
				};
				localStorage.setItem('userGuide',JSON.stringify(obj));
				
				$('.guide-wrap').show();
				if(custId != null && custId != ""){
					saveGuide(guideType);
				}
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 新手指引：点击下一步
 */
$('.guide-wrap').click(function(){
	var $gwrap = $(".guide-wrap>.guide-top");
	var checkNext = $gwrap.attr("class");
	if(checkNext.indexOf("ghold") == -1 && customerIntegral == "Y"){
		$(".guide-wrap>.guide-top").addClass("ghold");
	}else{
		$('.guide-wrap').fadeOut(1000);
	}
	
});

/**
 * 使新手指引已读
 */
function saveGuide(type){
	if($('#customerId').val()) {
		$.ajax({                                       
			url: mainServer + '/weixin/customerGuide/saveGuide',
			data:{
				'type': type //已读每日签到指引，如用户未读下一步也不会再指引下一步
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.code == "1010"){
					var anniversaryObj = JSON.parse(localStorage.getItem("userGuide"));
					if(anniversaryObj){
						var obj = {
							flag:1,
							guideType:type
						};
						localStorage.setItem('userGuide',JSON.stringify(obj));
					}
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
}
