<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>挥货-增加专题文章</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
	
		<!-- 富文本编辑器 -->
		<script type="text/javascript">
			var mainServer = '${mainserver}';
			var resourcepath='${resourcepath}';
			var qiniulink = '${qiniulink}';
			window.EDITOR_INIT_SPRING_PATH = mainServer;
			window.UEDITOR_HOME_URL = window.EDITOR_INIT_SPRING_PATH + "/resources/admin/js/ueditor/";
		</script>
		
		<style type="text/css">
			label{
				cursor:pointer;
			}
			
			.edui-default .edui-editor{
				border: 1px solid #d4d4d4 !important;
			}
		
			.addTea{
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
		</style>   
	</head>
	
	<body>
		<div class="container">
			<div class="content">
				<div class="easyui-tabs1" style="width:100%;">
			      <div title="添加专题文章信息" data-options="closable:false" class="basic-info">
			      		<div class="column"><span class="current">基础信息</span>(<font color="red">*</font>&nbsp;是必填项)</div>
			      		<form id="specialform" name="specialform" method="post" enctype="multipart/form-data">
			      			<input type="hidden" id="articleFrom" name="articleFrom" value="0">
			      			<input type="hidden" id="recommendMore" name="recommendMore" value="0">
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">文章标题&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="textbox" type="text" name="articleTitle" id="articleTitle"  style="height:35px;margin:0 0;width:220px;" maxlength="100"/>
											<span id="Title_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">作者昵称&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-textbox" type="text" name="author" id="author" style="height:35px;margin:0 0;width:220px;"/>
											<span id="author_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">推荐到首页&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select name="homeFlag" id="homeFlag" style="height:35px;width: 120px;">
												<option value="1" selected="selected">是</option>
												<option value="0">否</option>
											</select>
											<span id="homeFlag_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">文章类型&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select id="articleTypeId" name="articleTypeId" style="height:35px;width: 120px;">
												<option value="0">未选择</option>
											<c:forEach var="sat" items="${types}">
												<option value="${sat.typeId}">${sat.typeName}</option>
											</c:forEach>
											</select>
											<span id="articleTypeId_msg" style="color: red;">如果不推荐到精选文章分类专区，则不需要选择</span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">发布状态&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select id="status" name="status" style="height:35px;width: 120px;">
												<option value="1">发布</option>
												<option value="2">草稿箱</option>
											</select>
										</td>
										<td class="kv-label">所属期刊</td>
										<td class="kv-content">
											<select id="periodicalId" name="periodicalId" style="height:35px;width: 120px;">
												<option value="0">未选择</option>
												<c:forEach var="periodicalVo" items="${articlePeriodicals}">
													<option value="${periodicalVo.periodicalId}">${periodicalVo.periodical}</option>
												</c:forEach>
											</select>
											<span id="periodicalId_msg" style="color: red;">如果不推荐到精选文章期刊，则不需要选择</span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">推荐首页排序</td>
										<td class="kv-content">
											<input type="text" id="homeSorts" name="homeSorts" value="0">
											<span id="homeSorts_msg" style="color: red;">如果推荐到首页可以排序，升序</span>
										</td>
										<td class="kv-label">每日推荐&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select name="recommendFlag" id="recommendFlag" style="height:35px;width: 120px;">
												<option value="1" selected="selected">是</option>
												<option value="0">否</option>
											</select>
											<span id="recommendFlag_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">专题文章简介&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="3">
											<input class="easyui-textbox" type="text" name="articleIntroduce" id="articleIntroduce" style="height:35px;margin:0 0;width:90%;"/>
											<span id="Introduce_msg" style="color: red;"></span>
										</td>
									</tr>
								</tbody>
							</table> 
							
							<div class="column"><span class="current">关联产品（非必选项）</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">选择产品</div>
										</td>
										<td class="kv-content" colspan="5" >
											<input type="hidden"  name="productIds"  id="productIds"  readonly="readonly"/>
											<input class="easyui-textbox" type="text"  name="productNames" id="productNames"
													style="height:35px;margin:0 0;width:80%;" readonly="readonly" />
											<span class="addTea" onclick="chooseProduct()" >选择</span>
											<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="removeProd" onclick="removeProduct()">清空</a>											
											<span id="choicepro_msg" style="color: red;"></span>
										</td>
									</tr>
								</tbody>
							</table>
				<%-- 			
							<div class="column"><span class="current">精选主页列表封面图片</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label" >主页列表封面图&nbsp;<font color="red">*</font><br/></td>
										<td class="kv-content">
											<input class="easyui-filebox" type="text" name="homeCover" id="homeCover"
						                      data-options="buttonText:'选择图片',accept:'image/jpeg,image/png,image/gif,image/JPEG,image/PNG,image/GIF', onChange:function(){previewImg($(this));}"/>
												<span style="color: red;"></span><div class="homeCoverShow"></div>
										</td>
									</tr>
								</tbody>
							</table>
				--%>			
							<div class="column"><span class="current">文章封面图片</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label" >文章封面图片&nbsp;<font color="red">*</font><br/>(720x316)</td>
										<td class="kv-content" colspan="3">
										<input  type="hidden" name="articleCover" id="articleCover" />
										<span id="articleCover1" style="color: red;"></span>
											<div id="zyupload">
											</div>
										</td>
										<td class="kv-label" colspan="2" id="pics">
										</td>
									</tr>
								</tbody>
							</table>
							
							<div class="column"><span class="current">专题文章内容详情</span></div>
							<script id="editor-state" name="content" type="text/plain">
							
                    		</script>
                    		
                    		<div style="margin-top: 20px;" align="center">
								<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" onclick="isSubmit()">新增</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" data-options="selected:true" onclick="toBack()">返回</a>
							</div>
						</form>
			      </div> 
			    </div>
			</div>
		</div>
		
		 <div id="productdlg" class="easyui-dialog" title="选择产品" data-options="closed:true" style="width:800px;height:560px;padding:10px;">
	      	 <table id="dataGrid" style="width:100%;height:229px;" title="产品列表">
      		 </table>
      		 
      		 <div id="storeToolbar" style="padding:0 30px;">
	      		<div id="tb" style="padding:0 30px;">
	      			<div style="float: left;">
				             产品名称: <input class="easyui-textbox" type="text" name="productName"  id="productName" maxlength="30" style="width:166px;height:35px;line-height:35px;" />
				                    
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProduct()" >查询</a>
			       </div>
			       <div style="float: right;">
				       <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" onclick="saveProduct()">保存</a>
					   <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" data-options="selected:true" onclick="closeProductdilg()">关闭</a>
					</div>
		      	</div>
	     	</div>
		</div>
		
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/qn/qiniu.min.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/article/add_special_article.js"></script>
	</body>
</html>