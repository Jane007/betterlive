package com.kingleadsw.betterlive.model;

import java.math.BigDecimal;

import com.kingleadsw.betterlive.core.dto.BasePO;

/**
 * 代理人账户信息记录
 * @author zhangjing   2018年3月2日下午4:43:23
 *
 */
public class AgentAccountRecord extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3154425529102817817L;
	
	//收支类型（1收入 2提现 3退款）
	public final static int TYPE_INCOME = 1;
	public final static int TYPE_WITHDRAW = 2;
	public final static int TYPE_REFUND = 3;		
	
	private long  accountRecordId;                              // 记录id
	private long  accountId;                                    // 账户id
	private long  agentId;                                      // 代理人id
	private Integer  type;                                      // 收支类型（1收入2提现申请3提现失败退款4订单退款支出）
	private BigDecimal  amount;                                 // 金额
	private BigDecimal  beforeAmount;                           // 修改前金额
	private BigDecimal  afterAmount;                            // 修改后金额
	private long  objId;                           	    		// 收益：订单详情ID，提现/退款：提现ID
	private String  createTime;                                 // 创建时间
	private String modifyTime;                                  // 修改时间
	private long  modifier;                                     // 修改人id
	private Integer status;										//'状态(0，待到账 1 到账 2 失效)'
	
	public long getAccountRecordId() {
		return accountRecordId;
	}
	public void setAccountRecordId(long accountRecordId) {
		this.accountRecordId = accountRecordId;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getAgentId() {
		return agentId;
	}
	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}
	public long getObjId() {
		return objId;
	}
	public void setObjId(long objId) {
		this.objId = objId;
	}



	public long getModifier() {
		return modifier;
	}
	public void setModifier(long modifier) {
		this.modifier = modifier;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBeforeAmount() {
		return beforeAmount;
	}
	public void setBeforeAmount(BigDecimal beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	public BigDecimal getAfterAmount() {
		return afterAmount;
	}
	public void setAfterAmount(BigDecimal afterAmount) {
		this.afterAmount = afterAmount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
