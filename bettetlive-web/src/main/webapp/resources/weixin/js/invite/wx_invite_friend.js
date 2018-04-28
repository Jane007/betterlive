$(function(){
	invite.init();
	invite.invitedRankInfo();
	$(".buttonwrap").on("click",function(){
		invite.shareRightNow();
	});
	$(".goback").on("click",function(){
		window.location.href=backUrl;
	});
})

var invite = {
	obj:{
		dataid:'',
		datacontent:''
	},
	clickrul:function(){ //弹出遮罩层效果
		
		$('.rule').on("click",function(){
			var top = 0;		//给top变量一个初始值。
		    top = $(window).scrollTop();//获取页面的滚动条高度；
		    $('body').css("top",-top+"px");//给body一个负的top值；
		    $('body').addClass('add');//给body增加一个类添加固定定位。 
			//弹出遮罩和规则
			$('.shade').show();
			$('.clickrunl').show();
			$('.close').click(function(){
				$('body').removeClass('add');
				$('.clickrunl').hide();
				$('.shade').hide();
				$(window).scrollTop(top)
			})
		});
		
	},//查看规则
	oselect:function(){ //选择理由
		var that = this;
		$.ajax({
			url: mainserver + "/weixin/customerinvite/queryInviteReasons",
			datatype : "json",
			type : "post",
			success : function(data) {
				if(data.code==1010){
					var invites = data.data;
					var context = "";
					
					for(var i=0;i<invites.length;i++){
						context +="<li class='invite' data-id='"+invites[i].sysInviteId+"'>"
										+"<div class='select'>"
											+"<img src='"+resourcepath+"/weixin/img/invite/select.png'>"
										+"</div>"
										+"<p>"+invites[i].objValue+"</p>"
									+"</li>";
					}
					
					$("#invitedReason").html(context);
				}
				
				$('.invite').click(function(){
					$('.invite').find('img').attr('src',resourcepath+"/weixin/img/invite/select.png");
					$(this).find('img').attr('src',resourcepath+"/weixin/img/invite/onpitch.png");
					that.obj.dataid = $(this).attr("data-id");
					that.obj.datacontent=$(this).find('p').text();
				});
				
				$('.invite:first').trigger('click');
			}
		});
		
	},
	firstShare:function(){//第一次分享时券信息
		$.post("queryFirstInviteCouponTip",function(data){
			$(".prce span").html(data.data.couponMoney);
		},"json");
	},
	invitedCouponInfo:function(){//分享券信息
		$.post("queryInviteCouponsInfo",function(data){
			var _couponInfos = data.data
			var _couponInfohtml = '';
			var _div='';
			$.each(_couponInfos,function(index,_couponInfo){
				_div +=[
				        	'<div class="mark">',
				        		'<img src="'+resourcepath+'/weixin/img/invite/oval.png"/>',
				        	'</div>'
				        ].join('');
				_couponInfohtml +=[
									'<li class="lipro">',
										'<div class="litop">',
											'邀请<span>'+_couponInfo.objValue+'</span>人',
										'</div>',
										'<div class="libottom">',
											'<span>￥<strong>'+_couponInfo.sysDesc+'</strong></span>',
											'<span>优惠券</span>',
										'</div>',
									'</li>'
				                   ].join('');
			});
			$(".reasonpeople").html(_div);
			$('.people-bottom').find('ul').html(_couponInfohtml);
		},"json");
		
	},
	invitedRankInfo:function(){//分享排行榜
		$.post("queryInviteRankingList",function(data){
			var invitedlist= data.data
			var content = "";
			var _rank = "";

			$.each(invitedlist,function(index,invited){
				var img = "";
				if(index<=2){
					img = '<img class="phb" src="'+resourcepath+'/weixin/img/invite/ranking-0'+(index+1)+'.png"/>';
				}else{
					img = '<span>'+(index+1)+'</span>';
				}
				var objName = "";
				if(invited.objName != null){
					objName = invited.objName;
				}
				_rank += [
				          '	<li>',
								'<div class="drleft">',
									img,
									'<img class="hpor" src="'+invited.headUrl+'"/>',
								'</div>',
								'<div class="drright">',
									'<h4>'+ invited.nickname+'</h4>',
									'<div class="present">',
									'<span>'+objName+'</span>',
									'</div>',
									'<div class="friendnum">'+invited.objCount+'</div>',
								'</div>',
							'</li>'
				         ].join('')
			});
			$(".drlist").html(_rank);
		},"json");
		
	},
	shareRightNow:function(){//马上分享
		
		var that = this;
		
		var url = window.location.href;
		if(url.indexOf("#")!=-1){
			url = url.substring(0,url.indexOf("#"));
		}
		
		$.ajax({                                       
			url:mainserver+'/weixin/shareWeixin',
			data:{
				"url":url,
			},
			type:'post',
			dataType:'json',
			success:function(data){
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: data.shareInfo.appId, // 必填，公众号的唯一标识
				    timestamp: data.shareInfo.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.shareInfo.nonceStr, // 必填，生成签名的随机串
				    signature: data.shareInfo.signature,// 必填，签名，见附录1
				    jsApiList: [ // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						'onMenuShareTimeline',
						'onMenuShareAppMessage',
						'onMenuShareQQ'
				    ]
				});
			},
			failure:function(data){
				showvaguealert('出错了');
			}
		});
		
		_system._guide(true);
		
		var recordId=0;
		$.ajax({                                       
			url:mainserver+'/weixin/customerinvite/addInviteRecord',
			data:{"inviteReasonId":that.obj.dataid},
			type:'post',
			dataType:'json',
			async : false,
			success:function(data){
				recordId = data.data;
			}
		})
		
		var title = "送你一份新人专属豪礼，速来领取！";  
		var desc = that.obj.datacontent;
		var link = mainserver+"/weixin/customerinvite/shareRegister?customerId="+customerId+"&sysInviteId="+this.obj.dataid+"&recordId="+recordId;
		var imgUrl = resourcepath+"/weixin/img/huihuologo.png";
		
		wx.ready(function(){
			// 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareTimeline({
			  	title: title,
			  	desc: desc,
			  	link: link,
			  	imgUrl: imgUrl,
			  	trigger: function (res) {
			    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			    	//alert('用户点击分享到朋友圈');
			 	},
			  	success: function (res) {
			  		shareSuccess(recordId);
			  	},
			  	cancel: function (res) {
			    	//alert('已取消');
			  	},
			  	fail: function (res) {
			    	alert(JSON.stringify(res));
			  	}
			});
			  
			// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareAppMessage({
				title: title,
			  	desc: desc,
			  	link: link,
			  	imgUrl: imgUrl,
			  	trigger: function (res) {
			    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			    	//alert('用户点击分享到朋友圈');
			 	},
			  	success: function (res) {
			  		shareSuccess(recordId);
			  	},
			  	cancel: function (res) {
			    	//alert('已取消');
			  	},
			  	fail: function (res) {
			   // 	alert(JSON.stringify(res));
			    	showvaguealert(JSON.stringify(res));
			  	}
			});
			
			
			// 监听“分享给朋友”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareQQ({
				title: title,
			  	desc: desc,
			  	link: link, 
			  	imgUrl: imgUrl,
			  	trigger: function (res) {
			    	// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			    	//alert('用户点击分享到朋友圈');
			 	},
			  	success: function (res) {
			  		shareSuccess(recordId);
			  	},
			  	cancel: function (res) {
			    	//alert('已取消');
			  	},
			  	fail: function (res) {
			  		showvaguealert(JSON.stringify(res));
			  	}
			});
		});
		
		wx.error(function (res) {
			showvaguealert(res.errMsg);
		});
	},
	
	init:function(){
		this.clickrul();
		this.oselect();
		this.firstShare();
		this.invitedCouponInfo();
		
		
	}
}


function shareSuccess(recordId){
	$.post("inviteCallback",{"recordId":recordId},function(data){
		var inviteData= data;
		if(inviteData.code==1010){
			if(inviteData.data.hasFirstGift==1){
				var top = 0;		//给top变量一个初始值。
			    top = $(window).scrollTop();//获取页面的滚动条高度；
			    $('body').css("top",-top+"px");//给body一个负的top值；
			    $('body').addClass('add');//给body增加一个类添加固定定位。 
				
				
				
				$('.gain').find('h4').html(inviteData.data.giftDesc);
				$('.shade').show();
				$('.separate').show();
				_system._cover(false);
				_system.$("guide").style.display="none";
				
				
				$('.oyes').on('click',function(){
					$('body').removeClass('add');
					$('.shade').hide();
					$('.separate').hide();
					$('.shade').hide();
					$(window).scrollTop(top)
				});
			}else{
				_system._cover(false);
				_system.$("guide").style.display="none";
				showvaguealert("已经分享成功");
			}
		}else{
			showvaguealert(data.msg);
		}
		
	})
}
//提示弹框
function showvaguealert(con){
	$('.vaguealert').show();
	$('.vaguealert').find('p').html(con);
	setTimeout(function(){
		$('.vaguealert').hide();
	},1000);
}
var _system={
    $:function(id){return document.getElementById(id);},
_client:function(){
  return {w:document.documentElement.scrollWidth,h:document.documentElement.scrollHeight,bw:document.documentElement.clientWidth,bh:document.documentElement.clientHeight};
},
_scroll:function(){
  return {x:document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft,y:document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop};
},
_cover:function(show){
  if(show){
 this.$("cover").style.display="block";
 this.$("cover").style.width=(this._client().bw>this._client().w?this._client().bw:this._client().w)+"px";
 this.$("cover").style.height=(this._client().bh>this._client().h?this._client().bh:this._client().h)+"px";
}else{
 this.$("cover").style.display="none";
}
},
_guide:function(click){
  this._cover(true);
  this.$("guide").style.display="block";
  this.$("guide").style.top=(_system._scroll().y+5)+"px";
  var top = 0;		//给top变量一个初始值。
  top = $(window).scrollTop();//获取页面的滚动条高度；
  $('body').css("top",-top+"px");//给body一个负的top值；
  $('body').addClass('add');//给body增加一个类添加固定定位。 
  window.onresize=function(){_system._cover(true);_system.$("guide").style.top=(_system._scroll().y+5)+"px";};
if(click){_system.$("cover").onclick=function(){
     _system._cover();
     $('body').removeClass('add');
     $(window).scrollTop(top);
     _system.$("guide").style.display="none";
_system.$("cover").onclick=null;
window.onresize=null;
};}
},
_zero:function(n){
  return n<0?0:n;
}
}

      
      
      


