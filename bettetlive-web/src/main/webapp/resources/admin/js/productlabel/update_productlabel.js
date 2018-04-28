function gradeChange() {
	var options = $("#labelType option:selected"); // 获取选中的项
	var labelTitle = options.text();
	$("#labelTitle").val("");
	if (labelTitle == '自定义') {
		$("#labelTitle").val("");
	} else {
		$("#labelTitle").val(labelTitle);
	}
}

function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/productlabel/editproductlabel',
				onSubmit : function() {
					// 标签名称
					var specialName = $("#labelTitle").val();
					if (null == specialName || "" == specialName) {
						$("#Name_msg").html("不能为空");
						$("#labelTitle").focus();
						return false;
					} else {
						$("#Name_msg").html("");
					}

					var specialTitle = $("#labelType").val(); // 标签类型
					if (specialTitle == "" || specialTitle == null) {
						$("#Title_msg").html("不能为空");
						$("#labelType").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}

					var showStart = $("input[name=showStart]").val(); // 开始时间
					if ("" == showStart || showStart == null) {
						$("#Start_msg").html("不能为空");
						$("#showStart").focus();
						return false;
					} else {
						$("#Start_msg").html("");
					}
					var showEnd = $("input[name=showEnd]").val(); // 结束时间
					if ("" == showEnd || showEnd == null) {
						$("#End_msg").html("不能为空");
						$("#showEnd").focus();
						return false;
					} else {
						$("#End_msg").html("");
					}
					if (showStart > showEnd) {
						$("#Start_msg").html("开始时间不能大于结束时间");
						return false;
					}
				},
				success : function(data) {
					var obj = eval('(' + data + ')');
					$.messager.alert('提示消息',
							'<div style="position:relative;top:20px;">'
									+ obj.msg + '</div>', 'success');
					if ("succ" == obj.result) {
						toBack();
					}
				}
			});

}
function toBack() {
	window.location.href = mainServer + "/admin/productlabel/findList";
}