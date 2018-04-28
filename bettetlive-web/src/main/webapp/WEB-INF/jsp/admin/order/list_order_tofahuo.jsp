<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>订单发货</title> 
			<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
			<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
			<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
			<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
			<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
	    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
	    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
			<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
			<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
			<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
			<script type="text/javascript">
		    	var mainServer = "${mainserver}";
	    	</script>
			
			
			<style type="text/css">
				.basicinfo{
					width: 15%;
					height: 300px;
					background-color: #EBEBEB;
					border: solid 1px #C1C1C1 ;
					writing-mode: vertical-rl;
					position: relative;
				}
				.basicinfo p{
					width: 16px;
				    position: absolute;
				    top: 0;
				    left: 0;
				    right: 0;
				    bottom: 0;
				    margin: auto;
				}
				.basicdata{
					width: 40%;
					height: 300px;
					border: solid 1px #C1C1C1 ;
				}
			
				.basicdata p{
					font-size: 12px;
					padding: 10px 0 0 10px;
				}	
				
				.productInfo{
					width: 15%;
					background-color: #EBEBEB;
					border: solid 1px #C1C1C1 ;
					position: relative;
				}		
				
				.productInfo p{
					width: 80px;
					height:20px;
				    position: absolute;
				    top: 0;
				    left: 0;
				    right: 0;
				    bottom: 0;
				    margin: auto;
				}    
				
				.accessoriessInfo{
					width: 15%;
					height: 50px;
					background-color: #EBEBEB;
					border: solid 1px #C1C1C1 ;
					position: relative;
				}
				
				.accessoriessInfo p{
					width: 80px;
					height:20px;
				    position: absolute;
				    top: 0;
				    left: 0;
				    right: 0;
				    bottom: 0;
				    margin: auto;
				}    
				
				.productdata{
					width: 80%;
					border: solid 1px #C1C1C1 ;
				}
				
				.productdata p{
					font-size: 12px;
				}
				
				.accessoriessdata{
					width: 80%;
					height: 50px;
					border: solid 1px #C1C1C1 ;
				}
				
				.accessoriessdata p{
					font-size: 12px;
				}	
			</style>
	</head> 
	<body>
	
	    <div id="detailDlg" class="easyui-dialog" title="订单详情" data-options="closed:true" style="width:990px;height:500px;padding:10px;" fit="true">
	    	<div>
	    		<div class="basicinfo" style="float: left;">
	    			<p align="center" style="font-size: 16px;font-weight: bolder;">基本信息</p>
	    		</div>
	    		
	    		<div class="basicdata" style="float: left;">
	    			<p>订单编号:${orderInfo.order_code}</p>
	    			<p>订单总金额：￥${orderInfo.total_price}</p>
	    		    
	    		    <p>礼品卡抵扣金额：
	    		    	<c:if test="${null!= orderInfo.gitf_card_money}">￥${orderInfo.gitf_card_money}</c:if>
	    		    </p>
	    		    
	    		    <p>优惠券抵扣金额：
						<c:if test="${orderInfo.conpon_money !=null}">￥${orderInfo.conpon_money}</c:if>
					</p>
					<p>单品红包抵扣金额：
						<c:if test="${userSingleCouponVo.couponMoney !=null}">￥${userSingleCouponVo.couponMoney }</c:if>
					</p>
	    		    <p>运费：
	    		    	<c:if test="${orderInfo.freight!=null}">￥${orderInfo.freight}</c:if>
	    		    </p>
	    			<p>应付金额：￥${orderInfo.pay_money}</p>
	    			<p>订购人:${customerInfo.nickname}</p>
	    			<p>订购电话: ${customerInfo.mobile}</p>
	    			<p>收货人:${orderInfo.receiver}</p>
	    			<p>收货人电话:${orderInfo.mobile}</p>
	    			<p>收货地址:${orderInfo.address}</p>
		    	</div>
		    	<div  class="basicdata" style="float: left;">
		    		<%-- <p>
		    			订单状态：
		    		    <c:if test="${orderInfo.status ==1}">待付款</c:if>
		    		    <c:if test="${orderInfo.status ==2}">待配送</c:if>
		    		    <c:if test="${orderInfo.status ==3}">待签收</c:if>
		    		    <c:if test="${orderInfo.status ==4}">待评价</c:if>
		    		    <c:if test="${orderInfo.status ==5}">已完成</c:if>
		    		    <c:if test="${orderInfo.status ==7}">已退款</c:if>
		    		</p> --%>
		    		<p>支付方式:
		    					<c:if test="${orderInfo.pay_type =='1'}">微信</c:if>
		    		   		    <c:if test="${orderInfo.pay_type =='2'}">支付宝</c:if>
		    		   		    <c:if test="${orderInfo.pay_type =='3'}">招行一网通</c:if>
		    		</p>
		    		<p>支付状态:
		    		    <c:if test="${orderInfo.status ==1}">待付款</c:if>
		    			<c:if test="${orderInfo.status >=1}">已付款</c:if>
		    		</p>
		    		<p>下单时间:${orderInfo.order_time}</p>
		    		<p>支付时间:${paylogInfo.pay_time}</p>
		    		<p>订单留言:${orderInfo.message_info}</p>
		    	</div>
	    	</div>
	    	<div>
	    		<div class="productInfo" style="float: left;">
	    			<p align="center" style="font-size: 16px;font-weight: bolder;">商品信息</p>
	    		</div>
	    		<div class="productdata" style="float: left;">
	   				<c:forEach items="${orderInfo.listOrderProductVo}" varStatus="index" var="l">
	   					<p style="margin-bottom:5px;">${index.index+1 }.&nbsp; 
	   					      【订单编号】:${l.sub_order_code} &nbsp;&nbsp;
	   					      【商品名称】:${l.product_name} &nbsp;&nbsp; 
	   					      【规格】:${l.spec_name} &nbsp;&nbsp;
	   					      【数量】:${l.quantity} &nbsp;&nbsp;
	   					      【单价】:${l.price} &nbsp;&nbsp;
	   					      【优惠价格】:${l.activity_price} &nbsp;&nbsp;
	   					       【商品状态 】:<c:if test="${l.status ==1}">待付款</c:if>
		    		    <c:if test="${l.status ==2}">待发货</c:if>
		    		    <c:if test="${l.status ==3}">待签收</c:if>
		    		    <c:if test="${l.status ==4}">待评价</c:if>
		    		    <c:if test="${l.status ==5}">已完成</c:if>
		    		    <c:if test="${l.status ==7}">已退款</c:if> 
	   					  &nbsp;&nbsp;<a href="javascript:void(0);" lang="${l.orderpro_id }" onclick="toConfirmFahuo(this)">商品发货</a>
	   					</p>
	   				</c:forEach>
	    		</div>
	    	</div>
	    	<%-- <div>
	    		<div class="accessoriessInfo" style="float: left;">
	    			<p align="center" style="font-size: 16px;font-weight: bolder;width: 100px;">订单取消信息</p>
	    		</div>
	    		<div class="accessoriessdata" style="float: left;">
	    			<c:if test="${orderCancelInfo !=null}">
		   				<p>【订单编号】:${orderCancelInfo.order_code} &nbsp;&nbsp; 
	   					      【操作人】:${orderCancelInfo.loginname} &nbsp;&nbsp;
	   					      【操作时间】:${orderCancelInfo.create_time} &nbsp;&nbsp;
		   			    </p>
		   			    <p>【取消原因】:${orderCancelInfo.detail}</p>
	   			    </c:if>
	    		</div>
	    	</div> --%>
	         
	     </div>
	     
	     
	    <div id="fahuoDlg" class="easyui-dialog" title="商品发货" data-options="closed:true,modal:true" style="width:400px;height:240px;padding:10px;">
		    <form action="addReply" id="Logistics_info_form" method="post">
		       <input  type="hidden" name="order_product_id" id="order_product_id"/>
			   	<table class="kv-table">
					<tbody>
					    <tr>
							<td class="kv-label">快递公司:</td>
							<td class="kv-content" colspan="4"> 
					            <select  name="company_code" id="company_code" style="width:200px;height:30px;line-height:30px;"  >
					           </select>
					    	</td>
						</tr>
						<tr>
							<td class="kv-label">快递单号:</td>
							<td class="kv-content" colspan="4"> 
								
								<input type="text" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" style="width:198px;height:28px;line-height:28px;" class="" name="logistics_code" id="logistics_code"/>
					    	</td>
						</tr>
						
					</tbody>
				</table>
				<div style="padding:5px;text-align: center;margin-top:20px;" >
			       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitLogisticsForm()">保存</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeForm()">取消</a>
				</div>
			</form>
		</div>
	     
	     <script type="text/javascript" src="${resourcepath}/admin/js/order/list_order_tofahuo.js"></script>
	</body> 
</html>
<script>
	$(function(){
		$('.productInfo').css('height',$('.productdata').height())
	})
</script>
