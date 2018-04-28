<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>极光推送</title>
<link rel="stylesheet" type="text/css" href="${resourcepath}/plugin/custom/uimaker/easyui.css">
<link rel="stylesheet" type="text/css" href="${resourcepath}/plugin/custom/uimaker/icon.css">
<link rel="stylesheet" type="text/css" href="${resourcepath}/admin/css/base.css">
<link rel="stylesheet" type="text/css" href="${resourcepath}/admin/css/providers.css">
<link rel="stylesheet" type="text/css" href="${resourcepath}/admin/css/basic_info.css">
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
<script src="${resourcepath}/admin/js/jquery-form.js"></script>
<script type="text/javascript">
	var mainServer = "${mainserver}";
</script>


<style type="text/css">
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
			<div class="easyui-tabs1" style="width: 100%;">
				<div title="极光推送" data-options="closable:false" class="basic-info">
					<div class="column">
						<span class="current">填写推送内容</span>
					</div>
					<form id="pushform" name="pushform" action="${mainserver}/admin/jpush/pushData" method="post">
						<input type="hidden" name="specialId" id="specialId" readonly="readonly">
						<input type="hidden" name="articleId" id="articleId" readonly="readonly">
						<input type="hidden" name="articleTitle" id="articleTitle" readonly="readonly">
						<input type="hidden" name="productId" id="productId" readonly="readonly">
						<input type="hidden" name="userGroupId" id="userGroupId" readonly="readonly">
						<input type="hidden" name="customerId" id="customerId" readonly="readonly">
						<table class="kv-table">
							<tbody>
								<tr>
									<td class="kv-label">推送类型:&nbsp;<font color="red">*</font></td>
									<td class="kv-content">
										<select id="pushTypeId" name="pushTypeId" style="height:35px;margin:0 0;width:220px;" onchange="resetForm()">
											<option value="0">专题活动</option>
											<option value="1">动态文章</option>
											<option value="2">精选文章</option>
											<option value="3">视频</option>
											<option value="4">商品</option>
											<option value="5">团购</option>
											<option value="6">正在开的团</option>
											<option value="7">限量抢购</option>
											<option value="8">限量抢购列表</option>
											<option value="9">团购列表</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="kv-label">关联活动&nbsp;<font color="red">*</font></td>
									<td class="kv-content">
										<input class="easyui-textbox" type="text" id="objName"  style="height:35px;margin:0 0;width:90%;" readonly="readonly" />
										<span class="addTea" onclick="chooseObject()" >选择</span>		
									</td>
								</tr>
<%-- 								<tr> 
	 									<td class="kv-label">推送标题:&nbsp;<font color="red">*</font></td>
	 									<td class="kv-content"> 
	 										<input type="text" id="pushTitle" name="pushTitle" style="width: 90%;height:35px;">
	 									</td>
	 								</tr> --%>
								<tr>
									<td class="kv-label">推送内容:&nbsp;<font color="red">*</font></td>
									<td class="kv-content">
										<textarea id="pushContent" name="pushContent" rows="" cols=""
											style="width: 90%;height:300px;" maxlength="100"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
						<table class="kv-table">
							<tbody>

								<tr>
									<td colspan="5" align="center">
										<div style="margin-top: 20px;" align="center">
											<a id="subId" href="javascript:void(0);" class="easyui-linkbutton"
												iconCls="icon-add" data-options="selected:true"
												onclick="isSubmit()">发送</a>
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

	<div id="dialogSpecial" class="easyui-dialog" title="专题活动" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="specialGrid" style="width:100%;">
	      </table>
	      
	      <div id="specialToolbar" style="padding:0 30px;">
	      		<div id="tb1" style="padding:0 30px;">
					   专题标题: <input class="easyui-textbox" type="text"  id="specialName" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchSpecial()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	<div id="dialogCircleArticle" class="easyui-dialog" title="动态文章" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="circleArticleGrid" style="width:100%;">
	      </table>
	      
	      <div id="circleArticleToolbar" style="padding:0 30px;">
	      		<div id="tb2" style="padding:0 30px;">
					  文章标题: <input class="easyui-textbox" type="text"  id="circleTitle" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchCircleArticle()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	<div id="dialogGoodArticle" class="easyui-dialog" title="精选文章" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="goodArticleGrid" style="width:100%;">
	      </table>
	      
	      <div id="goodArticleToolbar" style="padding:0 30px;">
	      		<div id="tb3" style="padding:0 30px;">
					  文章标题: <input class="easyui-textbox" type="text"  id="goodTitle" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchGoodArticle()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	<div id="dialogVideo" class="easyui-dialog" title="视频列表" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="videoGrid" style="width:100%;">
	      </table>
	      
	      <div id="videoToolbar" style="padding:0 30px;">
	      		<div id="tb4" style="padding:0 30px;">
					  视频标题: <input class="easyui-textbox" type="text"  id="videoName" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchVideoArticle()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	<div id="dialogProduct" class="easyui-dialog" title="商品列表" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="productGrid" style="width:100%;">
	      </table>
	      
	      <div id="productToolbar" style="padding:0 30px;">
	      		<div id="tb5" style="padding:0 30px;">
					  商品名称: <input class="easyui-textbox" type="text"  id="productName" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProduct()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	<div id="dialogGroup" class="easyui-dialog" title="团购列表" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="groupGrid" style="width:100%;">
	      </table>
	      
	      <div id="groupToolbar" style="padding:0 30px;">
	      		<div id="tb6" style="padding:0 30px;">
					  团购活动名称: <input class="easyui-textbox" type="text"  id="specialName" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchGroup()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	<div id="dialogJoinGroup" class="easyui-dialog" title="正在开的团" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="joinGroupGrid" style="width:100%;">
	      </table>
	      
	      <div id="joinGroupToolbar" style="padding:0 30px;">
	      		<div id="tb7" style="padding:0 30px;">
					  团购活动名称: <input class="easyui-textbox" type="text"  id="joinGroupName" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchJoinGroup()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	<div id="dialogLimit" class="easyui-dialog" title="限量抢购" data-options="closed:true,modal:true" style="width:730px;height:550px;padding:5px;">
		  <table id="limitGrid" style="width:100%;">
	      </table>
	      
	      <div id="limitToolbar" style="padding:0 30px;">
	      		<div id="tb8" style="padding:0 30px;">
					  限量抢购活动名称: <input class="easyui-textbox" type="text"  id="limitName" maxlength="40" style="width:166px;height:35px;line-height:35px;"></input>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchSpecialLimit()" >查询</a>
		      	</div>
		       
	     </div>
	</div>
	
	<script type="text/javascript" src="${resourcepath}/admin/js/jpush/push_data.js"></script>
</body>
</html>