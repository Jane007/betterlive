var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
var picsBanner = new Array();

var boxDataGrid;
$(function() {
	
	$("#status").val(status);
	$("#articleTypeId").val(articleTypeId);
	$("#content").val(toTextarea(articleContent));
	queryPictures();

	// 初始化插件
	var bannerzy = $("#bannerUpload")
			.zyUpload(
					{
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
						isadd : false, // 区分添加商品 true 还是添加商品类型 false
						/* 外部获得的回调接口 */
						onSelect : function(selectFiles, allFiles) { // 选择文件的回调方法
																		// selectFile:当前选中的文件
																		// allFiles:还没上传的全部文件
							console.info("当前选择了以下文件：");
							console.info(selectFiles);
							if (allFiles.length > 9) {
								$.messager.alert('提示', '只能选择六张图片', 'error');
							}
						},
						onDelete : function(file, files) { // 删除一个文件的回调方法
															// file:当前删除的文件
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
							// $.messager.alert('提示',response.msg,'info');
							buffer.append(response.imgurl + ',');
							
							imgBuffer.clean();
							imgBuffer.append('<li><img src="');
							imgBuffer.append(response.imgurl);
							imgBuffer
									.append('" width="160px"/><div class="action">&nbsp;<a class="del">删除</a>&nbsp;&nbsp;<a class="mainPic">设为封面</a></div></li>');
							$('#bannerShow').append(imgBuffer.toString());
							picsBanner.push(response.imgurl);
							console.log(picsBanner + "---productBanner");
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
							url : mainServer + '/admin/product/queryProductAllJson?productStatus=1&isOnline=1',
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

function queryPictures() {
	$.ajax({
		url : mainServer + '/admin/specialarticle/queryPictureList',
		data : {
			"objectId" : $("#articleId").val()
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.list == null || data.list.length <= 0) {
				return;
			}
			picsBanner = data.pictureArray;
			var list = data.list;
			
			var articleCover = $("#articleCover").val();
			for ( var continueIndex in list) {
				if (isNaN(continueIndex)) {
					continue;
				}
				var vo = list[continueIndex];
				
				var showHtml = '<li class="noMainPic" data-picid="'+vo.picture_id +'" data-soft="'+vo.sort+'"><img alt="" src="' + vo.original_img + '"><div class="action">&nbsp;<a class="del">删除</a>&nbsp;&nbsp;<a class="mainPic">设为封面</a></div></li>';
				if(articleCover===vo.original_img){
					$("#mainPicIndex").val(continueIndex);
					showHtml = showHtml.replace("设为封面","封面图").replace("noMainPic","mainPic");
				}
				$("#bannerShow").append(showHtml);
			}
			//mainPicIndex
			$("body").on("click",".bannerShow a.mainPic",function(){
				if(!confirm("是否要设为封面图？")){
					return;
				}
				
				var pli = $(this).parents('li');
				$("#mainPicIndex").val(pli.index());
				console.log("mainpic index:%d",pli.index());
				$(this).text("封面图").parents("li")
				.removeClass("noMainPic").addClass("mainPic")
				.siblings("li").removeClass("mainPic").addClass("noMainPic")
				.find("a.mainPic").text("设为封面");
			
			});
			
			var delPicUrl = mainServer + "/admin/specialarticle/delProdImg";
			$("body").on("click",".bannerShow a.del",function(){
				if(!confirm("是否要删除图片？")){
					return;
				}
				
				var tli = $(this).parents('li');
				var pid= tli.data('picid');
				if(!pid){
					$(tli).remove();
					reSetterImg();
					alert("删除图片成功。");
					//alert("新上传的图片无法删除，请在修改状态删除。");
					return;
				}
				
				$.ajax({
					type:"post",
					dataType:"json",
					url:delPicUrl,
					data:{"pictureId":pid},
					success:function(data){
						//成功后把页面中的顺序也重置
						$(tli).remove();
						reSetterImg();
						alert("删除图片"+data.msg);
						
					}
				});
			});
			//重新初始化内存中缓存的图片信息
			function reSetterImg(){
				picsBanner = new Array();
				var imgs='';
				$('.bannerShow').find('img').each(function(i,o){
					picsBanner.push($(o).attr("src"));
				});
				$("#mainPicIndex").val($('.bannerShow').find('li.mainPic').index());
				//console.log("改变顺序后，封面图 index：%d",$('.bannerShow').find('li.mainPic').index());
				//console.log("save imgs: %s",picsBanner);
			}
			if($('.bannerShow').find("li").size()>0){
			//初始化排序 
			$('.bannerShow').sortable().bind('sortupdate', function() {
				var upPicUrl = mainServer + "/admin/specialarticle/updatePictureSort";
					$('.bannerShow').find('img').each(function(i,o){
					
						if($(o).data('sort')!=i){
							var pid=$(o).parent('li').data('picid');
							var sort=i;
							//console.info("图片id%d,图片順序%d",pid,i)
							//Integer picId,Integer sort,Boolean isDel
							$.ajax({
								type:"post",
								dataType:"json",
								url:upPicUrl,
								async:true,
								data:{"pictureId":pid,"sort":sort},
								success:function(data){
									if(data.code=='1010'){
										$(o).parent('li').data('picid',sort);
									}
									reSetterImg();
								}
							});
						}
					});
				});
			}
		},
		failure : function(data) {
			alert('出错了');
		}
	});
}
/**
 * @funciton 转换textarea存入数据库的回车换行和空格 textarea --- 数据库,用val取数据，置换'\n'
 */
function textareaTo(str) {
	var reg = new RegExp("\n", "g");
	var regSpace = new RegExp(" ", "g");

	str = str.replace(reg, "<br>");
	str = str.replace(regSpace, "&nbsp;");

	return str;
}

/**
 * @funciton 数据库 --- 编辑页面 .val(str)
 */
function toTextarea(str) {
	var reg = new RegExp("<br>", "g");
	var regSpace = new RegExp("&nbsp;", "g");

	str = str.replace(reg, "\n");
	str = str.replace(regSpace, " ");

	return str;
}

function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainServer + '/admin/specialarticle/editCircleArticle',
				onSubmit : function() {

					var articleTitle = $("#articleTitle").val(); // 社区文章标题
					if (articleTitle == "" || articleTitle == null) {
						$("#Title_msg").html("不能为空");
						$("#specialTitle").focus();
						return false;
					} else {
						$("#Title_msg").html("");
					}

					var content = $("#content").val();
					if (content == null || content == "") {
						$("#content_msg").html("内容不能为空");
						return false;
					} else {
						var ctt = textareaTo(content);
						$("#content").val(ctt);
						$("#content_msg").html("");
					}

					// 商品封面图
					if (picsBanner == null || picsBanner == '') {
						$("#banner_msg").text('请至少上传一张商品轮播图');
						return false;
					} else {
						$('#pictureArray').val(picsBanner);
						$("#banner_msg").text("");
					}

					// var articleIntroduce=$("#articleIntroduce").val();
					// //社区文章介绍
					// if(null == articleIntroduce || "" ==articleIntroduce){
					// $("#Introduce_msg").html("不能为空");
					// $("#articleIntroduce").focus();
					// return false;
					// }else if(articleIntroduce.length > 120){
					// $("#Introduce_msg").html("不能超过120字");
					// $("#articleIntroduce").focus();
					// return false;
					// }else{
					// $("#Introduce_msg").html("");
					// }

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
	window.location.href = mainServer + "/admin/specialarticle/findCircleList";
}
