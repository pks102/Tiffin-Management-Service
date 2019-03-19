
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		return userService.signInVerify(loginRequest);
	}

	@PostMapping("/signup/{userType}")
	public ResponseEntity<?> registerUser(@RequestBody User signUpRequest,
			@PathVariable("userType") String userTypeName) {

		return userService.registerUserImpl(signUpRequest, userTypeName);
	}

	@PostMapping("/user")
	public ResponseEntity<?> updateUser(@RequestBody User user, HttpServletRequest request) {

		return userService.updateImpl(user, request);

	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/vendor") // to show all the vendors
	public ResponseEntity<?> showVendors() {
		return userService.ListOfVendors();
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/vendorItems/{vendorId}") // show items to Customer
	public ResponseEntity<?> showVendorItem(@PathVariable("vendorId") int vendorId) {
		return userService.showVendorItems(vendorId);
	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor/item") // add items for vendor
	public ResponseEntity<?> addItem(@RequestBody VendorItem vendorItem, HttpServletRequest request) {
		return userService.addItemImpl(vendorItem, request);

	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/vendor/{vendorItemId}") // edit items for vendor
	public ResponseEntity<?> editItem(@PathVariable("vendorItemId") int vendorItemId,
			@RequestBody VendorItem vendorItem, HttpServletRequest request) {
		return userService.editItem(vendorItemId, vendorItem, request);
	}

	@PreAuthorize("hasRole('ROLE_vendor')")
	@DeleteMapping("vendor/{vendorItemId}") // delete items for vendor
	public ResponseEntity<?> deleteItem(@PathVariable("vendorItemId") int vendorItemId, HttpServletRequest request) {
		return userService.deleteItems(vendorItemId, request);

	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/addToCart/{vendorItemId}/{quantity}") // add items to cart for customer
	public ResponseEntity<?> addToCart(@PathVariable("vendorItemId") int vendorItemId,
			@PathVariable("quantity") int quantity, HttpServletRequest request) {
		return userService.addToCartImpl(vendorItemId, quantity, request);
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/cart") // view Cart for customer
	public ResponseEntity<?> viewCart(HttpServletRequest request) {
		return userService.viewCartImpl(request);
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@DeleteMapping("/cart/{cartId}") // Delete items from cart for customer
	public ResponseEntity<?> deleteCart(@PathVariable("cartId") int cartId, @RequestBody VendorItem vendorItem,
			HttpServletRequest request) {
		return userService.deleteCartImpl(cartId, request);
	}

	@PreAuthorize("hasRole('ROLE_customer')")
	@GetMapping("/order/{paymentMode}") // placed order
	ResponseEntity<?> payment(@PathVariable("paymentMode") String paymentMode, HttpServletRequest request) {
		return userService.orderImpl(paymentMode, request);
	}

}
