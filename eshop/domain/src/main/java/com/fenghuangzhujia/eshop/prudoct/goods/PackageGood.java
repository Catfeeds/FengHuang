package com.fenghuangzhujia.eshop.prudoct.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fenghuangzhujia.eshop.core.commerce.goods.Good;
import com.fenghuangzhujia.eshop.prudoct.packaging.DecoratingPackage;
import com.fenghuangzhujia.foundation.area.Area;

@Entity
@Table(name="fhzj_decorating_good")
public class PackageGood extends Good {
	
	private DecoratingPackage decoratingPackage;	
	private Date appointTime;
	private Area area;
	private String address;
	private String remark;
	private Double houseArea;
	
	/**
	 * 购买的套餐
	 * @return
	 */
	public DecoratingPackage getDecoratingPackage() {
		return decoratingPackage;
	}
	public void setDecoratingPackage(DecoratingPackage decoratingPackage) {
		this.decoratingPackage = decoratingPackage;
	}

	/**
	 * 客户选择的装修时间
	 * @return
	 */
	public Date getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}

	/**
	 * 客户所在地区
	 * @return
	 */
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 客户详细地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 客户留言
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	@Transient
	public double getPrice() {
		return decoratingPackage.getMarketPrice();
	}
	
	@Override
	@Transient
	public double getRealPrice() {
		return decoratingPackage.getSalePrice();
	}
	
	@Override
	@Transient
	public String getMainPic() {
		return decoratingPackage.getMainPic().getUrl();
	}
	
	/**
	 * 房屋面积
	 * @return
	 */
	public Double getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(Double houseArea) {
		this.houseArea = houseArea;
	}
}
