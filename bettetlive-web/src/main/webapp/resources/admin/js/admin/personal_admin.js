function isSubmit() {
	$('#specialform').form(
			'submit',
			{
				url : mainserver + '/admin/personal/editAdmin',
				onSubmit : function() {
					// 用户名称
					var username = $("#username").val();
					if (null == username || "" == username) {
						$("#username_msg").html("不能为空");
						$("#username").focus();
						return false;
					} else {
						$("#username_msg").html("");
					}

					var loginname = $("#loginname").val(); // 登录账号
					if (loginname == "" || loginname == null) {
						$("#loginname_msg").html("不能为空");
						$("#loginname").focus();
						return false;
					} else {
						$("#loginname_msg").html("");
					}

					var passwo = $("#passwo").val(); // 登录密码
					if (passwo == "" || passwo == null) {
						$("#password_msg").html("");
					} else {
						$("#password_msg").html("");
						$("#password").val($.md5(passwo));
					}
				},
				success : function(data) {
					var obj = eval('(' + data + ')');
					$.messager.alert('提示消息',
							'<div style="position:relative;top:20px;">'
									+ obj.msg + '</div>', 'success');
					if ("1010" == obj.code) {
						$("#staffId").val(obj.data.staffId);
						$("#headUrl").val(obj.data.headUrl);
						$("#showHeadImg").attr("src", obj.data.headUrl);
						window.parent.document.getElementById("mainHeadUrl").src = obj.data.headUrl;
//						$(".pf-user").parent().find("img").attr("src", obj.data.headUrl);
					}
				}
			});

}
				function loadImage(img) {
				    var filePath = img.value;
				    var fileExt = filePath.substring(filePath.lastIndexOf("."))
				        .toLowerCase();
						var fileSize = 1024*1024+1;
				    if (!checkFileExt(fileExt)) {
				        alert("您上传的文件不是图片,请重新上传！");
				        img.value = "";
				        return;
				    }
				    if (img.files && img.files[0]) {
				    	fileSize=img.files[0].size;
				    } else {
				        img.select();
				        var url = document.selection.createRange().text;
				        try {
				            var fso = new ActiveXObject("Scripting.FileSystemObject");
				        } catch (e) {
				            alert('如果你用的是ie8以下 请将安全级别调低！');
				        }
				        filesize=fso.GetFile(url).size;
				    }
				    
				    return fileSize;
				} 
				function checkFileExt(ext) {
				    if (!ext.match(/.jpg|.gif|.png|.bmp/i)) {
				        return false;
				    }
				    return true;
				}
				function validateImgSize(file,size){
					var fileSize = loadImage(file);
					console.log("file size :%s, confine size: %s",fileSize,size)
					
					if(fileSize<=size){
						return true;
					}else{
						alert("请上传小于 1M 的图片.")
						file.value="";
						return false;
					}
				}

function toReload() {
	window.location.reload();
}