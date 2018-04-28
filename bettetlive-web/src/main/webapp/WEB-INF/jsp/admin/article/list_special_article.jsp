<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看美食推荐列表</title>
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
		  <table id="StoreGrid" style="width:100%;height:700px;" title="精选文章列表">
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb">
				             文章名称: <input class="easyui-textbox" type="text" name="articleTitle"  id="articleTitle" maxlength="30" style="width:166px;height:35px;line-height:35px;" />
				             文章类型: <input class="easyui-textbox" type="text" name="articleTypeName"  id="articleTypeName" maxlength="30" style="width:166px;height:35px;line-height:35px;" />
<!-- 				             创建时间: <input id="startTime" name="startTime"  class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>- -->
<!-- 						<input id="endTime" name="endTime"  class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/> -->
					 文章状态: <select id="status" name="status" style="height:35px;width:100px;"  >
				             	<option value="1">已发布</option>
				             	<option value="2">草稿箱</option>
				             	<option value="4">已失效</option>
				             </select>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchSpecialArticle()" >查询</a>
		      	</div>
		      	<div>
		      	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddSpecialArticle()">新增文章</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditSpecialArticle()">修改文章</a>
		           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn1"   onclick="toDelSpecialArticle(1)">发布文章</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn2"   onclick="toDelSpecialArticle(4)">下架文章</a>
		           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn3"   onclick="toDelSpecialArticle(0)">删除文章</a>
		      	</div>
	     </div>
      	<div id="tb" style="padding:0 30px;">
      	</div>
		<script type="text/javascript" src="${resourcepath}/admin/js/article/list_special_article.js"></script>
	</body>
</html>