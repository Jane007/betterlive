$.extend($.fn.validatebox.defaults.rules, {
	noComma : {
		validator : function(value, param) {
			var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
			var valuex = $.trim(value);
			if ('' == valuex) {
				return false;
			}
			
			return reg.test(valuex);
		},
		message : '只能是中文、英文、数字'
	},
	date : {
		validator : function(value, param) {
			var regexp = /^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s(((0?[1-9])|(1[0-2]))\:([0-5][0-9])((\s)|(\:([0-5][0-9])\s))([AM|PM|am|pm]{2,2})))?$/;
			return regexp.test(value);
		},
		message : '请输入正确的日期，例：2010-10-15'
	},
	
	endDateCheck : {
		validator : function(value,param){
			var endDate = $("#endDateId").datebox("getValue"); 
			var now = new Date();	
			var flag = true;
			var time1 = new Date(endDate).getTime();
			var temp = now.getFullYear()+'-'+(now.getMonth()+1)+'-'+now.getDate();
			var time2 = new Date(temp).getTime();
		    if(time1<time2){
		    	flag = false;
		    }
		    return flag;
		},
		message:'结束时间不能早于当前时间'
	},
	
	isempty : {
		validator : function(value, param) {
			var flag = false;
			if ($.trim(value).length != 0) {
				flag = true;
			}
			return flag;
		},
		message : '不能为空'
	},
	phone : {
		validator : function(value, param) {
			var phoneRegWithArea = /^[0][1-9]{2,3}[-]?[0-9]{5,8}$/;
			var phoneRegNoArea = /^[1-9]{1}[0-9]{5,8}$/;
			var mobilePhone = /^[1][3,5,8][0-9]{9}$/;
			if (value.length > 9) {
				if (value.substring(0, 1) == 1) {
					return mobilePhone.test(value);
				} else {
					return phoneRegWithArea.test(value);
				}
			} else {
				return phoneRegNoArea.test(value);
			}
		},
		message : '请输入正确的联系电话'
	},
	mobilePhone : {
		validator : function(value, param) {
			var reg = /^(13|15|18)\d{9}$/i;
			return reg.test(value);
		},
		message : '请输入正确的手机号码'
	},
	eqPwd : {
		validator : function(value, param) {
			var valu = $(param[0]).val();
			return value == valu;
		},
		message : '新密码两次输入不一致，请重新输入'
	},
	isInteger : {
		validator : function(value, param) {
			var regexp = /^\d{0,3}$/;
			return regexp.test(value);
		},
		message : '必须为整数'
	},
	age : {
		validator : function(value, param) {
			var regexp = /^(100|[1-9]|[1-9]\d)$/;
			return regexp.test(value);
		},
		message : '请输入1~100正整数'
	},
	integer : {
		validator : function(value, param) {
			var regexp = /^[0-9]*[1-9][0-9]*$/;
			return regexp.test(value);
		},
		message : '请输入正整数'
	},
	double : {
		validator : function(value, param) {
			var regexp = /^\d+(\.\d+)?$/;
			return regexp.test(value);
		},
		message : '请输入正数'
	},
	zip : {
		validator : function(value, param) {
			var regexp = /^(\d{3})(\d{3})?$/;
			return regexp.test(value);
		},
		message : '请输入正确的邮政编码'
	},
	fax : {
		validator : function(value, param) {
			var regexp = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
			return regexp.test(value);
		},
		message : '请输入正确的传真号码'
	},
	double : {
		validator : function(value, param) {
			var regexp = /^[0-9]+(.[0-9]{2})?$/;
			return regexp.test(value);
		},
		message : '请输入正确金额，如0.00'
	},
	doubleFee : {
		validator : function(value, param) {
			var regexp = /^[0-9]{1,9}\.[0-9]{1,2}$/;
			return regexp.test(value);
		},
		message : '请输入正确金额，如0.00 (小数点前最多九位，小数点后最多两位)'
	},
	capital : {
		validator : function(value, param) {
			var regexp = /^[A-Z]+$/;
			return regexp.test(value);
		},
		message : '请输入大写英文字母A~Z'
	},
	contentValidate : {
		validator : function(value, param) {
			var regexp = /^[\u4E00-\u9FA5a-zA-Z0-9_]{1,200}$/;
			return regexp.test(value);
		},
		message : '请输入汉字 英文字母 数字 下划线'
	},
	yyyymm : {
		validator : function(value, param) {
			var regexp = /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(0[1-9]|1[0-2])$/;
			return regexp.test(value);
		},
		message : '请输入正确的日期，如 2013-02'
	},
	kefuPhone : {
		validator : function(value, param) {
			var regexp = /^\d(\d|[-]|[;]|\s)+(\d|\s)$/;
			return regexp.test(value);
		},
		message : '输入格式不正确'
	},
	english_ : {// 验证英语和下划线
		validator : function(value) {
			return /^[A-Za-z0-9_-]+$/i.test(value);
		},
		message : '请输入英文、数字、下划线'
	},
	englishNum : {// 验证英语和数字
		validator : function(value) {
			return /^[0-9a-zA-Z]+$/.test(value);
		},
		message : '请输入英文、数字'
	},
	floatFix2: {
		validator : function(value, param) {
			var regexp = /^[0-9]{1,9}\.[0-9]{1,2}$/;
			return regexp.test(value);
		},
		message : '正数且最多只能有2位小数'
	},
	floatFix: {
		validator : function(value, param) {
			var regexp = /^[0-9]{1,9}\.[0-9]{1,1}$/;
			return regexp.test(value);
		},
		message : '必须大于0且有1位小数'
	},
	testPassword : {// 验证英语和数字
		validator : function(value) {
			return /^[a-zA-Z0-9]{6,10}$/.test(value);
		},
		message : '最少6位只支持英文、数字'
	}
});