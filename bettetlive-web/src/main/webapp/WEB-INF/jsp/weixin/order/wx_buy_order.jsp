<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801061702" />
	    <link rel="stylesheet" href="${resourcepath}/weixin/css/editOrder.css?t=201801061702" />
		<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-填写订单</title>
		<script type="text/javascript">
    		var mainServer = '${mainserver}';
    		var returnType='${returnType}';
			var ttp='${ifCouponMoney}'; 
			var productPrice='${price}';      //商品总金额
			var lastPayMoney = productPrice;
			var freight='${freight}';         //运费
			var activityPrice='${totalActivityPrice}';   //活动优惠价格 
			var receiverAddress='${receiverAddress}';
			var singleCouponId='${singleCouponVo.userSingleId}';
			var productId = '${productId}';
			var spacId ='${productSpecId}';
			var orgPrice= '${totalPrice}';
			var couponId = '${couponInfo.coupon_id}';
			var fullCut = '${fullCut}';
			var coupon_money = '${couponInfo.coupon_money}';
			var couponMoney = '${singleCouponVo.couponMoney}';
			var ifCouponProIds = '${ifCouponProIds}';
			var ifCouponSpecIds = '${ifCouponSpecIds}';
			var specialId = '${specialVo.specialId}';
			var userGroupId = '${userGroupId}';
			var token ='${token}';
			var orderSource = '${orderSource}';
			var extensionType = '${extension_type}';
			var product_id = '${product.product_id}';
			var tzhuantiId = '${tzhuantiId}';
			var myIntegral = parseFloat('${myIntegral}');
			var goldDeductSpecId = '${goldDeductSpecId}';
			var minLimitIntegral = '${minLimitIntegral}';
			var maxLimitIntegral = '${maxLimitIntegral}';
			var integralDeductionRate = '${integralDeductionRate}';
			var surplusIntegral = myIntegral;
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
		<div class="initloading"></div>
			
		<input id="myAddressId" type="hidden" value="${receiverAddress.receiverId}" readonly="readonly">
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="mainBox" style="top: 0rem;">
				<div class="sitebox editcom">
					<div class="no-address" style="display: none;">
						<div class="editaddressbox">
							<div class="editaddress">
								<a href="javascript:void(0)" onclick="addNewAddress()" >新增收货地址</a>
							</div>
						</div>
					</div>
					
					<div class="has-address"  style="display: none;">
						<div class="consignee" lang="${receiverAddress.receiverId}">
							<p class="takenamebox">
								<label for="">
									${receiverAddress.receiverName}
								</label>
								<span class="takephone">${receiverAddress.mobile}</span>
								<c:if test="${receiverAddress.isDefault==1}">
									<span class="moren">默认</span>
								</c:if>
							</p>
							<p class="takeaddress">
									<label for="">
									</label>
								
								<span>${receiverAddress.address}</span>
							</p>
						</div>
						<div class="rightclick" lang="${receiverAddress.receiverId}"></div>
					</div>
					<input type="hidden" name="deilverStatus" id="deilverStatus" value="${is_deilver}"/>
					<input type="hidden" name="deilverMsg" id="deilverMsg" value="${nonDeliveryMsg}"/>
				</div>
				
				<div class="goodsBox">
					<c:forEach items="${listProduct}" var="l" varStatus="status">
						<div class="orgoodslist-wrap">
							<div class="orgoodslist noborder">
								<div class="orgoodspic">
									<img src="${l.spec_img }" alt="" />
								</div>
								<div class="orgoodscontent">
									<p class="orgoodsname">
										<span>${l.product_name}</span>
										<em style="float:right;">x${l.amount}</em>
									</p>
									<p class="orgoodsweight">${l.spec_name }</p>
									<p class="orgoodprice">
										<c:choose>
											<c:when test="${l.activity_price != null && l.activity_price != '' && l.activity_price != '-1'}">
												<span>￥${l.activity_price}</span><strong>￥${l.spec_price}</strong>
											</c:when>
											<c:when test="${l.discount_price != null && l.discount_price >= 0}">
												<span>￥${l.discount_price}</span><strong>￥${l.spec_price}</strong>
											</c:when>
											<c:otherwise>
												<span>￥${l.spec_price}</span>
											</c:otherwise>
										</c:choose>

										<c:if test="${l.deliver_status != 1}">
											<span class="fahuotime">不在配送范围内</span>
										</c:if>
									</p>
								</div>

								<input type="hidden" id="productId_${status.index }" value="${l.product_id }"  readonly="readonly" />
								<input type="hidden" id="specId_${status.index }" value="${l.spec_id }"   readonly="readonly"/>
								<input type="hidden" id="amount_${status.index }" value="${l.amount}"  readonly="readonly"/>
							</div>
							
							<c:if test="${(specialVo != null && specialVo.specialType != 3 && l.redeemSpecId != null) || (l.redeemSpecId != null)}">
								<div class="hold">
									<span>金币优惠购:</span>
									<span class="numberhold">${l.redeemDesc}</span>
									<span class="calloff" data-id="${l.redeemSpecId}" 
										data-need-integral="${l.needIntegral}" data-deductible-amount="${l.deductibleAmount}">参加</span>
								</div>
							</c:if>
						</div>
					</c:forEach>
				</div>
				
				<div class="compile editcom">
					<ul>
						<c:if test="${returnType !=  4}">
						<li id="couponLineId" class="redact" >
							<label class="coupons">
								<span id="couponSpanId"><input type="checkbox" id="card1" name="card1"/></span>
								<i>优惠券</i>
							</label>
							<span class="monright" id="couponMoney">暂无可用优惠券</span>
							
						</li>
						</c:if>
						<c:if test="${null !=userMoney && userMoney > 0}">
						<li class="gift-card">
								<label class="giftcards">
									<span id="giftCardSpanId"><input type="checkbox" id="cardRdo" name="card2"/></span>
									<i>礼品卡</i>
								</label>
								<span class="monright">余额：¥ <em>${userMoney}</em></span>
						</li>
						</c:if>
						<c:if test="${returnType !=  4}">
						<li id="singleCouponLineId" class="redact" >
							<label class="coupons">
								<span id="singleCouponSpanId"><input type="checkbox" id="card3" name="card3"/></span>
								<i>红包</i>
							</label>
							<span class="monright" id="singleCouponMoney">暂无可用红包</span>
							
						</li>
						</c:if>
						<c:if test="${(specialVo != null && specialVo.specialType != 3 && goldDeductSpecId > 0) || (goldDeductSpecId > 0)}">
						<li id="integralLineId" class="redact" style="border-bottom:0;display: none;" data-redeem-spec-id="${goldDeductSpecId}" data-calculate-money="0">
							<label id="integralLabelId" class="coupons">
								<span id="integralSpanId"><input type="checkbox" id="integralCheckId" name="integralCheckId"/></span>
								<i>金币</i>
							</label>
							<div class="warning">
								<img src="${resourcepath}/weixin/img/order_05.png" />
							</div>
							<span class="monright">
							</span>
						</li>
						</c:if>
					</ul>
				</div>
				<div class="sumBox">
					<ul>
						<li>
							<label for="">商品合计</label>
							<span>￥<i class="price">${totalPrice}</i></span> 
						</li>
						<li>
							<label for="">运费</label>
							<c:if test="${freight ==0}">
								<span><i class="freight">免运费</i></span>
							</c:if>
							<c:if test="${freight >0}">
								<span>￥<i class="freight" >${freight}</i></span>
							</c:if>
							<c:if test="${freeFreight >0}">
								<em>再买${freeFreight}元零食免邮</em>
							</c:if>
						</li>
						
							<c:if test="${totalActivityPrice > 0}">
								<li>
									<label for="">活动优惠</label>
									<span>-￥<i class="activityprice">${totalActivityPrice}</i></span>
									<c:if test="${specialVo !=null && specialVo.specialId > 0}">
										<em>${specialVo.specialName}</em>
									</c:if>
									
								</li>
							</c:if>
							<c:if test="${returnType != 4 && fullCut != null && fullCut > 0}">
								<li>
									<label for="">满减活动</label>
									<span>-￥<i class="activityprice">${fullCut}</i></span>
										<em>${salePromotion.promotionName}</em>
									
								</li>
							</c:if>
					</ul>		
				</div>
				<div class="payType" style="border-bottom:1px solid #ececec; ">
					<label class="coupons paybox">
								<span id="payweiixnId" class="on"><input type="checkbox" id="card7" name="payType" value="1" checked="checked"/></span>
								<i class='weixinimg' style='margin-left:0.1rem'>微信支付</i>
					</label> 
				
				</div>
				
				<div class="payType" style="border-bottom:1px solid #ececec; ">				
					<label class="coupons payzfbbox">  
								<span id="payzfbId"><input type="checkbox" id="card8" name="payType" value="2"/></span>
								<i class="zfbimg" style='margin-left:0.1rem'>支付宝</i>
					</label>
  
				</div>
				<div class="payType" style="border-bottom:1px solid #ececec; ">				
					<label class="coupons payywtbox">  
								<span id="payywtId"><input type="checkbox" id="card9" name="payType" value="3"/></span>
								<i class="ywtimg" style='margin-left:0.1rem'>招行一网通</i>
					</label>
  
				</div>
				<div class="mjiabox"><span>买家留言:</span><form action=""><input type="text" placeholder="您对本次交易的说明，可与挥货协商" maxlength="25"/></form></div>   
				<div class="consent">
					<span class="on"><input type="radio" checked="checked"/></span>
					<em>我已同意<a>《挥货服务协议》</a></em>
				</div>
			</div>
			<div class="botShop">
				<a href="javascript:void(0);" class="newBuy">实付：¥<em>${price}</em></a>
				<c:choose>
					<c:when test="${is_deilver == 'true' }">
						<span class="go-shop" onclick="makeSure()">去付款</span>
					</c:when>
					<c:otherwise>
						<span class="go-shop disable">去付款</span>
					</c:otherwise>
				</c:choose>
				
				<input type="hidden" id="token" name="token" value="${token}"/>
			</div>
			
		</div>
		
		<div class="mask"></div>
		<div class="dia-pay-pass">
			<p class="import-pass">请输入礼品卡支付密码</p>
			<div class="dia-bot">
				<p class="bot-git">礼品卡余额</p>
				<p class="bot-sum">¥${userMoney}</p>
				<div class="m-passwordInput ">
					<label>
						<input type="password" pattern="/[0-9]*/" class="inputArea2" maxlength="6" value=""/>
					</label>
				</div>
				<div class="dia-cancel"><img src="${resourcepath}/weixin/img/zhuceout.png" alt="" /></div>
			</div>
		</div>
		
		<div class="vaguealert">
			<p></p>
		</div>
		<div class="agreement">
			<div class="outBoxTitle">
				<div class="outBox-a"></div>
			</div>
			<div class="agreement-con">
	        	<h3>挥货服务协议</h3>
				<h4>感谢您成为【挥货】的注册用户。</h4>
				<h4>为了保障您（以下简称“用户”）的权益，请在使用【挥货】服务之前，详细阅读此服务协议（以下简称“本协议”）所有内容。当用户开始使用【挥货】服务，用户的行为表示用户同意并签署了本协议，构成用户与【挥货】平台之间的协议，具有合同效力。</h4>
				<h4>本协议内容包括协议正文、本协议明确援引的其他协议、【挥货】平台已经发布的或将来可能发布的各类规则，该等内容均为本协议不可分割的组成部分，与协议正文具有同等法律效力。除另行明确声明外，用户使用【挥货】服务均受本协议约束。</h4>
				
				<h4>第一条  定义</h4>
				<h5>1、【挥货】是以产品品质、服务质量为主的美食类电商平台。用户登录【挥货】平台后，可以浏览平台上发布的商品和服务信息，进行商品或服务交易等活动。</h5>
				<h5>2、商家是指通过【挥货】平台陈列、销售商品或服务，并最终和用户完成交易的主体，是商品和服务的提供方和销售方。</h5>
				
				<h4>第二条  【挥货】服务协议的修订</h4>
				<h4>根据国家法律法规变化及网站运营需要，【挥货】平台有权在必要时通过在平台发出公告等合理方式修改本条款，修改后的协议一经发布并经您确认同意后即刻生效，并代替原来的协议，用户可在【挥货】平台上随时查阅最新协议。用户如继续使用【挥货】服务，则视为对修改内容的同意，当发生有关争议时，以最新的服务协议为准；用户在不同意修改内容的情况下，有权停止使用本协议涉及的服务。</h4>
				
				<h4>第三条  账户管理</h4>
				<h5>1、您注册成功后，【挥货】会为您开通一个账户，作为您使用【挥货】服务的唯一身份标识。该账户的所有权归属于【挥货】，您仅拥有使用权。登录账户时，您可以使用您提供或确认的用户名、邮箱、手机号码或【挥货】允许的其他方式作为注册用户名进行登录，但在登录时您必须输入您设定并保管的账户密码。为保护您的权益，您在设定账户的密码时，请勿使用重复性或者连续数字的简单密码。请您对密码加以妥善保管，切勿将密码告知他人，因密码保管不善而造成的所有损失由您自行承担。</h5>
				<h5>2、【挥货】只允许每位用户使用一个平台账户, 用户通过对用户的账户所进行的活动、行为和事件依法享有权利和承担责任，且不得以任何形式转让账户、授权他人使用账户以及与他人交易账户。</h5>
				<h5>3、用户应保管好自己的帐号和密码，如因用户未保管好自己的帐号和密码而对自己、【挥货】平台或第三方造成损害的，用户将负全部责任。另外，用户应对自己帐号中的所有活动和事件负全责。用户有权随时改变帐号的登录密码或支付密码。</h5>
				
				<h5>4、用户在使用【挥货】服务时填写、登录、使用的帐号名称、头像、个人简介等帐号信息资料应遵守法律法规、社会主义制度、国家利益、公民合法权益、公共秩序、社会道德风尚和信息真实性等七条底线，不得在帐号信息资料中出现违法和不良信息，且用户保证在填写、登录、使用帐号信息资料时，不得有以下情形：</h5>
				<h6>（1）违反宪法或法律法规规定的；</h6>
				<h6>（2）危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</h6>
				<h6>（3）损害国家荣誉和利益的，损害公共利益的；</h6>
				<h6>（4）煽动民族仇恨、民族歧视，破坏民族团结的；</h6>
				<h6>（5）破坏国家宗教政策，宣扬邪教和封建迷信的；</h6>
				<h6>（6）散布谣言，扰乱社会秩序，破坏社会稳定的；</h6>
				<h6>（7）散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</h6>
				<h6>（8）侮辱或者诽谤他人，侵害他人合法权益的；</h6>
				<h6>（9）含有法律、行政法规禁止的其他内容的。</h6>
				<h4>若用户登录、使用帐号头像、个人简介等帐号信息资料存在违法和不良信息的，【挥货】平台有权采取通知限期改正、暂停使用等措施。对于冒用关联机构或社会名人登录、使用、填写帐号名称、头像、个人简介的，【挥货】平台有权取消该帐号在平台上使用，并向政府主管部门进行报告。</h4>
				
				<h5>6、鉴于网络服务的特殊性，【挥货】无义务审核是否是用户本人使用该组用户名及密码，仅审核用户名及密码是否与数据库中保存的一致，任何人只要输入的用户名及密码与数据库中保存的一致，即可凭借该组用户名及密码登陆并获得【挥货】所提供的各类服务，所以即使用户认为其账户登陆行为并非用户本人所为，【挥货】将不承担因此而产生的任何责任。</h5>
				
				<h5>7、用户可以通过在【挥货】网站参与商品评价、网站活动等方式获得积分和抵用券。积分和抵用券都具有特定的使用规则和有效期，逾期将被清零；请不时关注账户中的积分和抵用券的有效期，在有效期届满前使用，【挥货】将不再另行作特别通知；【挥货】不对逾期清零的积分和抵用券负责。对于恶意购买和不正当手段获得的积分和抵用券，【挥货】有权作出独立判断并采取包含但不限于冻结用户账户或清空积分、抵用券等措施。</h5>
				
				<h4>第四条  【挥货】服务规范</h4>
				<h5>1、【挥货】平台是为用户提供获取商品和服务信息、就商品和/或服务的交易进行协商及开展交易的第三方平台，并非商品和服务的提供方或销售方，平台上的所有商品和服务由商家向用户提供，并由商家承担其商品和服务（以下统称为“商品”）的质量保证责任。</h5>
				
				<h5>2、除法律规定或者【挥货】平台上公示的承诺外，【挥货】平台将不对所提供的商品的适用性或满足用户特定需求及目的进行任何明示或者默示的担保。请用户在购买前确认自身的需求，同时仔细阅读商品详情说明。【挥货】平台会督促商家对其在平台上展示的商品提供真实、准确、完整的信息，但不能保证平台上所有商品的相关内容均为准确完整、真实有效的信息，亦不承担因商品信息导致的任何责任。</h5>
				
				<h5>3、商家将根据国家法律法规及【挥货】平台上公布的售后服务政策向用户提供售后保障。【挥货】平台上的售后服务政策为本协议的组成部分，【挥货】平台有权以声明、通知或其他形式变更售后服务政策。</h5>
				
				<h5>4、用户同意并保证：为了更好的为用户提供服务，【挥货】平台有权记录用户在选购商品过程中在线填写的所有信息，并提供给商家或【挥货】平台的关联公司。用户保证该等信息准确、合法，该等信息将作为用户本次交易的不可撤销的承诺，用户承担因该等信息错误、非法等导致的后果。如用户提供的信息过期、无效进而导致商家或【挥货】平台无法与用户取得联系的，因此导致用户在使用【挥货】服务中产生任何损失或增加费用的，应由用户完全独自承担。</h5>
				
				<h5>5、用户了解并同意，【挥货】平台有权应有关部门的要求，向其提供用户提交给【挥货】平台或用户在使用【挥货】服务中存储于【挥货】平台服务器的必要信息。如用户涉嫌侵犯他人合法权益，则【挥货】平台有权在初步判断涉嫌侵权行为存在的情况下，向权利人提供关于用户的前述必要信息。</h5>
				
				<h5>6、除非另有证明，储存在【挥货】平台服务器上的数据是用户使用【挥货】服务的唯一有效证据。</h5>
				
				<h5>7、除法律另有规定外，【挥货】平台保留自行决定是否提供服务、限制账户权限、编辑或清除网页内容以及取消用户订单的权利。</h5>
				
				<h4>五	第五条  【挥货】平台使用规则</h4>
				<h5>1、用户可浏览【挥货】平台上的商品信息，如用户希望完成选购并支付订单的，用户需先登录或注册【挥货】平台认可的帐号后进行登录，并根据页面提示提交选购的商品信息，包括但不限于商品数量、交付所需的收货人信息。用户在提交订单时登录的帐号和密码是【挥货】平台确认用户身份的唯一依据。</h5>
				
				<h5>2、用户有权选择使用【挥货】平台接受的支付方式，用户理解并确认部分支付服务由【挥货】平台之外具备合法资质的第三方为用户提供，该等支付服务的使用条件及规范由用户与支付服务提供方确定，与【挥货】平台无关。某些支付方式可能需要在支付时验证用户的【挥货】平台支付密码，用户如未设置支付密码，【挥货】平台将引导用户进行设置。</h5>
				
				<h5>3、用户确认：在使用【挥货】服务过程中，用户应当是具备完全民事权利能力和完全民事行为能力的自然人、法人或其他组织。若用户不具备前述主体资格，则用户及用户的监护人应承担因此而导致的一切后果，【挥货】平台有权向用户的监护人追偿。</h5>
				
				<h5>6、【挥货】平台上的商品和服务的价格、数量等信息随时可能发生变动，对此【挥货】平台不作特别通知。由于【挥货】平台上的商品的数量庞大、互联网技术因素等客观原因存在，【挥货】平台上显示的信息可能存在一定的滞后和误差，对此请用户知悉并理解。为最大限度的提高平台上商品信息的准确性、及时性、完整性和有效性，【挥货】平台有权利对商品信息进行及时的监测、修改或删除。如用户提交订单后，【挥货】平台发现平台的相关页面上包括但不限于商品名称、价格或数量价格等关键信息存在标注错误的，有权取消错误订单并及时通知商家和用户。</h5>
				
				<h5>7、在用户提交订单时，收货人与用户本人不一致的，收货人的行为和意思表示视为用户的行为和意思表示，用户应对收货人的行为及意思表示的法律后果承担连带责任。</h5>
				
				<h5>8、用户理解并同意：【挥货】平台实行先付款后发货的方式，用户及时、足额、合法的支付选购商品所需的款项是商家给用户发货的前提。付款方式将在【挥货】平台予以公示。用户应在确认订单时，选择付款方式，并严格按照已选择的方式付款。用户未能按照所选择的方式或在指定时间完成付款的，【挥货】平台有权取消订单。</h5>
				
				<h5>9、除法律另有规定强制性规定外，用户理解并同意以下订单成立规则：</h5>
				<h6>（1）【挥货】平台上展示的商品及其价格、规格等信息仅是商家发布的供用户浏览参考的交易信息，用户下单时须填写希望购买的商品数量、价款及支付方式、收货人、联系方式、收货地址等具体内容，系统将根据用户填写的前述必要信息自动生成相应订单，用户确认无误后可提交订单。该订单仅是用户向商家发出的希望购买特定商品的交易诉求。</h6>
				<h6>（2）【挥货】平台系统将用户提交的订单反馈至商家，经商家确认并将订单中选购的商品向用户或用户指定的收货人发出时（以订单显示的状态为准），方视为用户和商家之间就实际发出的商品建立合同关系。如用户在一份订单中购买了多个商品但商家实际只发出了部分商品时，用户和商家直接仅就实际发出的商品建立合同关系。</h6>
				<h6>（3）由于市场变化及各种以合理商业努力难以控制的因素的影响，【挥货】平台无法保证用户提交的订单信息中选购的商品都有库存；如用户拟购买的商品发生缺货，用户有权取消订单。</h6>
				
				<h5>10、用户应当保证在使用【挥货】服务进行交易过程中遵守诚实信用的原则。用户支付交易款项过程中，将被邀请复查选购商品的信息，包括单价、购买数量、付款方式、商品的运输方式和费用等。请用户仔细确认该等信息。该存储于【挥货】平台服务器的订单表格被认为是用户的该次交易对应的发货、退货和争议事项的证据。用户点击“提交订单”意味着用户认可订单表格中包含的所有信息都是正确、合法和完整的，用户须对用户在使用【挥货】服务过程中的上述行为承担法律责任。</h5>
				
				<h5>11、用户所选购的商品将被送至订单表格上注明的送货地址，具体物流服务由用户所选购的商品的商家提供，【挥货】平台不承担责任。无论何种原因该商品不能送达到送货地址，请用户尽快跟【挥货】客服热线（400-1869-797）取得联系，【挥货】平台将会同商家进行解决。</h5>
				
				<h5>12、用户可以随时使用自己的帐号和登录密码登录【挥货】平台，查询订单状态。</h5>
				
				<h5>13、商家在【挥货】平台上列出的所有送货时间仅为参考时间，如因以下情况造成订单延迟或无法配送、交货等，商家不承担延迟配送、交货的责任，并有权在必要时取消订单：</h5>
				<h6>（1）用户提供的信息错误、地址不详细等原因导致的；</h6>
				<h6>（2）货物送达后无人签收，导致无法配送或延迟配送的；</h6>
				<h6>（3）情势变更因素导致的；</h6>
				<h6>（4）因节假日、大型促销活动、预购或抢购人数众多等原因导致的；</h6>
				<h6>（5）不可抗力因素导致的，例如：自然灾害、交通戒严、突发战争等。</h6>
				
				<h5>14、用户有权在【挥货】平台上发布内容，同时也有义务独立承担用户在【挥货】平台所发布内容的责任。用户发布的内容必须遵守法律法规及其他相关规定。用户承诺不得发布以下内容，否则一经发现【挥货】平台有义务立即停止传输，保存有关记录，向国家有关机关报告，并且删除该内容或停止用户的帐号的使用权限。</h5>
				<h6>（1）侵犯他人知识产权或其他合法权利的相关内容；</h6>
				<h6>（2）透露他人隐私信息，包含真实姓名、联系方式、家庭住址、照片、站内短信、聊天记录、社交网络帐号等；</h6>
				<h6>（3）发布商业广告或在评论区域发布广告链接；</h6>
				<h6>（4）相关法律、行政法规禁止的其他内容。</h6>
				
				<h5>15、用户账户内的任何优惠、促销信息仅供用户本人在【挥货】平台购物时的使用，用户不得转卖、转让予他人。【挥货】平台进行优惠、促销的目的是为满足广大的消费需求，一切以牟利、排挤竞争为意图或者为达到其它恶意目的的参与行为均不予接受，【挥货】平台有权在发现不正当行为时，有权会同商家采取包括但不限于暂停发货、取消订单及限制账户权限等措施。</h5>
				<h5>16、用户可将其与商家因交易行为产生的争议提交【挥货】平台，提交即视为用户委托【挥货】平台单方判断与该争议相关的事实及应适用的规则，进而作出处理决定。该判断和决定将对用户产生约束力。但用户理解并同意，【挥货】平台并非司法机构，仅是基于用户委托以中立第三方身份进行争议处理，无法保证处理结果符合用户的预期，也不对处理结果承担任何责任。如用户因此遭受损失，用户同意自行向责任方追偿。</h5>
				
				<h4>第六条  其他约定</h4>
				<h5>1、责任范围</h5>
				<h5>【挥货】平台对不可抗力导致的损失不承担责任。本协议所指不可抗力包括：天灾、法律法规或政府指令的变更，因网络服务特性而特有的原因，例如境内外基础电信运营商的故障、计算机或互联网相关技术缺陷、互联网覆盖范围限制、计算机病毒、黑客攻击等因素，及其他合法范围内的不能预见、不能避免并不能克服的客观情况。</h5>
				
				<h5>2、服务中止、中断及终止：【挥货】平台根据自身商业决策等原因可能会选择中止、中断及终止【挥货】服务。如有此等情形发生，【挥货】平台会采取公告的形式通知用户。经国家行政或司法机关的生效法律文书确认用户存在违法或侵权行为，或者【挥货】平台根据自身的判断，认为用户的行为涉嫌违反本协议的协议或涉嫌违反法律法规的规定的，则【挥货】平台有权中止、中断或终止向用户提供服务，且无须为此向用户或任何第三方承担责任。</h5>
				
				<h5>3、所有权及知识产权：</h5>
				<h6>（1）【挥货】平台上所有内容，包括但不限于文字、软件、声音、图片、录像、图表、网站架构、网站画面的安排、网页设计、在广告中的全部内容、商品以及其它信息均由【挥货】平台或其他权利人依法拥有其知识产权，包括但不限于商标权、专利权、著作权、商业秘密等。非经【挥货】平台或其他权利人书面同意，用户不得擅自使用、修改、全部或部分复制、公开传播、改变、散布、发行或公开发表、转载、引用、链接、抓取或以其他方式使用本平台程序或内容。如有违反，用户同意承担由此给【挥货】平台或其他权利人造成的一切损失。</h6>
				<h6>（2）【挥货】平台尊重知识产权并注重保护用户享有的各项权利。在【挥货】平台上，用户可能需要通过发表评论等各种方式向【挥货】平台提供内容。在此情况下，用户仍然享有此等内容的完整知识产权，但承诺不将已发表于本平台的信息，以任何形式发布或授权其他主体以任何方式使用。用户在提供内容时将授予【挥货】平台一项全球性的免费许可，允许【挥货】平台及其关联公司使用、传播、复制、修改、再许可、翻译、创建衍生作品、出版、表演及展示此等内容。</h6>
				
				<h5>4、通知：所有发给用户的通知都可通过电子邮件、短信、常规的信件或在【挥货】平台显著位置公告的方式进行传送。为使用户及时、全面了解【挥货】平台的各项服务，用户同意，【挥货】平台可以多次、长期向用户发送各类商业性短信息而无需另行获得用户的同意。</h5>
				
				<h5>5、除非法律允许或【挥货】书面许可，您不得从事下列行为：</h5>
				<h6>(1) 删除软件及其副本上关于著作权的信息；</h6>
				<h6>(2) 对软件进行反向工程、反向汇编、反向编译或者以其他方式尝试发现软件的源代码；</h6>
				<h6>(3) 对软件或者软件运行过程中释放到任何终端内存中的数据、软件运行过程中客户端与服务器端的交互数据，以及软件运行所必需的系统数据，进行复制、修改、增加、删除、挂接运行或创作任何衍生作品，形式包括但不限于使用插件、外挂或非经合法授权的第三方工具/服务接入软件和相关系统；</h6>
				<h6>(4) 修改或伪造软件运行中的指令、数据，增加、删减、变动软件的功能或运行效果，或者将用于上述用途的软件、方法进行运营或向公众传播，无论上述行为是否为商业目的；</h6>
				<h6>(5) 通过非【挥货】开发、授权的第三方软件、插件、外挂、系统，使用【挥货】平台服务，或制作、发布、传播非【挥货】开发、授权的第三方软件、插件、外挂、系统；</h6>
				<h6>(6) 用户不能利用【挥货】服务进行采购、销售或其他商业用途;</h6>
				<h6>(7) 其他未经【挥货】明示授权的行为。</h6>
				
				<h5>6、本协议适用中华人民共和国大陆地区施行的法律。当本协议的任何内容与中华人民共和国法律相抵触时，应当以法律规定为准，同时相关协议将按法律规定进行修改或重新解释，而本协议其他部分的法律效力不变。</h5>
				
				<h5>7、如用户在使用【挥货】服务过程中出现纠纷，应进行友好协商，若协商不成，应将纠纷提交中国国际经济贸易仲裁委员会根据其仲裁规则进行仲裁。</h5>
				
				<h5>8、【挥货】平台不行使、未能及时行使或者未充分行使本协议或者按照法律规定所享有的权利，不应被视为放弃该权利，也不影响【挥货】平台在将来行使该权利。</h5>
				
				<h5>9、如果用户对本协议内容有任何疑问，请拨打【挥货】平台客服热线：400-1869-797。</h5>
			</div>
        </div>
        <div class="bkbg" style="display: none;"></div> 
        
		<div class="shepassdboxs" style="display: none;">
			<span>请确认付款</span>  
			<div class="qushan">
				<a class="left" id="cancelId" href="javascript:closeAlert();">取消</a>
				<a class="right" id="surePayId" href="javascript:submitForm();">确定</a> 
			</div>
		</div>
		<!--金币使用规则弹窗-->
		<div class="rule-wrap">
			<div class="rule">
				<div class="rele-top">
					<div class="close"></div>
					金币使用规则
				</div>
				<div class="reledetails">
					<div class="box">
						<h3>一、使用条件</h3>
						<p>*当前积分达到500及以上即可抵扣商品价格；</p>
						<p>*参加了积分抵扣活动的商品即可使用积分抵扣；</p>
						<p>*单次抵扣的金额不得高于10元；</p>
					</div>
					<div class="box">
						<h3>二、使用数量</h3>
						<p>*单次交易中，可用于商品抵扣的金币范围是500~1000；</p>
						<p>*例：501金币可抵5.01元，以此类推</p>
					</div>
				</div>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/common.js?t=201712131738"></script>
	<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/order/wx_buy_order.js?t=201804221531"></script>
</html>	