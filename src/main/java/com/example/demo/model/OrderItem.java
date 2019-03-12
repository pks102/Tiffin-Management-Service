package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="orderId")
	private Order orders;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="vendorItemId")
	private VendorItem vendorItem;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Order getOrders() {
		return orders;
	}
	public void setOrders(Order orders) {
		this.orders = orders;
	}
	public VendorItem getVendorItem() {
		return vendorItem;
	}
	public void setVendorItem(VendorItem vendorItem) {
		this.vendorItem = vendorItem;
	}
	
}
