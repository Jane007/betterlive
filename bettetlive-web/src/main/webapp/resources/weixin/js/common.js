//采用正则表达式获取地址栏参数
function getQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
} 

function isCheckWeiXin(value){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
    	if(value != null && value != ""){
    		showvaguealert(value);
    	}
        return false;
    }
}
  
//$('.shopping').click(function(){
//	var wxCheck = isCheckWeiXin();
//	if(!wxCheck){
//		return; 
//	}
//	window.location.href= mainServer + '/weixin/shoppingcart/toshoppingcar';
//});

//$('.search-frame span i').click(function(){//查询商品
//	var productName = $('#productName').val();
//	if(productName==null || productName==''){
//		$('#productName').attr('placeholder','请输入商品名称进行搜索');
//		$(this).focus();
//	}else{
//		$("#searchName").val(productName);
//		$("#form2").submit();
//	}
//	
//});
//$('#productName').keypress(function(e){
//	if(e.keyCode == 13){
//		$(this).blur();
//		var productName = $('#productName').val();
//		if(productName==null || productName==''){
//			$('#productName').attr('placeholder','请输入商品名称进行搜索');
//			$(this).focus();
//		}else{
//			$("#searchName").val(productName);
//			$("#form2").submit();
//		}
//	}
//});

function checkMoneyByPoint(useMoney){
	var result = "0";
	if(useMoney != null && useMoney != ""){
		result = useMoney+"";
		if(result.indexOf(".") > 0 && result.substring(result.indexOf(".")+1, result.length) == "00"){
			result = result.substring(0, result.indexOf("."));
		}
	}
	return result;
}

//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},2000);
}

//点击去下载
function toDownloadApp(){
	var sourceType = navigator.userAgent;
	if(sourceType == null || sourceType == ""){
		showvaguealert("此网页不存在哦");
		return;
	}
	if (sourceType.indexOf('iPhone') > -1) {//苹果手机
			window.location.href = "https://itunes.apple.com/cn/app/jie-zou-da-shi/id1237548987?mt=8";
	} else  if (sourceType.indexOf('Android') > -1 || sourceType.indexOf('Linux') > -1) {//安卓手机
			window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.klsw.betterlive";

	}else{
		showvaguealert("请在android或ios的移动端打开");
	}
}