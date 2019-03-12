
package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.model.VendorItem;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserTypeRepository;
import com.example.demo.repository.VendorItemRepository;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.UserPrinciple;
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

	/**
	 * @return
	 */
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("registerUser");
		return mv;
	}

	/**
	 * @return
	 */
	@GetMapping("/loginpage")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("loginpage");
		return mv;
	}

	/**
	 * @param userName
	 * @param password
	 * @return
	 */
	@PostMapping("/signin")
	public ModelAndView authenticateUser(@RequestParam("userName") String userName,
			@RequestParam("password") String password) {
		ModelAndView mv = null;
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userName, password);
		System.out.println("--------------" + usernamePasswordAuthenticationToken);
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		System.out.print(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
		System.out.println(jwt);
		if (authentication.isAuthenticated()) {
			UserPrinciple user1 = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Iterator<? extends GrantedAuthority> iterator = user1.getAuthorities().iterator();
			while (iterator.hasNext()) {
				GrantedAuthority grantedAuthority = iterator.next();
				System.out.println(grantedAuthority.getAuthority());
				if (grantedAuthority.getAuthority().equals("ROLE_customer")) {

					mv = new ModelAndView("welcomeCustomer");
					mv.addObject("user", user1);

				} else if (grantedAuthority.getAuthority().equals("ROLE_vendor")) {

					mv = new ModelAndView("welcomeVendor");
					mv.addObject("user", user1);
				}
			}
		} else {
			mv = new ModelAndView("errorPage");
		}
		return mv;

	}

	/**
	 * @param signUpRequest
	 * @param userTypeName
	 * @return
	 */
	@PostMapping("/signup")
	public ModelAndView registerUser(@ModelAttribute("user") User signUpRequest,
			@RequestParam("utype") String userTypeName) {
		// Creating user's account
		System.out.println(signUpRequest + "---------------");
		System.out.println(userTypeName + "---------------");

		if (userTypeName.equals("customer")) {
			UserType userType = userTypeRepository.findByUserTypeName("customer");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			System.out.println("-------" + signUpRequest);
			userService.addCustomer(signUpRequest);

		} else if (userTypeName.equalsIgnoreCase("vendor")) {
			UserType userType = userTypeRepository.findByUserTypeName("vendor");
			userType.setUserTypeId(userType.getUserTypeId());
			List<UserType> userTypes = new ArrayList<>();
			userTypes.add(userType);
			signUpRequest.setUserType(userTypes);
			userService.addVendor(signUpRequest);
		}
		return new ModelAndView("loginpage");
	}

	/**
	 * @param userId
	 * @return
	 */
	@PostMapping("/editUser")
	public ModelAndView edit(@RequestParam("userId") int userId) {
		ModelAndView mv = null;
		mv = userService.edit(userId);
		System.out.println("in edit controller");
		return mv;

	}

	/**
	 * @return
	 */
	@PostMapping("/showVendors") // to show all the vendors
	public ModelAndView showVendors() {
		System.out.println("in show vendor controller");
		ModelAndView mv = userService.ListOfVendors();
		return mv;
	}

	/**
	 * @param userId
	 * @return
	 */
	@PostMapping("/showVendorItems") // show items to Customer
	public ModelAndView showVendorItem(@RequestParam("userId") int userId) {
		System.out.println("in show vendor controller");
		ModelAndView mv = userService.showVendorItems(userId);
		return mv;
	}
	@PostMapping("/addToCart") // show items to Customer
	public ModelAndView addToCart(@RequestParam("vendorId") int userId,@RequestParam("vendorItemId") int vendorItemId) {
		System.out.println("in addto cart controller"+userId);
		ModelAndView mv = userService.addToCartImpl(userId,vendorItemId);
		return mv;
	}
	
	@PostMapping("viewCart")
public ModelAndView viewCart(@RequestParam("cart")int vendorItemId) {
		ModelAndView mv = userService.viewCartImpl(vendorItemId);
		return mv;
	}
	/**
	 * @param userId
	 * @return
	 */
	@PostMapping("/changePassword")
	public ModelAndView changePassword(@RequestParam("userId") int userId) {
		ModelAndView mv = null;
		mv = userService.editPassword(userId);
		System.out.println("in change password controller");
		return mv;

	}

	/**
	 * @param userId
	 * @param newPassword
	 * @param confirmPassword
	 * @return
	 */
	@PostMapping("/updatePassword")
	public ModelAndView updatePassword(@RequestParam("userId") int userId,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
		ModelAndView mv = null;
		mv = userService.updatePassword(userId, newPassword, confirmPassword);
		System.out.println("after update");
		return mv;
	}

	/**
	 * @param user
	 * @return
	 */
	@PostMapping("/updateUser")
	public ModelAndView update(User user) {
		ModelAndView mv = null;
		mv = userService.update(user);
		return mv;
	}

	/**
	 * @param userId
	 * @return
	 */
	@PostMapping("/item")
	public ModelAndView item(@RequestParam("userId") int userId) {
		User user = new User();
		user.setUserId(userId);
		ModelAndView mv = new ModelAndView("addItem", "user", user);
		return mv;
	}

	/**
	 * @param itemName
	 * @param price
	 * @param userId
	 * @return
	 */
	@PostMapping("/addItem")
	public ModelAndView add(@RequestParam("itemName") String itemName, @RequestParam("price") BigDecimal price,
			@RequestParam("userId") int userId) {
		ModelAndView mv = null;
		mv = userService.addItem(itemName, price, userId);
		return mv;
	}

	/**
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_vendor')")
	@PostMapping("/viewItems")
	public ModelAndView viewItem(@RequestParam("userId") int userId) {
		System.out.println("in view item");
		ModelAndView mv = userService.viewItems(userId);
		return mv;
	}

	/**
	 * @param vendorItemId
	 * @return
	 */
	@PostMapping("/editItem")
	public ModelAndView editItem(@RequestParam("vendorItemId") int vendorItemId) {
		ModelAndView mv = null;
		mv = userService.editItem(vendorItemId);
		return mv;

	}

	/**
	 * @param vendorItem
	 * @return
	 */
	@PostMapping("/updateItem")
	public ModelAndView updateItem(VendorItem vendorItem) {

		ModelAndView mv = null;
		mv = userService.updateItem(vendorItem);
		return mv;
	}

	/**
	 * @param vendorItemId
	 * @return
	 */
	@PostMapping("/deleteItem")
	public ModelAndView deleteItem(@RequestParam("vendorItemId") int vendorItemId) {
		ModelAndView mv = null;
		mv = userService.deleteItems(vendorItemId);
		return mv;

	}

	/**
	 * @return
	 */
	@GetMapping("/errorPage")
	public ModelAndView error() {
		return new ModelAndView("errorPage");
	}

}
