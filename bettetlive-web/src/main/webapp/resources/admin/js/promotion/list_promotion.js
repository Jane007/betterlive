var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();

var boxDataGrid;
// 提示弹框
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}

function findPromotion() {
	var startTime = $("input[name=startTime]").val(); // 开始时间
	var endTime = $("input[name=endTime]").val(); // 结束时间
	if (startTime > endTime) {
		showvaguealert("开始时间不能大于结束时间");
		return false;
	}

	searchPromotion();
}

$(function() {
	boxDataGrid = StoreGrid.createGrid('PromotionGrid');
	searchPromotion();
});
function searchPromotion() {
	var promotionName = $("#promotionName").val();
	var startTime = $("input[name=startTime]").val(); // 开始时间
	var endTime = $("input[name=endTime]").val(); // 结束时间
	$('#PromotionGrid').datagrid('load', {
		"promotionName" : promotionName,
		"startTime" : startTime,
		"endTime" : endTime
	})
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/promotion/queryPromotionAllJson',
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
			columns : [ [ {
				title : "序号",
				field : "promotionId",
				checkbox : true,
				width : '5%'
			}, {
				title : "活动名称",
				field : "promotionName",
				width : '25%'
			}, {
				title : "满多少钱",
				field : "fullMoney",
				width : '10%'
			}, {
				title : "减多少钱",
				field : "cutMoney",
				width : '10%'

			}, {
				title : "状态",
				field : "status",
				width : '15%',
				formatter : function(value, row) {
					switch (value) {
					case 1:
						return '正常';
					case 0:
						return '删除';
					}
				}
			}, {
				title : "开始时间",
				field : "startTime",
				width : '15%'
			}, {
				title : "结束时间",
				field : "endTime",
				width : '15%'

			}, {
				title : "创建时间",
				field : "createTime",
				width : '15%'

			} ] ],
			toolbar : '#storeToolbar',
			onBeforeLoad : function(param) {
				$('#PromotionGrid').datagrid('clearSelections');
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
// 查看详细、添加预售、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
	}
}

// 新增商品
function toAddPromotion() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/promotion/toAddPromotion";
	if (centerTabs.tabs('exists', '添加促销活动信息')) {
		centerTabs.tabs('select', '添加促销活动信息');
		var tab = centerTabs.tabs('getTab', '添加促销活动信息');
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
							title : '添加促销活动信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
// 修改活动
function toEditPromotion() {
	var centerTabs = parent.centerTabs;
	var promotionId = boxDataGrid.datagrid('getSelected').promotionId;
	if (promotionId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}

	var url = mainServer + "/admin/promotion/toEditPromotion?promotionId="
			+ promotionId;
	if (centerTabs.tabs('exists', '修改活动信息')) {
		centerTabs.tabs('select', '修改活动信息');
		var tab = centerTabs.tabs('getTab', '修改活动信息');
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
							title : '修改活动信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
// 删除 活动
function toDelPromotion() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var promotionIdArray = "";
	$.each(objectArray, function(i, obj) {
		promotionIdArray += obj.promotionId + ",";
	});

	if (promotionIdArray == '') {
		$.messager.alert('提示', "请先选择商品", 'error');
		return false;
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {

			$.ajax({
				url : "delPromotion",
				data : {
					"promotionIdArray" : promotionIdArray
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					var obj = eval('(' + data + ')');
					if (obj.result == 'succ') {
						searchPromotion();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}