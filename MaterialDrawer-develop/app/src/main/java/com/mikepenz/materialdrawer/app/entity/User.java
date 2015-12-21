package com.mikepenz.materialdrawer.app.entity;

import java.util.List;

public class User {

	public static final String emailIdKey = "emailID";
	private List<UserAddress> userAddresss;
	private UserProfile userProfile;
	private String emailId;
	private Integer walletAmount;
	private String userId;
	private List<String> orderIds;


	public List<UserAddress> getUserAddresss()
	{
		return userAddresss;
	}

	public void setUserAddresss(List<UserAddress> userAddresss)
	{
		this.userAddresss = userAddresss;
	}

	public UserProfile getUserProfile()
	{
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile)
	{
		this.userProfile = userProfile;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public List<String> getOrderIds()
	{
		return orderIds;
	}

	public void setOrderIds(List<String> orderIds)
	{
		this.orderIds = orderIds;
	}

	public Integer getWalletAmount()
	{
		return walletAmount;
	}

	public void setWalletAmount(Integer walletAmount)
	{
		this.walletAmount = walletAmount;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "userAddresss = " + userAddresss + ", userProfile = "
				+ userProfile + "]";
	}
}
