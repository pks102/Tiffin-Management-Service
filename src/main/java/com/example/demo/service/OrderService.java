package com.example.demo.service;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.AddToCartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;

@Service
public class OrderService {

	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	AddToCartRepository cartRepository;

	public ResponseEntity<?> orderImpl(String paymentMode, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		if (userRepository.findByUserName(username) != null) {
			User customer = userRepository.findByUserName(username);
			if (!cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending").isEmpty()
					&& cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending") != null) {
				List<Cart> cart = cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending");
				Iterator<Cart> it = cart.iterator();
				while (it.hasNext()) {

					it.next().setOrderStatus("placed");
				}
				Order order = new Order();
				order.setUser(customer);
				order.setPaymentMode(paymentMode);
				orderRepository.save(order);
				return new ResponseEntity(new Response("200", "order successfully placed", cart), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new Response("400", "No items available in cart"), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}

	}
}
