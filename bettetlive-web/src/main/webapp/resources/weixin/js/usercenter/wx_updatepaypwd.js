var newPwd = '';
var affirmPwd = '';
var oldPwd = '';
$(function() {

	$("#oldPwd").keyup(
			function(e) {
				var pwdObj = $(this);
				if (pwdObj.val().length >= 6) {
					pwdObj.val(pwdObj.val().substring(0, 6));
				}
				if (pwdObj.val().length <= 6) {
					var len = pwdObj.val().length;
					if (e.keyCode == 8) {
						pwdObj.siblings('.blockWrapper').find('.block i').eq(
								len).hide();
					} else {
						pwdObj.siblings('.blockWrapper').find('.block i').eq(
								len - 1).show();
					}
					var pwd = $(this).val();
					if (pwd.length == 6) {
						oldPwd = pwd;
					}
				}
			});

	$("#oldNext").click(
			function() {

				var payValue = $.md5(oldPwd);

				// 后台验证密码
				$.ajax({
					type : 'POST',
					url : mainServer + "/weixin/usercenter/affirmPayPwd",
					data : {
						payPwd : payValue
					},
					async : false,
					dataType : 'json',
					success : function(data) {
						if (data.msg == 'success') {
							$('.s2').addClass('active').siblings('span')
									.removeClass('active');
							$('.stepBox p span').css("width", "66.6%");
							$('.setting').show().siblings('form').hide();
						} else {
							showvaguealert("原密码输入错误");
							$("#oldPwd").val('');
							$('#verifinput .block i').hide();
							$('.verification').show().siblings('form').hide();
						}
					}
				});
			});
	$("#newPwd").keyup(
			function(e) {
				var pwdObj = $(this);
				if (pwdObj.val().length >= 6) {
					pwdObj.val(pwdObj.val().substring(0, 6));
				}
				if (pwdObj.val().length <= 6) {
					var len = pwdObj.val().length;
					if (e.keyCode == 8) {
						pwdObj.siblings('.blockWrapper').find('.block i').eq(
								len).hide();
					} else {
						pwdObj.siblings('.blockWrapper').find('.block i').eq(
								len - 1).show();
					}
					var pwd = $(this).val();
					if (pwd.length == 6) {
						newPwd = pwd;

					}
				}
			});
	// 输入新密码下一步操作
	$('#newNext').click(function() {
		var getpwd = $("#newPwd").val();
		if (getpwd.length == 0 || getpwd == null) {
			showvaguealert("请输入新的密码");
			return;
		}
		if (!(getpwd.length == 6)) {
			showvaguealert("密码长度必须为6");
			return;
		}
		$('.s3').addClass('active').siblings('span').removeClass('active');
		$('.stepBox p span').css("width", "100%");
		$('.affirm').show().siblings('form').hide();
	});

	$('#confirmPwd').keyup(
			function(e) {
				var pwdObj = $(this);
				if (pwdObj.val().length >= 6) {
					pwdObj.val(pwdObj.val().substring(0, 6));
				}
				if (pwdObj.val().length <= 6) {
					var len = pwdObj.val().length;
					if (e.keyCode == 8) {
						pwdObj.siblings('.blockWrapper').find('.block i').eq(
								len).hide();
					} else {
						pwdObj.siblings('.blockWrapper').find('.block i').eq(
								len - 1).show();
					}
					var pwd = $(this).val();
					if (pwd.length == 6) {
						affirmPwd = pwd;
					}
				}
			});

	$('.inputArea').focus(function() {
		$(this).siblings('.blockWrapper').addClass('active');
	}).blur(function() {
		$(this).siblings('.blockWrapper').removeClass('active');
	});
	// 点击确认修改
	$('#sure').click(function() {
		if (newPwd != affirmPwd) {// 两次密码验证是否一致
			showvaguealert("两次密码不一致");
			$('.s3').addClass('active').siblings('span').removeClass('active');
			$('.stepBox p span').css("width", "100%");
			$("#confirmPwd").val('');
			$('#affirm .block i').hide();
			$('.affirm').show().siblings('form').hide();
		} else {
			var payValue = $.md5(newPwd);
			// 后台验证密码
			$.ajax({
				type : 'POST',
				url : mainServer + "/weixin/usercenter/updatePayPwd",
				data : {
					pay_pwd : payValue
				},
				async : false,
				dataType : 'json',
				success : function(data) {
					if (data.msg == 'success') {
						$('.set-success').show();
						$('.mask').show();
						toBack();

					} else {
						showvaguealert("密码设置失败");
					}
				}
			});

		}

	});
	// 点击弹框好的
	$('.backlogin').click(function() {
		$('.set-success').hide();
		$('.mask').hide();
		location.href = mainServer + "/weixin/usercenter/toAccount";
	});
	// 点击获取验证码
	$('.phoneBox span').click(function() {
		showvaguealert('请输入手机号码！');
	});
	// 提示弹框
	function showvaguealert(con) {
		$('.vaguealert').show();
		$('.vaguealert').find('p').html(con);
		setTimeout(function() {
			$('.vaguealert').hide();
		}, 2000);
	}
});

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