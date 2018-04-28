//字符限制提示
$(function () {  
        //匹配包含给定属性的元素，keyup在按键释放时发生   
        $("#textval").keyup(function () {  
            var area = $(this);  
            //parseInt 方法返回与保存在 numString 中的数字值相等的整数。如果 numString 的前缀不能解释为整数，则返回 NaN（而不是数字）。  
            var max = parseInt(area.attr("maxlength"), 10); //获取maxlength的值 转化为10进制，将输入到textarea的文本长度  
            //这个判断可知max得到的是不是数字，设定的大小是多少  
            if (max > 0) {  

                if (area.val().length > max) { //textarea的文本长度大于maxlength   
                    area.val(area.val().substr(0, max)); //截断textarea的文本重新赋值   
                }  

                var yishu = area.val().length;  
                var sheng = max - area.val().length;  
                $("#lyishu").html(yishu);  
                $("#lsheng").html(sheng);  
            }  
        });  
        $("#textval").blur(function () {  
            var area = $(this);  
            var max = parseInt(area.attr("maxlength"), 10); //获取maxlength的值   
            if (max > 0) {  

                if (area.val().length > max) { //textarea的文本长度大于maxlength   
                    area.val(area.val().substr(0, max)); //截断textarea的文本重新赋值   
                }  

                var yishu = area.val().length;  
                var sheng = max - area.val().length;  
                $("#lyishu").html(yishu);  
                $("#lsheng").html(sheng);  
            }  
        });  


    });   
    
	//成功完成后跳转评论详情
    function go1(){
			window.location.href=mainServer+"/weixin/productcomment/toProductCommentDetail?type="+type+"&commentId="+commentId;
    }
    
  //提交评价
	$('.submitBox').click(function(){
		if(!$(this).hasClass('active')){
			var ovalue=$('.textcon textarea').val();
			var len=$('.updataBox li').length;
			
			var maxChars  = 100;
			if ($("#textval").val().length > maxChars){
				showvaguealert('亲，评论过长</br>请输入100字以内！');
				return false;
			} 
			
    		if (ovalue != null && ovalue != '') {
    			
    			 $.ajax({    
    		          url: mainServer+'/weixin/productcomment/replyComment' , 
    		          type: 'POST',    
    		          data: {
    		        		"commentId":$("#commentId").val(),
    						"rootId":$("#rootId").val(),
    						"content":ovalue
    		          },    
    		          dataType:'json',
    		          success:function(data) { 
        			        if(data.code == 1010){  
        						
        						var picCnt = $('.updataBox li').length;
        						
        						$('.textcon textarea').attr('readonly','readonly');
        						if (picCnt > 0) {
        							var num1 = uploadSuccess();
        							setTimeout(function(){
        								$('.submit-success').hide();
        							},picCnt*2000); 
        						}
        						showvaguealert("评价提交成功");
        						$('.submitBox').addClass('active').html('您的评价已提交');
        						setTimeout(function(){
        							go1();
        						},2000); 
        						
        			        }else{  
        			        	showvaguealert(data.msg);
        			        }  
        			     },     
        			     error : function() {  
        			          showvaguealert('异常！');
        			     }   
    		     });     
			}else{
				showvaguealert('亲，请输入您的评价！');
			}
		} 
			
});
	
	
//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
			$('.vaguealert').hide();
		},1000);
}
	
//修改微信自带的返回键
$(function(){
		var url = mainServer+"/weixin/productcomment/toProductCommentDetail?type="+type+"&commentId="+commentId;
		var bool=false;  
        setTimeout(function(){  
              bool=true;  
        },1000); 
            
		pushHistory(); 
		
	    window.addEventListener("popstate", function(e) {
	    	if(bool){
	    		window.location.href= url;
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
