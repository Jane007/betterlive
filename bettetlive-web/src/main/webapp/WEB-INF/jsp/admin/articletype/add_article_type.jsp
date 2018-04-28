<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>挥货-增加专题文章分类</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
   		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
   		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
		<script type="text/javascript">
			var mainServer = "${mainserver}";
		</script>
	</head>
	
	<body>
		<div class="container">
			<div class="content">
				<div class="easyui-tabs1" style="width:100%;">
			      <div title="添加文章分类" data-options="closable:false" class="basic-info">
			      		<div class="column"><span class="current">基础信息</span>(<font color="red">*</font>&nbsp;是必填项)</div>
			      		<form id="dataform" name="dataform" method="post" enctype="multipart/form-data">
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">分类名称&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-textbox" type="text" name="typeName" id="typeName" style="height:35px;margin:0 0;width:220px;"/>
											<span id="name_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">排序&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-textbox" type="text" name="sort" id="sort" style="height:35px;margin:0 0;width:220px;"/>
											<span id="sort_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">状态&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select name="status" id="status" style="height:35px;width: 120px;">
												<option value="1" selected="selected">正常</option>
												<option value="0">失效</option>
											</select>
											<span id="status_msg" style="color: red;"></span>
										</td>
										<td class="kv-label"></td>
										<td class="kv-content">
										</td>
									</tr>
								</tbody>
							</table> 
							
							<div class="column"><span class="current">主页类型图片</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label" >主页类型图片&nbsp;<font color="red">*</font><br/></td>
										<td class="kv-content" colspan="3">
										<input  type="hidden" name="typeCover" id="typeCover" />
										<span id="typeCover1" style="color: red;"></span>
											<div id="zyupload">
											</div>
										</td>
										<td class="kv-label" colspan="2" id="pics">
										</td>
									</tr>
								</tbody>
							</table>
                    		<div style="margin-top: 20px;" align="center">
								<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" onclick="isSubmit()">新增</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" data-options="selected:true" onclick="toBack()">返回</a>
							</div>
						</form>
			      </div> 
			    </div>
			</div>
		</div>
		
		<script type="text/javascript" src="${resourcepath}/admin/js/qn/qiniu.min.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/articletype/add_article_type.js"></script>
	</body>
</html>