package com.infostretch.tiffin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infostretch.tiffin.model.Cart;

public interface AddToCartRepository extends JpaRepository<Cart, Integer>{
	Optional<List<Cart>> findByUserUserIdAndOrderStatus(int userId,String orderStatus);
	
}
