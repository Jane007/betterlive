<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看订单管理列表</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/order/list_otherorder.js"></script>
		
	</head>
	
	<body>
		
	<!-- 配置导入框 -->  
	<div id="importExcel" class="easyui-dialog" title="导入excel文件" style="width: 400px;display: none;" data-options="modal:true">  
	    <form id="uploadExcel"  method="post" enctype="multipart/form-data">    
	       	 选择文件：　<input id="excelId" name="excel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">    
	    </form>    
	    <div style="text-align: center; padding: 5px 0;">  
	        <a id="impId" href="javascript:void(0)" class="easyui-linkbutton"  
	            onclick="uploadExcel()" style="width: 80px">导入</a>  
	    </div>  
	</div> 
	    
		<table id="StoreGrid" style="width:100%;height:529px;" title="订单列表"></table>
     
     	<div id="storeToolbar" style="padding:0 30px;">
     		<div>
		        <span class="con-span">支付编号: </span> 
	            <input type="text"  class="easyui-textbox" name="payCode" id="payCode" style="width:160px;height:30px;line-height:30px;"/>
                <span class="con-span">订单编号: </span> 
	            <input type="text"  class="easyui-textbox" name="orderCode" id="orderCode" style="width:160px;height:30px;line-height:30px;"/>
			   <span class="con-span">支付时间段：</span>
			   <input id="startTime" name="startTime"  class="easyui-datebox" style="height:30px;margin:0 0;width:180px;"/>-
			   <input id="endTime" name="endTime"  class="easyui-datebox" style="height:30px;margin:0 0;width:180px;"/>
			    <span class="con-span">用户名称: </span>	
			   <input class="easyui-textbox" type="text" name="customerName" id="customerName" style="height:30px;width:160px;line-height:30px;"/>
			   
		   </div>
		   <div>
		   	   <span class="con-span">收货手机: </span>
			   <input class="easyui-textbox" type="text" name="orderMobile" id="orderMobile" style="height:30px;width:120px;line-height:30px;"/>
			   <span class="con-span">用户来源: </span>
	           <input class="easyui-textbox" type="text" name="customerSource" id="customerSource" style="height:30px;width:140px;line-height:30px;"/>
			   <span class="con-span">订单来源: </span>
	           <input class="easyui-textbox" type="text" name="orderSource" id="orderSource" style="height:30px;width:140px;line-height:30px;"/>
			   
				<div style="display: inline;width: 600px;">	    
				   <span>
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="queryOrderList()">查询</a>
					    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" data-options="selected:true" onclick="clearForm()">重置</a>
				   </span>
							  
			       <span class="con-span">
			            <a id="conExport" class="easyui-linkbutton" data-options="selected:true" onclick="$('#importExcel').dialog('open');">导入订单</a>
			       </span>
	       		</div>
	   		</div>
     </div>

    <script type="text/javascript">
    var startTime = "${startTime}";
    var endTime = "${endTime}";
    var customerSource = "${customerSource}";
    var orderSource = "${orderSource}";
    var mainServer = "${mainserver}";
    var productCode = "${productCode}";
    

    $("#uploadExcel").form({
    	type : 'post',
    	url : mainServer + '/admin/otherorder/importOtherOrders',
    	dataType : "json",
    	onSubmit : function() {
    		var fileName = $('#excelId').filebox('getValue');
    		// 对文件格式进行校验
    		var d1 = /\.[^\.]+$/.exec(fileName);
    		if (fileName == "") {
    			$.messager.alert('Excel导入', '请选择将要上传的文件!');
    			return false;
    		} else if (d1 != ".xls" && d1 != ".xlsx") {
    			$.messager.alert('提示', '请选择xls、xlsx格式文件！', 'info');
    			return false;
    		}
    		$("#impId").linkbutton('disable');
    		return true;
    	},
    	success : function(data) {
    		var result = eval('(' + data + ')');
    		if (result != null && result.code == 1010) {
    			$.messager.alert('提示!', '导入成功', 'info', function() {
    				$("#impId").linkbutton('enable');
    				$('#importExcel').dialog('close');
    				queryOrderList();
    			});
    		} else {
    			$.messager.confirm('提示', "导入失败");
    			$("#impId").linkbutton('enable');
    		}
    	}
    });
    </script>
	</body>
</html>