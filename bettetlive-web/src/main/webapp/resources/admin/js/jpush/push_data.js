var specialGrid;
var circleArticleGrid;
var goodArticleGrid;
var videoGrid;
var productGrid;
var groupGrid;
var joinGroupGrid;
var limitGrid;
$(function() {
	specialGrid = SpecialGrid.createGrid('specialGrid');
	circleArticleGrid = CircleArticleGrid.createGrid('circleArticleGrid');
	goodArticleGrid = GoodArticleGrid.createGrid('goodArticleGrid');
	videoGrid = VideoGrid.createGrid('videoGrid');
	productGrid = ProductGrid.createGrid('productGrid');
	groupGrid = GroupGrid.createGrid('groupGrid');
	joinGroupGrid = JoinGroupGrid.createGrid('joinGroupGrid');
	limitGrid = LimitGrid.createGrid('limitGrid');
});

function chooseObject() {
	var pushTypeId = $("#pushTypeId").val();
	if (pushTypeId == 0) { // 专题详情
		$('#dialogSpecial').dialog("open");
	} else if (pushTypeId == 1) { // 动态文章详情
		$('#dialogCircleArticle').dialog("open");
	} else if (pushTypeId == 2) { // 精选文章详情
		$('#dialogGoodArticle').dialog("open");
	} else if (pushTypeId == 3) { // 视频详情
		$('#dialogVideo').dialog("open");
	} else if (pushTypeId == 4) { // 商品详情
		$('#dialogProduct').dialog("open");
	} else if (pushTypeId == 5) { // 团购详情
		$('#dialogGroup').dialog("open");
	} else if (pushTypeId == 6) { // 参团详情
		$('#dialogJoinGroup').dialog("open");
	} else if (pushTypeId == 7) { // 限量抢购详情
		$('#dialogLimit').dialog("open");
	}
}

var SpecialGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=0',
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
			columns : [ [ {
				title : "序号",
				field : "specialId",
				checkbox : true,
				width : '5%'
			}, {
				title : "专题标题",
				field : "specialName",
				width : '23%'
			}, {
				title : "专题封面",
				field : "specialCover",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "活动开始时间",
				field : "startTime",
				width : '18%'
			}, {
				title : "活动结束时间",
				field : "endTime",
				width : '18%'
			} ] ],
			toolbar : '#specialToolbar',
			onBeforeLoad : function(param) {
				$('#specialGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.specialName);
				$("#specialId").val(rowData.specialId);
				$('#dialogSpecial').dialog("close");
			}
		});
	}
};

var CircleArticleGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=1',
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
			columns : [ [ {
				title : "序号",
				field : "articleId",
				checkbox : true,
				width : '5%'
			}, {
				title : "动态标题",
				field : "articleTitle",
				width : '23%'
			}, {
				title : "动态封面",
				field : "articleCover",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "作者",
				field : "author",
				width : '18%'
			}, {
				title : "创建时间",
				field : "createTime",
				width : '18%'
			} ] ],
			toolbar : '#circleArticleToolbar',
			onBeforeLoad : function(param) {
				$('#circleArticleGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.articleTitle);
				$("#articleTitle").val(rowData.articleTitle);
				$("#articleId").val(rowData.articleId);
				$("#customerId").val(rowData.customerId);
				$('#dialogCircleArticle').dialog("close");
			}
		});
	}
};

var GoodArticleGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=2',
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
			columns : [ [ {
				title : "序号",
				field : "articleId",
				checkbox : true,
				width : '5%'
			}, {
				title : "精选标题",
				field : "articleTitle",
				width : '23%'
			}, {
				title : "精选封面",
				field : "articleCover",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "发布时间",
				field : "createTime",
				width : '18%'
			} ] ],
			toolbar : '#goodArticleToolbar',
			onBeforeLoad : function(param) {
				$('#goodArticleGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.articleTitle);
				$("#articleTitle").val(rowData.articleTitle);
				$("#articleId").val(rowData.articleId);
				$('#dialogGoodArticle').dialog("close");
			}
		});
	}
};

var VideoGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=3',
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
			columns : [ [ {
				title : "序号",
				field : "articleId",
				checkbox : true,
				width : '5%'
			}, {
				title : "视频标题",
				field : "specialName",
				width : '23%'
			}, {
				title : "视频封面图",
				field : "specialCover",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "创建时间",
				field : "createTime",
				width : '18%'
			} ] ],
			toolbar : '#videoToolbar',
			onBeforeLoad : function(param) {
				$('#videoGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.specialName);
				$("#specialId").val(rowData.specialId);
				$('#dialogVideo').dialog("close");
			}
		});
	}
};

var ProductGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=4',
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
			columns : [ [ {
				title : "序号",
				field : "product_id",
				checkbox : true,
				width : '5%'
			}, {
				title : "商品名称",
				field : "product_name",
				width : '23%'
			}, {
				title : "商品LOGO",
				field : "product_logo",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "创建时间",
				field : "create_time",
				width : '18%'
			} ] ],
			toolbar : '#productToolbar',
			onBeforeLoad : function(param) {
				$('#productGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.product_name);
				$("#productId").val(rowData.product_id);
				$('#dialogProduct').dialog("close");
			}
		});
	}
};

var GroupGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=5',
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
			columns : [ [ {
				title : "序号",
				field : "specialId",
				checkbox : true,
				width : '5%'
			}, {
				title : "团购活动名称",
				field : "specialName",
				width : '23%'
			}, {
				title : "团购封面",
				field : "specialCover",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "商品名称",
				field : "productSpecVo.product_name",
				width : '23%'
			}, {
				title : "活动开始时间",
				field : "starttime",
				width : '18%'
			}, {
				title : "活动结束时间",
				field : "endtime",
				width : '18%'
			} ] ],
			toolbar : '#groupToolbar',
			onBeforeLoad : function(param) {
				$('#groupGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.specialName);
				$("#productId").val(rowData.productSpecVo.product_id);
				$("#specialId").val(rowData.specialId);
				$('#dialogGroup').dialog("close");
			}
		});
	}
};

var JoinGroupGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=6',
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
			columns : [ [ {
				title : "序号",
				field : "specialId",
				checkbox : true,
				width : '5%'
			}, {
				title : "开团人",
				field : "nickName",
				width : '18%'
			}, {
				title : "参团上限",
				field : "limitCopy",
				width : '18%'
			}, {
				title : "当前参团人数",
				field : "custNum",
				width : '18%'
			}, {
				title : "团购活动名称",
				field : "specialName",
				width : '23%'
			}, {
				title : "商品名称",
				field : "productName",
				width : '23%'
			}, {
				title : "参团规格",
				field : "specName",
				width : '23%'
			}, {
				title : "团购封面",
				field : "specialCover",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "活动开始时间",
				field : "starttime",
				width : '18%'
			}, {
				title : "活动结束时间",
				field : "endtime",
				width : '18%'
			} ] ],
			toolbar : '#joinGroupToolbar',
			onBeforeLoad : function(param) {
				$('#joinGroupGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.specialName);
				$("#productId").val(rowData.productId);
				$("#specialId").val(rowData.specialId);
				$("#userGroupId").val(rowData.userGroupId);
				$('#dialogJoinGroup').dialog("close");
			}
		});
	}
};

var LimitGrid = {
	createGrid : function(grId) {
		return $('#' + grId).datagrid({
			url : mainServer + '/admin/jpush/queryPrePushDatas?type=7',
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
			columns : [ [ {
				title : "序号",
				field : "specialId",
				checkbox : true,
				width : '5%'
			}, {
				title : "专题标题",
				field : "specialName",
				width : '23%'
			}, {
				title : "专题封面",
				field : "specialCover",
				width : '23%',
				align : "left",
				formatter : function(value, rec) {// 使用formatter格式化刷子
					return '<img src="' + value + '" style="height:80px;"/>';
				}
			}, {
				title : "活动开始时间",
				field : "startTime",
				width : '18%'
			}, {
				title : "活动结束时间",
				field : "endTime",
				width : '18%'
			} ] ],
			toolbar : '#limitToolbar',
			onBeforeLoad : function(param) {
				$('#limitGrid').datagrid('clearSelections');
			},
			onSelect : function(rowIndex, rowData) {
				$("#objName").textbox('setValue', rowData.specialName);
				$("#specialId").val(rowData.specialId);
				$("#productId").val(rowData.productSpecVo.product_id);
				$('#dialogLimit').dialog("close");
			}
		});
	}
};

function searchSpecial() {
	$('#specialGrid').datagrid('load', {
		"specialName" : $("#specialName").val()
	});
}

function searchCircleArticle() {
	$('#circleArticleGrid').datagrid('load', {
		"articleTitle" : $("#circleTitle").val()
	});
}
function searchGoodArticle() {
	$('#goodArticleGrid').datagrid('load', {
		"articleTitle" : $("#goodTitle").val()
	});
}
function searchVideoArticle() {
	$('#videoGrid').datagrid('load', {
		"specialName" : $("#videoName").val()
	});
}
function searchProduct() {
	$('#productGrid').datagrid('load', {
		"productName" : $("#productName").val()
	});
}
function searchJoinGroup() {
	$('#joinGroupGrid').datagrid('load', {
		"specialName" : $("#joinGroupName").val()
	});
}
function searchSpecialLimit() {
	$('#limitGrid').datagrid('load', {
		"specialName" : $("#limitName").val()
	});
}

function resetForm() {
	$("#specialId").val("");
	$("#articleId").val("");
	$("#articleTitle").val("");
	$("#productId").val("");
	$("#userGroupId").val("");
	$("#customerId").val("");
	$("#objName").textbox('setValue', "");
	$("#pushContent").val("");
}

function isSubmit() {
	var pushContent = $("#pushContent").val();
	if (pushContent == null || pushContent == "" || pushContent.length == 0) {
		alert("请填写推送内容");
		return false;
	}
	//若选中8,9则显示标题输入框否则隐藏
	var pushTypeId = $("#pushTypeId").val();
//	if(pushTypeId == 8 || pushTypeId == 9){
//		var pushTitle = $("#pushTitle").val();
//		if (pushTitle == null || pushTitle == "" || pushTitle.length == 0) {
//			alert("请填写推送标题");
//			return false;
//		}
//	}
	$("#subId").attr("onclick", "void(0)");
	$('#pushform')
			.ajaxSubmit(
					{
						type : 'post',
						dataType : "json",
						sync : true,
						error : function(data) {
						},
						success : function(data) {
							if ("1010" == data.code) {
								$("#subId").attr("onclick", "isSubmit()");
								$.messager
										.alert(
												'提示消息',
												'<div style="position:relative;top:20px;">发送成功</div>',
												'success');
							} else {
								$.messager
										.alert(
												'提示消息',
												'<div style="position:relative;top:20px;">发送失败</div>',
												'error');
							}
						}
					});
}