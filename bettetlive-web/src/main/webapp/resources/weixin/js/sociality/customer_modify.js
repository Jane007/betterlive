function guan() {
	$(".xbbox").hide();
	$(".mask").hide();
}
$(".xbbox>ul>li>a").click(
		function() {
			var backUrl = mainServer + "/weixin/customer/toCustomerModify";
			var xingbie = $(this).text();
			var sexChange = "0";
			if ($(this).text() != "取消") {
				switch ($(this).text()) {
				case "男":
					sexChange = "1";
					$('.xbbox>ul>li').removeClass('on');
					$('.xbbox>ul>li').eq(0).addClass('on');
					break;
				case "女":
					sexChange = "2";
					$('.xbbox>ul>li').removeClass('on');
					$('.xbbox>ul>li').eq(1).addClass('on');
					break;
				default:
					sexChange = "0";
					$('.xbbox>ul>li').removeClass('on');
					$('.xbbox>ul>li').eq(2).addClass('on');
					break;
				}
				;
				$.ajax({
					type : "POST",
					data : {
						sex : sexChange
					},
					dataType : "JSON",
					async : false,
					url : mainServer + "/weixin/customer/changeSex",
					success : function(data) {
						if (data.result == 'success') {
							$(".xbzhi").text(xingbie);
							$(".xbbox").hide();
							$(".mask").hide();
						} else if (data.result == "failure") {
							window.location.href = mainServer
									+ "/weixin/tologin?backUrl=" + backUrl;
						} else {
							$(".xbbox").hide();
							$(".mask").hide();
						}
					}
				});
			} else {
				$(".xbbox").hide();
				$(".mask").hide();
			}

})
		
		
$(function() {

	if (sex == 0) {
		$("#custSex").html("保密");
		$('.xbbox>ul>li').removeClass('on');
		$('.xbbox>ul>li').eq(2).addClass('on');
	} else if (sex == 1) {
		$("#custSex").html("男");
		$('.xbbox>ul>li').removeClass('on');
		$('.xbbox>ul>li').eq(0).addClass('on');
	} else if (sex == 2) {
		$("#custSex").html("女");
		$('.xbbox>ul>li').removeClass('on');
		$('.xbbox>ul>li').eq(1).addClass('on');
	}
	
	if (null != signature && "" != signature) {
		if (signature.length > 15) {
			$("#custSignature").html(signature.substring(0, 15) + "……");
		} else {
			$("#custSignature").html(signature);
		}
	} else {
		$("#custSignature").html("请填写");
	}

	if (null != mobile && "" != mobile) {
		$("#phone").html("已绑定");
	} else {
		$("#phone").html("未绑定");
	}
	var openid = "${customer.openid }";
	if (null != openid && "" != openid) {
		$("#isOpenId").html("已绑定");
		$("#isOpenId").parent().attr("style",
				"background:none;padding-right:0.3rem;");
	} else {
		$("#isOpenId").html("未绑定");
		$("#isOpenId").click(function() {
			bindWeChat();
		})
	}
	// 点击出现性别选择
	$(".xbxuan").click(function() {
		$(".xbbox").show();
		$(".mask").show();
	})

	$('body').on("change", "#file1", function() {
		var file = document.getElementById("file1");
		var oimg = document.getElementById("imgHead");
		var oimgSrc = oimg.src;
		var fileName = $('#file1').val();
		var prefix = fileName.substring(fileName.lastIndexOf("."));
		switch (prefix.toLowerCase()) {
		case ".jpg":
			oimg.src = window.URL.createObjectURL(file.files[0]);
			upload(fileName, oimgSrc);
			break;
		case ".jpeg":
			oimg.src = window.URL.createObjectURL(file.files[0]);
			upload(fileName, oimgSrc);
			break;
		case ".bmp":
			oimg.src = window.URL.createObjectURL(file.files[0]);
			upload(fileName, oimgSrc);
			break;
		case ".png":
			oimg.src = window.URL.createObjectURL(file.files[0]);
			upload(fileName, oimgSrc);
			break;
		default:
			showvaguealert("请上传正确的图片类型");
			break;
		}
	});
});

function upload(fileName, oimgSrc) {
	var file = document.getElementById("file1");
	var maxSize = 1024 * 1024;
	var fileSize = file.files[0].size;
	if (fileSize <= maxSize) {
		$.ajaxFileUpload({
			url : mainServer + '/weixin/customer/editHeadUrl', // 提交的路径
			secureuri : false, // 是否启用安全提交，默认为false
			fileElementId : 'file1', // file控件id

			dataType : 'json',
			data : {
				touxiang : fileName
			// 传递参数，用于解析出文件名
			}, // 键:值，传递文件名
			success : function(data, status) {
				if ("succ" == data.result) {
					// location.href="${mainserver}/weixin/customer/toCustomerModify";
				} else {
					showvaguealert(data.msg);
				}
			},
			error : function(data, status) {
				showvaguealert(eval("(" + data.responseText + ")").msg);
				setTimeout(function() {
					location.reload();
				}, 1000);
			}
		});
	} else {
		showvaguealert("请上传小于1M大小的图片");
		var oimg = document.getElementById("imgHead");
		oimg.src = oimgSrc;
	}

}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 3000);
}

function editNickName() {
	location.href = mainServer + "/weixin/customer/toUpdateNickName";
}

function editSignature() {
	location.href = mainServer + "/weixin/customer/toUpdateSignature";
}

function editNewPhone() {
	location.href = mainServer + "/weixin/customer/toUpdateNewPhone";
}

// 绑定微信号
function bindWeChat() {
	location.href = mainServer + "/weixin/customer/bindWeChat";
}

// 修改微信自带的返回键
$(function() {

	var url = mainServer + "/weixin/socialityhome/toSocialityHome";

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