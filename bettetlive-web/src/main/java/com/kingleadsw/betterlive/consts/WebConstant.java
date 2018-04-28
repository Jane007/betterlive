package com.kingleadsw.betterlive.consts;

import java.util.ResourceBundle;

/**
 * author szx date 2016-08-19 8:54 定义web常用常量参数
 **/
public class WebConstant {

	public static final String SESSION_FRONT_USER = "account";

	public static final String SESSION_ADMIN_USER = "admin";
	
	public static final String USER_DEFAULT_PHOTO = "http://www.hlife.shop/huihuo/resources/images/default_photo.png";
	
	/**
	 * 资源配置信息.
	 */
	private final static ResourceBundle config = ResourceBundle.getBundle("web-constants");

	/**
	 * 系统访问路径
	 */
	public final static String MAIN_SERVER = config.getString("com.betterlive.main.server");
	/**
	 * 资源文件路径
	 */
	public final static String RESOURCE_PATH = config.getString("com.betterlive.resourcepath");
	/**
	 * 系统文件上传根路径
	 */
	public final static String UPLOAT_ROOT_PATH = config.getString("upload_file_path");

	/**
	 * 图片上传根路径目录
	 */
	public final static String UPLOAT_ROOT_PATH_IMAGE = config.getString("upload_image_path");

	/**
	 * 评论图片上传目录
	 */
	public final static String UPLOAT_ROOT_PATH_IMAGE_ARTICLE = "/article";

	/**
	 * 评论图片上传大小限制暂定300K
	 */
	public final static Long UPLOAT_ROOT_PATH_IMAGE_ARTICLE_SIZE = 1024 * 1024l;
	
	/**
	 * app token有效时间，当前值约为1年
	 */
	public final static int TOKEN_TIME = 30844800;

	/**
	 * 7牛账号密码
	 */
	public final static String QINIU_ACCESS_KEY = config
			.getString("qiniu_access_key");
	public final static String QINIU_SECRET_KEY = config
			.getString("qiniu_secret_key");
	public final static String QINIU_CLOUD_KEY = config
			.getString("qiniu_cloud_key");
	public final static String QINIU_LINK = config.getString("qiniu_link");
	/**
	 * 七牛缩略图接口参数
	 */
	public final static String QINIU_THUMP = "?imageMogr2/thumbnail/100x";// config.getString("qiniu_thump");

	public final static String WX_ORDER_NOTIFY_URL = config.getString("wx.order.notify.path");

	public final static String WX_RECHARGE_NOTIFY_URL = config.getString("wx.recharge.notify.path");

	
	/*****************************   微信支付  ************************************************** */
	/**
	 * 微信appid
	 */
	public final static String WX_APPID = config.getString("wx.appid");
	/**
	 * 微信 secret
	 */
	public final static String WX_SECRET = config.getString("wx.secret");
	
	
	
	
	/**
	 * 微信商户号
	 */
	public final static String WX_MCH_ID = config.getString("wx.mch.id");
	
	
	public final static String WX_TEST_TOKEN = config.getString("wx.test.token");

	public final static String MCH_API_KEY = config.getString("mch.api.key");
	/**
	 * 微信支付通知回调地址
	 */
	public static final String WX_PAY_NOTIFY =config.getString("wx.order.notify.path");
	
	/**
	 * 一卡通支付接口
	 */
	public final static String YKT_PAY_API = config.getString("ykt.pay.api");
	/**
	 * 一卡通查询公钥接口
	 */
	public final static String YKT_FIND_PUB_KEY = config.getString("ykt.find.pub.key");
	/**
	 * 一卡通单笔交易查询接口
	 */
	public final static String YKT_QUERY_INGLE_ORDER_API = config.getString("ykt.query.ingle.order.api");
	/**
	 * 一卡通支付公钥
	 */
//	public final static String YKT_PUB_KEY = config.getString("ykt.pub.key");
	/**
	 * 一卡通支付秘钥
	 */
	public final static String YKT_SECRET_KEY = config.getString("ykt.secret.key");
	/**
	 * 一卡通商户号
	 */
	public final static String YKT_MERCHANT_NO = config.getString("ykt.merchant.no");
	/**
	 * 一卡通商户分行号
	 */
	public final static String YKT_BRANCH_NO = config.getString("ykt.branch.no");
	/**
	 * 一卡通支付成功通知接口
	 */
	public final static String YKT_PAY_NOTICE_URL = config.getString("ykt.pay.notice.url");
	/**
	 * 一卡通支付完成后返回页面
	 */
	public final static String YKT_RETURN_URL = config.getString("ykt.return.url");
	/**
	 * 一卡通首次签约通知接口
	 */
	public final static String YKT_SIGN_NOTICE_URL = config.getString("ykt.sign.notice.url");
	
	
	/**
	 * 是否启用默认的用户
	 */
	public static final String ISMEMBER = config.getString("isMember");

	public static final String WX_ACCESS_TOKEN_URL = config.getString("wx.access.token.url");

	public static final String WX_CERTFILEPATH = config.getString("wx.certfilepath");
	

	/**
	 * 微信订单发货发送消息模板id
	 */
	public final static String FAHUO_TEMPLATEID = config.getString("wx.fahuo.templateid");
	/**
	 * 微信购买商品发送消息模板id
	 */
	public final static String BUYGOODS_TEMPLATEID = config.getString("wx.buygoods.templateid");
	
	/**
	 * 客服服务号码
	 */
	public final static String SERVICE_PHONE = config.getString("wx.service_phone");
	
	/**
	 * IOS挥货应用市场地址
	 */
	public final static String IOS_HUIHUO_APPSTORE_LOCAL = config.getString("ios.appstore.local");
	
	
	
	
	/***
	 * 新版首页红包展示没购买过商品用户的新手红包
	 */
	public final static String NEW_USER_COUPON = config.getString("xs.couponId");
	
	/**
	 *分享新手100元红包
	 */
	public final static String SHARE_REGISTER_HUNDRED_COUPONS=config.getString("xs.hundred.couponId");
}
