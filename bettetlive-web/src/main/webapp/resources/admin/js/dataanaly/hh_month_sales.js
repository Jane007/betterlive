// 查询月度
function queryDayCanvas(queryMonth) {
	$("#queryDate").val(queryMonth);
	$("#canvasForm")
			.attr("action", mainServer + "/admin/orderanaly/hhDaySales");
	document.forms['canvasForm'].submit();
}

// 查询其他平台年度月报表
function queryOtherMonthCanvas() {
	$("#queryDate").val("");
	$("#canvasForm").attr("action",
			mainServer + "/admin/orderanaly/otherMonthSales");
	document.forms['canvasForm'].submit();
}

// 用户来源统计
function customerSourceStatistics() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/orderanaly/hhCustomerSourceStatistics";
	if (centerTabs.tabs('exists', '用户来源统计')) {
		centerTabs.tabs('select', '用户来源统计');
		var tab = centerTabs.tabs('getTab', '用户来源统计');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '用户来源统计',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 订单来源统计
function orderSourceStatistics() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/orderanaly/hhOrderSourceStatistics";
	if (centerTabs.tabs('exists', '订单来源统计')) {
		centerTabs.tabs('select', '订单来源统计');
		var tab = centerTabs.tabs('getTab', '订单来源统计');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '订单来源统计',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 商品销量
function productSalseStatistics() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/orderanaly/hhProductSalseStatistics";
	if (centerTabs.tabs('exists', '挥货平台商品销量')) {
		centerTabs.tabs('select', '挥货平台商品销量');
		var tab = centerTabs.tabs('getTab', '挥货平台商品销量');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '挥货平台商品销量',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}