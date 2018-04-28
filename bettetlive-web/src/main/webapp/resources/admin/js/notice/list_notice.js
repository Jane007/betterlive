var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchProductType() {
	$('#StoreGrid').datagrid('load', {
		"status" : $("#statusSearch").val(),
		"noticeTitle" : $("#noticeTitleStr").textbox('getValue')
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/notice/queryNoticeAllJson',
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
				field : "noticeId",
				checkbox : true,
				width : '5%'
			}, {
				title : "公告标题",
				field : "noticeTitle",
				width : '15%'
			}, {
				title : "公告内容",
				field : "noticeContent",
				width : '18%'
			}, {
				title : "公告链接地址",
				field : "noticeUrl",
				width : '40%'
			}, {
				title : "公告类型",
				field : "noticeType",
				width : '10%',
				formatter : function(value, row) {

					// 公告类型，1：系統文章id，2：预购id，3：专题活动id，4：产品id
					if (value == 1) {
						return "系统文章";
					} else if (value == 2) {
						return "预购产品";
					} else if (value == 3) {
						return "专题活动";
					} else if (value == 4) {
						return "产品";
					} else {
						return "";
					}
				}
			}, {
				title : "状态",
				field : "status",
				width : '8%',
				formatter : function(value, row) {
					// 状态：1：正常，:0：禁用
					if (value == 1) {
						return "正常";
					} else {
						return "禁用";
					}
				}
			}, {
				title : "创建时间",
				field : "createTime",
				width : '8%'
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

function delConfDlg() {
	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			var noticeId = boxDataGrid.datagrid("getSelected").noticeId;
			$.ajax({
				url : mainServer + "/admin/notice/removeNotice",
				data : {
					"noticeId" : noticeId
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					var obj = data;
					console.log(data);

					if ("1010" == obj.code) {
						searchProductType();
					} else {
						$.messager.alert('提示', obj.msg, 'info');
					}
				}

			});
		}
	});
}

function updConfDlg() {
	$('#dlg').dialog({
		title : '修改公告'
	});
	var noticeId = boxDataGrid.datagrid('getSelected').noticeId;
	if (noticeId == '' || null == noticeId) {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}

	$.ajax({
		type : "POST",
		url : mainServer + "/admin/notice/queryNoticeJson",
		data : {
			'noticeId' : noticeId
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			var noticeInfo = data.data;
			if (1010 == data.code) {
				$('#noticeId').val(noticeInfo.noticeId);
				// var noticeTitle=

				$("#noticeTitle").textbox("setValue", noticeInfo.noticeTitle);
				// var bannerUrl=
				$("#noticeUrl").textbox("setValue", noticeInfo.noticeUrl);
				// $("#noticeUrl_h").val(noticeInfo.noticeUrl);

				$('#noticeType').val(noticeInfo.noticeType);
				$('#status').val(noticeInfo.status);

				$("#startTime").datebox("setValue", noticeInfo.startTime);
				$("#endTime").datebox("setValue", noticeInfo.endTime);
				$("#noticeContent").val(noticeInfo.noticeContent);

				$("#add_Ptype").hide();
				$("#edit_Ptype").show();
				$('#dlg').dialog('open');

				var noticeType = noticeInfo.noticeType;
				buffer = new StringBuffer();
				getObjectId(noticeType, buffer);

			} else {
				$.messager.alert('提示', data.msg, 'info');
			}

		}
	});
}

function closeProductType() {
	// $('#noticeTitle').textbox('setValue', '');
	$('#dlg').form('clear');
	$('#dlg').dialog('close');
}

function addProductType() {
	// noticeTitle noticeType objectId status noticeContent
	var noticeTitle = $("#noticeTitle").val();
	var noticeUrl = $("#noticeUrl").val();
	var noticeContent = $("#noticeContent").val();
	var noticeType = $("#noticeType").val();
	var status = $("#status").val();
	var startTime = $("#startTime").datebox('getValue');
	var endTime = $("#endTime").datebox('getValue');

	if (noticeTitle == '') {
		$.messager.alert('提示', "公告标题不能为空", 'info');
		return;
	}

	if (noticeType == '' || noticeType == 'null' || noticeType == null) {
		$.messager.alert('提示', "请选择公告类型", 'info');
		return;
	}
	if (noticeUrl == '') {
		$.messager.alert('提示', "公告链接地址不能为空", 'info');
		return;
	}

	if (startTime == '') {
		$.messager.alert('提示', "开始时间不能为空", 'info');
		return;
	}

	if (endTime == '') {
		$.messager.alert('提示', "结束时间不能为空", 'info');
		return;
	}
	startTime = startTime.replace(/-/g, "");
	endTime = endTime.replace(/-/g, "");
	if (endTime <= startTime) {
		$.messager.alert('提示', "结束时间不能小于或等于开始时间", 'info');
		return;
	}

	if (status == '' || status == 'null' || status == null) {
		$.messager.alert('提示', "请选择公告状态", 'info');
		return;
	}
	if (noticeContent == '') {
		$.messager.alert('提示', "公告内容不能为空", 'info');
		return;
	}

	$('#form1').form('submit', {
		url : mainServer + '/admin/notice/addNotice',
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close');
			}

			return isValid;
		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			console.log(data);

			if ("1010" == obj.code) {
				closeProductType();
				searchProductType();
			} else {
				$.messager.alert('提示', "新增失败", 'info');
			}
		}
	});
}

function editProductType() {
	var noticeId = $("#noticeId").val();
	var noticeTitle = $("#noticeTitle").val();
	var noticeUrl = $("#noticeUrl").val();
	var noticeContent = $("#noticeContent").val();
	var noticeType = $("#noticeType").val();
	var status = $("#status").val();
	var startTime = $("#startTime").datebox('getValue');
	var endTime = $("#endTime").datebox('getValue');

	if (noticeId == '') {
		$.messager.alert('提示', "缺失重要参数", 'info');
		return;
	}

	if (noticeTitle == '') {
		$.messager.alert('提示', "公告标题不能为空", 'info');
		return;
	}

	if (noticeType == '' || noticeType == 'null' || noticeType == null) {
		$.messager.alert('提示', "请选择公告类型", 'info');
		return;
	}
	if (noticeUrl == '') {
		$.messager.alert('提示', "公告链接地址不能为空", 'info');
		return;
	}

	if (startTime == '') {
		$.messager.alert('提示', "开始时间不能为空", 'info');
		return;
	}

	if (endTime == '') {
		$.messager.alert('提示', "结束时间不能为空", 'info');
		return;
	}
	startTime = startTime.replace(/-/g, "");
	endTime = endTime.replace(/-/g, "");
	if (endTime <= startTime) {
		$.messager.alert('提示', "结束时间不能小于或等于开始时间", 'info');
		return;
	}

	if (status == '' || status == 'null' || status == null) {
		$.messager.alert('提示', "请选择公告状态", 'info');
		return;
	}
	if (noticeContent == '') {
		$.messager.alert('提示', "公告内容不能为空", 'info');
		return;
	}

	$('#form1').form('submit', {
		url : mainServer + '/admin/notice/addNotice',
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close');
			}
			return isValid;
		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			console.log(data);

			if ("1010" == obj.code) {
				closeProductType();
				searchProductType();
			} else {
				$.messager.alert('提示', "修改失败", 'info');
			}
		}
	});
}

/*
 * var noticeUrl = ""; $("#noticeType").change(function(){ var noticeType =
 * $(this).val(); buffer = new StringBuffer(); getObjectId(noticeType,buffer);
 * });
 */

/*
 * function getObjectId(noticeType,buffer){
 * 
 * $.ajax({ type: "POST", url: mainServer+"/admin/notice/noticeTypeChange",
 * data: {'noticeType':noticeType}, dataType: 'json', async: false, success:
 * function(data) { var list = data.list;
 * 
 * $.each(list,function(i,obj){ if(noticeType==2){//预售id buffer.append('<option
 * value="'+obj.preId+'">'+obj.preName+'</option>'); }else
 * if(noticeType==3){//专题活动id buffer.append('<option
 * value="'+obj.specialPage+'_'+obj.specialId+'">'+obj.specialName+'</option>');
 * }else if(noticeType==4){//4：产品id buffer.append('<option
 * value="'+obj.product_id+'">'+obj.product_name+'</option>'); } });
 * 
 * $("#objectId").html(buffer.toString());
 * 
 * var objectId = $("#objectId").val(); noticeUrl = data.noticeUrl; var url =
 * mainServer+"/"+data.noticeUrl+objectId; if(noticeType==3){//专题活动有自己的页面
 * console.log(objectId+"--objectId"); noticeUrl=objectId.split("_")[0];
 * $("#noticeUrl").textbox("setValue",objectId.split("_")[0]);
 * $("#noticeUrl_h").val(objectId.split("_")[0]);
 * $("#objectId_h").val(objectId.split("_")[1]); }else{
 * $("#noticeUrl").textbox("setValue",url); $("#noticeUrl_h").val(url);
 * $("#objectId_h").val(objectId); }
 *  } }); }
 */

/*
 * $("#objectId").change(function(){ var objectId = $(this).val(); var url =
 * mainServer+"/"+noticeUrl+objectId if(bannerType==3){
 * $("#noticeUrl").textbox("setValue",objectId.split("_")[0]);
 * $("#noticeUrl_h").val(objectId.split("_")[0]);
 * $("#objectId_h").val(objectId.split("_")[1]); }else{
 * $("#noticeUrl").textbox("setValue",url); $("#noticeUrl_h").val(url);
 * $("#objectId_h").val(objectId); }
 * 
 * 
 * });
 */
function addOpenDlg() {
	$('#dlg').form('clear');
	$("#add_Ptype").show();
	$("#edit_Ptype").hide();
	$('#dlg').dialog('open');
}

function toBack() {
	window.location.href = mainServer + "/admin/notice/findList";
}