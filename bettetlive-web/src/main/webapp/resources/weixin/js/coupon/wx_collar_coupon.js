$(function() {
	$('.guizbt').click(function() {
		$('.useRuleBox').show();
		$('.mask').show();
	});

	$('.outBoxTitle').click(function() {
		$('.useRuleBox').hide();
		$('.mask').hide();
	});

	$('.phoneCode').click(
			function() {
				var $this = $(this);
				var sMobile = $('#phoneNum').val();
				if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(sMobile))
						|| sMobile.length != 11) {
					showvaguealert('请输入正确手机号码！');
				} else {// 手机发短信验证码
					time_lun();
					getMessage();
				}
			});

});

var count = 59;
function countDown() {
	$("#getCode").html(count + "s");
	if (count == 0) {
		$("#getCode").attr("onclick", "getMessage()");
		$("#getCode").html("再次发送");
		count = 59;
	} else {
		setTimeout(countDown, 1000);
		count--;
	}
}
function getMessage() {
	var phoneNum = $('#phoneNum').val();
	$.ajax({
		type : 'POST',
		url : mainServer + "/weixin/presentcard/sendMessage",
		data : {
			'phoneNum' : phoneNum
		},
		async : false,
		dataType : 'json',
		success : function(data) {
			if ('fail' == data.result) {
				showvaguealert(data.msg);
			} else {
				showvaguealert("手机验证码已经发送！");
				$('#getCode').css("background", "#E3DBDA");
				$('#getCode').unbind("click");
				setTimeout(countDown, 1000);
			}

		}
	})
}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

var codeflag = false;
function time_lun() {
	if (!codeflag) {
		var codetime = setInterval(countdown, 1000);
		var i = 60;
		countdown();
		function countdown() {
			i--;
			$('.phoneCode').text(i + 's').addClass('active');
			if (i <= 0) {
				i = 0;
				clearInterval(codetime);
				$('.phoneCode').text('获取验证码').removeClass('active');
				codeflag = false;
			}
		}
		codeflag = true;
	}
}

function register() {
	
	var cmId = $("#cmId").val();
	if (cmId == null || cmId == "" || isNaN(cmId) || parseInt(cmId) <= 0) {
		showvaguealert('活动出现异常，请联系客服');
		return false;
	}
	
	if(cmId != 55 && cmId != 75){
		alert("活动已结束");
		return false;
	}

	var checkMobile = $("#checkMobile").val();
	if (checkMobile == null || checkMobile == "" || checkMobile.length == 0) {
		var mobilePhone = $("#phoneNum").val();
		if (mobilePhone == null || mobilePhone == "") {
			showvaguealert('请输入手机号码');
			return false;
		}

		if (!(/^((1[0-9]{2})+\d{8})$/.test(mobilePhone))) {
			showvaguealert('手机号码格式错误');
			return false;
		}

		var code = $('#msgCode').val();
		if (code == null || code == '') {
			showvaguealert("请输入手机验证码");
			return false;
		}
	}

	$.ajax({
		url : mainServer + '/weixin/customercoupon/collarCoupon',
		data : {
			"mobile" : mobilePhone,
			"cmId" : cmId,
			"checkCode" : code
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1021") {
				showvaguealert(data.msg);
			} else if (data.code == "1010") {
				showvaguealert("领取成功，即将进入挥货商城~");
				setTimeout(function() {
					window.location.href = mainServer + "/weixin/index";
				}, 900);
			} else {
				window.location.href = mainServer
						+ "/common/toFuwubc?ttitle=红包信息提示&tipsContent="
						+ data.msg;
			}
		},
		failure : function(data) {
			showvaguealert('出错了');
		}
	});
}

// 修改微信自带的返回键
$(function() {
	var bool = false;
	setTimeout(function() {
		bool = true;
	}, 1000);

	var backUrl = mainServer + "/weixin/index";

	pushHistory();

	window.addEventListener("popstate", function(e) {
		if (bool) {
			window.location.href = backUrl;
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
