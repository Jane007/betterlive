<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>管理员管理</title>
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
		  <table id="StoreGrid" style="width:100%;height:529px;" title="管理员管理">
	        
	      </table>
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
	      			用户名称: <input class="easyui-textbox" type="text" name="username"  id="username" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
								    
				    <span class="con-span">创建时间：</span> 
				    <input id="startTime" name="startTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="height: 30px; margin: 0 0; width: 180px;" />- 
				    <input id="endTime" name="endTime" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="height: 30px; margin: 0 0; width: 180px;" />        
				      
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchAdmin()" >查询</a>
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddAdmin()">增加管理员</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditAdmin()">修改管理员</a>
				       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"  enctype ="multipart/form-data"  onclick="toDelAdmin()">删除管理员</a>	          
	     </div>
	     
	        
      	<div id="tb" style="padding:0 30px;">
      	</div>
		<input type="hidden" name="labelId" id="labelId" value=""/>
			
		<script type="text/javascript" src="${resourcepath}/admin/js/admin/list_admin.js"></script>
	</body>
</html>