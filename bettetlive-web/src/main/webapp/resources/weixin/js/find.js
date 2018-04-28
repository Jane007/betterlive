$(function(){
	
	 /*瀑布流初始化设置*/
	var $grid = $('.grid').masonry({
		itemSelector : '.grid-item',
		gutter:10
    });
    // layout Masonry after each image loads
	$grid.imagesLoaded().done( function() {
		console.log('uuuu===');
	  $grid.masonry('layout');
	});
	   var pageIndex = 0 ; 
	   var dataFall = [];
	   var totalItem = 10;
	   $(window).scroll(function(){
	   	$grid.masonry('layout');
                var scrollTop = $(this).scrollTop();
                var scrollHeight = $(document).height();
                var windowHeight = $(this).height();
                if(scrollTop + windowHeight == scrollHeight){
                        $.ajax({
		               		dataType:"json",
					        type:'get',
					        url:'./dynamic.json',
				            success:function(result){
				            	dataFall = result.result.article;
				            	
				            	setTimeout(function(){
				            		appendFall();
				            	},500)
				            },
				            error:function(e){
				            	console.log('请求失败')
				            }
			            
	                   })
                	
                }
                
         });
 		
		function appendFall(){
          $.each(dataFall, function(index ,value) {
          	var dataLength = dataFall.length;
          	$grid.imagesLoaded().done( function() {
	        	$grid.masonry('layout');
	        });
	      var detailUrl;
      	  var $griDiv = $('<div class="grid-item item">');
      	  var $img = $("<img class='item-img'>");
      	  $img.attr('src',value.articlePic).appendTo($griDiv);
      	  var $section = $('<section class="section-p">');
      	  $section.appendTo($griDiv);
      	  var $p = $('<p class="title-p">');
      	  $p.html(value.title).appendTo($section);
      	  var $details = $("<div class='details'>");
      	  $details.appendTo($section);
      	  var $detailsleft = $("<div class='details-left'>");
      	  $detailsleft.appendTo($details); 
      	  var $detailsright = $("<div class='details-right'>");
      	  $detailsright.appendTo($details); 
      	  var $leftimg = $("<img>");
      	  $leftimg.attr('src',value.header).appendTo($detailsleft);;
      	  var $span = $('<span>'+ value.name +'</span>');
      	  $span.appendTo($detailsleft)
      	  var $rspan = $('<span>'+ value.praiseCount +'</span>');
      	  $rspan.appendTo($detailsright)
      	  
      	  var $items = $griDiv;
		  $items.imagesLoaded().done(function(){
				 $grid.masonry('layout');
	             $grid.append( $items ).masonry('appended', $items);
			})
           });
        }
    
    
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})
