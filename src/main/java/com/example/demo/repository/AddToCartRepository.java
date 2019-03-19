package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Cart;

public interface AddToCartRepository extends JpaRepository<Cart, Integer>{
	List<Cart> findByUserUserIdAndOrderStatus(int userId,String orderStatus);
	
}
