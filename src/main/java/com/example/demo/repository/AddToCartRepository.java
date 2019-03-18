package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Cart;

public interface AddToCartRepository extends JpaRepository<Cart, Integer>{
	List<Cart> findByUserUserId(int userId);
	Cart findByVendorItemItemNameAndUserUserId(String itemName,int customerId);
}
