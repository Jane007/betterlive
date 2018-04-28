var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

function myparser(s) {
	if (!s)
		return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/searchLabelReport/queryLabelAllJson',

							rownumbers : true,
							singleSelect : false,
							pagination : true,
							autoRowHeight : true,
							fit : true,
							fitColumns : true, // 自动使列适应表格宽度以防止出现水平滚动。
							striped : true,
							checkOnSelect : true, // 点击某一行时，则会选中/取消选中复选框
							selectOnCheck : true, // 点击复选框将会选中该行
							collapsible : true,
							showFooter : true,
							columns : [ [
									{
										title : "序号",
										field : "labelId",
										checkbox : true,
										width : 100,
									},
									{
										title : "标签名称",
										field : "labelName",
										width : 100,
									},
									{
										title : "标签状态",
										field : "status",
										width : 100,
										formatter : function(value, row) {
											switch (value) {
											case 0:
												return '上架';
											case 1:
												return '下架';
											}
										}

									},
									{
										title : "搜索次数",
										field : "searchCount",
										width : 100,

									},
									{
										title : "标签类型",
										field : "labelType",
										width : 100,
										formatter : function(value, row) {
											switch (value) {
											case 1:
												return '热门搜索';
											}
										}
									},
									{
										title : "排列顺序",
										field : "labelSort",
										width : 100,

									},
									{
										title : "详细列表",
										field : "xiangXi",
										width : 100,
										formatter : function(value, row) {
											return '<a href="javascript:clickDetailedEntity(\''
													+ row.labelId
													+ '\');" style="color:blue;">查看</a>';
										}
									}

							] ],
							toolbar : '#storeToolbar',
							onBeforeLoad : function(param) {
								$('#StoreGrid').datagrid('clearSelections');
							},
							onLoadSuccess : function(data) {
								initCUIDBtn();
							},
							onSelect : function(rowIndex, rowData) {
								initCUIDBtn();
							},
							onUnselect : function(rowIndex, rowData) {
								initCUIDBtn();
							},
							onCheckAll : function(rowIndex, rowData) {
								initCUIDBtn();
							}
						});
	}
};

function clickDetailedEntity(value) {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();
	if (startTime > endTime) {
		alert("开始时间不能大于结束时间");
		return false;
	}
	var centerTabs = parent.centerTabs;
	var url = "${mainserver}/admin/searchLabelReport/findSearchList?labelId="
			+ value + "&startTime=" + startTime + "&endTime=" + endTime;
	if (centerTabs.tabs('exists', '热门标签搜索列表')) {
		centerTabs.tabs('select', '热门标签搜索列表');
		var tab = centerTabs.tabs('getTab', '热门标签搜索列表');
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
							title : '热门标签搜索列表',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

function searchLabel() {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();
	if (startTime > endTime) {
		alert("开始时间不能大于结束时间");
		return false;
	}
	var showStart = $("input[name=showStart]").val();
	$('#StoreGrid').datagrid('load', {
		"labelName" : $("#labelName").val(),
		"labelType" : $("#labelType").val(),
		"startTime" : startTime,
		"endTime" : endTime,
		"showTime" : showStart
	});
}