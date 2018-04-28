<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201801061758" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/personalData.css?t=201801061758" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/LCalendar.css?t=201801061758" />
    	<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-个人资料</title>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var headUrl='${customervo.head_url}';
	   		var userSex='${customer.sex}';
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
		
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="mydatabox" style="padding-top:0px;">
				<form id="form1" enctype="multipart/form-data">
				<ul>
					<li> 
						<span class="ospan">头像</span>  
						<input type="file" id="file1" name="touxiang" value="上传头像" class="shangchuan" />
						<span class="myright ospan mypic">
							<%-- <img src="${resourcepath}/weixin/img/morentouxiang.png" alt="" /> --%>
							<img src="${customer.head_url }" alt="" id="imgHead"/>
						</span>
					</li>
					<li>
						<span class="ospan">昵称</span>
						<span class="myright ospan">
						<input class="myright ospan" type="text" id="nickname" name="nickname" value="${customer.nickname}" maxlength="15"/>
						</span>	
					</li>
					<li class="sexset">
						<span class="ospan">性别</span>
						<label class="myright">
						<span id="man">
								<input type="radio" name="sex" value="1" />
							</span><i>男</i>&nbsp;&nbsp;
							<span class="setright" id="man">
								<input type="radio" name="sex" value="2"/>
							</span><i>女</i>
						</label>
					</li>
					<li>
						<span class="ospan">生日</span>
						<span class="myright ospan"></span> 
						<c:if test="${customer.birthday != null}">
							<input type="button" id="birthday" name="birthday" readonly value="${customer.birthday}"   />  
						</c:if>
						<c:if test="${customer.birthday == null}">
						<input type="button" id="birthday" name="birthday" readonly value="请选择生日"  style="color:#666;" />   
						</c:if>
					</li>
				</ul>
				</form>
			<div class="saveData">保存</div>
			</div>
		</div>
			<div class="mask"></div>
		<div class="vaguealert">
			<p></p>
		</div>
		
		
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/LCalendar.js"></script>
	<script src="${resourcepath}/weixin/js/usercenter/wx_user_info.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/ajaxfileupload.js"></script>
</html>

