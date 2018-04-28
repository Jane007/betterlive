$(function() {

	if (labelType != null) {
		$("#labelType").val(labelType);
	}

	if (labelType != null) {
		$("#status").val(status);
	}

	if (homeFlag != null) {
		$("#homeFlag").val(homeFlag);
	}
});

function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/postage/editPostage',
				onSubmit : function() {
					// 运费名称
					var postageName = $("#postageName").val();
					if (null == postageName || "" == postageName) {
						$("#postageName_msg").html("不能为空");
						$("#postageName").focus();
						return false;
					} else {
						$("#postageName_msg").html("");
					}

					// 运费类型
					var postageType = $("#postageType").val();
					if (null == postageType || "" == postageType) {
						$("#postageType_msg").html("不能为空");
						$("#postageType").focus();
						return false;
					} else {
						$("#postageType_msg").html("");
					}

					// 包邮条件
					var meetConditions = $("#meetConditions").val();
					if (null == meetConditions || "" == meetConditions) {
						$("#meetConditions_msg").html("不能为空");
						$("#meetConditions").focus();
						return false;
					} else if (postageType == "1") {
						if (!(/(^[1-9]\d*$)/.test(meetConditions))) {
							$("#meetConditions_msg").html("运费类型为价格类,请输入正整数");
							$("#meetConditions").focus();
							return false;
						} else {
							$("#meetConditions_msg").html("");
						}
					} else {
						$("#meetConditions_msg").html("");
					}
					// 运费价格
					var postage = $("#postage").val();
					if (null == postage || "" == postage) {
						$("#postage_msg").html("不能为空");
						$("#postage").focus();
						return false;
					} else if (postageType == "1") {
						if (!(/(^[1-9]\d*$)/.test(postage))) {
							$("#postage_msg").html("运费类型为价格类,请输入正整数");
							$("#postage").focus();
							return false;
						} else {
							$("#postage_msg").html("");
						}
					} else {
						$("#postage_msg").html("");
					}
					var postageMsg = $("#postageMsg").val();
					if (null == postageMsg || "" == postageMsg) {
						$("#postageMsg_msg").html("不能为空");
						$("postageMsg").focus();
						return false;
					}  else if(postageMsg.length > 20){
						$("#postageMsg_msg").html("不能超过20个字");
						$("postageMsg").focus();
						return false;
					} else {
						$("#postageMsg_msg").html("");
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
	var centerTabs = parent.centerTabs;
	centerTabs.tabs('close', '修改运费信息');
}