var StringBuffer = Application.StringBuffer;
var buffer = new StringBuffer();
var imgBuffer = new StringBuffer();
$(function() {
	// 初始化插件
    uploadProductBanner();	
    loadProductBanner();
    reSetterImg();
});


function loadProductBanner(){
	var delPicUrl = mainServer + "/admin/product/delProdImg";
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
	if($('.bannerShow').find("li").size()>0){
					
		$('.bannerShow').sortable().bind('sortupdate', function() {
			var upPicUrl = mainServer + "/admin/product/goodsSort";
			$('.bannerShow').find('img').each(function(i,o){
				
				if($(o).data('sort')!=i){
					var pid=$(o).parent('li').data('picid');
					var sort=i;
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
							reSetterImg()
						}
					});
				}
			});
		});
	}
}

function uploadProductBanner(){
	$("#bannerUpload").zyUpload({
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
			if (allFiles.length > 6) {
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
			imgBuffer.clean();
			// $.messager.alert('提示',response.msg,'info');
			imgBuffer.append('<li><img src="');
			imgBuffer.append(response.imgurl);
			imgBuffer
					.append('"/><div class="action">&nbsp;<a class="del">删除</a></div></li>');
			$('#bannerShow').append(imgBuffer.toString()); 
			
			//这里设置成null的话那代表之前上传的被清空了
			//parent.productBanner[0] = '';
			parent.productBanner.push(response.imgurl);
			console.log("begin parent.productBanner");
			console.log(parent.productBanner);
			console.log("end parent.productBanner");
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
}

//重新初始化内存中缓存的图片信息
function reSetterImg(){
	parent.productBanner = new Array();
	var imgs='';
	$('.bannerShow').find('img').each(function(i,o){
		parent.productBanner.push($(o).attr("src"));
	});
	
}