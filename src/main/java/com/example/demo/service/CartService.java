package com.example.demo.service;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.AddToCartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorItemRepository;
import com.example.demo.security.JwtProvider;

@Service
public class CartService {

	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorItemRepository vendorItemRepository;
	@Autowired
	AddToCartRepository cartRepository;

	public ResponseEntity<?> addToCartImpl(int vendorItemId, long quantity, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		if (userRepository.findByUserName(username) != null && vendorItemRepository.findById(vendorItemId) != null) {
			User oldUser = userRepository.findByUserName(username);
			VendorItem vendorItem = vendorItemRepository.findById(vendorItemId).get();
			Cart cart = new Cart();
			cart.setUser(oldUser);
			cart.setVendorItem(vendorItem);
			cart.setQuantity(quantity);
			cart.setOrderStatus("pending");
			cartRepository.save(cart);
			return new ResponseEntity(new Response("200", "item successfully added", cart), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> viewCartImpl(HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		User customer = userRepository.findByUserName(username);
		if (!cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending").isEmpty()) {
			List<Cart> cart = cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending");
			
			double totalAmount=0;
			
			for(Cart ls:cart) {
				double price=0;
				double quantity=1;
			quantity=quantity * (ls.getQuantity());
			price=price+ls.getVendorItem().getPrice();
			totalAmount= totalAmount+(price*quantity);
				
			}
			return new ResponseEntity(new Response("200","Total amount to be paid :"+totalAmount, cart), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteCartImpl(int cartId, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);

		if (userRepository.findByUserName(username) != null && cartRepository.findById(cartId).get() != null) {
			Cart cart = cartRepository.findById(cartId).get();
			cartRepository.delete(cart);
			return new ResponseEntity(new Response("200", "item deleted from the cart", cart), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}
}
