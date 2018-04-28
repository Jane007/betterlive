<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看活动管理列表</title>
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
	    <script type="text/javascript">
			var mainServer = '${mainserver}';
	    </script>
	</head>
	
	<body>
		  
		<table id="StoreGrid" style="width:100%;height:529px;" title="活动管理"></table>
     
     	<div id="storeToolbar" style="padding:0 30px;">
	        <span class="con-span">活动名称: </span> 
            <input type="text"  class="easyui-textbox" name="activityNameSearch" id="activityNameSearch" style="width:166px;height:30px;line-height:30px;"/>
           
		   <span style="position:relative;top:-3px;left:5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="queryCouponManager()">查询</a>
			    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" data-options="selected:true" onclick="clearForm()">重置</a>
		   </span>
					  
	       <div class="opt-buttons">
	           <a id="addConfBtn" class="easyui-linkbutton"  iconCls="icon-add" data-options="selected:true"  onclick="addCouponManagerDlg()">新增</a>
	           <a id="updConfBtn" class="easyui-linkbutton"  iconCls="icon-edit" data-options="selected:true" onclick="updateCouponManagerDlg()" >修改</a>
	           <%-- <a id="deleteConfBtn" class="easyui-linkbutton"  iconCls="icon-remove" data-options="selected:true"   onclick="deleteCouponManagerDlg()">删除</a> --%>
	       </div>
     	</div>
     
     
      <div id="upCouponManagerDlg" class="easyui-dialog" title="活动管理" data-options="closed:true" style="width:700px;height:600px;padding:10px;">
		<form id="couponManagerForm" method="post">
         	<table class="kv-table">
         		<input type="hidden" id ="activityId" name ="activityId" readonly="readonly"/>
         		<input type="hidden" id ="pId" name ="pId" readonly="readonly"/>
				<tbody>
					<tr>
						<td class="kv-label">活动主题:</td>
						<td class="kv-content" >
						      <input class="easyui-textbox" name="theme" id="theme" style="width:280px;height:35px;line-height:35px;"/>
					    </td>
					</tr>
					<tr> 
						<td class="kv-label">产品名称: </td>
						<td class="kv-content">
							 <select  name="productId" id="productId" style="width:280px;height:35px;line-height:35px;" onchange="chooseSpec()">
	                            <option value="" selected="selected">请选择</option>
					         </select>
					    </td>
					</tr>
					
					<tr>
					<td class="kv-label">开始时间:</td>
						<td class="kv-content">
						     <input class="easyui-datetimebox" name="starttime" id="starttime" style="width:280px;height:35px;line-height:35px;"/>
					    </td>
					</tr>    					
					<tr>
					    <td class="kv-label">结束时间:</td>
						<td class="kv-content" >
						      <input class="easyui-datetimebox" name="endtime" id="endtime" style="width:280px;height:35px;line-height:35px;" />
					    </td>
					</tr>
					
					<tr>
						<td class="kv-label" colspan="2">
							<div style="font-size: 14px;font-weight: bolder;" align="center">
								<div>规格与优惠价格 </div>
								<div style="margin-top: 15px;">
								          规格<input type="text"  style="color: red;width: 50px;" value="5千克"  readonly="readonly"/>—
									产品价格<input type="text"  style="color: red;width: 40px;" value="120" readonly="readonly" /> 元—
									活动价格(必填)<input type="text" style="color: red;width: 40px;" value="118" readonly="readonly" /> 元</font>
								</div>
							</div>
						</td>
					</tr>
					<tr style="height: 170px;">     
						<td class="kv-label"> 最多8个: </td>
						<td class="kv-content" align="left">
							<input type="hidden"  name="teaTypeLength"  id="teaTypeLength" value="0"/>
							<div id="product_1" style="float:left;" align="left">
									
							</div>
							<div id="product_2" style="float:left;margin-top: 10px;" align="left">
								
							</div>
							<div id="product_3" style="float:left;margin-top: 10px;" align="left">
								
							</div>
						</td>
						
					</tr>	
					
				</tbody>
			</table>
			<div style="margin-top: 30px;" align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  id="addcoupon"  style="display: none;"data-options="selected:true"  onclick="addCouponManagerForm()">新增</a>
                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  id="editcoupon"  style="display: none;" data-options="selected:true"  onclick="editCouponManagerForm()">修改</a>
                    
             </div>
        </form>
     </div>
   
   	<script type="text/javascript" src="${resourcepath}/admin/js/activity/list_activity_manager.js"></script>
	</body>
</html>