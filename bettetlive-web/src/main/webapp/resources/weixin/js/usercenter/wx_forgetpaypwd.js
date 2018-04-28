var count = 59;
var msgCode = '';// 发送到手机上的验证码
var firstPwd = '';
var sencodPwd = '';
var codeflag = false;
$(function() {

	// 输入密码
	var pwdObj = $("#pwd");
	$("#pwd").keyup(function(e) {
		if (pwdObj.val().length > 6) {
			pwdObj.blur();
			pwdObj.val()
		}
		if (pwdObj.val().length <= 6) {
			var len = pwdObj.val().length;
			if (e.keyCode == 8) {
				$('.block i').eq(len).hide();
			} else {
				$('.block i').eq(len - 1).show();
			}
			var pwd = $(this).val();
			if (pwd.length == 6) {
				firstPwd = pwd;
			}
		}

	});

	$('#newNext').click(function() {
		var pwdss = $("#pwd").val();
		if (pwdss.length == 0) {
			showvaguealert("请输入新密码");
			return;
		}
		if (pwdss.length != 6) {
			showvaguealert("你输入的密码长度不为6");
			return;
		}

		$('.s3').addClass('active').siblings('span').removeClass('active');
		$('.stepBox p span').css("width", "100%");
		$('.affirm').show().siblings('form').hide();
	});

	// 输入密码

	$("#pwdT").keyup(function(e) {
		pwdObj = $("#pwdT");
		if (pwdObj.val().length > 6) {
			pwdObj.blur();
			pwdObj.val()
		}
		if (pwdObj.val().length <= 6) {
			var len = pwdObj.val().length;
			if (e.keyCode == 8) {
				$('#affirm .block i').eq(len).hide();
			} else {
				$('#affirm .block i').eq(len - 1).show();
			}
		}
		var pwd = $(this).val();
		if (pwd.length == 6) {
			sencodPwd = pwd;
		}
	});

	$('#sure').click(
			function() {
				var payValue = $.md5(firstPwd);
				if (firstPwd == sencodPwd) {// 两次一致,后台保存支付密码
					$.ajax({
						type : 'POST',
						url : mainServer
								+ "/weixin/presentcard/savePayPassword",
						data : {
							pay_pwd : payValue
						},
						async : false,
						dataType : 'json',
						success : function(data) {
							if (data.msg == 'success') {
								showvaguealert("支付密码设置成功");
								location.href = mainServer
										+ "/weixin/presentcard/toPresentcard";
							} else {
								showvaguealert("支付密码设置失败");
							}
						}
					})
				} else {
					showvaguealert("两次密码输入不一致");
					$('.s3').addClass('active').siblings('span').removeClass(
							'active');
					$('.stepBox p span').css("width", "100%");
					$("#pwdT").val('');
					$('#affirm .block i').hide();
					$('.affirm').show().siblings('form').hide();
				}
			});
	$('.inputArea').focus(function() {
		$('.blockWrapper').addClass('active');
	}).blur(function() {
		$('.blockWrapper').removeClass('active');
	});

	// 点击弹框好的
	$('.backlogin').click(function() {
		$('.set-success').hide();
		$('.mask').hide();
	});
	// 点击获取验证码
	$('#getCode').click(
			function() {
				var sMobile = $('#phoneNum').val();
				if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(sMobile))
						|| sMobile.length != 11) {
					showvaguealert('请输入正确手机号码！');
				}
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
								$('#getCode').text('获取验证码').removeClass(
										'codeactive');
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

	$('#msgNext').click(
			function() {
				var code = $('#msgCode').val();
				var phoneNum = $('#phoneNum').val();

				$.ajax({
					type : 'POST',
					url : mainServer + "/weixin/presentcard/checkSmsCode",
					data : {
						"phoneNum" : phoneNum,
						"checkCode" : code
					},
					async : false,
					dataType : 'json',
					success : function(data) {
						if (data.result == 'success') {
							$('.s2').addClass('active').siblings('span')
									.removeClass('active');
							$('.stepBox p span').css("width", "66.6%");
							$('.setting').show().siblings('form').hide();
						} else {
							showvaguealert("请输入正确的手机验证码");
						}
					}
				});

			});
});
// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 3000);
}

function countDown() {
	$("#getCode").html(count + "s");
	if (count == 0) {
		$("#getCode").css("background", "#aa2c23");
		$("#getCode").attr("onclick", "javascript:getMessage();");
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
		/* url: mainServer + "/weixin/presentcard/sendMessage", */
		data : {
			'phoneNum' : phoneNum
		},
		async : false,
		dataType : 'json',
		success : function(data) {
			if ('fail' == data.result) {
				showvaguealert(data.msg);
				codeflag = false;
			} else {
				showvaguealert("手机验证码已经发送！");
				$('#getCode').css("background", "#E3DBDA");
				$('#getCode').unbind("click");
				setTimeout(countDown, 1000);
				codeflag = true;

			}

		}
	})

}

$(function() {

	var url = mainServer + "/weixin/usercenter/toAccount";

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