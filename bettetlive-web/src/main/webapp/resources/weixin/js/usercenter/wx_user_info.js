//初始化时间控件
var calendar = new LCalendar();
calendar.init({
	'trigger' : '#birthday', // 标签id
	'type' : 'date', // date 调出日期选择 datetime 调出日期时间选择 time 调出时间选择 ym 调出年月选择,
	'minDate' : '1900-1-1', // 最小日期
	'maxDate' : new Date().getFullYear() + '-' + (new Date().getMonth() + 1)
			+ '-' + new Date().getDate(), // 最大日期
	'dclick' : function(e, a, b, c, d) {
	}
});
$(function() {
	$('.shopCar').click(
			function() {
				window.location.href = mainServer
						+ '/weixin/shoppingcart/toshoppingcar';
			});

	// 选择性别 时就直接更新
	$('.sexset label span').click(function() {
		$(this).addClass('on').siblings('span').removeClass('on');
		var usersex = $(this).find("input[name='sex']").val();
		$("input:radio[value='" + userSex + "']").attr("checked", "checked");

	});

	if (null != userSex && "" != userSex) {
		$("input:radio[value='" + userSex + "']").parent("#man").addClass('on');
		$("input:radio[value='" + userSex + "']").attr("checked", "checked");
	}

	// 修改用户生日
	$("#birthday").click(function() {
	});

	// 后退
	$('.backPage').click(function() {
		window.history.go(-1);
	});

	$('body').on(
			"change",
			"#file1",
			function() {
				var file = document.getElementById("file1");
				var oimg = document.getElementById("imgHead");
				var oimgSrc = oimg.src;
				var prefix = $('#file1').val().substring(
						$('#file1').val().lastIndexOf("."));
				switch (prefix.toLowerCase()) {
				case ".jpg":
					oimg.src = window.URL.createObjectURL(file.files[0]);
					break;
				case ".jpeg":
					oimg.src = window.URL.createObjectURL(file.files[0]);
					break;
				case ".bmp":
					oimg.src = window.URL.createObjectURL(file.files[0]);
					break;
				case ".png":
					oimg.src = window.URL.createObjectURL(file.files[0]);
					break;
				default:
					showvaguealert("请上传正确的图片类型");
					$('#file1').val("");
					break;
				}

				if ($('#file1').val() != "") {
					var file = document.getElementById("file1");
					var maxSize = 1024 * 1024;
					var fileSize = file.files[0].size;
					if (fileSize > maxSize) {
						showvaguealert("请上传小于1M大小的图片");
						$('#file1').val("");
						oimg.src = oimgSrc;
					}
				}
			});
});

$('.saveData').on('click', function() {
	var sex = $('input[name="sex"]:checked').val();
	var birthday = $('#birthday').val();
	var nickname = $('#nickname').val();
	if (null == nickname || '' == nickname) {
		showvaguealert('请输入昵称');
		return;
	}
	if (birthday == null || birthday == "" || birthday == "请选择生日") {
		showvaguealert('请选择生日日期');
		return;
	}

	personalInfo(sex, birthday, headUrl, nickname)
});

// 更新个人信息
function personalInfo(sex, birthday, headUrl, nickname) {
	var fileName = $('#file1').val();
	upload(sex, birthday, headUrl, nickname, fileName);
}

function upload(sex, birthday, headUrl, nickname, fileName) {
	$.ajaxFileUpload({
		url : mainServer + '/weixin/usercenter/updateUserInfo', // 提交的路径
		secureuri : false, // 是否启用安全提交，默认为false
		fileElementId : 'file1', // file控件id

		dataType : 'json',
		data : {
			sex : sex,
			birthday : birthday,
			nickname : nickname,
			touxiang : fileName
		// 传递参数，用于解析出文件名
		}, // 键:值，传递文件名
		success : function(data, status) {
			if ("succ" == data.result) {
				location.href = mainServer + "/weixin/tologin";
			} else {
				showvaguealert(data.msg);
			}
		},
		error : function(data, status) {
			showvaguealert(eval("(" + data.responseText + ")").msg);
		}
	});
}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 3000);
}