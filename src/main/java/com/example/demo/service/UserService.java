package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.model.VendorItem;
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
	private PasswordEncoder encoder;

	public ModelAndView signInVerify(String userName, String password) {
		ModelAndView mv = null;
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userName, password);
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
		User user = userRepository.findByUserName(userName);
		Token token = new Token();
		if (user.getToken() != null) {
			int tokenId = user.getToken().getTokenId();
			token = tokenRepo.findById(tokenId).get();
			token.setJwtToken(jwt);
			tokenRepo.save(token);

			user.setToken(token);
			userRepository.save(user);
		} else {

			token.setJwtToken(jwt);
			tokenRepo.save(token);

			user.setToken(token);
			userRepository.save(user);
		}
		if (authentication.isAuthenticated()) {
			UserPrinciple user1 = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Iterator<? extends GrantedAuthority> iterator = user1.getAuthorities().iterator();
			while (iterator.hasNext()) {
				GrantedAuthority grantedAuthority = iterator.next();
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

	public void addCustomer(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void addVendor(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		System.out.println(user);
		userRepository.save(user);
	}

	public ModelAndView edit(int userId) {
		User user = new User();
		user = userRepository.findById(userId).get();

		ModelAndView mv = new ModelAndView("update", "user", user);
		System.out.println("in edit service");
		return mv;
	}

	public ModelAndView update(User newUser) {
		// get the oldUser using the id passed in newUser through hidden form field in
		// welcomeCustomer.jsp
		User oldUser = userRepository.findById(newUser.getUserId()).get();
		ModelAndView mv = null;
		// get the id of oldUser
		int userId = oldUser.getUserId();
		// get the roles of the oldUser
		List<UserType> roles = oldUser.getUserType();
		// set the roles of the newUser based on the retrieved roles of oldUser
		newUser.setUserType(roles);
		// set the userId of the newUser to the id of oldUser.
		newUser.setUserId(userId);
		userRepository.save(newUser);
		// redirect to the to page welcome based on type of user ie Customer or vendor
		Iterator<UserType> it = roles.iterator();
		while (it.hasNext()) {
			// get the role of the user
			String role = it.next().getUserTypeName();
			if (role.equals("customer")) {
				mv = new ModelAndView("welcomeCustomer", "user", newUser);
			} else if (role.equals("vendor")) {
				mv = new ModelAndView("welcomeVendor", "user", newUser);
			}
		}

		return mv;
	}

	public ModelAndView addItem(String itemName, BigDecimal price, int userId) {
		User user = new User();
		user = userRepository.findById(userId).get();
		VendorItem vendorItem = new VendorItem();
		vendorItem.setItemName(itemName);
		vendorItem.setPrice(price);
		vendorItem.setUser(user);
		vendorItemRepository.save(vendorItem);
		ModelAndView mv = new ModelAndView("welcomeVendor", "user", user);
		return mv;
	}

	public ModelAndView viewItems(int userId) {
		System.out.println("in view item service");
		List<VendorItem> ls = vendorItemRepository.findByUserUserId(userId);
		ModelAndView mv = new ModelAndView("viewItem", "list", ls);
		return mv;
	}

	public ModelAndView editItem(int vendorItemId) {
		VendorItem vendorItem = new VendorItem();
		vendorItem = vendorItemRepository.findById(vendorItemId).get();
		ModelAndView mv = new ModelAndView("updateItem", "vendorItem", vendorItem);
		System.out.println("in edit service");
		return mv;

	}

	public ModelAndView updateItem(VendorItem vendorItem) {
		VendorItem oldList = vendorItemRepository.findById(vendorItem.getVendorItemId()).get();
		User user = oldList.getUser();
		oldList = vendorItem;
		oldList.setUser(user);
		vendorItemRepository.save(oldList);
		ModelAndView mv = new ModelAndView("welcomeVendor", "user", user);
		return mv;
	}

	public ModelAndView deleteItems(int vendorItemId) {
		VendorItem vendorItem = vendorItemRepository.findById(vendorItemId).get();
		User user = vendorItem.getUser();
		ModelAndView mv = new ModelAndView("welcomeVendor", "user", user);
		vendorItemRepository.deleteById(vendorItemId);
		return mv;
	}

	public ModelAndView editPassword(int userId) {
		User user = new User();
		user = userRepository.findById(userId).get();
		ModelAndView mv = new ModelAndView("updatePassword", "user", user);
		return mv;

	}

	public ModelAndView updatePassword(int userId, String newPassword, String confirmPassword) {
		ModelAndView mv = null;
		User user = new User();
		user = userRepository.findById(userId).get();
		System.out.println(user);
		System.out.println(user.getPassword());

		if (newPassword.equals(confirmPassword)) {
			user.setPassword(encoder.encode(newPassword));

			System.out.println(encoder.encode(newPassword));
			userRepository.save(user);
			List<UserType> roles = user.getUserType();
			// get the role of the user
			String role = roles.get(0).getUserTypeName();
			System.out.println(role);
			if (role.equals("customer")) {
				mv = new ModelAndView("welcomeCustomer", "user", user);
			} else if (role.equals("vendor")) {
				mv = new ModelAndView("welcomeVendor", "user", user);
			}

		} else {
			mv = new ModelAndView("errorPage");
		}
		return mv;
	}

	public ModelAndView ListOfVendors() {
		List<User> vendors = userRepository.findByUserTypeUserTypeId(2);
		System.out.println("in show vendor service");
		return new ModelAndView("showVendorList", "vendorList", vendors);
	}

	public ModelAndView showVendorItems(int userId) {
		List<VendorItem> listOfVendorItem = vendorItemRepository.findByUserUserId(userId);
		User vendor = userRepository.findById(userId).get();
		ModelAndView mv = new ModelAndView("displayItem", "vendorItem", listOfVendorItem).addObject("vendor", vendor);
		return mv;
	}

	public ModelAndView addToCartImpl(int userId, int vendorItemId) {
		List<VendorItem> listOfVendorItem = vendorItemRepository.findByUserUserId(userId);// for displaying specific
																							// vendors item
		User vendor = userRepository.findById(userId).get();// get selected vendor
		List<VendorItem> cart = new ArrayList<VendorItem>();
		cart.add(vendorItemRepository.findById(vendorItemId).get());// add to cart
		ModelAndView mv = new ModelAndView("displayItem", "vendorItem", listOfVendorItem).addObject("vendor", vendor)
				.addObject("cart", cart);
		System.out.println(cart);
		return mv;
	}

	public ModelAndView viewCartImpl(int vendorItemId) {
		VendorItem vendorItem = vendorItemRepository.findById(vendorItemId).get();
		ModelAndView mv = new ModelAndView("displayCart", "vendorItem", vendorItem);
		return mv;
	}

}
