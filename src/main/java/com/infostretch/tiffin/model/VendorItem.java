package com.infostretch.tiffin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VendorItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int vendorItemId;
	
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "vendorId")
    private User user;
	
    @Override
	public String toString() {
		return "VendorItem [vendorItemId=" + vendorItemId + ", user=" + user + ", itemName=" + itemName + ", price="
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
	public VendorItem() {
		super();
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
