package com.fenghuangzhujia.eshop.commerce.order.dto;

import java.util.Date;

import com.fenghuangzhujia.eshop.commerce.order.GoodOrder.OrderStatus;
import com.fenghuangzhujia.eshop.commerce.order.GoodOrder.SourceType;
import com.fenghuangzhujia.eshop.core.user.dto.UserDto;
import com.fenghuangzhujia.foundation.core.dto.DtoBaseModel;
import com.fenghuangzhujia.foundation.media.MediaContentDto;

public class GoodOrderDto extends DtoBaseModel {	
	private double price;	
	private OrderStatus status;			
	private Integer count;
	private String name;
	private String type;
	private MediaContentDto mainPic;	
	private Object good;
	private UserDto user;
	/**交易时间*/
	private Date payTime;
	private String code;
	private String mobile;
	private String realName;
	/**订单来源*/
	private SourceType source;
	private Date createTime;
	private Date updateTime;

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MediaContentDto getMainPic() {
		return mainPic;
	}
	public void setMainPic(MediaContentDto mainPic) {
		this.mainPic = mainPic;
	}
	public Object getGood() {
		return good;
	}
	public void setGood(Object good) {
		this.good = good;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public SourceType getSource() {
		return source;
	}
	public void setSource(SourceType source) {
		this.source = source;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
