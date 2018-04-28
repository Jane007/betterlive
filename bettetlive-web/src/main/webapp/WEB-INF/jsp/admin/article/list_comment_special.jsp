<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<title>查看社区文章列表</title> 
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
		<script type="text/javascript">
	   		var mainServer = '${mainserver}';
		</script>
		<script type="text/javascript" src="${resourcepath}/admin/js/article/list_comment_special.js"></script>
	</head>
	 
	<body>
		<table id="StoreGrid" style="width:100%;height:529px;" title="文章评论列表">
		</table>
		
		<div id="storeToolbar">
			<div id="tb" style="padding:0 30px;">
			   	   类型 : <select id="articleFrom" class="easyui-combobox" name="articleFrom" style="width:166px;height:35px;line-height:35px;">
								<option value="0" selected="selected">精选</option>
								<option value="1">动态</option>
			   	      </select>
			   	 状态：<select id="status" class="easyui-combobox" name="status" style="width:166px;height:35px;line-height:35px;">
								<option value="2" selected="selected">审核通过</option>
								<option value="3">审核失败</option>
								<option value="4">已删除</option>
			   	      </select>
			    	
			            <!--  客户昵称: <input class="easyui-textbox" type="text" name="customerName"  id="customerName" maxlength="40" style="width:166px;height:35px;line-height:35px;" />
			            评论时间段：<input class="easyui-datetimebox" type="text" name="start" id="start"  style="height:35px;margin:0 0;width:180px;"/> --&nbsp;&nbsp;
				   	<input class="easyui-datetimebox" type="text" name="end" id="end"  style="height:35px;margin:0 0;width:180px;"/>     -->
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchComment()" >查询</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" id="reConfBtn" onclick="reConfDlg()" >回复</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" id="checkConfBtn" onclick="checkConfDlg(2)" >审核通过</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" id="notCheckConfBtn" onclick="checkConfDlg(3)" >审核不通过</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="selected:true" id="updConfBtn" onclick="updConfDlg()" >删除评论</a>
		  	</div>
	  	</div>
		<div id="dlg" class="easyui-dialog" title="添加回复" data-options="closed:true,modal:true" style="width:750px;height:240px;padding:10px;">
		    <form action="addReply" id="party_base_info" method="post">
			   	<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">评论回复:</td>
							<td class="kv-content" colspan="4"> 
								<input  type="hidden" name="subMsgType" id="subMsgType"/>
								<input  type="hidden" name="commentId" id="commentId"/>
								<input  type="hidden" name="parentId" id="parentId"/>
								<textarea id="replyMsg" name="content"  style = "width:95%;height:100px;"></textarea>
					    	</td>
						</tr>
					
						<tr>
							<td class="kv-label"></td>
							<td class="kv-content" colspan="4">
								<div style="padding:5px" align="center">
			                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitReplyForm()">保存</a>
			                       <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
					            </div>
						    </td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="vaguealert">
			<p style="color: red"></p>
		</div>
</body> 
</html>
