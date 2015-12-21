package com.mikepenz.materialdrawer.app.entity;

import java.math.BigDecimal;
import java.util.List;


public class Cart {

	private String userId;
	private String deviceId;
	private List<CartItem> cartItems;
	private BigDecimal totalCheckoutAmount = BigDecimal.ZERO;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public BigDecimal getTotalCheckoutAmount() {
		return totalCheckoutAmount;
	}

	public void setTotalCheckoutAmount(BigDecimal totalCheckoutAmount) {
		this.totalCheckoutAmount = totalCheckoutAmount;
	}

}