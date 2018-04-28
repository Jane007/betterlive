package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerLotteryManager;
import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.core.constant.IntegralConstants;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.integral.LotteryUtil;
import com.kingleadsw.betterlive.model.CustomerIntegralRecord;
import com.kingleadsw.betterlive.model.SystemLottery;
import com.kingleadsw.betterlive.service.CustomerIntegralRecordService;
import com.kingleadsw.betterlive.service.SystemLotteryService;
import com.kingleadsw.betterlive.vo.CustomerVo;

@Component
@Transactional
public class CustomerLotteryManagerImpl implements CustomerLotteryManager {
	
	protected Logger logger = Logger.getLogger(CustomerLotteryManagerImpl.class);

	@Autowired
	private SystemLotteryService systemLotteryService;
	@Autowired
	private CustomerIntegralRecordService customerIntegralRecordService;
	@Autowired
	private CustomerManager customerManager;
	
	@Override
	public Map<String, Object> queryCustomerSignInfo(Integer customerId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int checkLottery = IntegralConstants.COMMON_STATUS_NO;
		int serialSign = IntegralConstants.COMMON_STATUS_NO;
		int lotterySignStatus = IntegralConstants.LOTTERY_STATUS_NO;
		try {
			
			PageData cl = new PageData();
			//每日抽奖活动
			cl.put("lotteryType", IntegralConstants.LOTTERY_TYPE_SIGN);
			SystemLottery lottery = systemLotteryService.queryOne(cl);
			lotterySignStatus = lottery.getStatus();
			//返回签到活动状态
			result.put("lotterySignStatus", lotterySignStatus);
			
			if(customerId == null || customerId.intValue() == 0) {
				result.put("checkLottery", checkLottery);
				result.put("serialSign", serialSign);
				return result;
			}

			//每日是否抽奖
			cl.clear();
			cl.put("customerId", customerId);
			cl.put("integralType", IntegralConstants.INTEGRAL_RECORD_TYPE_ONE);
			cl.put("status", IntegralConstants.RECORD_STATUS_YES);
			cl.put("recordType", IntegralConstants.RECORD_INCOME_YES);
			cl.put("checkDay", IntegralConstants.COMMON_STATUS_YES);
			checkLottery = customerIntegralRecordService.queryIntegralRecordCount(cl);
			
			cl.remove("checkDay");
			List<CustomerIntegralRecord> records = customerIntegralRecordService.queryListPage(cl);
			if(checkLottery == 1) {  //今日已抽奖，判断连续抽奖天数
				serialSign = getLotterySineNumByNow(records);
			}else {
				serialSign = getLotterySineNumByPre(records);
			}
			result.put("checkLottery", checkLottery);
			result.put("serialSign", serialSign);
			result.put("systemLotteryId", lottery.getSystemLotteryId());
			return result;
		} catch (Exception e) {
			logger.error("CustomerLotteryManagerImpl.queryCustomerSignInfo --error", e);
			result.put("checkLottery", checkLottery);
			result.put("serialSign", serialSign);
			return result;
		}
	}
	
	@Override
	public Map<String, Object> getLotterySign(int customerId, int clickIndex) {
		try {
			CustomerVo cust = customerManager.selectByPrimaryKey(customerId);
			if(cust == null){
				return CallBackConstant.DATA_NOT_FOUND.callbackError("用户信息异常");
			}
			//每日签到活动信息
			Map<String, Object> lotterySignInfo = this.queryCustomerSignInfo(customerId);
			if(!lotterySignInfo.get("lotterySignStatus").toString().equals("0")){
				return CallBackConstant.FAILED.callbackError("活动已结束~");
			}
			
			if(Integer.parseInt(lotterySignInfo.get("checkLottery").toString()) > 0){
				return CallBackConstant.DATA_HAD_FOUND.callbackError("您今天已经抽奖过啦");
			}
			
			PageData pd = new PageData();
			pd.put("customerId", customerId);
			List<CustomerIntegralRecord> weekList = customerIntegralRecordService.queryWeekDailyBonus(pd);
			int serialSign = 0;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar todayCalendar = Calendar.getInstance();
			String strToday = sdf.format(new Date());
			Date today = sdf.parse(strToday);
			todayCalendar.setTime(today);
			if (weekList != null && weekList.size() > 1) {
				for (int i = 0; i < weekList.size(); i++) {
					if(i == 0) {
						serialSign++;
						continue;
					}
					
					CustomerIntegralRecord nowDay = weekList.get(i);
					if(i == weekList.size() - 1) { //检查最后一条记录是不是昨天
						Calendar lastCalendar = Calendar.getInstance();
						Date lastday = sdf.parse(nowDay.getCreateTime());
						lastCalendar.setTime(lastday);
						lastCalendar.add(lastCalendar.DATE, +1);
						if(todayCalendar.getTimeInMillis() != lastCalendar.getTimeInMillis()) {
							serialSign = 0;
							break;
						}
					}
					
					
					CustomerIntegralRecord preDay = weekList.get(i - 1);

					// 判断一周连续
					int now = nowDay.getWday();
					int pre = preDay.getWday();
					if (now - 1 == pre) {
						serialSign++;
						if (serialSign == 7) {
							break;
						}
					} else {
						serialSign = 1;
						break;
					}
				}
			} else if (weekList != null && weekList.size() == 1) {
				CustomerIntegralRecord checkRecord = weekList.get(0);
				Calendar lastCalendar = Calendar.getInstance();
				Date lastday = sdf.parse(checkRecord.getCreateTime());
				lastCalendar.setTime(lastday);
				lastCalendar.add(lastCalendar.DATE, +1);
				if(todayCalendar.getTimeInMillis() == lastCalendar.getTimeInMillis()) {
					serialSign = 1;
				}
			}
			BigDecimal signIntegral = LotteryUtil.compareToRate(serialSign);
			
			CustomerIntegralRecord custIntegralRecord = new CustomerIntegralRecord();
			custIntegralRecord.setCustomerId(customerId);
			custIntegralRecord.setIntegral(signIntegral);
			custIntegralRecord.setIntegralType(IntegralConstants.INTEGRAL_RECORD_TYPE_ONE);
			custIntegralRecord.setRecordType(IntegralConstants.RECORD_INCOME_YES);
			custIntegralRecord.setStatus(IntegralConstants.RECORD_STATUS_YES);
			custIntegralRecord.setObjId(Long.parseLong(lotterySignInfo.get("systemLotteryId").toString()));
			this.customerIntegralRecordService.insert(custIntegralRecord);
			
			String custLevelName = cust.getLevelName();
			BigDecimal currentIntegral = cust.getCurrentIntegral();
			BigDecimal accumulativeIntegral = cust.getAccumulativeIntegral();
			int requirementIntegral = 0;
			if (signIntegral.compareTo(BigDecimal.ZERO) == 1) {
				//校验是否升级，增加账户金币
				Map<String, Object> checkCust = customerManager.upgradeCustomerLevel(cust.getCustomer_id(), signIntegral);
				if(checkCust.get("code") != null){
					int checkCode = Integer.parseInt(checkCust.get("code").toString());
					if (checkCode == 1010) {
						@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map<String, Object>) checkCust.get("data");
						custLevelName = map.get("custLevelName").toString();
						currentIntegral = new BigDecimal(map.get("currentIntegral").toString());
						accumulativeIntegral = new BigDecimal(map.get("accumulativeIntegral").toString());
						requirementIntegral = Integer.parseInt(map.get("requirementIntegral").toString());
					}
				}
			}
			
			
			//抽奖弹窗，每个单元格对应的金币描述;随机排序，且将当前点击的单元格位置匹配上
			String[] specsDesc = LotteryUtil.lotteryDescSorts(clickIndex, signIntegral);
			
			serialSign = Integer.parseInt(lotterySignInfo.get("serialSign").toString()) + 1;
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("signIntegral", signIntegral);
			result.put("serialSign", serialSign);
			result.put("lotteryIntegralDesc", specsDesc);
			result.put("custLevelName", custLevelName);
			result.put("currentIntegral", currentIntegral);
			result.put("accumulativeIntegral", accumulativeIntegral);
			result.put("requirementIntegral", requirementIntegral);
			return CallBackConstant.SUCCESS.callback(result);
		} catch (Exception e) {
			logger.error("CustomerLotteryManagerImpl.getLotterySign --error", e);
			return CallBackConstant.FAILED.callback();
		}
	}
	
	
	/**
	 * 今天已签到，计算连续签到天数
	 * @param records
	 * @return
	 * @throws ParseException 
	 */
	public static int getLotterySineNumByNow(List<CustomerIntegralRecord> records) throws ParseException {
		int serialSign = 0;
		if (records != null && records.size() > 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar todayCalendar = Calendar.getInstance();
			String strToday = sdf.format(new Date());
			Date today = sdf.parse(strToday);
			todayCalendar.setTime(today);
			
			for (int i = 0; i < records.size(); i++) {
				
				CustomerIntegralRecord nowDay = records.get(i);
				Date now = sdf.parse(nowDay.getCreateTime());
				Calendar minusCalendar = Calendar.getInstance();
				minusCalendar.setTime(now);
				if(i == 0) {
					//检查当天签到记录是否存在
					if(todayCalendar.getTimeInMillis() != minusCalendar.getTimeInMillis()) {
						break;
					}
					serialSign++;
					continue;
				}
				
				CustomerIntegralRecord preDay = records.get(i - 1);
				
				Date pre = sdf.parse(preDay.getCreateTime());
				Calendar preCalendar = Calendar.getInstance();
				preCalendar.setTime(pre);
				preCalendar.add(preCalendar.DATE, -1);
				if (minusCalendar.getTimeInMillis() != preCalendar.getTimeInMillis()) {
					break;
				}
				serialSign++;
			}
		} else {
			serialSign = 1;
		}
		return serialSign;
	}
	
	/**
	 * 今天还没签到，计算连续签到天数
	 * @param records
	 * @return
	 * @throws ParseException 
	 */
	public static int getLotterySineNumByPre(List<CustomerIntegralRecord> records) throws ParseException {
		int serialSign = 0;
		if (records != null && records.size() > 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar todayCalendar = Calendar.getInstance();
			String strToday = sdf.format(new Date());
			Date today = sdf.parse(strToday);
			todayCalendar.setTime(today);
			todayCalendar.add(todayCalendar.DATE, -1);
			
			for (int i = 0; i < records.size(); i++) {
				
				CustomerIntegralRecord nowDay = records.get(i);
				Date now = sdf.parse(nowDay.getCreateTime());
				Calendar minusCalendar = Calendar.getInstance();
				minusCalendar.setTime(now);
				if(i == 0) {
					//检查当昨天签到记录是否存在
					if(todayCalendar.getTimeInMillis() != minusCalendar.getTimeInMillis()) {
						break;
					}
					serialSign++;
					continue;
				}
				
				CustomerIntegralRecord preDay = records.get(i - 1);
				
				Date pre = sdf.parse(preDay.getCreateTime());
				Calendar preCalendar = Calendar.getInstance();
				preCalendar.setTime(pre);
				preCalendar.add(preCalendar.DATE, -1);
				if (minusCalendar.getTimeInMillis() != preCalendar.getTimeInMillis()) {
					break;
				}
				serialSign++;
			}
		} else {
			serialSign = 1;
		}
		return serialSign;
	}

}
