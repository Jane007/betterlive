<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>挥货-运费管理</title>
<%@include file="../common.jsp"%>
<script type="text/javascript">
	var postageString = "${postageMap}";
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
			
		
		</style>
</head>
<body>
	<table id="StoreGrid" style="width:50%;" title="运费信息列表">
	        
    </table>
	      
	<div id="storeToolbar" style="padding:0 30px;">
   		<div id="tb" style="padding:15px 30px;">
   			运费模板名称: <input class="easyui-textbox" type="text" name="postageName"  id="postageName" maxlength="30" style="width:160px;height:35px;line-height:35px;" />
			运费模板类型:<select id="postageType" name="postageType"  style="width:100px;height:31px;line-height:31px;margin-right: 10px;">
				             	<option value="1" selected="selected">价格类</option>
				             </select>					    
				      
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="searchPostage()" >查询</a>
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" data-options="iconCls:'icon-list'" onclick="toAddPostage()">新增运费</a>
				       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="iconCls:'icon-list'" id="updConfBtn"   onclick="toEditPostage()">修改运费</a>
				       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" data-options="iconCls:'icon-list'" id="delConfBtn"  enctype ="multipart/form-data"  onclick="delPostage()">删除运费</a>
      	</div>
    </div>
	        
   	<div id="tb" style="padding:0 30px;">
      
   	</div>
	<div id="dlg" class="easyui-dialog" title="选择产品" data-options="closed:true" style="width:700px;height:500px;padding:10px;display: none;">
	      	<table class="kv-table">
				<tbody>
					<tr>
							<c:forEach items="${products}"  var="product">
			           			
			           			 <span class="waiteTea"  id="${product.product_id}" title="${product.product_name}"  onclick="addCreateProduct(this)" >${product.product_name}</span> 
			           		</c:forEach>						
							<input type="hidden"  name="postageId"  id="postageId"/>
							<div style="padding:35px" align="center">
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true" onclick="isSureProduct()">确定</a>
								<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="selected:true" onclick="closeProduct()" >关闭</a>
                            </div>
					</tr>
				</tbody>	
			</table>
		</div>
		
	<script type="text/javascript" src="${resourcepath}/admin/js/postage/postage_list.js"></script>
	</body>
</html>