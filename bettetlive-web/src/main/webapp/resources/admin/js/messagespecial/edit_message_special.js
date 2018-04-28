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

function choiceCust(value) {
	if (value == 2) { // 指定用户
		$("#objShow").show();
		$("#objNameShow").html("用户手机号");
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
				url : mainServer + '/admin/specialmessage/saveSpecialMessage',
				ajax : true,
				onSubmit : function() {
					var objid = $("#objId").val();
					if (objid == null || objid == "" || isNaN(objid)) {
						alert("请选择活动");
						return false;
					}

					var sendCrowd = $('input[name="sendCrowd"]:checked').val();
					if (sendCrowd == 2) {
						var objName = $("#objName").textbox('getValue');
						if (objName == null || objName == ""
								|| objName.length <= 0) {
							alert("请输入用户手机号");
							return false;
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
								+ "/admin/specialmessage/findlist";
					} else {
						$.messager.alert('提示消息', resultData.msg, 'error');
					}
				}
			});
}