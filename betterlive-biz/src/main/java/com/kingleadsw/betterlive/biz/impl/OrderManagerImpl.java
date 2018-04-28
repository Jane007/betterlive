package com.kingleadsw.betterlive.biz.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingleadsw.betterlive.biz.CustomerManager;
import com.kingleadsw.betterlive.biz.FreightManager;
import com.kingleadsw.betterlive.biz.OrderManager;
import com.kingleadsw.betterlive.core.biz.BaseManagerImpl;
import com.kingleadsw.betterlive.core.constant.OrderConstants;
import com.kingleadsw.betterlive.core.convert.BeanConverter;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;
import com.kingleadsw.betterlive.core.util.CallBackConstant;
import com.kingleadsw.betterlive.core.util.DateUtil;
import com.kingleadsw.betterlive.core.util.StringUtil;
import com.kingleadsw.betterlive.model.ActivityProduct;
import com.kingleadsw.betterlive.model.CouponInfo;
import com.kingleadsw.betterlive.model.Customer;
import com.kingleadsw.betterlive.model.GiftCard;
import com.kingleadsw.betterlive.model.GiftCardRecord;
import com.kingleadsw.betterlive.model.GroupJoin;
import com.kingleadsw.betterlive.model.Order;
import com.kingleadsw.betterlive.model.OrderProduct;
import com.kingleadsw.betterlive.model.PayLog;
import com.kingleadsw.betterlive.model.PreProduct;
import com.kingleadsw.betterlive.model.Product;
import com.kingleadsw.betterlive.model.ProductSpec;
import com.kingleadsw.betterlive.model.ReceiverAddress;
import com.kingleadsw.betterlive.model.Special;
import com.kingleadsw.betterlive.model.SysDict;
import com.kingleadsw.betterlive.model.SysGroup;
import com.kingleadsw.betterlive.model.UseLock;
import com.kingleadsw.betterlive.model.UserGroup;
import com.kingleadsw.betterlive.service.ActivityProductService;
import com.kingleadsw.betterlive.service.CouponInfoService;
import com.kingleadsw.betterlive.service.CustomerService;
import com.kingleadsw.betterlive.service.GiftCardRecordService;
import com.kingleadsw.betterlive.service.GiftCardService;
import com.kingleadsw.betterlive.service.GroupJoinService;
import com.kingleadsw.betterlive.service.OrderProductService;
import com.kingleadsw.betterlive.service.OrderService;
import com.kingleadsw.betterlive.service.PayLogService;
import com.kingleadsw.betterlive.service.PreProductService;
import com.kingleadsw.betterlive.service.ProductService;
import com.kingleadsw.betterlive.service.ProductSpecService;
import com.kingleadsw.betterlive.service.ReceiverAddressService;
import com.kingleadsw.betterlive.service.ShoppingCartService;
import com.kingleadsw.betterlive.service.SpecialService;
import com.kingleadsw.betterlive.service.SysDictService;
import com.kingleadsw.betterlive.service.SysGroupService;
import com.kingleadsw.betterlive.service.UseLockService;
import com.kingleadsw.betterlive.service.UserGroupService;
import com.kingleadsw.betterlive.vo.CouponInfoVo;
import com.kingleadsw.betterlive.vo.OrderProductVo;
import com.kingleadsw.betterlive.vo.OrderVo;


/**
 * 订单 实际交互 实现层
 * 2017-03-10 by chen
 */
@Component
@Transactional
public class OrderManagerImpl extends BaseManagerImpl<OrderVo,Order> implements OrderManager {
	
	private Logger logger = Logger.getLogger(OrderManagerImpl.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private UseLockService useLockService;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private SysGroupService sysGroupService;
	@Autowired
	private GroupJoinService groupJoinService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSpecService productSpecService;
	@Autowired
	private ActivityProductService activityProductService;
	@Autowired
	private PreProductService preProductService;
	@Autowired
	private ReceiverAddressService receiverAddressService;
	@Autowired
	private FreightManager freightManager;
	@Autowired
	private CouponInfoService couponInfoService;
	@Autowired
	private GiftCardService giftCardService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private PayLogService payLogService;
	@Autowired
	private GiftCardRecordService giftCardRecordService;
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private SysDictService sysDictService;
	
	
	 //添加订单
	@Override
	public int insertOrder(OrderVo order) {
		Order orders=vo2poer.transfer(new Order(),order);
		orders.setStatus(1);       //待支付
		int ret=orderService.insertOrder(orders);
		order.setOrder_id(orders.getOrder_id());
		return ret;
	}

	//更新订单
	@Override
	public int editOder(PageData pd) {
		
		return orderService.editOder(pd);
	}
	
	//根据条件查询订单
		@Override
		public OrderVo findOrder3(PageData pd) {
			Order order=orderService.findOrder(pd);
			
			if(null == order){
				return null;
			}
			
			OrderVo orderVo=po2voer.transfer(new OrderVo(),order);
			if(CollectionUtils.isNotEmpty(order.getListOrderProduct())){
				List<OrderProductVo> listOrderProductVo=new ArrayList<OrderProductVo>(order.getListOrderProduct().size());
				OrderProductVo orderProductVo=null;
				for (OrderProduct orderProduct: order.getListOrderProduct() ) {
						orderProductVo=new OrderProductVo();
						
						orderProductVo.setOrderpro_id(orderProduct.getOrderpro_id());
						orderProductVo.setOrder_id(orderProduct.getOrder_id());
						orderProductVo.setProduct_id(orderProduct.getProduct_id());
						orderProductVo.setProduct_name(orderProduct.getProduct_name());
						orderProductVo.setSpec_id(orderProduct.getSpec_id());
						orderProductVo.setSpec_img(orderProduct.getSpec_img());
						orderProductVo.setSpec_name(orderProduct.getSpec_name());
						orderProductVo.setQuantity(orderProduct.getQuantity());
						orderProductVo.setPrice(orderProduct.getPrice());
						orderProductVo.setIs_evaluate(orderProduct.getIs_evaluate());
						orderProductVo.setActivity_price(orderProduct.getActivity_price());
						orderProductVo.setProduct_status(orderProduct.getProduct_status());
						if(orderProduct.getStatus()==null){
							orderProductVo.setStatus(order.getStatus());
						}else{
							orderProductVo.setStatus(orderProduct.getStatus());
						}
						
						
						orderProductVo.setLogistics_code(orderProduct.getLogistics_code());
						orderProductVo.setCompany_code(orderProduct.getCompany_code());
						orderProductVo.setSub_order_code(orderProduct.getSub_order_code());
						orderProductVo.setSend_time(orderProduct.getSend_time());
						
						listOrderProductVo.add(orderProductVo);
					
				}
				orderVo.setListOrderProductVo(listOrderProductVo);
			}
			
			//判断  未支付订单（1） 是否超过支付有效期
			if(orderVo.getStatus() == 1 && StringUtil.isNoNull(pd.getString("expiretime"))){
				 String expiretime = pd.getString("expiretime");
				 int expireMinutes = 0;  //过期分钟数
				 if (StringUtil.isNumber(expiretime)) {
					 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
				 }
				 Date orderTime = DateUtil.stringToDate(orderVo.getOrder_time());  
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(orderTime);
				 calendar.add(Calendar.MINUTE, expireMinutes);
				
				 Date current = new Date();
				 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
				 if(current.after(calendar.getTime())){      //超过有效期
					 orderVo.setIsExpire(1);
				 }else{
					Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
					String time = "";
					
					long hours=l/1000/3600;
					long myushu=l/1000%3600;
					long minites=myushu/60;
					long syushu=myushu%60;
					
					if(hours>0){
						if(String.valueOf(hours).length()>1){
							time=hours+":";
						}else{
							time="0"+hours+":";
						}
					}else{
						time="00:";
					}
					
					if(minites>0){
						if(String.valueOf(minites).length()>1){
							time+=minites+":";
						}else{
							time+="0"+minites+":";
						}
					}else{
						time+="00:";
					}
					
					if(syushu>0){
						if(String.valueOf(syushu).length()>1){
							time+=syushu;
						}else{
							time+="0"+syushu;
						}
					}else{
						time+="00";
					}
					orderVo.setSurplusTiem(time);
				}
			}
			return orderVo ;
		}
		
		
		
		//根据条件查询订单
		@Override
		public OrderVo findOrder4(PageData pd) {
			Order order=orderService.findOrder(pd);
			
			if(null == order){
				return null;
			}
			
			OrderVo orderVo=po2voer.transfer(new OrderVo(),order);
			if(CollectionUtils.isNotEmpty(order.getListOrderProduct())){
				List<OrderProductVo> listOrderProductVo=new ArrayList<OrderProductVo>(order.getListOrderProduct().size());
				OrderProductVo orderProductVo=null;
				Integer orderpro_id=pd.getInteger("orderpro_id");
				for (OrderProduct orderProduct: order.getListOrderProduct() ) {
					if(orderpro_id!=null && (orderpro_id==orderProduct.getOrderpro_id()||orderpro_id.equals(orderProduct.getOrderpro_id()))){
						orderProductVo=new OrderProductVo();
						
						orderProductVo.setOrderpro_id(orderProduct.getOrderpro_id());
						orderProductVo.setOrder_id(orderProduct.getOrder_id());
						orderProductVo.setProduct_id(orderProduct.getProduct_id());
						orderProductVo.setProduct_name(orderProduct.getProduct_name());
						orderProductVo.setSpec_id(orderProduct.getSpec_id());
						orderProductVo.setSpec_img(orderProduct.getSpec_img());
						orderProductVo.setSpec_name(orderProduct.getSpec_name());
						orderProductVo.setQuantity(orderProduct.getQuantity());
						orderProductVo.setPrice(orderProduct.getPrice());
						orderProductVo.setIs_evaluate(orderProduct.getIs_evaluate());
						orderProductVo.setActivity_price(orderProduct.getActivity_price());
						orderProductVo.setProduct_status(orderProduct.getProduct_status());
						if(orderProduct.getStatus()==null){
							orderProductVo.setStatus(order.getStatus());
						}else{
							orderProductVo.setStatus(orderProduct.getStatus());
						}
						
						
						orderProductVo.setLogistics_code(orderProduct.getLogistics_code());
						orderProductVo.setCompany_code(orderProduct.getCompany_code());
						orderProductVo.setSub_order_code(orderProduct.getSub_order_code());
						orderProductVo.setSend_time(orderProduct.getSend_time());
						
						listOrderProductVo.add(orderProductVo);
					}
					
				}
				orderVo.setListOrderProductVo(listOrderProductVo);
			}
			
			//判断  未支付订单（1） 是否超过支付有效期
			if(orderVo.getStatus() == 1 && StringUtil.isNoNull(pd.getString("expiretime"))){
				 String expiretime = pd.getString("expiretime");
				 int expireMinutes = 0;  //过期分钟数
				 if (StringUtil.isNumber(expiretime)) {
					 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
				 }
				 Date orderTime = DateUtil.stringToDate(orderVo.getOrder_time());  
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(orderTime);
				 calendar.add(Calendar.MINUTE, expireMinutes);
				
				 Date current = new Date();
				 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
				 if(current.after(calendar.getTime())){      //超过有效期
					 orderVo.setIsExpire(1);
				 }else{
					Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
					String time = "";
					
					long hours=l/1000/3600;
					long myushu=l/1000%3600;
					long minites=myushu/60;
					long syushu=myushu%60;
					
					if(hours>0){
						if(String.valueOf(hours).length()>1){
							time=hours+":";
						}else{
							time="0"+hours+":";
						}
					}else{
						time="00:";
					}
					
					if(minites>0){
						if(String.valueOf(minites).length()>1){
							time+=minites+":";
						}else{
							time+="0"+minites+":";
						}
					}else{
						time+="00:";
					}
					
					if(syushu>0){
						if(String.valueOf(syushu).length()>1){
							time+=syushu;
						}else{
							time+="0"+syushu;
						}
					}else{
						time+="00";
					}
					orderVo.setSurplusTiem(time);
				}
			}
			return orderVo ;
		}
	
	//根据条件查询订单
	@Override
	public OrderVo findOrder(PageData pd) {
		Order order=orderService.findOrder(pd);
		
		if(null == order){
			return null;
		}
		
		OrderVo orderVo=po2voer.transfer(new OrderVo(),order);
		if(StringUtil.isNull(orderVo.getFreePrice())){
			orderVo.setFreePrice("0");
		}
		if(CollectionUtils.isNotEmpty(order.getListOrderProduct())){
			List<OrderProductVo> listOrderProductVo=new ArrayList<OrderProductVo>(order.getListOrderProduct().size());
			OrderProductVo orderProductVo=null;
			
			BigDecimal ordTotalPrice = BigDecimal.ZERO;
			BigDecimal ordTotalPay = BigDecimal.ZERO;
			BigDecimal ordTotalDis = BigDecimal.ZERO;
			BigDecimal ordTotalGift = BigDecimal.ZERO;
			for (OrderProduct orderProduct: order.getListOrderProduct() ) {
					orderProductVo=new OrderProductVo();
					
					orderProductVo.setOrderpro_id(orderProduct.getOrderpro_id());
					orderProductVo.setOrder_id(orderProduct.getOrder_id());
					orderProductVo.setProduct_id(orderProduct.getProduct_id());
					orderProductVo.setProduct_name(orderProduct.getProduct_name());
					orderProductVo.setSpec_id(orderProduct.getSpec_id());
					orderProductVo.setSpec_img(orderProduct.getSpec_img());
					orderProductVo.setSpec_name(orderProduct.getSpec_name());
					orderProductVo.setQuantity(orderProduct.getQuantity());
					orderProductVo.setPrice(orderProduct.getPrice());
					orderProductVo.setIs_evaluate(orderProduct.getIs_evaluate());
					orderProductVo.setActivity_price(orderProduct.getActivity_price());
					orderProductVo.setProduct_status(orderProduct.getProduct_status());
					if(StringUtil.isNotNull(orderProduct.getDiscount_price())){
						orderProductVo.setDiscount_price(orderProduct.getDiscount_price());
					}
					if(orderProduct.getStatus()==null){
						orderProductVo.setStatus(order.getStatus());
					}else{
						orderProductVo.setStatus(orderProduct.getStatus());
					}
					
					
					orderProductVo.setLogistics_code(orderProduct.getLogistics_code());
					orderProductVo.setCompany_code(orderProduct.getCompany_code());
					orderProductVo.setSub_order_code(orderProduct.getSub_order_code());
					orderProductVo.setSend_time(orderProduct.getSend_time());
					
					BigDecimal totalPrice = new BigDecimal(orderProduct.getPrice()).multiply(new BigDecimal(orderProduct.getQuantity()));
					orderProductVo.setTotalPrice(totalPrice+"");
					BigDecimal totalReal = totalPrice;
					BigDecimal totalDiscount = BigDecimal.ZERO;
					BigDecimal giftCardMoney = BigDecimal.ZERO;
					if(StringUtil.isNotNull(orderProduct.getActivity_price()) && new BigDecimal(orderProduct.getActivity_price()).compareTo(BigDecimal.ZERO)==1){
						totalReal = new BigDecimal(orderProduct.getActivity_price()).multiply(new BigDecimal(orderProduct.getQuantity()));
					}else if(StringUtil.isNotNull(orderProduct.getDiscount_price())  && new BigDecimal(orderProduct.getDiscount_price()).compareTo(BigDecimal.ZERO)==1){
						totalReal = new BigDecimal(orderProduct.getDiscount_price()).multiply(new BigDecimal(orderProduct.getQuantity()));
					}
					if(orderProduct.getCut_money() != null && orderProduct.getCut_money().compareTo(BigDecimal.ZERO)==1){
						totalReal = totalReal.subtract(orderProduct.getCut_money());
						orderProductVo.setFull_money(orderProduct.getCut_money());
					}
					if(orderProduct.getCoupon_money() != null && orderProduct.getCoupon_money().compareTo(BigDecimal.ZERO)==1){
						totalReal = totalReal.subtract(orderProduct.getCoupon_money());
					}
					BigDecimal addAmount = BigDecimal.ZERO;
					if(StringUtil.isNotNull(orderVo.getConpon_money()) && new BigDecimal(orderVo.getConpon_money()).compareTo(BigDecimal.ZERO)==1){
						addAmount = addAmount.add(new BigDecimal(orderVo.getConpon_money()));
					}
					if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
						addAmount = addAmount.add(new BigDecimal(order.getGitf_card_money()));
						
					}
					if(new BigDecimal(orderVo.getPay_money()).compareTo(BigDecimal.ZERO) == 1){
						BigDecimal yhje = addAmount.divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
						yhje = yhje.multiply(new BigDecimal(orderProductVo.getQuantity()));
						if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
							BigDecimal tempG = new BigDecimal(orderVo.getGitf_card_money()).divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
							tempG = tempG.multiply(new BigDecimal(orderProductVo.getQuantity()));
							giftCardMoney = tempG;
						}
						
						totalReal = totalReal.subtract(yhje);
						
					}else{
						BigDecimal tempPay = addAmount.subtract(totalReal);
						if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
							BigDecimal tempG = new BigDecimal(orderVo.getGitf_card_money()).divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
							tempG = tempG.multiply(new BigDecimal(orderProductVo.getQuantity()));
							giftCardMoney = tempG;
						}
						if(tempPay.compareTo(BigDecimal.ZERO) == 0 || tempPay.compareTo(BigDecimal.ZERO) == 1){
							totalReal = BigDecimal.ZERO;
						}
					}
				
					if(totalReal.compareTo(BigDecimal.ZERO) == -1){
						totalReal = BigDecimal.ZERO;
					}
					BigDecimal freight =  BigDecimal.ZERO;
					if(new BigDecimal(orderVo.getFreight()).compareTo(BigDecimal.ZERO) == 1){
						freight = new BigDecimal(orderVo.getFreight());
						freight = freight.divide(new BigDecimal(orderVo.getQuantity_detail()), BigDecimal.ROUND_HALF_EVEN);
					}
					if (orderVo.getStatus() == 1 || orderVo.getStatus() == 2 || orderVo.getStatus() == 6) {
						totalDiscount=totalPrice.subtract(totalReal).subtract(giftCardMoney);
					}else{
						totalDiscount=totalPrice.add(freight).subtract(totalReal).subtract(giftCardMoney);
					}
					
					orderProductVo.setTotalPrice(totalPrice+"");
					orderProductVo.setGiftCardMoney(giftCardMoney+"");
					orderProductVo.setTotalPay(totalReal+"");
					listOrderProductVo.add(orderProductVo);
					ordTotalPrice = ordTotalPrice.add(totalPrice);
					if ((orderVo.getStatus() == 1 || orderVo.getStatus() == 2 || orderVo.getStatus() == 6) && totalReal.compareTo(BigDecimal.ZERO) == 1) {
						ordTotalPay = ordTotalPay.add(totalReal).add(freight);
					}else{
						ordTotalPay = ordTotalPay.add(totalReal);
					}
					ordTotalGift = ordTotalGift.add(giftCardMoney);
					ordTotalDis = ordTotalDis.add(totalDiscount);
				
			}
			String ordtt = ordTotalPay.toString();
			if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
				ordtt = ordtt.substring(0, ordtt.length()-1);
				if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()).equals(".")){
					ordtt = ordtt.substring(0, ordtt.length()-1);
				}
			}
			if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
				ordtt = ordtt.substring(0, ordtt.length()-1);
				if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals(".")){
					ordtt = ordtt.substring(0, ordtt.length()-1);
				}
			}
			if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
				ordtt = ordtt.substring(0, ordtt.length()-1);
				if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals(".")){
					ordtt = ordtt.substring(0, ordtt.length()-1);
				}
			}
			orderVo.setTotal_price(ordTotalPrice+"");
			orderVo.setPay_money(ordtt);
			orderVo.setGitf_card_money(ordTotalGift+"");
			orderVo.setFreePrice(ordTotalDis+"");
			orderVo.setListOrderProductVo(listOrderProductVo);
		}
		
		//判断  未支付订单（1） 是否超过支付有效期
		if(orderVo.getStatus() == 1 && StringUtil.isNoNull(pd.getString("expiretime"))){
			 String expiretime = pd.getString("expiretime");
			 int expireMinutes = 0;  //过期分钟数
			 if (StringUtil.isNumber(expiretime)) {
				 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
			 }
			 Date orderTime = DateUtil.stringToDate(orderVo.getOrder_time());  
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(orderTime);
			 calendar.add(Calendar.MINUTE, expireMinutes);
			
			 Date current = new Date();
			 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
			 if(current.after(calendar.getTime())){      //超过有效期
				 orderVo.setIsExpire(1);
			 }else{
				Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
				String time = "";
				
				long hours=l/1000/3600;
				long myushu=l/1000%3600;
				long minites=myushu/60;
				long syushu=myushu%60;
				
				if(hours>0){
					if(String.valueOf(hours).length()>1){
						time=hours+":";
					}else{
						time="0"+hours+":";
					}
				}else{
					time="00:";
				}
				
				if(minites>0){
					if(String.valueOf(minites).length()>1){
						time+=minites+":";
					}else{
						time+="0"+minites+":";
					}
				}else{
					time+="00:";
				}
				
				if(syushu>0){
					if(String.valueOf(syushu).length()>1){
						time+=syushu;
					}else{
						time+="0"+syushu;
					}
				}else{
					time+="00";
				}
				orderVo.setSurplusTiem(time);
			}
		}
		return orderVo ;
	}
	
	
	
	//根据条件查询订单
	@Override
	public OrderVo findOrder2(PageData pd) {
		Order order=orderService.findOrder(pd);
		
		if(null == order){
			return null;
		}
		
		OrderVo orderVo=po2voer.transfer(new OrderVo(),order);
		if(StringUtil.isNull(orderVo.getFreePrice())){
			orderVo.setFreePrice("0");
		}
		if(CollectionUtils.isNotEmpty(order.getListOrderProduct())){
			List<OrderProductVo> listOrderProductVo=new ArrayList<OrderProductVo>(order.getListOrderProduct().size());
			OrderProductVo orderProductVo=null;
			Integer orderpro_id=pd.getInteger("orderpro_id");
			BigDecimal ordTotalPrice = BigDecimal.ZERO;
			BigDecimal ordTotalPay = BigDecimal.ZERO;
			BigDecimal ordTotalDis = BigDecimal.ZERO;
			BigDecimal ordTotalGift = BigDecimal.ZERO;
			for (OrderProduct orderProduct: order.getListOrderProduct() ) {
				if(orderpro_id!=null && (orderpro_id==orderProduct.getOrderpro_id()||orderpro_id.equals(orderProduct.getOrderpro_id()))){
					orderProductVo=new OrderProductVo();
					
					orderProductVo.setOrderpro_id(orderProduct.getOrderpro_id());
					orderProductVo.setOrder_id(orderProduct.getOrder_id());
					orderProductVo.setProduct_id(orderProduct.getProduct_id());
					orderProductVo.setProduct_name(orderProduct.getProduct_name());
					orderProductVo.setSpec_id(orderProduct.getSpec_id());
					orderProductVo.setSpec_img(orderProduct.getSpec_img());
					orderProductVo.setSpec_name(orderProduct.getSpec_name());
					orderProductVo.setQuantity(orderProduct.getQuantity());
					orderProductVo.setPrice(orderProduct.getPrice());
					orderProductVo.setIs_evaluate(orderProduct.getIs_evaluate());
					orderProductVo.setActivity_price(orderProduct.getActivity_price());
					orderProductVo.setProduct_status(orderProduct.getProduct_status());
					if(StringUtil.isNotNull(orderProduct.getDiscount_price())){
						orderProductVo.setDiscount_price(orderProduct.getDiscount_price());
					}
					if(orderProduct.getStatus()==null){
						orderProductVo.setStatus(order.getStatus());
					}else{
						orderProductVo.setStatus(orderProduct.getStatus());
					}
					
					
					orderProductVo.setLogistics_code(orderProduct.getLogistics_code());
					orderProductVo.setCompany_code(orderProduct.getCompany_code());
					orderProductVo.setSub_order_code(orderProduct.getSub_order_code());
					orderProductVo.setSend_time(orderProduct.getSend_time());
					
					BigDecimal totalPrice = new BigDecimal(orderProduct.getPrice()).multiply(new BigDecimal(orderProduct.getQuantity()));
					BigDecimal totalReal = totalPrice;
					BigDecimal totalDiscount = BigDecimal.ZERO;
					BigDecimal giftCardMoney = BigDecimal.ZERO;
					if(StringUtil.isNotNull(orderProduct.getActivity_price()) && new BigDecimal(orderProduct.getActivity_price()).compareTo(BigDecimal.ZERO)==1){
						totalReal = new BigDecimal(orderProduct.getActivity_price()).multiply(new BigDecimal(orderProduct.getQuantity()));
					}else if(StringUtil.isNotNull(orderProduct.getDiscount_price())  && new BigDecimal(orderProduct.getDiscount_price()).compareTo(BigDecimal.ZERO)==1){
						totalReal = new BigDecimal(orderProduct.getDiscount_price()).multiply(new BigDecimal(orderProduct.getQuantity()));
					}
					if(orderProduct.getCut_money() != null && orderProduct.getCut_money().compareTo(BigDecimal.ZERO)==1){
						totalReal = totalReal.subtract(orderProduct.getCut_money());
						orderProductVo.setFull_money(orderProduct.getCut_money());
					}
					if(orderProduct.getCoupon_money() != null && orderProduct.getCoupon_money().compareTo(BigDecimal.ZERO)==1){
						totalReal = totalReal.subtract(orderProduct.getCoupon_money());
					}
					BigDecimal addAmount = BigDecimal.ZERO;
					if(StringUtil.isNotNull(orderVo.getConpon_money()) && new BigDecimal(orderVo.getConpon_money()).compareTo(BigDecimal.ZERO)==1){
						addAmount = addAmount.add(new BigDecimal(orderVo.getConpon_money()));
					}
					if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
						addAmount = addAmount.add(new BigDecimal(order.getGitf_card_money()));
						
					}
					if(new BigDecimal(orderVo.getPay_money()).compareTo(BigDecimal.ZERO) == 1){
						BigDecimal yhje = addAmount.divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
						yhje = yhje.multiply(new BigDecimal(orderProductVo.getQuantity()));
						if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
							BigDecimal tempG = new BigDecimal(orderVo.getGitf_card_money()).divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
							tempG = tempG.multiply(new BigDecimal(orderProductVo.getQuantity()));
							giftCardMoney = tempG;
						}
						
						totalReal = totalReal.subtract(yhje);
						
					}else{
						BigDecimal tempPay = addAmount.subtract(totalReal);
						if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
							BigDecimal tempG = new BigDecimal(orderVo.getGitf_card_money()).divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
							tempG = tempG.multiply(new BigDecimal(orderProductVo.getQuantity()));
							giftCardMoney = tempG;
						}
						if(tempPay.compareTo(BigDecimal.ZERO) == 0 || tempPay.compareTo(BigDecimal.ZERO) == 1){
							totalReal = BigDecimal.ZERO;
						}
					}
					if(totalReal.compareTo(BigDecimal.ZERO) == -1){
						totalReal = BigDecimal.ZERO;
					}
					
					BigDecimal freight =  BigDecimal.ZERO;
					if(new BigDecimal(orderVo.getFreight()).compareTo(BigDecimal.ZERO) == 1){
						freight = new BigDecimal(orderVo.getFreight());
						freight = freight.divide(new BigDecimal(orderVo.getQuantity_detail()), BigDecimal.ROUND_HALF_EVEN);
					}
					if (orderVo.getStatus() == 1 || orderVo.getStatus() == 2 || orderVo.getStatus() == 6) {
						totalDiscount=totalPrice.subtract(totalReal).subtract(giftCardMoney);
					}else{
						totalDiscount=totalPrice.add(freight).subtract(totalReal).subtract(giftCardMoney);
					}
					orderProductVo.setTotalPrice(totalPrice+"");
					orderProductVo.setGiftCardMoney(giftCardMoney+"");
					orderProductVo.setTotalPay(totalReal+"");
					listOrderProductVo.add(orderProductVo);
					orderVo.setFreight(freight.toString());
					ordTotalPrice = ordTotalPrice.add(totalPrice);
					if ((orderVo.getStatus() == 1 || orderVo.getStatus() == 2 || orderVo.getStatus() == 6) && totalReal.compareTo(BigDecimal.ZERO) == 1) {
						ordTotalPay = ordTotalPay.add(totalReal).add(freight);
					}else{
						ordTotalPay = ordTotalPay.add(totalReal);
					}
					ordTotalGift = ordTotalGift.add(giftCardMoney);
					ordTotalDis = ordTotalDis.add(totalDiscount);
				}
				
			}
			
			String ordtt = ordTotalPay.toString();
			if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
				ordtt = ordtt.substring(0, ordtt.length()-1);
				if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()).equals(".")){
					ordtt = ordtt.substring(0, ordtt.length()-1);
				}
			}
			if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
				ordtt = ordtt.substring(0, ordtt.length()-1);
				if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals(".")){
					ordtt = ordtt.substring(0, ordtt.length()-1);
				}
			}
			if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
				ordtt = ordtt.substring(0, ordtt.length()-1);
				if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals(".")){
					ordtt = ordtt.substring(0, ordtt.length()-1);
				}
			}
			orderVo.setTotal_price(ordTotalPrice+"");
			orderVo.setPay_money(ordtt);
			orderVo.setGitf_card_money(ordTotalGift+"");
			orderVo.setFreePrice(ordTotalDis+"");
			orderVo.setListOrderProductVo(listOrderProductVo);
		}
		
		//判断  未支付订单（1） 是否超过支付有效期
		if(orderVo.getStatus() == 1 && StringUtil.isNoNull(pd.getString("expiretime"))){
			 String expiretime = pd.getString("expiretime");
			 int expireMinutes = 0;  //过期分钟数
			 if (StringUtil.isNumber(expiretime)) {
				 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
			 }
			 Date orderTime = DateUtil.stringToDate(orderVo.getOrder_time());  
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(orderTime);
			 calendar.add(Calendar.MINUTE, expireMinutes);
			
			 Date current = new Date();
			 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
			 if(current.after(calendar.getTime())){      //超过有效期
				 orderVo.setIsExpire(1);
			 }else{
				Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
				String time = "";
				
				long hours=l/1000/3600;
				long myushu=l/1000%3600;
				long minites=myushu/60;
				long syushu=myushu%60;
				
				if(hours>0){
					if(String.valueOf(hours).length()>1){
						time=hours+":";
					}else{
						time="0"+hours+":";
					}
				}else{
					time="00:";
				}
				
				if(minites>0){
					if(String.valueOf(minites).length()>1){
						time+=minites+":";
					}else{
						time+="0"+minites+":";
					}
				}else{
					time+="00:";
				}
				
				if(syushu>0){
					if(String.valueOf(syushu).length()>1){
						time+=syushu;
					}else{
						time+="0"+syushu;
					}
				}else{
					time+="00";
				}
				orderVo.setSurplusTiem(time);
			}
		}
		return orderVo ;
	}
	

	//根据条件分页查询订单
	@Override
	public List<OrderVo> findAllorderListPage(PageData pd) {
		
		List<Order> listOrder=orderService.findAllorderListPage(pd);
		List<OrderVo> listOrderVo = null;
		
		if(null !=listOrder && listOrder.size()>0){
			listOrderVo = new ArrayList<OrderVo>();
			//List<OrderProductVo> listOrderProductVo = generator.transfer(OrderProductVo.class, listOrder);
			for (Order order: listOrder) {
				OrderVo orderVo=po2voer.transfer(new OrderVo(),order);
				List<OrderProductVo> listOrderProductVo = generator.transfer(OrderProductVo.class,order.getListOrderProduct()) ;
				if(listOrderProductVo != null && listOrderProductVo.size() > 0){
					orderVo.setListOrderProductVo(listOrderProductVo);
				}
				
				 //判断  未支付订单（1） 是否超过支付有效期
				 if(orderVo.getStatus() == 1 && StringUtil.isNoNull(pd.getString("expiretime"))){
					 String expiretime = pd.getString("expiretime");
					 int expireMinutes = 0;  //过期分钟数
					 if (StringUtil.isNumber(expiretime)) {
						 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
					 }
					 Date orderTime = DateUtil.stringToDate(orderVo.getOrder_time());  
					 Calendar calendar = Calendar.getInstance();
					 calendar.setTime(orderTime);
					 calendar.add(Calendar.MINUTE, expireMinutes);
					
					 Date current = new Date();
					 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
					 if(current.after(calendar.getTime())){      //超过有效期
						 orderVo.setIsExpire(1);
					 }else{
						Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
						String time = "";
						
						long hours=l/1000/3600;
						long myushu=l/1000%3600;
						long minites=myushu/60;
						long syushu=myushu%60;
						
						if(hours>0){
							if(String.valueOf(hours).length()>1){
								time=hours+":";
							}else{
								time="0"+hours+":";
							}
						}else{
							time="00:";
						}
						
						if(minites>0){
							if(String.valueOf(minites).length()>1){
								time+=minites+":";
							}else{
								time+="0"+minites+":";
							}
						}else{
							time+="00:";
						}
						
						if(syushu>0){
							if(String.valueOf(syushu).length()>1){
								time+=syushu;
							}else{
								time+="0"+syushu;
							}
						}else{
							time+="00";
						}
						orderVo.setSurplusTiem(time);
					}
				}
					
				listOrderVo.add(orderVo);
			}
		}
		return listOrderVo;
	}
	
	//根据条件分页查询订单
		@Override
		public List<OrderVo> findAllorder3ListPage(PageData pd) {
			
			List<Order> listOrder=orderService.findAllorder2ListPage(pd);
			List<OrderVo> listOrderVo = null;
			//List<OrderVo> newListOrderVo=new ArrayList<OrderVo>();
			Map<Integer,ArrayList<OrderProductVo>> map=new HashMap<Integer,ArrayList<OrderProductVo>>();
			
			
			if(null !=listOrder && listOrder.size()>0){
				listOrderVo = new ArrayList<OrderVo>();
				//List<OrderProductVo> listOrderProductVo = generator.transfer(OrderProductVo.class, listOrder);
				for (Order order: listOrder) {
					OrderVo orderVo=po2voer.transfer(new OrderVo(),order);
					//List<OrderProductVo> listOrderProductVo = generator.transfer(OrderProductVo.class,order.getListOrderProduct()) ;
					//orderVo.setListOrderProductVo(listOrderProductVo);
					
					//对于待支付订单把所购买的商品合并在一个订单中
					if(orderVo.getStatus()==1&&orderVo.getSub_status()==1){
						int orderId=orderVo.getOrder_id();
						ArrayList<OrderProductVo> orderList=null;
						if(map.containsKey(orderId)){
							orderList=map.get(orderId);
						}else{
							orderList=new ArrayList<OrderProductVo>();
						}
						OrderProductVo orderProductVo=new OrderProductVo();
						orderProductVo.setOrderpro_id(orderVo.getOrderpro_id());
						orderProductVo.setSpec_img(orderVo.getSpec_img());
						orderProductVo.setSpec_name(orderVo.getSpec_name());
						orderProductVo.setProduct_status(orderVo.getProduct_status());
						
						orderList.add(orderProductVo);
						map.put(orderId, orderList);
					}
					
					
					
					
					 //判断  未支付订单（1） 是否超过支付有效期
					 if(orderVo.getSub_status() == 1 && StringUtil.isNoNull(pd.getString("expiretime"))){
						 String expiretime = pd.getString("expiretime");
						 int expireMinutes = 0;  //过期分钟数
						 if (StringUtil.isNumber(expiretime)) {
							 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
						 }
						 Date orderTime = DateUtil.stringToDate(orderVo.getOrder_time());  
						 Calendar calendar = Calendar.getInstance();
						 calendar.setTime(orderTime);
						 calendar.add(Calendar.MINUTE, expireMinutes);
						
						 Date current = new Date();
						 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
						 if(current.after(calendar.getTime())){      //超过有效期
							 orderVo.setIsExpire(1);
						 }else{
							Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
							String time = "";
							
							long hours=l/1000/3600;
							long myushu=l/1000%3600;
							long minites=myushu/60;
							long syushu=myushu%60;
							
							if(hours>0){
								if(String.valueOf(hours).length()>1){
									time=hours+":";
								}else{
									time="0"+hours+":";
								}
							}else{
								time="00:";
							}
							
							if(minites>0){
								if(String.valueOf(minites).length()>1){
									time+=minites+":";
								}else{
									time+="0"+minites+":";
								}
							}else{
								time+="00:";
							}
							
							if(syushu>0){
								if(String.valueOf(syushu).length()>1){
									time+=syushu;
								}else{
									time+="0"+syushu;
								}
							}else{
								time+="00";
							}
							orderVo.setSurplusTiem(time);
						}
					}
					 //查询待支付订单且过期订单不在待支付选项卡显示
					if(!(orderVo.getIsExpire()==1 && pd.getString("status").equals("1"))){
						listOrderVo.add(orderVo);
					}
					
				}
			}
			List<OrderVo> listOrderVo2 = new ArrayList<OrderVo>();
			List<Integer>ids=new ArrayList<Integer>();
			if(listOrderVo!=null&&listOrderVo.size()>0){
				for(OrderVo vo:listOrderVo){
					if(vo.getSub_status()==1){
						if(!ids.contains(vo.getOrder_id())){
							vo.setListOrderProductVo(map.get(vo.getOrder_id()));
							ids.add(vo.getOrder_id());
							listOrderVo2.add(vo);
						}
					}else{
						vo.setListOrderProductVo(map.get(vo.getOrder_id()));
						ids.add(vo.getOrder_id());
						listOrderVo2.add(vo);
					}
						
				}
			}
			
			
			return listOrderVo2;
		}
	
	//根据条件分页查询订单
	@Override
	public List<OrderVo> findAllorder2ListPage(PageData pd) {
		
		List<Order> listOrder=orderService.findAllorder2ListPage(pd);
		List<OrderVo> listOrderVo = null;
		//List<OrderVo> newListOrderVo=new ArrayList<OrderVo>();
		Map<Integer,ArrayList<OrderProductVo>> map=new HashMap<Integer,ArrayList<OrderProductVo>>();
		
		
		if(null !=listOrder && listOrder.size()>0){
			listOrderVo = new ArrayList<OrderVo>();
			//List<OrderProductVo> listOrderProductVo = generator.transfer(OrderProductVo.class, listOrder);
			for (Order order: listOrder) {
				OrderVo orderVo=po2voer.transfer(new OrderVo(),order);
				try {
					if(orderVo.getGroupJoinId() > 0){
						int specialStatus = 0;
						long crrt = System.currentTimeMillis();
						PageData spdt = new PageData();
						spdt.put("groupJoinId", orderVo.getGroupJoinId());
						GroupJoin grouJoinVo = this.groupJoinService.queryOne(spdt);
						spdt.clear();
						spdt.put("userGroupId", grouJoinVo.getUserGroupId());
						UserGroup userGroup = this.userGroupService.queryOne(spdt);
						spdt.clear();
						spdt.put("specialId", grouJoinVo.getSpecialId());
						spdt.put("groupId", grouJoinVo.getGroupId());
						SysGroup sysGroup = this.sysGroupService.queryOne(spdt);
						
						Special special = this.specialService.queryOne(spdt);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						long endTime = sdf.parse(special.getEndTime()).getTime();
						if(endTime <= crrt || special.getStatus().intValue() != 1){
							specialStatus = 1; //活动结束
						}else if(userGroup.getCustNum() < sysGroup.getLimitCopy() && userGroup.getStatus() == 1){
							specialStatus = 2; //此团正在进行中
						}
						orderVo.setSpecialStatus(specialStatus);
					}
				} catch (ParseException e) {
					logger.error("findAllorder2ListPage ---error", e);
				}
				
				
				//List<OrderProductVo> listOrderProductVo = generator.transfer(OrderProductVo.class,order.getListOrderProduct()) ;
				//orderVo.setListOrderProductVo(listOrderProductVo);
				if(orderVo.getStatus().intValue() != 1){
					BigDecimal totalPrice = new BigDecimal(orderVo.getPrice()).multiply(new BigDecimal(orderVo.getQuantity()));
					BigDecimal totalReal = totalPrice;
					BigDecimal totalDiscount = BigDecimal.ZERO;
					BigDecimal giftCardMoney = BigDecimal.ZERO;
					if(StringUtil.isNotNull(orderVo.getActivity_price()) && new BigDecimal(orderVo.getActivity_price()).compareTo(BigDecimal.ZERO)==1){
						totalReal = new BigDecimal(orderVo.getActivity_price()).multiply(new BigDecimal(orderVo.getQuantity()));
					}else if(StringUtil.isNotNull(orderVo.getDiscount_price())  && new BigDecimal(orderVo.getDiscount_price()).compareTo(BigDecimal.ZERO)==1){
						totalReal = new BigDecimal(orderVo.getDiscount_price()).multiply(new BigDecimal(orderVo.getQuantity()));
					}
					if(orderVo.getCut_money() != null && orderVo.getCut_money().compareTo(BigDecimal.ZERO)==1){
						totalReal = totalReal.subtract(orderVo.getCut_money());
					}
					if(orderVo.getCoupon_money() != null && orderVo.getCoupon_money().compareTo(BigDecimal.ZERO)==1){
						totalReal = totalReal.subtract(orderVo.getCoupon_money());
					}
					BigDecimal addAmount = BigDecimal.ZERO;
					if(StringUtil.isNotNull(orderVo.getConpon_money()) && new BigDecimal(orderVo.getConpon_money()).compareTo(BigDecimal.ZERO)==1){
						addAmount = addAmount.add(new BigDecimal(orderVo.getConpon_money()));
					}
					if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
						addAmount = addAmount.add(new BigDecimal(order.getGitf_card_money()));
						
					}
					if(new BigDecimal(orderVo.getPay_money()).compareTo(BigDecimal.ZERO) == 1){
						BigDecimal yhje = addAmount.divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
						yhje = yhje.multiply(new BigDecimal(orderVo.getQuantity()));
						
						if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
							BigDecimal tempG = new BigDecimal(orderVo.getGitf_card_money()).divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
							tempG = tempG.multiply(new BigDecimal(orderVo.getQuantity()));
							giftCardMoney = tempG;
						}
						
						totalReal = totalReal.subtract(yhje);
						
					}else{
						BigDecimal tempPay = addAmount.subtract(totalReal);
						if(order.getGitf_card_money() != null && new BigDecimal(orderVo.getGitf_card_money()).compareTo(BigDecimal.ZERO) == 1){
							BigDecimal tempG = new BigDecimal(orderVo.getGitf_card_money()).divide(new BigDecimal(orderVo.getQuantityTotal()), BigDecimal.ROUND_UP);
							tempG = tempG.multiply(new BigDecimal(orderVo.getQuantity()));
							giftCardMoney = tempG;
						}
						if(tempPay.compareTo(BigDecimal.ZERO) == 0 || tempPay.compareTo(BigDecimal.ZERO) == 1){
							totalReal = BigDecimal.ZERO;
						}
					}
					
					if(totalReal.compareTo(BigDecimal.ZERO) == -1){
						totalReal = BigDecimal.ZERO;
					}
					BigDecimal freight =  BigDecimal.ZERO;
					if(new BigDecimal(orderVo.getFreight()).compareTo(BigDecimal.ZERO) == 1){
						freight = new BigDecimal(orderVo.getFreight());
						freight = freight.divide(new BigDecimal(orderVo.getQuantity_detail()), BigDecimal.ROUND_HALF_EVEN);
					}
					totalDiscount=totalPrice.subtract(totalReal).subtract(freight).subtract(giftCardMoney);
					String ordtt = "";
					
					if ((orderVo.getStatus() == 1 || orderVo.getStatus() == 2 || orderVo.getStatus() == 6) && totalReal.compareTo(BigDecimal.ZERO) == 1) {
						ordtt = totalReal.add(freight).toString();
					}else{
						ordtt = totalReal.toString();
					}
					
					
					if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
						ordtt = ordtt.substring(0, ordtt.length()-1);
						if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()).equals(".")){
							ordtt = ordtt.substring(0, ordtt.length()-1);
						}
					}
					if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
						ordtt = ordtt.substring(0, ordtt.length()-1);
						if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals(".")){
							ordtt = ordtt.substring(0, ordtt.length()-1);
						}
					}
					if(ordtt.indexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals("0")){
						ordtt = ordtt.substring(0, ordtt.length()-1);
						if(ordtt.lastIndexOf(".") > 0 && ordtt.substring(ordtt.length()-1).equals(".")){
							ordtt = ordtt.substring(0, ordtt.length()-1);
						}
					}
					orderVo.setTotal_price(totalPrice+"");
					orderVo.setGitf_card_money(giftCardMoney+"");
					orderVo.setPay_money(ordtt);
					orderVo.setFreePrice(totalDiscount+"");
				}
				//对于待支付订单把所购买的商品合并在一个订单中
				if(orderVo.getStatus()==1&&orderVo.getSub_status()==1){
					int orderId=orderVo.getOrder_id();
					ArrayList<OrderProductVo> orderList=null;
					if(map.containsKey(orderId)){
						orderList=map.get(orderId);
					}else{
						orderList=new ArrayList<OrderProductVo>();
					}
					OrderProductVo orderProductVo=new OrderProductVo();
					orderProductVo.setOrderpro_id(orderVo.getOrderpro_id());
					orderProductVo.setSpec_img(orderVo.getSpec_img());
					orderProductVo.setSpec_name(orderVo.getSpec_name());
					orderProductVo.setProduct_status(orderVo.getProduct_status());
					orderList.add(orderProductVo);
					map.put(orderId, orderList);
				}
				
				
				
				
				 //判断  未支付订单（1） 是否超过支付有效期
				 if(orderVo.getSub_status() == 1 && StringUtil.isNoNull(pd.getString("expiretime"))){
					 String expiretime = pd.getString("expiretime");
					 int expireMinutes = 0;  //过期分钟数
					 if (StringUtil.isNumber(expiretime)) {
						 expireMinutes = (int) (Float.valueOf(expiretime) * 60) ;
					 }
					 Date orderTime = DateUtil.stringToDate(orderVo.getOrder_time());  
					 Calendar calendar = Calendar.getInstance();
					 calendar.setTime(orderTime);
					 calendar.add(Calendar.MINUTE, expireMinutes);
					
					 Date current = new Date();
					 //System.out.println("current:" + DateUtil.dateToString(current) + "____;guoqi:" + DateUtil.dateToString(calendar.getTime()));
					 if(current.after(calendar.getTime())){      //超过有效期
						 orderVo.setIsExpire(1);
					 }else{
						Long l = calendar.getTimeInMillis()-current.getTime(); //过期时间到当前时间的毫秒数
						String time = "";
						
						long hours=l/1000/3600;
						long myushu=l/1000%3600;
						long minites=myushu/60;
						long syushu=myushu%60;
						
						if(hours>0){
							if(String.valueOf(hours).length()>1){
								time=hours+":";
							}else{
								time="0"+hours+":";
							}
						}else{
							time="00:";
						}
						
						if(minites>0){
							if(String.valueOf(minites).length()>1){
								time+=minites+":";
							}else{
								time+="0"+minites+":";
							}
						}else{
							time+="00:";
						}
						
						if(syushu>0){
							if(String.valueOf(syushu).length()>1){
								time+=syushu;
							}else{
								time+="0"+syushu;
							}
						}else{
							time+="00";
						}
						orderVo.setSurplusTiem(time);
					}
				}
				 //查询待支付订单且过期订单不在待支付选项卡显示
				if(!(orderVo.getIsExpire()==1 && pd.getString("status").equals("1"))){
					listOrderVo.add(orderVo);
				}
				
			}
		}
		List<OrderVo> listOrderVo2 = new ArrayList<OrderVo>();
		List<Integer>ids=new ArrayList<Integer>();
		if(listOrderVo!=null&&listOrderVo.size()>0){
			for(OrderVo vo:listOrderVo){
				if(vo.getSub_status()==1){
					if(!ids.contains(vo.getOrder_id())){
						vo.setListOrderProductVo(map.get(vo.getOrder_id()));
						ids.add(vo.getOrder_id());
						listOrderVo2.add(vo);
					}
				}else{
					vo.setListOrderProductVo(map.get(vo.getOrder_id()));
					ids.add(vo.getOrder_id());
					listOrderVo2.add(vo);
				}
					
			}
		}
		
		
		return listOrderVo2;
	}
	
	//根据用户id查询历史购买订单
	@Override
	public List<OrderVo> findHistoryOrderByCustomerId(int customerId) {
		return po2voer.transfer(OrderVo.class, orderService.findHistoryOrderByCustomerId(customerId));
	}
	
	 //查询 订单时间超过系统设置时间的商品订单 且  已锁定的积分和优惠券
	@Override
	public List<OrderVo> findOrderLockIntegerAndCoupon(PageData pd) {
		return po2voer.transfer(OrderVo.class, orderService.findOrderLockIntegerAndCoupon(pd));
	}

	//查询订单详情  
	@Override
	public OrderVo queryOrderDetails(PageData pd) {
		return po2voer.transfer(new OrderVo(), orderService.queryOrderDetails(pd));
	}
	
	
	@Override
	protected BaseService<Order> getService() {
		return orderService;
	}

	@Override
	public int cancelOrder(OrderVo order) {
 
		PageData editOrderPd=new PageData();
		editOrderPd.put("orderId", order.getOrder_id());
		editOrderPd.put("status", OrderConstants.STATUS_ORDER_CANCEL);
		int result = orderService.editOder(editOrderPd);
		
		String orderCode=order.getOrder_code();
		PageData editUseLockPd = new PageData();
		//礼品卡 and 优惠券解除锁定
		editUseLockPd.put("lockStatus", 2);
		editUseLockPd.put("orderCode", orderCode);
		result += useLockService.editUseLockById(editUseLockPd);
		
		PageData oppd = new PageData();
		oppd.put("orderId", order.getOrder_id());
		List<OrderProduct> ops = this.orderProductService.findListOrderProduct(oppd);
		for (OrderProduct orderProduct : ops) {
			PageData pspd = new PageData();
			pspd.put("specId", orderProduct.getSpec_id());
			ProductSpec pspec = this.productSpecService.queryProductSpecByOption(pspd);
			if(pspec != null){
				int stockCopy = pspec.getStock_copy() + orderProduct.getQuantity();
				ProductSpec pps = new ProductSpec();
				pps.setSpec_id(pspec.getSpec_id());
				pps.setStock_copy(stockCopy);
				productSpecService.updateProductSpecBySid(pps);
			}
		}
		
		return result;
	}

	/**
	 * 创建订单
	 */
	@Override
	public Map<String, Object> createOrder(PageData pd) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String productIds = pd.getString("productIds");          //购买的商品Id
		String specIds = pd.getString("specIds");                //规格Id               
		String amounts = pd.getString("amounts");                //购买数量
		String receiveId = pd.getString("receiverId");           //收货地址 ID
		String useCouponId = pd.getString("useCouponId");        //使用的优惠券ID
		String payType = pd.getString("payType");     		   //支付类型
		String giftcardId = pd.getString("giftcardId");          //购物卡ID  使用礼品卡   useGiftCard
		String cartIds = pd.getString("cartIds");                //购物车Id
		String token = pd.getString("token");                    //用户标识
		
	    Customer customer = BeanConverter.convert(new Customer(), customerManager.queryCustomerByToken(token));
		if (customer == null) {
			return CallBackConstant.LOGIN_TIME_OUT.callback();
		}
		
		logger.info("Customer用户id为"+customer.getCustomer_id()+"--进入了app下单------");
		
		if(productIds==null||"".equals(productIds) || specIds==null||"".equals(specIds)
				 || amounts==null||"".equals(amounts)){
			return CallBackConstant.PARAMETER_ERROR.callback("购买的产品信息为空");
		}
		
		String[] productIdArray = productIds.split(",");
		String[] specIdArray = specIds.split(",");
		String[] amountArray = amounts.split(",");
		
		String orderCode=generateOrderCode();
		
		BigDecimal price = null;
		BigDecimal payPrice = null;
		
		//查询订单商品存进订单商品集合
		List<OrderProduct> listOrderProducts = null;
		
		//获取订单商品金额
		Map<String,Object> orderMap = getOrderPrice(productIdArray,specIdArray,amountArray,customer);
		
		if(orderMap.get("exist") != null){ //商品已下架
			return CallBackConstant.FAILED.callback(orderMap.get("exist"));
		}
		if(orderMap.get("noStockCopy") != null){  //商品无库存
			return CallBackConstant.FAILED.callback(resultMap.get("noStockCopy"));
		}
		price = new BigDecimal(orderMap.get("price").toString()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		logger.info("商品总价price----------------"+price);
		payPrice = new BigDecimal(orderMap.get("payPrice").toString()).setScale(2, BigDecimal.ROUND_HALF_UP);

		listOrderProducts = (List<OrderProduct>) orderMap.get("listOrderProducts");
		
		PageData pagedata = new PageData();
		pagedata.put("customerId", customer.getCustomer_id());
		
		//收货地址   查询运费
		pagedata.put("receiverId",receiveId);
		ReceiverAddress receiverAddressVo = receiverAddressService.selectReceiverAddressByOption(pagedata);
		float freight = 0f;
		
		if(null == receiverAddressVo){
			return CallBackConstant.PARAMETER_ERROR.callback("无效的收货地址");
		}else{
			if(null != receiverAddressVo){
				Map<String, String> freightParam = new HashMap<String, String>();
				freightParam.put("provinceCode", String.valueOf(receiverAddressVo.getProvinceId()));
				freightParam.put("areaCode", String.valueOf(receiverAddressVo.getCityId()));
				Map<String, Object> freightMap = freightManager.getFreightByAreaCode(receiverAddressVo.getProvinceId().toString(), 
						receiverAddressVo.getCityId().toString());
				
				// 1010 为查询成功，根据价格判断是否超过 免减价格： 否  增加运费
				if("1010".equals(String.valueOf(freightMap.get("code")))){
					Map<String, Object> data = (Map<String, Object>) freightMap.get("data");
					
					logger.info("商品运费为--------"+freight);
					float full_cut=Float.valueOf(String.valueOf(data.get("full_cut")));
					
					float totalPrice=Float.valueOf(String.valueOf(price));
					if(totalPrice < full_cut){
						freight=Float.valueOf(String.valueOf(data.get("freight")));
						payPrice = payPrice.add(new BigDecimal(freight));
						logger.info("商品实际payPrice加运费----------------"+payPrice);
					}
				} else {
					return CallBackConstant.FAILED.callback("查询运费失败");
				}
			}
		}
				
		
		//锁定优惠券或者礼品卡实体类
		UseLock useLockInfo=null;
		
		//优惠券
		CouponInfo couponInfo = null;
		if(null!=useCouponId && !"".equals(useCouponId)){         
			pagedata.put("canusecoupon","0");
			pagedata.put("couponId",useCouponId);
			couponInfo = couponInfoService.findUserCoupon(pagedata);
			
			if(null==couponInfo ){
				return CallBackConstant.PARAMETER_ERROR.callback("无效的优惠卷");
			}else{
				BigDecimal startMoney=new BigDecimal(couponInfo.getStart_money());
				if(startMoney.compareTo(price)==1){
					return CallBackConstant.PARAMETER_ERROR.callback("非法使用的优惠卷");
				}
				
				PageData pd2 = new PageData();
				pd2.put("couponId", useCouponId);
				pd2.put("customerId", customer.getCustomer_id());
				pd2.put("lockStatus", 1);
				UseLock lockCoupon = useLockService.findUseLock(pd2);     //根据用户ID ,优惠券Id,锁定状态（已锁定）  查询优惠券是否被使用
				
				if(null !=lockCoupon && couponInfo.getCoupon_id()==lockCoupon.getUse_coupon_id()){
					return CallBackConstant.PARAMETER_ERROR.callback("优惠券已在订单【"+lockCoupon.getOrder_code()+"】中使用!");
				}else{
					//用户礼品卡 锁定实体类
					useLockInfo = new UseLock();
					useLockInfo.setOrder_code(orderCode);
					useLockInfo.setCustomer_id(customer.getCustomer_id());
					useLockInfo.setUse_coupon_id(Integer.parseInt(useCouponId));
					useLockInfo.setLock_status(1);      //已使用 （使用完）
					useLockService.insertUseLock(useLockInfo);
					logger.info("customerId为"+customer.getCustomer_id()+"使用了优惠券Use_coupon_id--"+useCouponId);
				}
				
				payPrice = payPrice.subtract(new BigDecimal(couponInfo.getCoupon_money()));
			}
		}
		
		logger.info("商品实际payPrice减去了优惠活动券---------------"+payPrice);
		
		/**
		 * 使用的礼品卡
		 * 
		 * 说明:
		 *   1 .查看用户礼品卡可用总余额
		 *   2 .如果礼品卡余额大于或者等于支付就直接返回支付成功
		 *   2. 如果礼品卡余额小于支付就减去在去支付
		 * 
		 */
		String giftCardNos="";
		BigDecimal useGiftMoney=new BigDecimal(0);
		if("useGiftCard".equals(giftcardId)){
			
			PageData pd2 = new PageData();
			pd2.put("customerId", String.valueOf(customer.getCustomer_id()));
			pd2.put("lockStatus","1");
			List<UseLock> lockGiftCards = useLockService.findListUseLock(pd2);     //根据用户ID ,优惠券Id,锁定状态（已锁定  使用完）  查询优惠券是否被使用
			
			BigDecimal canUseTotalMoney = giftCardService.queryCustomerCanUseTotalMoney(customer.getCustomer_id());
			BigDecimal useLockMoney = BigDecimal.ZERO;
			
			if(null!=lockGiftCards && lockGiftCards.size()>0){
				for (int i = 0; i < lockGiftCards.size(); i++) {
					if(null!=lockGiftCards.get(i).getUse_giftcard_no()){
						//累加所有已使用的礼品卡金额
						useLockMoney=useLockMoney.add(new BigDecimal(lockGiftCards.get(i).getUse_card_money()));
					}
				}
			}
			
			if(useLockMoney.intValue()>0){
				canUseTotalMoney=canUseTotalMoney.subtract(useLockMoney);
			}
			
			if(null!=canUseTotalMoney && canUseTotalMoney.compareTo(new BigDecimal(0))>0){
				PageData pds = new PageData();
				pds.put("status","1");
				pds.put("customerId",String.valueOf(customer.getCustomer_id()));
				List<GiftCard> listGiftCards = giftCardService.findListGiftCard(pds);
				
				//去除已锁定的礼品卡
				if(null!=listGiftCards && listGiftCards.size()>0){
					int cardCount=listGiftCards.size();
					for (int i = 0; i <cardCount ; i++) {
						if(null!=lockGiftCards && lockGiftCards.size()>0){
							for (int j = 0; j < lockGiftCards.size(); j++) {
								if(listGiftCards.get(i).getCard_no().equals(lockGiftCards.get(j).getUse_giftcard_no())){
									listGiftCards.remove(i);
									cardCount=listGiftCards.size();
								}
							}
						}else{
							break;
						}
					}
				}
				if(null!=listGiftCards && listGiftCards.size()>0){
					pds.put("status","5");   //未使用完
					List<GiftCard> listnNotALlGiftCards = giftCardService.findListGiftCard(pds);
					if(null!=listnNotALlGiftCards && listnNotALlGiftCards.size()>0 ){
						
						for (int j = 0; j <listGiftCards.size() ; j++) {
							for (int i = 0; i < listnNotALlGiftCards.size(); i++) {
								if(listGiftCards.get(j).getCard_no().equals(listnNotALlGiftCards.get(i).getCard_no())){
									BigDecimal useMoney=new BigDecimal(listnNotALlGiftCards.get(i).getCard_money());
									if(null!=listGiftCards.get(j).getCard_money() && !"".equals(listGiftCards.get(j).getCard_money())){
										useMoney=useMoney.add(new BigDecimal(listGiftCards.get(j).getCard_money()));
									}
									listGiftCards.get(j).setCard_use(String.valueOf(useMoney));
								}
							}
						}	
					}
				}
				
				
				if(null!=listGiftCards && listGiftCards.size()>0){
					
					for (int i = 0; i < listGiftCards.size(); i++) {
						 //礼品卡可使用金额
						BigDecimal leftMomey=null;
						if(null!=listGiftCards.get(i).getCard_use() && !"".equals(listGiftCards.get(i).getCard_use())){
							leftMomey=new BigDecimal(listGiftCards.get(i).getCard_money()).subtract(new BigDecimal(listGiftCards.get(i).getCard_use()));
						}else{
							leftMomey=new BigDecimal(listGiftCards.get(i).getCard_money());
						}
						
						//先判 支付总价 - 礼品卡可用金额  >=0  直接锁定
						if(payPrice.subtract(leftMomey).compareTo(BigDecimal.ZERO) >=0 ){
							payPrice=payPrice.subtract(leftMomey);
							//用户礼品卡 锁定实体类
							useLockInfo=new UseLock();
							useLockInfo.setOrder_code(orderCode);
							useLockInfo.setCustomer_id(customer.getCustomer_id());
							useLockInfo.setUse_giftcard_no(listGiftCards.get(i).getCard_no());
							useLockInfo.setUse_card_money(String.valueOf(leftMomey));
							useLockInfo.setLock_status(1);      //已使用 （使用完）
							useLockService.insertUseLock(useLockInfo);
							
							logger.info("customerId为"+customer.getCustomer_id()+"礼品卡Use_giftcard_no--"+listGiftCards.get(i).getCard_no()+"--使用礼品卡金额为--"+leftMomey+"");
							useGiftMoney=useGiftMoney.add(leftMomey);
							giftCardNos+=listGiftCards.get(i).getCard_no()+",";
							
					    //否则, 未使用完  5   在退出循环 	
						}else if(payPrice.subtract(leftMomey).compareTo(BigDecimal.ZERO)==-1){
				//			BigDecimal useMoney=leftMomey.subtract(payPrice);
							
							useGiftMoney=useGiftMoney.add(payPrice);
							
							//用户礼品卡 锁定实体类
							useLockInfo=new UseLock();
							useLockInfo.setOrder_code(orderCode);
							useLockInfo.setCustomer_id(customer.getCustomer_id());
							useLockInfo.setUse_giftcard_no(listGiftCards.get(i).getCard_no());
							useLockInfo.setUse_card_money(String.valueOf(payPrice));
							useLockInfo.setLock_status(5);      //未使用完
							useLockService.insertUseLock(useLockInfo);
							payPrice=BigDecimal.ZERO;
							giftCardNos+=listGiftCards.get(i).getCard_no()+",";
							logger.info("customerId为"+customer.getCustomer_id()+"礼品卡Use_giftcard_no--"+listGiftCards.get(i).getCard_no()+"--使用礼品卡金额为--"+leftMomey+"");

							break;
						}
					}
					
				}else{
					return CallBackConstant.PARAMETER_ERROR.callback("礼品卡余额已被锁定");
				}
				
			}else{
				return CallBackConstant.PARAMETER_ERROR.callback("礼品卡余额为0");
			}
		}
		
		if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡
			if(giftCardNos.lastIndexOf(",")!=-1){
				giftCardNos=giftCardNos.substring(0,giftCardNos.lastIndexOf(","));
			}
		}
		
		Order order = new Order();
		order.setOrder_code(orderCode);  						 //订单编号
		order.setCustomer_id(customer.getCustomer_id());       //客户
		order.setOrder_type(1);  //线上订单
		order.setReceiver(receiverAddressVo.getReceiverName());   //收货人
		order.setMobile(receiverAddressVo.getMobile());           //手机号码
		order.setAddress(receiverAddressVo.getAddress());
		order.setFreight(String.valueOf(freight));                //运费
		
		if(null !=useCouponId && !"".equals(useCouponId)){          //优惠券
			order.setUse_coupon(Integer.parseInt(useCouponId));
			order.setConpon_money(String.valueOf(couponInfo.getCoupon_money()));
		}
		
		if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡       
			order.setUse_gift_card(giftCardNos);
			order.setGitf_card_money(String.valueOf(useGiftMoney));
		}
		
		order.setPay_type(Integer.parseInt(payType));         ///支付方式，1：微信；2：支付宝    微信平台都是 1 
		order.setTotal_price(String.valueOf(price));        //订单总金额
		if(payPrice.floatValue() < 0){
			payPrice = new BigDecimal(0);
		} else {
			order.setStatus(OrderConstants.STATUS_ORDER_NOPAY);
		}
		
		
		logger.info("customerId为"+customer.getCustomer_id()+"订单号为orderVoCode--"+order.getOrder_code()+"商品总价为---"
		+order.getTotal_price()+"-------商品实际支付payPrice--"+payPrice);
		
		order.setPay_money(String.valueOf(payPrice));       //实付金额
		
		orderService.insertOrder(order);
		
		//把订单ID添加进入订单商品 且 进行增加操作
		if(!listOrderProducts.isEmpty() && listOrderProducts.size()>0){
			for (int i = 0; i < listOrderProducts.size(); i++) {
				listOrderProducts.get(i).setOrder_id(order.getOrder_id());
			}
			orderProductService.addBatchOrderProduct(listOrderProducts);
		}
		
		
		/**
		 * 从购物车中结算商品，下单后需要删除购物车 
		 * 2017-2-10  by chen
		 */
		if(null!=cartIds && !"".equals(cartIds)){
			PageData pds=new PageData();
			pds.put("customerId", String.valueOf(customer.getCustomer_id()));
			pds.put("cartId",cartIds);
			shoppingCartService.deleteShoppingCartByCidAndCid(pds);
		}
		
		try {
			logger.info("记录支付日志: startting... ");
			
			//记录支付日志
			PayLog payLogInfo=new PayLog();
			payLogInfo.setOrder_id(order.getOrder_id());
			payLogInfo.setOrder_code(orderCode);
			payLogInfo.setCustomer_id(customer.getCustomer_id());
			payLogInfo.setPay_type(Integer.parseInt(payType));
			payLogInfo.setPay_money(order.getPay_money());
			
			if(null !=useCouponId && !"".equals(useCouponId)){          //优惠券
				payLogInfo.setCoupon_id(Integer.parseInt(useCouponId));
				payLogInfo.setConpon_money(String.valueOf(couponInfo.getCoupon_money()));
			}
			
			if(null!=giftCardNos && !"".equals(giftCardNos)){            //礼品卡
				payLogInfo.setGift_card_no(giftCardNos);
				payLogInfo.setGitf_card_money(String.valueOf(useGiftMoney));
			}
			
			payLogService.insertPayLog(payLogInfo);
			
			logger.info("记录支付日志: 成功... ");
			logger.info("记录支付日志: 完成... ");
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.info("记录支付日志: 异常... ");
			logger.info("记录支付日志: 失败... ");
		}
		logger.info("记录支付日志: 结束... ");
		
		
		//如果支付的钱为0 ,就直接调用支付成功后 的方法
		if(payPrice.compareTo(BigDecimal.ZERO)==0){
			logger.info("支付金额==0时，不走微信支付------------------------------------------------------------");
			//1.修改订单状态
			//2.修改优惠券状态
			//3.修改用户礼品卡和优惠券的锁定状态
			try {
				notifyUpdateAboutOrder(orderCode,"","1",customer);
				resultMap.put("orderCode", orderCode);
				resultMap.put("notify", "succ");
			} catch (Exception e) {
				logger.error("使用礼品卡或者优惠券后,订单应支付总价为0,调用支付成功后的方法出现异常", e);
			}
			
		} else {
			resultMap.put("orderCode", orderCode);
			resultMap.put("orderId", order.getOrder_id());
		}
		return CallBackConstant.SUCCESS.callback(resultMap);
	}
	
	
	public static String generateOrderCode(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String str=sdf.format(new Date());
		int result=new Random().nextInt(1000);
		return str+result;
	}
	
	
	/**
	 * 获取订单商品价格
	 * @param productIdArray
	 * @param specIdArray
	 * @param amountArray
	 * @param customer
	 * @return
	 */
	public Map<String,Object> getOrderPrice(String[] productIdArray,String[] specIdArray,String[] amountArray,Customer customer){
		List<OrderProduct> listOrderProducts = new ArrayList<OrderProduct>();
		
		BigDecimal totalPrice=new BigDecimal(0);
		BigDecimal payPrice=new BigDecimal(0);
		Map<String,Object> map=new HashMap<String, Object>();
		
		//查询购买的商品价格信息
		PageData pageData=new PageData();
		for (int i = 0; i < productIdArray.length; i++) {
			BigDecimal price=new BigDecimal(0);
			
			pageData.put("productId", productIdArray[i]);
			pageData.put("specId", specIdArray[i]);
			
			Product product = productService.selectProductByOption(pageData);
			if(null == product){ //商品不存在或已经删除下架
				map.put("exist", "商品已下架");
				return map;
			} else {
				if (product.getStatus() != 1) {
					map.put("exist", product.getProduct_name() + "已经下架");
					return map;
				}
			}
			//判断库存
			ProductSpec spec = productSpecService.queryProductSpecByOption(pageData);
			
			if(spec !=null && spec.getStock_copy() != null){
				int restCopy = spec.getStock_copy()-Integer.parseInt(amountArray[i]);
				if(restCopy<0){
					map.put("noStockCopy",product.getProduct_name()+"库存不足");
					return map;
				}
			}
			
			
			logger.info("商品id是productId---"+productIdArray[i]+"--商品名称为"+product.getProduct_name()+"--------规格specId-----"+specIdArray[i]);
			List<ActivityProduct> listActivtyProduct = activityProductService.queryList(pageData); //查看商品是否存在活动
			
			//优惠多少钱
			BigDecimal leftMoney=null;
			boolean isSpec = false;
			boolean isPre = false;
			int prelimit = 0;
			PreProduct preProduct = new PreProduct();
			Special special = new Special();
			//计算此商品的总价格
			if(listActivtyProduct!=null&&listActivtyProduct.size()>=0){
				String actPrice = "";
				for (ActivityProduct activityPro : listActivtyProduct) {//符合时间的活动
					PageData spd = new PageData();
					if(activityPro.getActivity_type()==1){//预售
						spd.put("preId", activityPro.getActivity_id());
						 preProduct = preProductService.selectPreProductByOption(spd);
						if(preProduct != null){
							Date dt = DateUtil.stringToDate(preProduct.getRaiseEnd());
							Date start = DateUtil.stringToDate(preProduct.getRaiseStart());
							if(dt.after(new Date())&&start.before(new Date())){
								actPrice=activityPro.getActivity_price();
								prelimit = preProduct.getLimitBuy()==null?-1:preProduct.getLimitBuy();
								isPre = true;
								logger.info(preProduct.getPreName()+"---此预售商品活动价格为------"+actPrice);
								break;
							}
						}
					}else{//专题
						spd.put("specialId", activityPro.getActivity_id());
						special = specialService.selectSpecialByOption(spd);
						if(special != null){
							Date dt = DateUtil.stringToDate(special.getEndTime());
							Date start = DateUtil.stringToDate(special.getStartTime());
							if(dt.after(new Date())&&start.before(new Date())){
								actPrice=activityPro.getActivity_price();
								isSpec = true;
								logger.info(special.getSpecialName()+"---此专题商品活动价格为------"+actPrice);
								break;
							}
						}
					}
				}
				if(actPrice!=""){
					leftMoney = new BigDecimal(actPrice);
				}
			}
			
			OrderProduct orderProduct = null;
			if(null!=product&&null!=spec){
				orderProduct = new OrderProduct();
				orderProduct.setProduct_id(product.getProduct_id());
				orderProduct.setProduct_name(product.getProduct_name());
				orderProduct.setSpec_id(spec.getSpec_id());
				orderProduct.setSpec_name(spec.getSpec_name());
				orderProduct.setPrice(spec.getSpec_price());
				price = new BigDecimal(spec.getSpec_price());
				
				orderProduct.setQuantity(Integer.parseInt(amountArray[i]));
			}
			if(null !=leftMoney){
				orderProduct.setActivity_price(String.valueOf(leftMoney));
			}
			
			orderProduct.setIs_evaluate(0);
			listOrderProducts.add(orderProduct);
			
			price=price.multiply(new BigDecimal(amountArray[i]));          //计算出商品总价格
			totalPrice=totalPrice.add(price);
			if(orderProduct.getActivity_price()!=null&&orderProduct.getActivity_price()!=""){
				BigDecimal payMoney = new BigDecimal(amountArray[i]).multiply(new BigDecimal(orderProduct.getActivity_price()));
				payPrice=payPrice.add(payMoney);
			}else{
				payPrice=totalPrice;
			}
		}
			
		map.put("payPrice", payPrice);
		map.put("price", totalPrice);
		map.put("listOrderProducts", listOrderProducts);
		
		return map;
	}
	
	/**
	 * @param response
	 * @param tradeNo
	 * 1.减少用户 总积分,可用积分 和 对应的使用积分记录
	 * 2.增加用户 总积分,可用积分 和 对应的增加积分记录
	 * 3.修改订单状态
	 * 4.修改优惠券状态   
	 * 5.修改用户积分和优惠券的锁定状态      发现积分BUG 只有确认收货后才可以发送积分  2017-03-02 by chen 
	 * @throws Exception 
	 */
	public void notifyUpdateAboutOrder(String tradeNo,String transactionId,String payType,Customer customer) throws Exception{
		PageData pd =new PageData();
		
		try {
			
			//查询支付日志
			pd.put("orderCode", tradeNo);
			PayLog payLogInfo = payLogService.findPayLog(pd);
			
			if(null!=payLogInfo){
		//		logger.info("支付状态:"+payLogInfo.getPay_time());   //支付时间不为空就是没有支付
				//订单已支付，不需要处理逻辑
				if(null!=payLogInfo.getPay_time()&& !"".equals(payLogInfo.getPay_time())){
					return ;
				}
				
				/**
				 * 更新支付日志信息    流水号，支付类型，支付时间
				 */
				pd.clear();
				if(null!=transactionId && !"".equals(transactionId)){
					pd.put("transId",transactionId);
				}
				pd.put("payType",payType);
				pd.put("orderId",payLogInfo.getOrder_id());
				pd.put("customerId",payLogInfo.getCustomer_id());
				payLogService.editPayLog(pd);
				
				
				pd.clear();
				pd.put("orderId",String.valueOf(payLogInfo.getOrder_id()));
				pd.put("orderCode",String.valueOf(payLogInfo.getOrder_code()));
				Order order = orderService.findOrder(pd);
				
				if(null != order){
					/**
					 * 更新订单支付状态和支付类型
					 */
					pd.put("status","2");       //订单待发货(已付款)
					pd.put("payType",payType);
					pd.put("customerId", payLogInfo.getCustomer_id());
					orderService.editOder(pd);
				}else{
					throw new Exception();	
				}
				
				//更换锁定用户使用的礼品卡与优惠券
				if( (null != payLogInfo.getCoupon_id() && !"".equals(payLogInfo.getCoupon_id())) || 
					 	(null != payLogInfo.getGift_card_no() && !"".equals(payLogInfo.getGift_card_no()))){
					
					//根据条件查询 锁定记录      不为空 已被锁定   为空  解除锁定  
					PageData pdData=new PageData();
					pdData.put("orderCode", tradeNo);
					pdData.put("customerId", payLogInfo.getCustomer_id());
					pdData.put("lockStatus",1);    //完全用完余额
					
					logger.info("用户customer_id为-"+payLogInfo.getCustomer_id()+"调用了useLockManager.findUseLock()方法---订单orderCode--"+tradeNo);
					
					List<UseLock> listUseLockInfo = useLockService.findListUseLock(pdData);
					
					List<UseLock> listUserLock = new ArrayList<UseLock>();
					if(null!=listUseLockInfo && listUseLockInfo.size()>0){
						listUserLock.addAll(listUseLockInfo);
					}
					
					pdData.put("lockStatus",5);      //用了部分礼品卡余额
					listUseLockInfo = useLockService.findListUseLock(pdData);
					if(null!=listUseLockInfo && listUseLockInfo.size()>0){
						listUserLock.addAll(listUseLockInfo);
					}
					
					if(null!=listUserLock && listUserLock.size()>0){
						pdData.put("orderCode",tradeNo);    			//锁定订单编号
						pdData.put("lockStatus","3");   				//成功支付
						useLockService.editUseLockById(pdData);
						logger.info("成功支付调用了useLockManager.editUseLockById()方法");
					}
					
					
					
					logger.info("用户customer_id为-"+payLogInfo.getCustomer_id()+"准备更新用户优惠券使用。。。 ");
					
					//增加使用优惠券使用记录
					if(payLogInfo.getCoupon_id()!=null && !"".equals(payLogInfo.getCoupon_id())){
						logger.info("开始更新用户使用优惠券。。。 start");
						logger.info("优惠券id:"+payLogInfo.getCoupon_id());
						
						CouponInfoVo couponInfo=new CouponInfoVo();
						couponInfo.setCoupon_id(payLogInfo.getCoupon_id());           
						couponInfo.setUsed_time(DateUtil.dateToString(new Date()));          
						couponInfo.setStatus(1);    								  
						couponInfo.setOrder_code(tradeNo);                          
						couponInfo.setFrom_order_id(order.getOrder_id());          
						PageData pds=new PageData();
						pds.put("status","1");                                         //已使用     
						pds.put("orderCode",tradeNo);                                  //使用的订单编号 
						pds.put("usedTime",DateUtil.dateToString(new Date()));         //使用时间       
						pds.put("orderId",String.valueOf(order.getOrder_id()));      //使用的订单ID
						pds.put("couponId",payLogInfo.getCoupon_id());                 //使用的优惠券ID
						
						couponInfoService.updateUserCouponByCid(pds);
						
						logger.info("结束更新用户使用优惠券。。。 end");
					}
					
					logger.info("完成更新用户使用优惠券。。。 finish");
					
					
					/**
					 *  礼品卡说明：      如果礼品卡的钱不用完，添加进入锁定状态 （1 全部使用    5没有使用完） 
					 *       1. 如果使用使用礼品卡的金额正好与 订单金额一致   那么 不存在多出一个 5没有使用完  礼品卡号
					 *       2. 如果礼品卡的金额与订单金额不一致  那么存在多出一个礼品卡 5没有使用完 卡号  
					 */
					
					//增加使用礼品卡记录
					if(payLogInfo.getGift_card_no()!=null && !"".equals(payLogInfo.getGift_card_no())){
						logger.info("优惠券ids:"+payLogInfo.getGift_card_no());
						
						for (int i = 0; i < listUserLock.size(); i++) {      //锁定的钱
							
							
							if(null ==listUserLock.get(i).getUse_coupon_id() && null!=listUserLock.get(i).getUse_giftcard_no()
									 && null!=listUserLock.get(i).getUse_card_money()){
								
								String cardNo=listUserLock.get(i).getUse_giftcard_no();
								String cardMoeny=listUserLock.get(i).getUse_card_money();
								
								//修改礼品卡，应该查询出这张卡   以前使用多少钱 +现在使用的钱   修改状态为已用完  且 记录使用的总金额
								PageData datas=new PageData();
								datas.put("giftCardNo",cardNo);
								GiftCard giftCardVo = giftCardService.findGiftCard(datas);
								
								BigDecimal cardTotalMoeny=new BigDecimal(giftCardVo.getCard_money());
								
								BigDecimal leftMoney=new BigDecimal(cardMoeny);
								if(null!=giftCardVo.getCard_use()&& !"".equals(giftCardVo.getCard_use())){
									leftMoney=leftMoney.add(new BigDecimal(giftCardVo.getCard_use()));
								}
								
								
								//然后把  对应的卡   剩下使用的钱+已使用的钱
								datas.clear();
								datas.put("cardUseMoney",String.valueOf(leftMoney));
								datas.put("cardId",giftCardVo.getCard_id());
								
								//锁定状态为 1 （已用完） 就修改礼品卡状态为 3 已用完 
								if(listUserLock.get(i).getLock_status()==1 || cardTotalMoeny.compareTo(leftMoney)==0 ){
									datas.put("status","3");
								}
								
								giftCardService.updateGiftCardByGid(datas);
								
								GiftCardRecord giftCardRecord = new GiftCardRecord();
								giftCardRecord.setCardNo(listUserLock.get(i).getUse_giftcard_no());
								giftCardRecord.setCustomerId(customer.getCustomer_id());
								giftCardRecord.setOrderNo(tradeNo);
								giftCardRecord.setRecordRemak("订单编号:"+tradeNo+" 使用");
								giftCardRecord.setRecordType((byte)2);
								giftCardRecord.setMoney(new BigDecimal(cardMoeny));
								giftCardRecordService.insertGiftCardRecord(giftCardRecord);
							}
						}
					}
					
				}
				
				//4.支会成功后要改变预售的支持人数和金额;
				logger.info("支付成功回调修改预购进度开始--------");
				PageData orpd = new PageData();
				orpd.put("orderId", order.getOrder_id());
				orpd.put("orderCode", order.getOrder_code());
				List<OrderProduct> orderProducts = orderProductService.findListOrderProduct(orpd);
				
				if(orderProducts!=null&&orderProducts.size()>0){
					for (OrderProduct orderProduct : orderProducts) {
						orpd.clear();
						orpd.put("productId", orderProduct.getProduct_id());
						orpd.put("specId", orderProduct.getSpec_id());
						
						List<PreProduct> preProductVo = preProductService.queryList(orpd);//根据条件查出预购商品
						
						if(preProductVo!=null&&preProductVo.size()>0){
							for (PreProduct pre : preProductVo) {
								
								//根据条件查出此用户是否买过此产品
								PageData productPd = new PageData();
								productPd.put("productId", orderProduct.getProduct_id());
								productPd.put("customerId", order.getCustomer_id());
								productPd.put("startTime", pre.getRaiseStart());
								productPd.put("endTime", order.getOrder_time());
								productPd.put("hasBuy", "hasBuy");//已付款的订单
								List<OrderProduct> orderProductlist = orderProductService.findListOrderProduct(productPd);
								logger.info("之前下单----------------------------====》");
								
								
								BigDecimal raiseMoney = new BigDecimal(orderProduct.getPrice()).multiply(new BigDecimal(orderProduct.getQuantity()));
								if(org.apache.commons.lang.StringUtils.isNotBlank(orderProduct.getActivity_price())){
									raiseMoney = new BigDecimal(orderProduct.getActivity_price()).multiply(new BigDecimal(orderProduct.getQuantity()));
								}
								if(orderProductlist!=null&&orderProductlist.size()>0){//买过此产品的客户算一份
									pre.setSupportNum(pre.getSupportNum());
								}else{//没买过的支持人数要加1
									pre.setSupportNum(pre.getSupportNum()+1);
								}
								
								pre.setRaiseMoney(pre.getRaiseMoney().add(raiseMoney));
								preProductService.updatePreProduct(pre);//修改预购进度
							}
						}
					}
				}
				
				logger.info("支付成功回调修改预购成功--------");
				
				
				//5.购买成功之后修改库存
				logger.info("支付成功后回调，修改库存开始----------------------------------");
				if(orderProducts!=null&&orderProducts.size()>0){
					for (OrderProduct orderProduct : orderProducts) {
						orpd.clear();
						orpd.put("productId", orderProduct.getProduct_id());
						orpd.put("specId", orderProduct.getSpec_id());
						orpd.put("status","1");
						//判断库存
						ProductSpec spec = productSpecService.queryProductSpecByOption(orpd);
						if(spec != null && spec.getStock_copy() != null){
							int saleOut = orderProduct.getQuantity();
							int sale = spec.getSales_copy()==null?1:spec.getSales_copy();
							spec.setSales_copy(sale+saleOut);//卖出的份数
							logger.info("卖出去份数--------"+spec.getSales_copy()+"rrrrrrrrrrrrrrrrrrrr=======");
							//库存就要减去卖出去的份数
							int stock = spec.getStock_copy()-saleOut;
							if(stock>=0){
								spec.setStock_copy(stock);
							}else{//超卖
								spec.setStock_copy(0);
								logger.info("用户"+customer.getCustomer_id()+"购买产品ID为"+orderProduct.getProduct_id()+"购买数量为"+saleOut+"支付成功后回调，库存超卖"+stock+"份-----------------------------------");

							}
							int count  = productSpecService.updateProductSpecBySid(spec);
							if(count>0){
								logger.info("用户"+customer.getCustomer_id()+"购买产品ID为"+orderProduct.getProduct_id()+"购买数量为"+saleOut+"支付成功后回,库存有"+spec.getStock_copy()+"件-----------------------------------");

							}
						}
					}
				}
				
				logger.info("支付成功后回调，修改库存结束-----------------------------------");
				
			}
			
		} catch (NumberFormatException e) {
			logger.error("调用了 notifyUpdateAboutOrder() 方法  异常", e);
		}
	}

	@Override
	public int updateOrderCodeByOrdeId(PageData pd) {
		return orderService.updateOrderCodeByOrdeId(pd);
	}

	@Override
	public List<OrderVo> findAllorders(PageData pd) {
		return po2voer.transfer(OrderVo.class,orderService.findAllorders(pd));
	}

	@Override
	public int queryOrderCountByCust(int customerId) {
		return orderService.queryOrderCountByCust(customerId);
	}

	@Override
	public int queryOrderCountByParams(PageData ordpd) {
		return orderService.queryOrderCountByParams(ordpd);
	}

	@Override
	public Map<String, Integer> queryMyOrderNums(Integer customerId) {
		int waitPayOrderNum = 0;
		int waitDeliveryOrderNum = 0;
		int waitReceiveOrderNum = 0;
		int waitCommentOrderNum = 0;
		Map<String, Integer> results = new HashMap<String, Integer>();
		
		try {
			
			if(customerId == null || customerId.intValue() <= 0){
				results.put("waitPayOrderNum", waitPayOrderNum);
				results.put("waitDeliveryOrderNum", waitDeliveryOrderNum);
				results.put("waitReceiveOrderNum", waitReceiveOrderNum);
				results.put("waitCommentOrderNum", waitCommentOrderNum);
				return results;
			}
			
			PageData ordpd = new PageData();
			ordpd.put("customerId", customerId);
			ordpd.put("status", "1");
			ordpd.put("dictCode","SYSTEM_ORDER_EXPIRE");
			SysDict sysDict = sysDictService.selectByCode(ordpd);
			int expiretime = 30; //默认待支付订单30分钟有效
			if (null != sysDict) {
				//待支付订单有效期，将系统系数转为分钟数
				expiretime = (int) (Float.valueOf(sysDict.getDictValue()) * 60);
			}
			ordpd.put("expiretime", expiretime);
			//待支付订单
			waitPayOrderNum = this.orderService.queryOrderCountByParams(ordpd);
			
			//已支付订单
			ordpd.clear();
			ordpd.put("customerId", customerId);
			List<Map<String, Object>> orderNums = this.orderProductService.queryMyOrderNum(ordpd);
			if(orderNums != null && orderNums.size() > 0){
				for (Map<String, Object> map : orderNums) {
					int ordStatus = Integer.parseInt(map.get("status").toString());
					int ordCount = Integer.parseInt(map.get("cnt").toString());
					if(ordStatus == 2) { // 待发货
						waitDeliveryOrderNum = ordCount;
					}else if(ordStatus == 3) { // 待签收
						waitReceiveOrderNum = ordCount;
					}else if(ordStatus == 4) { // 待评论
						waitCommentOrderNum = ordCount;
					}
				}
			}
			
			results.put("waitPayOrderNum", waitPayOrderNum);
			results.put("waitDeliveryOrderNum", waitDeliveryOrderNum);
			results.put("waitReceiveOrderNum", waitReceiveOrderNum);
			results.put("waitCommentOrderNum", waitCommentOrderNum);
			return results;
		} catch (Exception e) {
			logger.error("OrderManagerImpl.queryMyOrderNumsByNotExsist --error", e);
			results.put("waitPayOrderNum", waitPayOrderNum);
			results.put("waitDeliveryOrderNum", waitDeliveryOrderNum);
			results.put("waitReceiveOrderNum", waitReceiveOrderNum);
			results.put("waitCommentOrderNum", waitCommentOrderNum);
			return results;
		}
	}

}
