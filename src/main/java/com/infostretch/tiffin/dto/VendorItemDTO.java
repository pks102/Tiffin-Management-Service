package com.infostretch.tiffin.dto;

import com.infostretch.tiffin.model.User;

public class VendorItemDTO {
	private int vendorItemId;

	private User user;

	@Override
	public String toString() {
		return "VendorItemDTO [vendorItemId=" + vendorItemId + ", user=" + user + ", itemName=" + itemName + ", price="
				+ price + "]";
	}

	private String itemName;

	private double price;

	public int getVendorItemId() {
		return vendorItemId;
	}

	public void setVendorItemId(int vendorItemId) {
		this.vendorItemId = vendorItemId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
