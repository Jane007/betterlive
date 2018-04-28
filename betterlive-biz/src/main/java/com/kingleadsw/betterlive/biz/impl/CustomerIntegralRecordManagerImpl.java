package com.kingleadsw.betterlive.biz.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerIntegralRecordManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.OrderProductManager;
import com.kingleadsw.betterlive.biz.SpecialArticleManager;
import com.kingleadsw.betterlive.biz.SpecialMananger;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.BigDecimalUtil;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.CustomerIntegralRecord;
import com.kingleadsw.betterlive.service.CustomerIntegralRecordService;
import com.kingleadsw.betterlive.vo.CustomerIntegralRecordVo;
import com.kingleadsw.betterlive.vo.CustomerVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.SpecialArticleVo;
import com.kingleadsw.betterlive.vo.SpecialVo;

/**
 * 2018-03-28 by fang
 */
@Component
@Transactional
public class CustomerIntegralRecordManagerImpl extends BaseManagerImpl<CustomerIntegralRecordVo,CustomerIntegralRecord> implements CustomerIntegralRecordManager{
	protected Logger logger = Logger.getLogger(CustomerIntegralRecordManagerImpl.class);
	
	@Autowired
    private CustomerIntegralRecordService customerIntegralRecordService;
    @Autowired
    private SpecialArticleManager specialArticleManager;
    
    @Autowired
    private SpecialMananger specialManager;
    @Autowired
    private OrderProductManager orderProductManager;
    @Autowired
    private CustomerManager customerManager;
    
    @Override
    protected BaseService<CustomerIntegralRecord> getService() {
    	return customerIntegralRecordService;
    }

	@Override
	public int queryIntegralRecordCount(PageData cl) {
		return customerIntegralRecordService.queryIntegralRecordCount(cl);
	}

	@Override
	public List<CustomerIntegralRecordVo> queryWeekDailyBonus(PageData pd) {
		List<CustomerIntegralRecordVo> list = po2voer.transfer(CustomerIntegralRecordVo.class, customerIntegralRecordService.queryWeekDailyBonus(pd));
		return list;
	}

	@Override
	public List<CustomerIntegralRecordVo> queryAwardListPage(PageData pd) {
		
		try {
			List<CustomerIntegralRecordVo> list = po2voer.transfer(CustomerIntegralRecordVo.class, customerIntegralRecordService.queryAwardListPage(pd));
			if (list == null) {
				return new ArrayList<CustomerIntegralRecordVo>();
			}
			
			String mainServer = pd.getString("mainServer");
			for (CustomerIntegralRecordVo customerIntegralRecordVo : list) {
				Integer integralType = customerIntegralRecordVo.getIntegralType();
				long customerId = customerIntegralRecordVo.getCustomerId();
				long objectId = customerIntegralRecordVo.getObjId();
				String createTime = customerIntegralRecordVo.getCreateTime();
				if(StringUtils.isNotBlank(createTime)){
					createTime = createTime.substring(0,10);
				}
				customerIntegralRecordVo.setCreateTime(createTime);
				if (objectId == 0) {
					return null;
				}
				if (integralType == IntegralConstants.INTEGRAL_RECORD_TYPE_TWO) {// 发动态
						pd.put("articleId", objectId);
						pd.put("status", 1);
						SpecialArticleVo article = specialArticleManager.queryCircleOne(pd);
						if (article == null) {
							continue;
						}
						String introduce = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
						if(StringUtil.isNotNull(article.getArticleIntroduce())){
							introduce = article.getArticleIntroduce();
						}
						customerIntegralRecordVo.setTitle(article.getArticleTitle());
						customerIntegralRecordVo.setImgUrl(article.getArticleCover());
						customerIntegralRecordVo.setLinkUrl(mainServer+"/weixin/discovery/toDynamicDetail?articleId="+objectId+"&shareCustomerId="+customerId);
						customerIntegralRecordVo.setShareDesc(introduce);
						customerIntegralRecordVo.setPraiseCount(article.getPraiseCount());
						
				} else if (integralType == IntegralConstants.INTEGRAL_RECORD_TYPE_FIVE) {// 3.分享文章（动态、精选）5点赞文章
						pd.put("articleId", objectId);
						pd.put("status", 1);
						SpecialArticleVo article = specialArticleManager.queryOne(pd);
						if (article == null) {
							continue;
						}
						String linkUrl = mainServer+"/weixin/discovery/toDynamicDetail?articleId="+objectId+"&shareCustomerId="+customerId;
						if(article.getArticleFrom() == 0){
							linkUrl = mainServer+"/weixin/discovery/toSelectedDetail?articleId="+objectId+"&shareCustomerId="+customerId;
						}
						String introduce = "传播好吃的、有趣的美食资讯，只要你会吃、会写、会分享，就能成为美食达人！";
						if(StringUtil.isNotNull(article.getArticleIntroduce())){
							introduce = article.getArticleIntroduce();
						}
						customerIntegralRecordVo.setTitle(article.getArticleTitle());
						customerIntegralRecordVo.setImgUrl(article.getArticleCover());
						customerIntegralRecordVo.setLinkUrl(linkUrl);
						customerIntegralRecordVo.setShareDesc(introduce);
						customerIntegralRecordVo.setPraiseCount(article.getPraiseCount());
				} else if (integralType == IntegralConstants.INTEGRAL_RECORD_TYPE_SIX) {// 4分享视频  6点赞视频
						pd.put("specialId",objectId);
						pd.put("status", 1);
						pd.put("specialType", 4);
						SpecialVo specialVo = specialManager.queryOne(pd);
						if (specialVo == null) {
							continue;
						}
						customerIntegralRecordVo.setTitle(specialVo.getSpecialName());
						customerIntegralRecordVo.setImgUrl(specialVo.getSpecialCover());
						customerIntegralRecordVo.setLinkUrl(mainServer+"/weixin/discovery/toTorialComment?specialId="+objectId+"&shareCustomerId="+customerId);
						customerIntegralRecordVo.setPraiseCount(specialVo.getPraiseCount());
						customerIntegralRecordVo.setShareDesc(specialVo.getSpecialIntroduce());
				} else if (integralType == IntegralConstants.INTEGRAL_RECORD_TYPE_SERVEN) {// 订单支付
					   OrderProductVo orderproduct = orderProductManager.selectByPrimaryKey((int)objectId);
					   customerIntegralRecordVo.setTitle(orderproduct.getProduct_name());
					   customerIntegralRecordVo.setImgUrl(orderproduct.getSpec_img());
					   customerIntegralRecordVo.setPraiseCount(0);
					   customerIntegralRecordVo.setShareDesc(orderproduct.getProduct_name());
					   customerIntegralRecordVo.setLinkUrl(mainServer+"/weixin/order/queryOrderDetails?tabType=0&orderId="+orderproduct.getOrder_id()+"&orderCode="+orderproduct.getSub_order_code()+"&orderpro_id="+orderproduct.getOrderpro_id());
					   customerIntegralRecordVo.setProductId(orderproduct.getProduct_id());
				}
				this.installAppDesc(customerIntegralRecordVo);
			}
		
			return list;
		} catch (Exception e) {
			logger.error("查询要领取金币的文章报错",e);
		}
		
		return null;
	}
	
	private CustomerIntegralRecordVo installAppDesc(CustomerIntegralRecordVo customerIntegralRecordVo){
		//int priseCount = customerIntegralRecordVo.getPraiseCount();
		String integral = BigDecimalUtil.subZeroAndDot(customerIntegralRecordVo.getIntegral());
		String appDesc = customerIntegralRecordVo.getShareDesc();
		if (customerIntegralRecordVo.getIntegralType() == IntegralConstants.INTEGRAL_RECORD_TYPE_TWO) {
			appDesc = "已审核通过，获得了" + integral + "个金币";
		} else if (customerIntegralRecordVo.getIntegralType() == IntegralConstants.INTEGRAL_RECORD_TYPE_SERVEN) {
			appDesc = "购买成功，获得了" + integral + "个金币";
		}
		if(appDesc==null){
			appDesc = customerIntegralRecordVo.getTitle();
		}
		
		customerIntegralRecordVo.setAppDesc(appDesc);
		return customerIntegralRecordVo;
	}

	@Override
	public TreeMap<Integer, String> installWeekCheckData(PageData pd) {
		
		TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
		try {
			
			List<CustomerIntegralRecordVo> weekList = this.queryWeekDailyBonus(pd);
			List<Integer> nums = new ArrayList<Integer>();
			Calendar cal = Calendar.getInstance();
			int wday = cal.get(Calendar.DAY_OF_WEEK) - 1;
			// 一周的数据
			treeMap.put(0, "before");// 0是星期天
			for (int i = 1; i < 7; i++) {
				treeMap.put(i, "before");
				if (i > wday) {
					treeMap.put(i, "after");
				}
			}

			if (weekList != null) {
				for (CustomerIntegralRecordVo customerIntegralRecordVo : weekList) {
					nums.add(customerIntegralRecordVo.getWday());
				}
				int max = Collections.max(nums);
				Set<Integer> setKey = treeMap.keySet();
				for (int i = 0; i < setKey.size(); i++) {// 最大日期之前的都是before
					if (i < max) {
						treeMap.put(i, "before");
					}
				}
				for (CustomerIntegralRecordVo customerIntegralRecordVo : weekList) {// 参加签到的数据
					treeMap.put(customerIntegralRecordVo.getWday(), "now");
				}
			}
			
		} catch (Exception e) {
			logger.error("组装七天签到数据异常",e);
		}
		return treeMap;
	}

	@Override
	public Map<String, Object> getGoldAward(PageData pd) {
		//领取金币的动作
		try {
			//1、将用户积分记录表里的
			String recordId = pd.getString("recordId");
			if(StringUtils.isBlank(recordId)){
				return CallBackConstant.PARAMETER_ERROR.callbackError("领取金币记录不能为空");
			}
			
			CustomerIntegralRecord record = customerIntegralRecordService.selectByPrimaryKey(Integer.parseInt(recordId));
			if (record == null) {
				return CallBackConstant.DATA_NOT_FOUND.callback();
			}
			
			//用户表里积分修改
			CustomerVo customer = customerManager.selectByPrimaryKey((int)record.getCustomerId());
			if(customer == null) {
				return CallBackConstant.DATA_NOT_FOUND.callback();
			}
			
			if(record.getStatus().intValue() == IntegralConstants.RECORD_STATUS_FAIL){
				return CallBackConstant.FAILED.callbackError("金币已失效");
			}
			
			if(record.getStatus().intValue() == IntegralConstants.RECORD_STATUS_YES){
				return CallBackConstant.FAILED.callbackError("您已经领取过，不能重复领取");
			}
			
			record.setStatus(IntegralConstants.RECORD_STATUS_YES);
			int count = customerIntegralRecordService.updateByPrimaryKey(record);
			if (count <= 0) {
				return CallBackConstant.FAILED.callbackError("领取金币失败");
			}
			
			//校验是否升级, 增加账户金币
			customerManager.upgradeCustomerLevel(customer.getCustomer_id(), record.getIntegral());
			
			String formatIntegral = BigDecimalUtil.subZeroAndDot(record.getIntegral());
			return CallBackConstant.SUCCESS.callback(formatIntegral);

		} catch (Exception e) {
			logger.error("领取金币异常",e);
			return CallBackConstant.FAILED.callback();
		}
	}


	@Override
	public BigDecimal queryIntegralNumByParams(PageData pd) {
		
		return customerIntegralRecordService.queryIntegralNumByParams(pd);
	}

	@Override
	public List<CustomerIntegralRecordVo> queryOverdueList(PageData pd) {
		List<CustomerIntegralRecordVo> list = po2voer.transfer(CustomerIntegralRecordVo.class, customerIntegralRecordService.queryOverdueList(pd));
		return list;
	}

	@Override
	public Integer updateOverDueStatus(PageData pd) {
		try {
			List<CustomerIntegralRecord> list = customerIntegralRecordService.queryOverdueList(pd);
			int count = 0;
			if(list==null || list.size()==0){//没有过期的数据，不要处理
				return 0;
			}
			//有过期的数据，就要处理
			for (CustomerIntegralRecord record : list) {
				record.setStatus(IntegralConstants.RECORD_STATUS_FAIL);//失效
				count += customerIntegralRecordService.updateByPrimaryKey(record);
			}
			return count;
		} catch (Exception e) {
			logger.error("修改过期金币记录出现异常",e);
			return -1;
		}
		
	}

	@Override
	public int checkArticleAndVideoPraise(Integer customerId) {
		try {
			PageData pd = new PageData();
			//检查文章是否有赞
			pd.put("customerId", customerId);
			pd.put("shareType", 4);
			pd.put("praiseType", 4);
			pd.put("shareContentType", IntegralConstants.INTEGRAL_RECORD_TYPE_THREE);
			pd.put("sharePraiseType", IntegralConstants.INTEGRAL_RECORD_TYPE_FIVE);
			List<CustomerIntegralRecord> articlePraiseList = customerIntegralRecordService.queryCustSharePraiseList(pd);
			
			//检查视频是否有赞
			pd.clear();
			pd.put("customerId", customerId);
			pd.put("shareType", 3);
			pd.put("praiseType", 2);
			pd.put("shareContentType", IntegralConstants.INTEGRAL_RECORD_TYPE_FOUR);
			pd.put("sharePraiseType", IntegralConstants.INTEGRAL_RECORD_TYPE_SIX);
			List<CustomerIntegralRecord> videoPraiseList = customerIntegralRecordService.queryCustSharePraiseList(pd);
			
			List<CustomerIntegralRecord> recordList = new ArrayList<CustomerIntegralRecord>();
			if(articlePraiseList != null && articlePraiseList.size() > 0){
				for (CustomerIntegralRecord praise : articlePraiseList) {
					
					if(praise.getCheckRecordId() != null){
						continue;
					}
					
					int integralCount = praise.getPraiseCount();
					if(integralCount > IntegralConstants.LIMIT_PRAISE_TIMES){
						integralCount = IntegralConstants.LIMIT_PRAISE_TIMES;
					}
					
					CustomerIntegralRecord record = new CustomerIntegralRecord();
					record.setCustomerId(customerId);
					record.setIntegral(new BigDecimal(integralCount));
					record.setStatus(IntegralConstants.RECORD_STATUS_WAIT);
					record.setIntegralType(IntegralConstants.INTEGRAL_RECORD_TYPE_FIVE);
					record.setRecordType(IntegralConstants.RECORD_INCOME_YES);
					record.setObjId(praise.getObjId());
					recordList.add(record);
					
				}
			}
			
			if(videoPraiseList != null && videoPraiseList.size() > 0){
				for (CustomerIntegralRecord praise : videoPraiseList) {
					if(praise.getCheckRecordId() != null){
						continue;
					}
					
					int integralCount = praise.getPraiseCount();
					if(integralCount > IntegralConstants.LIMIT_PRAISE_TIMES){
						integralCount = IntegralConstants.LIMIT_PRAISE_TIMES;
					}
					
					CustomerIntegralRecord record = new CustomerIntegralRecord();
					record.setCustomerId(customerId);
					record.setIntegral(new BigDecimal(integralCount));
					record.setStatus(IntegralConstants.RECORD_STATUS_WAIT);
					record.setIntegralType(IntegralConstants.INTEGRAL_RECORD_TYPE_SIX);
					record.setRecordType(IntegralConstants.RECORD_INCOME_YES);
					record.setObjId(praise.getObjId());
					recordList.add(record);
					
				}
			}
			
			int result = IntegralConstants.COMMON_STATUS_NO;
			if(recordList != null && recordList.size() > 0) {
				int count = this.customerIntegralRecordService.batchSave(recordList);
				if(count > 0){
					result = IntegralConstants.COMMON_STATUS_YES;
				}
			}
			
			return result;
		} catch (Exception e) {
			logger.error("CustomerIntegralRecordManagerImpl.checkArticleAndVideoPraise --error", e);
			return IntegralConstants.COMMON_STATUS_NO;
		}
	}




}
