<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>挥货-修改专题</title>
    	<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
		 
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
			
			.waiteTea{
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
			
			.chooseTea{
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
				<div class="easyui-tabs1" style="width:100%;">
			      <div title="修改专题信息" data-options="closable:false" class="basic-info">
			      		<div class="column"><span class="current">基础信息</span>(<font color="red">*</font>&nbsp;是必填项)</div>
			      		<form id="specialform" method="post" enctype="multipart/form-data">
					      	<input type="hidden" name="specialId" id="specialId" value="${special.specialId }" />
					      	<input type="hidden" name="status" id="status" value="${special.status }" />
					      	<input type="hidden" name="activityType" value="2">
					      	<input type="hidden" name="homeFlag" id="homeFlag" value="0">
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">专题名称&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="textbox" type="text" name="specialName" id="specialName" value="${special.specialName }" data-options="required:true" missingMessage="不能为空" style="height:35px;margin:0 0;width:220px;" maxlength="100"/>
											<span id="Name_msg" style="color: red;"></span>
										</td>
										
										<td class="kv-label">专题标题&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="textbox" type="text" name="specialTitle" id="specialTitle" value="${special.specialTitle }"  style="height:35px;margin:0 0;width:220px;" maxlength="100"/>
											<span id="Title_msg" style="color: red;"></span>
										</td>
									</tr>
										
									<tr>
										<td class="kv-label">专题类型&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select id="specialType" name="specialType" style="height:35px;margin:0 0;width:220px;" >
												<c:if test="${special.specialType == 1 }">
													<option value="1" selected="selected">限时活动</option>
												</c:if>
											</select>
											<span id="Type_msg" style="color: red;"></span>
										</td>
										
										<td class="kv-label">专题页面（zip压缩包）&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-filebox" type="text" name="file1" id="specialPage" data-options="buttonText:'上传文件'" value="${special.specialPage }" style="height:35px;margin:0 0;width:220px;"/>
											<span id="Page_msg" style="color: red;"></span>
										</td> 
									</tr>
									
									<tr>
										<td class="kv-label">专题开始时间&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-datetimebox" type="text" name="startTime" id="startTime" value="${special.startTime }" style="height:35px;margin:0 0;width:220px;"/>
											<span id="Start_msg" style="color: red;"></span>
										</td>
										
										<td class="kv-label">专题结束时间&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-datetimebox" type="text" name="endTime" id="endTime" value="${special.endTime }" style="height:35px;margin:0 0;width:220px;"/>
											<span id="End_msg" style="color: red;"></span>
										</td> 
									</tr>
									
									<tr>
								<%-- 
										<td class="kv-label">推荐到首页&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<select name="homeFlag" id="homeFlag" style="height:35px;width: 120px;">
												<c:if test="${special.homeFlag == 1}">
													<option value="1" selected="selected">是</option>
													<option value="0">否</option>
												</c:if>
												<c:if test="${special.homeFlag == 0}">
													<option value="1">是</option>
													<option value="0" selected="selected">否</option>
												</c:if>
											</select>
											<span id="homeFlag_msg" style="color: red;">首页仅能展示一个专题</span>
										</td>
								--%>
										<td class="kv-label" colspan="3">专题介绍&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-textbox" type="text" name="specialIntroduce" id="specialIntroduce" value="${special.specialIntroduce }" style="height:35px;margin:0 0;width:90%;"/>
											<span id="Introduce_msg" style="color: red;"></span>
										</td>
									</tr>
								</tbody>
							</table> 
							<div class="column"><span class="current">选择产品</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">选择产品</div>
										</td>
										<td class="kv-content" colspan="5" >
											<input type="hidden"  name="notDispatchs"  id="notDispatchs"  readonly="readonly" value="${productIds }"/>
											<input class="easyui-textbox" type="text"  name="dispatching" id="dispatching" value="${productName }"  style="height:35px;margin:0 0;width:90%;" readonly="readonly" />
											<span class="addTea" onclick="chooseProduct()" >选择</span>											
											<span id="Dispatch_msg" style="color: red;"></span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="column" id="spec"><span class="current">产品规格--原价--专题价格</span></div>
							<table class="kv-table">
								<tbody id="spectbl">
									<tr>
										<td class="kv-label" style="width:200px">
											<div style="font-size: 12px;font-weight: bolder;" align="center">规格--原价--专题价格</div>
											<input type="hidden" name="speclist" id="speclist">
											<input type="hidden" name="productlist" id="productlist">
											<input type="hidden" name="activtylist" id="activtylist" value="${activtylistId }">
										</td>
										<td id="addSpec" class="kv-label" colspan="5" >
											
										</td>
									</tr>
								</tbody>
							</table>
							<div class="column"><span class="current">专题封面图片</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label" >专题封面图片&nbsp;<font color="red">*</font><br/>(720x316)</td>
										<td class="kv-content" colspan="3">
										<input  type="hidden" name="specialCover" id="specialCover" value="${special.specialCover }"/>
										<span id="specialCover1" style="color: red;"></span>
											<div id="zyupload">
											</div>
										</td>
										<td class="kv-label" colspan="2" id="pics">
											<img id="pimg" src="${special.specialCover }" width="160px"/>
										</td>
									</tr>
									<tr>
										<td colspan="5" align="center">
											<div style="margin-top: 20px;" align="center">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" onclick="isSubmit()">修改</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" data-options="selected:true" onclick="toBack()">返回</a>
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
		<div id="dlg" class="easyui-dialog" title="选择产品" data-options="closed:true" style="width:700px;height:500px;padding:10px;">
	      	<table class="kv-table">
				<tbody>
					<tr>
							<c:forEach items="${products}"  var="product">
			           			
			           			 <span class="waiteTea"  id="${product.product_id}" title="${product.product_name}"  onclick="addCreateProduct(this)" >${product.product_name}</span> 
			           		</c:forEach>						
							
							<div style="padding:35px" align="center">
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" onclick="isSureProduct()">确定</a>
								<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="selected:true" onclick="closeProduct()" >关闭</a>
                            </div>
					</tr>
				</tbody>	
			</table>
		</div>
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
		<script type="text/javascript">
			var mainServer= '${mainserver}';
		</script>
		<script type="text/javascript" src="${resourcepath}/admin/js/special/edit_special.js"></script>
	</body>
</html>