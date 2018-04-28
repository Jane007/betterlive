var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();

var limitNum = -1; // 限量
var hasBuy = 0; // 已经购买的份数
var restCopy = 100; // 剩余购买数量
var carNums = 0;
var carCanAdd = 0; // 购物车还可以添加多少份
var stockCopy = 0; // 库存

var packageDesc=''; //套餐说明

$(function() {
	$(".initloading").show();
	setTimeout(function(){
		$(".initloading").hide();
	},800);
	
	//全选按钮状态复位
	initial();
	//计算商品总价格
	goodstotal();
	
	//是单选变为全选
	function initial() {
		var prod = $(".checklist input[type=checkbox]");
		var n = 0;
		for (var i = 0; i < prod.length; i++) {
			if (!prod[i].checked) {
				$('.allCheck span').removeClass('on');
				$(".allCheck input[type=checkbox]")[0].checked = false;
			} else {
				n++;
			}
		}
		if (prod.length == n) {
			$('.allCheck span').addClass('on');
			$(".allCheck input[type=checkbox]")[0].checked = true;
		}

		if (n == 0) {
			$('.orderRight').addClass('active');
			$('.delAll').addClass('active');
		} else {
			$('.orderRight').removeClass('active');
			$('.delAll').removeClass('active');
		}
	}
	
	//点击编辑
	$('.editing').click(function() {
		if ($(this).hasClass('active')) {
			$(this).text('编辑').removeClass('active');
			$('.chNorms').removeClass('active');
			$('.carcakename').show();
			$('.carweight').show();
			$('.shopnumber').show();
			$('.orderRight').show();
			$('.delAll').hide();
			$('.hejibox').show();
			$('.totalSum').show();
			var len = $('.carlist').length;
			for (var i = 0; i < len; i++) {
				$('.shopnumber span').eq(i).text($('.ocompute input').eq(i).val());
			}
		} else {
			$(this).text('完成').addClass('active');
			$('.chNorms').addClass('active');
			$('.carcakename').hide();
			$('.carweight').hide();
			$('.shopnumber').hide();
			$('.orderRight').hide();
			$('.delAll').show();
			$('.hejibox').hide();
			$('.totalSum').hide();
		}

	});
	
	//全选选中的样式
	$('.allCheck').click(function() {

		if ($(this).find('span').hasClass('on')) {
			$(this).find('span').removeClass('on');
			$('.checklist span').removeClass('on');
			$(this).find('input[type=checkbox]')[0].checked = false;
			$('.orderRight').addClass('active');
			$('.delAll').addClass('active');
			//				$('.allCheck i').text(0);
		} else {
			$(this).find('span').addClass('on');
			$('.checklist span').addClass('on');
			$(this).find('input[type=checkbox]')[0].checked = true;
			$('.orderRight').removeClass('active');
			$('.delAll').removeClass('active');
			//				initial();
		}
		var key = $(this).find('input[type=checkbox]').is(":checked");
		var prod = $(".checklist input[type=checkbox]");

		for (var i = 0; i < prod.length; i++) {
			prod[i].checked = key;
			goodstotal();
		}
	});
	
	//点击购物车商品单选中的样式
	$('.checklist span').click(function() {
		if ($(this).hasClass('on')) {
			$(this).removeClass('on');
		} else {
			$(this).addClass('on');
		}
		goodstotal();
		initial();
	});
	
	//***************** START  ***********************  编辑时增加或者减少商品数量   ************** START ***********************************
	//减少商品数量
	$('.cut').click(function() {
		var $numval = parseInt($(this).next('input').val());
		if ($numval <= 2) {
			$numval = 2;
		}
		var endCount=$numval-1;
		$(this).next('input').val(endCount);
		$(this).parents('.carlist').find('.shopnumber span').text($(this).next('input').val());
		goodstotal();
		
		$("#editType").val("2");
		$("#carId").val($(this).parents('.carlist').attr("id"));
		$("#buyAmount").val(endCount);
		updateAmount();
		
	});
	
	//增加商品数量
	$('.add').click(function() {
		var $numval = parseInt($(this).prev('input').val());
		var endCount = $numval+1;
		
		$(this).parents('.carlist').find('.shopnumber span').text($(this).prev('input').val());
		var carId = $(this).parents('.carlist').attr("id");
		var $this = $(this).prev('input');
		var buyAmount = endCount;
		//规格限购
		var $choose=$(this).parents('.carlist').find('.choose');
			stockCopy=$choose.attr("data-copy");
			limitNum=$choose.attr("data-limit");
			hasBuy = $choose.attr("data-hasBuy");
			restCopy=$choose.attr("data-restCopy");
			packageDesc=$(this).attr("data-package");
		if(stockCopy!=null&&stockCopy!=''){
    		if(parseInt(stockCopy)<=0){
    			showvaguealert("此产品规格暂无库存");
    			return false;
    		}
    	}
	    
		if(parseInt(limitNum)>0){//限购才走这些判断
			if(parseInt(buyAmount)>parseInt(restCopy)){
				if(parseInt(hasBuy)>0){
					showvaguealert("此商品限购"+limitNum+"件，您已购买"+hasBuy+"件");
				}else{
					showvaguealert("此商品限购"+limitNum+"件");
				}
				return false;
	    	}
			
			if(parseInt(buyAmount)>parseInt(stockCopy)){
    			showvaguealert("库存仅剩"+stockCopy+"件");
    			return false;
    		}
		}
			
		//调后台限购
		$.ajax({
			url : mainServer+'/weixin/shoppingcart/calculateCopy',
			type : 'post',
			dataType : 'json',
			data : {
				'cartId' : carId,
				'buyAmount':buyAmount
			},
			success : function(data) {
				if(data.result=='YES'){
					$this.val(endCount);
					goodstotal();
					$("#editType").val("2");
					$("#carId").val(carId);
					$("#buyAmount").val(endCount);
					updateAmount();
				}else{
					showvaguealert(data.msgs);
				}
			}
		});
	});
	
	//***************** END  ***********************  编辑时增加或者减少商品数量   ************** END ***********************************
	
	
	//合计金额
	function goodstotal() {
		var meetConditions = '${meetConditions}';
		var productIds = '${productIds}';
		var postage = '${postage}';
		if ('' != meetConditions && '' != productIds && '' != postage) {
			productIds = productIds.substring(1,productIds.lastIndexOf(","));
			productIds = productIds.split(",");
		}
		var $carlist = $('.carlist');
		var bum = 0;
		var totnum = 0;
		var totalPrice = 0;
		var pro_id = 0;
		for (var i = 0; i < $carlist.length; i++) {
			var prod = $carlist.eq(i).find(".checklist input[type=checkbox]").is(":checked");
			if (prod) {
				var price = ($carlist.eq(i).find(".shopprice span").text());
				var num = $carlist.eq(i).find(".ocompute input").val();
				bum += parseFloat((price * num).toFixed(2));
				totnum += parseFloat(num);
				pro_id = $carlist.eq(i).find(".shopprice span").attr("id");
				for (var j = 0; j < productIds.length; j++) {
					if (productIds[j] == pro_id) {
						totalPrice += parseFloat((price * num).toFixed(2));
					}
				}
			}
		}
		totalPrice = totalPrice.toFixed(2);
		if (totalPrice == 0) {
			$('.tishiby').hide();
		}else if (totalPrice < parseFloat(meetConditions)) {
			$('.tishiby').text("未满足"+meetConditions+"元零食类商品免邮活动");
			$('.tishiby').show();
			bum += parseFloat(postage);
		}else{
			$('.tishiby').text("已满足"+meetConditions+"元免邮费");
			$('.tishiby').show();
		}
		bum = bum.toFixed(2);
		$('.totalSum').text('￥' + bum);
		$('.allCheck i').text(totnum);
	}

	//点击全部选中商品删除
	var delALL = false;
	var delCartId = "";
	$(document).on('click', '.delAll', function() {
		var prod = $(".checklist input[type=checkbox]");
		var n = 0;
		for (i = 0; i < prod.length; i++) {
			if (prod[i].checked) {
				n++;
				$(prod[i]).parents('.carlist').addClass('alldel_list');

				delCartId += $(prod[i]).attr("value") + ",";
			}
		}

		if (n == 0) {
			showvaguealert("请选择需要删除的商品");
			return false;
		} else {
			delCartId = delCartId.substring(0, delCartId.lastIndexOf(",")); //截取字符串
		}

		if (n > 0) {
			$('.whetherbox').addClass('active');
			$('.mask').css('z-index',"120").show();
			if (n == (prod.length)) {
				delALL = true;
				$('.whetherbox p').html('是否删除全部商品？');
			} else {
				delALL = false;
				$('.whetherbox p').html('是否删除' + n + '件商品？');
			}
			//   }else{
			//   	$('.whetherbox p').html('没有商品可删除？');
			
		}
	});
	//删除显示去逛逛
	function checkCarlist() {
		var _carListLen = $('.carlist').length;
		if(_carListLen == 0) {
			$('.noyhbg').show();
			$('.carBox').hide();
			$('.hasgoods').hide();
			$('.editing').hide();
		}
	}
	//取消删除
	$('.whetherbox .no').click(function() {
		$('.whetherbox').removeClass('active');
		$('.carlist').removeClass('del_list');
		$('.carlist').removeClass('alldel_list');
		$('.mask').css('z-index', '').hide();
	})

	//确定删除
	$('.whetherbox .yes').click(function() {
		var $carlist = $('.carlist');
		$('.whetherbox').removeClass('active');
		$('.mask').css('z-index', '').hide();
			
		$('.carlist.del_list').remove();
		$('.carlist.alldel_list').remove();

		delProductsByCart(delCartId, delALL);
		if (delALL) {
			$('.placeOrder').hide();
		}
		delCartId = "";
		delALL = false;
		//刷新缓存
		checkCarlist();
		goodstotal();
		initial();
	});

	//***********************规格窗口*********************************
	//弹出规格窗口
	$('.choose').click(function() {
		$("#p_spec li").remove();
		$('.standardBox').addClass('active');
		$('.mask').show();
		$(this).parents('.carlist').addClass('active');
		//商品图片
		var osrc = $('.carlist.active').find('.carpic img')[0].src;
		$('.proPic img').attr('src', osrc);
		//价格或活动价格
		var acprice = parseFloat($('.carlist.active').find('.shopprice span').text());
		//原价
		var oprice = $('.carlist.active').find('.shopprice strong').text();
		
		if(oprice != null && oprice != "" && oprice != undefined){
			oprice = oprice.substring(1, oprice.length);
			oprice = parseFloat(oprice).toFixed(2);
			
			if(oprice > 0){
				acprice= parseFloat(acprice);
				$(".norms .normsPrice").find("span").html("￥"+acprice);
				$(".norms .normsPrice").find("strong").html("￥"+oprice);
			}
		}else{
			
			acprice = parseFloat(acprice).toFixed(2);
			console.log(acprice+"--oprice");
			$(".norms .normsPrice").find("span").html("￥"+acprice);
			$(".norms .normsPrice").find("strong").html("");
		}
		//数量
		var onum = parseFloat($('.carlist.active').find('.ocompute input').val());
		$('.calGoodNums').val(onum);

		var productsize = $(this).find('span').text();
			stockCopy=$(this).attr("data-copy");
			limitNum=$(this).attr("data-limit");
			hasBuy = $(this).attr("data-hasBuy");
			restCopy=$(this).attr("data-restCopy");
			packageDesc=$(this).attr("data-package");
			if(limitNum!=null&&limitNum!=''&&parseInt(limitNum)>=1){
				$('.normsChoose').html('已选择：'+productsize+'(限购'+limitNum+'件)');
			}else{
				$('.normsChoose').html('已选择：'+productsize);
			}
			
			if(stockCopy!=null&&stockCopy!=''){
				if(parseInt(stockCopy)>=1){
					$("#stockCopy").html("库存："+stockCopy+"件");  
//						$("#buyForm").show();
				}else{
					$("#stockCopy").html("已秒完");
//						$("#buyForm").hide();
				}
			}
		var productId = $(this).attr("lang");

		//根据商品ID动态查询商品规格	
		if (null == productId || "" == productId) {
			showvaguealert('参数错误!');
			return false;
		}
		if(packageDesc!=null&&packageDesc!=''){
			$("#packageDesc").html("<label>规格说明：</label>"+packageDesc);
		}else{
			$("#packageDesc").html("");
		}
		
		$("#productId").val(productId);
		$("#extensionType").val($(this).attr("extension-type"));
		$("#carId").val($(this).parents('.carlist').attr("id"));

		$.ajax({
			url : mainServer+'/weixin/prepurchase/queryProductSpecAllJson',
			type : 'post',
			dataType : 'json',
			data : {
				'productId' : productId
			},
			success : function(data) {
				if (data.result == 'succ') {
					$.each(data.list,function(i, ele) {
						if (ele.activity_price != null&& ele.activity_price > 0) {
							var active = '';
							if (productsize == ele.spec_name) {
								active = 'active';
							} 
							buffer.append('<li value="'+ele.activity_price+'" data-orgPrice="'+ele.spec_price+'" id="'+ele.spec_id+'" data-copy="'+ele.stock_copy+'" data-limit="'+ele.limit_max_copy+'"  data-hasBuy="'
									+ele.hasBuy_copy+'" data-restCopy="'+ele.rest_copy+'" data-carNums="'+ele.carNums+'"  class="'+active+'">'+ ele.spec_name+ '</li>');
							
						} else if (ele.discount_price != null && ele.discount_price > 0){
							var active = '';
							if (productsize == ele.spec_name) {
								active = 'active';
							} 
							buffer.append('<li value="'+ele.discount_price+'" data-orgPrice="'+ele.spec_price+'" id="'+ele.spec_id+'" data-copy="'+ele.stock_copy+'" data-limit="'+ele.limit_max_copy+'"  data-hasBuy="'
									+ele.hasBuy_copy+'" data-restCopy="'+ele.rest_copy+'" data-carNums="'+ele.carNums+'"  class="'+active+'">'+ ele.spec_name+ '</li>');
							
						} else {
							var active = '';
							if (productsize == ele.spec_name) {
								active = 'active';
							} 
							buffer.append('<li value="'+ele.spec_price+'" id="'+ele.spec_id+'" data-copy="'+ele.stock_copy+'" data-limit="'+ele.limit_max_copy+'"  data-hasBuy="'
									+ele.hasBuy_copy+'" data-restCopy="'+ele.rest_copy+'" data-carNums="'+ele.carNums+'" class="'+active+'">'+ ele.spec_name+ '</li>');
						}
						buffer.append('<input type="hidden" id="spec_img'+ele.spec_id+'" value="'+ele.spec_img +'"/>');
					});

					$(".installBox ul").append(buffer.toString());
					buffer.clean();
				}
			}
		});
	});

	//选择规格
	$('.installBox').delegate('li', 'click', function() {
		$(this).addClass('active').siblings('li').removeClass('active');
		var standnumber = $(this).html();
		var standprice = $(this).attr("value");
		var standspecId = $(this).attr("id");
		var specValue = $(this).attr("data-orgprice");
		
		stockCopy=$(this).attr("data-copy");
		limitNum=$(this).attr("data-limit");
		hasBuy = $(this).attr("data-hasBuy");
		restCopy=$(this).attr("data-restCopy");
		
		if(limitNum!=null&&limitNum!=''&&parseInt(limitNum)>=1){
			$('.normsChoose').html('已选择：'+standnumber+'(限购'+limitNum+'件)');
		}else{
			$('.normsChoose').html('已选择：'+standnumber);
		}
		$('.proPic img').attr("src",$("#spec_img"+standspecId).val());
		if(stockCopy!=null&&stockCopy!=''){
			if(parseInt(stockCopy)>=1){
				$("#stockCopy").html("库存："+stockCopy+"件");
//					$("#buyForm").show();
			}else{
				$("#stockCopy").html("已秒完");
//					$("#buyForm").hide();
			}
		}
		if(specValue != null && specValue != ""){
			$('.normsPrice strong').text("￥"+specValue);
		}else{
			$('.normsPrice strong').text("");
		}
		
		$('.normsPrice span').text("￥"+standprice)
		$("#productSpecId").val(standspecId);
		$("#buyAmount").val($('.calGoodNums').val());

	});

	//点击隐藏规格窗口
	$('.outBox').click(function() {
		hideObj();
		isActive();
		$('.carlist').removeClass('active');
		$('.installBox li').removeClass('active');

		$(".installBox li").remove();
		clearInput();
	});

	
	//点击确定规格
	$('.sureBox').click(function() {
		//规格
		var standnumber = $('.installBox li.active').html();
		var standspecId = $('.installBox li.active').attr("id");
		var standprice = $('.installBox li.active').attr("value");
		var orgprice = $('.installBox li.active').attr("data-orgprice");
		var specImg = $('#spec_img'+standspecId).val();
		//数量
		var $numval = parseInt($('input.calGoodNums').val());
		var buyAmount = $numval;
		var $this = $('.installBox ul li.active');
		stockCopy=$this.attr("data-copy");
		limitNum=$this.attr("data-limit");
		hasBuy = $this.attr("data-hasBuy");
		restCopy=$this.attr("data-restCopy");
		//console.log(stockCopy+"--stockCopy--limitNum--"+limitNum+"---hasBuy--"+hasBuy+"--restCopy--"+restCopy+"--buyAmount-"+buyAmount);
		if(stockCopy!=null&&stockCopy!=''){
    		if(parseInt(stockCopy)<=0){
    			showvaguealert("此产品规格暂无库存");
    			return false;
    		}
    	}
    	
		if(parseInt(limitNum)>0){//限购才走这些判断
			if(parseInt(buyAmount)>parseInt(restCopy)){
    			showvaguealert("此商品限购"+limitNum+"，不可加入购物车");
    			return false;
    		}
			if(parseInt(hasBuy)>= parseInt(limitNum)){
    			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
    			return false;
			}
			if(parseInt(buyAmount)>parseInt(stockCopy)){
    			showvaguealert("库存仅剩"+stockCopy+"件");
    			return false;
    		}
			
		}
		
		hideObj();
		isActive();
		
		var carcakename=$('.carlist.active').find('.carcontent .carcakename').text();
		var extension_type=$('.carlist.active').find('.choose').attr('extension-type');
		
		var product_id=$('.carlist.active').find('.choose').attr('lang');
		var addFlag=true;
		$('.carlist').each(function(i,ele){
			if(!$(this).hasClass("active")){
				var name=$(this).find('.carcontent .carcakename').text();
				var weight=$(this).find('.carcontent .carweight').text();
				
				var extensionType=$(this).find('.choose').attr('extension-type');
				var productId=$(this).find('.choose').attr('lang');
				var spec_id = $(this).find('.choose').attr('spec-id');
				
				if(carcakename==name && standnumber==weight && product_id==productId && extension_type==extensionType){
					$("#coalition").val("2");
					$("#coalition_pid").val($(this).attr("id"));
					var buyAmount=$(this).find('.ocompute input').val();
					//添加后的总数量
					$numval=parseInt($numval)+parseInt(buyAmount);
					if(parseInt($numval)>parseInt(restCopy)){
						addFlag = false;
						showvaguealert("此商品限购"+limitNum+"，不可加入购物车");
						return false;
					}
					
					var $removeSpec = $('#' + productId + "_" + spec_id);
					var $specActive = $('.carlist.active').find('.choose');
					$specActive.attr('id', $removeSpec.attr('id'));
					$specActive.attr('spec-id', $removeSpec.attr('spec-id'));
					$specActive.attr('lang', $removeSpec.attr('lang'));
					$specActive.attr('extension-type', $removeSpec.attr('extension-type'));
					$specActive.attr('data-copy', $removeSpec.attr('data-copy'));
					$specActive.attr('data-limit', $removeSpec.attr('data-limit'));
					$specActive.attr('data-hasbuy', $removeSpec.attr('data-hasbuy'));
					$specActive.attr('data-restcopy', $removeSpec.attr('data-restcopy'));
					
					$(this).remove();
					return false;
				}
			}
		});
		if(!addFlag){
			return;
		}
		$('.carlist.active').find('.choose span').html(standnumber);
		$('.carlist.active').find('.ocompute input').val($numval);

		$('.carlist.active').find('.shopprice span').html(standprice);
		
		$('.carlist.active').find('.shopprice strong').remove();
		if(orgprice != undefined && parseFloat(orgprice) > 0){
			$('.carlist.active').find('.shopprice').append("<strong>￥"+orgprice+"</strong>");
		}
		
		$('.carlist.active').find('.carweight').html(standnumber);
		$('.carlist.active').find('img').attr("src", specImg);
		$('.carlist').removeClass('active');

		goodstotal();

		$("#productSpecId").val(standspecId);
		$("#buyAmount").val($numval);
		$("#editType").val("1");
		
		updateAmount(); //更新修改 

	});

	function isActive() {
		var oli = $('.installBox li');
		if (oli.hasClass('active')) {
			var oguige = ($('.normsChoose').text()).replace('已选择：', '');
			var oval = $('.calGoodNums').val();
			$('.etalon span').text(oguige);
			$('.etalon i').text('x' + oval);
		}

		$(".installBox li").remove();
	}

	function hideObj() {
		$('.standardBox').removeClass('active');
		$('.mask').hide();
	}

	//提示弹框
	function showvaguealert(con) {
		$('.vaguealert').show();
		$('.vaguealert').find('p').html(con);
		setTimeout(function() {
			$('.vaguealert').hide();
		}, 2000);
	}



	//增加数量
	$('.calAdd').click(function() {
		var buyAmount=$('.calGoodNums').val();
		var outli = $('.installBox li'); //选择规格之后才可以点击数量
		if (outli.hasClass('active')) {
			var $this = $('.installBox ul li.active');
			stockCopy=$this.attr("data-copy");
			limitNum=$this.attr("data-limit");
			hasBuy = $this.attr("data-hasBuy");
			restCopy=$this.attr("data-restCopy");
			var maxBuy = limitNum;
			if(stockCopy!=null&&stockCopy!=''){
	    		if(parseInt(stockCopy)<=0){
	    			showvaguealert("此产品规格暂无库存");
	    			return false;
	    		}
	    	}
	    	if(parseInt(buyAmount)>=parseInt(stockCopy)){
    			showvaguealert("库存仅剩"+stockCopy+"件");
    			return false;
    		}
	    	if(parseInt(limitNum)>=1){//大于0才限购
	    		if(parseInt(restCopy)<=0){//剩余购买数量
					showvaguealert("此产品限购"+maxBuy+"份");
	    			return false;
	    		}
	    		
				if(parseInt(hasBuy)>= parseInt(limitNum)){
	    			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
	    			return false;
				}
	    		if(parseInt(hasBuy)>0&&parseInt(buyAmount)>parseInt(restCopy)){
	    			showvaguealert("您已经购买了"+hasBuy+"件，可再购买"+restCopy+"件");
	    			return false;
	    		}
	    		if(parseInt(buyAmount)>parseInt(restCopy)){
	    			showvaguealert("此产品限购"+maxBuy+"份");
	    			return false;
	    		}
	    		
	    		if(parseInt(buyAmount)>=1){
	    				if(parseInt(maxBuy)-parseInt(hasBuy)<=parseInt(buyAmount)){
		    				showvaguealert("此产品限购"+maxBuy+"件,最多可再购买"+restCopy+"件");
			    			return false;
		    			}
	    			
	    		}
	    	}
			var $numval = parseInt($(this).prev('input.calGoodNums').val());
			$(this).siblings('.calGoodNums').val($numval + 1);
			
		} else {
			showvaguealert('请先选择规格');
		}
	});

	//减少数量
	$('.calCut').click(function() {
		var outli = $('.installBox li'); //选择规格之后才可以点击数量
		if (outli.hasClass('active')) {
			var $numval = parseInt($(this).next('input').val());
			if ($numval <= 2) {
				$numval = 2;
			}
			$(this).siblings('.calGoodNums').val($numval - 1);
			//$(this).find('.ocompute input').val()
		} else {
			showvaguealert('请先选择规格');
		}
	});
	
	
	//***************************      删除购物车中的商品   ******************************************
	function delProductsByCart(cartIds, allOrOthers) {
		if (cartIds.split(",").length > 0 && (allOrOthers == true || allOrOthers == false)) {
			$.ajax({
				url : mainServer+'/weixin/shoppingcart/deleteShoppingCarById',
				type : 'post',
				dataType : 'json',
				async : false,
				data : {"cartId" : cartIds,"flag" : allOrOthers},
				success : function(data) {
					if ("succ" == data.result) {
						showvaguealert(data.msg);
					}
				},
				error : function(data) {
					showvaguealert('出错了');
				}
			});
		} else {
			showvaguealert('参数错误');
		}
	}

	//***************************     修改购物车中的商品   ******************************************
	function updateAmount() {
		var carId = $("#carId").val();
		var extensionType = $("#extensionType").val();
		var productSpecId = $("#productSpecId").val();
		var buyAmount = $("#buyAmount").val();
		var editType = $("#editType").val();
		var coalition=$("#coalition").val();
		var coalition_pid=$("#coalition_pid").val();
		
		clearInput(); //清空
		if(editType==1){
			if (null != carId && ''!=carId &&  null!= productSpecId && 
				''!= productSpecId && null!= buyAmount && ''!= buyAmount) {

				$.ajax({
					url : mainServer+'/weixin/shoppingcart/updateAmount',
					type : 'post',
					dataType : 'json',
					async:false,
					data : {
						"cartId" : carId,
						"extensionType" : extensionType,
						"productSpecId" : productSpecId,
						"amount" : buyAmount,
						"editType":editType,
						"coalition":coalition,
						"coalition_pid":coalition_pid
					},
					success : function(data) {
						if ("succ" != data.result) {
							showvaguealert(data.msg);
						}else{
							showvaguealert(data.msg);
						}
					},
					error : function(data) {
						showvaguealert('出错了');
						return false;
					}
				});
			} else {
				showvaguealert('不能为空！');
				return false;
			}
		
		}else if(editType==2){
			if (null != carId && ''!=carId  && null!= buyAmount && ''!= buyAmount) {
				
				$.ajax({
					url : mainServer+'/weixin/shoppingcart/updateAmount',
					type : 'post',
					dataType : 'json',
					data : {
						"cartId" : carId,
						"amount" : buyAmount,
						"editType":editType
					},
					success : function(data) {
						if ("succ" != data.result) {
							showvaguealert(data.msg);
						}
					},
					error : function(data) {
						showvaguealert('出错了');
						return false;
					}
				});
			} else {
				showvaguealert('不能为空！');
				return false;
			}
		}else{
			showvaguealert('非法参数！');
			return false;
		}
	}
	
	//去付款
	$(".orderRight").click(function(){
		
		if(null==mobile || ""==mobile){
			window.location.href = mainServer+"/weixin/tologin";
			return false;
		}
		
		var cartIds = "";
		$(".carlist").each(function(){
			var prod = $(this).find("input[type=checkbox]")[0];
			if (prod.checked) {
				cartIds += $(prod).attr("value") + ",";
			}
		});
		
		
		if(cartIds.length>0){
			cartIds = cartIds.substring(0, cartIds.lastIndexOf(",")); //购物车中的商品
			$.ajax({
				url:mainServer+'/weixin/shoppingcart/vilidateLimit?cartIds='+cartIds,
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.code !="1010"){
						showvaguealert(data.msg);
					}else if (data.code == "1011"){
						var url = window.location.href;
						if(url.indexOf("#")!=-1){
							url = url.substring(0,url.indexOf("#"));
						}
						window.location.href = mainServer+"/weixin/tologin?backUrl=" + url;
					}else{
						window.location.href = mainServer+'/weixin/order/addBuyOrders?isNew=1&cartIds='+cartIds;
					}
				},
				failure:function(data){
					showvaguealert('出错了');
				}
			});
			
			
		}else{
			showvaguealert('请至少选择一款商品');
			return false;
		}
	});
	

	function clearInput() {
		$("#carId").val('');
		$("#productId").val('');
		$("#extensionType").val('');
		$("#productSpecId").val('');
		$("#buyAmount").val('');
		$("#editType").val('');
		$("#coalition").val('');
		$("#coalition_pid").val('');
	}

});



function findProduct(product_id, activity_type, activity_id) {
	if (activity_type != null && activity_type == 2) {
		window.location.href = mainServer+"/weixin/product/toLimitGoodsdetails?specialId="
				+ activity_id + "&productId=" + product_id;
	} else {

		window.location.href = mainServer+"/weixin/product/towxgoodsdetails?productId="
				+ product_id;
	}

}





$(function() {
	queryLikeProducts();
});

// 提示弹框
function showvaguealert(con) {
	$('.mask').show();
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
		$('.mask').hide();
	}, 2000);
}

function queryLikeProducts() {
	$.ajax({
		url : mainServer+'/weixin/product/queryLikeProducts',
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				if (data.data == null || data.data.length <= 0) {
					return;
				}
				var list = data.data;
				for ( var continueIndex in list) {
					if (isNaN(continueIndex)) {
						continue;
					}
					var productVo = list[continueIndex];

					var shareExplain = "";
					if (productVo.share_explain != null) {
						shareExplain = productVo.share_explain;
						if (shareExplain.length > 25) {
							shareExplain = shareExplain
									.substring(0, 25)
									+ "...";
						}
					}
					var showLabel = "";
					if (productVo.labelName != null
							&& productVo.labelName != "") {
						showLabel = "<p>" + productVo.labelName
								+ "</p>";
					}
					var local = mainServer+"/weixin/product/towxgoodsdetails?productId="
							+ productVo.product_id;
					if (productVo.activityType == 2) { // 限量抢购
						local = mainServer+"/weixin/product/toLimitGoodsdetails?productId="
								+ productVo.product_id
								+ "&specialId="
								+ productVo.activity_id;
					} else if (productVo.activityType == 3) { // 团购
						local = mainServer+"/weixin/product/toGroupGoodsdetails?specialId="
								+ productVo.activity_id
								+ "&productId="
								+ productVo.product_id;
					}
					var showMoneyLine = "";
					if (productVo.activityPrice != null
							&& productVo.activityPrice != ""
							&& parseFloat(productVo.activityPrice) >= 0) {
						showMoneyLine = "<span>￥"
								+ checkMoneyByPoint(productVo.activityPrice)
								+ "<strong>￥"
								+ checkMoneyByPoint(productVo.price)
								+ "</strong></span>";
					} else if (productVo.discountPrice != null
							&& productVo.discountPrice != ""
							&& parseFloat(productVo.discountPrice) >= 0) {
						showMoneyLine = "<span>￥"
								+ checkMoneyByPoint(productVo.discountPrice)
								+ "<strong>￥"
								+ checkMoneyByPoint(productVo.price)
								+ "</strong></span>";
					} else {
						showMoneyLine = "<span>￥"
								+ checkMoneyByPoint(productVo.price)
								+ "</span>";
					}
					var showHtml = '<div class="tuijian" onclick="location.href=\''
							+ local
							+ '\'">'
							+ ' 		<div class="left">'
							+ '		<img src="'
							+ productVo.product_logo
							+ '" alt="" />'
							+ '	</div>'
							+ '	<div class="right">'
							+ '		<div class="tjname">'
							+ '		<span  id="name'
							+ productVo.product_id
							+ '">'
							+ productVo.product_name
							+ '</span>'
							+ showLabel
							+ '		</div>'
							+ '		<div class="tjcent">'
							+ shareExplain
							+ '		</div>'
							+ '		<div class="tjmoney">'
							+ showMoneyLine
							+ '			<p>销量'
							+ productVo.salesVolume
							+ '份</p>'
							+ '		</div>' + '	</div>' + '</div>'

					$("#tuijianId").append(showHtml);
					if (showLabel != null && showLabel != ""
							&& showLabel.length > 0) {
						$("#name" + productVo.product_id).css("width",
								"2rem")
					} else {
						$("#name" + productVo.product_id).css("width",
								"3.7rem")
					}
				}
				;

			} else {
				showvaguealert("出现异常");
			}
		},
		failure : function(data) {
			showvaguealert('出错了');
		}
	});
}

// 修改微信自带的返回键
$(function(){
	var url=mainServer+'/weixin/index';
		
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
