<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/myAddress.css" />
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-我的地址</title>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var receiverAddress='${listReceiverAddress}';
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
			
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="mainBox" style="top: 0rem;">
				<c:if test="${listReceiverAddress != null && fn:length(listReceiverAddress)>0 }">
					<div class="consigneebox">
					
						<c:forEach items="${listReceiverAddress}"  var="ra">
							
							<div class="addresslist" id="${ra.receiverId}">
								<p class="consignee">
									<span class="conname">${ra.receiverName}</span>
									<span class="phonenumber">${ra.mobile}</span>
								</p>
								<p class="Receivingaddress">
									${ra.address}
								</p>
								<p class="defaultaddress">
									<span class="debox">
										<c:if test="${ra.isDefault ==1}">
											<span class="default on">
										</c:if>
										<c:if test="${ra.isDefault !=1}">
											<span class="default ">
										</c:if>
										
											<input type="radio" name="address"/>
										</span>
										<i>设为默认地址</i>
									</span>
									
									<span class="addressdel">删除</span>
									<a href="${mainserver}/weixin/addressmanager/toAddReceiverAddress?receiverId=${ra.receiverId}" class="addressedit">编辑</a>
								</p>
							</div>
							
						</c:forEach>	
					</div>
				</c:if>
				
				<div class="no-address">
					<div class="no-address-img">
						<img src="${resourcepath}/weixin/img/no-address.png" alt="" />
					</div>
					<p>目前还没有收货地址</p>
				</div>
				
				<a href="${mainserver}/weixin/addressmanager/toAddReceiverAddress?receiverId=0" class="newAdd-address">
					新增地址
				</a>
			</div>
		</div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
    <script src="${resourcepath}/weixin/js/receiveraddress/wx_list_receiver.js"></script>
</html>