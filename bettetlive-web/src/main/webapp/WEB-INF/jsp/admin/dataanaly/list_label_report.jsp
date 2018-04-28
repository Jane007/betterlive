<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

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
				    <span class="con-span">日期：</span> <input id="startTime" name="startTime"
				class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser"
				style="height: 30px; margin: 0 0; width: 180px;" />- <input
				id="endTime" name="endTime" class="easyui-datebox"
				data-options="formatter:myformatter,parser:myparser"
				style="height: 30px; margin: 0 0; width: 180px;" /> <span>         
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchLabel()" >查询</a></span>
		      	</div>
	     </div>
	     
	        
      	<div id="tb" style="padding:0 30px;">
      	</div>

		<input type="hidden" name="labelId" id="labelId" value=""/>
		
		<script type="text/javascript" src="${resourcepath}/admin/js/dataanaly/list_label_report.js"></script>
	</body>
</html>