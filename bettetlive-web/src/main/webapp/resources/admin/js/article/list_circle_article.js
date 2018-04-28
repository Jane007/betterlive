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

function searchSpecialArticle() {
	// var startTime = $("input[name=startTime]").val();
	// var endTime = $("input[name=endTime]").val();
	$('#StoreGrid').datagrid('load', {
		"articleTitle" : $("#articleTitle").val(),
		// "startTime":startTime,
		// "endTime":endTime,
		"status" : $("#status").val()
	});
}

var StoreGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/specialarticle/querySpecialArticleAllJson?articleFrom=1',
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
										field : "articleId",
										checkbox : true,
										width : 100
									},
									{
										title : "文章名称",
										field : "articleTitle",
										width : 100
									},
									{
										title : "文章封面",
										field : "articleCover",
										width : 120,
										height : 80,
										align : "left",
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<img src=' + value
													+ ' style="height:80px;"/>';
										}
									}
									// ,{
									// TITLE:"文章简介",
									// FIELD:"ARTICLEINTRODUCE",
									// WIDTH:100
									// }
									,
									{
										title : "是否推荐到首页",
										field : "homeFlag",
										width : 80,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var showValue = "否";
											if (value == 1) {
												showValue = "是";
											}
											return showValue;
										}
									},
									{
										title : "状态",
										field : "status",
										width : 100,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var showValue = "已删除";
											if (value == 1) {
												showValue = "已发布";
											} else if (value == 2) {
												showValue = "草稿箱";
											} else if (value == 3) {
												showValue = "待审核";
											} else if (value == 4) {
												showValue = "未通过审核";
											}
											return showValue;
										}
									},
									{
										title : "推荐至精选文章",
										field : "recSpecial",
										width : 100,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var showValue = "未推荐";
											if (rec.articleTypeId != null
													&& rec.articleTypeId > 0) {
												showValue = "已推荐";
											}
											return showValue;
										}
									},
									{
										title : "推荐至更多动态",
										field : "recommendMore",
										width : 100,
										formatter : function(value, rec) {// 使用formatter格式化刷子
											var showValue = "未推荐";
											if (rec.recommendMore != null
													&& rec.recommendMore > 0) {
												showValue = "已推荐";
											}
											return showValue;
										}
									}, {
										title : "发帖人",
										field : "author",
										width : 80
									}, {
										title : "创建时间",
										field : "createTime",
										width : 80
									}, {
										title : "发布时间",
										field : "publishTime",
										width : 80
									} ] ],
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
							}
						});
	}
};

// 查看详细、添加、修改、删除按钮状态同步刷新
function initCUIDBtn() {
	var rows = boxDataGrid.datagrid('getSelections');
	if (rows.length > 1) {// 多行情况
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn1');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn2');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn3');
	} else if (rows.length == 1) {
		boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn1');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn2');
		boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn3');
	} else if (rows.length == 0) {
		boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn1');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn2');
		boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn3');
	}
}

function toEditSpecialArticle() {
	var centerTabs = parent.centerTabs;
	var articleId = boxDataGrid.datagrid('getSelected').articleId;
	if (articleId == '') {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择要修改的记录</div>',
				'error');
		return;
	}
	var url = mainServer
			+ "/admin/specialarticle/toEditCircleArticle?articleId="
			+ articleId;
	if (centerTabs.tabs('exists', '修改社区文章')) {
		centerTabs.tabs('select', '修改社区文章');
		var tab = centerTabs.tabs('getTab', '修改社区文章');
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
							title : '修改社区文章',
							closable : true,
							content : '<iframe class="page-iframe" src="'
									+ url
									+ '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
						});
	}
}

// 文章状态处理
function toDelSpecialArticle(value) {
	
	var opinonMsg = "";
	var opinonTitle = "";
	
	if (value == 1) {
		opinonMsg = "您发表的动态审核已通过";
		opinonTitle = "请输入通过理由";   
	}
	
	if (value == 4) {
		opinonMsg = "您发表的动态有违规内容请查看规则后再发表";
		opinonTitle = "请输入拒绝理由"; 
	}
	
	var msg = opinonTitle + ': <input id="opinion" type="text" class="textbox-text validatebox-text textbox-prompt" autocomplete="off" value="'+ opinonMsg +'" style="margin-left: 30px; margin-right: 0px; padding-top: 6px; padding-bottom: 6px; width: 250px;" data-original-title="" title="">';
	
	$.messager.confirm("提示", msg, function(r) {
		if (r) {
			
			var articleId = boxDataGrid.datagrid("getSelected").articleId;
			var opinion = $('#opinion').val(); //意见
	
			$.ajax({
				url : "editArticleSpecialStatus",
				data : {
					"articleId" : articleId,
					"checkStatus" : value,
					"opinion" : opinion
				},
				datatype : "json",
				type : "post",
				success : function(data) {

					var obj = eval('(' + data + ')');
					if (obj.result == 'succ') {
						searchSpecialArticle();
					} else {
						$.messager.alert('提示', obj.msg, 'error');
					}

				}

			});
		}
	});
}