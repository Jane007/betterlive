<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
	    <link rel="stylesheet" href="${resourcepath}/weixin/css/logisticsInfor.css" />
		<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-物流信息</title>
	</head>

	<body>
		<div class="initloading"></div>
		<div class="container">
			<div class="mainBox" style="top: 0rem;">
				<div class="logisticsHeader">
					<div class="logLeft">
						<img src="${orderProductVo.spec_img }" alt="" />
						<span><c:if test="${orderProductVo.quantity>0 }">${orderProductVo.quantity }件商品</c:if></span>
					</div>
					
					<div class="logRight">
						<ul>
							<li>
								<label for="">物流状态：</label>
								<span>
								  <c:if test="${logistics.code==1010 }">
								     <c:choose>
									     <c:when test="${logistics.state==0 }">
									                     待收货
									     </c:when>
									     <c:when test="${logistics.state==1 }">
									                    已揽件
									     </c:when>
									     <c:when test="${logistics.state==2 }">
									                      疑难
									     </c:when>
									     <c:when test="${logistics.state==3 }">
									                     已签收
									     </c:when>
									     <c:when test="${logistics.state==4 }">
									                      已退签
									     </c:when>
									     <c:when test="${logistics.state==5 }">
									                      派件中
									     </c:when>
									     <c:when test="${logistics.state==5 }">
									                      退回中
									     </c:when>
								    </c:choose>
								  </c:if>
								  <c:if test="${logistics.code==1020 }">
								     ${logistics.msg}
								  </c:if>
								</span>
							</li>
<%-- 							<c:if test="${logistics.code==1010 }"> --%>
								<li>
									<label for="">物流公司：</label>
									<span>${conpanyName }</span>
								</li>
								<li>
									<label for=""> 运单编号：</label>
									<span>${orderProductVo.logistics_code }</span>
								</li>
<%-- 							</c:if> --%>
							
							<!-- <li>
								<label for="">物流客服：</label>
								<span>52325</span>
							</li> -->
						</ul>
					</div>
				</div>
				<c:if test="${logistics.code==1010 }">
				   				<div class="logInforBox">
									<div class="logTitle">物流信息</div>
									<div class="logstepsBox">
									<c:forEach items="${logistics.data }" var="logistic">
									  
										<div class="logitems">
											<div class="dotBox"></div>
											<div class="logContent">
												<div class="logsite">${logistic.context }</div>
												<p class="logTime">${logistic.time }</p>
											</div>
										</div>
									 
									 
									</c:forEach>
									<div class="lines"></div>
									 </div>
									 
								</div>
				
				</c:if>

				
				
				
				<div class="userInfor">
					<ul>
						<li>
							<label for="">收货人：</label>
							<span>${orderVo.receiver }</span>
						</li>
						<li>
							<label for="">联系方式：</label>
							<span>${orderVo.mobile }</span>
						</li>
						<li>
							<label for="">配送至：</label>
							<span>${orderVo.address }</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script>
	var mainServer = "${mainserver}";
	var type = "${type}";
	</script>
	<script src="${resourcepath}/weixin/js/order/logisticsInfo.js?time=8"></script>
</html>	