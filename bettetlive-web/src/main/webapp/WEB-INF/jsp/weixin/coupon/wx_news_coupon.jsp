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
		<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
	    <link rel="stylesheet" href="${resourcepath}/weixin/css/myCoupon.css" />
	    
		<script type="text/javascript" src="${resourcepath}/weixin/js/rem.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<title>挥货-使用优惠券</title>
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
 
		 
		 <!-- 没有优惠券时显示的DIV -->
		 <div class="noyhbg" style="display:none;">
		 <span>您还没有优惠券哦~</span>
		 </div>
		 
		 <div style="margin-top:0.2rem;" >
		 	<div class="yhbox">
		 		<div class="yhnrcent">
		 			<div class="top">
		 				<strong><em>￥</em>30</strong>    
		 				<span><em>优惠券</em>(满100可用)<p>限单品苏丹王榴莲千层榴莲千</p>
		 				</span>
		 			</div>
		 			<div class="xiam">
		 				<span>有效期至2016-10-21</span>
		 				<a class="shiyong">立即使用</a> 
		 			</div>
		 		</div>
		 		<div class="yhnrcent">
		 			<div class="top">
		 				<strong><em>￥</em>30</strong>    
		 				<span><em>优惠券</em>(满100可用)<p>限单品苏丹王榴莲千层榴莲千</p>
		 				</span>
		 			</div>
		 			<div class="xiam">
		 				<span>有效期至2016-10-21</span>
		 				<a>立即领取</a> 
		 			</div>
		 		</div>
		 	</div>
		 </div>
		 
	</body>
	
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/common.js"></script>
	
	<script type="text/javascript">	 
	var ordernav=$(".myordrnav li");
	var ordercent=$(".myordercent"); 
	for(var i=0;i<ordernav.length;i++){  
		ordernav[i].index=i;  
		ordernav[i].onclick=function(){
			for(var i=0;i<ordernav.length;i++){
				ordernav[i].className="";
				ordercent[i].style.display="none";  
			}      
			ordernav[this.index].className="current";  
			ordercent[this.index].style.display="block";           
		}
	  
		
	}  
		
	</script>
</html>	