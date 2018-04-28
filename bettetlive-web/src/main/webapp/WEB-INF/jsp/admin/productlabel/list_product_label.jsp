<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>商品标签</title>
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
		  <table id="StoreGrid" style="width:100%;height:529px;" title="商品标签">
	        
	      </table>
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
	      			标签名称: <input class="easyui-textbox" type="text" name="labelTitle"  id="labelTitle" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
				             商品名称: <input class="easyui-textbox" type="text" name="product_name"  id="product_name" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
				            商品编号: <input class="easyui-textbox" type="text" name="product_name"  id="product_code" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
				   <!--  <input id="d12" type="text"/> -->
					<!-- <img onclick="WdatePicker({el:'d12'})" src="../skin/datePicker.gif" width="34" height="40" align="absmiddle"> -->
				    
				             
				               标签类型:<select id="labelType" name="labelType"  style="width:100px;height:31px;line-height:31px;margin-right: 10px;">
				             	<option value="" selected="selected">请选择</option>
				             	<option value="1">新品</option>
				             	<option value="2">拼团</option>
				             	<option value="3">抢购</option>
				             	<option value="4">满减</option>
				             	<option value="5">爆款</option>
				             </select>
				             
				     开始时间 : 
	                  <input id="txtBeginTime" name="showStart"  class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"
				                   style="height:31px;line-height:31px;"/>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProduct()" >查询</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddProduct()">增加标签</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditProduct()">修改标签</a>
				       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"  enctype ="multipart/form-data"  onclick="toDelProduct()">删除标签</a>
		      	</div>
	     </div>
	     
	        
      	<div id="tb" style="padding:0 30px;">
      	</div>

		<input type="hidden" name="productLabelId" id="productLabelId" value=""/>
	
    	<script type="text/javascript" src="${resourcepath}/admin/js/productlabel/list_product_label.js"></script>
	</body>
</html>