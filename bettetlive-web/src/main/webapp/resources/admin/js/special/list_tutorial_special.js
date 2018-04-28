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

function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

function searchSpecial() {
	var startTime = $("input[name=startTime]").val();
	var endTime = $("input[name=endTime]").val();
	if (startTime > endTime) {
		showvaguealert("开始时间不能大于结束时间");
		return false;
	}
	$('#StoreGrid').datagrid('load', {
		"specialType" : $("#specialType").val(),
		"specialName" : $("#specialName").val(),
		"startTime" : startTime,
		"endTime" : endTime,
		"status" : $("#status").val(),
		"homeFlag":$("#homeFlag").val()

	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/special/querySpecialAllJson?specialType=4&status=1',
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
										field : "specialId",
										checkbox : true,
										width : 100
									},
									{
										title : "教程名称",
										field : "specialName",
										width : 100
									},
									{
										title : "教程封面",
										field : "specialCover",
										width : 120,
										height : 80,
										align : "left",
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<img src=' + value
													+ ' style="height:80px;"/>';
										}
									}, {
										title : "教程标题",
										field : "specialTitle",
										width : 80
									}, {
										title : "教程介绍",
										field : "specialIntroduce",
										width : 100
									}, {
										title : "视频路径",
										field : "specialPage",
										width : 80

									}, {
										title : "教程开始时间",
										field : "startTime",
										width : 80
									}, {
										title : "教程结束时间",
										field : "endTime",
										width : 80
									}, {
										title : "教程创建时间",
										field : "createTime",
										width : 80
									}, {
										title : "状态",
										field : "status",
										width : 100,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var showValue = "已删除";
											if (value == 1) {
												showValue = "上架";
											} else if (value == 2) {
												showValue = "下架";
											}
											return showValue;
										}
									} ] ],
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

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'upConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'downConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'upConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'downConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'upConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'downConfBtn');
	}
}

function toEditSpecial() {
	var centerTabs = parent.centerTabs;
	var specialId = boxDataGrid.datagrid('getSelected').specialId;
	if (specialId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer + "/admin/special/toEditTutorialSpecial?specialId="
			+ specialId;
	if (centerTabs.tabs('exists', '修改教程信息')) {
		centerTabs.tabs('select', '修改教程信息');
		var tab = centerTabs.tabs('getTab', '修改教程信息');
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
							title : '修改教程信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增商品
function toAddSpecial() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/special/toAddTutorialSpecial";
	if (centerTabs.tabs('exists', '添加美食教程')) {
		centerTabs.tabs('select', '添加美食教程');
		var tab = centerTabs.tabs('getTab', '添加美食教程');
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
							title : '添加美食教程',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
// 逻辑删除教程
function toDelSpecial(status) {
	var objectArray = boxDataGrid.datagrid('getSelections');
	var specialIdArray = "";
	$.each(objectArray, function(i, obj) {
		specialIdArray += obj.specialId + ",";
	});
	if (specialIdArray == '') {
		$.messager.alert('提示', "请先选择商品", 'error');
		return false;
	} else {
		if (specialIdArray.lastIndexOf(",") != -1) {
			specialIdArray = specialIdArray.substring(0, specialIdArray
					.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认此操作吗?", function(r) {
		if (r) {
			$.ajax({
				url : "delSpecial",
				data : {
					"specialIdArray" : specialIdArray,
					"checkStatus" : status
				},
				datatype : "json",
				type : "post",
				success : function(data) {

					var obj = eval('(' + data + ')');
					if (obj.result == 'succ') {
						searchSpecial();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}
