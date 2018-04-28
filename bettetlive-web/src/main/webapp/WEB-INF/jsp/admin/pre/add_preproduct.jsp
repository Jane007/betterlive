<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>增加预售商品</title>
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
		

	</head>
	
	<body>
		<div class="container">
			<div class="content">
				<div class="easyui-tabs1" style="width:100%;">
			      <div title="添加预售商品信息" data-options="closable:false" class="basic-info">
			      		<div class="column"><span class="current">基础信息</span>&nbsp;(<font color="red">*</font>&nbsp;是必填项)</div>
			      		<form id="productform"  action="${mainServer}/admin/preproduct/addPreProduct"  method="post" enctype="multipart/form-data">
					      	<input type="hidden" name="discountPrice" value="0">
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">预售标题&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="textbox" type="text" name="preName" id="preName" style="height:35px;margin:0 0;width:220px;" maxlength="100"/>
											<span id="Name_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">产品名称&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<select id="productId" name="productId" style="height:35px;margin:0 0;width:220px;" onchange="chooseSpec()">
												<option value="" selected="selected">请选择</option>
												<c:forEach items="${products }" var="product">
													<option value="${product.product_id }">${product.product_name }</option>
												</c:forEach>
											</select>
											<span id="Product_msg" style="color: red;"></span>
										</td>
									
									<tr>
										<td class="kv-label">预售开始时间&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="easyui-datetimebox" type="text" name="raiseStart" id="raiseStart"  style="height:35px;margin:0 0;width:220px;"/>
											<span id="Start_msg" style="color: red;"></span>
										</td>
										
										<td class="kv-label">预售结束时间&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="easyui-datetimebox" type="text" name="raiseEnd" id="raiseEnd"  style="height:35px;margin:0 0;width:220px;"/>
											<span id="End_msg" style="color: red;"></span>
										</td> 
									</tr>
									
									
									<tr>
										<td class="kv-label">已筹金额&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="easyui-numberbox" type="text" name="raiseMoney" id="raiseMoney" style="height:35px;margin:0 0;width:30%;" maxlength="10"/>元
											<span id="Money_msg" style="color: red;"></span>
										</td>
											
										<td class="kv-label">众筹目标&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="easyui-numberbox" type="text" name="raiseTarget" id="raiseTarget" style="height:35px;margin:0 0;width:30%;" maxlength="10"/>元
											<span id="Target_msg" style="color: red;"></span>
										</td> 
										
									</tr>
									
									
									
									
									<tr>
										<td class="kv-label">支持人数&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="numberbox" type="text" name="supportNum" id="supportNum" style="height:35px;margin:0 0;width:20%;" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>&nbsp;位
											<span id="Time_msg" style="color: red;"></span>
										</td> 
										
										<td class="kv-label">发货时间&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="easyui-datetimebox" type="text" name="deliverTime" id="deliverTime" style="height:35px;margin:0 0;width:220px;"/>
											<span id="Deliver_msg" style="color: red;"></span>
										</td>
										
										
									</tr>
									<tr>	
										<td class="kv-label">排序</td>
										<td class="kv-content" colspan="2">
											<input class="numberbox" type="text" name="rankOrder" id="rankOrder" style="height:35px;margin:0 0;width:20%;" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
											<span id="Order_msg" style="color: red;"></span>
										</td> 
										
										
									</tr>
									<tr>
										<td class="kv-label" colspan="8">
											<div style="font-size: 14px;font-weight: bolder;" align="center">
												<div>规格与预售价格 </div>
												<div style="margin-top: 15px;">
												          规格<input type="text"  style="color: red;width: 50px;" value="5千克"  readonly="readonly"/>—
													产品价格<input type="text"  style="color: red;width: 40px;" value="120" readonly="readonly" /> 元—
													预售价格(必填)<input type="text" style="color: red;width: 40px;" value="118" readonly="readonly" /> 元</font>
												</div>
											</div>
										</td>
									</tr>
									<tr style="height: 170px;" >     
										<td class="kv-label"> 最多8个: &nbsp;<font color="red">*</font></td>
										<td class="kv-content" align="left" colspan="8">
											<input type="hidden"  name="teaTypeLength"  id="teaTypeLength" value="0"/>
											<div id="product_1" style="float:left;" align="left">
													
											</div>
											<div id="product_2" style="float:left;margin-top: 10px;" align="left">
												
											</div>
											<div id="product_3" style="float:left;margin-top: 10px;" align="left">
												
											</div>
										</td>
										
									</tr>	
					
								</tbody>
							</table>
							
							<div class="column"><span class="current">预售封面图片</span></div>
							<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">预售封面图片&nbsp;<font color="red">*</font></br>
											<span id="productLogo_msg" style="color: red;"></span>
										</td>
										<td class="kv-content" colspan="3">
											<input  type="hidden" name="productLogo" id="productLogo" />
											<span id="productLogo1" style="color: red;"></span>
											<div id="zyupload">
											</div>
										</td>
										<td class="kv-label" colspan="2" id="pics">
											<img id="pimg" src="" width="80%"/>
										</td>
									</tr>
								</tbody>
							</table>
							
							<table class="kv-table">
								<tbody>
									<tr>
										<td colspan="5" align="center">
											<div style="margin-top: 20px;" align="center">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" onclick="isSubmit()">新增</a>
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
		
		
	
		
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/qn/qiniu.min.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/pre/add_preproduct.js"></script>
		<script type="text/javascript">
		var mainServer = '${mainserver}';
		</script>
	</body>
</html>