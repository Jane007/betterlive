<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
<script>
var mainServer = '${mainserver}';
</script>

<table class="kv-table">
	<tbody>
		<tr>
			<td class="kv-label" style="width: 10%">
				商品封面图</br>(360x330)&nbsp;<font color="red">*</font>
				<span id="prologo_msg" style="color: red;"></span>
			</td>
			<td class="kv-content" colspan="3" style="width: 40%">
				<input  type="hidden" name="productUrl" id="productUrl" value="${product.product_logo }"/>
				<span id="productUrl1" style="color: red;"></span>
				<div id="zyupload">
				</div>
			</td>
			<td class="kv-label" colspan="2" id="pics" style="width: 50%">
				<img id="pimg" src="${product.product_logo }" style="width:150px; height: 150px;"/>
			</td>
		</tr>
	</tbody>
</table>

<script type="text/javascript" src="${resourcepath}/admin/js/product/productlogo.js"></script>