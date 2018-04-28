var boxDataGrid;
$(function() {
	boxDataGrid = StoreGrid.createGrid('StoreGrid');
});

function searchProduct() {
	$('#StoreGrid').datagrid('load', {
		"status" : $("#product_status").val(),
		"productName" : $("#product_name").val(),
		"isHomepage" : $("#isHomePage").val(),
		"extensionType" : $("#extensionType").val(),
		"isOnline" : $("#isOnline").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/product/queryProductAllJson',

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
										field : "product_id",
										checkbox : true,
										width : 80
									},
									{
										title : "商品编号",
										field : "product_code",
										width : 100
									},
									{
										title : "商品名称",
										field : "product_name",
										width : 100
									},
									{
										title : "商品状态",
										field : "status",
										width : 50,
										formatter : function(value, row) {
											switch (value) {
											case 1:
												return '上架';
											case 2:
												return '下架';
											case 3:
												return '已删除';
											}
										}

									},
									{
										title : "是否线上",
										field : "is_online",
										width : 80,
										formatter : function(value, rec) {
											switch (value) {
											case 1:
												return '线上';
											case 2:
												return '线下 &nbsp;&nbsp;<a href="javascript:createCode('
														+ rec.product_id
														+ ')">生成二维码</a>';
											}
										}
									},
									{
										title : "温馨提示",
										field : "prompt",
										width : 100
									},
									{
										title : "创建时间",
										field : "create_time",
										width : 80

									},
									{
										title : "每周新品操作",
										field : "aaa",
										formatter : function(value, rec,
												rowIndex) {
											var check = "";
											var listExtend = rec.extensionVos;
											var indexChecked = "";
											if (listExtend != null
													&& '' != listExtend
													&& listExtend.length > 0) {
												$
														.each(
																listExtend,
																function(i,
																		extension) {
																	if (extension.extensionType == '1') {// 每周新品
																		check = "checked";
																	}
																	if (extension.isHomepage == '1'
																			&& extension.extensionType == 1) {
																		indexChecked = "checked";
																	}
																});
											}
											var dis = "";
											if (rec.desabled == 0
													|| rec.desabled == '0') {
												dis = "disabled";
											}
											var newProduct = "<input type=\"checkbox\"  name=\"xp_"
													+ rec.product_id
													+ "\" id=\"xp_"
													+ rec.product_id
													+ "\"  value='1' onclick='setProductToIndex("
													+ rec.product_id
													+ ",1,\"xp\")' "
													+ check
													+ "  "
													+ dis
													+ ">每周新品&nbsp;&nbsp;";
											var isIndex = "<input type=\"checkbox\"  name=\"xpi_"
													+ rec.product_id
													+ "\" id=\"xpi_"
													+ rec.product_id
													+ "\"  value=\"1\" onclick='setProductToIndex("
													+ rec.product_id
													+ ",1,\"xp\")' "
													+ indexChecked
													+ " "
													+ dis
													+ ">是否显示首页&nbsp;&nbsp;";
											return newProduct + isIndex;
										},
										width : 120
									},
									{
										title : "人气推荐操作",
										field : "bbb",
										formatter : function(value, rec,
												rowIndex) {
											var check = "";
											var listExtend = rec.extensionVos;
											var indexChecked = "";
											if (listExtend != null
													&& '' != listExtend
													&& listExtend.length > 0) {
												$
														.each(
																listExtend,
																function(i,
																		extension) {
																	if (extension.extensionType == '2') {// 人气推荐
																		check = "checked";
																	}
																	if (extension.isHomepage == '1'
																			&& extension.extensionType == 2) {
																		indexChecked = "checked";
																	}
																});
											}
											var dis = "";
											if (rec.desabled == 0
													|| rec.desabled == '0') {
												dis = "disabled";
											}
											var recomand = "<input type='checkbox'  name='rq_"
													+ rec.product_id
													+ "' id='rq_"
													+ rec.product_id
													+ "'  value='"
													+ rec.product_id
													+ "' onclick='setProductToIndex("
													+ rec.product_id
													+ ",2,\"rq\")' "
													+ check
													+ " "
													+ dis
													+ ">人气推荐&nbsp;&nbsp;";
											var isIndex = "<input type='checkbox'  name='rqi_"
													+ rec.product_id
													+ "' id='rqi_"
													+ rec.product_id
													+ "'  value='1' onclick='setProductToIndex("
													+ rec.product_id
													+ ",2,\"rq\")' "
													+ indexChecked
													+ " "
													+ dis
													+ ">是否显示首页&nbsp;&nbsp;";
											return recomand + isIndex;
										},
										width : 120
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

function createCode(projectId) {
	location.href = mainServer + '/admin/product/createCode?productId='
			+ projectId;
}

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
// 设置每周新品，人气推荐
function setProductToIndex(productId, type, flag) {
	var val = 0;
	var index = 0;
	if ($('#xp_' + productId).is(':checked') && flag == 'xp') {// 每周新品

		val = 1;
		$('#rq_' + productId).attr("disabled", "disabled");
		$('#rqi_' + productId).attr("disabled", "disabled");
	} else if ($('#rq_' + productId).is(':checked') && flag == 'rq') {// 人气推荐
		val = 1;
		$('#xp_' + productId).attr("disabled", "disabled");
		$('#xpi_' + productId).attr("disabled", "disabled");
	} else {
		val = 0;
		$('#xp_' + productId).attr("disabled", "disabled");
		$('#xpi_' + productId).attr("disabled", "disabled");
		$('#rq_' + productId).attr("disabled", "disabled");
		$('#rqi_' + productId).attr("disabled", "disabled");
		$('#rq_' + productId).attr("disabled", false);
		$('#rqi_' + productId).attr("disabled", false);
		$('#xp_' + productId).attr("disabled", false);
		$('#xpi_' + productId).attr("disabled", false);
		/*
		 * $('#xpi_'+productId).removeAttr("checked");
		 * $('#rqi_'+productId).removeAttr("checked");
		 */
	}
	if ($('#xpi_' + productId).is(':checked') && flag == 'xp') {
		index = 1;
	} else if ($('#rqi_' + productId).is(':checked') && flag == 'rq') {
		index = 1;
	} else {
		index = 0;
	}
	if (val == 1) {// 设为每击新品或推荐
		$.ajax({
			type : "POST",
			datatype : "json",
			url : mainServer + "/admin/newproduct/addNew",
			data : {
				"productId" : productId,
				"extensionType" : type,
				"isHomepage" : index
			},
			async : false,
			success : function(data) {
				var obj = eval('(' + data + ')');
				if (obj.result == 'succ') {
					$.messager.alert('提示', obj.msg, 'info');
				} else if (obj.result == 'fail') {
					$.messager.alert('提示', obj.msg, 'error');
					if (flag == 'xp') {
						$('#xpi_' + productId).attr("checked", false);
					} else if (flag == 'rq') {
						$('#rqi_' + productId).attr("checked", false);
					}
				}
			}
		});
	} else {// 取消设置delete
		$.ajax({
			type : "POST",
			url : mainServer + "/admin/newproduct/delNew",
			datatype : "json",
			data : {
				productId : productId,
				extensionType : type
			},
			async : false,
			success : function(data) {
				var obj = data;
				if (1010 == obj.code) {
					$.messager.alert('提示', obj.msg, 'info');
				} else {
					$.messager.alert('提示', obj.msg, 'error');

				}
			}
		});
	}

}

function toEditProduct() {
	var centerTabs = parent.centerTabs;
	var product_id = boxDataGrid.datagrid('getSelected').product_id;
	if (product_id == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer + "/admin/product/toUpdateProduct?productId="
			+ product_id;
	if (centerTabs.tabs('exists', '修改商品信息')) {
		centerTabs.tabs('select', '修改商品信息');
		var tab = centerTabs.tabs('getTab', '修改商品信息');
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
							title : '修改商品信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 新增商品
function toAddProduct() {
	var centerTabs = parent.centerTabs;
	var url = mainServer + "/admin/product/toAddProduct";
	if (centerTabs.tabs('exists', '添加商品信息')) {
		centerTabs.tabs('select', '添加商品信息');
		var tab = centerTabs.tabs('getTab', '添加商品信息');
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
							title : '添加商品信息',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}
function toDelProduct() {
	var objectArray = boxDataGrid.datagrid('getSelections');

	var productIdArray = "";
	$.each(objectArray, function(i, obj) {
		productIdArray += obj.product_id + ",";
	});
	if (productIdArray == '') {
		$.messager.alert('提示', "请先选择商品", 'error');
		return false;
	} else {
		if (productIdArray.lastIndexOf(",") != -1) {
			productIdArray = productIdArray.substring(0, productIdArray
					.lastIndexOf(","));
		}
	}

	$.messager.confirm("提示", "你确认要删除吗?", function(r) {
		if (r) {

			$.ajax({
				url : "delProduct",
				data : {
					"productIdArray" : productIdArray
				},
				datatype : "json",
				type : "post",
				success : function(data) {
					var obj = eval('(' + data + ')');
					if (obj.result == 'succ') {
						searchProduct();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}
