<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    	<title>首页banner图列表</title> 
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
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
	
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
		</script>
	</head>
	<body>
	      <table id="StoreGrid" style="width:100%;height:529px;" title="商品类型列表">
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
					   banner分类标题: <input class="easyui-textbox" type="text" name="bannerTitleStr"  id="bannerTitleStr" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <select id="statusSearch" name="statusSearch" style="height:35px;margin:0 0;width:220px;" >
				                    <option value="">未选择</option>
									<option value="1">上架</option>
									<option value="0">下架</option>
								</select>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProductType()" >查询</a>
				      <!--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>  -->
				       
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addOpenDlg()">新增banner图</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updConfBtn"   onclick="updConfDlg()">修改</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="delConfBtn"   onclick="delConfDlg()">删除</a>
		      	</div>
		       
	     </div>
     
    	<div id="dlg" class="easyui-dialog" title="新增banner图" data-options="closed:true"  fit=“true”  style="width:620px;height:420px;padding:10px;">
    		<form  id="form1" action="" method="post"  enctype="multipart/form-data">
		       	<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">banner分类类型&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select id="bannerType" name="bannerType" style="height:35px;margin:0 0;width:220px;"  onchange="gradeChange()">
									<option value="0">分类列表</option>
									<option value="4">星球推荐</option>
									<option value="3">休闲零食</option>
									<option value="1">应季水果</option>
									<option value="2">水产肉类</option>
									<option value="5">限量抢购</option>
									<option value="6">每周新品</option>
									<option value="7">人气推荐</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="kv-label">banner分类标题&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="textbox" name="bannerTitle" id="bannerTitle"  style="height:35px;margin:0 0;width:57%;" maxlength="100"  >
							</td>
						</tr>
						<tr>
						
						<!-- <tr>
						
							<td class="kv-label">banner链接地址&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-textbox" type="text" id="bannerUrl" style="height:35px;margin:0 0;width:90%;" disabled="disabled"/>
							</td>
						</tr> -->
					
						<tr>
							<td class="kv-label">状态&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select id="status" name="status" style="height:35px;margin:0 0;width:220px;" >
									<option value="1">上架</option>
									<option value="0">下架</option>
								</select>
							</td>
						</tr>
						<tr>
						    <td class="kv-label">banner图片:&nbsp;<font color="red">*</font><br/>(720x476)</td>
							<td class="kv-content" colspan="2"> 
							    <input  type="hidden" name="bannerImg" id="bannerImg" />
								<a class="easyui-linkbutton" data-options="selected:true" onclick="$('#uploadImg').dialog('open')">上传图片</a>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img id='pimg' src='' width='20%'>
						    </td>
						</tr>
						<tr>
							<td class="kv-label">图片匹配商品&nbsp;</td>
							<td class="kv-content">
								<select id="productId" name="productId" style="height:35px;margin:0 0;width:220px;" >
									<option value="0">请选择商品</option>
									<c:forEach items="${products}"  var="product">
									<option value="${product.product_id}">${product.product_name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>	
						<tr>
							<td colspan="2">
								<div align="center" style="margin-top: 60px;">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" 
									   onclick="addProductType()" id="add_Ptype" >保存</a>
									   
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" 
									   onclick="editProductType()"  id="edit_Ptype" style="display: none;">保存</a>
									   
									<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="selected:true" 
									   onclick="closeProductType()" >关闭</a>
									   
									<input type="hidden" name="classifyBannerId" id="classifyBannerId"/>
									<input type="hidden" name="bannerUrl" id="bannerUrl_h"/>
									
								</div>
							</td>
						</tr>
					</tbody>
				</table> 
			</form>	
		</div> 
		<div id="uploadImg" class="easyui-dialog" title="上传封面图片" data-options="closed:true,modal:true" style="width:530px;height:450px;padding:5px;">
			<div id="zyupload"></div>
		</div>
   
	<script type="text/javascript" src="${resourcepath}/admin/js/classifybanner/list_classifybanner.js"></script>
</body> 
</html>
