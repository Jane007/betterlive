function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/role/addRole',
				onSubmit : function() {

					// 角色名称
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
						setTimeout("toClose();", 2000);
					}
				}
			});

}
function toClose() {
	var centerTabs = parent.centerTabs;
	centerTabs.tabs('close', '添加角色');
}