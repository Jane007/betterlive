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
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
	   	<script type="text/javascript">
		   	var mainServer = '${mainserver}';
		</script>

	</head>
	<body>
	      <table id="StoreGrid" style="width:100%;height:529px;" title="商品类型列表">
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
					   banner标题: <input class="easyui-textbox" type="text" name="bannerTitleStr"  id="bannerTitleStr" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <select id="statusSearch" name="statusSearch" style="height:35px;margin:0 0;width:220px;" >
				                    <option value="">未选择</option>
									<option value="1">正常</option>
									<option value="0">禁用</option>
								</select>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProductType()" >查询</a>
				      <!--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>  -->
				       
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addOpenDlg()">新增</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updConfBtn"   onclick="updConfDlg()">修改</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="delConfBtn"   onclick="delConfDlg()">删除</a>
		      	</div>
		       
	     </div>
     
    	<div id="dlg" class="easyui-dialog" title="新增banner图" data-options="closed:true"  fit=“true”  style="width:620px;height:420px;padding:10px;">
    		<form  id="form1" action="" method="post"  enctype="multipart/form-data">
		       	<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">banner标题&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-textbox" type="text" name="bannerTitle" id="bannerTitle"  style="height:35px;margin:0 0;width:80%;" maxlength="100"/>
							</td>
						</tr>
						<tr>
							<td class="kv-label">banner类型&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select id="bannerType" name="bannerType" style="height:35px;margin:0 0;width:220px;" >
									<option value="1">自定义链接</option> 
									<option value="2">预售产品</option>
									<option value="3">专题活动</option>
									<option value="4">产品</option>
								</select>
							</td>
						</tr>
						<tr>
						
						<tr id="linkObjId">
							<td class="kv-label">关联实体&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select id="objectId" style="height:35px;margin:0 0;width:320px;" >
									
								</select>
							</td>
						</tr>
						<tr>
						
							<td class="kv-label">banner链接地址&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-textbox" type="text" id="bannerUrl" style="height:35px;margin:0 0;width:90%;"/>
							</td>
						</tr>
					
						<tr>
							<td class="kv-label">状态&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select id="status" name="status" style="height:35px;margin:0 0;width:220px;" >
									<option value="1">正常</option>
									<option value="0">禁用</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="kv-label">排序&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
							<input class="easyui-textbox" type="text" id="isSort" name="isSort" style="height:35px;margin:0 0;width:90%;" value="1"/>
							</td>
						</tr>
						<tr>
						    <td class="kv-label">banner图片:&nbsp;<font color="red">*</font><br/>(720x476)</td>
							<td class="kv-content" colspan="2"> 
								<input class="easyui-filebox" type="text" name="bannerImg1" id="bannerImg" style="height:35px;width:60px;"
				                      data-options="buttonText:'选择图片',accept:'image/jpeg,image/png,image/gif,image/JPEG,image/PNG,image/GIF', onChange:function(){previewImg($(this));}"/>
										<span style="color: red;"></span><div class="bannerImgShow"></div>
										<p style="color: red;">* 请选择小于1兆的图片</p>
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
									   
									<input type="hidden" name="bannerId" id="bannerId" value=""/>
									<input type="hidden" name="bannerUrl" id="bannerUrl_h" value=""/>
									<input type="hidden" name="objectId" id="objectId_h" value=""/>
									
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
	<script type="text/javascript" src="${resourcepath}/admin/js/banner/list_banner.js"></script>
</body> 
</html>
