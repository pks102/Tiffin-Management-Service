package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService cartService;

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/addToCart/{vendorItemId}/{quantity}") // add items to cart for customer
	public ResponseEntity<?> addToCart(@PathVariable("vendorItemId") int vendorItemId,
			@PathVariable("quantity") long quantity, HttpServletRequest request) {
		return cartService.addToCartImpl(vendorItemId, quantity, request);
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/cart") // view Cart for customer
	public ResponseEntity<?> viewCart(HttpServletRequest request) {
		return cartService.viewCartImpl(request);
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@DeleteMapping("/cart/{cartId}") // Delete items from cart for customer
	public ResponseEntity<?> deleteCart(@PathVariable("cartId") int cartId, HttpServletRequest request) {
		return cartService.deleteCartImpl(cartId, request);
	}
}
