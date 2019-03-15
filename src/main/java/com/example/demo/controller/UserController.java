
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.LoginForm;
import com.example.demo.model.User;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.JwtTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTypeRepository;
import com.example.demo.repository.VendorItemRepository;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.UserService;

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
	public void registerUser(@RequestBody User signUpRequest, @PathVariable("userType") String userTypeName) {

		userService.registerUserImpl(signUpRequest, userTypeName);
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
		System.out.println("in show vendor controller");
		return userService.ListOfVendors();
	}

	
	/**
	 * @param userId
	 * @return
	 */
	@GetMapping("/showVendorItems/{userId}") // show items to Customer
	public ResponseEntity<?> showVendorItem(@PathVariable("userId") int userId) {
		System.out.println("in show vendor Items controller");
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
	 * @param userId
	 * @param vendorItemId
	 * @return
	 */
	@PostMapping("/addToCart") // show items to Customer
	public ModelAndView addToCart(@RequestParam("vendorId") int userId,
			@RequestParam("vendorItemId") int vendorItemId) {
		System.out.println("in addto cart controller" + userId);
		ModelAndView mv = userService.addToCartImpl(userId, vendorItemId);
		return mv;
	}

	/**
	 * @param vendorItemId
	 * @return
	 */
	@PostMapping("viewCart")
	public ModelAndView viewCart(@RequestParam("cart") int vendorItemId) {
		ModelAndView mv = userService.viewCartImpl(vendorItemId);
		return mv;
	}


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
