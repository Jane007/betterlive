<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>挥货-修改拼团活动</title>
    	<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
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
			      <div title="修改活动信息" data-options="closable:false" class="basic-info">
			      		<div class="column"><span class="current">基础信息</span>(<font color="red">*</font>&nbsp;是必填项)</div>
			      		<form id="specialform"  method="post" enctype="multipart/form-data">
					      	<input type="hidden" name="specialId" id="specialId" value="${special.specialId }" />
					      	<input type="hidden" name="status" id="status" value="${special.status}" />
			      	 		<input type="hidden" id="specialType" name="specialType" value="${specialType}">
							<input type="hidden" id="activityType" name="activityType" value="${activityType}">
			      			<input id="productId" name="productId" type="hidden" value="${productId}">
			      			<input id="groupId" name="groupId" type="hidden" value="${sysGroup.groupId}">
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">拼团活动标题&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="textbox" type="text" name="specialTitle" id="specialTitle" value="${special.specialTitle }"  style="height:35px;margin:0 0;width:220px;" maxlength="100"/>
											<span id="Title_msg" style="color: red;"></span>
										</td>
								<%--	<td class="kv-label">销量&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-textbox" type="text" name="fakeSalesCopy" id="fakeSalesCopy" style="height:35px;margin:0 0;width:220px;" value="${special.fakeSalesCopy}"/>
											<span id="fakeSalesCopy_msg" style="color: red;">不填则显示真实销量</span>
										</td>
								--%>
										<td class="kv-label">商品排序</td>
										<td class="kv-content" colspan="3">
											<input class="numberbox" type="text" name="specialSort" id="specialSort" style="height:35px;margin:0 0;width:15%;" onkeyup="value=value.replace(/[^\d]/g,'')" value="${special.specialSort}"/>
											数字越小排位越前，从0开始排序
											<span id="special_short_msg" style="color: red;"></span>
										</td>
										
									</tr>
									<tr>
										<td class="kv-label">开团数量&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="textbox" type="text" name="groupCopy" id="groupCopy" value="${sysGroup.groupCopy}" style="height:35px;margin:0 0;width:220px;" maxlength="100"/>
											<span id="groupCopy_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">拼团人数&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-textbox" type="text" name="limitCopy" id="limitCopy" value="${sysGroup.limitCopy}" style="height:35px;margin:0 0;width:220px;"/>
											<span id="limitCopy_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">拼团活动开始时间&nbsp;<font color="red">*</font></td>
										<td class="kv-content">
											<input class="easyui-datetimebox" type="text" name="startTime" id="startTime" value="${special.startTime }" style="height:35px;margin:0 0;width:220px;"/>
											<span id="Start_msg" style="color: red;"></span>
										</td>
										
										<td class="kv-label">拼团活动结束时间&nbsp;<font color="red">*</font></td>
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
											<span id="homeFlag_msg" style="color: red;">首页仅能展示一个拼团活动</span>
										</td>
									--%>
										<td class="kv-label">拼团活动介绍&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="3">
											<input class="easyui-textbox" type="text" name="specialIntroduce" id="specialIntroduce" value="${special.specialIntroduce }" style="height:35px;margin:0 0;width:90%;"/>
											<span id="Introduce_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">活动规则一&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="3">
											<input type="text" name="desc1" id="desc1" style="width: 60%;" value="${sysGroup.desc1}">
											<span id="rul1_msg" style="color: red;">最多4点规则描述</span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">活动规则二&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="3">
											<input type="text" name="desc2" id="desc2" style="width: 60%;" value="${sysGroup.desc2}">
											<span id="rul1_msg" style="color: red;">最多4点规则描述</span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">活动规则三&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="3">
											<input type="text" name="desc3" id="desc3" style="width: 60%;" value="${sysGroup.desc3}">
											<span id="rul1_msg" style="color: red;">最多4点规则描述</span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">活动规则四&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="3">
											<input type="text" name="desc4" id="desc4" style="width: 60%;" value="${sysGroup.desc4}">
											<span id="rul1_msg" style="color: red;">最多4点规则描述</span>
										</td>
									</tr>
									
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
											<input type="hidden"  name="notDispatchs"  id="notDispatchs"  readonly="readonly" value="${productId}"/>
											<input class="easyui-textbox" type="text"  name="dispatching" id="dispatching" value="${productName }"  style="height:35px;margin:0 0;width:90%;" readonly="readonly" />
<!-- 											<span class="addTea" onclick="chooseProduct()" >选择</span>											 -->
											<span id="Dispatch_msg" style="color: red;"></span>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="column" id="spec"><span class="current">产品规格--原价--拼团活动价格</span></div>
							<table class="kv-table">
								<tbody id="spectbl">
									<tr>
										<td class="kv-label" style="width:200px">
											<div style="font-size: 12px;font-weight: bolder;" align="center">规格--原价--拼团活动价格</div>
											<input type="hidden" name="speclist" id="speclist">
											<input type="hidden" name="productlist" id="productlist">
											<input type="hidden" name="activtylist" id="activtylist" value="${activtylistId }">
										</td>
										<td id="addSpec" class="kv-label" colspan="5" >
											
										</td>
									</tr>
								</tbody>
							</table>
							<div class="column"><span class="current">拼团活动封面图片</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label" >拼团活动封面图片&nbsp;<font color="red">*</font><br/>(720x316)</td>
										<td class="kv-content" colspan="3">
										<input  type="hidden" name="specialCover" id="specialCover" value="${special.specialCover}"/>
										<span id="specialCover1" style="color: red;"></span>
											<div id="zyupload">
											</div>
										</td>
										<td class="kv-label" colspan="2" id="pics">
											<img id="pimg" src="${special.specialCover}" width="160px"/>
										</td>
									</tr>
									<%--
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">拼团列表图片</div>
										</td>
										<td class="kv-content" colspan="5" >
											<input class="easyui-filebox" type="text" name="group_img" id="group_img" style="height:35px;width:60px;"
						                      data-options="buttonText:'选择图片',accept:'image/jpeg,image/png,image/gif,image/JPEG,image/PNG,image/GIF', onChange:function(){previewImg($(this));}"/>
												<span style="color: red;"></span><div id="listImgShow"><img style="width:60px;height:60px;" src="${special.listImg}"></div>
										</td>
									</tr>
									--%>
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
	      	<table id="StoreGrid" style="width:100%;height:529px;" title="商品列表">
	        
	      	</table>
	      
		      <div id="storeToolbar" style="padding:0 30px;">
		      		<div id="tb" style="padding:0 30px;">
					             商品名称: <input class="easyui-textbox" type="text" name="product_name"  id="product_name" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
					                    
					       	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchProduct()" >查询</a>
			      	</div>
		     </div>
		</div>
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.drag-1.0.0.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/zyupload.party.js"></script>
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/qn/qiniu.min.js"></script>
		<script type="text/javascript">
		var mainServer = '${mainserver}';
		</script>
		<script type="text/javascript" src="${resourcepath}/admin/js/special/edit_group_special.js"></script>
	</body>
</html>