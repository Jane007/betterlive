<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>用户优惠券管理</title> 
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
	     <table id="StoreGrid" style="width:100%;height:529px;" title="用户优惠券管理"></table>
	     
	     <div id="storeToolbar" style="padding:0 30px;">
	           <span class="con-span">手机号码: </span> 
	           <input type="text"  class="easyui-textbox" name="mobileSearch" id="mobileSearch" style="width:166px;height:35px;line-height:35px;"/>
	           <span class="con-span">用户名: </span> 
	           <input type="text"  class="easyui-textbox" name="nicknameSearch" id="nicknameSearch" style="width:166px;height:35px;line-height:35px;"/>
		        <span class="con-span">优惠券名称: </span> 
	           <input type="text"  class="easyui-textbox" name="couponNameSearch" id="couponNameSearch" style="width:166px;height:35px;line-height:35px;"/>
	           		
	           
	         <%--   <span class="con-span">开始时间: </span> 
	           <input class="easyui-datebox" name="starttime" id="starttime" style="width:166px;height:35px;"/>
	           <span class="con-span">结束时间: </span> 
	           <input class="easyui-datebox" name="endtime" id="endtime" style="width:166px;height:35px;"/> --%>
	           
			   <span style="position:relative;top:-3px;left:5px;">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryCouponManager()">查询</a>
				    <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="clearForm()">重置</a>
			   </span>
						  
		       <div class="opt-buttons" style="margin-bottom: 10px;">
		           <a id="addConfBtn" class="easyui-linkbutton"iconCls="icon-add"  data-options="selected:true" onclick="addCouponManagerDlg()">新增</a>
		       </div>
	     </div>
	     
	     <div id="upCouponManagerDlg" class="easyui-dialog" title="用户优惠券管理" data-options="closed:true" style="width:700px;height:340px;padding:10px;">
			<form id="couponManagerForm" method="post">
	         	<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">发放对象:&nbsp;<font color="red">*</font></td>
							<td class="kv-content">
								<select  name="toObject" id="toObject" onchange="changeObject()" style="width:285px;height:42px;line-height:35px;"  >
								    <option value="1" >全部用户</option>
									<option value="2">个人用户</option>
						        </select>
						     </td>
						</tr>
						<tr>
							<td class="kv-label"> 用户手机号码: </td>
							<td class="kv-content" >
								 <input name="mobile" id="mobile"  style="width:285px;height:35px;line-height:35px;display: none;"/>
							</td>
						</tr>
						
						<tr>
							<td class="kv-label"> 优惠券名称: &nbsp;<font color="red">*</font></td>
							<td class="kv-content" >
								 <select  name="cmId" id="cmId" style="width:285px;height:42px;line-height:35px;" onchange="changeCouponManager()" >
									<option value="">请选择</option>			                            
						         </select>
							</td>
						</tr>
					</tbody>
				</table>
				
				<div style="margin-top:30px" align="center">
                       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="submitCouponManagerForm()">增加</a>
                      <%--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear"  onclick="clearCouponManagerForm()">全部清空</a> --%>
                </div>
	        </form>
	     </div> 
	
    	<script type="text/javascript" src="${resourcepath}/admin/js/coupon/list_user_couponinfo.js"></script>
	</body> 
</html>
