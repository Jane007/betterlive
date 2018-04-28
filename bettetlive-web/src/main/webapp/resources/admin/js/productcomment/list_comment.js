var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();

var boxDataGrid;
function showvaguealert(con) {
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function() {
		$('.vaguealert').hide();
	}, 2000);
}
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

$('#start').datetimebox({
	required : false,
	showSeconds : true
});
$('#end').datetimebox({
	required : true,
	showSeconds : true
});
function searchComment() {
	var mobile = $("#customerName").val();
	var startTime = $("input[name=start]").val(); // 开始时间
	var endTime = $("input[name=end]").val(); // 结束时间
	if (startTime > endTime) {
		showvaguealert("开始时间不能大于结束时间");
		return false;
	}
	if ((/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(mobile))) {
		$('#StoreGrid').datagrid('load', {
			"product_name" : $("#productName").val(),
			"mobile" : mobile,
			"start" : $("input[name=start]").val(),
			"end" : $("input[name=end]").val()
		});
	} else {
		$('#StoreGrid').datagrid('load', {
			"product_name" : $("#productName").val(),
			"customerName" : $("#customerName").val(),
			"start" : $("input[name=start]").val(),
			"end" : $("input[name=end]").val()
		});
	}

}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/productcomment/queryCommentAllJson?parentFlag=1',
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
							columns : [ [
									{
										title : "序号",
										field : "comment_id",
										checkbox : true,
										width : 100
									},
									{
										title : "订单编号",
										field : "order_code",
										width : 100
									/*
									 * }, { title:"商品编号", field:"product_id",
									 * width:60
									 */
									},
									{
										title : "商品名称",
										field : "product_name",
										width : 80
									},
									{
										title : "客户昵称",
										field : "customerVo.nickname",
										width : 60,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											if (rec.customerVo != null
													&& rec.customerVo.nickname != null) {
												return rec.customerVo.nickname;
											}
										}
									},
									{
										title : "联系方式",
										field : "mobile",
										width : 80
									},
									{
										title : "评论内容",
										field : "content",
										width : 150,
										formatter : function(value, data, index) {
											var showLine = "";
											if (value != null && value != "") {
												showLine = '<span title="'
														+ value
														+ '"  class="easyui-tooltip">'
														+ value + '</span>';
											}
											return showLine;
										}
									},
									{ 
										title:"评论人数", 
										field:"reply_count",
										width:80 
									 },
									{ 
										title:"点赞人数", 
										field:"praise_count",
										width:80 
									 },
									 {
										title : "评论时间",
										field : "create_time",
										width : 95
									}, {
										title : "审核状态",
										field : "status",
										width : 50,
										formatter : function(value, row) {
											switch (value) {
											case 2:
												return '通过';
											case 3:
												return '不通过';
											case 4:
												return '已删除';
											default:
												return '未审核';
											}
										}
									},{
										title : "回复评论",
										field : "aa",
										width : 80,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<a href="'
													+ mainServer
													+ '/admin/productcomment/findIdList?id='
													+ rec.comment_id
													+ '">查看全部回复</a>'

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
							}
						});
	}
};

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'reConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'checkConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'reConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'checkConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'reConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'checkConfBtn');
	}
}

function updConfDlg() {
	var centerTabs = parent.centerTabs;
	var commentId = boxDataGrid.datagrid('getSelected').comment_id;
	var productId = boxDataGrid.datagrid('getSelected').product_id;

	if (commentId == '' || productId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要删除的评论</div>',
				'error');
		return false;
	}

	$.ajax({
		type : "POST",
		url : mainServer + "/admin/productcomment/delCommentInfo",
		data : {
			'commentId' : commentId,
			'productId' : productId
		},
		async : false,
		success : function(data) {
			var obj = eval(data);

			if ("succ" == obj.result) {
				$.messager.alert('提示消息',
						'<div style="position:relative;top:20px;">删除编号ID为'
								+ commentId + '的评论成功</div>', 'success');
				searchComment();
			} else {
				$.messager.alert('提示消息',
						'<div style="position:relative;top:20px;">删除编号ID为'
								+ commentId + '的评论失败</div>', 'success');
			}
		}
	});
}

function checkConfDlg() {
	var centerTabs = parent.centerTabs;
	var commentId = boxDataGrid.datagrid('getSelected').comment_id;
	var productId = boxDataGrid.datagrid('getSelected').product_id;
	var _status = 0;
	if (commentId == '' || productId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要审核的评论</div>',
				'error');
		return false;
	}

	$.messager.defaults = {
		ok : "审核通过",
		cancel : "审核不通过"
	};
	$.messager.confirm("评论提示", "您确定对该条评论进行审核吗？", function(data) {
		if (data) {
			_status = 2;
		} else {
			_status = 3;
		}
		checkComment(_status, commentId, productId);
	});
//	$.messager.defaults = {
//		ok : "确认",
//		cancel : "取消"
//	};
}

function checkComment(_status, commentId, productId) {
	if (_status > 0) {
		$.ajax({
			type : "POST",
			url : mainServer + "/admin/productcomment/checkComment",
			data : {
				'commentId' : commentId,
				'productId' : productId,
				'status' : _status
			},
			async : false,
			success : function(data) {
				var obj = eval('(' + data + ')');
				$.messager.defaults = {
				ok : "确认"
				};
				if ("succ" == obj.result) {
					$.messager.alert('提示消息',
							'<div style="position:relative;top:20px;">编号ID为'
									+ commentId + '的审核操作成功</div>', 'info');
					searchComment();
				} else {
					$.messager.alert('提示消息',
							'<div style="position:relative;top:20px;">编号ID为'
									+ commentId + '的审核操作失败</div>', 'error');
				}
			}
		});
	}
}

function clearForm() {
	$("#dlg").form('clear');
}
function reConfDlg() {

	$('#dlg').dialog('open');
	$('#dlg').form('clear');
}
function submitReplyForm() {
	var rep = $('#replyMsg').val();

	if ("" == rep || null == rep) {
		$.messager.alert('提示消息', "评论不能为空", 'info');
		$("#dlg").dialog("close");
		searchComment();
		return;
	}
	var spId = $('#StoreGrid').datagrid('getSelected');
	if (spId != null) {
		$('#commentId').val(spId.comment_id);
	}
	$('#party_base_info').form('submit', {
		url : 'addReplyComment',
		ajax : true,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			data = typeof data == 'object' ? data : $.parseJSON(data);

			if (data.result != -1) {
				$.messager.alert('提示消息', data.msg, 'info');
				$("#dlg").dialog("close");
				searchComment();
			} else {
				$.messager.alert('提示消息', data.msg, 'error');
				$("#dlg").dialog("close");
			}
		}
	});
}