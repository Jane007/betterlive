$(function() {

	$(".initloading").show();
	setTimeout(function() {
		$(".initloading").hide();
	}, 800);
});
//修改微信自带的返回键
$(function() {
	var bool = false;
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			window.location.href = mainServer + "/weixin/index";
		}
	}, false);

	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}

});