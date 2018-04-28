//字符限制提示
$(function() {
	// 匹配包含给定属性的元素，keyup在按键释放时发生
	$("#textval").keyup(function() {
		var area = $(this);
		// parseInt 方法返回与保存在 numString 中的数字值相等的整数。如果 numString 的前缀不能解释为整数，则返回
		// NaN（而不是数字）。
		var max = parseInt(area.attr("maxlength"), 10); // 获取maxlength的值
														// 转化为10进制，将输入到textarea的文本长度
		// 这个判断可知max得到的是不是数字，设定的大小是多少
		if (max > 0) {

			if (area.val().length > max) { // textarea的文本长度大于maxlength
				area.val(area.val().substr(0, max)); // 截断textarea的文本重新赋值
			}

			var yishu = area.val().length;
			var sheng = max - area.val().length;
			$("#lyishu").html(yishu);
			$("#lsheng").html(sheng);
		}
	});
	$("#textval").blur(function() {
		var area = $(this);
		var max = parseInt(area.attr("maxlength"), 10); // 获取maxlength的值
		if (max > 0) {

			if (area.val().length > max) { // textarea的文本长度大于maxlength
				area.val(area.val().substr(0, max)); // 截断textarea的文本重新赋值
			}

			var yishu = area.val().length;
			var sheng = max - area.val().length;
			$("#lyishu").html(yishu);
			$("#lsheng").html(sheng);
		}
	});

});

var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
$(function() {

	// 添加图片到手机的方法
	function preview1(file, showobj) {
		var img = new Image(), url = img.src = URL.createObjectURL(file);
		var $img = $(img);
		img.onload = function() {
			// URL.revokeObjectURL(url)
			$('.' + showobj).append($img);
		}
	}
	// 上传图片
	var i = 0;// 最多只能6张
	$('#filepic').change(function(e) {
		if (i < 6) {
			var oli = '<li></li>';
			$('.updataBox ul').append(oli);
			var file = e.target.files[0];
			var a2 = uploadImg(file);
			preview1(file, 'updataBox li:last');
			$("#filepic").val("");
			i++;
		} else {
			$('.updataBox label').hide();
			showvaguealert('最多上传6张图片');
		}
	});
	// 点击图片
	$(document).on('click', '.updataBox li', function() {
		$('.updataBox li i').remove();
		var oi = '<i></i>';

		$(this).append(oi);
	});
	// 删除图片
	$(document).on('click', '.updataBox li i', function() {
		var a1 = $("#urlimages").val();
		var a2 = $(this).prev('img').attr("src") + ",";

		$("#urlimages").val(a1.replace(a2, ""));
		buffer = $("#urlimages").val();

		$(this).parents('li').remove();
		i--;
		if (i <= 6) {
			$('.updataBox label').show();
		}
	});

	// 成功完成后跳转订单详情
	function go1() {
		window.location.href = mainServer+"/weixin/order/findList?status=4";
	}

	// 提交评价
	$('.submitBox').click(
			function() {
				if (!$(this).hasClass('active')) {
					var ovalue = $('.textcon textarea').val();
					var len = $('.updataBox li').length;

					var maxChars = 100;
					var txtVal = $("#textval").val();
					if (txtVal == null || txtVal == "" || txtVal.length <= 0) {
						showvaguealert('请填写评论内容');
						return false;
					}

					if (txtVal.length > maxChars) {
						showvaguealert('亲，评论过长</br>请输入100字以内！');
						return false;
					}

					if (ovalue != null && ovalue != '') {

						var formData = new FormData($("#evalform")[0]);

						$.ajax({
							url : mainServer
									+ '/weixin/productcomment/saveComment',
							type : 'POST',
							data : formData,
							async : false,
							cache : false,
							contentType : false,
							processData : false,
							success : function(data) {
								var obj = eval('(' + data + ')');
								if (obj.result == "succ") {

									var picCnt = $('.updataBox li').length;

									$('.textcon textarea').attr('readonly',
											'readonly');
									if (picCnt > 0) {
										var num1 = uploadSuccess();
										setTimeout(function() {
											$('.submit-success').hide();
										}, picCnt * 2000);
									}

									showvaguealert('评价提交成功');
									$('.submitBox').addClass('active')
											.html('您的评价已提交');
									setTimeout(function() {
										go1();
									}, 2000);

								} else {
									showvaguealert('提交失败！');
								}
							},
							error : function() {
								showvaguealert('异常！');
							}
						});
						/*
						 * $('#evalform').form('submit', {
						 * url:'${mainserver}/weixin/order/saveComment',
						 * onSubmit: function(){ }, success:function(data) { var
						 * obj=eval('('+data+')'); if(obj.result =="succ"){
						 * $('.submit-success').show(); $('.mask').show();
						 * }else{ showvaguealert('提交失败！'); } }, error :
						 * function() { showvaguealert('异常！'); } });
						 */
					} else {
						showvaguealert('亲，请输入您的评价！');
					}
				}

			});

	// 点击好的
	$('.OK').click(function() {
		go1();
		// 添加改变样式
	});
});

function uploadSuccess() {
	var em = '<em></em>';
	// $('.updataBox li').append(em);

	// var lilist = $('.updataBox li');
	var num = 2000;
	$('li').each(function() {
		var a1 = $(this);
		setTimeout(function() {
			a1.append(em);
		}, num);
		num = num + 1500;
	});
	return num;
}

function uploadImg() {
	var a2 = "";
	var formData = new FormData($("#evalform")[0]);

	$.ajax({
		url : mainServer + '/weixin/productcomment/uploadImg', /* 这是处理文件上传的servlet */
		type : 'POST',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			var a1 = data.imgurl + ",";
			buffer += a1;
			$("#urlimages").val(buffer);
			/*
			 * alert() var obj=eval('('+data+')'); if(obj.result =="succ"){
			 * $('.submit-success').show(); $('.mask').show(); }else{
			 * showvaguealert('提交失败！'); }
			 */
			a2 = data.imgurl;
		},
		error : function() {
			showvaguealert('异常！');
		}
	});
	return a2;
}

$(function() {

	var url = mainServer + "/weixin/order/findList?status=" + type;
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
