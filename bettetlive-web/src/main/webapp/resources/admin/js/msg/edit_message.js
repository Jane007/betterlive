var boxDataGrid;
$(function() {
	boxDataGrid = ActivityGrid.createGrid('activityGrid');
});

function searchSpecial() {
	$('#activityGrid').datagrid('load', {
		"specialType" : $("#specialType").val(),
		"specialName" : $("#specialName").val()
	});
}

var ActivityGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/special/querySpecialAllJson',
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
				title : "专题名称",
				field : "specialName",
				width : 100
			}, {
				title : "专题封面",
				field : "specialCover",
				width : 120,
				height : 80,
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src=' + value + ' style="height:80px;"/>';
				}
			}, {
				title : "专题标题",
				field : "specialTitle",
				width : 80
			}, {
				title : "专题介绍",
				field : "specialIntroduce",
				width : 100
			}, {
				title : "专题页面路径",
				field : "specialPage",
				width : 80

			}, {
				title : "专题开始时间",
				field : "startTime",
				width : 80
			}, {
				title : "专题结束时间",
				field : "endTime",
				width : 80
			}, {
				title : "专题创建时间",
				field : "createTime",
				width : 80
			} ] ],
			toolbar : '#storeToolbar',
			onBeforeLoad : function(param) {
				$('#activityGrid').datagrid('clearSelections');
			},
			onLoadSuccess : function(data) {
			},
			onSelect : function(rowIndex, rowData) {
				var row = $("#activityGrid").datagrid("getSelected");
				$("#objId").val(row.specialId);
				$("#msgTitle").textbox('setValue', row.specialTitle);
				;
				$("#msgDetail").val(row.specialIntroduce);
				$("#actdlg").dialog("close");
			},
			onUnselect : function(rowIndex, rowData) {
			}
		});
	}
};

function changeMsgType(value) {
	if (value == 0) { // 系统消息
		$("#modifySubMsgType").find("option").remove();
		$("#modifySubMsgType")
				.append(
						'<option value="0" selected="selected">系统公告</option><option value="8">物流公告</option>');
		changeSubMsgType(0);
	} else if (value == 1) { // 挥货活动
		$("#modifySubMsgType").find("option").remove();
		$("#modifySubMsgType").append(
				'<option value="10" selected="selected">专题活动</option>');
		changeSubMsgType(10);
	} else if (value == 3) { // 交易消息
		$("#modifySubMsgType").find("option").remove();
		$("#modifySubMsgType").append(
				'<option value="7" selected="selected">订单已退款</option>');
		changeSubMsgType(7);
	}
}

function changeSubMsgType(value) {
	$("#choiceActShow").hide();
	$("#objId").val("");
	$("input:radio[name='sendCrowd']").eq(0).prop('checked', 'checked');
	$("[name=sendCrowd]:radio").attr("disabled", false);
	$("#objName").textbox('setValue', "");
	choiceCust(1);
	if (value == 0) {
		$("#msgTitle").textbox('setValue', "系统公告");
	} else if (value == 7) {
		$("#msgTitle").textbox('setValue', "订单已退款");
		$("input:radio[name='sendCrowd']").eq(1).prop('checked', 'checked');
		$("[name=sendCrowd]:radio").attr("disabled", "disabled");
		choiceCust(2);
	} else if (value == 8) {
		$("#msgTitle").textbox('setValue', "物流公告");
	} else if (value == 10) {
		$("#choiceActShow").show();
		$("#msgTitle").textbox('setValue', "活动公告");
	}
}

function choiceCust(value) {
	var subMsgType = $("#modifySubMsgType").val();
	if (value == 2) { // 指定用户
		if (subMsgType == 7) { // 订单退款
			$("#objShow").show();
			$("#objNameShow").html("订单编号");
		} else if (subMsgType == 8) {// 物流公告
			$("#objShow").show();
			$("#objNameShow").html("物流单号");
		} else {
			$("#objShow").show();
			$("#objNameShow").html("用户手机号");
		}
	} else {
		$("#objShow").hide();
		// $("#objNameShow").html("用户手机号");
	}
}

function choiceActivity() {
	$("#actdlg").dialog("open");
	$('#activityGrid').datagrid('load', {
		"specialType" : $("#specialType").val(),
		"specialName" : $("#specialName").val()
	});
}

function submitForm() {
	$('#messageSendForm').form(
			'submit',
			{
				url : mainServer + '/admin/message/saveSend',
				ajax : true,
				onSubmit : function() {
					var subMsgType = $("#modifySubMsgType").val();
					var objid = $("#objId").val();
					if (subMsgType == 10
							&& (objid == null || objid == "" || isNaN(objid))) {
						alert("请选择活动");
						return false;
					}

					var sendCrowd = $('input[name="sendCrowd"]:checked').val();
					if (sendCrowd == 2) {
						var objName = $("#objName").textbox('getValue');
						if (objName == null || objName == ""
								|| objName.length <= 0) {
							if (subMsgType == 7) {
								alert("请输入订单编号");
								return false;
							} else if (subMsgType == 8) {
								alert("请输入物流单号");
								return false;
							} else {
								alert("请输入用户手机号");
								return false;
							}
						}
					}

					var msgTitle = $("#msgTitle").val();
					if (msgTitle == null || msgTitle == "") {
						alert("请输入标题");
						return false;
					}

					var msgDetail = $("#msgDetail").val();
					if (msgDetail == null || msgDetail == "") {
						alert("请输入消息内容");
						return false;
					}
					$("[name=sendCrowd]:radio").attr("disabled", false);
				},
				success : function(data) {
					var resultData = eval('(' + data + ')');
					if (resultData.flag == 1) {
						$.messager.alert('提示消息', resultData.msg, 'info');
						window.location.href = mainServer
								+ "/admin/message/list";
					} else {
						$.messager.alert('提示消息', resultData.msg, 'error');
					}
				}
			});
}
