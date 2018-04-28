<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>挥货-修改社区文章</title>
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
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/jquery.sortable.js"></script>
		
		<script type="text/javascript">
			var mainServer = '${mainserver}';
			var resourcepath='${resourcepath}';
			var qiniulink = '${qiniulink}';
			var status = "${article.status}";
			var articleTypeId = "${article.articleTypeId}";
			var articleContent = "${article.content}";
		</script>
		
		<style type="text/css">
			label{
				cursor:pointer;
			}
			.bannerShow li{
				padding:1px;
				width:100px;
				height: 60px;
				border:1px solid #ccc;
				margin-bottom:25px;
			}
			.bannerShow li.mainPic{
				border:2px solid red;
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
			.txteare{
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
			      <div title="修改社区文章信息" data-options="closable:false" class="basic-info">
			      		<div class="column"><span class="current">基础信息</span>(<font color="red">*</font>&nbsp;是必填项)</div>
			      		<form id="specialform"  method="post" enctype="multipart/form-data">
				      	 	<input type="hidden" name="articleId" id="articleId" value="${article.articleId }" />
				      	 	<input type="hidden" id="articleFrom" name="articleFrom" value="1">
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">社区文章标题&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="textbox" type="text" name="articleTitle" id="articleTitle" value="${article.articleTitle }"  style="height:35px;margin:0 0;width:220px;" maxlength="100"/>
											<span id="Title_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">作者昵称&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											${article.author}
										</td>
									</tr>
									<tr>
										<td class="kv-label">推荐到首页&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select name="homeFlag" id="homeFlag" style="height:35px;width: 120px;">
												<c:if test="${article.homeFlag == 1}">
													<option value="1" selected="selected">是</option>
													<option value="0">否</option>
												</c:if>
												<c:if test="${article.homeFlag == 0}">
													<option value="1">是</option>
													<option value="0" selected="selected">否</option>
												</c:if>
											</select>
											<span id="homeFlag_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">发布状态&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select id="status" name="status" style="height:35px;width: 120px;">
									 			<option value="3">待审核</option>
								             	<option value="1">审核通过</option>
							             	 	<option value="4">审核未通过</option>
							             	 	<option value="0">已删除</option>
											</select>
											<span id="status_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">文章类型</td>
										<td class="kv-content">
											<select id="articleTypeId" name="articleTypeId" style="height:35px;width: 120px;">
												<option value="0">未选择</option>
												<c:forEach var="sat" items="${types}">
													<option value="${sat.typeId}">${sat.typeName}</option>
												</c:forEach>
											</select>
											<span id="articleTypeId_msg" style="color: red;">如果不推荐到精选文章分类专区，则不需要选择</span>
										</td>
										<td class="kv-label">所属期刊</td>
										<td class="kv-content">
											<select id="periodicalId" name="periodicalId" style="height:35px;width: 120px;">
												<option value="0">未选择</option>
												<c:forEach var="periodicalVo" items="${articlePeriodicals}">
													<option value="${periodicalVo.periodicalId}" 
														<c:if test="${periodicalVo.periodicalId == article.periodicalId}">selected="selected"</c:if>>
															${periodicalVo.periodical}
													</option>
												</c:forEach>
											</select>
											<span id="periodicalId_msg" style="color: red;">如果不推荐到精选文章期刊，则不需要选择</span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">是否推荐到更多动态</td>
										<td class="kv-content">
											<select id="recommendMore" name="recommendMore" style="height:35px;width: 120px;">
												<c:if test="${article.recommendMore == 1}">
										 			<option value="0">否</option>
									             	<option value="1" selected="selected">是</option>
								             	</c:if>
								             	<c:if test="${article.recommendMore == 0}">
										 			<option value="0" selected="selected">否</option>
									             	<option value="1">是</option>
								             	</c:if>
											</select>
											<span id="recommendMore_msg" style="color: red;"></span>
										</td>
										
										<td class="kv-label">推荐首页排序</td>
										<td class="kv-content">
											<input type="text" id="homeSorts" name="homeSorts" value="${article.homeSorts}">
											<span id="homeSorts_msg" style="color: red;">如果推荐到首页可以排序，升序</span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">文章简介&nbsp;</td>
										<td class="kv-content" colspan="3">
											<input class="easyui-textbox" type="text" name="articleIntroduce" id="articleIntroduce" style="height:35px;margin:0 0;width:90%;" value="${article.articleIntroduce}"/>
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
											<input type="hidden"  name="productIds"  id="productIds"  readonly="readonly" value="${productIds}"/>
											<input class="easyui-textbox" type="text"  name="productNames" id="productNames" value="${productNames}"
													style="height:35px;margin:0 0;width:80%;" readonly="readonly" />
											<span class="addTea" onclick="chooseProduct()" >选择</span>
											<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="removeProd" onclick="removeProduct()">清空</a>											
											<span id="choicepro_msg" style="color: red;"></span>
										</td>
									</tr>
								</tbody>
							</table>
							
							<div class="column"><span class="current">社区文章图片-默认第一张是封面图</span>&nbsp;<font color="red">*</font></div>
					  		<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label" style="width: 10%">
											社区文章图片</br>(720x476)&nbsp;<font color="red">*</font>
											<span id="banner_msg" style="color: red;"></span>
										</td>
										<td class="kv-content" colspan="2" style="width: 40%">
											<!-- 用来处理回显封面图 -->
											<input  type="hidden" id="articleCover" value="${article.articleCover }"/>
											<input  type="hidden" name="pictureArray" id="pictureArray" value="${pictureArray}"/>
											<span id="bannerUploadMsg" style="color: red;"></span>
											<div id="bannerUpload">
											</div>
										</td>
										<td class="kv-label" colspan="3" style="width: 50%">
											<!-- 默认封面为第一张图 -->
											<input type="hidden" name="mainPicIndex" id="mainPicIndex" value="0">
											<ul id="bannerShow" data-items="li" class="bannerShow">
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="column"><span class="current">社区文章内容详情</span>&nbsp;<font color="red">*</font></div>
                   			<textarea class="txteare" id="content" name="content" placeholder="写下你的心得吧..." rows="" cols="" style="width: 97%;height:400px;"></textarea> 	
                   			<span id="content_msg" style="color: red;"></span>
                   			
                    			<div style="margin-top: 20px;text-align: center;">
									<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" onclick="isSubmit()">修改</a>
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
		<script type="text/javascript" src="${resourcepath}/admin/js/qn/qiniu.min.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/article/edit_circle_article.js"></script>
	</body>
</html>