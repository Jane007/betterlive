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
		<title>挥货-账号信息</title>
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
		<input type="hidden" id="SERVER_TIME" name="SERVER_TIME" readonly="readonly" value="${now.getTime()}" />
		<script src="${resourcepath}/weixin/js/refresh.js"></script>
		<div class="container">
			<div class="mainBox" style="top:0.2rem; ">
   
				<div class="contentBox">
					<div class="identityBox">
						<form action="" class="verification" id="phoneForm" style="display: none;">
							<ul>
								<li class="phoneBox">
									<input type="text" class="numberbox" placeholder="请输入手机号码" value="${phone}"  readonly="readonly" id="phoneNum" maxlength="11" />
									<span id="getCode">获取验证码</span>
								</li>
								<li>
									<input type="text" placeholder="请输入验证码" name="msgCode" id="msgCode" maxlength="5"/>
								</li>
							</ul>
							<div class="nextStep" id="msgNext">
							        确定  
						 	</div>
						</form>
					
						<form action="" class="verification" style="display:none;" id="newphone">
							<ul>
								<li class="phoneBox">
									<input type="text" placeholder="请输入新手机号码" class="numberbox" name="newphoneNum" id="newphoneNum"  maxlength="11" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" />
									<span id="getNewCode">获取验证码</span>
								</li>
								<li>
									<input type="text" placeholder="请输入验证码" name="msgCode" id="msgNewCode" maxlength="5"/>
								</li>
							</ul>
								<div class="nextStep" id="saveNewPhone" >
								         确定
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
		var msgCode = '';//发送到手机上的验证码
		var sMobile = '${customer.mobile}'
		var codeflag = false;
		$(function(){
			if(sMobile != null && sMobile != ""){
				$("#phoneForm").show();
			}else{
				$("#newphone").show();
			}
			//点击弹框好的
			$('.backlogin').click(function(){
				$('.set-success').hide();
				$('.mask').hide();
			});
			
			
			$('#saveNewPhone').click(function(){
				var mobile = $('#newphoneNum').val();
				if(mobile == null || mobile == ""){
		    		 showvaguealert('请输入手机号码');
					  return false;
		    	}else if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(mobile))){ 
					  flag = false;
					  showvaguealert('手机号码格式错误');
					  return false;
				}else if(sMobile==mobile){
					  showvaguealert('新号码与旧号码一致');
					  return false;
				}else{
					var code = $('#msgNewCode').val();
					if(code.length==0){
						 showvaguealert('请输入验证码');
						 return false;
					}
					$.ajax({
						type: 'POST',
					   	url: mainServer + "/weixin/presentcard/checkNewSmsCode",
					   	data:{"phoneNum":mobile, "checkCode":code},
					   	async: false,
					   	dataType:'json',  
							success:function(data) { 
							  	if(data.result == 'success'){
							  		$.ajax({
									   type: 'POST',
									   url: mainServer + "/weixin/customer/editNewPhone",
									   data:{'mobile':mobile},
									   async: false,
									   dataType:'json',  
									   success:function(data) { 
										  if(data.result=='succ'){
											  showvaguealert(data.msg);
											  setTimeout(function(){
					                        		location.href="${mainserver}/weixin/customer/toCustomerModify";
					            				},1000);
											  /* location.href=mainServer+'/weixin/usercenter/toAccount'; */
										  }else if(data.result=="failure"){
											  window.location.href = "${mainserver}/weixin/tologin";
										  } else{
											  showvaguealert(data.msg);
										  }
									   }
								  })
								  
							  	}else if(data.result=="failure"){
									  window.location.href = "${mainserver}/weixin/tologin";
							  	} else{
								  	showvaguealert(data.msg);
							  	}
					   		}
					});
					
				}
			})
			
			//****************** 验证旧手机号码  ****************************
			var count=59;
			function countDown(){
				$("#getCode").html(count+"s");
				if(count <= 0){
					$("#getCode").css("background","#e62d29");
					$("#getCode").bind("click",function(){
						getMessage();
					});
					$("#getCode").html("再次发送");
					count=59;
				}else{
					setTimeout(countDown, 1000);
					count--;
				}
			}
			//点击获取验证码
			$('#getCode').click(function(){
				var phoneNum =sMobile;// $('#phoneNum').val();
				if(phoneNum == null || phoneNum == ""){
					 showvaguealert('非法请求');
					 return false;
				}
				var $this = $(this);
				if(!codeflag){
					getMessage();
					if(!codeflag){
						return;
					}
					$(this).addClass('codeactive');
					$('#getCode').text('重新获取 ('+ 60+')');
					var codetime=setInterval(countdown,1000);
					var i=60;
					function countdown(){
						i--;
						$('#getCode').text('重新获取 (' + i+ ')');
						if(i<=0){
							i=0;
							clearInterval(codetime);
							$('#getCode').text('获取验证码').removeClass('codeactive');
							codeflag=false;
						}
					}
					codeflag=true;
				}
				
			});
			
			
			//****************** 验证新手机号码  ****************************
			var newcount=59;
			function newCountDown(){
				$("#getNewCode").html(newcount+"s");
				if(newcount <= 0){
					$("#getNewCode").css("background","#e62d29");
					$("#getNewCode").bind("click",function(){
						getNewMessage();
					});
					$("#getNewCode").html("再次发送");
					newcount=59;
				}else{
					setTimeout(newCountDown, 1000);
					newcount--;
				}
			}
			//点击获取验证码
			$('#getNewCode').click(function(){
				var mobile = $('#newphoneNum').val();
				var $this = $(this);
				if(mobile==null || mobile==''){
					showvaguealert('请输入手机号码！');
				}else{
					getNewMessage();
				}
				
				
			});
			
			function toBack(){
	        	window.location.href="${mainserver}/weixin/tologin";
	        }
			
			
			 //绑定手机号码获取验证码
			/* function getMessage(){
				var phoneNum = $('#phoneNum').val();
					$.ajax({
						type: 'POST',
					   url: mainServer + "/weixin/presentcard/sendMessage",
					   data:{'phoneNum':phoneNum},
					   async: false,
					   dataType:'json',  
					   success:function(data) {
						   if('fail' == data.result){
							   showvaguealert("手机验证码发送失败！")
						   }else{
							   showvaguealert("手机验证码已经发送！");
							   $('#getCode').css("background","#E3DBDA");
							   $('#getCode').unbind("click");
							   setTimeout(countDown, 1000);
						   }
						  
					   }
				  })
			} */
			
			
			
			 //绑定手机号码获取验证码
			function getMessage(){
		    	var phoneNum =sMobile;
		    	$.ajax({
		    		url: "${mainserver}/weixin/usercenter/sendEmail",
					type: "POST",
					dataType:'json',
					async:false,
					data:{'eMail':phoneNum,'selectType':'phone'},
					success: function(data) {
						if('fail' == data.result){
							    showvaguealert(data.msg)
							    codeflag=true;
						   }else{
							   showvaguealert(data.msg);
							   codeflag=false;
							   $('#getCode').css("background","#E3DBDA");
							   $('#getCode').unbind("click");
							   setTimeout(countDown, 1000);
						   }
					}
				});
		    }
			
			//绑定新手机号码获取验证码
			function getNewMessage(){
		    	var phoneNum =$('#newphoneNum').val();
		    	if(phoneNum == null || phoneNum == ""){
		    		 showvaguealert('请输入手机号码');
					  return false;
		    	}else if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phoneNum))){ 
					  flag = false;
					  showvaguealert('手机号码格式错误');
					  return false;
				}else if(sMobile==phoneNum){
					  showvaguealert('新号码与旧号码一致');
					  return false;
				}
		    	$.ajax({
		    		url: "${mainserver}/weixin/usercenter/sendEmail",
					type: "POST",
					dataType:'json',
					async:false,
					data:{'eMail':phoneNum,'selectType':'phone'},
					success: function(data) {
						if('fail' == data.result){
							 showvaguealert(data.msg)
						}else{
						    showvaguealert("手机验证码已经发送！");
						    $('#getNewCode').css("background","#E3DBDA");
						    $('#getNewCode').unbind("click");
						    setTimeout(newCountDown, 1000);
						    
						}
					}
				});
		    }
			
			
			$('#msgNext').click(function(){
				var phoneNum =sMobile;// $('#phoneNum').val();
				if(phoneNum == null || phoneNum == "" || phoneNum.length <= 0){
					 showvaguealert('非法请求');
					 return false;
				}
				var code = $('#msgCode').val();
				if(code == null || code == "" || code.length==0){
					 showvaguealert('请输入验证码');
					 return false;
				}
				$.ajax({
					type: 'POST',
				   	url: mainServer + "/weixin/presentcard/checkSmsCode",
				   	data:{"phoneNum":phoneNum, "checkCode":code},
				   	async: false,
				   	dataType:'json',  
						success:function(data) { 
						  	if(data.result == 'success'){
							  	$('.s2').addClass('active').siblings('span').removeClass('active');
								$('.stepBox p span').css("width","100%");
								$('#newphone').show().siblings('form').hide();
						  	}else if(data.result=="failure"){
								  window.location.href = "${mainserver}/weixin/tologin";
						  	}else{
							  	 showvaguealert(data.msg);
						  	}
				   		}
				});
			});
			
			
			//提示弹框
			function showvaguealert(con){
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function(){
					$('.vaguealert').hide();
				},1000);
			}
		});
		
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
		});
	
	</script>
</html>
