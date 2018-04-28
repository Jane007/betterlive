var boxDataGrid;
$(function() {
	boxDataGrid = MsgGrid.createGrid('msgGrid');
});

var MsgGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid(
				{
					url : mainServer + '/admin/message/queryMsgList',
					rownumbers : true,
					singleSelect : false,
					pagination : true,
					fit : true,// 自适应大小
					fitColumns : true, // 自动使列适应表格宽度以防止出现水平滚动。
					striped : true,
					checkOnSelect : true, // 点击某一行时，则会选中/取消选中复选框
					selectOnCheck : true, // 点击复选框将会选中该行
					collapsible : true,
					columns : [ [
							{
								title : "",
								field : "msgId",
								checkbox : true
							},
							{
								title : "用户",
								field : "customerName",
								width : "15%"
							},
							{
								title : "消息标题",
								field : "msgTitle",
								width : "20%"
							},
							{
								title : "消息类型",
								field : "msgType",
								width : "10%",
								formatter : function(value, row) {
									var showLine = "";
									if (value == 0) {
										showLine = "系统消息";
									} else if (value == 1) {
										showLine = "挥货活动";
									} else if (value == 2) {
										showLine = "优惠券/红包";
									} else if (value == 3) {
										showLine = "交易消息";
									} else if (value == 4) {
										showLine = "好友消息 ";
									}
									return showLine;
								}
							},
							{
								title : "详细分类",
								field : "subMsgType",
								width : "10%",
								formatter : function(value, row) {
									var showLine = "";
									// 消息详细分类：0默认，1获取优惠券，2获取红包，3优惠券过期提醒，4红包过期提醒，5订单已支付，6订单已发货，7订单已退款，8物流公告，9评价消息，10商品公告
									if (value == 0) {
										showLine = "系统公告";
									} else if (value == 1) {
										showLine = "获取优惠券";
									} else if (value == 2) {
										showLine = "获取红包";
									} else if (value == 3) {
										showLine = "优惠券过期提醒";
									} else if (value == 4) {
										showLine = "红包过期提醒 ";
									} else if (value == 5) {
										showLine = "订单已支付 ";
									} else if (value == 6) {
										showLine = "订单已发货";
									} else if (value == 7) {
										showLine = "订单已退款";
									} else if (value == 8) {
										showLine = "物流公告";
									} else if (value == 9) {
										showLine = "评价消息";
									} else if (value == 10) {
										showLine = "专题活动";
									}
									return showLine;
								}
							},
							{
								title : "创建时间",
								field : "createTime",
								width : "10%",
								formatter : function(value, row) {
									var date = new Date();
									date.setTime(value);
									return myformatter(date);
								}
							},
							{
								title : "消息内容",
								field : "msgDetail",
								width : "30%",
								formatter : function(value, row) {
									var showLine = "";
									if (value != null && value != "") {
										showLine = '<span title="' + value
												+ '"  class="easyui-tooltip">'
												+ value + '</span>';
									}
									return showLine;
								}
							} ] ],
					toolbar : '#msgToolbar',
					onBeforeLoad : function(param) {
						$('#msgGrid').datagrid('clearSelections');
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

function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

function queryData() {
	$('#msgGrid').datagrid('load', {
		"msgTitle" : $("#msgTitle").val(),
		"msgType" : $("#msgType").val()
	});
}

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
	// boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'deleteConfBtn');
	} else if (rows.length == 1) {
		// boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'deleteConfBtn');
	} else if (rows.length == 0) {
		// boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'deleteConfBtn');
	}
}

function addConfDlg() {
	var url = mainServer + "/admin/message/toAddMsg";
	var centerTabs = parent.centerTabs;
	if (centerTabs.tabs('exists', '发送消息')) {
		centerTabs.tabs('select', '发送消息');
		var tab = centerTabs.tabs('getTab', '发送消息');
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
							title : '发送消息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

function deleteConfDlg() {
	var msgId = $('#msgGrid').datagrid('getSelected').msgId;
	if (msgId <= 0) {
		$.messager.alert('提示消息', '请先选择要删除的数据', 'error');
		return false;
	}
	$.messager.confirm('确认框', '你确认要删除该条记录吗?', function(r) {
		if (r) {
			$.ajax({
				type : 'POST',
				url : mainServer + "/admin/message/delete",
				data : {
					msgId : msgId
				},
				dataType : 'json',
				success : function(data) {
					if (data.flag == 1) {
						$.messager.alert('提示消息', '删除成功', 'info');
						queryData();
					} else {
						$.messager.alert('提示消息', '删除失败', 'error');
					}
				},
				error : function() {
					alert("异常！");
				}
			});
		}
	});

}