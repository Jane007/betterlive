var tabIndex = 0;
	var nextIndex = 1;
	var loadtobottom=true;
	$(function() {
		$("#myordercent0").html("");
		$("#myordercent0").show();
		$(".noyhbg").hide();
		querySingleCoupon(0,1,10);
		var ordernav = $(".myordrnav li");
		var ordercent = $(".myordercent");
		for (var i = 0; i < ordernav.length; i++) {
			ordernav[i].index = i;
			ordernav[i].onclick = function() {
				for (var i = 0; i < ordernav.length; i++) {
					ordernav[i].className = "";
					ordercent[i].style.display = "none";
				}
				ordernav[this.index].className = "current";
				ordercent[this.index].style.display = "block";
				$("#myordercent"+this.index).html("");
				$("#myordercent"+this.index).show();
				$(".noyhbg").hide();
				tabIndex = this.index;
				nextIndex = 1;
				loadtobottom=true;
				$("#pageNext").val(1);
				$(window).scrollTop(0);
				querySingleCoupon(this.index, 1, 10);
			}
		}
	});
	
	//往上滑
	$(document).scroll(function(){
		totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
		if($(document).height() <= totalheight){
			if(loadtobottom==true){
				var next = $("#pageNext").val();
				var pageCount = $("#pageCount").val();
				var pageNow = $("#pageNow").val();
				
				if(parseInt(next)>1){
					if(nextIndex != next){
						nextIndex++;
						querySingleCoupon(tabIndex,next,10);
						$(".loadingmore").show();  
						 setTimeout(function(){    	
							$(".loadingmore").hide(); 	
							
						},1500); 
					}
				}
				if(parseInt(next)>=parseInt(pageCount)){
					loadtobottom=false;
				} 
				
			}
		}
	});
	
	function querySingleCoupon(idx,pageIndex,pageSize){
		$(".initloading").show();
		
		var index = idx;
		if(idx==2){
			index=3;
		}
		$.ajax({
			url : mainServer+'/weixin/customercoupon/mySingleCouponList',
			data : {
				rows:pageSize,pageIndex:pageIndex,"useFlag" : index
			},
			type : 'post',
			dataType : 'json',
			success:function(data) { 
				   if(data.code == "1010"){ //获取成功
					    var pageNow = data.pageInfo.pageNow; 				   
						var pageCount = data.pageInfo.pageCount;
						$("#pageCount").val(pageCount);
						$("#pageNow").val(pageNow);
						var next = parseInt(pageNow)+1;
						if(next>=pageCount){
							next=pageCount;
						}
						$("#pageNext").val(next);
						$("#pageNow").val(pageNow);
						if((data.data == null || data.data.length <= 0) && pageIndex == 1){
							setTimeout(function(){
								$(".initloading").hide();
								$("#myordercent"+idx).hide();
								$(".noyhbg").show();
							},300);
							return;
						}else{
							$(".noyhbg").hide();
						}
						var list = data.data;
						
						for ( var continueIndex in list) {
							if (isNaN(continueIndex)) {
								continue;
							}
							var c = list[continueIndex];
							
							var statusLine = "class=\"yhbox hbdai\"";
							var couponName = c.couponName;
							if(couponName != null && couponName.length > 8){
								couponName = couponName.substring(0, 8)+"...";
							}
							if(c.status == 1){
								var usedTime = c.usedTime;
								usedTime=usedTime.substring(0,10);
	
								statusLine = "class=\"yhbox hbdai hbyi\"";
								var showHtml = '	<div '+statusLine+' >'
								+ '	 		  <div class="yhnrcent">'
								+ '	 			<div class="left">￥'+c.couponMoney+''
								+ '	 			<strong>满'+c.fullMoney+'可用</strong>'
								+ '	 			</div>'
								+ '	 			<div  class="center">'
								+ '	 				<span>'+couponName+'</span>'
	 						    + '	 			<p>限'+c.product_name+'使用</p>'
							 	+ '              <strong>使用日期'+usedTime+'</strong>'
								+ '	 			</div>' 
								+ '              <div class="right">'
								+ '	 		    </div>'
								+ '           </div>'
								+ '        </div>';
								
							}else if(c.status == 2 ||(c.status==0 && index==3)){
								var endTime = c.endTime;
								endTime=endTime.substring(0,10)
								statusLine = "class=\"yhbox hbdai hbyi hbyg\"";
								var showHtml = '	<div '+statusLine+' >'
								+ '	 		  <div class="yhnrcent">'
								+ '	 			<div class="left">￥'+c.couponMoney+''
								+ '	 			<strong>满'+c.fullMoney+'可用</strong>'
								+ '	 			</div>'
								+ '	 			<div  class="center">'
								+ '	 				<span>'+couponName+'</span>'
							    + '	 			<p>限'+c.product_name+'使用</p>'
							 	+ '              <strong>过期时间'+endTime+'</strong>'
								+ '	 			</div>' 
								+ '              <div class="right">'
								+ '	 		    </div>'
								+ '           </div>'
								+ '        </div>';
							} else {
								var endTime = c.endTime;
								endTime=endTime.substring(0,10)
								var showHtml = '	<div '+statusLine+' >'
								+ '	 		  <div class="yhnrcent">'
								+ '	 			<div class="left">￥'+c.couponMoney+''
								+ '	 			<strong>满'+c.fullMoney+'可用</strong>'
								+ '	 			</div>'
								+ '	 			<div  class="center">'
								+ '	 				<span>'+couponName+'</span>'
							    + '	 			<p>限'+c.product_name+'使用</p>'
							 	+ '              <strong>有效期至'+endTime+'</strong>'
								+ '	 			</div>' 
								+ '              <div class="right">'
								+ '              <a href='+mainServer+'/weixin/product/towxgoodsdetails?productId='+c.productId+'>立即使用</a>'
								+ '	 		    </div>'
								+ '           </div>'
								+ '        </div>';
							}
							$("#myordercent"+idx).append(showHtml);
					}
					setTimeout(function(){
						$(".initloading").hide();
					},800);
				}else if(data.code == "1011"){
					window.location.href = mainServer+"/weixin/tologin";
				} else {
					showvaguealert("出现异常");
				}
			},
			failure : function(data) {
				showvaguealert('出错了');
			}
		});
	}
	
	function showvaguealert(con) {
		$('.vaguealert').show();
		$('.vaguealert').find('p').html(con);
		setTimeout(function() {
			$('.vaguealert').hide();
		}, 3000);
	}