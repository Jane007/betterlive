<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>查看礼品卡管理列表</title>
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
		<script type="text/javascript" src="${resourcepath}/admin/js/jquery.loadmask.min.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/jquery-form.js"></script>
	  	<script type="text/javascript">
    		var mainServer = '${mainserver}';
    	</script>
	</head>
	
	<body>
		  
		<table id="StoreGrid" style="width:100%;height:529px;" title="礼品卡管理"></table>
     
     	<div id="storeToolbar" style="padding:0 30px;">
	        <span class="con-span">礼品卡卡号: </span> 
            <input type="text"  class="easyui-textbox" name="cardNoSearch" id="cardNoSearch" style="width:166px;height:30px;line-height:30px;"/>
           
           <span class="con-span">礼品卡状态: </span> 
           <select name="cardStatusSearch" id="cardStatusSearch"  style="width:166px;height:30px;line-height:30px;">
           	   <option value="">请选择</option>
           	   <option value="0">未使用</option>
           	   <option value="1">已使用</option>
           	   <option value="2">已过期</option>
           </select>
            
           <span class="con-span">绑定用户: </span> 
           <input type="text"  class="easyui-textbox" name="customerNameSearch" id="customerNameSearch" style="width:166px;height:30px;line-height:30px;"/>
           
		   <span style="position:relative;top:-3px;left:5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="queryCouponManager()">查询</a>
			    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" data-options="selected:true" onclick="loadExcel()">批量导入数据</a>
			    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" data-options="selected:true" onclick="clearForm()">重置</a>
		   </span>
					  
	       <div class="opt-buttons">
	           <a id="addConfBtn" class="easyui-linkbutton"  iconCls="icon-add" data-options="selected:true"  onclick="addGiftCardManagerDlg()">新增</a>
	           <a id="updConfBtn" class="easyui-linkbutton"  iconCls="icon-edit" data-options="selected:true" onclick="updateGiftCardManagerDlg()" >修改</a>
	           <%-- <a id="deleteConfBtn" class="easyui-linkbutton"  iconCls="icon-remove" data-options="selected:true"   onclick="deleteCouponManagerDlg()">删除</a> --%>
	       </div>
     	</div>
     
     
      <div id="upCouponManagerDlg" class="easyui-dialog" title="礼品卡管理" data-options="closed:true" style="width:700px;height:400px;padding:10px;">
		<form id="couponManagerForm" method="post">
         	<table class="kv-table">
         		<input type="hidden" id ="cardId" name ="cardId" />
				<tbody>
					<tr>
						<td class="kv-label">礼品卡卡号:<font color="red">*</font></td>
						<td class="kv-content" >
						      <input name="giftCardNo" id="giftCardNo" style="width:280px;height:30px;line-height:30px;" onkeyup="this.value=this.value.replace(/[^0-9a-zA-Z]+/,'');" maxlength="20"/>
					    </td>
					</tr>
					<tr>
						<td class="kv-label">礼品卡密码:<font color="red">*</font></td>
						<td class="kv-content" >
						      <input name="giftCardPw" id="giftCardPw" style="width:280px;height:30px;line-height:30px;" onkeyup="this.value=this.value.replace(/[^0-9a-zA-Z]+/,'');" maxlength="16"/>
					    </td>
					</tr>
					<tr>
					    <td class="kv-label">礼品卡金额(元):<font color="red">*</font></td>
						<td class="kv-content" >
						      <input name="giftCardMoney" id="giftCardMoney" style="width:280px;height:30px;line-height:30px;" 
						        onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"  />
					    </td>
					</tr>
					<tr>
						<td class="kv-label"> 礼品卡状态: <font color="red">*</font></td>
						<td class="kv-content">
							 <select  name="status" id="status" style="width:285px;height:35px;line-height:35px;"  onchange="checkUseDate();" >
	                            <option value="" selected="selected">请选择</option>
							    <option value="0" >未使用</option>
								<option value="1">已使用</option>
								<option value="2">已过期</option>
					         </select>
					    </td>
					</tr>
				</tbody>
			</table>
			<div style="margin-top: 30px;" align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  id="addcoupon"  style="display: none;"data-options="selected:true"  onclick="addGiftCardManagerForm()">新增</a>
                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  id="editcoupon"  style="display: none;" data-options="selected:true"  onclick="editGiftCardManagerForm()">修改</a>
                    
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" data-options="selected:true" onclick="clearCouponManagerForm()">全部清空</a>
             </div>
        </form>
     </div>
  
   	<script type="text/javascript" src="${resourcepath}/admin/js/giftcard/list_giftcard.js"></script>
	</body>
</html>