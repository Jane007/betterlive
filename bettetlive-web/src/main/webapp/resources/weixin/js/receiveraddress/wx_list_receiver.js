
		$(function(){
			$(".initloading").show();
			setTimeout(function(){
				$(".initloading").hide();
			},800);
			
			//点击设置默认地址
			$(document).on('click','.debox',function(){
				$(this).find('.default').addClass('on').parents('.addresslist').siblings('.addresslist').find('.default').removeClass('on');
			    $(this).find('input[type=radio]')[0].checked=true;
		    	var  receiverId=$(this).find('.default').parents('.addresslist').attr("id");
				if(null !=receiverId && ''!=receiverId && receiverId>0){		    	
			    	$.ajax({
		 			    type: 'POST',
		 			    url: mainServer+'/weixin/addressmanager/updatedeDaultReceive',
		 			    data:{'receiverId':receiverId},
		 			    dataType:'json',  
		 	 			success:function(data) { 
		 	 				if(data.result =='succ' ){  
		  			     		showvaguealert(data.msg);
		  			     		window.location.href=mainServer+"/weixin/addressmanager/toReceiverAddress";
		  			     	}  
		 	 			},
		 	 			error : function() {  
		 	 				showvaguealert('出现异常');
		 	 			} 
		 	 		});
				}
			});
			
			
			if(null==receiverAddress || ''==receiverAddress){
				$(".no-address").show();
			}else{
				$(".no-address").hide();
			}
			
			//提示弹框
			function showvaguealert(con) {
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function() {
					$('.vaguealert').hide();
				}, 2000);
			}
			
			//操作成功提示弹框
			function succeshowvaguealert(con) {
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function() {
					$('.vaguealert').hide();
				}, 3000);
			}
			
			//删除收货地址
			$(".addressdel").click(function(){
				var receiverId = $(this).parents('.addresslist').attr("id");
				var delItem = $(this);
				if(null !=receiverId && ''!=receiverId && receiverId>0){		    	
			    	$.ajax({
		 			    type: 'POST',
		 			    url: mainServer+'/weixin/addressmanager/deleteReceiverAddress',
		 			    data:{'receiverId':receiverId},
		 			    dataType:'json',  
		 	 			success:function(data) { 
		  			        if(data.result =='succ' ){  
		  			        	//删除当前收货地址
					        	delItem.closest('.addresslist').remove();
		  			        	succeshowvaguealert(data.msg);
		  			        	//判断是否一个地址都没有
		  			        	if ($('.consigneebox').children().length == 0) {
		  			        		$('.no-address').show();
		  			        	}
		  			     	}else{
		  			     		showvaguealert(data.msg);
		  			     	}  
		 	 			},
		 	 			error : function() {  
		 	 				showvaguealert('出现异常');
		 	 			} 
		 	 		});
				}else{
					showvaguealert('请选择需要删除的记录');
				}
			});
		});
		
		$(function(){
			var bool=false;  
            setTimeout(function(){  
                  bool=true;  
            },1000); 
	            
			pushHistory(); 
			
		    window.addEventListener("popstate", function(e) {
		    	if(bool){
		    		window.location.href=mainServer+"/weixin/tologin";
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
	