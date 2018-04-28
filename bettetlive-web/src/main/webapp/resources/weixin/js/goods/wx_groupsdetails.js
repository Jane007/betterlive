//初始化swiper
var mySwiper = new Swiper(".swiper-container", {
	loop : true,
	pagination : '.swiper-pagination',
	autoplayDisableOnInteraction : false,
	autoplay : '3000'
});


var specLen = $('.installBox ul li').length; // 商品规格数量
var limitNum = -1; // 限量
var hasBuy = 0; // 已经购买的份数
var restCopy = 100; // 剩余购买数量
var carNums = 0;
var carCanAdd = 0; // 购物车还可以添加多少份
var packageDesc = ''; // 套餐说明
var promoitionName = ''; // 活动名称
var fullMoney = ''; // 满多少钱
var cutMoney = ''; // 减多少钱


$(function() {
	
	$(".initloading").show();
	setTimeout(function() {
		$(".initloading").hide();
	}, 800);

	queryLikeProducts();
	var pem = $(".protitle").find("em").html();
	if (pem != null && pem != "" && pem.length > 0) {
		$(".protitle").find("h3").css("width", "5.5rem");
	} else {
		$(".protitle").find("h3").css("width", "auto");
	}

	// 底部tab切换
	$('.footer li').click(function() {
		$(this).addClass('active').siblings('li').removeClass('active');
	});
	// 滚动一定的距离出现返回顶部
	$(window).bind("scroll", function() {
		var top = $(this).scrollTop(); // 当前窗口的滚动距离
		if (top >= 300) {
			$('.backTop').show();
		} else {
			$('.backTop').hide();
		}
	});
	// 点击返回顶部
	$('.backTop').on('click', function() {
		$(document).scrollTop('0');
	});
	// 弹出规格窗口
	$('.etalon').click(function() {
		$('#specDivLine').addClass('active');
		$('.mask').show();
	});
	// 弹出产品参数窗口
	$('#cpcsLineId').click(function() {
		$('#cpccId').addClass('active');
		$('.mask').show();
	});
	// 点击隐藏规格窗口
	$('.outBox,.mask').click(function() {
		hideObj();
		isActive();
		// if (specLen > 1) {
		// is_select = 0;
		// }
	});

	function hideObj() {
		$('.standardBox').removeClass('active');
		$('.mask').hide();
	}

	$('#cartsId')
			.click(
					function() {
						window.location.href = mainServer+'/weixin/shoppingcart/toshoppingcar';
					});

	// ***********************规格窗口*********************************
	// 价格
	// var oprice=$('.productPrice span').text();
	// $('.normsPrice span').text(oprice)

	// 选择规格
	$('.installBox li').click(
					function() {
						$('.calGoodNums').val(1);
						is_select = 1;
						$(this).addClass('active').siblings('li').removeClass(
								'active');
						var standnumber = $(this).html();
						var standprice = $(this).attr("value");
						var standspecId = $(this).attr("id");
						var specValue = $(this).attr("data-orgprice");
						stockCopy = $(this).attr("data-copy");
						limitNum = $(this).attr("data-limit");
						hasBuy = $(this).attr("data-hasBuy");
						restCopy = $(this).attr("data-restCopy");
						carNums = $(this).attr("data-carNums");
						carCanAdd = $(this).attr("data-carCanAdd");
						packageDesc = $(this).attr("data-package");
						promoitionName = $(this).attr("data-promoitionName");
						fullMoney = $(this).attr("data-fullMoney");
						cutMoney = $(this).attr("data-cutMoney");
						if (limitNum != null && limitNum != ''
								&& parseInt(limitNum) >= 1) {
							$('.normsChoose').html(
									'已选择：' + standnumber + '(限购' + limitNum
											+ '件)');
						} else {
							$('.normsChoose').html('已选择：' + standnumber);
						}
						$('.normsPrice span').text(standprice);
						if (specValue != null && specValue != "") {
							$('.normsPrice strong').text(specValue);
						} else {
							$('.normsPrice strong').text("");
						}
						$("#productSpecId").val(standspecId);
						$("#buyAmount").val($('.calGoodNums').val());

						// 满减操作
						var price = standprice.substring(1, standprice.length);
						var amount = $('.calGoodNums').val();
						// 计算满减
						calculateFullCut(price, amount, fullMoney, cutMoney)

						$('.proPic img').attr("src",
								$("#spec_img" + standspecId).val());
						if (stockCopy != null && stockCopy != '') {
							if (parseInt(stockCopy) >= 1) {
								$("#stockCopy").html("库存" + stockCopy + "件");
								$("#buyForm").show();
							} else {
								$("#stockCopy").html("已秒完");
								// $("#buyForm").hide();
							}
						} else {
							$("#stockCopy").html("");
						}
						if (packageDesc != null && packageDesc != '') {
							$("#packageDesc").html(
									"<label>规格说明：</label>" + packageDesc);
						} else {
							$("#packageDesc").html("");
						}
						if (promoitionName != null && promoitionName != '') {
							$('#activity').addClass("activity");
							$('#activity').html(
									"<p><span>满减</span><em>" + promoitionName
											+ "</em></p>");
							$('#fullCut').addClass("activity");
							$('#fullCut').html(
									"<p><span>满减</span><i class='subTitle'>"
											+ promoitionName + "</i></p>");
						} else {
							$('#activity').html("");
							$('#activity').removeClass("activity");
							$('#fullCut').html("");
							$('#fullCut').removeClass("activity");
							$('#fullCut').hide();
						}
					});

	// 选择规格之后才可以点击数量
	var outli = $('.installBox li');
	// 增加数量
	$('.calAdd').click(
					function() {
						if (outli.hasClass('active')) {
							var $this = $(this);
							var $numval = parseInt($(this).prev('input.calGoodNums').val());
							var maxBuy = limitNum;
							var buyAmount = $("#buyAmount").val();
							if (stockCopy != null && stockCopy != '') {
								if (parseInt(stockCopy) <= 0) {
									showvaguealert("此产品规格暂无库存");
									return false;
								}
							}
							if (parseInt(buyAmount) >= parseInt(stockCopy)) {
								showvaguealert("库存仅剩" + stockCopy + "件");
								return false;
							}

							if (parseInt(limitNum) == 0) {
								showvaguealert("此产品限购不可购买");
								return false;
							}
							if (parseInt(limitNum) >= 1) {

								if (parseInt(restCopy) <= 0) {// 剩余购买数量
									showvaguealert("此产品限购" + maxBuy + "份");
									return false;
								}
								if(parseInt(hasBuy)>= parseInt(limitNum)){
					    			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
					    			return false;
				    			}
								if (parseInt(hasBuy) > 0
										&& parseInt(buyAmount) > parseInt(restCopy)) {
									showvaguealert("您已经购买了" + hasBuy + "件，可再购买"
											+ restCopy + "件");
									return false;
								}
								if (parseInt(buyAmount) > parseInt(restCopy)) {
									showvaguealert("此产品限购" + maxBuy + "份");
									return false;
								}

								if (parseInt(buyAmount) >= 1) {
									if (parseInt(maxBuy) - parseInt(hasBuy) <= parseInt(buyAmount)) {
										showvaguealert("此产品限购" + maxBuy
												+ "件,最多可再购买" + restCopy + "件");
										return false;
									}

								}
							}
							$(this).siblings('.calGoodNums').val($numval + 1);
							$("#buyAmount").val($numval + 1);
							var amount = $numval + 1;
							var standprice = $('.normsPrice span').text();
							var price = standprice.substring(1,
									standprice.length);
							calculateFullCut(price, amount, fullMoney, cutMoney)

						} else {
							showvaguealert('请先选择规格');
						}
					});

	// 减少数量
	$('.calCut').click(function() {
		if (outli.hasClass('active')) {
			var $numval = parseInt($(this).next('input').val());
			if ($numval <= 2) {
				$numval = 2;
			}
			$(this).siblings('.calGoodNums').val($numval - 1);
			$("#buyAmount").val($numval - 1)
			var amount = $numval - 1;
			var standprice = $('.normsPrice span').text();
			var price = standprice.substring(1, standprice.length);
			calculateFullCut(price, amount, fullMoney, cutMoney)
		} else {
			showvaguealert('请先选择规格');
		}
	});

	initSepc();

});

// 判断是否选择有选择规格，如果选择了规格，则更新规格数量的显示
function isActive() {
	var oli = $('.installBox li');
	if (oli.hasClass('active')) {
		is_select = 1;
		var oguige = ($('.normsChoose').text()).replace('已选择：', '');
		var oval = $('.calGoodNums').val();
		$('.etalon span').text(oguige);
		$('.etalon i').text('x' + oval);
		$("#buyAmount").val($('.calGoodNums').val());
		var price = $('.normsPrice span').text()
		// 满减
		if ($('#fullCut').html() != '') {
			$('#fullCut').show();
		} else {
			$('#fullCut').css("display", "none");
		}

	}
}

function calculateFullCut(price, amount, fullMoney, cutMoney) {
	price = parseFloat(price);
	amount = parseFloat(amount);
	var total = parseFloat(price * amount);
	if (fullMoney != null && fullMoney != '') {
		fullMoney = parseFloat(fullMoney);
		if (total >= fullMoney) {
			if (cutMoney != null && cutMoney != '') {
				cutMoney = parseFloat(cutMoney)
				total = total - cutMoney;
				total = total.toFixed(2);
				$('.actTotal span').text("总价" + total);
				$('.actTotal em').text("(满" + fullMoney + "减" + cutMoney + ")");
			}

		}
	} else {
		$('.actTotal span').text("");
		$('.actTotal em').text("");
	}
}

// 默认选择第一个规格，初始化价格及已选择
function initSepc() {
	// if (specLen == 1) {
	$('.installBox li:first').click();
	// 更新规格数量显示
	isActive();
	// }
}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 20000);
}

var is_select = 0; // 是否选择了规格
var buyMS = restCopy; // 马上购买剩余的购买数量

function toAddBuy() {

	
	if (null == customerId || 0 == customerId) {
		window.location.href = mainServer + "/weixin/tologin?backUrl=" + back;
		return false;
	}

	if (null == mobile || "" == mobile) {
		window.location.href = mainServer
				+ "/weixin/usercenter/toBoundPhone?backUrl=" + back;
		return false;
	}
	$("#buyForm").attr("action",mainServer+ "/weixin/order/addBuyOrder?returnType=6&tzhuantiId="+specialId);

	// 是否有一个规格被选中
	var is_spec_active = $('.installBox ul li').hasClass('active');
	if ((is_select == 0) && !is_spec_active) { // 没有选规格
		if ($('#specDivLine').hasClass('active')) { // 如果已经弹出选择规格框，则提示请选择规格
			showvaguealert('请选择商品规格！');
		} else {
			$('.etalon').click(); // 弹出选择规格
		}
		return;
	} else if ((is_select == 0) && is_spec_active) {
		if (!$('#specDivLine').hasClass('active')) { // 如果已经弹出选择规格框，则提示请选择规格
			$('.etalon').click(); // 弹出选择规格
			return;
		}
	}

	var productId = $("#productId").val();
	if ("" == productId || null == productId) {
		showvaguealert('请重新选择商品！');
		return false;
	}

	var productSpecId = $("#productSpecId").val();
	if (null == productSpecId || '' == productSpecId) {
		showvaguealert('请选择商品规格！');
		return false;
	}

	var buyAmount = $("#buyAmount").val();
	if ("" == buyAmount || null == buyAmount) {
		showvaguealert('请选择购买数量！');
		return false;
	}
	if (stockCopy != null && stockCopy != '') {
		if (parseInt(stockCopy) <= 0) {
			showvaguealert("此产品规格暂无库存");
			return false;
		}
	}
	if (parseInt(buyAmount) > parseInt(stockCopy)) {
		showvaguealert("库存仅剩" + stockCopy + "件");
		return false;
	}
	if (parseInt(limitNum) > 0) {// 限购才走这些判断
		if (parseInt(buyAmount) > parseInt(buyMS)) {
			showvaguealert("您已经购买了" + hasBuy + "件，不可再购买");
			return false;
		}
		if(parseInt(hasBuy)>= parseInt(limitNum)){
			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
			return false;
		}
		if (parseInt(buyAmount) > parseInt(limitNum)) {
			showvaguealert("此商品限购" + limitNum + "件");
			return false;
		}
	}
	is_select = 0;
	$("#buyForm").submit();
}

function toAddGroupBuy() {
	
	if (null == customerId || 0 == customerId) {
		window.location.href = mainServer + "/weixin/tologin?backUrl=" + link;
		return false;
	}

	if (null == mobile || "" == mobile) {
		window.location.href = mainServer
				+ "/weixin/usercenter/toBoundPhone?backUrl=" + link;
		return false;
	}

	$("#buyForm").attr("action", mainServer + "/weixin/order/addOrderByGroup");

	
	if (specStatus != 1 || specEnd <= currentTime) {
		showvaguealert("活动已结束");
		return;
	} else if (specStart > currentTime) {
		showvaguealert("活动还没开始哦~");
		return;
	}

	
	if (parseFloat(joinCopy) >= parseFloat(groupCopy)) {
		showvaguealert("开团数量已达上限~");
		return;
	}
	// 是否有一个规格被选中
	var is_spec_active = $('.installBox ul li').hasClass('active');
	if ((is_select == 0) && !is_spec_active) { // 没有选规格
		if ($('#specDivLine').hasClass('active')) { // 如果已经弹出选择规格框，则提示请选择规格
			showvaguealert('请选择商品规格！');
		} else {
			$('.etalon').click(); // 弹出选择规格
		}
		return;
	} else if ((is_select == 0) && is_spec_active) {
		if (!$('#specDivLine').hasClass('active')) { // 如果已经弹出选择规格框，则提示请选择规格
			$('.etalon').click(); // 弹出选择规格
			return;
		}
	}

	var productId = $("#productId").val();
	if ("" == productId || null == productId) {
		showvaguealert('请重新选择商品！');
		return false;
	}

	var productSpecId = $("#productSpecId").val();
	if (null == productSpecId || '' == productSpecId) {
		showvaguealert('请选择商品规格！');
		return false;
	}

	var buyAmount = $("#buyAmount").val();
	if ("" == buyAmount || null == buyAmount) {
		showvaguealert('请选择购买数量！');
		return false;
	}
	if (stockCopy != null && stockCopy != '') {
		if (parseInt(stockCopy) <= 0) {
			showvaguealert("此产品规格暂无库存");
			return false;
		}
	}
	if (parseInt(buyAmount) > parseInt(stockCopy)) {
		showvaguealert("库存仅剩" + stockCopy + "件");
		return false;
	}
	if (parseInt(limitNum) > 0) {// 限购才走这些判断
		if (parseInt(buyAmount) > parseInt(buyMS)) {
			showvaguealert("您已经购买了" + hasBuy + "件，不可再购买");
			return false;
		}
		if(parseInt(hasBuy)>= parseInt(limitNum)){
			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
			return false;
		}
		if (parseInt(buyAmount) > parseInt(limitNum)) {
			showvaguealert("此商品限购" + limitNum + "件");
			return false;
		}
	}
	is_select = 0;
	$("#buyForm").submit();
}



//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},2000);
}

function addOrCancelCollect(flag, collectionId){
	if(null==customerId || 0==customerId){
		window.location.href = mainServer+"/weixin/tologin?backUrl="+back;
		return false;
	}
	
 	if((flag != 0 && flag != 1) || isNaN(flag)){
 		showvaguealert("出现异常");
 		return;
 	}
 	
 	var url = mainServer+"/weixin/collection/addCollection";
 	var data={
 		"collectionType":1,
 		"objId":objId
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
		$(".shou").attr("onclick", "void(0);");

		$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$(".shou").removeClass("shoucan");
					$(".shou").attr("onclick", "addOrCancelCollect(0, 0)");
					showvaguealert("已取消收藏");
				}else{
					$(".shou").attr("onclick", "addOrCancelCollect(1, "+data.data+")");
					$(".shou").addClass("shoucan");
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
								+'		<span id="name'+productVo.product_id+'">' + productVo.product_name + '</span>'
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
								+'</div>';
					$(".tuijianbox").append(showHtml);
					if(showLabel != null && showLabel != "" && showLabel.length > 0){
						$("#name"+productVo.product_id).css("width","2.6rem");
					}else{  
						$("#name"+productVo.product_id).css("width","4rem");
					}	
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


function shareProduct(){
		$.ajax({                                       
		url: mainServer+"/weixin/share/addShare",
		data:{
			"shareType":1,
			"objId":objId
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

function toJoinGroup(userGroupId, productId, specialId){
	window.location.href = mainServer+"/weixin/productgroup/toJoinGroup?userGroupId="+userGroupId+"&productId="+productId+"&specialId="+specialId;
}

//选择规格
function checkGoodNum (value){
	if(value != null && value != ""){
		var exp = /^[1-9]\d*$/;
		if(!exp.test(value)){
			showvaguealert('只能输入正整数');
			$(".calGoodNums").val(1);
			$("#buyAmount").val(1);
			return false;
		}

		var maxBuy = limitNum;
    	var buyAmount = $("#buyAmount").val();
    	if(stockCopy!=null&&stockCopy!=''){
    		if(parseInt(stockCopy)<=0){
    			$(".calGoodNums").val(1);
    			showvaguealert("此产品规格暂无库存");
    			return false;
    		}
    	}
    	if(parseInt(value)>=parseInt(stockCopy)){
    		$(".calGoodNums").val(1);
    		$("#buyAmount").val(1);
			showvaguealert("库存仅剩"+stockCopy+"件");
			return false;
		}
    	
    	if(parseInt(limitNum)==0){
    		$(".calGoodNums").val(1);
    		showvaguealert("此产品限购不可购买");
			return false;
    	}
    	if(parseInt(limitNum)>=1){
    		
    		if(parseInt(restCopy)<=0){//剩余购买数量
    			$(".calGoodNums").val(1);
				showvaguealert("此产品限购"+maxBuy+"份");
    			return false;
    		}
    		if(parseInt(hasBuy)>= parseInt(limitNum)){
    			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
    			return false;
			}
    		if(parseInt(hasBuy)>0&&parseInt(buyAmount)>parseInt(restCopy)){
    			$(".calGoodNums").val(parseInt(restCopy));
    			$("#buyAmount").val(parseInt(restCopy));
    			showvaguealert("您已经购买了"+hasBuy+"件，可再购买"+restCopy+"件");
    			return false;
    		}
    		if(parseInt(buyAmount)>parseInt(restCopy)){
    			$(".calGoodNums").val(parseInt(maxBuy));
    			$("#buyAmount").val(parseInt(maxBuy));
    			showvaguealert("此产品限购"+maxBuy+"份");
    			return false;
    		}
    		
    		if(parseInt(buyAmount)>=1){
    				if(parseInt(maxBuy)-parseInt(hasBuy)<=parseInt(buyAmount)){
    					$(".calGoodNums").val(restCopy);
    	    			$("#buyAmount").val(parseInt(restCopy));
	    				showvaguealert("此产品限购"+maxBuy+"件,最多可再购买"+restCopy+"件");
		    			return false;
	    			}
    			
    		}
    	}
// 		$(this).siblings('.calGoodNums').val(value+1);
		$("#buyAmount").val(parseInt(value));
		var standprice = $('.normsPrice span').text();
		var price = standprice.substring(1,standprice.length);
		calculateFullCut(price,parseInt(value),fullMoney,cutMoney);
	}else{
		$(".calGoodNums").val(1);
	}
};


//修改微信自带的返回键 
$(function() {
	var bool = false;
	var backUrl = back;
	
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			console.log(backUrl+"---backUrl");
			if (backUrl != null && backUrl != "" && backUrl != undefined) {
				window.location.href = backUrl;
			} else {
				window.location.href = mainServer + "/weixin/toGroupGoods";
			}
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

function toOtherHome(otherCustId){
	 back = window.location.href;
	 if(customerId != null && customerId > 0 && customerId == otherCustId){
		window.location.href = mainServer + "/weixin/socialityhome/toSocialityHome?backUrl="+back.replace(/\&/g,"%26");
	 }else{
	 	window.location.href = mainServer + "/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+otherCustId+"&backUrl="+back.replace(/\&/g,"%26");
	 }
}