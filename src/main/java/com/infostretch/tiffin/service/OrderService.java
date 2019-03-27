package com.infostretch.tiffin.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infostretch.tiffin.model.Cart;
import com.infostretch.tiffin.model.Order;
import com.infostretch.tiffin.model.User;
import com.infostretch.tiffin.repository.AddToCartRepository;
import com.infostretch.tiffin.repository.OrderRepository;
import com.infostretch.tiffin.repository.UserRepository;
import com.infostretch.tiffin.security.JwtProvider;
import com.infostretch.tiffin.utility.UtilityClass;

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
	@Autowired
	UtilityClass utilityClass;
	public ResponseEntity<Order> orderImpl(String paymentMode, HttpServletRequest request) {
		String userName = utilityClass.forToken(request);
		Optional<User> customer1 = userRepository.findByUserName(userName);
		if (customer1.isPresent()) {
			User customer=customer1.get();
			Optional<List<Cart>> cart1=cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(),Constants.PENDING);
			if (cart1.isPresent()) {
				List<Cart> cart = cart1.get();
				Iterator<Cart> it = cart.iterator();
				while (it.hasNext()) {
					it.next().setOrderStatus(Constants.PLACED);
				}
				Order order = new Order();
				order.setUser(customer);
				order.setPaymentMode(paymentMode);
				orderRepository.save(order);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
}
