var brokeNews = {};
var url=mainServer+"/weixin/discovery/toDynamic";

function closeAlert() {
	$('.shepassdboxs').hide();
	$('.bkbg').hide();
	localStorage.removeItem("brokeNews_"+myCustId);
}
function toEdit() {
	$('.shepassdboxs').hide();
	$('.bkbg').hide();
	var obj = localStorage.getItem("brokeNews_"+myCustId)// 读取缓存，括号里面就是储存的名字
	obj = typeof obj == 'object' ? data : $.parseJSON(obj);
	var title = obj.title;
	$("#articleTitle").val(title);
	var content = obj.content;
	$("#contentShow").val(content);

}

function fontxian() {
	var fontxz = 30;
	var backTypeval = $("#articleTitle").val();
	var backTypelen = backTypeval.length;
	var fontXf = fontxz - backTypelen;

	if (fontXf >= 0) {
		$(".fontxian").text(fontXf);
	}

}

$(function() {
	var obj = localStorage.getItem("brokeNews_"+myCustId)// 读取缓存，括号里面就是储存的名字
	if (obj != null && obj != '') {
		$('.shepassdboxs').show();
		$('.bkbg').show();
	}
	$('.goback').on('click',function(){
		var backType=$("#backType").val();
		if(backType != null && backType == 1){
			url=mainServer+"/weixin/discovery/toDynamic";
		}
		if(backType != null && backType == 2){
			url=mainServer+"/weixin/socialityhome/toSocialityHome";
		}
		if(backType != null && backType == 3){
			url=mainServer+"/weixin/integral/toWinIntegral";
		}
		if(backUrl!=null){
			url = backUrl;
		}
		alertCancelTip(url);
	});
});

// 保存草稿
$('.draft').on('click', function() {
	var articleTitle = $("#articleTitle").val();
	if (articleTitle != null) {// 放缓存草稿标题
		brokeNews.title = articleTitle;
	} 
	var content = $("#contentShow").val();
	if (content != null && content != "") {// 放缓存草稿内容
		brokeNews.content = content;
	}
	
	if(articleTitle=='' && content==''){
		showvaguealert("输入内容才能保存草稿哦");
		return;
	}
	
	if(articleTitle!='' || content!=''){
		localStorage.setItem('brokeNews_'+myCustId, JSON.stringify(brokeNews))// 存，前面的brokeNews是存储名
		showvaguealert("保存成功，可再次编辑哦！");
	}

});

var IMG_MAXCOUNT = 9;// 最多选中图片张数
var IMG_AJAXPATH = mainServer + "/weixin/discovery/publishDynamic";// 异步传输服务端位置
var UP_IMGCOUNT = 1;// 上传图片张数记录
// 打开文件选择对话框
$("#div_imgfile").click(function() {
	if ($(".lookimg").length > IMG_MAXCOUNT) {
		showvaguealert("一次最多上传" + IMG_MAXCOUNT + "张图片");
		return;
	}
	;

	var _CRE_FILE = document.createElement("input");
	if ($(".imgfile").length <= $(".lookimg").length) {// 个数不足则新创建对象
		_CRE_FILE.setAttribute("type", "file");
		_CRE_FILE.setAttribute("class", "imgfile");
		_CRE_FILE.setAttribute("multiple", "true");

		_CRE_FILE.setAttribute("name", "atcImgs");
		_CRE_FILE.setAttribute("num", UP_IMGCOUNT);// 记录此对象对应的编号
		$("#div_imgfile").after(_CRE_FILE);
	} else { // 否则获取最后未使用对象
		_CRE_FILE = $(".imgfile").eq(0).get(0);

	}
	return $(_CRE_FILE).click();// 打开对象选择框
});

// 创建预览图，在动态创建的file元素onchange事件中处理
$(".imgfile")
		.live(
				"change",
				function() {
					
					for (var i = 0; i < this.files.length; i++) {
						$(".initloading").show();
						// 预览功能 end
						setTimeout(function() {
							$(".initloading").hide();
						}, 800);
						if ($(this).val().length > 0) {// 判断是否有选中图片
							 $("#div_imgfile img").attr('src',resourcepath+'/weixin/img/jiazp.png');

							// 判断图片格式是否正确
							var FORMAT = $(this).val().substr(
									$(this).val().lastIndexOf(".") + 1,
									$(this).val().length).toLowerCase();
							if (FORMAT != "png" && FORMAT != "jpg"
									&& FORMAT != "jpeg" && FORMAT != "gif") {
								showvaguealert("图片只能是jpg、jpeg、png、gif格式");
								return;
							}
							// 判断图片是否过大，当前设置1MB
							var file = this.files[i];// 获取file文件对象
							// 创建预览外层
							var _prevdiv = document.createElement("div");
							_prevdiv.setAttribute("class", "lookimg");
							// 创建内层img对象
							var preview = document.createElement("img");
							$(_prevdiv).append(preview);
							// 创建删除按钮
							var IMG_DELBTN = document.createElement("div");
							IMG_DELBTN.setAttribute("class", "lookimg_delBtn");
							IMG_DELBTN.innerHTML = "<img class='close' src='"+ resourcepath + "/weixin/img/hbxx.png'>";
							$(_prevdiv).append(IMG_DELBTN);
							// 计算$('#div_imglook')的宽度
							$('#div_imglook').width(($('.lookimg').length + 1) * 110 + 110);
							// 图片添加后动态控制滚动条到最右边
							$('.xuantubox').scrollLeft($('#div_imglook').width()- $('.xuantubox').width())
							// 创建进度条
							var IMG_PROGRESS = document.createElement("div");
							IMG_PROGRESS.setAttribute("class",
									"lookimg_progress");
							$(IMG_PROGRESS).append(document.createElement("div"));
							$(_prevdiv).append(IMG_PROGRESS);
							// 记录此对象对应编号
							_prevdiv.setAttribute("num", $(this).attr("num"));
							// 对象注入界面
							$("#div_imglook").children("#div_imgfile").before(_prevdiv);
							UP_IMGCOUNT++;// 编号增长防重复

							// 预览功能 start
							preview.src = window.URL.createObjectURL(this.files[i])
						}
					}
				});

// 删除选中图片
$(".lookimg_delBtn").live(
		"click",
		function() {
			if ($('.lookimg').length - 1 < 1) {
				$("#div_imgfile img").attr('src',
						resourcepath + '/weixin/img/fazp.gif');
			} else {
				$("#div_imgfile img").attr('src',
						resourcepath + '/weixin/img/jiazp.png');
			}
			;

			$(".imgfile[num=" + $(this).parent().attr("num") + "]").remove();// 移除图片file
			$(this).parent().remove();// 移除图片显示
			// 计算$('#div_imglook')宽度
			$('#div_imglook').width(($('.lookimg').length) * 110 + 110);

		});

function publish() {
	if (myCustId <= 0) {
		window.location.href = mainServer + "/weixin/tologin";
		return;
	}

	var articleTitle = $("#articleTitle").val();
	if (articleTitle == null || articleTitle == "" || articleTitle.length <= 0) {
		showvaguealert("请添加标题");
		return;
	}
	if (articleTitle.length > 30) {
		showvaguealert("标题长度不能超过30");
		return;
	}
	var content = $("#contentShow").val();
	if (content == null || content == "" || content.length <= 0) {
		showvaguealert("请写下心得");
		return;
	}

	var ctt = textareaTo(content);
	$("#content").val(ctt);

	if ($(".lookimg").length <= 0) {
		showvaguealert("请添加照片");
		return;
	}
	if ($(".lookimg").length > IMG_MAXCOUNT) {
		showvaguealert("一次最多上传" + IMG_MAXCOUNT + "张图片");
		return;
	}
	$("#publishId").attr("href", "javascript:void(0);");
	$('.progress').show();
	var percent = 0;
	$('#publishForm').ajaxSubmit({
		type : 'post',
		dataType : "json",
		sync : true,
		error : function(data) {
		},
		success : function(data) {
			$("#publishId").attr("href", "javascript:publish();");
			if ("1010" == data.code) {
				// showvaguealert("发布成功");
				localStorage.removeItem("brokeNews_"+myCustId);// 删除草稿
				if (percent >= 100) {	//上传成功
					 $(".progress").fadeToggle(1000,function(){
						 $('.oshate-succeed').fadeIn(1000,function(){
							 setTimeout(function(){
								 $('.oshate-succeed').fadeOut(1000,function(){
									 window.location.href=mainServer+"/weixin/socialityhome/toSocialityHome";
								 })
							 },500)
						 })
					 });

				}

			} else if ("1011" == data.code) {
				window.location.href = mainServer + "/weixin/tologin";
			} else {
				$(".initloading").hide();
				 $(".progress").hide();
				showvaguealert(data.msg);
			}
		},
		xhr : function() {
			myXhr = $.ajaxSettings.xhr();
			if (myXhr.upload) {
				myXhr.upload.addEventListener('progress', function(e) {
					if (e.lengthComputable) {
						percent = Math.floor(e.loaded / e.total * 100);
						if (percent <= 100) {
							$(".plan").stop().animate({
								"width" : percent + "%"
							}, 1000);
							$(".plan span").html(percent + '%');
						}
					}
				}, false);
			}
			return myXhr;
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


//修改微信自带的返回键

function closeConcernAlert(){
	 $(".bkbg").hide();
	 $(".shepassdboxs").hide();
	   var state = { 
   		title: "title", 
   		url:"#"
   	}; 
   	window.history.pushState(state, "title","");  
}

function alertCancelTip(url){
	 var conIndex = 0;
	 var articleTitle = $("#articleTitle").val();
	 var content = $("#contentShow").val();
	 if(articleTitle != null && articleTitle != ""){
		 conIndex = 1;
	 }else if(content != null && content != ""){
		 conIndex = 1;
	 }else if($(".lookimg").length > 0){
		 conIndex = 1;
	 }
	 if(conIndex == 1){
		 $(".bkbg").show();
		 $("#cancelEdit").show();
	 }else{
		 $("#draftEdit").hide();
		 window.location.href=url;
	 }
}

$(function(){
	
	
	var backType=$("#backType").val();
	if(backType != null && backType == 1){
		url=mainServer+"/weixin/discovery/toDynamic";
	}
	if(backType != null && backType == 2){
		url=mainServer+"/weixin/socialityhome/toSocialityHome";
	}
	if(backType != null && backType == 3){
		url=mainServer+"/weixin/integral/toWinIntegral";
	}
	if(backUrl!=null){
		url = backUrl;
	}
	
	$("#cancelId").attr("href", url);
	var bool=false;  
   setTimeout(function(){  
         bool=true;  
   },1000); 
       
	pushHistory(); 
	
   window.addEventListener("popstate", function(e) {
   	if(bool){
   		alertCancelTip(url);
   	}
   }, false);
   
   
   function pushHistory(){ 
       var state = { 
   		title: "title", 
   		url:"#"
   	}; 
   	window.history.pushState(state, "title","");  
   }
   
});
