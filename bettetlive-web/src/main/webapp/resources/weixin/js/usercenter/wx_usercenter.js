function toSocialityHome() {
	var backUrl = mainServer + "/weixin/tologin";
	window.location.href = mainServer
			+ "/weixin/socialityhome/toSocialityHome?backUrl=" + backUrl;
}
// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 1000);
}