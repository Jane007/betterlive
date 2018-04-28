var msgCode = '';// 发送到手机上的验证码
var codeflag = false;
// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 1000);
}
var phoneflag = false;
$(function() {
	// 判断手机号码
	$("#mobile").change(function() {
		var $phone = /^((1[0-9]{2})+\d{8})$/;
		var $phoneval = $("#mobile").val();
		if ($phoneval) {
			if (!$phone.test($phoneval)) {
				phoneflag = false;
				return false;
			} else {
				phoneflag = true;
			}
		} else {
			showvaguealert('您输入的手机号不能为空!');
			return false;
		}

	});

	// 点击获取验证码
	$('.verificationcode').click(
			function() {
				var $phoneval = $("#mobile").val();
				if (!codeflag) {
					if (!codeflag && phoneflag) {
						getVerifCode();
						if (!codeflag) {
							return;
						}
						$(this).addClass('codeactive');
						$('.verificationcode').text('重新获取 (' + 60 + ')');
						var codetime = setInterval(countdown, 1000);
						var i = 60;
						function countdown() {
							i--;
							$('.verificationcode').text('重新获取 (' + i + ')');
							if (i <= 0) {
								i = 0;
								clearInterval(codetime);
								$('.verificationcode').text('获取验证码')
										.removeClass('codeactive');
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

	// 判断验证码
	var getflag = false;
	var $code = /^[0-9]{5}$/;
	$("#verifycode").change(function() {
		var $entrypass = $("#verifycode").val();
		if (!$code.test($entrypass)) {
			showvaguealert('验证码为5位数字!');
			getflag = false;
		} else {
			getflag = true;
		}
	});

	// 点击注册确定
	$('.now-bound').click(
			function() {
				var phone = $("#mobile").val();
				if (phone == null || phone == "" || phone.length <= 0) {
					showvaguealert('请输入手机号码');
					return;
				}

				if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phone))) {
					showvaguealert('请输入正确的手机号码');
					return;
				}

				var $entrypass = $("#verifycode").val();

				if ($entrypass == null || $entrypass == ""
						|| $entrypass.length <= 0) {
					showvaguealert('验证码不能为空');
					return false;
				} else if (!$code.test($entrypass)) {
					showvaguealert('验证码为5位数字!');
					return false;
				}

				if (!phoneflag) {
					return;
				}

				$.ajax({
					url : mainServer + '/weixin/usercenter/bindMobile',
					data : {
						"mobile" : phone,
						"verifycode" : $entrypass
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (data.result == 'succ') {
							$(".now-complete").removeClass("now-bound");
							$(".now-complete").css("background", "#CCC");
							showvaguealert(data.msg);
							var backUrl = window.location.href;
							if (backUrl.indexOf("backUrl") != -1) {
								backUrl = backUrl.substring(backUrl
										.indexOf("backUrl=") + 8,
										backUrl.length);
								window.location.href = backUrl;
							} else {
								toBack();
							}
							return;
						}
						showvaguealert(data.msg);
						return;
					},
					failure : function(data) {
						showvaguealert(data.msg);
						return;
					}
				});
			});
});

// 绑定手机号码获取验证码
function getVerifCode() {
	var phoneNum = $("#mobile").val();
	$.ajax({
		url : mainServer + "/weixin/presentcard/findMobile",
		type : "POST",
		dataType : 'json',
		async : false,
		data : {
			'phoneNum' : phoneNum
		},
		success : function(data) {
			var obj = data;
			if ('fail' == obj.result) {
				$.ajax({
					url : mainServer + "/weixin/presentcard/sendMessage",
					type : "POST",
					dataType : 'json',
					async : false,
					data : {
						'phoneNum' : phoneNum
					},
					success : function(data) {
						var obj = data;
						if ('fail' != data.result) {
							showvaguealert(obj.msg);
							codeflag = true;
							return;
						} else {
							showvaguealert(obj.msg);
							codeflag = false;
							return;
						}
					}
				});
			} else {
				showvaguealert(obj.msg);
				codeflag = false;
				return;
			}
		}
	});
}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

function toBack() {
	window.location.href = mainServer + "/weixin/tologin";
}