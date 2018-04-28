/**
 * 
 * @param {Object} fen 剩余分钟
 * @param {Object} miao 剩余秒
 * @param {Object} divCls 当前dom的class
 */
var DownObj = function(fen,miao,divCls){
	this.divCls = divCls;
	this.miao = parseInt(miao);
	this.Fen = parseInt(fen);
	this.timer = 0;
	var that = this;
	
	DownObj.prototype.showTime = function(){
		if(this.Fen==0&&this.miao==1)//分钟数=0的时候
		{
			clearInterval(this.timer);
			$('.'+this.divCls+' .payBox a').text('已过期').addClass('active').removeAttr('href');
			$('.'+this.divCls+' .order-del').show();
			$('.'+this.divCls+' .order-cancel').hide();
		}
		if(this.Fen>=0&&this.Fen<=10)//分钟数0~10
		{
			this.miao--;
			if(this.miao<=0)//秒数等于0的时候
			{
				this.miao=60;
				$('.'+this.divCls+' .payBox em').text('0'+this.Fen+':00');
			}
			if(this.miao>0&&this.miao<10)//秒数0~10的时候
			{
				$('.'+this.divCls+' .payBox em').text('0'+this.Fen+':0'+this.miao);
			}
			if(this.miao>=10&&this.miao!=60)//秒数大于等于10的时候
			{
				if(this.miao==59)
				{
					this.Fen--
				}
				var c=this.miao%10;
				var d=Math.floor(this.miao/10);
				$('.'+this.divCls+' .payBox em').text('0'+this.Fen+':'+d+c);
			}
		}
		if(this.Fen>10)//分钟数大于10的时候
		{
			this.miao--;
			if(this.miao==0)//秒数等于0的时候
			{
				this.miao=60;
				var a=this.Fen%10;
				var b=Math.floor(this.Fen/10);
				$('.'+this.divCls+' .payBox em').text(b+''+a+':00');
			}
			if(this.miao>0&&this.miao<10)//秒数0~10的时候
			{
				var a=this.Fen%10;
				var b=Math.floor(this.Fen/10);
				$('.'+this.divCls+' .payBox em').text(b+''+a+':0'+this.miao);
			}
			if(this.miao>=10&&this.miao!=60)//秒数大于等于10的时候
			{
				if(this.miao==59)
				{
					this.Fen--
				}
				var a=this.Fen%10;
				var b=Math.floor(this.Fen/10);
				var c=this.miao%10;
				var d=Math.floor(this.miao/10);
				$('.'+this.divCls+' .payBox em').text(b+''+a+':'+d+c);
			}
		}
		
	}
	DownObj.prototype.interalVal = function(){
		this.timer = setInterval(function(){
			that.showTime();
		},1000);
	}
}
