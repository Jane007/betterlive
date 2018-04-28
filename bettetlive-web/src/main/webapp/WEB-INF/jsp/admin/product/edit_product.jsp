<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>更新商品</title>
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
			var productId= "${productId}";
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
			/*商品规格项*/
			.prospec_item {
				height: 100px; line-height: 50px;
				margin-top:10px;
			}
			/*商品规格预览*/
			.specImgPrev {  
				width: 50px; height: 50px; display: inline; float:right;
			}
			.specImgPrev img {  
				width: 50px; height: 50px;
			}
		</style>   
	</head>
	
	<body>
		<div class="container">
			<div class="content">
	      		<form id="productform" action="${mainServer}/admin/product/editProduct" method="post" enctype="multipart/form-data">
	      		<input type="hidden" name="product_logo" id="product_logo" />
	      		<input type="hidden" name="pictureArray" id="pictureArray" />
	      		<input type="hidden" name="productUrl" id="productUrl" />
				<div class="easyui-tabs1" style="width:100%;">
					<div title="商品基本信息" data-options="closable:false" class="basic-info">
						<div class="column"><span class="current">基础信息</span></div>
					      	<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label">商品名称&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<input class="easyui-textbox" type="text" name="productName" id="productName" style="height:35px;margin:0 0;width:220px;" maxlength="50"/>
											<input  type="hidden" name="product_id" id="product_id" value="${productId}"  readonly="readonly"/>
											<span id="Name_msg" style="color: red;"></span>
										</td>
										<td class="kv-label">商品分类&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<select name="product_type" id="productType" style="height:35px;margin:0 0;width:220px;" >
												<option value="0">未分类</option>
												<option value="4">星球推荐</option>
												<option value="3">休闲零食</option>
												<option value="1">应季水果</option>
												<option value="2">水产肉类</option>
											</select>
											<span id="productType_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">猜你喜欢&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<select id="productLove" name="productLove" style="height:35px;margin:0 0;width:220px;" >
												<option value="" selected="selected">请选择</option>
												<option value="1">是</option>
												<option value="2">否 </option>
											</select>
											<span id="Love_msg" style="color: red;"></span>
										</td>
										
										<td class="kv-label">商品状态&nbsp;<font color="red">*</font></td>
										<td class="kv-content" colspan="2">
											<select id="productStatus" name="productStatus" style="height:35px;margin:0 0;width:220px;" >
												<option value="" selected="selected">请选择</option>
												<option value="1">上架</option>
												<option value="2">下架</option>
												<option value="3">已删除</option>
											</select>
											<span id="Status_msg" style="color: red;"></span>
										</td> 
									</tr>
									
									<tr>
										
										<td class="kv-label">是否线上产品</td>
										<td class="kv-content" colspan="2">
											<input type="radio" name="isOnline" id="isOnline" value="1" checked/>是
											<input type="radio" name="isOnline" id="isOnline" value="2" />否
										</td>
										<td class="kv-label">商品排序</td>
										<td class="kv-content" colspan="2">
											<input class="numberbox" type="text" name="order_num" id="orderNum" style="height:35px;margin:0 0;width:15%;" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
											数字越小排位越前
											<span id="orderNum_msg" style="color: red;"></span>
										</td>
										
									</tr>
									
									
									
									<tr>
										<td class="kv-label">每周新品</td>
										<td class="kv-content" colspan="2">
											<label for="weekly_yes">
												<input type="radio" name="weekly" id="weekly_yes" value="1"/>是
											</label>
											<label for="weekly_no">
												<input type="radio" name="weekly" id="weekly_no" value="0" checked="checked"/>否
											</label>
											<span style="font-weight: bold; padding-left:20px;">首页显示</span>
											<label for="weekly_homepage_yes">
												<input type="radio" name="weekly_homepage" id="weekly_homepage_yes" value="1"/>是
											</label>
											<label for="weekly_homepage_no">
												<input type="radio" name="weekly_homepage" id="weekly_homepage_no" value="0" checked="checked"/>否
											</label>
										</td>
										<td class="kv-label">人气推荐</td>
										<td class="kv-content" colspan="2">
											<label for="tuijian_yes">
												<input type="radio" name="tuijian" id="tuijian_yes" value="1"/>是
											</label>
											<label for="tuijian_no">
												<input type="radio" name="tuijian" id="tuijian_no" value="0" checked="checked"/>否
											</label>
											<span style="font-weight: bold; padding-left:20px;">首页显示</span>
											<label for="tuijian_homepage_yes">
												<input type="radio" name="tuijian_homepage" id="tuijian_homepage_yes" value="1"/>是
											</label>
											<label for="tuijian_homepage_no">
												<input type="radio" name="tuijian_homepage" id="tuijian_homepage_no" value="0" checked="checked"/>否
											</label>
										</td>
									</tr>
									
									<tr>
										<td class="kv-label">免运费</td>
										<td class="kv-content">
											<label for="weekly_yes">
												<input type="radio" name="is_freight" id="freight_yes" value="1" checked="checked"/>是
											</label>
											<label for="weekly_no">
												<input type="radio" name="is_freight" id="freight_no" value="0"/>否
											</label>
										</td>
										<td class="kv-label">权威质检</td>
										<td class="kv-content">
											<label for="tuijian_yes">
												<input type="radio" name="is_quality" id="quality_yes" value="1" checked="checked"/>是
											</label>
											<label for="tuijian_no">
												<input type="radio" name="is_quality" id="quality_no" value="0"/>否
											</label>
										</td>
										<td class="kv-label">专业测评</td>
										<td class="kv-content">
											<label for="tuijian_yes">
												<input type="radio" name="is_testing" id="testing_yes" value="1" checked="checked"/>是
											</label>
											<label for="tuijian_no">
												<input type="radio" name="is_testing" id="testing_no" value="0"/>否
											</label>
										</td>
									</tr>
									
									<tr>
										<td class="kv-label">虚拟销量</td>
										<td class="kv-content">
										   <input class="easyui-textbox" type="text" name="fakeSalesCopy" id="fakeSalesCopy" style="height:35px;margin:0 0;" value="0"/>
											<span id="fakeSales_msg" style="color: red;">不填则为真实销量</span>
										</td>
										<td class="kv-label">仓库所在地址</td>
										<td class="kv-content" colspan="4">
										    <select id="provinceId" name="provinceId" style="height:35px;margin:0 0;width:220px;"  onchange="selectAddress()">
												<option value="" selected="selected">请选择</option>
												<c:forEach items="${list}" var="l">
													<option value="${l.areaId}">${l.areaName}</option>	
												</c:forEach>
											</select>—
											<select id="cityId" name="cityId" style="height:35px;margin:0 0;width:220px;" onchange="selectCity()">
												<option value="" selected="selected">请选择</option>
											</select>
											
											<span id="Area_msg" style="color: red;"></span>
										</td>
										
										
									</tr>
									<tr>
											<td class="kv-label">温馨提示&nbsp;<font color="red">*</font></td>
											<td class="kv-content" colspan="2">
												<input class="easyui-textbox" type="text" name="productPrompt" id="productPrompt" style="height:35px;margin:0 0;width:80%;"/>
												<span id="Prompt_msg" style="color: red;"></span>
											</td>
											<td class="kv-label">是否允许使用优惠券和红包&nbsp;<font color="red">*</font></td>
											<td class="kv-content" colspan="2">
												<select id="ifCoupon" name="ifCoupon" style="height:35px;margin:0 0;width:120px;">
													<option value="0">允许</option>
													<option value="1">不允许</option>
												</select>
											</td>
									</tr>
									
									<tr>
										<td class="kv-label" colspan="6">
											<div style="font-size: 14px;font-weight: bolder;" align="center">产品参数 &nbsp;&nbsp; 示例:
												 <input  style="color: red;width: 40px;" value="产地" readonly="readonly"/>—
												 <input style="color: red;width: 130px;" value="黑龙江牡丹江市" readonly="readonly" />
											 </div>
										</td>
									</tr>
									
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">可多选(最多8个)&nbsp;<font color="red">*</font></div>
										</td>
										<td class="kv-content" colspan="5" >
											<span class="addTea" onclick="addProductType()" >增加</span>
											<span class="addTea" onclick="delProductType()" >减少</span>
											<input type="hidden"  name="productTypeLength"  id="productTypeLength" value="1"/>
											<div id="tea_1" style="float:left;">
												
											</div>
											<div id="tea_2" style="float:left;margin-top: 10px;">
												
											</div>
											<span id="Size_msg" style="color: red;"></span>
										</td>
									</tr>
									
									<tr>
										<td class="kv-label" colspan="6">
											<div style="font-size: 14px;font-weight: bolder;" align="center">
												规格—原价-优惠价（没有则填-1）—库存 —限购（-1代表不限购）—限购开始时间—限购结束时间&nbsp;&nbsp;<br/> 示例:<input  style="color: red;width: 50px;" value="5千克" readonly="readonly"/>—
										      	<input style="color: red;width: 50px;" value="120" readonly="readonly" /> 元（原价）
									      		<input style="color: red;width: 50px;" value="100" readonly="readonly" /> 优惠价（原价）
										      	—<input style="color: red;width: 50px;" value="100" readonly="readonly" />件
										      	—<input style="color: red;width: 50px;" value="2" readonly="readonly" />件
										      	—<input style="color: red;width: 160px;" value="2017-05-11 00:00:00" readonly="readonly" />
										      	—<input style="color: red;width: 160px;" value="2017-05-15 00:00:00" readonly="readonly" />
										      	&nbsp;&nbsp;&nbsp;规格图片大小 120x120&nbsp;&nbsp;&nbsp;
										      	<span class="addTea" onclick="addTeaType()">增加</span>
												<span class="addTea" onclick="delTeaType()">减少</span>
											</div>
										</td>
									</tr>
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">&nbsp;<font color="red">*</font></div>
										</td>
										<td class="kv-content" colspan="5" >
											<input type="hidden" name="teaTypeLength" id="teaTypeLength" value="1"/>
											<div id="product_1" style="float:left;">
												
											</div>
											
											<span id="Product_msg" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">首页每周新品推荐排第一位置的图</div>
										</td>
										<td class="kv-content" colspan="5" >
											<input class="easyui-filebox" type="text" name="homeweekly_first_img" id="homeweeklyFirstImg" style="height:35px;width:60px;"
						                      data-options="buttonText:'选择图片',accept:'image/jpeg,image/png,image/gif,image/JPEG,image/PNG,image/GIF', onChange:function(){previewImg($(this));}"/>
												<span style="color: red;"></span><div id="homeweeklyFirstImgShow"></div>
										</td>
									</tr>
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">首页每周新品推荐非第一位置的图</div>
										</td>
										<td class="kv-content" colspan="5" >
												<input class="easyui-filebox" type="text" name="homeweekly_after_img" id="homeweeklyAfterImg" style="height:35px;width:60px;"
						                      data-options="buttonText:'选择图片',accept:'image/jpg,image/png,image/gif,image/JPG,image/PNG,image/GIF', onChange:function(){previewImg($(this));}"/>
												<span style="color: red;"></span><div id="homeweeklyAfterImgShow"></div>
										</td>
									</tr>
									<tr>
										<td class="kv-label">
											<div style="font-size: 12px;font-weight: bolder;" align="center">首页人气推荐图</div>
										</td>
										<td class="kv-content" colspan="5" >
											<input class="easyui-filebox" type="text" name="homefame_img" id="homefameImg" style="height:35px;width:60px;"
						                      data-options="buttonText:'选择图片',accept:'image/jpg,image/png,image/gif,image/JPG,image/PNG,image/GIF', onChange:function(){previewImg($(this));}"/>
												<span style="color: red;"></span><div id="homefameImgShow"></div>
										</td>
									</tr>
									
							</tbody>
						</table> 
						<div class="column"><span class="current">商品介绍</span></div>
						<table class="kv-table">
							<tbody>
								<tr>
									<td class="kv-label">简介</td>
									<td class="kv-content">
										<input type="text" name="shareExplain" id="shareExplain" class="textbox" style="height:35px;margin:0 0;width:80%;" maxlength="100"/>
									</td>
								</tr>		
								<tr>
									<td class="kv-label" style="width: 15%">商品介绍&nbsp;<font color="red">*</font></td>
									<td class="kv-content" style="width: 85%"> 
										<input type="hidden"  name="productIntroduce" id="productIntroduce" readonly="readonly" />
										<div class="view_content" id="editor_id">
											<script id="editor-state" name="content" type="text/plain">
												
                    						</script>
										</div>
							            <span id="Introduce_msg" style="color: red;"></span>	
								     </td>
								</tr>
								<tr>
									<td colspan="6" align="center">
										<div style="margin-top: 20px;" align="center">
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true" onclick="isSubmit()">修改</a>
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" data-options="selected:true" onclick="toBack()">返回</a>
											
											<input type="hidden" id="areaIds" name="areaIds" readonly="readonly"/>
											<input type="hidden" id="deliverType" name="deliverType" readonly="readonly"/>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
		      		</div>
			      	<div title="商品图片" data-options="closable:false" class="basic-info">
			      		<div class="column"><span class="current">商品封面图</span></div>
			      		<iframe name="proLogoFrm" src="productlogo.html?productId=${productId }" width="100%" height="350px" id="proLogoFrm">
						</iframe>
						<div class="column"><span class="current">商品轮播图</span></div>
						<iframe name="proBannerFrm" src="productbanner.html?productId=${productId }" width="100%" height="350px">
							
						</iframe>
		      		</div>
		      		
		      		
		      		<div title="商品配送地址" data-options="closable:false" class="basic-info">
		      			<div class="column"><span class="current">商品配送地址</span></div>
		      			<table class="kv-table">
								<tbody>
									<tr>
										<td class="kv-label" style="width: 20%">
											<%-- <ul id="getDeliverTree" class="easyui-tree"  data-options="url:'${mainserver}/admin/deliverarea/getTree',method:'get',animate:true,checkbox:true"> --%>
											<ul id="getDeliverTree" class="easyui-tree"  data-options="animate:true,checkbox:true">
												
											</ul>
										</td>
										<td class="kv-content" colspan="3" style="width: 40%">
											<div>
												当前选择：<font style="font-weight:bolder;font-size: 14px;" id="chioceArea"></font>
											</div>
											<div>	
												配送地址类型:<select id="deliverAddress" name="deliverAddress" style="height:35px;margin:0 0;width:220px;"  onchange="selectDeliverType()">
															<option value="" selected="selected">请选择</option>
															<option value="1">同城</option>
															<option value="2">本省</option>
															<option value="3">自定义</option>
														</select>
														
														<span id="Deliver_msg" style="color: red;"></span>
											
											</div>
										
										</td>
									</tr>
								</tbody>		
						</table>	
		      		</div>
		      		
		      		
			    </div>
		      	</form>
			</div>
		</div>
		
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="${resourcepath}/admin/js/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/qn/qiniu.min.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/product/edit_product.js"></script>
	</body>
</html>