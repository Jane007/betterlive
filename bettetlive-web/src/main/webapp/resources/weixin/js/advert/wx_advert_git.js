var url = window.location.href;
var ua = navigator.userAgent;
$(function() {
	//获取缓存判断是否存在顶部下载栏显示状态
	var nowTimer = Date.parse(new Date());
	var dayTime = 60 * 60 * 24 * 1000;
	var dowflag = JSON.parse(localStorage.getItem("dowboxhide")) ;
	//读取缓存数据判断是否存在或者已经超过一天
	if(!dowflag || nowTimer - dowflag.startTime >= dayTime){
		localStorage.removeItem('dowboxhide');
		$('.dowbox-wrap').show();
	}else{
		$('.dowbox-wrap').hide();
		$('.title-tp').css('margin-top','0.46rem')
	};

	if (ua.indexOf('HuiHuoApp') > 0 || ua.indexOf('HuiHuoIOSApp') > 0) {
		$(".dowbox").hide();
	}
	
	onload();

});

//取消弹窗
$(".shut.dow").click(function(){
	var timer = Date.parse(new Date());
	var obj = {
			startTime:timer,
			dowboxhide:"ok"
		};
	$('.dowbox-wrap').fadeOut(500,function(){
		localStorage.setItem('dowboxhide',JSON.stringify(obj));
		$('.title-tp').css({
			'transition': 'all 0.3s',
			'margin-top':'0.46rem'
		})
	})
})


function getCoupon(event) {
	if (ua.indexOf('HuiHuoIOSApp') > 0) {
		event.preventDefault();
		event.stopPropagation();
		try {// getCoupon是调用ios的方法
			window.webkit.messageHandlers.getCoupon.postMessage("");
		} catch (err) {
			document.writeln("捕捉到异常，开始执行HuiHuoIOSApp块语句 --->");
		}
		return;
	}

	if (ua.indexOf('HuiHuoApp') < 0) {
		event.preventDefault();
		event.stopPropagation();
		$.ajax({
			url : mainServer + '/weixin/advert/receiveMultiCoupon',
			type : 'post',
			success : function(data) {
				var result = data;
				if (result.code == 1011) {// 没有登陆
					$('.shad').show();
					$('.shepassdboxs').show();
					$('#cancelId').attr('href',
							mainserver + "/weixin/tologin?backUrl=" + url);
				} else if (result.code == 1405) {// 已经领过了
					$('.shad').show();
					$('#hasPacket').show();
					setTimeout(function() {
						$('#hasPacket').hide();
						$('.shad').hide();
					}, 1000);
				} else if (result.code == 1010) {// 领取成功
					$('.shad').show();
					$('#successed').show();
					setTimeout(function() {
						$('#successed').hide();
						$('.shad').hide();
					}, 1000);
				} else {
					showvaguealert(result.msg);
				}
			}
		});

	}
}

$(".decode a").click(getCoupon)

function closeAlert() {
	$('.shad').hide();
	$(".shepassdboxs").hide();
}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

onload = function() {
	// 修改页面背景图片尺寸
	$('body').height($(document).height());
	$('body').children('img').height($('body').height());
};
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
