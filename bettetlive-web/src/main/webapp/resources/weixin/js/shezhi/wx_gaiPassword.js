var newPwd = '';
var affirmPwd = '';
var oldPwd = '';
// 点击确认修改
$('#sure')
		.click(
				function() {
					var oldPwd = $("#oldPwd").val();
					if (oldPwd == null || oldPwd == "") {
						showvaguealert("还没有输入旧密码");
						return;
					}
					var newPwd = $("#newPwd").val();
					if (newPwd == null || newPwd == "") {
						showvaguealert("还没有输入新密码");
						return;
					}

					var affirmPwd = $("#affirmPwd").val();
					if (affirmPwd == null || affirmPwd == "") {
						showvaguealert("请再次输入新密码");
						return;
					}

					if (!(affirmPwd.length >= 6 && affirmPwd.length <= 18)) {
						showvaguealert("密码长度只能在6到18之间");
						return;
					}
					if (newPwd != affirmPwd) {
						showvaguealert("两次密码不一致");
						return;
					}

					if (oldPwd.length == 0) {
						showvaguealert("请输入原始密码");
						return;
					}
					if (oldPwd.length >= 6 && oldPwd.length <= 18) {
						var payValue = $.md5(oldPwd);
						// 后台验证密码
						$
								.ajax({
									type : 'POST',
									url : mainServer
											+ "/weixin/usercenter/affirmPassword",
									data : {
										password : payValue
									},
									async : false,
									dataType : 'json',
									success : function(data) {
										if (data.msg == 'success') {
											var payValue = $.md5(newPwd);
											// 后台验证密码
											$.ajax({
														type : 'POST',
														url : mainServer
																+ "/weixin/usercenter/doUpdatePassword",
														data : {
															password : payValue
														},
														async : false,
														dataType : 'json',
														success : function(data) {
															if (data.msg == 'success') {
																showvaguealert('修改密码成功!');
																setTimeout(
																		function() {
																			toBack();
																		}, 1000);
															} else {
																showvaguealert(data.desc);
															}
														}
													});
										} else {
											showvaguealert("你输入的原始密码不正确");
											return;
										}
									}
								});
					} else {
						showvaguealert("密码长度只能6到18之间");
					}

				});

function newPwdonblurs() {
	var newPwd = $("#newPwd").val();

	if (newPwd == null || newPwd == "") {
		showvaguealert("请输入新密码");
	} else if (newPwd.length >= 6 && newPwd.length <= 18) {
		$("#affirmPwd").removeAttr("disabled");
	} else {
		showvaguealert("密码长度请在6到18位之间");
	}
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
	window.location.href = mainServer + "/weixin/usercenter/toAccountUs";
}
