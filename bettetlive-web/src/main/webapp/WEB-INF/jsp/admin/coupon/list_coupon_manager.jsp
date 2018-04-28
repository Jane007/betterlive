<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看优惠券管理列表</title>
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
		  
		<table id="StoreGrid" style="width:100%;height:529px;" title="优惠券管理"></table>
     
     	<div id="storeToolbar" style="padding:0 30px;">
	        <span class="con-span">优惠券名称: </span> 
            <input type="text"  class="easyui-textbox" name="couponNameSearch" id="couponNameSearch" style="width:166px;height:30px;line-height:30px;" maxlength="30"/>
           
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
     
     
      <div id="upCouponManagerDlg" class="easyui-dialog" title="优惠券管理" data-options="closed:true" style="width:700px;height:400px;padding:10px;">
		<form id="couponManagerForm" method="post">
       		<input type="hidden" id ="cmId" name ="cmId" readonly="readonly"/>
         	<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">优惠券名称:&nbsp;<font color="red">*</font></td>
						<td class="kv-content" >
						      <input name="couponName" id="couponName" style="width:280px;height:30px;line-height:30px;" maxlength="20"/>
					    </td>
					</tr>
					<tr>
						<td class="kv-label"> 优惠券类型: &nbsp;<font color="red">*</font></td>
						<td class="kv-content">
							 <select  name="couponType" id="couponType" style="width:285px;height:35px;line-height:35px;">
	                            <option value="" selected="selected">请选择</option>
							    <option value="1">分享券</option>
								<option value="2">补偿券</option>
								<option value="3">新手券</option>
					         </select>
					    </td>
					</tr>
					
					<tr>
					<td class="kv-label">优惠券有效期(天):&nbsp;<font color="red">*</font></td>
						<td class="kv-content">
						     <input name="endDate" id="endDate" style="width:280px;height:30px;line-height:30px;" 
						        onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"/>
					    </td>
					</tr>    					
					<tr>
					    <td class="kv-label">优惠券金额(元):&nbsp;<font color="red">*</font></td>
						<td class="kv-content" >
						      <input name="couponMoney" id="couponMoney" style="width:280px;height:30px;line-height:30px;" 
						        onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)" />
					    </td>
					</tr>
					<tr>     
						<td class="kv-label"> 使用门槛金额(元):&nbsp;<font color="red">*</font> </td>
						<td class="kv-content" >
							 <input name="useMinMoney" id="useMinMoney"  style="width:280px;height:30px;line-height:30px;"
							 	onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"/>
						</td>
						
					</tr>
					<tr>     
						<td class="kv-label"> 简介:&nbsp;<font color="red">*</font> </td>
						<td class="kv-content" >
							 <input name="coupon_content" id="coupon_content" style="width:280px;height:30px;line-height:30px;" maxlength="20"/>
						</td>
						
					</tr>	
					<tr>
						<td class="kv-label">是否推荐到首页&nbsp;</td>
						<td class="kv-content">
							<select name="home_flag" id="homeFlag" style="width: 100px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</td>
					</tr>
					
				</tbody>
			</table>
			<div style="margin-top: 30px;" align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  id="addcoupon"  style="display: none;"data-options="selected:true"  onclick="addCouponManagerForm()">新增</a>
                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  id="editcoupon"  style="display: none;" data-options="selected:true"  onclick="editCouponManagerForm()">修改</a>
                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" data-options="selected:true" onclick="clearCouponManagerForm()">全部清空</a>
             </div>
        </form>
     </div>
     
   	<script type="text/javascript" src="${resourcepath}/admin/js/coupon/list_coupon_manager.js"></script>
	</body>
</html>