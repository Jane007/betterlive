var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

function myparser(s) {
	if (!s)
		return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}

function searchLabel() {
	var showStart = $("input[name=showStart]").val();
	$('#StoreGrid').datagrid('load', {
		"labelName" : $("#labelName").val(),
		"labelType" : $("#labelType").val(),
		"showTime" : showStart
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer + '/admin/label/queryLabelAllJson',

							rownumbers : true,
							singleSelect : false,
							pagination : true,
							autoRowHeight : true,
							fit : true,
							fitColumns : true, // 自动使列适应表格宽度以防止出现水平滚动。
							striped : true,
							checkOnSelect : true, // 点击某一行时，则会选中/取消选中复选框
							selectOnCheck : true, // 点击复选框将会选中该行
							collapsible : true,
							showFooter : true,
							columns : [ [
									{
										title : "序号",
										field : "labelId",
										checkbox : true,
										width : 100,
									},
									{
										title : "标签名称",
										field : "labelName",
										width : 100,
									},
									{
										title : "标签状态",
										field : "status",
										width : 100,
										formatter : function(value, row) {
											switch (value) {
											case 0:
												return '上架';
											case 1:
												return '下架';
											}
										}

									},
									{
										title : "搜索次数",
										field : "searchCount",
										width : 100,

									},
									{
										title : "标签类型",
										field : "labelType",
										width : 100,
										formatter : function(value, row) {
											switch (value) {
											case 1:
												return '热门搜索';
											}
										}
									},
									{
										title : "排列顺序",
										field : "labelSort",
										width : 100,

									},
									{
										title : "详细列表",
										field : "xiangXi",
										width : 100,
										formatter : function(value, row) {
											return '<a href="javascript:clickDetailedEntity(\''
													+ row.labelId
													+ '\');" style="color:blue;">查看</a>';
										}
									},{
				                        title:"选择商品匹配",
				                        field:"do",
				                        width:50,
				                        formatter : function(value, row) {
				                        	return '<a href="javascript:clickProducts('
											+ row.labelId+',\''+row.productIds
											+ '\');" style="color:blue;">匹配商品</a>';
										}
				                    }

							] ],
							toolbar : '#storeToolbar',
							onBeforeLoad : function(param) {
								$('#StoreGrid').datagrid('clearSelections');
							},
							onLoadSuccess : function(data) {
								initCUIDBtn();
							},
							onSelect : function(rowIndex, rowData) {
								initCUIDBtn();
							},
							onUnselect : function(rowIndex, rowData) {
								initCUIDBtn();
							},
							onCheckAll : function(rowIndex, rowData) {
								initCUIDBtn();
							}
						});
	}
};

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况

		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
	}
}

function toEditLabel() {
	var centerTabs = parent.centerTabs;
	var label_id = boxDataGrid.datagrid('getSelected').labelId;
	if (label_id == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer + "/admin/label/toUpdateLabel?labelId=" + label_id;
	if (centerTabs.tabs('exists', '修改标签信息')) {
		centerTabs.tabs('select', '修改标签信息');
		var tab = centerTabs.tabs('getTab', '修改标签信息');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '修改标签信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增标签
function toAddLabel() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/label/toAddLabel";
	if (centerTabs.tabs('exists', '添加热门标签')) {
		centerTabs.tabs('select', '添加热门标签');
		var tab = centerTabs.tabs('getTab', '添加热门标签');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '添加热门标签',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
function toDelLabel() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var labelIdArray = "";
	$.each(objectArray, function(i, obj) {
		labelIdArray += obj.labelId + ",";
	});
	if (labelIdArray == '') {
		$.messager.alert('提示', "请先选择商品", 'error');
		return false;
	} else {
		if (labelIdArray.lastIndexOf(",") != -1) {
			labelIdArray = labelIdArray.substring(0, labelIdArray
					.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {
			$.ajax({
				url : mainServer + "/admin/label/delLabel",
				data : {
					"labelIdArray" : labelIdArray
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					if (data.result == 'succ') {
						searchLabel();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}

function clickDetailedEntity(value) {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/label/findSearchList?labelId=" + value;
	if (centerTabs.tabs('exists', '热门标签搜索列表')) {
		centerTabs.tabs('select', '热门标签搜索列表');
		var tab = centerTabs.tabs('getTab', '热门标签搜索列表');
		var option = tab.panel('options');
		option.content = '<iframe class="page-iframe" src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
		centerTabs.tabs('update', {
			tab : tab,
			options : option
		});
	} else {
		centerTabs
				.tabs(
						'add',
						{
							title : '热门标签搜索列表',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
function closeProduct(){
	   $("span[class='chooseTea']").each(function(i,ele){
		   $(this).attr("class","waiteTea");
	   });
	   $("span").each(function(i,ele){
		   $(this).show();
	   });
	   $("#labelId").val("");
	   $("#dlg").dialog("close");
}

function clickProducts(labelId,productIds) {
	   closeProduct();
	   $("#labelId").val(labelId);
	   if(""!=productIds && null!=productIds){
		   var array=new Array();
		   array=productIds.split(",");
		 	
		   for (var i= 0; i< array.length; i++) {
			  $("#"+array[i]).attr("class","chooseTea");
		   }
	   }
	   
	$("#dlg").dialog("open");
}

function addCreateProduct(obj){
	  if($(obj).attr("class")=="waiteTea"){
	   	   $(obj).attr("class","chooseTea");
	   }else{
		   $(obj).attr("class","waiteTea");
	   }
}

function isSureProduct(){
	   var chooseProductIds="";
	   $("span[class='chooseTea']").each(function(i,ele){
		   chooseProductIds+=$(this).attr("id")+",";
	   });
	   if (chooseProductIds.lastIndexOf(",") != -1) {
		   chooseProductIds = chooseProductIds.substring(0, chooseProductIds.lastIndexOf(","));
	}
	  /*
		 * if(chooseProductIds.lastIndexOf(",")){
		 * chooseProductIds=chooseProductIds.substr(0,chooseProductIds.lastIndexOf(",")); }
		 */

	   $.ajax({
			url:mainServer+"/admin/label/editlabel",
			data:{
				"labelId":$("#labelId").val(),
				"chooseProductIds":chooseProductIds
			},
			datatype:"json",
			type:"post",
			success:function(data){
             if(data!=null&&''!=data){
             	if ("succ" == data.result) {
             		$.messager.alert('提示','分配商品成功',data.result);
					}else{
						$.messager.alert('提示',data.msg,data.result);
					}
             	
             }else{
             	$.messager.alert('提示','分配商品失败','error');
             }
             closeProduct();
             window.location.reload();
			}
				
		});
}