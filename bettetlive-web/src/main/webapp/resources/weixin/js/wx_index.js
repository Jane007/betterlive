var _couponIds="";
var _cmIds="";
$(function(){

	$(".initloading").show();
	setTimeout(function(){
		$(".initloading").hide();
	},800);
	
	
	//判断消息数量小图标是否显示
	var checkMsgNum = $('.msg').find('span').html();
	if(checkMsgNum > 0){
		$('.msg').find('span').css('visibility','visible');
		if(checkMsgNum>99){
			$('.msg').find('span').show().html("99+").css('font-size','10px');
		}else{
			$('.msg').find('span').show().html(checkMsgNum);
		}
	}else{
		$('.msg').find('span').css('visibility','hidden')
	};
	
	//购物车数据处理
	var cartCnt = $('.footer').find('span').html();
	if(cartCnt==0 || cartCnt == null){
		$('.gwnb').hide();
	}else if(cartCnt>99){
		$('.footer').find('span').html("99+").css('font-size','10px');
	}//否则就不处理，照常显示

	
	
	
	
	queryHomeLabel();
	
	queryNewProduct();
	
	queryHotProduct();
	
	queryLimitByHome();
	
	queryRecomendArticles();
	
	queryVideoByHome();
	
    //下载占位替换
	queryPlaceholder();

	//活动弹窗
	querySpecialAlertInfo();

	
	var mySwiper=new Swiper(".swiper-container",{
		loop:true,
		pagination : '.swiper-pagination',
		autoplayDisableOnInteraction : false,
		autoplay:'3000'
	});
	
	$('#contentId').dropload({
        scrollArea : window,
        domUp : {
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh">↓下拉刷新...</div>',
            domUpdate  : '<div class="dropload-update">↑释放更新...</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
        },
        loadUpFn : function(me){
			location.reload();
        }
    });
	

});

$('.shut').click(function(){
    var timer = Date.parse(new Date());
    var obj = {
        startTime:timer,
        dowboxhide:"ok"
    };
	$(".dowbox-wrap").fadeOut(500,function(){
		localStorage.setItem('dowboxhide',JSON.stringify(obj));
		$('.header-wrap').css({
			'transition': 'all 0.3s',
			'top':'0'
		});
		$('.content').css({
			'margin-top':'1.166rem'
		});
	})
});

$('.close').click(function(){
	$('.shad').hide();
	$('.bggift-wrap').hide();
});

$('.hbclose').click(function(){
	$('.shad').hide();
	$('.outhb').hide();
});
//一键领取
$('.bgbtn').click(function(){
	receiveMultiCouponNew(_couponIds, _cmIds)
});

//首页弹窗活动关闭
$('.oclose').click(function(event){
	event.stopPropagation();
	$('.anniversary-wrap').fadeOut(1000, function(){
	});
});

/**
 * 单个文章跳转处理
 * @param articleId
 * @param articleFrom
 */
function queryOneArticle(articleId,articleFrom){
	var url = window.location.href;
	if(url.indexOf("#")!=-1){
		url = url.substring(0,url.indexOf("#"));
	}
	var local = mainServer + "/weixin/discovery/toSelectedDetail?articleId="+articleId+"&backUrl="+url;
	if(articleFrom != null && articleFrom == 1){
		local = mainServer + "/weixin/discovery/toDynamicDetail?articleId="+articleId+"&backUrl="+url;
	}
	window.location.href = local;
}
function queryPlaceholder(){
	var url = mainServer + '/weixin/queryPlaceholder';
	$.post(url,function(data){
		if(data.code == "1010"){//说明有数据就替换下载占位
			$(".dowbox .activitytext h3").html(data.data.title);
			$(".dowbox .activitytext p").html(data.data.resume);
			$(".dowbox .dowbtn a").attr("href",data.data.linkUrl);
			$(".dowbox .dowbtn a").html("点击参与");
		}else{
			$(".dowbox .activitytext h3").html("挥货APP豪礼送不停！");
			$(".dowbox .activitytext p").html("多款美食限量抢购，低至1元！");
			$(".dowbox .dowbtn a").attr("href", mainServer + "/weixin/share/shareDownloadApp");
			$(".dowbox .dowbtn a").html("点击下载");
		}
		
		//获取缓存判断是否存在顶部下载栏显示状态
		var nowTimer = Date.parse(new Date());
		var dayTime = 60 * 60  * 1000;
		var dowflag = JSON.parse(localStorage.getItem("dowboxhide"));
		//读取缓存数据判断是否存在或者已经超过一小时
		if(null == dowflag || !dowflag || nowTimer - dowflag.startTime >= dayTime){
			localStorage.removeItem('dowboxhide');
			$('.dowbox-wrap').show();
			//因换算单位暂不统一动态计算banner位置
			$('#contentId').css({
				'margin-top':($('.dowbox-wrap').height() + $('.header').height())
			});
		}else{
			$('.dowbox-wrap').hide();
			$('.header-wrap').css('top',"0");
			$('#contentId').css('margin-top',"1.166rem");
		}

	});
}
/**
 * 专题弹窗活动
 */
function querySpecialAlertInfo() {
	$.ajax({                                       
		url: mainServer + '/weixin/querySpecialAlertInfo',
		data:{},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //领取成功
				var pro = data.data;
				$(".anniversary").css({
					"background":"url(" + pro.backGroundUrl + ") no-repeat",
					"background-size": "100% 100%"
				});
				
				var flag = false;
				var anniversaryObj = JSON.parse(localStorage.getItem("anniversaryObj"));
				var timer = new Date().getTime();
				if(anniversaryObj && timer - anniversaryObj.nowtime >= 3600000){
					flag = true;
				}
				
				if(!anniversaryObj || anniversaryObj == null || anniversaryObj == '' || flag){
					$('.anniversary-wrap').fadeIn(1000);

					$(".anniversary").attr("onclick", "location.href='"+ pro.linkUrl +"'");
					//缓存状态标记
					var nowtime = new Date().getTime();
					var obj = {
							flag:true,
							nowtime:nowtime,
							specialId:pro.specialId
					};
					localStorage.setItem('anniversaryObj',JSON.stringify(obj))
				}else if(anniversaryObj.specialId != pro.specialId){
					localStorage.removeItem('anniversaryObj');
					$('.anniversary-wrap').fadeIn(1000);

					$(".anniversary").attr("onclick", "location.href='"+ pro.linkUrl +"'");
					//缓存状态标记
					var nowtime = new Date().getTime();
					var obj = {
							flag:true,
							nowtime:nowtime,
							specialId:pro.specialId
					};
					localStorage.setItem('anniversaryObj',JSON.stringify(obj))
				}else{
					showCouponAlert(); //如果专题弹窗活动已弹 则走红包弹窗的逻辑
				}
			}else if (data.code == "1404"){
				showCouponAlert(); //如果没用专题弹窗活动 则走红包弹窗的逻辑
			}else{
				showvaguealert(data.msg);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}


/**
 * 批量领取单品红包
 * @param e
 * @param couponId
 * @param productId
 */
function receiveSingleCouponList(e, couponId, productId) {
	if(couponId == null || couponId <= 0){
		showvaguealert('访问出错了');
		return;
	};
	
	var _op = $(e).parent();
	_op.find('span').remove();
	_op.addClass('on');
	var _showBtn = "<span><a href='"+mainServer+"/weixin/product/towxgoodsdetails?productId="+productId+"' style=''>立即使用</a></span>";
	_op.append(_showBtn);
	$.ajax({                                       
		url: mainServer + '/weixin/customercoupon/receiveSingleCouponList',
		data:{
			"couponId":couponId,
			"productId":productId
		},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //领取成功
				showvaguealert('领取成功');
			}else if (data.code == "1011"){
				window.location.href = mainServer + "/weixin/tologin?backUrl=" + mainServer + "/weixin/index";
			}else if (data.hasFlag == "1"){
				showvaguealert("您已经领取过");
			}else{
				showvaguealert(data.msg);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 单个优惠券领取
 * @param e
 * @param cmId
 */
function receiveCoupon(e, cmId){
	if(cmId == null || cmId <= 0){
		showvaguealert('访问出错了');
		return;
	};
	$.ajax({                                       
		url: mainServer + '/weixin/customercoupon/receiveCoupon',
		data:{
			"cmId":cmId,
		}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //领取成功
				showvaguealert('领取成功');
				var _op = $(e).parent();
				_op.find('span').remove();
				_op.addClass('yhon');
				var _showBtn = "<a href='javascript:void(0);' style=''>已领取</a>";
				_op.append(_showBtn);
			}else if (data.code == "1011"){  
				window.location.href = mainServer + "/weixin/tologin?backUrl="+mainServer+"/weixin/index";
			}else{
				showvaguealert(data.msg);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

function showCouponAlert(){
	$.ajax({                                       
		url: mainServer + '/weixin/getCoupons',
		type:'post',
		dataType:'json',
		success:function(data){
			var nowTimer = Date.parse(new Date());
			var dayTime = 60 * 60  * 1000;
			var showflag = JSON.parse(localStorage.getItem("showCoupon"));
			if(null == showflag || !showflag || nowTimer - showflag.nowtime >= dayTime){
				if(showflag != null){
					localStorage.removeItem('showCoupon');
				}
				
				var nowtime = new Date().getTime();
				var obj = {
						flag:true,
						nowtime:nowtime
				};
				
				localStorage.setItem('showCoupon',JSON.stringify(obj))
				
				var hasExpiring = data.hasExpiring;   
				var couponManagersLen = 0;
				var singleCouponsLen = 0;
				var xrhb = data.newUserCoupon;
				
				if(data.couponManagers != null){
					couponManagersLen = data.couponManagers.length;
				}
				if(data.singleCoupons != null) {
					singleCouponsLen = data.singleCoupons.length;
				}
				
				var totalLen = couponManagersLen + singleCouponsLen;
				
				var singleCoupons = data.singleCoupons;
				var couponManagers = data.couponManagers;
				if(totalLen>2){
					if(xrhb != null && xrhb != "" && xrhb == "1"){
						return;//新手红包暂停一天
						$(".bggift").find("h2").html("新人专享278元大礼包");
					}
					var content = showManyCoupon(singleCoupons,couponManagers);
					$('.giftlist').show();
					
					$('.shad').show();
					$('.giftlist').html(content);
					$('.bggift-wrap').show();
				}else if(totalLen>0 && totalLen<=2){//小于2的一种样式
					$('.outhb').show();
					var content = showLittleCoupon(singleCoupons,couponManagers)
					$('.shad').show();
					$('.hblist').html(content)
					
				}
			}
		}
	})
}

/**
 * 多个红包样式
 * @param singleCoupons
 * @param couponManagers
 * @returns {String}
 */
function showManyCoupon(singleCoupons,couponManagers){
	var content = '';
	if(singleCoupons!=null&&singleCoupons!=''){
		$.each(singleCoupons,function(index,scs){
			_couponIds+=scs.couponId+",";
			content += ['<li>',
						'<h3>￥'+scs.couponMoney+'</h3>',
						'<h4>'+scs.couponName+'</h4>',
						'<div class="btn">',
							'<span onclick="receiveSingleCouponList(this, \''+scs.couponId+'\',\''+scs.productId+'\');">立即领取</span>',
						'</div>',
					'</li>'].join('');
		});
	}
	if(couponManagers!=null&&couponManagers!=''){
		$.each(couponManagers,function(index,cms){
			_cmIds+=cms.cm_id+",";
			content += ['<li>',
						'<h3>￥'+cms.coupon_money+'</h3>',
						'<h4>'+cms.coupon_name+'</h4>',
						'<div class="btn">',
							'<span onclick="receiveCoupon(this, '+cms.cm_id+');">立即领取</span>',
						'</div>',
					'</li>'].join('');
		});
	}
	return content;
}


function showLittleCoupon(singleCoupons,couponManagers){
	var content = '';
	if(singleCoupons!=null&&singleCoupons!=''){
		$.each(singleCoupons,function(index,scs){
			_couponIds+=scs.couponId+",";
			content +=['<li>',
			           		'<div class="hbleft fl">',
			           			'<div class="monert">',
			           				'￥'+scs.couponMoney+'',
			           			'</div>',
			           			'<div class="bot">',
			           				'满'+scs.fullMoney+'可使用',
			           			'</div>',
			           		'</div>',
			           	'<div class="hbright fl">',
			           		'<h3>'+scs.couponName+'(限单品)</h3>',
			           		'<p>有效期至'+scs.endTime+'</p>',
			           		'<div class="butt">',
			           			'<span onclick="receiveSingleCouponList(this, \''+scs.couponId+'\',\''+scs.productId+'\');">立即领取</span>',
			           		'</div>',
			           	'</div>',
			           	'</li>'].join('');
		});
		
	}
	
	if(couponManagers!=null&&couponManagers!=''){
		$.each(couponManagers,function(index,cms){
			_cmIds+=cms.cm_id+","
			content +=['<li>',
			           		'<div class="hbleft fl">',
			           			'<div class="monert">',
			           				'￥'+cms.coupon_money+'',
			           			'</div>',
			           			'<div class="bot">',
			           				'满'+cms.usemin_money+'可使用',
			           			'</div>',
			           		'</div>',
			           	'<div class="hbright fl">',
			           		'<h3>'+cms.coupon_name+'</h3>',
			           		'<h5>有效期<span>'+cms.usemax_date+'</span>天</h5>',
			           		'<div class="butt">',
			           			'<span onclick="receiveCoupon(this, '+cms.cm_id+');">立即领取</span>',
			           		'</div>',
			           	'</div>',
			           	'</li>'].join('');
		});
		
	}
	return content;
}

/**
 * 一键领取红包
 * @param _couponIds
 * @param _cmIds
 */
function receiveMultiCouponNew(_couponIds, _cmIds) {
	$.ajax({                                       
		url: mainServer + '/weixin/customercoupon/receiveMultiCoupon',
		data:{
			"couponIds": _couponIds,
			"cmIds": _cmIds,
		}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //领取成功
				$('.bggift-wrap').hide();
				$('.shad').show();
				$(".hb").show();
				setTimeout(function(){
					$('.shad').hide();
					$(".hb").hide();
				},800);
//				showvaguealert('领取成功');
				
			}else if (data.code == "1011"){
				window.location.href = mainServer + "/weixin/tologin?backUrl=" + mainServer + "/weixin/index";
			}else{
				showvaguealert(data.msg);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 热门搜索标签
 */
function queryHomeLabel() {
	$.ajax({                                       
		url: mainServer + '/common/queryHomeLabel',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010" && data.data != null && data.data.labelId>0){
				$("#searchBox").val(data.data.labelName);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

function queryNewProduct() {
	$.ajax({                                       
		url: mainServer + '/weixin/queryIndexPro',
		data:{
			"extensionType":1
		}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data.length <= 0){
				return;
			}
			
			var list = data.data;
			for (var conIndex in list) {
				if(isNaN(conIndex)){
					continue;
				}
				var vo = list[conIndex];
				var gposation = "pro-right";
				var showIndex = 0;
				
				var toGoodDetail = "";
				//查看详情
				if(vo.activityType == 2){
					toGoodDetail = "todWxGoodDetails("+vo.product_id+","+vo.activity_id+",3)";
				}else if(vo.activityType == 3){
					toGoodDetail = "todWxGoodDetails("+vo.product_id+","+vo.activity_id+",2)";
				}else {
					toGoodDetail = "todWxGoodDetails("+vo.product_id+",0,1)";
				}
				
				if(conIndex == 0){	//第一个位置
					gposation = "pro-left";
					$(".newproduct ."+gposation).attr("onclick", toGoodDetail);
					//商品图片
					$(".newproduct ."+gposation).find("img").attr("src", vo.homeweekly_first_img);
				}else{	//第二/三个位置
					if(conIndex == 2){
						showIndex = 1;
					}
					$(".newproduct ."+gposation).find("div").eq(2*parseInt(showIndex)).attr("onclick", toGoodDetail);
					//商品图片
					$(".newproduct ."+gposation).find("img").eq(showIndex).attr("src", vo.homeweekly_after_img);
				}
				
				//标题
				$(".newproduct ."+gposation).find("h3").eq(showIndex).html(vo.product_name);
				
				//描述
				if(vo.share_explain != null && vo.share_explain != ""){
					$(".newproduct ."+gposation).find("h4").eq(showIndex).html(vo.share_explain);
				}
				
				var priceHtml = "￥";
				
				if(vo.activityPrice != null && vo.activityPrice !='' && vo.activityPrice != '-1'){
					priceHtml += vo.activityPrice;
				}else if(vo.discountPrice != null && vo.discountPrice !='' && vo.discountPrice != '-1'){
					priceHtml += vo.discountPrice;
				}else{
					priceHtml += vo.price;
				}
				//价钱
				$(".newproduct ."+gposation).find("span").eq(showIndex).html(priceHtml);
				
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 去查看商品详情
 * @param productId
 * @param specialId
 * @param type
 */
function todWxGoodDetails(productId,specialId,type){
	var backUrl=mainServer+"/weixin/index";
	if(type==1){	//普通商品                                                               
		window.location.href=mainServer+'/weixin/product/towxgoodsdetails?productId='+productId+"&backUrl="+backUrl;
	}else if(type==2){	//团购                                                               
		window.location.href=mainServer+'/weixin/product/toGroupGoodsdetails?productId='+productId+'&specialId='+specialId+"&backUrl="+backUrl;
	}else if(type==3){	//限量抢购                                                               
		window.location.href=mainServer+'/weixin/product/toLimitGoodsdetails?productId='+productId+'&specialId='+specialId+"&backUrl="+backUrl;
	}else{
		alert("参数错误");
	}
}

/**
 * 人气推荐
 */
function queryHotProduct() {
	$.ajax({                                       
		url: mainServer + '/weixin/queryIndexPro',
		data:{
			"extensionType":2
		}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data.length <= 0){
				return;
			}
			
			var list = data.data;
			for (var conIndex in list) {
				if(isNaN(conIndex)){
					continue;
				}
				var vo = list[conIndex];
				//查看详情
				if(vo.activityType == 2){
					toGoodDetail = "todWxGoodDetails("+vo.product_id+","+vo.activity_id+",3)";
				}else if(vo.activityType == 3){
					toGoodDetail = "todWxGoodDetails("+vo.product_id+","+vo.activity_id+",2)";
				}else {
					toGoodDetail = "todWxGoodDetails("+vo.product_id+",0,1)";
				}
				var productLogo = resourcepath + "/weixin/img/index/pro_4.png";
				if(vo.product_logo != null && vo.product_logo != ""){
					productLogo = vo.product_logo;
				}
				var showHtml = '<li onclick="' + toGoodDetail + '">' 
							  +'	<a href="javascript:void(0);">'
							  +'		<img src="' + productLogo + '"/>'
							  +'	</a>'
							  +'	<div class="litext">'
						  	  +'		<h3>' + vo.product_name + '</h3>';
				if(vo.share_explain != null && vo.share_explain != ""){
					showHtml	  +='		<h4>' + vo.share_explain + '</h4>';
				}
					showHtml	  +='		<div class="proprice">';
				if(vo.activityPrice != null && vo.activityPrice !='' && vo.activityPrice != '-1'){
					showHtml += '<span>￥' + vo.activityPrice + '</span>';
					showHtml += '<span class="secondSpan">￥' + vo.price + '</span>';
				}else if(vo.discountPrice != null && vo.discountPrice !='' && vo.discountPrice != '-1'){
					showHtml += '<span>￥' + vo.discountPrice + '</span>';
					showHtml += '<span class="secondSpan">￥' + vo.price + '</span>';
				}else{
					showHtml += '<span>￥' + vo.price + '</span>';
					showHtml += '<span></span>';
				}
				if(vo.labelName != null && vo.labelName != ""){
					showHtml +='<div class="by">'+vo.labelName+'</div>';
				}
				showHtml += '		</div> ';
				showHtml += '	</div>';
				
				showHtml += '</li>';
				$(".recommend ul").append(showHtml);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 限量特惠
 */
function queryLimitByHome() {
	var url = window.location.href;
	if(url.indexOf("#")!=-1){
		url = url.substring(0,url.indexOf("#"));
	}
	
	$.ajax({                                       
		url: mainServer + '/weixin/queryLimitByHome',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data.length < 3){  //没有数据
				$(".rush-wrap").hide();
				return;
			}
			$(".rush-wrap").show();
			if(data.data.length > 3){
				$(".rush-wrap .title .on").show();
			}
			var list = data.data;
			for (var continueIndex in list) {
				if(isNaN(continueIndex)){
					continue;
				}
				if(continueIndex > 3){
					break;
				}
				var specialVo = list[continueIndex];
				var proVo = specialVo.productSpecVo;
				
				var stateLabel="购买";
				var btnCls = "";
				if(specialVo.status != 1 || specialVo.longEnd <= data.currtime){
					stateLabel="结束";
					btnCls = " ok";
				}else if(specialVo.longStart > data.currtime){
					stateLabel="预热";
					btnCls = " ok";
				}else if(proVo.total_stock_copy <= 0){
					stateLabel="售罄";
					btnCls = " ok";
				}
				
				var logo = proVo.product_logo;
				if(logo=='' || logo==null){
					logo = mainServer+"/resources/weixin/img/goods/pro-default.png"
				}
				var stockCopy = proVo.total_stock_copy;
				if(stockCopy=='' || stockCopy==null){
					stockCopy = 0;
				}
				var local = mainServer + "/weixin/product/toLimitGoodsdetails?productId="+proVo.product_id+"&specialId="+specialVo.specialId+"&backUrl="+url;
				var showHtml = "<li>"
							+"		<div class=\"rushtop\">"
							+"			<a href=\""+local+"\">"
							+"				<img src=\""+logo+"\"/>"
							+"			</a>"
							+"			<div class=\"textbot\">"
							+"				<h5>剩余<span>"+stockCopy+"</span>件</h5>"
							+"			</div>"
							+"			<!--抢光-->"
							+"			<div class=\"by"+btnCls+"\">"+stateLabel+"</div>"
							+"		</div>"
							+"		<div class=\"rushbot\">"
							+"			<div class=\"spantext\">"
							+"				<span>￥"+proVo.activity_price+"</span><span>￥"+proVo.spec_price+"</span>"
							+"			</div>"
							+"		</div>"
							+"</li>";
				$(".rushlist").append(showHtml);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

/**
 * 精选列表
 */
function queryRecomendArticles() {
	$.ajax({                                       
		url: mainServer + '/weixin/queryRecomendArticles',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data.length <= 0){  //没有数据
				return;
			}
			
			var backUrl = mainServer+"/weixin/index";
			var list = data.data;
			for (var conIndex in list) {
				if(isNaN(conIndex)){
					continue;
				}
				var vo = list[conIndex];
				var articleCover = resourcepath + "/weixin/img/index/pro_5.png";
				if(vo.articleCover != null && vo.articleCover != ""){
					articleCover = vo.articleCover;
				}
				
				var clickLocal = mainServer + "/weixin/discovery/toSelectedDetail?articleId="+vo.articleId+"&backUrl="+backUrl;
				if(vo.articleFrom == 1){
					clickLocal = mainServer + "/weixin/discovery/toDynamicDetail?articleId="+vo.articleId+"&backUrl="+backUrl;
				}
				
				var showHtml = '<div class="swiper-slide">'
							+'		<a href="'+clickLocal+'">'
							+'			<img src="' + articleCover + '"/>'
							+'			<div class="spetext">'
							+'				<h3>' + vo.articleTitle + '</h3>';
							
				if(vo.articleIntroduce != null && vo.articleIntroduce != ""){
					showHtml +='	<h4>' + vo.articleIntroduce + '</h4>';
				}
				
				showHtml += '			</div>';
				showHtml += '		</a>';
				showHtml += '</div>';
				
				$(".special .swiper-wrapper").append(showHtml);
				
			};

			var swiper1= new Swiper('.swiper-container1', {     
				slidesPerView: 'auto',
		        slidesPerView: 1.07,                
		        paginationClickable: true,
		        spaceBetween: 10, 
		   });
			$('.swiper-slide:last-child').find('a').css('margin-right','0.3888rem')
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

function queryVideoByHome(){
	$.ajax({                                       
		url: mainServer + '/weixin/queryVideoByHome',
		data:{}, 
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code !="1010" || data.data == null || data.data.specialId == null || data.data.specialId <= 0){  //没有数据
				return;
			}
			var vo = data.data;
			
//			$(".todayvideo .title").html(vo.productLabel);
			$(".video").find("video").attr("src",vo.specialPage);
			$(".bottext").find("h3").append(vo.specialTitle);
			$(".bottext .dz").find("span").html(vo.collectionCount);
			if(vo.isCollection > 0){
				$(".bottext .dz").addClass("on");
				$("#vdoCollectId").attr("onclick", "addOrCancelCollection(1, "+vo.specialId+", "+vo.isCollection+")");
			}else{
				$("#vdoCollectId").attr("onclick", "addOrCancelCollection(0, "+vo.specialId+", 0)");
			}
			$(".videoshad").css({
				"background" : "url('"+ vo.specialCover +"') no-repeat",
				"background-size" : "100% 100%"
			});
			$(".todayvideo-wrap").show();
			
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});
}

function addOrCancelCollection(flag,specialId,collectionId){
	
 	if((flag != 0 && flag != 1) || isNaN(flag)){
 		showvaguealert("出现异常");
 		return;
 	}
 	if(specialId == null || specialId == "" || isNaN(specialId) || parseFloat(specialId) <= 0){
			showvaguealert('出现异常啦');
			return;
		}
 	var url = mainServer + "/weixin/collection/addCollection";
 	var data={
 		"collectionType":3,
 		"objId":specialId
 	};
 	if(flag == 1){
 		url = mainServer + "/weixin/collection/cancelCollection";
 		if(collectionId == null || collectionId == "" || isNaN(collectionId) || parseFloat(collectionId) <= 0){
 			showvaguealert('出现异常啦');
 			return;
 		}
 		data={
	 		"collectionId":collectionId
	 	};
 	}
 	$("#vdoCollectId").attr("onclick", "void(0);");
	$.ajax({                                       
		url: url,
		data:data,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.code == "1010"){ //获取成功
				if(flag == 1){
					$("#vdoCollectId").attr("onclick", "addOrCancelCollection(0, "+specialId+", 0)");
					$("#vdoCollectId").removeClass('on');
					var coCount = $("#vdoCollectId").find("span").html();
					$("#vdoCollectId").find("span").html(parseFloat(coCount)-1);
					showvaguealert("已取消收藏");
				}else{
					$("#vdoCollectId").attr("onclick", "addOrCancelCollection(1,"+specialId+","+data.data+")");
					$("#vdoCollectId").addClass('on');
					var coCount = $("#vdoCollectId").find("span").html();
					$("#vdoCollectId").find("span").html(parseFloat(coCount)+1);
					showvaguealert("收藏成功");
				}
			}else if (data.code == "1011"){
				var url = window.location.href;
				if(url.indexOf("#")!=-1){
					url = url.substring(0,url.indexOf("#"));
				}
				window.location.href = mainServer + "/weixin/tologin?backUrl=" + url;
			}else{
				showvaguealert(data.msg);
			}
		},
		failure:function(data){
			showvaguealert('访问出错');
		}
	});
}

$('.videoshad').click(function(){
	 this.parentNode.getElementsByTagName("video")[0].play();
	 $(this).remove();
});

//点击视频控制播放暂停
var videoNode = document.getElementsByTagName('video')[0]; 
$('video').click(function(){
	if (videoNode.paused){
		videoNode.play();
    }else{
    	videoNode.pause();
    }
})