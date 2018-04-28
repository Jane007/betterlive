<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>消息-关注</title>
		<link rel="stylesheet" href="${resourcepath}/weixin/css/result.css?t=201801291615" />
		<link rel="stylesheet" href="${resourcepath}/weixin/css/news/msg_newattention.css?t=201801291615"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
			var mainServer = "${mainserver}";
			var resourcepath = "${resourcepath}";
			var myCustId = "${customerId}";
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
		<div id="layout">
			<div class="content-wrap">
				<div class="content">
					<!--好友列表-->
					<div class="friendlist">
					</div>
					
					<div class="loadingmore">   
						<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
					</div>
					
				</div>
			</div>
		</div>
		<div class="bkbg"></div> 
		<div class="shepassdboxs">
				<span>确定要取消关注吗</span>  
				<div class="qushan">
					<a class="left" href="javascript:closeConcernAlert();">放弃</a>
					<a class="right" id="cancelId" href="javascript:void(0);">确定</a> 
				</div>
		</div>
		<!--暂无消息-->
		<div class="noyhbg" style="display: none;">
			<img src="${resourcepath}/weixin/img/lingdan.png"/>
			<span>暂无消息~</span>
		</div>
		<div class="vaguealert">
			<p></p>
		</div>
		
		<input type="hidden" name="pageCount" id="pageCount" value="">
	    <input type="hidden" name="pageNow" id="pageNow" value="">
	    <input type="hidden" name="pageNext" id="pageNext" value="">
		<script src="${resourcepath}/weixin/js/common.js"></script> 
		<script src="${resourcepath}/weixin/js/news/msg_newattention.js?time=201802090001"></script> 
		<script type="text/javascript">
			$(function(){
				var bool=false;  
	            setTimeout(function(){  
	                  bool=true;  
	            },1000); 
		            
				pushHistory(); 
				
			    window.addEventListener("popstate", function(e) {
			    	if(bool){
			    		window.location.href= "${mainserver}/weixin/message/showUnread";	
			    	}
			    }, false);
			    
			    function pushHistory(){ 
			        var state = { 
		        		title: "title", 
		        		url:"#"
		        	}; 
		        	window.history.pushState(state, "title","#");  
			    }
			    
			});
		
		</script>
	</body>
</html>
