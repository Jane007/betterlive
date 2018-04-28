function isSubmit() {
	var orderCode = $("#orderCode").val();
	if (null == orderCode || "" == orderCode) {
		$("#orderCode_msg").html("不能为空");
		$("#orderCode").focus();
		return;
	}
	
	$.ajax({
		url:mainServer + '/admin/order/querySingleOrder',
		data:{
			"orderCode":orderCode
		},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.result=='succ'){
				var rspData = data.resultQuery.rspData;
				var cardType = "";
				var orderStatus = "";
				if ("02" == rspData.cardType) {
					cardType ="一卡通";
				}else if ("03" == rspData.cardType) {
					cardType ="信用卡";
				}else if ("08" == rspData.cardType) {
					cardType ="他行储蓄卡";
				}else if ("09" == rspData.cardType) {
					cardType ="他行信用卡";
				}else{
					cardType = rspData.cardType;
				}
				
				if("0" == rspData.orderStatus){
					orderStatus = "已结帐";
				}else if("1" == rspData.orderStatus){
					orderStatus = "已撤销";
				}else if("2" == rspData.orderStatus){
					orderStatus = "部分结帐";
				}else if("4" == rspData.orderStatus){
					orderStatus = "未结帐";
				}else if("7" == rspData.orderStatus){
					orderStatus = "冻结交易,冻结金额已经全部结账";
				}else if("8" == rspData.orderStatus){
					orderStatus = "冻结交易，冻结金额只结帐了一部分";
				}
				
				var showHtml = '<table class="kv-table"><tbody><tr><td class="kv-label">处理结果:</td><td class="kv-content" colspan="3">'
					+rspData.rspCode+'</td></tr>'
					+'<tr><td class="kv-label">详细信息:</td><td class="kv-content" colspan="3">'
					+rspData.rspMsg+'</td>'
					+'<tr><td class="kv-label">银行返回时间:</td><td class="kv-content" colspan="3">'
					+rspData.dateTime+'</td>'
					+'<tr><td class="kv-label">订单日期:</td><td class="kv-content" colspan="3">'
					+rspData.date+'</td>'
					+'<tr><td class="kv-label">订单编号:</td><td class="kv-content" colspan="3">'
					+rspData.orderNo+'</td>'
					+'<tr><td class="kv-label">银行订单流水号:</td><td class="kv-content" colspan="3">'
					+rspData.bankSerialNo+'</td>'
					+'<tr><td class="kv-label">订单金额:</td><td class="kv-content" colspan="3">'
					+rspData.orderAmount+'</td>'
					+'<tr><td class="kv-label">费用金额:</td><td class="kv-content" colspan="3">'
					+rspData.fee+'</td>'
					+'<tr><td class="kv-label">银行受理日期:</td><td class="kv-content" colspan="3">'
					+rspData.bankDate+'</td>'
					+'<tr><td class="kv-label">银行受理时间:</td><td class="kv-content" colspan="3">'
					+rspData.bankTime+'</td>'
					+'<tr><td class="kv-label">结算金额:</td><td class="kv-content" colspan="3">'
					+rspData.settleAmount+'</td>'
					+'<tr><td class="kv-label">优惠金额:</td><td class="kv-content" colspan="3">'
					+rspData.discountAmount+'</td>'
					+'<tr><td class="kv-label">订单状态:</td><td class="kv-content" colspan="3">'
					+orderStatus+'</td>'
					+'<tr><td class="kv-label">银行处理日期:</td><td class="kv-content" colspan="3">'
					+rspData.settleDate+'</td>'
					+'<tr><td class="kv-label">银行处理时间:</td><td class="kv-content" colspan="3">'
					+rspData.settleTime+'</td>'
					+'<tr><td class="kv-label">卡类型:</td><td class="kv-content" colspan="3">'
					+cardType+'</td>'
					+'</tr></tbody></table>';
				$("#resultJson").html(showHtml);
			}else if(data.result=='fail'){
				alert(data.resultQuery);
			}
		},
		failure:function(data){
			showvaguealert('出错了');
		}
	});

}

function toClose() {
	var centerTabs = parent.centerTabs;
	centerTabs.tabs('close', '一卡通单笔订单查询');
}