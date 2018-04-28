<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="r" uri="http://www.kingleadsw.com/taglib/replace" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection">
	<meta name="keywords" content="新品预售,挥货,挥货商城,电商平台" /> 
	<meta name="description" content="挥货新品预售活动为您网罗最新，最全地道好货" /> 
	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    <link rel="stylesheet" href="${resourcepath}/weixin/css/goodsdetails.css" />
    <link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css"/>
	<script src="${resourcepath}/weixin/js/rem.js"></script>
	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<title>挥货-新品预售</title>
	<style type="text/css">
		.exampleBox img {
			max-width: 100%;
		}
	</style>
	<script type="text/javascript">
   		var mainServer = '${mainserver}';
   		
   		
   		
   		var img = $('.swiper-wrapper').find('.swiper-slide:first').find('img').attr('src');
		var title = '挥货 - ${productInfo.product_name}';
		var shareExplain = '${productInfo.share_explain}';
		var desc = "挥货美食星球，贩卖星际各处的美食，覆盖吃货电波，一切美食都可哔哔！";
		if(shareExplain!=null && shareExplain!=""){
			desc = shareExplain;
		}
		
		//var link = '${mainserver}/weixin/index';
		var imgUrl = img;
		
		
		var mobile='${mobile}';
		var stockCopy=0; //库存
		var specLen = $('.installBox ul li').length;  //商品规格数量
		var limitNum=-1;		//限量
		var hasBuy=0;        //已经购买的份数
		var restCopy=100;	//剩余购买数量
		var carNums= 0;
		var carCanAdd = 0;	//购物车还可以添加多少份
		var packageDesc=''; //套餐说明
		
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
			<span class="backPage"></span>		
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
		<div class="mainBox">
			<div class="swiper-container">
				<div class="swiper-wrapper">
					<c:forEach items="${productInfo.pictures }" var="pic">
						<div class="swiper-slide">
							<a href=""><img src="${pic.original_img }" alt="" /></a>
						</div>
					</c:forEach>
				</div>
				<div class="purchaseMark">
					预售商品
				</div>
				<div class="swiper-pagination"></div>
			</div>
			<div class="goodsContent">
				<div class="goodsInfor">
					<div class="variety">
						<div class="productName">
							${productInfo.product_name}${names}
						</div>
						<div class="productPrice">
							<c:if test="${productInfo.listSpecVo[0].activity_price !=null && productInfo.listSpecVo[0].activity_price !=''}">
								<span>¥${productInfo.listSpecVo[0].activity_price}</span>
								
								<del>¥${productInfo.listSpecVo[0].spec_price}</del>
							</c:if>
							
							<c:if test="${productInfo.listSpecVo[0].activity_price ==null || productInfo.listSpecVo[0].activity_price ==''}">
								<span>¥${productInfo.listSpecVo[0].spec_price}</span>
							</c:if>
						</div>
					</div>
					<div class="crowdFunding">
				     	<div class="barBox">
							<span id="bar" class="bar"></span>
						</div>
						<input type="hidden" id="raiseMoney1" value="${preProduct.raiseMoney}">
						<input type="hidden" id="raiseTarget1" value="${preProduct.raiseTarget}">
						<div class="crowData">
							<div class="crowSum">
								<label for="">已筹金额</label>
								<p>￥${preProduct.raiseMoney/10000}万</p>
							</div>
							<div class="supportNum">
								<label for="">支持人数</label>
								<p>${preProduct.supportNum}</p>
							</div>
							<div class="residueTime">
								<label for="">剩余时间</label>
								<p>${preProduct.raiseTime}</p>
							</div>
							<div class="objective">
								<label for="">预售目标</label>
								<p>${preProduct.raiseTarget/10000}万</p>
							</div>
						</div>
					</div>
					<ul>
						<li class="etalon">
							<label for="">请选择规格数量<span></span> <i></i></label>
							<em></em>
						</li>
					
						<li >
							<label for="" style="color: #333333;font-size: 0.24rem">发货日期：<span id="spanx1"></span> <i></i></label>
						</li>
						<c:if test="${productInfo.activity_id>0}">
							<li class="sales">
								<!-- <label for="">活动促销：<span>春季换新，限时特价优惠</span></label> -->
								
								<label for="">活动促销：<span>${productInfo.activity_name}</span></label>
								<em></em>
							</li>
						</c:if>
					</ul>
					<div class="reminder">
						温馨提示：${productInfo.prompt}
					</div>
				</div>
				<div class="parameterBox">
					<div class="parTitle">
						产品参数
					</div>
					<ul>
						<c:forEach items="${productInfo.paramAndValue}"  var="pv"  >
							<c:set var="params" value="${fn:split(pv,'&#&$')[0] }"></c:set>
				    		<c:set var="values" value="${fn:split(pv,'&#&$')[1] }"></c:set>
							
							<li>
								<label for="">${params}：</label>
								<span>${values}</span>
							</li>
						</c:forEach>
					</ul>
				</div>
			
				<div class="evaluateBox">
					<div class="evaTitle">
						评价<em>（${fn:length(comments)}）</em>	
					</div>
					<c:if test="${fn:length(comments)>0 }">
					<div class="messageBox">
						<div class="mesList">
							<div class="userPic">
								<img src="${comments[0].customerVo.head_url }" alt="" />
							</div>
							<div class="mesInfor">
								<div class="userName">
									<c:if test="${comments[0].customerVo.nickname!=null && comments[0].customerVo.nickname!=''}">
										<r:replace str="${comments[0].customerVo.nickname}" mark="*"/>
									</c:if>
									
								</div>
								<div class="mesTime">
									${comments[0].create_time}
								</div>
								<div class="mesCon">
									${comments[0].content }
								</div>
							</div>
						</div>
					</div>
					</c:if>
				</div>
				
				<div class="exampleBox">
					${productInfo.introduce}						
				</div> 
				
				<div class="recommendBox">
					<div class="recommendTitle">
						<div class="likeBox">
							<span>猜你喜欢</span>
						</div>
					</div>
					<div class="goodsListBox">
						<c:forEach items="${listProduct}" var="list" varStatus="status">
							<div class="goodsList">
								<a href="${mainserver }/weixin/product/towxgoodsdetails?productId=${list.product_id}">
									<div class="imgsrc" data-src = "${list.product_logo}"></div>
									<p class="goodsName">${list.product_name} ${list.listSpecVo[0].spec_name}</p>
									
									<c:choose>
										<c:when test="${list.listSpecVo[0].activity_price !=null }">
											<p class="goodsPrice">¥${list.listSpecVo[0].activity_price}</p>
										</c:when>
										<c:otherwise>
											<p class="goodsPrice">¥${list.listSpecVo[0].spec_price}</p>
										</c:otherwise>
									</c:choose>
								</a>
							</div>
						</c:forEach>
					</div>
				</div>
		</div>
		<form id="buyForm" action="${mainserver}/weixin/order/addBuyOrder" method="post">		
			<div class="botShop">
			
				<c:if test="${!empty isActive}">
					<a href="javascript:void(0)" class="newBuy" onclick="toAddShotCar(1)">立即购买</a>
					<a href="javascript:void(0)" class="addShopCar" onclick="toAddShotCar(2)">加入购物车</a>
				</c:if>
					<input type="hidden"  id="productId"  name="productId"  value="${productInfo.product_id}"  readonly="readonly"/>
					<input type="hidden"  id="extension_type"  name="extension_type"  value="${extension_type}" readonly="readonly"/>
					<input type="hidden"  id="productSpecId"  name="productSpecId"  readonly="readonly"/>
					<input type="hidden"  id="isNew"  name="isNew"  value="1" readonly="readonly"/>
					<input type="hidden"  id="buyAmount"  name="buyAmount"  readonly="readonly"/>
				
			</div>
		</form>
	</div>
	</div>
	
	<div class="backTop"></div>
	<div class="mask"></div>
	<div class="dia-mask"></div>
	
	<div class="vaguealert">
		<p>绑定成功</p>
	</div>
	
	<div class="standardBox acitve">
		<div class="standTop">
			<div class="proPic">
				<c:choose>
					<c:when test="${!empty productInfo.listSpecVo}">
						<img id="spec-img" src="${productInfo.listSpecVo[0].spec_img}" alt="" />
					</c:when>
					<c:otherwise>
						<img id="spec-img" src="${resourcepath}/weixin/img/no-spec.png" alt="" />
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="norms">
				<p class="normsPrice">
					<label for="">价格：</label>
					<span></span>
				</p>
				<p class="inventory" id="stockCopy">
					
				</p>
				<p class="normsChoose">请选择规格</p>
				<p class="inventory" id="packageDesc">
				</p>
			</div>
		</div>
		<div class="installBox">
			<label for="">规格</label>
			<ul>
				<c:forEach items="${productInfo.listSpecVo}"  var="s"  >
					<c:choose>
						<c:when test="${s.activity_price !=null}">
							<li value="¥ ${s.activity_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
							data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}"
							data-carNums="${s.carNums }" data-carCanAdd="${s.carCanAdd }" data-package="${s.package_desc }"
							>${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
								
							</li>
						</c:when>
						<c:otherwise>
							<li value="¥ ${s.spec_price}" id="${s.spec_id}" data-copy="${s.stock_copy }" 
							data-limit="${s.limit_max_copy }" data-hasBuy="${s.hasBuy_copy }" data-restCopy="${s.rest_copy}"
							data-carNums="${s.carNums }" data-carCanAdd="${s.carCanAdd }" data-package="${s.package_desc }"
							>${s.spec_name}
								<input type="hidden" id="spec_img${s.spec_id}" value="${s.spec_img }"/>
								<!-- <input type="hidden" id="copy${s.spec_id}" value="${s.stock_copy }"/> -->
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="comNumber">
			<label for="">数量</label>
			<div class="calculate">
				<i class="calCut">-</i>
				<input type="text" readonly="readonly" value="1" class="calGoodNums"/>
				<i class="calAdd">+</i>
			</div>
			<div style="margin-top:10px" id="stockCopy">
				
			</div>
		</div>
		<div class="outBox">
			<img src="${resourcepath}/weixin/img/outbox.png" alt="" />
		</div>
		
		
		<div style="display: none;">
			<form id="form2" action="${mainserver}/weixin/search" method="post">
				<input  id="searchName" name="productName" readonly="readonly">
			</form>
		</div>
	</div>
	
	
	<div class="boundPhone">
		<div class="dia-title">绑定手机号码</div>
			<form id="bindingForm" action="" method="post">
				<ul>
					<li class="phoneBox">
						<label for=""></label>
						<input type="tel" name="mobile"  id="mobile"  maxlength="11" placeholder="请输入手机号码"/>
						<span class="verificationcode">获取验证码</span>
					</li>
					<li class="verifyCode">
						<label for=""></label>
						<input type="text"  name="verifycode"  id="verifycode"  maxlength="5"  placeholder="请输入验证码" />
					</li> 
				</ul>
				<div class="now-bound">绑定</div>
			</form>
		<div class="boundout"><img src="${resourcepath}/weixin/img/outbox.png" alt="" /></div>
    </div>
    
    	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/Lazyloading.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/prepurchase/wx_prepurchase_detail?t=201802271511"></script>
	<script src="${resourcepath}/weixin/js/shareToWechart.js?t=201802271456"></script>

</body>
</html>