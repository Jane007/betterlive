(function(){
	var StringBuffer = function(){
		var buffer = [];
		this.append = function(string){
			buffer.push(string);
			
			return this;
		}
		this.toString = function(){
			return buffer.join('');
		}
		this.clean = function(){
			buffer = [];
		}
	};
	
	/**参数说明：
	 * 根据长度截取先使用字符串，超长部分追加…
	 * str 对象字符串
	 * len 目标字节长度
	 * 返回值： 处理结果字符串
	 */
	var cutString = function(str, len) {
		//length属性读出来的汉字长度为1
		if(str.length*2 <= len) {
			return str;
		}
		var strlen = 0;
		var s = "";
		for(var i = 0;i < str.length; i++) {
			s = s + str.charAt(i);
			if (str.charCodeAt(i) > 128) {
				strlen = strlen + 2;
				if(strlen >= len){
					return s.substring(0,s.length-1) + "......";
				}
			} else {
				strlen = strlen + 1;
				if(strlen >= len){
					return s.substring(0,s.length-2) + "......";
				}
			}
		}
		return s;
	};
	
	window.Application = {
		StringBuffer: StringBuffer,
		CutString: cutString
	}
	
})();

//判定元素是否在数组中
Array.prototype.S = String.fromCharCode(2);
Array.prototype.in_array = function(e){
    var r = new RegExp(this.S + e + this.S);
    return (r.test(this.S + this.join(this.S) + this.S));
};

/**
 * Simple Map
 * var m = new Map();
 * m.put('key','value');
 * ...
 * var s = "";
 * m.each(function(key,value,index){
 *         s += index+":"+ key+"="+value+"\n";
 * });
 * alert(s);
 */
function Map() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();
    
    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
    };
    
    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };
    
    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
    	for (var i = 0; i < this.keys.length; i++) {
            if (this.keys[i] == key) {
            	this.keys.splice(i, 1);
            	break;
            }
        }
    	this.data[key] = null;
    };
    
    /**
     * 遍历Map,执行处理函数
     * 
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn){
        if(typeof fn != 'function'){
            return;
        }
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var k = this.keys[i];
            fn(k,this.data[k],i);
        }
    };
    
    /**
     * 获取键值数组(类似Java的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++) {
            entrys[i] = {
                key : this.keys[i],
                value : this.data[this.keys[i]]
            };
        }
        return entrys;
    };
    
    /**
     * 获取键值数组(类似Java的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.getVals = function() {
        var len = this.keys.length;
        var vals = new Array(len);
        for (var i = 0; i < len; i++) {
        	vals[i] = this.data[this.keys[i]];
        }
        return vals;
    };
    
    
    /**
     * 判断Map是否为空
     */
    this.isEmpty = function() {
        return this.keys.length == 0;
    };
    
    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };
    
    /**
     * 重写toString 
     */
    this.toString = function(){
        var s = "{";
        for(var i=0;i<this.keys.length;i++,s+=','){
            var k = this.keys[i];
            s += k+"="+this.data[k];
        }
        s+="}";
        return s;
    };
}