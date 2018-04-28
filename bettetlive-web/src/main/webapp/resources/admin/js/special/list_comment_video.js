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

function searchComment() {
	$('#StoreGrid').datagrid('load', {
		"status" : $('#status').combobox('getValue')
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/specialcomment/queryVideoJson?parentFlag=1&status=2',
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
										field : "commentId",
										checkbox : true,
										width : 100
									},
									{
										title : "文章标题",
										field : "articleTitle",
										width : 100
									},
//									{
//										title : "评论人ID",
//										field : "customerId",
//										width : 80
//									},
									{
										title : "评论人",
										field : "customerVo.nickname",
										width : 80,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var nickname = "";
											if(rec.customerVo != null && rec.customerVo.nickname != null){
												nickname = rec.customerVo.nickname;
											}
											return nickname;
										}
									},
									{
										title : "点赞数",
										field : "praiseCount",
										width : 80
									},
									{
										title : "回复数",
										field : "replyCount",
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
										title : "专题类型",
										field : "specialType",
										width : 60,
										formatter : function(value, rec) {// 使用formatter格式化刷子
										
											return "教程";

										}
									},
									{
										title : "回复评论",
										field : "aa",
										width : 80,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<a href="' + mainServer + '/admin/specialcomment/findVideoIdList?id=' + rec.commentId + '" >查看全部回复</a>'

										}
									}, {
										title : "评论时间",
										field : "createTime",
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
		boxDataGrid.datagrid("disableToolbarBtn", 'reConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'checkConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'notCheckConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'reConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'checkConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'notCheckConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'reConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'checkConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'notCheckConfBtn');
	}
}

function updConfDlg() {
	var centerTabs = parent.centerTabs;
	var commentId = boxDataGrid.datagrid('getSelected').commentId;
	var specialId = boxDataGrid.datagrid('getSelected').specialId;

	if (commentId === '' || specialId === '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要删除的评论</div>',
				'error');
		return false;
	}
	$.ajax({
		type : "POST",
		url : mainServer + "/admin/specialcomment/delCommentInfo",
		data : {
			
			'commentId' : commentId,
			'specialId' : specialId
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

function checkConfDlg(_status) {
	
	var centerTabs = parent.centerTabs;
	var commentId = boxDataGrid.datagrid('getSelected').commentId;
	
	var opinonMsg = "";
	var opinonTitle = "";
	
	if (commentId == '' || commentId == undefined) {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要审核的评论</div>',
				'error');
		return false;
	}

	if (_status == 2) {
		opinonMsg = "评论审核通过";
		opinonTitle = "请输入通过理由";   
	}
	
	if (_status == 3) {
		opinonMsg = "您发表的评论有违规内容请查看规则后在发表";
		opinonTitle = "请输入拒绝理由"; 
	}
	
	var msg = opinonTitle + ': <input id="opinion" type="text" class="textbox-text validatebox-text textbox-prompt" autocomplete="off" value="'+ opinonMsg +'" style="margin-left: 30px; margin-right: 0px; padding-top: 6px; padding-bottom: 6px; width: 250px;" data-original-title="" title="">';
	
	$.messager.confirm("提示", msg, function(r) {
		if (r) {
	
			var opinion = $('#opinion').val(); //意见
	
			$.ajax({
				url : "checkSpecial",
				data : {
					"commentId" : commentId,
					"status" : _status,
					"opinion" : opinion
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					var obj = eval('(' + data + ')');
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
	});
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
		return;
	}
	var spId = $('#StoreGrid').datagrid('getSelected');
	if (spId == null) {
		$.messager.alert('提示消息', "请选择要回复的评论", 'info');
		$("#dlg").dialog("close");
		return;
	}
	
	$('#commentId').val(spId.commentId);
	$('#parentId').val(spId.commentId);
	$('#subMsgType').val(14);
	$('#party_base_info').form('submit', {
		url : 'addReplyComment',
		ajax : true,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			data = typeof data == 'object' ? data : $.parseJSON(data);

			if (data.result == "succ") {
				$.messager.alert('提示消息', data.msg, 'info');
				searchComment();
				$("#dlg").dialog("close");
			} else {
				$.messager.alert('提示消息', data.msg, 'error');
			}
		}
	});
}
