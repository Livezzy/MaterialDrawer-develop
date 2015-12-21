package com.mikepenz.materialdrawer.app.entity;

import java.util.List;


/**
 * Created by DPM on 10/22/2015.
 */
public class SubCategoryResponse extends Response {

//	public DelearInventory delearInventory;
	public List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
