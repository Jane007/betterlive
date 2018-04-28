var tabIndex = 0;
var nextIndex = 1;
var loadtobottom = true;
$(function() {
	$("#myordercent0").html("");
	$("#myordercent0").show();
	$(".noyhbg").hide();
	queryCoupons(0, 1, 10);
	var ordernav = $(".myordrnav li");
	var ordercent = $(".myordercent");
	for (var i = 0; i < ordernav.length; i++) {
		ordernav[i].index = i;
		ordernav[i].onclick = function() {
			for (var i = 0; i < ordernav.length; i++) {
				ordernav[i].className = "";
				ordercent[i].style.display = "none";
			}
			ordernav[this.index].className = "current";
			ordercent[this.index].style.display = "block";
			$("#myordercent" + this.index).html("");
			$("#myordercent" + this.index).show();
			$(".noyhbg").hide();
			tabIndex = this.index;
			nextIndex = 1;
			loadtobottom = true;
			$("#pageNext").val(1);
			$(window).scrollTop(0);
			queryCoupons(this.index, 1, 10);
		}
	}
});

// 往上滑
$(document).scroll(
		function() {
			totalheight = parseFloat($(window).height())
					+ parseFloat($(window).scrollTop());
			if ($(document).height() <= totalheight) {
				if (loadtobottom == true) {
					var next = $("#pageNext").val();
					var pageCount = $("#pageCount").val();
					var pageNow = $("#pageNow").val();

					if (parseInt(next) > 1) {
						if (nextIndex != next) {
							nextIndex++;
							queryCoupons(tabIndex, next, 10);
							$(".loadingmore").show();
							setTimeout(function() {
								$(".loadingmore").hide();

							}, 1500);
						}
					}
					if (parseInt(next) >= parseInt(pageCount)) {
						loadtobottom = false;
					}

				}
			}
		});

function queryCoupons(idx, pageIndex, pageSize) {
	$(".initloading").show();

	var index = idx;
	if (idx == 2) {
		index = 3;
	}
	$.ajax({
		url : mainServer + '/weixin/customercoupon/myCouponList',
		data : {
			rows : pageSize,
			pageIndex : pageIndex,
			"useFlag" : index
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.code == "1010") { // 获取成功
				var pageNow = data.pageInfo.pageNow;
				var pageCount = data.pageInfo.pageCount;
				$("#pageCount").val(pageCount);
				$("#pageNow").val(pageNow);
				var next = parseInt(pageNow) + 1;
				if (next >= pageCount) {
					next = pageCount;
				}
				$("#pageNext").val(next);
				$("#pageNow").val(pageNow);
				if ((data.data == null || data.data.length <= 0)
						&& pageIndex == 1) {
					setTimeout(function() {
						$(".initloading").hide();
						$("#myordercent" + idx).hide();
						$(".noyhbg").show();
					}, 300);
					return;
				} else {
					$(".noyhbg").hide();
				}
				var list = data.data;
				$(".noyhbg").hide();
				for ( var continueIndex in list) {
					if (isNaN(continueIndex)) {
						continue;
					}
					var c = list[continueIndex];
					var couponContent = "";
					if (c.coupon_content != null) {
						couponContent = c.coupon_content;
						if (couponContent.length > 8) {
							couponContent = couponContent.substring(0, 8)
									+ "...";
						}
					}

					var endtime = c.endtime;
					endtime = endtime.substring(0, 10);
					var statusLine = "class=\"yhbox\"";
					if (c.status == 1) {
						statusLine = "class=\"yhbox yhybox\"";

					} else if (c.status == 2 || (c.status == 0 && index == 3)) {
						statusLine = "class=\"yhbox yhybox yhgbox\"";
					}
					var showHtml = '	 			<div ' + statusLine + ' >'
							+ '	 				<div class="yhnrcent">'
							+ '	 			<div class="top">'
							+ '	 			<strong><em>￥</em>' + c.coupon_money
							+ '</strong>' + '	 			<span><em>' + c.coupon_name
							+ '</em>(满' + c.start_money + '可用)<p>'
							+ couponContent + '</p> </span>' + '	 			</div>'
							+ '	 			<div class="xiam">' + '	 				<span>有效期至'
							+ endtime + '</span>' + '	 			</div>'
							+ '	 			</div>' + '	 		</div>';

					$("#myordercent" + idx).append(showHtml);
				}
				setTimeout(function() {
					$(".initloading").hide();
				}, 800);
			} else if (data.code == "1011") {
				window.location.href = mainServer + "/weixin/tologin";
			} else {
				showvaguealert("出现异常");
			}
		},
		failure : function(data) {
			showvaguealert('出错了');
		}
	});
}

/**
 * 
 * 获取当前时间
 */
function p(s) {
	return s < 10 ? '0' + s : s;
}

// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 3000);
}