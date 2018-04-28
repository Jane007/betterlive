<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/404.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-404</title>
		<script>
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
		<div class="container">
			<div class="errorTop">
				<div class="errorImg">
					<img src="${resourcepath}/weixin/img/404.png" alt="" />
				</div>
				<p>发生了一个小错误</p>
			</div>
			<div class="errorBot">
				<div class="cause">
					<label for="">可能原因：</label>
					<span>
						<em>网络信号弱</em>
						<em>找不到页面请求</em>
					</span>
				</div>
				<div class="operation">
					<a href="" class="refreshBox">
						<i></i>
						<em>刷新页面</em>
					</a>
					<a href="javascript:void(0)" class="rollback">
						<i></i>
						<em>返回上一页</em>
					</a>
				</div>
			</div>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script>
		$(function(){
			$('.rollback').click(function(){
				window.history.go(-1);
			});
			
		});
		
	</script>
</html>
