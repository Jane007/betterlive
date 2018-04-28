var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
var boxDataGrid;

$(function() {
	$("#status").val(status);
	$("#articleTypeId").val(articleTypeId);
	var ue = UE.getEditor('editor-state', {
		toolbars : [ [ 'source', // 源代码
		'anchor', // 锚点
		'undo', // 撤销
		'redo', // 重做
		'bold', // 加粗
		'indent', // 首行缩进
		'snapscreen', // 截图
		'italic', // 斜体
		'underline', // 下划线
		'strikethrough', // 删除线
		'subscript', // 下标
		'fontborder', // 字符边框
		'superscript', // 上标
		'formatmatch', // 格式刷
		'blockquote', // 引用
		'pasteplain', // 纯文本粘贴模式
		'selectall', // 全选
		'print', // 打印
		'preview', // 预览
		'horizontal', // 分隔线
		'removeformat', // 清除格式
		'time', // 时间
		'date', // 日期
		'unlink', // 取消链接
		'insertrow', // 前插入行
		'insertcol', // 前插入列
		'mergeright', // 右合并单元格
		'mergedown', // 下合并单元格
		'deleterow', // 删除行
		'deletecol', // 删除列
		'splittorows', // 拆分成行
		'splittocols', // 拆分成列
		'splittocells', // 完全拆分单元格
		'deletecaption', // 删除表格标题
		'inserttitle', // 插入标题
		'mergecells', // 合并多个单元格
		'deletetable', // 删除表格
		'cleardoc', // 清空文档
		'insertparagraphbeforetable', // "表格前插入行"
		'insertcode', // 代码语言
		'fontfamily', // 字体
		'fontsize', // 字号
		'paragraph', // 段落格式
		'simpleupload', // 单图上传
		'insertimage', // 多图上传
		'edittable', // 表格属性
		'edittd', // 单元格属性
		'link', // 超链接
		'emotion', // 表情
		'spechars', // 特殊字符
		'searchreplace', // 查询替换
		'map', // Baidu地图
		'gmap', // Google地图
		'insertvideo', // 视频
		'help', // 帮助
		'justifyleft', // 居左对齐
		'justifyright', // 居右对齐
		'justifycenter', // 居中对齐
		'justifyjustify', // 两端对齐
		'forecolor', // 字体颜色
		'backcolor', // 背景色
		'insertorderedlist', // 有序列表
		'insertunorderedlist', // 无序列表
		'fullscreen', // 全屏
		'directionalityltr', // 从左向右输入
		'directionalityrtl', // 从右向左输入
		'rowspacingtop', // 段前距
		'rowspacingbottom', // 段后距
		'pagebreak', // 分页
		'insertframe', // 插入Iframe
		'imagenone', // 默认
		'imageleft', // 左浮动
		'imageright', // 右浮动
		'attachment', // 附件
		'imagecenter', // 居中
		'wordimage', // 图片转存
		'lineheight', // 行间距
		'edittip ', // 编辑提示
		'customstyle', // 自定义标题
		'autotypeset', // 自动排版
		'webapp', // 百度应用
		'touppercase', // 字母大写
		'tolowercase', // 字母小写
		'background', // 背景
		'template', // 模板
		'scrawl', // 涂鸦
		'music', // 音乐
		'inserttable', // 插入表格
		'drafts', // 从草稿箱加载
		'charts', // 图表
		] ],
		allowDivTransToP : false,
		initialFrameWidth : '100%',
		initialFrameHeight : 500,
		autoHeightEnabled : false,
		enableAutoSave : false
	});
	// 初始化插件
	$("#zyupload").zyUpload({
		width : "515px", // 宽度
		height : "310px", // 宽度
		itemWidth : "100px", // 文件项的宽度
		itemHeight : "85px", // 文件项的高度
		url : mainServer + "/common/uploadImg", // 上传文件的路径
		fileType : [ "jpg", "png", "gif", "JPG", "PNG", "GIF" ], // 上传文件的类型
		fileSize : 1048576, // 上传文件的大小
		multiple : false, // 是否可以多个文件上传
		dragDrop : true, // 是否可以拖动上传文件
		tailor : false, // 是否可以裁剪图片
		del : true, // 是否可以删除文件
		finishDel : true, // 是否在上传文件完成后删除预览
		isadd : false, // 区分添加专题 true 还是添加专题类型 false
		/* 外部获得的回调接口 */
		onSelect : function(selectFiles, allFiles) { // 选择文件的回调方法
														// selectFile:当前选中的文件
														// allFiles:还没上传的全部文件
			console.info("当前选择了以下文件：");
			console.info(selectFiles);
			if (allFiles.length > 1) {
				$.messager.alert('提示', '只能选择一张图片', 'error');
			}
		},
		onDelete : function(file, files) { // 删除一个文件的回调方法 file:当前删除的文件
											// files:删除之后的文件
			console.info("当前删除了此文件：");
			console.info(file.name);
		},
		onSuccess : function(file, resp) { // 文件上传成功的回调方法
			var response = eval('(' + resp + ')');
			console.info("此文件上传成功：");
			console.info(file.name);
			console.info("此文件上传到服务器地址：");
			console.info(response);
			$.messager.alert('提示', response.msg, 'info');
			buffer.append(response.imgurl + ',');
			imgBuffer.append('<img id="pimg" src="');
			imgBuffer.append(response.imgurl);
			imgBuffer.append('" width="160px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
			$("#articleCover").val(response.imgurl);
			$('#pics').html(imgBuffer.toString());
		},
		onFailure : function(file, response) { // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file.name);
		},
		onComplete : function(response) { // 上传完成的回调方法
			console.info("文件上传完成");
			console.info(response);
			imgBuffer.clean();
		}
	});

	boxDataGrid = DataGrid.createGrid('dataGrid');
});

var DataGrid = {
	createGrid : function(grId) {
		return $('#' + grId)
				.datagrid(
						{
							url : mainServer
									+ '/admin/product/queryProductAllJson?productStatus=1&isOnline=1',
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
										width : 100
									},
									{
										title : "商品名称",
										field : "product_name",
										width : 100
									},
									{
										title : "商品封面",
										field : "product_logo",
										width : 120,
										height : 80,
										align : "left",
										formatter : function(value, rec) {// 使用formatter格式化刷子
											return '<img src=' + value
													+ ' style="height:80px;"/>';
										}
									} ] ],
							toolbar : '#storeToolbar',
							onBeforeLoad : function(param) {
								$('#dataGrid').datagrid('clearSelections');
							},
							onLoadSuccess : function(data) {
							},
							onSelect : function(rowIndex, rowData) {
							},
							onUnselect : function(rowIndex, rowData) {
							}
						});
	}
};

function previewImg(e, f) {
	// $(e).parent().next().text('');
	var fileInput = $(e).next().find('input[type=file]');
	var file = fileInput[0].files[0];
	var img = new Image(), url = img.src = URL.createObjectURL(file);
	img.style.width = '60px';
	img.style.height = '60px';
	var $img = $(img);
	img.onload = function() {
		$(e).next().next().next().empty().prepend($img);
	}
}

function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/specialarticle/addSpecialArticle',
				onSubmit : function() {

					var articleTitle = $("#articleTitle").val(); // 专题文章标题
					if (articleTitle == "" || articleTitle == null) {
						$("#Title_msg").html("不能为空");
						$("#specialTitle").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}

					var articleIntroduce = $("#articleIntroduce").val(); // 专题文章介绍
					if (null == articleIntroduce || "" == articleIntroduce) {
						$("#Introduce_msg").html("不能为空");
						$("#articleIntroduce").focus();
						return false;
					} else if (articleIntroduce.length > 120) {
						$("#Introduce_msg").html("不能超过120字");
						$("#articleIntroduce").focus();
						return false;
					} else {
						$("#Introduce_msg").html("");
					}

					var specialUrl2 = $("#articleCover").val(); // 专题文章封面
					if ("" == specialUrl2 || null == specialUrl2) {
						$("#articleCover1").html("图片已失效请重新选择");
						return false;
					} else {
						$("#articleCover1").html("");
					}
					buffer.clean();
				},
				success : function(data) {
					var obj = eval('(' + data + ')');
					$.messager.alert('提示消息',
							'<div style="position:relative;top:20px;">'
									+ obj.msg + '</div>', 'success');
					if ("succ" == obj.result) {
						toBack();
					}
				}
			});
}

function saveProduct() {
	var objectArray = boxDataGrid.datagrid('getSelections');
	if (objectArray.length <= 0) {
		$.messager.alert('提示消息',
				'<div style="position:relative;top:20px;">请选择商品</div>',
				'success');
		return;
	}
	var productIds = "";
	var productNames = "";
	$.each(objectArray, function(i, obj) {
		productIds += obj.product_id + ",";
		productNames += obj.product_name + ",";
	});
	productIds = productIds.substring(0, productIds.length - 1);
	productNames = productNames.substring(0, productNames.length - 1);

	$("#productIds").val(productIds);
	$("#productNames").textbox('setValue', productNames);
	closeProductdilg();
}

function chooseProduct() {
	$("#productdlg").dialog("open");
	searchProduct();
}

function searchProduct() {
	$('#dataGrid').datagrid('load', {
		"productName" : $("#productName").val()
	});
}

function closeProductdilg() {
	$("#productdlg").dialog("close");
	$("#productName").textbox('setValue', '');
}

function removeProduct() {
	$("#productIds").val("");
	$("#productNames").textbox('setValue', "");
}

function toBack() {
	window.location.href = mainServer + "/admin/specialarticle/findList";
}