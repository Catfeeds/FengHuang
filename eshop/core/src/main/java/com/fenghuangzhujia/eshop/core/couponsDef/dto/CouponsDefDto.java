package com.fenghuangzhujia.eshop.core.couponsDef.dto;

import java.util.Date;

import com.fenghuangzhujia.foundation.core.dto.DtoBaseModel;

public class CouponsDefDto extends DtoBaseModel {

	/**触发优惠券分发的事件类型，唯一*/
	private String event;
	/**优惠券名称*/
	private String name;
	/**优惠券金额*/
	private Double money;
	/**优惠券有效期截止时间*/
	private Date expireTime;
	/**是否分发优惠券，true，则按照定义分发优惠券*/
	private boolean inUse;
	/**剩余优惠券数量*/
	private Integer remainCount=0;
	/**已经分发优惠券数量*/
	private Integer consumedCount=0;
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public Integer getRemainCount() {
		return remainCount;
	}
	public Integer getConsumedCount() {
		return consumedCount;
	}
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}
	public void setConsumedCount(Integer consumedCount) {
		this.consumedCount = consumedCount;
	}
}
