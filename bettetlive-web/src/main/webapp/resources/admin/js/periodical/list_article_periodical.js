var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('dtGrid');
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

function searchDatas() {
	$('#dtGrid').datagrid('load', {
		"periodical" : $("#periodical").val(),
		"periodicalTitle" : $("#periodicalTitle").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/articleperiodical/queryListPage?status=1',
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
							columns : [ [ {
								title : "序号",
								field : "periodicalId",
								checkbox : true,
								width : 100
							}, {
								title : "期数",
								field : "periodical",
								width : 100
							}, {
								title : "期刊主题",
								field : "periodicalTitle",
								width : 100
							}, {
								title : "创建时间",
								field : "strCreateTime",
								width : 80
							} ] ],
							toolbar : '#toolbar',
							onBeforeLoad : function(param) {
								$('#dtGrid').datagrid('clearSelections');
							},
							onLoadSuccess : function(data) {
								initCUIDBtn();
							},
							onSelect : function(rowIndex, rowData) {
								initCUIDBtn();
							},
							onUnselect : function(rowIndex, rowData) {
								initCUIDBtn();
							}
						});
	}
};

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
	}
}

function toEditData() {
	var centerTabs = parent.centerTabs;
	var periodicalId = boxDataGrid.datagrid('getSelected').periodicalId;
	if (periodicalId == null || periodicalId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer
			+ "/admin/articleperiodical/toEditArticlePeriodical?periodicalId="
			+ periodicalId;
	if (centerTabs.tabs('exists', '修改文章期刊')) {
		centerTabs.tabs('select', '修改文章期刊');
		var tab = centerTabs.tabs('getTab', '修改文章期刊');
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
							title : '修改文章期刊',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增商品
function toAddData() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/articleperiodical/toAddArticlePeriodical";
	if (centerTabs.tabs('exists', '添加文章期刊')) {
		centerTabs.tabs('select', '添加文章期刊');
		var tab = centerTabs.tabs('getTab', '添加专题文章信息');
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
							title : '添加文章期刊',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
// 逻辑删除专题
function toDeleteData(value) {
	$.messager
			.confirm(
					"提示",
					"你确认要删除吗?",
					function(r) {
						if (r) {
							var periodicalId = boxDataGrid
									.datagrid("getSelected").periodicalId;
							$
									.ajax({
										url : mainServer
												+ "/admin/articleperiodical/deleteArticlePeriodical",
										data : {
											"periodicalId" : periodicalId
										},
										datatype : "json",
										type : "post",
										success : function(data) {

											if (data.code == '1010') {
												searchDatas();
											} else {
												$.messager.alert('提示',
														data.msg, 'error');
											}

										}

									});
						}
					});
}