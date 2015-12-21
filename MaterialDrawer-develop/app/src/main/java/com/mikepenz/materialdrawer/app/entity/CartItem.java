package com.mikepenz.materialdrawer.app.entity;

import java.math.BigDecimal;

public class CartItem {

		private String skuCode;
		private String categoryId;

		private String name;
		private Integer qty;
		private BigDecimal displayPrice;
		private Integer numberOfItems;

		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getQty() {
			return qty;
		}

		public void setQty(Integer qty) {
			this.qty = qty;
		}

		public BigDecimal getDisplayPrice() {
			return displayPrice;
		}

		public void setDisplayPrice(BigDecimal displayPrice) {
			this.displayPrice = displayPrice;
		}

		public String getSkuCode() {
			return skuCode;
		}

		public void setSkuCode(String skuCode) {
			this.skuCode = skuCode;
		}

		public Integer getNumberOfItems() {
			return numberOfItems;
		}

		public void setNumberOfItems(Integer numberOfItems) {
			this.numberOfItems = numberOfItems;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}

	}