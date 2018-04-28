var mainPlatform = {

	init: function(){

		this.bindEvent();
		// this.render(menu['home']);
	},

	bindEvent: function(){
		var self = this;
		// 顶部大菜单单击事件  
		$(document).on('click', '.pf-nav-item', function() {
            $('.pf-nav-item').removeClass('current');
            $(this).addClass('current');

            // 渲染对应侧边菜单
            var m = $(this).data('menu');
            //self.render(menu[m]);
        });
		
        $(document).on('click', '.sider-nav li', function() {  //左侧一级菜单点击     
            
            if($(this).hasClass('current')){
            	$(this).removeClass('current');      
            }else{
            	$('.sider-nav li').removeClass('current');    
                $(this).addClass('current');   
            }
        });  
        
       

        $(document).on('click', '.pf-logout', function() {
            layer.confirm('您确定要退出吗？', {
              icon: 4,
			  title: '确定退出' //按钮
			}, function(){
			  location.href= 'login.html'; 
			});
        });
        //左侧菜单收起
        $(document).on('click', '.toggle-icon', function() { 
            $(this).closest("#pf-bd").toggleClass("toggle");
            setTimeout(function(){
            	$(window).resize();
            },300)
        });

        $(document).on('click', '.pf-modify-pwd', function() {
            $('#pf-page').find('iframe').eq(0).attr('src', 'backend/modify_pwd.html')
        });

        $(document).on('click', '.pf-notice-item', function() {
            $('#pf-page').find('iframe').eq(0).attr('src', 'backend/notice.html')
        });
	},

	render: function(menu){
		var current,
			html = ['<h2 class="pf-model-name"><span class="pf-sider-icon"></span><span class="pf-name">'+ menu.title +'</span></h2>'];

		html.push('<ul class="sider-nav">');
		for(var i = 0, len = menu.menu.length; i < len; i++){
			if(menu.menu[i].isCurrent){
				current = menu.menu[i];
				html.push('<li class="current" title="'+ menu.menu[i].title +'" data-src="'+ menu.menu[i].href +'"><a href="javascript:;"><img src="'+ menu.menu[i].icon +'"><span class="sider-nav-title">'+ menu.menu[i].title +'</span><i class="iconfont"></i></a></li>');
			}else{
				html.push('<li data-src="'+ menu.menu[i].href +'" title="'+ menu.menu[i].title +'"><a href="javascript:;"><img src="'+ menu.menu[i].icon +'"><span class="sider-nav-title">'+ menu.menu[i].title +'</span><i class="iconfont"></i></a></li>');
			}
		}
		html.push('</ul>');

		$('iframe').attr('src', current.href);
		$('#pf-sider').html(html.join(''));
	}

};

var centerTabs;
var tabsMenu;
var menuName;

$(function() {
	centerTabs = $('.easyui-tabs1').tabs({
      	tabHeight: 44,
      	onSelect:function(title,index){
        	var currentTab = $('.easyui-tabs1').tabs("getSelected");
        	if(typeof(centerTabs) != 'undefined'){
        		centerTabs.tabs('select', title);
			}
      	},
      	onContextMenu : function(e, title) {
			e.preventDefault();
			tabsMenu.menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		}
    });
	
	tabsMenu = $('#tabsMenu').menu({
		onClick : function(item) {
			var curTabTitle = $(this).data('tabTitle');
			var type = $(item.target).attr('type');

			if (type === 'refresh') {
				refreshTab(curTabTitle);
				return;
			}

			if (type === 'close') {
				var t = centerTabs.tabs('getTab', curTabTitle);
				if (t.panel('options').closable) {
					centerTabs.tabs('close', curTabTitle);
				}
				return;
			}

			var allTabs = centerTabs.tabs('tabs');
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel('options');
				if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === 'closeAll') {
					closeTabsTitle.push(opt.title);
				}
			});

			for ( var i = 0; i < closeTabsTitle.length; i++) {
				centerTabs.tabs('close', closeTabsTitle[i]);
			}
		}
	});
	
});

function addTab(node) {
	$('.sider-nav-s li').removeClass('active');
	if (!$(node).hasClass('active')) {
		$(node).addClass('active');
	}
	
	var $node = $(node);
	menuName = $node.attr('title');
	if (centerTabs.tabs('exists', menuName)) {
		centerTabs.tabs('select', menuName);
		refreshTab(menuName);
	} else {
		if ($node.attr("url") && $node.attr("url").length > 0) {
			var url = $node.attr("url"); 
			if(url.indexOf('null')>0){//如果不是有效地址则挑转
				url = "/noDev.jsp";
			}
			
			centerTabs.tabs('add', {
				title : menuName,
				closable : true,
				content : '<iframe class="page-iframe" src="' +url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
			});
		}
	}
}

function refreshTab(title) {
	var tab = centerTabs.tabs('getTab', title);
	centerTabs.tabs('select', {
		tab : tab,
		options : tab.panel('options')
	});
}

function addMySelfTab(menuName,url){
	centerTabs.tabs('add', {
		title : menuName,
		closable : true,
		content : '<iframe class="page-iframe" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
	});
}

mainPlatform.init();