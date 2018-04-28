function isSubmit() {
	$('#postForm').form('submit', {
		url : mainServer + '/admin/articleperiodical/addArticlePeriodical',
		onSubmit : function() {

			var periodical = $("#periodical").val(); // 期数
			if (periodical == "" || periodical == null) {
				$("#periodical_msg").html("不能为空");
				$("#periodical").focus();
				return false;
			} else {
				$("#periodical_msg").html("");
			}

			var periodicalTitle = $("#periodicalTitle").val(); // 专题文章期刊主题
			if (periodicalTitle == "" || periodicalTitle == null) {
				$("#title_msg").html("不能为空");
				$("#periodicalTitle").focus();
				return false;
			} else {
				$("#title_msg").html("");
			}

		},
		success : function(data) {
			var obj = eval('(' + data + ')');
			if ("1010" == obj.code) {
				toBack();
			} else {
				$.messager.alert('提示', obj.msg, 'error');
			}
		}
	});
}

function toBack() {
	window.location.href = mainServer + "/admin/articleperiodical/findList";
}