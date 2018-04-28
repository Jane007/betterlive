<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    	<title>首页公告列表</title> 
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
		<script type="text/javascript" src="${resourcepath}/admin/js/notice/list_notice.js"></script>
	</head>
	<body>
	      <table id="StoreGrid" style="width:100%;height:529px;" title="公告列表">
	      </table>
	      
	      <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
					        公告标题: <input class="easyui-textbox" type="text" name="noticeTitleStr"  id="noticeTitleStr" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
					      状态:     
				       <select id="statusSearch" name="statusSearch" style="height:35px;margin:0 0;width:220px;" >
				                    <option value="">未选择</option>
									<option value="1">正常</option>
									<option value="0">禁用</option>
								</select>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProductType()" >查询</a>
				       
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addOpenDlg()">新增公告</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" id="updConfBtn"   onclick="updConfDlg()">修改</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" id="delConfBtn"   onclick="delConfDlg()">删除</a>
		      	</div>
		       
	     </div>
     
    	<div id="dlg" class="easyui-dialog" title="新增公告" data-options="closed:true"  fit=“true”  style="width:700px;height:600px;padding:10px;">
    		<form  id="form1" action="" method="post"  enctype="multipart/form-data">
		       	<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">公告标题&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-textbox" type="text" name="noticeTitle" id="noticeTitle"  style="height:35px;margin:0 0;width:80%;" maxlength="100"/>
							</td>
						</tr>
						<tr>
							<td class="kv-label">公告类型&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select id="noticeType" name="noticeType" style="height:35px;margin:0 0;width:220px;" >
									<!--<option value="1">系统文章</option>  -->
									<option value="2">预售产品</option>
									<option value="3">专题活动</option>
									<option value="4">产品</option>
								</select>
							</td>
						</tr>
						<tr>
					
						<!-- <tr>
							<td class="kv-label">关联实体&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select id="objectId" name="objectId" style="height:35px;margin:0 0;width:320px;" >
									
								</select>
							</td>
						</tr> -->
						<tr>
						
							<td class="kv-label">公告链接地址&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-textbox" type="text" id="noticeUrl" name="noticeUrl" style="height:35px;margin:0 0;width:90%;" />
							</td>
						</tr>
						<tr>
							<td class="kv-label">开始时间&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-datebox" type="text" name="startTime" id="startTime"  style="height:35px;margin:0 0;width:220px;" />
							</td>
						</tr>
						<tr>
							<td class="kv-label">结束时间&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<input class="easyui-datebox" type="text" name="endTime" id="endTime"  style="height:35px;margin:0 0;width:220px;" />
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
							<td class="kv-label">公告内容&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<textarea rows="5" cols="50" id="noticeContent" name="noticeContent"> </textarea>
							</td>
						</tr>
						
						
						<tr>
							<td colspan="2">
								<div align="center" style="margin-top: 60px;">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" 
									   onclick="addProductType()" id="add_Ptype" >新增</a>
									   
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" 
									   onclick="editProductType()"  id="edit_Ptype" style="display: none;">编辑</a>
									   
									<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="selected:true" 
									   onclick="closeProductType()" >关闭</a>
									   
									<input type="hidden" name="noticeId" id="noticeId" value=""/>
									<!-- <input type="hidden" name="noticeUrl" id="noticeUrl_h" value=""/> -->
									<input type="hidden" name="objectId_h" id="objectId_h" value=""/>
									
								</div>
							</td>
						</tr>
					</tbody>
				</table> 
			</form>	
		</div> 
		
		
   	 <script type="text/javascript">
   		var mainServer = '${mainserver}';
	</script>
	
</body> 
</html>
