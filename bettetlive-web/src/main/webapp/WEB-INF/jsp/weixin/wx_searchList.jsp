<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/goodLists.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-搜索结果</title>
		<style type="text/css">
			body{
				background: #fbfbf3;
			}
			.resultBox{
				width: 100%;
				box-sizing: border-box;
			}
			.no-searchResult{
			    text-align: center;
			    color: #999999;
			    font-size: 0.28rem;
		        padding: 2.6rem 0 0;
			}
			.no-searchResult img{
			    width: 1.22rem;
			    height: 1.22rem;
			    display: block;
			    margin: 0 auto 0.4rem;
			}
			.no-searchResult p{
				margin: 0.15rem 0 0 0;
			}
		</style>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
    	</script>
	</head>
	
	<body>
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="header">
				<a href="${mainserver}/weixin/index">
					<img src="${resourcepath}/weixin/img/huihuo-logo.png" alt="" />
				</a>
				<a href="javascript:void(0)" class="searchBox"></a>
				<a href="javascript:void(0)" class="shopCar">
				<span class="totNums<c:if test="${cartCnt >0}"> totNums_sp</c:if>"><c:if test="${cartCnt >0}">${cartCnt }</c:if></span>
				</a>
				<div class="search-frame">
					<span>
						<i></i>
						<input type="search" placeholder="请输入商品名称进行搜索" id="productName"/>
					</span>
					<em>取消</em>
				</div>
			</div>
			<div class="resultBox">
				<div class="goodsListBox">
				</div>
				<div class="no-searchResult">
					<img src="${resourcepath}/weixin/img/no-searchResult.png" alt="" />
					<p>没有搜索结果</p>
					<p>换个关键词搜索一下吧</p>
				</div>
			</div>
		</div>
		<div class="mask" style="z-index: 8"></div>
		<div class="dia-mask"></div>
	
		<div class="vaguealert">
			<p></p>
		</div>
		<div style="display: none;">
			<form id="form2" action="${mainserver}/weixin/search" method="post">
				<input  id="searchName" name="productName" readonly="readonly">
			</form>
		</div>
		
		<input type="hidden" name="pageCount" id="pageCount" value="">
		<input type="hidden" name="pageNow" id="pageNow" value="">
		<input type="hidden" name="pageNext" id="pageNext" value="">
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript">
		var StringBuffer = Application.StringBuffer;
		var buffer = new StringBuffer();
		var productName='${productName}';
		
		//提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},20000);
		}
		
		$(function(){
			
			
			$('.backPage').click(function(){
		    	window.history.go(-1);
				//window.location.href=mainServer+'/weixin/index';
			});
			findSome(1,8);
		});
		
		function toWxGoodDetails(pId){
			if(pId>0){                                                               
				window.location.href=mainServer+'/weixin/product/towxgoodsdetails?productId='+pId;
			}else{
				alert("参数错误");
			}
		}
		
		$('.shopCar').click(function(){
			window.location.href='${mainserver}/weixin/shoppingcart/toshoppingcar';
		});
		
		
		$(function(){
			$('.footer li').click(function(){
				$(this).addClass('active').siblings('li').removeClass('active');
			});
		});
		
		
		function findSome(pageIndex,pageSize){
			$.ajax({
				url:mainServer+'/weixin/searchProductAllJson',
				data:{productName:productName,rows:pageSize,pageIndex:pageIndex},
				dataType:"json",
				type:"POST",
				async: false,
				success:function(data){
					if (data == null || data.list == null) {
						$('.no-searchResult').show();
						return;
					}
					
					//封装分页
					   var obj = data;
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
					
					
					
					$('.no-searchResult').hide();
					$(".goodsListBox").empty();
					$.each(data.list,function(i,product){
						var price="";
						if(null!=product.listSpecVo[0].activity_price && ""!=product.listSpecVo[0].activity_price){
							price=product.listSpecVo[0].activity_price;
						}else{
							price=product.listSpecVo[0].spec_price;
						}
						
						buffer.append('<div class="goodsList">'+
											'<a href="javascript:void(0)" onclick="toWxGoodDetails('+product.product_id+')" >'+
												'<div class="imgsrc" data-src = "'+product.product_logo+'"></div>'+
												'<p class="goodsName">'+product.product_name+' '+product.listSpecVo[0].spec_name+'</p>'+
												'<p class="goodsPrice">¥'+price+'</p>'+
											'</a>'+
						  			  '</div>');
					})
					
					$(".goodsListBox").append(buffer.toString());
				}
			});
		};
		
		var loadtobottom=true;
		$(document).scroll(function(){
			totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
			if($(document).height() <= totalheight){
				if(loadtobottom==true){
					//loadtobottom=false;
					//此处为加载更多数据代码
					//alert('此处为加载更多数据');
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();
					if(parseInt(next)>1){
						findSome(next,6);
					}
					if(parseInt(next)>=parseInt(pageCount)){
						loadtobottom=false;
					}
				}
			}
		});
		
		
	</script>
</html>
