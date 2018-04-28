//懒加载
			/* 获取节点 */    
			var imgsrc = document.getElementsByClassName('imgsrc');
			/* 创建img标签函数 */    
			function createImg(obj){
			    var src = '';
			    if(obj.dataset.src){
			        src = obj.dataset.src;
			    }else{
			        src = obj.getAttribute('data-src');
			    }
			    if(obj.children.length <= 0){
			        var img = document.createElement('img');
			        img.src = src;
			        obj.appendChild(img);
			    }
			}
			/* 计算节点到文档顶部的距离 */    
			function getTop(obj){
			    var h = 0;
			    while(obj){
			        h += obj.offsetTop;
			        obj = obj.offsetParent;
			    }
			    return h;
			}
			/* 滚动实时计算所到区域并进行相关计算 */
			window.onscroll = function(){
			    var t = document.documentElement.clientHeight + (document.body.scrollTop || document.documentElement.scrollTop);
			    for(var i=0;i<imgsrc.length;i++){
			        if(getTop(imgsrc[i]) < t){ 
		        		setTimeout('createImg(imgsrc['+i+'])',300);
			        }
			    } 
			}
			/* 页面加载完便执行一次滚动函数 */
			window.onload = function(){
			    window.onscroll();
			}