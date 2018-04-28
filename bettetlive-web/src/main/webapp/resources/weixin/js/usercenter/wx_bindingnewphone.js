var msgCode = '';// 发送到手机上的验证码
var codeflag = false;
$(function() {
	if (sMobile != null && sMobile != "") {
		$("#phoneForm").show();
	} else {
		$("#newphone").show();
	}
	// 点击弹框好的
	$('.backlogin').click(function() {
		$('.set-success').hide();
		$('.mask').hide();
	});

	$('#saveNewPhone')
			.click(
					function() {
						var mobile = $('#newphoneNum').val();
						if (mobile == null || mobile == "") {
							showvaguealert('请输入手机号码');
							return false;
						} else if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(mobile))) {
							flag = false;
							showvaguealert('手机号码格式错误');
							return false;
						} else if (sMobile == mobile) {
							showvaguealert('新号码与旧号码一致');
							return false;
						} else {
							var code = $('#msgNewCode').val();
							if (code.length == 0) {
								showvaguealert('请输入验证码');
								return false;
							}
							$
									.ajax({
										type : 'POST',
										url : mainServer
												+ "/weixin/presentcard/checkNewSmsCode",
										data : {
											"phoneNum" : mobile,
											"checkCode" : code
										},
										async : false,
										dataType : 'json',
										success : function(data) {
											if (data.result == 'success') {
												$
														.ajax({
															type : 'POST',
															url : mainServer
																	+ "/weixin/usercenter/updateNewPhone",
															data : {
																'mobile' : mobile
															},
															async : false,
															dataType : 'json',
															success : function(
																	data) {
																if (data.result == 'succ') {
																	showvaguealert(data.msg);
																	toBack();
																} else if (data.result == "failure") {
																	window.location.href = mainServer
																			+ "/weixin/tologin";
																} else {
																	showvaguealert(data.msg);
																}
															}
														})

											} else if (data.result == "failure") {
												window.location.href = mainServer
														+ "/weixin/tologin";
											} else {
												showvaguealert(data.msg);
											}
										}
									});

						}
					})

	// ****************** 验证旧手机号码 ****************************
	var count = 59;
	function countDown() {
		$("#getCode").html(count + "s");
		if (count <= 0) {
			$("#getCode").css("background", "#e62d29");
			$("#getCode").bind("click", function() {
				getMessage();
			});
			$("#getCode").html("再次发送");
			count = 59;
		} else {
			setTimeout(countDown, 1000);
			count--;
		}
	}
	// 点击获取验证码
	$('#getCode').click(function() {
		var phoneNum = sMobile;// $('#phoneNum').val();
		if (phoneNum == null || phoneNum == "") {
			showvaguealert('非法请求');
			return false;
		}
		var $this = $(this);
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
		}

	});

	// ****************** 验证新手机号码 ****************************
	var newcount = 59;
	function newCountDown() {
		$("#getNewCode").html(newcount + "s");
		if (newcount <= 0) {
			$("#getNewCode").css("background", "#e62d29");
			$("#getNewCode").bind("click", function() {
				getNewMessage();
			});
			$("#getNewCode").html("再次发送");
			newcount = 59;
		} else {
			setTimeout(newCountDown, 1000);
			newcount--;
		}
	}
	// 点击获取验证码
	$('#getNewCode').click(function() {
		var mobile = $('#newphoneNum').val();
		var $this = $(this);
		if (mobile == null || mobile == '') {
			showvaguealert('请输入手机号码！');
		} else {
			getNewMessage();
		}

	});

	function toBack() {
		window.location.href = mainServer + "/weixin/tologin";
	}

	// 绑定手机号码获取验证码
	/*
	 * function getMessage(){ var phoneNum = $('#phoneNum').val(); $.ajax({
	 * type: 'POST', url: mainServer + "/weixin/presentcard/sendMessage",
	 * data:{'phoneNum':phoneNum}, async: false, dataType:'json',
	 * success:function(data) { if('fail' == data.result){
	 * showvaguealert("手机验证码发送失败！") }else{ showvaguealert("手机验证码已经发送！");
	 * $('#getCode').css("background","#E3DBDA"); $('#getCode').unbind("click");
	 * setTimeout(countDown, 1000); }
	 *  } }) }
	 */

	// 绑定手机号码获取验证码
	function getMessage() {
		var phoneNum = sMobile;
		$.ajax({
			url : mainServer + "/weixin/usercenter/sendEmail",
			type : "POST",
			dataType : 'json',
			async : false,
			data : {
				'eMail' : phoneNum,
				'selectType' : 'phone'
			},
			success : function(data) {
				if ('fail' == data.result) {
					showvaguealert(data.msg)
					codeflag = true;
				} else {
					showvaguealert(data.msg);
					codeflag = false;
					$('#getCode').css("background", "#E3DBDA");
					$('#getCode').unbind("click");
					setTimeout(countDown, 1000);
				}
			}
		});
	}

	// 绑定新手机号码获取验证码
	function getNewMessage() {
		var phoneNum = $('#newphoneNum').val();
		if (phoneNum == null || phoneNum == "") {
			showvaguealert('请输入手机号码');
			return false;
		} else if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(phoneNum))) {
			flag = false;
			showvaguealert('手机号码格式错误');
			return false;
		} else if (sMobile == phoneNum) {
			showvaguealert('新号码与旧号码一致');
			return false;
		}
		$.ajax({
			url : mainServer + "/weixin/usercenter/sendEmail",
			type : "POST",
			dataType : 'json',
			async : false,
			data : {
				'eMail' : phoneNum,
				'selectType' : 'phone'
			},
			success : function(data) {
				if ('fail' == data.result) {
					showvaguealert(data.msg)
				} else {
					showvaguealert("手机验证码已经发送！");
					$('#getNewCode').css("background", "#E3DBDA");
					$('#getNewCode').unbind("click");
					setTimeout(newCountDown, 1000);

				}
			}
		});
	}

	$('#msgNext')
			.click(
					function() {
						var phoneNum = sMobile;// $('#phoneNum').val();
						if (phoneNum == null || phoneNum == ""
								|| phoneNum.length <= 0) {
							showvaguealert('非法请求');
							return false;
						}
						var code = $('#msgCode').val();
						if (code == null || code == "" || code.length == 0) {
							showvaguealert('请输入验证码');
							return false;
						}
						$.ajax({
							type : 'POST',
							url : mainServer
									+ "/weixin/presentcard/checkSmsCode",
							data : {
								"phoneNum" : phoneNum,
								"checkCode" : code
							},
							async : false,
							dataType : 'json',
							success : function(data) {
								if (data.result == 'success') {
									$('.s2').addClass('active')
											.siblings('span').removeClass(
													'active');
									$('.stepBox p span').css("width", "100%");
									$('#newphone').show().siblings('form')
											.hide();
								} else if (data.result == "failure") {
									window.location.href = mainServer
											+ "/weixin/tologin";
								} else {
									showvaguealert(data.msg);
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
		}, 1000);
	}
});

$(function() {

	var url = mainServer + "/weixin/usercenter/toAccountUs";

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