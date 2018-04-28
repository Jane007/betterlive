<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>挥货后台管理-发消息</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers1.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
	    <script type="text/javascript">
	  		var mainServer = '${mainserver}';
	    </script>
	</head>
<body>
<body>
	<form id="messageSendForm" method="post" >
			<input type="hidden" id="objId" name="objId" value="0">
			<input type="hidden" id="msgType" name="msgType" value="1">
			<input type="hidden" id="subMsgType" name="subMsgType" value="10">
        	<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">消息标题</td>
					<td class="kv-content">
						<input class="easyui-textbox" name="msgTitle" id="msgTitle" value="活动公告" data-options="required:true" missingMessage="不能为空" style="height:35px;margin:0 0;width: 270px;"/>
						<a id="choiceActShow" href="javascript:choiceActivity();" >选择活动</a>
					
					</td>
				</tr>
				<tr>
					<td class="kv-label">消息内容</td>
					<td class="kv-content">
						<textarea id="msgDetail" rows=4 cols =50 name="msgDetail" class="textarea easyui-validatebox" style="width: 40%;" data-options="required:true" missingMessage="不能为空"></textarea>
					</td>
				</tr>
				<tr>
					<td class="kv-label">发送人群</td>
					<td class="kv-content">
						<input type="radio" id="custs" name="sendCrowd" value="1" checked="checked" onchange="choiceCust(this.value)">所有用户
						<input type="radio" id="cust" name="sendCrowd" value="2" onchange="choiceCust(this.value)">指定用户
					</td>
				</tr>
				<tr id="objShow" style="display: none;">
					<td class="kv-label" id="objNameShow">用户手机号码</td>
					<td class="kv-content">
						<input class="easyui-textbox" name="objName" id="objName" value="" data-options="required:true" missingMessage="不能为空" style="height:35px;margin:0 0;width: 270px;"/>
					</td>
				</tr>
				<tr>
					<td class="kv-content" colspan="2">
						<div style="padding:5px" align="center">
   	                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" data-options="selected:true" onclick="submitForm()">保存</a>
                           </div>
                        </td>
				</tr>
			</tbody>
		</table>
       </form>
       
       <div id="actdlg" class="easyui-dialog" title="选择活动" data-options="closed:true" style="width:1000px;height:500px;padding:10px;">
	      	 <table id="activityGrid" style="width:100%;height:229px;">
      		 </table>
      		 
      		 <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
				             专题名称: <input class="easyui-textbox" type="text" name="specialName"  id="specialName" maxlength="30" style="width:166px;height:35px;line-height:35px;" />
				             
				            专题类型: <select id="specialType" name="specialType" style="height:35px;width:160px;"  >
				             	<option value="" selected="selected">请选择</option>
				             	<option value="1">限时活动</option>
				             	<option value="2">限量抢购</option>
				             	<option value="3">团购活动</option>
				             	<option value="4">美食教程</option>
				             	
				             </select>
				                    
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchSpecial()" >查询</a>
		      	</div>
	     	</div>
		</div>

	<script type="text/javascript" src="${resourcepath}/admin/js/messagespecial/edit_message_special.js"></script>
</body>
</html>