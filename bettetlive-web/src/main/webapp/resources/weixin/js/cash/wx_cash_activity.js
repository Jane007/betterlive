function cashGift(){
			if(myCustId == null || myCustId <= 0){
				window.location.href = mainServer+"/weixin/tologin?backUrl="+window.location.href;
				return;
			}
			var codeNum = $("#codeNum").val();
			if(codeNum != null && codeNum != "" && codeNum.length > 0){
// 				if(!/^[0-9]*[1-9][0-9]*$/.test(codeNum)){
// 					showvaguealert("只能输入正整数");
// 					return;
// 				}
				if(codeNum.length > 6){
					showvaguealert("兑换码格式错误");
					return;
				}
			}else{
				showvaguealert("请输入兑换码");
				return;
			}
			
			$.ajax({                                       
				url:mainServer+'/weixin/customercash/getCashGift',
				data:{
					"codeValue":codeNum,
				},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.code == "1010"){	//操作成功
						showvaguealert("您已兑换成功");		
						setTimeout(function(){
							toBuy(data.data);
						},900);
					}else if (data.code == "1011"){
						window.location.href = mainServer+"/weixin/tologin?backUrl="+window.location.href;
					}else if(data.code == "1405"){
						showvaguealert(data.msg);
						setTimeout(function(){
							toBuy(data.data);
						},900);
					}else{
						showvaguealert(data.msg);
					}
				},
				failure:function(data){
					showvaguealert('出错了');
				}
			});
		}
		
		function toBuy(productId){
			if(productId == null || productId == "" || parseInt(productId) <= 0){
				showvaguealert('出现异常，请及时联系客服~');
				return;
			}
			var ordSource = "";
			var theUrl = window.location.href;
			if(theUrl.indexOf("source")!=-1){
				ordSource = getQueryString("source");
				ordSource = "&orderSource="+ordSource;
			}
			window.location.href = mainServer+"/weixin/product/towxgoodsdetails?productId="+productId+ordSource;
		}
		
		function toMyOrder(){
			
			var backUrl =  window.location.href;
			if(myCustId == null || myCustId <= 0){
				window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
				return;
			}else{
				window.location.href = mainServer+"/weixin/order/findList?status=0";
			}
		}