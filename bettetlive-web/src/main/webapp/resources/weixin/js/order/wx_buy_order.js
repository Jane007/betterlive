	var checkGiftPwd = false;
	var npayRealMoney = 0;
	var goldNeedIntegral = 0;
		$(function(){
			$(".initloading").show();
			setTimeout(function(){
				$(".initloading").hide();
			},800);
			
			var deilverStatus = $("#deilverStatus").val();
			var deilverMsg = $("#deilverMsg").val();
			if(deilverStatus != null && deilverStatus == "false"){
				 setTimeout(function() {  
					 alert(deilverMsg);
			        },  
			        500)  
			}
			if(fullCut == null || fullCut == "" || fullCut == undefined){
				fullCut = 0;
			}
			
			if(couponId == null || couponId == "" || couponId == undefined){
				couponId = 0;
			}
			if(singleCouponId == null || singleCouponId == "" || singleCouponId == undefined){
				singleCouponId = 0;
			}
			
			if(activityPrice == null || activityPrice == "" || activityPrice == undefined){
				activityPrice = 0;
			}
			
			if(couponId > 0){
				$("#card1").attr("checked", "true");
				$("#couponSpanId").addClass("on");
				$("#couponMoney").html('- ¥ <em data-couponInfo="'+couponId+'">'+coupon_money+'</em><i></i>');
			}else{
				$("#couponMoney").html('暂无可用优惠券');
				$("#couponLineId").addClass("empty");
			}
			
			if(singleCouponId > 0){
				$("#card3").attr("checked", "true");
				$("#singleCouponSpanId").addClass("on");
				$("#singleCouponMoney").html('- ¥ <em data-singleCoupon="'+singleCouponId+'">'+couponMoney+'</em><i></i>');
			}else{
				$("#singleCouponMoney").html('暂无可用红包');
				$("#singleCouponLineId").addClass("empty");
			}
			
			var myAddressId = $("#myAddressId").val();
			if(null==myAddressId || ""==myAddressId || myAddressId <= 0){
				$(".no-address").show();
				$(".has-address").hide();
			}else{
				$(".no-address").hide();
				$(".has-address").show();
			}
			
			initGoldDeductInfo();
			
			//开始计算商品价格
			totalCount();
			
	});	
		
		//点击普通金币抵扣活动
		$('#integralSpanId').click(function(){
			if(goldDeductSpecId <= 0){
				return;
			}
			
			if($(this).hasClass('on')){
				var clearMoney = $("#integralLineId").attr("data-calculate-money");
				if(clearMoney != null && clearMoney != "" && parseFloat(clearMoney) > 0){
					var addIntegral = parseFloat((clearMoney/integralDeductionRate).toFixed(2));
					surplusIntegral = surplusIntegral + addIntegral;
				}
				goldNeedIntegral = 0;
				$("#integralCheckId").attr('checked', false);
				$(this).removeClass('on');
				$("#integralLineId").attr("data-calculate-money", "0");
				totalCount();
			}else{
				if(surplusIntegral < minLimitIntegral){
					return;
				}
				
				if(lastPayMoney <= 0){
					showvaguealert("实付金额为0，无需使用金币抵扣");
					return;
				}
				
				var calculateMoney = parseFloat((surplusIntegral*integralDeductionRate).toFixed(2)); //当前金币兑换成余额
				var calculateIntegral = parseFloat((lastPayMoney/integralDeductionRate).toFixed(2)); //需要这么多金币
				var useMoney = 0;

				var useMaxIntegral = surplusIntegral;
				if(useMaxIntegral >= maxLimitIntegral){
					useMaxIntegral = maxLimitIntegral;
					calculateMoney = parseFloat((useMaxIntegral*integralDeductionRate).toFixed(2));
				}
				if(calculateIntegral >= useMaxIntegral){
					goldNeedIntegral = useMaxIntegral;
					surplusIntegral = surplusIntegral - goldNeedIntegral;
					useMoney = calculateMoney;
				}else{
					surplusIntegral = surplusIntegral - calculateIntegral;
					
					goldNeedIntegral = calculateIntegral;
					useMoney = lastPayMoney;
				}
				$("#integralCheckId").attr('checked', true);
				$(this).addClass('on');
				$("#integralLineId").attr("data-calculate-money", useMoney);
				totalCount();
			}
		});
		
		//参与金币优惠购
		$('.orgoodslist-wrap').on("click", ".calloff", function(){
			var btn = $(this).html();
			var needIntegral = $(this).attr("data-need-integral");
			var integralCheckId = $("#integralCheckId").attr('checked');
			if(btn == "参加"){
				if(lastPayMoney <= 0){
					showvaguealert("实付金额为0，无需参与金币优惠购");
					return;
				}
				
				//判断当前金币余额
				if(surplusIntegral < parseFloat(needIntegral)){ //参与
					showvaguealert("您的金币还不足以参加抵扣哦");
					return;
				}
				surplusIntegral = surplusIntegral - parseFloat(needIntegral);
				if(!integralCheckId){ //当前未选中普通金币抵扣
					if(surplusIntegral < minLimitIntegral){
						$("#integralLineId").addClass("empty");
						$("#integralLineId .monright").html("共" + surplusIntegral + "金币，满" + minLimitIntegral + "金币可用");
					}else {
						if(surplusIntegral >= maxLimitIntegral){
							var calculateMoney = parseFloat((maxLimitIntegral*integralDeductionRate).toFixed(2));
							$("#integralLineId .monright").html("当前可用" + maxLimitIntegral + "金币，可抵扣" + calculateMoney + "元");
						}else{
							var calculateMoney = parseFloat((surplusIntegral*integralDeductionRate).toFixed(2));
							$("#integralLineId .monright").html("当前可用" + surplusIntegral + "金币，可抵扣" + calculateMoney + "元");
						}
					}
				}
				$(this).html("取消");
				totalCount();
			}else if (btn == "取消"){
				surplusIntegral = surplusIntegral + parseFloat(needIntegral);
				if(!integralCheckId){
					if(surplusIntegral < minLimitIntegral){
						$("#integralLineId").addClass("empty");
						$("#integralLineId .monright").html("共" + surplusIntegral + "金币，满" + minLimitIntegral + "金币可用");
					}else {
						$("#integralLineId").removeClass("empty");
						if(surplusIntegral >= maxLimitIntegral){
							var calculateMoney = parseFloat((maxLimitIntegral*integralDeductionRate).toFixed(2));
							$("#integralLineId .monright").html("当前可用" + maxLimitIntegral + "金币，可抵扣" + calculateMoney + "元");
						}else{
							var calculateMoney = parseFloat((surplusIntegral*integralDeductionRate).toFixed(2));
							$("#integralLineId .monright").html("当前可用" + surplusIntegral + "金币，可抵扣" + calculateMoney + "元");
						}
					}
					$(this).html("参加");
				}else{
					var checkGoldIntegral = surplusIntegral+ parseFloat(goldNeedIntegral);
					surplusIntegral = checkGoldIntegral;
					if(checkGoldIntegral >= maxLimitIntegral){
						var calculateMoney = parseFloat((maxLimitIntegral*integralDeductionRate).toFixed(2));
						$("#integralLineId .monright").html("当前可用" + maxLimitIntegral + "金币，可抵扣" + calculateMoney + "元");
					}else{
						var calculateMoney = parseFloat((checkGoldIntegral*integralDeductionRate).toFixed(2));
						$("#integralLineId .monright").html("当前可用" + checkGoldIntegral + "金币，可抵扣" + calculateMoney + "元");
					}
					
					$(this).html("参加");
					
					totalCount();
					
					if(lastPayMoney <= 0){
						return;
					}

					$("#integralLineId").attr("data-calculate-money", "0");
					
					var calculateMoney = parseFloat((checkGoldIntegral*integralDeductionRate).toFixed(2)); //当前金币兑换成余额
					var calculateIntegral = parseFloat((lastPayMoney/integralDeductionRate).toFixed(2)); //需要这么多金币
					var useMoney = 0;

					var useMaxIntegral = checkGoldIntegral;
					if(useMaxIntegral >= maxLimitIntegral){
						useMaxIntegral = maxLimitIntegral;
						calculateMoney = parseFloat((useMaxIntegral*integralDeductionRate).toFixed(2));
					}
					if(calculateIntegral >= useMaxIntegral){
						goldNeedIntegral = useMaxIntegral;
						surplusIntegral = surplusIntegral - goldNeedIntegral;
						useMoney = calculateMoney;
					}else{
						surplusIntegral = surplusIntegral - calculateIntegral;
						
						goldNeedIntegral = calculateIntegral;
						useMoney = lastPayMoney;
					}
					
					$("#integralLineId").attr("data-calculate-money", useMoney);
				}
				
				totalCount();
			
			}
		});
		
		//同意协议
		$('.consent span').click(function(){
			if($(this).hasClass('on')){
				$(this).removeClass('on');
				$('.go-shop').addClass('active');
			}else{
				$(this).addClass('on');
				$('.go-shop').removeClass('active');
			}
		});
		
		//点击打开协议
		$('.consent a').click(function(){
			$('.agreement').show();
			$('.mask').show();
		});
		//点击关闭协议，点击遮罩层关闭协议
		$('.outBox-a,.mask').click(function(){
			if (!$('.dia-pay-pass').is(':visible')) {
				$('.mask').hide();
			}
			$('.agreement').hide();
		});
		
		$('.dia-cancel').click(function(){
			$('.dia-pay-pass').hide();
			$('.mask').hide();
			$("#cardRdo").prop('checked',false);
			$("#giftCardSpanId").removeClass("on");
			$(".gift-card").find(".giftcards").attr("lang","");
			totalCount();
		});
		
		//使用礼品卡
		 $(".inputArea2").keyup(function(e){

	            var pwdObj = $(".inputArea2");

	            if(pwdObj.val().length === 6){

	                var payValue=$.md5(pwdObj.val());

	                $.ajax({
	                    type: 'POST',
	                    url: mainServer + "/weixin/usercenter/affirmPayPwdByOrder",
	                    async: false,
	                    data: {'payPwd':payValue},
	                    dataType:'json',
	                    success:function(data) {
	                        if(data.result =='succ' ){
	                            $('.dia-pay-pass').hide();
	                            $('.mask').hide();
	                            $(".gift-card").find(".giftcards").attr("lang","useGiftCard");
	                            pwdObj.val('');
	                        	checkGiftPwd = true;
	                            totalCount();
	                        }else{
	                            showvaguealert('密码错误');
	                            pwdObj.val("");
	                            $('.block i').hide();
	                            return false;
	                        }
	                    },
	                    error : function() {
	                        showvaguealert('请重新选择商品');
	                        return false;
	                    }
	                });

	            }

	        });
		
		$('.inputArea2').focus(function(){
			$('.blockWrapper').addClass('active');
		}).blur(function(){
			$('.blockWrapper').removeClass('active');
		});
		
		function totalCount(){
			var couponPrice = 0;
			var singleCouponPrice = 0;
			
			if(returnType != 4){
				if($("#card1").is(':checked')){
					couponPrice=$("#couponLineId em").text();      //优惠券金额
					if(null ==couponPrice || ""==couponPrice){
						couponPrice=0;
					}
				}
				if($("#card3").is(':checked')){
					singleCouponPrice=$("#singleCouponLineId em").text();      //红包金额
					if(null ==singleCouponPrice|| ""==singleCouponPrice || undefined == singleCouponPrice){
						singleCouponPrice=0;
					}
				}
			}
			
			var usecard=$(".gift-card").find(".giftcards").attr("lang");
			var cardPrice = 0;
			var checkGiftBox = $("#cardRdo").is(':checked');
			if(checkGiftBox){
				cardPrice = $(".gift-card em").text();     //礼品卡使用金额
				if(null ==cardPrice|| ""==cardPrice){
					cardPrice=0;
				}
			}
			
			 
			var postagePrice=$(".sumBox .freight").text();
			if (postagePrice == "免运费") {
				postagePrice = 0.00;
			}
			
			 //实付金额 = 总金额 + 运费  -活动优惠价格 -优惠券金额  - 单品红包金额 -满减金额
			var totalPrice=parseFloat(orgPrice)+parseFloat(freight)- parseFloat(activityPrice)-parseFloat(couponPrice)-parseFloat(singleCouponPrice)-parseFloat(fullCut);
			
			npayRealMoney = totalPrice;
			
			if(usecard=="useGiftCard" && checkGiftBox){
				//实付金额 = 总金额  - 礼品卡使用金额
				totalPrice=parseFloat(orgPrice)-parseFloat(freight)- parseFloat(activityPrice)-parseFloat(fullCut);
				if(parseFloat(cardPrice) >=totalPrice){
					totalPrice=0.0;
					$("[name='payType']").removeAttr("checked");//取消选中支付方式
					$('#payywtId').removeClass('on');
					$('#payweiixnId').removeClass('on');
					$('#payzfbId').removeClass('on');
				}else{
					totalPrice=totalPrice-parseFloat(cardPrice);
				} 
			}
			var deductionMoney = 0;
			$('.goodsBox .calloff').each(function(i){
				if($(this).html() == "取消"){
					deductionMoney = parseFloat($(this).attr("data-deductible-amount"));
				}
			})
			
			totalPrice = totalPrice - deductionMoney;

			var integralCheckId = $("#integralCheckId").attr('checked');
			var calculateMoney = parseFloat($("#integralLineId").attr("data-calculate-money"));
			if(goldDeductSpecId > 0 && integralCheckId && calculateMoney != null 
					&& calculateMoney != "" && parseFloat(calculateMoney) > 0){ //使用普通金币抵扣
				totalPrice = totalPrice - calculateMoney;
			}
			
			if(totalPrice<=0){
				totalPrice=0;
			}else{
				totalPrice=totalPrice.toFixed(2);
			}
			lastPayMoney = totalPrice;
			$(".newBuy em").html(totalPrice);
		}
		
		
		//跳转到选择优惠券
		$("#couponLineId .monright i").click(function(){
			var _this=$(this).parents('#couponLineId').find('label span');
//			    var bool=$(_this).hasClass("on");
//				if(bool){
				 //parseFloat($(".sumBox .price").text());  
				if(null !=ttp && ""!=ttp && parseFloat(ttp) > 0){
					window.location.href=mainServer+"/weixin/customercoupon/toChooseCouponInfo?returnType="+returnType+"&totalPrice="+ttp;
				}else{
					showvaguealert('商品价格异常');
					return false;
				}
//				}else{
//					showvaguealert('请选择优惠券后再操作');
//					return false;
//				} 
			
		});
		
		//普通金币抵扣规则
		$("#integralLineId .warning").click(function(){
			$(".rule-wrap").show();
		});
		
		//普通金币抵扣规则关闭
		$(".rule-wrap .close").click(function(){
			$(".rule-wrap").hide();
		});
		
		//跳转到选择单品红包
		$("#singleCouponLineId .monright i").click(function(){
//				var _this=$(this).parents('#singleCouponLineId').find('label span');
//			    var bool=$(_this).hasClass("on");
//				if(bool){
				if(null !=ttp && ""!=ttp && parseFloat(ttp) > 0){
					window.location.href=mainServer+"/weixin/customercoupon/toChooseSingleCoupon?returnType="+returnType+"&totalPrice="+ttp+"&productId="+ifCouponProIds+"&specId="+ifCouponSpecIds;
				}else{
					showvaguealert('商品价格异常');
					return false;
				}
//				}else{
//					showvaguealert('请选择红包后再操作');
//					return false;
//				} 
			
		});
		
		
		//选择地址
		$(".has-address").click(function(){
			var receiverId=$(this).find(".consignee").attr("lang");
			if(undefined==receiverId){
				receiverId="";
			}
			
			window.location.href=mainServer+"/weixin/addressmanager/toChooseAddress?returnType="+returnType+"&receiverId="+receiverId;
		});
		
		//选择支付方式
		$(".payType").click(function(event){ 	
			var ckSelect = $(this).find("input").is(':checked');
			if(ckSelect){
				var checkGiftBox = $("#cardRdo").is(':checked');
				var usecard=$(".gift-card").find(".giftcards").attr("lang");
				var cardPrice=$(".gift-card em").text();     //礼品卡使用金额
				if(checkGiftBox && usecard == "useGiftCard" && parseFloat(cardPrice) >= parseFloat(npayRealMoney)){ //选择了礼品卡，判断礼品卡金额是否足够付完
					$('#cardRdo').prop('checked',false);
					$('#giftCardSpanId').removeClass('on');
					totalCount();
				}
			}
		})
		
		//选择微信支付
		$(".paybox").click(function(event){ 	
			event.preventDefault();
			var hasOn = $('#payweiixnId').hasClass('on');
			
			if(hasOn){
				$('#payweiixnId').removeClass('on');
				$("#card7").removeAttr("checked");
			}else{
				$('#payweiixnId').addClass('on');  
				$('#payzfbId').removeClass('on');
				$('#payywtId').removeClass('on');
				$("#card7").attr("checked", "true");
				$("#card8").removeAttr("checked");
				$("#card9").removeAttr("checked");
			}
		});
		//选择支付宝
		$(".payzfbbox").click(function(event){ 
			event.preventDefault();
			var hasOn = $('#payzfbId').hasClass('on');
			
			if(hasOn){
				$('#payzfbId').removeClass('on');
				$("#card8").removeAttr("checked");
			}else{ 
				$('#payzfbId').addClass('on');
				$('#payweiixnId').removeClass('on');
				$('#payywtId').removeClass('on');
				$("#card8").attr("checked", "true");
				$("#card7").removeAttr("checked");
				$("#card9").removeAttr("checked");
			}
		})
		//选择一网通
		$(".payywtbox").click(function(event){ 	
			event.preventDefault();
			var hasOn = $('#payywtId').hasClass('on');
			if(hasOn){
				$('#payywtId').removeClass('on');
				$("#card9").removeAttr("checked");
			}else{ 
				$('#payywtId').addClass('on');
				$('#payweiixnId').removeClass('on');
				$('#payzfbId').removeClass('on');
				$("#card9").attr("checked", "true");
				$("#card7").removeAttr("checked");
				$("#card8").removeAttr("checked");
			}
			
		})
		
		//点击选择/取消礼品卡 
		$('.gift-card .giftcards').click(function(){
			var checkGiftBox = $("#cardRdo").is(':checked');
			if(checkGiftBox){
				$('#giftCardSpanId').addClass('on');
				$('#cardRdo').prop('checked',true);
				if(returnType != 4){
					$('#card1').prop('checked',false);
					$('#card3').prop('checked',false);
//					$("#couponLineId .monright em").html('0.00');
//					$("#singleCouponLineId .monright em").html('0.00');
					$("#couponSpanId").removeClass("on");
					$("#singleCouponSpanId").removeClass("on");
//					var couponId=$("#couponLineId em").attr("data-couponinfo");      //优惠券金额
//					if(undefined !=couponId && null != couponId){
//						$("#couponLineId em").attr("data-couponinfo", "");
//					}
//					var usesinglecouponId=$("#singleCouponLineId em").attr("data-singlecoupon");      //单品红包
//					if(undefined !=usesinglecouponId && null != usesinglecouponId){
//						$("#singleCouponLineId em").attr("data-singlecoupon", "");
//					}
				}
				if(!checkGiftPwd){
					//每次打开输入密码框清空样式
					$('.dia-pay-pass').show();
					$(".blockWrapper .block").find("i").removeAttr("style");   
					$(".m-passwordInput").find("input").focus();
					$('.mask').show();
				}
				
				chooseOrderDistinctByType('useGiftCard');
			}else{
				$('#cardRdo').prop('checked',false);
				$('#giftCardSpanId').removeClass('on');
			}
			
			totalCount();
			
		});
		
		
		//选择优惠券
		$('#couponLineId .coupons').click(function(){
			var isEmpty = $(this).parent('#couponLineId').hasClass('empty');
			if (isEmpty) {
				return;
			}
			var hasOn = $('#couponSpanId').hasClass('on');
			if(hasOn){ //取消
//				$("#couponLineId .monright em").html('0.00');
				$('#couponSpanId').removeClass('on');
				$('#card1').prop('checked',false);
				chooseOrderDistinctByType('useCoupon');
				
//				var couponId=$("#couponLineId em").attr("data-couponinfo");      //优惠券金额
//				if(undefined !=couponId && null != couponId){
//					$("#couponLineId em").attr("data-couponinfo", "");
//				}
			}else{	//选择
				$('#couponSpanId').addClass('on');
				$('#card1').prop('checked',true);
			}
			hasOn = $('#giftCardSpanId').hasClass('on');
			if(hasOn){
				$('#giftCardSpanId').removeClass('on');
				$('#cardRdo').prop('checked',false);
//				$(".gift-card").find(".giftcards").attr("lang","");
			}
			totalCount();
		});
		
		//选择单品红包
		$('#singleCouponLineId .coupons').click(function(){
			var isEmpty = $(this).parent('#singleCouponLineId').hasClass('empty');
			if (isEmpty) {
				return;
			}
			var hasOn = $('#singleCouponSpanId').hasClass('on');
			if(hasOn){
//				$("#singleCouponLineId .monright em").html('0.00');
				$('#singleCouponSpanId').removeClass('on');
				$('#card3').prop('checked',false);
				chooseOrderDistinctByType('useSingleCoupon');
//				var usesinglecouponId=$("#singleCouponLineId em").attr("data-singlecoupon");      //单品红包
//				if(undefined !=usesinglecouponId && null != usesinglecouponId){
//					$("#singleCouponLineId em").attr("data-singlecoupon", "");
//				}
			}else{
				$('#singleCouponSpanId').addClass('on');
				$('#card3').prop('checked',true);
			}
			hasOn = $('#giftCardSpanId').hasClass('on');
			if(hasOn){
				$('#giftCardSpanId').removeClass('on');
				$('#cardRdo').prop('checked',false);
//				$(".gift-card").find(".giftcards").attr("lang","");
			}
			totalCount();
		});
		function chooseOrderDistinctByType(flag){
			$.ajax({
			   type: 'POST',
			   url: mainServer + "/weixin/order/chooseOrderDistinctByType",
			   async: false,
			   data: {'flag':flag},
			   dataType:'json',  
			   success:function(data) { 
			        if(data.result =='succ' ){  
			     //   	$("#returnKey").val(data.returnKey); 
			        /* }else{
			        	showvaguealert('无法找到指定参数');
						return false; */
			        }  
			     },  
			     error : function() {  
			    	 showvaguealert('清空对应参数异常');
					 return false;
			     } 
		    });
	}
			
	//新增地址
	function addNewAddress(){
		window.location.href=mainServer+"/weixin/addressmanager/toAddReceiverFromOrder?returnType="+returnType+"&receiverId=";
	}
	
	
	//支付订单
	var orderCode="";
	var orderId = 0;
	var payData="";
	
	function  makeSure(){
		
		var messageInfo = $(".mjiabox input").val(); 
		if(messageInfo != null && messageInfo != "" && messageInfo.length > 25){
			showvaguealert('留言仅限25个字');
			return;
		}    
		
		if ($('.go-shop').hasClass('active')) {
			showvaguealert('请勾选《挥货服务协议》');
			return;
		}
		
    	var receiverId=$(".consignee").attr("lang");
    	if(undefined==receiverId){
			receiverId="";
		}
    	
    	var receiverName=$(".takenamebox label").text();
    	var takephone=$(".takenamebox .takephone").text();
    	var takeaddress=$(".takeaddress span").text();
		
    	if(null==receiverId || ""==receiverId){
			showvaguealert('请选择收货地址');
			return;
		}
		
		if(null==receiverName || ""==receiverName){
			showvaguealert('收货人不能为空');
			return;
		}
		
		if(null==takephone || ""==takephone){
			showvaguealert('收货人手机不能为空');
			return;
		}
		
		if(null==takeaddress || ""==takeaddress){
			showvaguealert('收货地址不能为空');
			return;
		}
		
	
		//选择支付宝微信
		var payType = 0;
		$("[name='payType']").each(function(){
			 if($(this).attr("checked")){
				 payType = $(this).val()
				 
			 }   
		})
		if(!payType){
			//判断礼品卡是否已足够付完所有金额
			var usecard=$(".gift-card").find(".giftcards").attr("lang");
			var cardPrice=$(".gift-card em").text();     //礼品卡使用金额
			var checkGiftBox = $("#cardRdo").is(':checked');
			if(checkGiftBox && usecard=="useGiftCard" && parseFloat(cardPrice) >= parseFloat(npayRealMoney)){
				payType = 4;
			}else{
				showvaguealert('请选择支付方式');
				return;
			}
		}
		if(payType == 1){
	    	if(!isCheckWeiXin("请在微信端支付，或选择支付宝支付")){
				return;
			}
		}
		
		$(".bkbg").show();
		$(".shepassdboxs").show();
	}
	
	function closeAlert(){
		$(".bkbg").hide();
		$(".shepassdboxs").hide();
		$("#surePayId").attr("href", "javascript:submitForm();");
	}
	
	//初始普通金币抵扣活动信息
	function initGoldDeductInfo(){
		if(myIntegral < minLimitIntegral){
			$("#integralLineId").addClass("empty");
			$("#integralLineId .monright").html("共" + myIntegral + "金币，满" + minLimitIntegral + "金币可用");
		}else {
			if(myIntegral >= maxLimitIntegral){
				var calculateMoney = parseFloat((maxLimitIntegral*integralDeductionRate).toFixed(2));
				$("#integralLineId .monright").html("当前可用" + maxLimitIntegral + "金币，可抵扣" + calculateMoney + "元");
			}else{
				var calculateMoney = parseFloat((myIntegral*integralDeductionRate).toFixed(2));
				$("#integralLineId .monright").html("当前可用" + myIntegral + "金币，可抵扣" + calculateMoney + "元");
			}
		}
		if(goldDeductSpecId > 0){
			$("#integralLineId").show();
		}
	}
	
	function submitForm(){
		    
		var messageInfo = $(".mjiabox input").val(); 
		  
		
    	var receiverId=$(".consignee").attr("lang");
    	
    	
    	var receiverName=$(".takenamebox label").text();
    	var takephone=$(".takenamebox .takephone").text();
    	var takeaddress=$(".takeaddress span").text();
		
    	
		
		var producrIds=[];
		var specIds=[];
		var amounts=[];
		$(".orgoodslist").find("input[id^='productId_']").each(function(){
			producrIds.push($(this).val());
		});
		
		var specId="";
		$(".orgoodslist").find("input[id^='specId_']").each(function(){
			specIds.push($(this).val());
			specId=$(this).val();
		});
		
		var amount="";
		$(".orgoodslist").find("input[id^='amount_']").each(function(){
			amounts.push($(this).val());
			amount=$(this).val();
		});
		
		//选择支付宝微信
		var payType = 0;
		$("[name='payType']").each(function(){
			 if($(this).attr("checked")){
				 payType = $(this).val();
			 }   
		})
		
		var giftcardId=$(".gift-card").find(".giftcards").attr("lang");
		var cardPrice=$(".gift-card em").text();     //礼品卡使用金额
		var checkGiftBox = $("#cardRdo").is(':checked');
		if(giftcardId=="useGiftCard"){
			if(checkGiftBox){
				//判断礼品卡是否已足够付完所有金额
				if(parseFloat(cardPrice) >= parseFloat(npayRealMoney)){
					payType = 4;
				}
			}else{ //没选中礼品卡，表示没使用
				giftcardId = "";
			}
		}
		
		if(usecard=="useGiftCard" && checkGiftBox){
			//实付金额 = 总金额  - 礼品卡使用金额
			totalPrice=parseFloat(orgPrice)-parseFloat(freight)- parseFloat(activityPrice)-parseFloat(fullCut);
			if(parseFloat(cardPrice) >=totalPrice){
				totalPrice=0.0;
				$("[name='payType']").removeAttr("checked");//取消选中支付方式
				$('#payywtId').removeClass('on');
				$('#payweiixnId').removeClass('on');
				$('#payzfbId').removeClass('on');
			}else{
				totalPrice=totalPrice-parseFloat(cardPrice);
			} 
		}
		
		var redeemSpecId = "";
		$('.goodsBox .calloff').each(function(i){
			if($(this).html() == "取消"){
				redeemSpecId = $(this).attr("data-id");
			}
		})
		
		var integralCheckId = $("#integralCheckId").attr('checked');
		var calculateMoney = $("#integralLineId").attr("data-calculate-money");
		var integralGoldSpecId = "";
		if(integralCheckId && calculateMoney != null 
				&& calculateMoney != "" && parseFloat(calculateMoney) > 0){ //使用普通金币抵扣
			integralGoldSpecId = goldDeductSpecId+"";
		}
		
		
		$('.go-shop').addClass('disable').removeAttr('onclick');
		$("#surePayId").attr("href", "javascript:void(0);");

		if(returnType == 4){	//团购
			$.ajax({
				url:mainServer+'/weixin/payment/payByGroup',
				data:{
					"productIds":producrIds.toString(),
					"specIds":specIds.toString(),
					"amounts":amounts.toString(),
					"message_info":messageInfo,
					"giftcardId":giftcardId,
					"payType":payType,
					"receiverId":receiverId,
					"receivername":receiverName,
					"receivermobile":takephone,
					"receiveraddress":takeaddress,
					"specialId":specialId,
					"userGroupId":userGroupId,
					"token":token,
					"orderSource":orderSource
				},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.result=='succ'){
						//禁用去付款按钮，取消事件绑定
						if(data.notify=='succ'){   //支付金额为0，直接跳转,不走微信或支付宝支付
							window.location.href=mainServer+"/weixin/order/payResult?orderCode="+data.orderCode;  //从支付完成进入订单详情 
						}else{
							if(payType=='1'){
								var record = data;
								orderCode = record.tradeNo;
								orderId = record.orderId;
								payData = data.payData;
								wexinBridge();
							}else if(payType=='2'){
								window.location.href=mainServer+"/weixin/payment/toAliPaySkip?orderCode="+data.orderCode;
							}else if(payType=='3'){
								oneCardPay(data.orderCode);
							}else{
								showvaguealert("未知的支付方式");
							}
						}
					}else if(data.result=='notexist'){	
						showvaguealert(data.msg);
					}else{
						showvaguealert(data.msg);
					}
				},
				failure:function(data){
					showvaguealert('出错了');
				}
			});

		}else {
			var couponId= "";      //优惠券ID
			var checkCouponBox = $("#card1").is(':checked');
			if(checkCouponBox){
				couponId = $("#couponLineId em").attr("data-couponinfo")
				if(undefined==couponId){
					couponId="";
				}
			}
			
			var checkSingleCouponBox = $("#card3").is(':checked');
			var usesinglecouponId = "";      //单品红包
			if(checkSingleCouponBox){
				usesinglecouponId=$("#singleCouponLineId em").attr("data-singlecoupon")
				if(undefined==usesinglecouponId){
					usesinglecouponId="";
				}
			}
			
			$.ajax({
				url:mainServer+'/weixin/payment/pay',
				data:{
					"productIds":producrIds.toString(),
					"specIds":specIds.toString(),
					"amounts":amounts.toString(),
					"message_info":messageInfo,
					"extension_type":extensionType,
					"giftcardId":giftcardId,
					"usesinglecouponId":usesinglecouponId,
					"useCouponId":couponId,
					"payType":payType,
					"receiverId":receiverId,
					"receivername":receiverName,
					"receivermobile":takephone,
					"receiveraddress":takeaddress,
					"cartIds":'',
					"goldDeductSpecIds":integralGoldSpecId,
					"redeemSpecIds":redeemSpecId,
					"token":token,
					"orderSource":orderSource
				},
				type:'post',
				dataType:'json',
				success:function(data){
		
					if(data.result=='succ'){
						if(data.notify=='succ'){   //支付金额为0，直接跳转 ,不走微信/支付宝/一网通支付
							window.location.href=mainServer+"/weixin/order/payResult?orderCode="+data.orderCode;  //从支付完成进入订单详情 
						}else{
							if(payType=='1'){
								var record = data;
								orderCode = record.tradeNo;
								orderId = record.orderId;
								payData = data.payData;
								wexinBridge();
							}else if(payType=='2'){
								window.location.href=mainServer+"/weixin/payment/toAliPaySkip?orderCode="+data.orderCode;
							}else if(payType=='3'){
								oneCardPay(data.orderCode);
							}else{
								showvaguealert("未知的支付方式");
							}
						}
					}else{
						showvaguealert(data.msg);
						
					}
				},
				failure:function(data){
					showvaguealert('出错了');
				}
			});
		}
    }
		    
	//**********************************************    不要动    *********************************************************
	function wexinBridge(){
		if (typeof WeixinJSBridge == "undefined"){
		   if( document.addEventListener ){
		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		   }else if (document.attachEvent){
		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			   onBridgeReady();
		   }
		}else{
		   onBridgeReady();
		} 
	}
	
	function onBridgeReady(){
	   WeixinJSBridge.invoke(
	       'getBrandWCPayRequest',payData,
	       function(res){ 
	    	   // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	    	   if(res.err_msg == "get_brand_wcpay_request:ok") {
	    		   window.location.href=mainServer+"/weixin/order/payResult?orderCode="+orderCode;  //从支付完成进入订单详情
	           } else if (res.err_msg == "get_brand_wcpay_request:cancel"){
	        	   showvaguealert('您已取消支付');
	        	   setTimeout(function(){
	        		   //订单去掉支付，进入订单倒计时页面
	        		   window.location.href=mainServer+"/weixin/order/toPayOrderDetail?type=1&orderId="+orderId+"&orderCode="+orderCode;
	        	   },500);
	           } else {
	    		   showvaguealert('出错了');
	           } 
	       }
	   ); 
	}
	
	function oneCardPay(orderNo){
		$.ajax({
			url:mainServer+'/weixin/payment/oneCardPay',
			data:{
				"orderCode":orderNo
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.result=='succ'){
					sendFormRequest(JSON.stringify(data.oneCardPack),data.payApi);
				}
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
	}
	
	//发送表单数据
	function sendFormRequest(xmlRequest,payApi){
		var form = document.createElement("form");
		document.body.appendChild(form);
		var param = document.createElement("input");
		param.type = "hidden";
		param.value = xmlRequest;
		param.name = "jsonRequestData";
		form.appendChild(param);
		form.action = payApi;
		form.method = "POST";
		form.accept_charset="UTF-8";
		form.submit(); 
	}
    //********************************************************************************************************************************
	
	$(function(){
		
		var url="";
		if(1==returnType){
			url=mainServer+"/weixin/product/towxgoodsdetails?productId="+productId;
		}else if(5==returnType){
			url=mainServer+"/weixin/product/toLimitGoodsdetails?productId="+productId+"&specialId="+specialId;
		}else if(4==returnType){
			url=mainServer+"/weixin/product/toGroupGoodsdetails?productId="+product_id+"&specialId="+specialId;
		}else if(6==returnType){ //团购的单独购买
			url=mainServer+"/weixin/product/toGroupGoodsdetails?productId="+productId+"&specialId="+tzhuantiId;
		}
		
		
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
        	window.history.pushState(state, "title","#");  
	    }
	    
	});