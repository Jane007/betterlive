function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/menu/editMenu',
				onSubmit : function() {

					var menuName = $("#menuName").val(); // 菜单名称
					if (menuName == "" || menuName == null) {
						$("#menuName_msg").html("不能为空");
						$("#menuName").focus();
						return false;
					} else {
						$("#menuName_msg").html("");
					}

					var menuOrd = $("#menuOrd").val(); // 菜单展示顺序
					if (menuOrd == "" || menuOrd == null) {
						$("#menuOrd_msg").html("不能为空");
						$("#menuOrd").focus();
						return false;
					} else {
						$("#menuOrd_msg").html("");
					}
					var status = $("#status").val(); // 菜单状态
					if (status == "" || status == null) {
						$("#status_msg").html("不能为空");
						$("#status").focus();
						return false;
					} else {
						$("#status_msg").html("");
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
	window.location.href = mainServer + "/admin/menu/findList";
}