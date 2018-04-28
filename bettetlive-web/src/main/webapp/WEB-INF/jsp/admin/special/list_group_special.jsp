<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看拼团活动活动列表</title>
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
		  <table id="StoreGrid" style="width:100%;height:529px;" title="拼团活动列表">
	        
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
	      		       <input type="hidden" name="specialType" id = "specialType" value="3">
				             拼团名称: <input class="easyui-textbox" type="text" name="specialName"  id="specialName" maxlength="30" style="width:166px;height:35px;line-height:35px;" />
				                    
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchSpecial()" >查询</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddSpecial()">增加活动</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditSpecial()">修改活动</a>
			           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="upConfBtn"   onclick="toDelSpecial(1)">上架</a>
			           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="downConfBtn"   onclick="toDelSpecial(2)">下架</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"   onclick="toDelSpecial(0)">删除活动</a>
				       &nbsp;&nbsp;|&nbsp;&nbsp;
<!-- 				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="homeCover"   onclick="addOpenDlg()">首页封面</a> -->
		      	</div>
	     </div>
	     
	        
      	<div id="tb" style="padding:0 30px;">
		       
      	</div>

		
		<div id="dlg" class="easyui-dialog" title="编辑封面图" data-options="closed:true"  fit=“true”  style="width:520px;height:320px;padding:10px;">
    		<form  id="form1" action="" method="post"  enctype="multipart/form-data">
    			<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label"> 团购首页封面图:&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-filebox" type="text" name="bannerImg" id="bannerImg" style="height:35px;width:60px;"
		                      		data-options="buttonText:'选择图片',accept:'image/jpeg,image/png,image/gif,image/JPEG,image/PNG,image/GIF', onChange:function(){previewImg($(this));}"/>
								<span style="color: red;"></span><div class="bannerImgShow"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center" style="margin-top: 60px;">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" 
									   onclick="editGroupHomePic()" id="saveBtn" >保存</a>
									<c:if test="${groupPic != null && groupPic.picture_id > 0}">
										<c:if test="${groupPic.status == 0}">
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="selected:true" 
									   		onclick="delGroupHomePic(1)" id="upBtn" >上架</a>
								   		</c:if>
							<%-- 		<c:if test="${groupPic.status == 1}">
									  	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="selected:true" 
									   		onclick="delGroupHomePic(0)" id="downBtn" >下架</a>
								   		</c:if>
					   		--%>
									</c:if>
									<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="selected:true" 
									   onclick="closeDlg()" >关闭</a>
									   
									<input type="hidden" name="picture_id" id="picture_id" value="${groupPic.picture_id}"/>
									<input type="hidden" name="picture_type" id="picture_type" value="${groupPic.picture_type}"/>
									<input type="hidden" name="original_img" id="original_img" value="${groupPic.original_img}"/>
									
								</div>
							</td>
						</tr>
					</tbody>
				</table>   
			</form>	
		</div> 
		
    	<script type="text/javascript" src="${resourcepath}/admin/js/special/list_group_special.js"></script>
	</body>
</html>