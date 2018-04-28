function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainserver + '/admin/admin/editAdmin',
				onSubmit : function() {
					// 用户名称
					var username = $("#username").val();
					if (null == username || "" == username) {
						$("#username_msg").html("不能为空");
						$("#username").focus();
						return false;
					} else {
						$("#username_msg").html("");
					}

					var loginname = $("#loginname").val(); // 登录账号
					if (loginname == "" || loginname == null) {
						$("#loginname_msg").html("不能为空");
						$("#loginname").focus();
						return false;
					} else {
						$("#loginname_msg").html("");
					}

					var passwo = $("#passwo").val(); // 登录密码
					if (passwo == "" || passwo == null) {
						$("#password_msg").html("");
					} else {
						$("#password_msg").html("");
						$("#password").val($.md5(passwo));
					}
					var roleId = $("#roleId").val(); // 分配角色
					if (roleId == "" || roleId == null) {
						$("#roleId_msg").html("不能为空");
						$("#roleId").focus();
						return false;
					} else {
						$("#roleId_msg").html("");
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
	window.location.href = mainserver + "/admin/admin/findList";
}