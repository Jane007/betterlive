<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>会员用户记录列表</title> 
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
		</script>
		
	</head>
	<body>
	      <table id="StoreGrid" style="width:100%;height:529px;" title="会员用户记录列表">
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
				              用户手机：<input class="easyui-textbox" type="text" name="customerMobile"  id="customerMobile" maxlength="11" style="width:130px;height:35px;line-height:35px;">
					 用户昵称：<input class="easyui-textbox" type="text" name="customerName"  id="customerName" maxlength="20" style="width:100px;height:35px;line-height:35px;">
				     
				      注册时间范围：
				       <input class="easyui-datetimebox" type="text" name="start" id="start"  style="height:30px;margin:0 0;width:180px;"/>--
			   		   <input class="easyui-datetimebox" type="text" name="end" id="end"  style="height:30px;margin:0 0;width:180px;"/>
				       是否绑定手机号码：
	           <select name="isBinding" id="isBinding" style="width:100px;height:30px;line-height:30px;">
	            	<option value="" selected="selected">请选择</option>
			    	<option value="1">是</option>
			    	<option value="0">否</option>
	           </select>
				<br/>
			用户来源: 
	           <input class="easyui-textbox" type="text" name="orderSource"  id="orderSource" maxlength="20" style="width:200px;height:35px;line-height:35px;">
			 是否下单：
	           <select name="existsOrder" id="existsOrder" style="width:100px;height:32px;line-height:30px;">
	            	<option value="" selected="selected">请选择</option>
			    	<option value="1">是</option>
			    	<option value="2">否</option>
	           </select>
			
		
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProductType()" >查询</a>
				   <a id="conExport" class="easyui-linkbutton" data-options="selected:true" onclick="exportExcel()">导出</a>
				     
		      	</div>
		       <div class="vaguealert">
			<p style="color: red"></p>
	     </div>
	<script type="text/javascript" src="${resourcepath}/admin/js/customer/list_customer.js"></script>
	
</body> 
</html>
