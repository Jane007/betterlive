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
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/personalData.css" />
      	<link rel="stylesheet" href="${resourcepath}/weixin/css/personal.css" />
    	<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-设置</title> 
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var sex = "${customer.sex}";
	   		var mobile = "${customer.mobile}";
	   		var signature = "${customer.signature }";
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
					<li style="background:none;padding-right:0.3rem; "> 
						<span class="ospan">账号</span> 
						<span class="sheright">${customer.mobile }</span>
					</li>
					<li onclick="javascript:editNickName();">
						<span class="ospan">昵称</span>
						<span class="sheright" >${customer.nickname }</span>
					</li>
					<li class="sexset xbxuan">
						<span class="ospan">性别</span>  
						<span class="sheright xbzhi" id="custSex"></span> 
					</li>
					 <li class="sexset" onclick="javascript:editSignature();">
						<span class="ospan">个性签名</span>
						<span class="sheright" id="custSignature" ></span> 
					</li>				
				</ul> 
				<ul  style="margin-top:0.2rem;">
					<li class="sexset" onclick="javascript:editNewPhone();">
						<span class="ospan">绑定手机</span>
						<span class="sheright" id="phone" ></span> 
					</li> 
					 <li class="sexset">
						<span class="ospan">微信号</span>
						<span class="sheright" id="isOpenId"></span> 
					</li>
				</ul>
				</form> 
 
		</div>
		<div class="mask" onclick="guan();"></div> 
		<div class="vaguealert">
			<p></p>
		</div>
		
		<div class="xbbox">
			<ul>
				<li><a>男</a></li>  
				<li><a>女</a></li>
				<li><a>保密</a></li>
				<li><a>取消</a></li>
			</ul>
		</div>
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/LCalendar.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${resourcepath}/weixin/js/sociality/customer_modify.js?t=2018502261020"></script>
</html>

