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
		  <table id="StoreGrid" style="width:100%;height:700px;" title="文章分类列表">
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb">
				             文章分类名称: <input class="easyui-textbox" type="text" name="typeName"  id="typeName" maxlength="30" style="width:166px;height:35px;line-height:35px;" />
					 文章状态: <select id="status" name="status" style="height:35px;width:100px;"  >
					 			<option value="-1">全部</option>
				             	<option value="1">正常</option>
				             	<option value="0">失效</option>
				             </select>  
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchArticleType()" >查询</a>
		      	</div>
		      	<div>
		      	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddArticleType()">增加文章分类</a>
			       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditArticleType()">修改文章分类</a>
		           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn1"   onclick="toDelArticleType(1)">上架文章类型</a>
		           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn2"   onclick="toDelArticleType(0)">删除文章类型</a>
		      	</div>
	     </div>
      	<div id="tb" style="padding:0 30px;">
      	</div>
		
		<script type="text/javascript" src="${resourcepath}/admin/js/articletype/list_article_type.js"></script>
	</body>
</html>