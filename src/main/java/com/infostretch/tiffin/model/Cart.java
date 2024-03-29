package com.infostretch.tiffin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cartId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "customerId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "vendorItemId")
	private VendorItem vendorItem;

	private double quantity;
	
	private String orderStatus;
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity2) {
		this.quantity = quantity2;
	}
	
	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", user=" + user + ", vendorItem=" + vendorItem + ", quantity=" + quantity
				+ ", orderStatus=" + orderStatus + "]";
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VendorItem getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(VendorItem vendorItem) {
		this.vendorItem = vendorItem;
	}
	
}
