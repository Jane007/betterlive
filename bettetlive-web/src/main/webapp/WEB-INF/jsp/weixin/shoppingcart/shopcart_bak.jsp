<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html manifest="IGNORE.manifest">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/shopCar.css" />
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css" />
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-购物车</title>
	</head>
	

	<body>
		<input type="text" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script type="text/javascript">
			//每次webview重新打开H5首页，就把server time记录本地存储
			var SERVER_TIME = document.getElementById("SERVER_TIME"); 
			var REMOTE_VER = SERVER_TIME && SERVER_TIME.value;
			if(REMOTE_VER){
			    var LOCAL_VER = sessionStorage && sessionStorage.PAGEVERSION;
			    if(LOCAL_VER && parseInt(LOCAL_VER) >= parseInt(REMOTE_VER)){
			        //说明html是从本地缓存中读取的
			        location.reload(true);
			    }else{
			        //说明html是从server端重新生成的，更新LOCAL_VER
			        sessionStorage.PAGEVERSION = REMOTE_VER;
			    }
			}
		</script>
		<div class="container">
			<div class="header">
				<img src="${resourcepath}/weixin/img/huihuo-logo.png" alt="" />
				<span class="backPage"></span>
				<div class="editing">编辑</div>
			</div>
			<div class="hasgoods">
				<div class="carBox">
					<c:forEach items="${listShoppingCart}" var="l">
						<div class="carlist" id="${l.cart_id }">
							<div class="checklist">
								<span class=""> 
									<input type="checkbox" value="${l.cart_id }" />
								</span>
							</div>
							<div class="carpic">
								<img src="${l.spec_img }" alt="" />
							</div>
							<div class="carcontent">
								<p class="carcakename">${l.product_name}</p>
								<p class="carweight">${l.spec_name}</p>
								<div class="shopprice">
									<i>￥</i>
									<%-- 判断是否存在优惠价格，存在就显示优惠价 --%>
									<c:choose>
										<c:when test="${l.activity_price !=null }">
											<span>${l.activity_price}</span>
										</c:when>
										<c:otherwise>
											<span>${l.spec_price}</span>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="shopnumber">
									x<span>${l.amount}</span>
								</div>
							</div>
							<div class="chNorms">
								<div class="choose" lang="${l.product_id}" id="${l.extension_type}">
									已选择：<span>${l.spec_name}</span>
								</div>
								<div class="computebox">
									<span class="ocompute" >
										<i class="cut">-</i>
											<input type="text" value="${l.amount}" readonly="readonly" />  
										<i class="add">+</i>
									</span>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			
			<div class="nogoods">
				<img src="${resourcepath}/weixin/img/no-goods.png" alt="" /> 
				<a href="javascript:void(0)" onclick="toWXIndex()">去首页</a>
			</div>
	
			<div class="placeOrder">
				<div class="orderLeft">
					<div class="allCheck">
						<span class=""> <input type="checkbox" checked="checked" />
						</span> <em>已选择（<i>0</i>）
						</em>
					</div>
					<div class="totalSum">￥0.00</div>
				</div>
				<div class="orderRight">下单</div>
				<div class="delAll">删除所选</div>
	
				<input type="hidden" id="carId" name="carId" readonly="readonly" /> 
				<input type="hidden" id="productId" name="productId" readonly="readonly" />
				<input type="hidden" id="extensionType" name="extensionType" readonly="readonly" /> 
				<input type="hidden" id="productSpecId" name="productSpecId" readonly="readonly" />
				<input type="hidden" id="buyAmount" name="buyAmount" readonly="readonly" />
				<input type="hidden" id="editType" name="editType" readonly="readonly" />
				
				<input type="hidden" id="coalition" name="coalition" readonly="readonly" />
				<input type="hidden" id="coalition_pid" name="coalition_pid" readonly="readonly" />
			</div>
		</div>
		
		<div class="mask"></div>
		<div class="vaguealert">
			<p>绑定成功</p>
		</div>
	
		<!-- 选择商品规格与价格 -->
		<div class="standardBox">
			<div class="standTop">
				<div class="proPic">
					<img src="${resourcepath}/weixin/img/banner2.jpg" alt="" />
				</div>
				<div class="norms">
					<p class="normsPrice">
						<label for="">价格：</label> ¥ <span></span>
					</p>
					<p class="normsChoose">请选择规格</p>
				</div>
			</div>
			<div class="installBox">
				<label for="">规格</label>
				<ul>
	
				</ul>
			</div>
			<div class="comNumber">
				<label for="">数量</label>
				<div class="calculate">
					<i class="calCut">-</i> 
						<input type="text" readonly="readonly" value="1" class="calGoodNums" /> 
					<i class="calAdd">+</i>
				</div>
			</div>
			<div class="sureBox">确定</div>
			<div class="outBox">
				<img src="${resourcepath}/weixin/img/outbox.png" alt="" />
			</div>
		</div>
		<div class="whetherbox">
        	<p></p>
        	<div class="key">
        		<span class="no">取消</span>
        		<span class="yes">确定</span>
        	</div>
        </div>
	</body>

	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>

	<script type="text/javascript">
		var StringBuffer = Application.StringBuffer;
		var buffer = new StringBuffer();
	
		$(function() {
			/*
			var isFirst = $('#isFirst').val();
			alert(isFirst);
			if (isFirst) {
			   	var parent = document.getElementById("isFirstDiv");
			   	var isFirst = document.getElementById("isFirst");
			    parent.removeChild(isFirst);
				//$('#isFirst').remove();
			} else {
				window.location.reload();
				alert('重新加载');
			}
			*/
			//后退
			$('.backPage').click(function(){
				window.history.go(-1);
			});
			
			//如果没有商品*********************
			nogoods();
			function nogoods() {
				var carlist = $('.carlist');
				if (carlist.length > 0) {
					$('.hasgoods').show();
					$('.nogoods').hide();
				} else {
					$('.hasgoods').hide();
					$('.nogoods').show();
				}
			}
			
			//初始化商品选中状态
			var buyIdStr = sessionStorage.getItem("buyIds");
			console.log(buyIdStr);
			var cartIdStr = sessionStorage.getItem("cartIds");
			console.log(cartIdStr);
			
			function initCheck() {
				if (buyIdStr) {
					var ids = buyIdStr.split(',');
					for(var i = 0; i < ids.length; i++) {
						$('#'+ids[i]).find('.checklist').children().eq(0).addClass('on');
						$('#'+ids[i]).find('.checklist').find('input[type=checkbox]').attr("checked", true);
					}
					
					$(".carlist").each(function(i, ele){
						var id = $(ele).attr('id');
						if ($.inArray(id, ids) == -1) {  //该商品没有被选中
							$('#'+id).find('.checklist').children().eq(0).removeClass('on');
							$('#'+id).find('.checklist').find('input[type=checkbox]').attr("checked", false); 
						}
					});
				} else if (cartIdStr) {
					var cIds = cartIdStr.split(',');
					$(".carlist").each(function(i, ele){
						var id = $(ele).attr('id');
						if ($.inArray(id, cIds) == -1) {  //页面中没有该商品
							 $('#'+id).remove();  //将该商品移除
							 return true;
						}
						if ($.inArray(id, buyIdStr) != -1) {  //在购买列表里
							$('#'+ids[i]).find('.checklist').children().eq(0).addClass('on');
							$('#'+ids[i]).find('.checklist').find('input[type=checkbox]').attr("checked", true);
						}
					});
				} else {
					$(".carlist").each(function(i, ele){
						var id = $(ele).attr('id');
						$('#'+id).find('.checklist').children().eq(0).addClass('on');
						$('#'+id).find('.checklist').find('input[type=checkbox]').attr("checked", true); 
					});
				}
			}
			
			//初始化选中
			initCheck();
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
				var endCount=$numval+1;
				$(this).prev('input').val(endCount);
				$(this).parents('.carlist').find('.shopnumber span').text($(this).prev('input').val());
				goodstotal();
				
				$("#editType").val("2");
				$("#carId").val($(this).parents('.carlist').attr("id"));
				$("#buyAmount").val(endCount);
				updateAmount();
				
			});
			
			//***************** END  ***********************  编辑时增加或者减少商品数量   ************** END ***********************************
			
			
			//合计金额
			function goodstotal() {
				var $carlist = $('.carlist');
				var bum = 0;
				var totnum = 0;
				for (var i = 0; i < $carlist.length; i++) {
					var prod = $carlist.eq(i).find(".checklist input[type=checkbox]").is(":checked");
					if (prod) {
						var price = ($carlist.eq(i).find(".shopprice span").text());
						var num = $carlist.eq(i).find(".ocompute input").val();
						bum += parseFloat((price * num).toFixed(2));
						totnum += parseFloat(num);
					}
				}
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
	
				delProductsByCart(delCartId, delALL)
				delCartId = "";
				delALL = false;
				
				//刷新缓存
	
				goodstotal();
				nogoods();
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
				//价格
				var oprice = parseFloat($('.carlist.active').find('.shopprice span').text());
				$('.normsPrice span').text(oprice);
				//数量
				var onum = parseFloat($('.carlist.active').find('.ocompute input').val());
				$('.calGoodNums').val(onum);
	
				var productsize = $(this).find('span').text();
				$('.normsChoose').text('已选择：' + productsize);
	
				var productId = $(this).attr("lang");
	
				//根据商品ID动态查询商品规格	
				if (null == productId || "" == productId) {
					showvaguealert('参数错误!');
					return false;
				}
	
				$("#productId").val(productId);
				$("#extensionType").val($(this).attr("id"));
				$("#carId").val($(this).parents('.carlist').attr("id"));
	
				$.ajax({
					url : '${mainserver}/weixin/prepurchase/queryProductSpecAllJson',
					type : 'post',
					dataType : 'json',
					data : {
						'productId' : productId
					},
					success : function(data) {
						if (data.result == 'succ') {
							$.each(data.list,function(i, ele) {
								if (ele.discount_price != null&& ele.discount_price > 0) {
									if (productsize == ele.spec_name) {
										buffer.append('<li value="'+ele.discount_price+'" id="'+ele.spec_id+'"  class="active">'+ ele.spec_name+ '</li>');
									} else {
										buffer.append('<li value="'+ele.discount_price+'" id="'+ele.spec_id+'">'+ ele.spec_name+ '</li>');
									}
								} else {
									if (productsize == ele.spec_name) {
										buffer.append('<li value="'+ele.spec_price+'" id="'+ele.spec_id+'" class="active">'+ ele.spec_name+ '</li>');
									} else {
										buffer.append('<li value="'+ele.spec_price+'" id="'+ele.spec_id+'">'+ ele.spec_name+ '</li>');
									}
								}
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
	
				$('.normsChoose').html('已选择：' + standnumber);
				$('.normsPrice span').text(standprice)
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
	
				//数量
				var $numval = parseInt($('input.calGoodNums').val());
	
				hideObj();
				isActive();
				
				var carcakename=$('.carlist.active').find('.carcontent .carcakename').text();
				var extension_type=$('.carlist.active').find('.choose').attr('id');
				
				var product_id=$('.carlist.active').find('.choose').attr('lang');
				$('.carlist').each(function(i,ele){
					if(!$(this).hasClass("active")){
						var name=$(this).find('.carcontent .carcakename').text();
						var weight=$(this).find('.carcontent .carweight').text();
						
						var extensionType=$(this).find('.choose').attr('id');
						var productId=$(this).find('.choose').attr('lang');
						
						if(carcakename==name && standnumber==weight && product_id==productId && extension_type==extensionType){
							$("#coalition").val("2");
							$("#coalition_pid").val($(this).attr("id"));
							var buyAmount=$(this).find('.ocompute input').val();
							$numval=parseInt($numval)+parseInt(buyAmount);
							$(this).remove();
							return false;
						}
					}
				});
					
				$('.carlist.active').find('.choose span').html(standnumber);
				$('.carlist.active').find('.ocompute input').val($numval);
	
				$('.carlist.active').find('.shopprice span').html(standprice);
				$('.carlist.active').find('.carweight').html(standnumber);
	
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
	
			//价格
			var oprice = $('.productPrice span').text();
			$('.normsPrice span').text(oprice)
	
			//增加数量
			$('.calAdd').click(function() {
				var outli = $('.installBox li'); //选择规格之后才可以点击数量
				if (outli.hasClass('active')) {
					var $this = $(this);
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
				} else {
					showvaguealert('请先选择规格');
				}
			});
			
			
			//***************************      删除购物车中的商品   ******************************************
			function delProductsByCart(cartIds, allOrOthers) {
				if (cartIds.split(",").length > 0 && (allOrOthers == true || allOrOthers == false)) {
					$.ajax({
						url : '${mainserver}/weixin/shoppingcart/deleteShoppingCarById',
						type : 'post',
						dataType : 'json',
						async : false,
						data : {"cartId" : cartIds,"flag" : allOrOthers},
						success : function(data) {
							if ("succ" == data.result) {
								showvaguealert(data.msg);
								
								var delIds = cartIds.split(',');
								var newCarts = new Array();
								var cartIdStr = sessionStorage.getItem("cartIds");
								if (cartIdStr) {
									var cartIdArr = cartIdStr.split(',');
									$.each(cartIdArr, function(i, d) { //遍历购物车缓存
										if ($.inArray(d, delIds) == -1) {  //没有删除商品
											newCarts.push(d);
										}
									});
									sessionStorage.setItem("cartIds", newCarts.join(','));
								}
								
								var buyIdStr = sessionStorage.getItem("buyIds");
								var newBuys = new Array();
								if (buyIdStr) {
									var buyIdArr = buyIdStr.split(',');
									$.each(buyIdArr, function(i, d) { //遍历购买缓存
										if ($.inArray(d, delIds) == -1) {  //删除的不在购买的列表里
											newBuys.push(d);
										}
									});
									sessionStorage.setItem("buyIds", newBuys.join(','));
								}
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
							url : '${mainserver}/weixin/shoppingcart/updateAmount',
							type : 'post',
							dataType : 'json',
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
							url : '${mainserver}/weixin/shoppingcart/updateAmount',
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
				var buyIds = "";
				var cartIds = "";
				$(".carlist").each(function(){
					var prod = $(this).find("input[type=checkbox]")[0];
					if (prod.checked) {
						buyIds += $(prod).attr("value") + ",";
					}
					cartIds += $(prod).attr("value") + ",";
				});
				
				if(buyIds.length>0){
					buyIds = buyIds.substring(0, buyIds.lastIndexOf(",")); //已经选择下单的商品
					sessionStorage.setItem('buyIds', buyIds);
					cartIds = cartIds.substring(0, cartIds.lastIndexOf(",")); //购物车中的商品
					sessionStorage.setItem('cartIds', cartIds);
					window.location.href = '${mainserver}/weixin/order/addBuyOrders?cartIds='+buyIds;
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
		
		function toWXIndex() {
			window.location.href = "${mainserver}/weixin/index";
		}
	</script>

</html>