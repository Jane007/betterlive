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
		<link rel="stylesheet" href="${resourcepath}/weixin/css/myAddress.css?t=201701041416" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/LArea.css" />
		<script src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-编辑收货地址</title>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var returnType='${returnType}';
	   		var isDefault='${receiverAddressVo.isDefault}';
	   		var receiver_id= '${receiverId}';
	   		
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
			<div class="mainBox" style="top: 0rem;">
				<div class="adressBox">
					<input type="hidden" name="receiverId" value="${receiverAddressVo.receiverId}" id="receiverId">
					<input type="hidden" name="isDefault" value="${receiverAddressVo.isDefault}" id="isDefault">
					<ul class="fillIn">
						
						<li>
							<input type="text" placeholder="姓名"  name="receiveName" id="receiveName" maxlength="20" value="${receiverAddressVo.receiverName }" maxlength="20"/>
						</li>
						<li>
							<input type="text" placeholder="手机号码"  name="receiveMobile" id="receiveMobile" value="${receiverAddressVo.mobile }" maxlength="11"/>
						</li>
						<li>
							<c:if test="${receiverAddressVo.address != null && receiverAddressVo.address != ''}">
							<input id="areabox" type="button" readonly="readonly" value="${fn:split(receiverAddressVo.address,';')[0] }"/> 
							</c:if>   
							<c:if test="${receiverAddressVo.address == null || receiverAddressVo.address == ''}">
							<input id="areabox" type="button" readonly="readonly" value="省份，城市，区县"/> 
							</c:if>            
							<input id="value1" type="hidden" value="${addressCode}"/>
						</li>
						<li>
							<input type="text" placeholder="详细地址"   name="addressDetail" id="addressDetail"  value="${fn:split(receiverAddressVo.address,';')[1] }"/>
						</li>
						<div class="consigneebox">
						<div class="addresslist" style="padding:0;background:none;border:none;"> 
						<p class="defaultaddress" style="border: 0;height:0.4rem;padding-top:0.2rem;">
									<span class="debox">
											<span class="default ">
											<input type="radio" name="address"/>
											</span>
											<i>设为默认地址</i>
									</span>			 
						</p>
						</div>
						</div>
					</ul>
				</div>
				<div class="saveAddress">
					保存地址
				</div>
			</div>
		</div>
		
		<div class="mask"></div>
		<div class="vaguealert">
			<p></p>
		</div>
		
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/LArea.js"></script>
	<script src="${resourcepath}/weixin/js/LAreaData.js"></script>
	<script src="${resourcepath}/weixin/js/receiveraddress/wx_add_receiver.js"></script>
</html>