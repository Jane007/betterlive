<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>挥货后台管理-系统参数管理</title>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers1.css">
		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
    	<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
	  	<script type="text/javascript">
    		var mainServer = '${mainserver}';
    	</script>

	</head>
<body>
<body>
      <table id="sysconfigGrid" style="width:100%;height:529px;" title="系统参数列表">
      
      </table>
     <div id="sysconfigToolbar" style="padding:0 30px;">
       <div class="conditions">
           <span class="con-span">参数编码: </span><input class="easyui-textbox" type="text" name="dict_code2" id="dict_code2" style="width:166px;height:35px;line-height:35px;"></input>
           <span class="con-span">参数名称: </span><input class="easyui-textbox" type="text" name="dict_name2" id="dict_name2" style="width:166px;height:35px;line-height:35px;"></input>
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true" onclick="Store()">查询</a>
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="clearConForm()">重置</a>
       </div>
       <div class="opt-buttons">
           <a id="addConfBtn" class="easyui-linkbutton" iconCls="icon-add" data-options="selected:true"  onclick="addConfDlg()">新增</a>
           <a id="updConfBtn" class="easyui-linkbutton" iconCls="icon-edit" data-options="selected:true"  onclick="updConfDlg()">修改</a>
           <a id="deleteConfBtn" class="easyui-linkbutton"  iconCls="icon-remove" data-options="selected:true" onclick="deleteConfDlg()">删除</a>
       </div>
     </div>
     
     <div id="updConfDlg" class="easyui-dialog" title="新增系统参数" data-options="closed:true" style="width:720px;height:490px;padding:10px;">
		<form id="detail" method="post" action="save">
         	<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">参数编码</td>
						<td class="kv-content">
							<input class="easyui-textbox" name="dictCode" id="dictCode" data-options ="required:true" missingMessage="不能为空" style="height:35px;margin:0 0;width:80%;"/>
							<input type="hidden" id ="sysDictId" name ="sysDictId" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="kv-label">参数名称</td>
						<td class="kv-content">
							<input class="easyui-textbox" name="dictName" id="dictName"  data-options="required:true" missingMessage="不能为空" style="height:35px;margin:0 0;width:80%;"/>
						</td>
					</tr>
					<tr>
						<td class="kv-label">参数类型</td>
						<td class="kv-content">
							<select class="easyui-combobox" style="width:200px;height: 35px;" name="dictType" id="dictType" data-options="editable:false,required:true">
	                            <option value="1" selected="selected">系统内置</option>
	                            <option value="2">管理员添加</option>
	                            <option value="3">不限</option>
	                        </select>
						</td>
					</tr>
					<tr>
						<td class="kv-label">参数状态</td>
						<td class="kv-content">
							<select class="easyui-combobox" style="width:200px;height: 35px;" name="status" id="status" data-options="editable:false,required:true">
	                            <option value="1" selected="selected">有效</option>
	                            <option value="2">禁用</option>
	                        </select>
						</td>
					</tr>
					<tr>
						<td class="kv-label">参数值</td>
						<td class="kv-content">
							<input class="easyui-textbox" name="dictValue" id="dictValue"  data-options="required:true" missingMessage="不能为空" style="height:35px;margin:0 0;width:80%;"/>
						</td>
					</tr>
					<tr>
						<td class="kv-label">参数描述</td>
						<td class="kv-content">
							<textarea id="description" rows=3 cols =80 name="description" class="textarea easyui-validatebox" style="width: 80%;"></textarea>
						</td>
					</tr>
					<tr>
						<td class="kv-content" colspan="2">
							<div style="padding:5px" align="center">
    	                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" data-options="selected:true" onclick="submitForm()">保存</a>
    	                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" data-options="selected:true" onclick="clearForm()">全部清空</a>
                            </div>
                         </td>
					</tr>
				</tbody>
			</table>
        </form>
     </div>
    
	<script type="text/javascript" src="${resourcepath}/admin/js/systemconfig/sysconfig.js"></script>
</body>
</html>