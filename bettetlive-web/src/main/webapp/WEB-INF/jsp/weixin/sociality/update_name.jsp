<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/boundNewPhone.css" />
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
		<title>挥货-昵称</title>
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
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			
			<div class="mainBox" style="overflow:auto;top:0.2rem; ">  
				<div class="contentBox">
					<div class="identityBox">
						<form action="" class="verification" id="phoneForm">
							<ul>
								<li>
									<input type="text" placeholder="请输入昵称" value="${nickName}" name="nickName" id="nickName" maxlength="15"/>
								</li>
							</ul> 
							<div class="nextStep" id="msgNext" onclick="javascript:submitName();">
							       保存
						 	</div>
						</form>					 
					</div>
					
				</div> 
			</div>
		</div>
		<div class="mask"></div>
		<div class="vaguealert">
			<p></p> 
		</div>
		<div class="set-success">
			<img src="${resourcepath}/weixin/img/reset.png" alt="" />
			<p>设置成功</p>
			<a class="backlogin">
				好的
			</a>
		</div>
	</body>
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript">
			function submitName(){
				var nickname = $("#nickName").val();
				if(null == nickname || "" == nickname){
					showvaguealert("昵称不可以为空！");
					return;
				}
				$.ajax({ 
			        type: "POST", 
			        data :{
			        	nickName:nickname
			        },
			        dataType: "JSON", 
			        async: false, 
			        url: "${mainserver}/weixin/customer/editNickName",
			        success: function(data) {
                        if (data.result == 'success') {
	                       	showvaguealert("修改成功！");
	                       	setTimeout(function(){
	                       		location.href="${mainserver}/weixin/customer/toCustomerModify";
	           				},1000);
                        }else if(data.result=="failure"){
						  window.location.href = "${mainserver}/weixin/tologin";
					  	}  else {
                          showvaguealert("修改失败！");
                        }
			        }
			    });
			} 		
	
			//提示弹框
			function showvaguealert(con){
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function(){
					$('.vaguealert').hide();
				},1000);
			};
		
	</script>
	<!-- 修改微信自带的返回键 -->
	<script type="text/javascript">
		<%String ref = request.getHeader("REFERER");%>
		$(function(){
			
			var url="<%=ref%>";
			
			
			var bool=false;  
            setTimeout(function(){  
                  bool=true;  
            },1000); 
	            
			pushHistory(); 
			
		    window.addEventListener("popstate", function(e) {
		    	if(bool){
		    		window.location.href=url;	
		    	}
		    }, false);
		    
		    function pushHistory(){ 
		        var state = { 
	        		title: "title", 
	        		url:"#"
	        	}; 
	        	window.history.pushState(state, "title","#");  
		    }
		    
		    
		  //返回上一个页面
			$('.backPage').click(function(){
				window.location.href=url;	
			});
		});
	
	</script>
</html>
