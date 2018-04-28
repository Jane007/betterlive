var codeflag = false;
$('#sure').click(function() {
	var newPwd = $("#newPwd").val();
	if (newPwd.length == 0) {
		showvaguealert("请输入新密码");
		return;
	}
	if (!(newPwd.length >= 6 && newPwd.length <= 18)) {
		showvaguealert("密码长度只能6到18之间");
		return;
	}
	var affirmPwd = $("#affirmPwd").val();
	if (newPwd != affirmPwd) {// 两次密码验证是否一致
		showvaguealert("两次密码不一致");
	} else {
		var payValue = $.md5(newPwd);
		// 后台验证密码
		$.ajax({
			type : 'POST',
			url : mainServer + "/weixin/usercenter/doUpdatePassword",
			data : {
				password : payValue
			},
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data.msg == 'success') {
					showvaguealert("设置密码成功");
					setTimeout(function() {
						toBack();
					}, 1000);
				} else if (data.msg == 'fils') {
					showvaguealert('你设置的密码与你之前的密码一样');
					return;
				} else {
					showvaguealert('设置密码失败');
					return;
				}
			}

		});
	}

});

// 点击获取验证码
$('#getCode').click(function() {
	var $this = $(this);
	var sMobile = $('#phoneNum').val();
	if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(sMobile)) || sMobile.length != 11) {
		showvaguealert('请输入正确手机号码！');
	}
	var $phoneval = $("#phoneNum").val();
	if (!codeflag) {
		if (!codeflag) {
			getMessage();
			if (!codeflag) {
				return;
			}
			$(this).addClass('codeactive');
			$('#getCode').text('重新获取 (' + 60 + ')');
			var codetime = setInterval(countdown, 1000);
			var i = 60;
			function countdown() {
				i--;
				$('#getCode').text('重新获取 (' + i + ')');
				if (i <= 0) {
					i = 0;
					clearInterval(codetime);
					$('#getCode').text('获取验证码').removeClass('codeactive');
					codeflag = false;
				}
			}
			codeflag = true;

		} else {
			if ($phoneval) {
				showvaguealert('您输入的号码有误！');
			} else {
				showvaguealert('您输入的号码不能为空！');
			}
		}
	}
});

var count = 59;
function countDown() {
	$("#getCode").html(count + "s");
	if (count == 0) {
		$("#getCode").css("background", "#aa2c23");
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
		url : mainServer + "/weixin/presentcard/sendMessageEdit",
		data : {
			'phoneNum' : phoneNum
		},
		async : false,
		dataType : 'json',
		success : function(data) {
			if ('fail' == data.result) {
				showvaguealert(data.msg)
				codeflag = true;
			} else {
				showvaguealert(data.msg);
				$('#getCode').css("background", "#E3DBDA");
				$('#getCode').unbind("click");
				codeflag = false;
				setTimeout(countDown, 1000);
			}

		}
	})
}

$('#msgNext').click(function() {
	var sMobile = $('#phoneNum').val();
	var code = $('#msgCode').val();
	if (code == null) {
		showvaguealert('请输入验证码');
		return;
	}
	$.ajax({
		type : 'POST',
		url : mainServer + "/weixin/presentcard/checkSmsCode",
		data : {
			"phoneNum" : sMobile,
			"checkCode" : code
		},
		async : false,
		dataType : 'json',
		success : function(data) {
			if (data.result == 'success') {
				$('#s2').css('display', 'block');
				$('#s1').css('display', 'none');
			} else {
				showvaguealert(data.msg);
				return;
			}
		}
	});
});

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

function toBack() {
	window.location.href = mainServer + "/weixin/usercenter/toAccountUs";
}