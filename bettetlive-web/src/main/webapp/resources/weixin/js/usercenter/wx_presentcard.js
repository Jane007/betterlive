$(function() {

	$('.backPage').click(function() {
		window.history.go(-1);
	});
	/**
	 * 四个分割添加空格
	 */
	function appendSpaceByForth(s) {
		if (s.length > 0) {
			s = s.replace(/\s/g, "", "");
			var at = [];
			for (var i = 0; i < s.length; i++) {
				at.push(s.charAt(i));
				if ((i + 1) % 4 == 0) {
					at.push(" ");
				}
			}
			return at.join("");
		}
		return "";
	}

	// 点击添加礼品卡
	$('.addpresentCard').click(
			function() {
				if (mobile == null || mobile == '') {
					$('.getbox').html("请先绑定手机号码");
					$('.yes').attr('href',
							mainServer+'/weixin/usercenter/toBoundPhone');
					$('.dia-psd').show();
				} else if (payPwd == null || payPwd == '') {
					$('.dia-psd').show();
				} else {
					$('.dia-presentCard').show();
				}
				$('.mask').show();
			});
	// 点击设置密码的取消
	$('.no').click(function() {
		$('.dia-psd').hide();
		$('.mask').hide();
	});
	// 点击取消
	$('.dia-cancel').click(function() {
		$('.dia-presentCard').hide();
		$('.mask').hide();
	});
	// 输入框
	var pwdObj = $("#pswd-input");
	var idad;
	$("#pswd-input").keyup(function(e) {
		var evt = e || event;
		var va = $(this).val();
		if (/[^A-Z]^\d/g.test(va.replace(/\s/g, "", ""))) {
			pwdObj.val(pwdObj.val().substring(0, pwdObj.val().length - 1));
		}
		idad = setInterval(function() {
			if (idad) {
				if (pwdObj.val().length >= 19) {
					pwdObj.val(pwdObj.val().substring(0, 19));
				}

				if (pwdObj.val().length < 19) {
					$('.dia-addCard').removeClass("active");
					pwdObj.val(appendSpaceByForth(pwdObj.val()));
				}

				if (pwdObj.val().replace(/\s/g, "", "").length == 16) {
					$('.dia-addCard').addClass("active");
				}
				clearInterval(idad);
			}
		}, 50);
	});
	// 子菜单点击添加礼品卡
	$('.dia-addCard').click(function() {
		if ($(this).hasClass('active')) {
			var pwdinput = $("#pswd-input").val().replace(/\ +/g, "");
			$.ajax({
				type : 'POST',
				url : mainServer + "/weixin/presentcard/addPresentCard",
				data : {
					giftCardNo : pwdinput
				},
				async : false,
				dataType : 'json',
				success : function(data) {
					if (data.result == 'success') {
						$('.dia-card-success').show();
						$('.dia-presentCard').hide();
					} else if (data.result == 'faild') {
						$('.mask').show();
						showvaguealert('礼品卡添加失败');
					} else if (data.result == 'noCardNo') {
						$('.mask').show();
						showvaguealert('没有此礼品卡');
					}
				}
			});
		}
	});
	// 点击好的
	$('.OK').click(function() {
		$('.dia-card-success').hide();
		$('.mask').hide();
		window.location.reload();
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

$(function() {
	$('.footer li').click(function() {
		$(this).addClass('active').siblings('li').removeClass('active');
	});
});