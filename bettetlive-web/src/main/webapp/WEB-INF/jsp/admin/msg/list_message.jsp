<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>挥货后台管理-消息管理</title>
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
      <table id="msgGrid" style="width:100%;height:529px;" title="系统消息列表">
      
      </table>
     <div id="msgToolbar" style="padding:0 30px;">
       <div class="conditions">
           <span class="con-span">消息标题: </span><input class="easyui-textbox" type="text" name="msgTitle" id="msgTitle" style="width:166px;height:35px;line-height:35px;"></input>
           <span class="con-span">消息类型: </span><select id="msgType" name="msgType" style="width:100px;height:35px;line-height:35px;">
           											<option value="-1">全部</option>
           											<option value="0">系统消息</option>
													<option value="1">挥货活动</option>
													<option value="2">优惠券/红包</option>
													<option value="3">交易消息</option>
													<option value="4">好友消息 </option>
           										</select>
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="queryData()">查询</a>
       </div>
       <div class="opt-buttons">
           <a id="addConfBtn" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true"  onclick="addConfDlg()">发消息</a>
<!--            <a id="updConfBtn" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true"  onclick="updConfDlg()">修改</a> -->
           <a id="deleteConfBtn" class="easyui-linkbutton"  iconCls="icon-remove" data-options="selected:true" onclick="deleteConfDlg()">删除</a>
       </div>
     </div>
	<script type="text/javascript" src="${resourcepath}/admin/js/msg/list_message.js"></script>
</body>
</html>