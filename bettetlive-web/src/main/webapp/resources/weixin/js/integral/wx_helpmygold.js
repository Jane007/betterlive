$(function(){
	$('.box').click(function(){
		$(this).addClass('on').siblings().removeClass('on');
		if($(this).index() == 0){
			$('.ongold').show();
			$('.onhonor').hide()
		};
		if($(this).index() == 1){
			$('.ongold').hide();
			$('.onhonor').show()
		}
	})
})

//微信自带返回
$(function(){
	var url=mainServer+"/weixin/integral/toIntegral";
	if(backUrl != null && backUrl != "" && backUrl != undefined){
		url = backUrl;
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
    	window.history.pushState(state, "title","");  
    }
    
    
  //返回上一个页面
	$('.fan').click(function(){
		window.location.href=url;	
	});
});