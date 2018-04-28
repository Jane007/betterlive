function toFeedBacUs() {
	var content = $("#content").val();
	if(content.length=0 || content==null || content==""){
		showvaguealert("请输入反馈内容");
		return;
	}
	var contact = $("#contact").val();
	var re = /^((1[0-9]{2})+\d{8})$/;
    if (re.test(contact)) {
    	
    }else{
    	showvaguealert("手机号码格式错误");
    	return
    }
	$.ajax({
		url : mainServer+'/weixin/feedback/addFeedBack',
		data : {
			"content" : content,
			"contact" : contact
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 查询成功
				showvaguealert("谢谢你的反馈!");
				setTimeout(function() {
					toBack();
				}, 800);
				return;
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
	
function toBack() {
	window.location.href = mainServer+"/weixin/usercenter/toUserSetting";
}
	
$(function(){
 	var url= mainServer+"/weixin/usercenter/toUserSetting";
	
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
    },1000); 
        
	pushHistory(); 
	
    window.addEventListener("popstate", function(e) {
    	if(bool){
    		window.location.href = url;	
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