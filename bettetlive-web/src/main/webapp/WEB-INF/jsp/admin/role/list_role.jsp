<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>角色管理</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
		<link href="${resourcepath}/admin/css/zTree/zTreeStyle.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
    	
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/ajaxfileupload.js"></script>
    	
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zTree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zTree/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript">
			var mainServer = '${mainserver}';
	    </script>
	</head>
	<body>
		  <table id="StoreGrid" style="width:100%;height:529px;" title="角色管理">
	        
	      </table>
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
	      			角色名称: <input class="easyui-textbox" type="text" name="roleName"  id="roleName" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
								    
				      
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchRole()" >查询</a>
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddRole()">增加角色</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditRole()">修改角色</a>
				       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"  enctype ="multipart/form-data"  onclick="toDelRole()">删除角色</a>	          
	     </div>
	     
	    
				<div id="dlg" class="easyui-dialog" title="菜单列表" data-options="closed:true"  fit=“true”  style="width:620px;height:420px;padding:10px;">
					<input type="hidden" id="roleIdTree" name="roleIdTree" >
					<table style="width: 100%;">
						<tr style="COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
							<td>
								<font size="2">
									角色名称：<span style="color:black;" id="roleName_msg"></span>&nbsp;
								</font>
							</td>
						</tr>
						<tr style="COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
							<td>
								<input class="button blue" id="openTree" type="button" value="展&nbsp;&nbsp;开"/>
								<input class="button blue" id="closeTree" type="button" value="折&nbsp;&nbsp;叠"/>
							</td>
						</tr>
					</table>
					<div style="overflow:auto;width:100%;height: 87%;">
						<ul id="mtree" class="ztree" style="width:97%;height: 95%;"></ul>
					</div>
					<input class="button blue" id="isOk" onclick="onCheck();" type="button" value="确&nbsp;&nbsp;定"/>
					<input class="button blue" id="closeDiv" type="button" onclick="javascript:$('#dlg').dialog('close');" value="取&nbsp;&nbsp;消"/>
				</div>
	     
      	<div id="tb" style="padding:0 30px;">
      	</div>

    	<script type="text/javascript" src="${resourcepath}/admin/js/role/list_role.js"></script>
	</body>
</html>