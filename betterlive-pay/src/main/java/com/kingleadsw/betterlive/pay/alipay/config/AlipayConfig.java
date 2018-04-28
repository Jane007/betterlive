package com.kingleadsw.betterlive.pay.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	//合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "2088621483157375";
	
	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	//支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
//	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//	public static String ali_public_key ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	//老版wap支付宝公钥
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//老版wap私钥
	public static String Private_Key_HUIHUO = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJGIAoaAZKU4yBO/Uv/0/FI8bcBLi/NuvnS4wy1r4UqArMNUb+aaU1vWxryzyimdaH6iMx/oVEoNdUgPzzXcjmYehNrWjyrtTGqbTCMsEyGbtfyuCq+tqyUS/YhiSC2qtsKIzsyd2/1/I2JIDioi7XuxZRRb7mkE92r/3AjYDZ+NAgMBAAECgYA0EY2VwepAkwhCyHlRyr1cTvKVknIILt9Fs7VhUu/QUCl/2X4P67dXbIi+izURnpf8dtwD1oQR2q7TkZR5mOf5ZlBE4OxSG4TJ3xMOgi273DSWvzkgN1EEkydTfjaRbNMUvulfGGDkLqELpKEg9hMq9qo5mjEBtYCc5+GvIWoWoQJBANqlt80XBL1LIajl18MpCdCrA/91ymL6mqo8BbF8TMuMto0ffQ5PeI1DR7FmlQWgUNpC7xPwe0NkTGI2yYirVqMCQQCqZKXzLxkWg12xMEGSAZwCEERdtEH0/lHwyt7zan7skNTZNIeHKgjsk+qpuv+uPpHiWAho+pEwj6/cSQZUQwQPAkEApIKXnqo5GKwU8V2uT9rq1aQ259kapApmoLt0lSwvI+AF4nzUvRWyYKdEpAfJDGipLO+7rsb8F6K5jhQm4rv3/QJBAJ7mFUvfFApGn0zpr3w1PANAoECyJQAPWuKujZN6MLrzpUqZDfoiXQgiOKfn1x/rAaiOZ+leIZ+jn2BSSTCy/kkCQQCCQUT852X0jVenfTxfUYd3ifMBNvi47azBiOG0tSFBA6eWZZo7sjNjk2tO9dpQ0wBCOcP67DVotzOGi4esfXGT";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path ="C://";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	//收款账号 
//	public static String get_be_payed = "1975334989@qq.com";
	public static String get_be_payed = partner;
	
	
	//支付宝公钥-app
	public static String ali_public_key_app ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	//应用ID和私钥
	public static String AliPay_APPID_HUIHUO = "2017102609537860";
	//应用app私钥
	public static String Private_Key_HUIHUO_APP = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMMoW5Z6xaPRhRIgvOeblGxbJmiPZ4tpdLkqiH6WH2uqfHqh9QwYR0McRGGB1gJ4bYInDTPWGcig+ruz4BeQw0UiM3LZ7fdufzkiF72BUwegmGyr/v30s3SYRqDn+AJ80f2kTU1cdW22zUxZpMmAzH6kTHjrH/hM8Ym4jZ/+f6ILAgMBAAECgYA/qbOu9B/zH888KafWGh+h9V4jL9zEUsN8AA8E1G4tjjELRDgPGQBuORNph5RLvANlON4KseY5V6JThwssRNId8eyPUaYh/l37p035yTdV6j8vKjwh1CM1eYawwf5QtbY8XJIX4Nm8a6qjDP8nHFjmaL8yMim9q51bHA9FP7C3wQJBAPScsIO4Hv9Efyd7MczcXbhoTMnKeeZCwTNbgwEVWJQk3Vx985Zc/jQU8NLEuSK9SnKv7ZXTsHSrtcrjPAPzdCECQQDMPkQBMup/cWa0rHVk9tkE5r2d74rwPY0IETQwonkBWBhhNtDY+9BIWzx7UUrR/r32jwOQQrDwiFMmq8afvxCrAkBwc1fyNcKKN0L9M3VBaEztaYVjfuFrKWUH5xnidZ2y/P+GdaB1j2qNJtNPEYDKp5qB/r6KEjcca5O3IZ+FcXYBAkB7IMp0gpJ19BJ7t0AqdtoMJ2KGlQOIo0Kja2OwMjKKyeYgrSAov14oUMBcg3x8rnss6bi3Pk1XJIgkVwguA8xzAkBBtTxN9RNikjNs/04qAKrDB2dGqAzPAsxEdSGKiYgE+9Z8SlRxOiCKgnehwlqVlCj9fnwhY6XcJDqfCjchJ1tm";

	// 接收通知的接口名
	public static String service = System.getProperty("alipay.pay.notify.path");
	
	// 支付宝请求网关地址TODO dev :
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	
	// RSA2
	public static String SIGNTYPE = "RSA2";
	//刚刚在这里复制过去匹配的
	public static String str_url="";
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑]
	//支付请求接口
	public static String AliPayForm_Method_Pay ="alipay.trade.app.pay";
	//统一收单线下交易查询接口
	public static String AliPayForm_Method_Query ="alipay.trade.query";
	//统一收单交易退款接口
	public static String AliPayForm_Method_Refund ="alipay.trade.refund";
	public static String AliPayForm_Charset ="utf-8";
	public static String AliPayForm_Format ="JSON";
	public static String AliPayForm_SignType ="RSA";
	public static String AliPayForm_Version ="1.0";
	public static String AliPayForm_Timeout_Express ="125m"; //超时2小时(延迟5min)
	public static String AliPayForm_ProductCode ="QUICK_MSECURITY_PAY";
	
	//订单url(下单/查询/退款)
	public static String AliPayForm_OrderUrl="https://openapi.alipay.com/gateway.do";

	//公共返回码 10000--业务处理成功
	public static String AliPayQuery_ReturnCode_Success = "10000";
	
	//异步返回结果交易状态码
	public static String AliNotify_TradeState_WAIT = "WAIT_BUYER_PAY";
	public static String AliNotify_TradeState_CLOSED = "TRADE_CLOSED";
	public static String AliNotify_TradeState_SUCCESS = "TRADE_SUCCESS";
	public static String AliNotify_TradeState_FINISHED = "TRADE_FINISHED";
	//app支付通知回调地址
	public static String AliPayForm_NotifyOrderUrl_APP = "/app/payment/alipayNotify";
	
	//------------------wap端支付参数
	//支付宝的支付方式
	public static String paytype = "1";
	//表单提交方式
	public static String AliPayForm_Method ="get";
	//支付按钮
	public static String AliPayForm_ButtonName ="确认";
	//支付接口服务
	public static String AliPayForm_Service="alipay.wap.create.direct.pay.by.user";
	//订单返回url
	public static String AliPayForm_ReturnUrl="/weixin/order/payResult?orderCode=";
	//订单通知url
	public static String AliPayForm_NotifyOrderUrl_WAP="/weixin/payment/alipayNotify";
	//商品展示页-
	public static String AliPayForm_ShowUrl="/weixin/order/toPayOrderDetail?type=1&orderId=";
}

