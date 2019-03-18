package com.example.demo.service;

import java.util.ArrayList;
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
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.AddToCartRepository;
import com.example.demo.repository.JwtTokenRepository;
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
	private PasswordEncoder encoder;

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
		return new ResponseEntity<>(new Response("200" ,user1,"Succesfully logged in"), HttpStatus.OK);
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
			return  new ResponseEntity<>(new Response("200" ,signUpRequest,"Succesfully registered"),
                    HttpStatus.OK);

		} else if (userTypeName.equalsIgnoreCase("vendor")) {
			UserType userType = userTypeRepository.findByUserTypeName("vendor");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
			userRepository.save(signUpRequest);
					return  new ResponseEntity<>(new Response("200" ,signUpRequest,"Succesfully registered "),
		                    HttpStatus.OK);
		}else {
			return  new ResponseEntity<>(new Response("400" ,"","Enter valid details"),
                    HttpStatus.BAD_REQUEST);
		}
	}


	public ResponseEntity<?> updateImpl(User newUser,HttpServletRequest request) {

		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 if(userRepository.findByUserName(username)!=null)
		 {
			User oldUser  =userRepository.findByUserName(username); 
			newUser.setUserId(oldUser.getUserId());
			Token tempToken = oldUser.getToken();
			oldUser=newUser;
			oldUser.setPassword(encoder.encode(newUser.getPassword()));
			oldUser.setToken(tempToken);
			userRepository.save(oldUser);
			return  new ResponseEntity<>(new Response("200" ,oldUser,"Succesfully updated"),
                    HttpStatus.OK);		} else {
                    	return  new ResponseEntity<>(new Response("401" ,"","Enter valid Details"),
                                HttpStatus.BAD_REQUEST);		}

	}

	public ResponseEntity<?> showVendorItems(int vendorId) {
		if (userRepository.findById(vendorId) != null && vendorItemRepository.findByUserUserId(vendorId) != null
				&& !vendorItemRepository.findByUserUserId(vendorId).isEmpty())  {
			
				List<VendorItem> listOfVendorItem = vendorItemRepository.findByUserUserId(vendorId);
				return new ResponseEntity(new Response("200" ,listOfVendorItem,"Succesfully updated"),
                    HttpStatus.OK);
			
		} else {
			return  new ResponseEntity<>(new Response("401" ,"","Enter valid Details"),
                    HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> addItemImpl(VendorItem vendorItem,HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 System.out.println("username----------------------------------"+username);
		 if(userRepository.findByUserName(username)!=null)
		 {
			 vendorItem.setUser(userRepository.findByUserName(username));
		vendorItemRepository.save(vendorItem);
		return ResponseEntity.ok().body("successfully added Item");
		}else {
		return ResponseEntity.ok().body("Enter valid vendorId");
		}
	}

	public ResponseEntity<?> editItem(String itemName, VendorItem vendorItem,HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 if(userRepository.findByUserName(username)!=null)
		 {
			 User oldUser=userRepository.findByUserName(username);
			 VendorItem oldVendorItem=vendorItemRepository.findByUserUserIdAndItemName(oldUser.getUserId(),itemName);
		vendorItem.setVendorItemId(oldVendorItem.getVendorItemId());
		vendorItem.setUser(oldUser);
		vendorItemRepository.save(vendorItem);
		return ResponseEntity.ok().body("successfully edited item");
		 }else {
			return ResponseEntity.ok().body("Enter valid itemName");
			}
	}

	public ResponseEntity<?> deleteItems(String itemName,HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 System.out.println("username----------------------------------"+username);
		 if(userRepository.findByUserName(username)!=null)
		 {
		 User oldUser=userRepository.findByUserName(username);
		 VendorItem vendorItem=vendorItemRepository.findByUserUserIdAndItemName(oldUser.getUserId(), itemName);
		vendorItemRepository.deleteById(vendorItem.getVendorItemId());
		return ResponseEntity.ok().body("deleted");
		}else {
			return ResponseEntity.ok().body("Enter valid itemName");
			}
	}

	public ResponseEntity<?> ListOfVendors() {

		List<User> vendors = userRepository.findByUserTypeUserTypeId(2);
		System.out.println("in show vendor service");
		return ResponseEntity.ok().body(vendors);
	}

	public ResponseEntity<?> addToCartImpl(int vendorId,int quantity,String itemName,HttpServletRequest request ) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		 if(userRepository.findByUserName(username)!=null && vendorItemRepository.findByUserUserId(vendorId) != null
					&& !vendorItemRepository.findByUserUserId(vendorId).isEmpty())
		 {
			 User oldUser=userRepository.findByUserName(username);
		VendorItem vendorItem=vendorItemRepository.findByUserUserIdAndItemName(vendorId, itemName);
		Cart cart=new Cart();
		cart.setUser(oldUser);
		cart.setVendorItem(vendorItem);
		cart.setQuantity(quantity);
		cartRepository.save(cart);
		return ResponseEntity.ok().body("item successfully added");
		 }else {
			 return ResponseEntity.ok().body("Invalid vendorId or itemName");
		 }
	}

	public ResponseEntity<?> viewCartImpl(HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		User customer= userRepository.findByUserName(username);
		 List<Cart> cart=cartRepository.findByUserUserId(customer.getUserId());
		 return ResponseEntity.ok().body(cart);
	}

	public ResponseEntity<?> deleteCartImpl(String itemName, HttpServletRequest request) {
		String replaceToken = request.getHeader("Authorization");
		String Token=replaceToken.replace("Bearer ","");
		 String username = jwtProvider.getUserNameFromJwtToken(Token);
		
		if(userRepository.findByUserName(username)!=null && cartRepository.findByVendorItemItemNameAndUserUserId(itemName, userRepository.findByUserName(username).getUserId())!=null) {
			User customer= userRepository.findByUserName(username);
			Cart cart=cartRepository.findByVendorItemItemNameAndUserUserId(itemName, customer.getUserId());
		cartRepository.delete(cart);
		return ResponseEntity.ok().body("item deleted from the cart");
		}else {
			 return ResponseEntity.ok().body(" item not present in cart");
	}
	}

	/*
	 * public ResponseEntity<?> paymentImpl(String paymentMode, HttpServletRequest
	 * request) { String replaceToken = request.getHeader("Authorization"); String
	 * Token=replaceToken.replace("Bearer ",""); String username =
	 * jwtProvider.getUserNameFromJwtToken(Token); User customer=
	 * userRepository.findByUserName(username); return null; }
	 */
}
