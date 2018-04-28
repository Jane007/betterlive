<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>挥货-增加文章期刊</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
	
		<!-- 富文本编辑器 -->
		<script type="text/javascript">
			var mainServer = '${mainserver}';
			var resourcepath='${resourcepath}';
		</script>
		
		<style type="text/css">
			label{
				cursor:pointer;
			}
			
		</style>   
	</head>
	
	<body>
		<div class="container">
			<div class="content">
				<div class="easyui-tabs1" style="width:100%;">
			      <div title="添加文章期刊" data-options="closable:false" class="basic-info">
			      		<form id="postForm" name="postForm" method="post" enctype="multipart/form-data">
			      			<input name="status" value="1" type="hidden">
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">期数&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="textbox" type="text" name="periodical" id="periodical" placeholder="如：第1期"
											style="height:35px;margin:0 0;width:100px;" maxlength="100"/>
											<span id="periodical_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">期刊主题&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-textbox" type="text" name="periodicalTitle" id="periodicalTitle" style="height:35px;margin:0 0;width:220px;"/>
											<span id="title_msg" style="color: red;"></span>
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
		
		<script type="text/javascript" src="${resourcepath}/admin/js/periodical/add_article_periodical.js"></script>
	</body>
</html>