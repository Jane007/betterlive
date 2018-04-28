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
		 	 				if(data.result !='succ' ){  
		  			     		showvaguealert(data.msg);
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
			
			
			//选中地址
			$('.Receivingaddress').click(function(){
				$(this).parents('.addresslist').addClass('active');
				$(this).parents('.addresslist').siblings('.addresslist').removeClass('active');
				
				var useAddress=$(this).parents('.addresslist.active').attr("id");
				
				
				if(returnType==1 || returnType==5 || returnType==6){
					window.location.href=mainServer+'/weixin/order/addBuyOrder?useAddress='+useAddress;
					
				}else if(returnType==2){
					window.location.href=mainServer+'/weixin/order/addBuyOrders?useAddress='+useAddress;
				}else if(returnType==3){//线下活动支付
					window.location.href=mainServer+'/weixin/order/addXXBuyOrder?useAddress='+useAddress;
				}else if(returnType==4){//团购
					window.location.href=mainServer+'/weixin/order/addOrderByGroup?useAddress='+useAddress;
				}else{
					showvaguealert("缺失重要参数");
				}
			});
			
			$('.consignee').click(function(){
				$(this).parents('.addresslist').addClass('active');
				$(this).parents('.addresslist').siblings('.addresslist').removeClass('active');
				
				var useAddress=$(this).parents('.addresslist.active').attr("id");
				
				
				if(returnType==1 || returnType==5 || returnType==6){
					window.location.href=mainServer+'/weixin/order/addBuyOrder?useAddress='+useAddress;
					
				}else if(returnType==2){
					window.location.href=mainServer+'/weixin/order/addBuyOrders?useAddress='+useAddress;
				}else if(returnType==3){//线下活动支付
					window.location.href=mainServer+'/weixin/order/addXXBuyOrder?useAddress='+useAddress;
				}else if(returnType==4){//团购
					window.location.href=mainServer+'/weixin/order/addOrderByGroup?useAddress='+useAddress;
				}else{
					showvaguealert("缺失重要参数");
				}
			});
			
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
			var url="";
			if(returnType==1 || returnType==5 || returnType==6){
				url=mainServer+'/weixin/order/addBuyOrder';
			}else if(returnType==2){
				url=mainServer+'/weixin/order/addBuyOrders';
			}else if(returnType==3){//线下活动支付
				url=mainServer+'/weixin/order/addXXBuyOrder';
			}else if(returnType==4){//团购
				url=mainServer+'/weixin/order/addOrderByGroup';
			}else{
				showvaguealert("缺失重要参数");
			}
			
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