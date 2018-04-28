var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();

$(function() {
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
		isadd : false, // 区分添加拼团活动 true 还是添加拼团活动类型 false
		/* 外部获得的回调接口 */
		onSelect : function(selectFiles, allFiles) { // 选择文件的回调方法
														// selectFile:当前选中的文件
														// allFiles:还没上传的全部文件
			console.info("当前选择了以下文件：");
			console.info(selectFiles);
			if (allFiles.length > 1) {
				$.messager.alert('提示', '只能选择一张图片', 'error');
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
			$.messager.alert('提示', response.msg, 'info');
			buffer.append(response.imgurl);
			imgBuffer.append('<img id="pimg" src="');
			imgBuffer.append(response.imgurl);
			imgBuffer.append('" width="160px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
			$("#typeCover").val(response.imgurl);
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

});

function isSubmit() {
	$('#dataform').form(
			'submit',
			{
				url : mainServer + '/admin/specialarticletype/editArticleType',
				onSubmit : function() {

					var typeName = $("#typeName").val(); // 专题类型名称
					if (typeName == "" || typeName == null) {
						$("#name_msg").html("不能为空");
						$("#typeName").focus();
						return false;
					} else {
						$("#name_msg").html("");
					}

					var sort = $("#sort").val(); // 排序
					if (sort == "" || sort == null) {
						$("#sort_msg").html("不能为空");
						$("#sort").focus();
						return false;
					} else {
						$("#sort_msg").html("");
					}

					$("#typeCover").val(buffer.toString());
					var specialUrl2 = $("#typeCover").val(); // 拼团活动封面
					if ("" == specialUrl2 || null == specialUrl2) {
						$("#typeCover1").html("图片已失效请重新选择");
						return false;
					} else {
						$("#typeCover1").html("");
					}
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

function toBack() {
	window.location.href = mainServer + "/admin/specialarticletype/findList";
}