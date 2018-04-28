$(function() {
	$(".initloading").show();
	setTimeout(function() {
		$(".initloading").hide();
	}, 800);

	// 记录动态物流消息线条的高度
	linedown();
	function linedown() {
		var width = $('.dotBox').width();
		var height = $('.dotBox').height();
		$('.dotBox').css({
			width : width + 'px',
			height : height + 'px'
		})
		var dotBox = document.getElementsByClassName('dotBox');
		var len = dotBox.length;
		var h = getTop(dotBox[len - 1]) - getTop(dotBox[0]);
		$('.lines').outerHeight(h);

		/* 计算节点到文档顶部的距离 */
		function getTop(obj) {
			var h = 0;
			while (obj) {
				h += obj.offsetTop;
				obj = obj.offsetParent;
			}
			return h;
		}
	}
	// 无数据出现的图标
	showNodata();
	function showNodata() {
		var oList = $('.logstepsBox .logitems').length;
		if (oList <= 0) {
			createNodata();
		}
		// 无数据的图标提示
		function createNodata() {
			var obj = '<div class="inboxing">' + '<div class="inbox-logo">'
					+ '<img src="img/kuaidiche.png" alt="" />' + '</div>'
					+ '<p>揽件员正在准备揽件，请稍等...</p>' + '</div>';
			$('.logstepsBox').append(obj);
		}
	}
});

$(function() {

	var url = mainServer + "/weixin/order/findList?status="+type;
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