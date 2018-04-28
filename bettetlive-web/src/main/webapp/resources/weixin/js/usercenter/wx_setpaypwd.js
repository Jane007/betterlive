var msgCode = '';// 发送到手机上的验证码
var firstPwd = '';
var sencodPwd = '';
$(function() {

	$('.backPage').click(function() {
		window.history.go(-1);
	});

	$('.shopCar').click(
			function() {
				window.location.href = mainServer
						+ '/weixin/shoppingcart/toshoppingcar';
			});

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
										+ "/weixin/presentcard/findList";
							} else {
								showvaguealert("支付密码设置失败");
							}
						}
					});
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
				var $this = $(this);
				var sMobile = $('#phoneNum').val();
				if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(sMobile))
						|| sMobile.length != 11) {
					showvaguealert('请输入正确手机号码！');
				} else {// 手机发短信验证码
					getMessage();
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
			url : mainServer + "/weixin/presentcard/sendMessage",
			data : {
				'phoneNum' : phoneNum
			},
			async : false,
			dataType : 'json',
			success : function(data) {
				if ('fail' == data.result) {
					showvaguealert(data.msg)
				} else {
					showvaguealert(data.msg);
					$('#getCode').css("background", "#E3DBDA");
					$('#getCode').unbind("click");
					setTimeout(countDown, 1000);
				}

			}
		})
	}
	$('#msgNext').click(
			function() {
				var sMobile = $('#phoneNum').val();
				var code = $('#msgCode').val();
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
							$('.s2').addClass('active').siblings('span')
									.removeClass('active');
							$('.stepBox p span').css("width", "66.6%");
							$('.setting').show().siblings('form').hide();
						} else {
							showvaguealert(data.msg);
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
