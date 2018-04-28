$(function() {

	// 点击设置密码的取消
	$('.dia-not-set .no').click(function() {
		$('.dia-not-set').hide();
		$('.mask').hide();
	});

	/*
	 * //点击退出 $('.exitLogin').click(function(){ $('.dia-exit').show();
	 * $('.mask').show(); });
	 */
	// 点击退出取消
	$('.dia-exit .no').click(function() {
		$('.dia-exit').hide();
		$('.mask').hide();
	});
	// 点击退出确定
	$('.dia-exit .yes').click(function() {
		$('.dia-exit').hide();
		$('.mask').hide();
	});

});
$(function() {
	var url = mainServer+"/weixin/presentcard/toPresentcard";

	var bool = false;
	setTimeout(function() {
		bool = true;
	}, 1000);

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			window.location.href = url;
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