package com.mikepenz.materialdrawer.app.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Dimention {
	private String pictureDescription;
	private String pictureIcon;

	private String skuId;
	private String groupId;
	private float mrp;
	private BigDecimal displayPrice;
	private float discount;
	// ml or gm
	private String unit;
	// value 200 400
	private Integer qty;
	private String description;// //
	private String howToUse;

	private boolean isOffer;
	private String offerDesc;
	private Integer unitsOnOrder;
	private Integer unitsInStock;

	private Date mfdDate;
	private Date expiryDate;

	public String getSkuId() {
		return skuId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getHowToUse() {
		return howToUse;
	}

	public void setHowToUse(String howToUse) {
		this.howToUse = howToUse;
	}

	public BigDecimal getDisplayPrice() {
		return displayPrice;
	}

	public void setDisplayPrice(BigDecimal displayPrice) {
		this.displayPrice = displayPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isOffer() {
		return isOffer;
	}

	public void setOffer(boolean isOffer) {
		this.isOffer = isOffer;
	}

	public String getOfferDesc() {
		return offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Integer getUnitsOnOrder() {
		return unitsOnOrder;
	}

	public void setUnitsOnOrder(Integer unitsOnOrder) {
		this.unitsOnOrder = unitsOnOrder;
	}

	public Integer getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(Integer unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public Date getMfdDate() {
		return mfdDate;
	}

	public void setMfdDate(Date mfdDate) {
		this.mfdDate = mfdDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public float getMrp() {
		return mrp;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public String getPictureDescription() {
		return pictureDescription;
	}

	public void setPictureDescription(String pictureDescription) {
		this.pictureDescription = pictureDescription;
	}

	public String getPictureIcon() {
		return pictureIcon;
	}

	public void setPictureIcon(String pictureIcon) {
		this.pictureIcon = pictureIcon;
	}

	@Override
	public String toString() {
		return "" + skuId;
	}

}
