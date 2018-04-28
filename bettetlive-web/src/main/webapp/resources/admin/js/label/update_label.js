function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/label/editlabel',
				onSubmit : function() {
					// 标签名称
					var specialName = $("#labelName").val();
					if (null == specialName || "" == specialName) {
						$("#Name_msg").html("不能为空");
						$("#labelName").focus();
						return false;
					} else {
						$("#Name_msg").html("");
					}

					var specialType = $("#labelType").val(); // 标签类型
					if (specialType == "" || specialType == null) {
						$("#Title_msg").html("不能为空");
						$("#labelType").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}
					// 标签排序
					var specialSort = $("#labelSort").val();
					if (null == specialSort || "" == specialSort) {
						$("#Name_msg").html("不能为空");
						$("#labelSort").focus();
						return false;
					} else {
						$("#Name_msg").html("");
					}

					var specialStatus = $("#status").val(); // 标签状态
					if (specialStatus == "" || specialStatus == null) {
						$("#Title_msg").html("不能为空");
						$("#status").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}

				},
				success : function(data) {
					var obj = eval('(' + data + ')');
					$.messager.alert('提示消息',
							'<div style="position:relative;top:20px;">'
									+ obj.msg + '</div>', 'success');
					if ("succ" == obj.result) {
						setTimeout("toBack()", 2000);
					}
				}
			});

}
function toBack() {
	window.location.href = mainServer + "/admin/label/findList";
}