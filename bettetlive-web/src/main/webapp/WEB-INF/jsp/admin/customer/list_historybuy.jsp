<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>历史购买记录列表</title> 
	   <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
	
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	   		var customerId = '${customerId}';
		</script>
	</head>
	<body>
	      <table id="StoreGrid" style="width:100%;height:529px;" title="历史购买记录列表">
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
				            订单编号：<input class="easyui-textbox" type="text" name="orderCode"  id="orderCode" maxlength="11" style="width:200px;height:35px;line-height:35px;">
					 收货手机：<input class="easyui-textbox" type="text" name="mobile"  id="mobile" maxlength="20" style="width:160px;height:35px;line-height:35px;">
				   	下单时间段：<input class="easyui-datetimebox" type="text" name="start" id="start"  style="height:35px;margin:0 0;width:180px;"/> --&nbsp;
				   	<input class="easyui-datetimebox" type="text" name="end" id="end"  style="height:35px;margin:0 0;width:180px;"/><br/>
				   	订单状态：<select  name="orderStatusSearch" id="orderStatusSearch" style="width:100px;height:33px;line-height:30px;"  >
	            	<option value="" selected="selected">请选择</option>
	            	
			    	<option value="1">待付款</option>
					<option value="2">待发货</option>
					<option value="3">待签收</option>
					<option value="4">待评价</option>
					<option value="5">已完成</option>
					<option value="6">已取消</option>
					<option value="7">已退款</option>
					<option value="0">已删除</option>
	           </select>
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchOrder()" >查询</a>
				   	<a id="conExport" class="easyui-linkbutton" data-options="selected:true" onclick="exportExcel()">导出</a>
		      	</div>
	     </div>
   	
	<script type="text/javascript" src="${resourcepath}/admin/js/customer/list_historybuy.js"></script>
</body> 
</html>
