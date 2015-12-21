package com.mikepenz.materialdrawer.app.entity;

public class GetCartResponse extends Response {
	private String deviceId;
	private Cart cart;
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
