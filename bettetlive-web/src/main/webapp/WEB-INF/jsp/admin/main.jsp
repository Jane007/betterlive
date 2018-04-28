<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>挥货后台管理系统</title>
		<%@include file="common.jsp"%>
	</head>
	 
	<body>
	    <div class="container">
	        <div id="pf-hd">
	            <div class="pf-logo">
	              <img src="${resourcepath}/admin/images/logo.png" alt="logo" style="margin-top:0px;">
	         
	            </div>
	
	            <div class="pf-user">
	           			<img id="mainHeadUrl" src="${admin.headUrl}" alt="" />
	                <%-- <span style="color:#fff;">${admin.username}，欢迎你回来！</span> --%>
	                <span style="color:#fff;margin-left: 1rem;">${username}，欢迎你回来！
	                	<input type="hidden" id="username" value="${username}"/>
	                </span>
	                <button class="loginout" style="color:#fff; width:52px;height:26px;background: #000;border:1px;cursor: pointer;">
	                                                           退出
	                </button>
	                
	            </div>
	
	        </div>
	
	        <div id="pf-bd">
	            <div id="pf-sider">
	                <ul class="sider-nav">
	            		 <c:forEach var="menu" items="${list }">
	            		
	            			<!-- <li class="current"> -->
	            			<li >
		                        <a href="javascript:;">
		                            <span class="iconfont sider-nav-icon">&#xe620;</span>
		                            <span class="sider-nav-title">${menu.menuName }</span>
		                            <i class="iconfont">&#xe642;</i>
		                        </a>
		                        <c:forEach var="cm" items="${childList }">
		                        	<c:if test="${cm.parentId eq menu.menuId}">
		                        		<ul class="sider-nav-s">
				                           <li url="${mainserver}${cm.menuUrl }" onclick="addTab(this)" title="${cm.menuName }" >
				                           	    <a href="javascript:void(0)">${cm.menuName }</a>
				                           </li>
				                        </ul>
		                        	</c:if>
		                        </c:forEach>
		                     </li>
	            		
	            		</c:forEach> 
	                 </ul> 
	            </div>
	
	            <div id="pf-page">
	                <div class="easyui-tabs1" style="width:100%;height:100%;">
	                  <div title="首页" style="padding:10px 5px 5px 10px;">
	                    <iframe class="page-iframe"  frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
	                  </div>
	                </div>
	            </div>
	        </div>
	        
         	<div data-options="region:'center'" id="centerTabs" name="centerTabs">
		    </div>
		    
		    <div id="tabsMenu" style="width: 120px;display:none;">
				<div type="refresh">刷新</div>
				<div class="menu-sep"></div>
				<div type="close">关闭</div>
				<div type="closeOther">关闭其他</div>
				<div type="closeAll">关闭所有</div>
			</div>
	    </div>
	    <!--[if IE 7]>
	      <script type="text/javascript">
	        $(window).resize(function(){
	          $('#pf-bd').height($(window).height()-76);
	        }).resize();
	        
	      </script>
	    <![endif]-->  
	
	    
	    <script type="text/javascript">
	    	var mainserver='${mainserver}';
		    
		    $(function(){
		    	 //退出
		    	$('.loginout').click(function(){
		    		window.location.href=mainserver+"/admin/dologout";	
		        });
		    })
		    
	    
		    $(window).resize(function(){
		          $('.tabs-panels').height($("#pf-page").height()-46);
		          $('.panel-body').height($("#pf-page").height()-76)
		    }).resize();
		
		    var page = 0,
		        pages = ($('.pf-nav').height() / 70) - 1;
		
		    if(pages === 0){
		      $('.pf-nav-prev,.pf-nav-next').hide();
		    }
		    
		    $(document).on('click', '.pf-nav-prev,.pf-nav-next', function(){
		      if($(this).hasClass('disabled')) return;
		      if($(this).hasClass('pf-nav-next')){
		        page++;
		        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
		        if(page == pages){
		          $(this).addClass('disabled');
		          $('.pf-nav-prev').removeClass('disabled');
		        }else{
		          $('.pf-nav-prev').removeClass('disabled');
		        }
		        
		      }else{
		        page--;
		        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
		        if(page == 0){
		          $(this).addClass('disabled');
		          $('.pf-nav-next').removeClass('disabled');
		        }else{
		          $('.pf-nav-next').removeClass('disabled');
		        }
		      }
		    });
		    
	    </script>
	    
	</body> 
</html>