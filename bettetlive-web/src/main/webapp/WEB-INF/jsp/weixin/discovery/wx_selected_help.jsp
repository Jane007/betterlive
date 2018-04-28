<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<meta name="keywords" content="挥货,个人中心,挥货商城" /> 
		<meta name="description" content="挥货商城个人中心" />
		<title>分享规则</title>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/result.css"/>
		<link rel="stylesheet" type="text/css" href="${resourcepath}/weixin/css/discovery/wx_selected_help.css"/>
		<script src="${resourcepath}/weixin/js/flexible.js"></script>
		<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	</head>
	<body>
		<div id="layout">
			<div class="ongold">
				<div class="textlist">
					<div class="sharetop">
						<div class="box">
							<img src="${resourcepath}/weixin/img/discovery/sharerule.jpg"/>
						</div>
					</div>
					<div class="textcontent">
						<h3>通过哪些途径可获得大量金币呢？</h3>
						<p>1、每日抽签；</p>
						<p>2、发布动态且审核通过；</p>
						<p>3、分享文章/视频/动态内容；</p>
					</div>
					<div class="textcontent">
						<h3>可领取金币的范围是？</h3>
						<p>1、每日首次抽奖可获得数额不等的金币；</p>
						<p>2、发布动态且审核通过后，根据发表的内容评分等级，越优质的文章，金币越高；</p>
						<p>3、每日可多次分享不同的文章/视频/动态内容获得金币；</p>
					</div>
					<div class="textcontent">
						<h3>金币的奖励明细是？</h3>
						<table border="1" cellspacing="3" cellpadding="3">
							<tr>
								<th>事件</th>
								<th>金币</th>
								<th>有效次数(1自然日内)</th>
							</tr>
							<tr>
								<td>每日分享</td>
								<td>3</td>
								<td>5</td>
							</tr>
							<tr>
								<td>内容点赞</td>
								<td>1~10</td>
								<td>/</td>
							</tr>
							<tr>
								<td>发布帖子</td>
								<td>0~40</td>
								<td>2</td>
							</tr>
							<tr>
								<td>每日抽奖</td>
								<td>0~10</td>
								<td>1</td>
							</tr>
						</table>
						<p>说明：</p>
						<p>1、每日分享文章或者视频给朋友，分享成功后可获得3个金币，每日分享获得金币的次数为5次；</p>
						<p>2、分享到外部的文章或视频，有点赞量即可获得对应金币，1个赞=1个金币（金币为额外叠加）；</p>
						<p>3、根据帖子内容划分为4个等级；1级（特优）——20、2级（良好）——10、3级（一般）——5、4级（不通过）——0；</p>
					</div>
					<div class="textcontent">
						<h3>注意事项有哪些？</h3>
						<p>1、不同的金币到账类型金币的到账可能存在延迟，最迟30分钟内到账；</p>
						<p>2、每天的金币数量有限，送完即止；</p>
						<p>3、若金币已获得但未领取，将不做失效处理，直到更新金币体系才会清除金币；</p>
						<p>4、禁止通过违规操作获得金币，一旦发现违规获得金币，挥货有权对违规获得的金币进行回收且撤销相关订单；</p>
						<p>5、用户获得但未领取或未使用的金币，将会在下一个自然年过期，挥货将会对过期的金币做作废处理。例如：2018年12月31日将会清空2017年获得但未领取或未使用的金币；</p>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	//修改微信自带的返回键
	$(function(){
		var url = "${mainServer}/weixin/discovery/toSelected";
		var backUrl = "${backUrl}";
		if(backUrl != null && backUrl != "" && backUrl != undefined){
			url = backUrl;
		}
				
		var bool=false;  
	    setTimeout(function(){  
	          bool=true;  
	     },1000); 
			
	    window.addEventListener("popstate", function(e) {
			   if(bool){
				   window.location.href=url;	
			    }
		}, false);
	    
	    function pushHistory(){ 
			   var state = { 
		        	title: "title", 
		        	url:"#"
		        }; 
		        	window.history.pushState(state, "title","#");  
			    }
			    
			    $('.goback').click(function(){
					window.location.href=url;
				});
			    
	});
	</script>
</html>
