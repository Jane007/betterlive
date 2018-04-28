function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});
function searchSpecial() {
	$('#StoreGrid').datagrid('load', {
		"specialType" : $("#specialType").val(),
		"specialName" : $("#specialName").val(),
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/specialmessage/querySpecialAllJson',
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
				field : "specialId",
				checkbox : true,
				width : 100
			}, {
				title : "用户昵称",
				field : "nickname",
				width : 100
			}, {
				title : "联系方式",
				field : "mobile",
				width : 100
			}, {
				title : "专题名称",
				field : "specialName",
				width : 100
			}, {
				title : "专题标题",
				field : "specialTitle",
				width : 80
			}, {
				title : "专题介绍",
				field : "specialIntroduce",
				width : 100
			}, {
				title : "消息创建时间",
				field : "createTime",
				width : 80,
				formatter : function(value, row) {
					var date = new Date();
					date.setTime(value);
					return myformatter(date);
				}
			}, {
				title : "消息标题",
				field : "msgTitle",
				width : 80
			}, {
				title : "消息内容",
				field : "msgDetail",
				width : 80
			}
			// ,{
			// title:"消息类型",
			// field:"msgType",
			// width:80,
			// formatter:function(value,row){
			// switch(value){
			// case 1:return'挥货活动';
			// }
			// }
			// },{
			// title:"消息分类",
			// field:"subMsgType",
			// width:80,
			// formatter:function(value,row){
			// switch(value){
			// case 10:return'商品公告';
			// }
			// }
			// }
			, {
				title : "消息分类",
				field : "specialType",
				width : 80,
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '限时活动';
					case 2:
						return '限量抢购';
					case 3:
						return '团购活动';
					case 4:
						return '美食教程';
					}
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

// 新增商品
function toAddSpecial() {
	var url = mainServer + "/admin/specialmessage/toAddMessageSpecial";
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