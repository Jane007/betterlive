
		var area1 = new LArea();
	    area1.init({
	        'trigger': '#areabox', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
	        'valueTo': '#value1', //选择完毕后id属性输出到该位置
	        'keys': {
	        	 id: 'value',
	             name: 'text'
	        }, //绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
	        'type': 2, //数据源类型
	        'data':[provs_data, citys_data, dists_data],
	    });
	    //area1.value=[1,13,3];//控制初始位置，注意：该方法并不会影响到input的value
		
		
		$(function(){
			var rid = $("#receiverId").val();
			if(rid == null || rid == ""){
				$("#receiverId").val(0);
			}
			
			

			if(isDefault==1){
				$(this).find('.default').addClass('on').parents('.addresslist').siblings('.addresslist').find('.default').removeClass('on');
		    	$(this).find('input[type=radio]')[0].checked=true; 
			}
			
			
			//点击设置默认地址
			$(document).on('click','.debox',function(){
				if($(".debox .default").hasClass('on')){
					$(".debox .default").removeClass('on');
			    	$(this).find('input[type=radio]')[0].checked=false;
				}else{
					$(".debox .default").addClass('on');
			    	$(this).find('input[type=radio]')[0].checked=true;
				}
			});
		    
			$(".saveAddress").bind('click',function(){
		    	var receiveName=$("#receiveName").val();
		    	var receiveMobile=$("#receiveMobile").val(); 
		    	var addressDetail=$("#addressDetail").val();
		    	if(null == receiveName || ''==receiveName){
		    		showvaguealert("请输入收货人姓名")
		    		return false;
		    	}
		    	
		    	if(null == receiveMobile || ''==receiveMobile){
		    		showvaguealert("请输入收货人手机号码")
		    		return false;
		    	}else{
		    		 var pattern = /^1[34578]\d{9}$/; 
		    		 if(!pattern.test(receiveMobile)){
		    			 showvaguealert("请输入正确的手机号码")
				    		return false;
		    		 } 
		    	}
		    	
		    	var area = $('#areabox').val();
				if(null == area || ''==area || "省份，城市，区县" == area){
					showvaguealert("请选择省市县")
		    		return false;
				}
		    	if(null == addressDetail || ''==addressDetail){
		    		showvaguealert("请输入详细地址")
		    		return false;
		    	}
		    	var code = $("#value1").val(); //地区编码，逗号分隔
		    	
		    	var receiverId=$('#receiverId').val();
		    	
		    	var isDefault=$("#isDefault").val();
		    	var val=$('input:radio[name="address"]:checked').val();
		    	 if(val==null){
		    		 isDefault=0;
		    	 }else{
		    		 isDefault=1;
		    	 } 
		    	$.ajax({
					url:mainServer+'/weixin/addressmanager/doAddAdress',
					data:{
						receiveName:receiveName,
						receiveMobile:receiveMobile,
						area:area,
						areaCode:code,
						addressDetail:addressDetail,
						receiverId:receiverId,
						isDefault:isDefault
					},
					dataType:"json",
					type:"POST",
					async: false,
					success:function(data){
						if(data.msg=='success'){
							$('.saveAddress').unbind("click");
						//	showvaguealert("添加地址成功");
							if(null!=returnType && ""!=returnType){
								window.location.href=mainServer+"/weixin/addressmanager/toChooseAddress?returnType="+returnType+"&receiverId="+receiver_id;
							}else{
								toBack();
							}
							
						}else{
							showvaguealert("添加地址失败");
						}
					}
		    	})
		    	
		    })
		    
		    function toBack(){
	        	window.location.href=mainServer+"/weixin/addressmanager/toReceiverAddress";
	        }
		    //提示弹框
			function showvaguealert(con) {
		    	$('.mask').show();
				$('.vaguealert').show();
				$('.vaguealert').find('p').html(con);
				setTimeout(function() {
					$('.vaguealert').hide();
					$('.mask').hide();
				}, 1000);
			}
		});
		$(function(){
			var url="";
			if(null!=returnType && ""!=returnType){
				url=mainServer+"/weixin/addressmanager/toChooseAddress?returnType="+returnType+"&receiverId="+receiver_id;
			}else{
				url=mainServer+"/weixin/addressmanager/toReceiverAddress";
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