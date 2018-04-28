<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="UTF-8">
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="http://www.hlife.shop/huihuo/resources/weixin/css/common.css">
    	<link rel="stylesheet" href="http://www.hlife.shop/huihuo/resources/weixin/css/discuss.css">
    	<link rel="stylesheet" href="http://www.hlife.shop/huihuo/resources/weixin/css/photoswipe.css">
		<link rel="stylesheet" href="http://www.hlife.shop/huihuo/resources/weixin/css/default-skin/default-skin.css">
  		<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/application.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/toolbar.js"></script>
		<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
		
		<script type="text/javascript">
			var mainServer = '${mainserver}';
			var commentId = '${id}';
		</script>
		<script type="text/javascript" src="${resourcepath}/admin/js/article/article_comment_replys.js"></script>
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
	    <link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
		<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/zyupload-1.0.0.min.css">
		<title>挥货-评论详情</title>
		<style>  
			body{padding:10px;} 
			h3{font-size:14px; display:block; line-height:30px; font-weight:normal;color:#ff0000; margin-bottom: 10px; }   
		    .plbox{background:#fcfcfc;padding:10px;}
		    .plbox .pltopbox{overflow:hidden;zoom:1;margin-bottom: 10px; }
		    .plbox .pltopbox span{float:left; font-size: 14px;color:#333;}
		    .plbox .pltopbox span strong{font-weight:normal;color:blue;} 
		    .plbox .pltopbox p{float:right; font-size: 12px;color:#999;} 
		    .nrboxs{font-size: 14px;color:#666; line-height: 24px; } 
		    .plll{margin-bottom:20px;  }
		    .pllltop{border-top:1px solid #ccc; padding-top:20px; }
		    .huifubox a{text-decoration:underline;color:#094997;font-size:12px; }
		    .inputbox{margin-top:10px; } 
		    .inputbox textarea{width:98%; padding:1%;}   
		    .inputbox a{display:block;width:130px;height:40px;text-align:center;line-height:40px;text-align:center;color:#fff;font-size: 12px; background:#0a63bd; margin-top:20px; }    
		</style> 
	</head>
	
	<body>
		<h3>查看全部评论</h3> <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" data-options="selected:true" onclick="toBack()">返回</a>
		<div class="plbox">
			
			 
		</div>
		<h3 style="margin-top:20px; display: none" id="postbox">后台点击回复用户</h3>
		<div class="plboxs" >
		</div>
		<div class="vaguealert">
			<p></p>
		</div>
</body> 

</html>
