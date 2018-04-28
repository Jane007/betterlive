<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看商品列表</title>
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
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
		<script type="text/javascript">
			var mainServer = '${mainserver}';
    	</script>
	</head>
<body>
	 <table id="PromotionGrid" style="width:100%;height:529px;" title="促销活动列表">
	 </table>
	 
	 <div id="storeToolbar" style="padding:0 10px;">
		 	<div id="tb" style="padding:0 10px;">
					            促销活动名称: <input class="easyui-textbox" type="text" name="promotionName"  id="promotionName" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
					             
					     活动时间段：      
					   	<input class="easyui-datetimebox" type="text" name="startTime" id="startTime"  style="height:30px;margin:0 0;width:180px;"/>--
			   			<input class="easyui-datetimebox" type="text" name="endTime" id="endTime"  style="height:30px;margin:0 0;width:180px;"/>
					  
					                    
					       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="findPromotion()" >查询</a>
					       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddPromotion()">增加活动</a>
					       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditPromotion()">修改活动</a>
					       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"   onclick="toDelPromotion()">删除活动</a>
			</div>
			<div class="vaguealert">
			<p style="color: red"></p>
		</div>
	 </div>
	
	<script type="text/javascript" src="${resourcepath}/admin/js/promotion/list_promotion.js"></script>
</body>
</html>