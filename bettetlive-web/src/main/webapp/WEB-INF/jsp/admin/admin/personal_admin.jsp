<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改个人信息</title>
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
<script type="text/javascript" src="${resourcepath}/plugin/jquery.md5.js"></script>
<script type="text/javascript">

	$("#sex").val("${admin.sex }");
	var mainserver= '${mainserver}';
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
				<div title="修改管理员信息" data-options="closable:false" class="basic-info">
					<div class="column">
						<span class="current">基础信息</span>(<font color="red">*</font>&nbsp;是必填项)
					</div>
					<form id="specialform" name="specialform"
						action="${mainserver}/admin/personal/editAdmin"
						method="post" enctype="multipart/form-data">
						<input type="hidden" name="staffId" id="staffId" value="${admin.staffId}">
						<table class="kv-table">
							<tbody>
								<tr>
									<td class="kv-label">管理员名称:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
						
										<input id="username" name="username" value="${admin.username }" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									 	<span id="username_msg" style="color: red;"></span>
									</td>
								</tr>
								<tr>
									<td class="kv-label">登录账号:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
										<input id="loginname" name="loginname" value="${admin.loginname }" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									 	<span id="loginname_msg" style="color: red;"></span>
									</td>
								</tr>
								<tr>
									<td class="kv-label">登录密码:&nbsp;</td>
									<td class="kv-content" colspan="3">
										<input id="passwo" name="passwo" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
										<input id="password" name="password" value="${admin.password }" class="textbox"  maxlength="100" type="hidden" >
									 	<span id="password_msg" style="color: red;">如不修改登录密码请勿填写</span>
									</td>
								</tr>
								<tr>
									<td class="kv-label">手机号码:&nbsp;</td>
									<td class="kv-content" colspan="3">
										<input id="mobile" name="mobile" value="${admin.mobile }" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									</td>
								</tr>
								<tr>
									<td class="kv-label">性别:&nbsp;</td>
									<td class="kv-content" colspan="3"><select id="sex" name="sex" style="width:100px;height:31px;line-height:31px;">
											<option value="" selected="selected">请选择</option>
											<option value="0" >男</option>
											<option value="1" >女</option>
									</select>
									<script>
										$("#sex").val("${admin.sex}");
									</script>
									 </td>
								</tr>
								<tr>
									<td class="kv-label">头像:&nbsp;<font color="red">*</font></td>
									<td class="kv-content" colspan="3">
						
										<input type="hidden" name="headUrl"  id="headUrl" value="${admin.headUrl}">
										<img id="showHeadImg" style="width:100px;height:100px" src="${admin.headUrl}"><br>
										<input onchange="validateImgSize(this,1024*1024)" accept="image/gif, image/jpeg, image/png, image/bmp" id="touxiang" name="touxiang" type="file" class="textbox" style="height:35px;margin:0 0;width:220px;" maxlength="100" >
									 	<span id="headUrl" style="color: red;"></span>
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
												onclick="isSubmit()">修改</a>
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
	<script type="text/javascript" src="${resourcepath}/admin/js/admin/personal_admin.js"></script>
</body>
</html>