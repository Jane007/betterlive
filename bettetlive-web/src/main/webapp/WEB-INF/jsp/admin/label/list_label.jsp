<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>热门搜索标签</title>
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
	<style type="text/css">
				label{
				cursor:pointer;
			}
			
			.edui-default .edui-editor{
				border: 1px solid #d4d4d4 !important;
			}
			
			.addTea{
			    background: #1da02b;
			    filter: none;
			    color: #fff;
			    border: 1px solid #1da02b;
			    display: inline-block;
			    position: relative;
			    overflow: hidden;
			    margin: 0;
			    padding: 0;
			    vertical-align: top;
			    display: inline-block;
			    vertical-align: top;
			    width: 60px;
			    border-radius: 5px 5px 5px 5px;
			    line-height: 33px;
			    font-size: 12px;
			    padding: 0;
			    margin: 0 4px;
			    cursor: pointer;
			    text-align: center;
			}
			
			.waiteTea{
		/* 	    background: #1da02b; */
			    filter: none;
			    color: #1F1F1F;
			    border: 1px solid #1da02b;
			    display: inline-block;
			    position: relative;
			    overflow: hidden;
			    margin: 0;
			    padding: 0;
			    vertical-align: top;
			    display: inline-block;
			    vertical-align: top;
			    width: 120px;
			    border-radius: 5px 5px 5px 5px;
			    line-height: 33px;
			    font-size: 12px;
			    padding: 0;
			    margin: 0 4px;
			    cursor: pointer;
			    text-align: center;
			    margin-top: 10px;
			}
			
			.chooseTea{
		 	    background: #1da02b; 
			    filter: none;
			    color: #fff;
			    border: 1px solid #1da02b;
			    display: inline-block;
			    position: relative;
			    overflow: hidden;
			    margin: 0;
			    padding: 0;
			    vertical-align: top;
			    display: inline-block;
			    vertical-align: top;
			    width: 120px;
			    border-radius: 5px 5px 5px 5px;
			    line-height: 33px;
			    font-size: 12px;
			    padding: 0;
			    margin: 0 4px;
			    cursor: pointer;
			    text-align: center;
			     margin-top: 10px;
			}
			
		
		</style>
	</head>
	<body>
		  <table id="StoreGrid" style="width:100%;height:529px;" title="商品标签">
	        
	      </table>
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
	      			标签名称: <input class="easyui-textbox" type="text" name="labelName"  id="labelName" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
				   <!--  <input id="d12" type="text"/> -->
					<!-- <img onclick="WdatePicker({el:'d12'})" src="../skin/datePicker.gif" width="34" height="40" align="absmiddle"> -->
				    
				             
				               标签类型:<select id="labelType" name="labelType"  style="width:100px;height:31px;line-height:31px;margin-right: 10px;">
				             	<option value="1" selected="selected">热门搜索</option>
				             </select>
				             
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchLabel()" >查询</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddLabel()">增加标签</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditLabel()">修改标签</a>
				       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"  enctype ="multipart/form-data"  onclick="toDelLabel()">标签下架</a>
		      	</div>
	     </div>
	     <div id="dlg" class="easyui-dialog" title="选择产品" data-options="closed:true" style="width:700px;height:500px;padding:10px;display: none;">
	      	<table class="kv-table">
				<tbody>
					<tr>
							<c:forEach items="${products}"  var="product">
			           			 <span class="waiteTea"  id="${product.product_id}" title="${product.product_name}"  onclick="addCreateProduct(this)" >${product.product_name}</span> 
			           		</c:forEach>						
							<input type="hidden" name="labelId" id="labelId"/>
							<div style="padding:35px" align="center">
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" onclick="isSureProduct()">确定</a>
								<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="selected:true" onclick="closeProduct()" >关闭</a>
                            </div>
					</tr>
				</tbody>	
			</table>
		</div>
	        
		<script type="text/javascript" src="${resourcepath}/admin/js/label/list_label.js"></script>	
	</body>
</html>