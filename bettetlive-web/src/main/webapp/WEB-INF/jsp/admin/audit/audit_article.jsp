<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<meta content="telephone=no" name="format-detection">
    	<meta content="email=no" name="format-detection">
    	<link rel="stylesheet" href="${resourcepath}/weixin/css/photoswipe.css">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/default-skin/default-skin.css">
		<link rel="stylesheet" href="${resourcepath}/weixin/css/swiper-3.3.1.min.css?t=201711072204" />
		<title>挥货-文章审核</title> 
	   <script  type="text/javascript">
		    var mainServer= "${mainserver}";
		    var customerId="${customerId}";
	    </script>
		<style type="text/css">  
					*{
		    margin: 0;
		    padding: 0;
		    -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
		   -webkit-touch-callout:none;  /*系统默认菜单被禁用*/
		    -webkit-user-select:none; /*webkit浏览器*/
		    -khtml-user-select:none; /*早期浏览器*/
		    -moz-user-select:none;/*火狐*/
		    -ms-user-select:none; /*IE10*/
		    user-select:none;
		}
		input,textarea {
		    -webkit-user-select:auto; /*webkit浏览器*/
		    margin: 0px;
		    padding: 0px;
		    outline: none;
		     -webkit-appearance: none;  
		}  
		body{
		background:#efefef;  
		} 
		.shenhetop{background:#fff;height:0.81rem;position:fixed;top:0;width:100%;border-bottom:0.02rem solid #ddd; z-index:200; } 
		.shenhetop ul{display:block;overflow:hidden;zoom:1; font-size:0;background:#fff;height:0.81rem; line-height:0.81rem; } 
		.shenhetop ul li{width:33.33%;display:inline-block; text-align:center;font-size:0.26rem;color:#999;position:relative;}
		.shenhetop ul li:after{position:absolute;content:""; width:1px; height:40%; background:#eee; top:30%;right:0; }
		.shenhetop ul li a{display:block;width:100%;height:100%; }  
		.shenhetop ul li.current a{color:#e62d29;} 
		.shencontbox{border-top:0.02rem solid #ddd;   }    
		.plyi{overflow:hidden;zoom:1;height:0.6rem;line-height:0.6rem;margin-bottom:0.25rem; padding:0 0.26rem; border-bottom:0.02rem solid #ddd;padding-bottom:0.2rem; }
		.plyi em{float:left;width:0.6rem;height:0.6rem;overflow:hidden;border-radius:0.6rem;margin-right:0.17rem;font-size:0; }  
		.plyi em img{width:100%; height:100%;}
		.plyi span{float:left;font-size:0.24rem;color:#868686;}
		.plyi p{float:right;font-size:0.2rem;color:#868686;}
		.tiecont{padding:0.26rem 0;margin-bottom:0.2rem;background:#fff;   }  
		
		.mesPic{padding-left:0.26rem; }		     
		.dtboximlist .my-gallery figure{ width:2rem;height:2rem;  margin-bottom:0.12rem;  font-size:0;overflow:hidden; }   
		.dtboximlist .my-gallery figure img{width:100%;height:100%;}   
		.shenhefont{font-size:0.24rem;color:#666; padding:0 0.26rem; }    
		.tbox{overflow:hidden;zoom:1; text-align:right; padding:0 0.26rem;  font-size:0; padding-top:0.2rem; } 
		.tbox a{display:inline-block; padding:0.1rem 0.2rem; background:#eee;color:#333; font-size:0.24rem;margin-left:0.2rem; border-radius:0.08rem; }  
		a.notong{background:#e62d29;color:#fff;}
		.zhankaibox a{font-size:0.26rem;color:#e62d29;}  
		.zhankaibox{ border-bottom:0.02rem solid #ddd; font-size:0; padding-left:0.26rem;padding-bottom:0.2rem;display:none; } 
		.loadingmore{font-size:0.24rem;position:fixed;bottom:0; width:100%; text-align:center; background:#fff;height:0.8rem;line-height:0.8rem;  display:none;  overflow:hidden;zoom:1;left:0; z-index:1000;  }
		.loadingmore img{float:left; width:1.5rem;height:1rem; margin-top:-0.1rem;margin-left:20%;}
		.loadingmore p{float:left; margin-left:-0.4rem;  color:#898989; }
		</style>
	</head>
	<body>
		<div class="shenhetop">
			<ul> 
				<li id="tab0" class="current"> 
					<a href="javascript:refresh(0);">待审核(${pendingAudit})</a>
				</li>
				<li id="tab1">
					<a href="javascript:refresh(1);">已通过(${auditPassed})</a>
				</li>
				<li id="tab2">
					<a href="javascript:refresh(2);">未通过(${noPassed})</a>
				</li>
			</ul>
		</div>
		<div class="shencontbox" id="myContent0">
		</div>
		<div class="shencontbox" style="display:none;" id="myContent1">
		</div>
		<div class="shencontbox" style="display:none;" id="myContent2">
		</div>
		<div class="shencontbox"  style="display:none;" id="myContent3">
			 <span>暂无数据</span>
		</div>
		<div class="loadingmore">   
			<img src="${resourcepath}/weixin/img/timg.gif" alt="" /><p>正在加载更多的数据...</p>
		</div>
		
		 <input type="hidden" name="pageCount" id="pageCount" value="">
		 <input type="hidden" name="pageNow" id="pageNow" value="">
		 <input type="hidden" name="pageNext" id="pageNext" value="">
		 <!--查看大图的背景以及按键-->
		<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true"  >  
		    <div class="pswp__bg"></div> 
		    <div class="pswp__scroll-wrap" >    
		        <div class="pswp__container"> 
		            <div class="pswp__item"></div>
		            <div class="pswp__item"></div>
		            <div class="pswp__item"></div>    
		        </div>
		        <div class="pswp__ui pswp__ui--hidden">
		            <div class="pswp__top-bar">
		                <div class="pswp__counter"></div>
		                <div class="pswp__preloader">
		                    <div class="pswp__preloader__icn">
		                      <div class="pswp__preloader__cut">
		                        <div class="pswp__preloader__donut"></div>
		                      </div>
		                    </div> 
		                </div>  
		            </div>
		            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap" >
		                <div class="pswp__share-tooltip"></div> 
		            </div> 
		            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
		            </button>
		            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
		            </button>
		            <div class="pswp__caption" > 
		                <div class="pswp__caption__center"></div>   
		            </div>    
		        </div>   
		    </div>
		</div>
	</body> 
	<script src="${resourcepath}/weixin/js/jquery-2.1.1.min.js"></script>
	<script src="${resourcepath}/weixin/js/photoswipe.js"></script>
	<script src="${resourcepath}/weixin/js/photoswipe-ui-default.min.js"></script>  
	<script src="${resourcepath}/weixin/js/swiper-3.3.1.min.js"></script>
	<script type="text/javascript" src="${resourcepath}/admin/js/audit/audit_article.js"></script>
</html>

