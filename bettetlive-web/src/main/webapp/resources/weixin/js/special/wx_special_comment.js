$(function(){
	listData.init();
});
		 
$('#contentId').focus(function(){
	var oSelf = this;
	setTimeout(function(){
	oSelf.scrollIntoView(true);
		$(window).scrollTop($(document).height())
	},100)
});

var listData = {
		//所有的请求参数
		obj:{
			pageIndex:1,/*这两个是假设页码和数据条数*/
			pageSize:10,
			pageCount:'',/*总页数*/
			url:mainServer+'/weixin/specialcomment/specialCommentDetail',
			specialId:specialId,
			rootId:rootId,
			commentPraiseType:3,
			specialType:4
		},
		//上拉加载分页
		loadMore:function(){
				var oSelf = this;
				var loading = false;//上拉加载状态标记
				$(document.body).infinite();
				$('.weui-loadmore').hide();
				$(document.body).infinite().on("infinite", function() {
					if(loading) return;
					loading = true;
				if(oSelf.obj.pageCount!=null&&oSelf.obj.pageIndex<=oSelf.obj.pageCount){
						if(oSelf.obj.pageIndex<=oSelf.obj.pageCount){
							oSelf.obj.pageIndex++;
							$('.weui-loadmore').show();
							setTimeout(function() {
							   oSelf.getData();
							   loading = false;
							   $('.weui-loadmore').hide();
							 }, 1500);   //模拟延迟
						}else{
							loading = true;
							 $('.weui-loadmore').hide();
							//销毁上拉加载的组件 ,在需要的地方销毁
							$(document.body).destroyInfinite()
						};
						
					}
				});
		},
		//请求数据
		getData:function(){
			var oSelf = this;
			$.post(oSelf.obj.url,{
				rows:oSelf.obj.pageSize,
				pageIndex:oSelf.obj.pageIndex,
				specialId:oSelf.obj.specialId,
				rootId:oSelf.obj.rootId,
				commentPraiseType:oSelf.obj.commentPraiseType,
				specialType:oSelf.obj.specialType
			},function(data,errror){
				if(data.code == '1010'){
					oSelf.obj.pageCount=data.pageInfo.pageCount;
					if(data.data && data.data.length>0){
						oSelf.establishDom(data.data);
					}else{//暂无数据
						
					}
					
				}else{
					$.toast("出现异常","text");
				}
				
			})
		},
		//创建列表
		establishDom:function(data){
			var _html = '';
			for(var i = 0; i < data.length; i++){
				var commentVo = typeof data[i] == 'object' ? data[i] : $.parseJSON(data[i]);
				if(commentVo.isPraise!=null){
					var onDz='';
					if(commentVo.isPraise>0){
						onDz = ' on ';
					};
					
					var replyLine = "";
					if(commentVo.replyerId != null && commentVo.replyerId > 0 && commentVo.replyerId != $("#cmmCustId").val()){
						replyLine = "回复<span style=\"color:#a2985b\">"+commentVo.replyerName+"</span>：";
					}
					
					var showHtml = '<div class="plcent" >'
				    	+'		<div class="plcttop">'
				    	+'			<span class="one" onclick="toSocialityHome('+commentVo.customerVo.customer_id+')">'
				    	+'				<img src="'+commentVo.customerVo.head_url+'" alt="">'
				    	+'			</span>'
				    	+'			<span class="two" onclick="toSocialityHome('+commentVo.customerVo.customer_id+')">'
				    	+				commentVo.customerVo.nickname
				    	+'			</span>'
				    	+'			<span class="three">'
				    	+				commentVo.createTime
				    	+'			</span>'
				    	+'		</div>'
				    	+'		<div class="dchfcent">'
				    	+		replyLine +	commentVo.content
				    	+'		</div>'
				    	+'		<div class="plbotbox">'
				    	+'			<span class="dz '+onDz+'" data-priase-id="'+commentVo.isPraise+'" data-comment-id="'+commentVo.commentId+'">'+commentVo.praiseCount+'</span>'
				    	+'		</div>'
				    	+'	</div>';
						
						$('#replysId').append(showHtml)
					
				}
				
				
			}
		},
		//点赞效果
		operate:function(){
			//点赞
			$(document).on('click','.dz',function(){
				//点击获取当前点赞数量
				let num = $(this).html();
				var oSelf = $(this);
				//判断是否已经收藏
				//没登陆
				if(customerId == null || customerId <= 0){
					window.location.href =  mainServer + "/weixin/tologin?backUrl="+link;
					return;
				};
				if($(this).hasClass('on')){
					var url = mainServer + "/weixin/praise/cancelPraise";
					var praiseId = $(this).attr("data-priase-id");
					$.post(url,{
						"praiseId":praiseId
					},function(data,error){
						if(data.code == '1010'){
							num--;
							oSelf.removeClass('on');
							oSelf.html(num);
							oSelf.attr("data-priase-id",0);
							$.toast("取消点赞","text");
						}else{
							$.toast(data.msg,"text");
						}
					})
					
				}else{
					var url = mainServer + "/weixin/praise/addPraise";
					var commentId = $(this).attr("data-comment-id");
					$.post(url,{
						"praiseType":3,
						"objId":commentId
					},function(data,error){
						if(data.code == '1010'){
							num++;
							oSelf.addClass('on');
							oSelf.html(num);
							oSelf.attr("data-priase-id",data.data);
							$.toast("点赞成功","text");
						}else{
							$.toast(data.msg,"text");
						}
					})
					
					
				}
			});
			
		},
		init:function(){
			this.loadMore();
			this.getData();
			this.operate()
		}
}

function addComment(content){
		
		if(customerId == null || customerId <= 0){
			window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
		}else{
			if (content == null || content == "" || content.length <= 0){
				return false;
			} 
			if (content.length > 100){
				showvaguealert('亲，评论过长</br>请输入100字以内！');
				return false;
			} 
			$.ajax({    
		          url: mainServer+'/weixin/specialcomment/addComment' , 
		          type: 'POST',    
		          data: {
		        		"specialId":specialId,
		        		"specialType":4,
		        		"content":content,
		        		"parentId":commentId,
		        		"rootId":rootId
		          },    
		          dataType:'json',
		          success:function(data) { 
  			        if(data.code == 1010){  
  						$(".shafabg").hide();
  						var cmmCount = parseInt($("#totalCountId").html()) + 1;
  						$("#totalCountId").html(cmmCount);
  						
  						$("#contentId").val("");
  						$("#replysId").html("");
  						var oSelf = listData.obj;
  						oSelf.pageIndex=1;
  						listData.getData();
  			        }else{  
  			        	showvaguealert(data.msg);
  			        }  
  			     },     
  			     error : function() {  
  			          showvaguealert('异常！');
  			     }   
		     });  
		}
}

	
function toSocialityHome(authorId){
	var myCustId = customerId;
	if(myCustId > 0 && myCustId == authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toSocialityHome";
	}else if (myCustId > 0 && myCustId != authorId){
		window.location.href=mainServer+"/weixin/socialityhome/toOtherSocialityHome?otherCustomerId="+authorId;
	}else{
		window.location.href = mainServer+"/weixin/tologin?backUrl="+backUrl;
	}
}
	 
	//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},1000);
}

//修改微信自带的返回键 
$(function(){
	
	var url = backUrl;
	if(url == null || url == ""){
		url = mainServer+"weixin/discovery/toTorialComment?specialId="+specialId;
	}
	
	var bool=false;  
    setTimeout(function(){  
          bool=true;  
    },1000); 
        
	pushHistory(); 
	
    window.addEventListener("popstate", function(e) {
    	if(bool){
    		window.location.href = url;
    	}
    }, false);
    
    function pushHistory(){ 
        var state = { 
    		title: "title", 
    		url:"#"
    	}; 
    	window.history.pushState(state, "title","#");  
    }
    
});


