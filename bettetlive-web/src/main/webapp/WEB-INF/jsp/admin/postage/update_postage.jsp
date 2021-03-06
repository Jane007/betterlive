<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加商品标签</title>
<link rel="stylesheet" type="text/css"
	href="${resourcepath}/plugin/custom/uimaker/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${resourcepath}/plugin/custom/uimaker/icon.css">
<link rel="stylesheet" type="text/css"
	href="${resourcepath}/admin/css/base.css">
<link rel="stylesheet" type="text/css"
	href="${resourcepath}/admin/css/providers.css">
<link rel="stylesheet" type="text/css"
	href="${resourcepath}/admin/css/basic_info.css">
<link rel="stylesheet" type="text/css"
	href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
<script type="text/javascript"
	src="${resourcepath}/plugin/custom/jquery.min.js"></script>
<script type="text/javascript"
	src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
<script type="text/javascript"
	src="${resourcepath}/admin/js/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${resourcepath}/admin/js/application.js"></script>
<script type="text/javascript"
	src="${resourcepath}/admin/js/zyupload.party.js"></script>
<script type="text/javascript">
	var labelType = "${label.labelType}";
	var status = "${label.status}";
	var homeFlag = "${label.homeFlag}";
	var mainServer = "${mainserver}";
</script>
<style type="text/css">
label {
	cursor: pointer;
}

.edui-default .edui-editor {
	border: 1px solid #d4d4d4 !important;
}

.addTea {
	background: #1da02b;
	filter: none;
	color: #fff;
	border: 1px solid #1da02b;
	display: inline-block;
	position: relative;
	overflow: hidden;
	margin: 0;
	padding: 0;
	vertical-align: top;
	display: inline-block;
	vertical-align: top;
	width: 60px;
	border-radius: 5px 5px 5px 5px;
	line-height: 33px;
	font-size: 12px;
	padding: 0;
	margin: 0 4px;
	cursor: pointer;
	text-align: center;
}

.waiteTea {
	/* 	    background: #1da02b; */
	filter: none;
	color: #1F1F1F;
	border: 1px solid #1da02b;
	display: inline-block;
	position: relative;
	overflow: hidden;
	margin: 0;
	padding: 0;
	vertical-align: top;
	display: inline-block;
	vertical-align: top;
	width: 120px;
	border-radius: 5px 5px 5px 5px;
	line-height: 33px;
	font-size: 12px;
	padding: 0;
	margin: 0 4px;
	cursor: pointer;
	text-align: center;
	margin-top: 10px;
}

.chooseTea {
	background: #1da02b;
	filter: none;
	color: #fff;
	border: 1px solid #1da02b;
	display: inline-block;
	position: relative;
	overflow: hidden;
	margin: 0;
	padding: 0;
	vertical-align: top;
	display: inline-block;
	vertical-align: top;
	width: 120px;
	border-radius: 5px 5px 5px 5px;
	line-height: 33px;
	font-size: 12px;
	padding: 0;
	margin: 0 4px;
	cursor: pointer;
	text-align: center;
	margin-top: 10px;
}
</style>
</head>

<body>
	<div class="container">
		<div class="content">
			<div class="easyui-tabs1" style="width: 100%;">
				<div title="修改运费信息" data-options="closable:false" class="basic-info">
					<div class="column">
						<span class="current">基础信息</span>(<font color="red">*</font>&nbsp;是必填项)
					</div>
					<form id="specialform" name="specialform"
						action="${mainServer}/admin/postage/postagevo"
						method="post" enctype="multipart/form-data">
						<table class="kv-table">
							<tbody>
								<tr>
									<td class="kv-label">运费模板名称:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
										<input type="hidden" id="postageId" name="postageId" value="${postage.postageId}" >
										<input id="postageName" name="postageName" value="${postage.postageName}" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									 	<span id="postageName_msg" style="color: red;"></span>
									</td>
								</tr>
								<tr>
									<td class="kv-label">运费模板类型:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
										<select id="postageType" name="postageType"  style="width:100px;height:31px;line-height:31px;margin-right: 10px;">
				             				<option value="1" selected="selected">价格类</option>
				            			 </select>	
									 	<span id="roleType_msg" style="color: red;"></span>
									</td>
								</tr>
								<tr>
									<td class="kv-label">包邮的条件:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
										<input id="meetConditions" name="meetConditions" value="${postage.meetConditions}" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									 	<span id="meetConditions_msg" style="color: red;"></span>
									</td>
								</tr>
								<tr>
									<td class="kv-label">运费:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
										<input id="postage" name="postage" value="${postage.postage}" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									 	<span id="postage_msg" style="color: red;"></span>
									</td>
								</tr>
								<tr>
									<td class="kv-label">详情描述:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
										<input id="postageMsg" name="postageMsg" value="${postage.postageMsg}" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									 	<span id="postageMsg_msg" style="color: red;">用来在商品详情页展示,描述运费模板规则</span>
									</td>
								</tr>
							</tbody>
						</table>
						<table class="kv-table">
							<tbody>

								<tr>
									<td colspan="5" align="center">
										<div style="margin-top: 20px;" align="center">
											<a href="javascript:void(0);" class="easyui-linkbutton"
												iconCls="icon-add" data-options="selected:true"
												onclick="isSubmit()">修改</a> <a href="javascript:void(0);"
												class="easyui-linkbutton" iconCls="icon-back"
												data-options="selected:true" onclick="toBack()">返回</a>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${resourcepath}/admin/js/postage/update_postage.js"></script>
</body>
</html>