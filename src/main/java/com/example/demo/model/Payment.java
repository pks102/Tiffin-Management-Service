package com.example.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int paymentId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="orderId")
	private Cart cart;
	

private String paymentMode;

	

	public String getPaymentMode() {
	return paymentMode;
}


public void setPaymentMode(String paymentMode) {
	this.paymentMode = paymentMode;
}

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date paymentDate;

	
	
	public Payment() {
		super();
	}

	
	
	
	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}


	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", cart=" + cart + ", paymentDate=" + paymentDate + "]";
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
