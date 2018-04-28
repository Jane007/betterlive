<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>挥货平台商品销量</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
	    </script>
	</head>
	
	<body>
		
	    
		<table id="dataGrid" style="width:100%;height:529px;" title="挥货平台商品销量"></table>
     
     	<div id="dataToolbar" style="padding:0 30px;">
		   <div>
			   <span class="con-span">商品名称: </span>
	           <input class="easyui-textbox" type="text" name="productName" id="productName" style="height:30px;width:140px;line-height:30px;"/>
	           
	           <span class="con-span">日期：</span>
			   <input id="startTime" name="startTime"  class="easyui-datebox" style="height:30px;margin:0 0;width:180px;"/>-
			   <input id="endTime" name="endTime"  class="easyui-datebox" style="height:30px;margin:0 0;width:180px;"/>
			   
			   <span>
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="queryList()">查询</a>
						<a id="conExport" class="easyui-linkbutton" data-options="selected:true" onclick="exportExcel()">导出</a>
			   </span>
	   		</div>
     </div>

  
    <script type="text/javascript" src="${resourcepath}/admin/js/dataanaly/hh_productsales_statistics.js"></script>
	</body>
</html>