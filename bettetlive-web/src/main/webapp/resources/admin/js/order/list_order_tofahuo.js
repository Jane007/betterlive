$(function() {
	$('#detailDlg').dialog('open');
});
function closeForm() {
	$("#fahuoDlg").dialog('close');
}
function submitLogisticsForm() {

	var order_product_id = $("#order_product_id").val();
	var company_code = $("#company_code").val();
	var logistics_code = $("#logistics_code").val();

	$.ajax({
		url : mainServer + "/admin/order/saveLogisticsInfo",
		data : {
			order_product_id : order_product_id,
			company_code : company_code,
			logistics_code : logistics_code
		},
		datatype : "json",
		type : "post",
		success : function(data) {
			if (data.code == 1010) {
				$("#fahuoDlg").dialog('close');
				$.messager.alert("提示", "发货成功", "info");
			} else {
				$.messager.alert("提示", data.msg, "info");
			}
		}
	});
}

function toConfirmFahuo(obj) {
	var order_product_id = $(obj).attr("lang");
	$("#order_product_id").val(order_product_id);
	$("#fahuoDlg").dialog('open');

	// 加载物流信息
	queryLogisticsCompany(order_product_id);

}
// company_code
function queryLogisticsCompany(order_product_id) {
	$("#company_code").empty();
	$("#logistics_code").val("");
	$.ajax({
		url : mainServer + "/admin/order/queryLogisticsCompany",
		data : {
			order_product_id : order_product_id
		},
		datatype : "json",
		type : "post",
		success : function(data) {
			if (data.code == 1010) {
				$.each(data.data, function(i, e) {
					if (data.company_code == e.companyCode) {
						$("#company_code").append(
								'<option value="' + e.companyCode
										+ '" selected="selected">'
										+ e.companyName + '</option>');
						$("#logistics_code").val(data.logistics_code);
					} else {
						$("#company_code").append(
								'<option value="' + e.companyCode + '">'
										+ e.companyName + '</option>');
					}

				})

			}
		}
	});
}