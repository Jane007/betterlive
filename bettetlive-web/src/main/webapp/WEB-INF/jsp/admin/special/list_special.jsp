<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看专题列表</title>
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
		  <table id="StoreGrid" style="width:100%;height:700px;" title="美食推荐列表">
	      </table>
	      
	      <div id="storeToolbar">
	      		<div id="tb">
				             专题名称: <input class="easyui-textbox" type="text" name="specialName"  id="specialName" maxlength="30" style="width:166px;height:35px;line-height:35px;" />
				             创建时间: <input id="startTime" name="startTime"  class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:166px;height:35px;line-height:35px;"/>-
						<input id="endTime" name="endTime"  class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:166px;height:35px;line-height:35px;"/>
					 专题状态: <select id="status" name="specialType" style="height:35px;width:100px;"  >
				             	<option value="1">上架</option>
				             	<option value="2">下架</option>
				             </select>  
		             <input type="hidden" id="specialType" name="specialType" value="1" readonly="readonly"> 
	                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchSpecial()" >查询</a>
		      	</div>
		      	<div>
			       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddSpecial()">增加专题</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditSpecial()">修改专题</a>
		           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn1"   onclick="toDelSpecial(1)">上架专题</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn2"   onclick="toDelSpecial(2)">下架专题</a>
		           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn3"   onclick="toDelSpecial(0)">删除专题</a>
		      	</div>
	     </div>
      	<div id="tb" style="padding:0 30px;">
      	</div>
	
    	<script type="text/javascript" src="${resourcepath}/admin/js/special/list_special.js"></script>
	</body>
</html>