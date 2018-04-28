
var integral = {
	obj:{
		url:mainServer+"/weixin/integral/findIntegralRecord", //请求接口
		pageIndex:1,
		pageCount:''	//总页数
	},
	//上拉加载
	loadMore: function() {
		var oSelf = this;
		var loading = false;
		$(document.body).infinite();
		$(document.body).infinite().on("infinite",function(){
			if(loading) return;
			loading = true;
			$('.weui-loadmore').show();
			setTimeout(function(){
				if(oSelf.obj.pageIndex <= oSelf.obj.pageCount) {
					oSelf.obj.pageIndex++;
					if(oSelf.obj.pageIndex <= oSelf.obj.pageCount) {
						oSelf.findIntegralRecord()
					}
				}else {
					$(document.body).destroyInfinite();
				};
				loading = false;
				$('.weui-loadmore').hide()
			}, 1500)
		})
	},
	goBack:function(){
		$(".goback").click(function(){
			var url = mainServer+"/weixin/toMyIndex";
			if(backUrl != null && backUrl != "" && backUrl != undefined){
				url = backUrl;
			}
			location.href = url;
		});
	},
	//请求数据
	findIntegralRecord:function(){
		var oSelf = this;
		$.post(oSelf.obj.url,{
			customerId:customerId,
			pageIndex: oSelf.obj.pageIndex,
			rows: 10
		},function(data, error){
			if (data.code == '1010') {
				oSelf.obj.pageCount = data.pageInfo.pageCount
				oSelf.establishpage(data.data);
			}
		});
	},
	//渲染页面
	establishpage:function(list){
		if (list.length == 0) {
			$('.nodata').show();
		};
		for (var i = 0; i < list.length; i++) {
			recordVo = list[i];
			var integraltype = (function(){
				switch (recordVo.integralType) {
				case 0:return"系统赠送"
				case 1:return"每日签到"
				case 2:return"发动态"
				case 3:return"分享文章"
				case 4:return"分享视频"
				case 5:return"分享文章点赞"
				case 6:return"分享视频点赞"
				case 7:return"订单支付"
				}
			})();
			var checkRecordType = recordVo.recordType == 0?"+":"-";
			var showHtml = [
							'<li>',
							'	<h3>'+ integraltype +'</h3>',	
							'	<div class="time">'+ recordVo.createTime +'</div>',	
							'	<div class="money '+ (recordVo.recordType == 0?'':'minus') +'">'+checkRecordType+recordVo.integral+'</div>',
							'</li>'	
			               ].join('');
			$('.list').append(showHtml)
		}
	},
	//计算当前值得成长值的位置
	growthValue:function(){
		var num = accumulativeIntegral * 1;
		var oleft = '';
		var cleft = '';
		if(num <= 0){
			$('.currbg').addClass('zero');
			$('.currbg img').attr('src',resourcePath+'/weixin/img/integral/head_05.png');
			$('.currentvalue').css('left','0.4259rem')
		}else{
			if(num <= 100){
				oleft = (num * 1.48 / 50 ).toFixed(2) + 'rem';
				cleft = (num * 1.48 / 50 ).toFixed(2)*1 + 0.4259 + 'rem';
			}else if(num > 100 && num <=1000){
				oleft = ((num - 100)* 1.48 / 900 + 2.96).toFixed(2) + 'rem';
				cleft = ((num - 100)* 1.48 / 900 + 2.96).toFixed(2)*1 + 0.4259 + 'rem';
			}else if(num > 1000 && num <= 5000){
				oleft = ((num - 1000)* 1.48 / 4000 + 4.44).toFixed(2) + 'rem';
				cleft = ((num - 1000)* 1.48 / 4000 + 4.44).toFixed(2)*1 + 0.4259 + 'rem';
			}else if(num > 5000 && num <= 10000){
				oleft = ((num - 5000)* 1.48 / 5000 + 5.92).toFixed(2) + 'rem';
				cleft = ((num - 5000)* 1.48 / 5000 + 5.92).toFixed(2)*1 + 0.4259 + 'rem';
			}else if(num > 10000 && num <= 30000){
				oleft = ((num - 10000)* 1.48 / 20000 + 7.4).toFixed(2) + 'rem';
				cleft = ((num - 10000)* 1.48 / 20000 + 7.4).toFixed(2)*1 + 0.4259 + 'rem';
				$('.graph-wrap').scrollLeft(150)
			}else if(num > 30000 && num <= 60000){
				oleft = ((num - 30000)* 1.48/ 30000 + 8.88).toFixed(2) + 'rem';
				cleft = ((num - 30000)* 1.48/ 30000 + 8.88).toFixed(2)*1 + 0.4259 + 'rem';
				$('.graph-wrap').scrollLeft(150)
			}else if(num > 60000 && num < 100000){
				oleft = ((num - 60000)* 1.48 / 40000 + 10.36).toFixed(2) + 'rem';
				cleft = ((num - 60000)* 1.48 / 40000 + 10.36).toFixed(2)*1 + 0.4259 + 'rem';
				$('.graph-wrap').scrollLeft(150)
			}else{
				oleft = 11.84 + 'rem';
				cleft = 11.84*1 + 0.4259 + 'rem';
			};
			$('.onspan').css('left',oleft);
			$('.currentvalue').css('left',cleft);
		}
	},
	inIt:function(){
		this.findIntegralRecord();
		this.growthValue();
		this.loadMore();
		this.goBack()
	}
};

integral.inIt();

//微信自带返回
$(function(){
	var url=mainServer+"/weixin/toMyIndex";
	if(backUrl != null && backUrl != "" && backUrl != undefined){
		url = backUrl;
	}
	var bool=false;
    setTimeout(function(){  
          bool=true;  
    },1000); 
	pushHistory();
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
    	window.history.pushState(state, "title","");  
    }
  //返回上一个页面
	$('.fan').click(function(){
		window.location.href=url;	
	});
});
