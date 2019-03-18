
package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.LoginForm;
import com.example.demo.model.User;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.JwtTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTypeRepository;
import com.example.demo.repository.VendorItemRepository;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.UserService;

/**
 * @author kartik.parmar
 *
 */
@RestController
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	VendorItemRepository vendorItemRepository;
	@Autowired
	UserService userService;
	@Autowired
	UserTypeRepository userTypeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	JwtTokenRepository tokenRepo;

	
	/**
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		return userService.signInVerify(loginRequest);
	}

	/**
	 * @param signUpRequest
	 * @param userTypeName
	 * @return
	 */
	@PostMapping("/signup/{userType}")
	public ResponseEntity<?> registerUser(@RequestBody User signUpRequest, @PathVariable("userType") String userTypeName) {

		return userService.registerUserImpl(signUpRequest, userTypeName);
	}

	/**
	 * @param userId
	 * @return
	 */
	@PutMapping("/editUser")
	public ResponseEntity<?> updateUser(
			@RequestBody User user,HttpServletRequest request) {

		return userService.updateImpl(user,request);

	}

	/**
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/showVendors") // to show all the vendors
	public ResponseEntity<?> showVendors() {
		return userService.ListOfVendors();
	}

	
	/**
	 * @param userId
	 * @return
	 */
	@GetMapping("/showVendorItems/{userId}") // show items to Customer
	public ResponseEntity<?> showVendorItem(@PathVariable("userId") int userId) {
		return userService.showVendorItems(userId);
	}
	
	/**
	 * @param vendorItem
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/addItem")
	public ResponseEntity<?> addItem(@RequestBody VendorItem vendorItem,HttpServletRequest request) {
	return	userService.addItemImpl(vendorItem,request);
		
	}
	

	/**
	 * @param vendorId
	 * @param itemName
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_customer')")
	@PostMapping("/addToCart/{vendorId}/{itemName}/{quantity}") // show items to Customer
	public ResponseEntity<?> addToCart(@PathVariable("vendorId") int vendorId,@PathVariable("quantity")int quantity,@PathVariable("itemName")String itemName,HttpServletRequest request) {
		return userService.addToCartImpl(vendorId,quantity,itemName,request);
	}

	/**
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/viewCart")
	public ResponseEntity<?> viewCart(HttpServletRequest request) {
		return userService.viewCartImpl(request);
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@PostMapping("/deleteCart/{itemName}") // show items to Customer
	public ResponseEntity<?> deleteCart(@PathVariable("itemName")String itemName,@RequestBody VendorItem vendorItem,HttpServletRequest request) {
		return userService.deleteCartImpl(itemName,request);
	}
	
	
	/*
	 * @PreAuthorize("hasRole('ROLE_customer')")
	 * 
	 * @PostMapping("/payment/{paymentMode}") // show items to Customer public
	 * ResponseEntity<?> payment(@PathVariable("paymentMode")String
	 * paymentMode,HttpServletRequest request) { return
	 * userService.paymentImpl(paymentMode,request); }
	 */

	/**
	 * @param vendorId
	 * @param itemName
	 * @param vendorItem
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_vendor')")
	@PutMapping("/editItem/{itemName}")
	public ResponseEntity<?> editItem(@PathVariable("itemName")String itemName,@RequestBody VendorItem vendorItem,HttpServletRequest request) {
		return userService.editItem(itemName,vendorItem,request);
}

	
	/**
	 * @param vendorItemId
	 * @param itemName
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_vendor')")
	@DeleteMapping("/deleteItem/{itemName}")
	public ResponseEntity<?> deleteItem(@PathVariable("itemName")String itemName,HttpServletRequest request) {
		return userService.deleteItems(itemName, request);

	}


}
