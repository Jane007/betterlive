//初始化swiper
		var mySwiper=new Swiper(".swiper-container",{
			loop:true,
			pagination : '.swiper-pagination',
			autoplayDisableOnInteraction : false,
			autoplay:'3000'
		});
		
		$(function(){
			var raiseMoney1 = $("#raiseMoney1").val();
			var raiseTarget1 = $("#raiseTarget1").val();
			var raiseMoney = (raiseMoney1/10000).toFixed(2);
			var raiseTarget = (raiseTarget1/10000).toFixed(2);
			var barwidth = ((raiseMoney1/raiseTarget1)*100).toFixed(2)+"%";
			$("#bar").css("width",""+barwidth+"");
		});
		
		
		$(function(){
			//日期转换
			var time1 = '${preProduct.deliverTime}';
			var x1 = time1.substring(0,time1.indexOf(" "));
			$("#spanx1").text(x1);
			
			$('.shopCar').click(function(){
				window.location.href=mainServer+'/weixin/shoppingcart/toshoppingcar';
			});
			
			//返回上一层
			$('.backPage').click(function(){
		//		window.history.go(-1);
				window.location.href=mainServer+'/weixin/prepurchase/findList';
			});
			
			//底部tab切换
			$('.footer li').click(function(){
				$(this).addClass('active').siblings('li').removeClass('active');
			});
			//滚动一定的距离出现返回顶部
			$(window).bind("scroll", function(){ 
				var top = $(this).scrollTop(); // 当前窗口的滚动距离
		        if(top>=300){
		        	$('.backTop').show();	
		        }else{
		        	$('.backTop').hide();
		        }
		  	});
			//点击返回顶部
			$('.backTop').on('click',function(){
				$(document).scrollTop('0');
			});
			
			//弹出规格窗口
			$('.etalon').click(function(){
				$('.standardBox').addClass('active');
				$('.mask').show();
			});
			//点击隐藏规格窗口
			$('.outBox').click(function(){
				hideObj();
				isActive();
				if (specLen > 1) {
					is_select = 0;
				}
			});
			
			function hideObj(){
				$('.standardBox').removeClass('active');
				$('.mask').hide();
			}
			
			//***********************规格窗口*********************************
			//价格
			var oprice=$('.productPrice span').text();
			$('.normsPrice span').text(oprice)
			
			//选择规格
			$('.installBox li').click(function(){
				is_select = 1;
				$(this).addClass('active').siblings('li').removeClass('active');
				$('#spec-img').attr('src', $(this).attr("spec-img"));
				var standnumber=$(this).html();
				var standprice=$(this).attr("value");
				var standspecId=$(this).attr("id");
				stockCopy=$(this).attr("data-copy");
				limitNum=$(this).attr("data-limit");
				hasBuy = $(this).attr("data-hasBuy");
				restCopy=$(this).attr("data-restCopy");
				carNums=$(this).attr("data-carNums");
				carCanAdd=$(this).attr("data-carCanAdd");
				packageDesc=$(this).attr("data-package");
				if(limitNum!=null&&limitNum!=''&&parseInt(limitNum)>=1){
					$('.normsChoose').html('已选择：'+standnumber+'(限购'+limitNum+'件)');
				}else{
					$('.normsChoose').html('已选择：'+standnumber);
				}
				
				$('.normsPrice span').text(standprice)
				$("#productSpecId").val(standspecId);
				$("#buyAmount").val($('.calGoodNums').val());
				$('.proPic img').attr("src",$("#spec_img"+standspecId).val());
				if(stockCopy!=null&&stockCopy!=''){
					if(parseInt(stockCopy)>=1){
						$("#stockCopy").html("库存<em>"+stockCopy+"</em>件");
						$("#buyForm").show();
					}else{
						$("#stockCopy").html("已秒完");
						$("#buyForm").hide();
					}
				}
				if(packageDesc!=null&&packageDesc!=''){
					$("#packageDesc").html("套餐说明："+packageDesc);
				}else{
					$("#packageDesc").html("");
				}

			});
			
			//选择规格之后才可以点击数量
			var outli=$('.installBox li');
			//增加数量
			$('.calAdd').click(function(){
				if(outli.hasClass('active')){
					var $this=$(this);
					var $numval=parseInt($(this).prev('input.calGoodNums').val());
					
			    	var maxBuy = limitNum;
			    	var buyAmount=$("#buyAmount").val();
			    	if(stockCopy!=null&&stockCopy!=''){
			    		if(parseInt(stockCopy)<=0){
			    			showvaguealert("此产品规格暂无库存");
			    			return false;
			    		}
			    	}
			    	if(parseInt(buyAmount)>=parseInt(stockCopy)){
		    			showvaguealert("库存仅剩"+stockCopy+"件");
		    			return false;
		    		}
			    	if(parseInt(limitNum)>=1){
			    		
			    	
				    	if(parseInt(restCopy)<=0){//剩余购买数量
			    			showvaguealert("此商品限购"+maxBuy+"件");
			    			return false;
			    		}
			    		
				    	if(parseInt(hasBuy)>0&&parseInt(buyAmount)>parseInt(restCopy)){
			    			showvaguealert("您已经购买了"+hasBuy+"件，可再购买"+restCopy+"件aa");
			    			return false;
			    		}
			    		if(parseInt(buyAmount)>parseInt(restCopy)){
			    			showvaguealert("此产品限购"+maxBuy+"份");
			    			return false;
			    		}
			    		
			    		if(parseInt(buyAmount)>=1){
			    				if(parseInt(maxBuy)-parseInt(hasBuy)<=parseInt(buyAmount)){
				    				showvaguealert("此产品限购"+maxBuy+"件,最多可购买"+restCopy+"件");
					    			return false;
				    			}
			    		}
				    }
					$(this).siblings('.calGoodNums').val($numval+1);
					$("#buyAmount").val($numval+1)
				}else{
					showvaguealert('请先选择规格');
				}
			});
			//减少数量
			$('.calCut').click(function(){
				if(outli.hasClass('active')){
					var $numval=parseInt($(this).next('input').val());
					if($numval<=2){
						$numval=2;
					}
					$(this).siblings('.calGoodNums').val($numval-1);
					$("#buyAmount").val($numval-1)
				}else{
					showvaguealert('请先选择规格');
				}
			});
			
			$(".shopCar").click(function(){
				window.location.href=mainServer+'/weixin/shoppingcart/toshoppingcar';
			});
			
			initSepc();
			
		
		});
		
		//默认选择第一个规格，初始化价格及已选择
		function initSepc() {
			if (specLen == 1) {
				$('.installBox li:first').click();
				//更新规格数量显示
				isActive();
			}
		}
		
		function isActive(){
			var oli=$('.installBox li');
			if(oli.hasClass('active')){
				var oguige = ($('.normsChoose').text()).replace('已选择：','');
				var oval = $('.calGoodNums').val();
				$('.etalon span').text(oguige);
				$('.etalon i').text('x'+oval);
				
				$("#buyAmount").val($('.calGoodNums').val());  
			}
		}
		
		//提示弹框
		function showvaguealert(con){
			$('.vaguealert').show();
			$('.vaguealert').find('p').html(con);
			setTimeout(function(){
				$('.vaguealert').hide();
			},2000);
		}
		
		
		var is_select = 0;  //是否选择了规格
		var buyMS = restCopy;			//马上购买剩余的购买数量
		function toAddShotCar(type){
			//是否有一个规格被选中
			var is_spec_active = $('.installBox ul li').hasClass('active');
			if ((is_select == 0) && (specLen > 1) && !is_spec_active) {  //没有选规格
				if ($('.standardBox').hasClass('active')) {  //如果已经弹出选择规格框，则提示请选择规格
					showvaguealert('请选择商品规格！');
				} else {
					$('.etalon').click(); //弹出选择规格
				}
				return;
			} else if ((is_select == 0) && is_spec_active) {
				if (!$('.standardBox').hasClass('active')) {  //如果已经弹出选择规格框，则提示请选择规格
					$('.etalon').click(); //弹出选择规格
					return;
				}
			}
			
			if(1==type &&( null==mobile || ""==mobile) ){
				$(".boundPhone").show();
				$('.dia-mask').show();
				return false;
			}
			
	    	var productId=$("#productId").val();
	    	if(""==productId||null ==productId){
	    		showvaguealert('请重新选择商品！');
	    		return false;
	    	}
	    	
	    	var productSpecId= $("#productSpecId").val();
	    	if(null ==productSpecId || ''==productSpecId ){
	    		showvaguealert('请选择商品规格！');
	    		return false;
	    	}
	    	
	    	var buyAmount=$("#buyAmount").val();
	    	if(""==buyAmount||null ==buyAmount){
	    		showvaguealert('请选择购买数量！');
	    		return false;
	    	}
	    	
			var extension_type=$("#extension_type").val();       //商品类型
			
			if(stockCopy!=null&&stockCopy!=''){
	    		if(parseInt(stockCopy)<=0){
	    			showvaguealert("此产品规格暂无库存");
	    			return false;
	    		}
	    	}
	    	if(parseInt(buyAmount)>parseInt(stockCopy)){
    			showvaguealert("库存仅剩"+stockCopy+"件");
    			return false;
    		}
	    	
		    if(type==2){
		    	var $this = $('.installBox ul li.active');
    			stockCopy=$this.attr("data-copy");
				limitNum=$this.attr("data-limit");
				hasBuy = $this.attr("data-hasBuy");
				restCopy=$this.attr("data-restCopy");
				carNums=$this.attr("data-carNums");
				carCanAdd=$this.attr("data-carCanAdd");
				if(parseInt(limitNum)>0){//限购才走这些判断
						if(parseInt(carNums)>0){//大于0才限购
		    				if(parseInt(buyAmount)>parseInt(carCanAdd)){
			    				showvaguealert("您的购物车里已有"+carNums+"件，最多只能有"+limitNum+"件");
				    			return false;
				    		}
			    			
		    			}
		    			if(parseInt(buyAmount)>parseInt(restCopy)){
			    			showvaguealert("此商品限购"+limitNum+"，不可加入购物车");
			    			return false;
			    		}
		    			if(parseInt(carCanAdd)<0){
		    				showvaguealert("此产品限购"+limitNum+"份");
			    			return false;
		    			}
					}
		    		$.ajax({
			    		url:mainServer+'/weixin/shoppingcart/addShoppingCar',
			    		type:'post',
			    		dataType:'json',
			    		data:{
			    			'productId':productId,
			    			'productSpecId':productSpecId,
			    			'amount':buyAmount,
			    			'extension_type':extension_type
			    		},
			    		success:function(data){
			    			if(data.result=='succ'){
			    				var cnums=parseInt(carNums)+parseInt(buyAmount);
			    				var ccAdd = parseInt(carCanAdd)-parseInt(buyAmount);
			    				var rc= parseInt(restCopy)-parseInt(buyAmount);
			    				
			    				$this.attr("data-carNums",cnums);
			    				$this.attr("data-carCanAdd",ccAdd);
			    				$this.attr("data-restCopy",rc);
			    				
			    				
				    			$(".totNums").empty();
				    			$(".totNums").text(data.cartCnt);
				    			if(!$(".totNums").hasClass("totNums_sp")){
				    				$(".totNums").addClass("totNums_sp");
				    			}
				    			$(".outBox").click(); //关闭商品规格弹框
				    			if (specLen > 1) {
					    			is_select = 0;
				    			}
			    			}
			    			showvaguealert(data.msg);
			    		}
			    	});
		    		
		    	}else if(type==1){
		    		if(parseInt(limitNum)>0){//限购才走这些判断
			    		if(parseInt(buyAmount)>parseInt(buyMS)){
			    			showvaguealert("您已经购买了"+hasBuy+"件，不可再购买");
			    			return false;
			    		}
			    		if(parseInt(buyAmount)>parseInt(limitNum)){
		    				showvaguealert("此商品限购"+limitNum+"件，不可再购买");
			    			return false;
		    			}
		    		}
		    		
		    		is_select = 0;
					$("#buyForm").submit();
		    	}
	    	
	    }
		
		
		$(".evaluateBox .evaTitle").click(function(){
			var productId=$("#productId").val();
	    	if(""==productId||null ==productId){
	    		showvaguealert('参数错误！');
	    		return false;
	    	}
			window.location.href=mainServer+'/weixin/productcomment/findList?productId='+productId+'&prepurchase=1';
		});
		
		
		
		$(function(){
			
			//判断手机号码
			var phoneflag=false;
			$("#mobile").change(function(){
				var $phone=/^((1[0-9]{2})+\d{8})$/;
				var $phoneval=$("#mobile").val();
				if($phoneval){
					if(!$phone.test($phoneval)){ 
						showvaguealert('您输入的手机号有误！');
					    phoneflag=false;
					}else{
						phoneflag=true;
					}
				}else{
					showvaguealert('您输入的手机号不能为空!');
				}
				
			});
			
			//点击获取验证码
			var codeflag=false;
			$('.verificationcode').click(function(){
				var $phoneval=$("#mobile").val();
				if(!codeflag){
					if(!codeflag&&phoneflag){
						$(this).addClass('codeactive');
						$('.verificationcode').text('重新获取 ('+ 60+')');
						var codetime=setInterval(countdown,1000);
						var i=60;
						function countdown(){
							i--;
							$('.verificationcode').text('重新获取 (' + i+ ')');
							if(i<=0){
								i=0;
								clearInterval(codetime);
								$('.verificationcode').text('获取验证码').removeClass('codeactive');
								codeflag=false;
							}
						}
						codeflag=true;
						getVerifCode();
					}else{
						if($phoneval){
							showvaguealert('您输入的号码有误！');
						}else{
							showvaguealert('您输入的号码不能为空！');
						}
					}
				}
				
			});
			
			
			//判断验证码
			var getflag=false;
			var $code=/^[0-9]{5}$/;
			$("#verifycode").change(function(){
				var $entrypass=$("#verifycode").val();
				if(!$code.test($entrypass)){ 
					showvaguealert('验证码为5位数字!');
				    getflag=false;
				}else{
					getflag=true;
				}
			});
			
			
			//点击注册确定
			$('.now-bound').click(function(){
				if(getflag&&phoneflag){
					var phone=$("#mobile").val();
					var verifycode=$("#verifycode").val();
					
					$.ajax({
						url:mainServer+'/weixin/usercenter/toRegeditUser',
						data:{
							"mobile":phone,
							"code":verifycode
						},
						type:'post',
						dataType:'json',
						success:function(data){
							if(data.result=='succ'){
								mobile=phone;
								
								$(".boundPhone").hide();
								$('.dia-mask').hide();	
							}else{
								showvaguealert(data.msg);
							}
						},
						failure:function(data){
							showvaguealert('出错了');
						}
					});
					
					
				}else{
					var $phoneval=$("#mobile").val();
					var $entrypass=$("#verifycode").val();
					
					if(!$phoneval){
						showvaguealert('您输入的号码不能为空！');
					}else if(!phoneflag){
						showvaguealert('您输入的号码有误！');
					}else if(!$entrypass){
						showvaguealert('验证码不能为空！');
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
			},2000);
		}
		
		
		//绑定手机号码获取验证码
		function getVerifCode(){
	    	var phoneNum =$("#mobile").val();
	    	$.ajax({
	    		url: mainServer+"/weixin/presentcard/sendMessage",
				type: "POST",
				dataType:'json',
				async:false,
				data:{'phoneNum':phoneNum},
				success: function(data) {
					var obj = data;
					if('fail' != data.result){
						showvaguealert('获取验证码成功！');
					}else{
						showvaguealert('获取验证码失败！');
					}
				}
			});
	    } 
		
		
		//点击取消绑定手机框
		$('.boundout').click(function(){
			$('.boundPhone').hide();
			$('.dia-mask').hide();
		});
		
		$('.backPage').click(function(){
			 window.history.go(-1);
		});