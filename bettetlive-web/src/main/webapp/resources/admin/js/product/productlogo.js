$(function() {
	// 初始化插件
	var proLogo = $("#zyupload").zyUpload({
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
			if (allFiles.length > 1) {
				$.messager.alert('提示', '只能选择三张图片', 'error');
			}
			if (allFiles.length == 1) {
				$.messager.alert('提示', '选完图片请点击新增再保存', 'info');
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
			// $.messager.alert('提示',response.msg,'info');
			$('#pimg').attr('src', response.imgurl).show();
			parent.productLogo = response.imgurl;
		},
		onFailure : function(file, response) { // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file.name);
		},
		onComplete : function(response) { // 上传完成的回调方法
			console.info("文件上传完成");
			console.info(response);
		}
	});
});