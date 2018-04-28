<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看每周新品列表</title>
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
			var newSize = '${size}';
		</script>	
	</head>
	
	<body>
		  <table id="StoreGrid" style="width:100%;height:529px;" title="每周新品列表">
	        
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" id='addConfBtn' onclick="addOpenDlg()">增加每周新品</a>
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditRecommend()">修改每周新品</a>
		      		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="delConfBtn"   onclick="delRecommend()">删除每周新品</a>
		      		
		      	</div>
	     </div>
	     
	        
      	<div id="tb" style="padding:0 30px;">
		       
      	</div>

		<div id="dlg" class="easyui-dialog" title="新增每周新品" data-options="closed:true"  fit=“true”  style="width:620px;height:420px;padding:10px;">
    		<form  id="form1" action="" method="post">
    			
		       	<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">新品名称</td>
							<td class="kv-content">
								<select id="productId" style="height:35px;margin:0 0;width:220px;">
									<c:forEach items="${products }" var="product">
										<option value="${product.product_id }">${product.product_name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="kv-label">是否首页展示</td>
							<td class="kv-content">
								<select id="isHomepage" name="isHomepage" style="height:35px;margin:0 0;width:220px;">
									<option value="0" selected = "selected">否</option>
									<option value="1">是</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center" style="margin-top: 60px;">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" 
									   onclick="addRecommend()" id="add_Ptype" >保存</a>
									   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" 
									   onclick="editRecommend()" id="edit_Ptype" >保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="selected:true" 
									   onclick="closeProductType()" >关闭</a>
									<input id="extensionId" name="extensionId" type="hidden" >
									<input id="productId_p" name="productId"  type="hidden" >
								</div>
							</td>
						</tr>
					</tbody>
				</table> 
			</form>	
		</div>
		
		<script type="text/javascript" src="${resourcepath}/admin/js/new/list_new.js"></script>
	</body>
</html>