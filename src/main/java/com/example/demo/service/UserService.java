package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.LoginForm;
import com.example.demo.model.Order;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.AddToCartRepository;
import com.example.demo.repository.JwtTokenRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTypeRepository;
import com.example.demo.repository.VendorItemRepository;
import com.example.demo.security.JwtProvider;

@Service
public class UserService {
	@Autowired
	JwtTokenRepository tokenRepo;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserTypeRepository userTypeRepository;
	@Autowired
	VendorItemRepository vendorItemRepository;
	@Autowired
	AddToCartRepository cartRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	OrderRepository orderRepository; 

	public ResponseEntity<?> signInVerify(LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);

		User user1 = userRepository.findByUserName(loginRequest.getUserName());

		Token token = new Token();
		if (user1.getToken() != null) {
			int tokenId = user1.getToken().getTokenId();
			token = tokenRepo.findById(tokenId).get();
			token.setJwtToken(jwt);
			tokenRepo.save(token);

			user1.setToken(token);
			userRepository.save(user1);
			System.out.println("token-----------------------" + token);

		} else {
			token.setJwtToken(jwt);
			tokenRepo.save(token);
			user1.setToken(token);
			userRepository.save(user1);
			System.out.println("token  else-----------------------" + token);
		}
		return new ResponseEntity<>(new Response("200", "Succesfully logged in", user1), HttpStatus.OK);
	}

	public ResponseEntity<?> registerUserImpl(User signUpRequest, String userTypeName) {
		if (userTypeName.equalsIgnoreCase("customer")) {
			UserType userType = userTypeRepository.findByUserTypeName("customer");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
			userRepository.save(signUpRequest);
			return new ResponseEntity<>(new Response("200", "Succesfully registered", signUpRequest), HttpStatus.OK);

		} else if (userTypeName.equalsIgnoreCase("vendor")) {
			UserType userType = userTypeRepository.findByUserTypeName("vendor");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
			userRepository.save(signUpRequest);
			return new ResponseEntity<>(new Response("200", "Succesfully registered ", signUpRequest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> updateImpl(User newUser, HttpServletRequest request) {

		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		if (userRepository.findByUserName(username) != null) {
			User oldUser = userRepository.findByUserName(username);
			newUser.setUserId(oldUser.getUserId());
			newUser.setUserType(oldUser.getUserType());
			Token tempToken = oldUser.getToken();
			oldUser = newUser;
			oldUser.setPassword(encoder.encode(newUser.getPassword()));
			oldUser.setToken(tempToken);
			userRepository.save(oldUser);
			return new ResponseEntity<>(new Response("200", "Succesfully updated", oldUser), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<?> showVendorItems(int vendorId) {
		if (userRepository.findById(vendorId) != null && vendorItemRepository.findByUserUserId(vendorId) != null
				&& !vendorItemRepository.findByUserUserId(vendorId).isEmpty()) {

			List<VendorItem> listOfVendorItem = vendorItemRepository.findByUserUserId(vendorId);
			return new ResponseEntity(new Response("200", "Succesfully updated", listOfVendorItem), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> addItemImpl(VendorItem vendorItem, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		System.out.println("username----------------------------------" + username);
		if (userRepository.findByUserName(username) != null) {
			vendorItem.setUser(userRepository.findByUserName(username));
			vendorItemRepository.save(vendorItem);
			return new ResponseEntity(new Response("200", "successfully added Item", vendorItem), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> editItem(int vendorItemId, VendorItem vendorItem, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		if (userRepository.findByUserName(username) != null) {
			User oldUser = userRepository.findByUserName(username);
			VendorItem oldVendorItem = vendorItemRepository.findById(vendorItemId).get();
			System.out.println("----------------------" + oldVendorItem);
			vendorItem.setVendorItemId(oldVendorItem.getVendorItemId());
			vendorItem.setUser(oldUser);
			vendorItemRepository.save(vendorItem);
			return new ResponseEntity(new Response("200", "successfully Edited Item", vendorItem), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteItems(int vendorItemId, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		System.out.println("username----------------------------------" + username);
		if (userRepository.findByUserName(username) != null) {
			User oldUser = userRepository.findByUserName(username);
			VendorItem vendorItem = vendorItemRepository.findById(vendorItemId).get();
			vendorItemRepository.deleteById(vendorItem.getVendorItemId());
			return new ResponseEntity(new Response("200", "successfully Deleted Item", vendorItem), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> ListOfVendors() {

		List<User> vendors = userRepository.findByUserTypeUserTypeId(2);
		System.out.println("in show vendor service");
		return new ResponseEntity(new Response("200", "List of Vendors", vendors), HttpStatus.OK);
	}

	public ResponseEntity<?> addToCartImpl(int vendorItemId, int quantity, HttpServletRequest request) {
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
		if (!cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(),"pending").isEmpty()) {
			List<Cart> cart = cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(),"pending");
			return new ResponseEntity(new Response("200", "List of Cart", cart), HttpStatus.OK);
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

	public ResponseEntity<?> orderImpl(String paymentMode, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token = replaceToken.replace("Bearer ", "");
		String username = jwtProvider.getUserNameFromJwtToken(Token);
		if (userRepository.findByUserName(username) != null) {
		User customer = userRepository.findByUserName(username);
		if(!cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending").isEmpty() && cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending")!=null) {
		List<Cart> cart=cartRepository.findByUserUserIdAndOrderStatus(customer.getUserId(), "pending");
		Iterator<Cart> it=cart.iterator();
		while(it.hasNext()) {
			
			it.next().setOrderStatus("placed");
		}
		Order order=new Order();
		order.setUser(customer);
		order.setPaymentMode(paymentMode);
		orderRepository.save(order);
		return new ResponseEntity(new Response("200", "order successfully placed",cart), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Response("400", "No items available in cart"), HttpStatus.BAD_REQUEST);
		}
		}
		else {
			return new ResponseEntity<>(new Response("400", "Enter valid Details"), HttpStatus.BAD_REQUEST);
		}
		
	}

}
