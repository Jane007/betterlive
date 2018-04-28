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
		  <table id="StoreGrid" style="width:100%;height:529px;" title="商品列表">
	        
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
				             商品名称: <input class="easyui-textbox" type="text" name="product_name"  id="product_name" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
				             
				             商品状态: <select id="product_status" name="product_status" style="height:35px;width:110px;margin-right:10px"  >
				             	<option value="" selected="selected">请选择</option>
				             	<option value="1">上架</option>
				             	<option value="2">下架</option>
				             	<option value="3">已删除</option>
				             </select>
				   展示类型：<select id="extensionType" name="extensionType" style="height:35px;width:110px;margin-right:10px">
				    			<option value="" selected="selected">请选择</option>
				    			<option value="1">每周新品</option>
				    			<option value="2">人气推荐</option>
				    	</select>
				   首页展示：<select id="isHomePage" name="is_HomePage" style="height:35px;width:110px;margin-right:10px">
				    			<option value="" selected="selected">请选择</option>
				    			<option value="1">显示</option>
				    			<option value="0">不显示</option>
				    	</select>
	    		   是否线上：<select id="isOnline" name="isOnline" style="height:35px;width:110px;margin-right:10px">
				    			<option value="" selected="selected">请选择</option>
				    			<option value="1">线上</option>
				    			<option value="2">线下</option>
				    	</select>
				                    
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProduct()" >查询</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddProduct()">增加商品</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditProduct()">修改商品</a>
				       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"   onclick="toDelProduct()">删除商品</a>
		      	</div>
	     </div>
	     
	        
      	<div id="tb" style="padding:0 30px;">
		       
      	</div>
		
		<script type="text/javascript" src="${resourcepath}/admin/js/product/list_product.js"></script>
	</body>
</html>