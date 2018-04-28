<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/jquery.draft.css">
<style>
.bannerShow li{
	padding:1px;
	width:100px;
	height: 60px;
	border:1px solid #ccc;
	margin-bottom:25px;
}
.bannerShow li div.action{
	margin-top:4px;
	margin-right:4px;
	text-align:right;
}

.bannerShow img {
	width: 100px;
	height: 60px;
}
</style>
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>

<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/product/jquery.draft.min.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/jquery.sortable.js"></script>
<script type="text/javascript">
	var mainServer = '${mainserver}';
</script>


<table class="kv-table">
	<tbody>
		<tr>
			<td class="kv-label" style="width: 10%">
				商品轮播图</br>(720x476)&nbsp;<font color="red">*</font>
				<span id="probanner_msg" style="color: red;"></span>
			</td>
			<td class="kv-content" colspan="2" style="width: 40%">
				<input  type="hidden" name="pictureArray" id="pictureArray"/>
				<span id="bannerUploadMsg" style="color: red;"></span>
				<div id="bannerUpload">
				</div>
			</td>
			
			<td class="kv-label" colspan="3" style="width: 50%">
				<ul id="bannerShow" class="bannerShow">
				<c:forEach items="${pictures }" var="pic">
					<li data-picid="${pic.picture_id}" data-sort="${pic.sort}">
					<img alt="" src="${pic.original_img }"><div class="action">&nbsp;<a class="del">删除</a></div> 
					</li>
				</c:forEach>
				<!-- 
					<span class="display:block;" attr-id="${pic.picture_id}"> 
						<a onclick="deleteImg(${pic.picture_id}, this)">删除</a>
					</span>
				 -->
				</ul>				
			</td>
		</tr>
	</tbody>
</table>
<script type="text/javascript" src="${resourcepath}/admin/js/product/productbanner.js"></script>
