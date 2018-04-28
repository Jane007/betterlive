function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/role/editRole',
				onSubmit : function() {
					// 用户名称
					var roleName = $("#roleName").val();
					if (null == roleName || "" == roleName) {
						$("#roleName_msg").html("不能为空");
						$("#roleName").focus();
						return false;
					} else {
						$("#roleName_msg").html("");
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
	window.location.href = mainServer + "/admin/role/findList";
}