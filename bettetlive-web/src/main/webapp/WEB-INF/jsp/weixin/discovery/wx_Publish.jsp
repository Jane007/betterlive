<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,挥货商城,电子商务,网购,电商平台,厨房,农产品,绿色,安全,土特产,健康,品质" /> 
		<meta name="description" content="挥货，你的美食分享平台" /> 
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/common.css?t=201811072204" /> 
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/faxian.css?t=201801241022" />
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/discovery/wx_publishNew.css?t=201801310930" />
   		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
    	<script src="${resourcepath}/weixin/js/rem.js"></script>
        <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>  <!-- 必须要低版本jq -->  
   		<script src="http://www.jq22.com/jquery/jquery-migrate-1.2.1.min.js"></script>
	 	<script src="${resourcepath}/weixin/js/jquery-form.js"></script>
    	<title>挥货-发表心得</title> 
    	<style>
    		
			
    	</style>
    	<script type="text/javascript">  
			var mainServer = '${mainserver}'; 
			var myCustId = "${myCustId}";
			var resourcepath = "${resourcepath}";
			var backUrl = "${backUrl}";
			var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "https://hm.baidu.com/hm.js?d2a55783801cf33b1c198d5ebd62ec3d";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
			</script>
	</head>
	
	<body style="margin:0; padding:0; ">
		<div class="initloading"></div>
		<div class="bkbg" style="display: none;"></div> 
		<div class="shepassdboxs" style="display: none;" id='cancelEdit'>
				<span>你还没有编辑完，确定退出吗？</span>  
				<div class="qushan">
					<a class="left dia-cancel" href="javascript:closeConcernAlert();">取消</a>
					<a class="right dia-addCard" id="cancelId" href="javascript:void(0);">确定</a> 
				</div>
		</div>
		<!--头部-->
			<div class="header-wrap">
				<div class="header">
					<div class="goback">
						<img src="${resourcepath}/weixin/img/goback.png"/>
					</div>
					<h2>我要爆料</h2>
					<div class="draft">
						<span>存草稿</span>
					</div>
				</div>
			</div> 	
		<form action="${mainserver}/weixin/discovery/publishDynamic" id="publishForm" enctype="multipart/form-data">
		  <input type="hidden" id="backType"  name="backType" value="${backType}" readonly="readonly">
		  <div class="fabiaobox"> 
		  		<div class="fbtitle">
		  			<input type="text" id="articleTitle" name="articleTitle" placeholder="添加标题，限30个字" maxlength="30" onchange="fontxian()" onkeydown='if(event.keyCode==13) return false;' value=''/>       
		  			<span class="fontxian">30</span>
		  		</div> 
		  		<div class="fbcont">
		  			<pre><span></span><br></pre>
		  			<textarea id="contentShow" name="contentShow" placeholder="写下你的心得吧..." rows="" cols="" ></textarea> 	 
	  				<textarea id="content" name="content" rows="" cols="" style="display: none;"></textarea> 	 
		  		</div>
		  		<div class="xuantubox">
		  			<!--图片选择对话框-->
		  			
		  			<!--图片预览容器-->
				    <div id="div_imglook"> 
				  	    <div id="div_imgfile"><img  src="${resourcepath}/weixin/img/fazp.gif" alt="" /></div> 
				        <div style="clear: both;"></div>
				    </div>
		  		</div>
		  </div>
		  <div class="xindebox"> 
		  		<a href="${mainserver}/weixin/discovery/toArticleRight">如何写出一篇优质的心得</a>
		  </div>
		  <div class="fabuxdboxs" id="btn_ImgUpStart">
		  		<a id="publishId" href="javascript:publish();">发布</a>
		  </div>
	  </form> 
	  <!--进度条-->
	  <div class="progress">
			<h3>正在上传...</h3>
			<div class="plan-wrap">
				<div class="plan">
					<span>0%</span>
				</div>
			</div>
	  </div> 	
	  <div class="vaguealert">
			<p></p>
		</div>
		<div class="bkbg" style="display: none;"></div> 
		<div class="shepassdboxs" style="display: none;" id="draftEdit">
			<span>您最近保存了一篇草稿，是否需要编辑？</span>  
			<div class="qushan">
				<a class="left" id="cancelId" href="javascript:closeAlert();">不了</a>
				<a class="right" id="surePayId" href="javascript:toEdit();">去编辑</a> 
			</div>
		</div>
		<!--分享成功弹窗提示-->
		<div class="oshate-succeed">
			<div class="hold">
				<h3>发布成功</h3>
				<p>审核通过后</p>
				<p>最高可获得20个金币噢</p>
			</div>
		</div>
	</body> 
  
	<script src="${resourcepath}/weixin/js/common.js"></script>
	<script src="${resourcepath}/weixin/js/discovery/wx_publish.js"></script>
 	
</html>